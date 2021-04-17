package com.lx.linwanandroid_mvvm.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lx.linwanandroid_mvvm.base.BaseViewModel
import com.lx.linwanandroid_mvvm.model.api.apiData
import com.lx.linwanandroid_mvvm.model.bean.HomeArticle
import com.lx.linwanandroid_mvvm.ui.login.LoginViewModel
import kotlinx.coroutines.flow.collectLatest

/**
 * @title：HomeViewModel
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/04/13
 */
class HomeViewModel : BaseViewModel(){
    private val homeRepository by lazy { HomeRepository() }

    enum class StateView{
        SUCCESS, ERROR, EMPTY, NETWORK_ERROR, LODING
    }

    private val _uiState = MutableLiveData<StateView>()
    val uiState: LiveData<StateView> = _uiState

    private val _homeList = MutableLiveData<MutableList<HomeArticle.DatasBean>>()
    val homeList: LiveData<MutableList<HomeArticle.DatasBean>> = _homeList

    fun getArticles(page: Int) {
        _uiState.value = StateView.LODING
        launch(
            block = {
                homeRepository.getHomeList(page).collectLatest {
                    _homeList.value = it.datas
                    _uiState.value =StateView.SUCCESS
                }
            },
            error = {
                _uiState.value = StateView.ERROR
            }
        )
    }
}