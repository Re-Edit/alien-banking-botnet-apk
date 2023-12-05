package com.google.android.gms.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzbcc<TResult> extends zzbby {
    private final zzbgc<Api.zzb, TResult> zzaBy;
    private final zzbfy zzaBz;
    private final TaskCompletionSource<TResult> zzalG;

    public zzbcc(int i, zzbgc<Api.zzb, TResult> zzbgc, TaskCompletionSource<TResult> taskCompletionSource, zzbfy zzbfy) {
        super(i);
        this.zzalG = taskCompletionSource;
        this.zzaBy = zzbgc;
        this.zzaBz = zzbfy;
    }

    public final void zza(@NonNull zzbdf zzbdf, boolean z) {
        zzbdf.zza(this.zzalG, z);
    }

    public final void zza(zzbep<?> zzbep) throws DeadObjectException {
        try {
            this.zzaBy.zza(zzbep.zzpH(), this.zzalG);
        } catch (DeadObjectException e) {
            throw e;
        } catch (RemoteException e2) {
            zzp(zzbby.zza(e2));
        }
    }

    public final void zzp(@NonNull Status status) {
        this.zzalG.trySetException(this.zzaBz.zzq(status));
    }
}
