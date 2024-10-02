package com.brentvatne.common.react

import androidx.media3.common.Format
import com.brentvatne.common.api.TimedMetadata
import com.brentvatne.exoplayer.LocaleUtils.getLanguageDisplayName
import com.brentvatne.exoplayer.ManifestUtils.getRepresentationFileName
import com.brentvatne.exoplayer.ManifestUtils.getRepresentationOf
import com.brentvatne.exoplayer.ManifestUtils.getRepresentationSupplementalProperties
import com.brentvatne.exoplayer.ReactExoplayerView
import com.brentvatne.exoplayer.TrackInfo
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.UIManagerHelper
import com.facebook.react.uimanager.events.Event
import com.facebook.react.uimanager.events.EventDispatcher
import java.io.PrintWriter
import java.io.StringWriter

enum class EventTypes(val eventName: String) {
    EVENT_LOAD_START("onVideoLoadStart"),
    EVENT_LOAD("onVideoLoad"),
    EVENT_ERROR("onVideoError"),
    EVENT_PROGRESS("onVideoProgress"),
    EVENT_BANDWIDTH("onVideoBandwidthUpdate"),
    EVENT_CONTROLS_VISIBILITY_CHANGE("onControlsVisibilityChange"),
    EVENT_SEEK("onVideoSeek"),
    EVENT_END("onVideoEnd"),
    EVENT_FULLSCREEN_WILL_PRESENT("onVideoFullscreenPlayerWillPresent"),
    EVENT_FULLSCREEN_DID_PRESENT("onVideoFullscreenPlayerDidPresent"),
    EVENT_FULLSCREEN_WILL_DISMISS("onVideoFullscreenPlayerWillDismiss"),
    EVENT_FULLSCREEN_DID_DISMISS("onVideoFullscreenPlayerDidDismiss"),

    EVENT_READY("onReadyForDisplay"),
    EVENT_BUFFER("onVideoBuffer"),
    EVENT_PLAYBACK_STATE_CHANGED("onVideoPlaybackStateChanged"),
    EVENT_IDLE("onVideoIdle"),
    EVENT_TIMED_METADATA("onTimedMetadata"),
    EVENT_AUDIO_BECOMING_NOISY("onVideoAudioBecomingNoisy"),
    EVENT_AUDIO_FOCUS_CHANGE("onAudioFocusChanged"),
    EVENT_PLAYBACK_RATE_CHANGE("onPlaybackRateChange"),
    EVENT_VOLUME_CHANGE("onVolumeChange"),
    EVENT_AUDIO_TRACKS("onAudioTracks"),
    EVENT_TEXT_TRACKS("onTextTracks"),

    EVENT_TEXT_TRACK_DATA_CHANGED("onTextTrackDataChanged"),
    EVENT_VIDEO_TRACKS("onVideoTracks"),
    EVENT_ON_RECEIVE_AD_EVENT("onReceiveAdEvent");

    companion object {
        fun toMap() =
            mutableMapOf<String, Any>().apply {
                EventTypes.values().toList().forEach { eventType ->
                    put("top${eventType.eventName.removePrefix("on")}", hashMapOf("registrationName" to eventType.eventName))
                }
            }
    }
}

class VideoEventEmitter {
    lateinit var onVideoLoadStart: () -> Unit
    lateinit var onVideoLoad: (
        duration: Long,
        currentPosition: Long,
        videoWidth: Int,
        videoHeight: Int,
        audioTracks: List<TrackInfo>,
        selectedAudioTrack: TrackInfo?,
        textTracks: List<TrackInfo>,
        selectedTextTrack: TrackInfo?,
        videoTracks: List<TrackInfo>,
        selectedVideoTrack: TrackInfo?,
        manifest: Any?,
        trackId: String?
    ) -> Unit
    lateinit var onVideoError: (errorString: String, exception: Exception, errorCode: String) -> Unit
    lateinit var onVideoProgress: (currentPosition: Long, bufferedDuration: Long, seekableDuration: Long, currentPlaybackTime: Double) -> Unit
    lateinit var onVideoBandwidthUpdate: (bitRateEstimate: Long, height: Int, width: Int, trackId: String?) -> Unit
    lateinit var onVideoPlaybackStateChanged: (isPlaying: Boolean, isSeeking: Boolean) -> Unit
    lateinit var onVideoSeek: (currentPosition: Long, seekTime: Long) -> Unit
    lateinit var onVideoEnd: () -> Unit
    lateinit var onVideoFullscreenPlayerWillPresent: () -> Unit
    lateinit var onVideoFullscreenPlayerDidPresent: () -> Unit
    lateinit var onVideoFullscreenPlayerWillDismiss: () -> Unit
    lateinit var onVideoFullscreenPlayerDidDismiss: () -> Unit
    lateinit var onReadyForDisplay: () -> Unit
    lateinit var onVideoBuffer: (isBuffering: Boolean) -> Unit
    lateinit var onControlsVisibilityChange: (isVisible: Boolean) -> Unit
    lateinit var onVideoIdle: () -> Unit
    lateinit var onTimedMetadata: (metadataArrayList: ArrayList<TimedMetadata>) -> Unit
    lateinit var onVideoAudioBecomingNoisy: () -> Unit
    lateinit var onAudioFocusChanged: (hasFocus: Boolean) -> Unit
    lateinit var onPlaybackRateChange: (rate: Float) -> Unit
    lateinit var onVolumeChange: (volume: Float) -> Unit
    lateinit var onAudioTracks: (audioTracks: List<TrackInfo>, selectedTrack: TrackInfo?, manifest: Any?) -> Unit
    lateinit var onTextTracks: (textTracks: List<TrackInfo>, selectedTrack: TrackInfo?) -> Unit
    lateinit var onVideoTracks: (videoTracks: List<TrackInfo>, selectedTrack: TrackInfo?, manifest: Any?) -> Unit
    lateinit var onTextTrackDataChanged: (textTrackData: String) -> Unit
    lateinit var onReceiveAdEvent: (adEvent: String, adData: Map<String?, String?>?) -> Unit

    fun addEventEmitters(reactContext: ThemedReactContext, view: ReactExoplayerView) {
        val dispatcher = UIManagerHelper.getEventDispatcherForReactTag(reactContext, view.id)
        val surfaceId = UIManagerHelper.getSurfaceId(reactContext)

        if (dispatcher != null) {
            val event = EventBuilder(surfaceId, view.id, dispatcher)

            onVideoLoadStart = {
                event.dispatch(EventTypes.EVENT_LOAD_START)
            }
            onVideoLoad = {
                    duration,
                    currentPosition,
                    videoWidth,
                    videoHeight,
                    audioTracks,
                    selectedAudioTrack,
                    textTracks,
                    selectedTextTrack,
                    videoTracks,
                    selectedVideoTrack,
                    manifest,
                    trackId
                ->
                event.dispatch(EventTypes.EVENT_LOAD) {
                    putOnLoadData(
                        duration,
                        currentPosition,
                        videoWidth,
                        videoHeight,
                        audioTracks,
                        selectedAudioTrack,
                        textTracks,
                        selectedTextTrack,
                        videoTracks,
                        selectedVideoTrack,
                        manifest,
                        trackId
                    )
                }
            }
            onVideoError = { errorString, exception, errorCode ->
                event.dispatch(EventTypes.EVENT_ERROR) {
                    putMap(
                        "error",
                        Arguments.createMap().apply {
                            // Prepare stack trace
                            val sw = StringWriter()
                            val pw = PrintWriter(sw)
                            exception.printStackTrace(pw)
                            val stackTrace = sw.toString()

                            putString("errorString", errorString)
                            putString("errorException", exception.toString())
                            putString("errorCode", errorCode)
                            putString("errorStackTrace", stackTrace)
                        }
                    )
                }
            }
            onVideoProgress = { currentPosition, bufferedDuration, seekableDuration, currentPlaybackTime ->
                event.dispatch(EventTypes.EVENT_PROGRESS) {
                    putDouble("currentTime", currentPosition / 1000.0)
                    putDouble("playableDuration", bufferedDuration / 1000.0)
                    putDouble("seekableDuration", seekableDuration / 1000.0)
                    putDouble("currentPlaybackTime", currentPlaybackTime)
                }
            }
            onVideoBandwidthUpdate = { bitRateEstimate, height, width, trackId ->
                event.dispatch(EventTypes.EVENT_BANDWIDTH) {
                    putDouble("bitrate", bitRateEstimate.toDouble())
                    if (width > 0) {
                        putInt("width", width)
                    }
                    if (height > 0) {
                        putInt("height", height)
                    }
                    trackId?.let { putString("trackId", it) }
                }
            }
            onVideoPlaybackStateChanged = { isPlaying, isSeeking ->
                event.dispatch(EventTypes.EVENT_PLAYBACK_STATE_CHANGED) {
                    putBoolean("isPlaying", isPlaying)
                    putBoolean("isSeeking", isSeeking)
                }
            }
            onVideoSeek = { currentPosition, seekTime ->
                event.dispatch(EventTypes.EVENT_SEEK) {
                    putDouble("currentTime", currentPosition / 1000.0)
                    putDouble("seekTime", seekTime / 1000.0)
                }
            }
            onVideoEnd = {
                event.dispatch(EventTypes.EVENT_END)
            }
            onVideoFullscreenPlayerWillPresent = {
                event.dispatch(EventTypes.EVENT_FULLSCREEN_WILL_PRESENT)
            }
            onVideoFullscreenPlayerDidPresent = {
                event.dispatch(EventTypes.EVENT_FULLSCREEN_DID_PRESENT)
            }
            onVideoFullscreenPlayerWillDismiss = {
                event.dispatch(EventTypes.EVENT_FULLSCREEN_WILL_DISMISS)
            }
            onVideoFullscreenPlayerDidDismiss = {
                event.dispatch(EventTypes.EVENT_FULLSCREEN_DID_DISMISS)
            }
            onReadyForDisplay = {
                event.dispatch(EventTypes.EVENT_READY)
            }
            onVideoBuffer = { isBuffering ->
                event.dispatch(EventTypes.EVENT_BUFFER) {
                    putBoolean("isBuffering", isBuffering)
                }
            }
            onControlsVisibilityChange = { isVisible ->
                event.dispatch(EventTypes.EVENT_CONTROLS_VISIBILITY_CHANGE) {
                    putBoolean("isVisible", isVisible)
                }
            }
            onVideoIdle = {
                event.dispatch(EventTypes.EVENT_IDLE)
            }
            onTimedMetadata = fn@{ metadataArrayList ->
                if (metadataArrayList.size == 0) {
                    return@fn
                }
                event.dispatch(EventTypes.EVENT_TIMED_METADATA) {
                    putArray(
                        "metadata",
                        Arguments.createArray().apply {
                            metadataArrayList.forEachIndexed { _, metadata ->
                                pushMap(
                                    Arguments.createMap().apply {
                                        putString("identifier", metadata.identifier)
                                        putString("value", metadata.value)
                                    }
                                )
                            }
                        }
                    )
                }
            }
            onVideoAudioBecomingNoisy = {
                event.dispatch(EventTypes.EVENT_AUDIO_BECOMING_NOISY)
            }
            onAudioFocusChanged = { hasFocus ->
                event.dispatch(EventTypes.EVENT_AUDIO_FOCUS_CHANGE) {
                    putBoolean("hasAudioFocus", hasFocus)
                }
            }
            onPlaybackRateChange = { rate ->
                event.dispatch(EventTypes.EVENT_PLAYBACK_RATE_CHANGE) {
                    putDouble("playbackRate", rate.toDouble())
                }
            }
            onVolumeChange = { volume ->
                event.dispatch(EventTypes.EVENT_VOLUME_CHANGE) {
                    putDouble("volume", volume.toDouble())
                }
            }
            onAudioTracks = { audioTracks, selectedTrack, manifest ->
                event.dispatch(EventTypes.EVENT_AUDIO_TRACKS) {
                    putAudioTracksData(audioTracks, selectedTrack, manifest)
                }
            }
            onTextTracks = { textTracks, selectedTrack ->
                event.dispatch(EventTypes.EVENT_TEXT_TRACKS) {
                    putTextTracksData(textTracks, selectedTrack)
                }
            }
            onVideoTracks = { videoTracks, selectedTrack, manifest ->
                event.dispatch(EventTypes.EVENT_VIDEO_TRACKS) {
                    putVideoTracksData(videoTracks, selectedTrack, manifest)
                }
            }
            onTextTrackDataChanged = { textTrackData ->
                event.dispatch(EventTypes.EVENT_TEXT_TRACK_DATA_CHANGED) {
                    putString("subtitleTracks", textTrackData)
                }
            }
            onReceiveAdEvent = { adEvent, adData ->
                event.dispatch(EventTypes.EVENT_ON_RECEIVE_AD_EVENT) {
                    putString("event", adEvent)
                    putMap(
                        "data",
                        Arguments.createMap().apply {
                            adData?.let { data ->
                                for ((key, value) in data) {
                                    putString(key!!, value)
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    private fun WritableMap.putOnLoadData(
        duration: Long,
        currentPosition: Long,
        videoWidth: Int,
        videoHeight: Int,
        audioTracks: List<TrackInfo>,
        selectedAudioTrack: TrackInfo?,
        textTracks: List<TrackInfo>,
        selectedTextTrack: TrackInfo?,
        videoTracks: List<TrackInfo>,
        selectedVideoTrack: TrackInfo?,
        manifest: Any?,
        trackId: String?
    ) = apply {
        putDouble("duration", duration / 1000.0)
        putDouble("currentTime", currentPosition / 1000.0)

        val naturalSize: WritableMap = aspectRatioToNaturalSize(videoWidth, videoHeight)
        putMap("naturalSize", naturalSize)
        trackId?.let { putString("trackId", it) }
        putAudioTracksData(audioTracks, selectedAudioTrack, manifest)
        putTextTracksData(textTracks, selectedTextTrack)
        putVideoTracksData(videoTracks, selectedVideoTrack, manifest)

        // TODO: Actually check if you can.
        putBoolean("canPlayFastForward", true)
        putBoolean("canPlaySlowForward", true)
        putBoolean("canPlaySlowReverse", true)
        putBoolean("canPlayReverse", true)
        putBoolean("canPlayFastForward", true)
        putBoolean("canStepBackward", true)
        putBoolean("canStepForward", true)
    }

    private fun WritableMap.putAudioTracksData(audioTracks: List<TrackInfo>, selectedTrack: TrackInfo?, manifest: Any?): WritableMap =
        apply {
            audioTracks
                .map { track ->
                    val selected = selectedTrack != null && selectedTrack.complexIndex == track.complexIndex
                    createAudioTrackInfo(track, selected, manifest)
                }
                .let { putArray("audioTracks", Arguments.makeNativeArray(it)) }
        }

    private fun WritableMap.putVideoTracksData(videoTracks: List<TrackInfo>, selectedTrack: TrackInfo?, manifest: Any?): WritableMap =
        apply {
            videoTracks
                .map { track ->
                    val selected = selectedTrack != null && selectedTrack.complexIndex == track.complexIndex
                    createVideoTrackInfo(track, selected, manifest)
                }
                .let { putArray("videoTracks", Arguments.makeNativeArray(it)) }
        }

    private fun WritableMap.putTextTracksData(textTracks: List<TrackInfo>, selectedTrack: TrackInfo?): WritableMap =
        apply {
            textTracks
                .map { track ->
                    val selected = selectedTrack != null && selectedTrack.complexIndex == track.complexIndex
                    createTextTrackInfo(track, selected)
                }
                .let { putArray("textTracks", Arguments.makeNativeArray(it)) }
        }

    private class EventBuilder(private val surfaceId: Int, private val viewId: Int, private val dispatcher: EventDispatcher) {
        fun dispatch(event: EventTypes, paramsSetter: (WritableMap.() -> Unit)? = null) =
            dispatcher.dispatchEvent(object : Event<Event<*>>(surfaceId, viewId) {
                override fun getEventName() = "top${event.eventName.removePrefix("on")}"
                override fun getEventData() = Arguments.createMap().apply(paramsSetter ?: {})
            })
    }

    private fun aspectRatioToNaturalSize(videoWidth: Int, videoHeight: Int): WritableMap =
        Arguments.createMap().apply {
            if (videoWidth > 0) {
                putInt("width", videoWidth)
            }
            if (videoHeight > 0) {
                putInt("height", videoHeight)
            }

            val orientation = when {
                videoWidth > videoHeight -> "landscape"
                videoWidth < videoHeight -> "portrait"
                else -> "square"
            }

            putString("orientation", orientation)
        }

    private fun createAudioTrackInfo(track: TrackInfo?, selected: Boolean, manifest: Any?): WritableMap? {
        if (track == null) return null
        val complexIndex = track.complexIndex
        val format = track.format
        val audioTrack = Arguments.createMap()
        audioTrack.putDouble("index", complexIndex.toDouble())
        audioTrack.putString("trackId", if (format.id != null) format.id else complexIndex.toString())
        audioTrack.putString("title", getLanguageDisplayName(format.language))
        audioTrack.putString("type", format.sampleMimeType)
        audioTrack.putString("language", format.language)
        audioTrack.putInt("bitrate", if (format.bitrate == Format.NO_VALUE) 0 else format.bitrate)
        audioTrack.putString("codecs", format.codecs)
        audioTrack.putString("channels", (if (format.channelCount != Format.NO_VALUE) format.channelCount else 0).toString())
        audioTrack.putBoolean("selected", selected)
        if (manifest != null) {
            val representation = getRepresentationOf(manifest, track)
            audioTrack.putString("file", getRepresentationFileName(representation))
            audioTrack.putString("supplementalProperties", getRepresentationSupplementalProperties(representation))
        }
        return audioTrack
    }

    private fun createVideoTrackInfo(track: TrackInfo?, selected: Boolean, manifest: Any?): WritableMap? {
        if (track == null) return null
        val complexIndex = track.complexIndex
        val format = track.format
        val videoTrack = Arguments.createMap()
        videoTrack.putDouble("index", complexIndex.toDouble())
        videoTrack.putString("trackId", if (format.id != null) format.id else complexIndex.toString())
        videoTrack.putInt("width", if (format.width == Format.NO_VALUE) 0 else format.width)
        videoTrack.putInt("height", if (format.height == Format.NO_VALUE) 0 else format.height)
        videoTrack.putInt("bitrate", if (format.bitrate == Format.NO_VALUE) 0 else format.bitrate)
        videoTrack.putString("codecs", format.codecs)
        videoTrack.putBoolean("selected", selected)
        videoTrack.putInt("rotation", format.rotationDegrees)
        if (manifest != null) {
            val representation = getRepresentationOf(manifest, track)
            videoTrack.putString("file", getRepresentationFileName(representation))
            videoTrack.putString("supplementalProperties", getRepresentationSupplementalProperties(representation))
        }
        return videoTrack
    }

    private fun createTextTrackInfo(track: TrackInfo?, selected: Boolean): WritableMap? {
        if (track == null) return null
        val complexIndex = track.complexIndex
        val format = track.format
        val textTrack = Arguments.createMap()
        textTrack.putDouble("index", complexIndex.toDouble())
        textTrack.putString("trackId", if (format.id != null) format.id else complexIndex.toString())
        textTrack.putString("title", getLanguageDisplayName(format.language))
        textTrack.putString("type", format.sampleMimeType)
        textTrack.putString("language", format.language)
        textTrack.putBoolean("selected", selected)
        return textTrack
    }
}
