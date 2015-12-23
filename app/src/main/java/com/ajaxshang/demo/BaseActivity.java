package com.ajaxshang.demo;

import android.app.Activity;
import android.os.Bundle;

import com.ajaxshang.demo.utils.Download;

/**
 * Created by Administrator on 2015/12/22.
 */
public class BaseActivity extends Activity {
    Download download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Download download = new Download(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        download.close();
    }
}
