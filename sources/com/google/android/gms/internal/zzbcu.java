package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzbr;

public final class zzbcu implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private final boolean zzaCl;
    private zzbcv zzaCm;
    public final Api<?> zzayY;

    public zzbcu(Api<?> api, boolean z) {
        this.zzayY = api;
        this.zzaCl = z;
    }

    private final void zzpB() {
        zzbr.zzb(this.zzaCm, (Object) "Callbacks must be attached to a ClientConnectionHelper instance before connecting the client.");
    }

    public final void onConnected(@Nullable Bundle bundle) {
        zzpB();
        this.zzaCm.onConnected(bundle);
    }

    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        zzpB();
        this.zzaCm.zza(connectionResult, this.zzayY, this.zzaCl);
    }

    public final void onConnectionSuspended(int i) {
        zzpB();
        this.zzaCm.onConnectionSuspended(i);
    }

    public final void zza(zzbcv zzbcv) {
        this.zzaCm = zzbcv;
    }
}
