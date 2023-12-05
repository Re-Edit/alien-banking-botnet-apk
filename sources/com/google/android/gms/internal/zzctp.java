package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzctm;
import java.util.ArrayList;

final class zzctp extends zzctm.zzf {
    private /* synthetic */ String zzbBT;
    private /* synthetic */ String zzbBV;
    private /* synthetic */ int[] zzbBW;
    private /* synthetic */ int zzbBX;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzctp(GoogleApiClient googleApiClient, int[] iArr, int i, String str, String str2) {
        super(googleApiClient);
        this.zzbBW = iArr;
        this.zzbBX = i;
        this.zzbBV = str;
        this.zzbBT = str2;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        zzctz zzctz = (zzctz) zzb;
        ArrayList arrayList = new ArrayList();
        for (int valueOf : this.zzbBW) {
            arrayList.add(Integer.valueOf(valueOf));
        }
        zzctz.zza(this.zzbCa, arrayList, this.zzbBX, this.zzbBV, this.zzbBT);
    }
}
