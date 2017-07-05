package com.leer.googlemarket.domain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Leer on 2017/5/11.
 */

public class JsonParser {
    public static ArrayList<AppInfo> parserHomeAppInfo(String json) {
        ArrayList<AppInfo> appInfos = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                AppInfo appInfo = new AppInfo();
                appInfo.des = jsonObject1.getString("des");
                appInfo.id = jsonObject1.getString("id");
                appInfo.iconUrl = jsonObject1.getString("iconUrl");
                appInfo.name = jsonObject1.getString("name");
                appInfo.packageName = jsonObject1.getString("packageName");
                appInfo.size = jsonObject1.getLong("size");
                appInfo.downloadUrl = jsonObject1.getString("downloadUrl");
                appInfo.stars = (float) jsonObject1.getDouble("stars");
                appInfos.add(appInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return appInfos;
    }

    public static ArrayList<String> parserHomePicture(String json) {
        ArrayList<String> pictrues = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("picture");
            for (int i = 0; i < jsonArray.length(); i++) {
                pictrues.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return pictrues;
    }



    public static ArrayList<AppInfo> parserAppAppinfo(String json) {
        ArrayList<AppInfo> appInfos = new ArrayList<>();
        try {

            JSONArray ja = new JSONArray(json);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                AppInfo appInfo = new AppInfo();
                appInfo.des = jo.getString("des");
                appInfo.id = jo.getString("id");
                appInfo.iconUrl = jo.getString("iconUrl");
                appInfo.name = jo.getString("name");
                appInfo.packageName = jo.getString("packageName");
                appInfo.size = jo.getLong("size");
                appInfo.downloadUrl = jo.getString("downloadUrl");
                appInfo.stars = (float) jo.getDouble("stars");
                appInfos.add(appInfo);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return appInfos;
    }

    /**
     * @param json 从服务器获取的关于"专题"的网络数据
     * @return 返回"专题"对应的对象列表
     */
    public static ArrayList<SubjectInfo> parserSubjectInfo(String json){
        ArrayList<SubjectInfo> subjectInfos = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0;i<jsonArray.length();i++){
                SubjectInfo subjectInfo = new SubjectInfo();
                JSONObject jo = jsonArray.getJSONObject(i);
                subjectInfo.des = jo.getString("des");
                subjectInfo.imgUrl = jo.getString("url");

                subjectInfos.add(subjectInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return subjectInfos;
    }

    public static ArrayList<RecommendInfo> parserRecommendInfo(String json){
        ArrayList<RecommendInfo> recommendInfos = new ArrayList<>();
        try {
            JSONArray jo = new JSONArray(json);
            for(int i = 0;i<jo.length();i++){
                RecommendInfo info = new RecommendInfo();
                String s = jo.getString(i);
                info.recom = s;
                recommendInfos.add(info);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recommendInfos;
    }

    public static ArrayList<HotInfo> parserHotInfo(String json){
        ArrayList<HotInfo> hotInfos = new ArrayList<>();
        try {
            JSONArray jo = new JSONArray(json);
            for(int i = 0;i<jo.length();i++){
                HotInfo info = new HotInfo();
                String s = jo.getString(i);
                info.name = s;
                hotInfos.add(info);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hotInfos;
    }

    public static ArrayList<CategoryInfo> parserCategoryInfo(String json){
        ArrayList<CategoryInfo> categoryInfos = new ArrayList<>();
        try {
            JSONArray ja = new JSONArray(json);
            for(int i = 0;i<ja.length();i++){
                JSONArray jsa = ja.getJSONObject(i).getJSONArray("infos");
                for(int j = 0;j<jsa.length();j++){
                    JSONObject jo = jsa.getJSONObject(i);
                    CategoryInfo info = new CategoryInfo();
                    info.name1 = jo.getString("name1");
                    info.name2 = jo.getString("name2");
                    info.name3 = jo.getString("name3");
                    info.url1 = jo.getString("url1");
                    info.url2 = jo.getString("url2");
                    info.url3 = jo.getString("url3");
                    if(i == 0){
                        info.isGame = true;
                    }else{
                        info.isGame = false;
                    }

                    categoryInfos.add(info);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return categoryInfos;
    }

}
