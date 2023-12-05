package com.jcraft.jsch;

public interface Identity {
    void clear();

    boolean decrypt();

    String getAlgName();

    String getName();

    byte[] getPublicKeyBlob();

    byte[] getSignature(byte[] bArr);

    boolean isEncrypted();

    boolean setPassphrase(byte[] bArr) throws JSchException;
}
