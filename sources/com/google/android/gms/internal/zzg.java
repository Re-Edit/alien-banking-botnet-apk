package com.google.android.gms.internal;

public final class zzg implements zzx {
    private int zzn;
    private int zzo;
    private final int zzp;
    private final float zzq;

    public zzg() {
        this(2500, 1, 1.0f);
    }

    private zzg(int i, int i2, float f) {
        this.zzn = 2500;
        this.zzp = 1;
        this.zzq = 1.0f;
    }

    public final int zza() {
        return this.zzn;
    }

    public final void zza(zzaa zzaa) throws zzaa {
        boolean z = true;
        this.zzo++;
        int i = this.zzn;
        this.zzn = (int) (((float) i) + (((float) i) * this.zzq));
        if (this.zzo > this.zzp) {
            z = false;
        }
        if (!z) {
            throw zzaa;
        }
    }

    public final int zzb() {
        return this.zzo;
    }
}
