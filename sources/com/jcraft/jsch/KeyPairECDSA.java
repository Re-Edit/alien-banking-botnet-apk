package com.jcraft.jsch;

public class KeyPairECDSA extends KeyPair {
    private static final byte[] begin = Util.str2byte("-----BEGIN EC PRIVATE KEY-----");
    private static final byte[] end = Util.str2byte("-----END EC PRIVATE KEY-----");
    private static String[] names = {"nistp256", "nistp384", "nistp521"};
    private static byte[][] oids = {new byte[]{6, 8, 42, -122, 72, -50, 61, 3, 1, 7}, new byte[]{6, 5, 43, -127, 4, 0, 34}, new byte[]{6, 5, 43, -127, 4, 0, 35}};
    private int key_size;
    private byte[] name;
    private byte[] prv_array;
    private byte[] r_array;
    private byte[] s_array;

    public int getKeyType() {
        return 3;
    }

    public KeyPairECDSA(JSch jSch) {
        this(jSch, (byte[]) null, (byte[]) null, (byte[]) null, (byte[]) null);
    }

    public KeyPairECDSA(JSch jSch, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) {
        super(jSch);
        this.name = Util.str2byte(names[0]);
        int i = 256;
        this.key_size = 256;
        if (bArr != null) {
            this.name = bArr;
        }
        this.r_array = bArr2;
        this.s_array = bArr3;
        this.prv_array = bArr4;
        if (bArr4 != null) {
            if (bArr4.length >= 64) {
                i = 521;
            } else if (bArr4.length >= 48) {
                i = 384;
            }
            this.key_size = i;
        }
    }

    /* access modifiers changed from: package-private */
    public void generate(int i) throws JSchException {
        this.key_size = i;
        try {
            JSch jSch = this.jsch;
            KeyPairGenECDSA keyPairGenECDSA = (KeyPairGenECDSA) Class.forName(JSch.getConfig("keypairgen.ecdsa")).newInstance();
            keyPairGenECDSA.init(i);
            this.prv_array = keyPairGenECDSA.getD();
            this.r_array = keyPairGenECDSA.getR();
            this.s_array = keyPairGenECDSA.getS();
            this.name = Util.str2byte(names[this.prv_array.length >= 64 ? 2 : this.prv_array.length >= 48 ? (char) 1 : 0]);
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
        byte[] bArr = {1};
        byte[][] bArr2 = oids;
        byte[] bArr3 = this.r_array;
        byte[] bArr4 = bArr2[bArr3.length >= 64 ? 2 : bArr3.length >= 48 ? (char) 1 : 0];
        byte[] point = toPoint(this.r_array, this.s_array);
        int i = ((point.length + 1) & 128) == 0 ? 3 : 4;
        byte[] bArr5 = new byte[(point.length + i)];
        System.arraycopy(point, 0, bArr5, i, point.length);
        bArr5[0] = 3;
        if (i == 3) {
            bArr5[1] = (byte) (point.length + 1);
        } else {
            bArr5[1] = -127;
            bArr5[2] = (byte) (point.length + 1);
        }
        int countLength = countLength(bArr.length) + 1 + bArr.length + 1 + countLength(this.prv_array.length) + this.prv_array.length + 1 + countLength(bArr4.length) + bArr4.length + 1 + countLength(bArr5.length) + bArr5.length;
        byte[] bArr6 = new byte[(countLength(countLength) + 1 + countLength)];
        writeDATA(bArr6, (byte) -95, writeDATA(bArr6, (byte) -96, writeOCTETSTRING(bArr6, writeINTEGER(bArr6, writeSEQUENCE(bArr6, 0, countLength), bArr), this.prv_array), bArr4), bArr5);
        return bArr6;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v31, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v24, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v25, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v32, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v33, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v35, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v27, resolved type: byte} */
    /* JADX WARNING: type inference failed for: r6v9, types: [int] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parse(byte[] r9) {
        /*
            r8 = this;
            r0 = 0
            int r1 = r8.vendor     // Catch:{ Exception -> 0x010d }
            r2 = 1
            if (r1 != r2) goto L_0x0007
            return r0
        L_0x0007:
            int r1 = r8.vendor     // Catch:{ Exception -> 0x010d }
            r3 = 2
            if (r1 != r3) goto L_0x000d
            return r0
        L_0x000d:
            byte r1 = r9[r0]     // Catch:{ Exception -> 0x010d }
            r4 = 48
            if (r1 == r4) goto L_0x0014
            return r0
        L_0x0014:
            byte r1 = r9[r2]     // Catch:{ Exception -> 0x010d }
            r1 = r1 & 255(0xff, float:3.57E-43)
            r5 = r1 & 128(0x80, float:1.794E-43)
            if (r5 == 0) goto L_0x002a
            r1 = r1 & 127(0x7f, float:1.78E-43)
            r5 = 2
        L_0x001f:
            int r6 = r1 + -1
            if (r1 <= 0) goto L_0x002b
            int r1 = r5 + 1
            byte r5 = r9[r5]     // Catch:{ Exception -> 0x010d }
            r5 = r1
            r1 = r6
            goto L_0x001f
        L_0x002a:
            r5 = 2
        L_0x002b:
            byte r1 = r9[r5]     // Catch:{ Exception -> 0x010d }
            if (r1 == r3) goto L_0x0030
            return r0
        L_0x0030:
            int r5 = r5 + r2
            int r1 = r5 + 1
            byte r3 = r9[r5]     // Catch:{ Exception -> 0x010d }
            r3 = r3 & 255(0xff, float:3.57E-43)
            r5 = r3 & 128(0x80, float:1.794E-43)
            if (r5 == 0) goto L_0x0051
            r3 = r3 & 127(0x7f, float:1.78E-43)
            r5 = 0
        L_0x003e:
            int r6 = r3 + -1
            if (r3 <= 0) goto L_0x0050
            int r3 = r5 << 8
            int r5 = r1 + 1
            byte r1 = r9[r1]     // Catch:{ Exception -> 0x010d }
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r1 = r1 + r3
            r3 = r6
            r7 = r5
            r5 = r1
            r1 = r7
            goto L_0x003e
        L_0x0050:
            r3 = r5
        L_0x0051:
            int r1 = r1 + r3
            int r1 = r1 + r2
            int r3 = r1 + 1
            byte r1 = r9[r1]     // Catch:{ Exception -> 0x010d }
            r1 = r1 & 255(0xff, float:3.57E-43)
            r5 = r1 & 128(0x80, float:1.794E-43)
            if (r5 == 0) goto L_0x0072
            r1 = r1 & 127(0x7f, float:1.78E-43)
            r5 = 0
        L_0x0060:
            int r6 = r1 + -1
            if (r1 <= 0) goto L_0x0071
            int r1 = r5 << 8
            int r5 = r3 + 1
            byte r3 = r9[r3]     // Catch:{ Exception -> 0x010d }
            r3 = r3 & 255(0xff, float:3.57E-43)
            int r1 = r1 + r3
            r3 = r5
            r5 = r1
            r1 = r6
            goto L_0x0060
        L_0x0071:
            r1 = r5
        L_0x0072:
            byte[] r5 = new byte[r1]     // Catch:{ Exception -> 0x010d }
            r8.prv_array = r5     // Catch:{ Exception -> 0x010d }
            byte[] r5 = r8.prv_array     // Catch:{ Exception -> 0x010d }
            java.lang.System.arraycopy(r9, r3, r5, r0, r1)     // Catch:{ Exception -> 0x010d }
            int r3 = r3 + r1
            int r3 = r3 + r2
            int r1 = r3 + 1
            byte r3 = r9[r3]     // Catch:{ Exception -> 0x010d }
            r3 = r3 & 255(0xff, float:3.57E-43)
            r5 = r3 & 128(0x80, float:1.794E-43)
            if (r5 == 0) goto L_0x009d
            r3 = r3 & 127(0x7f, float:1.78E-43)
            r5 = 0
        L_0x008a:
            int r6 = r3 + -1
            if (r3 <= 0) goto L_0x009c
            int r3 = r5 << 8
            int r5 = r1 + 1
            byte r1 = r9[r1]     // Catch:{ Exception -> 0x010d }
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r1 = r1 + r3
            r3 = r6
            r7 = r5
            r5 = r1
            r1 = r7
            goto L_0x008a
        L_0x009c:
            r3 = r5
        L_0x009d:
            byte[] r5 = new byte[r3]     // Catch:{ Exception -> 0x010d }
            java.lang.System.arraycopy(r9, r1, r5, r0, r3)     // Catch:{ Exception -> 0x010d }
            int r1 = r1 + r3
            r3 = 0
        L_0x00a4:
            byte[][] r6 = oids     // Catch:{ Exception -> 0x010d }
            int r6 = r6.length     // Catch:{ Exception -> 0x010d }
            if (r3 >= r6) goto L_0x00c1
            byte[][] r6 = oids     // Catch:{ Exception -> 0x010d }
            r6 = r6[r3]     // Catch:{ Exception -> 0x010d }
            boolean r6 = com.jcraft.jsch.Util.array_equals(r6, r5)     // Catch:{ Exception -> 0x010d }
            if (r6 == 0) goto L_0x00be
            java.lang.String[] r5 = names     // Catch:{ Exception -> 0x010d }
            r3 = r5[r3]     // Catch:{ Exception -> 0x010d }
            byte[] r3 = com.jcraft.jsch.Util.str2byte(r3)     // Catch:{ Exception -> 0x010d }
            r8.name = r3     // Catch:{ Exception -> 0x010d }
            goto L_0x00c1
        L_0x00be:
            int r3 = r3 + 1
            goto L_0x00a4
        L_0x00c1:
            int r1 = r1 + r2
            int r3 = r1 + 1
            byte r1 = r9[r1]     // Catch:{ Exception -> 0x010d }
            r1 = r1 & 255(0xff, float:3.57E-43)
            r5 = r1 & 128(0x80, float:1.794E-43)
            if (r5 == 0) goto L_0x00e1
            r1 = r1 & 127(0x7f, float:1.78E-43)
            r5 = 0
        L_0x00cf:
            int r6 = r1 + -1
            if (r1 <= 0) goto L_0x00e0
            int r1 = r5 << 8
            int r5 = r3 + 1
            byte r3 = r9[r3]     // Catch:{ Exception -> 0x010d }
            r3 = r3 & 255(0xff, float:3.57E-43)
            int r1 = r1 + r3
            r3 = r5
            r5 = r1
            r1 = r6
            goto L_0x00cf
        L_0x00e0:
            r1 = r5
        L_0x00e1:
            byte[] r5 = new byte[r1]     // Catch:{ Exception -> 0x010d }
            java.lang.System.arraycopy(r9, r3, r5, r0, r1)     // Catch:{ Exception -> 0x010d }
            byte[][] r9 = fromPoint(r5)     // Catch:{ Exception -> 0x010d }
            r1 = r9[r0]     // Catch:{ Exception -> 0x010d }
            r8.r_array = r1     // Catch:{ Exception -> 0x010d }
            r9 = r9[r2]     // Catch:{ Exception -> 0x010d }
            r8.s_array = r9     // Catch:{ Exception -> 0x010d }
            byte[] r9 = r8.prv_array     // Catch:{ Exception -> 0x010d }
            if (r9 == 0) goto L_0x010c
            byte[] r9 = r8.prv_array     // Catch:{ Exception -> 0x010d }
            int r9 = r9.length     // Catch:{ Exception -> 0x010d }
            r1 = 64
            if (r9 < r1) goto L_0x0100
            r9 = 521(0x209, float:7.3E-43)
            goto L_0x010a
        L_0x0100:
            byte[] r9 = r8.prv_array     // Catch:{ Exception -> 0x010d }
            int r9 = r9.length     // Catch:{ Exception -> 0x010d }
            if (r9 < r4) goto L_0x0108
            r9 = 384(0x180, float:5.38E-43)
            goto L_0x010a
        L_0x0108:
            r9 = 256(0x100, float:3.59E-43)
        L_0x010a:
            r8.key_size = r9     // Catch:{ Exception -> 0x010d }
        L_0x010c:
            return r2
        L_0x010d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.KeyPairECDSA.parse(byte[]):boolean");
    }

    public byte[] getPublicKeyBlob() {
        byte[] publicKeyBlob = super.getPublicKeyBlob();
        if (publicKeyBlob != null) {
            return publicKeyBlob;
        }
        if (this.r_array == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ecdsa-sha2-");
        stringBuffer.append(new String(this.name));
        byte[] bArr = this.r_array;
        byte[][] bArr2 = {Util.str2byte(stringBuffer.toString()), this.name, new byte[(bArr.length + 1 + this.s_array.length)]};
        bArr2[2][0] = 4;
        System.arraycopy(bArr, 0, bArr2[2], 1, bArr.length);
        byte[] bArr3 = this.s_array;
        System.arraycopy(bArr3, 0, bArr2[2], this.r_array.length + 1, bArr3.length);
        return Buffer.fromBytes(bArr2).buffer;
    }

    /* access modifiers changed from: package-private */
    public byte[] getKeyTypeName() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ecdsa-sha2-");
        stringBuffer.append(new String(this.name));
        return Util.str2byte(stringBuffer.toString());
    }

    public int getKeySize() {
        return this.key_size;
    }

    public byte[] getSignature(byte[] bArr) {
        try {
            JSch jSch = this.jsch;
            SignatureECDSA signatureECDSA = (SignatureECDSA) Class.forName(JSch.getConfig("signature.ecdsa")).newInstance();
            signatureECDSA.init();
            signatureECDSA.setPrvKey(this.prv_array);
            signatureECDSA.update(bArr);
            byte[] sign = signatureECDSA.sign();
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("ecdsa-sha2-");
            stringBuffer.append(new String(this.name));
            return Buffer.fromBytes(new byte[][]{Util.str2byte(stringBuffer.toString()), sign}).buffer;
        } catch (Exception unused) {
            return null;
        }
    }

    public Signature getVerifier() {
        try {
            JSch jSch = this.jsch;
            SignatureECDSA signatureECDSA = (SignatureECDSA) Class.forName(JSch.getConfig("signature.ecdsa")).newInstance();
            signatureECDSA.init();
            if (this.r_array == null && this.s_array == null && getPublicKeyBlob() != null) {
                Buffer buffer = new Buffer(getPublicKeyBlob());
                buffer.getString();
                buffer.getString();
                byte[][] fromPoint = fromPoint(buffer.getString());
                this.r_array = fromPoint[0];
                this.s_array = fromPoint[1];
            }
            signatureECDSA.setPubKey(this.r_array, this.s_array);
            return signatureECDSA;
        } catch (Exception unused) {
            return null;
        }
    }

    static KeyPair fromSSHAgent(JSch jSch, Buffer buffer) throws JSchException {
        byte[][] bytes = buffer.getBytes(5, "invalid key format");
        byte[] bArr = bytes[1];
        byte[][] fromPoint = fromPoint(bytes[2]);
        KeyPairECDSA keyPairECDSA = new KeyPairECDSA(jSch, bArr, fromPoint[0], fromPoint[1], bytes[3]);
        keyPairECDSA.publicKeyComment = new String(bytes[4]);
        keyPairECDSA.vendor = 0;
        return keyPairECDSA;
    }

    public byte[] forSSHAgent() throws JSchException {
        if (!isEncrypted()) {
            Buffer buffer = new Buffer();
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("ecdsa-sha2-");
            stringBuffer.append(new String(this.name));
            buffer.putString(Util.str2byte(stringBuffer.toString()));
            buffer.putString(this.name);
            buffer.putString(toPoint(this.r_array, this.s_array));
            buffer.putString(this.prv_array);
            buffer.putString(Util.str2byte(this.publicKeyComment));
            byte[] bArr = new byte[buffer.getLength()];
            buffer.getByte(bArr, 0, bArr.length);
            return bArr;
        }
        throw new JSchException("key is encrypted.");
    }

    static byte[] toPoint(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[(bArr.length + 1 + bArr2.length)];
        bArr3[0] = 4;
        System.arraycopy(bArr, 0, bArr3, 1, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length + 1, bArr2.length);
        return bArr3;
    }

    static byte[][] fromPoint(byte[] bArr) {
        int i = 0;
        while (bArr[i] != 4) {
            i++;
        }
        int i2 = i + 1;
        byte[] bArr2 = new byte[((bArr.length - i2) / 2)];
        byte[] bArr3 = new byte[((bArr.length - i2) / 2)];
        System.arraycopy(bArr, i2, bArr2, 0, bArr2.length);
        System.arraycopy(bArr, i2 + bArr2.length, bArr3, 0, bArr3.length);
        return new byte[][]{bArr2, bArr3};
    }

    public void dispose() {
        super.dispose();
        Util.bzero(this.prv_array);
    }
}
