package com.google.android.gms.common;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import com.google.android.gms.common.util.zzy;
import com.google.android.gms.internal.zzbim;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class zzo {
    @Deprecated
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    @Deprecated
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = 11010000;
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";
    private static boolean zzaAq = false;
    private static boolean zzaAr = false;
    private static boolean zzaAs = false;
    private static boolean zzaAt = false;
    static final AtomicBoolean zzaAu = new AtomicBoolean();
    private static final AtomicBoolean zzaAv = new AtomicBoolean();

    zzo() {
    }

    @Deprecated
    public static PendingIntent getErrorPendingIntent(int i, Context context, int i2) {
        return zze.zzoU().getErrorResolutionPendingIntent(context, i, i2);
    }

    @Deprecated
    public static String getErrorString(int i) {
        return ConnectionResult.getStatusString(i);
    }

    @Deprecated
    public static String getOpenSourceSoftwareLicenseInfo(Context context) {
        InputStream openInputStream;
        try {
            openInputStream = context.getContentResolver().openInputStream(new Uri.Builder().scheme("android.resource").authority("com.google.android.gms").appendPath("raw").appendPath("oss_notice").build());
            String next = new Scanner(openInputStream).useDelimiter("\\A").next();
            if (openInputStream != null) {
                openInputStream.close();
            }
            return next;
        } catch (NoSuchElementException unused) {
            if (openInputStream != null) {
                openInputStream.close();
            }
            return null;
        } catch (Exception unused2) {
            return null;
        } catch (Throwable th) {
            if (openInputStream != null) {
                openInputStream.close();
            }
            throw th;
        }
    }

    public static Context getRemoteContext(Context context) {
        try {
            return context.createPackageContext("com.google.android.gms", 3);
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    public static Resources getRemoteResource(Context context) {
        try {
            return context.getPackageManager().getResourcesForApplication("com.google.android.gms");
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00c1, code lost:
        if (com.google.android.gms.common.zzp.zza(r6, r8) == null) goto L_0x00c3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00ce, code lost:
        if (com.google.android.gms.common.zzp.zza(r6, com.google.android.gms.common.zzj.zzaAm) == null) goto L_0x00c3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00d9, code lost:
        if ((r6.versionCode / 1000) >= (GOOGLE_PLAY_SERVICES_VERSION_CODE / 1000)) goto L_0x0101;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00db, code lost:
        r0 = GOOGLE_PLAY_SERVICES_VERSION_CODE;
        r1 = r6.versionCode;
        r3 = new java.lang.StringBuilder(77);
        r3.append("Google Play services out of date.  Requires ");
        r3.append(r0);
        r3.append(" but found ");
        r3.append(r1);
        android.util.Log.w("GooglePlayServicesUtil", r3.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0100, code lost:
        return 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0101, code lost:
        r8 = r6.applicationInfo;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0103, code lost:
        if (r8 != null) goto L_0x0115;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:?, code lost:
        r8 = r0.getApplicationInfo("com.google.android.gms", 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x010c, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x010d, code lost:
        android.util.Log.wtf("GooglePlayServicesUtil", "Google Play services missing when getting application info.", r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0114, code lost:
        return 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0117, code lost:
        if (r8.enabled != false) goto L_0x011b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0119, code lost:
        return 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x011b, code lost:
        return 0;
     */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int isGooglePlayServicesAvailable(android.content.Context r8) {
        /*
            android.content.pm.PackageManager r0 = r8.getPackageManager()
            android.content.res.Resources r1 = r8.getResources()     // Catch:{ Throwable -> 0x000e }
            int r2 = com.google.android.gms.R.string.common_google_play_services_unknown_issue     // Catch:{ Throwable -> 0x000e }
            r1.getString(r2)     // Catch:{ Throwable -> 0x000e }
            goto L_0x0015
        L_0x000e:
            java.lang.String r1 = "GooglePlayServicesUtil"
            java.lang.String r2 = "The Google Play services resources were not found. Check your project configuration to ensure that the resources are included."
            android.util.Log.e(r1, r2)
        L_0x0015:
            java.lang.String r1 = "com.google.android.gms"
            java.lang.String r2 = r8.getPackageName()
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x0078
            java.util.concurrent.atomic.AtomicBoolean r1 = zzaAv
            boolean r1 = r1.get()
            if (r1 != 0) goto L_0x0078
            int r1 = com.google.android.gms.common.internal.zzbg.zzaE(r8)
            if (r1 == 0) goto L_0x0070
            int r2 = GOOGLE_PLAY_SERVICES_VERSION_CODE
            if (r1 != r2) goto L_0x0034
            goto L_0x0078
        L_0x0034:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = "com.google.android.gms.version"
            java.lang.String r0 = java.lang.String.valueOf(r0)
            java.lang.String r3 = java.lang.String.valueOf(r0)
            int r3 = r3.length()
            int r3 = r3 + 290
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>(r3)
            java.lang.String r3 = "The meta-data tag in your app's AndroidManifest.xml does not have the right value.  Expected "
            r4.append(r3)
            r4.append(r2)
            java.lang.String r2 = " but found "
            r4.append(r2)
            r4.append(r1)
            java.lang.String r1 = ".  You must have the following declaration within the <application> element:     <meta-data android:name=\""
            r4.append(r1)
            r4.append(r0)
            java.lang.String r0 = "\" android:value=\"@integer/google_play_services_version\" />"
            r4.append(r0)
            java.lang.String r0 = r4.toString()
            r8.<init>(r0)
            throw r8
        L_0x0070:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = "A required meta-data tag in your app's AndroidManifest.xml does not exist.  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />"
            r8.<init>(r0)
            throw r8
        L_0x0078:
            boolean r1 = com.google.android.gms.common.util.zzk.zzaH(r8)
            r2 = 1
            r3 = 0
            if (r1 != 0) goto L_0x0088
            boolean r1 = com.google.android.gms.common.util.zzk.zzaJ(r8)
            if (r1 != 0) goto L_0x0088
            r1 = 1
            goto L_0x0089
        L_0x0088:
            r1 = 0
        L_0x0089:
            r4 = 0
            r5 = 9
            if (r1 == 0) goto L_0x009f
            java.lang.String r4 = "com.android.vending"
            r6 = 8256(0x2040, float:1.1569E-41)
            android.content.pm.PackageInfo r4 = r0.getPackageInfo(r4, r6)     // Catch:{ NameNotFoundException -> 0x0097 }
            goto L_0x009f
        L_0x0097:
            java.lang.String r8 = "GooglePlayServicesUtil"
            java.lang.String r0 = "Google Play Store is missing."
        L_0x009b:
            android.util.Log.w(r8, r0)
            return r5
        L_0x009f:
            java.lang.String r6 = "com.google.android.gms"
            r7 = 64
            android.content.pm.PackageInfo r6 = r0.getPackageInfo(r6, r7)     // Catch:{ NameNotFoundException -> 0x011c }
            com.google.android.gms.common.zzp.zzax(r8)
            if (r1 == 0) goto L_0x00c8
            com.google.android.gms.common.zzg[] r8 = com.google.android.gms.common.zzj.zzaAm
            com.google.android.gms.common.zzg r8 = com.google.android.gms.common.zzp.zza((android.content.pm.PackageInfo) r4, (com.google.android.gms.common.zzg[]) r8)
            if (r8 != 0) goto L_0x00b9
            java.lang.String r8 = "GooglePlayServicesUtil"
            java.lang.String r0 = "Google Play Store signature invalid."
            goto L_0x009b
        L_0x00b9:
            com.google.android.gms.common.zzg[] r1 = new com.google.android.gms.common.zzg[r2]
            r1[r3] = r8
            com.google.android.gms.common.zzg r8 = com.google.android.gms.common.zzp.zza((android.content.pm.PackageInfo) r6, (com.google.android.gms.common.zzg[]) r1)
            if (r8 != 0) goto L_0x00d1
        L_0x00c3:
            java.lang.String r8 = "GooglePlayServicesUtil"
            java.lang.String r0 = "Google Play services signature invalid."
            goto L_0x009b
        L_0x00c8:
            com.google.android.gms.common.zzg[] r8 = com.google.android.gms.common.zzj.zzaAm
            com.google.android.gms.common.zzg r8 = com.google.android.gms.common.zzp.zza((android.content.pm.PackageInfo) r6, (com.google.android.gms.common.zzg[]) r8)
            if (r8 != 0) goto L_0x00d1
            goto L_0x00c3
        L_0x00d1:
            int r8 = GOOGLE_PLAY_SERVICES_VERSION_CODE
            int r8 = r8 / 1000
            int r1 = r6.versionCode
            int r1 = r1 / 1000
            if (r1 >= r8) goto L_0x0101
            java.lang.String r8 = "GooglePlayServicesUtil"
            int r0 = GOOGLE_PLAY_SERVICES_VERSION_CODE
            int r1 = r6.versionCode
            r2 = 77
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>(r2)
            java.lang.String r2 = "Google Play services out of date.  Requires "
            r3.append(r2)
            r3.append(r0)
            java.lang.String r0 = " but found "
            r3.append(r0)
            r3.append(r1)
            java.lang.String r0 = r3.toString()
            android.util.Log.w(r8, r0)
            r8 = 2
            return r8
        L_0x0101:
            android.content.pm.ApplicationInfo r8 = r6.applicationInfo
            if (r8 != 0) goto L_0x0115
            java.lang.String r8 = "com.google.android.gms"
            android.content.pm.ApplicationInfo r8 = r0.getApplicationInfo(r8, r3)     // Catch:{ NameNotFoundException -> 0x010c }
            goto L_0x0115
        L_0x010c:
            r8 = move-exception
            java.lang.String r0 = "GooglePlayServicesUtil"
            java.lang.String r1 = "Google Play services missing when getting application info."
            android.util.Log.wtf(r0, r1, r8)
            return r2
        L_0x0115:
            boolean r8 = r8.enabled
            if (r8 != 0) goto L_0x011b
            r8 = 3
            return r8
        L_0x011b:
            return r3
        L_0x011c:
            java.lang.String r8 = "GooglePlayServicesUtil"
            java.lang.String r0 = "Google Play services is missing."
            android.util.Log.w(r8, r0)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.zzo.isGooglePlayServicesAvailable(android.content.Context):int");
    }

    @Deprecated
    public static boolean isUserRecoverableError(int i) {
        if (i == 9) {
            return true;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
                return true;
            default:
                return false;
        }
    }

    @Deprecated
    public static void zzah(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        int isGooglePlayServicesAvailable = zze.zzoU().isGooglePlayServicesAvailable(context);
        if (isGooglePlayServicesAvailable != 0) {
            zze.zzoU();
            Intent zza = zze.zza(context, isGooglePlayServicesAvailable, "e");
            StringBuilder sb = new StringBuilder(57);
            sb.append("GooglePlayServices not available due to error ");
            sb.append(isGooglePlayServicesAvailable);
            Log.e("GooglePlayServicesUtil", sb.toString());
            if (zza == null) {
                throw new GooglePlayServicesNotAvailableException(isGooglePlayServicesAvailable);
            }
            throw new GooglePlayServicesRepairableException(isGooglePlayServicesAvailable, "Google Play Services not available", zza);
        }
    }

    @Deprecated
    public static void zzat(Context context) {
        if (!zzaAu.getAndSet(true)) {
            try {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
                if (notificationManager != null) {
                    notificationManager.cancel(10436);
                }
            } catch (SecurityException unused) {
            }
        }
    }

    @Deprecated
    public static int zzau(Context context) {
        try {
            return context.getPackageManager().getPackageInfo("com.google.android.gms", 0).versionCode;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
            return 0;
        }
    }

    public static boolean zzaw(Context context) {
        if (!zzaAt) {
            try {
                PackageInfo packageInfo = zzbim.zzaP(context).getPackageInfo("com.google.android.gms", 64);
                if (packageInfo != null) {
                    zzp.zzax(context);
                    if (zzp.zza(packageInfo, zzj.zzaAm[1]) != null) {
                        zzaAs = true;
                        zzaAt = true;
                    }
                }
                zzaAs = false;
            } catch (PackageManager.NameNotFoundException e) {
                Log.w("GooglePlayServicesUtil", "Cannot find Google Play services package name.", e);
            } catch (Throwable th) {
                zzaAt = true;
                throw th;
            }
            zzaAt = true;
        }
        return zzaAs || !"user".equals(Build.TYPE);
    }

    @TargetApi(19)
    @Deprecated
    public static boolean zzb(Context context, int i, String str) {
        return zzy.zzb(context, i, str);
    }

    @Deprecated
    public static boolean zze(Context context, int i) {
        if (i == 18) {
            return true;
        }
        if (i == 1) {
            return zzy(context, "com.google.android.gms");
        }
        return false;
    }

    @Deprecated
    public static boolean zzf(Context context, int i) {
        return zzy.zzf(context, i);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004e, code lost:
        r5 = ((android.os.UserManager) r5.getSystemService("user")).getApplicationRestrictions(r5.getPackageName());
     */
    @android.annotation.TargetApi(21)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean zzy(android.content.Context r5, java.lang.String r6) {
        /*
            java.lang.String r0 = "com.google.android.gms"
            boolean r0 = r6.equals(r0)
            boolean r1 = com.google.android.gms.common.util.zzs.zzsd()
            r2 = 1
            if (r1 == 0) goto L_0x0034
            android.content.pm.PackageManager r1 = r5.getPackageManager()
            android.content.pm.PackageInstaller r1 = r1.getPackageInstaller()
            java.util.List r1 = r1.getAllSessions()
            java.util.Iterator r1 = r1.iterator()
        L_0x001d:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x0034
            java.lang.Object r3 = r1.next()
            android.content.pm.PackageInstaller$SessionInfo r3 = (android.content.pm.PackageInstaller.SessionInfo) r3
            java.lang.String r3 = r3.getAppPackageName()
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x001d
            return r2
        L_0x0034:
            android.content.pm.PackageManager r1 = r5.getPackageManager()
            r3 = 8192(0x2000, float:1.14794E-41)
            r4 = 0
            android.content.pm.ApplicationInfo r6 = r1.getApplicationInfo(r6, r3)     // Catch:{ NameNotFoundException -> 0x0074 }
            if (r0 == 0) goto L_0x0044
            boolean r5 = r6.enabled     // Catch:{ NameNotFoundException -> 0x0074 }
            return r5
        L_0x0044:
            boolean r6 = r6.enabled     // Catch:{ NameNotFoundException -> 0x0074 }
            if (r6 == 0) goto L_0x0074
            boolean r6 = com.google.android.gms.common.util.zzs.zzsa()     // Catch:{ NameNotFoundException -> 0x0074 }
            if (r6 == 0) goto L_0x0070
            java.lang.String r6 = "user"
            java.lang.Object r6 = r5.getSystemService(r6)     // Catch:{ NameNotFoundException -> 0x0074 }
            android.os.UserManager r6 = (android.os.UserManager) r6     // Catch:{ NameNotFoundException -> 0x0074 }
            java.lang.String r5 = r5.getPackageName()     // Catch:{ NameNotFoundException -> 0x0074 }
            android.os.Bundle r5 = r6.getApplicationRestrictions(r5)     // Catch:{ NameNotFoundException -> 0x0074 }
            if (r5 == 0) goto L_0x0070
            java.lang.String r6 = "true"
            java.lang.String r0 = "restricted_profile"
            java.lang.String r5 = r5.getString(r0)     // Catch:{ NameNotFoundException -> 0x0074 }
            boolean r5 = r6.equals(r5)     // Catch:{ NameNotFoundException -> 0x0074 }
            if (r5 == 0) goto L_0x0070
            r5 = 1
            goto L_0x0071
        L_0x0070:
            r5 = 0
        L_0x0071:
            if (r5 != 0) goto L_0x0074
            return r2
        L_0x0074:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.zzo.zzy(android.content.Context, java.lang.String):boolean");
    }
}
