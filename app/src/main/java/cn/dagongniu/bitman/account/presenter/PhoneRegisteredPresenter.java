package cn.dagongniu.bitman.account.presenter;

import android.app.Activity;

import cn.dagongniu.bitman.account.module.PhoneRegisteredModule;
import cn.dagongniu.bitman.account.view.IRegisteredView;
import cn.dagongniu.bitman.base.BasePresenter;
import cn.dagongniu.bitman.https.HttpBaseBean;
import cn.dagongniu.bitman.https.OnBaseDataListener;
import cn.dagongniu.bitman.https.RequestState;
import cn.dagongniu.bitman.https.StateBaseUtils;

/***
 * 手机注册 Presenter
 */
public class PhoneRegisteredPresenter extends BasePresenter {

    private PhoneRegisteredModule phoneRegisteredModule;
    private IRegisteredView iRegisteredView;
    private Activity activity;
    RequestState state;

    public PhoneRegisteredPresenter(IRegisteredView iRegisteredView, RequestState state) {
        super(iRegisteredView);
        this.state = state;
        activity = (Activity) iRegisteredView.getContext();
        this.iRegisteredView = iRegisteredView;
        phoneRegisteredModule = new PhoneRegisteredModule(activity);
    }

    public void getPhoneRegisteredModule() {
        phoneRegisteredModule.requestServerDataOne(new OnBaseDataListener<HttpBaseBean>() {

            @Override
            public void onNewData(HttpBaseBean data) {
                if (data.isSuccess()) {
                    //响应请求数据回去
                    StateBaseUtils.success(iRegisteredView, state, data);
                } else {
                    StateBaseUtils.failure(iRegisteredView, state, data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
                StateBaseUtils.error(iRegisteredView, state, code);
            }
        }, state, iRegisteredView.getEmailAndPhone(),
                iRegisteredView.getPassword(),
                iRegisteredView.getPassword(),
                null,//短信验证码
                iRegisteredView.getInvateCode());
    }

}
