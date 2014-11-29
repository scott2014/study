package com.yhj.app.downloader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yhj.app.downloader.api.DownloadListener;
import com.yhj.app.downloader.api.Downloader;

import org.w3c.dom.Text;

import java.io.File;


public class MainActivity extends Activity implements DownloadListener {
    private EditText mAddressEdit;

    @Override
    public void onDownloadComplete(String fileName, String fileType) {
        this.mStatus.setText(R.string.download_complete);
    }

    private TextView mProgressText;
    private TextView mFilename;
    private TextView mFilesize;
    private TextView mStatus;
    private ProgressBar mProgressBar;
    private Button mDownloadBtn;
    private Button mCancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mAddressEdit = (EditText) this.findViewById(R.id.et_address);
        this.mProgressText = (TextView) this.findViewById(R.id.tv_progress);
        this.mFilename = (TextView) this.findViewById(R.id.tv_filename);
        this.mFilesize = (TextView) this.findViewById(R.id.tv_filesize);
        this.mStatus = (TextView) this.findViewById(R.id.tv_status);
        this.mDownloadBtn = (Button) this.findViewById(R.id.btn_download);
        this.mCancelBtn = (Button) this.findViewById(R.id.btn_cancel);
        this.mProgressBar = (ProgressBar) this.findViewById(R.id.progress);

        this.mDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Downloader.get().createTask(16,mAddressEdit.getText().toString(),"aaa.apk",MainActivity.this);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getFilePath() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/a2014/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    @Override
    public void onGetFileSizeComplete(long fileSize, String fileName, String fileType) {
        this.mFilesize.setText(fileSize + "bytes");
        this.mFilename.setText(fileName);
    }

    @Override
    public void onDownload(long fileSize, long completeSize, String savePath, String saveName) {
        if (fileSize != completeSize) {
            this.mStatus.setText(R.string.downloading);
        }
        Log.e("fileSize",fileSize + "");
        Log.e("completeSize", completeSize + "");
        double c = completeSize;
        double f = fileSize;
        int per = (int)((c / f) * 100);
        mProgressBar.setProgress(per);
        mProgressText.setText(per + "%");
    }

    @Override
    public void onDownloadFail(int errorCode, String fileName, String fileType) {

    }
}
