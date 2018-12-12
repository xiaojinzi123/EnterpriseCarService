package com.ehi.enterprise.ui.actionbar;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ehi.enterprise.tools.ResourceUtil;
import com.ehi.enterprise.tools.ScreenUtil;
import com.ehi.enterprise.ui.R;

/**
 * 这个标题栏默认现在是白色的背景,菜单和返回按钮都是灰黑色的样式的
 *
 * @author xiaojinzi
 */
public class UITitleBar extends RelativeLayout {

    public UITitleBar(Context context) {
        this(context, null);
    }

    public UITitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UITitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Nullable
    private OnClickListener backClickListener;
    @Nullable
    private OnClickListener menuClickListener;
    @Nullable
    private OnClickListener menuClickListener1;

    private RelativeLayout rl_titlebar_container;

    private ImageView iv_back;
    private TextView tv_title;

    // 菜单1
    private RelativeLayout rl_menu;
    private TextView tv_menu;
    private ImageView iv_menu;

    // 菜单2
    private RelativeLayout rl_menu1;
    private TextView tv_menu1;
    private ImageView iv_menu1;

    private View view_under_line;

    // 是否背景是透明的
    private boolean isTransparentBg;

    // 是否返回按钮是看不见的
    private boolean backImgVisiable;

    private boolean isShowUnderLine;

    /**
     * 初始化
     *
     * @param context
     */
    private void init(final Context context, AttributeSet attrs) {

        // 读取自定义的属性----------------start

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.UITitleBar);

        isTransparentBg = a.getBoolean(R.styleable.UITitleBar_ehiBgTransparent, false);
        backImgVisiable = a.getBoolean(R.styleable.UITitleBar_ehiBackImgVisiable, true);
        isShowUnderLine = a.getBoolean(R.styleable.UITitleBar_ehiShowUnderline, false);

        Drawable menuDrawable = a.getDrawable(R.styleable.UITitleBar_ehiMenu1Img);
        String menuText = a.getString(R.styleable.UITitleBar_ehiMenuText);
        int menuTextColor = a.getColor(R.styleable.UITitleBar_ehiMenuTextColor, getMenuTextColor());
        ColorStateList menuTextColorStateList = a.getColorStateList(R.styleable.UITitleBar_ehiMenuTextColor);
        if (menuTextColorStateList == null) {
            menuTextColorStateList = getMenuTextColorStateList();
        }
        int menuPadding = a.getDimensionPixelSize(R.styleable.UITitleBar_ehiMenuImgPadding, getResources().getDimensionPixelOffset(R.dimen.ui_titlebar_iv_menu_padding));

        Drawable menu1Drawable = a.getDrawable(R.styleable.UITitleBar_ehiMenu1Img);
        String menu1Text = a.getString(R.styleable.UITitleBar_ehiMenu1Text);
        int menu1TextColor = a.getColor(R.styleable.UITitleBar_ehiMenu1TextColor, getMenuTextColor());
        ColorStateList menu1TextColorStateList = a.getColorStateList(R.styleable.UITitleBar_ehiMenu1TextColor);
        if (menu1TextColorStateList == null) {
            menu1TextColorStateList = getMenuTextColorStateList();
        }
        int menu1Padding = a.getDimensionPixelSize(R.styleable.UITitleBar_ehiMenu1ImgPadding, getResources().getDimensionPixelOffset(R.dimen.ui_titlebar_iv_menu_padding));

        String title = a.getString(R.styleable.UITitleBar_ehiTitle);

        boolean isFitsystem = a.getBoolean(R.styleable.UITitleBar_ehiFitsystem, true);

        a.recycle();

        // 读取自定义的属性----------------end

        removeAllViews();

        // 创建标题栏的视图并且找到所有的控件-------start

        View view = View.inflate(context, R.layout.ui_titlebar, null);

        rl_titlebar_container = view.findViewById(R.id.rl_titlebar_container);
        iv_back = view.findViewById(R.id.iv_back);
        tv_title = view.findViewById(R.id.tv_title);

        rl_menu = view.findViewById(R.id.rl_menu);
        tv_menu = view.findViewById(R.id.tv_menu);
        iv_menu = view.findViewById(R.id.iv_menu);

        rl_menu1 = view.findViewById(R.id.rl_menu1);
        tv_menu1 = view.findViewById(R.id.tv_menu1);
        iv_menu1 = view.findViewById(R.id.iv_menu1);

        view_under_line = view.findViewById(R.id.view_under_line);

        // 创建标题栏的视图并且找到所有的控件-------end

        // 对视图上样式进行调整-------start

        Drawable backDrawable = getBackDrawable();
        int titleTextColor = getTitleTextColor();

        if (isFitsystem) {
            rl_titlebar_container.setPadding(rl_titlebar_container.getPaddingLeft(), getStatusHeight(), rl_titlebar_container.getPaddingRight(), rl_titlebar_container.getPaddingBottom());
        }

        // 处理背景
        fitBg();

        // 处理返回图标
        fitBackView(backDrawable);

        // 处理标题

        tv_title.setTextColor(titleTextColor);
        if (title != null) {
            tv_title.setText(title);
        }

        // 处理菜单
        if (menuTextColorStateList == null) {
            tv_menu.setTextColor(menuTextColor);
        } else {
            tv_menu.setTextColor(menuTextColorStateList);
        }
        if (menu1TextColorStateList == null) {
            tv_menu1.setTextColor(menu1TextColor);
        } else {
            tv_menu1.setTextColor(menu1TextColorStateList);
        }

        iv_menu.setPadding(menuPadding, menuPadding, menuPadding, menuPadding);
        iv_menu1.setPadding(menu1Padding, menu1Padding, menu1Padding, menu1Padding);

        if (menuDrawable == null && TextUtils.isEmpty(menuText)) {
            setMenuVisible(false);
        } else {
            setMenuVisible(true);
            if (menuDrawable != null) {
                iv_menu.setImageDrawable(menuDrawable);
            }
            if (!TextUtils.isEmpty(menuText)) {
                tv_menu.setText(menuText);
            }
        }

        if (menu1Drawable == null && TextUtils.isEmpty(menu1Text)) {
            setMenu1Visible(false);
        } else {
            setMenu1Visible(true);
            if (menu1Drawable != null) {
                iv_menu1.setImageDrawable(menu1Drawable);
            }
            if (!TextUtils.isEmpty(menu1Text)) {
                tv_menu1.setText(menu1Text);
            }
        }

        view_under_line.setVisibility(isShowUnderLine ? VISIBLE : GONE);

        // 对视图上样式进行调整-------end

        soveListener(context);

        addView(view);

    }

    private void soveListener(final Context context) {

        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (backClickListener != null) {
                    backClickListener.onClick(v);
                } else {
                    if (context instanceof Activity) {
                        ((Activity) context).finish(); // default behavior
                    }
                }
            }
        });

        tv_menu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuClickListener != null) {
                    menuClickListener.onClick(v);
                }
            }
        });

        tv_menu1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuClickListener1 != null) {
                    menuClickListener1.onClick(v);
                }
            }
        });

        iv_menu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuClickListener != null) {
                    menuClickListener.onClick(v);
                }
            }
        });

        iv_menu1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuClickListener1 != null) {
                    menuClickListener1.onClick(v);
                }
            }
        });

    }

    // ------------------ 样式的处理-------------start

    protected Drawable getBgDrawable() {
        return ResourceUtil.getDrawable(getResources(), R.drawable.ui_titlebar_bg);
    }

    protected Drawable getBackDrawable() {
        return ResourceUtil.getDrawable(getResources(), R.drawable.resource_back_icon1);
    }

    @ColorInt
    protected int getTitleTextColor() {
        return ResourceUtil.getColor(R.color.ui_titlebar_text);
    }

    @ColorInt
    protected int getMenuTextColor() {
        return ResourceUtil.getColor(R.color.ui_titlebar_text);
    }

    @Nullable
    protected ColorStateList getMenuTextColorStateList() {

        int[][] states = new int[2][];
        int[] colors = new int[2];

        states[0] = new int[]{-android.R.attr.state_enabled};
        states[1] = new int[]{};

        colors[0] = ResourceUtil.getColor(R.color.ui_titlebar_text_disable);
        colors[1] = getMenuTextColor();

        ColorStateList colorStateList = new ColorStateList(states, colors);

        return colorStateList;
    }

    // ------------------ 样式的处理-------------end

    // ------------------ 设置监听 ------------------ start

    public void setBackClickListener(@Nullable OnClickListener backClickListener) {
        this.backClickListener = backClickListener;
    }

    public void setMenuClickListener(@Nullable OnClickListener menuClickListener) {
        this.menuClickListener = menuClickListener;
    }

    public void setMenu1ClickListener(@Nullable OnClickListener menuClickListener) {
        this.menuClickListener1 = menuClickListener;
    }

    // ------------------ 设置监听 ------------------ end

    // ------------------ 设置title ------------------ start

    public void setTitle(@Nullable String title) {
        if (title == null) return;

        tv_title.setText(title);
    }

    public void setTitle(@StringRes int id) {
        tv_title.setText(id);
    }

    @Nullable
    public String getTitle() {
        if (tv_title == null) {
            return null;
        }
        return tv_title.getText().toString();
    }

    // ------------------ 设置title ------------------ end

    // ------------------ 设置menu text ------------------ start

    public void setMenuText(String menuText) {
        if (menuText == null) return;
        setMenuVisible(true);
        tv_menu.setText(menuText);
    }

    public void setMenuText(@StringRes int menuText) {
        setMenuVisible(true);
        tv_menu.setText(menuText);
    }

    public void setMenu1Text(String menuText) {
        if (menuText == null) return;
        setMenu1Visible(true);
        tv_menu1.setText(menuText);
    }

    public void setMenu1Text(@StringRes int menuText) {
        setMenu1Visible(true);
        tv_menu1.setText(menuText);
    }

    // ------------------ 设置menu text ------------------ end


    // ------------------ 设置menu color ------------------ start

    public void setMenuTextColor(@ColorInt int textColor) {
        tv_menu.setTextColor(textColor);
    }

    public void setMenuTextColorRes(@ColorRes int textColorRes) {
        tv_menu.setTextColor(ResourceUtil.getColor(textColorRes));
    }

    public void setMenuTextColorStateList(@ColorRes int textColorRes) {
        tv_menu.setTextColor(ResourceUtil.getColorStateList(textColorRes));
    }

    public void setMenu1TextColor(@ColorInt int textColor) {
        tv_menu1.setTextColor(textColor);
    }

    public void setMenu1TextColorRes(@ColorRes int textColorRes) {
        tv_menu1.setTextColor(ResourceUtil.getColor(textColorRes));
    }

    public void setMenu1TextColorStateList(@ColorRes int textColorRes) {
        tv_menu1.setTextColor(ResourceUtil.getColorStateList(textColorRes));
    }

    // ------------------ 设置menu color ------------------ end


    public void setMenuImg(@DrawableRes int menuImgRsd) {
        iv_menu.setImageResource(menuImgRsd);
    }

    public void setMenu1Img(@DrawableRes int menuImgRsd) {
        iv_menu1.setImageResource(menuImgRsd);
    }

    public void setBackDrawable(Drawable backDrawable) {
        fitBackView(backDrawable);
    }

    public void setBackDrawableRes(@DrawableRes int backDrawable) {
        fitBackView(ResourceUtil.getDrawable(backDrawable));
    }

    public void setBackVisible(boolean b) {
        if (b) {
            iv_back.setVisibility(VISIBLE);
        } else {
            iv_back.setVisibility(INVISIBLE);
        }
    }

    public void setMenuVisible(boolean b) {
        if (b) {
            rl_menu.setVisibility(VISIBLE);
        } else {
            rl_menu.setVisibility(GONE);
        }
    }

    public void setMenu1Visible(boolean b) {
        if (b) {
            rl_menu1.setVisibility(VISIBLE);
        } else {
            rl_menu1.setVisibility(GONE);
        }
    }

    public void setMenuEnabled(boolean b) {
        tv_menu.setEnabled(b);
        iv_menu.setEnabled(b);
    }

    public void setMenu1Enabled(boolean b) {
        tv_menu1.setEnabled(b);
        iv_menu1.setEnabled(b);
    }

    public boolean isTransparentBg() {
        return isTransparentBg;
    }

    public void setTransparentBg(boolean transparentBg) {
        isTransparentBg = transparentBg;
        fitBg();
    }

    public boolean isShowUnderLine() {
        return isShowUnderLine;
    }

    public void setShowUnderLine(boolean showUnderLine) {
        isShowUnderLine = showUnderLine;
        view_under_line.setVisibility(isShowUnderLine ? VISIBLE : GONE);
    }

    /**
     * 背景的处理
     */
    private void fitBg() {
        if (isTransparentBg) {
            rl_titlebar_container.setBackgroundColor(Color.TRANSPARENT);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                rl_titlebar_container.setBackground(getBgDrawable());
            } else {
                rl_titlebar_container.setBackgroundDrawable(getBgDrawable());
            }

        }
    }

    private void fitBackView(Drawable backDrawable) {

        if (backImgVisiable) {
            iv_back.setVisibility(VISIBLE);
            if (backDrawable == null) {
                iv_back.setVisibility(INVISIBLE);
            } else {
                iv_back.setImageDrawable(backDrawable);
            }
        } else {
            iv_back.setVisibility(INVISIBLE);
        }
    }

    public int dpToPx(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(1, dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * 获得状态栏的高度
     */
    private int getStatusHeight() {
        return ScreenUtil.getStatusHeight();
    }

}
