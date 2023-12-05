package com.google.android.gms.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzctm;
import com.google.android.gms.safetynet.zza;

final class zzctu extends zzctg {
    private /* synthetic */ zzctm.zzb zzbCb;

    zzctu(zzctm.zzb zzb) {
        this.zzbCb = zzb;
    }

    public final void zza(Status status, zza zza) {
        this.zzbCb.setResult(new zzctm.zza(status, zza));
    }
}
