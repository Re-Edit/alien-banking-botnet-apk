package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.concurrent.atomic.AtomicReference;

final class zzbed implements GoogleApiClient.ConnectionCallbacks {
    private /* synthetic */ zzbeb zzaDP;
    private /* synthetic */ AtomicReference zzaDQ;
    private /* synthetic */ zzbfz zzaDR;

    zzbed(zzbeb zzbeb, AtomicReference atomicReference, zzbfz zzbfz) {
        this.zzaDP = zzbeb;
        this.zzaDQ = atomicReference;
        this.zzaDR = zzbfz;
    }

    public final void onConnected(Bundle bundle) {
        this.zzaDP.zza((GoogleApiClient) this.zzaDQ.get(), this.zzaDR, true);
    }

    public final void onConnectionSuspended(int i) {
    }
}
