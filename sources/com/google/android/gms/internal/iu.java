package com.google.android.gms.internal;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

public class iu {
    private static Uri CONTENT_URI = Uri.parse("content://com.google.android.gsf.gservices");
    private static Uri zzbUc = Uri.parse("content://com.google.android.gsf.gservices/prefix");
    private static Pattern zzbUd = Pattern.compile("^(1|true|t|on|yes|y)$", 2);
    private static Pattern zzbUe = Pattern.compile("^(0|false|f|off|no|n)$", 2);
    /* access modifiers changed from: private */
    public static final AtomicBoolean zzbUf = new AtomicBoolean();
    private static HashMap<String, String> zzbUg;
    private static HashMap<String, Boolean> zzbUh = new HashMap<>();
    private static HashMap<String, Integer> zzbUi = new HashMap<>();
    private static HashMap<String, Long> zzbUj = new HashMap<>();
    private static HashMap<String, Float> zzbUk = new HashMap<>();
    private static Object zzbUl;
    private static boolean zzbUm;
    private static String[] zzbUn = new String[0];

    public static long getLong(ContentResolver contentResolver, String str, long j) {
        Object zzb = zzb(contentResolver);
        long j2 = 0;
        Long l = (Long) zza(zzbUj, str, 0L);
        if (l != null) {
            return l.longValue();
        }
        String zza = zza(contentResolver, str, (String) null);
        if (zza != null) {
            try {
                long parseLong = Long.parseLong(zza);
                l = Long.valueOf(parseLong);
                j2 = parseLong;
            } catch (NumberFormatException unused) {
            }
        }
        HashMap<String, Long> hashMap = zzbUj;
        synchronized (iu.class) {
            if (zzb == zzbUl) {
                hashMap.put(str, l);
                zzbUg.remove(str);
            }
        }
        return j2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0012, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static <T> T zza(java.util.HashMap<java.lang.String, T> r2, java.lang.String r3, T r4) {
        /*
            java.lang.Class<com.google.android.gms.internal.iu> r0 = com.google.android.gms.internal.iu.class
            monitor-enter(r0)
            boolean r1 = r2.containsKey(r3)     // Catch:{ all -> 0x0016 }
            if (r1 == 0) goto L_0x0013
            java.lang.Object r2 = r2.get(r3)     // Catch:{ all -> 0x0016 }
            if (r2 == 0) goto L_0x0010
            goto L_0x0011
        L_0x0010:
            r2 = r4
        L_0x0011:
            monitor-exit(r0)     // Catch:{ all -> 0x0016 }
            return r2
        L_0x0013:
            monitor-exit(r0)     // Catch:{ all -> 0x0016 }
            r2 = 0
            return r2
        L_0x0016:
            r2 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0016 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.iu.zza(java.util.HashMap, java.lang.String, java.lang.Object):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001e, code lost:
        return r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0054, code lost:
        return r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0056, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x005b, code lost:
        r9 = r9.query(CONTENT_URI, (java.lang.String[]) null, (java.lang.String) null, new java.lang.String[]{r10}, (java.lang.String) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x006b, code lost:
        if (r9 == null) goto L_0x008e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0071, code lost:
        if (r9.moveToFirst() != false) goto L_0x0074;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0074, code lost:
        r11 = r9.getString(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0078, code lost:
        if (r11 == null) goto L_0x0081;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x007e, code lost:
        if (r11.equals((java.lang.Object) null) == false) goto L_0x0081;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0080, code lost:
        r11 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0081, code lost:
        zza(r0, r10, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0084, code lost:
        if (r11 == null) goto L_0x0087;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0087, code lost:
        r11 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x008d, code lost:
        return r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
        zza(r0, r10, (java.lang.String) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0091, code lost:
        if (r9 == null) goto L_0x0096;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0093, code lost:
        r9.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0096, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0097, code lost:
        r10 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0098, code lost:
        if (r9 != null) goto L_0x009a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x009a, code lost:
        r9.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x009d, code lost:
        throw r10;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String zza(android.content.ContentResolver r9, java.lang.String r10, java.lang.String r11) {
        /*
            java.lang.Class<com.google.android.gms.internal.iu> r11 = com.google.android.gms.internal.iu.class
            monitor-enter(r11)
            zza(r9)     // Catch:{ all -> 0x009e }
            java.lang.Object r0 = zzbUl     // Catch:{ all -> 0x009e }
            java.util.HashMap<java.lang.String, java.lang.String> r1 = zzbUg     // Catch:{ all -> 0x009e }
            boolean r1 = r1.containsKey(r10)     // Catch:{ all -> 0x009e }
            r2 = 0
            if (r1 == 0) goto L_0x001f
            java.util.HashMap<java.lang.String, java.lang.String> r9 = zzbUg     // Catch:{ all -> 0x009e }
            java.lang.Object r9 = r9.get(r10)     // Catch:{ all -> 0x009e }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ all -> 0x009e }
            if (r9 == 0) goto L_0x001c
            goto L_0x001d
        L_0x001c:
            r9 = r2
        L_0x001d:
            monitor-exit(r11)     // Catch:{ all -> 0x009e }
            return r9
        L_0x001f:
            java.lang.String[] r1 = zzbUn     // Catch:{ all -> 0x009e }
            int r3 = r1.length     // Catch:{ all -> 0x009e }
            r4 = 0
            r5 = 0
        L_0x0024:
            if (r5 >= r3) goto L_0x005a
            r6 = r1[r5]     // Catch:{ all -> 0x009e }
            boolean r6 = r10.startsWith(r6)     // Catch:{ all -> 0x009e }
            if (r6 == 0) goto L_0x0057
            boolean r0 = zzbUm     // Catch:{ all -> 0x009e }
            if (r0 == 0) goto L_0x003a
            java.util.HashMap<java.lang.String, java.lang.String> r0 = zzbUg     // Catch:{ all -> 0x009e }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x009e }
            if (r0 == 0) goto L_0x0055
        L_0x003a:
            java.lang.String[] r0 = zzbUn     // Catch:{ all -> 0x009e }
            zzc(r9, r0)     // Catch:{ all -> 0x009e }
            java.util.HashMap<java.lang.String, java.lang.String> r9 = zzbUg     // Catch:{ all -> 0x009e }
            boolean r9 = r9.containsKey(r10)     // Catch:{ all -> 0x009e }
            if (r9 == 0) goto L_0x0055
            java.util.HashMap<java.lang.String, java.lang.String> r9 = zzbUg     // Catch:{ all -> 0x009e }
            java.lang.Object r9 = r9.get(r10)     // Catch:{ all -> 0x009e }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ all -> 0x009e }
            if (r9 == 0) goto L_0x0052
            goto L_0x0053
        L_0x0052:
            r9 = r2
        L_0x0053:
            monitor-exit(r11)     // Catch:{ all -> 0x009e }
            return r9
        L_0x0055:
            monitor-exit(r11)     // Catch:{ all -> 0x009e }
            return r2
        L_0x0057:
            int r5 = r5 + 1
            goto L_0x0024
        L_0x005a:
            monitor-exit(r11)     // Catch:{ all -> 0x009e }
            android.net.Uri r11 = CONTENT_URI
            r5 = 0
            r6 = 0
            r1 = 1
            java.lang.String[] r7 = new java.lang.String[r1]
            r7[r4] = r10
            r8 = 0
            r3 = r9
            r4 = r11
            android.database.Cursor r9 = r3.query(r4, r5, r6, r7, r8)
            if (r9 == 0) goto L_0x008e
            boolean r11 = r9.moveToFirst()     // Catch:{ all -> 0x0097 }
            if (r11 != 0) goto L_0x0074
            goto L_0x008e
        L_0x0074:
            java.lang.String r11 = r9.getString(r1)     // Catch:{ all -> 0x0097 }
            if (r11 == 0) goto L_0x0081
            boolean r1 = r11.equals(r2)     // Catch:{ all -> 0x0097 }
            if (r1 == 0) goto L_0x0081
            r11 = r2
        L_0x0081:
            zza((java.lang.Object) r0, (java.lang.String) r10, (java.lang.String) r11)     // Catch:{ all -> 0x0097 }
            if (r11 == 0) goto L_0x0087
            goto L_0x0088
        L_0x0087:
            r11 = r2
        L_0x0088:
            if (r9 == 0) goto L_0x008d
            r9.close()
        L_0x008d:
            return r11
        L_0x008e:
            zza((java.lang.Object) r0, (java.lang.String) r10, (java.lang.String) r2)     // Catch:{ all -> 0x0097 }
            if (r9 == 0) goto L_0x0096
            r9.close()
        L_0x0096:
            return r2
        L_0x0097:
            r10 = move-exception
            if (r9 == 0) goto L_0x009d
            r9.close()
        L_0x009d:
            throw r10
        L_0x009e:
            r9 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x009e }
            throw r9
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.iu.zza(android.content.ContentResolver, java.lang.String, java.lang.String):java.lang.String");
    }

    private static Map<String, String> zza(ContentResolver contentResolver, String... strArr) {
        Cursor query = contentResolver.query(zzbUc, (String[]) null, (String) null, strArr, (String) null);
        TreeMap treeMap = new TreeMap();
        if (query == null) {
            return treeMap;
        }
        while (query.moveToNext()) {
            try {
                treeMap.put(query.getString(0), query.getString(1));
            } finally {
                query.close();
            }
        }
        return treeMap;
    }

    private static void zza(ContentResolver contentResolver) {
        if (zzbUg == null) {
            zzbUf.set(false);
            zzbUg = new HashMap<>();
            zzbUl = new Object();
            zzbUm = false;
            contentResolver.registerContentObserver(CONTENT_URI, true, new iv((Handler) null));
        } else if (zzbUf.getAndSet(false)) {
            zzbUg.clear();
            zzbUh.clear();
            zzbUi.clear();
            zzbUj.clear();
            zzbUk.clear();
            zzbUl = new Object();
            zzbUm = false;
        }
    }

    private static void zza(Object obj, String str, String str2) {
        synchronized (iu.class) {
            if (obj == zzbUl) {
                zzbUg.put(str, str2);
            }
        }
    }

    private static Object zzb(ContentResolver contentResolver) {
        Object obj;
        synchronized (iu.class) {
            zza(contentResolver);
            obj = zzbUl;
        }
        return obj;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x006c, code lost:
        if (r9.length != 0) goto L_0x006e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void zzb(android.content.ContentResolver r8, java.lang.String... r9) {
        /*
            int r0 = r9.length
            if (r0 != 0) goto L_0x0004
            return
        L_0x0004:
            java.lang.Class<com.google.android.gms.internal.iu> r0 = com.google.android.gms.internal.iu.class
            monitor-enter(r0)
            zza(r8)     // Catch:{ all -> 0x0077 }
            java.lang.String[] r1 = zzbUn     // Catch:{ all -> 0x0077 }
            int r1 = r1.length     // Catch:{ all -> 0x0077 }
            int r2 = r9.length     // Catch:{ all -> 0x0077 }
            int r1 = r1 + r2
            int r1 = r1 << 2
            int r1 = r1 / 3
            int r1 = r1 + 1
            java.util.HashSet r2 = new java.util.HashSet     // Catch:{ all -> 0x0077 }
            r2.<init>(r1)     // Catch:{ all -> 0x0077 }
            java.lang.String[] r1 = zzbUn     // Catch:{ all -> 0x0077 }
            java.util.List r1 = java.util.Arrays.asList(r1)     // Catch:{ all -> 0x0077 }
            r2.addAll(r1)     // Catch:{ all -> 0x0077 }
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x0077 }
            r1.<init>()     // Catch:{ all -> 0x0077 }
            int r3 = r9.length     // Catch:{ all -> 0x0077 }
            r4 = 0
            r5 = 0
        L_0x002b:
            if (r5 >= r3) goto L_0x003b
            r6 = r9[r5]     // Catch:{ all -> 0x0077 }
            boolean r7 = r2.add(r6)     // Catch:{ all -> 0x0077 }
            if (r7 == 0) goto L_0x0038
            r1.add(r6)     // Catch:{ all -> 0x0077 }
        L_0x0038:
            int r5 = r5 + 1
            goto L_0x002b
        L_0x003b:
            boolean r9 = r1.isEmpty()     // Catch:{ all -> 0x0077 }
            if (r9 == 0) goto L_0x0044
            java.lang.String[] r9 = new java.lang.String[r4]     // Catch:{ all -> 0x0077 }
            goto L_0x005e
        L_0x0044:
            int r9 = r2.size()     // Catch:{ all -> 0x0077 }
            java.lang.String[] r9 = new java.lang.String[r9]     // Catch:{ all -> 0x0077 }
            java.lang.Object[] r9 = r2.toArray(r9)     // Catch:{ all -> 0x0077 }
            java.lang.String[] r9 = (java.lang.String[]) r9     // Catch:{ all -> 0x0077 }
            zzbUn = r9     // Catch:{ all -> 0x0077 }
            int r9 = r1.size()     // Catch:{ all -> 0x0077 }
            java.lang.String[] r9 = new java.lang.String[r9]     // Catch:{ all -> 0x0077 }
            java.lang.Object[] r9 = r1.toArray(r9)     // Catch:{ all -> 0x0077 }
            java.lang.String[] r9 = (java.lang.String[]) r9     // Catch:{ all -> 0x0077 }
        L_0x005e:
            boolean r1 = zzbUm     // Catch:{ all -> 0x0077 }
            if (r1 == 0) goto L_0x0072
            java.util.HashMap<java.lang.String, java.lang.String> r1 = zzbUg     // Catch:{ all -> 0x0077 }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x0077 }
            if (r1 == 0) goto L_0x006b
            goto L_0x0072
        L_0x006b:
            int r1 = r9.length     // Catch:{ all -> 0x0077 }
            if (r1 == 0) goto L_0x0075
        L_0x006e:
            zzc(r8, r9)     // Catch:{ all -> 0x0077 }
            goto L_0x0075
        L_0x0072:
            java.lang.String[] r9 = zzbUn     // Catch:{ all -> 0x0077 }
            goto L_0x006e
        L_0x0075:
            monitor-exit(r0)     // Catch:{ all -> 0x0077 }
            return
        L_0x0077:
            r8 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0077 }
            throw r8
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.iu.zzb(android.content.ContentResolver, java.lang.String[]):void");
    }

    private static void zzc(ContentResolver contentResolver, String[] strArr) {
        zzbUg.putAll(zza(contentResolver, strArr));
        zzbUm = true;
    }
}
