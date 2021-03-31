package com.lx.linwanandroid_mvvm.model.api

import com.lx.linwanandroid_mvvm.model.exception.ApiException

/**
 * @title：ApiResponse
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/03/30
 */
data class ApiResponse<T>(val errorCode: Int, val errorMsg: String, val data: T)

fun<T> ApiResponse<T>.apiData() : T {
    if (errorCode == 0 && data != null) {
        return data
    } else {
        throw ApiException(errorCode, errorMsg)
    }
}