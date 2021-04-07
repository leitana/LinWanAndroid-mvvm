package com.lx.linwanandroid_mvvm.model.api


/**
 * @title：FlowResponse
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/04/07
 */
sealed class FlowResponse<out T> {
    class Success<out T>(val data: T): FlowResponse<T>()
    class Error(val errorMsg: String): FlowResponse<Nothing>()
}

inline fun <reified T> FlowResponse<T>.onSuccess(success: (T) -> Unit) {
    if (this is FlowResponse.Success) {
        success(data)
    }
}

inline fun <reified T> FlowResponse<T>.onError(error: (String) -> Unit) {
    if (this is FlowResponse.Error) {
        error(errorMsg)
    }
}