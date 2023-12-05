package com.jcraft.jsch;

import java.math.BigInteger;

public class KeyPairDSA extends KeyPair {
    private static final byte[] begin = Util.str2byte("-----BEGIN DSA PRIVATE KEY-----");
    private static final byte[] end = Util.str2byte("-----END DSA PRIVATE KEY-----");
    private static final byte[] sshdss = Util.str2byte("ssh-dss");
    private byte[] G_array;
    private byte[] P_array;
    private byte[] Q_array;
    private int key_size;
    private byte[] prv_array;
    private byte[] pub_array;

    public int getKeyType() {
        return 1;
    }

    public KeyPairDSA(JSch jSch) {
        this(jSch, (byte[]) null, (byte[]) null, (byte[]) null, (byte[]) null, (byte[]) null);
    }

    public KeyPairDSA(JSch jSch, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5) {
        super(jSch);
        this.key_size = 1024;
        this.P_array = bArr;
        this.Q_array = bArr2;
        this.G_array = bArr3;
        this.pub_array = bArr4;
        this.prv_array = bArr5;
        if (bArr != null) {
            this.key_size = new BigInteger(bArr).bitLength();
        }
    }

    /* access modifiers changed from: package-private */
    public void generate(int i) throws JSchException {
        this.key_size = i;
        try {
            JSch jSch = this.jsch;
            KeyPairGenDSA keyPairGenDSA = (KeyPairGenDSA) Class.forName(JSch.getConfig("keypairgen.dsa")).newInstance();
            keyPairGenDSA.init(i);
            this.P_array = keyPairGenDSA.getP();
            this.Q_array = keyPairGenDSA.getQ();
            this.G_array = keyPairGenDSA.getG();
            this.pub_array = keyPairGenDSA.getY();
            this.prv_array = keyPairGenDSA.getX();
        } catch (Exception e) {
            if (e instanceof Throwable) {
                throw new JSchException(e.toString(), e);
            }
            throw new JSchException(e.toString());
        }
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
    public byte[] getPrivateKey() {
        int countLength = countLength(1) + 1 + 1 + 1 + countLength(this.P_array.length) + this.P_array.length + 1 + countLength(this.Q_array.length) + this.Q_array.length + 1 + countLength(this.G_array.length) + this.G_array.length + 1 + countLength(this.pub_array.length) + this.pub_array.length + 1 + countLength(this.prv_array.length) + this.prv_array.length;
        byte[] bArr = new byte[(countLength(countLength) + 1 + countLength)];
        writeINTEGER(bArr, writeINTEGER(bArr, writeINTEGER(bArr, writeINTEGER(bArr, writeINTEGER(bArr, writeINTEGER(bArr, writeSEQUENCE(bArr, 0, countLength), new byte[1]), this.P_array), this.Q_array), this.G_array), this.pub_array), this.prv_array);
        return bArr;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v45, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v37, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v38, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v46, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v47, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v49, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v40, resolved type: byte} */
    /* JADX WARNING: type inference failed for: r5v6, types: [int] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parse(byte[] r8) {
        /*
            r7 = this;
            r0 = 0
            int r1 = r7.vendor     // Catch:{ Exception -> 0x0187 }
            r2 = 48
            r3 = 1
            if (r1 != r3) goto L_0x0045
            byte r1 = r8[r0]     // Catch:{ Exception -> 0x0187 }
            if (r1 == r2) goto L_0x0044
            com.jcraft.jsch.Buffer r1 = new com.jcraft.jsch.Buffer     // Catch:{ Exception -> 0x0187 }
            r1.<init>((byte[]) r8)     // Catch:{ Exception -> 0x0187 }
            r1.getInt()     // Catch:{ Exception -> 0x0187 }
            byte[] r8 = r1.getMPIntBits()     // Catch:{ Exception -> 0x0187 }
            r7.P_array = r8     // Catch:{ Exception -> 0x0187 }
            byte[] r8 = r1.getMPIntBits()     // Catch:{ Exception -> 0x0187 }
            r7.G_array = r8     // Catch:{ Exception -> 0x0187 }
            byte[] r8 = r1.getMPIntBits()     // Catch:{ Exception -> 0x0187 }
            r7.Q_array = r8     // Catch:{ Exception -> 0x0187 }
            byte[] r8 = r1.getMPIntBits()     // Catch:{ Exception -> 0x0187 }
            r7.pub_array = r8     // Catch:{ Exception -> 0x0187 }
            byte[] r8 = r1.getMPIntBits()     // Catch:{ Exception -> 0x0187 }
            r7.prv_array = r8     // Catch:{ Exception -> 0x0187 }
            byte[] r8 = r7.P_array     // Catch:{ Exception -> 0x0187 }
            if (r8 == 0) goto L_0x0043
            java.math.BigInteger r8 = new java.math.BigInteger     // Catch:{ Exception -> 0x0187 }
            byte[] r1 = r7.P_array     // Catch:{ Exception -> 0x0187 }
            r8.<init>(r1)     // Catch:{ Exception -> 0x0187 }
            int r8 = r8.bitLength()     // Catch:{ Exception -> 0x0187 }
            r7.key_size = r8     // Catch:{ Exception -> 0x0187 }
        L_0x0043:
            return r3
        L_0x0044:
            return r0
        L_0x0045:
            int r1 = r7.vendor     // Catch:{ Exception -> 0x0187 }
            r4 = 2
            if (r1 != r4) goto L_0x005f
            com.jcraft.jsch.Buffer r1 = new com.jcraft.jsch.Buffer     // Catch:{ Exception -> 0x0187 }
            r1.<init>((byte[]) r8)     // Catch:{ Exception -> 0x0187 }
            int r8 = r8.length     // Catch:{ Exception -> 0x0187 }
            r1.skip(r8)     // Catch:{ Exception -> 0x0187 }
            java.lang.String r8 = ""
            byte[][] r8 = r1.getBytes(r3, r8)     // Catch:{ JSchException -> 0x005e }
            r8 = r8[r0]     // Catch:{ JSchException -> 0x005e }
            r7.prv_array = r8     // Catch:{ JSchException -> 0x005e }
            return r3
        L_0x005e:
            return r0
        L_0x005f:
            byte r1 = r8[r0]     // Catch:{ Exception -> 0x0187 }
            if (r1 == r2) goto L_0x0064
            return r0
        L_0x0064:
            byte r1 = r8[r3]     // Catch:{ Exception -> 0x0187 }
            r1 = r1 & 255(0xff, float:3.57E-43)
            r2 = r1 & 128(0x80, float:1.794E-43)
            if (r2 == 0) goto L_0x007a
            r1 = r1 & 127(0x7f, float:1.78E-43)
            r2 = 2
        L_0x006f:
            int r5 = r1 + -1
            if (r1 <= 0) goto L_0x007b
            int r1 = r2 + 1
            byte r2 = r8[r2]     // Catch:{ Exception -> 0x0187 }
            r2 = r1
            r1 = r5
            goto L_0x006f
        L_0x007a:
            r2 = 2
        L_0x007b:
            byte r1 = r8[r2]     // Catch:{ Exception -> 0x0187 }
            if (r1 == r4) goto L_0x0080
            return r0
        L_0x0080:
            int r2 = r2 + r3
            int r1 = r2 + 1
            byte r2 = r8[r2]     // Catch:{ Exception -> 0x0187 }
            r2 = r2 & 255(0xff, float:3.57E-43)
            r4 = r2 & 128(0x80, float:1.794E-43)
            if (r4 == 0) goto L_0x00a1
            r2 = r2 & 127(0x7f, float:1.78E-43)
            r4 = 0
        L_0x008e:
            int r5 = r2 + -1
            if (r2 <= 0) goto L_0x00a0
            int r2 = r4 << 8
            int r4 = r1 + 1
            byte r1 = r8[r1]     // Catch:{ Exception -> 0x0187 }
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r1 = r1 + r2
            r2 = r5
            r6 = r4
            r4 = r1
            r1 = r6
            goto L_0x008e
        L_0x00a0:
            r2 = r4
        L_0x00a1:
            int r1 = r1 + r2
            int r1 = r1 + r3
            int r2 = r1 + 1
            byte r1 = r8[r1]     // Catch:{ Exception -> 0x0187 }
            r1 = r1 & 255(0xff, float:3.57E-43)
            r4 = r1 & 128(0x80, float:1.794E-43)
            if (r4 == 0) goto L_0x00c2
            r1 = r1 & 127(0x7f, float:1.78E-43)
            r4 = 0
        L_0x00b0:
            int r5 = r1 + -1
            if (r1 <= 0) goto L_0x00c1
            int r1 = r4 << 8
            int r4 = r2 + 1
            byte r2 = r8[r2]     // Catch:{ Exception -> 0x0187 }
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r1 = r1 + r2
            r2 = r4
            r4 = r1
            r1 = r5
            goto L_0x00b0
        L_0x00c1:
            r1 = r4
        L_0x00c2:
            byte[] r4 = new byte[r1]     // Catch:{ Exception -> 0x0187 }
            r7.P_array = r4     // Catch:{ Exception -> 0x0187 }
            byte[] r4 = r7.P_array     // Catch:{ Exception -> 0x0187 }
            java.lang.System.arraycopy(r8, r2, r4, r0, r1)     // Catch:{ Exception -> 0x0187 }
            int r2 = r2 + r1
            int r2 = r2 + r3
            int r1 = r2 + 1
            byte r2 = r8[r2]     // Catch:{ Exception -> 0x0187 }
            r2 = r2 & 255(0xff, float:3.57E-43)
            r4 = r2 & 128(0x80, float:1.794E-43)
            if (r4 == 0) goto L_0x00ed
            r2 = r2 & 127(0x7f, float:1.78E-43)
            r4 = 0
        L_0x00da:
            int r5 = r2 + -1
            if (r2 <= 0) goto L_0x00ec
            int r2 = r4 << 8
            int r4 = r1 + 1
            byte r1 = r8[r1]     // Catch:{ Exception -> 0x0187 }
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r1 = r1 + r2
            r2 = r5
            r6 = r4
            r4 = r1
            r1 = r6
            goto L_0x00da
        L_0x00ec:
            r2 = r4
        L_0x00ed:
            byte[] r4 = new byte[r2]     // Catch:{ Exception -> 0x0187 }
            r7.Q_array = r4     // Catch:{ Exception -> 0x0187 }
            byte[] r4 = r7.Q_array     // Catch:{ Exception -> 0x0187 }
            java.lang.System.arraycopy(r8, r1, r4, r0, r2)     // Catch:{ Exception -> 0x0187 }
            int r1 = r1 + r2
            int r1 = r1 + r3
            int r2 = r1 + 1
            byte r1 = r8[r1]     // Catch:{ Exception -> 0x0187 }
            r1 = r1 & 255(0xff, float:3.57E-43)
            r4 = r1 & 128(0x80, float:1.794E-43)
            if (r4 == 0) goto L_0x0117
            r1 = r1 & 127(0x7f, float:1.78E-43)
            r4 = 0
        L_0x0105:
            int r5 = r1 + -1
            if (r1 <= 0) goto L_0x0116
            int r1 = r4 << 8
            int r4 = r2 + 1
            byte r2 = r8[r2]     // Catch:{ Exception -> 0x0187 }
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r1 = r1 + r2
            r2 = r4
            r4 = r1
            r1 = r5
            goto L_0x0105
        L_0x0116:
            r1 = r4
        L_0x0117:
            byte[] r4 = new byte[r1]     // Catch:{ Exception -> 0x0187 }
            r7.G_array = r4     // Catch:{ Exception -> 0x0187 }
            byte[] r4 = r7.G_array     // Catch:{ Exception -> 0x0187 }
            java.lang.System.arraycopy(r8, r2, r4, r0, r1)     // Catch:{ Exception -> 0x0187 }
            int r2 = r2 + r1
            int r2 = r2 + r3
            int r1 = r2 + 1
            byte r2 = r8[r2]     // Catch:{ Exception -> 0x0187 }
            r2 = r2 & 255(0xff, float:3.57E-43)
            r4 = r2 & 128(0x80, float:1.794E-43)
            if (r4 == 0) goto L_0x0142
            r2 = r2 & 127(0x7f, float:1.78E-43)
            r4 = 0
        L_0x012f:
            int r5 = r2 + -1
            if (r2 <= 0) goto L_0x0141
            int r2 = r4 << 8
            int r4 = r1 + 1
            byte r1 = r8[r1]     // Catch:{ Exception -> 0x0187 }
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r1 = r1 + r2
            r2 = r5
            r6 = r4
            r4 = r1
            r1 = r6
            goto L_0x012f
        L_0x0141:
            r2 = r4
        L_0x0142:
            byte[] r4 = new byte[r2]     // Catch:{ Exception -> 0x0187 }
            r7.pub_array = r4     // Catch:{ Exception -> 0x0187 }
            byte[] r4 = r7.pub_array     // Catch:{ Exception -> 0x0187 }
            java.lang.System.arraycopy(r8, r1, r4, r0, r2)     // Catch:{ Exception -> 0x0187 }
            int r1 = r1 + r2
            int r1 = r1 + r3
            int r2 = r1 + 1
            byte r1 = r8[r1]     // Catch:{ Exception -> 0x0187 }
            r1 = r1 & 255(0xff, float:3.57E-43)
            r4 = r1 & 128(0x80, float:1.794E-43)
            if (r4 == 0) goto L_0x016c
            r1 = r1 & 127(0x7f, float:1.78E-43)
            r4 = 0
        L_0x015a:
            int r5 = r1 + -1
            if (r1 <= 0) goto L_0x016b
            int r1 = r4 << 8
            int r4 = r2 + 1
            byte r2 = r8[r2]     // Catch:{ Exception -> 0x0187 }
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r1 = r1 + r2
            r2 = r4
            r4 = r1
            r1 = r5
            goto L_0x015a
        L_0x016b:
            r1 = r4
        L_0x016c:
            byte[] r4 = new byte[r1]     // Catch:{ Exception -> 0x0187 }
            r7.prv_array = r4     // Catch:{ Exception -> 0x0187 }
            byte[] r4 = r7.prv_array     // Catch:{ Exception -> 0x0187 }
            java.lang.System.arraycopy(r8, r2, r4, r0, r1)     // Catch:{ Exception -> 0x0187 }
            byte[] r8 = r7.P_array     // Catch:{ Exception -> 0x0187 }
            if (r8 == 0) goto L_0x0186
            java.math.BigInteger r8 = new java.math.BigInteger     // Catch:{ Exception -> 0x0187 }
            byte[] r1 = r7.P_array     // Catch:{ Exception -> 0x0187 }
            r8.<init>(r1)     // Catch:{ Exception -> 0x0187 }
            int r8 = r8.bitLength()     // Catch:{ Exception -> 0x0187 }
            r7.key_size = r8     // Catch:{ Exception -> 0x0187 }
        L_0x0186:
            return r3
        L_0x0187:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.KeyPairDSA.parse(byte[]):boolean");
    }

    public byte[] getPublicKeyBlob() {
        byte[] publicKeyBlob = super.getPublicKeyBlob();
        if (publicKeyBlob != null) {
            return publicKeyBlob;
        }
        byte[] bArr = this.P_array;
        if (bArr == null) {
            return null;
        }
        return Buffer.fromBytes(new byte[][]{sshdss, bArr, this.Q_array, this.G_array, this.pub_array}).buffer;
    }

    /* access modifiers changed from: package-private */
    public byte[] getKeyTypeName() {
        return sshdss;
    }

    public int getKeySize() {
        return this.key_size;
    }

    public byte[] getSignature(byte[] bArr) {
        try {
            JSch jSch = this.jsch;
            SignatureDSA signatureDSA = (SignatureDSA) Class.forName(JSch.getConfig("signature.dss")).newInstance();
            signatureDSA.init();
            signatureDSA.setPrvKey(this.prv_array, this.P_array, this.Q_array, this.G_array);
            signatureDSA.update(bArr);
            return Buffer.fromBytes(new byte[][]{sshdss, signatureDSA.sign()}).buffer;
        } catch (Exception unused) {
            return null;
        }
    }

    public Signature getVerifier() {
        try {
            JSch jSch = this.jsch;
            SignatureDSA signatureDSA = (SignatureDSA) Class.forName(JSch.getConfig("signature.dss")).newInstance();
            signatureDSA.init();
            if (this.pub_array == null && this.P_array == null && getPublicKeyBlob() != null) {
                Buffer buffer = new Buffer(getPublicKeyBlob());
                buffer.getString();
                this.P_array = buffer.getString();
                this.Q_array = buffer.getString();
                this.G_array = buffer.getString();
                this.pub_array = buffer.getString();
            }
            signatureDSA.setPubKey(this.pub_array, this.P_array, this.Q_array, this.G_array);
            return signatureDSA;
        } catch (Exception unused) {
            return null;
        }
    }

    static KeyPair fromSSHAgent(JSch jSch, Buffer buffer) throws JSchException {
        byte[][] bytes = buffer.getBytes(7, "invalid key format");
        KeyPairDSA keyPairDSA = new KeyPairDSA(jSch, bytes[1], bytes[2], bytes[3], bytes[4], bytes[5]);
        keyPairDSA.publicKeyComment = new String(bytes[6]);
        keyPairDSA.vendor = 0;
        return keyPairDSA;
    }

    public byte[] forSSHAgent() throws JSchException {
        if (!isEncrypted()) {
            Buffer buffer = new Buffer();
            buffer.putString(sshdss);
            buffer.putString(this.P_array);
            buffer.putString(this.Q_array);
            buffer.putString(this.G_array);
            buffer.putString(this.pub_array);
            buffer.putString(this.prv_array);
            buffer.putString(Util.str2byte(this.publicKeyComment));
            byte[] bArr = new byte[buffer.getLength()];
            buffer.getByte(bArr, 0, bArr.length);
            return bArr;
        }
        throw new JSchException("key is encrypted.");
    }

    public void dispose() {
        super.dispose();
        Util.bzero(this.prv_array);
    }
}
