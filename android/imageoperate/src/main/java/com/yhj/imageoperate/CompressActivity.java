package com.yhj.imageoperate;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class CompressActivity extends Activity {
    private ImageView mImgBefore;
    private ImageView mImgAfter;

    private Button mCompressBtn;
    private Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compress);

        this.mImgBefore = (ImageView) this.findViewById(R.id.img_before);
        this.mImgAfter = (ImageView) this.findViewById(R.id.img_after);

        this.mCompressBtn = (Button) this.findViewById(R.id.btn_compress);

        try {
            bm = BitmapFactory.decodeStream(getAssets().open("a.jpg"));
            this.mImgBefore.setImageBitmap(bm);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.mCompressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Bitmap bm = compressBitmap(getSdcard() + "/a.jpg", 200, 120);
               File file = new File(getSdcard() + "/a123.jpg");
                OutputStream out = null;
                try {
                    out = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bm.compress(Bitmap.CompressFormat.JPEG,100,out);
               mImgAfter.setImageBitmap(bm);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compress, menu);
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

    private String getSdcard() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    private int calculateInSampleSize(BitmapFactory.Options opt,int reqWidth,int reqHeight) {
        final int height = opt.outHeight;
        final int width = opt.outWidth;

        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / reqHeight);
            final int widthRatio = Math.round((float)width/reqWidth);


            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    private Bitmap compressBitmap(String filePath,int reqWidth,int reqHeight) {
        final BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath,opt);

        opt.inSampleSize = calculateInSampleSize(opt,reqWidth,reqHeight);

        opt.inJustDecodeBounds = false;

        Bitmap bm = BitmapFactory.decodeFile(filePath,opt);

        if (bm == null) return null;

        int degree = readPictureDegree(filePath);

        bm = rotateBitmap(bm,degree);

        ByteArrayOutputStream baos = null;

        baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,30,baos);

        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bm;
    }

    //图片旋转
    private Bitmap rotateBitmap(Bitmap bitmap,int rotate) {
        if (bitmap == null) return null;

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix mtx = new Matrix();
        mtx.postRotate(rotate);

        return Bitmap.createBitmap(bitmap,0,0,w,h,mtx,true);
    }

    private static int readPictureDegree(String path) {
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
}
