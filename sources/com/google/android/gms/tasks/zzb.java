package com.google.android.gms.tasks;

final class zzb implements Runnable {
    private /* synthetic */ Task zzbLV;
    private /* synthetic */ zza zzbLW;

    zzb(zza zza, Task task) {
        this.zzbLW = zza;
        this.zzbLV = task;
    }

    public final void run() {
        try {
            this.zzbLW.zzbLU.setResult(this.zzbLW.zzbLT.then(this.zzbLV));
        } catch (RuntimeExecutionException e) {
            if (e.getCause() instanceof Exception) {
                this.zzbLW.zzbLU.setException((Exception) e.getCause());
            } else {
                this.zzbLW.zzbLU.setException(e);
            }
        } catch (Exception e2) {
            this.zzbLW.zzbLU.setException(e2);
        }
    }
}
