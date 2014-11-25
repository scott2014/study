package com.yhj.app.downloader.api;

import android.os.Handler;
import android.os.Message;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * Created by scott on 14-11-14.
 */
public class DownloadThread extends Thread {
    private Handler mHandler;
    private String url;
    private String startPos;
    private String endPos;
    private String fileSize;
    private String fileName;
    private String path;
    private RandomAccessFile accessFile;

    public DownloadThread(Handler handler,String url,String startPos,String endPos,String fileSize,String fileName,String path,RandomAccessFile accessFile) {
        mHandler = handler;
        this.url = url;
        this.startPos = startPos;
        this.endPos = endPos;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.path = path;
        this.accessFile = accessFile;
    }

    @Override
    public void run() {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse response = client.execute(get);
            InputStream is = response.getEntity().getContent();
            byte[] buffer = new byte[1024];
            int i = -1;
       //     RandomAccessFile accessFile = new RandomAccessFile(new File(path + File.separator + fileName),"rwd");
        //    accessFile.setLength(Long.parseLong(fileSize));
        //    accessFile.seek(Long.parseLong(startPos));
            while ((i = (is.read(buffer))) != -1) {
                accessFile.write(buffer,0,i);
                Message msg = new Message();
                msg.what = i;
                mHandler.sendMessage(msg);
            }
            accessFile.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
