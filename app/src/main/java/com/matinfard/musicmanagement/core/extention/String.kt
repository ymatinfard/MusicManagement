package com.matinfard.musicmanagement.core.extention

fun String.Companion.empty() = ""

fun String.shortenTo(maxLen: Int): String =
    if (this.length > maxLen) this.substring(0, maxLen-3).plus("…") else this