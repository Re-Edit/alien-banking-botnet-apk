package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class zzcrz extends zza {
    public static final Parcelable.Creator<zzcrz> CREATOR = new zzcsf();
    private static byte[][] zzazk = new byte[0][];
    private static zzcrz zzbAg;
    private static final zzcse zzbAp = new zzcsa();
    private static final zzcse zzbAq = new zzcsb();
    private static final zzcse zzbAr = new zzcsc();
    private static final zzcse zzbAs = new zzcsd();
    private String zzbAh;
    private byte[] zzbAi;
    private byte[][] zzbAj;
    private byte[][] zzbAk;
    private byte[][] zzbAl;
    private byte[][] zzbAm;
    private int[] zzbAn;
    private byte[][] zzbAo;

    /* JADX WARNING: type inference failed for: r0v4, types: [com.google.android.gms.internal.zzcsa, com.google.android.gms.internal.zzcse] */
    /* JADX WARNING: type inference failed for: r0v5, types: [com.google.android.gms.internal.zzcsb, com.google.android.gms.internal.zzcse] */
    /* JADX WARNING: type inference failed for: r0v6, types: [com.google.android.gms.internal.zzcsc, com.google.android.gms.internal.zzcse] */
    /* JADX WARNING: type inference failed for: r0v7, types: [com.google.android.gms.internal.zzcsd, com.google.android.gms.internal.zzcse] */
    static {
        byte[][] bArr = zzazk;
        zzbAg = new zzcrz("", (byte[]) null, bArr, bArr, bArr, bArr, (int[]) null, (byte[][]) null);
    }

    public zzcrz(String str, byte[] bArr, byte[][] bArr2, byte[][] bArr3, byte[][] bArr4, byte[][] bArr5, int[] iArr, byte[][] bArr6) {
        this.zzbAh = str;
        this.zzbAi = bArr;
        this.zzbAj = bArr2;
        this.zzbAk = bArr3;
        this.zzbAl = bArr4;
        this.zzbAm = bArr5;
        this.zzbAn = iArr;
        this.zzbAo = bArr6;
    }

    private static void zza(StringBuilder sb, String str, int[] iArr) {
        String str2;
        sb.append(str);
        sb.append("=");
        if (iArr == null) {
            str2 = "null";
        } else {
            sb.append("(");
            int length = iArr.length;
            int i = 0;
            boolean z = true;
            while (i < length) {
                int i2 = iArr[i];
                if (!z) {
                    sb.append(", ");
                }
                sb.append(i2);
                i++;
                z = false;
            }
            str2 = ")";
        }
        sb.append(str2);
    }

    private static void zza(StringBuilder sb, String str, byte[][] bArr) {
        String str2;
        sb.append(str);
        sb.append("=");
        if (bArr == null) {
            str2 = "null";
        } else {
            sb.append("(");
            int length = bArr.length;
            int i = 0;
            boolean z = true;
            while (i < length) {
                byte[] bArr2 = bArr[i];
                if (!z) {
                    sb.append(", ");
                }
                sb.append("'");
                sb.append(Base64.encodeToString(bArr2, 3));
                sb.append("'");
                i++;
                z = false;
            }
            str2 = ")";
        }
        sb.append(str2);
    }

    private static List<String> zzb(byte[][] bArr) {
        if (bArr == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(bArr.length);
        for (byte[] encodeToString : bArr) {
            arrayList.add(Base64.encodeToString(encodeToString, 3));
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    private static List<Integer> zzc(int[] iArr) {
        if (iArr == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(iArr.length);
        for (int valueOf : iArr) {
            arrayList.add(Integer.valueOf(valueOf));
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof zzcrz) {
            zzcrz zzcrz = (zzcrz) obj;
            return zzcsg.equals(this.zzbAh, zzcrz.zzbAh) && Arrays.equals(this.zzbAi, zzcrz.zzbAi) && zzcsg.equals(zzb(this.zzbAj), zzb(zzcrz.zzbAj)) && zzcsg.equals(zzb(this.zzbAk), zzb(zzcrz.zzbAk)) && zzcsg.equals(zzb(this.zzbAl), zzb(zzcrz.zzbAl)) && zzcsg.equals(zzb(this.zzbAm), zzb(zzcrz.zzbAm)) && zzcsg.equals(zzc(this.zzbAn), zzc(zzcrz.zzbAn)) && zzcsg.equals(zzb(this.zzbAo), zzb(zzcrz.zzbAo));
        }
    }

    public final String toString() {
        String str;
        String str2;
        StringBuilder sb = new StringBuilder("ExperimentTokens");
        sb.append("(");
        if (this.zzbAh == null) {
            str = "null";
        } else {
            String valueOf = String.valueOf("'");
            String str3 = this.zzbAh;
            String valueOf2 = String.valueOf("'");
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + String.valueOf(str3).length() + String.valueOf(valueOf2).length());
            sb2.append(valueOf);
            sb2.append(str3);
            sb2.append(valueOf2);
            str = sb2.toString();
        }
        sb.append(str);
        sb.append(", ");
        byte[] bArr = this.zzbAi;
        sb.append("direct");
        sb.append("=");
        if (bArr == null) {
            str2 = "null";
        } else {
            sb.append("'");
            sb.append(Base64.encodeToString(bArr, 3));
            str2 = "'";
        }
        sb.append(str2);
        sb.append(", ");
        zza(sb, "GAIA", this.zzbAj);
        sb.append(", ");
        zza(sb, "PSEUDO", this.zzbAk);
        sb.append(", ");
        zza(sb, "ALWAYS", this.zzbAl);
        sb.append(", ");
        zza(sb, "OTHER", this.zzbAm);
        sb.append(", ");
        zza(sb, "weak", this.zzbAn);
        sb.append(", ");
        zza(sb, "directs", this.zzbAo);
        sb.append(")");
        return sb.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, this.zzbAh, false);
        zzd.zza(parcel, 3, this.zzbAi, false);
        zzd.zza(parcel, 4, this.zzbAj, false);
        zzd.zza(parcel, 5, this.zzbAk, false);
        zzd.zza(parcel, 6, this.zzbAl, false);
        zzd.zza(parcel, 7, this.zzbAm, false);
        zzd.zza(parcel, 8, this.zzbAn, false);
        zzd.zza(parcel, 9, this.zzbAo, false);
        zzd.zzI(parcel, zze);
    }
}
