package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import java.io.File;

public final class zzw {
    @TargetApi(21)
    public static File getNoBackupFilesDir(Context context) {
        return zzs.zzsd() ? context.getNoBackupFilesDir() : zzd(new File(context.getApplicationInfo().dataDir, "no_backup"));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003a, code lost:
        return r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static synchronized java.io.File zzd(java.io.File r4) {
        /*
            java.lang.Class<com.google.android.gms.common.util.zzw> r0 = com.google.android.gms.common.util.zzw.class
            monitor-enter(r0)
            boolean r1 = r4.exists()     // Catch:{ all -> 0x003b }
            if (r1 != 0) goto L_0x0039
            boolean r1 = r4.mkdirs()     // Catch:{ all -> 0x003b }
            if (r1 != 0) goto L_0x0039
            boolean r1 = r4.exists()     // Catch:{ all -> 0x003b }
            if (r1 == 0) goto L_0x0017
            monitor-exit(r0)
            return r4
        L_0x0017:
            java.lang.String r1 = "SupportV4Utils"
            java.lang.String r2 = "Unable to create no-backup dir "
            java.lang.String r4 = r4.getPath()     // Catch:{ all -> 0x003b }
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ all -> 0x003b }
            int r3 = r4.length()     // Catch:{ all -> 0x003b }
            if (r3 == 0) goto L_0x002e
            java.lang.String r4 = r2.concat(r4)     // Catch:{ all -> 0x003b }
            goto L_0x0033
        L_0x002e:
            java.lang.String r4 = new java.lang.String     // Catch:{ all -> 0x003b }
            r4.<init>(r2)     // Catch:{ all -> 0x003b }
        L_0x0033:
            android.util.Log.w(r1, r4)     // Catch:{ all -> 0x003b }
            r4 = 0
            monitor-exit(r0)
            return r4
        L_0x0039:
            monitor-exit(r0)
            return r4
        L_0x003b:
            r4 = move-exception
            monitor-exit(r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.zzw.zzd(java.io.File):java.io.File");
    }
}
