package com.brentvatne.exoplayer

import java.net.CookiePolicy

enum class CookiesPolicy(val value: CookiePolicy?) {
    ALL(CookiePolicy.ACCEPT_ALL),
    NONE(CookiePolicy.ACCEPT_NONE),
    ORIGINAL(CookiePolicy.ACCEPT_ORIGINAL_SERVER),
    SYSTEM_DEFAULT(null)
}
