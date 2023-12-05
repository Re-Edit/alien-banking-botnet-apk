package com.google.android.gms.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzbr;

public final class zzbfi<L> {
    private volatile L mListener;
    private final zzbfj zzaEO;
    private final zzbfk<L> zzaEP;

    zzbfi(@NonNull Looper looper, @NonNull L l, @NonNull String str) {
        this.zzaEO = new zzbfj(this, looper);
        this.mListener = zzbr.zzb(l, (Object) "Listener must not be null");
        this.zzaEP = new zzbfk<>(l, zzbr.zzcF(str));
    }

    public final void clear() {
        this.mListener = null;
    }

    public final void zza(zzbfl<? super L> zzbfl) {
        zzbr.zzb(zzbfl, (Object) "Notifier must not be null");
        this.zzaEO.sendMessage(this.zzaEO.obtainMessage(1, zzbfl));
    }

    /* access modifiers changed from: package-private */
    public final void zzb(zzbfl<? super L> zzbfl) {
        L l = this.mListener;
        if (l == null) {
            zzbfl.zzpR();
            return;
        }
        try {
            zzbfl.zzq(l);
        } catch (RuntimeException e) {
            zzbfl.zzpR();
            throw e;
        }
    }

    public final boolean zzoa() {
        return this.mListener != null;
    }

    @NonNull
    public final zzbfk<L> zzqE() {
        return this.zzaEP;
    }
}
