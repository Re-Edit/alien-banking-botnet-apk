package com.jcraft.jsch;

public class Packet {
    private static Random random;
    byte[] ba4 = new byte[4];
    Buffer buffer;

    static void setRandom(Random random2) {
        random = random2;
    }

    public Packet(Buffer buffer2) {
        this.buffer = buffer2;
    }

    public void reset() {
        this.buffer.index = 5;
    }

    /* access modifiers changed from: package-private */
    public void padding(int i) {
        int i2 = this.buffer.index;
        int i3 = (-i2) & (i - 1);
        if (i3 < i) {
            i3 += i;
        }
        int i4 = (i2 + i3) - 4;
        byte[] bArr = this.ba4;
        bArr[0] = (byte) (i4 >>> 24);
        bArr[1] = (byte) (i4 >>> 16);
        bArr[2] = (byte) (i4 >>> 8);
        bArr[3] = (byte) i4;
        System.arraycopy(bArr, 0, this.buffer.buffer, 0, 4);
        this.buffer.buffer[4] = (byte) i3;
        synchronized (random) {
            random.fill(this.buffer.buffer, this.buffer.index, i3);
        }
        this.buffer.skip(i3);
    }

    /* access modifiers changed from: package-private */
    public int shift(int i, int i2, int i3) {
        int i4 = i + 5 + 9;
        int i5 = (-i4) & (i2 - 1);
        if (i5 < i2) {
            i5 += i2;
        }
        int i6 = i5 + i4 + i3 + 32;
        if (this.buffer.buffer.length < (((this.buffer.index + i6) - 5) - 9) - i) {
            byte[] bArr = new byte[((((this.buffer.index + i6) - 5) - 9) - i)];
            System.arraycopy(this.buffer.buffer, 0, bArr, 0, this.buffer.buffer.length);
            this.buffer.buffer = bArr;
        }
        System.arraycopy(this.buffer.buffer, i4, this.buffer.buffer, i6, ((this.buffer.index - 5) - 9) - i);
        Buffer buffer2 = this.buffer;
        buffer2.index = 10;
        buffer2.putInt(i);
        this.buffer.index = i4;
        return i6;
    }

    /* access modifiers changed from: package-private */
    public void unshift(byte b, int i, int i2, int i3) {
        System.arraycopy(this.buffer.buffer, i2, this.buffer.buffer, 14, i3);
        this.buffer.buffer[5] = b;
        Buffer buffer2 = this.buffer;
        buffer2.index = 6;
        buffer2.putInt(i);
        this.buffer.putInt(i3);
        this.buffer.index = i3 + 5 + 9;
    }

    /* access modifiers changed from: package-private */
    public Buffer getBuffer() {
        return this.buffer;
    }
}
