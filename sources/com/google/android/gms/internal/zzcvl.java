package com.google.android.gms.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.PowerManager;
import android.os.WorkSource;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.util.zzv;
import com.google.android.gms.common.util.zzz;

public final class zzcvl {
    private static boolean DEBUG = false;
    private static String TAG = "WakeLock";
    private static String zzbDa = "*gcore*:";
    private final Context mContext;
    private final String zzaJr;
    private final String zzaJt;
    private final PowerManager.WakeLock zzbDb;
    private WorkSource zzbDc;
    private final int zzbDd;
    private final String zzbDe;
    private boolean zzbDf;
    private int zzbDg;
    private int zzbDh;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public zzcvl(Context context, int i, String str) {
        this(context, 1, str, (String) null, context == null ? null : context.getPackageName());
    }

    @SuppressLint({"UnwrappedWakeLock"})
    private zzcvl(Context context, int i, String str, String str2, String str3) {
        this(context, 1, str, (String) null, str3, (String) null);
    }

    @SuppressLint({"UnwrappedWakeLock"})
    private zzcvl(Context context, int i, String str, String str2, String str3, String str4) {
        this.zzbDf = true;
        zzbr.zzh(str, "Wake lock name can NOT be empty");
        this.zzbDd = i;
        this.zzbDe = null;
        this.zzaJt = null;
        this.mContext = context.getApplicationContext();
        if (!"com.google.android.gms".equals(context.getPackageName())) {
            String valueOf = String.valueOf(zzbDa);
            String valueOf2 = String.valueOf(str);
            this.zzaJr = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        } else {
            this.zzaJr = str;
        }
        this.zzbDb = ((PowerManager) context.getSystemService("power")).newWakeLock(i, str);
        if (zzz.zzaM(this.mContext)) {
            this.zzbDc = zzz.zzE(context, zzv.zzcM(str3) ? context.getPackageName() : str3);
            WorkSource workSource = this.zzbDc;
            if (workSource != null && zzz.zzaM(this.mContext)) {
                WorkSource workSource2 = this.zzbDc;
                if (workSource2 != null) {
                    workSource2.add(workSource);
                } else {
                    this.zzbDc = workSource;
                }
                try {
                    this.zzbDb.setWorkSource(this.zzbDc);
                } catch (IllegalArgumentException e) {
                    Log.wtf(TAG, e.toString());
                }
            }
        }
    }

    private final boolean zzeW(String str) {
        String str2 = null;
        return !TextUtils.isEmpty(str2) && !str2.equals(this.zzbDe);
    }

    private final String zzi(String str, boolean z) {
        if (!this.zzbDf) {
            return this.zzbDe;
        }
        if (z) {
            return null;
        }
        return this.zzbDe;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001e, code lost:
        if (r10.zzbDh == 0) goto L_0x0020;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0016, code lost:
        if (r12 == false) goto L_0x0018;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void acquire(long r11) {
        /*
            r10 = this;
            r11 = 0
            boolean r12 = r10.zzeW(r11)
            java.lang.String r4 = r10.zzi(r11, r12)
            monitor-enter(r10)
            boolean r11 = r10.zzbDf     // Catch:{ all -> 0x004b }
            if (r11 == 0) goto L_0x0018
            int r11 = r10.zzbDg     // Catch:{ all -> 0x004b }
            int r0 = r11 + 1
            r10.zzbDg = r0     // Catch:{ all -> 0x004b }
            if (r11 == 0) goto L_0x0020
            if (r12 != 0) goto L_0x0020
        L_0x0018:
            boolean r11 = r10.zzbDf     // Catch:{ all -> 0x004b }
            if (r11 != 0) goto L_0x0042
            int r11 = r10.zzbDh     // Catch:{ all -> 0x004b }
            if (r11 != 0) goto L_0x0042
        L_0x0020:
            com.google.android.gms.common.stats.zze.zzrW()     // Catch:{ all -> 0x004b }
            android.content.Context r0 = r10.mContext     // Catch:{ all -> 0x004b }
            android.os.PowerManager$WakeLock r11 = r10.zzbDb     // Catch:{ all -> 0x004b }
            java.lang.String r1 = com.google.android.gms.common.stats.zzc.zza(r11, r4)     // Catch:{ all -> 0x004b }
            r2 = 7
            java.lang.String r3 = r10.zzaJr     // Catch:{ all -> 0x004b }
            r5 = 0
            int r6 = r10.zzbDd     // Catch:{ all -> 0x004b }
            android.os.WorkSource r11 = r10.zzbDc     // Catch:{ all -> 0x004b }
            java.util.List r7 = com.google.android.gms.common.util.zzz.zzb(r11)     // Catch:{ all -> 0x004b }
            r8 = 1000(0x3e8, double:4.94E-321)
            com.google.android.gms.common.stats.zze.zza(r0, r1, r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x004b }
            int r11 = r10.zzbDh     // Catch:{ all -> 0x004b }
            int r11 = r11 + 1
            r10.zzbDh = r11     // Catch:{ all -> 0x004b }
        L_0x0042:
            monitor-exit(r10)     // Catch:{ all -> 0x004b }
            android.os.PowerManager$WakeLock r11 = r10.zzbDb
            r0 = 1000(0x3e8, double:4.94E-321)
            r11.acquire(r0)
            return
        L_0x004b:
            r11 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x004b }
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcvl.acquire(long):void");
    }

    public final boolean isHeld() {
        return this.zzbDb.isHeld();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001e, code lost:
        if (r11.zzbDh == 1) goto L_0x0020;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0016, code lost:
        if (r1 == false) goto L_0x0018;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void release() {
        /*
            r11 = this;
            r0 = 0
            boolean r1 = r11.zzeW(r0)
            java.lang.String r6 = r11.zzi(r0, r1)
            monitor-enter(r11)
            boolean r0 = r11.zzbDf     // Catch:{ all -> 0x0047 }
            r10 = 1
            if (r0 == 0) goto L_0x0018
            int r0 = r11.zzbDg     // Catch:{ all -> 0x0047 }
            int r0 = r0 - r10
            r11.zzbDg = r0     // Catch:{ all -> 0x0047 }
            if (r0 == 0) goto L_0x0020
            if (r1 != 0) goto L_0x0020
        L_0x0018:
            boolean r0 = r11.zzbDf     // Catch:{ all -> 0x0047 }
            if (r0 != 0) goto L_0x0040
            int r0 = r11.zzbDh     // Catch:{ all -> 0x0047 }
            if (r0 != r10) goto L_0x0040
        L_0x0020:
            com.google.android.gms.common.stats.zze.zzrW()     // Catch:{ all -> 0x0047 }
            android.content.Context r2 = r11.mContext     // Catch:{ all -> 0x0047 }
            android.os.PowerManager$WakeLock r0 = r11.zzbDb     // Catch:{ all -> 0x0047 }
            java.lang.String r3 = com.google.android.gms.common.stats.zzc.zza(r0, r6)     // Catch:{ all -> 0x0047 }
            r4 = 8
            java.lang.String r5 = r11.zzaJr     // Catch:{ all -> 0x0047 }
            r7 = 0
            int r8 = r11.zzbDd     // Catch:{ all -> 0x0047 }
            android.os.WorkSource r0 = r11.zzbDc     // Catch:{ all -> 0x0047 }
            java.util.List r9 = com.google.android.gms.common.util.zzz.zzb(r0)     // Catch:{ all -> 0x0047 }
            com.google.android.gms.common.stats.zze.zza(r2, r3, r4, r5, r6, r7, r8, r9)     // Catch:{ all -> 0x0047 }
            int r0 = r11.zzbDh     // Catch:{ all -> 0x0047 }
            int r0 = r0 - r10
            r11.zzbDh = r0     // Catch:{ all -> 0x0047 }
        L_0x0040:
            monitor-exit(r11)     // Catch:{ all -> 0x0047 }
            android.os.PowerManager$WakeLock r0 = r11.zzbDb
            r0.release()
            return
        L_0x0047:
            r0 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x0047 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcvl.release():void");
    }

    public final void setReferenceCounted(boolean z) {
        this.zzbDb.setReferenceCounted(false);
        this.zzbDf = false;
    }
}
