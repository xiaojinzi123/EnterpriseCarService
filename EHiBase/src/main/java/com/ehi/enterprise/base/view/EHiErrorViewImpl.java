package com.ehi.enterprise.base.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ehi.enterprise.base.R;
import com.ehi.enterprise.base.view.inter.EHiBaseViewSupport;
import com.ehi.enterprise.base.view.inter.IEHiErrorView;
import com.ehi.tools.TextUtils;

/**
 * time   : 2018/07/10
 *
 * @author : xiaojinzi 30212
 */
public class EHiErrorViewImpl implements IEHiErrorView {

    @Nullable
    private RelativeLayout contentView;

    @Nullable
    private View.OnClickListener retryListener;

    @Override
    @NonNull
    public View createView(Context context) {

        if (contentView == null) {
            contentView = (RelativeLayout) View.inflate(context, R.layout.base_error_view, null);
        }

        return contentView;
    }

    @Override
    public void destory() {
        contentView = null;
        retryListener = null;
    }

    @Override
    public void showErrorView(@NonNull EHiBaseViewSupport.EHiErrorBean eHiErrorBean) {

        if (contentView == null || eHiErrorBean == null) {
            return;
        }

        contentView.setVisibility(View.VISIBLE);

        if (eHiErrorBean.getErrorEnum() == EHiBaseViewSupport.ErrorEnum.NetWorkError) {

            if (contentView.findViewById(R.id.rl_network_error) == null) {
                contentView.removeAllViews();
                View contentView = View.inflate(this.contentView.getContext(), R.layout.base_error_view_for_network, null);
                TextView tv_network_error = contentView.findViewById(R.id.tv_network_error);
                if (!TextUtils.isEmpty(eHiErrorBean.getMsg())) {
                    tv_network_error.setText(eHiErrorBean.getMsg());
                }else {
                    tv_network_error.setText("网络不给力呦");
                }
                this.contentView.addView(
                        contentView,
                        new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
                );
            }
            contentView.findViewById(R.id.rl_network_error).setVisibility(View.VISIBLE);

        } else if (eHiErrorBean.getErrorEnum() == EHiBaseViewSupport.ErrorEnum.ServerError) {

            EHiBaseViewSupport.EHiServerErrorBean serverError = (EHiBaseViewSupport.EHiServerErrorBean) eHiErrorBean;

            View errorItemView = null;

            if ((errorItemView = contentView.findViewById(R.id.rl_server_error)) == null) {
                contentView.removeAllViews();
                errorItemView = View.inflate(contentView.getContext(), R.layout.base_error_view_for_server, null);
                contentView.addView(errorItemView,
                        new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
                );
            }

            contentView.findViewById(R.id.rl_server_error).setVisibility(View.VISIBLE);

            TextView tv_server_code = errorItemView.findViewById(R.id.tv_server_code);
            TextView tv_server_error = errorItemView.findViewById(R.id.tv_server_error);

            tv_server_code.setText(String.valueOf(serverError.getHttpCode()));
            tv_server_error.setText(TextUtils.isEmpty(serverError.getHttpMessage()) ? "真不巧,网页走丢了~~~" : serverError.getHttpMessage());

        } else {

            View errorItemView = null;

            if ((errorItemView = contentView.findViewById(R.id.rl_unknow_error)) == null) {
                contentView.removeAllViews();
                errorItemView = View.inflate(contentView.getContext(), R.layout.base_view_for_unknow, null);
                contentView.addView(
                        errorItemView,
                        new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
                );
            }
            contentView.findViewById(R.id.rl_unknow_error).setVisibility(View.VISIBLE);

            TextView tv_unknow_error = errorItemView.findViewById(R.id.tv_unknow_error);
            tv_unknow_error.setText(TextUtils.isEmpty(eHiErrorBean.getMsg()) ? "未知错误" : eHiErrorBean.getMsg());

        }

        if (retryListener != null) {
            View retryView = contentView.findViewById(R.id.tv_retry);
            if (retryView != null) {
                retryView.setOnClickListener(retryListener);
            }
        }

    }

    @Override
    public void hideErrorView() {
        if (contentView == null) {
            return;
        }
        contentView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setOnRetryListener(@NonNull View.OnClickListener listener) {

        retryListener = listener;

        if (contentView == null) {
            return;
        }

        View retryView = contentView.findViewById(R.id.tv_retry);
        if (retryView != null) {
            retryView.setOnClickListener(retryListener);
        }


    }


}
