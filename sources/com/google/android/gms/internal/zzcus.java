package com.google.android.gms.internal;

import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;

public final class zzcus {
    public static final Api<zzcux> API = new Api<>("SignIn.API", zzajU, zzajT);
    private static Api<zzcuv> zzaMg = new Api<>("SignIn.INTERNAL_API", zzbCO, zzbCN);
    private static Api.zzf<zzcvg> zzajT = new Api.zzf<>();
    public static final Api.zza<zzcvg, zzcux> zzajU = new zzcut();
    private static Scope zzalX = new Scope(Scopes.PROFILE);
    private static Scope zzalY = new Scope("email");
    private static Api.zzf<zzcvg> zzbCN = new Api.zzf<>();
    private static Api.zza<zzcvg, zzcuv> zzbCO = new zzcuu();
}
