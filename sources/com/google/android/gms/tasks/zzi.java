package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

final class zzi<TResult> implements zzk<TResult> {
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    private final Executor zzbEs;
    /* access modifiers changed from: private */
    public OnSuccessListener<? super TResult> zzbMc;

    public zzi(@NonNull Executor executor, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        this.zzbEs = executor;
        this.zzbMc = onSuccessListener;
    }

    public final void cancel() {
        synchronized (this.mLock) {
            this.zzbMc = null;
        }
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        if (task.isSuccessful()) {
            synchronized (this.mLock) {
                if (this.zzbMc != null) {
                    this.zzbEs.execute(new zzj(this, task));
                }
            }
        }
    }
}
