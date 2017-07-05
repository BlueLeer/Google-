package com.leer.googlemarket.manager;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.leer.googlemarket.domain.AppInfo;
import com.leer.googlemarket.domain.DownloadInfo;
import com.leer.googlemarket.global.DownloadState;
import com.leer.googlemarket.utils.IOUtils;
import com.leer.googlemarket.utils.UIUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Leer on 2017/6/1.
 * <p>
 * DownloadManager:相当于一个被观察者
 */

public class DownloadManager {
    private static final String TAG = "DownloadManager";
    private static DownloadManager mDownloadManager = new DownloadManager();

    //所有的观察者的集合
    private static ArrayList<DownloadObserver> mObservers = new ArrayList<>();

    //所有下载信息的集合
    private static ConcurrentHashMap<String, DownloadInfo> mDownloadInfoMap = new ConcurrentHashMap<>();

    //所有下载任务的集合
    private static ConcurrentHashMap<String, DownloadTask> mDownloadTaskMap = new ConcurrentHashMap<>();


    private DownloadManager() {
    }

    public static DownloadManager getInstance() {
        return mDownloadManager;
    }

    //开始下载
    public void download(AppInfo appinfo) {
        //在本地寻找,如果已经存在安装包了,就不用下载了
        DownloadInfo info = mDownloadInfoMap.get(appinfo.id);
        if (info == null) {
            info = DownloadInfo.create(appinfo);
            mDownloadInfoMap.put(appinfo.id, info);
        }

        File file = new File(info.getPath());
        if (file.exists() && file.length() == appinfo.size) {
            info.setCurrentState(DownloadState.SUCCESS);
            notifyDownloadStateChanged(info);
        } else {
            //更改下载状态
            info.setCurrentState(DownloadState.WAITING);
            //通知观察者,状态发生改变
            notifyDownloadStateChanged(info);

            Log.d(TAG, "等待下载啦----------");

            DownloadTask task = new DownloadTask(info);
            ThreadManager.getInstance().execute(task);

            //将所有的下载任务放入集合当中去
            mDownloadTaskMap.put(info.getId(), task);
        }
    }

    private class DownloadTask implements Runnable {
        private DownloadInfo mDownloadInfo;

        private DownloadTask(DownloadInfo info) {
            this.mDownloadInfo = info;
        }

        @Override
        public void run() {
            //将下载状态更改为:正在下载
            mDownloadInfo.setCurrentState(DownloadState.DOWNLOADING);
            Log.d(TAG, "正在开始下载啦----------");
            notifyDownloadStateChanged(mDownloadInfo);

            File file = new File(mDownloadInfo.getPath());

            InputStream in = null;
            FileOutputStream fos = null;
            try {
                //检查本地文件是否存在,决定是重头下载还是断点续传
                if (!file.exists() || mDownloadInfo.getCurrentState() == DownloadState.ERROR
                        || mDownloadInfo.getCurrentPos() != file.length() ||
                        mDownloadInfo.getCurrentPos() == 0) {
                    //文件已经存在,下载错误,已经下载的文件长度出现错误,
                    // 文件已经创建,但是还没有开始下载,此时,都需要断点续传
                    in = getInputStream(0, mDownloadInfo.getDownloadUrl());
                    mDownloadInfo.setCurrentPos(0);

                } else {
                    //文件还没有存在,此时需要重新下载
                    in = getInputStream(file.length(), mDownloadInfo.getDownloadUrl());
                }

                //下载结束后,将下载的数据写入文件中
                if (in != null) {
                    fos = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    while ((len = (in.read(buffer))) != -1) {
                        fos.write(buffer, 0, len);
                        //将缓冲区的文件全部刷入
                        fos.flush();
                        mDownloadInfo.setCurrentPos(mDownloadInfo.getCurrentPos() + len);

                        Log.d(TAG, "当前的下载位置是 : " + mDownloadInfo.getCurrentPos());
                        //通知下载进度发生改变
                        notifyDownloadProgressChanged(mDownloadInfo);
                    }
                }

                //下载结束,进行检测
                if (file.length() == mDownloadInfo.getCurrentPos()) {
                    //成功下载
                    mDownloadInfo.setCurrentState(DownloadState.SUCCESS);
                    Log.d(TAG, "下载成功");
                    notifyDownloadStateChanged(mDownloadInfo);
                } else if (mDownloadInfo.getCurrentState() == DownloadState.PAUSE) {
                    //中途暂停
                    notifyDownloadStateChanged(mDownloadInfo);
                    Log.d(TAG, "下载暂停");
                } else {
                    //下载错误
                    //删除下载的文件
                    file.delete();
                    mDownloadInfo.setCurrentPos(0);
                    mDownloadInfo.setCurrentState(DownloadState.ERROR);
                    notifyDownloadStateChanged(mDownloadInfo);
                    Log.d(TAG, "下载错误");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(in);
                IOUtils.close(fos);
            }

            //从下载的集合中移除下载的任务
            mDownloadTaskMap.remove(mDownloadInfo.getId());
        }
    }

    private InputStream getInputStream(long range, String downloadUrl) throws Exception {
        HttpURLConnection connection;
        URL url = new URL(downloadUrl);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(3000);
        connection.setReadTimeout(3000);
        if (range != 0) {
            connection.setRequestProperty("Range", "bytes =" + range + "-");
        }
        return connection.getInputStream();
    }

    //暂停下载
    public void pause(AppInfo appInfo) {
        DownloadInfo info = mDownloadInfoMap.get(appInfo.id);
        if (info != null && (info.getCurrentState() == DownloadState.WAITING ||
                info.getCurrentState() == DownloadState.DOWNLOADING)) {
            DownloadTask task = mDownloadTaskMap.get(appInfo.id);
            if (task != null) {
                //暂停下载
                ThreadManager.getInstance().cancel(task);
                info.setCurrentState(DownloadState.PAUSE);
                notifyDownloadStateChanged(info);
            }
        }
    }

    //下载成功开始安装
    public void install(AppInfo appinfo) {
        DownloadInfo info = mDownloadInfoMap.get(appinfo.id);
        if (info != null) {
            notifyDownloadStateChanged(info);

            File file = new File(info.getPath());
            //启动安装
            Intent intent = new Intent();
            //启动一个隐式的activity
            intent.setAction(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
            UIUtils.getContext().startActivity(intent);
        }
    }

    public DownloadInfo getDownloadInfo(AppInfo appInfo) {
        return mDownloadInfoMap.get(appInfo.id);
    }

    public DownloadTask getDownloadTask(AppInfo appInfo) {
        return mDownloadTaskMap.get(appInfo.id);
    }

    //1.声明观察者接口
    public interface DownloadObserver {
        public void onDownloadStateChanged(DownloadInfo info);

        public void onDownloadProgress(DownloadInfo info);
    }

    //2.注册观察者
    public void registerObserver(DownloadObserver observer) {
        if (observer != null && !mObservers.contains(observer)) {
            mObservers.add(observer);
        }
    }

    //3.注销观察者
    public void unRegisterObserver(DownloadObserver observer) {
        if (observer != null && mObservers.contains(observer)) {
            mObservers.remove(observer);
        }
    }

    public void notifyDownloadStateChanged(DownloadInfo info) {
        for (DownloadObserver observer : mObservers) {
            observer.onDownloadStateChanged(info);
        }
    }

    public void notifyDownloadProgressChanged(DownloadInfo info) {
        for (DownloadObserver ob : mObservers) {
            ob.onDownloadProgress(info);
        }
    }


}
