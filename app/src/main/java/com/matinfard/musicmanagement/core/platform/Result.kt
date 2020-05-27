package com.matinfard.musicmanagement.core.platform

sealed class Result<out L, out R> {
    data class Failure<out L>(val a: L) : Result<L, Nothing>()

    data class Success<out R>(val b: R) : Result<Nothing, R>()

    val isSuccessful get() = this is Success<R>

    val isFailure get() = this is Failure<L>

    fun <L> failure(a: L) =
        Failure(a)

    fun <R> success(b: R) =
        Success(b)

    fun fold(fnL: (L) -> Any, fnR: (R) -> Any): Any =
        when (this) {
            is Failure -> fnL(a)
            is Success -> fnR(b)
        }
}
