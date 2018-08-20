package com.example.leo.myblue2.database;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Created by leo on 2017/6/18.
 */

public class DB {
    RequestQueue requestQueue;
    //String txt="";
    public DB(Context context){
        requestQueue = Volley.newRequestQueue(context);
    }

    public interface VolleyCallback{
        void onSuccess(ArrayList<dbitem> result);
    }
    public void loginQuery(String account,String password,final VolleyCallback callback){
        //$str2="select * from user where account='".$_GET["account"]."' && password='".$_GET["password"]."'";
        String url = "http://140.118.110.112/login_query.php?account="+account+"&password="+password;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST,url,null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(response.toString());
                        try {
                            ArrayList<dbitem> result=new ArrayList<>();
                            JSONArray data = response.getJSONArray("data");
                            //這邊要和上面json的名稱一樣
                            //下邊是把全部資料都印出來

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject jasondata = data.getJSONObject(i);
                                String userid = jasondata.getString("userid");
                                String account = jasondata.getString("account");
                                String pwd = jasondata.getString("password");
                                user u=new user(userid,account,pwd);
                                result.add(u);
                            }
                            callback.onSuccess(result);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("LEOgo",e.toString());
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.append(error.getMessage());

                        Log.e("LEOgo",error.getMessage().toString());
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
    public void permissionQuery(String userid,final VolleyCallback callback){
        //http://140.118.110.112/permission.php?userid=1
        String url = "http://140.118.110.112/permission.php?userid="+userid;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST,url,null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(response.toString());
                        try {
                            ArrayList<dbitem> result=new ArrayList<>();
                            JSONArray data = response.getJSONArray("data");
                            //這邊要和上面json的名稱一樣
                            //下邊是把全部資料都印出來
                            for (int i = 0; i < data.length(); i++) {

                                JSONObject jasondata = data.getJSONObject(i);
                                String deviceid = jasondata.getString("deviceid");
                                String deviceName = jasondata.getString("deviceName");
                                String deviceAddress = jasondata.getString("deviceAddress");
                                String realkey = jasondata.getString("realKey");
                                String deviceDescribe = jasondata.getString("deviceDescribe");
                                device d=new device(deviceid,deviceName,deviceAddress,realkey,deviceDescribe);
                                result.add(d);
                            }
                            callback.onSuccess(result);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("LEOgo",e.toString());
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.append(error.getMessage());

                        Log.e("LEOgo",error.getMessage().toString());
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    public void saveTemperature(String deviceid,String temp){
       // http://140.118.110.112/saveT.php?deviceid=1&temp=30&datetime='2017-12-12 13:20:39'
       // SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//String datetime = sDateFormat.format(new java.util.Date());


        SimpleDateFormat sDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat1.format(new java.util.Date());


        SimpleDateFormat sDateFormat2 = new SimpleDateFormat("HH:mm:ss");
        String time = sDateFormat2.format(new java.util.Date());


        String url = "http://140.118.110.112/saveT.php?deviceid="+deviceid+"&temp="+temp+"&date='"+date+"'&time='"+time+"'";

        Log.e("LEOgo",url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST,url,null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(response.toString());
                        Log.e("LEOgo",response.toString());
                        /*
                        try {

                            ArrayList<dbitem> result=new ArrayList<>();
                            JSONArray data = response.getJSONArray("data");
                            //這邊要和上面json的名稱一樣
                            //下邊是把全部資料都印出來
                            for (int i = 0; i < data.length(); i++) {

                                JSONObject jasondata = data.getJSONObject(i);
                                String deviceName = jasondata.getString("deviceName");
                                String deviceAddress = jasondata.getString("deviceAddress");
                                String realkey = jasondata.getString("realKey");
                                String deviceDescribe = jasondata.getString("deviceDescribe");
                                device d=new device(deviceName,deviceAddress,realkey,deviceDescribe);
                                result.add(d);
                            }
                           // callback.onSuccess(result);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("LEOgo",e.toString());
                        }
                        */
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.append(error.getMessage());

                        Log.e("LEOgo",error.getMessage().toString());
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

}
