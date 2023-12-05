package com.google.firebase.iid;

import android.content.Intent;
import android.util.Log;

final class zze implements Runnable {
    private /* synthetic */ Intent val$intent;
    private /* synthetic */ zzd zzcnf;

    zze(zzd zzd, Intent intent) {
        this.zzcnf = zzd;
        this.val$intent = intent;
    }

    public final void run() {
        String valueOf = String.valueOf(this.val$intent.getAction());
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 61);
        sb.append("Service took too long to process intent: ");
        sb.append(valueOf);
        sb.append(" App may get closed.");
        Log.w("EnhancedIntentService", sb.toString());
        this.zzcnf.finish();
    }
}
