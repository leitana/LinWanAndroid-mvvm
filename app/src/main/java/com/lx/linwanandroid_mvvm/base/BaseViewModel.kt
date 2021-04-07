package com.lx.linwanandroid_mvvm.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lx.linwanandroid_mvvm.constant.Constant
import com.lx.linwanandroid_mvvm.ext.showToast
import com.lx.linwanandroid_mvvm.model.exception.ApiException
import com.lx.linwanandroid_mvvm.model.exception.ApiStatus
import com.lx.linwanandroid_mvvm.model.exception.ExceptionHandle
import com.lx.linwanandroid_mvvm.net.RetrofitHelper
import com.lx.linwanandroid_mvvm.utils.Preference
import com.lx.linwanandroid_mvvm.utils.context
import kotlinx.coroutines.*

/**
 * @title：BaseViewModel
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/03/26
 */

typealias Block<T> = suspend (CoroutineScope) -> T
typealias Error = suspend (Exception) -> Unit
typealias Cancel = suspend (Exception) -> Unit

abstract class BaseViewModel: ViewModel() {

    val mExpectation: MutableLiveData<Throwable> = MutableLiveData()

    // UI
    fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    // IO
    suspend fun<T> launchOnIO(block: suspend CoroutineScope.() -> T) {
        withContext(Dispatchers.IO){
            block
        }
    }

    protected fun launch(
        block: Block<Unit>,
        error: Error? = null,
        cancel: Cancel? = null,
        showErrorToast: Boolean = true
    ): Job {
        return viewModelScope.launch {
            try {
                block.invoke(this)
            } catch (e: Exception) {
                when(e) {
                    is CancellationException -> {
                        cancel?.invoke(e)
                    }
                    else -> {
                        onError(e, showErrorToast)
                        error?.invoke(e)
                    }
                }
            }
        }
    }

    private fun onError(e: Exception, showErrorToast: Boolean){
        when(e) {
            is ApiException -> {
                when(e.code){
                    ApiStatus.TOKEN_INVALID -> {
                        Preference.clearPreference(Constant.USERNAME_KEY)
                        Preference.clearPreference(Constant.PASSWORD_KEY)
                        RetrofitHelper.clearCookie()
                    }
                    // 其他错误
                    else -> if (showErrorToast) context().showToast(ExceptionHandle.handleException(e))
                }
            }

            else ->
                if (showErrorToast) context().showToast(ExceptionHandle.handleException(e))
        }
    }
}