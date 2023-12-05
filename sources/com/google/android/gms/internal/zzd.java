package com.google.android.gms.internal;

import android.os.Process;
import java.util.concurrent.BlockingQueue;

public final class zzd extends Thread {
    private static final boolean DEBUG = zzab.DEBUG;
    private final BlockingQueue<zzp<?>> zzg;
    /* access modifiers changed from: private */
    public final BlockingQueue<zzp<?>> zzh;
    private final zzb zzi;
    private final zzw zzj;
    private volatile boolean zzk = false;

    public zzd(BlockingQueue<zzp<?>> blockingQueue, BlockingQueue<zzp<?>> blockingQueue2, zzb zzb, zzw zzw) {
        this.zzg = blockingQueue;
        this.zzh = blockingQueue2;
        this.zzi = zzb;
        this.zzj = zzw;
    }

    public final void quit() {
        this.zzk = true;
        interrupt();
    }

    public final void run() {
        BlockingQueue<zzp<?>> blockingQueue;
        if (DEBUG) {
            zzab.zza("start new dispatcher", new Object[0]);
        }
        Process.setThreadPriority(10);
        this.zzi.initialize();
        while (true) {
            try {
                zzp take = this.zzg.take();
                take.zzb("cache-queue-take");
                zzc zza = this.zzi.zza(take.getUrl());
                if (zza == null) {
                    take.zzb("cache-miss");
                    blockingQueue = this.zzh;
                } else {
                    if (zza.zzd < System.currentTimeMillis()) {
                        take.zzb("cache-hit-expired");
                        take.zza(zza);
                        blockingQueue = this.zzh;
                    } else {
                        take.zzb("cache-hit");
                        zzt zza2 = take.zza(new zzn(zza.data, zza.zzf));
                        take.zzb("cache-hit-parsed");
                        if (!(zza.zze < System.currentTimeMillis())) {
                            this.zzj.zza((zzp<?>) take, (zzt<?>) zza2);
                        } else {
                            take.zzb("cache-hit-refresh-needed");
                            take.zza(zza);
                            zza2.zzag = true;
                            this.zzj.zza(take, zza2, new zze(this, take));
                        }
                    }
                }
                blockingQueue.put(take);
            } catch (InterruptedException unused) {
                if (this.zzk) {
                    return;
                }
            }
        }
    }
}
