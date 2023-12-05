package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

final class zzg<TResult> implements zzk<TResult> {
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    private final Executor zzbEs;
    /* access modifiers changed from: private */
    public OnFailureListener zzbMa;

    public zzg(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        this.zzbEs = executor;
        this.zzbMa = onFailureListener;
    }

    public final void cancel() {
        synchronized (this.mLock) {
            this.zzbMa = null;
        }
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        if (!task.isSuccessful()) {
            synchronized (this.mLock) {
                if (this.zzbMa != null) {
                    this.zzbEs.execute(new zzh(this, task));
                }
            }
        }
    }
}
