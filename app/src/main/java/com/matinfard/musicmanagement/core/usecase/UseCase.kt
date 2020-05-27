package com.matinfard.musicmanagement.core.usecase

import com.matinfard.musicmanagement.core.platform.Result
import com.matinfard.musicmanagement.core.platform.Failure
import kotlinx.coroutines.*

abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Result<Failure, Type>

    suspend operator fun invoke(params: Params, onResult: (Result<Failure, Type>) -> Unit = {}) {
        coroutineScope {
            val job = async(Dispatchers.IO) { run(params) }
            withContext(Dispatchers.Main) { onResult(job.await()) }
        }
    }

    class None
}
