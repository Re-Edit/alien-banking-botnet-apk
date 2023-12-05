package com.jcraft.jsch.jce;

import com.jcraft.jsch.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class TripleDESCBC implements Cipher {
    private static final int bsize = 24;
    static /* synthetic */ Class class$javax$crypto$Cipher = null;
    private static final int ivsize = 8;
    private javax.crypto.Cipher cipher;

    public int getBlockSize() {
        return 24;
    }

    public int getIVSize() {
        return 8;
    }

    public boolean isCBC() {
        return true;
    }

    public void init(int i, byte[] bArr, byte[] bArr2) throws Exception {
        Class cls;
        if (bArr2.length > 8) {
            byte[] bArr3 = new byte[8];
            System.arraycopy(bArr2, 0, bArr3, 0, bArr3.length);
            bArr2 = bArr3;
        }
        if (bArr.length > 24) {
            byte[] bArr4 = new byte[24];
            System.arraycopy(bArr, 0, bArr4, 0, bArr4.length);
            bArr = bArr4;
        }
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("DESede/CBC/");
            stringBuffer.append("NoPadding");
            this.cipher = javax.crypto.Cipher.getInstance(stringBuffer.toString());
            SecretKey generateSecret = SecretKeyFactory.getInstance("DESede").generateSecret(new DESedeKeySpec(bArr));
            if (class$javax$crypto$Cipher == null) {
                cls = class$("javax.crypto.Cipher");
                class$javax$crypto$Cipher = cls;
            } else {
                cls = class$javax$crypto$Cipher;
            }
            synchronized (cls) {
                this.cipher.init(i == 0 ? 1 : 2, generateSecret, new IvParameterSpec(bArr2));
            }
        } catch (Exception e) {
            this.cipher = null;
            throw e;
        }
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public void update(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws Exception {
        this.cipher.update(bArr, i, i2, bArr2, i3);
    }
}
