package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzbr;

final class zzbcn {
    private final int zzaBR;
    private final ConnectionResult zzaBS;

    zzbcn(ConnectionResult connectionResult, int i) {
        zzbr.zzu(connectionResult);
        this.zzaBS = connectionResult;
        this.zzaBR = i;
    }

    /* access modifiers changed from: package-private */
    public final int zzpw() {
        return this.zzaBR;
    }

    /* access modifiers changed from: package-private */
    public final ConnectionResult zzpx() {
        return this.zzaBS;
    }
}
