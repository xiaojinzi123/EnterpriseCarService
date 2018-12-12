package com.ehi.enterprise.base.source;

import com.ehi.datasource.DataSourceApiImpl;

/**
 * 如果需要扩展 DataSource,请自定义自己的 DataSource 并且用注解 {@link com.ehi.datasource.EHiDataSourceAnno} 标记接口
 * time   : 2018/06/29
 *
 * @author : xiaojinzi 30212
 */
public class EHiDataSourceImpl extends DataSourceApiImpl implements EHiDataSource {
}
