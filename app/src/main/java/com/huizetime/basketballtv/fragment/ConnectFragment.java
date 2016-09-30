package com.huizetime.basketballtv.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huizetime.basketballtv.R;
import com.huizetime.basketballtv.activity.MainActivity;
import com.huizetime.basketballtv.manager.ConnectManager;

/**
 * Created by water_fairy on 2016/9/30.
 */

public class ConnectFragment extends Fragment {
    private TextView mConnectState;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connect, container, false);
        findView(view);
        Intent intent = new Intent(MainActivity.INIT_SERVER);
        getActivity().sendBroadcast(intent);
        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void findView(View view) {
        mConnectState = (TextView) view.findViewById(R.id.connect_state);
    }

    public void setConnectState(int state) {
        int stateStr = 0;
        int colorId = 0;
        switch (state) {
            case ConnectManager.STATE_WAITING:
                stateStr = R.string.waiting_connect;
                colorId = R.color.color_waiting;
                break;
            case ConnectManager.STATE_CONNECTED:
                stateStr = R.string.connected;
                colorId = R.color.color_connected;
                break;
            case ConnectManager.STATE_DISCONNECT:
                stateStr = R.string.disconnect;
                colorId = R.color.color_disconnect;
                break;
            case ConnectManager.STATE_ERROR:
                stateStr = R.string.init_equipment_error;
                colorId = R.color.color_error;
                break;
        }
        if (mConnectState != null) {
            final int finalStateStr = stateStr;
            final int finalColorId = colorId;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mConnectState.setText(finalStateStr);
                    mConnectState.setTextColor(getResources().getColor(finalColorId));
                }
            });

        }

    }


}
