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
import cn.dagongniu.bitman.utils.AppConstants;
import cn.dagongniu.bitman.utils.DebugUtils;

/**
 * 邮箱注册
 */
public class EamilRegisteredModule extends BaseModule<String, HttpBaseBean> {

    public EamilRegisteredModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<HttpBaseBean> onBaseDataListener, String... parm) {

    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<HttpBaseBean> onBaseDataListener, RequestState state, String... parm) {

        HashMap<String, String> hashMap = new HashMap<>();
        String email = parm[0];
        String password = parm[1];
        String repeatPassword = parm[2];
        String invateCode = parm[3];
        hashMap.put("email", email);                    //邮箱
        hashMap.put("password", password);              //密码
        hashMap.put("repeatPassword", repeatPassword);  //确认密码
        hashMap.put("invateCode", invateCode);          //邀请码
        hashMap.put("source", AppConstants.SOURCE);     //来源 1 pc 2 ios 3 android


        HttpUtils.getInstance().postLang(Http.user_emailRegister, hashMap, activity, new OnBaseDataListener<String>() {
            @Override
            public void onNewData(String data) {
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
