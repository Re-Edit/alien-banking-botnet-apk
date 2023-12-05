package com.google.android.gms.internal;

public class zzbgl<T> {
    private static String READ_PERMISSION = "com.google.android.providers.gsf.permission.READ_GSERVICES";
    private static zzbgr zzaFq = null;
    private static int zzaFr = 0;
    private static final Object zzuI = new Object();
    private String zzBQ;
    private T zzBR;
    private T zzaFs = null;

    protected zzbgl(String str, T t) {
        this.zzBQ = str;
        this.zzBR = t;
    }

    public static zzbgl<Float> zza(String str, Float f) {
        return new zzbgp(str, f);
    }

    public static zzbgl<Integer> zza(String str, Integer num) {
        return new zzbgo(str, num);
    }

    public static zzbgl<Long> zza(String str, Long l) {
        return new zzbgn(str, l);
    }

    public static zzbgl<Boolean> zzg(String str, boolean z) {
        return new zzbgm(str, Boolean.valueOf(z));
    }

    public static zzbgl<String> zzv(String str, String str2) {
        return new zzbgq(str, str2);
    }
}
