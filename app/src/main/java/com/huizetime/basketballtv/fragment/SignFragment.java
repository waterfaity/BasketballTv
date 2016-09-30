package com.huizetime.basketballtv.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huizetime.basketballtv.R;
import com.huizetime.basketballtv.adapter.SignAdapter;
import com.huizetime.basketballtv.application.MyApp;
import com.huizetime.basketballtv.bean.tv.TVSignBean;
import com.huizetime.basketballtv.utils.FileUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by water_fairy on 2016/9/30.
 */

public class SignFragment extends Fragment {
    public static final int A_LOGO = 1;
    public static final int B_LOGO = 2;
    public static final int QR_CODE = 3;
    public static final int SIGN_DATA = 4;

    private TextView mWatchName;//比赛名称
    private ImageView mALogo;//A队logo
    private ImageView mBLogo;//B队logo
    private ListView mASignList;//A队签到
    private ListView mBSignList;//B队签到
    private ImageView mQrCode;//二维码
    private TextView mAName, mBName;

    private ListView mAListView, mBListView;
    private SignAdapter mASignAdapter, mBSignAdapter;
    private int mTextTitleSize;
    private int mTextSize;
    private int mANameLeft;
    private int mQRSize;
    private int mlogoSize;

    private int mAdapterBigSize;
    private int mAdapterLittleSize;

    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign, container, false);
        assignViews(view);
        initData();
        initView();
        return view;
    }

    private void initData() {
        mContext = getActivity();
        int width = MyApp.getApp().getWidth();
        mTextTitleSize = (int) (60f / 1920 * width);
        mTextSize = (int) (54f / 1920 * width);
        mAdapterBigSize = (int) (27f / 1920 * width);
        mAdapterLittleSize = (int) (27f / 1920 * width);
        mANameLeft = (int) (0.15 * width);
        mQRSize = (int) ((408f) / 1920 * width);
        mlogoSize = (int) ((204f) / 1920 * width);
    }

    private void initView() {
//        mWatchName.setTextSize(mTextTitleSize);
//        mAName.setTextSize(mTextSize);
//        mBName.setTextSize(mTextSize);
//
//        mQrCode.getLayoutParams().height = mQRSize;
//        mQrCode.getLayoutParams().width = mQRSize;
//        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) mAName.getLayoutParams();
//        layoutParams1.leftMargin = mANameLeft;
//        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) mBName.getLayoutParams();
//        layoutParams2.rightMargin = mANameLeft;
    }


    private void assignViews(View view) {
        mWatchName = (TextView) view.findViewById(R.id.watch_name);
        mBLogo = (ImageView) view.findViewById(R.id.b_logo);
        mALogo = (ImageView) view.findViewById(R.id.a_logo);
        mASignList = (ListView) view.findViewById(R.id.a_sign_list);
        mBSignList = (ListView) view.findViewById(R.id.b_sign_list);
        mQrCode = (ImageView) view.findViewById(R.id.qr_code);
        mAName = (TextView) view.findViewById(R.id.a_name);
        mBName = (TextView) view.findViewById(R.id.b_name);
    }

    public void setData(int code, TVSignBean tvSignBean) {

        switch (code) {
            case A_LOGO:
                Picasso.with(mContext).
                        load(FileUtils.getImg(mContext, FileUtils.A_LOGO))
                        .resize(mlogoSize, mlogoSize)
                        .placeholder(R.mipmap.b_logo)
                        .into(mALogo);
                break;
            case B_LOGO:
                Picasso.with(mContext).
                        load(FileUtils.getImg(mContext, FileUtils.B_LOGO))
                        .resize(mlogoSize, mlogoSize)
                        .placeholder(R.mipmap.b_logo)
                        .into(mBLogo);
                break;
            case QR_CODE:
                Picasso.with(mContext).
                        load(FileUtils.getImg(mContext, FileUtils.QR_CODE))
                        .resize(mlogoSize, mlogoSize)
                        .placeholder(R.mipmap.qr_code)
                        .into(mQrCode);
                break;
            case SIGN_DATA:
                List<TVSignBean.Player> aList = tvSignBean.getEntityA().getList();
                List<TVSignBean.Player> bList = tvSignBean.getEntityB().getList();
                mAListView.setAdapter(new SignAdapter(aList, mContext, mAdapterBigSize, mAdapterLittleSize));
                mBListView.setAdapter(new SignAdapter(bList, mContext, mAdapterBigSize, mAdapterLittleSize));
                break;
        }
    }
}
