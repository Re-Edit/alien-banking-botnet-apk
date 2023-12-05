package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzq;

final class zzcut extends Api.zza<zzcvg, zzcux> {
    zzcut() {
    }

    public final /* synthetic */ Api.zze zza(Context context, Looper looper, zzq zzq, Object obj, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        zzcux zzcux = (zzcux) obj;
        if (zzcux == null) {
            zzcux = zzcux.zzbCQ;
        }
        return new zzcvg(context, looper, true, zzq, zzcux, connectionCallbacks, onConnectionFailedListener);
    }
}
