package cn.ibona.t1.common.control.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.UUID;

import cn.ibona.t1.common.BaseApplication;

/**
 * Created by Administrator on 2015/12/4.
 */
public class UDIDUtils {

    /**
     * 获取设备ID
     * @return
     */
    public static String getUDID(){
        final TelephonyManager tm = (TelephonyManager) BaseApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(BaseApplication.getInstance().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());

        String uniqueId = deviceUuid.toString();

        return uniqueId;
    }

}
