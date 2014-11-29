package com.yhj.app.downloader.api;

/**
 * Created by scott on 14-11-12.
 */
public interface DownloadListener {
    public void onGetFileSizeComplete(long fileSize,String fileName,String fileType);
    public void onDownload(long fileSize,long completeSize,String savePath,String saveName);
    public void onDownloadFail(int errorCode,String fileName,String fileType);
    public void onDownloadComplete(String fileName,String fileType);
}
