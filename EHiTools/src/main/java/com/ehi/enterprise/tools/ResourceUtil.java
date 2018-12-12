package com.ehi.enterprise.tools;

import android.app.Application;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

/**
 * Created by xiaojinzi on 02/11/2017 9:05 AM
 */
public class ResourceUtil {

    private static Application getApplication() {
        return ToolsConfig.getApp();
    }

    public static int getColor(@ColorRes int rsd) {
        return getColor(getApplication().getApplicationContext().getResources(), rsd);
    }

    public static int getColor(Resources resources, @ColorRes int rsd) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return resources.getColor(rsd, null);
        } else {
            return resources.getColor(rsd);
        }
    }

    @Nullable
    public static ColorStateList getColorStateList(@ColorRes int rsd) {
        return getColorStateList(getApplication().getResources(), rsd);
    }

    @Nullable
    public static ColorStateList getColorStateList(Resources resources, @ColorRes int rsd) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return resources.getColorStateList(rsd, null);
        } else {
            return resources.getColorStateList(rsd);
        }
    }

    public static Drawable getDrawable(@DrawableRes int rsd) {

        return getDrawable(getApplication().getResources(), rsd);

    }

    public static Drawable getDrawable(Resources resources, @DrawableRes int rsd) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return resources.getDrawable(rsd, null);
        } else {
            return resources.getDrawable(rsd);
        }

    }

    public static String getString(@StringRes int rsd) {
        return getString(getApplication().getResources(), rsd);
    }

    public static String getString(Resources resources, @StringRes int rsd) {
        return resources.getString(rsd);
    }

    public static int getDimen(@DimenRes int rsd) {
        return getDimen(getApplication().getResources(), rsd);
    }

    public static int getDimen(Resources resources, @DimenRes int rsd) {
        return resources.getDimensionPixelSize(rsd);
    }

}
