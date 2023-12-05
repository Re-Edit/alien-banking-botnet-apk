package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

final class zze<TResult> implements zzk<TResult> {
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    private final Executor zzbEs;
    /* access modifiers changed from: private */
    public OnCompleteListener<TResult> zzbLY;

    public zze(@NonNull Executor executor, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        this.zzbEs = executor;
        this.zzbLY = onCompleteListener;
    }

    public final void cancel() {
        synchronized (this.mLock) {
            this.zzbLY = null;
        }
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        synchronized (this.mLock) {
            if (this.zzbLY != null) {
                this.zzbEs.execute(new zzf(this, task));
            }
        }
    }
}
