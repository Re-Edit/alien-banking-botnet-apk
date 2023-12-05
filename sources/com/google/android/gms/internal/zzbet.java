package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzam;
import com.google.android.gms.common.internal.zzj;
import java.util.Set;

final class zzbet implements zzj, zzbfx {
    /* access modifiers changed from: private */
    public final zzbcf<?> zzaAM;
    /* access modifiers changed from: private */
    public final Api.zze zzaCA;
    private zzam zzaDn = null;
    final /* synthetic */ zzben zzaEo;
    /* access modifiers changed from: private */
    public boolean zzaEz = false;
    private Set<Scope> zzamg = null;

    public zzbet(zzben zzben, Api.zze zze, zzbcf<?> zzbcf) {
        this.zzaEo = zzben;
        this.zzaCA = zze;
        this.zzaAM = zzbcf;
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zzqx() {
        zzam zzam;
        if (this.zzaEz && (zzam = this.zzaDn) != null) {
            this.zzaCA.zza(zzam, this.zzamg);
        }
    }

    @WorkerThread
    public final void zzb(zzam zzam, Set<Scope> set) {
        if (zzam == null || set == null) {
            Log.wtf("GoogleApiManager", "Received null response from onSignInSuccess", new Exception());
            zzh(new ConnectionResult(4));
            return;
        }
        this.zzaDn = zzam;
        this.zzamg = set;
        zzqx();
    }

    public final void zzf(@NonNull ConnectionResult connectionResult) {
        this.zzaEo.mHandler.post(new zzbeu(this, connectionResult));
    }

    @WorkerThread
    public final void zzh(ConnectionResult connectionResult) {
        ((zzbep) this.zzaEo.zzaCD.get(this.zzaAM)).zzh(connectionResult);
    }
}
