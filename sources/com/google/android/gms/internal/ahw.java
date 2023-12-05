package com.google.android.gms.internal;

import java.io.IOException;

public final class ahw {
    private final byte[] buffer;
    private int zzcuM;
    private int zzcuN;
    private int zzcuO;
    private int zzcuP;
    private int zzcuQ;
    private int zzcuR = Integer.MAX_VALUE;
    private int zzcuS;
    private int zzcuT = 64;
    private int zzcuU = 67108864;

    private ahw(byte[] bArr, int i, int i2) {
        this.buffer = bArr;
        this.zzcuM = i;
        this.zzcuN = i2 + i;
        this.zzcuP = i;
    }

    public static ahw zzI(byte[] bArr) {
        return zzb(bArr, 0, bArr.length);
    }

    private final void zzLZ() {
        this.zzcuN += this.zzcuO;
        int i = this.zzcuN;
        int i2 = this.zzcuR;
        if (i > i2) {
            this.zzcuO = i - i2;
            this.zzcuN = i - this.zzcuO;
            return;
        }
        this.zzcuO = 0;
    }

    private final byte zzMb() throws IOException {
        int i = this.zzcuP;
        if (i != this.zzcuN) {
            byte[] bArr = this.buffer;
            this.zzcuP = i + 1;
            return bArr[i];
        }
        throw aie.zzMg();
    }

    public static ahw zzb(byte[] bArr, int i, int i2) {
        return new ahw(bArr, 0, i2);
    }

    private final void zzcp(int i) throws IOException {
        if (i >= 0) {
            int i2 = this.zzcuP;
            int i3 = i2 + i;
            int i4 = this.zzcuR;
            if (i3 > i4) {
                zzcp(i4 - i2);
                throw aie.zzMg();
            } else if (i <= this.zzcuN - i2) {
                this.zzcuP = i2 + i;
            } else {
                throw aie.zzMg();
            }
        } else {
            throw aie.zzMh();
        }
    }

    public final int getPosition() {
        return this.zzcuP - this.zzcuM;
    }

    public final byte[] readBytes() throws IOException {
        int zzLV = zzLV();
        if (zzLV < 0) {
            throw aie.zzMh();
        } else if (zzLV == 0) {
            return aij.zzcvs;
        } else {
            int i = this.zzcuN;
            int i2 = this.zzcuP;
            if (zzLV <= i - i2) {
                byte[] bArr = new byte[zzLV];
                System.arraycopy(this.buffer, i2, bArr, 0, zzLV);
                this.zzcuP += zzLV;
                return bArr;
            }
            throw aie.zzMg();
        }
    }

    public final String readString() throws IOException {
        int zzLV = zzLV();
        if (zzLV >= 0) {
            int i = this.zzcuN;
            int i2 = this.zzcuP;
            if (zzLV <= i - i2) {
                String str = new String(this.buffer, i2, zzLV, aid.UTF_8);
                this.zzcuP += zzLV;
                return str;
            }
            throw aie.zzMg();
        }
        throw aie.zzMh();
    }

    public final int zzLQ() throws IOException {
        if (this.zzcuP == this.zzcuN) {
            this.zzcuQ = 0;
            return 0;
        }
        this.zzcuQ = zzLV();
        int i = this.zzcuQ;
        if (i != 0) {
            return i;
        }
        throw new aie("Protocol message contained an invalid tag (zero).");
    }

    public final long zzLR() throws IOException {
        return zzLW();
    }

    public final int zzLS() throws IOException {
        return zzLV();
    }

    public final boolean zzLT() throws IOException {
        return zzLV() != 0;
    }

    public final long zzLU() throws IOException {
        long zzLW = zzLW();
        return (-(zzLW & 1)) ^ (zzLW >>> 1);
    }

    public final int zzLV() throws IOException {
        int i;
        byte zzMb = zzMb();
        if (zzMb >= 0) {
            return zzMb;
        }
        byte b = zzMb & Byte.MAX_VALUE;
        byte zzMb2 = zzMb();
        if (zzMb2 >= 0) {
            i = zzMb2 << 7;
        } else {
            b |= (zzMb2 & Byte.MAX_VALUE) << 7;
            byte zzMb3 = zzMb();
            if (zzMb3 >= 0) {
                i = zzMb3 << 14;
            } else {
                b |= (zzMb3 & Byte.MAX_VALUE) << 14;
                byte zzMb4 = zzMb();
                if (zzMb4 >= 0) {
                    i = zzMb4 << 21;
                } else {
                    byte b2 = b | ((zzMb4 & Byte.MAX_VALUE) << 21);
                    byte zzMb5 = zzMb();
                    byte b3 = b2 | (zzMb5 << 28);
                    if (zzMb5 >= 0) {
                        return b3;
                    }
                    for (int i2 = 0; i2 < 5; i2++) {
                        if (zzMb() >= 0) {
                            return b3;
                        }
                    }
                    throw aie.zzMi();
                }
            }
        }
        return b | i;
    }

    public final long zzLW() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte zzMb = zzMb();
            j |= ((long) (zzMb & Byte.MAX_VALUE)) << i;
            if ((zzMb & 128) == 0) {
                return j;
            }
        }
        throw aie.zzMi();
    }

    public final int zzLX() throws IOException {
        return (zzMb() & 255) | ((zzMb() & 255) << 8) | ((zzMb() & 255) << 16) | ((zzMb() & 255) << 24);
    }

    public final long zzLY() throws IOException {
        byte zzMb = zzMb();
        byte zzMb2 = zzMb();
        return ((((long) zzMb2) & 255) << 8) | (((long) zzMb) & 255) | ((((long) zzMb()) & 255) << 16) | ((((long) zzMb()) & 255) << 24) | ((((long) zzMb()) & 255) << 32) | ((((long) zzMb()) & 255) << 40) | ((((long) zzMb()) & 255) << 48) | ((((long) zzMb()) & 255) << 56);
    }

    public final int zzMa() {
        int i = this.zzcuR;
        if (i == Integer.MAX_VALUE) {
            return -1;
        }
        return i - this.zzcuP;
    }

    public final void zza(aif aif, int i) throws IOException {
        int i2 = this.zzcuS;
        if (i2 < this.zzcuT) {
            this.zzcuS = i2 + 1;
            aif.zza(this);
            zzck((i << 3) | 4);
            this.zzcuS--;
            return;
        }
        throw aie.zzMj();
    }

    public final void zzb(aif aif) throws IOException {
        int zzLV = zzLV();
        if (this.zzcuS < this.zzcuT) {
            int zzcm = zzcm(zzLV);
            this.zzcuS++;
            aif.zza(this);
            zzck(0);
            this.zzcuS--;
            zzcn(zzcm);
            return;
        }
        throw aie.zzMj();
    }

    public final void zzck(int i) throws aie {
        if (this.zzcuQ != i) {
            throw new aie("Protocol message end-group tag did not match expected tag.");
        }
    }

    public final boolean zzcl(int i) throws IOException {
        int zzLQ;
        switch (i & 7) {
            case 0:
                zzLV();
                return true;
            case 1:
                zzLY();
                return true;
            case 2:
                zzcp(zzLV());
                return true;
            case 3:
                break;
            case 4:
                return false;
            case 5:
                zzLX();
                return true;
            default:
                throw new aie("Protocol message tag had invalid wire type.");
        }
        do {
            zzLQ = zzLQ();
            if (zzLQ == 0 || !zzcl(zzLQ)) {
                zzck(((i >>> 3) << 3) | 4);
                return true;
            }
            zzLQ = zzLQ();
            zzck(((i >>> 3) << 3) | 4);
            return true;
        } while (!zzcl(zzLQ));
        zzck(((i >>> 3) << 3) | 4);
        return true;
    }

    public final int zzcm(int i) throws aie {
        if (i >= 0) {
            int i2 = i + this.zzcuP;
            int i3 = this.zzcuR;
            if (i2 <= i3) {
                this.zzcuR = i2;
                zzLZ();
                return i3;
            }
            throw aie.zzMg();
        }
        throw aie.zzMh();
    }

    public final void zzcn(int i) {
        this.zzcuR = i;
        zzLZ();
    }

    public final void zzco(int i) {
        zzq(i, this.zzcuQ);
    }

    public final byte[] zzp(int i, int i2) {
        if (i2 == 0) {
            return aij.zzcvs;
        }
        byte[] bArr = new byte[i2];
        System.arraycopy(this.buffer, this.zzcuM + i, bArr, 0, i2);
        return bArr;
    }

    /* access modifiers changed from: package-private */
    public final void zzq(int i, int i2) {
        int i3 = this.zzcuP;
        int i4 = this.zzcuM;
        if (i > i3 - i4) {
            StringBuilder sb = new StringBuilder(50);
            sb.append("Position ");
            sb.append(i);
            sb.append(" is beyond current ");
            sb.append(i3 - i4);
            throw new IllegalArgumentException(sb.toString());
        } else if (i >= 0) {
            this.zzcuP = i4 + i;
            this.zzcuQ = i2;
        } else {
            StringBuilder sb2 = new StringBuilder(24);
            sb2.append("Bad position ");
            sb2.append(i);
            throw new IllegalArgumentException(sb2.toString());
        }
    }
}
