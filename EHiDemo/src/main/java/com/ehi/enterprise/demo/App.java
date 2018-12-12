package com.ehi.enterprise.demo;

import android.app.Application;

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
    }

}
