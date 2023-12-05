package com.google.android.gms.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

final class zzai {
    public String key;
    public String zza;
    public long zzaz;
    public long zzb;
    public long zzc;
    public long zzd;
    public long zze;
    public Map<String, String> zzf;

    private zzai() {
    }

    public zzai(String str, zzc zzc2) {
        this.key = str;
        this.zzaz = (long) zzc2.data.length;
        this.zza = zzc2.zza;
        this.zzb = zzc2.zzb;
        this.zzc = zzc2.zzc;
        this.zzd = zzc2.zzd;
        this.zze = zzc2.zze;
        this.zzf = zzc2.zzf;
    }

    public static zzai zzf(InputStream inputStream) throws IOException {
        zzai zzai = new zzai();
        if (zzag.zzb(inputStream) == 538247942) {
            zzai.key = zzag.zzd(inputStream);
            zzai.zza = zzag.zzd(inputStream);
            if (zzai.zza.equals("")) {
                zzai.zza = null;
            }
            zzai.zzb = zzag.zzc(inputStream);
            zzai.zzc = zzag.zzc(inputStream);
            zzai.zzd = zzag.zzc(inputStream);
            zzai.zze = zzag.zzc(inputStream);
            zzai.zzf = zzag.zze(inputStream);
            return zzai;
        }
        throw new IOException();
    }

    public final boolean zza(OutputStream outputStream) {
        try {
            zzag.zza(outputStream, 538247942);
            zzag.zza(outputStream, this.key);
            zzag.zza(outputStream, this.zza == null ? "" : this.zza);
            zzag.zza(outputStream, this.zzb);
            zzag.zza(outputStream, this.zzc);
            zzag.zza(outputStream, this.zzd);
            zzag.zza(outputStream, this.zze);
            Map<String, String> map = this.zzf;
            if (map != null) {
                zzag.zza(outputStream, map.size());
                for (Map.Entry next : map.entrySet()) {
                    zzag.zza(outputStream, (String) next.getKey());
                    zzag.zza(outputStream, (String) next.getValue());
                }
            } else {
                zzag.zza(outputStream, 0);
            }
            outputStream.flush();
            return true;
        } catch (IOException e) {
            zzab.zzb("%s", e.toString());
            return false;
        }
    }
}
