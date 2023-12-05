package com.jcraft.jsch.jce;

import com.jcraft.jsch.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class ARCFOUR implements Cipher {
    private static final int bsize = 16;
    static /* synthetic */ Class class$javax$crypto$Cipher = null;
    private static final int ivsize = 8;
    private javax.crypto.Cipher cipher;

    public int getBlockSize() {
        return 16;
    }

    public int getIVSize() {
        return 8;
    }

    public boolean isCBC() {
        return false;
    }

    public void init(int i, byte[] bArr, byte[] bArr2) throws Exception {
        Class cls;
        if (bArr.length > 16) {
            byte[] bArr3 = new byte[16];
            System.arraycopy(bArr, 0, bArr3, 0, bArr3.length);
            bArr = bArr3;
        }
        try {
            this.cipher = javax.crypto.Cipher.getInstance("RC4");
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "RC4");
            if (class$javax$crypto$Cipher == null) {
                cls = class$("javax.crypto.Cipher");
                class$javax$crypto$Cipher = cls;
            } else {
                cls = class$javax$crypto$Cipher;
            }
            synchronized (cls) {
                this.cipher.init(i == 0 ? 1 : 2, secretKeySpec);
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
