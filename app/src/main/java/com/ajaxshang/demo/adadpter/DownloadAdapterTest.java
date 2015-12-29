package com.ajaxshang.demo.adadpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ajaxshang.demo.R;
import com.ajaxshang.demo.callback.UpdateCallback;
import com.ajaxshang.demo.entity.DownloadInfo;
import com.ajaxshang.demo.entity.MessageInfo;
import com.ajaxshang.demo.utils.Download;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/24.
 * <p/>
 * 解决思路，去掉down回调中的onProgress方法，在getView中通过查询downloadinfo的状态来获取下载状态进行更新数据
 */
public class DownloadAdapterTest extends BaseAdapter {

    public static final int MB_2_BYTE = 1024 * 1024;
    public static final int KB_2_BYTE = 1024;
    static final DecimalFormat DOUBLE_DECIMAL_FORMAT = new DecimalFormat("0.##");
    List<DownloadInfo> infos = new ArrayList<>();
    Context context;
    private UpdateCallback callback;

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

    public UpdateCallback getCallback() {
        return callback;
    }

    public void setCallback(UpdateCallback callback) {
        this.callback = callback;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.downloaditem, null);
            viewHolder.name_tv = (TextView) convertView.findViewById(R.id.name_tv);
            viewHolder.down_btn = (Button) convertView.findViewById(R.id.down_btn);
            viewHolder.size_tv = (TextView) convertView.findViewById(R.id.size_tv);
            viewHolder.status_tv = (TextView) convertView.findViewById(R.id.status_tv);
            viewHolder.progressBar = (ProgressBar) convertView.findViewById(R.id.down_progress);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        setData(viewHolder, position);
        return convertView;

    }

    private void setData(ViewHolder holder, final int position) {
        holder.name_tv.setText(infos.get(position).getName() + "  " + position);
        holder.down_btn.setText("下载");
        holder.progressBar.setMax(0);
        holder.progressBar.setVisibility(View.VISIBLE);
        holder.down_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Download download = new Download(context);
                download.down(infos.get(position), new Download.DownloadCallBack() {
                    @Override
                    public void onStart(long downloadId) {
//                        holder.progressBar.setVisibility(View.VISIBLE);
//                        holder.status_tv.setText("正在下载");
//                        holder.status_tv.setVisibility(View.VISIBLE);
//                        infos.get(itemIndex).setStatus(DownloadManager.STATUS_RUNNING);
//                        handler.sendMessage(handler.obtainMessage(0, (int) downloadId, position));
                        callback.startProgress(downloadId, position);
                    }

                    @Override
                    public void onSuccess(MessageInfo info) {
//                        holder.status_tv.setText("下载成功");
//                        holder.progressBar.setVisibility(View.INVISIBLE);
//                        holder.down_btn.setText("安装");
//                        infos.get(itemIndex).setStatus(DownloadManager.STATUS_SUCCESSFUL);
                    }

                    @Override
                    public void onFailure(int errorCode) {

                    }

                    @Override
                    public void onProgress(long id, long current, long total, long status) {
//                        holder.progressBar.setMax((int) total);
//                        holder.progressBar.setProgress((int) current);
//                        holder.down_btn.setText(getAppSize(current) + "/" + getAppSize(total));
//                        infos.get(position).setStatus(DownloadManager.STATUS_RUNNING);
//                        infos.get(position).setCurrent(current);
//                        infos.get(position).setTotal(total);

                    }
                });
            }
        });
    }


    public class ViewHolder {
        public TextView name_tv;
        public TextView size_tv;
        public TextView status_tv;
        public Button down_btn;
        public ProgressBar progressBar;
    }


}
