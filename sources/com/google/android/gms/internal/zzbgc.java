package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.tasks.TaskCompletionSource;

public abstract class zzbgc<A extends Api.zzb, TResult> {
    /* access modifiers changed from: protected */
    public abstract void zza(A a, TaskCompletionSource<TResult> taskCompletionSource) throws RemoteException;
}
