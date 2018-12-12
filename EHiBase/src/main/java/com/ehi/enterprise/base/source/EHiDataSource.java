package com.ehi.enterprise.base.source;

import android.support.annotation.Keep;

import com.ehi.datasource.DataSourceApi;

/**
 * 这个类其实可有可无,但是生成的那个类说不定什么时候就改了包名或者类名啥的,不好对付,所以这里写一个继承他,兼容改变
 * time   : 2018/06/29
 * <p>
 *
 * @see EHiDataSourceManager
 * @author : xiaojinzi 30212
 */
@Keep
public interface EHiDataSource extends DataSourceApi {
}
