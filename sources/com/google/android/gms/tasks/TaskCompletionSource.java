package com.google.android.gms.tasks;

import android.support.annotation.NonNull;

public class TaskCompletionSource<TResult> {
    private final zzn<TResult> zzbMg = new zzn<>();

    @NonNull
    public Task<TResult> getTask() {
        return this.zzbMg;
    }

    public void setException(@NonNull Exception exc) {
        this.zzbMg.setException(exc);
    }

    public void setResult(TResult tresult) {
        this.zzbMg.setResult(tresult);
    }

    public boolean trySetException(@NonNull Exception exc) {
        return this.zzbMg.trySetException(exc);
    }

    public boolean trySetResult(TResult tresult) {
        return this.zzbMg.trySetResult(tresult);
    }
}
