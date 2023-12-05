package com.jcraft.jsch;

public interface DH {
    void checkRange() throws Exception;

    byte[] getE() throws Exception;

    byte[] getK() throws Exception;

    void init() throws Exception;

    void setF(byte[] bArr);

    void setG(byte[] bArr);

    void setP(byte[] bArr);
}
