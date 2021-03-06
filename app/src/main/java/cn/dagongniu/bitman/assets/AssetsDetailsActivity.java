package cn.dagongniu.bitman.assets;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dagongniu.bitman.OAXApplication;
import cn.dagongniu.bitman.R;
import cn.dagongniu.bitman.assets.adapter.PropertyShowAdapter;
import cn.dagongniu.bitman.assets.bean.PropertyShowBean;
import cn.dagongniu.bitman.assets.presenter.PropertyShowPresenter;
import cn.dagongniu.bitman.assets.view.IPropertyShowView;
import cn.dagongniu.bitman.base.BaseActivity;
import cn.dagongniu.bitman.constant.Constant;
import cn.dagongniu.bitman.constant.UMConstant;
import cn.dagongniu.bitman.customview.MyTradingToolbar;
import cn.dagongniu.bitman.https.Http;
import cn.dagongniu.bitman.https.RequestState;
import cn.dagongniu.bitman.https.WebSocketsManager;
import cn.dagongniu.bitman.main.bean.AssetsDetesilTradeListBean;
import cn.dagongniu.bitman.my_ui.MyTopupCradActivity;
import cn.dagongniu.bitman.utils.AppManager;
import cn.dagongniu.bitman.utils.ClassConversionUtils;
import cn.dagongniu.bitman.utils.SkipActivityUtil;
import cn.dagongniu.bitman.utils.ToastUtil;
import cn.dagongniu.bitman.utils.um.UMManager;
import io.reactivex.disposables.Disposable;
import ua.naiksoftware.stomp.client.StompClient;

/**
 * 交易详情
 */
public class AssetsDetailsActivity extends BaseActivity implements IPropertyShowView {

    private static final String TAG = "AssetsDetailsActivity";

    @BindView(R.id.toolbar)
    MyTradingToolbar tradingToolbar;

    private Intent intent;
    private String MarketName;
    private String MarketId;
    private String icon;
    PropertyShowPresenter propertyShowPresenter;
    @BindView(R.id.tv_hold)
    TextView tvHold;
    @BindView(R.id.tv_available)
    TextView tvAvailable;
    @BindView(R.id.tv_freeze)
    TextView tvFreeze;

    @BindView(R.id.icon_iv)
    ImageView icon_iv;

    @BindView(R.id.tv_btc)
    TextView tvBtc;
    @BindView(R.id.asset_details_recycer)
    RecyclerView mRecyclerView;

    PropertyShowAdapter propertyShowAdapter;
    @BindView(R.id.rl_withdrawal)
    TextView rlWithdrawal;
    @BindView(R.id.rl_topup)
    TextView rlTopup;
    @BindView(R.id.icon_name)
    TextView icon_name;
    private StompClient mStompClient;
    PropertyShowBean propertyShowBean;
    private int delay = 10000;
    private int msg_what = 101;
    private WebSocketsManager.OnCreateStompClientListener mOnCreateStompClientListener;
    private Disposable mTopic;
    private boolean mIsBackgroundOrUnScreenOn;
    private boolean mIsTopicWebsocket;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_assets_details;
    }

    /**
     * 设置头部
     */
    private void initToolber(String title) {
        tradingToolbar.setTitleNameText(title);
        tradingToolbar.setRightImgVisibility(true);
        tradingToolbar.setRightTvVisibility(true);
        tradingToolbar.setRightTvVisibility(true);
        tradingToolbar.setRightTvColor(getContext().getResources().getColor(R.color.df_font));
        tradingToolbar.setLeftMoreClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        super.initView();
        intent = this.getIntent();
        propertyShowAdapter = new PropertyShowAdapter(this);
        MarketName = intent.getStringExtra("MarketName");
        MarketId = intent.getStringExtra("MarketId");
        icon = intent.getStringExtra("icon");

        Glide.with(mContext)
                .load(icon)
                .into(icon_iv);

        icon_name.setText(MarketName);
        initToolber(MarketName);
        propertyShowPresenter = new PropertyShowPresenter(this, RequestState.STATE_DIALOG);
        propertyShowPresenter.getPropertyShowModule();
        initWebsocket();
        mHandler.sendEmptyMessageDelayed(msg_what, delay);
    }

    @Override
    protected void initData() {
        super.initData();

    }

    public void adapClick() {
        propertyShowAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                PropertyShowBean.DataBean.TradeListBean tradeListBean = propertyShowBean.getData().getTradeList().get(position);
                SkipActivityUtil.skipToKLineActivity(tradeListBean.getId(), Constant.KLINE_MINTYPE_1, AssetsDetailsActivity.this);
            }
        });
    }

    /**
     * web socket初始化
     */
    private void initWebsocket() {
        if (mStompClient == null) {
            mOnCreateStompClientListener = new WebSocketsManager.OnCreateStompClientListener() {
                @Override
                public void onOpened() {
                    mTopic = WebSocketsManager.getInstance().topic(mStompClient, Http.TOPIC_MARKETLIST + MarketId, new WebSocketsManager.OnTopicListener() {
                        @Override
                        public void onNewData(String data) {
                            List<AssetsDetesilTradeListBean> assetsDetesilTradeListBeans = getListAssetsDetesilTradeGson(data);
                            List<PropertyShowBean.DataBean.TradeListBean> tradeListBeans = ClassConversionUtils.toTradeListBean(assetsDetesilTradeListBeans);
                            if (propertyShowAdapter != null) {
                                if (tradeListBeans != null && tradeListBeans.size() != 0) {
                                    propertyShowAdapter.setWebSocketMainMarket(tradeListBeans);
                                    propertyShowAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
                    mIsTopicWebsocket = true;
                }

                @Override
                public void onError() {
                }

                @Override
                public void onClosed() {
                }
            };
            mStompClient = WebSocketsManager.getInstance().createStompClient(mOnCreateStompClientListener);
        }

    }

    //通过Gson解析
    public List<AssetsDetesilTradeListBean> getListAssetsDetesilTradeGson(String jsonString) {
        List<AssetsDetesilTradeListBean> list = new ArrayList<AssetsDetesilTradeListBean>();
        Gson gson = new Gson();
        list = gson.fromJson(jsonString, new TypeToken<List<AssetsDetesilTradeListBean>>() {
        }.getType());
        return list;
    }

    /**
     * 定时去订阅 websocket
     */
//    Runnable webSocketRun = new Runnable() {
//        @Override
//        public void run() {
//            //每隔1s循环执行run方法
//            //initWebsocket();
//            mHandler.postDelayed(this, 10000);
////            WebSocketManager.getInstance().createStompClient(MarketId);
//            WebSocketsManager.getInstance().send(mStompClient, "/checkMarket/" + MarketId);
//        }
//    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mHandler.removeMessages(msg_what);
            mHandler.sendEmptyMessageDelayed(msg_what, delay);
            if (!mIsTopicWebsocket) {
                return;
            }
            KLog.d("WebSocketsManager MarketId = " + MarketId);
            WebSocketsManager.getInstance().send(mStompClient, "/checkMarket/" + MarketId);
        }
    };

    /**
     * 回调
     *
     * @param propertyShowBean
     */
    @Override
    public void setRefreshPropertyShowData(PropertyShowBean propertyShowBean) {
        this.propertyShowBean = propertyShowBean;
        tvHold.setText(propertyShowBean.getData().getUserCoin().getTotalBanlance().setScale(8, BigDecimal.ROUND_DOWN).toPlainString());
        tvAvailable.setText(propertyShowBean.getData().getUserCoin().getBanlance().setScale(8, BigDecimal.ROUND_DOWN).toPlainString());
        tvFreeze.setText(propertyShowBean.getData().getUserCoin().getFreezingBanlance().setScale(8, BigDecimal.ROUND_DOWN).toPlainString());
        tvBtc.setText(propertyShowBean.getData().getUserCoin().getCnyPrice().setScale(2, BigDecimal.ROUND_DOWN).toPlainString());

        propertyShowAdapter.setLineData(propertyShowBean.getData().getTradeList());
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        propertyShowAdapter.setNewData(propertyShowBean.getData().getTradeList());
        mRecyclerView.setAdapter(propertyShowAdapter);
        adapClick();
    }

    @Override
    public String getCoinId() {
        return MarketId;
    }

    /**
     * 参数有误-去登陆
     *
     * @param msg
     */
    @Override
    public void goLogin(String msg) {
        ToastUtil.ShowToast(msg);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (OAXApplication.state == 1 || OAXApplication.state == 2) {
            finish();
        }
        UMManager.onResume(mContext, UMConstant.OaxHomeNFragment);
        if (!AppManager.isAppIsInBackground(mContext) && OAXApplication.isScreenOn && mIsBackgroundOrUnScreenOn) {
            initWebsocket();
            KLog.d("WebSocketsManager initWebsocket");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (OAXApplication.mIsBackground || !OAXApplication.isScreenOn) {
            mIsBackgroundOrUnScreenOn = true;
            KLog.d("WebSocketsManager disconnect");
//            WebSocketsManager.getInstance().disconnect(mStompClient);
            if (mTopic != null && !mTopic.isDisposed()) {
                mTopic.dispose();
                mTopic = null;
            }
            if (mStompClient != null) {
                mIsTopicWebsocket = false;
                mStompClient.disconnect();
            }
            mStompClient = null;
            mOnCreateStompClientListener = null;
        } else {
            mIsBackgroundOrUnScreenOn = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        UMManager.onPause(mContext, UMConstant.OaxHomeNFragment);
//        if (OAXApplication.mIsBackground || !OAXApplication.isScreenOn) {
//            mIsBackgroundOrUnScreenOn = true;
//            KLog.d("WebSocketsManager disconnect");
////            WebSocketsManager.getInstance().disconnect(mStompClient);
//            if (mTopic != null && !mTopic.isDisposed()) {
//                mTopic.dispose();
//                mTopic = null;
//            }
//            if (mStompClient != null) {
//                mStompClient.disconnect();
//            }
//            mStompClient = null;
//            mOnCreateStompClientListener = null;
//        } else {
//            mIsBackgroundOrUnScreenOn = false;
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTopic != null && !mTopic.isDisposed()) {
            mTopic.dispose();
            mTopic = null;
        }
        if (mStompClient != null) {
            mIsTopicWebsocket = false;
            mStompClient.disconnect();
        }
        mStompClient = null;
        mOnCreateStompClientListener = null;
        if (mHandler != null) {
            mHandler.removeMessages(msg_what);
            mHandler = null;
        }
    }


    @OnClick({R.id.rl_withdrawal, R.id.rl_topup})
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_withdrawal:
                if (propertyShowBean != null) {
                    bundle.putString("MarketId", propertyShowBean.getData().getUserCoin().getId() + "");
                    bundle.putString("MarketName", propertyShowBean.getData().getUserCoin().getShortName());
                    openActivity(WithdrawalActivity.class, bundle);
                }
                break;
            case R.id.rl_topup:
                try{
                    bundle.putString("MarketId", propertyShowBean.getData().getUserCoin().getId() + "");
                    bundle.putString("MarketName", propertyShowBean.getData().getUserCoin().getShortName());
                    openActivity(MyTopupCradActivity.class, bundle);
                }catch (Exception e){}


                break;
        }
    }
}
