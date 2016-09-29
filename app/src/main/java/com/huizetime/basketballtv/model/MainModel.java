package com.huizetime.basketballtv.model;

/**
 * Created by water_fairy on 2016/9/27.
 */
public interface MainModel {
    void initData();

    void setAsServer();

    void close();

    void operateData(String json);

    void closeBT();
}
