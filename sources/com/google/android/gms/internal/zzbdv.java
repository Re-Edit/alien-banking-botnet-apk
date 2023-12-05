package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.api.Api;
import java.util.ArrayList;

final class zzbdv extends zzbdz {
    private /* synthetic */ zzbdp zzaDr;
    private final ArrayList<Api.zze> zzaDx;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zzbdv(zzbdp zzbdp, ArrayList<Api.zze> arrayList) {
        super(zzbdp, (zzbdq) null);
        this.zzaDr = zzbdp;
        this.zzaDx = arrayList;
    }

    @WorkerThread
    public final void zzpT() {
        this.zzaDr.zzaDb.zzaCn.zzaDI = this.zzaDr.zzpZ();
        ArrayList arrayList = this.zzaDx;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Api.zze) obj).zza(this.zzaDr.zzaDn, this.zzaDr.zzaDb.zzaCn.zzaDI);
        }
    }
}
