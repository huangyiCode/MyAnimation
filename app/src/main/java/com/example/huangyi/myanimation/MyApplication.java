package com.example.huangyi.myanimation;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.example.huangyi.myanimation.utils.DisplayUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.io.File;


/**
 * 主要用作第三方初始化
 * Created by wy on 2015/10/15.
 */
public class MyApplication extends Application  {
    public static ImageLoader imageLoader;
    public static int heightPixels;
    public static int widthPixels;

    @Override
    public void onCreate() {
        super.onCreate();
        // 获取屏幕高度
        heightPixels = DisplayUtils.getScreenHeight(this);
        // 获取屏幕宽度
        widthPixels = DisplayUtils.getScreenWidth(this);
        //初始化ImageLoader
        initImageLoader();
        //创建项目文件夹
        CreateFile();
    }


    private void initImageLoader() {
        // Create default options which will be used for every
        // displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher) // resource
                .showImageForEmptyUri(R.mipmap.ic_launcher) // resource or
                // drawable
                .showImageOnFail(R.mipmap.ic_launcher) // resource or
//                         drawable
                .displayer(new FadeInBitmapDisplayer(200, true, false, false)).cacheInMemory(true).cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(widthPixels, heightPixels) // default
                .diskCacheExtraOptions(widthPixels, heightPixels, null).defaultDisplayImageOptions(defaultOptions)
                .build();

        ImageLoader.getInstance().init(config); // Do it on Application start
        imageLoader = ImageLoader.getInstance();
//     Then later, when you want to display image
//     DiningMasterApplication.imageLoader.displayImage( String imageUrl,
//     ImageView imageView);

    }

    private void CreateFile() {
        //SDCard路径
        final String SdcardPath =
                Environment.getExternalStorageDirectory().getAbsolutePath();
        //项目文件夹
        final String RootPath = SdcardPath + Constants.ROOT_PATH;
        //image文件夹
        final String ImagePath = SdcardPath + Constants.IMAGE_PATH;
        //download文件夹
        final String DownloadPath = SdcardPath + Constants.DOWNLOAD_PATH;

        File dir = new File(RootPath);

        if (!dir.exists()) {
            dir.mkdirs();
        }
        File image = new File(ImagePath);
        if (!image.exists()) {
            image.mkdirs();
        }
        File download = new File(DownloadPath);

        if (!download.exists()) {
            download.mkdirs();
        }
    }

}
