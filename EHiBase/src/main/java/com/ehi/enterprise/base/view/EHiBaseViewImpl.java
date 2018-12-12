package com.ehi.enterprise.base.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.ehi.base.view.inter.IBaseView;
import com.ehi.enterprise.tools.ToastUtil;
import com.ehi.enterprise.ui.dialog.UIConfirmDialog;
import com.ehi.tools.ResourceUtil;

/**
 * time   : 2018/03/14
 *
 * @author : xiaojinzi 30212
 */
public class EHiBaseViewImpl implements IBaseView {

    @Nullable
    private Activity mContext;

    private Dialog dialog;

    public EHiBaseViewImpl(@Nullable Context context) {
        attachContext(context);
    }

    public void attachContext(@Nullable Context context) {
        if (context == null) {
            mContext = null;
        } else {
            if (context instanceof Activity) {
                mContext = (Activity) context;
            }
        }
    }

    public void detachContext() {
        mContext = null;
        destroy();
    }

    @Override
    public void showProgress() {
        showProgress(false);
    }

    @Override
    public void showProgress(boolean cancelable) {

        if (mContext == null) {
            destroy();
            return;
        }

        if (mContext.isDestroyed() || mContext.isFinishing()) { // 如果已经要销毁了
            return;
        }

        if (null == dialog) {
            View layout = LayoutInflater.from(mContext).inflate(com.ehi.enterprise.ui.R.layout.ui_progress_dialog, null);
            dialog = new Dialog(mContext, com.ehi.enterprise.ui.R.style.UiLoadingDialog);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(layout);
            Window window = dialog.getWindow();
            window.setWindowAnimations(com.ehi.enterprise.ui.R.style.UiLoadingDialogAnim);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        // 这个不能少
        if (null == dialog) {
            return;
        }

        dialog.setCancelable(cancelable);

        if (dialog.isShowing()) {
            return;
        }

        dialog.show();

    }

    @Override
    public void closeProgress() {
        if (null == dialog) {
            return;
        }
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void tip(@NonNull String msg) {
        tip(msg, TipEnum.Normal);
    }

    @Override
    public void tip(@StringRes int rsd) {
        if (null == mContext) {
            return;
        }
        tip(ResourceUtil.getString(mContext, rsd), TipEnum.Normal);
    }

    @Override
    public void tip(@NonNull String msg, TipEnum tipEnum) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        switch (tipEnum) {
            case Error:
            case Normal:
                ToastUtil.makeText(msg);
                break;
            case MsgBox:
                if (mContext != null && !TextUtils.isEmpty(msg)) {
                    UIConfirmDialog.build(mContext)
                            .content(msg)
                            .cancelString(null)
                            .build()
                            .show();
                }
                break;
        }
    }

    @Override
    public void tip(int i, @NonNull TipEnum tipEnum) {

    }

    @Override
    public void destroy() {
        closeProgress();
        mContext = null;
        dialog = null;
    }

}
