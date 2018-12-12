package com.ehi.enterprise.base.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.ehi.base.view.inter.IBaseView;
import com.ehi.enterprise.base.presenter.EHiBasePresenter;
import com.ehi.enterprise.base.view.inter.EHiBaseViewSupport;
import com.ehi.enterprise.ui.actionbar.UITitleBar;

/**
 * 1.支持使用注解显示标题栏
 * 2.指定内容布局的id
 * 3.支持初始化页面定制
 * 4.支持错误页面定制,错误包括：网络请求失败(客户端错误),服务器错误(类似于500的错误,404),其他错误
 *
 * @author : xiaojinzi 30212
 * @see
 * @see com.ehi.enterprise.base.view.anno.ContentView
 * @see com.ehi.enterprise.base.view.anno.TitleBar
 */
public abstract class EHiBaseActSupport<T extends EHiBasePresenter>
        extends EHiBaseAct<T> implements EHiBaseViewSupport, EHiBaseViewImplSupport.OnBaseViewListener {

    @Nullable
    protected EHiBaseViewImplSupport mEHiBaseView;

    @Nullable
    protected UITitleBar mTitleBar;

    /**
     * 权限是默认的包内可见,请不要更改
     *
     * @return
     */
    @Override
    IBaseView getBaseView() {
        return getRawWrapEHiBaseView();
    }

    private EHiBaseViewImplSupport getRawWrapEHiBaseView() {
        if (mEHiBaseView == null) {
            mEHiBaseView = new EHiBaseViewImplSupport(mContext);
            mEHiBaseView.setOnBaseViewListener(this);
        }
        return mEHiBaseView;
    }

    protected abstract void onContentViewConfig(@NonNull ContentViewConfig config);

    /**
     * 读取注解中的信息生成contentView
     *
     * @param context
     * @return
     */
    @Override
    protected final View getView(Context context) {

        ContentViewConfig config = new ContentViewConfig();
        onContentViewConfig(config);

        View contentView = getRawWrapEHiBaseView().getView(context, config,this);

        if (contentView == null) {
            throw new IllegalArgumentException("您没有在Activity Class 上写 @ContentView 或者 没有重写 getLayout() 方法");
        }

        mTitleBar = getRawWrapEHiBaseView().mTitleBar;

        return contentView;

    }

    @Override
    public void showEmptyView(@Nullable String msg) {
        getRawWrapEHiBaseView().toggle(true, false, false, null);
    }

    @Override
    public void hideEmptyView() {
        getRawWrapEHiBaseView().toggle(false, false, false, null);
    }

    @Override
    public void showErrorView(@NonNull EHiErrorBean EHiErrorBean) {
        getRawWrapEHiBaseView().showErrorView(EHiErrorBean);
    }

    @Override
    public void hideErrorView() {
        getRawWrapEHiBaseView().toggle(false, false, false, null);
    }

    @Override
    public void showInitView() {
        getRawWrapEHiBaseView().toggle(false, false, true, null);
    }

    @Override
    public void hideInitView() {
        getRawWrapEHiBaseView().hideInitView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEHiBaseView = null;
    }

    @Override
    public void onRetryClick() {
        presenter.onInit();
    }

}
