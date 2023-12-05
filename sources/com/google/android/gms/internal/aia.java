package com.google.android.gms.internal;

import com.google.android.gms.internal.ahz;
import java.io.IOException;
import java.util.List;

public final class aia<M extends ahz<M>, T> {
    public final int tag;
    private int type = 11;
    protected final Class<T> zzcmA;
    protected final boolean zzcuX;

    private aia(int i, Class<T> cls, int i2, boolean z) {
        this.zzcmA = cls;
        this.tag = i2;
        this.zzcuX = false;
    }

    public static <M extends ahz<M>, T extends aif> aia<M, T> zza(int i, Class<T> cls, long j) {
        return new aia<>(11, cls, (int) j, false);
    }

    private final Object zzb(ahw ahw) {
        Class<T> cls = this.zzcmA;
        try {
            switch (this.type) {
                case 10:
                    aif aif = (aif) cls.newInstance();
                    ahw.zza(aif, this.tag >>> 3);
                    return aif;
                case 11:
                    aif aif2 = (aif) cls.newInstance();
                    ahw.zzb(aif2);
                    return aif2;
                default:
                    int i = this.type;
                    StringBuilder sb = new StringBuilder(24);
                    sb.append("Unknown type ");
                    sb.append(i);
                    throw new IllegalArgumentException(sb.toString());
            }
        } catch (InstantiationException e) {
            String valueOf = String.valueOf(cls);
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 33);
            sb2.append("Error creating instance of class ");
            sb2.append(valueOf);
            throw new IllegalArgumentException(sb2.toString(), e);
        } catch (IllegalAccessException e2) {
            String valueOf2 = String.valueOf(cls);
            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf2).length() + 33);
            sb3.append("Error creating instance of class ");
            sb3.append(valueOf2);
            throw new IllegalArgumentException(sb3.toString(), e2);
        } catch (IOException e3) {
            throw new IllegalArgumentException("Error reading extension field", e3);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof aia)) {
            return false;
        }
        aia aia = (aia) obj;
        return this.type == aia.type && this.zzcmA == aia.zzcmA && this.tag == aia.tag;
    }

    public final int hashCode() {
        return (((((this.type + 1147) * 31) + this.zzcmA.hashCode()) * 31) + this.tag) * 31;
    }

    /* access modifiers changed from: package-private */
    public final T zzY(List<aii> list) {
        if (list != null && !list.isEmpty()) {
            return this.zzcmA.cast(zzb(ahw.zzI(list.get(list.size() - 1).zzbww)));
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public final void zza(Object obj, ahx ahx) {
        try {
            ahx.zzct(this.tag);
            switch (this.type) {
                case 10:
                    ((aif) obj).zza(ahx);
                    ahx.zzt(this.tag >>> 3, 4);
                    return;
                case 11:
                    ahx.zzc((aif) obj);
                    return;
                default:
                    int i = this.type;
                    StringBuilder sb = new StringBuilder(24);
                    sb.append("Unknown type ");
                    sb.append(i);
                    throw new IllegalArgumentException(sb.toString());
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /* access modifiers changed from: protected */
    public final int zzav(Object obj) {
        int i = this.tag >>> 3;
        int i2 = this.type;
        switch (i2) {
            case 10:
                return (ahx.zzcs(i) << 1) + ((aif) obj).zzMl();
            case 11:
                return ahx.zzb(i, (aif) obj);
            default:
                StringBuilder sb = new StringBuilder(24);
                sb.append("Unknown type ");
                sb.append(i2);
                throw new IllegalArgumentException(sb.toString());
        }
    }
}
