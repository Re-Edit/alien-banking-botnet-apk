package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public final class zzbgh {
    public static final Status zzaFl = new Status(8, "The connection to Google Play services was lost");
    private static final zzbcq<?>[] zzaFm = new zzbcq[0];
    private final Map<Api.zzc<?>, Api.zze> zzaDH;
    final Set<zzbcq<?>> zzaFn = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
    private final zzbgj zzaFo = new zzbgi(this);

    public zzbgh(Map<Api.zzc<?>, Api.zze> map) {
        this.zzaDH = map;
    }

    public final void release() {
        for (zzbcq zzbcq : (zzbcq[]) this.zzaFn.toArray(zzaFm)) {
            zzbcq.zza((zzbgj) null);
            zzbcq.zzpm();
            if (zzbcq.zzpz()) {
                this.zzaFn.remove(zzbcq);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzb(zzbcq<? extends Result> zzbcq) {
        this.zzaFn.add(zzbcq);
        zzbcq.zza(this.zzaFo);
    }

    public final void zzqK() {
        for (zzbcq zzs : (zzbcq[]) this.zzaFn.toArray(zzaFm)) {
            zzs.zzs(zzaFl);
        }
    }
}
