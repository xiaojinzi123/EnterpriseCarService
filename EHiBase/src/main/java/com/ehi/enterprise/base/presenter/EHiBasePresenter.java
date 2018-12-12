package com.ehi.enterprise.base.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ehi.base.presenter.IBaseRxPresenter;
import com.ehi.base.view.inter.IBaseView;
import com.ehi.enterprise.base.EHiBusinessFailureException;
import com.ehi.enterprise.support.ErrorUtil;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 这个可以针对任何的Observable进行订阅处理,除了定义的公共的异常,其他的异常请自行实现Consumer<Throwable>接口实现错误的逻辑
 * 这个Presenter只会配合{@link IBaseView}来增添代码,只写最最基础的
 *
 * @author : xiaojinzi 30212
 * @desc :
 * @time : 2017/12/06
 */
public class EHiBasePresenter<T extends IBaseView, P> extends IBaseRxPresenter<T> {

    /**
     * {@link #compositeSubscription} 在 presenter 销毁的时候会 dispose 内部所有的Dispose
     * 这个备用也会在 presenter 销毁的时候会 dispose 内部所有的Dispose
     * 不同的是,这个备用的你可以随时dispose,比如有些操作第二次的时候想要撤销第一次的行为,
     * 简而言之就是在 Presneter 没有被销毁之前,有些行为可能被频繁的撤回,那么你可以将这类的 Obserable
     * 添加到这个容器中,你随时可以销毁他们
     */
    @NonNull
    protected final CompositeDisposable secondCompositeDisposable = new CompositeDisposable();

    /**
     * 如果要单元测试,请Mock并且注入这个类
     */
    @Nullable
    protected P mDataSource;

    public EHiBasePresenter(@NonNull T view) {
        this(view, null);
    }

    public EHiBasePresenter(@NonNull T view, @Nullable P dataSource) {
        super(view);
        tag = getClass().getSimpleName();
        this.view = view;
        this.mDataSource = dataSource;
    }

    /**
     * 当 {@link android.app.Activity} or {@link android.support.v4.app.Fragment}
     * 的生命周期 oncreate 方法执行的时候
     * 这个方法会被调用
     * 你可以写一些初始化的代码
     */
    public void onInit() {
    }

    /**
     * 处理业务的时候的统一处理,这里是纯粹的处理错误的,不能添加其他不相关的代码,比如让视图关闭对话框这种
     *
     * @param t 错误
     * @return 返回一个值表示是否已经处理了
     */
    protected void normalErrorSove(@NonNull Throwable t) {

        if (ErrorUtil.isServerTimeoutError(t)) {
            view.tip("请求服务器超时，请稍后重试", IBaseView.TipEnum.Error);
        } else if (t instanceof EHiBusinessFailureException) {
            EHiBusinessFailureException ehiBusinessFailureException = (EHiBusinessFailureException) t;
            view.tip(ehiBusinessFailureException.getMessage());
        } else if (ErrorUtil.isNetWorkError(t)) {
            view.tip("网络异常,请检查您的网络", IBaseView.TipEnum.Error);
        } else if (ErrorUtil.isServerError(t)) {
            view.tip("服务器异常", IBaseView.TipEnum.Error);
        } else if (ErrorUtil.isUIUpdateException(t)) {
            view.tip("非主线程更新UIException");
        } else {
            view.tip("请求异常，请稍后重试", IBaseView.TipEnum.Error);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (secondCompositeDisposable != null) {
            secondCompositeDisposable.dispose();
        }
    }

}
