package com.jcraft.jsch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

public class IO {
    InputStream in;
    private boolean in_dontclose = false;
    OutputStream out;
    private boolean out_dontclose = false;
    OutputStream out_ext;
    private boolean out_ext_dontclose = false;

    /* access modifiers changed from: package-private */
    public void setOutputStream(OutputStream outputStream) {
        this.out = outputStream;
    }

    /* access modifiers changed from: package-private */
    public void setOutputStream(OutputStream outputStream, boolean z) {
        this.out_dontclose = z;
        setOutputStream(outputStream);
    }

    /* access modifiers changed from: package-private */
    public void setExtOutputStream(OutputStream outputStream) {
        this.out_ext = outputStream;
    }

    /* access modifiers changed from: package-private */
    public void setExtOutputStream(OutputStream outputStream, boolean z) {
        this.out_ext_dontclose = z;
        setExtOutputStream(outputStream);
    }

    /* access modifiers changed from: package-private */
    public void setInputStream(InputStream inputStream) {
        this.in = inputStream;
    }

    /* access modifiers changed from: package-private */
    public void setInputStream(InputStream inputStream, boolean z) {
        this.in_dontclose = z;
        setInputStream(inputStream);
    }

    public void put(Packet packet) throws IOException, SocketException {
        this.out.write(packet.buffer.buffer, 0, packet.buffer.index);
        this.out.flush();
    }

    /* access modifiers changed from: package-private */
    public void put(byte[] bArr, int i, int i2) throws IOException {
        this.out.write(bArr, i, i2);
        this.out.flush();
    }

    /* access modifiers changed from: package-private */
    public void put_ext(byte[] bArr, int i, int i2) throws IOException {
        this.out_ext.write(bArr, i, i2);
        this.out_ext.flush();
    }

    /* access modifiers changed from: package-private */
    public int getByte() throws IOException {
        return this.in.read();
    }

    /* access modifiers changed from: package-private */
    public void getByte(byte[] bArr) throws IOException {
        getByte(bArr, 0, bArr.length);
    }

    /* access modifiers changed from: package-private */
    public void getByte(byte[] bArr, int i, int i2) throws IOException {
        do {
            int read = this.in.read(bArr, i, i2);
            if (read >= 0) {
                i += read;
                i2 -= read;
            } else {
                throw new IOException("End of IO Stream Read");
            }
        } while (i2 > 0);
    }

    /* access modifiers changed from: package-private */
    public void out_close() {
        try {
            if (this.out != null && !this.out_dontclose) {
                this.out.close();
            }
            this.out = null;
        } catch (Exception unused) {
        }
    }

    public void close() {
        try {
            if (this.in != null && !this.in_dontclose) {
                this.in.close();
            }
            this.in = null;
        } catch (Exception unused) {
        }
        out_close();
        try {
            if (this.out_ext != null && !this.out_ext_dontclose) {
                this.out_ext.close();
            }
            this.out_ext = null;
        } catch (Exception unused2) {
        }
    }
}
