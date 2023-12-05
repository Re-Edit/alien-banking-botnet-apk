package com.google.android.gms.common.internal;

import android.util.Log;

public final class zzak {
    private static int zzaIb = 15;
    private static final String zzaIc = null;
    private final String zzaId;
    private final String zzaIe;

    public zzak(String str) {
        this(str, (String) null);
    }

    public zzak(String str, String str2) {
        zzbr.zzb(str, (Object) "log tag cannot be null");
        zzbr.zzb(str.length() <= 23, "tag \"%s\" is longer than the %d character maximum", str, 23);
        this.zzaId = str;
        if (str2 == null || str2.length() <= 0) {
            this.zzaIe = null;
        } else {
            this.zzaIe = str2;
        }
    }

    private final boolean zzaB(int i) {
        return Log.isLoggable(this.zzaId, i);
    }

    private final String zzcE(String str) {
        String str2 = this.zzaIe;
        return str2 == null ? str : str2.concat(str);
    }

    public final void zzb(String str, String str2, Throwable th) {
        if (zzaB(4)) {
            Log.i(str, zzcE(str2), th);
        }
    }

    public final void zzc(String str, String str2, Throwable th) {
        if (zzaB(5)) {
            Log.w(str, zzcE(str2), th);
        }
    }

    public final void zzd(String str, String str2, Throwable th) {
        if (zzaB(6)) {
            Log.e(str, zzcE(str2), th);
        }
    }

    public final void zze(String str, String str2, Throwable th) {
        if (zzaB(7)) {
            Log.e(str, zzcE(str2), th);
            Log.wtf(str, zzcE(str2), th);
        }
    }

    public final void zzx(String str, String str2) {
        if (zzaB(3)) {
            Log.d(str, zzcE(str2));
        }
    }

    public final void zzy(String str, String str2) {
        if (zzaB(5)) {
            Log.w(str, zzcE(str2));
        }
    }

    public final void zzz(String str, String str2) {
        if (zzaB(6)) {
            Log.e(str, zzcE(str2));
        }
    }
}
