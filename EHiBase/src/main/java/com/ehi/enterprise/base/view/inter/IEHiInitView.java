package com.ehi.enterprise.base.view.inter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * 表示初始化视图的接口
 * <p>
 * time   : 2018/07/10
 *
 * @author : xiaojinzi 30212
 */
public interface IEHiInitView {

    /**
     * 创建视图
     *
     * @param context 上下文
     * @return
     */
    @NonNull
    View createView(@NonNull Context context);

    /**
     * 销毁了
     */
    void destory();

    /**
     * 显示初始化视图
     */
    void showInitView();

    /**
     * 隐藏初始化视图
     */
    void hideInitView();

}
