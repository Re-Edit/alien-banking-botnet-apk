package com.google.android.gms.internal;

import android.support.v4.media.TransportMediator;
import android.support.v4.view.MotionEventCompat;
import java.io.IOException;
import java.util.Arrays;

public final class ajc extends ahz<ajc> implements Cloneable {
    private String tag = "";
    private boolean zzcfX = false;
    private aje zzcpG = null;
    public long zzcwE = 0;
    public long zzcwF = 0;
    private long zzcwG = 0;
    public int zzcwH = 0;
    private ajd[] zzcwI = ajd.zzMu();
    private byte[] zzcwJ = aij.zzcvs;
    private aja zzcwK = null;
    public byte[] zzcwL = aij.zzcvs;
    private String zzcwM = "";
    private String zzcwN = "";
    private aiz zzcwO = null;
    private String zzcwP = "";
    public long zzcwQ = 180000;
    private ajb zzcwR = null;
    public byte[] zzcwS = aij.zzcvs;
    private String zzcwT = "";
    private int zzcwU = 0;
    private int[] zzcwV = aij.zzcvm;
    private long zzcwW = 0;
    public int zzrE = 0;

    public ajc() {
        this.zzcuW = null;
        this.zzcvf = -1;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzMt */
    public final ajc clone() {
        try {
            ajc ajc = (ajc) super.clone();
            ajd[] ajdArr = this.zzcwI;
            if (ajdArr != null && ajdArr.length > 0) {
                ajc.zzcwI = new ajd[ajdArr.length];
                int i = 0;
                while (true) {
                    ajd[] ajdArr2 = this.zzcwI;
                    if (i >= ajdArr2.length) {
                        break;
                    }
                    if (ajdArr2[i] != null) {
                        ajc.zzcwI[i] = (ajd) ajdArr2[i].clone();
                    }
                    i++;
                }
            }
            aja aja = this.zzcwK;
            if (aja != null) {
                ajc.zzcwK = (aja) aja.clone();
            }
            aiz aiz = this.zzcwO;
            if (aiz != null) {
                ajc.zzcwO = (aiz) aiz.clone();
            }
            ajb ajb = this.zzcwR;
            if (ajb != null) {
                ajc.zzcwR = (ajb) ajb.clone();
            }
            int[] iArr = this.zzcwV;
            if (iArr != null && iArr.length > 0) {
                ajc.zzcwV = (int[]) iArr.clone();
            }
            aje aje = this.zzcpG;
            if (aje != null) {
                ajc.zzcpG = (aje) aje.clone();
            }
            return ajc;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ajc)) {
            return false;
        }
        ajc ajc = (ajc) obj;
        if (this.zzcwE != ajc.zzcwE || this.zzcwF != ajc.zzcwF || this.zzcwG != ajc.zzcwG) {
            return false;
        }
        String str = this.tag;
        if (str == null) {
            if (ajc.tag != null) {
                return false;
            }
        } else if (!str.equals(ajc.tag)) {
            return false;
        }
        if (this.zzcwH != ajc.zzcwH || this.zzrE != ajc.zzrE || this.zzcfX != ajc.zzcfX || !aid.equals((Object[]) this.zzcwI, (Object[]) ajc.zzcwI) || !Arrays.equals(this.zzcwJ, ajc.zzcwJ)) {
            return false;
        }
        aja aja = this.zzcwK;
        if (aja == null) {
            if (ajc.zzcwK != null) {
                return false;
            }
        } else if (!aja.equals(ajc.zzcwK)) {
            return false;
        }
        if (!Arrays.equals(this.zzcwL, ajc.zzcwL)) {
            return false;
        }
        String str2 = this.zzcwM;
        if (str2 == null) {
            if (ajc.zzcwM != null) {
                return false;
            }
        } else if (!str2.equals(ajc.zzcwM)) {
            return false;
        }
        String str3 = this.zzcwN;
        if (str3 == null) {
            if (ajc.zzcwN != null) {
                return false;
            }
        } else if (!str3.equals(ajc.zzcwN)) {
            return false;
        }
        aiz aiz = this.zzcwO;
        if (aiz == null) {
            if (ajc.zzcwO != null) {
                return false;
            }
        } else if (!aiz.equals(ajc.zzcwO)) {
            return false;
        }
        String str4 = this.zzcwP;
        if (str4 == null) {
            if (ajc.zzcwP != null) {
                return false;
            }
        } else if (!str4.equals(ajc.zzcwP)) {
            return false;
        }
        if (this.zzcwQ != ajc.zzcwQ) {
            return false;
        }
        ajb ajb = this.zzcwR;
        if (ajb == null) {
            if (ajc.zzcwR != null) {
                return false;
            }
        } else if (!ajb.equals(ajc.zzcwR)) {
            return false;
        }
        if (!Arrays.equals(this.zzcwS, ajc.zzcwS)) {
            return false;
        }
        String str5 = this.zzcwT;
        if (str5 == null) {
            if (ajc.zzcwT != null) {
                return false;
            }
        } else if (!str5.equals(ajc.zzcwT)) {
            return false;
        }
        if (this.zzcwU != ajc.zzcwU || !aid.equals(this.zzcwV, ajc.zzcwV) || this.zzcwW != ajc.zzcwW) {
            return false;
        }
        aje aje = this.zzcpG;
        if (aje == null) {
            if (ajc.zzcpG != null) {
                return false;
            }
        } else if (!aje.equals(ajc.zzcpG)) {
            return false;
        }
        return (this.zzcuW == null || this.zzcuW.isEmpty()) ? ajc.zzcuW == null || ajc.zzcuW.isEmpty() : this.zzcuW.equals(ajc.zzcuW);
    }

    public final int hashCode() {
        long j = this.zzcwE;
        long j2 = this.zzcwF;
        long j3 = this.zzcwG;
        int hashCode = (((((((getClass().getName().hashCode() + 527) * 31) + ((int) (j ^ (j >>> 32)))) * 31) + ((int) (j2 ^ (j2 >>> 32)))) * 31) + ((int) (j3 ^ (j3 >>> 32)))) * 31;
        String str = this.tag;
        int i = 0;
        int hashCode2 = (((((((((((hashCode + (str == null ? 0 : str.hashCode())) * 31) + this.zzcwH) * 31) + this.zzrE) * 31) + (this.zzcfX ? 1231 : 1237)) * 31) + aid.hashCode((Object[]) this.zzcwI)) * 31) + Arrays.hashCode(this.zzcwJ)) * 31;
        aja aja = this.zzcwK;
        int hashCode3 = (((hashCode2 + (aja == null ? 0 : aja.hashCode())) * 31) + Arrays.hashCode(this.zzcwL)) * 31;
        String str2 = this.zzcwM;
        int hashCode4 = (hashCode3 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.zzcwN;
        int hashCode5 = (hashCode4 + (str3 == null ? 0 : str3.hashCode())) * 31;
        aiz aiz = this.zzcwO;
        int hashCode6 = (hashCode5 + (aiz == null ? 0 : aiz.hashCode())) * 31;
        String str4 = this.zzcwP;
        int hashCode7 = str4 == null ? 0 : str4.hashCode();
        long j4 = this.zzcwQ;
        int i2 = (((hashCode6 + hashCode7) * 31) + ((int) (j4 ^ (j4 >>> 32)))) * 31;
        ajb ajb = this.zzcwR;
        int hashCode8 = (((i2 + (ajb == null ? 0 : ajb.hashCode())) * 31) + Arrays.hashCode(this.zzcwS)) * 31;
        String str5 = this.zzcwT;
        int hashCode9 = str5 == null ? 0 : str5.hashCode();
        long j5 = this.zzcwW;
        int hashCode10 = (((((((hashCode8 + hashCode9) * 31) + this.zzcwU) * 31) + aid.hashCode(this.zzcwV)) * 31) + ((int) (j5 ^ (j5 >>> 32)))) * 31;
        aje aje = this.zzcpG;
        int hashCode11 = (hashCode10 + (aje == null ? 0 : aje.hashCode())) * 31;
        if (this.zzcuW != null && !this.zzcuW.isEmpty()) {
            i = this.zzcuW.hashCode();
        }
        return hashCode11 + i;
    }

    public final /* synthetic */ ahz zzMd() throws CloneNotSupportedException {
        return (ajc) clone();
    }

    public final /* synthetic */ aif zzMe() throws CloneNotSupportedException {
        return (ajc) clone();
    }

    public final /* synthetic */ aif zza(ahw ahw) throws IOException {
        aif aif;
        while (true) {
            int zzLQ = ahw.zzLQ();
            switch (zzLQ) {
                case 0:
                    return this;
                case 8:
                    this.zzcwE = ahw.zzLR();
                    continue;
                case 18:
                    this.tag = ahw.readString();
                    continue;
                case 26:
                    int zzb = aij.zzb(ahw, 26);
                    ajd[] ajdArr = this.zzcwI;
                    int length = ajdArr == null ? 0 : ajdArr.length;
                    ajd[] ajdArr2 = new ajd[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzcwI, 0, ajdArr2, 0, length);
                    }
                    while (length < ajdArr2.length - 1) {
                        ajdArr2[length] = new ajd();
                        ahw.zzb(ajdArr2[length]);
                        ahw.zzLQ();
                        length++;
                    }
                    ajdArr2[length] = new ajd();
                    ahw.zzb(ajdArr2[length]);
                    this.zzcwI = ajdArr2;
                    continue;
                case MotionEventCompat.AXIS_GENERIC_3:
                    this.zzcwJ = ahw.readBytes();
                    continue;
                case 50:
                    this.zzcwL = ahw.readBytes();
                    continue;
                case 58:
                    if (this.zzcwO == null) {
                        this.zzcwO = new aiz();
                    }
                    aif = this.zzcwO;
                    break;
                case 66:
                    this.zzcwM = ahw.readString();
                    continue;
                case 74:
                    if (this.zzcwK == null) {
                        this.zzcwK = new aja();
                    }
                    aif = this.zzcwK;
                    break;
                case 80:
                    this.zzcfX = ahw.zzLT();
                    continue;
                case 88:
                    this.zzcwH = ahw.zzLS();
                    continue;
                case 96:
                    this.zzrE = ahw.zzLS();
                    continue;
                case 106:
                    this.zzcwN = ahw.readString();
                    continue;
                case 114:
                    this.zzcwP = ahw.readString();
                    continue;
                case 120:
                    this.zzcwQ = ahw.zzLU();
                    continue;
                case TransportMediator.KEYCODE_MEDIA_RECORD:
                    if (this.zzcwR == null) {
                        this.zzcwR = new ajb();
                    }
                    aif = this.zzcwR;
                    break;
                case 136:
                    this.zzcwF = ahw.zzLR();
                    continue;
                case 146:
                    this.zzcwS = ahw.readBytes();
                    continue;
                case 152:
                    int position = ahw.getPosition();
                    int zzLS = ahw.zzLS();
                    switch (zzLS) {
                        case 0:
                        case 1:
                        case 2:
                            this.zzcwU = zzLS;
                            break;
                        default:
                            ahw.zzco(position);
                            zza(ahw, zzLQ);
                            continue;
                    }
                case 160:
                    int zzb2 = aij.zzb(ahw, 160);
                    int[] iArr = this.zzcwV;
                    int length2 = iArr == null ? 0 : iArr.length;
                    int[] iArr2 = new int[(zzb2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.zzcwV, 0, iArr2, 0, length2);
                    }
                    while (length2 < iArr2.length - 1) {
                        iArr2[length2] = ahw.zzLS();
                        ahw.zzLQ();
                        length2++;
                    }
                    iArr2[length2] = ahw.zzLS();
                    this.zzcwV = iArr2;
                    continue;
                case 162:
                    int zzcm = ahw.zzcm(ahw.zzLV());
                    int position2 = ahw.getPosition();
                    int i = 0;
                    while (ahw.zzMa() > 0) {
                        ahw.zzLS();
                        i++;
                    }
                    ahw.zzco(position2);
                    int[] iArr3 = this.zzcwV;
                    int length3 = iArr3 == null ? 0 : iArr3.length;
                    int[] iArr4 = new int[(i + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.zzcwV, 0, iArr4, 0, length3);
                    }
                    while (length3 < iArr4.length) {
                        iArr4[length3] = ahw.zzLS();
                        length3++;
                    }
                    this.zzcwV = iArr4;
                    ahw.zzcn(zzcm);
                    continue;
                case 168:
                    this.zzcwG = ahw.zzLR();
                    continue;
                case 176:
                    this.zzcwW = ahw.zzLR();
                    continue;
                case 186:
                    if (this.zzcpG == null) {
                        this.zzcpG = new aje();
                    }
                    aif = this.zzcpG;
                    break;
                case 194:
                    this.zzcwT = ahw.readString();
                    continue;
                default:
                    if (!super.zza(ahw, zzLQ)) {
                        return this;
                    }
                    continue;
            }
            ahw.zzb(aif);
        }
    }

    public final void zza(ahx ahx) throws IOException {
        long j = this.zzcwE;
        if (j != 0) {
            ahx.zzb(1, j);
        }
        String str = this.tag;
        if (str != null && !str.equals("")) {
            ahx.zzl(2, this.tag);
        }
        ajd[] ajdArr = this.zzcwI;
        int i = 0;
        if (ajdArr != null && ajdArr.length > 0) {
            int i2 = 0;
            while (true) {
                ajd[] ajdArr2 = this.zzcwI;
                if (i2 >= ajdArr2.length) {
                    break;
                }
                ajd ajd = ajdArr2[i2];
                if (ajd != null) {
                    ahx.zza(3, (aif) ajd);
                }
                i2++;
            }
        }
        if (!Arrays.equals(this.zzcwJ, aij.zzcvs)) {
            ahx.zzb(4, this.zzcwJ);
        }
        if (!Arrays.equals(this.zzcwL, aij.zzcvs)) {
            ahx.zzb(6, this.zzcwL);
        }
        aiz aiz = this.zzcwO;
        if (aiz != null) {
            ahx.zza(7, (aif) aiz);
        }
        String str2 = this.zzcwM;
        if (str2 != null && !str2.equals("")) {
            ahx.zzl(8, this.zzcwM);
        }
        aja aja = this.zzcwK;
        if (aja != null) {
            ahx.zza(9, (aif) aja);
        }
        boolean z = this.zzcfX;
        if (z) {
            ahx.zzk(10, z);
        }
        int i3 = this.zzcwH;
        if (i3 != 0) {
            ahx.zzr(11, i3);
        }
        int i4 = this.zzrE;
        if (i4 != 0) {
            ahx.zzr(12, i4);
        }
        String str3 = this.zzcwN;
        if (str3 != null && !str3.equals("")) {
            ahx.zzl(13, this.zzcwN);
        }
        String str4 = this.zzcwP;
        if (str4 != null && !str4.equals("")) {
            ahx.zzl(14, this.zzcwP);
        }
        long j2 = this.zzcwQ;
        if (j2 != 180000) {
            ahx.zzd(15, j2);
        }
        ajb ajb = this.zzcwR;
        if (ajb != null) {
            ahx.zza(16, (aif) ajb);
        }
        long j3 = this.zzcwF;
        if (j3 != 0) {
            ahx.zzb(17, j3);
        }
        if (!Arrays.equals(this.zzcwS, aij.zzcvs)) {
            ahx.zzb(18, this.zzcwS);
        }
        int i5 = this.zzcwU;
        if (i5 != 0) {
            ahx.zzr(19, i5);
        }
        int[] iArr = this.zzcwV;
        if (iArr != null && iArr.length > 0) {
            while (true) {
                int[] iArr2 = this.zzcwV;
                if (i >= iArr2.length) {
                    break;
                }
                ahx.zzr(20, iArr2[i]);
                i++;
            }
        }
        long j4 = this.zzcwG;
        if (j4 != 0) {
            ahx.zzb(21, j4);
        }
        long j5 = this.zzcwW;
        if (j5 != 0) {
            ahx.zzb(22, j5);
        }
        aje aje = this.zzcpG;
        if (aje != null) {
            ahx.zza(23, (aif) aje);
        }
        String str5 = this.zzcwT;
        if (str5 != null && !str5.equals("")) {
            ahx.zzl(24, this.zzcwT);
        }
        super.zza(ahx);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int[] iArr;
        int zzn = super.zzn();
        long j = this.zzcwE;
        if (j != 0) {
            zzn += ahx.zze(1, j);
        }
        String str = this.tag;
        if (str != null && !str.equals("")) {
            zzn += ahx.zzm(2, this.tag);
        }
        ajd[] ajdArr = this.zzcwI;
        int i = 0;
        if (ajdArr != null && ajdArr.length > 0) {
            int i2 = zzn;
            int i3 = 0;
            while (true) {
                ajd[] ajdArr2 = this.zzcwI;
                if (i3 >= ajdArr2.length) {
                    break;
                }
                ajd ajd = ajdArr2[i3];
                if (ajd != null) {
                    i2 += ahx.zzb(3, (aif) ajd);
                }
                i3++;
            }
            zzn = i2;
        }
        if (!Arrays.equals(this.zzcwJ, aij.zzcvs)) {
            zzn += ahx.zzc(4, this.zzcwJ);
        }
        if (!Arrays.equals(this.zzcwL, aij.zzcvs)) {
            zzn += ahx.zzc(6, this.zzcwL);
        }
        aiz aiz = this.zzcwO;
        if (aiz != null) {
            zzn += ahx.zzb(7, (aif) aiz);
        }
        String str2 = this.zzcwM;
        if (str2 != null && !str2.equals("")) {
            zzn += ahx.zzm(8, this.zzcwM);
        }
        aja aja = this.zzcwK;
        if (aja != null) {
            zzn += ahx.zzb(9, (aif) aja);
        }
        if (this.zzcfX) {
            zzn += ahx.zzcs(10) + 1;
        }
        int i4 = this.zzcwH;
        if (i4 != 0) {
            zzn += ahx.zzs(11, i4);
        }
        int i5 = this.zzrE;
        if (i5 != 0) {
            zzn += ahx.zzs(12, i5);
        }
        String str3 = this.zzcwN;
        if (str3 != null && !str3.equals("")) {
            zzn += ahx.zzm(13, this.zzcwN);
        }
        String str4 = this.zzcwP;
        if (str4 != null && !str4.equals("")) {
            zzn += ahx.zzm(14, this.zzcwP);
        }
        long j2 = this.zzcwQ;
        if (j2 != 180000) {
            zzn += ahx.zzf(15, j2);
        }
        ajb ajb = this.zzcwR;
        if (ajb != null) {
            zzn += ahx.zzb(16, (aif) ajb);
        }
        long j3 = this.zzcwF;
        if (j3 != 0) {
            zzn += ahx.zze(17, j3);
        }
        if (!Arrays.equals(this.zzcwS, aij.zzcvs)) {
            zzn += ahx.zzc(18, this.zzcwS);
        }
        int i6 = this.zzcwU;
        if (i6 != 0) {
            zzn += ahx.zzs(19, i6);
        }
        int[] iArr2 = this.zzcwV;
        if (iArr2 != null && iArr2.length > 0) {
            int i7 = 0;
            while (true) {
                iArr = this.zzcwV;
                if (i >= iArr.length) {
                    break;
                }
                i7 += ahx.zzcq(iArr[i]);
                i++;
            }
            zzn = zzn + i7 + (iArr.length * 2);
        }
        long j4 = this.zzcwG;
        if (j4 != 0) {
            zzn += ahx.zze(21, j4);
        }
        long j5 = this.zzcwW;
        if (j5 != 0) {
            zzn += ahx.zze(22, j5);
        }
        aje aje = this.zzcpG;
        if (aje != null) {
            zzn += ahx.zzb(23, (aif) aje);
        }
        String str5 = this.zzcwT;
        return (str5 == null || str5.equals("")) ? zzn : zzn + ahx.zzm(24, this.zzcwT);
    }
}
