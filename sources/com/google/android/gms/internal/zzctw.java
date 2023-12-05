package com.google.android.gms.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzctm;
import com.google.android.gms.safetynet.zzd;

final class zzctw extends zzctg {
    private /* synthetic */ zzctm.zzd zzbCd;

    zzctw(zzctm.zzd zzd) {
        this.zzbCd = zzd;
    }

    public final void zza(Status status, zzd zzd) {
        this.zzbCd.setResult(new zzctm.zzg(status, zzd));
    }
}
