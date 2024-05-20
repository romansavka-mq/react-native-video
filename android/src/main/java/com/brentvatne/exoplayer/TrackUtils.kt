package com.brentvatne.exoplayer

import android.content.Context
import android.content.Context.CAPTIONING_SERVICE
import android.view.accessibility.CaptioningManager
import androidx.media3.common.C
import androidx.media3.common.Format
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector.Parameters
import androidx.media3.exoplayer.trackselection.ExoTrackSelection
import androidx.media3.exoplayer.trackselection.MappingTrackSelector.MappedTrackInfo
import androidx.media3.exoplayer.trackselection.TrackSelectionArray
import com.brentvatne.exoplayer.LocaleUtils.getLanguageDisplayName
import com.facebook.react.bridge.Dynamic
import com.facebook.react.bridge.ReadableType
import java.util.Locale

object TracksUtil {

    @JvmStatic
    fun getAudioTracks(info: MappedTrackInfo?): List<TrackInfo> = getTracks(info, C.TRACK_TYPE_AUDIO)

    @JvmStatic
    fun getVideoTracks(info: MappedTrackInfo?): List<TrackInfo> = getTracks(info, C.TRACK_TYPE_VIDEO)

    @JvmStatic
    fun getTextTracks(info: MappedTrackInfo?): List<TrackInfo> = getTracks(info, C.TRACK_TYPE_TEXT)

    @JvmStatic
    fun getSelectedAudioTrack(info: MappedTrackInfo?, tracks: TrackSelectionArray?): TrackInfo? = getSelectedTrack(info, tracks, C.TRACK_TYPE_AUDIO)

    @JvmStatic
    fun getSelectedVideoTrack(info: MappedTrackInfo?, tracks: TrackSelectionArray?): TrackInfo? = getSelectedTrack(info, tracks, C.TRACK_TYPE_VIDEO)

    @JvmStatic
    fun getSelectedTextTrack(info: MappedTrackInfo?, tracks: TrackSelectionArray?): TrackInfo? = getSelectedTrack(info, tracks, C.TRACK_TYPE_TEXT)

    @JvmStatic
    fun selectionChanged(lha: TrackSelectionArray?, rha: TrackSelectionArray?): Boolean {
        if (lha == null || lha !== rha) {
            return true
        } else {
            for (i in 0 until lha.length) {
                val lhSelection = lha[i] as ExoTrackSelection?
                val rhSelection = (rha[i] as ExoTrackSelection?)
                if (lhSelection != null && rhSelection != null && lhSelection.selectedFormat !== rhSelection.selectedFormat) {
                    return true
                }
            }
        }
        return false
    }

    @JvmStatic
    fun buildSelectionParameters(
        context: Context,
        initialParameters: Parameters,
        info: MappedTrackInfo,
        trackType: Int,
        type: String?,
        value: Dynamic?
    ): Parameters {
        val selection = mutableListOf<TrackInfo>()
        val builder = initialParameters.buildUpon()
        when (if (type != null && value != null) type else "default") {
            "disabled" -> {
                builder.setTrackTypeDisabled(trackType, true)
                // return without changing any overrides
                return builder.build()
            }

            "language" -> {
                val expected = value?.asString()
                if (trackType == C.TRACK_TYPE_AUDIO) {
                    builder.setPreferredAudioLanguage(expected)
                } else if (trackType == C.TRACK_TYPE_TEXT) {
                    builder.setPreferredTextLanguage(expected)
                }
            }

            "title" -> {
                val expected = value?.asString()
                val track = searchTrack(info, trackType) { format ->
                    expected == getLanguageDisplayName(format.language)
                }
                if (track != null) selection.add(track)
            }

            "resolution" -> {
                val expected = getDynamicInt(value)
                val track = searchTrack(info, trackType) { format ->
                    expected == format.height
                }
                if (track != null) selection.add(track)
            }

            "index" -> {
                val complexIndex = getDynamicLong(value)
                val renderIndex: Int = TrackInfo.parseRenderIndex(complexIndex)
                val groupIndex: Int = TrackInfo.parseGroupIndex(complexIndex)
                val trackIndex: Int = TrackInfo.parseTrackIndex(complexIndex)
                if (renderIndex >= 0 && renderIndex < info.rendererCount) {
                    val groups = info.getTrackGroups(renderIndex)
                    if (groupIndex >= 0 && groupIndex < groups.length) {
                        val group = groups[groupIndex]
                        if (trackIndex >= 0 && trackIndex < group.length) {
                            val format: Format = group.getFormat(trackIndex)
                            val selectionTrackTypeType = info.getRendererType(renderIndex)
                            selection.add(TrackInfo(selectionTrackTypeType, renderIndex, groupIndex, trackIndex, format))
                        }
                    }
                }
            }

            "auto", "system", "default" -> {
                // Enable all renders and clear any strict selection overrides
                // In case of C.TRACK_TYPE_VIDEO - all video tracks as valid options for ABR to choose from
                builder.setTrackTypeDisabled(trackType, false)
                builder.clearOverridesOfType(trackType)

                // Apply audio/subtitle selection base on current locale
                val currentLocale = Locale.getDefault()
                val locale2 = currentLocale.language // 2 letter code
                val locale3 = currentLocale.isO3Language // 3 letter code

                if (trackType == C.TRACK_TYPE_AUDIO) { // Default audio selection
                    val track = searchTrack(info, trackType) { format ->
                        format.language != null && (format.language.equals(locale2) || format.language.equals(locale3))
                    }
                    val preferred = track?.format?.language
                    builder.setPreferredAudioLanguage(preferred)
                } else if (trackType == C.TRACK_TYPE_TEXT) { // Default subtitle selection
                    val captioning = context.getSystemService(CAPTIONING_SERVICE) as CaptioningManager
                    if (captioning.isEnabled) {
                        val track = searchTrack(info, trackType) { format ->
                            format.language != null && (format.language.equals(locale2) || format.language.equals(locale3))
                        }
                        val preferred = track?.format?.language
                        builder.setPreferredTextLanguage(preferred)
                    }
                }
            }

            else -> {}
        }
        // Apply strict selection overrides
        for (track in selection) {
            val renderIndex = track.renderIndex
            val trackGroups = info.getTrackGroups(renderIndex)
            builder.setRendererDisabled(renderIndex, false)
            builder.clearOverridesOfType(track.type)
            builder.addOverride(TrackSelectionOverride(trackGroups[track.groupIndex], track.trackIndex))
        }
        return builder.build()
    }

    private fun searchTrack(info: MappedTrackInfo, trackType: Int, predicate: (Format) -> Boolean): TrackInfo? {
        for (renderIndex in 0 until info.rendererCount) {
            if (info.getRendererType(renderIndex) != trackType) continue
            val groups = info.getTrackGroups(renderIndex)
            for (groupIndex in 0 until groups.length) {
                val group = groups[groupIndex]
                for (trackIndex in 0 until group.length) {
                    if (info.getTrackSupport(renderIndex, groupIndex, trackIndex) == C.FORMAT_HANDLED) {
                        val format: Format = group.getFormat(trackIndex)
                        if (predicate(format)) {
                            return TrackInfo(trackType, renderIndex, groupIndex, trackIndex, format)
                        }
                    }
                }
            }
        }
        return null
    }

    private fun getTracks(info: MappedTrackInfo?, trackType: Int): List<TrackInfo> {
        if (info == null) return emptyList()
        val tracks = ArrayList<TrackInfo>()
        for (renderIndex in 0 until info.rendererCount) {
            if (info.getRendererType(renderIndex) != trackType) continue
            val groups = info.getTrackGroups(renderIndex)
            for (groupIndex in 0 until groups.length) {
                val group = groups[groupIndex]
                for (trackIndex in 0 until group.length) {
                    if (info.getTrackSupport(renderIndex, groupIndex, trackIndex) == C.FORMAT_HANDLED) {
                        val format: Format = group.getFormat(trackIndex)
                        tracks.add(TrackInfo(trackType, renderIndex, groupIndex, trackIndex, format))
                    }
                }
            }
        }
        return tracks
    }

    private fun getSelectedTrack(info: MappedTrackInfo?, selections: TrackSelectionArray?, trackType: Int): TrackInfo? {
        if (info == null || selections == null) {
            return null
        }
        for (renderIndex in 0 until info.rendererCount) {
            val rendererType = info.getRendererType(renderIndex)
            if (rendererType != trackType) continue
            val selection = selections[renderIndex] as? ExoTrackSelection ?: continue

            val renderGroups = info.getTrackGroups(renderIndex)
            val selectedTrackGroup = selection.trackGroup
            val selectedFormat = selection.selectedFormat
            val groupIndex = renderGroups.indexOf(selectedTrackGroup)
            val formatIndex = selectedTrackGroup.indexOf(selectedFormat)
            return TrackInfo(trackType, renderIndex, groupIndex, formatIndex, selectedFormat)
        }
        return null
    }

    private fun getDynamicLong(value: Dynamic?): Long =
        when (value?.type) {
            ReadableType.Number -> value.asDouble().toLong()
            ReadableType.String -> value.asString().toLong()
            else -> throw IllegalArgumentException("Unable to read long from $value")
        }

    private fun getDynamicInt(value: Dynamic?): Int =
        when (value?.type) {
            ReadableType.Number -> value.asDouble().toInt()
            ReadableType.String -> value.asString().toInt()
            else -> throw IllegalArgumentException("Unable to read int from $value")
        }
}
