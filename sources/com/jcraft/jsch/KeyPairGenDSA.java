package com.jcraft.jsch;

public interface KeyPairGenDSA {
    byte[] getG();

    byte[] getP();

    byte[] getQ();

    byte[] getX();

    byte[] getY();

    void init(int i) throws Exception;
}
