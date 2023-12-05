package com.google.android.gms.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

abstract class zzbbz extends zzbby {
    protected final TaskCompletionSource<Void> zzalG;

    public zzbbz(int i, TaskCompletionSource<Void> taskCompletionSource) {
        super(i);
        this.zzalG = taskCompletionSource;
    }

    public void zza(@NonNull zzbdf zzbdf, boolean z) {
    }

    public final void zza(zzbep<?> zzbep) throws DeadObjectException {
        try {
            zzb(zzbep);
        } catch (DeadObjectException e) {
            zzp(zzbby.zza((RemoteException) e));
            throw e;
        } catch (RemoteException e2) {
            zzp(zzbby.zza(e2));
        }
    }

    /* access modifiers changed from: protected */
    public abstract void zzb(zzbep<?> zzbep) throws RemoteException;

    public void zzp(@NonNull Status status) {
        this.zzalG.trySetException(new ApiException(status));
    }
}
