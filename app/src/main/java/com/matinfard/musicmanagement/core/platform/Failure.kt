package com.matinfard.musicmanagement.core.platform

sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    object UnknownError : Failure()
}
