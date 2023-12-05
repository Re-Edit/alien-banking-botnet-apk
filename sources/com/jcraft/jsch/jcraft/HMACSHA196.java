package com.jcraft.jsch.jcraft;

public class HMACSHA196 extends HMACSHA1 {
    private static final int BSIZE = 12;
    private static final String name = "hmac-sha1-96";
    private final byte[] _buf16 = new byte[20];

    public int getBlockSize() {
        return 12;
    }

    public String getName() {
        return name;
    }

    public void doFinal(byte[] bArr, int i) {
        super.doFinal(this._buf16, 0);
        System.arraycopy(this._buf16, 0, bArr, i, 12);
    }
}
