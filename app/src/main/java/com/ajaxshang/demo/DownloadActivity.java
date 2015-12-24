package com.ajaxshang.demo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ListView;
import android.widget.TextView;

import com.ajaxshang.demo.adadpter.DownloadAdapterTest;
import com.ajaxshang.demo.entity.DownloadInfo;
import com.ajaxshang.demo.utils.Download;

import java.util.ArrayList;
import java.util.List;

public class DownloadActivity extends FragmentActivity {

    Download download;
    private ListView listview;
    private DownloadAdapterTest adapter;
    private List<DownloadInfo> list = new ArrayList<>();
    private List<DownloadInfo> loadingList = new ArrayList<>();
    private TextView manager_tv;
    private TextView list_tv;

    private FragmentManager frgManager;
    private FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cityattractions);
        manager_tv = (TextView) this.findViewById(R.id.manager_tv);
        list_tv = (TextView) this.findViewById(R.id.list_tv);

        frgManager = getSupportFragmentManager();
        transaction = frgManager.beginTransaction();

        listview = (ListView) this.findViewById(R.id.listview);
        download = new Download(this);

        initList(20);
//        adapter.addAll(list);
        adapter = new DownloadAdapterTest(list, this);
        listview.setAdapter(adapter);

//        final DownListFragment downListFragment = new DownListFragment();
//        final DownManagerFragment downManagerFragment = new DownManagerFragment();
//        transaction.add(R.id.line_content, downListFragment);
//        transaction.commit();
//
//        list_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                transaction = frgManager.beginTransaction();
////                transaction.replace(R.id.line_content, downListFragment);
////                transaction.commit();
//            }
//        });
//        manager_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                transaction = frgManager.beginTransaction();
////                transaction.replace(R.id.line_content, downManagerFragment);
////                transaction.commit();
//            }
//        });

    }


//    private void getLoadingList() {
//        for (DownloadInfo info : list) {
//            if (info.getStatus() == 2) {
//                loadingList.add(info);
//            }
//        }
//    }

    private void initList() {


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

        DownloadInfo info5 = new DownloadInfo();
        info5.setName("酷狗音乐");
        info5.setSavePath("AjaxShang");
        info5.setDownloadPath("http://soft.duote.com.cn/kugou_8.0.19.18186.exe");
        list.add(info5);


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

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
//        download.close();
    }

}
