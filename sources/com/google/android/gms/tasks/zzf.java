package com.google.android.gms.tasks;

final class zzf implements Runnable {
    private /* synthetic */ Task zzbLV;
    private /* synthetic */ zze zzbLZ;

    zzf(zze zze, Task task) {
        this.zzbLZ = zze;
        this.zzbLV = task;
    }

    public final void run() {
        synchronized (this.zzbLZ.mLock) {
            if (this.zzbLZ.zzbLY != null) {
                this.zzbLZ.zzbLY.onComplete(this.zzbLV);
            }
        }
    }
}
