package com.yhj.app.downloader;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends Activity {
    private EditText mAddressEdit;
    private TextView mProgressText;
    private ProgressBar mProgressBar;
    private Button mDownloadBtn;
    private Button mCancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mAddressEdit = (EditText) this.findViewById(R.id.et_address);
        this.mProgressText = (TextView) this.findViewById(R.id.tv_progress);
        this.mDownloadBtn = (Button) this.findViewById(R.id.btn_download);
        this.mCancelBtn = (Button) this.findViewById(R.id.btn_cancel);
        this.mProgressBar = (ProgressBar) this.findViewById(R.id.progress);


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
}
