package com.google.android.gms.internal;

import java.util.Arrays;

public class zzcub {
    private static final String TAG = "zzcub";
    private static final char[] zzbyY = "0123456789abcdef".toCharArray();
    private final byte[] zzbyZ;

    public zzcub(byte[] bArr) {
        this.zzbyZ = bArr;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        try {
            return Arrays.equals(this.zzbyZ, ((zzcub) obj).zzbyZ);
        } catch (ClassCastException unused) {
            return false;
        }
    }

    public final byte[] getBytes() {
        return this.zzbyZ;
    }

    public int hashCode() {
        return Arrays.hashCode(this.zzbyZ) + 527;
    }

    public final zzcub zzbt(int i) {
        return new zzcub(Arrays.copyOfRange(this.zzbyZ, 0, 4));
    }
}
