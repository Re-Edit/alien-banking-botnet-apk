package com.google.android.gms.internal;

import android.os.RemoteException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzbcb extends zzbbz {
    private zzbfq<Api.zzb, ?> zzaBw;
    private zzbgk<Api.zzb, ?> zzaBx;

    public zzbcb(zzbfr zzbfr, TaskCompletionSource<Void> taskCompletionSource) {
        super(3, taskCompletionSource);
        this.zzaBw = zzbfr.zzaBw;
        this.zzaBx = zzbfr.zzaBx;
    }

    public final /* bridge */ /* synthetic */ void zza(@NonNull zzbdf zzbdf, boolean z) {
    }

    public final void zzb(zzbep<?> zzbep) throws RemoteException {
        this.zzaBw.zzb(zzbep.zzpH(), this.zzalG);
        if (this.zzaBw.zzqE() != null) {
            zzbep.zzqq().put(this.zzaBw.zzqE(), new zzbfr(this.zzaBw, this.zzaBx));
        }
    }

    public final /* bridge */ /* synthetic */ void zzp(@NonNull Status status) {
        super.zzp(status);
    }
}
