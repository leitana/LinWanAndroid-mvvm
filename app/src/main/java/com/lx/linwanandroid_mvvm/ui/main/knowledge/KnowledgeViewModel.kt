package com.lx.linwanandroid_mvvm.ui.main.knowledge

import androidx.lifecycle.MutableLiveData
import com.lx.linwanandroid_mvvm.base.BaseViewModel
import com.lx.linwanandroid_mvvm.model.bean.KnowledgeSysArticle

/**
 * @title：KnowledgeViewModel
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/06/28
 */
class KnowledgeViewModel: BaseViewModel() {

    val datas = MutableLiveData<MutableList<KnowledgeSysArticle.DatasBean>>()
    var page: Int = 0
    val refreshData: () -> Unit = { refreshList() }

    fun requestKnowledgeTree(){

    }

    fun refreshList(){

    }
}