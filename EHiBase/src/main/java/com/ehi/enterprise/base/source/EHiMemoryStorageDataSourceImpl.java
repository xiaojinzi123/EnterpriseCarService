package com.ehi.enterprise.base.source;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 内存上存储的DataSource,不适合存放大数据
 * <p>
 * time   : 2018/10/21
 *
 * @author : xiaojinzi 30212
 */
public class EHiMemoryStorageDataSourceImpl implements EHiMemoryStorageDataSource {

    private Map<String, Object> map = Collections.synchronizedMap(new HashMap<String, Object>());

    public void save(@NonNull String key, @NonNull Object value) {

        map.put(key, value);

    }

    public int intValue(@NonNull String key) {
        return intValue(key, 0);
    }

    @Override
    public int intValue(@NonNull String key, int defaultValue) {
        try {
            return (int) map.get(key);
        } catch (Exception ignore) {
            return defaultValue;
        }
    }

    public long longValue(@NonNull String key) {
        return longValue(key, 0l);
    }

    @Override
    public long longValue(@NonNull String key, long defaultValue) {
        try {
            return (long) map.get(key);
        } catch (Exception ignore) {
            return defaultValue;
        }
    }

    public float floatValue(@NonNull String key) {
        return floatValue(key, 0f);
    }

    @Override
    public float floatValue(@NonNull String key, float defaultValue) {
        try {
            return (float) map.get(key);
        } catch (Exception ignore) {
            return defaultValue;
        }
    }

    public double doubleValue(@NonNull String key) {
        return doubleValue(key, 0d);
    }

    @Override
    public double doubleValue(@NonNull String key, double defaultValue) {
        try {
            return (double) map.get(key);
        } catch (Exception ignore) {
            return defaultValue;
        }
    }

    public boolean booleanValue(@NonNull String key) {
        return booleanValue(key, false);
    }

    @Override
    public boolean booleanValue(@NonNull String key, boolean defaultValue) {
        try {
            return (boolean) map.get(key);
        } catch (Exception ignore) {
            return defaultValue;
        }
    }

    public String stringValue(@NonNull String key) {
        return stringValue(key, null);
    }

    @Override
    public String stringValue(@NonNull String key, String defaultValue) {
        try {
            return (String) map.get(key);
        } catch (Exception ignore) {
            return defaultValue;
        }
    }

}
