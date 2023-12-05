package com.google.android.gms.common.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;

public final class zzm implements zzj {
    private /* synthetic */ zzd zzaHg;

    public zzm(zzd zzd) {
        this.zzaHg = zzd;
    }

    public final void zzf(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.isSuccess()) {
            zzd zzd = this.zzaHg;
            zzd.zza((zzam) null, zzd.zzrf());
        } else if (this.zzaHg.zzaGY != null) {
            this.zzaHg.zzaGY.onConnectionFailed(connectionResult);
        }
    }
}
