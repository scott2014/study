package com.yhj.app.downloader.api;

/**
 * Created by scott on 2014/11/13.
 */
public class DownloadConst {
    public static final int GET_FILE_SIZE = 0x1;             //获取文件尺寸
    public static final int DOWNLOAD_BEGIN = 0x2;            //下载开始
    public static final int DOWNLOADING = 0x3;
    public static final int DOWNLOAD_FAIL = 0x4;             //下载失败
    public static final int CREATE_FILE_FAIL = 0x5;          //文件创建失败


    public static final int SDCARD_NOT_MOUNTED = 1;              //SD卡未找到
    public static final int NET_CONNECTED_FAIL = 2;              //网络连接失败
}
