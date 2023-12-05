package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.DeadObjectException;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbr;

public abstract class zzbck<R extends Result, A extends Api.zzb> extends zzbcq<R> implements zzbcl<R> {
    private final Api.zzc<A> zzaBO;
    private final Api<?> zzayY;

    @Deprecated
    protected zzbck(Api.zzc<A> zzc, GoogleApiClient googleApiClient) {
        super((GoogleApiClient) zzbr.zzb(googleApiClient, (Object) "GoogleApiClient must not be null"));
        this.zzaBO = (Api.zzc) zzbr.zzu(zzc);
        this.zzayY = null;
    }

    protected zzbck(Api<?> api, GoogleApiClient googleApiClient) {
        super((GoogleApiClient) zzbr.zzb(googleApiClient, (Object) "GoogleApiClient must not be null"));
        this.zzaBO = api.zzpb();
        this.zzayY = api;
    }

    private final void zzc(RemoteException remoteException) {
        zzr(new Status(8, remoteException.getLocalizedMessage(), (PendingIntent) null));
    }

    public /* bridge */ /* synthetic */ void setResult(Object obj) {
        super.setResult((Result) obj);
    }

    /* access modifiers changed from: protected */
    public abstract void zza(A a) throws RemoteException;

    public final void zzb(A a) throws DeadObjectException {
        try {
            zza(a);
        } catch (DeadObjectException e) {
            zzc(e);
            throw e;
        } catch (RemoteException e2) {
            zzc(e2);
        }
    }

    public final Api.zzc<A> zzpb() {
        return this.zzaBO;
    }

    public final Api<?> zzpe() {
        return this.zzayY;
    }

    public final void zzr(Status status) {
        zzbr.zzb(!status.isSuccess(), (Object) "Failed result must not be success");
        setResult(zzb(status));
    }
}
