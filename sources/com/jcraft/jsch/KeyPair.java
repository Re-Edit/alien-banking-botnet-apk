package com.jcraft.jsch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Vector;

public abstract class KeyPair {
    public static final int DSA = 1;
    public static final int ECDSA = 3;
    public static final int ERROR = 0;
    public static final int RSA = 2;
    public static final int UNKNOWN = 4;
    static final int VENDOR_FSECURE = 1;
    static final int VENDOR_OPENSSH = 0;
    static final int VENDOR_PKCS8 = 3;
    static final int VENDOR_PUTTY = 2;
    private static final byte[] cr = Util.str2byte("\n");
    static byte[][] header = {Util.str2byte("Proc-Type: 4,ENCRYPTED"), Util.str2byte("DEK-Info: DES-EDE3-CBC,")};
    private static final String[] header1 = {"PuTTY-User-Key-File-2: ", "Encryption: ", "Comment: ", "Public-Lines: "};
    private static final String[] header2 = {"Private-Lines: "};
    private static final String[] header3 = {"Private-MAC: "};
    private static byte[] space = Util.str2byte(" ");
    private Cipher cipher;
    protected byte[] data = null;
    protected boolean encrypted = false;
    private HASH hash;
    private byte[] iv = null;
    JSch jsch = null;
    private byte[] passphrase;
    protected String publicKeyComment = "no comment";
    private byte[] publickeyblob = null;
    private Random random;
    int vendor = 0;

    private static byte a2b(byte b) {
        return (48 > b || b > 57) ? (byte) ((b - 97) + 10) : (byte) (b - 48);
    }

    private static byte b2a(byte b) {
        return (b < 0 || b > 9) ? (byte) ((b - 10) + 65) : (byte) (b + 48);
    }

    /* access modifiers changed from: package-private */
    public int countLength(int i) {
        int i2 = 1;
        if (i <= 127) {
            return 1;
        }
        while (i > 0) {
            i >>>= 8;
            i2++;
        }
        return i2;
    }

    public abstract byte[] forSSHAgent() throws JSchException;

    /* access modifiers changed from: package-private */
    public abstract void generate(int i) throws JSchException;

    /* access modifiers changed from: package-private */
    public abstract byte[] getBegin();

    /* access modifiers changed from: package-private */
    public abstract byte[] getEnd();

    /* access modifiers changed from: package-private */
    public abstract int getKeySize();

    public abstract int getKeyType();

    /* access modifiers changed from: package-private */
    public abstract byte[] getKeyTypeName();

    /* access modifiers changed from: package-private */
    public abstract byte[] getPrivateKey();

    public abstract byte[] getSignature(byte[] bArr);

    public abstract Signature getVerifier();

    /* access modifiers changed from: package-private */
    public abstract boolean parse(byte[] bArr);

    public static KeyPair genKeyPair(JSch jSch, int i) throws JSchException {
        return genKeyPair(jSch, i, 1024);
    }

    public static KeyPair genKeyPair(JSch jSch, int i, int i2) throws JSchException {
        KeyPair keyPair;
        if (i == 1) {
            keyPair = new KeyPairDSA(jSch);
        } else if (i == 2) {
            keyPair = new KeyPairRSA(jSch);
        } else {
            keyPair = i == 3 ? new KeyPairECDSA(jSch) : null;
        }
        if (keyPair != null) {
            keyPair.generate(i2);
        }
        return keyPair;
    }

    public String getPublicKeyComment() {
        return this.publicKeyComment;
    }

    public void setPublicKeyComment(String str) {
        this.publicKeyComment = str;
    }

    public KeyPair(JSch jSch) {
        this.jsch = jSch;
    }

    public void writePrivateKey(OutputStream outputStream) {
        writePrivateKey(outputStream, (byte[]) null);
    }

    public void writePrivateKey(OutputStream outputStream, byte[] bArr) {
        if (bArr == null) {
            bArr = this.passphrase;
        }
        byte[] privateKey = getPrivateKey();
        byte[][] bArr2 = new byte[1][];
        byte[] encrypt = encrypt(privateKey, bArr2, bArr);
        if (encrypt != privateKey) {
            Util.bzero(privateKey);
        }
        int i = 0;
        byte[] bArr3 = bArr2[0];
        byte[] base64 = Util.toBase64(encrypt, 0, encrypt.length);
        try {
            outputStream.write(getBegin());
            outputStream.write(cr);
            if (bArr != null) {
                outputStream.write(header[0]);
                outputStream.write(cr);
                outputStream.write(header[1]);
                for (int i2 = 0; i2 < bArr3.length; i2++) {
                    outputStream.write(b2a((byte) ((bArr3[i2] >>> 4) & 15)));
                    outputStream.write(b2a((byte) (bArr3[i2] & 15)));
                }
                outputStream.write(cr);
                outputStream.write(cr);
            }
            while (true) {
                if (i < base64.length) {
                    int i3 = i + 64;
                    if (i3 >= base64.length) {
                        outputStream.write(base64, i, base64.length - i);
                        outputStream.write(cr);
                        break;
                    }
                    outputStream.write(base64, i, 64);
                    outputStream.write(cr);
                    i = i3;
                } else {
                    break;
                }
            }
            outputStream.write(getEnd());
            outputStream.write(cr);
        } catch (Exception unused) {
        }
    }

    public byte[] getPublicKeyBlob() {
        return this.publickeyblob;
    }

    public void writePublicKey(OutputStream outputStream, String str) {
        byte[] publicKeyBlob = getPublicKeyBlob();
        byte[] base64 = Util.toBase64(publicKeyBlob, 0, publicKeyBlob.length);
        try {
            outputStream.write(getKeyTypeName());
            outputStream.write(space);
            outputStream.write(base64, 0, base64.length);
            outputStream.write(space);
            outputStream.write(Util.str2byte(str));
            outputStream.write(cr);
        } catch (Exception unused) {
        }
    }

    public void writePublicKey(String str, String str2) throws FileNotFoundException, IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(str);
        writePublicKey((OutputStream) fileOutputStream, str2);
        fileOutputStream.close();
    }

    public void writeSECSHPublicKey(OutputStream outputStream, String str) {
        byte[] publicKeyBlob = getPublicKeyBlob();
        int i = 0;
        byte[] base64 = Util.toBase64(publicKeyBlob, 0, publicKeyBlob.length);
        try {
            outputStream.write(Util.str2byte("---- BEGIN SSH2 PUBLIC KEY ----"));
            outputStream.write(cr);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Comment: \"");
            stringBuffer.append(str);
            stringBuffer.append("\"");
            outputStream.write(Util.str2byte(stringBuffer.toString()));
            outputStream.write(cr);
            while (i < base64.length) {
                int i2 = 70;
                if (base64.length - i < 70) {
                    i2 = base64.length - i;
                }
                outputStream.write(base64, i, i2);
                outputStream.write(cr);
                i += i2;
            }
            outputStream.write(Util.str2byte("---- END SSH2 PUBLIC KEY ----"));
            outputStream.write(cr);
        } catch (Exception unused) {
        }
    }

    public void writeSECSHPublicKey(String str, String str2) throws FileNotFoundException, IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(str);
        writeSECSHPublicKey((OutputStream) fileOutputStream, str2);
        fileOutputStream.close();
    }

    public void writePrivateKey(String str) throws FileNotFoundException, IOException {
        writePrivateKey(str, (byte[]) null);
    }

    public void writePrivateKey(String str, byte[] bArr) throws FileNotFoundException, IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(str);
        writePrivateKey((OutputStream) fileOutputStream, bArr);
        fileOutputStream.close();
    }

    public String getFingerPrint() {
        if (this.hash == null) {
            this.hash = genHash();
        }
        byte[] publicKeyBlob = getPublicKeyBlob();
        if (publicKeyBlob == null) {
            return null;
        }
        return Util.getFingerPrint(this.hash, publicKeyBlob);
    }

    private byte[] encrypt(byte[] bArr, byte[][] bArr2, byte[] bArr3) {
        if (bArr3 == null) {
            return bArr;
        }
        if (this.cipher == null) {
            this.cipher = genCipher();
        }
        byte[] bArr4 = new byte[this.cipher.getIVSize()];
        bArr2[0] = bArr4;
        if (this.random == null) {
            this.random = genRandom();
        }
        this.random.fill(bArr4, 0, bArr4.length);
        byte[] genKey = genKey(bArr3, bArr4);
        int iVSize = this.cipher.getIVSize();
        byte[] bArr5 = new byte[(((bArr.length / iVSize) + 1) * iVSize)];
        System.arraycopy(bArr, 0, bArr5, 0, bArr.length);
        int length = iVSize - (bArr.length % iVSize);
        int length2 = bArr5.length;
        while (true) {
            length2--;
            if (bArr5.length - length <= length2) {
                bArr5[length2] = (byte) length;
            } else {
                try {
                    break;
                } catch (Exception unused) {
                }
            }
        }
        this.cipher.init(0, genKey, bArr4);
        this.cipher.update(bArr5, 0, bArr5.length, bArr5, 0);
        Util.bzero(genKey);
        return bArr5;
    }

    private byte[] decrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        try {
            byte[] genKey = genKey(bArr2, bArr3);
            this.cipher.init(1, genKey, bArr3);
            Util.bzero(genKey);
            byte[] bArr4 = new byte[bArr.length];
            this.cipher.update(bArr, 0, bArr.length, bArr4, 0);
            return bArr4;
        } catch (Exception unused) {
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public int writeSEQUENCE(byte[] bArr, int i, int i2) {
        bArr[i] = 48;
        return writeLength(bArr, i + 1, i2);
    }

    /* access modifiers changed from: package-private */
    public int writeINTEGER(byte[] bArr, int i, byte[] bArr2) {
        bArr[i] = 2;
        int writeLength = writeLength(bArr, i + 1, bArr2.length);
        System.arraycopy(bArr2, 0, bArr, writeLength, bArr2.length);
        return writeLength + bArr2.length;
    }

    /* access modifiers changed from: package-private */
    public int writeOCTETSTRING(byte[] bArr, int i, byte[] bArr2) {
        bArr[i] = 4;
        int writeLength = writeLength(bArr, i + 1, bArr2.length);
        System.arraycopy(bArr2, 0, bArr, writeLength, bArr2.length);
        return writeLength + bArr2.length;
    }

    /* access modifiers changed from: package-private */
    public int writeDATA(byte[] bArr, byte b, int i, byte[] bArr2) {
        bArr[i] = b;
        int writeLength = writeLength(bArr, i + 1, bArr2.length);
        System.arraycopy(bArr2, 0, bArr, writeLength, bArr2.length);
        return writeLength + bArr2.length;
    }

    /* access modifiers changed from: package-private */
    public int writeLength(byte[] bArr, int i, int i2) {
        int countLength = countLength(i2) - 1;
        if (countLength == 0) {
            int i3 = i + 1;
            bArr[i] = (byte) i2;
            return i3;
        }
        int i4 = i + 1;
        bArr[i] = (byte) (countLength | 128);
        int i5 = i4 + countLength;
        while (countLength > 0) {
            bArr[(i4 + countLength) - 1] = (byte) (i2 & 255);
            i2 >>>= 8;
            countLength--;
        }
        return i5;
    }

    private Random genRandom() {
        if (this.random == null) {
            try {
                JSch jSch = this.jsch;
                this.random = (Random) Class.forName(JSch.getConfig("random")).newInstance();
            } catch (Exception e) {
                PrintStream printStream = System.err;
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("connect: random ");
                stringBuffer.append(e);
                printStream.println(stringBuffer.toString());
            }
        }
        return this.random;
    }

    private HASH genHash() {
        try {
            JSch jSch = this.jsch;
            this.hash = (HASH) Class.forName(JSch.getConfig("md5")).newInstance();
            this.hash.init();
        } catch (Exception unused) {
        }
        return this.hash;
    }

    private Cipher genCipher() {
        try {
            JSch jSch = this.jsch;
            this.cipher = (Cipher) Class.forName(JSch.getConfig("3des-cbc")).newInstance();
        } catch (Exception unused) {
        }
        return this.cipher;
    }

    /* access modifiers changed from: package-private */
    public synchronized byte[] genKey(byte[] bArr, byte[] bArr2) {
        byte[] bArr3;
        if (this.cipher == null) {
            this.cipher = genCipher();
        }
        if (this.hash == null) {
            this.hash = genHash();
        }
        bArr3 = new byte[this.cipher.getBlockSize()];
        int blockSize = this.hash.getBlockSize();
        byte[] bArr4 = new byte[(((bArr3.length / blockSize) * blockSize) + (bArr3.length % blockSize == 0 ? 0 : blockSize))];
        byte[] bArr5 = null;
        try {
            if (this.vendor == 0) {
                byte[] bArr6 = null;
                int i = 0;
                while (i + blockSize <= bArr4.length) {
                    if (bArr6 != null) {
                        this.hash.update(bArr6, 0, bArr6.length);
                    }
                    this.hash.update(bArr, 0, bArr.length);
                    HASH hash2 = this.hash;
                    int i2 = 8;
                    if (bArr2.length <= 8) {
                        i2 = bArr2.length;
                    }
                    hash2.update(bArr2, 0, i2);
                    bArr6 = this.hash.digest();
                    System.arraycopy(bArr6, 0, bArr4, i, bArr6.length);
                    i += bArr6.length;
                }
                System.arraycopy(bArr4, 0, bArr3, 0, bArr3.length);
            } else if (this.vendor == 1) {
                int i3 = 0;
                while (i3 + blockSize <= bArr4.length) {
                    if (bArr5 != null) {
                        this.hash.update(bArr5, 0, bArr5.length);
                    }
                    this.hash.update(bArr, 0, bArr.length);
                    bArr5 = this.hash.digest();
                    System.arraycopy(bArr5, 0, bArr4, i3, bArr5.length);
                    i3 += bArr5.length;
                }
                System.arraycopy(bArr4, 0, bArr3, 0, bArr3.length);
            } else if (this.vendor == 2) {
                JSch jSch = this.jsch;
                HASH hash3 = (HASH) Class.forName(JSch.getConfig("sha-1")).newInstance();
                byte[] bArr7 = new byte[4];
                bArr3 = new byte[40];
                for (int i4 = 0; i4 < 2; i4++) {
                    hash3.init();
                    bArr7[3] = (byte) i4;
                    hash3.update(bArr7, 0, bArr7.length);
                    hash3.update(bArr, 0, bArr.length);
                    System.arraycopy(hash3.digest(), 0, bArr3, i4 * 20, 20);
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return bArr3;
    }

    public void setPassphrase(String str) {
        if (str == null || str.length() == 0) {
            setPassphrase((byte[]) null);
        } else {
            setPassphrase(Util.str2byte(str));
        }
    }

    public void setPassphrase(byte[] bArr) {
        if (bArr != null && bArr.length == 0) {
            bArr = null;
        }
        this.passphrase = bArr;
    }

    public boolean isEncrypted() {
        return this.encrypted;
    }

    public boolean decrypt(String str) {
        if (str == null || str.length() == 0) {
            return !this.encrypted;
        }
        return decrypt(Util.str2byte(str));
    }

    public boolean decrypt(byte[] bArr) {
        boolean z = this.encrypted;
        if (!z) {
            return true;
        }
        if (bArr == null) {
            return !z;
        }
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr2.length);
        byte[] decrypt = decrypt(this.data, bArr2, this.iv);
        Util.bzero(bArr2);
        if (parse(decrypt)) {
            this.encrypted = false;
        }
        return !this.encrypted;
    }

    public static KeyPair load(JSch jSch, String str) throws JSchException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append(".pub");
        String stringBuffer2 = stringBuffer.toString();
        if (!new File(stringBuffer2).exists()) {
            stringBuffer2 = null;
        }
        return load(jSch, str, stringBuffer2);
    }

    public static KeyPair load(JSch jSch, String str, String str2) throws JSchException {
        String str3;
        byte[] bArr;
        try {
            byte[] fromFile = Util.fromFile(str);
            if (str2 == null) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(str);
                stringBuffer.append(".pub");
                str3 = stringBuffer.toString();
            } else {
                str3 = str2;
            }
            try {
                bArr = Util.fromFile(str3);
            } catch (IOException e) {
                if (str2 == null) {
                    bArr = null;
                } else {
                    throw new JSchException(e.toString(), e);
                }
            }
            try {
                return load(jSch, fromFile, bArr);
            } finally {
                Util.bzero(fromFile);
            }
        } catch (IOException e2) {
            throw new JSchException(e2.toString(), e2);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:136:0x01d4, code lost:
        r2 = new java.lang.StringBuffer();
        r2.append("invalid privatekey: ");
        r2.append(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x01ea, code lost:
        throw new com.jcraft.jsch.JSchException(r2.toString());
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.jcraft.jsch.KeyPair load(com.jcraft.jsch.JSch r18, byte[] r19, byte[] r20) throws com.jcraft.jsch.JSchException {
        /*
            r0 = r18
            r1 = r19
            r2 = r20
            r3 = 8
            byte[] r4 = new byte[r3]
            java.lang.String r5 = ""
            r6 = 7
            r7 = 4
            r8 = 2
            r9 = 3
            r10 = 0
            r11 = 1
            if (r2 != 0) goto L_0x009a
            if (r1 == 0) goto L_0x009a
            int r12 = r1.length
            r13 = 11
            if (r12 <= r13) goto L_0x009a
            byte r12 = r1[r10]
            if (r12 != 0) goto L_0x009a
            byte r12 = r1[r11]
            if (r12 != 0) goto L_0x009a
            byte r12 = r1[r8]
            if (r12 != 0) goto L_0x009a
            byte r12 = r1[r9]
            if (r12 == r6) goto L_0x0031
            byte r12 = r1[r9]
            r13 = 19
            if (r12 != r13) goto L_0x009a
        L_0x0031:
            com.jcraft.jsch.Buffer r2 = new com.jcraft.jsch.Buffer
            r2.<init>((byte[]) r1)
            int r3 = r1.length
            r2.skip(r3)
            java.lang.String r3 = new java.lang.String
            byte[] r4 = r2.getString()
            r3.<init>(r4)
            r2.rewind()
            java.lang.String r4 = "ssh-rsa"
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x0053
            com.jcraft.jsch.KeyPair r0 = com.jcraft.jsch.KeyPairRSA.fromSSHAgent(r0, r2)
            goto L_0x0099
        L_0x0053:
            java.lang.String r4 = "ssh-dss"
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x0060
            com.jcraft.jsch.KeyPair r0 = com.jcraft.jsch.KeyPairDSA.fromSSHAgent(r0, r2)
            goto L_0x0099
        L_0x0060:
            java.lang.String r4 = "ecdsa-sha2-nistp256"
            boolean r4 = r3.equals(r4)
            if (r4 != 0) goto L_0x0095
            java.lang.String r4 = "ecdsa-sha2-nistp384"
            boolean r4 = r3.equals(r4)
            if (r4 != 0) goto L_0x0095
            java.lang.String r4 = "ecdsa-sha2-nistp512"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0079
            goto L_0x0095
        L_0x0079:
            com.jcraft.jsch.JSchException r0 = new com.jcraft.jsch.JSchException
            java.lang.StringBuffer r2 = new java.lang.StringBuffer
            r2.<init>()
            java.lang.String r3 = "privatekey: invalid key "
            r2.append(r3)
            java.lang.String r3 = new java.lang.String
            r3.<init>(r1, r7, r6)
            r2.append(r3)
            java.lang.String r1 = r2.toString()
            r0.<init>(r1)
            throw r0
        L_0x0095:
            com.jcraft.jsch.KeyPair r0 = com.jcraft.jsch.KeyPairECDSA.fromSSHAgent(r0, r2)
        L_0x0099:
            return r0
        L_0x009a:
            if (r1 == 0) goto L_0x00a6
            com.jcraft.jsch.KeyPair r12 = loadPPK(r18, r19)     // Catch:{ Exception -> 0x00a3 }
            if (r12 == 0) goto L_0x00a6
            return r12
        L_0x00a3:
            r0 = move-exception
            goto L_0x0552
        L_0x00a6:
            if (r1 == 0) goto L_0x00aa
            int r12 = r1.length     // Catch:{ Exception -> 0x00a3 }
            goto L_0x00ab
        L_0x00aa:
            r12 = 0
        L_0x00ab:
            r13 = 0
        L_0x00ac:
            r14 = 45
            if (r13 >= r12) goto L_0x00d3
            byte r15 = r1[r13]     // Catch:{ Exception -> 0x00a3 }
            if (r15 != r14) goto L_0x00cf
            int r15 = r13 + 4
            if (r15 >= r12) goto L_0x00cf
            int r16 = r13 + 1
            byte r6 = r1[r16]     // Catch:{ Exception -> 0x00a3 }
            if (r6 != r14) goto L_0x00cf
            int r6 = r13 + 2
            byte r6 = r1[r6]     // Catch:{ Exception -> 0x00a3 }
            if (r6 != r14) goto L_0x00cf
            int r6 = r13 + 3
            byte r6 = r1[r6]     // Catch:{ Exception -> 0x00a3 }
            if (r6 != r14) goto L_0x00cf
            byte r6 = r1[r15]     // Catch:{ Exception -> 0x00a3 }
            if (r6 != r14) goto L_0x00cf
            goto L_0x00d3
        L_0x00cf:
            int r13 = r13 + 1
            r6 = 7
            goto L_0x00ac
        L_0x00d3:
            r6 = 0
            r17 = r6
            r15 = 0
            r16 = 1
            r6 = r4
            r4 = 0
        L_0x00db:
            if (r13 >= r12) goto L_0x0440
            byte r11 = r1[r13]     // Catch:{ Exception -> 0x00a3 }
            r10 = 66
            r8 = 65
            r3 = 83
            r7 = 69
            if (r11 != r10) goto L_0x0202
            int r10 = r13 + 3
            if (r10 >= r12) goto L_0x0202
            int r11 = r13 + 1
            byte r11 = r1[r11]     // Catch:{ Exception -> 0x00a3 }
            if (r11 != r7) goto L_0x0202
            int r11 = r13 + 2
            byte r11 = r1[r11]     // Catch:{ Exception -> 0x00a3 }
            r14 = 71
            if (r11 != r14) goto L_0x0202
            byte r10 = r1[r10]     // Catch:{ Exception -> 0x00a3 }
            r11 = 73
            if (r10 != r11) goto L_0x0202
            int r13 = r13 + 6
            int r4 = r13 + 2
            if (r4 >= r12) goto L_0x01eb
            byte r10 = r1[r13]     // Catch:{ Exception -> 0x00a3 }
            r11 = 68
            if (r10 != r11) goto L_0x011a
            int r10 = r13 + 1
            byte r10 = r1[r10]     // Catch:{ Exception -> 0x00a3 }
            if (r10 != r3) goto L_0x011a
            byte r10 = r1[r4]     // Catch:{ Exception -> 0x00a3 }
            if (r10 != r8) goto L_0x011a
            r4 = 1
            goto L_0x01cb
        L_0x011a:
            byte r10 = r1[r13]     // Catch:{ Exception -> 0x00a3 }
            r11 = 82
            if (r10 != r11) goto L_0x012d
            int r10 = r13 + 1
            byte r10 = r1[r10]     // Catch:{ Exception -> 0x00a3 }
            if (r10 != r3) goto L_0x012d
            byte r10 = r1[r4]     // Catch:{ Exception -> 0x00a3 }
            if (r10 != r8) goto L_0x012d
            r4 = 2
            goto L_0x01cb
        L_0x012d:
            byte r10 = r1[r13]     // Catch:{ Exception -> 0x00a3 }
            if (r10 != r7) goto L_0x013c
            int r10 = r13 + 1
            byte r10 = r1[r10]     // Catch:{ Exception -> 0x00a3 }
            r11 = 67
            if (r10 != r11) goto L_0x013c
            r4 = 3
            goto L_0x01cb
        L_0x013c:
            byte r10 = r1[r13]     // Catch:{ Exception -> 0x00a3 }
            if (r10 != r3) goto L_0x0150
            int r10 = r13 + 1
            byte r10 = r1[r10]     // Catch:{ Exception -> 0x00a3 }
            if (r10 != r3) goto L_0x0150
            byte r3 = r1[r4]     // Catch:{ Exception -> 0x00a3 }
            r10 = 72
            if (r3 != r10) goto L_0x0150
            r4 = 4
            r15 = 1
            goto L_0x01cb
        L_0x0150:
            int r3 = r13 + 6
            if (r3 >= r12) goto L_0x0188
            byte r10 = r1[r13]     // Catch:{ Exception -> 0x00a3 }
            r11 = 80
            if (r10 != r11) goto L_0x0188
            int r10 = r13 + 1
            byte r10 = r1[r10]     // Catch:{ Exception -> 0x00a3 }
            r11 = 82
            if (r10 != r11) goto L_0x0188
            byte r10 = r1[r4]     // Catch:{ Exception -> 0x00a3 }
            r11 = 73
            if (r10 != r11) goto L_0x0188
            int r10 = r13 + 3
            byte r11 = r1[r10]     // Catch:{ Exception -> 0x00a3 }
            r14 = 86
            if (r11 != r14) goto L_0x0188
            int r11 = r13 + 4
            byte r11 = r1[r11]     // Catch:{ Exception -> 0x00a3 }
            if (r11 != r8) goto L_0x0188
            int r8 = r13 + 5
            byte r8 = r1[r8]     // Catch:{ Exception -> 0x00a3 }
            r11 = 84
            if (r8 != r11) goto L_0x0188
            byte r8 = r1[r3]     // Catch:{ Exception -> 0x00a3 }
            if (r8 != r7) goto L_0x0188
            r13 = r10
            r4 = 4
            r15 = 3
            r16 = 0
            goto L_0x01cb
        L_0x0188:
            int r8 = r13 + 8
            if (r8 >= r12) goto L_0x01d4
            byte r10 = r1[r13]     // Catch:{ Exception -> 0x00a3 }
            if (r10 != r7) goto L_0x01d4
            int r10 = r13 + 1
            byte r10 = r1[r10]     // Catch:{ Exception -> 0x00a3 }
            r11 = 78
            if (r10 != r11) goto L_0x01d4
            byte r4 = r1[r4]     // Catch:{ Exception -> 0x00a3 }
            r10 = 67
            if (r4 != r10) goto L_0x01d4
            int r4 = r13 + 3
            byte r4 = r1[r4]     // Catch:{ Exception -> 0x00a3 }
            r10 = 82
            if (r4 != r10) goto L_0x01d4
            int r4 = r13 + 4
            byte r4 = r1[r4]     // Catch:{ Exception -> 0x00a3 }
            r10 = 89
            if (r4 != r10) goto L_0x01d4
            int r4 = r13 + 5
            byte r10 = r1[r4]     // Catch:{ Exception -> 0x00a3 }
            r11 = 80
            if (r10 != r11) goto L_0x01d4
            byte r3 = r1[r3]     // Catch:{ Exception -> 0x00a3 }
            r10 = 84
            if (r3 != r10) goto L_0x01d4
            int r13 = r13 + 7
            byte r3 = r1[r13]     // Catch:{ Exception -> 0x00a3 }
            if (r3 != r7) goto L_0x01d4
            byte r3 = r1[r8]     // Catch:{ Exception -> 0x00a3 }
            r7 = 68
            if (r3 != r7) goto L_0x01d4
            r13 = r4
            r4 = 4
            r15 = 3
        L_0x01cb:
            int r13 = r13 + r9
            r7 = 4
            r8 = 2
            r10 = 0
            r11 = 1
            r14 = 45
            goto L_0x00db
        L_0x01d4:
            com.jcraft.jsch.JSchException r0 = new com.jcraft.jsch.JSchException     // Catch:{ Exception -> 0x00a3 }
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x00a3 }
            r2.<init>()     // Catch:{ Exception -> 0x00a3 }
            java.lang.String r3 = "invalid privatekey: "
            r2.append(r3)     // Catch:{ Exception -> 0x00a3 }
            r2.append(r1)     // Catch:{ Exception -> 0x00a3 }
            java.lang.String r1 = r2.toString()     // Catch:{ Exception -> 0x00a3 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x00a3 }
            throw r0     // Catch:{ Exception -> 0x00a3 }
        L_0x01eb:
            com.jcraft.jsch.JSchException r0 = new com.jcraft.jsch.JSchException     // Catch:{ Exception -> 0x00a3 }
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x00a3 }
            r2.<init>()     // Catch:{ Exception -> 0x00a3 }
            java.lang.String r3 = "invalid privatekey: "
            r2.append(r3)     // Catch:{ Exception -> 0x00a3 }
            r2.append(r1)     // Catch:{ Exception -> 0x00a3 }
            java.lang.String r1 = r2.toString()     // Catch:{ Exception -> 0x00a3 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x00a3 }
            throw r0     // Catch:{ Exception -> 0x00a3 }
        L_0x0202:
            byte r10 = r1[r13]     // Catch:{ Exception -> 0x00a3 }
            if (r10 != r8) goto L_0x0283
            int r10 = r13 + 7
            if (r10 >= r12) goto L_0x0283
            int r11 = r13 + 1
            byte r11 = r1[r11]     // Catch:{ Exception -> 0x00a3 }
            if (r11 != r7) goto L_0x0283
            int r11 = r13 + 2
            byte r11 = r1[r11]     // Catch:{ Exception -> 0x00a3 }
            if (r11 != r3) goto L_0x0283
            int r11 = r13 + 3
            byte r11 = r1[r11]     // Catch:{ Exception -> 0x00a3 }
            r14 = 45
            if (r11 != r14) goto L_0x0283
            int r11 = r13 + 4
            byte r11 = r1[r11]     // Catch:{ Exception -> 0x00a3 }
            r14 = 50
            if (r11 != r14) goto L_0x0283
            int r11 = r13 + 5
            byte r11 = r1[r11]     // Catch:{ Exception -> 0x00a3 }
            r14 = 53
            if (r11 != r14) goto L_0x0283
            int r11 = r13 + 6
            byte r11 = r1[r11]     // Catch:{ Exception -> 0x00a3 }
            r14 = 54
            if (r11 != r14) goto L_0x0283
            byte r10 = r1[r10]     // Catch:{ Exception -> 0x00a3 }
            r11 = 45
            if (r10 != r11) goto L_0x0283
            int r13 = r13 + 8
            java.lang.String r3 = "aes256-cbc"
            java.lang.String r3 = com.jcraft.jsch.JSch.getConfig(r3)     // Catch:{ Exception -> 0x00a3 }
            boolean r3 = com.jcraft.jsch.Session.checkCipher(r3)     // Catch:{ Exception -> 0x00a3 }
            if (r3 == 0) goto L_0x026c
            java.lang.String r3 = "aes256-cbc"
            java.lang.String r3 = com.jcraft.jsch.JSch.getConfig(r3)     // Catch:{ Exception -> 0x00a3 }
            java.lang.Class r3 = java.lang.Class.forName(r3)     // Catch:{ Exception -> 0x00a3 }
            java.lang.Object r3 = r3.newInstance()     // Catch:{ Exception -> 0x00a3 }
            com.jcraft.jsch.Cipher r3 = (com.jcraft.jsch.Cipher) r3     // Catch:{ Exception -> 0x00a3 }
            r17 = r3
            com.jcraft.jsch.Cipher r17 = (com.jcraft.jsch.Cipher) r17     // Catch:{ Exception -> 0x00a3 }
            int r3 = r17.getIVSize()     // Catch:{ Exception -> 0x00a3 }
            byte[] r6 = new byte[r3]     // Catch:{ Exception -> 0x00a3 }
            r7 = 4
            r8 = 2
            r10 = 0
            r11 = 1
            r14 = 45
            goto L_0x00db
        L_0x026c:
            com.jcraft.jsch.JSchException r0 = new com.jcraft.jsch.JSchException     // Catch:{ Exception -> 0x00a3 }
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x00a3 }
            r2.<init>()     // Catch:{ Exception -> 0x00a3 }
            java.lang.String r3 = "privatekey: aes256-cbc is not available "
            r2.append(r3)     // Catch:{ Exception -> 0x00a3 }
            r2.append(r1)     // Catch:{ Exception -> 0x00a3 }
            java.lang.String r1 = r2.toString()     // Catch:{ Exception -> 0x00a3 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x00a3 }
            throw r0     // Catch:{ Exception -> 0x00a3 }
        L_0x0283:
            byte r10 = r1[r13]     // Catch:{ Exception -> 0x00a3 }
            if (r10 != r8) goto L_0x0304
            int r10 = r13 + 7
            if (r10 >= r12) goto L_0x0304
            int r11 = r13 + 1
            byte r11 = r1[r11]     // Catch:{ Exception -> 0x00a3 }
            if (r11 != r7) goto L_0x0304
            int r11 = r13 + 2
            byte r11 = r1[r11]     // Catch:{ Exception -> 0x00a3 }
            if (r11 != r3) goto L_0x0304
            int r11 = r13 + 3
            byte r11 = r1[r11]     // Catch:{ Exception -> 0x00a3 }
            r14 = 45
            if (r11 != r14) goto L_0x0304
            int r11 = r13 + 4
            byte r11 = r1[r11]     // Catch:{ Exception -> 0x00a3 }
            r14 = 49
            if (r11 != r14) goto L_0x0304
            int r11 = r13 + 5
            byte r11 = r1[r11]     // Catch:{ Exception -> 0x00a3 }
            r14 = 57
            if (r11 != r14) goto L_0x0304
            int r11 = r13 + 6
            byte r11 = r1[r11]     // Catch:{ Exception -> 0x00a3 }
            r14 = 50
            if (r11 != r14) goto L_0x0304
            byte r10 = r1[r10]     // Catch:{ Exception -> 0x00a3 }
            r11 = 45
            if (r10 != r11) goto L_0x0304
            int r13 = r13 + 8
            java.lang.String r3 = "aes192-cbc"
            java.lang.String r3 = com.jcraft.jsch.JSch.getConfig(r3)     // Catch:{ Exception -> 0x00a3 }
            boolean r3 = com.jcraft.jsch.Session.checkCipher(r3)     // Catch:{ Exception -> 0x00a3 }
            if (r3 == 0) goto L_0x02ed
            java.lang.String r3 = "aes192-cbc"
            java.lang.String r3 = com.jcraft.jsch.JSch.getConfig(r3)     // Catch:{ Exception -> 0x00a3 }
            java.lang.Class r3 = java.lang.Class.forName(r3)     // Catch:{ Exception -> 0x00a3 }
            java.lang.Object r3 = r3.newInstance()     // Catch:{ Exception -> 0x00a3 }
            com.jcraft.jsch.Cipher r3 = (com.jcraft.jsch.Cipher) r3     // Catch:{ Exception -> 0x00a3 }
            r17 = r3
            com.jcraft.jsch.Cipher r17 = (com.jcraft.jsch.Cipher) r17     // Catch:{ Exception -> 0x00a3 }
            int r3 = r17.getIVSize()     // Catch:{ Exception -> 0x00a3 }
            byte[] r6 = new byte[r3]     // Catch:{ Exception -> 0x00a3 }
            r7 = 4
            r8 = 2
            r10 = 0
            r11 = 1
            r14 = 45
            goto L_0x00db
        L_0x02ed:
            com.jcraft.jsch.JSchException r0 = new com.jcraft.jsch.JSchException     // Catch:{ Exception -> 0x00a3 }
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x00a3 }
            r2.<init>()     // Catch:{ Exception -> 0x00a3 }
            java.lang.String r3 = "privatekey: aes192-cbc is not available "
            r2.append(r3)     // Catch:{ Exception -> 0x00a3 }
            r2.append(r1)     // Catch:{ Exception -> 0x00a3 }
            java.lang.String r1 = r2.toString()     // Catch:{ Exception -> 0x00a3 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x00a3 }
            throw r0     // Catch:{ Exception -> 0x00a3 }
        L_0x0304:
            byte r10 = r1[r13]     // Catch:{ Exception -> 0x00a3 }
            if (r10 != r8) goto L_0x0385
            int r8 = r13 + 7
            if (r8 >= r12) goto L_0x0385
            int r10 = r13 + 1
            byte r10 = r1[r10]     // Catch:{ Exception -> 0x00a3 }
            if (r10 != r7) goto L_0x0385
            int r7 = r13 + 2
            byte r7 = r1[r7]     // Catch:{ Exception -> 0x00a3 }
            if (r7 != r3) goto L_0x0385
            int r3 = r13 + 3
            byte r3 = r1[r3]     // Catch:{ Exception -> 0x00a3 }
            r7 = 45
            if (r3 != r7) goto L_0x0385
            int r3 = r13 + 4
            byte r3 = r1[r3]     // Catch:{ Exception -> 0x00a3 }
            r7 = 49
            if (r3 != r7) goto L_0x0385
            int r3 = r13 + 5
            byte r3 = r1[r3]     // Catch:{ Exception -> 0x00a3 }
            r7 = 50
            if (r3 != r7) goto L_0x0385
            int r3 = r13 + 6
            byte r3 = r1[r3]     // Catch:{ Exception -> 0x00a3 }
            r7 = 56
            if (r3 != r7) goto L_0x0385
            byte r3 = r1[r8]     // Catch:{ Exception -> 0x00a3 }
            r7 = 45
            if (r3 != r7) goto L_0x0385
            int r13 = r13 + 8
            java.lang.String r3 = "aes128-cbc"
            java.lang.String r3 = com.jcraft.jsch.JSch.getConfig(r3)     // Catch:{ Exception -> 0x00a3 }
            boolean r3 = com.jcraft.jsch.Session.checkCipher(r3)     // Catch:{ Exception -> 0x00a3 }
            if (r3 == 0) goto L_0x036e
            java.lang.String r3 = "aes128-cbc"
            java.lang.String r3 = com.jcraft.jsch.JSch.getConfig(r3)     // Catch:{ Exception -> 0x00a3 }
            java.lang.Class r3 = java.lang.Class.forName(r3)     // Catch:{ Exception -> 0x00a3 }
            java.lang.Object r3 = r3.newInstance()     // Catch:{ Exception -> 0x00a3 }
            com.jcraft.jsch.Cipher r3 = (com.jcraft.jsch.Cipher) r3     // Catch:{ Exception -> 0x00a3 }
            r17 = r3
            com.jcraft.jsch.Cipher r17 = (com.jcraft.jsch.Cipher) r17     // Catch:{ Exception -> 0x00a3 }
            int r3 = r17.getIVSize()     // Catch:{ Exception -> 0x00a3 }
            byte[] r6 = new byte[r3]     // Catch:{ Exception -> 0x00a3 }
            r7 = 4
            r8 = 2
            r10 = 0
            r11 = 1
            r14 = 45
            goto L_0x00db
        L_0x036e:
            com.jcraft.jsch.JSchException r0 = new com.jcraft.jsch.JSchException     // Catch:{ Exception -> 0x00a3 }
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x00a3 }
            r2.<init>()     // Catch:{ Exception -> 0x00a3 }
            java.lang.String r3 = "privatekey: aes128-cbc is not available "
            r2.append(r3)     // Catch:{ Exception -> 0x00a3 }
            r2.append(r1)     // Catch:{ Exception -> 0x00a3 }
            java.lang.String r1 = r2.toString()     // Catch:{ Exception -> 0x00a3 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x00a3 }
            throw r0     // Catch:{ Exception -> 0x00a3 }
        L_0x0385:
            byte r3 = r1[r13]     // Catch:{ Exception -> 0x00a3 }
            r7 = 67
            if (r3 != r7) goto L_0x03d0
            int r3 = r13 + 3
            if (r3 >= r12) goto L_0x03d0
            int r7 = r13 + 1
            byte r7 = r1[r7]     // Catch:{ Exception -> 0x00a3 }
            r8 = 66
            if (r7 != r8) goto L_0x03d0
            int r7 = r13 + 2
            byte r7 = r1[r7]     // Catch:{ Exception -> 0x00a3 }
            r8 = 67
            if (r7 != r8) goto L_0x03d0
            byte r3 = r1[r3]     // Catch:{ Exception -> 0x00a3 }
            r7 = 44
            if (r3 != r7) goto L_0x03d0
            int r13 = r13 + 4
            r3 = 0
        L_0x03a8:
            int r7 = r6.length     // Catch:{ Exception -> 0x00a3 }
            if (r3 >= r7) goto L_0x03c8
            int r7 = r13 + 1
            byte r8 = r1[r13]     // Catch:{ Exception -> 0x00a3 }
            byte r8 = a2b(r8)     // Catch:{ Exception -> 0x00a3 }
            r10 = 4
            int r8 = r8 << r10
            r8 = r8 & 240(0xf0, float:3.36E-43)
            int r13 = r7 + 1
            byte r7 = r1[r7]     // Catch:{ Exception -> 0x00a3 }
            byte r7 = a2b(r7)     // Catch:{ Exception -> 0x00a3 }
            r7 = r7 & 15
            int r8 = r8 + r7
            byte r7 = (byte) r8     // Catch:{ Exception -> 0x00a3 }
            r6[r3] = r7     // Catch:{ Exception -> 0x00a3 }
            int r3 = r3 + 1
            goto L_0x03a8
        L_0x03c8:
            r7 = 4
            r8 = 2
            r10 = 0
            r11 = 1
            r14 = 45
            goto L_0x00db
        L_0x03d0:
            byte r3 = r1[r13]     // Catch:{ Exception -> 0x00a3 }
            r7 = 13
            if (r3 != r7) goto L_0x03ea
            int r3 = r13 + 1
            int r7 = r1.length     // Catch:{ Exception -> 0x00a3 }
            if (r3 >= r7) goto L_0x03ea
            byte r7 = r1[r3]     // Catch:{ Exception -> 0x00a3 }
            r8 = 10
            if (r7 != r8) goto L_0x03ec
            r13 = r3
            r7 = 4
            r8 = 2
            r10 = 0
            r11 = 1
            r14 = 45
            goto L_0x00db
        L_0x03ea:
            r8 = 10
        L_0x03ec:
            byte r3 = r1[r13]     // Catch:{ Exception -> 0x00a3 }
            if (r3 != r8) goto L_0x0436
            int r3 = r13 + 1
            int r7 = r1.length     // Catch:{ Exception -> 0x00a3 }
            if (r3 >= r7) goto L_0x0436
            byte r7 = r1[r3]     // Catch:{ Exception -> 0x00a3 }
            if (r7 != r8) goto L_0x03fe
            int r13 = r13 + 2
            r10 = r16
            goto L_0x0442
        L_0x03fe:
            byte r7 = r1[r3]     // Catch:{ Exception -> 0x00a3 }
            r8 = 13
            if (r7 != r8) goto L_0x0414
            int r7 = r13 + 2
            int r8 = r1.length     // Catch:{ Exception -> 0x00a3 }
            if (r7 >= r8) goto L_0x0414
            byte r7 = r1[r7]     // Catch:{ Exception -> 0x00a3 }
            r8 = 10
            if (r7 != r8) goto L_0x0414
            int r13 = r13 + 3
            r10 = r16
            goto L_0x0442
        L_0x0414:
            r7 = r3
        L_0x0415:
            int r8 = r1.length     // Catch:{ Exception -> 0x00a3 }
            if (r7 >= r8) goto L_0x042a
            byte r8 = r1[r7]     // Catch:{ Exception -> 0x00a3 }
            r10 = 10
            if (r8 != r10) goto L_0x041f
            goto L_0x042a
        L_0x041f:
            byte r8 = r1[r7]     // Catch:{ Exception -> 0x00a3 }
            r10 = 58
            if (r8 != r10) goto L_0x0427
            r7 = 1
            goto L_0x042b
        L_0x0427:
            int r7 = r7 + 1
            goto L_0x0415
        L_0x042a:
            r7 = 0
        L_0x042b:
            if (r7 != 0) goto L_0x0436
            if (r15 == r9) goto L_0x0432
            r13 = r3
            r10 = 0
            goto L_0x0442
        L_0x0432:
            r13 = r3
            r10 = r16
            goto L_0x0442
        L_0x0436:
            int r13 = r13 + 1
            r7 = 4
            r8 = 2
            r10 = 0
            r11 = 1
            r14 = 45
            goto L_0x00db
        L_0x0440:
            r10 = r16
        L_0x0442:
            if (r1 == 0) goto L_0x04d3
            if (r4 == 0) goto L_0x04bc
            r7 = r13
        L_0x0447:
            if (r7 >= r12) goto L_0x0453
            byte r8 = r1[r7]     // Catch:{ Exception -> 0x00a3 }
            r11 = 45
            if (r8 != r11) goto L_0x0450
            goto L_0x0453
        L_0x0450:
            int r7 = r7 + 1
            goto L_0x0447
        L_0x0453:
            int r12 = r12 - r7
            if (r12 == 0) goto L_0x04a5
            int r7 = r7 - r13
            if (r7 == 0) goto L_0x04a5
            byte[] r7 = new byte[r7]     // Catch:{ Exception -> 0x00a3 }
            int r8 = r7.length     // Catch:{ Exception -> 0x00a3 }
            r11 = 0
            java.lang.System.arraycopy(r1, r13, r7, r11, r8)     // Catch:{ Exception -> 0x00a3 }
            int r8 = r7.length     // Catch:{ Exception -> 0x00a3 }
            r11 = r8
            r8 = 0
        L_0x0463:
            if (r8 >= r11) goto L_0x0496
            byte r12 = r7[r8]     // Catch:{ Exception -> 0x00a3 }
            r13 = 10
            if (r12 != r13) goto L_0x048b
            int r12 = r8 + -1
            byte r12 = r7[r12]     // Catch:{ Exception -> 0x00a3 }
            r13 = 13
            if (r12 != r13) goto L_0x0475
            r12 = 1
            goto L_0x0476
        L_0x0475:
            r12 = 0
        L_0x0476:
            int r13 = r8 + 1
            if (r12 == 0) goto L_0x047c
            r14 = 1
            goto L_0x047d
        L_0x047c:
            r14 = 0
        L_0x047d:
            int r14 = r8 - r14
            int r3 = r11 - r13
            java.lang.System.arraycopy(r7, r13, r7, r14, r3)     // Catch:{ Exception -> 0x00a3 }
            if (r12 == 0) goto L_0x0488
            int r11 = r11 + -1
        L_0x0488:
            int r11 = r11 + -1
            goto L_0x0463
        L_0x048b:
            byte r3 = r7[r8]     // Catch:{ Exception -> 0x00a3 }
            r12 = 45
            if (r3 != r12) goto L_0x0493
            r3 = 0
            goto L_0x0497
        L_0x0493:
            int r8 = r8 + 1
            goto L_0x0463
        L_0x0496:
            r3 = 0
        L_0x0497:
            int r8 = r8 - r3
            if (r8 <= 0) goto L_0x04a0
            byte[] r8 = com.jcraft.jsch.Util.fromBase64(r7, r3, r8)     // Catch:{ Exception -> 0x00a3 }
            r3 = r8
            goto L_0x04a1
        L_0x04a0:
            r3 = 0
        L_0x04a1:
            com.jcraft.jsch.Util.bzero(r7)     // Catch:{ Exception -> 0x00a3 }
            goto L_0x04d4
        L_0x04a5:
            com.jcraft.jsch.JSchException r0 = new com.jcraft.jsch.JSchException     // Catch:{ Exception -> 0x00a3 }
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x00a3 }
            r2.<init>()     // Catch:{ Exception -> 0x00a3 }
            java.lang.String r3 = "invalid privatekey: "
            r2.append(r3)     // Catch:{ Exception -> 0x00a3 }
            r2.append(r1)     // Catch:{ Exception -> 0x00a3 }
            java.lang.String r1 = r2.toString()     // Catch:{ Exception -> 0x00a3 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x00a3 }
            throw r0     // Catch:{ Exception -> 0x00a3 }
        L_0x04bc:
            com.jcraft.jsch.JSchException r0 = new com.jcraft.jsch.JSchException     // Catch:{ Exception -> 0x00a3 }
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x00a3 }
            r2.<init>()     // Catch:{ Exception -> 0x00a3 }
            java.lang.String r3 = "invalid privatekey: "
            r2.append(r3)     // Catch:{ Exception -> 0x00a3 }
            r2.append(r1)     // Catch:{ Exception -> 0x00a3 }
            java.lang.String r1 = r2.toString()     // Catch:{ Exception -> 0x00a3 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x00a3 }
            throw r0     // Catch:{ Exception -> 0x00a3 }
        L_0x04d3:
            r3 = 0
        L_0x04d4:
            if (r3 == 0) goto L_0x0571
            int r7 = r3.length     // Catch:{ Exception -> 0x00a3 }
            r8 = 4
            if (r7 <= r8) goto L_0x0571
            r7 = 0
            byte r8 = r3[r7]     // Catch:{ Exception -> 0x00a3 }
            r7 = 63
            if (r8 != r7) goto L_0x0571
            r7 = 1
            byte r8 = r3[r7]     // Catch:{ Exception -> 0x00a3 }
            r7 = 111(0x6f, float:1.56E-43)
            if (r8 != r7) goto L_0x0571
            r7 = 2
            byte r8 = r3[r7]     // Catch:{ Exception -> 0x00a3 }
            r7 = -7
            if (r8 != r7) goto L_0x0571
            byte r7 = r3[r9]     // Catch:{ Exception -> 0x00a3 }
            r8 = -21
            if (r7 != r8) goto L_0x0571
            com.jcraft.jsch.Buffer r7 = new com.jcraft.jsch.Buffer     // Catch:{ Exception -> 0x00a3 }
            r7.<init>((byte[]) r3)     // Catch:{ Exception -> 0x00a3 }
            r7.getInt()     // Catch:{ Exception -> 0x00a3 }
            r7.getInt()     // Catch:{ Exception -> 0x00a3 }
            r7.getString()     // Catch:{ Exception -> 0x00a3 }
            byte[] r8 = r7.getString()     // Catch:{ Exception -> 0x00a3 }
            java.lang.String r8 = com.jcraft.jsch.Util.byte2str(r8)     // Catch:{ Exception -> 0x00a3 }
            java.lang.String r11 = "3des-cbc"
            boolean r11 = r8.equals(r11)     // Catch:{ Exception -> 0x00a3 }
            if (r11 != 0) goto L_0x052d
            java.lang.String r11 = "none"
            boolean r8 = r8.equals(r11)     // Catch:{ Exception -> 0x00a3 }
            if (r8 == 0) goto L_0x0571
            r7.getInt()     // Catch:{ Exception -> 0x00a3 }
            r7.getInt()     // Catch:{ Exception -> 0x00a3 }
            int r3 = r3.length     // Catch:{ Exception -> 0x00a3 }
            int r8 = r7.getOffSet()     // Catch:{ Exception -> 0x00a3 }
            int r3 = r3 - r8
            byte[] r3 = new byte[r3]     // Catch:{ Exception -> 0x00a3 }
            r7.getByte((byte[]) r3)     // Catch:{ Exception -> 0x00a3 }
            r10 = 0
            goto L_0x0571
        L_0x052d:
            r7.getInt()     // Catch:{ Exception -> 0x00a3 }
            int r0 = r3.length     // Catch:{ Exception -> 0x00a3 }
            int r2 = r7.getOffSet()     // Catch:{ Exception -> 0x00a3 }
            int r0 = r0 - r2
            byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x00a3 }
            r7.getByte((byte[]) r0)     // Catch:{ Exception -> 0x00a3 }
            com.jcraft.jsch.JSchException r0 = new com.jcraft.jsch.JSchException     // Catch:{ Exception -> 0x00a3 }
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x00a3 }
            r2.<init>()     // Catch:{ Exception -> 0x00a3 }
            java.lang.String r3 = "unknown privatekey format: "
            r2.append(r3)     // Catch:{ Exception -> 0x00a3 }
            r2.append(r1)     // Catch:{ Exception -> 0x00a3 }
            java.lang.String r1 = r2.toString()     // Catch:{ Exception -> 0x00a3 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x00a3 }
            throw r0     // Catch:{ Exception -> 0x00a3 }
        L_0x0552:
            boolean r1 = r0 instanceof com.jcraft.jsch.JSchException
            if (r1 != 0) goto L_0x056e
            boolean r1 = r0 instanceof java.lang.Throwable
            if (r1 == 0) goto L_0x0564
            com.jcraft.jsch.JSchException r1 = new com.jcraft.jsch.JSchException
            java.lang.String r2 = r0.toString()
            r1.<init>(r2, r0)
            throw r1
        L_0x0564:
            com.jcraft.jsch.JSchException r1 = new com.jcraft.jsch.JSchException
            java.lang.String r0 = r0.toString()
            r1.<init>(r0)
            throw r1
        L_0x056e:
            com.jcraft.jsch.JSchException r0 = (com.jcraft.jsch.JSchException) r0
            throw r0
        L_0x0571:
            if (r2 == 0) goto L_0x071f
            int r8 = r2.length     // Catch:{ Exception -> 0x071d }
            int r11 = r2.length     // Catch:{ Exception -> 0x071d }
            r12 = 4
            if (r11 <= r12) goto L_0x0614
            r11 = 0
            byte r12 = r2[r11]     // Catch:{ Exception -> 0x071d }
            r11 = 45
            if (r12 != r11) goto L_0x0614
            r12 = 1
            byte r13 = r2[r12]     // Catch:{ Exception -> 0x071d }
            if (r13 != r11) goto L_0x0614
            r13 = 2
            byte r14 = r2[r13]     // Catch:{ Exception -> 0x071d }
            if (r14 != r11) goto L_0x0614
            byte r13 = r2[r9]     // Catch:{ Exception -> 0x071d }
            if (r13 != r11) goto L_0x0614
            r11 = 0
        L_0x058e:
            int r11 = r11 + r12
            int r12 = r2.length     // Catch:{ Exception -> 0x071d }
            if (r12 <= r11) goto L_0x059b
            byte r12 = r2[r11]     // Catch:{ Exception -> 0x071d }
            r13 = 10
            if (r12 != r13) goto L_0x0599
            goto L_0x059b
        L_0x0599:
            r12 = 1
            goto L_0x058e
        L_0x059b:
            int r12 = r2.length     // Catch:{ Exception -> 0x071d }
            if (r12 > r11) goto L_0x05a1
            r12 = r11
            r11 = 0
            goto L_0x05a3
        L_0x05a1:
            r12 = r11
            r11 = 1
        L_0x05a3:
            if (r11 == 0) goto L_0x05cb
            byte r13 = r2[r12]     // Catch:{ Exception -> 0x071d }
            r14 = 10
            if (r13 != r14) goto L_0x05c7
            int r13 = r12 + 1
            r7 = r13
        L_0x05ae:
            int r9 = r2.length     // Catch:{ Exception -> 0x071d }
            if (r7 >= r9) goto L_0x05c3
            byte r9 = r2[r7]     // Catch:{ Exception -> 0x071d }
            if (r9 != r14) goto L_0x05b6
            goto L_0x05c3
        L_0x05b6:
            byte r9 = r2[r7]     // Catch:{ Exception -> 0x071d }
            r14 = 58
            if (r9 != r14) goto L_0x05be
            r7 = 1
            goto L_0x05c4
        L_0x05be:
            int r7 = r7 + 1
            r14 = 10
            goto L_0x05ae
        L_0x05c3:
            r7 = 0
        L_0x05c4:
            if (r7 != 0) goto L_0x05c7
            goto L_0x05cc
        L_0x05c7:
            int r12 = r12 + 1
            r9 = 3
            goto L_0x05a3
        L_0x05cb:
            r13 = r12
        L_0x05cc:
            int r7 = r2.length     // Catch:{ Exception -> 0x071d }
            if (r7 > r13) goto L_0x05d0
            r11 = 0
        L_0x05d0:
            r7 = r13
        L_0x05d1:
            if (r11 == 0) goto L_0x05f1
            if (r7 >= r8) goto L_0x05f1
            byte r9 = r2[r7]     // Catch:{ Exception -> 0x071d }
            r12 = 10
            if (r9 != r12) goto L_0x05e7
            int r9 = r7 + 1
            int r12 = r8 - r7
            r14 = 1
            int r12 = r12 - r14
            java.lang.System.arraycopy(r2, r9, r2, r7, r12)     // Catch:{ Exception -> 0x071d }
            int r8 = r8 + -1
            goto L_0x05d1
        L_0x05e7:
            byte r9 = r2[r7]     // Catch:{ Exception -> 0x071d }
            r12 = 45
            if (r9 != r12) goto L_0x05ee
            goto L_0x05f1
        L_0x05ee:
            int r7 = r7 + 1
            goto L_0x05d1
        L_0x05f1:
            if (r11 == 0) goto L_0x0611
            int r7 = r7 - r13
            byte[] r7 = com.jcraft.jsch.Util.fromBase64(r2, r13, r7)     // Catch:{ Exception -> 0x071d }
            if (r1 == 0) goto L_0x05fd
            r2 = 4
            if (r4 != r2) goto L_0x0720
        L_0x05fd:
            r2 = 8
            byte r8 = r7[r2]     // Catch:{ Exception -> 0x0720 }
            r9 = 100
            if (r8 != r9) goto L_0x0608
            r4 = 1
            goto L_0x0720
        L_0x0608:
            byte r2 = r7[r2]     // Catch:{ Exception -> 0x0720 }
            r8 = 114(0x72, float:1.6E-43)
            if (r2 != r8) goto L_0x0720
            r4 = 2
            goto L_0x0720
        L_0x0611:
            r7 = 0
            goto L_0x0720
        L_0x0614:
            r7 = 0
            byte r9 = r2[r7]     // Catch:{ Exception -> 0x071d }
            r7 = 115(0x73, float:1.61E-43)
            if (r9 != r7) goto L_0x06a2
            r7 = 1
            byte r9 = r2[r7]     // Catch:{ Exception -> 0x071d }
            r7 = 115(0x73, float:1.61E-43)
            if (r9 != r7) goto L_0x06a2
            r7 = 2
            byte r9 = r2[r7]     // Catch:{ Exception -> 0x071d }
            r7 = 104(0x68, float:1.46E-43)
            if (r9 != r7) goto L_0x06a2
            r7 = 3
            byte r9 = r2[r7]     // Catch:{ Exception -> 0x071d }
            r7 = 45
            if (r9 != r7) goto L_0x06a2
            if (r1 != 0) goto L_0x0646
            int r7 = r2.length     // Catch:{ Exception -> 0x071d }
            r9 = 7
            if (r7 <= r9) goto L_0x0646
            r7 = 4
            byte r9 = r2[r7]     // Catch:{ Exception -> 0x071d }
            r11 = 100
            if (r9 != r11) goto L_0x063f
            r4 = 1
            goto L_0x0646
        L_0x063f:
            byte r7 = r2[r7]     // Catch:{ Exception -> 0x071d }
            r9 = 114(0x72, float:1.6E-43)
            if (r7 != r9) goto L_0x0646
            r4 = 2
        L_0x0646:
            r7 = 0
        L_0x0647:
            if (r7 >= r8) goto L_0x0653
            byte r9 = r2[r7]     // Catch:{ Exception -> 0x071d }
            r11 = 32
            if (r9 != r11) goto L_0x0650
            goto L_0x0653
        L_0x0650:
            int r7 = r7 + 1
            goto L_0x0647
        L_0x0653:
            r9 = 1
            int r7 = r7 + r9
            if (r7 >= r8) goto L_0x066e
            r9 = r7
        L_0x0658:
            if (r9 >= r8) goto L_0x0664
            byte r11 = r2[r9]     // Catch:{ Exception -> 0x071d }
            r12 = 32
            if (r11 != r12) goto L_0x0661
            goto L_0x0664
        L_0x0661:
            int r9 = r9 + 1
            goto L_0x0658
        L_0x0664:
            int r11 = r9 - r7
            byte[] r7 = com.jcraft.jsch.Util.fromBase64(r2, r7, r11)     // Catch:{ Exception -> 0x071d }
            r16 = r7
            r7 = r9
            goto L_0x0670
        L_0x066e:
            r16 = 0
        L_0x0670:
            int r9 = r7 + 1
            if (r7 >= r8) goto L_0x069e
            r7 = r9
        L_0x0675:
            if (r7 >= r8) goto L_0x0685
            byte r11 = r2[r7]     // Catch:{ Exception -> 0x0681 }
            r12 = 10
            if (r11 != r12) goto L_0x067e
            goto L_0x0685
        L_0x067e:
            int r7 = r7 + 1
            goto L_0x0675
        L_0x0681:
            r7 = r16
            goto L_0x0720
        L_0x0685:
            if (r7 <= 0) goto L_0x0691
            int r8 = r7 + -1
            byte r8 = r2[r8]     // Catch:{ Exception -> 0x0681 }
            r11 = 13
            if (r8 != r11) goto L_0x0691
            int r7 = r7 + -1
        L_0x0691:
            if (r9 >= r7) goto L_0x069a
            java.lang.String r8 = new java.lang.String     // Catch:{ Exception -> 0x0681 }
            int r7 = r7 - r9
            r8.<init>(r2, r9, r7)     // Catch:{ Exception -> 0x0681 }
            r5 = r8
        L_0x069a:
            r7 = r16
            goto L_0x0720
        L_0x069e:
            r7 = r16
            goto L_0x0720
        L_0x06a2:
            r7 = 0
            byte r9 = r2[r7]     // Catch:{ Exception -> 0x071d }
            r7 = 101(0x65, float:1.42E-43)
            if (r9 != r7) goto L_0x071b
            r7 = 1
            byte r9 = r2[r7]     // Catch:{ Exception -> 0x071d }
            r7 = 99
            if (r9 != r7) goto L_0x071b
            r7 = 2
            byte r9 = r2[r7]     // Catch:{ Exception -> 0x071d }
            r7 = 100
            if (r9 != r7) goto L_0x071b
            r7 = 3
            byte r9 = r2[r7]     // Catch:{ Exception -> 0x071d }
            r7 = 115(0x73, float:1.61E-43)
            if (r9 != r7) goto L_0x071b
            if (r1 != 0) goto L_0x06c5
            int r7 = r2.length     // Catch:{ Exception -> 0x071d }
            r9 = 7
            if (r7 <= r9) goto L_0x06c5
            r4 = 3
        L_0x06c5:
            r7 = 0
        L_0x06c6:
            if (r7 >= r8) goto L_0x06d2
            byte r9 = r2[r7]     // Catch:{ Exception -> 0x071d }
            r11 = 32
            if (r9 != r11) goto L_0x06cf
            goto L_0x06d2
        L_0x06cf:
            int r7 = r7 + 1
            goto L_0x06c6
        L_0x06d2:
            r9 = 1
            int r7 = r7 + r9
            if (r7 >= r8) goto L_0x06ed
            r9 = r7
        L_0x06d7:
            if (r9 >= r8) goto L_0x06e3
            byte r11 = r2[r9]     // Catch:{ Exception -> 0x071d }
            r12 = 32
            if (r11 != r12) goto L_0x06e0
            goto L_0x06e3
        L_0x06e0:
            int r9 = r9 + 1
            goto L_0x06d7
        L_0x06e3:
            int r11 = r9 - r7
            byte[] r7 = com.jcraft.jsch.Util.fromBase64(r2, r7, r11)     // Catch:{ Exception -> 0x071d }
            r16 = r7
            r7 = r9
            goto L_0x06ef
        L_0x06ed:
            r16 = 0
        L_0x06ef:
            int r9 = r7 + 1
            if (r7 >= r8) goto L_0x0718
            r7 = r9
        L_0x06f4:
            if (r7 >= r8) goto L_0x0700
            byte r11 = r2[r7]     // Catch:{ Exception -> 0x0681 }
            r12 = 10
            if (r11 != r12) goto L_0x06fd
            goto L_0x0700
        L_0x06fd:
            int r7 = r7 + 1
            goto L_0x06f4
        L_0x0700:
            if (r7 <= 0) goto L_0x070c
            int r8 = r7 + -1
            byte r8 = r2[r8]     // Catch:{ Exception -> 0x0681 }
            r11 = 13
            if (r8 != r11) goto L_0x070c
            int r7 = r7 + -1
        L_0x070c:
            if (r9 >= r7) goto L_0x0718
            java.lang.String r8 = new java.lang.String     // Catch:{ Exception -> 0x0681 }
            int r7 = r7 - r9
            r8.<init>(r2, r9, r7)     // Catch:{ Exception -> 0x0681 }
            r5 = r8
            r7 = r16
            goto L_0x0720
        L_0x0718:
            r7 = r16
            goto L_0x0720
        L_0x071b:
            r7 = 0
            goto L_0x0720
        L_0x071d:
            r7 = 0
            goto L_0x0720
        L_0x071f:
            r7 = 0
        L_0x0720:
            r2 = 0
            r8 = 1
            if (r4 != r8) goto L_0x072a
            com.jcraft.jsch.KeyPairDSA r2 = new com.jcraft.jsch.KeyPairDSA
            r2.<init>(r0)
            goto L_0x0743
        L_0x072a:
            r8 = 2
            if (r4 != r8) goto L_0x0733
            com.jcraft.jsch.KeyPairRSA r2 = new com.jcraft.jsch.KeyPairRSA
            r2.<init>(r0)
            goto L_0x0743
        L_0x0733:
            r8 = 3
            if (r4 != r8) goto L_0x073c
            com.jcraft.jsch.KeyPairECDSA r2 = new com.jcraft.jsch.KeyPairECDSA
            r2.<init>(r0)
            goto L_0x0743
        L_0x073c:
            if (r15 != r8) goto L_0x0743
            com.jcraft.jsch.KeyPairPKCS8 r2 = new com.jcraft.jsch.KeyPairPKCS8
            r2.<init>(r0)
        L_0x0743:
            if (r2 == 0) goto L_0x077c
            r2.encrypted = r10
            r2.publickeyblob = r7
            r2.vendor = r15
            r2.publicKeyComment = r5
            r0 = r17
            r2.cipher = r0
            if (r10 == 0) goto L_0x075b
            r0 = 1
            r2.encrypted = r0
            r2.iv = r6
            r2.data = r3
            goto L_0x077c
        L_0x075b:
            boolean r0 = r2.parse(r3)
            if (r0 == 0) goto L_0x0765
            r0 = 0
            r2.encrypted = r0
            return r2
        L_0x0765:
            com.jcraft.jsch.JSchException r0 = new com.jcraft.jsch.JSchException
            java.lang.StringBuffer r2 = new java.lang.StringBuffer
            r2.<init>()
            java.lang.String r3 = "invalid privatekey: "
            r2.append(r3)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            r0.<init>(r1)
            throw r0
        L_0x077c:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.KeyPair.load(com.jcraft.jsch.JSch, byte[], byte[]):com.jcraft.jsch.KeyPair");
    }

    public void dispose() {
        Util.bzero(this.passphrase);
    }

    public void finalize() {
        dispose();
    }

    /* JADX WARNING: type inference failed for: r1v9, types: [com.jcraft.jsch.KeyPairRSA] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.jcraft.jsch.KeyPair loadPPK(com.jcraft.jsch.JSch r10, byte[] r11) throws com.jcraft.jsch.JSchException {
        /*
            com.jcraft.jsch.Buffer r0 = new com.jcraft.jsch.Buffer
            r0.<init>((byte[]) r11)
            java.util.Hashtable r11 = new java.util.Hashtable
            r11.<init>()
        L_0x000a:
            boolean r1 = parseHeader(r0, r11)
            if (r1 != 0) goto L_0x000a
            java.lang.String r1 = "PuTTY-User-Key-File-2"
            java.lang.Object r1 = r11.get(r1)
            java.lang.String r1 = (java.lang.String) r1
            r2 = 0
            if (r1 != 0) goto L_0x001c
            return r2
        L_0x001c:
            java.lang.String r3 = "Public-Lines"
            java.lang.Object r3 = r11.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            int r3 = java.lang.Integer.parseInt(r3)
            byte[] r3 = parseLines(r0, r3)
        L_0x002c:
            boolean r4 = parseHeader(r0, r11)
            if (r4 != 0) goto L_0x002c
            java.lang.String r4 = "Private-Lines"
            java.lang.Object r4 = r11.get(r4)
            java.lang.String r4 = (java.lang.String) r4
            int r4 = java.lang.Integer.parseInt(r4)
            byte[] r4 = parseLines(r0, r4)
        L_0x0042:
            boolean r5 = parseHeader(r0, r11)
            if (r5 != 0) goto L_0x0042
            int r0 = r4.length
            r5 = 0
            byte[] r0 = com.jcraft.jsch.Util.fromBase64(r4, r5, r0)
            int r4 = r3.length
            byte[] r3 = com.jcraft.jsch.Util.fromBase64(r3, r5, r4)
            java.lang.String r4 = "ssh-rsa"
            boolean r4 = r1.equals(r4)
            if (r4 == 0) goto L_0x0085
            com.jcraft.jsch.Buffer r1 = new com.jcraft.jsch.Buffer
            r1.<init>((byte[]) r3)
            int r3 = r3.length
            r1.skip(r3)
            int r3 = r1.getInt()
            byte[] r3 = new byte[r3]
            r1.getByte((byte[]) r3)
            int r3 = r1.getInt()
            byte[] r3 = new byte[r3]
            r1.getByte((byte[]) r3)
            int r4 = r1.getInt()
            byte[] r4 = new byte[r4]
            r1.getByte((byte[]) r4)
            com.jcraft.jsch.KeyPairRSA r1 = new com.jcraft.jsch.KeyPairRSA
            r1.<init>(r10, r4, r3, r2)
            goto L_0x00cb
        L_0x0085:
            java.lang.String r4 = "ssh-dss"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x012f
            com.jcraft.jsch.Buffer r1 = new com.jcraft.jsch.Buffer
            r1.<init>((byte[]) r3)
            int r2 = r3.length
            r1.skip(r2)
            int r2 = r1.getInt()
            byte[] r2 = new byte[r2]
            r1.getByte((byte[]) r2)
            int r2 = r1.getInt()
            byte[] r5 = new byte[r2]
            r1.getByte((byte[]) r5)
            int r2 = r1.getInt()
            byte[] r6 = new byte[r2]
            r1.getByte((byte[]) r6)
            int r2 = r1.getInt()
            byte[] r7 = new byte[r2]
            r1.getByte((byte[]) r7)
            int r2 = r1.getInt()
            byte[] r8 = new byte[r2]
            r1.getByte((byte[]) r8)
            com.jcraft.jsch.KeyPairDSA r1 = new com.jcraft.jsch.KeyPairDSA
            r9 = 0
            r3 = r1
            r4 = r10
            r3.<init>(r4, r5, r6, r7, r8, r9)
        L_0x00cb:
            java.lang.String r10 = "Encryption"
            java.lang.Object r10 = r11.get(r10)
            java.lang.String r2 = "none"
            boolean r10 = r10.equals(r2)
            r10 = r10 ^ 1
            r1.encrypted = r10
            r10 = 2
            r1.vendor = r10
            java.lang.String r10 = "Comment"
            java.lang.Object r10 = r11.get(r10)
            java.lang.String r10 = (java.lang.String) r10
            r1.publicKeyComment = r10
            boolean r10 = r1.encrypted
            if (r10 == 0) goto L_0x0129
            java.lang.String r10 = "aes256-cbc"
            java.lang.String r10 = com.jcraft.jsch.JSch.getConfig(r10)
            boolean r10 = com.jcraft.jsch.Session.checkCipher(r10)
            if (r10 == 0) goto L_0x0121
            java.lang.String r10 = "aes256-cbc"
            java.lang.String r10 = com.jcraft.jsch.JSch.getConfig(r10)     // Catch:{ Exception -> 0x0119 }
            java.lang.Class r10 = java.lang.Class.forName(r10)     // Catch:{ Exception -> 0x0119 }
            java.lang.Object r10 = r10.newInstance()     // Catch:{ Exception -> 0x0119 }
            com.jcraft.jsch.Cipher r10 = (com.jcraft.jsch.Cipher) r10     // Catch:{ Exception -> 0x0119 }
            com.jcraft.jsch.Cipher r10 = (com.jcraft.jsch.Cipher) r10     // Catch:{ Exception -> 0x0119 }
            r1.cipher = r10     // Catch:{ Exception -> 0x0119 }
            com.jcraft.jsch.Cipher r10 = r1.cipher     // Catch:{ Exception -> 0x0119 }
            int r10 = r10.getIVSize()     // Catch:{ Exception -> 0x0119 }
            byte[] r10 = new byte[r10]     // Catch:{ Exception -> 0x0119 }
            r1.iv = r10     // Catch:{ Exception -> 0x0119 }
            r1.data = r0
            goto L_0x012e
        L_0x0119:
            com.jcraft.jsch.JSchException r10 = new com.jcraft.jsch.JSchException
            java.lang.String r11 = "The cipher 'aes256-cbc' is required, but it is not available."
            r10.<init>(r11)
            throw r10
        L_0x0121:
            com.jcraft.jsch.JSchException r10 = new com.jcraft.jsch.JSchException
            java.lang.String r11 = "The cipher 'aes256-cbc' is required, but it is not available."
            r10.<init>(r11)
            throw r10
        L_0x0129:
            r1.data = r0
            r1.parse(r0)
        L_0x012e:
            return r1
        L_0x012f:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.KeyPair.loadPPK(com.jcraft.jsch.JSch, byte[]):com.jcraft.jsch.KeyPair");
    }

    private static byte[] parseLines(Buffer buffer, int i) {
        int i2;
        byte[] bArr = buffer.buffer;
        int i3 = buffer.index;
        byte[] bArr2 = null;
        while (true) {
            int i4 = i - 1;
            if (i <= 0) {
                break;
            }
            int i5 = i3;
            while (true) {
                if (bArr.length <= i5) {
                    i2 = i5;
                    break;
                }
                i2 = i5 + 1;
                if (bArr[i5] != 13) {
                    i5 = i2;
                } else if (bArr2 == null) {
                    int i6 = (i2 - i3) - 1;
                    byte[] bArr3 = new byte[i6];
                    System.arraycopy(bArr, i3, bArr3, 0, i6);
                    bArr2 = bArr3;
                } else {
                    byte[] bArr4 = new byte[(((bArr2.length + i2) - i3) - 1)];
                    System.arraycopy(bArr2, 0, bArr4, 0, bArr2.length);
                    System.arraycopy(bArr, i3, bArr4, bArr2.length, (i2 - i3) - 1);
                    for (int i7 = 0; i7 < bArr2.length; i7++) {
                        bArr2[i7] = 0;
                    }
                    bArr2 = bArr4;
                }
            }
            if (bArr[i2] == 10) {
                i2++;
            }
            i3 = i2;
            i = i4;
        }
        if (bArr2 != null) {
            buffer.index = i3;
        }
        return bArr2;
    }

    private static boolean parseHeader(Buffer buffer, Hashtable hashtable) {
        String str;
        String str2;
        byte[] bArr = buffer.buffer;
        int i = buffer.index;
        int i2 = i;
        while (true) {
            str = null;
            if (i2 >= bArr.length || bArr[i2] == 13) {
                str2 = null;
            } else if (bArr[i2] == 58) {
                str2 = new String(bArr, i, i2 - i);
                int i3 = i2 + 1;
                i = (i3 >= bArr.length || bArr[i3] != 32) ? i3 : i3 + 1;
            } else {
                i2++;
            }
        }
        str2 = null;
        if (str2 == null) {
            return false;
        }
        int i4 = i;
        while (true) {
            if (i4 >= bArr.length) {
                break;
            } else if (bArr[i4] == 13) {
                str = new String(bArr, i, i4 - i);
                int i5 = i4 + 1;
                i = (i5 >= bArr.length || bArr[i5] != 10) ? i5 : i5 + 1;
            } else {
                i4++;
            }
        }
        if (str != null) {
            hashtable.put(str2, str);
            buffer.index = i;
        }
        if (str2 == null || str == null) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public void copy(KeyPair keyPair) {
        this.publickeyblob = keyPair.publickeyblob;
        this.vendor = keyPair.vendor;
        this.publicKeyComment = keyPair.publicKeyComment;
        this.cipher = keyPair.cipher;
    }

    class ASN1Exception extends Exception {
        ASN1Exception() {
        }
    }

    class ASN1 {
        byte[] buf;
        int length;
        int start;

        ASN1(KeyPair keyPair, byte[] bArr) throws ASN1Exception {
            this(bArr, 0, bArr.length);
        }

        ASN1(byte[] bArr, int i, int i2) throws ASN1Exception {
            this.buf = bArr;
            this.start = i;
            this.length = i2;
            if (i + i2 > bArr.length) {
                throw new ASN1Exception();
            }
        }

        /* access modifiers changed from: package-private */
        public int getType() {
            return this.buf[this.start] & 255;
        }

        /* access modifiers changed from: package-private */
        public boolean isSEQUENCE() {
            return getType() == 48;
        }

        /* access modifiers changed from: package-private */
        public boolean isINTEGER() {
            return getType() == 2;
        }

        /* access modifiers changed from: package-private */
        public boolean isOBJECT() {
            return getType() == 6;
        }

        /* access modifiers changed from: package-private */
        public boolean isOCTETSTRING() {
            return getType() == 4;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: byte} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: byte} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: byte} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private int getLength(int[] r7) {
            /*
                r6 = this;
                r0 = 0
                r1 = r7[r0]
                byte[] r2 = r6.buf
                int r3 = r1 + 1
                byte r1 = r2[r1]
                r1 = r1 & 255(0xff, float:3.57E-43)
                r2 = r1 & 128(0x80, float:1.794E-43)
                if (r2 == 0) goto L_0x0025
                r1 = r1 & 127(0x7f, float:1.78E-43)
                r2 = 0
            L_0x0012:
                int r4 = r1 + -1
                if (r1 <= 0) goto L_0x0024
                int r1 = r2 << 8
                byte[] r2 = r6.buf
                int r5 = r3 + 1
                byte r2 = r2[r3]
                r2 = r2 & 255(0xff, float:3.57E-43)
                int r2 = r2 + r1
                r1 = r4
                r3 = r5
                goto L_0x0012
            L_0x0024:
                r1 = r2
            L_0x0025:
                r7[r0] = r3
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.KeyPair.ASN1.getLength(int[]):int");
        }

        /* access modifiers changed from: package-private */
        public byte[] getContent() {
            int[] iArr = {this.start + 1};
            int length2 = getLength(iArr);
            byte[] bArr = new byte[length2];
            System.arraycopy(this.buf, iArr[0], bArr, 0, bArr.length);
            return bArr;
        }

        /* access modifiers changed from: package-private */
        public ASN1[] getContents() throws ASN1Exception {
            byte[] bArr = this.buf;
            int i = this.start;
            byte b = bArr[i];
            int[] iArr = {i + 1};
            int length2 = getLength(iArr);
            if (b == 5) {
                return new ASN1[0];
            }
            int i2 = iArr[0];
            Vector vector = new Vector();
            while (length2 > 0) {
                int i3 = i2 + 1;
                iArr[0] = i3;
                int length3 = getLength(iArr);
                int i4 = iArr[0];
                int i5 = i4 - i3;
                vector.addElement(new ASN1(this.buf, i3 - 1, i5 + 1 + length3));
                i2 = i4 + length3;
                length2 = ((length2 - 1) - i5) - length3;
            }
            ASN1[] asn1Arr = new ASN1[vector.size()];
            for (int i6 = 0; i6 < vector.size(); i6++) {
                asn1Arr[i6] = (ASN1) vector.elementAt(i6);
            }
            return asn1Arr;
        }
    }
}
