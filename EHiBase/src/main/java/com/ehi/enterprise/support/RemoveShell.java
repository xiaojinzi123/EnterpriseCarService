package com.ehi.enterprise.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xiaojinzi on 13/11/2017 10:03 AM
 * <p>
 * see {@link com.ehai.network.retrofit.EHaiGsonConverterFactory} and {@link com.ehai.network.retrofit.EHaiGsonResponseBodyConverter}
 * <p>
 * it means the data will be soved,you can get the real data
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RemoveShell {
}
