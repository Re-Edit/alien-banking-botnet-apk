package com.google.android.gms.internal;

import java.io.IOException;

public final class aiz extends ahz<aiz> implements Cloneable {
    private String[] zzcwu = aij.EMPTY_STRING_ARRAY;
    private String[] zzcwv = aij.EMPTY_STRING_ARRAY;
    private int[] zzcww = aij.zzcvm;
    private long[] zzcwx = aij.zzcvn;
    private long[] zzcwy = aij.zzcvn;

    public aiz() {
        this.zzcuW = null;
        this.zzcvf = -1;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzMq */
    public aiz clone() {
        try {
            aiz aiz = (aiz) super.clone();
            String[] strArr = this.zzcwu;
            if (strArr != null && strArr.length > 0) {
                aiz.zzcwu = (String[]) strArr.clone();
            }
            String[] strArr2 = this.zzcwv;
            if (strArr2 != null && strArr2.length > 0) {
                aiz.zzcwv = (String[]) strArr2.clone();
            }
            int[] iArr = this.zzcww;
            if (iArr != null && iArr.length > 0) {
                aiz.zzcww = (int[]) iArr.clone();
            }
            long[] jArr = this.zzcwx;
            if (jArr != null && jArr.length > 0) {
                aiz.zzcwx = (long[]) jArr.clone();
            }
            long[] jArr2 = this.zzcwy;
            if (jArr2 != null && jArr2.length > 0) {
                aiz.zzcwy = (long[]) jArr2.clone();
            }
            return aiz;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof aiz)) {
            return false;
        }
        aiz aiz = (aiz) obj;
        if (aid.equals((Object[]) this.zzcwu, (Object[]) aiz.zzcwu) && aid.equals((Object[]) this.zzcwv, (Object[]) aiz.zzcwv) && aid.equals(this.zzcww, aiz.zzcww) && aid.equals(this.zzcwx, aiz.zzcwx) && aid.equals(this.zzcwy, aiz.zzcwy)) {
            return (this.zzcuW == null || this.zzcuW.isEmpty()) ? aiz.zzcuW == null || aiz.zzcuW.isEmpty() : this.zzcuW.equals(aiz.zzcuW);
        }
        return false;
    }

    public final int hashCode() {
        return ((((((((((((getClass().getName().hashCode() + 527) * 31) + aid.hashCode((Object[]) this.zzcwu)) * 31) + aid.hashCode((Object[]) this.zzcwv)) * 31) + aid.hashCode(this.zzcww)) * 31) + aid.hashCode(this.zzcwx)) * 31) + aid.hashCode(this.zzcwy)) * 31) + ((this.zzcuW == null || this.zzcuW.isEmpty()) ? 0 : this.zzcuW.hashCode());
    }

    public final /* synthetic */ ahz zzMd() throws CloneNotSupportedException {
        return (aiz) clone();
    }

    public final /* synthetic */ aif zzMe() throws CloneNotSupportedException {
        return (aiz) clone();
    }

    public final /* synthetic */ aif zza(ahw ahw) throws IOException {
        int i;
        while (true) {
            int zzLQ = ahw.zzLQ();
            if (zzLQ == 0) {
                return this;
            }
            if (zzLQ == 10) {
                int zzb = aij.zzb(ahw, 10);
                String[] strArr = this.zzcwu;
                int length = strArr == null ? 0 : strArr.length;
                String[] strArr2 = new String[(zzb + length)];
                if (length != 0) {
                    System.arraycopy(this.zzcwu, 0, strArr2, 0, length);
                }
                while (length < strArr2.length - 1) {
                    strArr2[length] = ahw.readString();
                    ahw.zzLQ();
                    length++;
                }
                strArr2[length] = ahw.readString();
                this.zzcwu = strArr2;
            } else if (zzLQ == 18) {
                int zzb2 = aij.zzb(ahw, 18);
                String[] strArr3 = this.zzcwv;
                int length2 = strArr3 == null ? 0 : strArr3.length;
                String[] strArr4 = new String[(zzb2 + length2)];
                if (length2 != 0) {
                    System.arraycopy(this.zzcwv, 0, strArr4, 0, length2);
                }
                while (length2 < strArr4.length - 1) {
                    strArr4[length2] = ahw.readString();
                    ahw.zzLQ();
                    length2++;
                }
                strArr4[length2] = ahw.readString();
                this.zzcwv = strArr4;
            } else if (zzLQ != 24) {
                if (zzLQ == 26) {
                    i = ahw.zzcm(ahw.zzLV());
                    int position = ahw.getPosition();
                    int i2 = 0;
                    while (ahw.zzMa() > 0) {
                        ahw.zzLS();
                        i2++;
                    }
                    ahw.zzco(position);
                    int[] iArr = this.zzcww;
                    int length3 = iArr == null ? 0 : iArr.length;
                    int[] iArr2 = new int[(i2 + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.zzcww, 0, iArr2, 0, length3);
                    }
                    while (length3 < iArr2.length) {
                        iArr2[length3] = ahw.zzLS();
                        length3++;
                    }
                    this.zzcww = iArr2;
                } else if (zzLQ == 32) {
                    int zzb3 = aij.zzb(ahw, 32);
                    long[] jArr = this.zzcwx;
                    int length4 = jArr == null ? 0 : jArr.length;
                    long[] jArr2 = new long[(zzb3 + length4)];
                    if (length4 != 0) {
                        System.arraycopy(this.zzcwx, 0, jArr2, 0, length4);
                    }
                    while (length4 < jArr2.length - 1) {
                        jArr2[length4] = ahw.zzLR();
                        ahw.zzLQ();
                        length4++;
                    }
                    jArr2[length4] = ahw.zzLR();
                    this.zzcwx = jArr2;
                } else if (zzLQ == 34) {
                    i = ahw.zzcm(ahw.zzLV());
                    int position2 = ahw.getPosition();
                    int i3 = 0;
                    while (ahw.zzMa() > 0) {
                        ahw.zzLR();
                        i3++;
                    }
                    ahw.zzco(position2);
                    long[] jArr3 = this.zzcwx;
                    int length5 = jArr3 == null ? 0 : jArr3.length;
                    long[] jArr4 = new long[(i3 + length5)];
                    if (length5 != 0) {
                        System.arraycopy(this.zzcwx, 0, jArr4, 0, length5);
                    }
                    while (length5 < jArr4.length) {
                        jArr4[length5] = ahw.zzLR();
                        length5++;
                    }
                    this.zzcwx = jArr4;
                } else if (zzLQ == 40) {
                    int zzb4 = aij.zzb(ahw, 40);
                    long[] jArr5 = this.zzcwy;
                    int length6 = jArr5 == null ? 0 : jArr5.length;
                    long[] jArr6 = new long[(zzb4 + length6)];
                    if (length6 != 0) {
                        System.arraycopy(this.zzcwy, 0, jArr6, 0, length6);
                    }
                    while (length6 < jArr6.length - 1) {
                        jArr6[length6] = ahw.zzLR();
                        ahw.zzLQ();
                        length6++;
                    }
                    jArr6[length6] = ahw.zzLR();
                    this.zzcwy = jArr6;
                } else if (zzLQ == 42) {
                    i = ahw.zzcm(ahw.zzLV());
                    int position3 = ahw.getPosition();
                    int i4 = 0;
                    while (ahw.zzMa() > 0) {
                        ahw.zzLR();
                        i4++;
                    }
                    ahw.zzco(position3);
                    long[] jArr7 = this.zzcwy;
                    int length7 = jArr7 == null ? 0 : jArr7.length;
                    long[] jArr8 = new long[(i4 + length7)];
                    if (length7 != 0) {
                        System.arraycopy(this.zzcwy, 0, jArr8, 0, length7);
                    }
                    while (length7 < jArr8.length) {
                        jArr8[length7] = ahw.zzLR();
                        length7++;
                    }
                    this.zzcwy = jArr8;
                } else if (!super.zza(ahw, zzLQ)) {
                    return this;
                }
                ahw.zzcn(i);
            } else {
                int zzb5 = aij.zzb(ahw, 24);
                int[] iArr3 = this.zzcww;
                int length8 = iArr3 == null ? 0 : iArr3.length;
                int[] iArr4 = new int[(zzb5 + length8)];
                if (length8 != 0) {
                    System.arraycopy(this.zzcww, 0, iArr4, 0, length8);
                }
                while (length8 < iArr4.length - 1) {
                    iArr4[length8] = ahw.zzLS();
                    ahw.zzLQ();
                    length8++;
                }
                iArr4[length8] = ahw.zzLS();
                this.zzcww = iArr4;
            }
        }
    }

    public final void zza(ahx ahx) throws IOException {
        String[] strArr = this.zzcwu;
        int i = 0;
        if (strArr != null && strArr.length > 0) {
            int i2 = 0;
            while (true) {
                String[] strArr2 = this.zzcwu;
                if (i2 >= strArr2.length) {
                    break;
                }
                String str = strArr2[i2];
                if (str != null) {
                    ahx.zzl(1, str);
                }
                i2++;
            }
        }
        String[] strArr3 = this.zzcwv;
        if (strArr3 != null && strArr3.length > 0) {
            int i3 = 0;
            while (true) {
                String[] strArr4 = this.zzcwv;
                if (i3 >= strArr4.length) {
                    break;
                }
                String str2 = strArr4[i3];
                if (str2 != null) {
                    ahx.zzl(2, str2);
                }
                i3++;
            }
        }
        int[] iArr = this.zzcww;
        if (iArr != null && iArr.length > 0) {
            int i4 = 0;
            while (true) {
                int[] iArr2 = this.zzcww;
                if (i4 >= iArr2.length) {
                    break;
                }
                ahx.zzr(3, iArr2[i4]);
                i4++;
            }
        }
        long[] jArr = this.zzcwx;
        if (jArr != null && jArr.length > 0) {
            int i5 = 0;
            while (true) {
                long[] jArr2 = this.zzcwx;
                if (i5 >= jArr2.length) {
                    break;
                }
                ahx.zzb(4, jArr2[i5]);
                i5++;
            }
        }
        long[] jArr3 = this.zzcwy;
        if (jArr3 != null && jArr3.length > 0) {
            while (true) {
                long[] jArr4 = this.zzcwy;
                if (i >= jArr4.length) {
                    break;
                }
                ahx.zzb(5, jArr4[i]);
                i++;
            }
        }
        super.zza(ahx);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        long[] jArr;
        int[] iArr;
        int zzn = super.zzn();
        String[] strArr = this.zzcwu;
        int i = 0;
        if (strArr != null && strArr.length > 0) {
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            while (true) {
                String[] strArr2 = this.zzcwu;
                if (i2 >= strArr2.length) {
                    break;
                }
                String str = strArr2[i2];
                if (str != null) {
                    i4++;
                    i3 += ahx.zzip(str);
                }
                i2++;
            }
            zzn = zzn + i3 + (i4 * 1);
        }
        String[] strArr3 = this.zzcwv;
        if (strArr3 != null && strArr3.length > 0) {
            int i5 = 0;
            int i6 = 0;
            int i7 = 0;
            while (true) {
                String[] strArr4 = this.zzcwv;
                if (i5 >= strArr4.length) {
                    break;
                }
                String str2 = strArr4[i5];
                if (str2 != null) {
                    i7++;
                    i6 += ahx.zzip(str2);
                }
                i5++;
            }
            zzn = zzn + i6 + (i7 * 1);
        }
        int[] iArr2 = this.zzcww;
        if (iArr2 != null && iArr2.length > 0) {
            int i8 = 0;
            int i9 = 0;
            while (true) {
                iArr = this.zzcww;
                if (i8 >= iArr.length) {
                    break;
                }
                i9 += ahx.zzcq(iArr[i8]);
                i8++;
            }
            zzn = zzn + i9 + (iArr.length * 1);
        }
        long[] jArr2 = this.zzcwx;
        if (jArr2 != null && jArr2.length > 0) {
            int i10 = 0;
            int i11 = 0;
            while (true) {
                jArr = this.zzcwx;
                if (i10 >= jArr.length) {
                    break;
                }
                i11 += ahx.zzaP(jArr[i10]);
                i10++;
            }
            zzn = zzn + i11 + (jArr.length * 1);
        }
        long[] jArr3 = this.zzcwy;
        if (jArr3 == null || jArr3.length <= 0) {
            return zzn;
        }
        int i12 = 0;
        while (true) {
            long[] jArr4 = this.zzcwy;
            if (i >= jArr4.length) {
                return zzn + i12 + (jArr4.length * 1);
            }
            i12 += ahx.zzaP(jArr4[i]);
            i++;
        }
    }
}
