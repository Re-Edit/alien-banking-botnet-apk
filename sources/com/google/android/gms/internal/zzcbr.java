package com.google.android.gms.internal;

public final class zzcbr {
    private static zzcbr zzaXL;
    private final zzcbm zzaXM = new zzcbm();
    private final zzcbn zzaXN = new zzcbn();

    static {
        zzcbr zzcbr = new zzcbr();
        synchronized (zzcbr.class) {
            zzaXL = zzcbr;
        }
    }

    private zzcbr() {
    }

    private static zzcbr zztZ() {
        zzcbr zzcbr;
        synchronized (zzcbr.class) {
            zzcbr = zzaXL;
        }
        return zzcbr;
    }

    public static zzcbm zzua() {
        return zztZ().zzaXM;
    }

    public static zzcbn zzub() {
        return zztZ().zzaXN;
    }
}
