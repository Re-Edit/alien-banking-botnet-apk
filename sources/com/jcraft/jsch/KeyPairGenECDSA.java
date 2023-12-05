package com.jcraft.jsch;

public interface KeyPairGenECDSA {
    byte[] getD();

    byte[] getR();

    byte[] getS();

    void init(int i) throws Exception;
}
