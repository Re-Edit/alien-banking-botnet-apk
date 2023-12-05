package com.google.firebase.iid;

import android.util.Log;

final class zzg implements Runnable {
    private /* synthetic */ zzd zzcnh;
    private /* synthetic */ zzf zzcni;

    zzg(zzf zzf, zzd zzd) {
        this.zzcni = zzf;
        this.zzcnh = zzd;
    }

    public final void run() {
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            Log.d("EnhancedIntentService", "bg processing of the intent starting now");
        }
        this.zzcni.zzcng.handleIntent(this.zzcnh.intent);
        this.zzcnh.finish();
    }
}
