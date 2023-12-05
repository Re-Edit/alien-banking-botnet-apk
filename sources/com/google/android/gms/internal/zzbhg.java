package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;

abstract class zzbhg<R extends Result> extends zzbck<R, zzbhi> {
    public zzbhg(GoogleApiClient googleApiClient) {
        super((Api<?>) zzbha.API, googleApiClient);
    }
}
