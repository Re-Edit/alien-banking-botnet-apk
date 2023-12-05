package com.google.android.gms.common.util;

public final class zzn {
    public static String zza(byte[] bArr, int i, int i2, boolean z) {
        String str;
        Object[] objArr;
        String str2;
        if (bArr == null || bArr.length == 0 || i2 <= 0 || i2 > bArr.length) {
            return null;
        }
        StringBuilder sb = new StringBuilder((((i2 + 16) - 1) / 16) * 57);
        int i3 = i2;
        int i4 = 0;
        int i5 = 0;
        while (i3 > 0) {
            if (i4 == 0) {
                if (i2 < 65536) {
                    str2 = "%04X:";
                    objArr = new Object[]{Integer.valueOf(i5)};
                } else {
                    str2 = "%08X:";
                    objArr = new Object[]{Integer.valueOf(i5)};
                }
                str = String.format(str2, objArr);
            } else {
                if (i4 == 8) {
                    str = " -";
                }
                sb.append(String.format(" %02X", new Object[]{Integer.valueOf(bArr[i5] & 255)}));
                i3--;
                i4++;
                if (i4 != 16 || i3 == 0) {
                    sb.append(10);
                    i4 = 0;
                }
                i5++;
            }
            sb.append(str);
            sb.append(String.format(" %02X", new Object[]{Integer.valueOf(bArr[i5] & 255)}));
            i3--;
            i4++;
            if (i4 != 16) {
            }
            sb.append(10);
            i4 = 0;
            i5++;
        }
        return sb.toString();
    }
}
