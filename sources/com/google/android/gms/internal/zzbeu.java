package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzam;
import java.util.Collections;

final class zzbeu implements Runnable {
    private /* synthetic */ zzbet zzaEA;
    private /* synthetic */ ConnectionResult zzaEy;

    zzbeu(zzbet zzbet, ConnectionResult connectionResult) {
        this.zzaEA = zzbet;
        this.zzaEy = connectionResult;
    }

    public final void run() {
        if (this.zzaEy.isSuccess()) {
            boolean unused = this.zzaEA.zzaEz = true;
            if (this.zzaEA.zzaCA.zzmt()) {
                this.zzaEA.zzqx();
            } else {
                this.zzaEA.zzaCA.zza((zzam) null, Collections.emptySet());
            }
        } else {
            ((zzbep) this.zzaEA.zzaEo.zzaCD.get(this.zzaEA.zzaAM)).onConnectionFailed(this.zzaEy);
        }
    }
}
