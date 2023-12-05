package com.google.android.gms.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.internal.zzq;

public final class zzbda<O extends Api.ApiOptions> extends GoogleApi<O> {
    private final Api.zza<? extends zzcuw, zzcux> zzaBg;
    private final Api.zze zzaCA;
    private final zzbcu zzaCB;
    private final zzq zzaCC;

    public zzbda(@NonNull Context context, Api<O> api, Looper looper, @NonNull Api.zze zze, @NonNull zzbcu zzbcu, zzq zzq, Api.zza<? extends zzcuw, zzcux> zza) {
        super(context, api, looper);
        this.zzaCA = zze;
        this.zzaCB = zzbcu;
        this.zzaCC = zzq;
        this.zzaBg = zza;
        this.zzaAP.zzb((GoogleApi<?>) this);
    }

    public final Api.zze zza(Looper looper, zzbep<O> zzbep) {
        this.zzaCB.zza(zzbep);
        return this.zzaCA;
    }

    public final zzbfv zza(Context context, Handler handler) {
        return new zzbfv(context, handler, this.zzaCC, this.zzaBg);
    }

    public final Api.zze zzpH() {
        return this.zzaCA;
    }
}
