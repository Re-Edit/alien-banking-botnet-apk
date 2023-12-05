package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.internal.zzak;

public final class zzbhn {
    private final String mTag;
    private final zzak zzaIC;
    private final String zzaIe;
    private final int zzagZ;

    private zzbhn(String str, String str2) {
        this.zzaIe = str2;
        this.mTag = str;
        this.zzaIC = new zzak(str);
        this.zzagZ = getLogLevel();
    }

    public zzbhn(String str, String... strArr) {
        this(str, zzb(strArr));
    }

    private final String format(String str, @Nullable Object... objArr) {
        if (objArr != null && objArr.length > 0) {
            str = String.format(str, objArr);
        }
        return this.zzaIe.concat(str);
    }

    private final int getLogLevel() {
        int i = 2;
        while (7 >= i && !Log.isLoggable(this.mTag, i)) {
            i++;
        }
        return i;
    }

    private static String zzb(String... strArr) {
        if (strArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (String str : strArr) {
            if (sb.length() > 1) {
                sb.append(",");
            }
            sb.append(str);
        }
        sb.append(']');
        sb.append(' ');
        return sb.toString();
    }

    private final boolean zzz(int i) {
        return this.zzagZ <= i;
    }

    public final void zza(String str, Throwable th, @Nullable Object... objArr) {
        Log.e(this.mTag, format(str, objArr), th);
    }

    public final void zza(String str, @Nullable Object... objArr) {
        if (zzz(2)) {
            Log.v(this.mTag, format(str, objArr));
        }
    }

    public final void zzb(String str, Throwable th, @Nullable Object... objArr) {
        Log.wtf(this.mTag, format(str, objArr), th);
    }

    public final void zzb(String str, @Nullable Object... objArr) {
        if (zzz(3)) {
            Log.d(this.mTag, format(str, objArr));
        }
    }

    public final void zzd(Throwable th) {
        Log.wtf(this.mTag, th);
    }

    public final void zze(String str, @Nullable Object... objArr) {
        Log.i(this.mTag, format(str, objArr));
    }

    public final void zzf(String str, @Nullable Object... objArr) {
        Log.w(this.mTag, format(str, objArr));
    }
}
