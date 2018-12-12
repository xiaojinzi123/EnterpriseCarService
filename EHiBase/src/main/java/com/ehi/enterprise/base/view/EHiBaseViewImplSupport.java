package com.ehi.enterprise.base.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ehi.enterprise.base.BuildConfig;
import com.ehi.enterprise.base.view.anno.ContentView;
import com.ehi.enterprise.base.view.anno.TitleBar;
import com.ehi.enterprise.base.view.inter.EHiBaseViewSupport;
import com.ehi.enterprise.base.view.inter.IEHiErrorView;
import com.ehi.enterprise.base.view.inter.IEHiInitView;
import com.ehi.enterprise.ui.actionbar.UITitleBar;

import java.lang.reflect.Method;

/**
 * time   : 2018/06/28
 *
 * @author : xiaojinzi 30212
 * 解析以下的注解
 * @see ContentView
 * @see TitleBar
 */
public class EHiBaseViewImplSupport extends EHiBaseViewImpl implements EHiBaseViewSupport {

    public EHiBaseViewImplSupport(@Nullable Context context) {
        super(context);
    }

    @Nullable
    protected UITitleBar mTitleBar;

    @Nullable
    protected IEHiErrorView eHiErrorView;

    @Nullable
    protected IEHiInitView eHiInitView;

    /**
     * 布局文件中需要用错误视图和初始化视图包裹的视图,如果null就默认是contentView
     */
    @Nullable
    protected View realContentView;

    /**
     * 读取注解中的信息生成contentView
     *
     * @param mContext
     * @return
     */
    @Nullable
    protected final View getView(@NonNull Context mContext, @NonNull ContentViewConfig contentViewConfig, @NonNull Object targetView) {

        TitleBar titleBar = targetView.getClass().getAnnotation(TitleBar.class);

        LinearLayout ll_root = new LinearLayout(mContext);
        ll_root.setOrientation(LinearLayout.VERTICAL);
        ll_root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mTitleBar = soveTitleBar(mContext, titleBar, targetView);

        // 顶部是标题栏
        if (titleBar != null) {
            ll_root.addView(mTitleBar);
        }

        soveErrorView(contentViewConfig);
        soveInitView(contentViewConfig);

        if (contentViewConfig != null) {

            // 生成了内容的视图
            View contentView = View.inflate(mContext, contentViewConfig.layoutId, null);
            // 如果有错误页面,那么整合错误页面

            if (contentViewConfig.realContentId != INVALID) {
                realContentView = contentView.findViewById(contentViewConfig.realContentId);
            }

            if (realContentView == null) {
                realContentView = contentView;
            }

            // 构建一个新的容器
            RelativeLayout rl = new RelativeLayout(mContext);
            rl.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));

            if (eHiErrorView != null) {
                View errorView = eHiErrorView.createView(mContext);
                errorView.setLayoutParams(new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                ));
                eHiErrorView.hideErrorView();
                rl.addView(errorView);
            }

            if (eHiInitView != null) {
                View initView = eHiInitView.createView(mContext);
                initView.setLayoutParams(new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                ));
                // 初始化的时候不显示的
                eHiInitView.hideInitView();
                rl.addView(initView);
            }

            // 这时候要判断是否是对整个布局内容进行包裹的
            if (realContentView == contentView) {
                realContentView.setLayoutParams(new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                ));
                rl.addView(realContentView);
                ll_root.addView(rl);
            } else {

                // 真正视图的父容器
                ViewGroup realContentViewParent = (ViewGroup) realContentView.getParent();
                int realContentViewIndex = realContentViewParent.indexOfChild(realContentView);

                // 首先从真正的视图的父容器中移除自己
                realContentViewParent.removeView(realContentView);

                // 原来的布局参数复制过来
                rl.setLayoutParams(realContentView.getLayoutParams());
                realContentView.setLayoutParams(new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                ));

                rl.addView(realContentView);
                realContentViewParent.addView(rl, realContentViewIndex);

                ll_root.addView(contentView);

            }
        }

        return ll_root;

    }

    @Override
    public void showEmptyView(@Nullable String msg) {
        toggle(true, false, false, null);
    }

    @Override
    public void hideEmptyView() {
        toggle(false, false, false, null);
    }

    public void showErrorView(@NonNull EHiErrorBean eHiErrorBean) {
        toggle(false, true, false, eHiErrorBean);
    }

    @Override
    public void hideErrorView() {
        toggle(false, false, false, null);
    }

    @Override
    public void showInitView() {
        toggle(false, false, true, null);
    }

    @Override
    public void hideInitView() {
        toggle(false, false, false, null);
    }

    public void toggle(boolean isEmpty, boolean isError, boolean isInit, @Nullable EHiErrorBean eHiErrorBean) {

        boolean isShowContent = true;

        if (isEmpty) {
            // do nothing
        }

        if (isError) {
            isShowContent = false;
            if (eHiErrorView != null && eHiErrorBean != null) {
                eHiErrorView.showErrorView(eHiErrorBean);
            }
        } else {
            if (eHiErrorView != null) {
                eHiErrorView.hideErrorView();
            }
        }

        if (isInit) {
            isShowContent = false;
            if (eHiInitView != null) {
                eHiInitView.showInitView();
            }
        } else {
            if (eHiInitView != null) {
                eHiInitView.hideInitView();
            }
        }

        if (realContentView != null) {
            if (isShowContent) {
                realContentView.setVisibility(View.VISIBLE);
            } else {
                realContentView.setVisibility(View.INVISIBLE);
            }
        }

    }

    @Nullable
    private UITitleBar soveTitleBar(@NonNull final Context mContext, @Nullable final TitleBar titleBar, @NonNull final Object targetView) {

        if (titleBar == null) return null;

        UITitleBar tb = new UITitleBar(mContext);
        tb.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // 处理返回按钮
        int backImgRsd = titleBar.backImgRsd();
        if (backImgRsd == EHiBaseViewSupport.INVALID) {
            backImgRsd = EHiBaseViewSupport.DEFAULT_BACK_IMGRES;
        }

        tb.setBackDrawableRes(backImgRsd);

        // 是否透明化
        tb.setTransparentBg(titleBar.transparentBg());

        // 读取title

        if (!TextUtils.isEmpty(titleBar.value())) {
            tb.setTitle(titleBar.value());
        }

        if (!TextUtils.isEmpty(titleBar.title())) {
            tb.setTitle(titleBar.title());
        }

        int titleRsd = titleBar.titleRsd();
        if (titleRsd != INVALID) {
            tb.setTitle(titleRsd);
        }

        // 处理菜单文本和img

        if (titleBar.menuImgRsd() != INVALID) {
            tb.setMenuVisible(true);
            tb.setMenuImg(titleBar.menuImgRsd());
        } else if (!TextUtils.isEmpty(titleBar.menuText())) {
            tb.setMenuText(titleBar.menuText());
        } else if (titleBar.menuTextRsd() != INVALID) {
            tb.setMenuText(titleBar.menuTextRsd());
        }

        if (titleBar.menu1ImgRsd() != INVALID) {
            tb.setMenu1Visible(true);
            tb.setMenu1Img(titleBar.menu1ImgRsd());
        } else if (!TextUtils.isEmpty(titleBar.menu1Text())) {
            tb.setMenu1Text(titleBar.menu1Text());
        } else if (titleBar.menu1TextRsd() != INVALID) {
            tb.setMenu1Text(titleBar.menu1TextRsd());
        }

        // 处理菜单的文本颜色

        if (titleBar.menuTextColor() != INVALID) {
            tb.setMenuTextColor(titleBar.menuTextColor());
        } else if (titleBar.menuTextColorRsd() != INVALID) {
            tb.setMenuTextColorRes(titleBar.menuTextColorRsd());
        } else if (titleBar.menuTextColorStateList() != INVALID) {
            tb.setMenuTextColorStateList(titleBar.menuTextColorStateList());
        }

        if (titleBar.menu1TextColor() != INVALID) {
            tb.setMenu1TextColor(titleBar.menu1TextColor());
        } else if (titleBar.menu1TextColorRsd() != INVALID) {
            tb.setMenu1TextColorRes(titleBar.menu1TextColorRsd());
        } else if (titleBar.menu1TextColorStateList() != INVALID) {
            tb.setMenu1TextColorStateList(titleBar.menu1TextColorStateList());
        }

        tb.setShowUnderLine(titleBar.showUnderLine());

        // 注册menu事件

        tb.setMenuClickListener(new MenuOnClickListener(titleBar.menuListener(), mContext, targetView, titleBar.menuTargetView()));
        tb.setMenu1ClickListener(new MenuOnClickListener(titleBar.menu1Listener(), mContext, targetView, titleBar.menu1TargetView()));

        return tb;

    }

    private void soveErrorView(@Nullable ContentViewConfig contentView) {
        try {
            eHiErrorView = contentView.errorView.newInstance();
            eHiErrorView.setOnRetryListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onBaseViewListener != null) {
                        onBaseViewListener.onRetryClick();
                    }
                }
            });
        } catch (Exception ignore) {
            if (BuildConfig.DEBUG) {
                throw new RuntimeException("can't sove the error class:" + contentView.errorView);
            }
        }
    }

    private void soveInitView(@Nullable ContentViewConfig contentView) {
        try {
            eHiInitView = contentView.initView.newInstance();
        } catch (Exception ignore) {
            if (BuildConfig.DEBUG) {
                throw new RuntimeException("can't sove the init class:" + contentView.initView);
            }
        }
    }

    private OnBaseViewListener onBaseViewListener;

    public void setOnBaseViewListener(OnBaseViewListener onBaseViewListener) {
        this.onBaseViewListener = onBaseViewListener;
    }

    public interface OnBaseViewListener {

        void onRetryClick();

    }

    private class MenuOnClickListener implements View.OnClickListener {

        @NonNull
        private String menuClickMethodName;
        @NonNull
        private Context context;
        @NonNull
        private Object targetView;
        @NonNull
        private Class targetViewClass;

        public MenuOnClickListener(@NonNull String menuClickMethodName, @NonNull Context context, @NonNull Object targetView, @NonNull Class targetViewClass) {
            this.menuClickMethodName = menuClickMethodName;
            this.context = context;
            this.targetView = targetView;
            this.targetViewClass = targetViewClass;
        }

        @Override
        public void onClick(View v) {
            if (TextUtils.isEmpty(menuClickMethodName)) {
                try {
                    Intent intent = new Intent(context, targetViewClass);
                    context.startActivity(intent);
                } catch (Exception ignore) {
                }
            } else {
                try {
                    Method method = targetView.getClass().getMethod(menuClickMethodName, View.class);
                    method.invoke(targetView, v);
                } catch (NoSuchMethodException e) {
                    if (BuildConfig.DEBUG) { // 如果是测试环境的时候让它崩溃
                        throw new RuntimeException("the method '" + menuClickMethodName + "(View)' in " + targetView.getClass().getSimpleName() + " cannot be found,is declared with 'public' ?");
                    } else {
                        // ignore
                    }
                } catch (Exception e) {
                    if (BuildConfig.DEBUG) { // 如果是测试环境的时候让它崩溃
                        throw new RuntimeException("the method '" + menuClickMethodName + "(View)' in " + targetView.getClass().getSimpleName() + " is fail to invoke");
                    } else {
                        // ignore
                    }
                }
            }
        }

    }

}
