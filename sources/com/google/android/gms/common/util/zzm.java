package com.google.android.gms.common.util;

public final class zzm {
    private static final char[] zzaJT = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] zzaJU = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String zzb(byte[] bArr, boolean z) {
        int length = bArr.length;
        StringBuilder sb = new StringBuilder(length << 1);
        for (int i = 0; i < length; i++) {
            sb.append(zzaJT[(bArr[i] & 240) >>> 4]);
            sb.append(zzaJT[bArr[i] & 15]);
        }
        return sb.toString();
    }
}
