package com.lx.linwanandroid_mvvm.ui.login

import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.base.BaseVMActivity
import com.lx.linwanandroid_mvvm.databinding.ActivityLoginBinding
import com.lx.linwanandroid_mvvm.ext.showToast
import com.lx.linwanandroid_mvvm.utils.DialogUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @title：LoginActivity
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/04/01
 */
class LoginActivity : BaseVMActivity(){

    private val loginViewModel by viewModel<LoginViewModel>()
    private val binding by binding<ActivityLoginBinding>(R.layout.activity_login)

    private val mDialog by lazy {
        DialogUtil.getProgressDialog(this, getString(R.string.login_ing))
    }

    override fun initView() {
        binding.run {
            viewModel = loginViewModel
//            title = Title("登录")
        }
    }

    override fun initData() {

    }

    override fun startObserve() {
        loginViewModel.run {
            userName.observe(this@LoginActivity, {
                inputChanged()
            })
            password.observe(this@LoginActivity, {
                inputChanged()
            })
            uiState.observe(this@LoginActivity, {
                when(it) {
                    LoginViewModel.UiState.Logining -> showDialog()
                    LoginViewModel.UiState.Success -> {
                        dismissDialog()
                        showToast("登陆成功...")
                    }

                    LoginViewModel.UiState.Error -> {
                        dismissDialog()
                        showToast("登录失败")
                    }
                }
            })
        }
    }

    private fun showDialog() {
        mDialog.show()
    }

    private fun dismissDialog() {
        if (mDialog.isShowing) {
            mDialog.dismiss()
        }
    }
}