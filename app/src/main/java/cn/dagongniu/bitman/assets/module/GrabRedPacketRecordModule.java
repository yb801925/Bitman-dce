package cn.dagongniu.bitman.assets.module;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.HashMap;

import cn.dagongniu.bitman.assets.bean.GrabRedPacketRecordBean;
import cn.dagongniu.bitman.base.BaseModule;
import cn.dagongniu.bitman.https.Http;
import cn.dagongniu.bitman.https.HttpUtils;
import cn.dagongniu.bitman.https.OnBaseDataListener;
import cn.dagongniu.bitman.https.RequestState;
import cn.dagongniu.bitman.utils.DebugUtils;

/**
 * 我领取的红包记录 module
 */
public class GrabRedPacketRecordModule extends BaseModule<String, GrabRedPacketRecordBean> {

    public GrabRedPacketRecordModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<GrabRedPacketRecordBean> onBaseDataListener, String... parm) {
    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<GrabRedPacketRecordBean> onBaseDataListener, RequestState state, String... parm) {

        HashMap<String, String> hashMap = new HashMap<>();
        String pageNo = parm[0];
        String pageSize = parm[1];
        hashMap.put("pageNo", pageNo);            //开始页
        hashMap.put("pageSize", pageSize);              //每页条数

        HttpUtils.getInstance().postLangIdToken(Http.GRABREDPACKETRECORD, hashMap, activity, new OnBaseDataListener<String>() {
            @Override
            public void onNewData(String data) {
                DebugUtils.prinlnLog(data);
                try {
                    GrabRedPacketRecordBean grabRedPacketRecordBean = new Gson().fromJson(data, GrabRedPacketRecordBean.class);
                    onBaseDataListener.onNewData(grabRedPacketRecordBean);
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
