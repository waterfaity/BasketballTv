package com.huizetime.basketballtv.model;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.huizetime.basketballtv.activity.MainActivity;
import com.huizetime.basketballtv.application.MyApp;
import com.huizetime.basketballtv.bean.tv.TVData;
import com.huizetime.basketballtv.manager.ConnectManager;
import com.huizetime.basketballtv.presenter.MainPresenterListener;
import com.huizetime.basketballtv.utils.Base64Utils;
import com.huizetime.basketballtv.utils.FileUtils;
import com.huizetime.basketballtv.utils.PermissionUtils;

import java.io.File;

/**
 * Created by water_fairy on 2016/9/27.
 */
public class MainModelSimple implements MainModel {
    private static final String TAG = "mainModel";
    private MainPresenterListener mListener;
    private Activity mActivity;
    private ConnectManager mConnectManager;


    public MainModelSimple(MainPresenterListener listener, MainActivity activity) {
        this.mListener = listener;
        this.mActivity = activity;
    }

    @Override
    public void initData() {
        //初始化权限
        PermissionUtils.requestPermission(mActivity, PermissionUtils.REQUEST_LOCATION);
        //初始化蓝牙连接管理
        mConnectManager = ConnectManager.getInstance();

    }

    @Override
    public void setAsServer() {
        MyApp.getApp().getBTManager().initData(mActivity);
        mConnectManager.setServerListener(new ConnectManager.ServerConnectListener() {
            @Override
            public void onConnectSuccess() {
                mListener.onSuccess();
            }

            @Override
            public void onWaitingConnect() {
                mListener.onWaitingConnect();
            }

            @Override
            public void onDisconnect() {
                mListener.onDisconnect();
            }

            @Override
            public void onOpenServerError() {
                mListener.onOpenServerError();

            }

            @Override
            public void onRead(byte[] bytes, int len) {
                mListener.onRead(bytes, len);
            }
        });
        mConnectManager.setAsServer();
    }

    @Override
    public void close() {
        mConnectManager.close();
    }

    @Override
    public void operateData(String json) {

        Log.i(TAG, "接收数据:  " + json);
        TVData tvData = null;
        try {
            tvData = new Gson().fromJson(json, TVData.class);
        } catch (Exception e) {
            Log.i(TAG, "请稍候传输数据");
            mConnectManager.setServerResult(TVData.RESULT_WAITING);
            return;
        }
        mConnectManager.setServerResult(TVData.RESULT_OK);
        String img = tvData.getImg();

        switch (tvData.getCode()) {
            case TVData.TYPE_A_LOGO:
                mListener.setALogo(Base64Utils.decodeToFile(img, FileUtils.getImgPath(mActivity, FileUtils.A_LOGO)));
                break;
            case TVData.TYPE_B_LOGO:
                mListener.setBLogo(Base64Utils.decodeToFile(img, FileUtils.getImgPath(mActivity, FileUtils.B_LOGO)));
                break;
            case TVData.TYPE_QR_CODE:
                mListener.setQRCode(Base64Utils.decodeToFile(img, FileUtils.getImgPath(mActivity, FileUtils.QR_CODE)));
                break;
            case TVData.TYPE_SIGN:
                mListener.sign(tvData.getTvSignBean());
                break;
            case TVData.TYPE_INIT_SCORE:
                mListener.initScore(tvData.getTvScoreBean());
                break;
            case TVData.TYPE_EVENT:
                mListener.setEventScore(tvData.getTvScoreEventBean());
                break;
            case TVData.TYPE_CLOSE:
                mListener.close();
                break;
        }
    }

    @Override
    public void closeBT() {
        MyApp.getApp().getBTManager().getBTAdapter().disable();
        MyApp.getApp().getBTManager().getBTAdapter().enable();
    }
}
