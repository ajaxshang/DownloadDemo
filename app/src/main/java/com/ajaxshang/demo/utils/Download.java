package com.ajaxshang.demo.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.lang.reflect.Method;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 2015年12月17日 17:39:18 author:ajaxshang
 */
public class Download {

    public static final String METHOD_NAME_PAUSE_DOWNLOAD = "pauseDownload";
    public static final String METHOD_NAME_RESUME_DOWNLOAD = "resumeDownload";

    public static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
    static Context context;
    static DownloadManager manager;


    static long downloadId = 0;
    private static Method pauseDownload = null;
    private static Method resumeDownload = null;
    private static boolean isInitPauseDownload = false;
    private static boolean isInitResumeDownload = false;


    // 文件保存路径，手机根目录下AjaxShang文件夹
    public String DOWNLOAD_FOLDER_NAME = "";
    public String DOWNLOAD_FILE_NAME = "";
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            DownloadCallBack callBack = (DownloadCallBack) msg.obj;
            callBack.onProgress(msg.what, msg.arg1, msg.arg2);


        }
    };


    // 初始化
    public Download(Context context) {
        this.context = context;
        manager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
    }

    /**
     * 初始化暂停下载方法
     */

    private static void initPauseMethod() {
        if (isInitPauseDownload) {
            return;
        }

        isInitPauseDownload = true;
        try {
            pauseDownload = DownloadManager.class.getMethod(METHOD_NAME_PAUSE_DOWNLOAD, long[].class);
        } catch (Exception e) {
            // accept all exception
            e.printStackTrace();
        }
    }

    /**
     * 初始化重启下载方法
     */

    private static void initResumeMethod() {
        if (isInitResumeDownload) {
            return;
        }

        isInitResumeDownload = true;
        try {
            resumeDownload = DownloadManager.class.getMethod(METHOD_NAME_RESUME_DOWNLOAD, long[].class);
        } catch (Exception e) {
            // accept all exception
            e.printStackTrace();
        }
    }

    /**
     * 判定下载文件是否为APK
     */

    private static boolean isAPK(String name) {
        if (!name.equals("")) {
            if (name.substring(name.length() - 4, name.length()).toLowerCase().equals(".apk")) {
                return true;
            }
        }
        return false;
    }

    /**
     * APK安装方法
     */


    private static boolean install(Context context, String filePath) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        File file = new File(filePath);
        if (file != null && file.length() > 0 && file.exists() && file.isFile()) {
            i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            return true;
        }
        return false;
    }

    // 获取下载状态
    private static int[] getBytesAndStatus(long downloadId) {
        int[] bytesAndStatus = new int[]{-1, -1, 0};
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = null;
        try {
            c = manager.query(query);
            if (c != null && c.moveToFirst()) {
                bytesAndStatus[0] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                bytesAndStatus[1] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                bytesAndStatus[2] = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return bytesAndStatus;
    }

    // 获取错误代码
    private static int getErrorCode(long downloadId) {
        return getInt(downloadId, DownloadManager.COLUMN_REASON);
    }


    private static int getInt(long downloadId, String columnName) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        int result = -1;
        Cursor c = null;
        try {
            c = manager.query(query);
            if (c != null && c.moveToFirst()) {
                result = c.getInt(c.getColumnIndex(columnName));
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return result;
    }

    private static String getErrorStatus(int errorCode) {
        String status = "";
        switch (errorCode) {
            case DownloadManager.ERROR_FILE_ERROR:
                status = "file error";
                break;
            case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
                status = " download manager can't handle.";
                break;
            case DownloadManager.ERROR_HTTP_DATA_ERROR:
                status = "http error";
                break;
            case DownloadManager.ERROR_TOO_MANY_REDIRECTS:
                status = "too many redirects";
                break;
            case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                status = "储存空间不足";
                break;
            case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                status = "找不到储存卡";
                break;
            case DownloadManager.ERROR_CANNOT_RESUME:
                status = "can't resume";
                break;
            default:
                break;

        }
        return status;
    }

    /**
     * 下载方法
     *
     * @param path     下载路径
     * @param savePath 保存路径
     * @param callBack 回调函数
     */

    public void down(String path, String savePath, DownloadCallBack callBack) {

        DOWNLOAD_FOLDER_NAME = savePath;
        DOWNLOAD_FILE_NAME = getFileName(path);

        DownloadManager.Request down = new DownloadManager.Request(Uri.parse(path));
        // 设置网络格式，wifi条件下下载
        down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        // 设置通知栏是否提示
        down.setNotificationVisibility(View.VISIBLE);
        down.setVisibleInDownloadsUi(true);

        // 设置下载路径和文件名
        File folder = Environment.getExternalStoragePublicDirectory(savePath);
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdirs();
        }
        down.setDestinationInExternalPublicDir(savePath, DOWNLOAD_FILE_NAME);

        // 将下载请求放入队列，返回downloadId
        downloadId = manager.enqueue(down);
        callBack.onStart(downloadId);
        DownloadObRunnable runnable = new DownloadObRunnable(downloadId, callBack);
        scheduledExecutorService.scheduleAtFixedRate(runnable, 0, 1000, TimeUnit.SECONDS);

    }

    /**
     * 下载方法
     *
     * @param info     下载类
     * @param callBack 回调
     */

    public void down(DownloadInfo info, final DownloadCallBack callBack) {


        if (info == null) {
            return;
        }
        if (info.getDownloadPath().equals("")) {
            return;
        }
        DOWNLOAD_FILE_NAME = getFileName(info.getDownloadPath());
        DOWNLOAD_FOLDER_NAME = info.getSavePath();

        DownloadManager.Request down = new DownloadManager.Request(Uri.parse(info.getDownloadPath()));

        if (info.isMobileMode()) {
            down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);
        }

        if (info.isWifiMode()) {
            down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        }

        if (info.isWifiMode() && info.isMobileMode()) {
            down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        }

        if (info.isNotificationShow()) {
            down.setNotificationVisibility(View.VISIBLE);
            down.setTitle(info.getTitle());
            down.setDescription(info.getDescription());
        }

        down.setVisibleInDownloadsUi(true);

        if (!info.getSavePath().equals("")) {
            // 设置下载路径和文件名
            File folder = Environment.getExternalStoragePublicDirectory(info.getSavePath());
            if (!folder.exists() || !folder.isDirectory()) {
                folder.mkdirs();
            }
            down.setDestinationInExternalPublicDir(info.getSavePath(), DOWNLOAD_FILE_NAME);
        } else {
            down.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, DOWNLOAD_FILE_NAME);
        }

        // 将下载请求放入队列，返回downloadId
        if (down != null) {


            downloadId = manager.enqueue(down);
            callBack.onStart(downloadId);

            //FIXME 增加线程池，维护线程
            DownloadObRunnable runnable = new DownloadObRunnable(downloadId, callBack);
            scheduledExecutorService.scheduleAtFixedRate(runnable, 0, 1000, TimeUnit.SECONDS);
//            new Thread(runnable).start();

        }
    }

    private String getFileName(String downloadPath) {
        String[] strs = downloadPath.split(File.separator);
        if (strs.length != 0) {
            return strs[strs.length - 1];
        }
        return "";
    }

    /**
     * 取消下载
     */
    public int remove(long... downloadId) {
        return manager.remove(downloadId);
    }

    /**
     * 暂停下载
     */

    public int pauseDownload(long... ids) {
        initPauseMethod();
        if (pauseDownload == null) {
            return -1;
        }

        try {
            return ((Integer) pauseDownload.invoke(manager, ids)).intValue();
        } catch (Exception e) {
            /**
             * accept all exception, include ClassNotFoundException,
             * NoSuchMethodException, InvocationTargetException,
             * NullPointException
             */
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 继续下载
     */
    public int resumeDownload(long... ids) {
        initResumeMethod();
        if (resumeDownload == null) {
            return -1;
        }

        try {
            return ((Integer) resumeDownload.invoke(manager, ids)).intValue();
        } catch (Exception e) {
            /**
             * accept all exception, include ClassNotFoundException,
             * NoSuchMethodException, InvocationTargetException,
             * NullPointException
             */
            e.printStackTrace();
        }
        return -1;
    }


    private int getStatusById(long downloadId) {
        return getInt(downloadId, DownloadManager.COLUMN_STATUS);
    }

    public interface DownloadCallBack {
        void onStart(long downloadId);

        void onSuccess(long downloadId);

        void onFailure(int errorCode);

        void onProgress(long current, long total, long status);

    }

    class DownloadObRunnable implements Runnable {
        long id;
        DownloadCallBack callBack;

        public DownloadObRunnable(long id, DownloadCallBack callBack) {
            this.id = id;
            this.callBack = callBack;
        }

        @Override
        public void run() {
            long threadId = Thread.currentThread().getId();
            Log.d("Thread", threadId + "");
            while (true) {
                int[] bytesAndStatus = getBytesAndStatus(id);
                handler.sendMessage(handler.obtainMessage(bytesAndStatus[0], bytesAndStatus[1], bytesAndStatus[2], callBack));
                if (bytesAndStatus[2] == DownloadManager.STATUS_SUCCESSFUL) {
                    callBack.onSuccess(id);
                    scheduledExecutorService.shutdown();
                    break;
                }
                if (bytesAndStatus[2] == DownloadManager.STATUS_FAILED) {
                    callBack.onFailure(getErrorCode(id));
                    scheduledExecutorService.shutdownNow();
                    break;
                }
            }
        }
    }

    /**
     * 更新UI方法
     * */

//    private void updateView(long id, DownloadCallBack callBack) {
//        int[] bytesAndStatus = getBytesAndStatus(id);
//        handler.sendMessage(handler.obtainMessage(bytesAndStatus[0], bytesAndStatus[1], bytesAndStatus[2], callBack));
//    }


    /**
     * 关闭方法
     * */

    //    public void close() {
//        context.getContentResolver().unregisterContentObserver(downloadObserver);
//        context.unregisterReceiver(completeReceiver);
//        Log.d("Download", "downloadObserver and completeReceiver is close");
//    }

    /**
     * 不用内容提供者改用其他线程监控
     * */
//    class DownloadChangeObserver extends ContentObserver {
//        DownloadCallBack callBack;
//        long id = 0;
//
//
//        public DownloadChangeObserver(long id, DownloadCallBack callBack) {
//            super(handler);
//            this.callBack = callBack;
//            this.id = id;
//        }
//
//        @Override
//        public void onChange(boolean selfChange) {
//            updateView(id, callBack);
//        }
//
//
//    }

    /**
     * 下载完成监听
     * */
//    class CompleteReceiver extends BroadcastReceiver {
//        DownloadCallBack callBack;
//        long id = 0;
//
//        public CompleteReceiver(long id, DownloadCallBack callBack) {
//            this.callBack = callBack;
//            this.id = id;
//        }
//
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            /**
//             * get the id of download which have download success, if the id is
//             * my id and it's status is successful, then install it
//             **/
//            long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
//            if (completeDownloadId == id) {
//                if (getStatusById(id) == DownloadManager.STATUS_SUCCESSFUL) {
//                    callBack.onSuccess(id);
//                    // if (isAPK(DOWNLOAD_FILE_NAME)) {
//                    // if (!DOWNLOAD_FILE_NAME.equals("") ||
//                    // !DOWNLOAD_FOLDER_NAME.equals("")) {
//                    // String apkFilePath = new
//                    // StringBuilder(Environment.getExternalStorageDirectory().getAbsolutePath())
//                    // .append(File.separator).append(DOWNLOAD_FOLDER_NAME).append(File.separator).append(DOWNLOAD_FILE_NAME)
//                    // .toString();
//                    // install(context, apkFilePath);
//                    // }
//                    // }
//                }
//                if (getStatusById(id) == DownloadManager.STATUS_FAILED) {
//                    callBack.onFailure(getErrorCode(id));
//                }
////                close();
//            }
//        }
//    }
}
