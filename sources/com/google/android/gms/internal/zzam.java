package com.google.android.gms.internal;

import java.util.Map;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

public final class zzam {
    public static String zza(Map<String, String> map) {
        String str = map.get("Content-Type");
        if (str != null) {
            String[] split = str.split(";");
            for (int i = 1; i < split.length; i++) {
                String[] split2 = split[i].trim().split("=");
                if (split2.length == 2 && split2[0].equals("charset")) {
                    return split2[1];
                }
            }
        }
        return "ISO-8859-1";
    }

    public static zzc zzb(zzn zzn) {
        boolean z;
        long j;
        long j2;
        long j3;
        long j4;
        zzn zzn2 = zzn;
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = zzn2.zzy;
        String str = map.get("Date");
        long zzf = str != null ? zzf(str) : 0;
        String str2 = map.get("Cache-Control");
        int i = 0;
        if (str2 != null) {
            String[] split = str2.split(",");
            j2 = 0;
            int i2 = 0;
            j = 0;
            while (i < split.length) {
                String trim = split[i].trim();
                if (trim.equals("no-cache") || trim.equals("no-store")) {
                    return null;
                }
                if (trim.startsWith("max-age=")) {
                    try {
                        j2 = Long.parseLong(trim.substring(8));
                    } catch (Exception unused) {
                    }
                } else if (trim.startsWith("stale-while-revalidate=")) {
                    j = Long.parseLong(trim.substring(23));
                } else if (trim.equals("must-revalidate") || trim.equals("proxy-revalidate")) {
                    i2 = 1;
                }
                i++;
            }
            i = i2;
            z = true;
        } else {
            j2 = 0;
            j = 0;
            z = false;
        }
        String str3 = map.get("Expires");
        long zzf2 = str3 != null ? zzf(str3) : 0;
        String str4 = map.get("Last-Modified");
        long zzf3 = str4 != null ? zzf(str4) : 0;
        String str5 = map.get("ETag");
        if (z) {
            j4 = currentTimeMillis + (j2 * 1000);
            if (i == 0) {
                Long.signum(j);
                j3 = (j * 1000) + j4;
                zzc zzc = new zzc();
                zzc.data = zzn2.data;
                zzc.zza = str5;
                zzc.zze = j4;
                zzc.zzd = j3;
                zzc.zzb = zzf;
                zzc.zzc = zzf3;
                zzc.zzf = map;
                return zzc;
            }
        } else if (zzf <= 0 || zzf2 < zzf) {
            j4 = 0;
        } else {
            j3 = (zzf2 - zzf) + currentTimeMillis;
            j4 = j3;
            zzc zzc2 = new zzc();
            zzc2.data = zzn2.data;
            zzc2.zza = str5;
            zzc2.zze = j4;
            zzc2.zzd = j3;
            zzc2.zzb = zzf;
            zzc2.zzc = zzf3;
            zzc2.zzf = map;
            return zzc2;
        }
        j3 = j4;
        zzc zzc22 = new zzc();
        zzc22.data = zzn2.data;
        zzc22.zza = str5;
        zzc22.zze = j4;
        zzc22.zzd = j3;
        zzc22.zzb = zzf;
        zzc22.zzc = zzf3;
        zzc22.zzf = map;
        return zzc22;
    }

    private static long zzf(String str) {
        try {
            return DateUtils.parseDate(str).getTime();
        } catch (DateParseException unused) {
            return 0;
        }
    }
}
