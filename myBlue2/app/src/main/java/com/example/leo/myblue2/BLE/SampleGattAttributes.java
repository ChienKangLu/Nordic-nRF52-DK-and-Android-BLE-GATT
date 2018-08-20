/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.leo.myblue2.BLE;

import android.util.Log;

import java.util.HashMap;

/**
 * This class includes a small subset of standard GATT attributes for demonstration purposes.
 */
public class SampleGattAttributes {
    private static HashMap<String, String> attributes = new HashMap();
    /*
    public static String MyTemperature_MEASUREMENT = "0000abcd-1212-efde-1523-785fef13d123";//00002a37-0000-1000-8000-00805f9b34fb
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-1212-efde-1523-785fef13d123";//00002902-0000-1000-8000-00805f9b34fb
    public static String cccd="0000beef-1212-efde-1523-785fef13d123";
    */

    public static String Padding="0000";
    public static String BASE_UUID="-1212-efde-1523-785fef13d123";
    public static String Temperature_Service_uuid="abcd";
    public static String Temperature_Characteristic_uuid="beef";
    public static String UserKey_Characteristic_uuid="bbbb";
    public static String cccd_uuid="2902";
    public static String Temperature_Service=Padding+Temperature_Service_uuid+BASE_UUID;
    public static String Temperature_Characteristic=Padding+Temperature_Characteristic_uuid+BASE_UUID;
    public static String UserKey_Characteristic=Padding+UserKey_Characteristic_uuid+BASE_UUID;
    public static String cccd=Padding+cccd_uuid+BASE_UUID;


    public static String Normal_base_UUID="-0000-1000-8000-00805f9b34fb";


    public static String Generic_Access_uuid="1800";
    public static String Generic_Access=Padding+Generic_Access_uuid+Normal_base_UUID;//00002a00-0000-1000-8000-00805f9b34fb

    public static String Device_name_uuid="2a00";
    public static String Device_name=Padding+Device_name_uuid+Normal_base_UUID;//00002a00-0000-1000-8000-00805f9b34fb

    public static String Appearance_uuid="2a01";
    public static String Appearance=Padding+Appearance_uuid+Normal_base_UUID;//00002a00-0000-1000-8000-00805f9b34fb

    public static String Peripheral_Preferred_Connection_Parameters_uuid="2a04";
    public static String Peripheral_Preferred_Connection_Parameters=Padding+Peripheral_Preferred_Connection_Parameters_uuid+Normal_base_UUID;//00002a00-0000-1000-8000-00805f9b34fb

    public static String Central_Address_Resolution_uuid="2aa6";
    public static String Central_Address_Resolution=Padding+Central_Address_Resolution_uuid+Normal_base_UUID;//00002a00-0000-1000-8000-00805f9b34fb

    public static String Generic_Attribute_uuid="1801";
    public static String Generic_Attribute=Padding+Generic_Attribute_uuid+Normal_base_UUID;//00002a00-0000-1000-8000-00805f9b34fb


    static {
        // Sample Services.
        //attributes.put("0000beef-1212-efde-1523-785fef13d123", "MyTemperature Service");//0000180d-0000-1000-8000-00805f9b34fb
        //attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "Device Information Service");
        // Sample Characteristics.
        //attributes.put(MyTemperature_MEASUREMENT, "MyTemperature Measurement");

        attributes.put(Temperature_Service, "Temperature Service");

        attributes.put(Temperature_Characteristic, "Temperature Characteristic");
        attributes.put(UserKey_Characteristic, "Temperature Characteristic");

        attributes.put(Generic_Access, "Generic Access");
        attributes.put(Device_name, "Device Name");
        attributes.put(Appearance, "Appearance");
        attributes.put(Peripheral_Preferred_Connection_Parameters, "Peripheral Preferred Connection");// Parameters
        attributes.put(Central_Address_Resolution, "Central Address Resolution");
        attributes.put(Generic_Attribute, "Generic Attribute");

    }

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        Log.v("leogg",uuid);
        return name == null ? defaultName : name;
    }
}
