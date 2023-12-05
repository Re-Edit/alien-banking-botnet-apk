package com.jcraft.jsch.jce;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class SignatureRSA implements com.jcraft.jsch.SignatureRSA {
    KeyFactory keyFactory;
    Signature signature;

    public void init() throws Exception {
        this.signature = Signature.getInstance("SHA1withRSA");
        this.keyFactory = KeyFactory.getInstance("RSA");
    }

    public void setPubKey(byte[] bArr, byte[] bArr2) throws Exception {
        this.signature.initVerify(this.keyFactory.generatePublic(new RSAPublicKeySpec(new BigInteger(bArr2), new BigInteger(bArr))));
    }

    public void setPrvKey(byte[] bArr, byte[] bArr2) throws Exception {
        this.signature.initSign(this.keyFactory.generatePrivate(new RSAPrivateKeySpec(new BigInteger(bArr2), new BigInteger(bArr))));
    }

    public byte[] sign() throws Exception {
        return this.signature.sign();
    }

    public void update(byte[] bArr) throws Exception {
        this.signature.update(bArr);
    }

    public boolean verify(byte[] bArr) throws Exception {
        if (bArr[0] == 0 && bArr[1] == 0 && bArr[2] == 0) {
            int i = 4 + (((bArr[1] << 16) & 16711680) | ((bArr[0] << 24) & -16777216) | ((bArr[2] << 8) & 65280) | (bArr[3] & 255));
            int i2 = i + 1;
            int i3 = i2 + 1;
            byte b = ((bArr[i2] << 16) & 16711680) | ((bArr[i] << 24) & -16777216);
            int i4 = i3 + 1;
            byte b2 = b | ((bArr[i3] << 8) & 65280);
            int i5 = i4 + 1;
            int i6 = b2 | (bArr[i4] & 255);
            byte[] bArr2 = new byte[i6];
            System.arraycopy(bArr, i5, bArr2, 0, i6);
            bArr = bArr2;
        }
        return this.signature.verify(bArr);
    }
}
