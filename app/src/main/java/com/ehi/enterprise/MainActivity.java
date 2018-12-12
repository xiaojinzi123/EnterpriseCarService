package com.ehi.enterprise;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ehi.component.impl.EHiRouter;
import com.ehi.enterprise.base.view.ContentViewConfig;
import com.ehi.enterprise.base.view.EHiBaseActSupport;
import com.ehi.enterprise.base.view.anno.TitleBar;

@TitleBar("主界面")
public class MainActivity extends EHiBaseActSupport {

    @Override
    protected void onContentViewConfig(@NonNull ContentViewConfig config) {
        config.layoutId = R.layout.activity_main;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        EHiRouter.with(mContext)
                .host(ModuleConfig.User.NAME)
                .path(ModuleConfig.User.USER_LOGIN)
                .navigate();
    }

}
