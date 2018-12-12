package com.ehi.enterprise;

import android.app.Application;
import android.support.annotation.NonNull;

import com.ehi.component.anno.EHiModuleAppAnno;
import com.ehi.component.application.IComponentApplication;


/**
 * 组件化中 App 工程模块的 Application
 *
 * time   : 2018/08/09
 *
 * @author : xiaojinzi 30212
 */
@EHiModuleAppAnno
public class AppModuleApplication implements IComponentApplication {

    @Override
    public void onCreate(@NonNull Application context) {
    }

    @Override
    public void onDestory() {
    }

}
