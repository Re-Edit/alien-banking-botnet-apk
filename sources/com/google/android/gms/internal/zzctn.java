package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzctm;

final class zzctn extends zzctm.zzb {
    private /* synthetic */ byte[] zzbBS;
    private /* synthetic */ String zzbBT;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzctn(GoogleApiClient googleApiClient, byte[] bArr, String str) {
        super(googleApiClient);
        this.zzbBS = bArr;
        this.zzbBT = str;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzctz) zzb).zzb(this.zzbCa, this.zzbBS, this.zzbBT);
    }
}
