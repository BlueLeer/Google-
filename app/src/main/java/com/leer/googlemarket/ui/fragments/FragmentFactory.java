package com.leer.googlemarket.ui.fragments;

import java.util.HashMap;

/**生产fragment的工厂
 * Created by Leer on 2017/5/9.
 */

public class FragmentFactory {
    private static HashMap<Integer,BaseFragment> mFragmentMap = new HashMap<>();

    public static BaseFragment creatFragment(int positon){
        BaseFragment fragment = mFragmentMap.get(positon);
        if(fragment == null){
            switch (positon){
                case 0:
                    //首页
                    fragment = new HomeFragment();
                    break;
                case 1:
                    //应用
                    fragment = new AppFragment();
                    break;
                case 2:
                    //游戏
                    fragment = new GameFragment();
                    break;
                case 3:
                    //专题
                    fragment = new SubjectFragment();
                    break;
                case 4:
                    //推荐
                    fragment = new RecommendFragment();
                    break;
                case 5:
                    //分类
                    fragment = new CategoryFragment();
                    break;
                case 6:
                    //排行
                    fragment = new HotFragment();
                    break;
                default:
                    break;
            }

            mFragmentMap.put(positon,fragment);
        }

        return fragment;
    }
}
