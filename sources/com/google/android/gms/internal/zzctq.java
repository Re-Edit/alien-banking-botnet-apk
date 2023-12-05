package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzctm;

final class zzctq extends zzctm.zzc {
    zzctq(zzctm zzctm, GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzctk) ((zzctz) zzb).zzrd()).zzb(this.zzbCa);
    }
}
