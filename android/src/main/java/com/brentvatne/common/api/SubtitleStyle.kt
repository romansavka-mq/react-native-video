package com.brentvatne.common.api

import androidx.core.graphics.toColorInt
import com.brentvatne.common.toolbox.ReactBridgeUtils.safeGetFloat
import com.brentvatne.common.toolbox.ReactBridgeUtils.safeGetInt
import com.brentvatne.common.toolbox.ReactBridgeUtils.safeGetString
import com.facebook.react.bridge.ReadableMap

/**
 * Helper file to parse SubtitleStyle prop and build a dedicated class
 */
class SubtitleStyle private constructor() {
    var foregroundColor: Int? = null
        private set
    var backgroundColor: Int? = null
        private set
    var windowColor: Int? = null
        private set
    var fontSize = -1
        private set
    var paddingLeft = 0
        private set
    var paddingRight = 0
        private set
    var paddingTop = 0
        private set
    var paddingBottom = 0
        private set
    var opacity = 1f
        private set

    companion object {
        private const val PROP_FOREGROUND_COLOR = "foregroundColor"
        private const val PROP_BACKGROUND_COLOR = "backgroundColor"
        private const val PROP_WINDOW_COLOR = "windowColor"
        private const val PROP_FONT_SIZE_TRACK = "fontSize"
        private const val PROP_PADDING_BOTTOM = "paddingBottom"
        private const val PROP_PADDING_TOP = "paddingTop"
        private const val PROP_PADDING_LEFT = "paddingLeft"
        private const val PROP_PADDING_RIGHT = "paddingRight"
        private const val PROP_OPACITY = "opacity"

        @JvmStatic
        fun parse(src: ReadableMap?): SubtitleStyle =
            SubtitleStyle().apply {
                foregroundColor = runCatching {
                    safeGetString(src, PROP_FOREGROUND_COLOR)?.toColorInt()
                }.getOrNull()
                backgroundColor = runCatching {
                    safeGetString(src, PROP_BACKGROUND_COLOR)?.toColorInt()
                }.getOrNull()
                windowColor = runCatching {
                    safeGetString(src, PROP_WINDOW_COLOR)?.toColorInt()
                }.getOrNull()
                fontSize = safeGetInt(src, PROP_FONT_SIZE_TRACK, -1)
                paddingBottom = safeGetInt(src, PROP_PADDING_BOTTOM, 0)
                paddingTop = safeGetInt(src, PROP_PADDING_TOP, 0)
                paddingLeft = safeGetInt(src, PROP_PADDING_LEFT, 0)
                paddingRight = safeGetInt(src, PROP_PADDING_RIGHT, 0)
                opacity = safeGetFloat(src, PROP_OPACITY, 1f)
            }
    }
}
