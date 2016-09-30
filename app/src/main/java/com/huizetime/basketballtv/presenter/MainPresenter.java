package com.huizetime.basketballtv.presenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.huizetime.basketballtv.activity.MainActivity;
import com.huizetime.basketballtv.bean.tv.TVEventBean;
import com.huizetime.basketballtv.bean.tv.TVScoreBean;
import com.huizetime.basketballtv.bean.tv.TVSignBean;
import com.huizetime.basketballtv.fragment.LogFragment;
import com.huizetime.basketballtv.fragment.SignFragment;
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
        mView.setSign(SignFragment.A_LOGO, null);
    }

    @Override
    public void setBLogo(File file) {
        mView.setSign(SignFragment.B_LOGO, null);
    }

    @Override
    public void setQRCode(File file) {
        mView.setSign(SignFragment.QR_CODE, null);
    }

    @Override
    public void sign(TVSignBean tvSignBean) {
        mView.setSign(SignFragment.SIGN_DATA, tvSignBean);
    }

    @Override
    public void initScore(TVScoreBean tvScoreBean) {
        mView.setScore(LogFragment.INIT, tvScoreBean);
    }

    @Override
    public void setEventScore(TVEventBean tvScoreEventBean) {
        mView.setScore(LogFragment.EVENT, tvScoreEventBean);
    }

    //2
    @Override
    public void onSuccess() {
        time = 0;
        Log.i(TAG, "server: 连接成功");
        mView.onConnectSuccess();
    }

    @Override
    public void onWaitingConnect() {
        mView.onWaitingConnect();
        Log.i(TAG, "server: 等待连接中");
    }

    @Override
    public void onDisconnect() {
        Log.i(TAG, "server: 连接断开");
        Log.i(TAG, "server: 重新设置服务器");
        mView.onDisconnect();
        setAsServer();
    }

    @Override
    public void onOpenServerError() {
        mView.onOpenServerError();
        Log.i(TAG, "server: 服务开启失败");
        mModel.closeBT();
        time++;
        if (time > 2) {
            return;
        }
        handler.sendEmptyMessageDelayed(0, 5000);
    }

    @Override
    public void onRead(byte[] bytes, int len) {
        String json = TVDataReceiveUtils.receive(mDataTrans, bytes, len);
        if (!TextUtils.isEmpty(json)) {
            mDataTrans.clear();
            mModel.operateData(json);
        }
    }


    private int time;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setAsServer();
        }
    };
}
