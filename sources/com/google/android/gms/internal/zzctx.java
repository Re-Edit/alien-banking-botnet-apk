package com.google.android.gms.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzctm;
import com.google.android.gms.safetynet.zzf;

final class zzctx extends zzctg {
    private /* synthetic */ zzctm.zze zzbCe;

    zzctx(zzctm.zze zze) {
        this.zzbCe = zze;
    }

    public final void zza(Status status, zzf zzf) {
        this.zzbCe.setResult(new zzctm.zzh(status, zzf));
    }
}
