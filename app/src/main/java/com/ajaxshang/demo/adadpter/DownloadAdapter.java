package com.ajaxshang.demo.adadpter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ajaxshang.demo.R;
import com.ajaxshang.demo.entity.MessageInfo;
import com.ajaxshang.demo.utils.Download;
import com.ajaxshang.demo.entity.DownloadInfo;

import java.text.DecimalFormat;

public class DownloadAdapter extends AbstractBaseAdapter<DownloadInfo> {

    public static final int MB_2_BYTE = 1024 * 1024;
    public static final int KB_2_BYTE = 1024;
    static final DecimalFormat DOUBLE_DECIMAL_FORMAT = new DecimalFormat("0.##");
    private Context context;

    public DownloadAdapter(Context context) {
        super(context);
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
    public int getItemResource() {
        return R.layout.downloaditem;
    }

    @Override
    public View getItemView(final int position, View convertView) {
        TextView name_tv = ViewHolder.get(convertView, R.id.name_tv);
        final TextView status_tv = ViewHolder.get(convertView, R.id.status_tv);
        final TextView size_tv = ViewHolder.get(convertView, R.id.size_tv);
        final Button down = ViewHolder.get(convertView, R.id.down_btn);
        final ProgressBar progressBar = ViewHolder.get(convertView, R.id.down_progress);

        progressBar.setVisibility(View.INVISIBLE);
        status_tv.setVisibility(View.INVISIBLE);
        size_tv.setVisibility(View.INVISIBLE);

        final Download download = new Download(context);
        name_tv.setText(mListData.get(position).getName());
        down.setText("下载");
        down.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                download.down(mListData.get(position), new Download.DownloadCallBack() {


                    @Override
                    public void onStart(long downloadId) {
                        Log.d("Adapter", "onStart:" + downloadId + "");
                        progressBar.setVisibility(View.VISIBLE);
                        status_tv.setVisibility(View.VISIBLE);
                        size_tv.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onProgress(long current, long total, long status) {
                        Log.d("Adapter", "onProgress " + position + ":" + getAppSize(current) + "/" + getAppSize(total));
                        status_tv.setText("正在下载");
                        down.setText(getAppSize(current) + "/" + getAppSize(total));
                        progressBar.setMax((int) total);
                        progressBar.setProgress((int) current);
                        mListData.get(position).setStatus((int) status);
                    }

                    @Override
                    public void onFailure(int errorCode) {

                    }

                    @Override
                    public void onSuccess(MessageInfo info) {
                        progressBar.setVisibility(View.INVISIBLE);
                        size_tv.setVisibility(View.INVISIBLE);
                        status_tv.setText("下载成功");
                        down.setText("安装");
                    }

                });
            }
        });
        return convertView;
    }

}
