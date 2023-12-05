package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import com.google.android.gms.common.api.Api;

public final class zzcux implements Api.ApiOptions.Optional {
    public static final zzcux zzbCQ = new zzcux(false, false, (String) null, false, (String) null, false, (Long) null, (Long) null);
    private final boolean zzalj = false;
    private final String zzalk = null;
    private final boolean zzamc = false;
    private final String zzamd = null;
    private final boolean zzbCR = false;
    private final boolean zzbCS = false;
    private final Long zzbCT = null;
    private final Long zzbCU = null;

    static {
        new zzcuy();
    }

    private zzcux(boolean z, boolean z2, String str, boolean z3, String str2, boolean z4, Long l, Long l2) {
    }

    public final String getServerClientId() {
        return this.zzalk;
    }

    public final boolean isIdTokenRequested() {
        return this.zzalj;
    }

    public final boolean zzAp() {
        return this.zzbCR;
    }

    public final boolean zzAq() {
        return this.zzamc;
    }

    @Nullable
    public final String zzAr() {
        return this.zzamd;
    }

    public final boolean zzAs() {
        return this.zzbCS;
    }

    @Nullable
    public final Long zzAt() {
        return this.zzbCT;
    }

    @Nullable
    public final Long zzAu() {
        return this.zzbCU;
    }
}
