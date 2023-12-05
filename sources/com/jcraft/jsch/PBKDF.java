package com.jcraft.jsch;

public interface PBKDF {
    byte[] getKey(byte[] bArr, byte[] bArr2, int i, int i2);
}
