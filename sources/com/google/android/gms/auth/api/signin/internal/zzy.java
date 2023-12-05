package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.internal.zzbr;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONException;

public final class zzy {
    private static final Lock zzamF = new ReentrantLock();
    private static zzy zzamG;
    private final Lock zzamH = new ReentrantLock();
    private final SharedPreferences zzamI;

    private zzy(Context context) {
        this.zzamI = context.getSharedPreferences("com.google.android.gms.signin", 0);
    }

    public static zzy zzaj(Context context) {
        zzbr.zzu(context);
        zzamF.lock();
        try {
            if (zzamG == null) {
                zzamG = new zzy(context.getApplicationContext());
            }
            return zzamG;
        } finally {
            zzamF.unlock();
        }
    }

    private final GoogleSignInAccount zzbS(String str) {
        String zzbU;
        if (!TextUtils.isEmpty(str) && (zzbU = zzbU(zzs("googleSignInAccount", str))) != null) {
            try {
                return GoogleSignInAccount.zzbP(zzbU);
            } catch (JSONException unused) {
            }
        }
        return null;
    }

    private final GoogleSignInOptions zzbT(String str) {
        String zzbU;
        if (!TextUtils.isEmpty(str) && (zzbU = zzbU(zzs("googleSignInOptions", str))) != null) {
            try {
                return GoogleSignInOptions.zzbQ(zzbU);
            } catch (JSONException unused) {
            }
        }
        return null;
    }

    private final String zzbU(String str) {
        this.zzamH.lock();
        try {
            return this.zzamI.getString(str, (String) null);
        } finally {
            this.zzamH.unlock();
        }
    }

    private final void zzbV(String str) {
        this.zzamH.lock();
        try {
            this.zzamI.edit().remove(str).apply();
        } finally {
            this.zzamH.unlock();
        }
    }

    private final void zzr(String str, String str2) {
        this.zzamH.lock();
        try {
            this.zzamI.edit().putString(str, str2).apply();
        } finally {
            this.zzamH.unlock();
        }
    }

    private static String zzs(String str, String str2) {
        String valueOf = String.valueOf(":");
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + String.valueOf(valueOf).length() + String.valueOf(str2).length());
        sb.append(str);
        sb.append(valueOf);
        sb.append(str2);
        return sb.toString();
    }

    public final void zza(GoogleSignInAccount googleSignInAccount, GoogleSignInOptions googleSignInOptions) {
        zzbr.zzu(googleSignInAccount);
        zzbr.zzu(googleSignInOptions);
        zzr("defaultGoogleSignInAccount", googleSignInAccount.zzmv());
        zzbr.zzu(googleSignInAccount);
        zzbr.zzu(googleSignInOptions);
        String zzmv = googleSignInAccount.zzmv();
        zzr(zzs("googleSignInAccount", zzmv), googleSignInAccount.zzmw());
        zzr(zzs("googleSignInOptions", zzmv), googleSignInOptions.zzmA());
    }

    public final GoogleSignInAccount zzmL() {
        return zzbS(zzbU("defaultGoogleSignInAccount"));
    }

    public final GoogleSignInOptions zzmM() {
        return zzbT(zzbU("defaultGoogleSignInAccount"));
    }

    public final void zzmN() {
        String zzbU = zzbU("defaultGoogleSignInAccount");
        zzbV("defaultGoogleSignInAccount");
        if (!TextUtils.isEmpty(zzbU)) {
            zzbV(zzs("googleSignInAccount", zzbU));
            zzbV(zzs("googleSignInOptions", zzbU));
        }
    }
}
