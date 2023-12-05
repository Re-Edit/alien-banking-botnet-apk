package com.google.android.gms.internal;

import android.content.Context;

public final class zzbik {
    private static Context zzaKl;
    private static Boolean zzaKm;

    /* JADX WARNING: Can't wrap try/catch for region: R(4:23|24|25|26) */
    /* JADX WARNING: Can't wrap try/catch for region: R(5:17|18|19|20|21) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:20:0x003e */
    /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0056 */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:25:0x0056=Splitter:B:25:0x0056, B:20:0x003e=Splitter:B:20:0x003e} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized boolean zzaN(android.content.Context r5) {
        /*
            java.lang.Class<com.google.android.gms.internal.zzbik> r0 = com.google.android.gms.internal.zzbik.class
            monitor-enter(r0)
            android.content.Context r1 = r5.getApplicationContext()     // Catch:{ all -> 0x0065 }
            android.content.Context r2 = zzaKl     // Catch:{ all -> 0x0065 }
            if (r2 == 0) goto L_0x001b
            java.lang.Boolean r2 = zzaKm     // Catch:{ all -> 0x0065 }
            if (r2 == 0) goto L_0x001b
            android.content.Context r2 = zzaKl     // Catch:{ all -> 0x0065 }
            if (r2 != r1) goto L_0x001b
            java.lang.Boolean r5 = zzaKm     // Catch:{ all -> 0x0065 }
            boolean r5 = r5.booleanValue()     // Catch:{ all -> 0x0065 }
            monitor-exit(r0)
            return r5
        L_0x001b:
            r2 = 0
            zzaKm = r2     // Catch:{ all -> 0x0065 }
            boolean r2 = com.google.android.gms.common.util.zzs.isAtLeastO()     // Catch:{ all -> 0x0065 }
            r3 = 0
            if (r2 == 0) goto L_0x0045
            java.lang.Class<android.content.pm.PackageManager> r5 = android.content.pm.PackageManager.class
            java.lang.String r2 = "isInstantApp"
            java.lang.Class[] r4 = new java.lang.Class[r3]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x003e }
            java.lang.reflect.Method r5 = r5.getDeclaredMethod(r2, r4)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x003e }
            android.content.pm.PackageManager r2 = r1.getPackageManager()     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x003e }
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x003e }
            java.lang.Object r5 = r5.invoke(r2, r4)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x003e }
            java.lang.Boolean r5 = (java.lang.Boolean) r5     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x003e }
            zzaKm = r5     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x003e }
            goto L_0x005b
        L_0x003e:
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r3)     // Catch:{ all -> 0x0065 }
        L_0x0042:
            zzaKm = r5     // Catch:{ all -> 0x0065 }
            goto L_0x005b
        L_0x0045:
            java.lang.ClassLoader r5 = r5.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x0056 }
            java.lang.String r2 = "com.google.android.instantapps.supervisor.InstantAppsRuntime"
            r5.loadClass(r2)     // Catch:{ ClassNotFoundException -> 0x0056 }
            r5 = 1
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ ClassNotFoundException -> 0x0056 }
            zzaKm = r5     // Catch:{ ClassNotFoundException -> 0x0056 }
            goto L_0x005b
        L_0x0056:
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r3)     // Catch:{ all -> 0x0065 }
            goto L_0x0042
        L_0x005b:
            zzaKl = r1     // Catch:{ all -> 0x0065 }
            java.lang.Boolean r5 = zzaKm     // Catch:{ all -> 0x0065 }
            boolean r5 = r5.booleanValue()     // Catch:{ all -> 0x0065 }
            monitor-exit(r0)
            return r5
        L_0x0065:
            r5 = move-exception
            monitor-exit(r0)
            throw r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbik.zzaN(android.content.Context):boolean");
    }
}
