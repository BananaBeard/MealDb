package com.kovalenko.mealdb.persistence

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.kovalenko.mealdb.network.ApiErrorResponse
import com.kovalenko.mealdb.network.ApiResponse
import com.kovalenko.mealdb.network.ApiSuccessResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
abstract class NetworkBoundResource<ResultType, RequestType> {
    fun asFlow() = flow {
        val dbValue = loadFromDb().first()
        if (shouldFetch(dbValue)) {
            emit(Resource.loading(dbValue))
            fetchFromNetwork().collect { response ->
                when (response) {
                    is ApiSuccessResponse -> {
                        saveNetworkResult(processResponse(response))
                        emitAll(loadFromDb().map { Resource.success(it) })
                    }
                    is ApiErrorResponse -> {
                        emitAll(loadFromDb().map { Resource.error(response.errorMessage, it) })
                    }
                }
            }
        } else {
            emitAll(loadFromDb().map { Resource.success(it) })
        }
    }

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @MainThread
    protected abstract suspend fun saveNetworkResult(item: RequestType)

    @MainThread
    protected abstract suspend fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract suspend fun loadFromDb(): Flow<ResultType>

    @MainThread
    protected abstract suspend fun fetchFromNetwork(): Flow<ApiResponse<RequestType>>
}
