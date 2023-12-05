package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.auth.api.signin.internal.zzy;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

final class zzbef implements ResultCallback<Status> {
    private /* synthetic */ zzbeb zzaDP;
    private /* synthetic */ zzbfz zzaDR;
    private /* synthetic */ boolean zzaDS;
    private /* synthetic */ GoogleApiClient zzaqY;

    zzbef(zzbeb zzbeb, zzbfz zzbfz, boolean z, GoogleApiClient googleApiClient) {
        this.zzaDP = zzbeb;
        this.zzaDR = zzbfz;
        this.zzaDS = z;
        this.zzaqY = googleApiClient;
    }

    public final /* synthetic */ void onResult(@NonNull Result result) {
        Status status = (Status) result;
        zzy.zzaj(this.zzaDP.mContext).zzmN();
        if (status.isSuccess() && this.zzaDP.isConnected()) {
            this.zzaDP.reconnect();
        }
        this.zzaDR.setResult(status);
        if (this.zzaDS) {
            this.zzaqY.disconnect();
        }
    }
}
