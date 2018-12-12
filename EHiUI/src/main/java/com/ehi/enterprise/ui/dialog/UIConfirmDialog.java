package com.ehi.enterprise.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ehi.enterprise.ui.R;
import com.ehi.enterprise.ui.UIConfig;

/**
 * Created by xiaojinzi on 16/10/2017
 */
public class UIConfirmDialog extends AppCompatDialog {

    protected UIConfirmDialog(@NonNull Builder builder) {
        super(builder.mContext, R.style.UiConfirmDialog);

        // 创建自定义的xml布局
        View v = View.inflate(builder.mContext, R.layout.ui_confirm_dialog, null);

        init(v, builder);

        // 设置显示的布局
        setContentView(v);

    }

    private void init(View v, final Builder builder) {

        ImageView iv_hint_icon = v.findViewById(R.id.iv_hint_icon);
        TextView tv_cancel = v.findViewById(R.id.tv_cancel);
        TextView tv_confirm = v.findViewById(R.id.tv_confirm);
        TextView tv_content = v.findViewById(R.id.tv_content);
        View view_split_line_cancel_and_confirm = v.findViewById(R.id.view_split_line_cancel_and_confirm);

        if (builder.isShowHintIcon) {
            iv_hint_icon.setVisibility(View.VISIBLE);
            tv_content.setMinLines(1);
        } else {
            iv_hint_icon.setVisibility(View.GONE);
            // 为了只有文本的时候整个弹框高度能高一点
            tv_content.setMinLines(3);
        }

        if (TextUtils.isEmpty(builder.mCancelText) || TextUtils.isEmpty(builder.mConfirmText)) {
            view_split_line_cancel_and_confirm.setVisibility(View.GONE);
        } else {
            view_split_line_cancel_and_confirm.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(builder.mCancelText)) {
            tv_cancel.setVisibility(View.GONE);
            tv_confirm.setBackgroundResource(R.drawable.ui_confirm_dialog_confirm_bg1);
        } else {
            tv_cancel.setVisibility(View.VISIBLE);
            tv_cancel.setText(builder.mCancelText);
            tv_confirm.setBackgroundResource(R.drawable.ui_confirm_dialog_confirm_bg);
        }

        if (TextUtils.isEmpty(builder.mConfirmText)) {
            tv_confirm.setVisibility(View.GONE);
            tv_cancel.setBackgroundResource(R.drawable.ui_confirm_dialog_cancel_bg1);
        } else {
            tv_confirm.setVisibility(View.VISIBLE);
            tv_confirm.setText(builder.mConfirmText);
            tv_cancel.setBackgroundResource(R.drawable.ui_confirm_dialog_cancel_bg);
        }

        tv_content.setText(builder.mContent == null ? "" : builder.mContent);

        setCancelable(builder.mIsCancelable);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (builder.mCancelClickListener != null) {
                    builder.mCancelClickListener.onClick(v);
                }
            }
        });

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (builder.mConfirmClickListener != null) {
                    builder.mConfirmClickListener.onClick(v);
                }
            }
        });

    }

    public static class Builder {

        boolean isShowHintIcon = false;
        Context mContext;
        @NonNull
        CharSequence mContent;
        @NonNull
        CharSequence mConfirmText = "确定";
        boolean mIsCancelable = false;
        View.OnClickListener mConfirmClickListener;
        @Nullable
        CharSequence mCancelText = "取消";
        View.OnClickListener mCancelClickListener;

        public Builder(@NonNull Context context) {
            mContext = context;
        }

        public Builder hintIcon(boolean isShow) {
            isShowHintIcon = isShow;
            return this;
        }

        /**
         * 设置内容
         *
         * @param content
         * @return
         */
        public Builder content(@NonNull CharSequence content) {
            mContent = content;
            return this;
        }

        public Builder cancelable(boolean isCancelable) {
            mIsCancelable = isCancelable;
            return this;
        }

        public Builder confirmString(@NonNull CharSequence confirmText) {
            return confirm(confirmText, mConfirmClickListener);
        }

        public Builder confirmListener(@Nullable View.OnClickListener confirmClickListener) {
            return confirm(mConfirmText, confirmClickListener);
        }

        /**
         * 确定按钮的文本和点击事件
         *
         * @param confirmText          确定按钮的文本
         * @param confirmClickListener 点击事件
         * @return
         */
        public Builder confirm(@NonNull CharSequence confirmText, @Nullable View.OnClickListener confirmClickListener) {
            mConfirmText = confirmText;
            mConfirmClickListener = confirmClickListener;
            return this;
        }

        public Builder cancelString(@Nullable CharSequence cancelText) {
            return cancel(cancelText, mCancelClickListener);
        }

        public Builder cancelListener(@Nullable View.OnClickListener cancelClickListener) {
            return cancel(mCancelText, cancelClickListener);
        }

        /**
         * 取消按钮的文本和点击事件
         *
         * @param cancelText          取消的文本
         * @param cancelClickListener 点击事件
         * @return
         */
        public Builder cancel(@Nullable CharSequence cancelText, @Nullable View.OnClickListener cancelClickListener) {
            mCancelText = cancelText;
            mCancelClickListener = cancelClickListener;
            return this;
        }

        public UIConfirmDialog build() {
            if (UIConfig.DEBUG) {
                if (TextUtils.isEmpty(mConfirmText)) {
                    throw new IllegalArgumentException("the confirm text must not be empty,you must call method: 'confirm(@NonNull String confirmText)' ");
                }
                if (TextUtils.isEmpty(mContent)) {
                    throw new IllegalArgumentException("the content text must not be empty,you must call method: 'content(@NonNull String content)'  ");
                }
            }
            return new UIConfirmDialog(this);
        }

    }

    public static Builder build(@NonNull Context context) {
        return new Builder(context);
    }

}
