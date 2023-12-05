package com.google.android.gms.internal;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public final class zzag implements zzb {
    private final Map<String, zzai> zzav;
    private long zzaw;
    private final File zzax;
    private final int zzay;

    public zzag(File file) {
        this(file, 5242880);
    }

    private zzag(File file, int i) {
        this.zzav = new LinkedHashMap(16, 0.75f, true);
        this.zzaw = 0;
        this.zzax = file;
        this.zzay = 5242880;
    }

    private final synchronized void remove(String str) {
        boolean delete = zze(str).delete();
        zzai zzai = this.zzav.get(str);
        if (zzai != null) {
            this.zzaw -= zzai.zzaz;
            this.zzav.remove(str);
        }
        if (!delete) {
            zzab.zzb("Could not delete cache entry for key=%s, filename=%s", str, zzd(str));
        }
    }

    private static int zza(InputStream inputStream) throws IOException {
        int read = inputStream.read();
        if (read != -1) {
            return read;
        }
        throw new EOFException();
    }

    static void zza(OutputStream outputStream, int i) throws IOException {
        outputStream.write(i & 255);
        outputStream.write((i >> 8) & 255);
        outputStream.write((i >> 16) & 255);
        outputStream.write(i >>> 24);
    }

    static void zza(OutputStream outputStream, long j) throws IOException {
        outputStream.write((byte) ((int) j));
        outputStream.write((byte) ((int) (j >>> 8)));
        outputStream.write((byte) ((int) (j >>> 16)));
        outputStream.write((byte) ((int) (j >>> 24)));
        outputStream.write((byte) ((int) (j >>> 32)));
        outputStream.write((byte) ((int) (j >>> 40)));
        outputStream.write((byte) ((int) (j >>> 48)));
        outputStream.write((byte) ((int) (j >>> 56)));
    }

    static void zza(OutputStream outputStream, String str) throws IOException {
        byte[] bytes = str.getBytes("UTF-8");
        zza(outputStream, (long) bytes.length);
        outputStream.write(bytes, 0, bytes.length);
    }

    private final void zza(String str, zzai zzai) {
        if (!this.zzav.containsKey(str)) {
            this.zzaw += zzai.zzaz;
        } else {
            this.zzaw += zzai.zzaz - this.zzav.get(str).zzaz;
        }
        this.zzav.put(str, zzai);
    }

    private static byte[] zza(InputStream inputStream, int i) throws IOException {
        byte[] bArr = new byte[i];
        int i2 = 0;
        while (i2 < i) {
            int read = inputStream.read(bArr, i2, i - i2);
            if (read == -1) {
                break;
            }
            i2 += read;
        }
        if (i2 == i) {
            return bArr;
        }
        StringBuilder sb = new StringBuilder(50);
        sb.append("Expected ");
        sb.append(i);
        sb.append(" bytes, read ");
        sb.append(i2);
        sb.append(" bytes");
        throw new IOException(sb.toString());
    }

    static int zzb(InputStream inputStream) throws IOException {
        return (zza(inputStream) << 24) | zza(inputStream) | 0 | (zza(inputStream) << 8) | (zza(inputStream) << 16);
    }

    static long zzc(InputStream inputStream) throws IOException {
        return (((long) zza(inputStream)) & 255) | 0 | ((((long) zza(inputStream)) & 255) << 8) | ((((long) zza(inputStream)) & 255) << 16) | ((((long) zza(inputStream)) & 255) << 24) | ((((long) zza(inputStream)) & 255) << 32) | ((((long) zza(inputStream)) & 255) << 40) | ((((long) zza(inputStream)) & 255) << 48) | ((255 & ((long) zza(inputStream))) << 56);
    }

    static String zzd(InputStream inputStream) throws IOException {
        return new String(zza(inputStream, (int) zzc(inputStream)), "UTF-8");
    }

    private static String zzd(String str) {
        int length = str.length() / 2;
        String valueOf = String.valueOf(String.valueOf(str.substring(0, length).hashCode()));
        String valueOf2 = String.valueOf(String.valueOf(str.substring(length).hashCode()));
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    private final File zze(String str) {
        return new File(this.zzax, zzd(str));
    }

    static Map<String, String> zze(InputStream inputStream) throws IOException {
        int zzb = zzb(inputStream);
        Map<String, String> emptyMap = zzb == 0 ? Collections.emptyMap() : new HashMap<>(zzb);
        for (int i = 0; i < zzb; i++) {
            emptyMap.put(zzd(inputStream).intern(), zzd(inputStream).intern());
        }
        return emptyMap;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:25|26|(2:35|36)|37|38) */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0023, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:37:0x0063 */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x005a A[SYNTHETIC, Splitter:B:32:0x005a] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0066 A[SYNTHETIC, Splitter:B:40:0x0066] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0069 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void initialize() {
        /*
            r8 = this;
            monitor-enter(r8)
            java.io.File r0 = r8.zzax     // Catch:{ all -> 0x006e }
            boolean r0 = r0.exists()     // Catch:{ all -> 0x006e }
            r1 = 0
            if (r0 != 0) goto L_0x0024
            java.io.File r0 = r8.zzax     // Catch:{ all -> 0x006e }
            boolean r0 = r0.mkdirs()     // Catch:{ all -> 0x006e }
            if (r0 != 0) goto L_0x0022
            java.lang.String r0 = "Unable to create cache dir %s"
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x006e }
            java.io.File r3 = r8.zzax     // Catch:{ all -> 0x006e }
            java.lang.String r3 = r3.getAbsolutePath()     // Catch:{ all -> 0x006e }
            r2[r1] = r3     // Catch:{ all -> 0x006e }
            com.google.android.gms.internal.zzab.zzc(r0, r2)     // Catch:{ all -> 0x006e }
        L_0x0022:
            monitor-exit(r8)
            return
        L_0x0024:
            java.io.File r0 = r8.zzax     // Catch:{ all -> 0x006e }
            java.io.File[] r0 = r0.listFiles()     // Catch:{ all -> 0x006e }
            if (r0 != 0) goto L_0x002e
            monitor-exit(r8)
            return
        L_0x002e:
            int r2 = r0.length     // Catch:{ all -> 0x006e }
        L_0x002f:
            if (r1 >= r2) goto L_0x006c
            r3 = r0[r1]     // Catch:{ all -> 0x006e }
            r4 = 0
            java.io.BufferedInputStream r5 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x0058 }
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0058 }
            r6.<init>(r3)     // Catch:{ IOException -> 0x0058 }
            r5.<init>(r6)     // Catch:{ IOException -> 0x0058 }
            com.google.android.gms.internal.zzai r4 = com.google.android.gms.internal.zzai.zzf(r5)     // Catch:{ IOException -> 0x0054, all -> 0x0051 }
            long r6 = r3.length()     // Catch:{ IOException -> 0x0054, all -> 0x0051 }
            r4.zzaz = r6     // Catch:{ IOException -> 0x0054, all -> 0x0051 }
            java.lang.String r6 = r4.key     // Catch:{ IOException -> 0x0054, all -> 0x0051 }
            r8.zza((java.lang.String) r6, (com.google.android.gms.internal.zzai) r4)     // Catch:{ IOException -> 0x0054, all -> 0x0051 }
            r5.close()     // Catch:{ IOException -> 0x0069 }
            goto L_0x0069
        L_0x0051:
            r0 = move-exception
            r4 = r5
            goto L_0x005e
        L_0x0054:
            r4 = r5
            goto L_0x0058
        L_0x0056:
            r0 = move-exception
            goto L_0x005e
        L_0x0058:
            if (r3 == 0) goto L_0x0064
            r3.delete()     // Catch:{ all -> 0x0056 }
            goto L_0x0064
        L_0x005e:
            if (r4 == 0) goto L_0x0063
            r4.close()     // Catch:{ IOException -> 0x0063 }
        L_0x0063:
            throw r0     // Catch:{ all -> 0x006e }
        L_0x0064:
            if (r4 == 0) goto L_0x0069
            r4.close()     // Catch:{ IOException -> 0x0069 }
        L_0x0069:
            int r1 = r1 + 1
            goto L_0x002f
        L_0x006c:
            monitor-exit(r8)
            return
        L_0x006e:
            r0 = move-exception
            monitor-exit(r8)
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzag.initialize():void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x007d A[SYNTHETIC, Splitter:B:31:0x007d] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x009f A[SYNTHETIC, Splitter:B:44:0x009f] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00aa A[SYNTHETIC, Splitter:B:53:0x00aa] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized com.google.android.gms.internal.zzc zza(java.lang.String r12) {
        /*
            r11 = this;
            monitor-enter(r11)
            java.util.Map<java.lang.String, com.google.android.gms.internal.zzai> r0 = r11.zzav     // Catch:{ all -> 0x00b1 }
            java.lang.Object r0 = r0.get(r12)     // Catch:{ all -> 0x00b1 }
            com.google.android.gms.internal.zzai r0 = (com.google.android.gms.internal.zzai) r0     // Catch:{ all -> 0x00b1 }
            r1 = 0
            if (r0 != 0) goto L_0x000e
            monitor-exit(r11)
            return r1
        L_0x000e:
            java.io.File r2 = r11.zze((java.lang.String) r12)     // Catch:{ all -> 0x00b1 }
            r3 = 1
            r4 = 0
            r5 = 2
            com.google.android.gms.internal.zzaj r6 = new com.google.android.gms.internal.zzaj     // Catch:{ IOException -> 0x0085, NegativeArraySizeException -> 0x0063, all -> 0x0060 }
            java.io.BufferedInputStream r7 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x0085, NegativeArraySizeException -> 0x0063, all -> 0x0060 }
            java.io.FileInputStream r8 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0085, NegativeArraySizeException -> 0x0063, all -> 0x0060 }
            r8.<init>(r2)     // Catch:{ IOException -> 0x0085, NegativeArraySizeException -> 0x0063, all -> 0x0060 }
            r7.<init>(r8)     // Catch:{ IOException -> 0x0085, NegativeArraySizeException -> 0x0063, all -> 0x0060 }
            r6.<init>(r7)     // Catch:{ IOException -> 0x0085, NegativeArraySizeException -> 0x0063, all -> 0x0060 }
            com.google.android.gms.internal.zzai.zzf(r6)     // Catch:{ IOException -> 0x005e, NegativeArraySizeException -> 0x005c }
            long r7 = r2.length()     // Catch:{ IOException -> 0x005e, NegativeArraySizeException -> 0x005c }
            int r9 = r6.zzaA     // Catch:{ IOException -> 0x005e, NegativeArraySizeException -> 0x005c }
            long r9 = (long) r9     // Catch:{ IOException -> 0x005e, NegativeArraySizeException -> 0x005c }
            long r7 = r7 - r9
            int r8 = (int) r7     // Catch:{ IOException -> 0x005e, NegativeArraySizeException -> 0x005c }
            byte[] r7 = zza((java.io.InputStream) r6, (int) r8)     // Catch:{ IOException -> 0x005e, NegativeArraySizeException -> 0x005c }
            com.google.android.gms.internal.zzc r8 = new com.google.android.gms.internal.zzc     // Catch:{ IOException -> 0x005e, NegativeArraySizeException -> 0x005c }
            r8.<init>()     // Catch:{ IOException -> 0x005e, NegativeArraySizeException -> 0x005c }
            r8.data = r7     // Catch:{ IOException -> 0x005e, NegativeArraySizeException -> 0x005c }
            java.lang.String r7 = r0.zza     // Catch:{ IOException -> 0x005e, NegativeArraySizeException -> 0x005c }
            r8.zza = r7     // Catch:{ IOException -> 0x005e, NegativeArraySizeException -> 0x005c }
            long r9 = r0.zzb     // Catch:{ IOException -> 0x005e, NegativeArraySizeException -> 0x005c }
            r8.zzb = r9     // Catch:{ IOException -> 0x005e, NegativeArraySizeException -> 0x005c }
            long r9 = r0.zzc     // Catch:{ IOException -> 0x005e, NegativeArraySizeException -> 0x005c }
            r8.zzc = r9     // Catch:{ IOException -> 0x005e, NegativeArraySizeException -> 0x005c }
            long r9 = r0.zzd     // Catch:{ IOException -> 0x005e, NegativeArraySizeException -> 0x005c }
            r8.zzd = r9     // Catch:{ IOException -> 0x005e, NegativeArraySizeException -> 0x005c }
            long r9 = r0.zze     // Catch:{ IOException -> 0x005e, NegativeArraySizeException -> 0x005c }
            r8.zze = r9     // Catch:{ IOException -> 0x005e, NegativeArraySizeException -> 0x005c }
            java.util.Map<java.lang.String, java.lang.String> r0 = r0.zzf     // Catch:{ IOException -> 0x005e, NegativeArraySizeException -> 0x005c }
            r8.zzf = r0     // Catch:{ IOException -> 0x005e, NegativeArraySizeException -> 0x005c }
            r6.close()     // Catch:{ IOException -> 0x005a }
            monitor-exit(r11)
            return r8
        L_0x005a:
            monitor-exit(r11)
            return r1
        L_0x005c:
            r0 = move-exception
            goto L_0x0065
        L_0x005e:
            r0 = move-exception
            goto L_0x0087
        L_0x0060:
            r12 = move-exception
            r6 = r1
            goto L_0x00a8
        L_0x0063:
            r0 = move-exception
            r6 = r1
        L_0x0065:
            java.lang.String r7 = "%s: %s"
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x00a7 }
            java.lang.String r2 = r2.getAbsolutePath()     // Catch:{ all -> 0x00a7 }
            r5[r4] = r2     // Catch:{ all -> 0x00a7 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00a7 }
            r5[r3] = r0     // Catch:{ all -> 0x00a7 }
            com.google.android.gms.internal.zzab.zzb(r7, r5)     // Catch:{ all -> 0x00a7 }
            r11.remove(r12)     // Catch:{ all -> 0x00a7 }
            if (r6 == 0) goto L_0x0083
            r6.close()     // Catch:{ IOException -> 0x0081 }
            goto L_0x0083
        L_0x0081:
            monitor-exit(r11)
            return r1
        L_0x0083:
            monitor-exit(r11)
            return r1
        L_0x0085:
            r0 = move-exception
            r6 = r1
        L_0x0087:
            java.lang.String r7 = "%s: %s"
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x00a7 }
            java.lang.String r2 = r2.getAbsolutePath()     // Catch:{ all -> 0x00a7 }
            r5[r4] = r2     // Catch:{ all -> 0x00a7 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00a7 }
            r5[r3] = r0     // Catch:{ all -> 0x00a7 }
            com.google.android.gms.internal.zzab.zzb(r7, r5)     // Catch:{ all -> 0x00a7 }
            r11.remove(r12)     // Catch:{ all -> 0x00a7 }
            if (r6 == 0) goto L_0x00a5
            r6.close()     // Catch:{ IOException -> 0x00a3 }
            goto L_0x00a5
        L_0x00a3:
            monitor-exit(r11)
            return r1
        L_0x00a5:
            monitor-exit(r11)
            return r1
        L_0x00a7:
            r12 = move-exception
        L_0x00a8:
            if (r6 == 0) goto L_0x00b0
            r6.close()     // Catch:{ IOException -> 0x00ae }
            goto L_0x00b0
        L_0x00ae:
            monitor-exit(r11)
            return r1
        L_0x00b0:
            throw r12     // Catch:{ all -> 0x00b1 }
        L_0x00b1:
            r12 = move-exception
            monitor-exit(r11)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzag.zza(java.lang.String):com.google.android.gms.internal.zzc");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:33|34|(1:36)|37|38) */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00ff, code lost:
        if (r3.delete() == false) goto L_0x0101;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0101, code lost:
        com.google.android.gms.internal.zzab.zzb("Could not clean up file %s", r3.getAbsolutePath());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0111, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:33:0x00fb */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void zza(java.lang.String r20, com.google.android.gms.internal.zzc r21) {
        /*
            r19 = this;
            r1 = r19
            r0 = r20
            r2 = r21
            monitor-enter(r19)
            byte[] r3 = r2.data     // Catch:{ all -> 0x0112 }
            int r3 = r3.length     // Catch:{ all -> 0x0112 }
            long r4 = r1.zzaw     // Catch:{ all -> 0x0112 }
            long r6 = (long) r3     // Catch:{ all -> 0x0112 }
            long r4 = r4 + r6
            int r3 = r1.zzay     // Catch:{ all -> 0x0112 }
            long r8 = (long) r3     // Catch:{ all -> 0x0112 }
            r10 = 0
            int r11 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r11 < 0) goto L_0x00bd
            boolean r4 = com.google.android.gms.internal.zzab.DEBUG     // Catch:{ all -> 0x0112 }
            if (r4 == 0) goto L_0x0021
            java.lang.String r4 = "Pruning old cache entries."
            java.lang.Object[] r5 = new java.lang.Object[r10]     // Catch:{ all -> 0x0112 }
            com.google.android.gms.internal.zzab.zza(r4, r5)     // Catch:{ all -> 0x0112 }
        L_0x0021:
            long r4 = r1.zzaw     // Catch:{ all -> 0x0112 }
            long r8 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x0112 }
            java.util.Map<java.lang.String, com.google.android.gms.internal.zzai> r11 = r1.zzav     // Catch:{ all -> 0x0112 }
            java.util.Set r11 = r11.entrySet()     // Catch:{ all -> 0x0112 }
            java.util.Iterator r11 = r11.iterator()     // Catch:{ all -> 0x0112 }
            r12 = 0
        L_0x0032:
            boolean r13 = r11.hasNext()     // Catch:{ all -> 0x0112 }
            r14 = 2
            if (r13 == 0) goto L_0x0092
            java.lang.Object r13 = r11.next()     // Catch:{ all -> 0x0112 }
            java.util.Map$Entry r13 = (java.util.Map.Entry) r13     // Catch:{ all -> 0x0112 }
            java.lang.Object r13 = r13.getValue()     // Catch:{ all -> 0x0112 }
            com.google.android.gms.internal.zzai r13 = (com.google.android.gms.internal.zzai) r13     // Catch:{ all -> 0x0112 }
            java.lang.String r15 = r13.key     // Catch:{ all -> 0x0112 }
            java.io.File r15 = r1.zze((java.lang.String) r15)     // Catch:{ all -> 0x0112 }
            boolean r15 = r15.delete()     // Catch:{ all -> 0x0112 }
            if (r15 == 0) goto L_0x005d
            r16 = r4
            long r3 = r1.zzaw     // Catch:{ all -> 0x0112 }
            r18 = r11
            long r10 = r13.zzaz     // Catch:{ all -> 0x0112 }
            long r3 = r3 - r10
            r1.zzaw = r3     // Catch:{ all -> 0x0112 }
            goto L_0x0076
        L_0x005d:
            r16 = r4
            r18 = r11
            java.lang.String r3 = "Could not delete cache entry for key=%s, filename=%s"
            java.lang.Object[] r4 = new java.lang.Object[r14]     // Catch:{ all -> 0x0112 }
            java.lang.String r10 = r13.key     // Catch:{ all -> 0x0112 }
            r5 = 0
            r4[r5] = r10     // Catch:{ all -> 0x0112 }
            java.lang.String r10 = r13.key     // Catch:{ all -> 0x0112 }
            java.lang.String r10 = zzd((java.lang.String) r10)     // Catch:{ all -> 0x0112 }
            r11 = 1
            r4[r11] = r10     // Catch:{ all -> 0x0112 }
            com.google.android.gms.internal.zzab.zzb(r3, r4)     // Catch:{ all -> 0x0112 }
        L_0x0076:
            r18.remove()     // Catch:{ all -> 0x0112 }
            int r12 = r12 + 1
            long r3 = r1.zzaw     // Catch:{ all -> 0x0112 }
            long r3 = r3 + r6
            float r3 = (float) r3     // Catch:{ all -> 0x0112 }
            int r4 = r1.zzay     // Catch:{ all -> 0x0112 }
            float r4 = (float) r4     // Catch:{ all -> 0x0112 }
            r10 = 1063675494(0x3f666666, float:0.9)
            float r4 = r4 * r10
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 >= 0) goto L_0x008c
            goto L_0x0094
        L_0x008c:
            r4 = r16
            r11 = r18
            r10 = 0
            goto L_0x0032
        L_0x0092:
            r16 = r4
        L_0x0094:
            boolean r3 = com.google.android.gms.internal.zzab.DEBUG     // Catch:{ all -> 0x0112 }
            if (r3 == 0) goto L_0x00bd
            java.lang.String r3 = "pruned %d files, %d bytes, %d ms"
            r4 = 3
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x0112 }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r12)     // Catch:{ all -> 0x0112 }
            r5 = 0
            r4[r5] = r6     // Catch:{ all -> 0x0112 }
            long r6 = r1.zzaw     // Catch:{ all -> 0x0112 }
            long r6 = r6 - r16
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x0112 }
            r7 = 1
            r4[r7] = r6     // Catch:{ all -> 0x0112 }
            long r6 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x0112 }
            long r6 = r6 - r8
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x0112 }
            r4[r14] = r6     // Catch:{ all -> 0x0112 }
            com.google.android.gms.internal.zzab.zza(r3, r4)     // Catch:{ all -> 0x0112 }
        L_0x00bd:
            java.io.File r3 = r19.zze((java.lang.String) r20)     // Catch:{ all -> 0x0112 }
            java.io.BufferedOutputStream r4 = new java.io.BufferedOutputStream     // Catch:{ IOException -> 0x00fb }
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x00fb }
            r6.<init>(r3)     // Catch:{ IOException -> 0x00fb }
            r4.<init>(r6)     // Catch:{ IOException -> 0x00fb }
            com.google.android.gms.internal.zzai r6 = new com.google.android.gms.internal.zzai     // Catch:{ IOException -> 0x00fb }
            r6.<init>(r0, r2)     // Catch:{ IOException -> 0x00fb }
            boolean r7 = r6.zza(r4)     // Catch:{ IOException -> 0x00fb }
            if (r7 == 0) goto L_0x00e3
            byte[] r2 = r2.data     // Catch:{ IOException -> 0x00fb }
            r4.write(r2)     // Catch:{ IOException -> 0x00fb }
            r4.close()     // Catch:{ IOException -> 0x00fb }
            r1.zza((java.lang.String) r0, (com.google.android.gms.internal.zzai) r6)     // Catch:{ IOException -> 0x00fb }
            monitor-exit(r19)
            return
        L_0x00e3:
            r4.close()     // Catch:{ IOException -> 0x00fb }
            java.lang.String r0 = "Failed to write header for %s"
            r2 = 1
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch:{ IOException -> 0x00fb }
            java.lang.String r2 = r3.getAbsolutePath()     // Catch:{ IOException -> 0x00fb }
            r5 = 0
            r4[r5] = r2     // Catch:{ IOException -> 0x00fb }
            com.google.android.gms.internal.zzab.zzb(r0, r4)     // Catch:{ IOException -> 0x00fb }
            java.io.IOException r0 = new java.io.IOException     // Catch:{ IOException -> 0x00fb }
            r0.<init>()     // Catch:{ IOException -> 0x00fb }
            throw r0     // Catch:{ IOException -> 0x00fb }
        L_0x00fb:
            boolean r0 = r3.delete()     // Catch:{ all -> 0x0112 }
            if (r0 != 0) goto L_0x0110
            java.lang.String r0 = "Could not clean up file %s"
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0112 }
            java.lang.String r3 = r3.getAbsolutePath()     // Catch:{ all -> 0x0112 }
            r4 = 0
            r2[r4] = r3     // Catch:{ all -> 0x0112 }
            com.google.android.gms.internal.zzab.zzb(r0, r2)     // Catch:{ all -> 0x0112 }
        L_0x0110:
            monitor-exit(r19)
            return
        L_0x0112:
            r0 = move-exception
            monitor-exit(r19)
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzag.zza(java.lang.String, com.google.android.gms.internal.zzc):void");
    }
}
