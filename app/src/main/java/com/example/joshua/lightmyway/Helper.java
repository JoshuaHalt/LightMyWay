package com.example.joshua.lightmyway;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public final class Helper {
    public static String LIGHT_ALWAYS_ON = "lightAlwaysOn";

    public static void setPref(Context context, String key, Integer value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt(key, value);

        editor.apply();
    }

    public static Integer getPref(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, -1);
    }
}
