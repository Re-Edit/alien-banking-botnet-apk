package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

final class zzbee implements GoogleApiClient.OnConnectionFailedListener {
    private /* synthetic */ zzbfz zzaDR;

    zzbee(zzbeb zzbeb, zzbfz zzbfz) {
        this.zzaDR = zzbfz;
    }

    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zzaDR.setResult(new Status(8));
    }
}
