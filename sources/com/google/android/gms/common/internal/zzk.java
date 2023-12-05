package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.BinderThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public final class zzk extends zzaw {
    private zzd zzaHi;
    private final int zzaHj;

    public zzk(@NonNull zzd zzd, int i) {
        this.zzaHi = zzd;
        this.zzaHj = i;
    }

    @BinderThread
    public final void zza(int i, @Nullable Bundle bundle) {
        Log.wtf("GmsClient", "received deprecated onAccountValidationComplete callback, ignoring", new Exception());
    }

    @BinderThread
    public final void zza(int i, @NonNull IBinder iBinder, @Nullable Bundle bundle) {
        zzbr.zzb(this.zzaHi, (Object) "onPostInitComplete can be called only once per call to getRemoteService");
        this.zzaHi.zza(i, iBinder, bundle, this.zzaHj);
        this.zzaHi = null;
    }
}
