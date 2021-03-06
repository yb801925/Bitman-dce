package cn.dagongniu.bitman.account.presenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.List;

import cn.dagongniu.bitman.account.bean.HelpBean;
import cn.dagongniu.bitman.account.module.HelpModule;
import cn.dagongniu.bitman.base.OAXBasePresenter;
import cn.dagongniu.bitman.base.OAXIViewBean;
import cn.dagongniu.bitman.customview.LoadingState;
import cn.dagongniu.bitman.https.CommonJsonToBean;
import cn.dagongniu.bitman.https.OAXStateBaseUtils;
import cn.dagongniu.bitman.https.OnDataListener;
import cn.dagongniu.bitman.https.RequestState;

public class HelpPresenter extends OAXBasePresenter {
    private HelpModule mHelpModule;
    private Activity activity;
    OAXIViewBean view;
    RequestState mState;

    public HelpPresenter(OAXIViewBean view) {
        super(view);
        activity = (Activity) view.getContext();
        this.view = view;
        mHelpModule = new HelpModule(activity);
    }

    public void getData(HashMap<String, Object> params, RequestState state, boolean isLoadMore) {
        this.mState = state;
        mHelpModule.requestServerData(new OnDataListener<HelpBean>() {

            @Override
            public void onNewData(CommonJsonToBean<HelpBean> data) {
                KLog.d("onNewData == " + new Gson().toJson(data));
                if (data.getSuccess()) {
                    List<HelpBean.ListBean> list = data.getData().getList();
                    KLog.d("onNewData list.size() == " + list.size());
                    if (isLoadMore) {
                        OAXStateBaseUtils.successBean(view, mState, data);
                    } else {
                        if (list.size() == 0) {
                            view.setXState(LoadingState.STATE_EMPTY, data.getMsg());
                        } else {
                            OAXStateBaseUtils.successBean(view, mState, data);
                        }
                    }

                } else {
//                    OAXStateBaseUtils.failure(view, mState, data.getMsg());
                    view.setXState(LoadingState.STATE_ERROR, data.getMsg());
                }
            }

            @Override
            public void onError(String code) {
//                OAXStateBaseUtils.error(view, mState, code);
                view.setXState(LoadingState.STATE_ERROR, code);

            }
        }, state, params);
    }
}
