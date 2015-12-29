package com.ajaxshang.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ajaxshang.demo.entity.MessageInfo;
import com.ajaxshang.demo.utils.Download;

public class MainActivity extends Activity {

    public static final String DOWNLOAD_FOLDER_NAME = "AjaxShang/11";
    String TAG = "DownLoad";
    String path = "http://123.57.218.63:9008/download/PIE/AppAndroid/PiePlus.apk";
    String name = "PiePlus.apk";
    Button start_btn;
    Button cancel_btn;
    Button pause_btn;
    Button resume_btn;
    Button another_btn;
    long id = 0;
    Download download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start_btn = (Button) this.findViewById(R.id.start_btn);
        cancel_btn = (Button) this.findViewById(R.id.cancel_btn);
        pause_btn = (Button) this.findViewById(R.id.pause_btn);
        resume_btn = (Button) this.findViewById(R.id.resume_btn);
        another_btn = (Button) this.findViewById(R.id.another_btn);
        download = new Download(this);
        another_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DownloadActivity.class);
                startActivity(intent);

            }
        });
        start_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                download.down(path, DOWNLOAD_FOLDER_NAME, new Download.DownloadCallBack() {

                    @Override
                    public void onSuccess(MessageInfo info) {
//                        Log.d(TAG, "onSuccess:" + downloadId + "");
                    }

                    @Override
                    public void onFailure(int errorCode) {
                        Log.d(TAG, "onFailed:" + errorCode + "");
                    }

                    @Override
                    public void onStart(long downloadId) {
                        Log.d(TAG, "onStart:" + downloadId + "");
                        id = downloadId;
                    }

                    @Override
                    public void onProgress(long id, long current, long total, long status) {
                        // TODO Auto-generated method stub
                        Log.d(TAG, "onProgress:" + current + "/" + total + " status:" + status);
                    }

                });
            }
        });

        cancel_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.d(TAG, "remove:" + id);
                download.remove(id);
            }
        });

        pause_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.d(TAG, "pause:" + id);
                download.pauseDownload(id);
            }
        });

        resume_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.d(TAG, "resume:" + id);
                download.resumeDownload(id);
            }
        });

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
//        download.close();
    }

}
