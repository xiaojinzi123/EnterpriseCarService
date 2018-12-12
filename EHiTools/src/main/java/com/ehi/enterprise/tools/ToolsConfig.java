package com.ehi.enterprise.tools;

import android.app.Application;
import android.support.annotation.NonNull;

/**
 * time   : 2018/12/11
 *
 * @author : xiaojinzi 30212
 */
public class ToolsConfig {

    @NonNull
    private static Application app;

    private static boolean isDebug;

    public static void init(@NonNull Application app, boolean isDebug) {
        if (app == null) {
            new NullPointerException("the application is null");
        }
        ToolsConfig.app = app;
        ToolsConfig.isDebug = isDebug;
    }

    @NonNull
    public static Application getApp() {
        return app;
    }

}
