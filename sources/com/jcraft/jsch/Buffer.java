package com.jcraft.jsch;

import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.MotionEventCompat;

public class Buffer {
    byte[] buffer;
    int index;
    int s;
    final byte[] tmp;

    public Buffer(int i) {
        this.tmp = new byte[4];
        this.buffer = new byte[i];
        this.index = 0;
        this.s = 0;
    }

    public Buffer(byte[] bArr) {
        this.tmp = new byte[4];
        this.buffer = bArr;
        this.index = 0;
        this.s = 0;
    }

    public Buffer() {
        this(20480);
    }

    public void putByte(byte b) {
        byte[] bArr = this.buffer;
        int i = this.index;
        this.index = i + 1;
        bArr[i] = b;
    }

    public void putByte(byte[] bArr) {
        putByte(bArr, 0, bArr.length);
    }

    public void putByte(byte[] bArr, int i, int i2) {
        System.arraycopy(bArr, i, this.buffer, this.index, i2);
        this.index += i2;
    }

    public void putString(byte[] bArr) {
        putString(bArr, 0, bArr.length);
    }

    public void putString(byte[] bArr, int i, int i2) {
        putInt(i2);
        putByte(bArr, i, i2);
    }

    public void putInt(int i) {
        byte[] bArr = this.tmp;
        bArr[0] = (byte) (i >>> 24);
        bArr[1] = (byte) (i >>> 16);
        bArr[2] = (byte) (i >>> 8);
        bArr[3] = (byte) i;
        System.arraycopy(bArr, 0, this.buffer, this.index, 4);
        this.index += 4;
    }

    public void putLong(long j) {
        byte[] bArr = this.tmp;
        bArr[0] = (byte) ((int) (j >>> 56));
        bArr[1] = (byte) ((int) (j >>> 48));
        bArr[2] = (byte) ((int) (j >>> 40));
        bArr[3] = (byte) ((int) (j >>> 32));
        System.arraycopy(bArr, 0, this.buffer, this.index, 4);
        byte[] bArr2 = this.tmp;
        bArr2[0] = (byte) ((int) (j >>> 24));
        bArr2[1] = (byte) ((int) (j >>> 16));
        bArr2[2] = (byte) ((int) (j >>> 8));
        bArr2[3] = (byte) ((int) j);
        System.arraycopy(bArr2, 0, this.buffer, this.index + 4, 4);
        this.index += 8;
    }

    /* access modifiers changed from: package-private */
    public void skip(int i) {
        this.index += i;
    }

    /* access modifiers changed from: package-private */
    public void putPad(int i) {
        while (i > 0) {
            byte[] bArr = this.buffer;
            int i2 = this.index;
            this.index = i2 + 1;
            bArr[i2] = 0;
            i--;
        }
    }

    public void putMPInt(byte[] bArr) {
        int length = bArr.length;
        if ((bArr[0] & 128) != 0) {
            putInt(length + 1);
            putByte((byte) 0);
        } else {
            putInt(length);
        }
        putByte(bArr);
    }

    public int getLength() {
        return this.index - this.s;
    }

    public int getOffSet() {
        return this.s;
    }

    public void setOffSet(int i) {
        this.s = i;
    }

    public long getLong() {
        return ((((long) getInt()) & 4294967295L) << 32) | (4294967295L & ((long) getInt()));
    }

    public int getInt() {
        return ((getShort() << 16) & SupportMenu.CATEGORY_MASK) | (getShort() & SupportMenu.USER_MASK);
    }

    public long getUInt() {
        return (((((((long) getByte()) << 8) & 65280) | ((long) (getByte() & 255))) << 16) & -65536) | (((65280 & (((long) getByte()) << 8)) | ((long) (getByte() & 255))) & 65535);
    }

    /* access modifiers changed from: package-private */
    public int getShort() {
        return ((getByte() << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (getByte() & 255);
    }

    public int getByte() {
        byte[] bArr = this.buffer;
        int i = this.s;
        this.s = i + 1;
        return bArr[i] & 255;
    }

    public void getByte(byte[] bArr) {
        getByte(bArr, 0, bArr.length);
    }

    /* access modifiers changed from: package-private */
    public void getByte(byte[] bArr, int i, int i2) {
        System.arraycopy(this.buffer, this.s, bArr, i, i2);
        this.s += i2;
    }

    public int getByte(int i) {
        int i2 = this.s;
        this.s = i + i2;
        return i2;
    }

    public byte[] getMPInt() {
        int i = getInt();
        if (i < 0 || i > 8192) {
            i = 8192;
        }
        byte[] bArr = new byte[i];
        getByte(bArr, 0, i);
        return bArr;
    }

    public byte[] getMPIntBits() {
        int i = (getInt() + 7) / 8;
        byte[] bArr = new byte[i];
        getByte(bArr, 0, i);
        if ((bArr[0] & 128) == 0) {
            return bArr;
        }
        byte[] bArr2 = new byte[(bArr.length + 1)];
        bArr2[0] = 0;
        System.arraycopy(bArr, 0, bArr2, 1, bArr.length);
        return bArr2;
    }

    public byte[] getString() {
        int i = getInt();
        if (i < 0 || i > 262144) {
            i = 262144;
        }
        byte[] bArr = new byte[i];
        getByte(bArr, 0, i);
        return bArr;
    }

    /* access modifiers changed from: package-private */
    public byte[] getString(int[] iArr, int[] iArr2) {
        int i = getInt();
        iArr[0] = getByte(i);
        iArr2[0] = i;
        return this.buffer;
    }

    public void reset() {
        this.index = 0;
        this.s = 0;
    }

    public void shift() {
        int i = this.s;
        if (i != 0) {
            byte[] bArr = this.buffer;
            System.arraycopy(bArr, i, bArr, 0, this.index - i);
            this.index -= this.s;
            this.s = 0;
        }
    }

    /* access modifiers changed from: package-private */
    public void rewind() {
        this.s = 0;
    }

    /* access modifiers changed from: package-private */
    public byte getCommand() {
        return this.buffer[5];
    }

    /* access modifiers changed from: package-private */
    public void checkFreeSize(int i) {
        int i2 = this.index + i + 128;
        byte[] bArr = this.buffer;
        if (bArr.length < i2) {
            int length = bArr.length * 2;
            if (length < i2) {
                length = i2;
            }
            byte[] bArr2 = new byte[length];
            System.arraycopy(this.buffer, 0, bArr2, 0, this.index);
            this.buffer = bArr2;
        }
    }

    /* access modifiers changed from: package-private */
    public byte[][] getBytes(int i, String str) throws JSchException {
        byte[][] bArr = new byte[i][];
        int i2 = 0;
        while (i2 < i) {
            int i3 = getInt();
            if (getLength() >= i3) {
                bArr[i2] = new byte[i3];
                getByte(bArr[i2]);
                i2++;
            } else {
                throw new JSchException(str);
            }
        }
        return bArr;
    }

    static Buffer fromBytes(byte[][] bArr) {
        int length = bArr.length * 4;
        for (byte[] length2 : bArr) {
            length += length2.length;
        }
        Buffer buffer2 = new Buffer(length);
        for (byte[] putString : bArr) {
            buffer2.putString(putString);
        }
        return buffer2;
    }
}
