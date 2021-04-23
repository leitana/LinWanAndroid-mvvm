package com.lx.linwanandroid_mvvm.ui.login

import android.content.Intent
import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.base.BaseVMActivity
import com.lx.linwanandroid_mvvm.databinding.ActivityLoginBinding
import com.lx.linwanandroid_mvvm.ext.showToast
import com.lx.linwanandroid_mvvm.model.bean.Title
import com.lx.linwanandroid_mvvm.ui.main.MainActivity
import com.lx.linwanandroid_mvvm.utils.DialogUtil
import com.lx.linwanandroid_mvvm.utils.StatusBarUtil
import com.lx.linwanandroid_mvvm.utils.context
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
            title = Title("登录", context().getColor(R.color.gray4))
        }
        StatusBarUtil.transparentStatusBar(this)
    }

    override fun initData() {
        loginViewModel.initAccount()
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
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }

                    LoginViewModel.UiState.Error -> {
                        dismissDialog()
//                        showToast("登录失败")
                        failure.value?.let { it1 -> showToast(it1) }
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