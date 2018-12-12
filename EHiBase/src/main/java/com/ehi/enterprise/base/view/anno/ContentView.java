package com.ehi.enterprise.base.view.anno;

import com.ehi.enterprise.base.view.EHiErrorViewImpl;
import com.ehi.enterprise.base.view.EHiInitViewImpl;
import com.ehi.enterprise.base.view.inter.EHiBaseViewSupport;
import com.ehi.enterprise.base.view.inter.IEHiErrorView;
import com.ehi.enterprise.base.view.inter.IEHiInitView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示一个Activity需要支持的一些操作
 * 这个注解只有继承 {@link EHiBaseActSupport} 或者  的才会起作用
 * 这个注解只有继承 {@link EHiBaseFragmentSupport} 或者  的才会起作用
 *
 * @author : xiaojinzi 30212
 * @desc :
 * @time : 2017/12/15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ContentView {

    /**
     * 标记使用哪一个视图,0不起作用
     *
     * @return
     */
    int value();

    /**
     * 当0的时候不起作用,默认就是整个视图
     * 这个值的作用就是在视图中找到真正的内容视图,在这个上再包一层,用来嵌入空视图和错误页面等等
     * 为有些界面需要定制准备
     *
     * @return
     */
    int contentId() default EHiBaseViewSupport.INVALID;

    /**
     * 初始化视图
     *
     * @return
     */
    Class<? extends IEHiInitView> initView() default EHiInitViewImpl.class;

    /**
     * 错误视图
     *
     * @return
     */
    Class<? extends IEHiErrorView> errorView() default EHiErrorViewImpl.class;

    /**
     * 空状态视图
     * 暂时未实现
     *
     * @return
     */
    boolean emptyView() default false;

}
