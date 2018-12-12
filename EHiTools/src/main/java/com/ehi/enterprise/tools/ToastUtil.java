package com.ehi.enterprise.tools;

import android.app.Application;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtil {

    private static Application getApplication() {
        return ToolsConfig.getApp();
    }

    public static void makeText(String text) {
        Toast.makeText(getApplication(), text, Toast.LENGTH_SHORT).show();
    }

    public static void makeText(@StringRes int strResId) {
        Toast.makeText(getApplication(),
                getApplication().getResources().getString(strResId), Toast.LENGTH_SHORT).show();
    }

    public static void showCenterText(String title) {
        Toast toast = new Toast(getApplication());
        View view = LayoutInflater.from(getApplication())
                .inflate(R.layout.tools_toast_center_layout, null, false);
        TextView titleView = view.findViewById(R.id.tv_desc);
        titleView.setText(title);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showCenterText(@StringRes int strResId) {
        showCenterText(getApplication().getResources().getString(strResId));
    }

    public static void leftImgRightTextShow(@NonNull String str, @DrawableRes int imgSrc) {
        final Context context = getApplication();
        View view = LayoutInflater.from(context).inflate(R.layout.tools_toast_1_layout, null);
        ((ImageView) view.findViewById(R.id.iv)).setImageResource(imgSrc);
        ((TextView) view.findViewById(R.id.tv_desc)).setText(str);
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);//setting the view of custom toast layout
        toast.show();
    }

    public static void successShow(@NonNull String tip) {
        final Context context = getApplication();
        final Toast toast = Toast.makeText(context, tip, Toast.LENGTH_SHORT);
        View contentView = LayoutInflater.from(context).inflate(R.layout.tools_toast_success_layout, null);
        ((TextView) contentView.findViewById(R.id.tv_desc)).setText(tip);
        toast.setView(contentView);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
