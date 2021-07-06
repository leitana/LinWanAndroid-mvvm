package com.lx.linwanandroid_mvvm.ui.main.knowledge

import androidx.lifecycle.MutableLiveData
import com.lx.linwanandroid_mvvm.base.BaseViewModel
import com.lx.linwanandroid_mvvm.model.bean.KnowledgeSystem

/**
 * @title：KnowledgeTreeViewModel
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/07/06
 */
class KnowledgeTreeViewModel: BaseViewModel() {
    private val knowledgeTreeRepository: KnowledgeTreeRepository by lazy { KnowledgeTreeRepository() }
    var treeDatas = MutableLiveData<MutableList<KnowledgeSystem>>()
    val refreshStatus = MutableLiveData<Boolean>()
    val refreshData: () -> Unit = { getDataList() }

    fun getDataList(){
        refreshStatus.value = true
        launch(
            block = {
                treeDatas.value = knowledgeTreeRepository.requestKnowledgeList()
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
            }
        )
    }
}