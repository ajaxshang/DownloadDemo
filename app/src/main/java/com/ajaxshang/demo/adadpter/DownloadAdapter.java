package com.ajaxshang.demo.adadpter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ajaxshang.demo.R;
import com.ajaxshang.demo.utils.Download;
import com.ajaxshang.demo.utils.DownloadInfo;

public class DownloadAdapter extends AbstractBaseAdapter<DownloadInfo> {

    private Context context;

    public DownloadAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public int getItemResource() {
        return R.layout.downloaditem;
    }

    @Override
    public View getItemView(final int position, View convertView) {
        TextView name_tv = ViewHolder.get(convertView, R.id.name_tv);
        final TextView status_tv = ViewHolder.get(convertView, R.id.status_tv);
        final TextView size = ViewHolder.get(convertView, R.id.size_tv);
        final Button down = ViewHolder.get(convertView, R.id.down_btn);
        final ProgressBar progressBar = ViewHolder.get(convertView, R.id.down_progress);
        final Download download = new Download(context);
        name_tv.setText(mListData.get(position).getName());

        down.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                download.down(mListData.get(position), new Download.DownloadCallBack() {

                    @Override
                    public void onSuccess(long downloadId) {
                        Log.d("Adapter", "onSuccess:" + downloadId + "");
                        status_tv.setText("下载成功");
//                        download.close();
                    }

                    @Override
                    public void onStart(long downloadId) {
                        Log.d("Adapter", "onStart:" + downloadId + "");

                    }

                    @Override
                    public void onProgress(long current, long total, long status) {
                        Log.d("Adapter", "onProgress " + position + ":" + current + "/" + total + " status:" + status);
                        int i = (int) (current / total);
                        status_tv.setText("正在下载");
//                        progressBar.setProgress(i);
                    }

                    @Override
                    public void onFailure(int errorCode) {

                    }

                });
            }
        });
        return convertView;
    }

}
