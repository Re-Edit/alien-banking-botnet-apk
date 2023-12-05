package com.jcraft.jsch.jce;

public class HMACMD596 extends HMACMD5 {
    private final byte[] _buf16 = new byte[16];

    public int getBlockSize() {
        return 12;
    }

    public HMACMD596() {
        this.name = "hmac-md5-96";
    }

    public void doFinal(byte[] bArr, int i) {
        super.doFinal(this._buf16, 0);
        System.arraycopy(this._buf16, 0, bArr, i, 12);
    }
}
