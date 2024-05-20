package com.brentvatne.exoplayer

import androidx.media3.common.util.Util
import java.util.Locale

object LocaleUtils {
    @JvmStatic
    fun getLanguageDisplayName(lang: String?): String? =
        lang
            ?.let(Util::normalizeLanguageCode)
            ?.let(Locale::forLanguageTag)
            ?.let { locale -> locale.getDisplayName(locale) }
            ?.replaceFirstChar { it.uppercase() }
}
