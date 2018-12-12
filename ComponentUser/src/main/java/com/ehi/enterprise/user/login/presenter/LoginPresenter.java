package com.ehi.enterprise.user.login.presenter;

import android.support.annotation.NonNull;

import com.ehi.enterprise.base.presenter.EHiBasePresenterSupport;
import com.ehi.enterprise.base.source.EHiDataSource;
import com.ehi.enterprise.base.source.EHiDataSourceManager;
import com.ehi.enterprise.user.login.view.ILoginView;

/**
 * time   : 2018/12/12
 *
 * @author : xiaojinzi 30212
 */
public class LoginPresenter extends EHiBasePresenterSupport<ILoginView, EHiDataSource> {

    public LoginPresenter(@NonNull ILoginView view) {
        super(view, EHiDataSourceManager.getDataSource());
    }

}
