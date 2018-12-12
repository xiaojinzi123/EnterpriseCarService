package com.ehi.enterprise.user

import android.app.Application

import com.ehi.component.anno.EHiModuleAppAnno
import com.ehi.component.application.IComponentApplication


/**
 * 组件化中 User 模块的 Application
 *
 * time   : 2018/08/09
 *
 * @author : xiaojinzi 30212
 */
@EHiModuleAppAnno
class UserModuleApplication : IComponentApplication {

    override fun onCreate(context: Application) {}

    override fun onDestory() {}

}
