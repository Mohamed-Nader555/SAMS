package com.omar.sams.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;

public class Utils {

    public static String UserID = "userIDKey";
    public static String IsLoggedOn = "LoginKey";

    public static SharedPreferences sharedPreferences = null;
    public static SharedPreferences.Editor sharedPreferencesEditor = null;
    public static String FILENAME = "LoginData";

    public static SharedPreferences.Editor getSpEditor(Context context) {
        if (sharedPreferencesEditor == null) {
            sharedPreferences = context.getSharedPreferences(FILENAME, 0);
            sharedPreferencesEditor = sharedPreferences.edit();
        }

        return sharedPreferencesEditor;
    }

    public static SharedPreferences getSp(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(FILENAME, 0);
            sharedPreferencesEditor = sharedPreferences.edit();
        }

        return sharedPreferences;
    }

    public static AlertDialog showDialogMessage(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle(title).setMessage(message).show();
        if (alertDialog.isShowing()) {
            alertDialog.cancel();
        }
        return alertDialog;
    }


}
