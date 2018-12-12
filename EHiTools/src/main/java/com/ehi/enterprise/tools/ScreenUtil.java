package com.ehi.enterprise.tools;

import android.app.Application;
import android.content.Context;

/**
 * Created on 2018/11/6.
 * author : HeQing
 * desc :
 */
public class ScreenUtil {

    private static Application getApplication() {
        return ToolsConfig.getApp();
    }

    /**
     * 获得状态栏的高度
     *
     * @param context 上下文
     * @return
     */
    public static int getStatusHeight() {
        if (getApplication() == null){
            return 0;
        }
        int statusHeight = 0;
        try {
            final int resId = getApplication().getResources()
                    .getIdentifier("status_bar_height", "dimen", "android");
            if (resId > 0) {
                statusHeight = getApplication().getResources().getDimensionPixelSize(resId);
            }
            if (statusHeight > 0) {
                return statusHeight;
            }
            final Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            final Object object = clazz.newInstance();
            final int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = getApplication().getResources().getDimensionPixelSize(height);
            return statusHeight;
        } catch (Exception e) {
        }
        return statusHeight;
    }

}
