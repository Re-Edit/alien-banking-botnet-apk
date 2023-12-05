package com.jcraft.jsch;

public interface Cipher {
    public static final int DECRYPT_MODE = 1;
    public static final int ENCRYPT_MODE = 0;

    int getBlockSize();

    int getIVSize();

    void init(int i, byte[] bArr, byte[] bArr2) throws Exception;

    boolean isCBC();

    void update(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws Exception;
}
