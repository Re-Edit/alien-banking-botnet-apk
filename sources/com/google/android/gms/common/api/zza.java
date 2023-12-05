package com.google.android.gms.common.api;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.internal.zzbcf;
import java.util.ArrayList;

public final class zza extends Exception {
    private final ArrayMap<zzbcf<?>, ConnectionResult> zzaAD;

    public zza(ArrayMap<zzbcf<?>, ConnectionResult> arrayMap) {
        this.zzaAD = arrayMap;
    }

    public final String getMessage() {
        ArrayList arrayList = new ArrayList();
        boolean z = true;
        for (zzbcf next : this.zzaAD.keySet()) {
            ConnectionResult connectionResult = this.zzaAD.get(next);
            if (connectionResult.isSuccess()) {
                z = false;
            }
            String valueOf = String.valueOf(next.zzpp());
            String valueOf2 = String.valueOf(connectionResult);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 2 + String.valueOf(valueOf2).length());
            sb.append(valueOf);
            sb.append(": ");
            sb.append(valueOf2);
            arrayList.add(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(z ? "None of the queried APIs are available. " : "Some of the queried APIs are unavailable. ");
        sb2.append(TextUtils.join("; ", arrayList));
        return sb2.toString();
    }

    public final ConnectionResult zza(GoogleApi<? extends Api.ApiOptions> googleApi) {
        zzbcf<? extends Api.ApiOptions> zzpf = googleApi.zzpf();
        zzbr.zzb(this.zzaAD.get(zzpf) != null, (Object) "The given API was not part of the availability request.");
        return this.zzaAD.get(zzpf);
    }

    public final ArrayMap<zzbcf<?>, ConnectionResult> zzpd() {
        return this.zzaAD;
    }
}
