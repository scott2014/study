package com.yhj.imageoperate;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends Activity {
    private Button mCropBtn;
    private Button mCompressBtn;
    private ImageView mImgBefore;
    private ImageView mImgAfter;
    private Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mCropBtn = (Button) this.findViewById(R.id.btn_crop);
        this.mCompressBtn = (Button) this.findViewById(R.id.btn_compress);
        this.mImgBefore = (ImageView) this.findViewById(R.id.img_before);
        this.mImgAfter = (ImageView) this.findViewById(R.id.img_after);

        AssetManager am = getAssets();
        InputStream is = null;
        try {
            is = am.open("a.jpg");
            bm = BitmapFactory.decodeStream(is);
            this.mImgBefore.setImageBitmap(bm);
        } catch (IOException e) {
            e.printStackTrace();
        }



        this.mCropBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bm.getWidth() / 2 > 0 && bm.getHeight() / 2 > 0) {
                    bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth() / 2, bm.getHeight() / 2);
                    mImgAfter.setImageBitmap(bm);
                }
            }
        });

        this.mCompressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this, CompressActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
