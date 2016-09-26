package com.huizetime.basketballtv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.huizetime.basketballtv.bean.tv.TVData;
import com.huizetime.basketballtv.manager.BTDataTrans;
import com.huizetime.basketballtv.manager.BTManager;
import com.huizetime.basketballtv.utils.Base64Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "main";
    BTManager btManager;
    BTDataTrans btDataTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btDataTrans = new BTDataTrans();
        initBTManager();

    }

    private void initBTManager() {
        btManager = BTManager.getInstance().initData(this);
        btManager.setAsServer(new BTManager.OnConnectListener() {
            @Override
            public void onSuccess() {
                Log.i(TAG, "server 连接成功");
            }

            @Override
            public void onWaitingConnectTo() {
                Log.i(TAG, "server 等待连接中...");
            }

            @Override
            public void onConnecting() {

            }

            @Override
            public void onRead(byte[] bytes, int len) {
//                Log.i(TAG, "onRead: len" + len);
//                Log.i(TAG, "onRead: " + new String(bytes, 0, len));

                boolean trans = btDataTrans.trans(bytes, len);
                String json = null;
                if (trans) {
                    byte[] result = btDataTrans.getResult();
                    btDataTrans.clear();
                    json = new String(result);
                    json = json.replace("start-", "");
                    json = json.replace("---end", "");
                } else {

                    return;
                }
                Log.i(TAG, "onRead: " + json);

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(json);
                    int code = jsonObject.getInt("code");
                    switch (code) {
                        case TVData.TYPE_QR_CODE:
                            String img = jsonObject.getString("img");
                            img = img.replace("\n", "");
                            Log.i(TAG, "onRead: img_base64:" + img);
                            Base64Utils.decodeToFile(img, "/sdcard/" + new Date().getTime() + ".jpg");

                            break;
                        case TVData.TYPE_A_LOGO:
                            String imgA = jsonObject.getString("img");
                            imgA = imgA.replace("\n", "");
                            Log.i(TAG, "onRead:A_Icon" + imgA);
                            Base64Utils.decodeToFile(imgA, "/sdcard/" + new Date().getTime() + ".jpg");

                            break;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if ("close".equals(json)) {
                    btManager.close();
                    resetBTManager();
                }
            }

            @Override
            public void onWrite(byte[] bytes) {
                String s = new String(bytes);
                Log.i(TAG, "server 写入: " + s);
            }

            @Override
            public void onFailed() {
                Log.i(TAG, "server 服务器开启失败");
            }

            @Override
            public void onDisconnect() {
                Log.i(TAG, "server 连接断开");

                resetBTManager();
            }
        });

    }

    public void resetBTManager() {
        Log.i(TAG, "resetBTManager: 重置蓝牙管理器");
        if (btManager != null) {
            btManager.close();
            btManager = null;
        }
        initBTManager();
    }

    public void onClick(View view) {
        btManager.writeMsgFromServer("ahahfdalejfld总过".getBytes());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (btManager != null) {
            btManager.close();
        }
    }
}
