package com.google.android.gms.tasks;

import java.util.concurrent.Callable;

final class zzo implements Runnable {
    private /* synthetic */ Callable zzZq;
    private /* synthetic */ zzn zzbMm;

    zzo(zzn zzn, Callable callable) {
        this.zzbMm = zzn;
        this.zzZq = callable;
    }

    public final void run() {
        try {
            this.zzbMm.setResult(this.zzZq.call());
        } catch (Exception e) {
            this.zzbMm.setException(e);
        }
    }
}
