package com.lx.linwanandroid_mvvm.ui.main.knowledge

import com.lx.linwanandroid_mvvm.model.api.apiData
import com.lx.linwanandroid_mvvm.net.RetrofitHelper
import kotlinx.coroutines.flow.flow

/**
 * @title：KnowledgeTreeRepository
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/07/06
 */
class KnowledgeTreeRepository {
    suspend fun requestKnowledgeList() = RetrofitHelper.service.getKnowledgeTree().apiData()
}