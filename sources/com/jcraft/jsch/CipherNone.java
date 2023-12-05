package com.jcraft.jsch;

public class CipherNone implements Cipher {
    private static final int bsize = 16;
    private static final int ivsize = 8;

    public int getBlockSize() {
        return 16;
    }

    public int getIVSize() {
        return 8;
    }

    public void init(int i, byte[] bArr, byte[] bArr2) throws Exception {
    }

    public boolean isCBC() {
        return false;
    }

    public void update(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws Exception {
    }
}
