package com.lx.linwanandroid_mvvm.model.exception

import java.lang.RuntimeException

/**
 * @title：ApiException
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/03/31
 */
class ApiException(var code: Int, override var message: String) : RuntimeException()
