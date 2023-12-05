package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzab implements zzf {
    private /* synthetic */ GoogleApiClient.ConnectionCallbacks zzaHE;

    zzab(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zzaHE = connectionCallbacks;
    }

    public final void onConnected(@Nullable Bundle bundle) {
        this.zzaHE.onConnected(bundle);
    }

    public final void onConnectionSuspended(int i) {
        this.zzaHE.onConnectionSuspended(i);
    }
}
