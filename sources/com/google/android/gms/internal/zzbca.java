package com.google.android.gms.internal;

import android.os.DeadObjectException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbck;

public final class zzbca<A extends zzbck<? extends Result, Api.zzb>> extends zzbby {
    private A zzaBv;

    public zzbca(int i, A a) {
        super(i);
        this.zzaBv = a;
    }

    public final void zza(@NonNull zzbdf zzbdf, boolean z) {
        zzbdf.zza((zzbcq<? extends Result>) this.zzaBv, z);
    }

    public final void zza(zzbep<?> zzbep) throws DeadObjectException {
        this.zzaBv.zzb(zzbep.zzpH());
    }

    public final void zzp(@NonNull Status status) {
        this.zzaBv.zzr(status);
    }
}
