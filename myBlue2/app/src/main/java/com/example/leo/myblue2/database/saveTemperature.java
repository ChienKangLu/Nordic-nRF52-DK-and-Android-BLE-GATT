package com.example.leo.myblue2.database;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.leo.myblue2.login;

/**
 * Created by leo on 2017/6/18.
 */

public class saveTemperature extends AsyncTask<String, Void, String> {//傳入//進度條//輸出
    Context context;
    public saveTemperature(Context context){
        this.context=context;
    }
    @Override
    protected void onPreExecute() {
        //執行前 設定可以在這邊設定
        super.onPreExecute();
    }
    @Override
    protected String doInBackground(String... arg) {

        DB db=new DB(context);
        String deviceid=arg[0];
        String temp=arg[1];
        db.saveTemperature(deviceid,temp);
        return deviceid+"#"+temp;
    }

    @Override
    protected void onPostExecute(String s) {
        //執行後 完成背景任務
        //Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
        super.onPostExecute(s);
    }
}
