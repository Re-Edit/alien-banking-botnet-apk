package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzctm;

final class zzctt extends zzctm.zze {
    private /* synthetic */ String zzbBY;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzctt(zzctm zzctm, GoogleApiClient googleApiClient, String str) {
        super(googleApiClient);
        this.zzbBY = str;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzctk) ((zzctz) zzb).zzrd()).zza(this.zzbCa, this.zzbBY);
    }
}
