package com.jcraft.jsch;

public interface GSSContext {
    void create(String str, String str2) throws JSchException;

    void dispose();

    byte[] getMIC(byte[] bArr, int i, int i2);

    byte[] init(byte[] bArr, int i, int i2) throws JSchException;

    boolean isEstablished();
}
