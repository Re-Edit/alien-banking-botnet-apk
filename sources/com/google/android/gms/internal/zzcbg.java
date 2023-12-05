package com.google.android.gms.internal;

public abstract class zzcbg<T> {
    private final int zzBP;
    private final String zzBQ;
    private final T zzBR;

    private zzcbg(int i, String str, T t) {
        this.zzBP = i;
        this.zzBQ = str;
        this.zzBR = t;
        zzcbr.zzua().zza(this);
    }

    public static zzcbi zzb(int i, String str, Boolean bool) {
        return new zzcbi(0, str, bool);
    }

    public static zzcbj zzb(int i, String str, int i2) {
        return new zzcbj(0, str, Integer.valueOf(i2));
    }

    public static zzcbk zzb(int i, String str, long j) {
        return new zzcbk(0, str, Long.valueOf(j));
    }

    public final String getKey() {
        return this.zzBQ;
    }

    public final int getSource() {
        return this.zzBP;
    }

    /* access modifiers changed from: protected */
    public abstract T zza(zzcbo zzcbo);

    public final T zzdH() {
        return this.zzBR;
    }
}
