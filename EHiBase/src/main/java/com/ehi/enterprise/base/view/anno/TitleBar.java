package com.ehi.enterprise.base.view.anno;

import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.ehi.enterprise.base.view.inter.EHiBaseViewSupport;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标题栏的注解,这个注解只有继承{@link EHiBaseActSupport} 或者
 * {@link EHiBaseFragmentSupport} 才会起作用
 *
 * @author : xiaojinzi 30212
 * @see com.ehai.base.view.EHiBaseActSupport
 * @see com.ehai.base.view.EHiBaseFragmentSupport
 * <p>
 * implemented by {@link com.ehi.enterprise.base.view.EHiBaseViewImplSupport}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TitleBar {

    /**
     * 标题栏的标题
     *
     * @return
     */
    String value() default "";

    /**
     * 返回图标的资源id
     * 默认的值请看 {@link EHiBaseViewSupport#DEFAULT_BACK_IMGRES}
     *
     * @return
     */
    @DrawableRes int backImgRsd() default EHiBaseViewSupport.INVALID;

    /**
     * 标题栏的标题
     *
     * @return
     */
    String title() default "";

    /**
     * 标题栏的标题的资源id
     *
     * @return
     */
    @StringRes int titleRsd() default EHiBaseViewSupport.INVALID;

    /**
     * 菜单的文本
     *
     * @return
     */
    String menuText() default "";

    /**
     * 菜单的文本
     *
     * @return
     */
    String menu1Text() default "";

    /**
     * 菜单的颜色
     *
     * @return
     */
    @ColorInt int menuTextColor() default EHiBaseViewSupport.INVALID;

    /**
     * 菜单的颜色资源
     *
     * @return
     */
    @ColorRes int menuTextColorRsd() default EHiBaseViewSupport.INVALID;

    /**
     * 菜单的颜色
     *
     * @return
     */
    @ColorInt int menu1TextColor() default EHiBaseViewSupport.INVALID;

    /**
     * 菜单的颜色资源
     *
     * @return
     */
    @ColorRes int menu1TextColorRsd() default EHiBaseViewSupport.INVALID;

    /**
     * 菜单的颜色选择器
     *
     * @return
     */
    @ColorRes int menuTextColorStateList() default EHiBaseViewSupport.INVALID;

    /**
     * 菜单的颜色选择器
     *
     * @return
     */
    @ColorRes int menu1TextColorStateList() default EHiBaseViewSupport.INVALID;

    /**
     * 菜单的文本的资源id
     *
     * @return
     */
    int menuTextRsd() default EHiBaseViewSupport.INVALID;

    /**
     * 菜单的文本的资源id
     *
     * @return
     */
    int menu1TextRsd() default EHiBaseViewSupport.INVALID;

    /**
     * 菜单的图片的资源id
     *
     * @return
     */
    int menuImgRsd() default EHiBaseViewSupport.INVALID;

    /**
     * 菜单的图片的资源id
     *
     * @return
     */
    int menu1ImgRsd() default EHiBaseViewSupport.INVALID;

    /**
     * 菜单的点击事件执行的方法名称,方法名字可以任意指定,内部的参数可以是View也可以没有
     *
     * @return
     */
    String menuListener() default "";

    /**
     * 菜单的点击事件执行的方法名称,方法名字可以任意指定,内部的参数可以是View也可以没有
     *
     * @return
     */
    String menu1Listener() default "";

    /**
     * 菜单点击之后跳转的界面,仅限无参数的
     *
     * @return
     */
    Class<?> menuTargetView() default Void.class;

    /**
     * 菜单点击之后跳转的界面,仅限无参数的
     *
     * @return
     */
    Class<?> menu1TargetView() default Void.class;

    /**
     * 是否适配状态栏的高度,如果是就标题栏会撑出一个状态栏高度的top-padding
     *
     * @return
     */
    boolean fitsSystem() default true;

    /**
     * 是否背景是透明的
     *
     * @return
     */
    boolean transparentBg() default false;

    /**
     * 是否显示下边的线条
     *
     * @return
     */
    boolean showUnderLine() default false;

}
