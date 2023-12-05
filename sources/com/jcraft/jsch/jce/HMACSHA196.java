package com.jcraft.jsch.jce;

public class HMACSHA196 extends HMACSHA1 {
    private final byte[] _buf20 = new byte[20];

    public int getBlockSize() {
        return 12;
    }

    public HMACSHA196() {
        this.name = "hmac-sha1-96";
    }

    public void doFinal(byte[] bArr, int i) {
        super.doFinal(this._buf20, 0);
        System.arraycopy(this._buf20, 0, bArr, i, 12);
    }
}
