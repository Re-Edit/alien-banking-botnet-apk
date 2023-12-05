package com.google.android.gms.internal;

import android.support.annotation.BinderThread;
import java.lang.ref.WeakReference;

final class zzbdw extends zzcvb {
    private final WeakReference<zzbdp> zzaDs;

    zzbdw(zzbdp zzbdp) {
        this.zzaDs = new WeakReference<>(zzbdp);
    }

    @BinderThread
    public final void zzb(zzcvj zzcvj) {
        zzbdp zzbdp = (zzbdp) this.zzaDs.get();
        if (zzbdp != null) {
            zzbdp.zzaDb.zza((zzbek) new zzbdx(this, zzbdp, zzbdp, zzcvj));
        }
    }
}
