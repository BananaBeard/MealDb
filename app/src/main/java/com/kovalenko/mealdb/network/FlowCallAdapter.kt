package com.kovalenko.mealdb.network

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

@ExperimentalCoroutinesApi
class FlowCallAdapter<R>(private val responseType: Type) : CallAdapter<R, Flow<ApiResponse<R>>> {
    override fun adapt(call: Call<R>): Flow<ApiResponse<R>> {
        return callbackFlow {
            call.enqueue(object : Callback<R> {
                override fun onFailure(call: Call<R>, throwable: Throwable) {
                    sendBlocking(ApiResponse.create(throwable))
                }

                override fun onResponse(call: Call<R>, response: Response<R>) {
                    sendBlocking(ApiResponse.create(response))
                }
            })
            awaitClose { call.cancel() }
        }
    }

    override fun responseType() = responseType
}
