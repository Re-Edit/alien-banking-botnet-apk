package com.google.android.gms.internal;

import java.io.UnsupportedEncodingException;

public class zzar extends zzp<String> {
    private final zzv<String> zzaE;

    public zzar(int i, String str, zzv<String> zzv, zzu zzu) {
        super(i, str, zzu);
        this.zzaE = zzv;
    }

    /* access modifiers changed from: protected */
    public final zzt<String> zza(zzn zzn) {
        String str;
        try {
            str = new String(zzn.data, zzam.zza(zzn.zzy));
        } catch (UnsupportedEncodingException unused) {
            str = new String(zzn.data);
        }
        return zzt.zza(str, zzam.zzb(zzn));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Object obj) {
        this.zzaE.zzb((String) obj);
    }
}
