package com.brentvatne.exoplayer

import android.net.Uri
import androidx.media3.exoplayer.dash.manifest.DashManifest
import androidx.media3.exoplayer.dash.manifest.Representation

object ManifestUtils {
    @JvmStatic
    fun getRepresentationOf(manifest: Any?, track: TrackInfo?): Representation? {
        if (manifest !is DashManifest || track == null || track.format.id == null) return null
        return (0 until manifest.periodCount)
            .asSequence()
            .map { manifest.getPeriod(it) }
            .map { it.adaptationSets }
            .flatMap { adaptationSets ->
                adaptationSets
                    .filter { track.type == it.type }
                    .flatMap { it.representations }
            }
            .singleOrNull { track.format.id == it.format.id }
    }

    @JvmStatic
    fun getRepresentationFileName(representation: Representation?): String? =
        representation
            ?.baseUrls
            ?.firstOrNull()
            ?.runCatching { Uri.parse(url) }
            ?.getOrNull()
            ?.lastPathSegment

    @JvmStatic
    fun getRepresentationSupplementalProperties(representation: Representation?): String? =
        representation
            ?.supplementalProperties
            ?.joinToString("; ") {
                "${it.schemeIdUri}=${it.value}"
            }
}
