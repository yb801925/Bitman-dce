package cn.dagongniu.bitman.main.module;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.HashMap;

import cn.dagongniu.bitman.base.BaseModule;
import cn.dagongniu.bitman.https.Http;
import cn.dagongniu.bitman.https.HttpUtils;
import cn.dagongniu.bitman.https.OnBaseDataListener;
import cn.dagongniu.bitman.https.RequestState;
import cn.dagongniu.bitman.main.bean.NoticeCenterMoreBean;
import cn.dagongniu.bitman.utils.DebugUtils;

/**
 * 公告查看更多 module
 */
public class NoticeCenterReadMoreModule extends BaseModule<String, NoticeCenterMoreBean> {

    public NoticeCenterReadMoreModule(Activity activity) {
        super(activity);
    }

    @Override
    public void requestServerDataOne(OnBaseDataListener<NoticeCenterMoreBean> onBaseDataListener, String... parm) {

    }

    @Override
    public void requestServerDataOne(final OnBaseDataListener<NoticeCenterMoreBean> onBaseDataListener, RequestState state, String... parm) {

        HashMap<String, String> hashMap = new HashMap<>();
        String type = parm[0];
        String pageIndex = parm[1];
        String pageSize = parm[2];
        hashMap.put("type", type);              //公告类型 4新币 上线 5最新公告
        hashMap.put("pageIndex", pageIndex);     //页码
        hashMap.put("pageSize", pageSize);       //每页显示条数

        HttpUtils.getInstance().postLang(Http.noticeCenter_readMore, hashMap, activity, new OnBaseDataListener<String>() {
            @Override
            public void onNewData(String data) {
                DebugUtils.prinlnLog(data);
                try {
                    NoticeCenterMoreBean httpBaseBean = new Gson().fromJson(data, NoticeCenterMoreBean.class);
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
