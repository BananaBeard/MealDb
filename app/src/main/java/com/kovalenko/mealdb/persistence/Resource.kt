package com.kovalenko.mealdb.persistence



import com.kovalenko.mealdb.util.Status
import com.kovalenko.mealdb.util.Status.SUCCESS
import com.kovalenko.mealdb.util.Status.ERROR
import com.kovalenko.mealdb.util.Status.LOADING

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}
