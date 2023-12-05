package com.jcraft.jsch.jce;

import com.jcraft.jsch.Buffer;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;

public class SignatureECDSA implements com.jcraft.jsch.SignatureECDSA {
    static /* synthetic */ Class class$java$security$spec$ECParameterSpec;
    KeyFactory keyFactory;
    Signature signature;

    public void init() throws Exception {
        this.signature = Signature.getInstance("SHA256withECDSA");
        this.keyFactory = KeyFactory.getInstance("EC");
    }

    public void setPubKey(byte[] bArr, byte[] bArr2) throws Exception {
        byte[] insert0 = insert0(bArr);
        byte[] insert02 = insert0(bArr2);
        String str = "secp256r1";
        if (insert0.length >= 64) {
            str = "secp521r1";
        } else if (insert0.length >= 48) {
            str = "secp384r1";
        }
        AlgorithmParameters instance = AlgorithmParameters.getInstance("EC");
        instance.init(new ECGenParameterSpec(str));
        Class cls = class$java$security$spec$ECParameterSpec;
        if (cls == null) {
            cls = class$("java.security.spec.ECParameterSpec");
            class$java$security$spec$ECParameterSpec = cls;
        }
        this.signature.initVerify(this.keyFactory.generatePublic(new ECPublicKeySpec(new ECPoint(new BigInteger(1, insert0), new BigInteger(1, insert02)), (ECParameterSpec) instance.getParameterSpec(cls))));
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public void setPrvKey(byte[] bArr) throws Exception {
        byte[] insert0 = insert0(bArr);
        String str = "secp256r1";
        if (insert0.length >= 64) {
            str = "secp521r1";
        } else if (insert0.length >= 48) {
            str = "secp384r1";
        }
        AlgorithmParameters instance = AlgorithmParameters.getInstance("EC");
        instance.init(new ECGenParameterSpec(str));
        Class cls = class$java$security$spec$ECParameterSpec;
        if (cls == null) {
            cls = class$("java.security.spec.ECParameterSpec");
            class$java$security$spec$ECParameterSpec = cls;
        }
        this.signature.initSign(this.keyFactory.generatePrivate(new ECPrivateKeySpec(new BigInteger(1, insert0), (ECParameterSpec) instance.getParameterSpec(cls))));
    }

    public byte[] sign() throws Exception {
        byte[] sign = this.signature.sign();
        if (sign[0] != 48) {
            return sign;
        }
        int i = 3;
        if (sign[1] + 2 != sign.length && ((sign[1] & 128) == 0 || (sign[2] & 255) + 3 != sign.length)) {
            return sign;
        }
        if ((sign[1] & 128) != 0 && (sign[2] & 255) + 3 == sign.length) {
            i = 4;
        }
        byte[] bArr = new byte[sign[i]];
        byte[] bArr2 = new byte[sign[i + 2 + sign[i]]];
        System.arraycopy(sign, i + 1, bArr, 0, bArr.length);
        System.arraycopy(sign, i + 3 + sign[i], bArr2, 0, bArr2.length);
        byte[] chop0 = chop0(bArr);
        byte[] chop02 = chop0(bArr2);
        Buffer buffer = new Buffer();
        buffer.putMPInt(chop0);
        buffer.putMPInt(chop02);
        byte[] bArr3 = new byte[buffer.getLength()];
        buffer.setOffSet(0);
        buffer.getByte(bArr3);
        return bArr3;
    }

    public void update(byte[] bArr) throws Exception {
        this.signature.update(bArr);
    }

    public boolean verify(byte[] bArr) throws Exception {
        if (bArr[0] != 48 || (bArr[1] + 2 != bArr.length && ((bArr[1] & 128) == 0 || (bArr[2] & 255) + 3 != bArr.length))) {
            Buffer buffer = new Buffer(bArr);
            buffer.getString();
            buffer.getInt();
            byte[] mPInt = buffer.getMPInt();
            byte[] mPInt2 = buffer.getMPInt();
            byte[] insert0 = insert0(mPInt);
            byte[] insert02 = insert0(mPInt2);
            if (insert0.length < 64) {
                byte[] bArr2 = new byte[(insert0.length + 6 + insert02.length)];
                bArr2[0] = 48;
                bArr2[1] = (byte) (insert0.length + 4 + insert02.length);
                bArr2[2] = 2;
                bArr2[3] = (byte) insert0.length;
                System.arraycopy(insert0, 0, bArr2, 4, insert0.length);
                bArr2[insert0.length + 4] = 2;
                bArr2[insert0.length + 5] = (byte) insert02.length;
                System.arraycopy(insert02, 0, bArr2, insert0.length + 6, insert02.length);
                bArr = bArr2;
            } else {
                byte[] bArr3 = new byte[(insert0.length + 6 + insert02.length + 1)];
                bArr3[0] = 48;
                bArr3[1] = -127;
                bArr3[2] = (byte) (insert0.length + 4 + insert02.length);
                bArr3[3] = 2;
                bArr3[4] = (byte) insert0.length;
                System.arraycopy(insert0, 0, bArr3, 5, insert0.length);
                bArr3[insert0.length + 5] = 2;
                bArr3[insert0.length + 6] = (byte) insert02.length;
                System.arraycopy(insert02, 0, bArr3, insert0.length + 7, insert02.length);
                bArr = bArr3;
            }
        }
        return this.signature.verify(bArr);
    }

    private byte[] insert0(byte[] bArr) {
        if ((bArr[0] & 128) == 0) {
            return bArr;
        }
        byte[] bArr2 = new byte[(bArr.length + 1)];
        System.arraycopy(bArr, 0, bArr2, 1, bArr.length);
        bzero(bArr);
        return bArr2;
    }

    private byte[] chop0(byte[] bArr) {
        if (bArr[0] != 0) {
            return bArr;
        }
        byte[] bArr2 = new byte[(bArr.length - 1)];
        System.arraycopy(bArr, 1, bArr2, 0, bArr2.length);
        bzero(bArr);
        return bArr2;
    }

    private void bzero(byte[] bArr) {
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = 0;
        }
    }
}
