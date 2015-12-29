package com.ajaxshang.demo;

import android.app.Activity;
import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ajaxshang.demo.adadpter.DownloadAdapterTest;
import com.ajaxshang.demo.callback.UpdateCallback;
import com.ajaxshang.demo.entity.DownloadInfo;
import com.ajaxshang.demo.utils.Download;

import java.util.ArrayList;
import java.util.List;
//

public class DownloadActivity extends Activity implements UpdateCallback {

    Download download;
    private ListView listview;
    private DownloadAdapterTest adapterTest;
    private List<DownloadInfo> list = new ArrayList<>();
    private TextView manager_tv;
    private TextView list_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cityattractions);
        manager_tv = (TextView) this.findViewById(R.id.manager_tv);
        list_tv = (TextView) this.findViewById(R.id.list_tv);
        listview = (ListView) this.findViewById(R.id.listview);
        initList(20);

        download = new Download(this);
        adapterTest = new DownloadAdapterTest(list, this);
        adapterTest.setCallback(this);

        listview.setAdapter(adapterTest);

    }

    private void initList(int total) {
        for (int i = 0; i < total; i++) {
            DownloadInfo info = new DownloadInfo();
            info.setName("豌豆荚");
            info.setSavePath("AjaxShang");
            info.setDownloadPath("https://sm.wdjcdn.com/release/files/jupiter/5.9.1.8951/wandoujia-wandoujia_web.apk");
            list.add(info);
        }
    }

    private int updateProgressPartly(int position, long downloadId) {
        int[] bytesAndStatus = Download.getBytesAndStatus(downloadId);
        if (bytesAndStatus[2] == DownloadManager.STATUS_RUNNING) {
            Log.i("TAG", bytesAndStatus[0] + "/" + bytesAndStatus[1] + "stutas:" + bytesAndStatus[2]);
            int firstVisiblePosition = listview.getFirstVisiblePosition();
            int lastVisiblePosition = listview.getLastVisiblePosition();
            View view = null;
            if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
                view = listview.getChildAt(position - firstVisiblePosition);
                if (view.getTag() instanceof DownloadAdapterTest.ViewHolder) {
                    DownloadAdapterTest.ViewHolder vh = (DownloadAdapterTest.ViewHolder) view.getTag();
                    vh.progressBar.setMax(bytesAndStatus[1]);
                    vh.progressBar.setProgress(bytesAndStatus[0]);
                }
            }
        }
        return bytesAndStatus[2];
    }

    @Override
    public void startProgress(final long downloadId, final int position) {
        new Thread(new WatchDownloadRunnable(downloadId, position)).start();
    }


    class WatchDownloadRunnable implements Runnable {
        long downloadId;
        int position;

        public WatchDownloadRunnable(long downloadId, int position) {
            this.downloadId = downloadId;
            this.position = position;
        }

        @Override
        public void run() {
            while (true) {
                int status = updateProgressPartly(position, downloadId);
                switch (status) {
                    case DownloadManager.STATUS_SUCCESSFUL:
                        this.close();
                        break;
                }
            }
        }

        public void close() {
            close();
        }
    }
}
