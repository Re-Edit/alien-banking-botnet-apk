package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.util.zzf;
import com.google.android.gms.common.util.zzj;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleSignInAccount extends zza implements ReflectedParcelable {
    public static final Parcelable.Creator<GoogleSignInAccount> CREATOR = new zzb();
    private static zzf zzalP = zzj.zzrX();
    private static Comparator<Scope> zzalW = new zza();
    private int versionCode;
    private String zzIl;
    private List<Scope> zzakB;
    private String zzakZ;
    private String zzalQ;
    private String zzalR;
    private Uri zzalS;
    private String zzalT;
    private long zzalU;
    private String zzalV;
    private String zzala;
    private String zzalp;

    GoogleSignInAccount(int i, String str, String str2, String str3, String str4, Uri uri, String str5, long j, String str6, List<Scope> list, String str7, String str8) {
        this.versionCode = i;
        this.zzIl = str;
        this.zzalp = str2;
        this.zzalQ = str3;
        this.zzalR = str4;
        this.zzalS = uri;
        this.zzalT = str5;
        this.zzalU = j;
        this.zzalV = str6;
        this.zzakB = list;
        this.zzakZ = str7;
        this.zzala = str8;
    }

    @Nullable
    public static GoogleSignInAccount zzbP(@Nullable String str) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(str);
        String optString = jSONObject.optString("photoUrl", (String) null);
        Uri parse = !TextUtils.isEmpty(optString) ? Uri.parse(optString) : null;
        long parseLong = Long.parseLong(jSONObject.getString("expirationTime"));
        HashSet hashSet = new HashSet();
        JSONArray jSONArray = jSONObject.getJSONArray("grantedScopes");
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            hashSet.add(new Scope(jSONArray.getString(i)));
        }
        String optString2 = jSONObject.optString("id");
        String optString3 = jSONObject.optString("tokenId", (String) null);
        String optString4 = jSONObject.optString("email", (String) null);
        String optString5 = jSONObject.optString("displayName", (String) null);
        String optString6 = jSONObject.optString("givenName", (String) null);
        String optString7 = jSONObject.optString("familyName", (String) null);
        Long valueOf = Long.valueOf(parseLong);
        String string = jSONObject.getString("obfuscatedIdentifier");
        if (valueOf == null) {
            valueOf = Long.valueOf(zzalP.currentTimeMillis() / 1000);
        }
        GoogleSignInAccount googleSignInAccount = r3;
        GoogleSignInAccount googleSignInAccount2 = new GoogleSignInAccount(3, optString2, optString3, optString4, optString5, parse, (String) null, valueOf.longValue(), zzbr.zzcF(string), new ArrayList((Collection) zzbr.zzu(hashSet)), optString6, optString7);
        googleSignInAccount.zzalT = jSONObject.optString("serverAuthCode", (String) null);
        return googleSignInAccount;
    }

    private final JSONObject zzmx() {
        JSONObject jSONObject = new JSONObject();
        try {
            if (getId() != null) {
                jSONObject.put("id", getId());
            }
            if (getIdToken() != null) {
                jSONObject.put("tokenId", getIdToken());
            }
            if (getEmail() != null) {
                jSONObject.put("email", getEmail());
            }
            if (getDisplayName() != null) {
                jSONObject.put("displayName", getDisplayName());
            }
            if (getGivenName() != null) {
                jSONObject.put("givenName", getGivenName());
            }
            if (getFamilyName() != null) {
                jSONObject.put("familyName", getFamilyName());
            }
            if (getPhotoUrl() != null) {
                jSONObject.put("photoUrl", getPhotoUrl().toString());
            }
            if (getServerAuthCode() != null) {
                jSONObject.put("serverAuthCode", getServerAuthCode());
            }
            jSONObject.put("expirationTime", this.zzalU);
            jSONObject.put("obfuscatedIdentifier", this.zzalV);
            JSONArray jSONArray = new JSONArray();
            Collections.sort(this.zzakB, zzalW);
            for (Scope zzpn : this.zzakB) {
                jSONArray.put(zzpn.zzpn());
            }
            jSONObject.put("grantedScopes", jSONArray);
            return jSONObject;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GoogleSignInAccount)) {
            return false;
        }
        return ((GoogleSignInAccount) obj).zzmx().toString().equals(zzmx().toString());
    }

    @Nullable
    public Account getAccount() {
        String str = this.zzalQ;
        if (str == null) {
            return null;
        }
        return new Account(str, "com.google");
    }

    @Nullable
    public String getDisplayName() {
        return this.zzalR;
    }

    @Nullable
    public String getEmail() {
        return this.zzalQ;
    }

    @Nullable
    public String getFamilyName() {
        return this.zzala;
    }

    @Nullable
    public String getGivenName() {
        return this.zzakZ;
    }

    @NonNull
    public Set<Scope> getGrantedScopes() {
        return new HashSet(this.zzakB);
    }

    @Nullable
    public String getId() {
        return this.zzIl;
    }

    @Nullable
    public String getIdToken() {
        return this.zzalp;
    }

    @Nullable
    public Uri getPhotoUrl() {
        return this.zzalS;
    }

    @Nullable
    public String getServerAuthCode() {
        return this.zzalT;
    }

    public int hashCode() {
        return zzmx().toString().hashCode();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.versionCode);
        zzd.zza(parcel, 2, getId(), false);
        zzd.zza(parcel, 3, getIdToken(), false);
        zzd.zza(parcel, 4, getEmail(), false);
        zzd.zza(parcel, 5, getDisplayName(), false);
        zzd.zza(parcel, 6, (Parcelable) getPhotoUrl(), i, false);
        zzd.zza(parcel, 7, getServerAuthCode(), false);
        zzd.zza(parcel, 8, this.zzalU);
        zzd.zza(parcel, 9, this.zzalV, false);
        zzd.zzc(parcel, 10, this.zzakB, false);
        zzd.zza(parcel, 11, getGivenName(), false);
        zzd.zza(parcel, 12, getFamilyName(), false);
        zzd.zzI(parcel, zze);
    }

    public final boolean zzmu() {
        return zzalP.currentTimeMillis() / 1000 >= this.zzalU - 300;
    }

    @NonNull
    public final String zzmv() {
        return this.zzalV;
    }

    public final String zzmw() {
        JSONObject zzmx = zzmx();
        zzmx.remove("serverAuthCode");
        return zzmx.toString();
    }
}
