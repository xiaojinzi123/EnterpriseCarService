package com.ehi.enterprise.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 有关系统的工具类
 *
 * @author xiaojinzi
 */
public class SystemUtil {

    private SystemUtil() {
    }

    /**
     * 判断进程名成
     */
    public static String getCurProcessName() {
        final int pid = android.os.Process.myPid();
        FileReader fileReader = null;
        BufferedReader reader = null;
        try {
            fileReader = new FileReader("/proc/" + pid + "/cmdline");
            reader = new BufferedReader(fileReader);
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
                return processName;
            }
        } catch (Throwable throwable) {
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ignore) {

            }
        }
        return "";
    }

    /**
     * 获取app的名称
     *
     * @param context
     * @return
     */
    public static String getAppName(@NonNull Context context) {
        return context.getApplicationInfo().loadLabel(context.getPackageManager()) + "";
    }

    /**
     * 这个方法获取最近运行任何中最上面的一个应用的包名,<br>
     * 进行了api版本的判断,然后利用不同的方法获取包名,具有兼容性
     *
     * @return 返回包名, 如果出现异常或者获取失败返回""
     */
    public static String getTopAppInfoPackageName() {
        if (SystemInfo.apiVersion < 22) { // 如果版本低于22
            // 获取到activity的管理的类
            ActivityManager m = (ActivityManager) ToolsConfig.getApp().getSystemService(Context.ACTIVITY_SERVICE);
            // 获取最近的一个运行的任务的信息
            List<RunningTaskInfo> tasks = m.getRunningTasks(1);

            if (tasks != null && !tasks.isEmpty()) { // 如果集合不是空的

                // 返回任务栈中最上面的一个
                RunningTaskInfo info = m.getRunningTasks(1).get(0);

                return info.baseActivity.getPackageName();
            } else {
                return "";
            }
        } else {

            final int PROCESS_STATE_TOP = 2;
            try {
                // 获取正在运行的进程应用的信息实体中的一个字段,通过反射获取出来
                Field processStateField = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("processState");
                // 获取所有的正在运行的进程应用信息实体对象
                List<ActivityManager.RunningAppProcessInfo> processes = ((ActivityManager) ToolsConfig.getApp()
                        .getSystemService(Context.ACTIVITY_SERVICE)).getRunningAppProcesses();
                // 循环所有的进程,检测某一个进程的状态是最上面,也是就最近运行的一个应用的状态的时候,就返回这个应用的包名
                for (ActivityManager.RunningAppProcessInfo process : processes) {
                    if (process.importance <= ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                            && process.importanceReasonCode == 0) {
                        int state = processStateField.getInt(process);
                        if (state == PROCESS_STATE_TOP) { // 如果这个实体对象的状态为最近的运行应用
                            String[] packname = process.pkgList;
                            // 返回应用的包名
                            return packname[0];
                        }
                    }
                }
            } catch (Exception e) {
            }
            return "";
        }
    }

    /**
     * 跳转到某一个包名的应用详情界面,<br>
     * 就是系统自带的应用详界面,里面有删除缓存,卸载应用等操作
     */
    @SuppressLint("InlinedApi")
    public static void showInstalledAppDetails(@NonNull Activity context, @NonNull String packageName) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + packageName));
        context.startActivity(intent);
    }

    /**
     * 分享功能
     *
     * @param activityTitle Activity的名字
     * @param msgTitle      消息标题
     * @param msgText       消息内容
     * @param imgPath       图片路径，不分享图片则传null
     */
    public static void shareMsg(@NonNull String activityTitle, @NonNull String msgTitle, @NonNull String msgText,
                                @Nullable String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f.exists() && f.isFile()) {
                intent.setType("image/jpg");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ToolsConfig.getApp().startActivity(Intent.createChooser(intent, activityTitle));
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(@NonNull Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(@NonNull Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }

    /**
     * 设置状态栏的显示的颜色
     *
     * @param window
     * @param dark
     */
    public static void setStatusBar(@NonNull Window window, boolean dark) {
        if (window == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final View decorView = window.getDecorView();
            if (decorView != null) {
                if (dark) {
                    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            /**
             * 针对oppo等部分5.0状态栏变白，状态栏图标文字看不见的问题
             * 这里强制修改颜色
             */
            View bar =new View(window.getContext());
            bar.setBackgroundColor(Color.parseColor("#99d4d4d4"));
            ViewGroup content = (ViewGroup) window.getDecorView();
            content.addView(bar, content.getChildCount());
            bar.getLayoutParams().height = ScreenUtils.getStatusHeight(window.getContext());
        } else {
            boolean b = SystemUtil.FlymeSetStatusBarLightMode(window, dark);
            if (!b) {
                b = SystemUtil.MIUISetStatusBarLightMode(window, dark);
            }
        }
    }

    /**
     * 沉浸式状态栏
     *
     * @param window
     * @param dark
     */
    public static void immersionStatusBar(@NonNull Window window, boolean dark) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setStatusBar(window, dark);
    }

    public static void killCurrentProcess() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }

}
