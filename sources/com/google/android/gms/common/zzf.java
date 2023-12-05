package com.google.android.gms.common;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzbb;
import com.google.android.gms.common.internal.zzbc;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamite.DynamiteModule;

final class zzf {
    private static zzbb zzaAf;
    private static final Object zzaAg = new Object();
    private static Context zzaAh;

    static boolean zza(String str, zzg zzg) {
        return zza(str, zzg, false);
    }

    private static boolean zza(String str, zzg zzg, boolean z) {
        if (!zzoV()) {
            return false;
        }
        zzbr.zzu(zzaAh);
        try {
            return zzaAf.zza(new zzm(str, zzg, z), zzn.zzw(zzaAh.getPackageManager()));
        } catch (RemoteException e) {
            Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
            return false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0019, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static synchronized void zzav(android.content.Context r2) {
        /*
            java.lang.Class<com.google.android.gms.common.zzf> r0 = com.google.android.gms.common.zzf.class
            monitor-enter(r0)
            android.content.Context r1 = zzaAh     // Catch:{ all -> 0x001a }
            if (r1 != 0) goto L_0x0011
            if (r2 == 0) goto L_0x0018
            android.content.Context r2 = r2.getApplicationContext()     // Catch:{ all -> 0x001a }
            zzaAh = r2     // Catch:{ all -> 0x001a }
            monitor-exit(r0)
            return
        L_0x0011:
            java.lang.String r2 = "GoogleCertificates"
            java.lang.String r1 = "GoogleCertificates has been initialized already"
            android.util.Log.w(r2, r1)     // Catch:{ all -> 0x001a }
        L_0x0018:
            monitor-exit(r0)
            return
        L_0x001a:
            r2 = move-exception
            monitor-exit(r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.zzf.zzav(android.content.Context):void");
    }

    static boolean zzb(String str, zzg zzg) {
        return zza(str, zzg, true);
    }

    private static boolean zzoV() {
        if (zzaAf != null) {
            return true;
        }
        zzbr.zzu(zzaAh);
        synchronized (zzaAg) {
            if (zzaAf == null) {
                try {
                    zzaAf = zzbc.zzJ(DynamiteModule.zza(zzaAh, DynamiteModule.zzaST, "com.google.android.gms.googlecertificates").zzcW("com.google.android.gms.common.GoogleCertificatesImpl"));
                } catch (DynamiteModule.zzc e) {
                    Log.e("GoogleCertificates", "Failed to load com.google.android.gms.googlecertificates", e);
                    return false;
                }
            }
        }
        return true;
    }
}
