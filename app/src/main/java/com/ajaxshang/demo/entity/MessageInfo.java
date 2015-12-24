package com.ajaxshang.demo.entity;

import com.ajaxshang.demo.utils.Download;

/**
 * Created by Administrator on 2015/12/24.
 * 下载成功回调类
 */
public class MessageInfo {
    private Download.DownloadCallBack callBack;
    private long current;
    private long total;
    private int status;
    private String filePath;
    private long downloadid;

    public Download.DownloadCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(Download.DownloadCallBack callBack) {
        this.callBack = callBack;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getDownloadid() {
        return downloadid;
    }

    public void setDownloadid(long downloadid) {
        this.downloadid = downloadid;
    }
}
