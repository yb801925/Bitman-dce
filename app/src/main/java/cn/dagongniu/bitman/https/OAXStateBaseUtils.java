package cn.dagongniu.bitman.https;

import cn.dagongniu.bitman.base.OAXIView;
import cn.dagongniu.bitman.base.OAXIViewBean;
import cn.dagongniu.bitman.base.OAXIViewList;
import cn.dagongniu.bitman.customview.LoadingState;

/**
 * 网络请求后页面展示判断
 */
public class OAXStateBaseUtils<T> {

    public OAXStateBaseUtils() {
    }

    /**
     * 请求成功
     *
     * @param view
     * @param state
     * @param data
     */
    public static void successBean(OAXIViewBean view, RequestState state, CommonJsonToBean data) {
        switch (state) {
            case STATE_LOADMORE://翻页更多
                view.setLoadMore(data);
                break;
            case STATE_REFRESH://刷新
                view.setRefresh(data);
                break;
            default:
                view.setData(data);
                break;
        }
    }

    /**
     * 请求成功
     *
     * @param view
     * @param state
     * @param data
     */
    public static void successList(OAXIViewList view, RequestState state, CommonJsonToList data) {
        switch (state) {
            case STATE_LOADMORE://翻页更多
                view.setLoadMore(data);
                break;
            case STATE_REFRESH://刷新
                view.setRefresh(data);
                break;
            default:
                view.setData(data);
                break;
        }
    }

    /**
     * 请求失败
     *
     * @param view
     * @param state
     * @param msg
     */
    public static void failure(OAXIView view, RequestState state, String msg) {
        switch (state) {
            case STATE_LOADMORE://翻页更多
            case STATE_REFRESH://刷新
            case STATE_DIALOG://弹框模式
                view.showToast(msg);
                break;
            case STATE_ALL_SCREEN://全屏不需要弹框
                break;
            case STATE_ALL_SCREEN_AND_DIALOG://全屏加弹框
                view.setXState(LoadingState.STATE_CUSTOM, msg);
                break;
            default:
                view.showToast(msg);
                break;
        }
    }

    /**
     * 请求成功 但是数据为空
     *
     * @param view
     * @param state
     * @param msg
     */
    public static void isNull(OAXIView view, RequestState state, String msg) {
        switch (state) {
            case STATE_LOADMORE://翻页更多
            case STATE_REFRESH://刷新
            case STATE_DIALOG://弹框模式
                view.showToast(msg);
            case STATE_ALL_SCREEN://全屏不需要弹框
                break;
            case STATE_ALL_SCREEN_AND_DIALOG://全屏加弹框
                view.setXState(LoadingState.STATE_EMPTY, null);
                break;
            default:
                view.showToast(msg);
                break;
        }
    }

    /**
     * 失败 服务器异常 已经无网络
     *
     * @param view
     * @param state
     * @param msg
     */
    public static void error(OAXIView view, RequestState state, String msg) {
        switch (state) {
            case STATE_REFRESH://刷新
            case STATE_LOADMORE://翻页更多
            case STATE_DIALOG://弹框模式
                view.showToast(msg);
                break;
            case STATE_ALL_SCREEN://全屏不需要弹框
                break;
            case STATE_ALL_SCREEN_AND_DIALOG://全屏加弹框
                switch (msg) {
                    case "网络异常":
                        view.setXState(LoadingState.STATE_NO_NET, null);
                        break;
                    case "服务器异常":
                        view.setXState(LoadingState.STATE_ERROR, null);
                        break;
                }
                break;
            default:
                view.showToast(msg);
                break;
        }
    }


}
