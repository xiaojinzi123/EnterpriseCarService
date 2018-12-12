package com.ehi.datasource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来标识一个 DataSource 层的注解,最后都会被整合到一个最终的 DataSource 中
 * 每一个 DataSource 没法用Class属性指向实现类,只能使用全类名来指向,所以重构的时候,记得搜索出来的这些字符串也要更改
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface EHiDataSourceAnno {

    /**
     * 生成的方法的前缀,用于区分
     *
     * @return†
     */
    String value() default "";

    /**
     * 用于内部区分,不能为相同的
     *
     * @return
     */
    String uniqueCode();

    /**
     * 实现类,会被加入到缓存中
     */
    Class impl() default Void.class;

    /**
     * 如果实现类不是直接创建的,那么传入调用的方式
     *
     * @return
     */
    String callPath() default "";

}
