package com.ehi.enterprise;

import com.ehi.enterprise.base.source.EHiDataSource;
import com.ehi.enterprise.base.source.EHiDataSourceImpl;

/**
 * Datasource 的单例维护类
 */
public class EHiDataSourceManager {

    public static EHiDataSource getDataSource() {
        return Holder.INSTANCE.dataSource;
    }

    private enum Holder {
        INSTANCE;
        private EHiDataSource dataSource = new EHiDataSourceImpl();
    }

}