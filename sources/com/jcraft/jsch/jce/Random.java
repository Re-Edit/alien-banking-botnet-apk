package com.jcraft.jsch.jce;

import java.security.SecureRandom;

public class Random implements com.jcraft.jsch.Random {
    private SecureRandom random;
    private byte[] tmp;

    public Random() {
        this.tmp = new byte[16];
        this.random = null;
        this.random = new SecureRandom();
    }

    public void fill(byte[] bArr, int i, int i2) {
        if (i2 > this.tmp.length) {
            this.tmp = new byte[i2];
        }
        this.random.nextBytes(this.tmp);
        System.arraycopy(this.tmp, 0, bArr, i, i2);
    }
}
