package com.ehi.enterprise.base;

import java.io.IOException;

/**
 * Created by xiaojinzi on 19/09/2017.
 * 就是针对后台返回的最外层的格式如果是错误的情况下,把错误的信息封装成为这个异常抛出去
 */
public class EHiBusinessFailureException extends IOException {

    private int errorCode;

    public EHiBusinessFailureException() {
    }

    public EHiBusinessFailureException(String detailMessage, int errerCode) {
        super(detailMessage);
        this.errorCode = errerCode;
    }

    public EHiBusinessFailureException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public EHiBusinessFailureException(Throwable throwable) {
        super(throwable);
    }

    public int getErrorCode() {
        return errorCode;
    }

}
