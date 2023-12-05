package com.google.android.gms.internal;

import java.util.Arrays;

final class aii {
    final int tag;
    final byte[] zzbww;

    aii(int i, byte[] bArr) {
        this.tag = i;
        this.zzbww = bArr;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof aii)) {
            return false;
        }
        aii aii = (aii) obj;
        return this.tag == aii.tag && Arrays.equals(this.zzbww, aii.zzbww);
    }

    public final int hashCode() {
        return ((this.tag + 527) * 31) + Arrays.hashCode(this.zzbww);
    }
}
