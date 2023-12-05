package com.google.firebase.iid;

import android.content.BroadcastReceiver;
import android.content.Intent;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

final class zzd {
    final Intent intent;
    private final BroadcastReceiver.PendingResult zzcnc;
    private boolean zzcnd = false;
    private final ScheduledFuture<?> zzcne;

    zzd(Intent intent2, BroadcastReceiver.PendingResult pendingResult, ScheduledExecutorService scheduledExecutorService) {
        this.intent = intent2;
        this.zzcnc = pendingResult;
        this.zzcne = scheduledExecutorService.schedule(new zze(this, intent2), 9500, TimeUnit.MILLISECONDS);
    }

    /* access modifiers changed from: package-private */
    public final synchronized void finish() {
        if (!this.zzcnd) {
            this.zzcnc.finish();
            this.zzcne.cancel(false);
            this.zzcnd = true;
        }
    }
}
