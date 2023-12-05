package com.jcraft.jsch.jcraft;

public class HMACMD596 extends HMACMD5 {
    private static final int BSIZE = 12;
    private static final String name = "hmac-md5-96";
    private final byte[] _buf16 = new byte[16];

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
