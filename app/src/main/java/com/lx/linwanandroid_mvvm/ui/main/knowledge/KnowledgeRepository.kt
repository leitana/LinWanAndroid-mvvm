package com.lx.linwanandroid_mvvm.ui.main.knowledge

import com.lx.linwanandroid_mvvm.model.api.apiData
import com.lx.linwanandroid_mvvm.net.RetrofitHelper
import kotlinx.coroutines.flow.flow

/**
 * @title：KnowledgeRepository
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/06/28
 */
class KnowledgeRepository {
    suspend fun requestKnowledgeList() = flow {
        val knowledgeList = RetrofitHelper.service.getKnowledgeTree()
        emit(knowledgeList)
    }

    suspend fun getKnowledge(page: Int, cid: Int) = RetrofitHelper.service.getKnowledge(page, cid).apiData()
}