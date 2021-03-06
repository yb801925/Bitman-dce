package cn.dagongniu.bitman.assets.presenter;

import android.app.Activity;

import cn.dagongniu.bitman.R;
import cn.dagongniu.bitman.assets.bean.CoinAddressListBean;
import cn.dagongniu.bitman.assets.module.CoinAddressAddModule;
import cn.dagongniu.bitman.assets.view.ICoinAddressView;
import cn.dagongniu.bitman.base.BasePresenter;
import cn.dagongniu.bitman.https.OnBaseDataListener;
import cn.dagongniu.bitman.https.RequestState;
import cn.dagongniu.bitman.https.StateBaseUtils;

/**
 * 添加提币地址 Presenter
 */
public class CoinAddressAddPresenter extends BasePresenter {

    private CoinAddressAddModule coinAddressAddModule;
    private ICoinAddressView iCoinAddressView;
    private Activity activity;
    RequestState state;

    public CoinAddressAddPresenter(ICoinAddressView iCoinAddressView, RequestState state) {
        super(iCoinAddressView);
        this.state = state;
        activity = (Activity) iCoinAddressView.getContext();
        this.iCoinAddressView = iCoinAddressView;
        coinAddressAddModule = new CoinAddressAddModule(activity);
    }

    public void getCoinAddressAddModule() {
        coinAddressAddModule.requestServerDataOne(new OnBaseDataListener<CoinAddressListBean>() {
            @Override
            public void onNewData(CoinAddressListBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    StateBaseUtils.success(iCoinAddressView, state, data);
                    iCoinAddressView.isSuccess();
                } else {
                    StateBaseUtils.failure(iCoinAddressView, state, data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                if (activity.getResources().getString(R.string.to_login_go).equals(code)) {//前往登录
                    iCoinAddressView.goLogin(code);
                } else {
                    StateBaseUtils.error(iCoinAddressView, state, code);
                }
            }
        }, state, iCoinAddressView.getCoinId(), iCoinAddressView.getAddress(), iCoinAddressView.getRemark(), iCoinAddressView.getSmsCode(), iCoinAddressView.getEmailCode(), iCoinAddressView.getGoogleCode());
    }


}
