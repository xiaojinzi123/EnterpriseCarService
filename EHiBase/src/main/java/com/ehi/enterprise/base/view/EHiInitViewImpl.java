package com.ehi.enterprise.base.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.ehi.enterprise.base.R;
import com.ehi.enterprise.base.view.inter.IEHiInitView;

/**
 * time   : 2018/07/10
 *
 * @author : xiaojinzi 30212
 */
public class EHiInitViewImpl implements IEHiInitView {

    @Nullable
    private View contentView;

    @Override
    @NonNull
    public View createView(Context context) {
        if (contentView == null) {
            contentView = View.inflate(context, R.layout.base_init_view, null);
        }
        return contentView;
    }

    @Override
    public void destory() {
        contentView = null;
    }

    @Override
    public void showInitView() {

        if (contentView == null) {
            return;
        }
        contentView.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideInitView() {

        if (contentView == null) {
            return;
        }
        contentView.setVisibility(View.INVISIBLE);

    }

}
