package com.google.android.gms.tasks;

final class zzj implements Runnable {
    private /* synthetic */ Task zzbLV;
    private /* synthetic */ zzi zzbMd;

    zzj(zzi zzi, Task task) {
        this.zzbMd = zzi;
        this.zzbLV = task;
    }

    public final void run() {
        synchronized (this.zzbMd.mLock) {
            if (this.zzbMd.zzbMc != null) {
                this.zzbMd.zzbMc.onSuccess(this.zzbLV.getResult());
            }
        }
    }
}
