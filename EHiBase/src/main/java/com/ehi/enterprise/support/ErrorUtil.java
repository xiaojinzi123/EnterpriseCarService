package com.ehi.enterprise.support;

import android.support.annotation.NonNull;

import java.net.ConnectException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import javax.net.ssl.SSLException;

import retrofit2.HttpException;

/**
 * @author : xiaojinzi 30212
 * @desc :
 * @time : 2017/12/06
 */
public class ErrorUtil {

    private static Class[] netWorkFlags = {
            // 超时
            SocketTimeoutException.class,
            // 不知道主机
            UnknownHostException.class,
            // socket 问题
            SocketException.class,
            // 子类 NoRouteToHostException.class
            ConnectException.class,
            // SSL 连接的异常
            SSLException.class,
            // 子类 SSLHandshakeException.class
            // 协议异常
            ProtocolException.class,
            UnknownServiceException.class

    };

    private static Class[] serverError = {
            HttpException.class,
    };

    /**
     * 判断一个错误是不是网络错误,客户端错误
     *
     * @param t
     * @return
     */
    public static boolean isNetWorkError(@NonNull Throwable t) {

        for (Class flag: netWorkFlags) {
            if (t != null && flag.getName().equals(t.getClass().getName())) {
                return true;
            }
        }

        return false;

    }

    /**
     * 判断一个错误是不是服务端错误
     *
     * @param t
     * @return
     */
    public static boolean isServerError(@NonNull Throwable t) {

        for (Class flag: serverError) {

            if (t != null && flag.getName().equals(t.getClass().getName())) {
                return true;
            }

        }
        return false;

    }

    /**
     * 判断一个错误是不是服务端超时
     *
     * @param t
     * @return
     */
    public static boolean isServerTimeoutError(@NonNull Throwable t) {
        if (t != null && SocketTimeoutException.class.getName().equals(t.getClass().getName())) {
            return true;
        }
        return false;
    }


    public static boolean isUIUpdateException(@NonNull Throwable t){

        if("Only the original thread that created a view hierarchy can touch its views.".equals(t.getMessage())) {
            return true;
        }

        return false;

    }


}
