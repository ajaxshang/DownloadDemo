package com.ajaxshang.demo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ajaxshang.demo.adadpter.DownloadAdapter;
import com.ajaxshang.demo.entity.DownloadInfo;
import com.ajaxshang.demo.frgs.DownListFragment;
import com.ajaxshang.demo.frgs.DownManagerFragment;
import com.ajaxshang.demo.utils.Download;

import java.util.ArrayList;
import java.util.List;

public class DownloadActivity extends FragmentActivity {

    Download download;
    //    private ListView listview;
    private DownloadAdapter adapter;
    private List<DownloadInfo> list = new ArrayList<>();
    private List<DownloadInfo> loadingList = new ArrayList<>();
    private TextView manager_tv;
    private TextView list_tv;

    private FragmentManager frgManager;
    private FragmentTransaction transaction;

    private LinearLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cityattractions);
        manager_tv = (TextView) this.findViewById(R.id.manager_tv);
        list_tv = (TextView) this.findViewById(R.id.list_tv);
        content = (LinearLayout) this.findViewById(R.id.line_content);

        frgManager = getSupportFragmentManager();
        transaction = frgManager.beginTransaction();

//        listview = (ListView) this.findViewById(R.id.listview);
        download = new Download(this);
        adapter = new DownloadAdapter(this);
//        initList();
//        adapter.addAll(list);
//        listview.setAdapter(adapter);

        final DownListFragment downListFragment = new DownListFragment();
        final DownManagerFragment downManagerFragment = new DownManagerFragment();
        transaction.add(R.id.line_content, downListFragment);
        transaction.commit();

        list_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                transaction = frgManager.beginTransaction();
//                transaction.replace(R.id.line_content, downListFragment);
//                transaction.commit();
            }
        });
        manager_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                transaction = frgManager.beginTransaction();
//                transaction.replace(R.id.line_content, downManagerFragment);
//                transaction.commit();
            }
        });

    }


//    private void getLoadingList() {
//        for (DownloadInfo info : list) {
//            if (info.getStatus() == 2) {
//                loadingList.add(info);
//            }
//        }
//    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
//        download.close();
    }

}
