package com.google.android.gms.internal;

import com.google.android.gms.common.data.DataHolder;

public class zzcud {
    private static final String TAG = "zzcud";
    private int zzbBO;
    private int[] zzbCm;

    public zzcud(DataHolder dataHolder, int i) {
        int[] zzt;
        if (dataHolder != null && dataHolder.getStatusCode() == 0 && (zzt = zzt(dataHolder.zzg("", i, dataHolder.zzat(i)))) != null && zzt.length >= 3 && zzt[0] == 1 && zzt[1] == 1936614772) {
            this.zzbCm = new int[zzt.length];
            System.arraycopy(zzt, 0, this.zzbCm, 0, zzt.length);
            this.zzbBO = this.zzbCm[2];
        }
    }

    private static int[] zzt(byte[] bArr) {
        if (bArr == null || bArr.length % 4 != 0) {
            return null;
        }
        int[] iArr = new int[(bArr.length / 4)];
        for (int i = 0; i < bArr.length; i += 4) {
            iArr[i / 4] = (int) ((((long) bArr[i + 3]) & 255) | ((((long) bArr[i + 2]) & 255) << 8) | ((((long) bArr[i + 1]) & 255) << 16) | ((255 & ((long) bArr[i])) << 24));
        }
        return iArr;
    }

    public final int getThreatType() {
        return this.zzbBO;
    }

    public final boolean zzs(byte[] bArr) {
        int i;
        int[] iArr = this.zzbCm;
        if (iArr == null) {
            return false;
        }
        int i2 = 3;
        long j = ((((long) bArr[3]) & 255) | ((((long) bArr[2]) & 255) << 8) | ((((long) bArr[1]) & 255) << 16) | ((255 & ((long) bArr[0])) << 24)) & 4294967295L;
        int length = iArr.length - 1;
        while (true) {
            if (i2 > length) {
                i = -1;
                break;
            }
            i = ((length - i2) / 2) + i2;
            long j2 = ((long) iArr[i]) & 4294967295L;
            if (j2 == j) {
                break;
            } else if (j2 < j) {
                i2 = i + 1;
            } else {
                length = i - 1;
            }
        }
        return i != -1;
    }
}
