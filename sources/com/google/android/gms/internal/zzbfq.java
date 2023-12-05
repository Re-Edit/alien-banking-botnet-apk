package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.tasks.TaskCompletionSource;

public abstract class zzbfq<A extends Api.zzb, L> {
    private final zzbfi<L> zzaEW;

    protected zzbfq(zzbfi<L> zzbfi) {
        this.zzaEW = zzbfi;
    }

    /* access modifiers changed from: protected */
    public abstract void zzb(A a, TaskCompletionSource<Void> taskCompletionSource) throws RemoteException;

    public final zzbfk<L> zzqE() {
        return this.zzaEW.zzqE();
    }

    public final void zzqF() {
        this.zzaEW.clear();
    }
}
