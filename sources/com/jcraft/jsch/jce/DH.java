package com.jcraft.jsch.jce;

import com.jcraft.jsch.JSchException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;

public class DH implements com.jcraft.jsch.DH {
    BigInteger K;
    byte[] K_array;
    BigInteger e;
    byte[] e_array;
    BigInteger f;
    BigInteger g;
    private KeyAgreement myKeyAgree;
    private KeyPairGenerator myKpairGen;
    BigInteger p;

    public void checkRange() throws Exception {
    }

    public void init() throws Exception {
        this.myKpairGen = KeyPairGenerator.getInstance("DH");
        this.myKeyAgree = KeyAgreement.getInstance("DH");
    }

    public byte[] getE() throws Exception {
        if (this.e == null) {
            this.myKpairGen.initialize(new DHParameterSpec(this.p, this.g));
            KeyPair generateKeyPair = this.myKpairGen.generateKeyPair();
            this.myKeyAgree.init(generateKeyPair.getPrivate());
            this.e = ((DHPublicKey) generateKeyPair.getPublic()).getY();
            this.e_array = this.e.toByteArray();
        }
        return this.e_array;
    }

    public byte[] getK() throws Exception {
        if (this.K == null) {
            this.myKeyAgree.doPhase(KeyFactory.getInstance("DH").generatePublic(new DHPublicKeySpec(this.f, this.p, this.g)), true);
            byte[] generateSecret = this.myKeyAgree.generateSecret();
            this.K = new BigInteger(1, generateSecret);
            this.K_array = this.K.toByteArray();
            this.K_array = generateSecret;
        }
        return this.K_array;
    }

    public void setP(byte[] bArr) {
        setP(new BigInteger(1, bArr));
    }

    public void setG(byte[] bArr) {
        setG(new BigInteger(1, bArr));
    }

    public void setF(byte[] bArr) {
        setF(new BigInteger(1, bArr));
    }

    /* access modifiers changed from: package-private */
    public void setP(BigInteger bigInteger) {
        this.p = bigInteger;
    }

    /* access modifiers changed from: package-private */
    public void setG(BigInteger bigInteger) {
        this.g = bigInteger;
    }

    /* access modifiers changed from: package-private */
    public void setF(BigInteger bigInteger) {
        this.f = bigInteger;
    }

    private void checkRange(BigInteger bigInteger) throws Exception {
        BigInteger bigInteger2 = BigInteger.ONE;
        BigInteger subtract = this.p.subtract(bigInteger2);
        if (bigInteger2.compareTo(bigInteger) >= 0 || bigInteger.compareTo(subtract) >= 0) {
            throw new JSchException("invalid DH value");
        }
    }
}
