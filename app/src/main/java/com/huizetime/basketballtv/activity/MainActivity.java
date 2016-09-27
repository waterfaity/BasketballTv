package com.huizetime.basketballtv.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.huizetime.basketballtv.R;
import com.huizetime.basketballtv.bean.tv.TVData;
import com.huizetime.basketballtv.manager.BTDataTrans;
import com.huizetime.basketballtv.manager.BTManager;
import com.huizetime.basketballtv.manager.TVDataReceiveUtils;
import com.huizetime.basketballtv.presenter.MainPresenter;
import com.huizetime.basketballtv.presenter.MainPresenterListener;
import com.huizetime.basketballtv.utils.Base64Utils;
import com.huizetime.basketballtv.view.MainView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = "main";
    private MainPresenterListener mPresenter;
    BTDataTrans btDataTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }


    private void initView() {

    }


    private void initData() {
        mPresenter = new MainPresenter(this);
        mPresenter.initData();
        mPresenter.setAsServer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.close();
    }
}
