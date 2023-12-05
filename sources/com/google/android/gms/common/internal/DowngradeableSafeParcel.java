package com.google.android.gms.common.internal;

import com.google.android.gms.common.internal.safeparcel.zza;

public abstract class DowngradeableSafeParcel extends zza implements ReflectedParcelable {
    private static final Object zzaHs = new Object();
    private static ClassLoader zzaHt = null;
    private static Integer zzaHu = null;
    private boolean zzaHv = false;

    protected static boolean zzcA(String str) {
        zzru();
        return true;
    }

    private static ClassLoader zzru() {
        synchronized (zzaHs) {
        }
        return null;
    }

    protected static Integer zzrv() {
        synchronized (zzaHs) {
        }
        return null;
    }
}
