package com.google.android.gms.tasks;

final class zzh implements Runnable {
    private /* synthetic */ Task zzbLV;
    private /* synthetic */ zzg zzbMb;

    zzh(zzg zzg, Task task) {
        this.zzbMb = zzg;
        this.zzbLV = task;
    }

    public final void run() {
        synchronized (this.zzbMb.mLock) {
            if (this.zzbMb.zzbMa != null) {
                this.zzbMb.zzbMa.onFailure(this.zzbLV.getException());
            }
        }
    }
}
