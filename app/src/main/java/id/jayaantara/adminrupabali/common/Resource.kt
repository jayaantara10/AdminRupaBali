package id.jayaantara.adminrupabali.common

import retrofit2.HttpException


sealed class Resource<T>(val data: T? = null, val errorCode: Int? = 0) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(errorCode: Int, data: T? = null): Resource<T>(data, errorCode)
    class Loading<T>(data: T? = null): Resource<T>(data)
}
