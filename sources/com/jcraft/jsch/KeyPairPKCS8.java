package com.jcraft.jsch;

import com.jcraft.jsch.KeyPair;
import java.math.BigInteger;
import java.util.Vector;

public class KeyPairPKCS8 extends KeyPair {
    private static final byte[] aes128cbc = {96, -122, 72, 1, 101, 3, 4, 1, 2};
    private static final byte[] aes192cbc = {96, -122, 72, 1, 101, 3, 4, 1, 22};
    private static final byte[] aes256cbc = {96, -122, 72, 1, 101, 3, 4, 1, 42};
    private static final byte[] begin = Util.str2byte("-----BEGIN DSA PRIVATE KEY-----");
    private static final byte[] dsaEncryption = {42, -122, 72, -50, 56, 4, 1};
    private static final byte[] end = Util.str2byte("-----END DSA PRIVATE KEY-----");
    private static final byte[] pbeWithMD5AndDESCBC = {42, -122, 72, -122, -9, 13, 1, 5, 3};
    private static final byte[] pbes2 = {42, -122, 72, -122, -9, 13, 1, 5, 13};
    private static final byte[] pbkdf2 = {42, -122, 72, -122, -9, 13, 1, 5, 12};
    private static final byte[] rsaEncryption = {42, -122, 72, -122, -9, 13, 1, 1, 1};
    private KeyPair kpair = null;

    /* access modifiers changed from: package-private */
    public void generate(int i) throws JSchException {
    }

    /* access modifiers changed from: package-private */
    public byte[] getPrivateKey() {
        return null;
    }

    public KeyPairPKCS8(JSch jSch) {
        super(jSch);
    }

    /* access modifiers changed from: package-private */
    public byte[] getBegin() {
        return begin;
    }

    /* access modifiers changed from: package-private */
    public byte[] getEnd() {
        return end;
    }

    /* access modifiers changed from: package-private */
    public boolean parse(byte[] bArr) {
        try {
            Vector vector = new Vector();
            KeyPair.ASN1[] contents = new KeyPair.ASN1(this, bArr).getContents();
            KeyPair.ASN1 asn1 = contents[1];
            KeyPair.ASN1 asn12 = contents[2];
            KeyPair.ASN1[] contents2 = asn1.getContents();
            byte[] content = contents2[0].getContent();
            KeyPair.ASN1[] contents3 = contents2[1].getContents();
            if (contents3.length > 0) {
                for (KeyPair.ASN1 content2 : contents3) {
                    vector.addElement(content2.getContent());
                }
            }
            byte[] content3 = asn12.getContent();
            if (Util.array_equals(content, rsaEncryption)) {
                KeyPairRSA keyPairRSA = new KeyPairRSA(this.jsch);
                keyPairRSA.copy(this);
                if (keyPairRSA.parse(content3)) {
                    this.kpair = keyPairRSA;
                }
            } else if (Util.array_equals(content, dsaEncryption)) {
                KeyPair.ASN1 asn13 = new KeyPair.ASN1(this, content3);
                if (vector.size() == 0) {
                    KeyPair.ASN1[] contents4 = asn13.getContents();
                    byte[] content4 = contents4[1].getContent();
                    KeyPair.ASN1[] contents5 = contents4[0].getContents();
                    for (KeyPair.ASN1 content5 : contents5) {
                        vector.addElement(content5.getContent());
                    }
                    vector.addElement(content4);
                } else {
                    vector.addElement(asn13.getContent());
                }
                byte[] bArr2 = (byte[]) vector.elementAt(0);
                byte[] bArr3 = (byte[]) vector.elementAt(2);
                byte[] bArr4 = (byte[]) vector.elementAt(3);
                byte[] privateKey = new KeyPairDSA(this.jsch, bArr2, (byte[]) vector.elementAt(1), bArr3, new BigInteger(bArr3).modPow(new BigInteger(bArr4), new BigInteger(bArr2)).toByteArray(), bArr4).getPrivateKey();
                KeyPairDSA keyPairDSA = new KeyPairDSA(this.jsch);
                keyPairDSA.copy(this);
                if (keyPairDSA.parse(privateKey)) {
                    this.kpair = keyPairDSA;
                }
            }
            if (this.kpair != null) {
                return true;
            }
            return false;
        } catch (KeyPair.ASN1Exception unused) {
            return false;
        } catch (Exception unused2) {
            return false;
        }
    }

    public byte[] getPublicKeyBlob() {
        return this.kpair.getPublicKeyBlob();
    }

    /* access modifiers changed from: package-private */
    public byte[] getKeyTypeName() {
        return this.kpair.getKeyTypeName();
    }

    public int getKeyType() {
        return this.kpair.getKeyType();
    }

    public int getKeySize() {
        return this.kpair.getKeySize();
    }

    public byte[] getSignature(byte[] bArr) {
        return this.kpair.getSignature(bArr);
    }

    public Signature getVerifier() {
        return this.kpair.getVerifier();
    }

    public byte[] forSSHAgent() throws JSchException {
        return this.kpair.forSSHAgent();
    }

    public boolean decrypt(byte[] bArr) {
        if (!isEncrypted()) {
            return true;
        }
        if (bArr == null) {
            return !isEncrypted();
        }
        try {
            KeyPair.ASN1[] contents = new KeyPair.ASN1(this, this.data).getContents();
            byte[] content = contents[1].getContent();
            KeyPair.ASN1[] contents2 = contents[0].getContents();
            byte[] content2 = contents2[0].getContent();
            KeyPair.ASN1 asn1 = contents2[1];
            if (!Util.array_equals(content2, pbes2)) {
                return Util.array_equals(content2, pbeWithMD5AndDESCBC) ? false : false;
            }
            KeyPair.ASN1[] contents3 = asn1.getContents();
            KeyPair.ASN1 asn12 = contents3[0];
            KeyPair.ASN1 asn13 = contents3[1];
            KeyPair.ASN1[] contents4 = asn12.getContents();
            contents4[0].getContent();
            KeyPair.ASN1[] contents5 = contents4[1].getContents();
            byte[] content3 = contents5[0].getContent();
            int parseInt = Integer.parseInt(new BigInteger(contents5[1].getContent()).toString());
            KeyPair.ASN1[] contents6 = asn13.getContents();
            byte[] content4 = contents6[0].getContent();
            byte[] content5 = contents6[1].getContent();
            Cipher cipher = getCipher(content4);
            if (cipher == null) {
                return false;
            }
            byte[] bArr2 = null;
            try {
                JSch jSch = this.jsch;
                bArr2 = ((PBKDF) Class.forName(JSch.getConfig("pbkdf")).newInstance()).getKey(bArr, content3, parseInt, cipher.getBlockSize());
            } catch (Exception unused) {
            }
            if (bArr2 == null) {
                return false;
            }
            cipher.init(1, bArr2, content5);
            Util.bzero(bArr2);
            byte[] bArr3 = new byte[content.length];
            cipher.update(content, 0, content.length, bArr3, 0);
            if (parse(bArr3)) {
                this.encrypted = false;
                return true;
            }
            return false;
        } catch (KeyPair.ASN1Exception | Exception unused2) {
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.jcraft.jsch.Cipher getCipher(byte[] r6) {
        /*
            r5 = this;
            r0 = 0
            byte[] r1 = aes128cbc     // Catch:{ Exception -> 0x0039 }
            boolean r1 = com.jcraft.jsch.Util.array_equals(r6, r1)     // Catch:{ Exception -> 0x0039 }
            if (r1 == 0) goto L_0x000c
            java.lang.String r1 = "aes128-cbc"
            goto L_0x0023
        L_0x000c:
            byte[] r1 = aes192cbc     // Catch:{ Exception -> 0x0039 }
            boolean r1 = com.jcraft.jsch.Util.array_equals(r6, r1)     // Catch:{ Exception -> 0x0039 }
            if (r1 == 0) goto L_0x0017
            java.lang.String r1 = "aes192-cbc"
            goto L_0x0023
        L_0x0017:
            byte[] r1 = aes256cbc     // Catch:{ Exception -> 0x0039 }
            boolean r1 = com.jcraft.jsch.Util.array_equals(r6, r1)     // Catch:{ Exception -> 0x0039 }
            if (r1 == 0) goto L_0x0022
            java.lang.String r1 = "aes256-cbc"
            goto L_0x0023
        L_0x0022:
            r1 = r0
        L_0x0023:
            com.jcraft.jsch.JSch r2 = r5.jsch     // Catch:{ Exception -> 0x0037 }
            java.lang.String r2 = com.jcraft.jsch.JSch.getConfig(r1)     // Catch:{ Exception -> 0x0037 }
            java.lang.Class r2 = java.lang.Class.forName(r2)     // Catch:{ Exception -> 0x0037 }
            java.lang.Object r2 = r2.newInstance()     // Catch:{ Exception -> 0x0037 }
            com.jcraft.jsch.Cipher r2 = (com.jcraft.jsch.Cipher) r2     // Catch:{ Exception -> 0x0037 }
            com.jcraft.jsch.Cipher r2 = (com.jcraft.jsch.Cipher) r2     // Catch:{ Exception -> 0x0037 }
            r0 = r2
            goto L_0x008b
        L_0x0037:
            goto L_0x003a
        L_0x0039:
            r1 = r0
        L_0x003a:
            com.jcraft.jsch.Logger r2 = com.jcraft.jsch.JSch.getLogger()
            r3 = 4
            boolean r2 = r2.isEnabled(r3)
            if (r2 == 0) goto L_0x008b
            if (r1 != 0) goto L_0x005d
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            r1.<init>()
            java.lang.String r2 = "unknown oid: "
            r1.append(r2)
            java.lang.String r6 = com.jcraft.jsch.Util.toHex(r6)
            r1.append(r6)
            java.lang.String r6 = r1.toString()
            goto L_0x0073
        L_0x005d:
            java.lang.StringBuffer r6 = new java.lang.StringBuffer
            r6.<init>()
            java.lang.String r2 = "function "
            r6.append(r2)
            r6.append(r1)
            java.lang.String r1 = " is not supported"
            r6.append(r1)
            java.lang.String r6 = r6.toString()
        L_0x0073:
            com.jcraft.jsch.Logger r1 = com.jcraft.jsch.JSch.getLogger()
            java.lang.StringBuffer r2 = new java.lang.StringBuffer
            r2.<init>()
            java.lang.String r4 = "PKCS8: "
            r2.append(r4)
            r2.append(r6)
            java.lang.String r6 = r2.toString()
            r1.log(r3, r6)
        L_0x008b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.KeyPairPKCS8.getCipher(byte[]):com.jcraft.jsch.Cipher");
    }
}
