package com.google.android.gms.internal;

import java.util.Map;

public final class zzn {
    public final byte[] data;
    private int statusCode;
    private long zzA;
    public final Map<String, String> zzy;
    public final boolean zzz;

    public zzn(int i, byte[] bArr, Map<String, String> map, boolean z, long j) {
        this.statusCode = i;
        this.data = bArr;
        this.zzy = map;
        this.zzz = z;
        this.zzA = j;
    }

    public zzn(byte[] bArr, Map<String, String> map) {
        this(200, bArr, map, false, 0);
    }
}
