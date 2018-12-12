package com.ehi.enterprise.base.view;

import com.ehi.enterprise.base.R;
import com.ehi.enterprise.base.view.inter.EHiBaseViewSupport;
import com.ehi.enterprise.base.view.inter.IEHiErrorView;
import com.ehi.enterprise.base.view.inter.IEHiInitView;

/**
 * time   : 2018/12/12
 *
 * @author : xiaojinzi 30212
 */
public class ContentViewConfig {

    public int layoutId = R.layout.base_layout_error;

    public int realContentId = EHiBaseViewSupport.INVALID;

    public Class<? extends IEHiInitView> initView = EHiInitViewImpl.class;

    public Class<? extends IEHiErrorView> errorView = EHiErrorViewImpl.class;

}
