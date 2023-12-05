package com.google.android.gms.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

final class zzbdg implements PendingResult.zza {
    private /* synthetic */ zzbcq zzaCV;
    private /* synthetic */ zzbdf zzaCW;

    zzbdg(zzbdf zzbdf, zzbcq zzbcq) {
        this.zzaCW = zzbdf;
        this.zzaCV = zzbcq;
    }

    public final void zzo(Status status) {
        this.zzaCW.zzaCT.remove(this.zzaCV);
    }
}
