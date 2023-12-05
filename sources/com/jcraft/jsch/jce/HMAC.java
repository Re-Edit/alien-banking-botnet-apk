package com.jcraft.jsch.jce;

import com.jcraft.jsch.MAC;
import javax.crypto.Mac;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

abstract class HMAC implements MAC {
    protected String algorithm;
    protected int bsize;
    private Mac mac;
    protected String name;
    private final byte[] tmp = new byte[4];

    HMAC() {
    }

    public int getBlockSize() {
        return this.bsize;
    }

    public void init(byte[] bArr) throws Exception {
        int length = bArr.length;
        int i = this.bsize;
        if (length > i) {
            byte[] bArr2 = new byte[i];
            System.arraycopy(bArr, 0, bArr2, 0, i);
            bArr = bArr2;
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, this.algorithm);
        this.mac = Mac.getInstance(this.algorithm);
        this.mac.init(secretKeySpec);
    }

    public void update(int i) {
        byte[] bArr = this.tmp;
        bArr[0] = (byte) (i >>> 24);
        bArr[1] = (byte) (i >>> 16);
        bArr[2] = (byte) (i >>> 8);
        bArr[3] = (byte) i;
        update(bArr, 0, 4);
    }

    public void update(byte[] bArr, int i, int i2) {
        this.mac.update(bArr, i, i2);
    }

    public void doFinal(byte[] bArr, int i) {
        try {
            this.mac.doFinal(bArr, i);
        } catch (ShortBufferException e) {
            System.err.println(e);
        }
    }

    public String getName() {
        return this.name;
    }
}
