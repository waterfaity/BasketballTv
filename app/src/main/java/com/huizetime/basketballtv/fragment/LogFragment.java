package com.huizetime.basketballtv.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huizetime.basketballtv.R;

/**
 * Created by water_fairy on 2016/9/30.
 */

public class LogFragment extends Fragment {
    public static final int INIT = 1;
    public static final int EVENT = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log, container, false);
        return view;
    }

    public void setData(int code, Object object) {

    }
}
