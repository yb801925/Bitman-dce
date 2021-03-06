package cn.dagongniu.bitman.assets.view;

import cn.dagongniu.bitman.assets.bean.SendRedPacketRecordBean;
import cn.dagongniu.bitman.base.IView;

public interface ISendRedPacketRecordView extends IView {


    int getSendRedPacketRecordPageIndex();//页码

    int getSendRedPacketRecordPageSize();//	每页显示条数

    void setSendRedPacketRecordData(SendRedPacketRecordBean sendRedPacketRecordBean);//回调数据

    void setSendRedPacketRecordNull(SendRedPacketRecordBean sendRedPacketRecordBean);//回调数据

    void setSendRedPacketRecordMoreData(SendRedPacketRecordBean sendRedPacketRecordBean);//加载更多回调

    void setSendRedPacketRecordMoreErrer(String noticeCenterMoreData);//加载更多错误回调

    void setRefreshErrer();//刷新错误


}
