package com.ehi.enterprise;

import android.app.Application;

import com.ehi.component.ComponentConfig;
import com.ehi.component.impl.EHiModuleManager;
import com.ehi.component.impl.EHiRxRouter;
import com.ehi.enterprise.tools.SystemUtil;
import com.ehi.enterprise.tools.ToolsConfig;

/**
 * time   : 2018/12/12
 *
 * @author : xiaojinzi 30212
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ToolsConfig.init(this, true);

        if (getPackageName().equalsIgnoreCase(SystemUtil.getCurProcessName())) {
            initApp(this);
        }

    }

    /**
     * 一个进程初始化一次,这个只是为了主进程初始化的
     * @param app
     */
    private void initApp(App app) {

        ComponentConfig.init(app,true);
        EHiRxRouter.tryErrorCatch();

        // 注册用户模块
        EHiModuleManager.getInstance().register(ModuleConfig.User.NAME);

    }


}
