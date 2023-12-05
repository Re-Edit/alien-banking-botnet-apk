package com.google.android.gms.common.stats;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import com.google.android.gms.common.util.zze;
import java.util.Collections;
import java.util.List;

public final class zza {
    private static final Object zzaHN = new Object();
    private static volatile zza zzaJc;
    private final List<String> zzaJd = Collections.EMPTY_LIST;
    private final List<String> zzaJe = Collections.EMPTY_LIST;
    private final List<String> zzaJf = Collections.EMPTY_LIST;
    private final List<String> zzaJg = Collections.EMPTY_LIST;

    private zza() {
    }

    @SuppressLint({"UntrackedBindService"})
    public static boolean zza(Context context, String str, Intent intent, ServiceConnection serviceConnection, int i) {
        ComponentName component = intent.getComponent();
        if (!(component == null ? false : zze.zzD(context, component.getPackageName()))) {
            return context.bindService(intent, serviceConnection, i);
        }
        Log.w("ConnectionTracker", "Attempted to bind to a service in a STOPPED package.");
        return false;
    }

    public static zza zzrT() {
        if (zzaJc == null) {
            synchronized (zzaHN) {
                if (zzaJc == null) {
                    zzaJc = new zza();
                }
            }
        }
        return zzaJc;
    }

    public final boolean zza(Context context, Intent intent, ServiceConnection serviceConnection, int i) {
        return zza(context, context.getClass().getName(), intent, serviceConnection, i);
    }
}
