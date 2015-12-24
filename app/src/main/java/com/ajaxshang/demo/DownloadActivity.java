package com.ajaxshang.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.ajaxshang.demo.adadpter.DownloadAdapter;
import com.ajaxshang.demo.utils.Download;
import com.ajaxshang.demo.utils.DownloadInfo;

import java.util.ArrayList;
import java.util.List;

public class DownloadActivity extends Activity {

    Download download;
    private ListView listview;
    private DownloadAdapter adapter;
    private List<DownloadInfo> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cityattractions);
        listview = (ListView) this.findViewById(R.id.listview);
        download = new Download(this);
        adapter = new DownloadAdapter(this);
        initList();
        adapter.addAll(list);
        listview.setAdapter(adapter);

    }

    private void initList() {
        DownloadInfo info = new DownloadInfo();
        info.setName("酷狗音乐");
        info.setSavePath("AjaxShang");
        info.setDownloadPath("http://soft.duote.com.cn/kugou_8.0.19.18186.exe");
        list.add(info);

        DownloadInfo info1 = new DownloadInfo();
        info1.setName("豌豆荚");
        info1.setSavePath("AjaxShang");
        info1.setDownloadPath("https://sm.wdjcdn.com/release/files/jupiter/5.9.1.8951/wandoujia-wandoujia_web.apk");
        list.add(info1);

        DownloadInfo info2 = new DownloadInfo();
        info2.setName("Pie+");
        info2.setSavePath("AjaxShang");
        info2.setDownloadPath("http://123.57.218.63:9008/download/PIE/AppAndroid/PiePlus.apk");
        list.add(info2);

        DownloadInfo info3 = new DownloadInfo();
        info3.setName("酷我音乐");
        info3.setSavePath("AjaxShang");
        info3.setDownloadPath("http://soft.duote.com.cn/kwmusic.exe");
        list.add(info3);

        DownloadInfo info4 = new DownloadInfo();
        info4.setName("百度音乐");
        info4.setSavePath("AjaxShang");
        info4.setDownloadPath("http://soft.duote.com.cn/baidumusic.exe");
        list.add(info4);

//
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
//        download.close();
    }

}
