package com.ehi.enterprise.base.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ehi.base.view.inter.IBaseView;
import com.ehi.enterprise.base.presenter.EHiBasePresenter;
import com.ehi.enterprise.base.view.inter.EHiBaseViewSupport;
import com.ehi.enterprise.ui.actionbar.UITitleBar;

/**
 * time   : 2018/06/28
 *
 * @author : xiaojinzi 30212
 */
public abstract class EHiBaseFragmentSupport<T extends EHiBasePresenter> extends EHiBaseFragment<T> implements EHiBaseViewSupport, EHiBaseViewImplSupport.OnBaseViewListener {

    @Override
    public View getLayout(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {

        ContentViewConfig config = new ContentViewConfig();
        onContentViewConfig(config);

        View contentView = getRawWrapEHiBaseView().getView(mContext, config, this);

        if (contentView == null) {
            throw new IllegalArgumentException("您没有在Fragment Class 上写 @ContentView 或者 没有重写 getLayout() 方法");
        }

        mTitleBar = getRawWrapEHiBaseView().mTitleBar;

        return contentView;

    }

    protected abstract void onContentViewConfig(@NonNull ContentViewConfig config);

    @Nullable
    protected EHiBaseViewImplSupport mEHiBaseView;

    @Nullable
    protected UITitleBar mTitleBar;

    @Override
    protected IBaseView getBaseView() {
        return getRawWrapEHiBaseView();
    }

    protected EHiBaseViewImplSupport getRawWrapEHiBaseView() {
        if (mEHiBaseView == null) {
            mEHiBaseView = new EHiBaseViewImplSupport(mContext);
            mEHiBaseView.setOnBaseViewListener(this);
        }
        return mEHiBaseView;
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
        getRawWrapEHiBaseView().toggle(false, false, false, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mEHiBaseView = null;
    }


    @Override
    public void onRetryClick() {
        presenter.onInit();
    }

}
