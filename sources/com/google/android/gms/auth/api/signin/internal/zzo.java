package com.google.android.gms.auth.api.signin.internal;

public final class zzo {
    private static int zzamu = 31;
    private int zzamv = 1;

    public final zzo zzP(boolean z) {
        this.zzamv = (zzamu * this.zzamv) + (z ? 1 : 0);
        return this;
    }

    public final int zzmH() {
        return this.zzamv;
    }

    public final zzo zzo(Object obj) {
        this.zzamv = (zzamu * this.zzamv) + (obj == null ? 0 : obj.hashCode());
        return this;
    }
}
