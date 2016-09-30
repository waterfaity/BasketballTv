package com.huizetime.basketballtv.view;

import android.support.v4.app.Fragment;

import com.huizetime.basketballtv.bean.tv.TVScoreBean;
import com.huizetime.basketballtv.bean.tv.TVSignBean;

import java.util.List;

/**
 * Created by water_fairy on 2016/9/27.
 */
public interface MainView {
    List<Fragment> getFragmentList();

    void onConnectSuccess();

    void onWaitingConnect();

    void onDisconnect();

    void onOpenServerError();


    void setSign(int code, TVSignBean tvSignBean);


    void setScore(int code, Object object);
}
