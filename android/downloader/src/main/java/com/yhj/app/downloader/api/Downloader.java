package com.yhj.app.downloader.api;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author scott
 * 下载任务类,主要负责拉起多线程,完成下载任务.同时通知监听器数据变化
 *
 */
public class Downloader {
    private static Downloader instance;
    private DownloadListener listener;
    private long completeSize;
    private int taskCount;
    private String fileName;
    private String fileType;

    private Handler mDownloadHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DownloadConst.GET_FILE_SIZE:
                    listener.onGetFileSizeComplete(fileSize,fileName,fileType);
                    break;
                case DownloadConst.DOWNLOADING:
                    listener.onDownload(fileSize,completeSize,fileName,fileType);
                    break;
            }
        }
    };

    private long fileSize;


    private Downloader() {}

    public static Downloader get() {
        /*if (instance == null) {
            instance = new Downloader();
        }*/
        return new Downloader();
    }

    public void createTask(final int taskCount,final String url, final String name, final DownloadListener listener) {
        //获取文件名称和文件类型
        String fileName = getFileName(url);
        String fileType = getFileType(url);
        this.fileName = fileName;
        this.fileType = fileType;

        if (!checkSdcardMountStatus()) {
            listener.onDownloadFail(DownloadConst.SDCARD_NOT_MOUNTED,fileName,fileType);
            return;
        }

        completeSize = 0;
        this.listener = listener;
        this.taskCount = taskCount;
        HandlerThread thread = new HandlerThread(url);
        thread.start();
        Handler handler = new Handler(thread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(url);
                try {
                    HttpResponse response = client.execute(get);
                    fileSize = response.getEntity().getContentLength();

                    //1\获取文件尺寸完成
                    Message msgObj = new Message();
                    msgObj.what = DownloadConst.GET_FILE_SIZE;
                    mDownloadHandler.sendMessage(msgObj);

                    //2\开始分段下载
                    File file = new File(getSdCardPath() + File.separator + name);
                    //创建文件失败
                    if (!createFile(file)) {
                        Message msgObj0 = new Message();
                        msgObj0.what = DownloadConst.CREATE_FILE_FAIL;
                        mDownloadHandler.sendMessage(msgObj0);
                        return;
                    }

                    long range = fileSize / taskCount;
                    for (int i=0;i<taskCount;i++) {
                        long startPos = range * i;
                        long endPos;
                        if (i == taskCount - 1) {
                            endPos = fileSize;
                        } else {
                            endPos = range * (i + 1) - 1;
                        }
                        new DownloadThread(mDownloadHandler,url,startPos,endPos,fileSize,file,Downloader.this).start();
                    }

                } catch (IOException e) {
                    Log.e(Downloader.class.getCanonicalName(),e.getMessage() + "");
                    Message msgObj = new Message();
                    msgObj.what = DownloadConst.DOWNLOAD_FAIL;
                    mDownloadHandler.sendMessage(msgObj);
                }
            }
        };
        handler.sendEmptyMessage(0);
    }

   //检测内存卡是否存在
    private boolean checkSdcardMountStatus() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    //通过url获取文件名称
    private String getFileName(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        int index = url.lastIndexOf("/");
        if (index == -1) {
            return null;
        } else {
            return url.substring(index + 1);
        }
    }

    //通过url获取文件类型
    private String getFileType(String url) {
        String fileName = getFileName(url);
        if (!TextUtils.isEmpty(fileName)) {
            int index = fileName.lastIndexOf(".");
            if (index == -1) return null;
            return fileName.substring(index + 1);
        } else {
            return null;
        }
    }

    //获取SDCARD路径
    private String getSdCardPath() {
        if (checkSdcardMountStatus()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }

    //创建文件
    private boolean createFile(File file) {
        File parent = new File(file.getParent());
        try {
            if (!parent.exists()) {
                parent.mkdirs();
                file.createNewFile();
            } else {
                file.createNewFile();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //下载尺寸
    public synchronized void append(long size) {
        completeSize += size;
        if (completeSize == fileSize) {
            listener.onDownloadComplete(fileName,fileType);
        }
    }
}
