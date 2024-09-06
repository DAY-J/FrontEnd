package com.dayj.dayj.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response


fun <T> (suspend () -> Response<T>).toResultFlow(): Flow<Result<T>> {
    return flow {
        emit(this@toResultFlow().toResult())
    }.catch { throwable ->
        //Exception Manager
        emit(Result.Fail.Exception(0, throwable.message ?: ""))
    }
}


fun <T, P> (suspend (P) -> Response<T>).toResultFlowWithParam(): (P) -> Flow<Result<T>> {
    return { param: P ->
        flow {
            emit(this@toResultFlowWithParam(param).toResult())
        }.catch { throwable ->
            emit(Result.Fail.Exception(0, throwable.message ?: ""))
            //Exception Manager
        }
    }
}

fun <T, P1, P2> (suspend (P1, P2) -> Response<T>).toResultFlowWithParam(): (P1, P2) -> Flow<Result<T>> {
    return { p1, p2 ->
        flow {
            emit(this@toResultFlowWithParam(p1, p2).toResult())
        }.catch { throwable ->
            emit(Result.Fail.Exception(0, throwable.message ?: ""))
            //Exception Manager
        }
    }
}

fun <T, P1, P2, P3> (suspend (P1, P2, P3) -> Response<T>).toResultFlowWithParam(): (P1, P2, P3) -> Flow<Result<T>> {
    return { p1, p2, p3 ->
        flow {
            emit(this@toResultFlowWithParam(p1, p2, p3).toResult())
        }.catch { throwable ->
            //Exception Manager
            emit(Result.Fail.Exception(0, throwable.message ?: ""))
        }
    }
}

fun <T, P1, P2, P3, P4> (suspend (P1, P2, P3, P4) -> Response<T>).toResultFlowWithParam(): (P1, P2, P3, P4) -> Flow<Result<T>> {
    return { p1, p2, p3, p4 ->
        flow {
            emit(this@toResultFlowWithParam(p1, p2, p3, p4).toResult())
        }.catch { throwable ->
            //Exception Manager
            emit(Result.Fail.Exception(0, throwable.message ?: ""))
        }
    }
}