package com.ehi.enterprise;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Stack;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * use {@link android.app.Application#registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks)}
 * to listen all Action's life cycle method
 *
 * @author xiaojinzi
 */
public class ActivityStack {

    /**
     * flag the {@link Activity} is not allowed to finish,such
     * but you can call the method {@link #forcePopAllActivity()}
     */
    @Target(ElementType.TYPE)
    @Retention(RUNTIME)
    public @interface UnFinishActivity {

        /**
         * flag the Activity with a flag,empty string means Activity will be finish when
         * the method {@link #forcePopAllActivity()} invoked in any case
         * <p>
         * if the string is not empty,
         *
         * @return
         */
        String value();

    }

    /**
     * you can use Annotation {@link ActivityAction} to flag your Activity {@link Activity}
     * and write your custom action.
     * 你可以利用这个注解标记的Activity,一个完整的业务流程用同一个标记,
     * 这样子可以让你在这个流程结束的时候,
     * 可以准确无误的杀死这个流程上的所有Activity
     */
    @Target(ElementType.TYPE)
    @Retention(RUNTIME)
    public @interface ActivityAction {

        /**
         * the value of custom action
         *
         * @return
         */
        String[] value();

    }

    /**
     * flag a Activity you can open more than one
     */
    @Target(ElementType.TYPE)
    @Retention(RUNTIME)
    public @interface ActivityCanRepeat {
    }

    /**
     * the stack will be save all reference of Activity
     */
    private Stack<Activity> activityStack = new Stack<>();

    @NonNull
    private static ActivityStack instance;

    private ActivityStack() {
    }

    @MainThread
    public static ActivityStack getInstance() {
        if (instance == null) {
            instance = new ActivityStack();
        }
        return instance;
    }

    private String[] canNotDestoryActivities = {
            "com.jdpaysdk.author.AuthorActivity"
    };

    /**
     * 进入栈
     */
    public synchronized void pushActivity(Activity activity) {

        if (activity == null) return;
        if (activityStack.contains(activity)) return;

        // 检查顶层的这个是不是和当前要进入的这个是一个Class,这是一个折中的方式,下一步,让每一个视图都继承baseView中的父类,在父类中对点击事件进行处理

        boolean canOpenMore = activity.getClass().isAnnotationPresent(ActivityCanRepeat.class);

        if (activityStack.size() > 0 && !canOpenMore) {

            boolean isFinish = false;

            Activity preAct = activityStack.get(activityStack.size() - 1);

            if (preAct.getClass() == activity.getClass()) {
                isFinish = true;
            }

            // 如果匹配到名称就放过,不要销毁掉
            for (String className : canNotDestoryActivities) {
                if (className.equals(preAct.getClass().getName())) {
                    isFinish = false;
                    break;
                }
            }

            // 如果需要销毁前一个
            if (isFinish) {
                if (!preAct.isFinishing()) {
                    preAct.finish();
                }
                removeActivity(preAct);
            }

        }

        activityStack.add(activity);

    }

    /**
     * you can use Annotation {@link ActivityAction} to flag your Activity {@link Activity}
     * and write your custom action.
     * and then you can call this method delivery a action,such as "selfOrderAction" .
     * this method will finish all Activity which was marked with Annotation {@link ActivityAction}
     * and the Annotation's value() is equals to "selfOrderAction"
     *
     * @param action the custom action
     */
    public synchronized void popActivityWithAction(@NonNull String action) {
        outter:
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity currentAct = activityStack.get(i);
            if (currentAct == null) continue;
            ActivityAction activityAction = currentAct.getClass().getAnnotation(ActivityAction.class);
            if (activityAction == null) continue;
            String[] values = activityAction.value();
            if (values == null) continue;
            inner:
            for (String value : values) {
                if (value.equals(action)) {
                    currentAct.finish();
                    removeActivity(currentAct);
                    break inner;
                }
            }
        }
    }

    /**
     * old code
     * <p>
     * 删除栈的最上几层
     * 这方法建议不要调用,你应该使用Android中的广播机制来通知你需要销毁的界面或者使用建议的方法,而不是通过这个方法
     * please use method {@link #popActivityWithAction(String)}
     *
     * @param count 删除层数 当前层也算
     * @see {@link #popActivityWithAction(String)}
     */
    @Deprecated
    public synchronized void popCountActivity(int count) {

        if (count < 1) {
            return;
        }

        int size = activityStack.size();
        for (int i = size - 1; i > size - count - 1; i--) {
            if (i < 0 || i >= activityStack.size()) {
                continue;
            }
            Activity templateActivity = activityStack.get(i);
            if (templateActivity == null) continue;
            if (templateActivity.getClass().isAnnotationPresent(UnFinishActivity.class)) continue;
            templateActivity.finish();

            activityStack.pop();
        }

    }

    /**
     * will destory all Activity except the activity is masked with Annotation {@link UnFinishActivity}
     * see {@link #forcePopAllActivity()}
     *
     * @author cxj
     * @time 26/10/2017  10:52 AM
     */
    public synchronized void popAllActivity() {

        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity = activityStack.get(i);

            if (activity == null) continue;
            if (activity.getClass().isAnnotationPresent(UnFinishActivity.class)) continue;

            removeActivity(activity);

            if (!activity.isFinishing()) {
                activity.finish();
            }

        }

    }

    /**
     * will destory all Activity except the activity is masked with Annotation {@link UnFinishActivity}
     * see {@link #forcePopAllActivity()}
     *
     * @param act
     * @see {@link #forcePopAllActivity()}
     */
    @Deprecated
    public synchronized void popAllActivity(Activity act) {

        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity currentAct = activityStack.get(i);
            if (currentAct == null) continue;
            if (currentAct.getClass().isAnnotationPresent(UnFinishActivity.class)) continue;

            removeActivity(currentAct);

            if (!currentAct.isFinishing()) {
                currentAct.finish();
            }

        }

        if (act != null && !act.isFinishing()) {
            removeActivity(act);
            act.finish();
        }

    }

    /**
     * will finish all Activity
     *
     * @author cxj
     * @time 26/10/2017  1:27 PM
     */
    public synchronized void forcePopAllActivity() {
        forcePopAllActivityWithFalg();
    }


    /**
     * will finish all Activity except the Activities
     * which UnFinishActivity's value is equal to parameter 'flag'
     *
     * @param flags
     */
    public synchronized void forcePopAllActivityWithFalg(@Nullable String... flags) {

        for (int i = activityStack.size() - 1; i >= 0; i--) {

            Activity activity = activityStack.get(i);

            if (activity == null) continue;

            boolean isFinish = true;

            UnFinishActivity unFinishActivity = activity.getClass().getAnnotation(UnFinishActivity.class);
            if (unFinishActivity != null && flags != null) {
                // if the flag string is the same as value,the Activity will not be finish
                String value = unFinishActivity.value();
                for (int j = 0; j < flags.length; j++) {
                    if (value.equals(flags[j])) {
                        isFinish = false;
                        break;
                    }
                }
            }

            if (isFinish) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
                activityStack.remove(i);
            }

        }

    }

    /**
     * remove the reference of Activity
     *
     * @author cxj
     * @time 26/10/2017  10:52 AM
     */
    public synchronized void removeActivity(Activity activity) {
        activityStack.remove(activity);
    }

    /**
     * @return whether the the size of stack of Activity is zero or not
     */
    public synchronized boolean isEmpty() {
        if (activityStack == null || activityStack.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 主界面是否存在
     *
     * @return
     */
    public synchronized boolean isActivityExit(Class<?> clazz) {
        if (activityStack == null || activityStack.size() == 0) {
            return false;
        }

        boolean result = false;

        for (Activity act : activityStack) {
            if (act.getClass() == clazz) {
                result = true;
                break;
            }
        }

        return result;

    }

    /**
     * @return the size of stack of Activity
     */
    public synchronized int getSize() {
        if (activityStack == null) return 0;
        return activityStack.size();
    }

    /**
     * @return the Activity On the top
     */
    @Nullable
    public synchronized Activity getTopActivity() {
        return isEmpty() || activityStack == null ? null : activityStack.get(activityStack.size() - 1);
    }

}
