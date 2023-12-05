package com.google.android.gms.internal;

public final class aib implements Cloneable {
    private static final aic zzcuY = new aic();
    private int mSize;
    private boolean zzcuZ;
    private int[] zzcva;
    private aic[] zzcvb;

    aib() {
        this(10);
    }

    private aib(int i) {
        this.zzcuZ = false;
        int idealIntArraySize = idealIntArraySize(i);
        this.zzcva = new int[idealIntArraySize];
        this.zzcvb = new aic[idealIntArraySize];
        this.mSize = 0;
    }

    private static int idealIntArraySize(int i) {
        int i2 = i << 2;
        int i3 = 4;
        while (true) {
            if (i3 >= 32) {
                break;
            }
            int i4 = (1 << i3) - 12;
            if (i2 <= i4) {
                i2 = i4;
                break;
            }
            i3++;
        }
        return i2 / 4;
    }

    private final int zzcy(int i) {
        int i2 = this.mSize - 1;
        int i3 = 0;
        while (i3 <= i2) {
            int i4 = (i3 + i2) >>> 1;
            int i5 = this.zzcva[i4];
            if (i5 < i) {
                i3 = i4 + 1;
            } else if (i5 <= i) {
                return i4;
            } else {
                i2 = i4 - 1;
            }
        }
        return i3 ^ -1;
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        int i = this.mSize;
        aib aib = new aib(i);
        System.arraycopy(this.zzcva, 0, aib.zzcva, 0, i);
        for (int i2 = 0; i2 < i; i2++) {
            aic[] aicArr = this.zzcvb;
            if (aicArr[i2] != null) {
                aib.zzcvb[i2] = (aic) aicArr[i2].clone();
            }
        }
        aib.mSize = i;
        return aib;
    }

    public final boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof aib)) {
            return false;
        }
        aib aib = (aib) obj;
        int i = this.mSize;
        if (i != aib.mSize) {
            return false;
        }
        int[] iArr = this.zzcva;
        int[] iArr2 = aib.zzcva;
        int i2 = 0;
        while (true) {
            if (i2 >= i) {
                z = true;
                break;
            } else if (iArr[i2] != iArr2[i2]) {
                z = false;
                break;
            } else {
                i2++;
            }
        }
        if (z) {
            aic[] aicArr = this.zzcvb;
            aic[] aicArr2 = aib.zzcvb;
            int i3 = this.mSize;
            int i4 = 0;
            while (true) {
                if (i4 >= i3) {
                    z2 = true;
                    break;
                } else if (!aicArr[i4].equals(aicArr2[i4])) {
                    z2 = false;
                    break;
                } else {
                    i4++;
                }
            }
            if (z2) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = 17;
        for (int i2 = 0; i2 < this.mSize; i2++) {
            i = (((i * 31) + this.zzcva[i2]) * 31) + this.zzcvb[i2].hashCode();
        }
        return i;
    }

    public final boolean isEmpty() {
        return this.mSize == 0;
    }

    /* access modifiers changed from: package-private */
    public final int size() {
        return this.mSize;
    }

    /* access modifiers changed from: package-private */
    public final void zza(int i, aic aic) {
        int zzcy = zzcy(i);
        if (zzcy >= 0) {
            this.zzcvb[zzcy] = aic;
            return;
        }
        int i2 = zzcy ^ -1;
        if (i2 < this.mSize) {
            aic[] aicArr = this.zzcvb;
            if (aicArr[i2] == zzcuY) {
                this.zzcva[i2] = i;
                aicArr[i2] = aic;
                return;
            }
        }
        int i3 = this.mSize;
        if (i3 >= this.zzcva.length) {
            int idealIntArraySize = idealIntArraySize(i3 + 1);
            int[] iArr = new int[idealIntArraySize];
            aic[] aicArr2 = new aic[idealIntArraySize];
            int[] iArr2 = this.zzcva;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            aic[] aicArr3 = this.zzcvb;
            System.arraycopy(aicArr3, 0, aicArr2, 0, aicArr3.length);
            this.zzcva = iArr;
            this.zzcvb = aicArr2;
        }
        int i4 = this.mSize;
        if (i4 - i2 != 0) {
            int[] iArr3 = this.zzcva;
            int i5 = i2 + 1;
            System.arraycopy(iArr3, i2, iArr3, i5, i4 - i2);
            aic[] aicArr4 = this.zzcvb;
            System.arraycopy(aicArr4, i2, aicArr4, i5, this.mSize - i2);
        }
        this.zzcva[i2] = i;
        this.zzcvb[i2] = aic;
        this.mSize++;
    }

    /* access modifiers changed from: package-private */
    public final aic zzcw(int i) {
        int zzcy = zzcy(i);
        if (zzcy < 0) {
            return null;
        }
        aic[] aicArr = this.zzcvb;
        if (aicArr[zzcy] == zzcuY) {
            return null;
        }
        return aicArr[zzcy];
    }

    /* access modifiers changed from: package-private */
    public final aic zzcx(int i) {
        return this.zzcvb[i];
    }
}
