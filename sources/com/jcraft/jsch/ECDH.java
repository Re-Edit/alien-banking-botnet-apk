package com.jcraft.jsch;

public interface ECDH {
    byte[] getQ() throws Exception;

    byte[] getSecret(byte[] bArr, byte[] bArr2) throws Exception;

    void init(int i) throws Exception;

    boolean validate(byte[] bArr, byte[] bArr2) throws Exception;
}
