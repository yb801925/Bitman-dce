package cn.dagongniu.bitman.account.module;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.HashMap;

import cn.dagongniu.bitman.account.bean.IdentityResultBean;
import cn.dagongniu.bitman.base.BaseModule;
import cn.dagongniu.bitman.https.Http;
import cn.dagongniu.bitman.https.HttpUtils;
import cn.dagongniu.bitman.https.OnBaseDataListener;
import cn.dagongniu.bitman.https.RequestState;
import cn.dagongniu.bitman.utils.DebugUtils;

/**
 * 身份认证审核结果 module
 */
public class IdentityResultModule extends BaseModule<String, IdentityResultBean> {

    public IdentityResultModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<IdentityResultBean> onBaseDataListener, String... parm) {

    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<IdentityResultBean> onBaseDataListener, RequestState state, String... parm) {

        HashMap<String, String> map = new HashMap<String, String>();

        HttpUtils.getInstance().postLangIdToken(Http.IDENTITYRESULT, map, activity, new OnBaseDataListener<String>() {
            @Override
            public void onNewData(String data) {
                DebugUtils.prinlnLog(data);
                try {
                    IdentityResultBean httpBaseBean = new Gson().fromJson(data, IdentityResultBean.class);
                    onBaseDataListener.onNewData(httpBaseBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String code) {
                onBaseDataListener.onError(code);
            }
        }, state);


    }
}
