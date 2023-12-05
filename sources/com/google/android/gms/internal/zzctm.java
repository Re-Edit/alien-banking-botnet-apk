package com.google.android.gms.internal;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.SparseArray;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.HarmfulAppsData;
import com.google.android.gms.safetynet.SafeBrowsingData;
import com.google.android.gms.safetynet.SafeBrowsingThreat;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class zzctm implements SafetyNetApi {
    private static final String TAG = "zzctm";
    protected static SparseArray<zzcud> zzbBQ;
    protected static long zzbBR;

    static class zza implements SafetyNetApi.AttestationResult {
        private final Status mStatus;
        private final com.google.android.gms.safetynet.zza zzbBZ;

        public zza(Status status, com.google.android.gms.safetynet.zza zza) {
            this.mStatus = status;
            this.zzbBZ = zza;
        }

        public final String getJwsResult() {
            com.google.android.gms.safetynet.zza zza = this.zzbBZ;
            if (zza == null) {
                return null;
            }
            return zza.getJwsResult();
        }

        public final Status getStatus() {
            return this.mStatus;
        }
    }

    static abstract class zzb extends zzcth<SafetyNetApi.AttestationResult> {
        protected zzcti zzbCa = new zzctu(this);

        public zzb(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ Result zzb(Status status) {
            return new zza(status, (com.google.android.gms.safetynet.zza) null);
        }
    }

    static abstract class zzc extends zzcth<SafetyNetApi.VerifyAppsUserResult> {
        protected zzcti zzbCa = new zzctv(this);

        public zzc(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ Result zzb(Status status) {
            return new zzj(status, false);
        }
    }

    static abstract class zzd extends zzcth<SafetyNetApi.HarmfulAppsResult> {
        protected final zzcti zzbCa = new zzctw(this);

        public zzd(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ Result zzb(Status status) {
            return new zzg(status, (com.google.android.gms.safetynet.zzd) null);
        }
    }

    static abstract class zze extends zzcth<SafetyNetApi.RecaptchaTokenResult> {
        protected zzcti zzbCa = new zzctx(this);

        public zze(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ Result zzb(Status status) {
            return new zzh(status, (com.google.android.gms.safetynet.zzf) null);
        }
    }

    static abstract class zzf extends zzcth<SafetyNetApi.SafeBrowsingResult> {
        protected zzcti zzbCa = new zzcty(this);

        public zzf(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ Result zzb(Status status) {
            return new zzi(status, (SafeBrowsingData) null);
        }
    }

    static class zzg implements SafetyNetApi.HarmfulAppsResult {
        private final Status mStatus;
        private final com.google.android.gms.safetynet.zzd zzbCg;

        public zzg(Status status, com.google.android.gms.safetynet.zzd zzd) {
            this.mStatus = status;
            this.zzbCg = zzd;
        }

        public final List<HarmfulAppsData> getHarmfulAppsList() {
            com.google.android.gms.safetynet.zzd zzd = this.zzbCg;
            return zzd == null ? Collections.emptyList() : Arrays.asList(zzd.zzbBL);
        }

        public final long getLastScanTimeMs() {
            com.google.android.gms.safetynet.zzd zzd = this.zzbCg;
            if (zzd == null) {
                return 0;
            }
            return zzd.zzbBK;
        }

        public final Status getStatus() {
            return this.mStatus;
        }
    }

    static class zzh implements SafetyNetApi.RecaptchaTokenResult {
        private final Status mStatus;
        private final com.google.android.gms.safetynet.zzf zzbCh;

        public zzh(Status status, com.google.android.gms.safetynet.zzf zzf) {
            this.mStatus = status;
            this.zzbCh = zzf;
        }

        public final Status getStatus() {
            return this.mStatus;
        }

        public final String getTokenResult() {
            com.google.android.gms.safetynet.zzf zzf = this.zzbCh;
            if (zzf == null) {
                return null;
            }
            return zzf.getTokenResult();
        }
    }

    static class zzi implements SafetyNetApi.SafeBrowsingResult {
        private Status mStatus;
        private String zzbBM = null;
        private final SafeBrowsingData zzbCi;

        public zzi(Status status, SafeBrowsingData safeBrowsingData) {
            this.mStatus = status;
            this.zzbCi = safeBrowsingData;
            SafeBrowsingData safeBrowsingData2 = this.zzbCi;
            if (safeBrowsingData2 != null) {
                this.zzbBM = safeBrowsingData2.getMetadata();
            } else if (this.mStatus.isSuccess()) {
                this.mStatus = new Status(8);
            }
        }

        public final List<SafeBrowsingThreat> getDetectedThreats() {
            ArrayList arrayList = new ArrayList();
            String str = this.zzbBM;
            if (str == null) {
                return arrayList;
            }
            try {
                JSONArray jSONArray = new JSONObject(str).getJSONArray("matches");
                for (int i = 0; i < jSONArray.length(); i++) {
                    try {
                        arrayList.add(new SafeBrowsingThreat(Integer.parseInt(jSONArray.getJSONObject(i).getString("threat_type"))));
                    } catch (NumberFormatException | JSONException unused) {
                    }
                }
            } catch (JSONException unused2) {
            }
            return arrayList;
        }

        public final String getMetadata() {
            return this.zzbBM;
        }

        public final Status getStatus() {
            return this.mStatus;
        }
    }

    static class zzj implements SafetyNetApi.VerifyAppsUserResult {
        private Status mStatus;
        private boolean zzzH;

        public zzj() {
        }

        public zzj(Status status, boolean z) {
            this.mStatus = status;
            this.zzzH = z;
        }

        public final Status getStatus() {
            return this.mStatus;
        }

        public final boolean isVerifyAppsEnabled() {
            Status status = this.mStatus;
            if (status == null || !status.isSuccess()) {
                return false;
            }
            return this.zzzH;
        }
    }

    public static PendingResult<SafetyNetApi.SafeBrowsingResult> zza(GoogleApiClient googleApiClient, String str, int i, String str2, int... iArr) {
        if (iArr.length == 0) {
            throw new IllegalArgumentException("Null threatTypes in lookupUri");
        } else if (!TextUtils.isEmpty(str)) {
            return googleApiClient.zzd(new zzctp(googleApiClient, iArr, i, str, str2));
        } else {
            throw new IllegalArgumentException("Null or empty uri in lookupUri");
        }
    }

    public static PendingResult<SafetyNetApi.AttestationResult> zza(GoogleApiClient googleApiClient, byte[] bArr, String str) {
        return googleApiClient.zzd(new zzctn(googleApiClient, bArr, str));
    }

    public PendingResult<SafetyNetApi.AttestationResult> attest(GoogleApiClient googleApiClient, byte[] bArr) {
        return zza(googleApiClient, bArr, (String) null);
    }

    public PendingResult<SafetyNetApi.VerifyAppsUserResult> enableVerifyApps(GoogleApiClient googleApiClient) {
        return googleApiClient.zzd(new zzctr(this, googleApiClient));
    }

    public PendingResult<SafetyNetApi.VerifyAppsUserResult> isVerifyAppsEnabled(GoogleApiClient googleApiClient) {
        return googleApiClient.zzd(new zzctq(this, googleApiClient));
    }

    public boolean isVerifyAppsEnabled(Context context) {
        GoogleApiClient build = new GoogleApiClient.Builder(context).addApi(SafetyNet.API).build();
        try {
            boolean z = false;
            if (build.blockingConnect(3, TimeUnit.SECONDS).isSuccess()) {
                SafetyNetApi.VerifyAppsUserResult await = isVerifyAppsEnabled(build).await(3, TimeUnit.SECONDS);
                if (await != null && await.isVerifyAppsEnabled()) {
                    z = true;
                }
                return z;
            }
            if (build != null) {
                build.disconnect();
            }
            return false;
        } finally {
            if (build != null) {
                build.disconnect();
            }
        }
    }

    public PendingResult<SafetyNetApi.HarmfulAppsResult> listHarmfulApps(GoogleApiClient googleApiClient) {
        return googleApiClient.zzd(new zzcts(this, googleApiClient));
    }

    public PendingResult<SafetyNetApi.SafeBrowsingResult> lookupUri(GoogleApiClient googleApiClient, String str, String str2, int... iArr) {
        return zza(googleApiClient, str, 1, str2, iArr);
    }

    public PendingResult<SafetyNetApi.SafeBrowsingResult> lookupUri(GoogleApiClient googleApiClient, String str, int... iArr) {
        return zza(googleApiClient, str, 1, (String) null, iArr);
    }

    public PendingResult<SafetyNetApi.SafeBrowsingResult> lookupUri(GoogleApiClient googleApiClient, List<Integer> list, String str) {
        return zza(googleApiClient, list, str, (String) null);
    }

    public boolean lookupUriInLocalBlacklist(String str, int... iArr) {
        SparseArray<zzcud> sparseArray;
        List<zzcub> zzAi;
        if (iArr == null) {
            throw new IllegalArgumentException("Null threatTypes in lookupUri");
        } else if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Null or empty uri in lookupUri");
        } else if (zzbBQ == null || zzbBR == 0 || SystemClock.elapsedRealtime() - zzbBR >= 1200000 || (sparseArray = zzbBQ) == null || sparseArray.size() == 0 || (zzAi = new zzcue(str).zzAi()) == null || zzAi.isEmpty()) {
            return true;
        } else {
            Iterator<zzcub> it = zzAi.iterator();
            while (true) {
                int i = 0;
                if (!it.hasNext()) {
                    return false;
                }
                zzcub next = it.next();
                int length = iArr.length;
                while (true) {
                    if (i < length) {
                        zzcud zzcud = zzbBQ.get(iArr[i]);
                        if (zzcud == null || zzcud.zzs(next.zzbt(4).getBytes())) {
                            return true;
                        }
                        i++;
                    }
                }
            }
        }
    }

    public PendingResult<SafetyNetApi.RecaptchaTokenResult> verifyWithRecaptcha(GoogleApiClient googleApiClient, String str) {
        if (!TextUtils.isEmpty(str)) {
            return googleApiClient.zzd(new zzctt(this, googleApiClient, str));
        }
        throw new IllegalArgumentException("Null or empty site key in verifyWithRecaptcha");
    }

    public final PendingResult<SafetyNetApi.SafeBrowsingResult> zza(GoogleApiClient googleApiClient, List<Integer> list, String str, String str2) {
        if (list == null) {
            throw new IllegalArgumentException("Null threatTypes in lookupUri");
        } else if (!TextUtils.isEmpty(str)) {
            return googleApiClient.zzd(new zzcto(this, googleApiClient, list, str, str2));
        } else {
            throw new IllegalArgumentException("Null or empty uri in lookupUri");
        }
    }
}
