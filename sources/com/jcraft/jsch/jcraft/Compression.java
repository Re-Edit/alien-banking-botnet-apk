package com.jcraft.jsch.jcraft;

import com.jcraft.jzlib.ZStream;
import java.io.PrintStream;

public class Compression implements com.jcraft.jsch.Compression {
    private static final int BUF_SIZE = 4096;
    private final int buffer_margin = 52;
    private byte[] inflated_buf;
    private ZStream stream = new ZStream();
    private byte[] tmpbuf = new byte[4096];
    private int type;

    public void init(int i, int i2) {
        if (i == 1) {
            this.stream.deflateInit(i2);
            this.type = 1;
        } else if (i == 0) {
            this.stream.inflateInit();
            this.inflated_buf = new byte[4096];
            this.type = 0;
        }
    }

    public byte[] compress(byte[] bArr, int i, int[] iArr) {
        ZStream zStream = this.stream;
        zStream.next_in = bArr;
        zStream.next_in_index = i;
        zStream.avail_in = iArr[0] - i;
        do {
            ZStream zStream2 = this.stream;
            zStream2.next_out = this.tmpbuf;
            zStream2.next_out_index = 0;
            zStream2.avail_out = 4096;
            int deflate = zStream2.deflate(1);
            if (deflate != 0) {
                PrintStream printStream = System.err;
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("compress: deflate returnd ");
                stringBuffer.append(deflate);
                printStream.println(stringBuffer.toString());
            } else {
                int i2 = 4096 - this.stream.avail_out;
                int i3 = i + i2;
                int i4 = i3 + 52;
                if (bArr.length < i4) {
                    byte[] bArr2 = new byte[(i4 * 2)];
                    System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                    bArr = bArr2;
                }
                System.arraycopy(this.tmpbuf, 0, bArr, i, i2);
                i = i3;
            }
        } while (this.stream.avail_out == 0);
        iArr[0] = i;
        return bArr;
    }

    public byte[] uncompress(byte[] bArr, int i, int[] iArr) {
        ZStream zStream = this.stream;
        zStream.next_in = bArr;
        zStream.next_in_index = i;
        zStream.avail_in = iArr[0];
        int i2 = 0;
        while (true) {
            ZStream zStream2 = this.stream;
            zStream2.next_out = this.tmpbuf;
            zStream2.next_out_index = 0;
            zStream2.avail_out = 4096;
            int inflate = zStream2.inflate(1);
            if (inflate == -5) {
                if (i2 > bArr.length - i) {
                    byte[] bArr2 = new byte[(i2 + i)];
                    System.arraycopy(bArr, 0, bArr2, 0, i);
                    System.arraycopy(this.inflated_buf, 0, bArr2, i, i2);
                    bArr = bArr2;
                } else {
                    System.arraycopy(this.inflated_buf, 0, bArr, i, i2);
                }
                iArr[0] = i2;
                return bArr;
            } else if (inflate != 0) {
                PrintStream printStream = System.err;
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("uncompress: inflate returnd ");
                stringBuffer.append(inflate);
                printStream.println(stringBuffer.toString());
                return null;
            } else {
                int i3 = i2 + 4096;
                if (this.inflated_buf.length < i3 - this.stream.avail_out) {
                    int length = this.inflated_buf.length * 2;
                    if (length < i3 - this.stream.avail_out) {
                        length = i3 - this.stream.avail_out;
                    }
                    byte[] bArr3 = new byte[length];
                    System.arraycopy(this.inflated_buf, 0, bArr3, 0, i2);
                    this.inflated_buf = bArr3;
                }
                System.arraycopy(this.tmpbuf, 0, this.inflated_buf, i2, 4096 - this.stream.avail_out);
                i2 += 4096 - this.stream.avail_out;
                iArr[0] = i2;
            }
        }
    }
}
