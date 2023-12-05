package com.google.android.gms.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzctm;

final class zzctv extends zzctg {
    private /* synthetic */ zzctm.zzc zzbCc;

    zzctv(zzctm.zzc zzc) {
        this.zzbCc = zzc;
    }

    public final void zza(Status status, boolean z) {
        this.zzbCc.setResult(new zzctm.zzj(status, z));
    }
}
