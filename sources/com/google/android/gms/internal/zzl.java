package com.google.android.gms.internal;

import android.net.TrafficStats;
import android.os.Process;
import android.os.SystemClock;
import java.util.concurrent.BlockingQueue;

public final class zzl extends Thread {
    private final zzb zzi;
    private final zzw zzj;
    private volatile boolean zzk = false;
    private final BlockingQueue<zzp<?>> zzw;
    private final zzk zzx;

    public zzl(BlockingQueue<zzp<?>> blockingQueue, zzk zzk2, zzb zzb, zzw zzw2) {
        this.zzw = blockingQueue;
        this.zzx = zzk2;
        this.zzi = zzb;
        this.zzj = zzw2;
    }

    public final void quit() {
        this.zzk = true;
        interrupt();
    }

    public final void run() {
        Process.setThreadPriority(10);
        while (true) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            try {
                zzp take = this.zzw.take();
                try {
                    take.zzb("network-queue-take");
                    TrafficStats.setThreadStatsTag(take.zzc());
                    zzn zza = this.zzx.zza(take);
                    take.zzb("network-http-complete");
                    if (!zza.zzz || !take.zzl()) {
                        zzt zza2 = take.zza(zza);
                        take.zzb("network-parse-complete");
                        if (take.zzh() && zza2.zzae != null) {
                            this.zzi.zza(take.getUrl(), zza2.zzae);
                            take.zzb("network-cache-written");
                        }
                        take.zzk();
                        this.zzj.zza((zzp<?>) take, (zzt<?>) zza2);
                    } else {
                        take.zzc("not-modified");
                    }
                } catch (zzaa e) {
                    e.zza(SystemClock.elapsedRealtime() - elapsedRealtime);
                    this.zzj.zza((zzp<?>) take, e);
                } catch (Exception e2) {
                    zzab.zza(e2, "Unhandled exception %s", e2.toString());
                    zzaa zzaa = new zzaa((Throwable) e2);
                    zzaa.zza(SystemClock.elapsedRealtime() - elapsedRealtime);
                    this.zzj.zza((zzp<?>) take, zzaa);
                }
            } catch (InterruptedException unused) {
                if (this.zzk) {
                    return;
                }
            }
        }
    }
}
