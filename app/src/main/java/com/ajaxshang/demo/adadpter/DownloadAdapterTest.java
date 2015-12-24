package com.ajaxshang.demo.adadpter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ajaxshang.demo.R;
import com.ajaxshang.demo.entity.DownloadInfo;
import com.ajaxshang.demo.entity.MessageInfo;
import com.ajaxshang.demo.utils.Download;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/24.
 */
public class DownloadAdapterTest extends BaseAdapter {

    public static final int MB_2_BYTE = 1024 * 1024;
    public static final int KB_2_BYTE = 1024;
    static final DecimalFormat DOUBLE_DECIMAL_FORMAT = new DecimalFormat("0.##");
    List<DownloadInfo> infos = new ArrayList<>();
    Context context;

    public DownloadAdapterTest(List<DownloadInfo> infos, Context context) {
        this.infos = infos;
        this.context = context;
    }

    public static String getNotiPercent(long progress, long max) {
        int rate = 0;
        if (progress <= 0 || max <= 0) {
            rate = 0;
        } else if (progress > max) {
            rate = 100;
        } else {
            rate = (int) ((double) progress / max * 100);
        }
        return new StringBuilder(16).append(rate).append("%").toString();
    }

    public static CharSequence getAppSize(long size) {
        if (size <= 0) {
            return "0M";
        }

        if (size >= MB_2_BYTE) {
            return new StringBuilder(16).append(DOUBLE_DECIMAL_FORMAT.format((double) size / MB_2_BYTE)).append("M");
        } else if (size >= KB_2_BYTE) {
            return new StringBuilder(16).append(DOUBLE_DECIMAL_FORMAT.format((double) size / KB_2_BYTE)).append("K");
        } else {
            return size + "B";
        }
    }

    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public Object getItem(int position) {
        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.downloaditem, null);
            viewHolder.name_tv = (TextView) convertView.findViewById(R.id.name_tv);
            viewHolder.down_btn = (Button) convertView.findViewById(R.id.down_btn);
            viewHolder.size_tv = (TextView) convertView.findViewById(R.id.size_tv);
            viewHolder.status_tv = (TextView) convertView.findViewById(R.id.status_tv);
            viewHolder.progressBar = (ProgressBar) convertView.findViewById(R.id.down_progress);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final DownloadInfo info = infos.get(position);
        final Download download = new Download(context);
        if (info != null) {
            viewHolder.name_tv.setText(info.getName() + position);

            final ViewHolder finalViewHolder = viewHolder;
            convertView.setTag(finalViewHolder);
            viewHolder.down_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    download.down(info, new Download.DownloadCallBack() {
                        @Override
                        public void onStart(long downloadId) {
                            Log.d("Adapter", "onStart:" + downloadId + "");
                            finalViewHolder.progressBar.setVisibility(View.VISIBLE);
                            finalViewHolder.status_tv.setVisibility(View.VISIBLE);
                            finalViewHolder.size_tv.setVisibility(View.VISIBLE);

                        }

                        @Override
                        public void onProgress(long current, long total, long status) {
//                            Log.d("Adapter", "onProgress " + position + ":" + getAppSize(current) + "/" + getAppSize(total));
                            finalViewHolder.size_tv.setText("正在下载");
                            finalViewHolder.down_btn.setText(getAppSize(current) + "/" + getAppSize(total));
                            finalViewHolder.progressBar.setMax((int) total);
                            finalViewHolder.progressBar.setProgress((int) current);
//                            mListData.get(position).setStatus((int) status);
                        }

                        @Override
                        public void onFailure(int errorCode) {

                        }

                        @Override
                        public void onSuccess(MessageInfo info) {
                            finalViewHolder.progressBar.setVisibility(View.INVISIBLE);
                            finalViewHolder.size_tv.setVisibility(View.INVISIBLE);
                            finalViewHolder.status_tv.setText("下载成功");
                            finalViewHolder.down_btn.setText("安装");
                        }

                    });
                }
            });
        }

        return convertView;
    }

    static class ViewHolder {
        TextView name_tv;
        TextView size_tv;
        TextView status_tv;
        Button down_btn;
        ProgressBar progressBar;
    }
}
