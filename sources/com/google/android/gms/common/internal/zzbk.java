package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzbk {
    private static final zzbq zzaIk = new zzbl();

    public static <R extends Result, T extends Response<R>> Task<T> zza(PendingResult<R> pendingResult, T t) {
        return zza(pendingResult, new zzbn(t));
    }

    private static <R extends Result, T> Task<T> zza(PendingResult<R> pendingResult, zzbp<R, T> zzbp) {
        zzbq zzbq = zzaIk;
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        pendingResult.zza(new zzbm(pendingResult, taskCompletionSource, zzbp, zzbq));
        return taskCompletionSource.getTask();
    }

    public static <R extends Result> Task<Void> zzb(PendingResult<R> pendingResult) {
        return zza(pendingResult, new zzbo());
    }
}
