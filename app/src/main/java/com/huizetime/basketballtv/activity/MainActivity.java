package com.huizetime.basketballtv.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TabHost;

import com.huizetime.basketballtv.R;
import com.huizetime.basketballtv.application.MyApp;
import com.huizetime.basketballtv.bean.tv.TVSignBean;
import com.huizetime.basketballtv.fragment.ConnectFragment;
import com.huizetime.basketballtv.fragment.LogFragment;
import com.huizetime.basketballtv.fragment.SignFragment;
import com.huizetime.basketballtv.manager.BTDataTrans;
import com.huizetime.basketballtv.manager.ConnectManager;
import com.huizetime.basketballtv.presenter.MainPresenter;
import com.huizetime.basketballtv.presenter.MainPresenterListener;
import com.huizetime.basketballtv.view.MainView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {
    public static final String INIT_SERVER = "com.huizetime.baskballtv.bt.startServer";
    private static final String TAG = "main";
    private MainPresenterListener mPresenter;
    private FrameLayout mFrame;
    private ConnectFragment mConnectFragment;
    private LogFragment mLogFragment;
    private SignFragment mSignFragment;
    private List<Fragment> mFragmentList;
    private int mPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initWidth();
        initReceiver();
        initFragment();
        initData();
        findView();
        initView();
    }

    private void initWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        MyApp.getApp().setWidth(metrics.widthPixels);
    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(INIT_SERVER);
        registerReceiver(mConnectReceiver, intentFilter);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.connect:
                setCurrentFragment(0);
                break;
            case R.id.sign:
                setCurrentFragment(1);
                break;
            case R.id.log:
                setCurrentFragment(2);
                break;
        }
    }

    private void initFragment() {
        mFragmentList = new ArrayList<>();
        mConnectFragment = new ConnectFragment();
        mSignFragment = new SignFragment();
        mLogFragment = new LogFragment();
        getSupportFragmentManager().beginTransaction().
                add(R.id.frame, mLogFragment).
                add(R.id.frame, mSignFragment).
                add(R.id.frame, mConnectFragment).commit();
        mFragmentList.add(mConnectFragment);
        mFragmentList.add(mSignFragment);
        mFragmentList.add(mLogFragment);

        setCurrentFragment(0);
    }

    public void setCurrentFragment(int position) {
        if (mPos == position) {
            return;
        }
        mPos = position;
        for (int i = 0; i < mFragmentList.size(); i++) {
            if (i == position) continue;
            getSupportFragmentManager().beginTransaction()
                    .hide(mFragmentList.get(i)).commit();
        }

        getSupportFragmentManager().beginTransaction()
                .show(mFragmentList.get(position)).commit();
    }

    private void findView() {
    }


    private void initView() {

    }


    public void initData() {
        mPresenter = new MainPresenter(this);
        mPresenter.initData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.close();
        unregisterReceiver(mConnectReceiver);
    }

    @Override
    public List<Fragment> getFragmentList() {
        return mFragmentList;
    }

    @Override
    public void onConnectSuccess() {
        mConnectFragment.setConnectState(ConnectManager.STATE_CONNECTED);
    }

    @Override
    public void onWaitingConnect() {
        mConnectFragment.setConnectState(ConnectManager.STATE_WAITING);
    }

    @Override
    public void onDisconnect() {
        mConnectFragment.setConnectState(ConnectManager.STATE_DISCONNECT);
    }

    @Override
    public void onOpenServerError() {
        mConnectFragment.setConnectState(ConnectManager.STATE_ERROR);
    }

    @Override
    public void setSign(int code, TVSignBean tvSignBean) {
        setCurrentFragment(1);
        mSignFragment.setData(code, tvSignBean);
    }

    @Override
    public void setScore(int code, Object object) {
        setCurrentFragment(2);
        mLogFragment.setData(code, object);

    }

    private BroadcastReceiver mConnectReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(INIT_SERVER)) {
                mPresenter.setAsServer();
            }
        }
    };
}
