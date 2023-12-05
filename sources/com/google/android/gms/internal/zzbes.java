package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;

final class zzbes implements Runnable {
    private /* synthetic */ zzbep zzaEx;
    private /* synthetic */ ConnectionResult zzaEy;

    zzbes(zzbep zzbep, ConnectionResult connectionResult) {
        this.zzaEx = zzbep;
        this.zzaEy = connectionResult;
    }

    public final void run() {
        this.zzaEx.onConnectionFailed(this.zzaEy);
    }
}
