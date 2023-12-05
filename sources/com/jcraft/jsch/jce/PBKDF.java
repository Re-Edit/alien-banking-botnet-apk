package com.jcraft.jsch.jce;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PBKDF implements com.jcraft.jsch.PBKDF {
    public byte[] getKey(byte[] bArr, byte[] bArr2, int i, int i2) {
        char[] cArr = new char[bArr.length];
        for (int i3 = 0; i3 < bArr.length; i3++) {
            cArr[i3] = (char) (bArr[i3] & 255);
        }
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(cArr, bArr2, i, i2 * 8)).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException unused) {
            return null;
        }
    }
}
