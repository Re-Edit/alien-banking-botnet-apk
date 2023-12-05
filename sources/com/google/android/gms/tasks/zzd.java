package com.google.android.gms.tasks;

final class zzd implements Runnable {
    private /* synthetic */ Task zzbLV;
    private /* synthetic */ zzc zzbLX;

    zzd(zzc zzc, Task task) {
        this.zzbLX = zzc;
        this.zzbLV = task;
    }

    public final void run() {
        try {
            Task task = (Task) this.zzbLX.zzbLT.then(this.zzbLV);
            if (task == null) {
                this.zzbLX.onFailure(new NullPointerException("Continuation returned null"));
                return;
            }
            task.addOnSuccessListener(TaskExecutors.zzbMh, this.zzbLX);
            task.addOnFailureListener(TaskExecutors.zzbMh, (OnFailureListener) this.zzbLX);
        } catch (RuntimeExecutionException e) {
            if (e.getCause() instanceof Exception) {
                this.zzbLX.zzbLU.setException((Exception) e.getCause());
            } else {
                this.zzbLX.zzbLU.setException(e);
            }
        } catch (Exception e2) {
            this.zzbLX.zzbLU.setException(e2);
        }
    }
}
