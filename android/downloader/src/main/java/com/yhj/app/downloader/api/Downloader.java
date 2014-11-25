package com.yhj.app.downloader.api;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
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
 * Created by scott on 14-11-12.
 */
public class Downloader {
    private static Downloader instance;
    private DownloadListener listener;
    private long completeSize;
    private int taskCount;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            completeSize += msg.what;
            listener.onDownload(fileSize,(int)((double)completeSize / taskCount),"","");
        }
    };
    private Handler mDownloadHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DownloadConst.DOWNLOAD_BEGIN:
                    listener.onGetFileSizeComplete(fileSize,"","");
                    break;
            }
        }
    };

    private long fileSize;


    private Downloader() {}

    public static Downloader get() {
        if (instance == null) {
            instance = new Downloader();
        }
        return instance;
    }

    public void createTask(final int taskCount,final String url, final String path, final String name, final DownloadListener listener) {
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
                    //listener.onGetFileSizeComplete(fileSize,"",response.getEntity().getContentType().getValue().split("/")[1]);
                    File file = new File(path + File.separator + name);

                    Message msgObj = new Message();
                    msgObj.what = DownloadConst.GET_FILE_SIZE;
                    mDownloadHandler.sendMessage(msgObj);

                    long range = fileSize / taskCount;
                    for (int i=0;i<taskCount;i++) {
                   //     new DownloadTask(mHandler).execute(url, range * i + "", (range * (i + 1) - 1) + "", fileSize + "", "aaaa.apk",path);
                        RandomAccessFile accessFile = new RandomAccessFile(file,"rwd");
                        accessFile.setLength(fileSize);
                        accessFile.seek(range * i);
                        new DownloadThread(mHandler,url,range * i + "", (range * (i + 1) - 1) + "",fileSize + "",name,path,accessFile).start();
                    }

                } catch (IOException e) {
                    Log.e(Downloader.class.getCanonicalName(),e.getMessage() + "");
                 //   listener.onDownloadFail(1,"","");
                }
            }
        };
        handler.sendEmptyMessage(0);
    }


}
