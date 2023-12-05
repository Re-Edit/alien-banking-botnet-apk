package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

final class zzc<TResult, TContinuationResult> implements OnFailureListener, OnSuccessListener<TContinuationResult>, zzk<TResult> {
    private final Executor zzbEs;
    /* access modifiers changed from: private */
    public final Continuation<TResult, Task<TContinuationResult>> zzbLT;
    /* access modifiers changed from: private */
    public final zzn<TContinuationResult> zzbLU;

    public zzc(@NonNull Executor executor, @NonNull Continuation<TResult, Task<TContinuationResult>> continuation, @NonNull zzn<TContinuationResult> zzn) {
        this.zzbEs = executor;
        this.zzbLT = continuation;
        this.zzbLU = zzn;
    }

    public final void cancel() {
        throw new UnsupportedOperationException();
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        this.zzbEs.execute(new zzd(this, task));
    }

    public final void onFailure(@NonNull Exception exc) {
        this.zzbLU.setException(exc);
    }

    public final void onSuccess(TContinuationResult tcontinuationresult) {
        this.zzbLU.setResult(tcontinuationresult);
    }
}
