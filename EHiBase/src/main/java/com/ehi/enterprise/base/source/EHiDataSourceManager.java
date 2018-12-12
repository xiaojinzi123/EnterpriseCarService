package com.ehi.enterprise.base.source;

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