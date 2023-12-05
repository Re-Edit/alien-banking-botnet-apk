package com.google.android.gms.internal;

import android.app.Activity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.util.zzb;

public class zzbdi extends zzbcm {
    private zzben zzaAP;
    private final zzb<zzbcf<?>> zzaCY = new zzb<>();

    private zzbdi(zzbff zzbff) {
        super(zzbff);
        this.zzaEI.zza("ConnectionlessLifecycleHelper", (zzbfe) this);
    }

    public static void zza(Activity activity, zzben zzben, zzbcf<?> zzbcf) {
        zzn(activity);
        zzbff zzn = zzn(activity);
        zzbdi zzbdi = (zzbdi) zzn.zza("ConnectionlessLifecycleHelper", zzbdi.class);
        if (zzbdi == null) {
            zzbdi = new zzbdi(zzn);
        }
        zzbdi.zzaAP = zzben;
        zzbr.zzb(zzbcf, (Object) "ApiKey cannot be null");
        zzbdi.zzaCY.add(zzbcf);
        zzben.zza(zzbdi);
    }

    private final void zzpQ() {
        if (!this.zzaCY.isEmpty()) {
            this.zzaAP.zza(this);
        }
    }

    public final void onResume() {
        super.onResume();
        zzpQ();
    }

    public final void onStart() {
        super.onStart();
        zzpQ();
    }

    public final void onStop() {
        super.onStop();
        this.zzaAP.zzb(this);
    }

    /* access modifiers changed from: protected */
    public final void zza(ConnectionResult connectionResult, int i) {
        this.zzaAP.zza(connectionResult, i);
    }

    /* access modifiers changed from: package-private */
    public final zzb<zzbcf<?>> zzpP() {
        return this.zzaCY;
    }

    /* access modifiers changed from: protected */
    public final void zzpq() {
        this.zzaAP.zzpq();
    }
}
