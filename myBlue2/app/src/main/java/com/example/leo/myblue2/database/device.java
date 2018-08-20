package com.example.leo.myblue2.database;

/**
 * Created by leo on 2017/6/18.
 */

public class device implements dbitem {
    private String deviceid;
    private String deviceName;
    private String deviceAddress;
    private String realkey;
    private String deviceDescribe;

    public device(String deviceid,String deviceName, String deviceAddress, String realkey, String deviceDescribe) {
        this.deviceid=deviceid;
        this.deviceName = deviceName;
        this.deviceAddress = deviceAddress;
        this.realkey = realkey;
        this.deviceDescribe = deviceDescribe;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public String getRealkey() {
        return realkey;
    }

    public void setRealkey(String realkey) {
        this.realkey = realkey;
    }

    public String getDeviceDescribe() {
        return deviceDescribe;
    }

    public void setDeviceDescribe(String deviceDescribe) {
        this.deviceDescribe = deviceDescribe;
    }
}
