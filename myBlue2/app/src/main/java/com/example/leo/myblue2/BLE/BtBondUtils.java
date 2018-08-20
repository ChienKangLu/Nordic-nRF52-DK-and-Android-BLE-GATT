package com.example.leo.myblue2.BLE;

/**
 * Created by leo on 2017/5/5.
 */

import java.lang.reflect.Method;
import android.bluetooth.BluetoothDevice;

/**
 * 此类用来获取蓝牙匹配和接触匹配的方法，利用Java的反射原理来获取，因为该两种方法被android所隐藏，所以通过此方式来获取。
 *
 * @author THINK
 *
 */
public class BtBondUtils {

    /**
     * 与设备配对 参考源码：platform/packages/apps/Settings.git
     * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java
     */
    static public boolean createBond(Class btClass, BluetoothDevice btDevice) throws Exception {
        Method createBondMethod = btClass.getMethod("createBond");
        //invoke()方法主要是为了类反射，这样你可以在不知道具体的类的情况下，根据配置的字符串去调用一个类的方法。在灵活编程的时候非常有用。
        //很多框架代码都是这样去实现的。但是一般的编程，你是不需要这样做的，因为类都是你自己写的，怎么调用，怎么生成都是清楚的。
        Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }

    /**
     * 与设备解除配对 参考源码：platform/packages/apps/Settings.git
     * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java
     */
    static public boolean removeBond(Class btClass, BluetoothDevice btDevice) throws Exception {
        Method removeBondMethod = btClass.getMethod("removeBond");
        Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }

}