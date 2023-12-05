package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.DeadObjectException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzca;

public final class zzbdm implements zzbei {
    /* access modifiers changed from: private */
    public final zzbej zzaDb;
    private boolean zzaDc = false;

    public zzbdm(zzbej zzbej) {
        this.zzaDb = zzbej;
    }

    public final void begin() {
    }

    public final void connect() {
        if (this.zzaDc) {
            this.zzaDc = false;
            this.zzaDb.zza((zzbek) new zzbdo(this, this));
        }
    }

    public final boolean disconnect() {
        if (this.zzaDc) {
            return false;
        }
        if (this.zzaDb.zzaCn.zzqd()) {
            this.zzaDc = true;
            for (zzbge zzqI : this.zzaDb.zzaCn.zzaDM) {
                zzqI.zzqI();
            }
            return false;
        }
        this.zzaDb.zzg((ConnectionResult) null);
        return true;
    }

    public final void onConnected(Bundle bundle) {
    }

    public final void onConnectionSuspended(int i) {
        this.zzaDb.zzg((ConnectionResult) null);
        this.zzaDb.zzaEa.zze(i, this.zzaDc);
    }

    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean z) {
    }

    public final <A extends Api.zzb, R extends Result, T extends zzbck<R, A>> T zzd(T t) {
        return zze(t);
    }

    public final <A extends Api.zzb, T extends zzbck<? extends Result, A>> T zze(T t) {
        try {
            this.zzaDb.zzaCn.zzaDN.zzb(t);
            zzbeb zzbeb = this.zzaDb.zzaCn;
            Api.zze zze = zzbeb.zzaDH.get(t.zzpb());
            zzbr.zzb(zze, (Object) "Appropriate Api was not requested.");
            if (zze.isConnected() || !this.zzaDb.zzaDW.containsKey(t.zzpb())) {
                if (zze instanceof zzca) {
                    zzca zzca = (zzca) zze;
                    zze = null;
                }
                t.zzb(zze);
                return t;
            }
            t.zzr(new Status(17));
            return t;
        } catch (DeadObjectException unused) {
            this.zzaDb.zza((zzbek) new zzbdn(this, this));
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzpS() {
        if (this.zzaDc) {
            this.zzaDc = false;
            this.zzaDb.zzaCn.zzaDN.release();
            disconnect();
        }
    }
}
