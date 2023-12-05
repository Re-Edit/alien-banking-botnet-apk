package com.google.android.gms.safetynet;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.internal.zzctm;
import com.google.android.gms.internal.zzctz;
import com.google.android.gms.internal.zzcua;

public final class SafetyNet {
    @Deprecated
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>("SafetyNet.API", zzajU, zzajT);
    @Deprecated
    public static final SafetyNetApi SafetyNetApi = new zzctm();
    private static Api.zzf<zzctz> zzajT = new Api.zzf<>();
    private static Api.zza<zzctz, Api.ApiOptions.NoOptions> zzajU = new zzi();
    private static zzm zzbBP = new zzcua();

    private SafetyNet() {
    }

    public static SafetyNetClient getClient(Activity activity) {
        return new SafetyNetClient(activity);
    }

    public static SafetyNetClient getClient(Context context) {
        return new SafetyNetClient(context);
    }
}
