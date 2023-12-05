package com.google.android.gms.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.internal.zzbim;

public class zzp {
    private static zzp zzaAw;
    private final Context mContext;

    private zzp(Context context) {
        this.mContext = context.getApplicationContext();
    }

    static zzg zza(PackageInfo packageInfo, zzg... zzgArr) {
        if (packageInfo.signatures == null) {
            return null;
        }
        if (packageInfo.signatures.length != 1) {
            Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
            return null;
        }
        zzh zzh = new zzh(packageInfo.signatures[0].toByteArray());
        for (int i = 0; i < zzgArr.length; i++) {
            if (zzgArr[i].equals(zzh)) {
                return zzgArr[i];
            }
        }
        return null;
    }

    private static boolean zza(PackageInfo packageInfo, boolean z) {
        if (!(packageInfo == null || packageInfo.signatures == null)) {
            if (zza(packageInfo, z ? zzj.zzaAm : new zzg[]{zzj.zzaAm[0]}) != null) {
                return true;
            }
        }
        return false;
    }

    public static zzp zzax(Context context) {
        zzbr.zzu(context);
        synchronized (zzp.class) {
            if (zzaAw == null) {
                zzf.zzav(context);
                zzaAw = new zzp(context);
            }
        }
        return zzaAw;
    }

    private static boolean zzb(PackageInfo packageInfo, boolean z) {
        if (packageInfo.signatures.length != 1) {
            Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
            return false;
        }
        zzh zzh = new zzh(packageInfo.signatures[0].toByteArray());
        String str = packageInfo.packageName;
        boolean zzb = z ? zzf.zzb(str, zzh) : zzf.zza(str, zzh);
        if (!zzb) {
            StringBuilder sb = new StringBuilder(27);
            sb.append("Cert not in list. atk=");
            sb.append(z);
            Log.d("GoogleSignatureVerifier", sb.toString());
        }
        return zzb;
    }

    private final boolean zzct(String str) {
        try {
            PackageInfo packageInfo = zzbim.zzaP(this.mContext).getPackageInfo(str, 64);
            if (packageInfo == null) {
                return false;
            }
            if (zzo.zzaw(this.mContext)) {
                return zzb(packageInfo, true);
            }
            boolean zzb = zzb(packageInfo, false);
            if (!zzb && zzb(packageInfo, true)) {
                Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
            }
            return zzb;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    @Deprecated
    public final boolean zza(PackageManager packageManager, int i) {
        String[] packagesForUid = zzbim.zzaP(this.mContext).getPackagesForUid(i);
        if (!(packagesForUid == null || packagesForUid.length == 0)) {
            for (String zzct : packagesForUid) {
                if (zzct(zzct)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Deprecated
    public final boolean zza(PackageManager packageManager, PackageInfo packageInfo) {
        if (packageInfo != null) {
            if (zza(packageInfo, false)) {
                return true;
            }
            if (zza(packageInfo, true)) {
                if (zzo.zzaw(this.mContext)) {
                    return true;
                }
                Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
            }
        }
        return false;
    }
}
