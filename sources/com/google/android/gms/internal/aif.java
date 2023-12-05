package com.google.android.gms.internal;

import java.io.IOException;

public abstract class aif {
    protected volatile int zzcvf = -1;

    public static final <T extends aif> T zza(T t, byte[] bArr) throws aie {
        return zza(t, bArr, 0, bArr.length);
    }

    private static <T extends aif> T zza(T t, byte[] bArr, int i, int i2) throws aie {
        try {
            ahw zzb = ahw.zzb(bArr, 0, i2);
            t.zza(zzb);
            zzb.zzck(0);
            return t;
        } catch (aie e) {
            throw e;
        } catch (IOException unused) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).");
        }
    }

    public static final byte[] zzd(aif aif) {
        byte[] bArr = new byte[aif.zzMl()];
        try {
            ahx zzc = ahx.zzc(bArr, 0, bArr.length);
            aif.zza(zzc);
            zzc.zzMc();
            return bArr;
        } catch (IOException e) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
        }
    }

    public String toString() {
        return aig.zze(this);
    }

    /* renamed from: zzMe */
    public aif clone() throws CloneNotSupportedException {
        return (aif) super.clone();
    }

    public final int zzMk() {
        if (this.zzcvf < 0) {
            zzMl();
        }
        return this.zzcvf;
    }

    public final int zzMl() {
        int zzn = zzn();
        this.zzcvf = zzn;
        return zzn;
    }

    public abstract aif zza(ahw ahw) throws IOException;

    public void zza(ahx ahx) throws IOException {
    }

    /* access modifiers changed from: protected */
    public int zzn() {
        return 0;
    }
}
