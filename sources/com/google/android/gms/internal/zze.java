package com.google.android.gms.internal;

final class zze implements Runnable {
    private /* synthetic */ zzp zzl;
    private /* synthetic */ zzd zzm;

    zze(zzd zzd, zzp zzp) {
        this.zzm = zzd;
        this.zzl = zzp;
    }

    public final void run() {
        try {
            this.zzm.zzh.put(this.zzl);
        } catch (InterruptedException unused) {
        }
    }
}
