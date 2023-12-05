package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.google.android.gms.internal.zzbim;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class zza {
    public static byte[] zzA(Context context, String str) throws PackageManager.NameNotFoundException {
        MessageDigest zzbE;
        PackageInfo packageInfo = zzbim.zzaP(context).getPackageInfo(str, 64);
        if (packageInfo.signatures == null || packageInfo.signatures.length <= 0 || (zzbE = zzbE("SHA1")) == null) {
            return null;
        }
        return zzbE.digest(packageInfo.signatures[0].toByteArray());
    }

    private static MessageDigest zzbE(String str) {
        int i = 0;
        while (i < 2) {
            try {
                MessageDigest instance = MessageDigest.getInstance(str);
                if (instance != null) {
                    return instance;
                }
                i++;
            } catch (NoSuchAlgorithmException unused) {
            }
        }
        return null;
    }
}
