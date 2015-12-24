package com.ajaxshang.demo.frgs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ajaxshang.demo.R;
import com.ajaxshang.demo.adadpter.DownloadAdapter;
import com.ajaxshang.demo.entity.DownloadInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/24.
 * <p/>
 * 问题：
 * 1.进行下载时上下滑动listview出现显示错位现象，progressbar不显示，下载数据显示在错误位置
 * 2.切换fragment时显示也出现问题，怀疑是切换fragment时没有保存数据
 */
public class DownListFragment extends Fragment {

    private ListView listView;
    private List<DownloadInfo> list = new ArrayList<>();
    private DownloadAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_downloadlist, null);
        listView = (ListView) view.findViewById(R.id.download_list);
        initList();
        adapter = new DownloadAdapter(getActivity());
        adapter.addAll(list);
        listView.setAdapter(adapter);
        return view;
    }

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

}
