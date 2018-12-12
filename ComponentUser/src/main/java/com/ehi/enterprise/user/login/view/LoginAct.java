package com.ehi.enterprise.user.login.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.ehi.component.anno.EHiRouterAnno;
import com.ehi.enterprise.ModuleConfig;
import com.ehi.enterprise.base.view.ContentViewConfig;
import com.ehi.enterprise.base.view.EHiBaseActSupport;
import com.ehi.enterprise.base.view.anno.TitleBar;
import com.ehi.enterprise.user.R;
import com.ehi.enterprise.user.login.presenter.LoginPresenter;

/**
 * 登录界面
 *
 * @author xiaojinzi
 */
@TitleBar("登录")
@EHiRouterAnno(host = ModuleConfig.User.NAME, value = ModuleConfig.User.USER_LOGIN)
public class LoginAct extends EHiBaseActSupport implements ILoginView {

    private Button bt_login;

    @Override
    protected void onContentViewConfig(@NonNull ContentViewConfig config) {
        config.layoutId = R.layout.user_login_act;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        bt_login = findViewById(R.id.bt_login);
        presenter = new LoginPresenter(this);
    }

    @Override
    protected void initListener() {
        super.initListener();

        bt_login.setOnClickListener(v -> { });

    }


}
