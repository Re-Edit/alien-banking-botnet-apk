package com.jcraft.jsch.jce;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;

public class SignatureDSA implements com.jcraft.jsch.SignatureDSA {
    KeyFactory keyFactory;
    Signature signature;

    public void init() throws Exception {
        this.signature = Signature.getInstance("SHA1withDSA");
        this.keyFactory = KeyFactory.getInstance("DSA");
    }

    public void setPubKey(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) throws Exception {
        this.signature.initVerify(this.keyFactory.generatePublic(new DSAPublicKeySpec(new BigInteger(bArr), new BigInteger(bArr2), new BigInteger(bArr3), new BigInteger(bArr4))));
    }

    public void setPrvKey(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) throws Exception {
        this.signature.initSign(this.keyFactory.generatePrivate(new DSAPrivateKeySpec(new BigInteger(bArr), new BigInteger(bArr2), new BigInteger(bArr3), new BigInteger(bArr4))));
    }

    public byte[] sign() throws Exception {
        byte[] sign = this.signature.sign();
        int i = sign[3] & 255;
        byte[] bArr = new byte[i];
        System.arraycopy(sign, 4, bArr, 0, bArr.length);
        int i2 = 4 + i;
        int i3 = 1;
        int i4 = i2 + 1;
        int i5 = i4 + 1;
        byte[] bArr2 = new byte[(sign[i4] & 255)];
        System.arraycopy(sign, i5, bArr2, 0, bArr2.length);
        byte[] bArr3 = new byte[40];
        int i6 = 20;
        System.arraycopy(bArr, bArr.length > 20 ? 1 : 0, bArr3, bArr.length > 20 ? 0 : 20 - bArr.length, bArr.length > 20 ? 20 : bArr.length);
        if (bArr2.length <= 20) {
            i3 = 0;
        }
        int length = bArr2.length > 20 ? 20 : 40 - bArr2.length;
        if (bArr2.length <= 20) {
            i6 = bArr2.length;
        }
        System.arraycopy(bArr2, i3, bArr3, length, i6);
        return bArr3;
    }

    public void update(byte[] bArr) throws Exception {
        this.signature.update(bArr);
    }

    public boolean verify(byte[] bArr) throws Exception {
        if (bArr[0] == 0 && bArr[1] == 0 && bArr[2] == 0) {
            int i = (((bArr[0] << 24) & -16777216) | ((bArr[1] << 16) & 16711680) | ((bArr[2] << 8) & 65280) | (bArr[3] & 255)) + 4;
            int i2 = i + 1;
            int i3 = i2 + 1;
            byte b = ((bArr[i] << 24) & -16777216) | ((bArr[i2] << 16) & 16711680);
            int i4 = i3 + 1;
            byte b2 = b | ((bArr[i3] << 8) & 65280);
            int i5 = i4 + 1;
            int i6 = b2 | (bArr[i4] & 255);
            byte[] bArr2 = new byte[i6];
            System.arraycopy(bArr, i5, bArr2, 0, i6);
            bArr = bArr2;
        }
        byte b3 = (bArr[0] & 128) != 0 ? (byte) 1 : 0;
        byte b4 = (bArr[20] & 128) != 0 ? (byte) 1 : 0;
        byte[] bArr3 = new byte[(bArr.length + 6 + b3 + b4)];
        bArr3[0] = 48;
        bArr3[1] = 44;
        bArr3[1] = (byte) (bArr3[1] + b3);
        bArr3[1] = (byte) (bArr3[1] + b4);
        bArr3[2] = 2;
        bArr3[3] = 20;
        bArr3[3] = (byte) (bArr3[3] + b3);
        System.arraycopy(bArr, 0, bArr3, b3 + 4, 20);
        bArr3[bArr3[3] + 4] = 2;
        bArr3[bArr3[3] + 5] = 20;
        int i7 = bArr3[3] + 5;
        bArr3[i7] = (byte) (bArr3[i7] + b4);
        System.arraycopy(bArr, 20, bArr3, bArr3[3] + 6 + b4, 20);
        return this.signature.verify(bArr3);
    }
}
