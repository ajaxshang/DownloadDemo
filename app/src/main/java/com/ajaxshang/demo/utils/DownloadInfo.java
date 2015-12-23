package com.ajaxshang.demo.utils;

/**
 * Created by Administrator on 2015/12/19.
 */
public class DownloadInfo {

    // 必需项
    private String downloadPath;
    // 展示名字
    private String name;
    private String savePath;
    private boolean isWifiMode = false;
    private boolean isMobileMode = false;

    private boolean isNotificationShow = false;
    private String title;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public boolean isWifiMode() {
        return isWifiMode;
    }

    public void setIsWifiMode(boolean isWifiMode) {
        this.isWifiMode = isWifiMode;
    }

    public boolean isMobileMode() {
        return isMobileMode;
    }

    public void setIsMobileMode(boolean isMobileMode) {
        this.isMobileMode = isMobileMode;
    }

    public boolean isNotificationShow() {
        return isNotificationShow;
    }

    public void setIsNotificationShow(boolean isNotificationShow) {
        this.isNotificationShow = isNotificationShow;
    }
}
