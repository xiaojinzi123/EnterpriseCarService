package com.ehi.enterprise.base.view.inter;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ehi.enterprise.base.EHiBusinessFailureException;

/**
 * 因为每一个视图都是互斥的.所有显示其中的一个都会隐藏其他的
 *
 * @author : xiaojinzi 30212
 * <p>
 */
@MainThread
public interface EHiBaseViewSupport extends EHiBaseView {

    /**
     * 表示无效的
     */
    int INVALID = 0;

    /**
     * 默认的返回按钮
     */
    int DEFAULT_BACK_IMGRES = com.ehi.enterprise.resource.R.drawable.resource_back_icon1;

    /**
     * View 层尽管定接口给 P 层使用,只要 View层下面实现就行, P 只管调用
     */
    enum ErrorEnum {
        // 默认支持
        Unknow,
        // 网络错误也支持
        NetWorkError,
        // 服务端错误(暂未支持)
        ServerError,
        // 表示请求是成功了,但是后台告诉我们这是一个处理错误的请求
        BusinessFailure,
        // 参数错误(暂未支持)
        IllegalArgumentError
    }

    /**
     * show an init view with animation
     */
    void showInitView();

    /**
     * hide the init view
     */
    void hideInitView();

    /**
     * 显示错误视图
     *
     * @param eHiErrorBean
     */
    void showErrorView(@NonNull EHiErrorBean eHiErrorBean);

    /**
     * hide the error view
     */
    void hideErrorView();

    /**
     * show an empty view
     */
    void showEmptyView(@Nullable String msg);

    /**
     * hide the empty view
     */
    void hideEmptyView();

    class EHiErrorBean {

        // 错误的类型
        private EHiBaseViewSupport.ErrorEnum errorEnum;

        // 错误的提示
        protected String msg;

        public EHiErrorBean() {
            errorEnum = getErrorEnumImpl();
        }

        protected ErrorEnum getErrorEnumImpl() {
            return ErrorEnum.Unknow;
        }

        public ErrorEnum getErrorEnum() {
            return errorEnum;
        }

        public String getMsg() {
            return msg;
        }

        public EHiErrorBean setMsg(String msg) {
            this.msg = msg;
            return this;
        }

    }

    class EHiNetworkErrorBean extends EHiErrorBean {

        protected ErrorEnum getErrorEnumImpl() {
            return ErrorEnum.NetWorkError;
        }

    }

    class EHiServerErrorBean extends EHiErrorBean {

        protected ErrorEnum getErrorEnumImpl() {
            return ErrorEnum.ServerError;
        }

        private int httpCode;

        private String httpMessage;

        public EHiServerErrorBean(int httpCode, String httpMessage) {
            this.httpCode = httpCode;
            this.httpMessage = httpMessage;
        }

        public int getHttpCode() {
            return httpCode;
        }

        public void setHttpCode(int httpCode) {
            this.httpCode = httpCode;
        }

        public String getHttpMessage() {
            return httpMessage;
        }

        public void setHttpMessage(String httpMessage) {
            this.httpMessage = httpMessage;
        }
    }

    class EHiBusinessFailureError extends EHiErrorBean {

        private int errorCode;

        private String errorMessage;

        protected ErrorEnum getErrorEnumImpl() {
            return ErrorEnum.BusinessFailure;
        }

        public EHiBusinessFailureError(int errorCode, String errorMessage) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }

        public EHiBusinessFailureError(EHiBusinessFailureException e) {
            this.errorCode = e.getErrorCode();
            this.errorMessage = e.getMessage();
        }
    }

}
