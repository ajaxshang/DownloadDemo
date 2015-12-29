package com.ajaxshang.demo;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Administrator on 2015/12/25.
 */
public class DownloadService extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        DownloadInfo info = intent
        DownloadManager manager = (DownloadManager) this.getSystemService(this.DOWNLOAD_SERVICE);
//        DownloadManager.Request down = new DownloadManager.Request(Uri.parse(path));


        return super.onStartCommand(intent, flags, startId);


    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
