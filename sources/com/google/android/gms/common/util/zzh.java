package com.google.android.gms.common.util;

import android.content.Context;
import android.os.DropBoxManager;
import android.util.Log;
import com.google.android.gms.common.internal.zzbr;

public final class zzh {
    private static final String[] zzaJF = {"android.", "com.android.", "dalvik.", "java.", "javax."};
    private static DropBoxManager zzaJG = null;
    private static boolean zzaJH = false;
    private static int zzaJI = -1;
    private static int zzaJJ = 0;

    public static boolean zza(Context context, Throwable th) {
        return zza(context, th, 0);
    }

    private static boolean zza(Context context, Throwable th, int i) {
        try {
            zzbr.zzu(context);
            zzbr.zzu(th);
            return false;
        } catch (Exception e) {
            Log.e("CrashUtils", "Error adding exception to DropBox!", e);
            return false;
        }
    }
}
