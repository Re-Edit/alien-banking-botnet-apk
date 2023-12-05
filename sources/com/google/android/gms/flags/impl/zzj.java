package com.google.android.gms.flags.impl;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.gms.internal.zzcbs;

public final class zzj {
    private static SharedPreferences zzaXU;

    public static SharedPreferences zzaW(Context context) throws Exception {
        SharedPreferences sharedPreferences;
        synchronized (SharedPreferences.class) {
            if (zzaXU == null) {
                zzaXU = (SharedPreferences) zzcbs.zzb(new zzk(context));
            }
            sharedPreferences = zzaXU;
        }
        return sharedPreferences;
    }
}
