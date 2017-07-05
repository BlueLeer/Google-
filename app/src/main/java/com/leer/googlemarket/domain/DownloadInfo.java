package com.leer.googlemarket.domain;

import android.content.Context;

import com.leer.googlemarket.global.ConstantValues;
import com.leer.googlemarket.global.DownloadState;
import com.leer.googlemarket.utils.UIUtils;

import java.io.File;

/**
 * Created by Leer on 2017/6/2.
 */

public class DownloadInfo {
    private String mName;
    //apk的文件名
    private String mFileName;
    //apk文件的大小
    private long mCurrentPos;
    //apk的下载地址
    private String mDownloadUrl;
    //获取下载状态
    private DownloadState mCurrentState;

    //当前apk的总大小
    private long mTotalSize;

    private String mId;

    private String mPath;

    private DownloadInfo(){}

    public long getCurrentPos() {
        return mCurrentPos;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setCurrentPos(long currentPos) {
        mCurrentPos = currentPos;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        this.mPath = path;
    }

    public static DownloadInfo create(AppInfo appInfo){
        String downloadUrl = ConstantValues.ROOT_URL + "download?name=" + appInfo.downloadUrl;

        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.setCurrentState(DownloadState.UNDO);
        downloadInfo.setDownloadUrl(downloadUrl);
        downloadInfo.setName(appInfo.name);
        downloadInfo.setFileName(appInfo.name + ".apk");
        downloadInfo.setTotalSize(appInfo.size);
        downloadInfo.setCurrentPos(0);
        downloadInfo.setId(appInfo.id);
        downloadInfo.setPath(createDir()+File.separator+downloadInfo.getId()+".apk");

        return downloadInfo;
    }

    private static String createDir(){
        File rootFile = UIUtils.getContext().getDir("download", Context.MODE_PRIVATE);
        if(!rootFile.exists() || !rootFile.isDirectory()){
            rootFile.mkdir();
        }
        return rootFile.getPath();
    }


    //返回当前的下载进度
    public float getProgress(){
        return mCurrentPos/(float)mTotalSize;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public long getTotalSize() {
        return mTotalSize;
    }

    public void setTotalSize(long totalSize) {
        mTotalSize = totalSize;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setFileName(String fileName) {
        mFileName = fileName;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        mDownloadUrl = downloadUrl;
    }

    public DownloadState getCurrentState() {
        return mCurrentState;
    }

    public void setCurrentState(DownloadState currentState) {
        mCurrentState = currentState;
    }
}
