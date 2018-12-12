package com.ehi.enterprise.base.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ehi.enterprise.base.EHiBusinessFailureException;
import com.ehi.enterprise.base.view.inter.EHiBaseViewSupport;
import com.ehi.enterprise.support.ErrorUtil;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

/**
 * Base Presenter 的 针对一嗨租车的支持版本
 *
 * time : 2017/12/06
 *
 * @author : xiaojinzi 30212
 */
public class EHiBasePresenterSupport<T extends EHiBaseViewSupport, P> extends EHiBasePresenter<T, P> {

    public EHiBasePresenterSupport(@NonNull T view) {
        super(view);
    }

    public EHiBasePresenterSupport(@NonNull T view, @Nullable P dataSource) {
        super(view, dataSource);
    }

    protected <T1> void subscribeInit(@NonNull Observable<T1> o, final @NonNull Consumer<T1> success) {

        subscribeInit(o, success, null);

    }

    protected <T1> void subscribeInit(@NonNull Observable<T1> o, final @NonNull Consumer<T1> success, @Nullable final Consumer<Throwable> fail) {

        subscribeInit(o, success, fail, null);

    }

    protected <T1> void subscribeInit(@NonNull Observable<T1> o, final @NonNull Consumer<T1> success,
                                      @Nullable final Consumer<Throwable> fail, @Nullable final Action complete) {

        view.showInitView();

        final Consumer<T1> mAccept = new Consumer<T1>() {
            @Override
            public void accept(T1 t1) throws Exception {
                if (success != null) {
                    success.accept(t1);
                }
            }
        };

        final Consumer<Throwable> mFail = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable t) throws Exception {
                view.hideInitView();
                if (fail != null) {
                    fail.accept(t);
                } else {
                    ehiInitErrorSove(t);
                }
            }
        };

        final Action mComplete = new Action() {
            @Override
            public void run() throws Exception {
                view.hideInitView();
                if (complete != null) {
                    complete.run();
                }
            }
        };

        super.subscribe(o, mAccept, mFail, mComplete, false, false);

    }

    protected <T1> void fSubscribeInit(@NonNull Flowable<T1> o, final @NonNull Consumer<T1> success) {

        fSubscribeInit(o, success, null);

    }

    protected <T1> void fSubscribeInit(@NonNull Flowable<T1> o, final @NonNull Consumer<T1> success, @Nullable final Consumer<Throwable> fail) {

        fSubscribeInit(o, success, fail, null);

    }

    protected <T1> void fSubscribeInit(@NonNull Flowable<T1> o, final @NonNull Consumer<T1> success,
                                       @Nullable final Consumer<Throwable> fail, @Nullable final Action complete) {

        view.showInitView();

        final Consumer<T1> mAccept = new Consumer<T1>() {
            @Override
            public void accept(T1 t1) throws Exception {
                if (success != null) {
                    success.accept(t1);
                }
            }
        };

        final Consumer<Throwable> mFail = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable t) throws Exception {
                view.hideInitView();
                if (fail != null) {
                    fail.accept(t);
                } else {
                    ehiInitErrorSove(t);
                }
            }
        };

        final Action mComplete = new Action() {
            @Override
            public void run() throws Exception {
                view.hideInitView();
                if (complete != null) {
                    complete.run();
                }
            }
        };

        super.fSubscribe(o, mAccept, mFail, mComplete, false, false);

    }

    protected <T1> void mbSubscribeInit(@NonNull Maybe<T1> o, final @NonNull Consumer<T1> success) {

        mbSubscribeInit(o, success, null);

    }

    protected <T1> void mbSubscribeInit(@NonNull Maybe<T1> o, final @NonNull Consumer<T1> success, @Nullable final Consumer<Throwable> fail) {

        mbSubscribeInit(o, success, fail, null);

    }

    protected <T1> void mbSubscribeInit(@NonNull Maybe<T1> o, final @NonNull Consumer<T1> success,
                                        @Nullable final Consumer<Throwable> fail, @Nullable final Action complete) {

        view.showInitView();

        final Consumer<T1> mSuccess = new Consumer<T1>() {
            @Override
            public void accept(T1 t1) throws Exception {
                if (success != null) {
                    success.accept(t1);
                }
            }
        };

        final Consumer<Throwable> mFail = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable t) throws Exception {
                view.hideInitView();
                if (fail != null) {
                    fail.accept(t);
                } else {
                    ehiInitErrorSove(t);
                }
            }
        };

        final Action mComplete = new Action() {
            @Override
            public void run() throws Exception {
                view.hideInitView();
                if (complete != null) {
                    complete.run();
                }
            }
        };

        super.mbSubscribe(o, mSuccess, mFail, mComplete, false, false);

    }

    protected <T1> void sgSubscribeInit(@NonNull Single<T1> o, final @NonNull Consumer<T1> success) {

        sgSubscribeInit(o, success, null);

    }


    protected <T1> void sgSubscribeInit(@NonNull Single<T1> o, final @NonNull Consumer<T1> success,
                                        @Nullable final Consumer<Throwable> fail) {

        view.showInitView();

        final Consumer<T1> mSuccess = new Consumer<T1>() {
            @Override
            public void accept(T1 t1) throws Exception {
                view.hideInitView();
                if (success != null) {
                    success.accept(t1);
                }
            }
        };

        final Consumer<Throwable> mFail = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable t) throws Exception {
                view.hideInitView();
                if (fail != null) {
                    fail.accept(t);
                } else {
                    ehiInitErrorSove(t);
                }
            }
        };

        super.sgSubscribe(o, mSuccess, mFail, false, false);

    }

    protected <T1> void comSubscribeInit(@NonNull Completable o) {

        comSubscribeInit(o, null, null);

    }

    protected <T1> void comSubscribeInit(@NonNull Completable o, @Nullable final Action complete) {

        comSubscribeInit(o, complete, null);

    }

    protected <T1> void comSubscribeInit(@NonNull Completable o, @Nullable final Consumer<Throwable> fail) {

        comSubscribeInit(o, null, fail);

    }

    protected <T1> void comSubscribeInit(@NonNull Completable o, @Nullable final Action complete, @Nullable final Consumer<Throwable> fail) {

        view.showInitView();

        final Action mComplete = new Action() {
            @Override
            public void run() throws Exception {
                view.hideInitView();
                if (complete != null) {
                    complete.run();
                }
            }
        };

        final Consumer<Throwable> mFail = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable t) throws Exception {
                view.hideInitView();
                if (fail != null) {
                    fail.accept(t);
                } else {
                    ehiInitErrorSove(t);
                }
            }
        };

        super.comSubscribe(o, mFail, mComplete, false, false);

    }


    /**
     * 初始化的错误错误
     *
     * @param t
     * @return
     */
    protected boolean ehiInitErrorSove(@NonNull Throwable t) {

        if (ErrorUtil.isServerTimeoutError(t)) {
            EHiBaseViewSupport.EHiNetworkErrorBean error = new EHiBaseViewSupport.EHiNetworkErrorBean();
            error.setMsg("请求服务器超时");
            view.showErrorView(error);
            //view.tip("请求服务器超时，请稍后重试", IBaseView.TipEnum.Error);
        } else if (t instanceof EHiBusinessFailureException) {
            EHiBusinessFailureException eHiBusinessFailureException = (EHiBusinessFailureException) t;
            EHiBaseViewSupport.EHiBusinessFailureError error = new EHiBaseViewSupport.EHiBusinessFailureError(eHiBusinessFailureException);
            view.showErrorView(error);
        } else if (ErrorUtil.isNetWorkError(t)) {
            EHiBaseViewSupport.EHiNetworkErrorBean error = new EHiBaseViewSupport.EHiNetworkErrorBean();
            view.showErrorView(error);
        } else if (t.getClass() == HttpException.class) { // 单独判断这个是为了能显示出对应的网络错误的状态吗和名称
            HttpException httpException = (HttpException) t;
            EHiBaseViewSupport.EHiServerErrorBean error = new EHiBaseViewSupport.EHiServerErrorBean(httpException.code(), httpException.message());
            view.showErrorView(error);
        } else if (ErrorUtil.isServerError(t)) {
            EHiBaseViewSupport.EHiServerErrorBean error = new EHiBaseViewSupport.EHiServerErrorBean(500, "服务器错误");
            view.showErrorView(error);
        }  else if (ErrorUtil.isUIUpdateException(t)) {
            view.tip("非主线程更新UIException");
        } else {
            view.showErrorView(new EHiBaseViewSupport.EHiErrorBean().setMsg("未知错误"));
        }

        return true;

    }

}
