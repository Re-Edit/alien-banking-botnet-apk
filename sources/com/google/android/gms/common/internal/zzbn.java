package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.Response;
import com.google.android.gms.common.api.Result;

final class zzbn implements zzbp<R, T> {
    private /* synthetic */ Response zzaIp;

    zzbn(Response response) {
        this.zzaIp = response;
    }

    public final /* synthetic */ Object zzd(Result result) {
        this.zzaIp.setResult(result);
        return this.zzaIp;
    }
}
