package com.google.android.gms.common.api;

import android.os.Looper;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.internal.zzbce;
import com.google.android.gms.internal.zzbfy;

public final class zzd {
    private zzbfy zzaAO;
    private Looper zzrP;

    public final zzd zza(Looper looper) {
        zzbr.zzb(looper, (Object) "Looper must not be null.");
        this.zzrP = looper;
        return this;
    }

    public final zzd zza(zzbfy zzbfy) {
        zzbr.zzb(zzbfy, (Object) "StatusExceptionMapper must not be null.");
        this.zzaAO = zzbfy;
        return this;
    }

    public final GoogleApi.zza zzph() {
        if (this.zzaAO == null) {
            this.zzaAO = new zzbce();
        }
        if (this.zzrP == null) {
            this.zzrP = Looper.myLooper() != null ? Looper.myLooper() : Looper.getMainLooper();
        }
        return new GoogleApi.zza(this.zzaAO, this.zzrP);
    }
}
