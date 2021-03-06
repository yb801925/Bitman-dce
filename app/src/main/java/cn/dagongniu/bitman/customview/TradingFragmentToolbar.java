package cn.dagongniu.bitman.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.dagongniu.bitman.R;

/**
 * 自定义 交易标题控件
 */

public class TradingFragmentToolbar extends Toolbar {

    @BindView(R.id.im_left)
    ImageView im_left;
    @BindView(R.id.tv_left_name)
    TextView tv_left_name;
    @BindView(R.id.tv_right)
    TextView tv_right;


    public TradingFragmentToolbar(Context context) {
        this(context, null);
    }

    public TradingFragmentToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TradingFragmentToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View v = LayoutInflater.from(context).inflate(R.layout.toolbar_trading_fragment_layout, this, false);
        addView(v);
        ButterKnife.bind(this, v);
    }


    /**
     * 右边文字是否显示
     * true = 隐藏   iv_false = 显示
     *
     * @param visibility
     */
    public void setRightTvVisibility(boolean visibility) {
        if (visibility)
            tv_right.setVisibility(GONE);
        else
            tv_right.setVisibility(VISIBLE);
    }

    public void setPadding(int padding) {
        im_left.setPadding(padding, padding, padding, padding);
    }


    /**
     * 左边图标是否显示
     * true = 隐藏   iv_false = 显示
     *
     * @param visibility
     */
    public void setLeftImgVisibility(boolean visibility) {
        if (visibility)
            im_left.setVisibility(GONE);
        else
            im_left.setVisibility(VISIBLE);
    }

    public void setLeftImgDroable(Context context) {
        im_left.setImageDrawable(context.getResources().getDrawable(R.mipmap.trading_line_icon));

    }

    /**
     * 左边图片设置
     *
     * @param drawable
     */
    public void setLeftImg(Drawable drawable) {
        im_left.setImageDrawable(drawable);
    }

    /**
     * 左边事件
     *
     * @param listener
     */
    public void setLeftMoreClickListener(OnClickListener listener) {
        im_left.setOnClickListener(listener);
    }


//    /**
//     * title 事件
//     *
//     * @param listener
//     */
    public void setNameClickListener(OnClickListener listener) {
        tv_left_name.setOnClickListener(listener);
    }



    /**
     * 右边文字事件
     *
     * @param listener
     */
    public void setRightTvClickListener(OnClickListener listener) {
        tv_right.setOnClickListener(listener);
    }




    /**
     * 设置右边颜色
     *
     * @param color
     */
    public void setRightTvColor(int color) {
        tv_right.setTextColor(color);
    }


//    /**
//     * 设置名称
//     *
//     * @param c
//     */
    public void setTitleNameText(int c) {
        tv_left_name.setText(c);
    }

//    /**
//     * 设置名称
//     *
//     * @param title
//     */
    public void setTitleNameText(String title) {
        tv_left_name.setText(title);
    }



    /**
     * 设置右边字
     *
     * @param c
     */
    public void setRightNameText(int c) {
        tv_right.setText(c);
    }
}
