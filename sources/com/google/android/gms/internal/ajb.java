package com.google.android.gms.internal;

import java.io.IOException;
import java.util.Arrays;

public final class ajb extends ahz<ajb> implements Cloneable {
    private byte[] zzcwA = aij.zzcvs;
    private String zzcwB = "";
    private byte[][] zzcwC = aij.zzcvr;
    private boolean zzcwD = false;

    public ajb() {
        this.zzcuW = null;
        this.zzcvf = -1;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzMs */
    public ajb clone() {
        try {
            ajb ajb = (ajb) super.clone();
            byte[][] bArr = this.zzcwC;
            if (bArr != null && bArr.length > 0) {
                ajb.zzcwC = (byte[][]) bArr.clone();
            }
            return ajb;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ajb)) {
            return false;
        }
        ajb ajb = (ajb) obj;
        if (!Arrays.equals(this.zzcwA, ajb.zzcwA)) {
            return false;
        }
        String str = this.zzcwB;
        if (str == null) {
            if (ajb.zzcwB != null) {
                return false;
            }
        } else if (!str.equals(ajb.zzcwB)) {
            return false;
        }
        if (aid.zza(this.zzcwC, ajb.zzcwC) && this.zzcwD == ajb.zzcwD) {
            return (this.zzcuW == null || this.zzcuW.isEmpty()) ? ajb.zzcuW == null || ajb.zzcuW.isEmpty() : this.zzcuW.equals(ajb.zzcuW);
        }
        return false;
    }

    public final int hashCode() {
        int hashCode = (((getClass().getName().hashCode() + 527) * 31) + Arrays.hashCode(this.zzcwA)) * 31;
        String str = this.zzcwB;
        int i = 0;
        int hashCode2 = (((((hashCode + (str == null ? 0 : str.hashCode())) * 31) + aid.zzc(this.zzcwC)) * 31) + (this.zzcwD ? 1231 : 1237)) * 31;
        if (this.zzcuW != null && !this.zzcuW.isEmpty()) {
            i = this.zzcuW.hashCode();
        }
        return hashCode2 + i;
    }

    public final /* synthetic */ ahz zzMd() throws CloneNotSupportedException {
        return (ajb) clone();
    }

    public final /* synthetic */ aif zzMe() throws CloneNotSupportedException {
        return (ajb) clone();
    }

    public final /* synthetic */ aif zza(ahw ahw) throws IOException {
        while (true) {
            int zzLQ = ahw.zzLQ();
            if (zzLQ == 0) {
                return this;
            }
            if (zzLQ == 10) {
                this.zzcwA = ahw.readBytes();
            } else if (zzLQ == 18) {
                int zzb = aij.zzb(ahw, 18);
                byte[][] bArr = this.zzcwC;
                int length = bArr == null ? 0 : bArr.length;
                byte[][] bArr2 = new byte[(zzb + length)][];
                if (length != 0) {
                    System.arraycopy(this.zzcwC, 0, bArr2, 0, length);
                }
                while (length < bArr2.length - 1) {
                    bArr2[length] = ahw.readBytes();
                    ahw.zzLQ();
                    length++;
                }
                bArr2[length] = ahw.readBytes();
                this.zzcwC = bArr2;
            } else if (zzLQ == 24) {
                this.zzcwD = ahw.zzLT();
            } else if (zzLQ == 34) {
                this.zzcwB = ahw.readString();
            } else if (!super.zza(ahw, zzLQ)) {
                return this;
            }
        }
    }

    public final void zza(ahx ahx) throws IOException {
        if (!Arrays.equals(this.zzcwA, aij.zzcvs)) {
            ahx.zzb(1, this.zzcwA);
        }
        byte[][] bArr = this.zzcwC;
        if (bArr != null && bArr.length > 0) {
            int i = 0;
            while (true) {
                byte[][] bArr2 = this.zzcwC;
                if (i >= bArr2.length) {
                    break;
                }
                byte[] bArr3 = bArr2[i];
                if (bArr3 != null) {
                    ahx.zzb(2, bArr3);
                }
                i++;
            }
        }
        boolean z = this.zzcwD;
        if (z) {
            ahx.zzk(3, z);
        }
        String str = this.zzcwB;
        if (str != null && !str.equals("")) {
            ahx.zzl(4, this.zzcwB);
        }
        super.zza(ahx);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (!Arrays.equals(this.zzcwA, aij.zzcvs)) {
            zzn += ahx.zzc(1, this.zzcwA);
        }
        byte[][] bArr = this.zzcwC;
        if (bArr != null && bArr.length > 0) {
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            while (true) {
                byte[][] bArr2 = this.zzcwC;
                if (i >= bArr2.length) {
                    break;
                }
                byte[] bArr3 = bArr2[i];
                if (bArr3 != null) {
                    i3++;
                    i2 += ahx.zzK(bArr3);
                }
                i++;
            }
            zzn = zzn + i2 + (i3 * 1);
        }
        if (this.zzcwD) {
            zzn += ahx.zzcs(3) + 1;
        }
        String str = this.zzcwB;
        return (str == null || str.equals("")) ? zzn : zzn + ahx.zzm(4, this.zzcwB);
    }
}
