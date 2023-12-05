package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzctm;
import java.util.List;

final class zzcto extends zzctm.zzf {
    private /* synthetic */ String zzbBT;
    private /* synthetic */ List zzbBU;
    private /* synthetic */ String zzbBV;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcto(zzctm zzctm, GoogleApiClient googleApiClient, List list, String str, String str2) {
        super(googleApiClient);
        this.zzbBU = list;
        this.zzbBV = str;
        this.zzbBT = str2;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzctz) zzb).zza(this.zzbCa, this.zzbBU, 2, this.zzbBV, this.zzbBT);
    }
}
