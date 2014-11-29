package com.yhj.app.downloader.api;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * Created by scott on 14-11-14.
 */
public class DownloadThread extends Thread {
    private Handler mHandler;
    private String url;
    private long startPos;
    private long endPos;
    private long fileSize;
    private File file;
    private RandomAccessFile accessFile;
    private Downloader downloader;

    public DownloadThread(Handler handler,String url,long startPos,long endPos,long fileSize,File file,Downloader downloader) {
        mHandler = handler;
        this.url = url;
        this.startPos = startPos;
        this.endPos = endPos;
        this.fileSize = fileSize;
        this.file = file;
        this.downloader = downloader;
        Log.e("startPos>>>>>>>>>>>>>>>>>",startPos + "");
        Log.e("endPos>>>>>>>>>>>>>>>>>>>",endPos + "");
        try {
            this.accessFile = new RandomAccessFile(file,"rwd");
            this.accessFile.setLength(fileSize);
          //  this.accessFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        try {
            get.setHeader("Range","bytes=" + startPos + "-" + endPos);
            HttpResponse response = client.execute(get);
            InputStream is = response.getEntity().getContent();
            byte[] buffer = new byte[1024];
            int i = -1;
            accessFile.seek(startPos);
            while ((i = (is.read(buffer))) != -1) {
                accessFile.write(buffer,0,i);
                Log.e("i", i + "");
                downloader.append(i);
                Message msg = new Message();
                //msg.what = i;
                msg.what = DownloadConst.DOWNLOADING;
                mHandler.sendMessage(msg);
            }
            accessFile.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
