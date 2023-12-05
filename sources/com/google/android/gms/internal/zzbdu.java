package com.google.android.gms.internal;

import android.app.PendingIntent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzj;

final class zzbdu extends zzbek {
    private /* synthetic */ zzj zzaDw;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbdu(zzbds zzbds, zzbei zzbei, zzj zzj) {
        super(zzbei);
        this.zzaDw = zzj;
    }

    public final void zzpT() {
        this.zzaDw.zzf(new ConnectionResult(16, (PendingIntent) null));
    }
}
