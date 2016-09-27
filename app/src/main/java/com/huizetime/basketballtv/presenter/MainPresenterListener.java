package com.huizetime.basketballtv.presenter;

import com.huizetime.basketballtv.bean.tv.TVEventBean;
import com.huizetime.basketballtv.bean.tv.TVScoreBean;
import com.huizetime.basketballtv.bean.tv.TVSignBean;

import java.io.File;

/**
 * Created by water_fairy on 2016/9/27.
 */
public interface MainPresenterListener {
    void initData();

    void setAsServer();

    void reset();

    void onSuccess();

    void onWaitingConnect();

    void onDisconnect();

    void onOpenServerError();

    void onRead(byte[] bytes, int len);

    void close();

    /**
     * 设置A队logo
     *
     * @param file
     */
    void setALogo(File file);

    /**
     * 设置B队logo
     *
     * @param file
     */
    void setBLogo(File file);

    /**
     * 设置二维码
     *
     * @param file
     */
    void setQRCode(File file);

    /**
     * 设置签到人员
     *
     * @param tvSignBean
     */
    void sign(TVSignBean tvSignBean);

    /**
     * 初始化比分界面
     *
     * @param tvScoreBean
     */
    void initScore(TVScoreBean tvScoreBean);

    /**
     * 事件结果
     *
     * @param tvScoreEventBean
     */
    void setEventScore(TVEventBean tvScoreEventBean);
}
