package com.jcraft.jsch.jce;

import com.jcraft.jsch.JSchException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;

public class KeyPairGenECDSA implements com.jcraft.jsch.KeyPairGenECDSA {
    byte[] d;
    ECParameterSpec params;
    ECPrivateKey prvKey;
    ECPublicKey pubKey;
    byte[] r;
    byte[] s;

    public void init(int i) throws Exception {
        String str;
        if (i == 256) {
            str = "secp256r1";
        } else if (i == 384) {
            str = "secp384r1";
        } else if (i == 521) {
            str = "secp521r1";
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("unsupported key size: ");
            stringBuffer.append(i);
            throw new JSchException(stringBuffer.toString());
        }
        for (int i2 = 0; i2 < 1000; i2++) {
            KeyPairGenerator instance = KeyPairGenerator.getInstance("EC");
            instance.initialize(new ECGenParameterSpec(str));
            KeyPair genKeyPair = instance.genKeyPair();
            this.prvKey = (ECPrivateKey) genKeyPair.getPrivate();
            this.pubKey = (ECPublicKey) genKeyPair.getPublic();
            this.params = this.pubKey.getParams();
            this.d = this.prvKey.getS().toByteArray();
            ECPoint w = this.pubKey.getW();
            this.r = w.getAffineX().toByteArray();
            this.s = w.getAffineY().toByteArray();
            byte[] bArr = this.r;
            if (bArr.length == this.s.length && ((i == 256 && bArr.length == 32) || ((i == 384 && this.r.length == 48) || (i == 521 && this.r.length == 66)))) {
                break;
            }
        }
        byte[] bArr2 = this.d;
        if (bArr2.length < this.r.length) {
            this.d = insert0(bArr2);
        }
    }

    public byte[] getD() {
        return this.d;
    }

    public byte[] getR() {
        return this.r;
    }

    public byte[] getS() {
        return this.s;
    }

    /* access modifiers changed from: package-private */
    public ECPublicKey getPublicKey() {
        return this.pubKey;
    }

    /* access modifiers changed from: package-private */
    public ECPrivateKey getPrivateKey() {
        return this.prvKey;
    }

    private byte[] insert0(byte[] bArr) {
        byte[] bArr2 = new byte[(bArr.length + 1)];
        System.arraycopy(bArr, 0, bArr2, 1, bArr.length);
        bzero(bArr);
        return bArr2;
    }

    private byte[] chop0(byte[] bArr) {
        if (bArr[0] != 0 || (bArr[1] & 128) == 0) {
            return bArr;
        }
        byte[] bArr2 = new byte[(bArr.length - 1)];
        System.arraycopy(bArr, 1, bArr2, 0, bArr2.length);
        bzero(bArr);
        return bArr2;
    }

    private void bzero(byte[] bArr) {
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = 0;
        }
    }
}
