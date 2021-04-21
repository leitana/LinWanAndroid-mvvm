package com.lx.linwanandroid_mvvm.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.lx.linwanandroid_mvvm.base.BaseViewModel
import com.lx.linwanandroid_mvvm.ext.concat
import com.lx.linwanandroid_mvvm.model.api.apiData
import com.lx.linwanandroid_mvvm.model.bean.Banner
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

    val homeList = MutableLiveData<MutableList<HomeArticle.DatasBean>>()
//    val homeList: LiveData<MutableList<HomeArticle.DatasBean>> = _homeList

    var page: Int = 0

    private val _refreshStatus = MutableLiveData<Boolean>()
    val refreshStatus = _refreshStatus

    private val _loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val loadMoreStatus = _loadMoreStatus

    var bannerList = MutableLiveData<List<Banner>>()

    val refreshData: () -> Unit = { refreshList() }


    fun refreshList() {
        homeList.value?.clear()
        _refreshStatus.value = true
        page = 0
        launch(
            block = {
                homeRepository.getHomeList(page).collectLatest {
                    page = it.curPage
                    homeList.value = it.datas
                    refreshStatus.value = false
                    _uiState.value = StateView.SUCCESS
                }
            },
            error = {
                _uiState.value = StateView.ERROR
                refreshStatus.value = false
            }
        )
    }

    fun loadMoreList() {
        _refreshStatus.value = false
        _loadMoreStatus.value = LoadMoreStatus.Loading
        launch(
            block = {
                homeRepository.getArticles(page).collectLatest {
                    val homeArticle = it.apiData()
                    page = homeArticle.curPage
//                    homeList.value?.addAll(homeArticle.datas!!)
                    homeList.value = homeList.value.concat(homeArticle.datas!!)
//                    homeList.postValue(homeList.value)
                    loadMoreStatus.value = if (homeArticle.offset >= homeArticle.total) {
                        LoadMoreStatus.End
                    } else {
                        LoadMoreStatus.Complete
                    }
                    _uiState.value = StateView.SUCCESS
                }
            },
            error = {
                _uiState.value = StateView.ERROR
                refreshStatus.value = false
            }
        )
    }

    fun loadBanner() {
        launch(
            block = {
                bannerList.value = homeRepository.getBanner()
            },
        )
    }
}