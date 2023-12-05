package com.google.android.gms.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzj;
import java.lang.ref.WeakReference;

final class zzbdr implements zzj {
    /* access modifiers changed from: private */
    public final boolean zzaCl;
    private final WeakReference<zzbdp> zzaDs;
    private final Api<?> zzayY;

    public zzbdr(zzbdp zzbdp, Api<?> api, boolean z) {
        this.zzaDs = new WeakReference<>(zzbdp);
        this.zzayY = api;
        this.zzaCl = z;
    }

    public final void zzf(@NonNull ConnectionResult connectionResult) {
        zzbdp zzbdp = (zzbdp) this.zzaDs.get();
        if (zzbdp != null) {
            zzbr.zza(Looper.myLooper() == zzbdp.zzaDb.zzaCn.getLooper(), (Object) "onReportServiceBinding must be called on the GoogleApiClient handler thread");
            zzbdp.zzaCx.lock();
            try {
                if (zzbdp.zzan(0)) {
                    if (!connectionResult.isSuccess()) {
                        zzbdp.zzb(connectionResult, this.zzayY, this.zzaCl);
                    }
                    if (zzbdp.zzpU()) {
                        zzbdp.zzpV();
                    }
                }
            } finally {
                zzbdp.zzaCx.unlock();
            }
        }
    }
}
