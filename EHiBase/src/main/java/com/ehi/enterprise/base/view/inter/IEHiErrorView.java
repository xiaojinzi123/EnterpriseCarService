package com.ehi.enterprise.base.view.inter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * 表示空错误视图的接口
 * <p>
 * time   : 2018/07/10
 *
 * @author : xiaojinzi 30212
 */
public interface IEHiErrorView {

    /**
     * 创建视图
     *
     * @param context 上下文
     * @return
     */
    View createView(Context context);

    /**
     * 销毁了
     */
    void destory();

    /**
     * show an error view
     *
     * @param errorEnum
     */
    void showErrorView(@NonNull EHiBaseViewSupport.EHiErrorBean errorBean);

    /**
     * hide the error view
     */
    void hideErrorView();

    /**
     * 设置重试的监听
     *
     * @param listener
     */
    void setOnRetryListener(View.OnClickListener listener);

}
