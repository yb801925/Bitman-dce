package cn.dagongniu.bitman.trading.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;

import cn.dagongniu.bitman.R;
import cn.dagongniu.bitman.constant.SPConstant;
import cn.dagongniu.bitman.trading.bean.OrdersRecordBean;
import cn.dagongniu.bitman.utils.SPUtils;

/**
 * 订单 fragment适配器
 */
public class OrderRecordAdapter extends BaseQuickAdapter<OrdersRecordBean.DataBean.ListBean, BaseViewHolder> {

    Context context;

    public OrderRecordAdapter(Context context) {
        super(R.layout.entrust_order_item_layout);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrdersRecordBean.DataBean.ListBean item) {

        helper.setVisible(R.id.bt_item_cz, false);

        //时间
        helper.setText(R.id.tv_item_time, item.getCreateTime());

        //币种
        helper.setText(R.id.tv_ent_jyd, item.getMarketName());

        //价格
        helper.setText(R.id.tv_ent_jg, item.getPrice().setScale(item.getPriceDecimal(), BigDecimal.ROUND_DOWN).toPlainString());

        //数量
        helper.setText(R.id.tv_ent_count, item.getQty().setScale(item.getQtyDecimal(), BigDecimal.ROUND_DOWN).toPlainString());

        //状态
        switch (item.getType()) {
            case 1://买入
                helper.setText(R.id.tv_item_cz, context.getString(R.string.entrust_order_buy));
                helper.setTextColor(R.id.tv_item_cz, context.getResources().getColor(R.color.bule));
                helper.setTextColor(R.id.tv_ent_jg, context.getResources().getColor(R.color.bule));
                break;
            case 2://卖出
                helper.setText(R.id.tv_item_cz, context.getString(R.string.entrust_order_sell));
                helper.setTextColor(R.id.tv_item_cz, context.getResources().getColor(R.color.yellow5));
                helper.setTextColor(R.id.tv_ent_jg, context.getResources().getColor(R.color.yellow5));
                break;
            default:
                helper.setText(R.id.tv_item_cz, context.getString(R.string.unknown));
                helper.setTextColor(R.id.tv_item_cz, context.getResources().getColor(R.color.df_font_hui));
                helper.setTextColor(R.id.tv_ent_jg, context.getResources().getColor(R.color.df_font_hui));
                break;
        }
        String LANGUAGE = (String) SPUtils.getParam(mContext, SPConstant.LANGUAGE, "");
        if (LANGUAGE.equalsIgnoreCase("en") || LANGUAGE.equalsIgnoreCase("fr") || LANGUAGE.equalsIgnoreCase("th")) {
            helper.setImageDrawable(R.id.im_liang, context.getResources().getDrawable(R.drawable.liang_log_en));
        } else {
            helper.setImageDrawable(R.id.im_liang, context.getResources().getDrawable(R.drawable.liang_log));
        }


    }

}
