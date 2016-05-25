package com.example.huangyi.myanimation;

import android.os.Environment;

/**
 * Created by huangyi on 16/5/13.
 */
public class Constants {
    /**
     * 文件夹路径
     */
    public static final String ROOT_PATH = "/LIUDA";
    public static final String IMAGE_PATH = ROOT_PATH + "/IMAGE/";
    public static final String DOWNLOAD_PATH = ROOT_PATH + "/DOWNLOAD/";
    public static final String CHOOSE_PHOTO = Environment.getExternalStorageDirectory() + Constants.IMAGE_PATH + "liudaUserIcon.jpg";
    public static final String CUT_PHOTO = Environment.getExternalStorageDirectory() + Constants.IMAGE_PATH + "liudaUserCutIcon.jpg";

}
