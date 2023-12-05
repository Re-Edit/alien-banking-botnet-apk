package com.google.android.gms.common.util;

import android.os.Build;

public final class zzs {
    public static boolean isAtLeastN() {
        return Build.VERSION.SDK_INT >= 24;
    }

    public static boolean isAtLeastO() {
        return Build.VERSION.SDK_INT > 25 || "O".equals(Build.VERSION.CODENAME) || Build.VERSION.CODENAME.startsWith("OMR");
    }

    public static boolean zzrY() {
        return Build.VERSION.SDK_INT >= 16;
    }

    public static boolean zzrZ() {
        return Build.VERSION.SDK_INT >= 17;
    }

    public static boolean zzsa() {
        return Build.VERSION.SDK_INT >= 18;
    }

    public static boolean zzsb() {
        return Build.VERSION.SDK_INT >= 19;
    }

    public static boolean zzsc() {
        return Build.VERSION.SDK_INT >= 20;
    }

    public static boolean zzsd() {
        return Build.VERSION.SDK_INT >= 21;
    }
}
