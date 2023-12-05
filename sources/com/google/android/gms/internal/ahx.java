package com.google.android.gms.internal;

import android.support.v4.media.TransportMediator;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;

public final class ahx {
    private final ByteBuffer zzcuV;

    private ahx(ByteBuffer byteBuffer) {
        this.zzcuV = byteBuffer;
        this.zzcuV.order(ByteOrder.LITTLE_ENDIAN);
    }

    private ahx(byte[] bArr, int i, int i2) {
        this(ByteBuffer.wrap(bArr, i, i2));
    }

    public static ahx zzJ(byte[] bArr) {
        return zzc(bArr, 0, bArr.length);
    }

    public static int zzK(byte[] bArr) {
        return zzcu(bArr.length) + bArr.length;
    }

    private static int zza(CharSequence charSequence, byte[] bArr, int i, int i2) {
        int i3;
        int i4;
        char charAt;
        int length = charSequence.length();
        int i5 = i2 + i;
        int i6 = 0;
        while (i6 < length && (i4 = i6 + i) < i5 && (charAt = charSequence.charAt(i6)) < 128) {
            bArr[i4] = (byte) charAt;
            i6++;
        }
        if (i6 == length) {
            return i + length;
        }
        int i7 = i + i6;
        while (i6 < length) {
            char charAt2 = charSequence.charAt(i6);
            if (charAt2 < 128 && i7 < i5) {
                i3 = i7 + 1;
                bArr[i7] = (byte) charAt2;
            } else if (charAt2 < 2048 && i7 <= i5 - 2) {
                int i8 = i7 + 1;
                bArr[i7] = (byte) ((charAt2 >>> 6) | 960);
                i7 = i8 + 1;
                bArr[i8] = (byte) ((charAt2 & '?') | 128);
                i6++;
            } else if ((charAt2 < 55296 || 57343 < charAt2) && i7 <= i5 - 3) {
                int i9 = i7 + 1;
                bArr[i7] = (byte) ((charAt2 >>> 12) | 480);
                int i10 = i9 + 1;
                bArr[i9] = (byte) (((charAt2 >>> 6) & 63) | 128);
                i3 = i10 + 1;
                bArr[i10] = (byte) ((charAt2 & '?') | 128);
            } else if (i7 <= i5 - 4) {
                int i11 = i6 + 1;
                if (i11 != charSequence.length()) {
                    char charAt3 = charSequence.charAt(i11);
                    if (Character.isSurrogatePair(charAt2, charAt3)) {
                        int codePoint = Character.toCodePoint(charAt2, charAt3);
                        int i12 = i7 + 1;
                        bArr[i7] = (byte) ((codePoint >>> 18) | 240);
                        int i13 = i12 + 1;
                        bArr[i12] = (byte) (((codePoint >>> 12) & 63) | 128);
                        int i14 = i13 + 1;
                        bArr[i13] = (byte) (((codePoint >>> 6) & 63) | 128);
                        i7 = i14 + 1;
                        bArr[i14] = (byte) ((codePoint & 63) | 128);
                        i6 = i11;
                        i6++;
                    } else {
                        i6 = i11;
                    }
                }
                StringBuilder sb = new StringBuilder(39);
                sb.append("Unpaired surrogate at index ");
                sb.append(i6 - 1);
                throw new IllegalArgumentException(sb.toString());
            } else {
                StringBuilder sb2 = new StringBuilder(37);
                sb2.append("Failed writing ");
                sb2.append(charAt2);
                sb2.append(" at index ");
                sb2.append(i7);
                throw new ArrayIndexOutOfBoundsException(sb2.toString());
            }
            i7 = i3;
            i6++;
        }
        return i7;
    }

    private static void zza(CharSequence charSequence, ByteBuffer byteBuffer) {
        if (byteBuffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        } else if (byteBuffer.hasArray()) {
            try {
                byteBuffer.position(zza(charSequence, byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining()) - byteBuffer.arrayOffset());
            } catch (ArrayIndexOutOfBoundsException e) {
                BufferOverflowException bufferOverflowException = new BufferOverflowException();
                bufferOverflowException.initCause(e);
                throw bufferOverflowException;
            }
        } else {
            zzb(charSequence, byteBuffer);
        }
    }

    private final void zzaO(long j) throws IOException {
        while ((-128 & j) != 0) {
            zzcr((((int) j) & TransportMediator.KEYCODE_MEDIA_PAUSE) | 128);
            j >>>= 7;
        }
        zzcr((int) j);
    }

    public static int zzaP(long j) {
        if ((-128 & j) == 0) {
            return 1;
        }
        if ((-16384 & j) == 0) {
            return 2;
        }
        if ((-2097152 & j) == 0) {
            return 3;
        }
        if ((-268435456 & j) == 0) {
            return 4;
        }
        if ((-34359738368L & j) == 0) {
            return 5;
        }
        if ((-4398046511104L & j) == 0) {
            return 6;
        }
        if ((-562949953421312L & j) == 0) {
            return 7;
        }
        if ((-72057594037927936L & j) == 0) {
            return 8;
        }
        return (j & Long.MIN_VALUE) == 0 ? 9 : 10;
    }

    private final void zzaQ(long j) throws IOException {
        if (this.zzcuV.remaining() >= 8) {
            this.zzcuV.putLong(j);
            return;
        }
        throw new ahy(this.zzcuV.position(), this.zzcuV.limit());
    }

    private static long zzaR(long j) {
        return (j >> 63) ^ (j << 1);
    }

    public static int zzb(int i, aif aif) {
        int zzcs = zzcs(i);
        int zzMl = aif.zzMl();
        return zzcs + zzcu(zzMl) + zzMl;
    }

    private static int zzb(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        int i2 = 0;
        while (i2 < length && charSequence.charAt(i2) < 128) {
            i2++;
        }
        int i3 = length;
        while (true) {
            if (i2 >= length) {
                break;
            }
            char charAt = charSequence.charAt(i2);
            if (charAt < 2048) {
                i3 += (127 - charAt) >>> 31;
                i2++;
            } else {
                int length2 = charSequence.length();
                while (i2 < length2) {
                    char charAt2 = charSequence.charAt(i2);
                    if (charAt2 < 2048) {
                        i += (127 - charAt2) >>> 31;
                    } else {
                        i += 2;
                        if (55296 <= charAt2 && charAt2 <= 57343) {
                            if (Character.codePointAt(charSequence, i2) >= 65536) {
                                i2++;
                            } else {
                                StringBuilder sb = new StringBuilder(39);
                                sb.append("Unpaired surrogate at index ");
                                sb.append(i2);
                                throw new IllegalArgumentException(sb.toString());
                            }
                        }
                    }
                    i2++;
                }
                i3 += i;
            }
        }
        if (i3 >= length) {
            return i3;
        }
        StringBuilder sb2 = new StringBuilder(54);
        sb2.append("UTF-8 length does not fit in int: ");
        sb2.append(((long) i3) + 4294967296L);
        throw new IllegalArgumentException(sb2.toString());
    }

    private static void zzb(CharSequence charSequence, ByteBuffer byteBuffer) {
        int i;
        int length = charSequence.length();
        int i2 = 0;
        while (i2 < length) {
            char charAt = charSequence.charAt(i2);
            if (charAt >= 128) {
                if (charAt < 2048) {
                    i = (charAt >>> 6) | 960;
                } else if (charAt < 55296 || 57343 < charAt) {
                    byteBuffer.put((byte) ((charAt >>> 12) | 480));
                    i = ((charAt >>> 6) & 63) | 128;
                } else {
                    int i3 = i2 + 1;
                    if (i3 != charSequence.length()) {
                        char charAt2 = charSequence.charAt(i3);
                        if (Character.isSurrogatePair(charAt, charAt2)) {
                            int codePoint = Character.toCodePoint(charAt, charAt2);
                            byteBuffer.put((byte) ((codePoint >>> 18) | 240));
                            byteBuffer.put((byte) (((codePoint >>> 12) & 63) | 128));
                            byteBuffer.put((byte) (((codePoint >>> 6) & 63) | 128));
                            byteBuffer.put((byte) ((codePoint & 63) | 128));
                            i2 = i3;
                            i2++;
                        } else {
                            i2 = i3;
                        }
                    }
                    StringBuilder sb = new StringBuilder(39);
                    sb.append("Unpaired surrogate at index ");
                    sb.append(i2 - 1);
                    throw new IllegalArgumentException(sb.toString());
                }
                byteBuffer.put((byte) i);
                charAt = (charAt & '?') | 128;
            }
            byteBuffer.put((byte) charAt);
            i2++;
        }
    }

    public static int zzc(int i, byte[] bArr) {
        return zzcs(i) + zzK(bArr);
    }

    public static ahx zzc(byte[] bArr, int i, int i2) {
        return new ahx(bArr, 0, i2);
    }

    public static int zzcq(int i) {
        if (i >= 0) {
            return zzcu(i);
        }
        return 10;
    }

    private final void zzcr(int i) throws IOException {
        byte b = (byte) i;
        if (this.zzcuV.hasRemaining()) {
            this.zzcuV.put(b);
            return;
        }
        throw new ahy(this.zzcuV.position(), this.zzcuV.limit());
    }

    public static int zzcs(int i) {
        return zzcu(i << 3);
    }

    public static int zzcu(int i) {
        if ((i & -128) == 0) {
            return 1;
        }
        if ((i & -16384) == 0) {
            return 2;
        }
        if ((-2097152 & i) == 0) {
            return 3;
        }
        return (i & -268435456) == 0 ? 4 : 5;
    }

    public static int zzcv(int i) {
        return (i >> 31) ^ (i << 1);
    }

    public static int zze(int i, long j) {
        return zzcs(i) + zzaP(j);
    }

    public static int zzf(int i, long j) {
        return zzcs(i) + zzaP(zzaR(j));
    }

    public static int zzip(String str) {
        int zzb = zzb(str);
        return zzcu(zzb) + zzb;
    }

    public static int zzm(int i, String str) {
        return zzcs(i) + zzip(str);
    }

    public static int zzs(int i, int i2) {
        return zzcs(i) + zzcq(i2);
    }

    public final void zzL(byte[] bArr) throws IOException {
        int length = bArr.length;
        if (this.zzcuV.remaining() >= length) {
            this.zzcuV.put(bArr, 0, length);
            return;
        }
        throw new ahy(this.zzcuV.position(), this.zzcuV.limit());
    }

    public final void zzMc() {
        if (this.zzcuV.remaining() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    public final void zza(int i, double d) throws IOException {
        zzt(i, 1);
        zzaQ(Double.doubleToLongBits(d));
    }

    public final void zza(int i, long j) throws IOException {
        zzt(i, 0);
        zzaO(j);
    }

    public final void zza(int i, aif aif) throws IOException {
        zzt(i, 2);
        zzc(aif);
    }

    public final void zzb(int i, long j) throws IOException {
        zzt(i, 0);
        zzaO(j);
    }

    public final void zzb(int i, byte[] bArr) throws IOException {
        zzt(i, 2);
        zzct(bArr.length);
        zzL(bArr);
    }

    public final void zzc(int i, float f) throws IOException {
        zzt(i, 5);
        int floatToIntBits = Float.floatToIntBits(f);
        if (this.zzcuV.remaining() >= 4) {
            this.zzcuV.putInt(floatToIntBits);
            return;
        }
        throw new ahy(this.zzcuV.position(), this.zzcuV.limit());
    }

    public final void zzc(int i, long j) throws IOException {
        zzt(i, 1);
        zzaQ(j);
    }

    public final void zzc(aif aif) throws IOException {
        zzct(aif.zzMk());
        aif.zza(this);
    }

    public final void zzct(int i) throws IOException {
        while ((i & -128) != 0) {
            zzcr((i & TransportMediator.KEYCODE_MEDIA_PAUSE) | 128);
            i >>>= 7;
        }
        zzcr(i);
    }

    public final void zzd(int i, long j) throws IOException {
        zzt(i, 0);
        zzaO(zzaR(j));
    }

    public final void zzk(int i, boolean z) throws IOException {
        zzt(i, 0);
        byte b = z ? (byte) 1 : 0;
        if (this.zzcuV.hasRemaining()) {
            this.zzcuV.put(b);
            return;
        }
        throw new ahy(this.zzcuV.position(), this.zzcuV.limit());
    }

    public final void zzl(int i, String str) throws IOException {
        zzt(i, 2);
        try {
            int zzcu = zzcu(str.length());
            if (zzcu == zzcu(str.length() * 3)) {
                int position = this.zzcuV.position();
                if (this.zzcuV.remaining() >= zzcu) {
                    this.zzcuV.position(position + zzcu);
                    zza((CharSequence) str, this.zzcuV);
                    int position2 = this.zzcuV.position();
                    this.zzcuV.position(position);
                    zzct((position2 - position) - zzcu);
                    this.zzcuV.position(position2);
                    return;
                }
                throw new ahy(position + zzcu, this.zzcuV.limit());
            }
            zzct(zzb(str));
            zza((CharSequence) str, this.zzcuV);
        } catch (BufferOverflowException e) {
            ahy ahy = new ahy(this.zzcuV.position(), this.zzcuV.limit());
            ahy.initCause(e);
            throw ahy;
        }
    }

    public final void zzr(int i, int i2) throws IOException {
        zzt(i, 0);
        if (i2 >= 0) {
            zzct(i2);
        } else {
            zzaO((long) i2);
        }
    }

    public final void zzt(int i, int i2) throws IOException {
        zzct((i << 3) | i2);
    }
}
