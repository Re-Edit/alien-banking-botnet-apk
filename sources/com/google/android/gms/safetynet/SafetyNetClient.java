package com.google.android.gms.safetynet;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.internal.zzbk;
import com.google.android.gms.internal.zzbce;
import com.google.android.gms.internal.zzbfy;
import com.google.android.gms.internal.zzctm;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.Task;

public class SafetyNetClient extends GoogleApi<Api.ApiOptions.NoOptions> {
    SafetyNetClient(@NonNull Activity activity) {
        super(activity, SafetyNet.API, null, (zzbfy) new zzbce());
    }

    SafetyNetClient(@NonNull Context context) {
        super(context, SafetyNet.API, null, (zzbfy) new zzbce());
    }

    public Task<SafetyNetApi.AttestationResponse> attest(byte[] bArr, String str) {
        return zzbk.zza(zzctm.zza(zzpg(), bArr, str), new SafetyNetApi.AttestationResponse());
    }

    public Task<SafetyNetApi.VerifyAppsUserResponse> enableVerifyApps() {
        return zzbk.zza(SafetyNet.SafetyNetApi.enableVerifyApps(zzpg()), new SafetyNetApi.VerifyAppsUserResponse());
    }

    public Task<Void> initSafeBrowsing() {
        return zza(new zzj(this));
    }

    public Task<SafetyNetApi.VerifyAppsUserResponse> isVerifyAppsEnabled() {
        return zzbk.zza(SafetyNet.SafetyNetApi.isVerifyAppsEnabled(zzpg()), new SafetyNetApi.VerifyAppsUserResponse());
    }

    public Task<SafetyNetApi.HarmfulAppsResponse> listHarmfulApps() {
        return zzbk.zza(SafetyNet.SafetyNetApi.listHarmfulApps(zzpg()), new SafetyNetApi.HarmfulAppsResponse());
    }

    public Task<SafetyNetApi.SafeBrowsingResponse> lookupUri(String str, String str2, int... iArr) {
        return zzbk.zza(zzctm.zza(zzpg(), str, 3, str2, iArr), new SafetyNetApi.SafeBrowsingResponse());
    }

    public Task<Void> shutdownSafeBrowsing() {
        return zza(new zzl(this));
    }

    public Task<SafetyNetApi.RecaptchaTokenResponse> verifyWithRecaptcha(String str) {
        return zzbk.zza(SafetyNet.SafetyNetApi.verifyWithRecaptcha(zzpg(), str), new SafetyNetApi.RecaptchaTokenResponse());
    }
}
