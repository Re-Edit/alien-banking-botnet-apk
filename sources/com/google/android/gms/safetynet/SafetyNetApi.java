package com.google.android.gms.safetynet;

import android.content.Context;
import com.google.android.gms.common.annotation.KeepForSdkWithMembers;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.common.api.Result;
import java.util.List;

@KeepForSdkWithMembers
public interface SafetyNetApi {

    public static class AttestationResponse extends Response<AttestationResult> {
        public String getJwsResult() {
            return ((AttestationResult) getResult()).getJwsResult();
        }
    }

    @Deprecated
    public interface AttestationResult extends Result {
        String getJwsResult();
    }

    public static class HarmfulAppsResponse extends Response<HarmfulAppsResult> {
        public List<HarmfulAppsData> getHarmfulAppsList() {
            return ((HarmfulAppsResult) getResult()).getHarmfulAppsList();
        }

        public long getLastScanTimeMs() {
            return ((HarmfulAppsResult) getResult()).getLastScanTimeMs();
        }
    }

    @Deprecated
    public interface HarmfulAppsResult extends Result {
        List<HarmfulAppsData> getHarmfulAppsList();

        long getLastScanTimeMs();
    }

    public static class RecaptchaTokenResponse extends Response<RecaptchaTokenResult> {
        public String getTokenResult() {
            return ((RecaptchaTokenResult) getResult()).getTokenResult();
        }
    }

    @Deprecated
    public interface RecaptchaTokenResult extends Result {
        String getTokenResult();
    }

    @KeepForSdkWithMembers
    public static class SafeBrowsingResponse extends Response<SafeBrowsingResult> {
        public List<SafeBrowsingThreat> getDetectedThreats() {
            return ((SafeBrowsingResult) getResult()).getDetectedThreats();
        }

        public String getMetadata() {
            return ((SafeBrowsingResult) getResult()).getMetadata();
        }
    }

    @KeepForSdkWithMembers
    @Deprecated
    public interface SafeBrowsingResult extends Result {
        List<SafeBrowsingThreat> getDetectedThreats();

        String getMetadata();
    }

    public static class VerifyAppsUserResponse extends Response<VerifyAppsUserResult> {
        public boolean isVerifyAppsEnabled() {
            return ((VerifyAppsUserResult) getResult()).isVerifyAppsEnabled();
        }
    }

    @Deprecated
    public interface VerifyAppsUserResult extends Result {
        boolean isVerifyAppsEnabled();
    }

    @Deprecated
    PendingResult<AttestationResult> attest(GoogleApiClient googleApiClient, byte[] bArr);

    @Deprecated
    PendingResult<VerifyAppsUserResult> enableVerifyApps(GoogleApiClient googleApiClient);

    @Deprecated
    PendingResult<VerifyAppsUserResult> isVerifyAppsEnabled(GoogleApiClient googleApiClient);

    @Deprecated
    boolean isVerifyAppsEnabled(Context context);

    @Deprecated
    PendingResult<HarmfulAppsResult> listHarmfulApps(GoogleApiClient googleApiClient);

    @Deprecated
    PendingResult<SafeBrowsingResult> lookupUri(GoogleApiClient googleApiClient, String str, String str2, int... iArr);

    @Deprecated
    PendingResult<SafeBrowsingResult> lookupUri(GoogleApiClient googleApiClient, String str, int... iArr);

    PendingResult<SafeBrowsingResult> lookupUri(GoogleApiClient googleApiClient, List<Integer> list, String str);

    boolean lookupUriInLocalBlacklist(String str, int... iArr);

    @Deprecated
    PendingResult<RecaptchaTokenResult> verifyWithRecaptcha(GoogleApiClient googleApiClient, String str);
}
