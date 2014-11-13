package com.yhj.app.downloader.api;

import android.os.AsyncTask;
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
 * Created by scott on 14-11-12.
 */
public class DownloadTask extends AsyncTask<String,Long,Long> {
    private Handler mHandler;

    public DownloadTask(Handler handler) {
        mHandler = handler;
    }

    @Override
    protected Long doInBackground(String... params) {
        String url = params[0];
        String startPos = params[1];
        String endPos = params[2];
        String fileSize = params[3];
        String fileName = params[4];
        String path = params[5];

        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse response = client.execute(get);
            InputStream is = response.getEntity().getContent();
            byte[] buffer = new byte[1024];
            int i = -1;
            RandomAccessFile accessFile = new RandomAccessFile(new File(path + File.separator + fileName),"rwd");
            accessFile.setLength(Long.parseLong(fileSize));
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

        return null;
    }
}
