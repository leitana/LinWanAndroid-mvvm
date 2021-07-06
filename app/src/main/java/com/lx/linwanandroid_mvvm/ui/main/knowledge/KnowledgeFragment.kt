package com.lx.linwanandroid_mvvm.ui.main.knowledge

import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.base.BaseVMFragment
import com.lx.linwanandroid_mvvm.databinding.FragmentKnowledgeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @title：KnowledgeFragment
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/06/28
 */
class KnowledgeFragment: BaseVMFragment<FragmentKnowledgeBinding>(R.layout.fragment_knowledge) {


    private val knowledgeViewModel by viewModel<KnowledgeViewModel>()
    private val knowledgeAdapter: KnowledgeAdapter by lazy { KnowledgeAdapter() }

    override fun initView() {

    }

    override fun initData() {
    }

    override fun startObserve() {
    }
}