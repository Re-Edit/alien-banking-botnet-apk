package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.internal.zzbh;
import java.util.Arrays;

public final class zzbcf<O extends Api.ApiOptions> {
    private final O zzaAL;
    private final boolean zzaBB = true;
    private final int zzaBC;
    private final Api<O> zzayY;

    private zzbcf(Api<O> api) {
        this.zzayY = api;
        this.zzaAL = null;
        this.zzaBC = System.identityHashCode(this);
    }

    private zzbcf(Api<O> api, O o) {
        this.zzayY = api;
        this.zzaAL = o;
        this.zzaBC = Arrays.hashCode(new Object[]{this.zzayY, this.zzaAL});
    }

    public static <O extends Api.ApiOptions> zzbcf<O> zza(Api<O> api, O o) {
        return new zzbcf<>(api, o);
    }

    public static <O extends Api.ApiOptions> zzbcf<O> zzb(Api<O> api) {
        return new zzbcf<>(api);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbcf)) {
            return false;
        }
        zzbcf zzbcf = (zzbcf) obj;
        return !this.zzaBB && !zzbcf.zzaBB && zzbh.equal(this.zzayY, zzbcf.zzayY) && zzbh.equal(this.zzaAL, zzbcf.zzaAL);
    }

    public final int hashCode() {
        return this.zzaBC;
    }

    public final String zzpp() {
        return this.zzayY.getName();
    }
}
