package com.google.android.gms.internal;

abstract class zzbek {
    private final zzbei zzaEb;

    protected zzbek(zzbei zzbei) {
        this.zzaEb = zzbei;
    }

    public final void zzc(zzbej zzbej) {
        zzbej.zzaCx.lock();
        try {
            if (zzbej.zzaDX == this.zzaEb) {
                zzpT();
            }
        } finally {
            zzbej.zzaCx.unlock();
        }
    }

    /* access modifiers changed from: protected */
    public abstract void zzpT();
}
