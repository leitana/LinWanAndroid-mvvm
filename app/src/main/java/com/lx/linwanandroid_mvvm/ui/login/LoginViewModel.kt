package com.lx.linwanandroid_mvvm.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lx.linwanandroid_mvvm.base.BaseViewModel
import com.lx.linwanandroid_mvvm.constant.Constant
import com.lx.linwanandroid_mvvm.model.bean.Login
import com.lx.linwanandroid_mvvm.utils.Preference

/**
 * @title：LoginViewModel
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/04/04
 */
class LoginViewModel: BaseViewModel() {

    private val loginRepository by lazy { LoginRepository() }

    enum class UiState{
        Success, Error, Logining
    }

    val userName: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")

    private var isLogin by Preference(Constant.LOGIN_KEY, false)
    private var user: String by Preference(Constant.USERNAME_KEY, "")
    private var pwd: String by Preference(Constant.PASSWORD_KEY, "")

    private var isEnable: MutableLiveData<Boolean> = MutableLiveData(false)


    private val _uiState = MutableLiveData<UiState>()
    val uiState : LiveData<UiState> = _uiState

    private fun isInputValid(userName: String, passWord: String) = userName.isNotBlank() && passWord.isNotBlank()

    fun login() {
        _uiState.value = UiState.Logining
        launch(
            block = {
                val userInfo = loginRepository.login(userName.value ?: "", password.value ?: "")
                user = userInfo.username!!
                pwd = userInfo.password!!
                isLogin = true
                _uiState.value = UiState.Success
            },
            error = {
                val str = it.message
                _uiState.value = UiState.Error
            }
        )
    }

    fun inputChanged(){
        isEnable.value = isInputValid(userName.value ?: "", password.value ?: "")
    }

}