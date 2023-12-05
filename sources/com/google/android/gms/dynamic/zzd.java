package com.google.android.gms.dynamic;

import android.os.Bundle;

final class zzd implements zzi {
    private /* synthetic */ zza zzaSz;
    private /* synthetic */ Bundle zzxY;

    zzd(zza zza, Bundle bundle) {
        this.zzaSz = zza;
        this.zzxY = bundle;
    }

    public final int getState() {
        return 1;
    }

    public final void zzb(LifecycleDelegate lifecycleDelegate) {
        this.zzaSz.zzaSv.onCreate(this.zzxY);
    }
}
