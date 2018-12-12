package com.ehi.enterprise.base.view;

import android.view.View;

import com.ehi.base.view.inter.IBaseView;
import com.ehi.enterprise.base.presenter.EHiBasePresenter;
import com.ehi.enterprise.base.view.inter.EHiBaseView;

import butterknife.ButterKnife;

/**
 * modify by xiaojinzi
 * 类中的成员变量不会销毁,随着fragment的销毁而销毁,这个销毁和Fragment的生命周期销毁是两回事
 * 生命周期的销毁并不会导致这个类及其成员变量的销毁
 *
 * @author : xiaojinzi 30212
 */
public abstract class EHiBaseFragment<T extends EHiBasePresenter> extends com.ehi.base.view.IBaseFragment<T> implements EHiBaseView {

    /**
     * 这个作为 Activity 之间传值的时候作为key,这个key 作用于界面传值中最重要的值或者唯一的值的key
     */
    public static final String EXTRA_DATA_IN = "data";
    public static final String EXTRA_DATA_RESULT = "data";

    @Override
    protected void injectView(View contentView) {
        super.injectView(contentView);

        // 控件注入
        ButterKnife.bind(this, contentView);

    }

    @Override
    protected final IBaseView getRawWrapView() {
        return getBaseView();
    }

    IBaseView getBaseView() {
        return new EHiBaseViewImpl(mContext);
    }

}
