package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.internal.zzn;
import com.google.android.gms.auth.api.signin.internal.zzo;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbr;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleSignInOptions extends zza implements Api.ApiOptions.Optional, ReflectedParcelable {
    public static final Parcelable.Creator<GoogleSignInOptions> CREATOR = new zzd();
    public static final GoogleSignInOptions DEFAULT_GAMES_SIGN_IN = new Builder().requestScopes(SCOPE_GAMES, new Scope[0]).build();
    public static final GoogleSignInOptions DEFAULT_SIGN_IN = new Builder().requestId().requestProfile().build();
    private static Scope SCOPE_GAMES = new Scope(Scopes.GAMES);
    private static Comparator<Scope> zzalW = new zzc();
    public static final Scope zzalX = new Scope(Scopes.PROFILE);
    public static final Scope zzalY = new Scope("email");
    public static final Scope zzalZ = new Scope("openid");
    private int versionCode;
    /* access modifiers changed from: private */
    public Account zzajd;
    /* access modifiers changed from: private */
    public boolean zzalj;
    /* access modifiers changed from: private */
    public String zzalk;
    /* access modifiers changed from: private */
    public final ArrayList<Scope> zzama;
    /* access modifiers changed from: private */
    public final boolean zzamb;
    /* access modifiers changed from: private */
    public final boolean zzamc;
    /* access modifiers changed from: private */
    public String zzamd;
    /* access modifiers changed from: private */
    public ArrayList<zzn> zzame;
    private Map<Integer, zzn> zzamf;

    public static final class Builder {
        private Account zzajd;
        private boolean zzalj;
        private String zzalk;
        private boolean zzamb;
        private boolean zzamc;
        private String zzamd;
        private Set<Scope> zzamg = new HashSet();
        private Map<Integer, zzn> zzamh = new HashMap();

        public Builder() {
        }

        public Builder(@NonNull GoogleSignInOptions googleSignInOptions) {
            zzbr.zzu(googleSignInOptions);
            this.zzamg = new HashSet(googleSignInOptions.zzama);
            this.zzamb = googleSignInOptions.zzamb;
            this.zzamc = googleSignInOptions.zzamc;
            this.zzalj = googleSignInOptions.zzalj;
            this.zzalk = googleSignInOptions.zzalk;
            this.zzajd = googleSignInOptions.zzajd;
            this.zzamd = googleSignInOptions.zzamd;
            this.zzamh = GoogleSignInOptions.zzw(googleSignInOptions.zzame);
        }

        private final String zzbR(String str) {
            zzbr.zzcF(str);
            String str2 = this.zzalk;
            zzbr.zzb(str2 == null || str2.equals(str), (Object) "two different server client ids provided");
            return str;
        }

        public final Builder addExtension(GoogleSignInOptionsExtension googleSignInOptionsExtension) {
            if (!this.zzamh.containsKey(1)) {
                this.zzamh.put(1, new zzn(googleSignInOptionsExtension));
                return this;
            }
            throw new IllegalStateException("Only one extension per type may be added");
        }

        public final GoogleSignInOptions build() {
            if (this.zzalj && (this.zzajd == null || !this.zzamg.isEmpty())) {
                requestId();
            }
            return new GoogleSignInOptions(3, new ArrayList(this.zzamg), this.zzajd, this.zzalj, this.zzamb, this.zzamc, this.zzalk, this.zzamd, this.zzamh, (zzc) null);
        }

        public final Builder requestEmail() {
            this.zzamg.add(GoogleSignInOptions.zzalY);
            return this;
        }

        public final Builder requestId() {
            this.zzamg.add(GoogleSignInOptions.zzalZ);
            return this;
        }

        public final Builder requestIdToken(String str) {
            this.zzalj = true;
            this.zzalk = zzbR(str);
            return this;
        }

        public final Builder requestProfile() {
            this.zzamg.add(GoogleSignInOptions.zzalX);
            return this;
        }

        public final Builder requestScopes(Scope scope, Scope... scopeArr) {
            this.zzamg.add(scope);
            this.zzamg.addAll(Arrays.asList(scopeArr));
            return this;
        }

        public final Builder requestServerAuthCode(String str) {
            return requestServerAuthCode(str, false);
        }

        public final Builder requestServerAuthCode(String str, boolean z) {
            this.zzamb = true;
            this.zzalk = zzbR(str);
            this.zzamc = z;
            return this;
        }

        public final Builder setAccountName(String str) {
            this.zzajd = new Account(zzbr.zzcF(str), "com.google");
            return this;
        }

        public final Builder setHostedDomain(String str) {
            this.zzamd = zzbr.zzcF(str);
            return this;
        }
    }

    GoogleSignInOptions(int i, ArrayList<Scope> arrayList, Account account, boolean z, boolean z2, boolean z3, String str, String str2, ArrayList<zzn> arrayList2) {
        this(i, arrayList, account, z, z2, z3, str, str2, zzw(arrayList2));
    }

    private GoogleSignInOptions(int i, ArrayList<Scope> arrayList, Account account, boolean z, boolean z2, boolean z3, String str, String str2, Map<Integer, zzn> map) {
        this.versionCode = i;
        this.zzama = arrayList;
        this.zzajd = account;
        this.zzalj = z;
        this.zzamb = z2;
        this.zzamc = z3;
        this.zzalk = str;
        this.zzamd = str2;
        this.zzame = new ArrayList<>(map.values());
        this.zzamf = map;
    }

    /* synthetic */ GoogleSignInOptions(int i, ArrayList arrayList, Account account, boolean z, boolean z2, boolean z3, String str, String str2, Map map, zzc zzc) {
        this(3, (ArrayList<Scope>) arrayList, account, z, z2, z3, str, str2, (Map<Integer, zzn>) map);
    }

    @Nullable
    public static GoogleSignInOptions zzbQ(@Nullable String str) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(str);
        HashSet hashSet = new HashSet();
        JSONArray jSONArray = jSONObject.getJSONArray("scopes");
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            hashSet.add(new Scope(jSONArray.getString(i)));
        }
        String optString = jSONObject.optString("accountName", (String) null);
        return new GoogleSignInOptions(3, (ArrayList<Scope>) new ArrayList(hashSet), !TextUtils.isEmpty(optString) ? new Account(optString, "com.google") : null, jSONObject.getBoolean("idTokenRequested"), jSONObject.getBoolean("serverAuthRequested"), jSONObject.getBoolean("forceCodeForRefreshToken"), jSONObject.optString("serverClientId", (String) null), jSONObject.optString("hostedDomain", (String) null), (Map<Integer, zzn>) new HashMap());
    }

    private final JSONObject zzmx() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray();
            Collections.sort(this.zzama, zzalW);
            ArrayList arrayList = this.zzama;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                jSONArray.put(((Scope) obj).zzpn());
            }
            jSONObject.put("scopes", jSONArray);
            if (this.zzajd != null) {
                jSONObject.put("accountName", this.zzajd.name);
            }
            jSONObject.put("idTokenRequested", this.zzalj);
            jSONObject.put("forceCodeForRefreshToken", this.zzamc);
            jSONObject.put("serverAuthRequested", this.zzamb);
            if (!TextUtils.isEmpty(this.zzalk)) {
                jSONObject.put("serverClientId", this.zzalk);
            }
            if (!TextUtils.isEmpty(this.zzamd)) {
                jSONObject.put("hostedDomain", this.zzamd);
            }
            return jSONObject;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /* access modifiers changed from: private */
    public static Map<Integer, zzn> zzw(@Nullable List<zzn> list) {
        HashMap hashMap = new HashMap();
        if (list == null) {
            return hashMap;
        }
        for (zzn next : list) {
            hashMap.put(Integer.valueOf(next.getType()), next);
        }
        return hashMap;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0045, code lost:
        if (r3.zzajd.equals(r4.zzajd) != false) goto L_0x0047;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0060, code lost:
        if (r3.zzalk.equals(r4.zzalk) != false) goto L_0x0062;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r4) {
        /*
            r3 = this;
            r0 = 0
            if (r4 != 0) goto L_0x0004
            return r0
        L_0x0004:
            com.google.android.gms.auth.api.signin.GoogleSignInOptions r4 = (com.google.android.gms.auth.api.signin.GoogleSignInOptions) r4     // Catch:{ ClassCastException -> 0x0076 }
            java.util.ArrayList<com.google.android.gms.auth.api.signin.internal.zzn> r1 = r3.zzame     // Catch:{ ClassCastException -> 0x0076 }
            int r1 = r1.size()     // Catch:{ ClassCastException -> 0x0076 }
            if (r1 > 0) goto L_0x0076
            java.util.ArrayList<com.google.android.gms.auth.api.signin.internal.zzn> r1 = r4.zzame     // Catch:{ ClassCastException -> 0x0076 }
            int r1 = r1.size()     // Catch:{ ClassCastException -> 0x0076 }
            if (r1 <= 0) goto L_0x0017
            goto L_0x0076
        L_0x0017:
            java.util.ArrayList<com.google.android.gms.common.api.Scope> r1 = r3.zzama     // Catch:{ ClassCastException -> 0x0076 }
            int r1 = r1.size()     // Catch:{ ClassCastException -> 0x0076 }
            java.util.ArrayList r2 = r4.zzmy()     // Catch:{ ClassCastException -> 0x0076 }
            int r2 = r2.size()     // Catch:{ ClassCastException -> 0x0076 }
            if (r1 != r2) goto L_0x0076
            java.util.ArrayList<com.google.android.gms.common.api.Scope> r1 = r3.zzama     // Catch:{ ClassCastException -> 0x0076 }
            java.util.ArrayList r2 = r4.zzmy()     // Catch:{ ClassCastException -> 0x0076 }
            boolean r1 = r1.containsAll(r2)     // Catch:{ ClassCastException -> 0x0076 }
            if (r1 != 0) goto L_0x0034
            goto L_0x0076
        L_0x0034:
            android.accounts.Account r1 = r3.zzajd     // Catch:{ ClassCastException -> 0x0076 }
            if (r1 != 0) goto L_0x003d
            android.accounts.Account r1 = r4.zzajd     // Catch:{ ClassCastException -> 0x0076 }
            if (r1 != 0) goto L_0x0076
            goto L_0x0047
        L_0x003d:
            android.accounts.Account r1 = r3.zzajd     // Catch:{ ClassCastException -> 0x0076 }
            android.accounts.Account r2 = r4.zzajd     // Catch:{ ClassCastException -> 0x0076 }
            boolean r1 = r1.equals(r2)     // Catch:{ ClassCastException -> 0x0076 }
            if (r1 == 0) goto L_0x0076
        L_0x0047:
            java.lang.String r1 = r3.zzalk     // Catch:{ ClassCastException -> 0x0076 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ ClassCastException -> 0x0076 }
            if (r1 == 0) goto L_0x0058
            java.lang.String r1 = r4.zzalk     // Catch:{ ClassCastException -> 0x0076 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ ClassCastException -> 0x0076 }
            if (r1 == 0) goto L_0x0076
            goto L_0x0062
        L_0x0058:
            java.lang.String r1 = r3.zzalk     // Catch:{ ClassCastException -> 0x0076 }
            java.lang.String r2 = r4.zzalk     // Catch:{ ClassCastException -> 0x0076 }
            boolean r1 = r1.equals(r2)     // Catch:{ ClassCastException -> 0x0076 }
            if (r1 == 0) goto L_0x0076
        L_0x0062:
            boolean r1 = r3.zzamc     // Catch:{ ClassCastException -> 0x0076 }
            boolean r2 = r4.zzamc     // Catch:{ ClassCastException -> 0x0076 }
            if (r1 != r2) goto L_0x0076
            boolean r1 = r3.zzalj     // Catch:{ ClassCastException -> 0x0076 }
            boolean r2 = r4.zzalj     // Catch:{ ClassCastException -> 0x0076 }
            if (r1 != r2) goto L_0x0076
            boolean r1 = r3.zzamb     // Catch:{ ClassCastException -> 0x0076 }
            boolean r4 = r4.zzamb     // Catch:{ ClassCastException -> 0x0076 }
            if (r1 != r4) goto L_0x0076
            r4 = 1
            return r4
        L_0x0076:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.api.signin.GoogleSignInOptions.equals(java.lang.Object):boolean");
    }

    public final Account getAccount() {
        return this.zzajd;
    }

    public Scope[] getScopeArray() {
        ArrayList<Scope> arrayList = this.zzama;
        return (Scope[]) arrayList.toArray(new Scope[arrayList.size()]);
    }

    public final String getServerClientId() {
        return this.zzalk;
    }

    public int hashCode() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = this.zzama;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            arrayList.add(((Scope) obj).zzpn());
        }
        Collections.sort(arrayList);
        return new zzo().zzo(arrayList).zzo(this.zzajd).zzo(this.zzalk).zzP(this.zzamc).zzP(this.zzalj).zzP(this.zzamb).zzmH();
    }

    public final boolean isIdTokenRequested() {
        return this.zzalj;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.versionCode);
        zzd.zzc(parcel, 2, zzmy(), false);
        zzd.zza(parcel, 3, (Parcelable) this.zzajd, i, false);
        zzd.zza(parcel, 4, this.zzalj);
        zzd.zza(parcel, 5, this.zzamb);
        zzd.zza(parcel, 6, this.zzamc);
        zzd.zza(parcel, 7, this.zzalk, false);
        zzd.zza(parcel, 8, this.zzamd, false);
        zzd.zzc(parcel, 9, this.zzame, false);
        zzd.zzI(parcel, zze);
    }

    public final String zzmA() {
        return zzmx().toString();
    }

    public final ArrayList<Scope> zzmy() {
        return new ArrayList<>(this.zzama);
    }

    public final boolean zzmz() {
        return this.zzamb;
    }
}
