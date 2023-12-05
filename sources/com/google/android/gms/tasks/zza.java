package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

final class zza<TResult, TContinuationResult> implements zzk<TResult> {
    private final Executor zzbEs;
    /* access modifiers changed from: private */
    public final Continuation<TResult, TContinuationResult> zzbLT;
    /* access modifiers changed from: private */
    public final zzn<TContinuationResult> zzbLU;

    public zza(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation, @NonNull zzn<TContinuationResult> zzn) {
        this.zzbEs = executor;
        this.zzbLT = continuation;
        this.zzbLU = zzn;
    }

    public final void cancel() {
        throw new UnsupportedOperationException();
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        this.zzbEs.execute(new zzb(this, task));
    }
}
