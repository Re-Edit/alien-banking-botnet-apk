package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.os.SystemClock;

public final class zzl {
    private static IntentFilter zzaJQ = new IntentFilter("android.intent.action.BATTERY_CHANGED");
    private static long zzaJR;
    private static float zzaJS = Float.NaN;

    @TargetApi(20)
    public static int zzaK(Context context) {
        if (context == null || context.getApplicationContext() == null) {
            return -1;
        }
        Intent registerReceiver = context.getApplicationContext().registerReceiver((BroadcastReceiver) null, zzaJQ);
        int i = 0;
        int i2 = ((registerReceiver == null ? 0 : registerReceiver.getIntExtra("plugged", 0)) & 7) != 0 ? 1 : 0;
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        if (powerManager == null) {
            return -1;
        }
        if (zzs.zzsc() ? powerManager.isInteractive() : powerManager.isScreenOn()) {
            i = 1;
        }
        return (i << 1) | i2;
    }

    public static synchronized float zzaL(Context context) {
        synchronized (zzl.class) {
            if (SystemClock.elapsedRealtime() - zzaJR >= 60000 || Float.isNaN(zzaJS)) {
                Intent registerReceiver = context.getApplicationContext().registerReceiver((BroadcastReceiver) null, zzaJQ);
                if (registerReceiver != null) {
                    zzaJS = ((float) registerReceiver.getIntExtra("level", -1)) / ((float) registerReceiver.getIntExtra("scale", -1));
                }
                zzaJR = SystemClock.elapsedRealtime();
                float f = zzaJS;
                return f;
            }
            float f2 = zzaJS;
            return f2;
        }
    }
}
