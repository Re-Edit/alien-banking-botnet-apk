package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.safetynet.SafetyNet;

abstract class zzcth<R extends Result> extends zzbck<R, zzctz> {
    public zzcth(GoogleApiClient googleApiClient) {
        super((Api<?>) SafetyNet.API, googleApiClient);
    }

    public final /* bridge */ /* synthetic */ void setResult(Object obj) {
        super.setResult((Result) obj);
    }
}
