package cn.dagongniu.bitman.main.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.dagongniu.bitman.R;
import cn.dagongniu.bitman.main.bean.IndexPageBean;


/**
 * 市场搜索历史适配器
 */
public class SearchCoinHistoryAdapter extends BaseQuickAdapter<IndexPageBean.DataBean.AllMaketListBean.MarketListBean, BaseViewHolder> {


    Context context;

    public SearchCoinHistoryAdapter(Context context) {
        super(R.layout.search_coin_history_item_layout);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, IndexPageBean.DataBean.AllMaketListBean.MarketListBean data) {

        //-------
        //交易对   币种getCoinName/市场getMarketCoinName
        helper.setText(R.id.tv_market_name, data.getCoinName() + "/" + data.getMarketCoinName());




    }

}
