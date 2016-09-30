package com.huizetime.basketballtv.utils;

import android.content.Context;

import java.io.File;

/**
 * Created by water_fairy on 2016/9/30.
 */

public class FileUtils {
    public static final int A_LOGO = 1;
    public static final int B_LOGO = 2;
    public static final int QR_CODE = 3;

    public static String getImgPath(Context context, int code) {

        String path = context.getCacheDir() + File.separator;
        switch (code) {
            case A_LOGO:
                path = path + "a_logo.jpg";
                break;
            case B_LOGO:
                path = path + "b_logo.jpg";
                break;
            case QR_CODE:
                path = path + "qr_code.jpg";
                break;
        }
        return path;
    }

    public static File getImg(Context context, int code) {
        return new File(getImgPath(context, code));

    }
}
