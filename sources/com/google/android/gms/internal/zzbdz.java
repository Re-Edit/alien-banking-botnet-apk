package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;

abstract class zzbdz implements Runnable {
    private /* synthetic */ zzbdp zzaDr;

    private zzbdz(zzbdp zzbdp) {
        this.zzaDr = zzbdp;
    }

    /* synthetic */ zzbdz(zzbdp zzbdp, zzbdq zzbdq) {
        this(zzbdp);
    }

    @WorkerThread
    public void run() {
        this.zzaDr.zzaCx.lock();
        try {
            if (!Thread.interrupted()) {
                zzpT();
            }
        } catch (RuntimeException e) {
            this.zzaDr.zzaDb.zza(e);
        } catch (Throwable th) {
            this.zzaDr.zzaCx.unlock();
            throw th;
        }
        this.zzaDr.zzaCx.unlock();
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public abstract void zzpT();
}
