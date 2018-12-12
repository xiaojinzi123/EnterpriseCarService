package com.ehi.enterprise.base.view;

import com.ehi.base.view.IBaseActivity;
import com.ehi.base.view.inter.IBaseView;
import com.ehi.enterprise.base.presenter.EHiBasePresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 这个基类添加功能请慎重,如果不是全部Activity都会具备的功能
 * 请通过拓展实现
 *
 * @see EHiBaseFragment
 * @author : xiaojinzi 30212
 */
public abstract class EHiBaseAct<T extends EHiBasePresenter> extends IBaseActivity<T,IBaseView> implements IBaseView {

    /**
     * 这个作为 Activity 之间传值的时候作为key,这个key 作用于界面传值中最重要的值或者唯一的值的key
     */
    public static final String EXTRA_DATA_IN = "data";
    public static final String EXTRA_DATA_RESULT = "data";

    /**
     * BufferKnife的解绑对象
     */
    private Unbinder unbinder;

    @Override
    protected final void initBase() {
        super.initBase();
    }

    protected boolean isLightStatus() {
        return true;
    }

    @Override
    protected void injectView() {
        super.injectView();
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    /**
     * 上一层的Base中会缓存这个对象
     *
     * @return
     */
    @Override
    protected final IBaseView onCreateBaseView() {
        return getBaseView();
    }

    /**
     * 权限是默认的包内可见,请不要更改
     *
     * @return
     */
    IBaseView getBaseView() {
        return new EHiBaseViewImpl(mContext);
    }

}
