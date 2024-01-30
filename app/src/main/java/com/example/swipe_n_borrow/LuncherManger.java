package com.example.swipe_n_borrow;

import android.content.Context;
import android.content.SharedPreferences;

public class LuncherManger {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static String PREF_NAME = "LunchManger";
    private static String IS_FIRST_TIME = "isFirst";

    public LuncherManger (Context context){
        sharedPreferences = context.getSharedPreferences(PREF_NAME,0);
        editor =sharedPreferences.edit();
    }
    public void setFirstLunch(boolean is_first){
        editor.putBoolean(IS_FIRST_TIME,is_first);
        editor.commit();
    }

    public boolean isFirstTime(){
        return sharedPreferences.getBoolean(IS_FIRST_TIME,true);
    }

}
