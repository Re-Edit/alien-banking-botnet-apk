package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.zzj;
import java.util.Iterator;
import java.util.Map;

final class zzbds extends zzbdz {
    final /* synthetic */ zzbdp zzaDr;
    private final Map<Api.zze, zzbdr> zzaDt;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zzbds(zzbdp zzbdp, Map<Api.zze, zzbdr> map) {
        super(zzbdp, (zzbdq) null);
        this.zzaDr = zzbdp;
        this.zzaDt = map;
    }

    @WorkerThread
    public final void zzpT() {
        boolean z;
        Iterator<Api.zze> it = this.zzaDt.keySet().iterator();
        boolean z2 = true;
        int i = 0;
        boolean z3 = false;
        boolean z4 = true;
        while (true) {
            if (!it.hasNext()) {
                z2 = z3;
                z = false;
                break;
            }
            Api.zze next = it.next();
            if (!next.zzpc()) {
                z4 = false;
            } else if (!this.zzaDt.get(next).zzaCl) {
                z = true;
                break;
            } else {
                z3 = true;
            }
        }
        if (z2) {
            i = this.zzaDr.zzaCH.isGooglePlayServicesAvailable(this.zzaDr.mContext);
        }
        if (i == 0 || (!z && !z4)) {
            if (this.zzaDr.zzaDl) {
                this.zzaDr.zzaDj.connect();
            }
            for (Api.zze next2 : this.zzaDt.keySet()) {
                zzj zzj = this.zzaDt.get(next2);
                if (!next2.zzpc() || i == 0) {
                    next2.zza(zzj);
                } else {
                    this.zzaDr.zzaDb.zza((zzbek) new zzbdu(this, this.zzaDr, zzj));
                }
            }
            return;
        }
        this.zzaDr.zzaDb.zza((zzbek) new zzbdt(this, this.zzaDr, new ConnectionResult(i, (PendingIntent) null)));
    }
}
