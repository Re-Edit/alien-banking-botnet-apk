package com.google.firebase.iid;

import android.content.Intent;

final class zzc implements Runnable {
    private /* synthetic */ Intent val$intent;
    private /* synthetic */ Intent zzcna;
    private /* synthetic */ zzb zzcnb;

    zzc(zzb zzb, Intent intent, Intent intent2) {
        this.zzcnb = zzb;
        this.val$intent = intent;
        this.zzcna = intent2;
    }

    public final void run() {
        this.zzcnb.handleIntent(this.val$intent);
        this.zzcnb.zzm(this.zzcna);
    }
}
