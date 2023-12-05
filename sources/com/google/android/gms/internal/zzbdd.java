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

final class zzbdd implements OnCompleteListener<Void> {
    private /* synthetic */ zzbdb zzaCR;

    private zzbdd(zzbdb zzbdb) {
        this.zzaCR = zzbdb;
    }

    public final void onComplete(@NonNull Task<Void> task) {
        zzbdb zzbdb;
        ConnectionResult connectionResult;
        Map zzd;
        this.zzaCR.zzaCx.lock();
        try {
            if (this.zzaCR.zzaCM) {
                if (task.isSuccessful()) {
                    Map unused = this.zzaCR.zzaCN = new ArrayMap(this.zzaCR.zzaCD.size());
                    for (zzbda zzpf : this.zzaCR.zzaCD.values()) {
                        this.zzaCR.zzaCN.put(zzpf.zzpf(), ConnectionResult.zzazZ);
                    }
                } else {
                    if (task.getException() instanceof zza) {
                        zza zza = (zza) task.getException();
                        if (this.zzaCR.zzaCK) {
                            Map unused2 = this.zzaCR.zzaCN = new ArrayMap(this.zzaCR.zzaCD.size());
                            for (zzbda zzbda : this.zzaCR.zzaCD.values()) {
                                zzbcf zzpf2 = zzbda.zzpf();
                                ConnectionResult zza2 = zza.zza(zzbda);
                                if (this.zzaCR.zza((zzbda<?>) zzbda, zza2)) {
                                    zzd = this.zzaCR.zzaCN;
                                    zza2 = new ConnectionResult(16);
                                } else {
                                    zzd = this.zzaCR.zzaCN;
                                }
                                zzd.put(zzpf2, zza2);
                            }
                        } else {
                            Map unused3 = this.zzaCR.zzaCN = zza.zzpd();
                        }
                        zzbdb = this.zzaCR;
                        connectionResult = this.zzaCR.zzpL();
                    } else {
                        Log.e("ConnectionlessGAC", "Unexpected availability exception", task.getException());
                        Map unused4 = this.zzaCR.zzaCN = Collections.emptyMap();
                        zzbdb = this.zzaCR;
                        connectionResult = new ConnectionResult(8);
                    }
                    ConnectionResult unused5 = zzbdb.zzaCQ = connectionResult;
                }
                if (this.zzaCR.zzaCO != null) {
                    this.zzaCR.zzaCN.putAll(this.zzaCR.zzaCO);
                    ConnectionResult unused6 = this.zzaCR.zzaCQ = this.zzaCR.zzpL();
                }
                if (this.zzaCR.zzaCQ == null) {
                    this.zzaCR.zzpJ();
                    this.zzaCR.zzpK();
                } else {
                    boolean unused7 = this.zzaCR.zzaCM = false;
                    this.zzaCR.zzaCG.zzc(this.zzaCR.zzaCQ);
                }
                this.zzaCR.zzaCI.signalAll();
            }
        } finally {
            this.zzaCR.zzaCx.unlock();
        }
    }
}
