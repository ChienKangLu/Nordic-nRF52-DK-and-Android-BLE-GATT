package com.example.leo.myblue2;

import android.content.Context;
import android.content.Intent;

/**
 * Created by leo on 2017/6/18.
 */

public enum Navigator {
    INSTANCE;
    public void navigateToSecond(Context context, String value,String deviceid){
        if(context != null){
            Intent callingIntent = MainActivity.getCallingIntent(context, value,deviceid);
            context.startActivity(callingIntent);
            //((permissionPage)context).finish();
        }
    }
    public void navigateToPermission(Context context, String value){
        if(context != null){
            Intent callingIntent = permissionPage.getCallingIntent(context, value);
            context.startActivity(callingIntent);
            //((login)context).finish();
        }
    }
}