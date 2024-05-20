package com.brentvatne.exoplayer

import androidx.media3.common.Format

class TrackInfo(
    @JvmField
    val type: Int,
    @JvmField
    val renderIndex: Int,
    @JvmField
    val groupIndex: Int,
    @JvmField
    val trackIndex: Int,
    @JvmField
    val format: Format
) {
    @JvmField
    val complexIndex: Long = (renderIndex.toLong()) shl 32 or ((groupIndex.toLong()) shl 16) or (trackIndex.toLong() and 0xffffffffL)

    companion object {
        fun parseRenderIndex(complexIndex: Long): Int = (complexIndex shr 32).toShort().toInt()

        fun parseGroupIndex(complexIndex: Long): Int = (complexIndex shr 16).toShort().toInt()

        fun parseTrackIndex(complexIndex: Long): Int = complexIndex.toShort().toInt()
    }
}
