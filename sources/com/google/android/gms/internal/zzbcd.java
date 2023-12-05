package com.google.android.gms.internal;

import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzbcd extends zzbbz {
    private zzbfk<?> zzaBA;

    public zzbcd(zzbfk<?> zzbfk, TaskCompletionSource<Void> taskCompletionSource) {
        super(4, taskCompletionSource);
        this.zzaBA = zzbfk;
    }

    public final /* bridge */ /* synthetic */ void zza(@NonNull zzbdf zzbdf, boolean z) {
    }

    public final void zzb(zzbep<?> zzbep) throws RemoteException {
        zzbfr remove = zzbep.zzqq().remove(this.zzaBA);
        if (remove != null) {
            remove.zzaBx.zzc(zzbep.zzpH(), this.zzalG);
            remove.zzaBw.zzqF();
            return;
        }
        Log.wtf("UnregisterListenerTask", "Received call to unregister a listener without a matching registration call.", new Exception());
        this.zzalG.trySetException(new ApiException(Status.zzaBq));
    }

    public final /* bridge */ /* synthetic */ void zzp(@NonNull Status status) {
        super.zzp(status);
    }
}
