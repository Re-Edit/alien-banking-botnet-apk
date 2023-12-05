package com.jcraft.jsch.jce;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class KeyPairGenRSA implements com.jcraft.jsch.KeyPairGenRSA {
    byte[] c;
    byte[] d;
    byte[] e;
    byte[] ep;
    byte[] eq;
    byte[] n;
    byte[] p;
    byte[] q;

    public void init(int i) throws Exception {
        KeyPairGenerator instance = KeyPairGenerator.getInstance("RSA");
        instance.initialize(i, new SecureRandom());
        KeyPair generateKeyPair = instance.generateKeyPair();
        PublicKey publicKey = generateKeyPair.getPublic();
        PrivateKey privateKey = generateKeyPair.getPrivate();
        RSAPrivateKey rSAPrivateKey = (RSAPrivateKey) privateKey;
        this.d = rSAPrivateKey.getPrivateExponent().toByteArray();
        this.e = ((RSAPublicKey) publicKey).getPublicExponent().toByteArray();
        this.n = rSAPrivateKey.getModulus().toByteArray();
        RSAPrivateCrtKey rSAPrivateCrtKey = (RSAPrivateCrtKey) privateKey;
        this.c = rSAPrivateCrtKey.getCrtCoefficient().toByteArray();
        this.ep = rSAPrivateCrtKey.getPrimeExponentP().toByteArray();
        this.eq = rSAPrivateCrtKey.getPrimeExponentQ().toByteArray();
        this.p = rSAPrivateCrtKey.getPrimeP().toByteArray();
        this.q = rSAPrivateCrtKey.getPrimeQ().toByteArray();
    }

    public byte[] getD() {
        return this.d;
    }

    public byte[] getE() {
        return this.e;
    }

    public byte[] getN() {
        return this.n;
    }

    public byte[] getC() {
        return this.c;
    }

    public byte[] getEP() {
        return this.ep;
    }

    public byte[] getEQ() {
        return this.eq;
    }

    public byte[] getP() {
        return this.p;
    }

    public byte[] getQ() {
        return this.q;
    }
}
