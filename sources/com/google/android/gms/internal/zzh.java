package com.google.android.gms.internal;

import android.os.Handler;
import java.util.concurrent.Executor;

public final class zzh implements zzw {
    private final Executor zzr;

    public zzh(Handler handler) {
        this.zzr = new zzi(this, handler);
    }

    public final void zza(zzp<?> zzp, zzaa zzaa) {
        zzp.zzb("post-error");
        this.zzr.execute(new zzj(this, zzp, zzt.zzc(zzaa), (Runnable) null));
    }

    public final void zza(zzp<?> zzp, zzt<?> zzt) {
        zza(zzp, zzt, (Runnable) null);
    }

    public final void zza(zzp<?> zzp, zzt<?> zzt, Runnable runnable) {
        zzp.zzk();
        zzp.zzb("post-response");
        this.zzr.execute(new zzj(this, zzp, zzt, runnable));
    }
}
