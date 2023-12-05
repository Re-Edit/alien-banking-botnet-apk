package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import java.util.Collections;

public final class zzbea implements zzbei {
    private final zzbej zzaDb;

    public zzbea(zzbej zzbej) {
        this.zzaDb = zzbej;
    }

    public final void begin() {
        for (Api.zze disconnect : this.zzaDb.zzaDH.values()) {
            disconnect.disconnect();
        }
        this.zzaDb.zzaCn.zzaDI = Collections.emptySet();
    }

    public final void connect() {
        this.zzaDb.zzqf();
    }

    public final boolean disconnect() {
        return true;
    }

    public final void onConnected(Bundle bundle) {
    }

    public final void onConnectionSuspended(int i) {
    }

    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean z) {
    }

    public final <A extends Api.zzb, R extends Result, T extends zzbck<R, A>> T zzd(T t) {
        this.zzaDb.zzaCn.zzaCL.add(t);
        return t;
    }

    public final <A extends Api.zzb, T extends zzbck<? extends Result, A>> T zze(T t) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }
}
