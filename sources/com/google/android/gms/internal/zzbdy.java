package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzbdy implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private /* synthetic */ zzbdp zzaDr;

    private zzbdy(zzbdp zzbdp) {
        this.zzaDr = zzbdp;
    }

    /* synthetic */ zzbdy(zzbdp zzbdp, zzbdq zzbdq) {
        this(zzbdp);
    }

    public final void onConnected(Bundle bundle) {
        this.zzaDr.zzaDj.zza(new zzbdw(this.zzaDr));
    }

    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zzaDr.zzaCx.lock();
        try {
            if (this.zzaDr.zzd(connectionResult)) {
                this.zzaDr.zzpX();
                this.zzaDr.zzpV();
            } else {
                this.zzaDr.zze(connectionResult);
            }
        } finally {
            this.zzaDr.zzaCx.unlock();
        }
    }

    public final void onConnectionSuspended(int i) {
    }
}
