package com.huizetime.basketballtv.presenter;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.huizetime.basketballtv.activity.MainActivity;
import com.huizetime.basketballtv.bean.tv.TVEventBean;
import com.huizetime.basketballtv.bean.tv.TVScoreBean;
import com.huizetime.basketballtv.bean.tv.TVSignBean;
import com.huizetime.basketballtv.manager.BTDataTrans;
import com.huizetime.basketballtv.manager.TVDataReceiveUtils;
import com.huizetime.basketballtv.model.MainModel;
import com.huizetime.basketballtv.model.MainModelSimple;
import com.huizetime.basketballtv.view.MainView;

import java.io.File;

/**
 * Created by water_fairy on 2016/9/27.
 */
public class MainPresenter implements MainPresenterListener {
    private static final String TAG = "mainPresenter";
    private MainView mView;
    private Activity mActivity;
    private MainModel mModel;
    private BTDataTrans mDataTrans;

    public MainPresenter(MainActivity activity) {
        mView = activity;
        mActivity = activity;
        mModel = new MainModelSimple(this, activity);
    }

    //1
    @Override
    public void initData() {
        mModel.initData();
        mDataTrans = new BTDataTrans();
    }

    @Override
    public void setAsServer() {
        mModel.setAsServer();
    }

    @Override
    public void reset() {
        mModel.setAsServer();

    }


    @Override
    public void close() {
        mModel.close();
    }

    @Override
    public void setALogo(File file) {

    }

    @Override
    public void setBLogo(File file) {

    }

    @Override
    public void setQRCode(File file) {

    }

    @Override
    public void sign(TVSignBean tvSignBean) {

    }

    @Override
    public void initScore(TVScoreBean tvScoreBean) {

    }

    @Override
    public void setEventScore(TVEventBean tvScoreEventBean) {

    }

    //2
    @Override
    public void onSuccess() {
        Log.i(TAG, "server: 连接成功");
    }

    @Override
    public void onWaitingConnect() {
        Log.i(TAG, "server: 等待连接中");
    }

    @Override
    public void onDisconnect() {
        Log.i(TAG, "server: 连接断开");
        Log.i(TAG, "server: 重新设置服务器");
        setAsServer();
    }

    @Override
    public void onOpenServerError() {
        Log.i(TAG, "server: 服务开启失败");

    }

    @Override
    public void onRead(byte[] bytes, int len) {
//        Log.i(TAG, "read: 读取数据");

        String json = TVDataReceiveUtils.receive(mDataTrans, bytes, len);

        if (!TextUtils.isEmpty(json)) {
            mDataTrans.clear();
            mModel.operateData(json);
        }

    }

}
