package com.jcraft.jsch.jce;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.DSAKey;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;

public class KeyPairGenDSA implements com.jcraft.jsch.KeyPairGenDSA {
    byte[] g;
    byte[] p;
    byte[] q;
    byte[] x;
    byte[] y;

    public void init(int i) throws Exception {
        KeyPairGenerator instance = KeyPairGenerator.getInstance("DSA");
        instance.initialize(i, new SecureRandom());
        KeyPair generateKeyPair = instance.generateKeyPair();
        PublicKey publicKey = generateKeyPair.getPublic();
        PrivateKey privateKey = generateKeyPair.getPrivate();
        this.x = ((DSAPrivateKey) privateKey).getX().toByteArray();
        this.y = ((DSAPublicKey) publicKey).getY().toByteArray();
        DSAParams params = ((DSAKey) privateKey).getParams();
        this.p = params.getP().toByteArray();
        this.q = params.getQ().toByteArray();
        this.g = params.getG().toByteArray();
    }

    public byte[] getX() {
        return this.x;
    }

    public byte[] getY() {
        return this.y;
    }

    public byte[] getP() {
        return this.p;
    }

    public byte[] getQ() {
        return this.q;
    }

    public byte[] getG() {
        return this.g;
    }
}
