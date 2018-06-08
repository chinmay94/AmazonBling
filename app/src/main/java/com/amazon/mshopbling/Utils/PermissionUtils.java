package com.amazon.mshopbling.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionUtils {

    public static boolean checkSetPermission(Context context, Activity activity, String permission) {
        boolean hasPermission = false;
        int MY_PERMISSIONS_REQUEST = 0;
        if (ContextCompat.checkSelfPermission(context,
                permission)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    permission)) {
                hasPermission = false;
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(activity,
                        new String[]{permission},
                        MY_PERMISSIONS_REQUEST);
            }
        } else {
            hasPermission = true;
        }
        return hasPermission;
    }
}
