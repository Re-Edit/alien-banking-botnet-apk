package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.zza;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.Collections;
import java.util.Map;

final class zzbde implements OnCompleteListener<Void> {
    private /* synthetic */ zzbdb zzaCR;
    private zzbfu zzaCS;

    zzbde(zzbdb zzbdb, zzbfu zzbfu) {
        this.zzaCR = zzbdb;
        this.zzaCS = zzbfu;
    }

    /* access modifiers changed from: package-private */
    public final void cancel() {
        this.zzaCS.zzmD();
    }

    public final void onComplete(@NonNull Task<Void> task) {
        zzbfu zzbfu;
        Map zzg;
        this.zzaCR.zzaCx.lock();
        try {
            if (!this.zzaCR.zzaCM) {
                zzbfu = this.zzaCS;
            } else {
                if (task.isSuccessful()) {
                    Map unused = this.zzaCR.zzaCO = new ArrayMap(this.zzaCR.zzaCE.size());
                    for (zzbda zzpf : this.zzaCR.zzaCE.values()) {
                        this.zzaCR.zzaCO.put(zzpf.zzpf(), ConnectionResult.zzazZ);
                    }
                } else if (task.getException() instanceof zza) {
                    zza zza = (zza) task.getException();
                    if (this.zzaCR.zzaCK) {
                        Map unused2 = this.zzaCR.zzaCO = new ArrayMap(this.zzaCR.zzaCE.size());
                        for (zzbda zzbda : this.zzaCR.zzaCE.values()) {
                            zzbcf zzpf2 = zzbda.zzpf();
                            ConnectionResult zza2 = zza.zza(zzbda);
                            if (this.zzaCR.zza((zzbda<?>) zzbda, zza2)) {
                                zzg = this.zzaCR.zzaCO;
                                zza2 = new ConnectionResult(16);
                            } else {
                                zzg = this.zzaCR.zzaCO;
                            }
                            zzg.put(zzpf2, zza2);
                        }
                    } else {
                        Map unused3 = this.zzaCR.zzaCO = zza.zzpd();
                    }
                } else {
                    Log.e("ConnectionlessGAC", "Unexpected availability exception", task.getException());
                    Map unused4 = this.zzaCR.zzaCO = Collections.emptyMap();
                }
                if (this.zzaCR.isConnected()) {
                    this.zzaCR.zzaCN.putAll(this.zzaCR.zzaCO);
                    if (this.zzaCR.zzpL() == null) {
                        this.zzaCR.zzpJ();
                        this.zzaCR.zzpK();
                        this.zzaCR.zzaCI.signalAll();
                    }
                }
                zzbfu = this.zzaCS;
            }
            zzbfu.zzmD();
        } finally {
            this.zzaCR.zzaCx.unlock();
        }
    }
}
