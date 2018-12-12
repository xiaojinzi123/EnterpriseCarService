package com.ehi.enterprise.base.source;

import android.support.annotation.NonNull;

import com.ehi.datasource.EHiDataSourceAnno;

/**
 * time   : 2018/10/21
 *
 * @author : xiaojinzi 30212
 */
@EHiDataSourceAnno(value = "memoryStorage", uniqueCode = "memoryStorage", impl = EHiMemoryStorageDataSourceImpl.class)
public interface EHiMemoryStorageDataSource {

    void save(@NonNull String key, @NonNull Object value);

    int intValue(@NonNull String key);

    int intValue(@NonNull String key, int defaultValue);

    long longValue(@NonNull String key);

    long longValue(@NonNull String key, long defaultValue);

    float floatValue(@NonNull String key);

    float floatValue(@NonNull String key, float defaultValue);

    double doubleValue(@NonNull String key);

    double doubleValue(@NonNull String key, double defaultValue);

    boolean booleanValue(@NonNull String key);

    boolean booleanValue(@NonNull String key, boolean defaultValue);

    String stringValue(@NonNull String key);

    String stringValue(@NonNull String key, String defaultValue);

}
