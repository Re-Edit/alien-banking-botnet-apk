package com.google.android.gms.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;
import org.apache.http.Header;
import org.apache.http.HttpEntity;

public final class zzad implements zzk {
    private static boolean DEBUG = zzab.DEBUG;
    private static int zzam = 3000;
    private static int zzan = 4096;
    private zzan zzao;
    private zzae zzap;

    public zzad(zzan zzan2) {
        this(zzan2, new zzae(zzan));
    }

    private zzad(zzan zzan2, zzae zzae) {
        this.zzao = zzan2;
        this.zzap = zzae;
    }

    private static Map<String, String> zza(Header[] headerArr) {
        TreeMap treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < headerArr.length; i++) {
            treeMap.put(headerArr[i].getName(), headerArr[i].getValue());
        }
        return treeMap;
    }

    private static void zza(String str, zzp<?> zzp, zzaa zzaa) throws zzaa {
        zzx zzj = zzp.zzj();
        int zzi = zzp.zzi();
        try {
            zzj.zza(zzaa);
            zzp.zzb(String.format("%s-retry [timeout=%s]", new Object[]{str, Integer.valueOf(zzi)}));
        } catch (zzaa e) {
            zzp.zzb(String.format("%s-timeout-giveup [timeout=%s]", new Object[]{str, Integer.valueOf(zzi)}));
            throw e;
        }
    }

    private final byte[] zza(HttpEntity httpEntity) throws IOException, zzy {
        zzaq zzaq = new zzaq(this.zzap, (int) httpEntity.getContentLength());
        try {
            InputStream content = httpEntity.getContent();
            if (content != null) {
                byte[] zzb = this.zzap.zzb(1024);
                while (true) {
                    int read = content.read(zzb);
                    if (read == -1) {
                        break;
                    }
                    zzaq.write(zzb, 0, read);
                }
                byte[] byteArray = zzaq.toByteArray();
                try {
                    httpEntity.consumeContent();
                } catch (IOException unused) {
                    zzab.zza("Error occured when calling consumingContent", new Object[0]);
                }
                this.zzap.zza(zzb);
                zzaq.close();
                return byteArray;
            }
            throw new zzy();
        } catch (Throwable th) {
            try {
                httpEntity.consumeContent();
            } catch (IOException unused2) {
                zzab.zza("Error occured when calling consumingContent", new Object[0]);
            }
            this.zzap.zza((byte[]) null);
            zzaq.close();
            throw th;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v0, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v0, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v1, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v3, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v2, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v4, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v3, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v6, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v7, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v9, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v10, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v5, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v11, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v6, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v7, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v10, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v11, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v8, resolved type: java.lang.Object[]} */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x01c8, code lost:
        r0 = "socket";
        r5 = new com.google.android.gms.internal.zzz();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x01cf, code lost:
        zza(r0, r2, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00b2, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00b3, code lost:
        r14 = r5;
        r13 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00d3, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00d4, code lost:
        r14 = r5;
        r13 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0115, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0116, code lost:
        r8 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0123, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0127, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0128, code lost:
        r14 = r5;
        r10 = null;
        r13 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x012d, code lost:
        r0 = r10.getStatusLine().getStatusCode();
        com.google.android.gms.internal.zzab.zzc("Unexpected response code %d for %s", java.lang.Integer.valueOf(r0), r21.getUrl());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0149, code lost:
        if (r13 != null) goto L_0x014b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x014b, code lost:
        r11 = new com.google.android.gms.internal.zzn(r0, r13, r14, false, android.os.SystemClock.elapsedRealtime() - r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x015b, code lost:
        if (r0 == 401) goto L_0x0185;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0164, code lost:
        if (r0 < 400) goto L_0x0171;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0170, code lost:
        throw new com.google.android.gms.internal.zzf(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0173, code lost:
        if (r0 < 500) goto L_0x017f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x017e, code lost:
        throw new com.google.android.gms.internal.zzy(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x0184, code lost:
        throw new com.google.android.gms.internal.zzy(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x0185, code lost:
        zza("auth", r2, new com.google.android.gms.internal.zza(r11));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x0191, code lost:
        r0 = "network";
        r5 = new com.google.android.gms.internal.zzm();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x019e, code lost:
        throw new com.google.android.gms.internal.zzo(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x019f, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x01a0, code lost:
        r2 = java.lang.String.valueOf(r21.getUrl());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x01b0, code lost:
        if (r2.length() != 0) goto L_0x01b2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x01b2, code lost:
        r2 = "Bad URL ".concat(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x01b7, code lost:
        r2 = new java.lang.String("Bad URL ");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x01bf, code lost:
        throw new java.lang.RuntimeException(r2, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x01c0, code lost:
        r0 = "connection";
        r5 = new com.google.android.gms.internal.zzz();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:100:? A[ExcHandler: ConnectTimeoutException (unused org.apache.http.conn.ConnectTimeoutException), SYNTHETIC, Splitter:B:2:0x000f] */
    /* JADX WARNING: Removed duplicated region for block: B:102:? A[ExcHandler: SocketTimeoutException (unused java.net.SocketTimeoutException), SYNTHETIC, Splitter:B:2:0x000f] */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x0199 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0103 A[Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0115 }] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0118 A[Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }, EDGE_INSN: B:105:0x0118->B:59:0x0118 ?: BREAK  
    EDGE_INSN: B:109:0x0118->B:59:0x0118 ?: BREAK  ] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x012d  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x019f A[ExcHandler: MalformedURLException (r0v3 'e' java.net.MalformedURLException A[CUSTOM_DECLARE]), Splitter:B:2:0x000f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.internal.zzn zza(com.google.android.gms.internal.zzp<?> r21) throws com.google.android.gms.internal.zzaa {
        /*
            r20 = this;
            r1 = r20
            r2 = r21
            long r3 = android.os.SystemClock.elapsedRealtime()
        L_0x0008:
            java.util.Map r5 = java.util.Collections.emptyMap()
            r6 = 1
            r8 = 0
            r9 = 0
            java.util.HashMap r0 = new java.util.HashMap     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0127 }
            r0.<init>()     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0127 }
            com.google.android.gms.internal.zzc r10 = r21.zze()     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0127 }
            if (r10 == 0) goto L_0x003d
            java.lang.String r11 = r10.zza     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0127 }
            if (r11 == 0) goto L_0x0025
            java.lang.String r11 = "If-None-Match"
            java.lang.String r12 = r10.zza     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0127 }
            r0.put(r11, r12)     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0127 }
        L_0x0025:
            long r11 = r10.zzc     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0127 }
            r13 = 0
            int r15 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r15 <= 0) goto L_0x003d
            java.util.Date r11 = new java.util.Date     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0127 }
            long r12 = r10.zzc     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0127 }
            r11.<init>(r12)     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0127 }
            java.lang.String r10 = "If-Modified-Since"
            java.lang.String r11 = org.apache.http.impl.cookie.DateUtils.formatDate(r11)     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0127 }
            r0.put(r10, r11)     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0127 }
        L_0x003d:
            com.google.android.gms.internal.zzan r10 = r1.zzao     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0127 }
            org.apache.http.HttpResponse r10 = r10.zza(r2, r0)     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0127 }
            org.apache.http.StatusLine r0 = r10.getStatusLine()     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }
            int r12 = r0.getStatusCode()     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }
            org.apache.http.Header[] r11 = r10.getAllHeaders()     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }
            java.util.Map r5 = zza((org.apache.http.Header[]) r11)     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }
            r11 = 304(0x130, float:4.26E-43)
            if (r12 != r11) goto L_0x008b
            com.google.android.gms.internal.zzc r0 = r21.zze()     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }
            if (r0 != 0) goto L_0x0071
            com.google.android.gms.internal.zzn r0 = new com.google.android.gms.internal.zzn     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }
            r14 = 304(0x130, float:4.26E-43)
            r15 = 0
            r17 = 1
            long r11 = android.os.SystemClock.elapsedRealtime()     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }
            long r18 = r11 - r3
            r13 = r0
            r16 = r5
            r13.<init>(r14, r15, r16, r17, r18)     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }
            return r0
        L_0x0071:
            java.util.Map<java.lang.String, java.lang.String> r11 = r0.zzf     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }
            r11.putAll(r5)     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }
            com.google.android.gms.internal.zzn r11 = new com.google.android.gms.internal.zzn     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }
            r13 = 304(0x130, float:4.26E-43)
            byte[] r14 = r0.data     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }
            java.util.Map<java.lang.String, java.lang.String> r15 = r0.zzf     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }
            r16 = 1
            long r17 = android.os.SystemClock.elapsedRealtime()     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }
            long r17 = r17 - r3
            r12 = r11
            r12.<init>(r13, r14, r15, r16, r17)     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }
            return r11
        L_0x008b:
            org.apache.http.HttpEntity r11 = r10.getEntity()     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }
            if (r11 == 0) goto L_0x009a
            org.apache.http.HttpEntity r11 = r10.getEntity()     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }
            byte[] r8 = r1.zza((org.apache.http.HttpEntity) r11)     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }
            goto L_0x009c
        L_0x009a:
            byte[] r8 = new byte[r9]     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }
        L_0x009c:
            long r13 = android.os.SystemClock.elapsedRealtime()     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }
            long r13 = r13 - r3
            boolean r11 = DEBUG     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }
            if (r11 != 0) goto L_0x00ba
            int r11 = zzam     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x00b2 }
            r19 = r8
            long r7 = (long) r11
            int r11 = (r13 > r7 ? 1 : (r13 == r7 ? 0 : -1))
            if (r11 <= 0) goto L_0x00af
            goto L_0x00bc
        L_0x00af:
            r15 = r19
            goto L_0x00fb
        L_0x00b2:
            r0 = move-exception
            r19 = r8
            r14 = r5
            r13 = r19
            goto L_0x012b
        L_0x00ba:
            r19 = r8
        L_0x00bc:
            java.lang.String r7 = "HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]"
            r8 = 5
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x011f }
            r8[r9] = r2     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x011f }
            java.lang.Long r11 = java.lang.Long.valueOf(r13)     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x011f }
            r8[r6] = r11     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x011f }
            if (r19 == 0) goto L_0x00d7
            r15 = r19
            int r11 = r15.length     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x00d3 }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x00d3 }
            goto L_0x00db
        L_0x00d3:
            r0 = move-exception
            r14 = r5
            r13 = r15
            goto L_0x012b
        L_0x00d7:
            r15 = r19
            java.lang.String r11 = "null"
        L_0x00db:
            r13 = 2
            r8[r13] = r11     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0115 }
            r11 = 3
            int r0 = r0.getStatusCode()     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0115 }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0115 }
            r8[r11] = r0     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0115 }
            r0 = 4
            com.google.android.gms.internal.zzx r11 = r21.zzj()     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0115 }
            int r11 = r11.zzb()     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0115 }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0115 }
            r8[r0] = r11     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0115 }
            com.google.android.gms.internal.zzab.zzb(r7, r8)     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0115 }
        L_0x00fb:
            r0 = 200(0xc8, float:2.8E-43)
            if (r12 < r0) goto L_0x0118
            r0 = 299(0x12b, float:4.19E-43)
            if (r12 > r0) goto L_0x0118
            com.google.android.gms.internal.zzn r0 = new com.google.android.gms.internal.zzn     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0115 }
            r7 = 0
            long r13 = android.os.SystemClock.elapsedRealtime()     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0115 }
            long r16 = r13 - r3
            r11 = r0
            r13 = r15
            r14 = r5
            r8 = r15
            r15 = r7
            r11.<init>(r12, r13, r14, r15, r16)     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }
            return r0
        L_0x0115:
            r0 = move-exception
            r8 = r15
            goto L_0x0124
        L_0x0118:
            r8 = r15
            java.io.IOException r0 = new java.io.IOException     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }
            r0.<init>()     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }
            throw r0     // Catch:{ SocketTimeoutException -> 0x01c8, ConnectTimeoutException -> 0x01c0, MalformedURLException -> 0x019f, IOException -> 0x0123 }
        L_0x011f:
            r0 = move-exception
            r8 = r19
            goto L_0x0124
        L_0x0123:
            r0 = move-exception
        L_0x0124:
            r14 = r5
            r13 = r8
            goto L_0x012b
        L_0x0127:
            r0 = move-exception
            r14 = r5
            r10 = r8
            r13 = r10
        L_0x012b:
            if (r10 == 0) goto L_0x0199
            org.apache.http.StatusLine r0 = r10.getStatusLine()
            int r0 = r0.getStatusCode()
            java.lang.String r5 = "Unexpected response code %d for %s"
            r7 = 2
            java.lang.Object[] r7 = new java.lang.Object[r7]
            java.lang.Integer r8 = java.lang.Integer.valueOf(r0)
            r7[r9] = r8
            java.lang.String r8 = r21.getUrl()
            r7[r6] = r8
            com.google.android.gms.internal.zzab.zzc(r5, r7)
            if (r13 == 0) goto L_0x0191
            com.google.android.gms.internal.zzn r5 = new com.google.android.gms.internal.zzn
            r15 = 0
            long r6 = android.os.SystemClock.elapsedRealtime()
            long r16 = r6 - r3
            r11 = r5
            r12 = r0
            r11.<init>(r12, r13, r14, r15, r16)
            r6 = 401(0x191, float:5.62E-43)
            if (r0 == r6) goto L_0x0185
            r6 = 403(0x193, float:5.65E-43)
            if (r0 != r6) goto L_0x0162
            goto L_0x0185
        L_0x0162:
            r2 = 400(0x190, float:5.6E-43)
            if (r0 < r2) goto L_0x0171
            r2 = 499(0x1f3, float:6.99E-43)
            if (r0 <= r2) goto L_0x016b
            goto L_0x0171
        L_0x016b:
            com.google.android.gms.internal.zzf r0 = new com.google.android.gms.internal.zzf
            r0.<init>(r5)
            throw r0
        L_0x0171:
            r2 = 500(0x1f4, float:7.0E-43)
            if (r0 < r2) goto L_0x017f
            r2 = 599(0x257, float:8.4E-43)
            if (r0 > r2) goto L_0x017f
            com.google.android.gms.internal.zzy r0 = new com.google.android.gms.internal.zzy
            r0.<init>(r5)
            throw r0
        L_0x017f:
            com.google.android.gms.internal.zzy r0 = new com.google.android.gms.internal.zzy
            r0.<init>(r5)
            throw r0
        L_0x0185:
            java.lang.String r0 = "auth"
            com.google.android.gms.internal.zza r6 = new com.google.android.gms.internal.zza
            r6.<init>(r5)
            zza(r0, r2, r6)
            goto L_0x0008
        L_0x0191:
            java.lang.String r0 = "network"
            com.google.android.gms.internal.zzm r5 = new com.google.android.gms.internal.zzm
            r5.<init>()
            goto L_0x01cf
        L_0x0199:
            com.google.android.gms.internal.zzo r2 = new com.google.android.gms.internal.zzo
            r2.<init>(r0)
            throw r2
        L_0x019f:
            r0 = move-exception
            java.lang.RuntimeException r3 = new java.lang.RuntimeException
            java.lang.String r4 = "Bad URL "
            java.lang.String r2 = r21.getUrl()
            java.lang.String r2 = java.lang.String.valueOf(r2)
            int r5 = r2.length()
            if (r5 == 0) goto L_0x01b7
            java.lang.String r2 = r4.concat(r2)
            goto L_0x01bc
        L_0x01b7:
            java.lang.String r2 = new java.lang.String
            r2.<init>(r4)
        L_0x01bc:
            r3.<init>(r2, r0)
            throw r3
        L_0x01c0:
            java.lang.String r0 = "connection"
            com.google.android.gms.internal.zzz r5 = new com.google.android.gms.internal.zzz
            r5.<init>()
            goto L_0x01cf
        L_0x01c8:
            java.lang.String r0 = "socket"
            com.google.android.gms.internal.zzz r5 = new com.google.android.gms.internal.zzz
            r5.<init>()
        L_0x01cf:
            zza(r0, r2, r5)
            goto L_0x0008
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzad.zza(com.google.android.gms.internal.zzp):com.google.android.gms.internal.zzn");
    }
}
