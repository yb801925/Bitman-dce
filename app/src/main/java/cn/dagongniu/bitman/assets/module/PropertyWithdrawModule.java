package cn.dagongniu.bitman.assets.module;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.HashMap;

import cn.dagongniu.bitman.assets.bean.PropertyWithdrawBean;
import cn.dagongniu.bitman.base.BaseModule;
import cn.dagongniu.bitman.https.Http;
import cn.dagongniu.bitman.https.HttpUtils;
import cn.dagongniu.bitman.https.OnBaseDataListener;
import cn.dagongniu.bitman.https.RequestState;
import cn.dagongniu.bitman.utils.DebugUtils;

/**
 * 提现记录 module
 */
public class PropertyWithdrawModule extends BaseModule<String, PropertyWithdrawBean> {

    public PropertyWithdrawModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<PropertyWithdrawBean> onBaseDataListener, String... parm) {
    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<PropertyWithdrawBean> onBaseDataListener, RequestState state, String... parm) {

        HashMap<String, String> hashMap = new HashMap<>();
        String status = parm[0];
        String coinName = parm[1];
        String pageIndex = parm[2];
        String pageSize = parm[3];
        //hashMap.put("status", status);                  //状态
        //hashMap.put("coinName", coinName);              //币种名称
        hashMap.put("pageIndex", pageIndex);            //开始页
        hashMap.put("pageSize", pageSize);              //每页条数

        HttpUtils.getInstance().postLangIdToken(Http.PROPERTY_WITHDRAW, hashMap, activity, new OnBaseDataListener<String>() {
            @Override
            public void onNewData(String data) {
                DebugUtils.prinlnLog(data);
                try {
                    PropertyWithdrawBean propertyWithdrawBean = new Gson().fromJson(data, PropertyWithdrawBean.class);
                    onBaseDataListener.onNewData(propertyWithdrawBean);
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
