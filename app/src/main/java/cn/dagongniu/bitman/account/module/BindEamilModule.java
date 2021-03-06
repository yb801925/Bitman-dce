package cn.dagongniu.bitman.account.module;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.HashMap;

import cn.dagongniu.bitman.base.BaseModule;
import cn.dagongniu.bitman.https.Http;
import cn.dagongniu.bitman.https.HttpBaseBean;
import cn.dagongniu.bitman.https.HttpUtils;
import cn.dagongniu.bitman.https.OnBaseDataListener;
import cn.dagongniu.bitman.https.RequestState;
import cn.dagongniu.bitman.utils.DebugUtils;
import cn.dagongniu.bitman.utils.Logs;

/**
 * 绑定邮箱 module
 */
public class BindEamilModule extends BaseModule<String, HttpBaseBean> {

    public BindEamilModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<HttpBaseBean> onBaseDataListener, String... parm) {

    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<HttpBaseBean> onBaseDataListener, RequestState state, String... parm) {

        HashMap<String, String> hashMap = new HashMap<>();
        String email = parm[0];
        String code = parm[1];

        hashMap.put("email", email);                    //邮箱
        hashMap.put("code", code);                      //验证码

        HttpUtils.getInstance().postLangIdToken(Http.USER_BINDEMAIL, hashMap, activity, new OnBaseDataListener<String>() {
            @Override
            public void onNewData(String data) {
                Logs.s("     邮箱绑定数据:    "+data);
                DebugUtils.prinlnLog(data);
                try {
                    HttpBaseBean httpBaseBean = new Gson().fromJson(data, HttpBaseBean.class);
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
