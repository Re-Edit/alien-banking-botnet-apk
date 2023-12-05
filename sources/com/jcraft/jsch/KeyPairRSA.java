package com.jcraft.jsch;

import java.math.BigInteger;

public class KeyPairRSA extends KeyPair {
    private static final byte[] begin = Util.str2byte("-----BEGIN RSA PRIVATE KEY-----");
    private static final byte[] end = Util.str2byte("-----END RSA PRIVATE KEY-----");
    private static final byte[] sshrsa = Util.str2byte("ssh-rsa");
    private byte[] c_array;
    private byte[] ep_array;
    private byte[] eq_array;
    private int key_size;
    private byte[] n_array;
    private byte[] p_array;
    private byte[] prv_array;
    private byte[] pub_array;
    private byte[] q_array;

    public int getKeyType() {
        return 2;
    }

    public KeyPairRSA(JSch jSch) {
        this(jSch, (byte[]) null, (byte[]) null, (byte[]) null);
    }

    public KeyPairRSA(JSch jSch, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        super(jSch);
        this.key_size = 1024;
        this.n_array = bArr;
        this.pub_array = bArr2;
        this.prv_array = bArr3;
        if (bArr != null) {
            this.key_size = new BigInteger(bArr).bitLength();
        }
    }

    /* access modifiers changed from: package-private */
    public void generate(int i) throws JSchException {
        this.key_size = i;
        try {
            JSch jSch = this.jsch;
            KeyPairGenRSA keyPairGenRSA = (KeyPairGenRSA) Class.forName(JSch.getConfig("keypairgen.rsa")).newInstance();
            keyPairGenRSA.init(i);
            this.pub_array = keyPairGenRSA.getE();
            this.prv_array = keyPairGenRSA.getD();
            this.n_array = keyPairGenRSA.getN();
            this.p_array = keyPairGenRSA.getP();
            this.q_array = keyPairGenRSA.getQ();
            this.ep_array = keyPairGenRSA.getEP();
            this.eq_array = keyPairGenRSA.getEQ();
            this.c_array = keyPairGenRSA.getC();
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
        int countLength = countLength(1) + 1 + 1 + 1 + countLength(this.n_array.length) + this.n_array.length + 1 + countLength(this.pub_array.length) + this.pub_array.length + 1 + countLength(this.prv_array.length) + this.prv_array.length + 1 + countLength(this.p_array.length) + this.p_array.length + 1 + countLength(this.q_array.length) + this.q_array.length + 1 + countLength(this.ep_array.length) + this.ep_array.length + 1 + countLength(this.eq_array.length) + this.eq_array.length + 1 + countLength(this.c_array.length) + this.c_array.length;
        byte[] bArr = new byte[(countLength(countLength) + 1 + countLength)];
        writeINTEGER(bArr, writeINTEGER(bArr, writeINTEGER(bArr, writeINTEGER(bArr, writeINTEGER(bArr, writeINTEGER(bArr, writeINTEGER(bArr, writeINTEGER(bArr, writeINTEGER(bArr, writeSEQUENCE(bArr, 0, countLength), new byte[1]), this.n_array), this.pub_array), this.prv_array), this.p_array), this.q_array), this.ep_array), this.eq_array), this.c_array);
        return bArr;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v68, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v60, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v61, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v69, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v70, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v72, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v63, resolved type: byte} */
    /* JADX WARNING: type inference failed for: r5v9, types: [int] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parse(byte[] r8) {
        /*
            r7 = this;
            r0 = 0
            int r1 = r7.vendor     // Catch:{ Exception -> 0x021f }
            r2 = 2
            r3 = 1
            if (r1 != r2) goto L_0x0030
            com.jcraft.jsch.Buffer r1 = new com.jcraft.jsch.Buffer     // Catch:{ Exception -> 0x021f }
            r1.<init>((byte[]) r8)     // Catch:{ Exception -> 0x021f }
            int r8 = r8.length     // Catch:{ Exception -> 0x021f }
            r1.skip(r8)     // Catch:{ Exception -> 0x021f }
            r8 = 4
            java.lang.String r4 = ""
            byte[][] r8 = r1.getBytes(r8, r4)     // Catch:{ JSchException -> 0x002f }
            r1 = r8[r0]     // Catch:{ JSchException -> 0x002f }
            r7.prv_array = r1     // Catch:{ JSchException -> 0x002f }
            r1 = r8[r3]     // Catch:{ JSchException -> 0x002f }
            r7.p_array = r1     // Catch:{ JSchException -> 0x002f }
            r1 = r8[r2]     // Catch:{ JSchException -> 0x002f }
            r7.q_array = r1     // Catch:{ JSchException -> 0x002f }
            r1 = 3
            r8 = r8[r1]     // Catch:{ JSchException -> 0x002f }
            r7.c_array = r8     // Catch:{ JSchException -> 0x002f }
            r7.getEPArray()     // Catch:{ Exception -> 0x021f }
            r7.getEQArray()     // Catch:{ Exception -> 0x021f }
            return r3
        L_0x002f:
            return r0
        L_0x0030:
            int r1 = r7.vendor     // Catch:{ Exception -> 0x021f }
            if (r1 != r3) goto L_0x007c
            byte r1 = r8[r0]     // Catch:{ Exception -> 0x021f }
            r2 = 48
            if (r1 == r2) goto L_0x007b
            com.jcraft.jsch.Buffer r1 = new com.jcraft.jsch.Buffer     // Catch:{ Exception -> 0x021f }
            r1.<init>((byte[]) r8)     // Catch:{ Exception -> 0x021f }
            byte[] r8 = r1.getMPIntBits()     // Catch:{ Exception -> 0x021f }
            r7.pub_array = r8     // Catch:{ Exception -> 0x021f }
            byte[] r8 = r1.getMPIntBits()     // Catch:{ Exception -> 0x021f }
            r7.prv_array = r8     // Catch:{ Exception -> 0x021f }
            byte[] r8 = r1.getMPIntBits()     // Catch:{ Exception -> 0x021f }
            r7.n_array = r8     // Catch:{ Exception -> 0x021f }
            r1.getMPIntBits()     // Catch:{ Exception -> 0x021f }
            byte[] r8 = r1.getMPIntBits()     // Catch:{ Exception -> 0x021f }
            r7.p_array = r8     // Catch:{ Exception -> 0x021f }
            byte[] r8 = r1.getMPIntBits()     // Catch:{ Exception -> 0x021f }
            r7.q_array = r8     // Catch:{ Exception -> 0x021f }
            byte[] r8 = r7.n_array     // Catch:{ Exception -> 0x021f }
            if (r8 == 0) goto L_0x0071
            java.math.BigInteger r8 = new java.math.BigInteger     // Catch:{ Exception -> 0x021f }
            byte[] r1 = r7.n_array     // Catch:{ Exception -> 0x021f }
            r8.<init>(r1)     // Catch:{ Exception -> 0x021f }
            int r8 = r8.bitLength()     // Catch:{ Exception -> 0x021f }
            r7.key_size = r8     // Catch:{ Exception -> 0x021f }
        L_0x0071:
            r7.getEPArray()     // Catch:{ Exception -> 0x021f }
            r7.getEQArray()     // Catch:{ Exception -> 0x021f }
            r7.getCArray()     // Catch:{ Exception -> 0x021f }
            return r3
        L_0x007b:
            return r0
        L_0x007c:
            byte r1 = r8[r3]     // Catch:{ Exception -> 0x021f }
            r1 = r1 & 255(0xff, float:3.57E-43)
            r4 = r1 & 128(0x80, float:1.794E-43)
            if (r4 == 0) goto L_0x0092
            r1 = r1 & 127(0x7f, float:1.78E-43)
            r4 = 2
        L_0x0087:
            int r5 = r1 + -1
            if (r1 <= 0) goto L_0x0093
            int r1 = r4 + 1
            byte r4 = r8[r4]     // Catch:{ Exception -> 0x021f }
            r4 = r1
            r1 = r5
            goto L_0x0087
        L_0x0092:
            r4 = 2
        L_0x0093:
            byte r1 = r8[r4]     // Catch:{ Exception -> 0x021f }
            if (r1 == r2) goto L_0x0098
            return r0
        L_0x0098:
            int r4 = r4 + r3
            int r1 = r4 + 1
            byte r2 = r8[r4]     // Catch:{ Exception -> 0x021f }
            r2 = r2 & 255(0xff, float:3.57E-43)
            r4 = r2 & 128(0x80, float:1.794E-43)
            if (r4 == 0) goto L_0x00b9
            r2 = r2 & 127(0x7f, float:1.78E-43)
            r4 = 0
        L_0x00a6:
            int r5 = r2 + -1
            if (r2 <= 0) goto L_0x00b8
            int r2 = r4 << 8
            int r4 = r1 + 1
            byte r1 = r8[r1]     // Catch:{ Exception -> 0x021f }
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r1 = r1 + r2
            r2 = r5
            r6 = r4
            r4 = r1
            r1 = r6
            goto L_0x00a6
        L_0x00b8:
            r2 = r4
        L_0x00b9:
            int r1 = r1 + r2
            int r1 = r1 + r3
            int r2 = r1 + 1
            byte r1 = r8[r1]     // Catch:{ Exception -> 0x021f }
            r1 = r1 & 255(0xff, float:3.57E-43)
            r4 = r1 & 128(0x80, float:1.794E-43)
            if (r4 == 0) goto L_0x00da
            r1 = r1 & 127(0x7f, float:1.78E-43)
            r4 = 0
        L_0x00c8:
            int r5 = r1 + -1
            if (r1 <= 0) goto L_0x00d9
            int r1 = r4 << 8
            int r4 = r2 + 1
            byte r2 = r8[r2]     // Catch:{ Exception -> 0x021f }
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r1 = r1 + r2
            r2 = r4
            r4 = r1
            r1 = r5
            goto L_0x00c8
        L_0x00d9:
            r1 = r4
        L_0x00da:
            byte[] r4 = new byte[r1]     // Catch:{ Exception -> 0x021f }
            r7.n_array = r4     // Catch:{ Exception -> 0x021f }
            byte[] r4 = r7.n_array     // Catch:{ Exception -> 0x021f }
            java.lang.System.arraycopy(r8, r2, r4, r0, r1)     // Catch:{ Exception -> 0x021f }
            int r2 = r2 + r1
            int r2 = r2 + r3
            int r1 = r2 + 1
            byte r2 = r8[r2]     // Catch:{ Exception -> 0x021f }
            r2 = r2 & 255(0xff, float:3.57E-43)
            r4 = r2 & 128(0x80, float:1.794E-43)
            if (r4 == 0) goto L_0x0105
            r2 = r2 & 127(0x7f, float:1.78E-43)
            r4 = 0
        L_0x00f2:
            int r5 = r2 + -1
            if (r2 <= 0) goto L_0x0104
            int r2 = r4 << 8
            int r4 = r1 + 1
            byte r1 = r8[r1]     // Catch:{ Exception -> 0x021f }
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r1 = r1 + r2
            r2 = r5
            r6 = r4
            r4 = r1
            r1 = r6
            goto L_0x00f2
        L_0x0104:
            r2 = r4
        L_0x0105:
            byte[] r4 = new byte[r2]     // Catch:{ Exception -> 0x021f }
            r7.pub_array = r4     // Catch:{ Exception -> 0x021f }
            byte[] r4 = r7.pub_array     // Catch:{ Exception -> 0x021f }
            java.lang.System.arraycopy(r8, r1, r4, r0, r2)     // Catch:{ Exception -> 0x021f }
            int r1 = r1 + r2
            int r1 = r1 + r3
            int r2 = r1 + 1
            byte r1 = r8[r1]     // Catch:{ Exception -> 0x021f }
            r1 = r1 & 255(0xff, float:3.57E-43)
            r4 = r1 & 128(0x80, float:1.794E-43)
            if (r4 == 0) goto L_0x012f
            r1 = r1 & 127(0x7f, float:1.78E-43)
            r4 = 0
        L_0x011d:
            int r5 = r1 + -1
            if (r1 <= 0) goto L_0x012e
            int r1 = r4 << 8
            int r4 = r2 + 1
            byte r2 = r8[r2]     // Catch:{ Exception -> 0x021f }
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r1 = r1 + r2
            r2 = r4
            r4 = r1
            r1 = r5
            goto L_0x011d
        L_0x012e:
            r1 = r4
        L_0x012f:
            byte[] r4 = new byte[r1]     // Catch:{ Exception -> 0x021f }
            r7.prv_array = r4     // Catch:{ Exception -> 0x021f }
            byte[] r4 = r7.prv_array     // Catch:{ Exception -> 0x021f }
            java.lang.System.arraycopy(r8, r2, r4, r0, r1)     // Catch:{ Exception -> 0x021f }
            int r2 = r2 + r1
            int r2 = r2 + r3
            int r1 = r2 + 1
            byte r2 = r8[r2]     // Catch:{ Exception -> 0x021f }
            r2 = r2 & 255(0xff, float:3.57E-43)
            r4 = r2 & 128(0x80, float:1.794E-43)
            if (r4 == 0) goto L_0x015a
            r2 = r2 & 127(0x7f, float:1.78E-43)
            r4 = 0
        L_0x0147:
            int r5 = r2 + -1
            if (r2 <= 0) goto L_0x0159
            int r2 = r4 << 8
            int r4 = r1 + 1
            byte r1 = r8[r1]     // Catch:{ Exception -> 0x021f }
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r1 = r1 + r2
            r2 = r5
            r6 = r4
            r4 = r1
            r1 = r6
            goto L_0x0147
        L_0x0159:
            r2 = r4
        L_0x015a:
            byte[] r4 = new byte[r2]     // Catch:{ Exception -> 0x021f }
            r7.p_array = r4     // Catch:{ Exception -> 0x021f }
            byte[] r4 = r7.p_array     // Catch:{ Exception -> 0x021f }
            java.lang.System.arraycopy(r8, r1, r4, r0, r2)     // Catch:{ Exception -> 0x021f }
            int r1 = r1 + r2
            int r1 = r1 + r3
            int r2 = r1 + 1
            byte r1 = r8[r1]     // Catch:{ Exception -> 0x021f }
            r1 = r1 & 255(0xff, float:3.57E-43)
            r4 = r1 & 128(0x80, float:1.794E-43)
            if (r4 == 0) goto L_0x0184
            r1 = r1 & 127(0x7f, float:1.78E-43)
            r4 = 0
        L_0x0172:
            int r5 = r1 + -1
            if (r1 <= 0) goto L_0x0183
            int r1 = r4 << 8
            int r4 = r2 + 1
            byte r2 = r8[r2]     // Catch:{ Exception -> 0x021f }
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r1 = r1 + r2
            r2 = r4
            r4 = r1
            r1 = r5
            goto L_0x0172
        L_0x0183:
            r1 = r4
        L_0x0184:
            byte[] r4 = new byte[r1]     // Catch:{ Exception -> 0x021f }
            r7.q_array = r4     // Catch:{ Exception -> 0x021f }
            byte[] r4 = r7.q_array     // Catch:{ Exception -> 0x021f }
            java.lang.System.arraycopy(r8, r2, r4, r0, r1)     // Catch:{ Exception -> 0x021f }
            int r2 = r2 + r1
            int r2 = r2 + r3
            int r1 = r2 + 1
            byte r2 = r8[r2]     // Catch:{ Exception -> 0x021f }
            r2 = r2 & 255(0xff, float:3.57E-43)
            r4 = r2 & 128(0x80, float:1.794E-43)
            if (r4 == 0) goto L_0x01af
            r2 = r2 & 127(0x7f, float:1.78E-43)
            r4 = 0
        L_0x019c:
            int r5 = r2 + -1
            if (r2 <= 0) goto L_0x01ae
            int r2 = r4 << 8
            int r4 = r1 + 1
            byte r1 = r8[r1]     // Catch:{ Exception -> 0x021f }
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r1 = r1 + r2
            r2 = r5
            r6 = r4
            r4 = r1
            r1 = r6
            goto L_0x019c
        L_0x01ae:
            r2 = r4
        L_0x01af:
            byte[] r4 = new byte[r2]     // Catch:{ Exception -> 0x021f }
            r7.ep_array = r4     // Catch:{ Exception -> 0x021f }
            byte[] r4 = r7.ep_array     // Catch:{ Exception -> 0x021f }
            java.lang.System.arraycopy(r8, r1, r4, r0, r2)     // Catch:{ Exception -> 0x021f }
            int r1 = r1 + r2
            int r1 = r1 + r3
            int r2 = r1 + 1
            byte r1 = r8[r1]     // Catch:{ Exception -> 0x021f }
            r1 = r1 & 255(0xff, float:3.57E-43)
            r4 = r1 & 128(0x80, float:1.794E-43)
            if (r4 == 0) goto L_0x01d9
            r1 = r1 & 127(0x7f, float:1.78E-43)
            r4 = 0
        L_0x01c7:
            int r5 = r1 + -1
            if (r1 <= 0) goto L_0x01d8
            int r1 = r4 << 8
            int r4 = r2 + 1
            byte r2 = r8[r2]     // Catch:{ Exception -> 0x021f }
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r1 = r1 + r2
            r2 = r4
            r4 = r1
            r1 = r5
            goto L_0x01c7
        L_0x01d8:
            r1 = r4
        L_0x01d9:
            byte[] r4 = new byte[r1]     // Catch:{ Exception -> 0x021f }
            r7.eq_array = r4     // Catch:{ Exception -> 0x021f }
            byte[] r4 = r7.eq_array     // Catch:{ Exception -> 0x021f }
            java.lang.System.arraycopy(r8, r2, r4, r0, r1)     // Catch:{ Exception -> 0x021f }
            int r2 = r2 + r1
            int r2 = r2 + r3
            int r1 = r2 + 1
            byte r2 = r8[r2]     // Catch:{ Exception -> 0x021f }
            r2 = r2 & 255(0xff, float:3.57E-43)
            r4 = r2 & 128(0x80, float:1.794E-43)
            if (r4 == 0) goto L_0x0204
            r2 = r2 & 127(0x7f, float:1.78E-43)
            r4 = 0
        L_0x01f1:
            int r5 = r2 + -1
            if (r2 <= 0) goto L_0x0203
            int r2 = r4 << 8
            int r4 = r1 + 1
            byte r1 = r8[r1]     // Catch:{ Exception -> 0x021f }
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r1 = r1 + r2
            r2 = r5
            r6 = r4
            r4 = r1
            r1 = r6
            goto L_0x01f1
        L_0x0203:
            r2 = r4
        L_0x0204:
            byte[] r4 = new byte[r2]     // Catch:{ Exception -> 0x021f }
            r7.c_array = r4     // Catch:{ Exception -> 0x021f }
            byte[] r4 = r7.c_array     // Catch:{ Exception -> 0x021f }
            java.lang.System.arraycopy(r8, r1, r4, r0, r2)     // Catch:{ Exception -> 0x021f }
            byte[] r8 = r7.n_array     // Catch:{ Exception -> 0x021f }
            if (r8 == 0) goto L_0x021e
            java.math.BigInteger r8 = new java.math.BigInteger     // Catch:{ Exception -> 0x021f }
            byte[] r1 = r7.n_array     // Catch:{ Exception -> 0x021f }
            r8.<init>(r1)     // Catch:{ Exception -> 0x021f }
            int r8 = r8.bitLength()     // Catch:{ Exception -> 0x021f }
            r7.key_size = r8     // Catch:{ Exception -> 0x021f }
        L_0x021e:
            return r3
        L_0x021f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.KeyPairRSA.parse(byte[]):boolean");
    }

    public byte[] getPublicKeyBlob() {
        byte[] publicKeyBlob = super.getPublicKeyBlob();
        if (publicKeyBlob != null) {
            return publicKeyBlob;
        }
        byte[] bArr = this.pub_array;
        if (bArr == null) {
            return null;
        }
        return Buffer.fromBytes(new byte[][]{sshrsa, bArr, this.n_array}).buffer;
    }

    /* access modifiers changed from: package-private */
    public byte[] getKeyTypeName() {
        return sshrsa;
    }

    public int getKeySize() {
        return this.key_size;
    }

    public byte[] getSignature(byte[] bArr) {
        try {
            JSch jSch = this.jsch;
            SignatureRSA signatureRSA = (SignatureRSA) Class.forName(JSch.getConfig("signature.rsa")).newInstance();
            signatureRSA.init();
            signatureRSA.setPrvKey(this.prv_array, this.n_array);
            signatureRSA.update(bArr);
            return Buffer.fromBytes(new byte[][]{sshrsa, signatureRSA.sign()}).buffer;
        } catch (Exception unused) {
            return null;
        }
    }

    public Signature getVerifier() {
        try {
            JSch jSch = this.jsch;
            SignatureRSA signatureRSA = (SignatureRSA) Class.forName(JSch.getConfig("signature.rsa")).newInstance();
            signatureRSA.init();
            if (this.pub_array == null && this.n_array == null && getPublicKeyBlob() != null) {
                Buffer buffer = new Buffer(getPublicKeyBlob());
                buffer.getString();
                this.pub_array = buffer.getString();
                this.n_array = buffer.getString();
            }
            signatureRSA.setPubKey(this.pub_array, this.n_array);
            return signatureRSA;
        } catch (Exception unused) {
            return null;
        }
    }

    static KeyPair fromSSHAgent(JSch jSch, Buffer buffer) throws JSchException {
        byte[][] bytes = buffer.getBytes(8, "invalid key format");
        KeyPairRSA keyPairRSA = new KeyPairRSA(jSch, bytes[1], bytes[2], bytes[3]);
        keyPairRSA.c_array = bytes[4];
        keyPairRSA.p_array = bytes[5];
        keyPairRSA.q_array = bytes[6];
        keyPairRSA.publicKeyComment = new String(bytes[7]);
        keyPairRSA.vendor = 0;
        return keyPairRSA;
    }

    public byte[] forSSHAgent() throws JSchException {
        if (!isEncrypted()) {
            Buffer buffer = new Buffer();
            buffer.putString(sshrsa);
            buffer.putString(this.n_array);
            buffer.putString(this.pub_array);
            buffer.putString(this.prv_array);
            buffer.putString(getCArray());
            buffer.putString(this.p_array);
            buffer.putString(this.q_array);
            buffer.putString(Util.str2byte(this.publicKeyComment));
            byte[] bArr = new byte[buffer.getLength()];
            buffer.getByte(bArr, 0, bArr.length);
            return bArr;
        }
        throw new JSchException("key is encrypted.");
    }

    private byte[] getEPArray() {
        if (this.ep_array == null) {
            this.ep_array = new BigInteger(this.prv_array).mod(new BigInteger(this.p_array).subtract(BigInteger.ONE)).toByteArray();
        }
        return this.ep_array;
    }

    private byte[] getEQArray() {
        if (this.eq_array == null) {
            this.eq_array = new BigInteger(this.prv_array).mod(new BigInteger(this.q_array).subtract(BigInteger.ONE)).toByteArray();
        }
        return this.eq_array;
    }

    private byte[] getCArray() {
        if (this.c_array == null) {
            this.c_array = new BigInteger(this.q_array).modInverse(new BigInteger(this.p_array)).toByteArray();
        }
        return this.c_array;
    }

    public void dispose() {
        super.dispose();
        Util.bzero(this.prv_array);
    }
}
