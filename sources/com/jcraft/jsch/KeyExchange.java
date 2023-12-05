package com.jcraft.jsch;

import java.io.PrintStream;

public abstract class KeyExchange {
    static final int PROPOSAL_COMP_ALGS_CTOS = 6;
    static final int PROPOSAL_COMP_ALGS_STOC = 7;
    static final int PROPOSAL_ENC_ALGS_CTOS = 2;
    static final int PROPOSAL_ENC_ALGS_STOC = 3;
    static final int PROPOSAL_KEX_ALGS = 0;
    static final int PROPOSAL_LANG_CTOS = 8;
    static final int PROPOSAL_LANG_STOC = 9;
    static final int PROPOSAL_MAC_ALGS_CTOS = 4;
    static final int PROPOSAL_MAC_ALGS_STOC = 5;
    static final int PROPOSAL_MAX = 10;
    static final int PROPOSAL_SERVER_HOST_KEY_ALGS = 1;
    public static final int STATE_END = 0;
    static String enc_c2s = "blowfish-cbc";
    static String enc_s2c = "blowfish-cbc";
    static String kex = "diffie-hellman-group1-sha1";
    static String lang_c2s = "";
    static String lang_s2c = "";
    static String mac_c2s = "hmac-md5";
    static String mac_s2c = "hmac-md5";
    static String server_host_key = "ssh-rsa,ssh-dss";
    protected final int DSS = 1;
    protected final int ECDSA = 2;
    protected byte[] H = null;
    protected byte[] K = null;
    protected byte[] K_S = null;
    protected final int RSA = 0;
    private String key_alg_name = "";
    protected Session session = null;
    protected HASH sha = null;
    private int type = 0;

    public abstract int getState();

    public abstract void init(Session session2, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) throws Exception;

    public abstract boolean next(Buffer buffer) throws Exception;

    public String getKeyType() {
        int i = this.type;
        if (i == 1) {
            return "DSA";
        }
        return i == 0 ? "RSA" : "ECDSA";
    }

    public String getKeyAlgorithName() {
        return this.key_alg_name;
    }

    protected static String[] guess(byte[] bArr, byte[] bArr2) {
        String[] strArr = new String[10];
        Buffer buffer = new Buffer(bArr);
        buffer.setOffSet(17);
        Buffer buffer2 = new Buffer(bArr2);
        buffer2.setOffSet(17);
        if (JSch.getLogger().isEnabled(1)) {
            for (int i = 0; i < 10; i++) {
                Logger logger = JSch.getLogger();
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("kex: server: ");
                stringBuffer.append(Util.byte2str(buffer.getString()));
                logger.log(1, stringBuffer.toString());
            }
            for (int i2 = 0; i2 < 10; i2++) {
                Logger logger2 = JSch.getLogger();
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("kex: client: ");
                stringBuffer2.append(Util.byte2str(buffer2.getString()));
                logger2.log(1, stringBuffer2.toString());
            }
            buffer.setOffSet(17);
            buffer2.setOffSet(17);
        }
        for (int i3 = 0; i3 < 10; i3++) {
            byte[] string = buffer.getString();
            byte[] string2 = buffer2.getString();
            int i4 = 0;
            int i5 = 0;
            while (true) {
                if (i4 >= string2.length) {
                    break;
                }
                while (i4 < string2.length && string2[i4] != 44) {
                    i4++;
                }
                if (i5 == i4) {
                    return null;
                }
                String byte2str = Util.byte2str(string2, i5, i4 - i5);
                int i6 = 0;
                int i7 = 0;
                while (i6 < string.length) {
                    while (i6 < string.length && string[i6] != 44) {
                        i6++;
                    }
                    if (i7 == i6) {
                        return null;
                    }
                    if (byte2str.equals(Util.byte2str(string, i7, i6 - i7))) {
                        strArr[i3] = byte2str;
                        break;
                    }
                    i7 = i6 + 1;
                    i6 = i7;
                }
                i5 = i4 + 1;
                i4 = i5;
            }
            if (i4 == 0) {
                strArr[i3] = "";
            } else if (strArr[i3] == null) {
                return null;
            }
        }
        if (JSch.getLogger().isEnabled(1)) {
            Logger logger3 = JSch.getLogger();
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("kex: server->client ");
            stringBuffer3.append(strArr[3]);
            stringBuffer3.append(" ");
            stringBuffer3.append(strArr[5]);
            stringBuffer3.append(" ");
            stringBuffer3.append(strArr[7]);
            logger3.log(1, stringBuffer3.toString());
            Logger logger4 = JSch.getLogger();
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("kex: client->server ");
            stringBuffer4.append(strArr[2]);
            stringBuffer4.append(" ");
            stringBuffer4.append(strArr[4]);
            stringBuffer4.append(" ");
            stringBuffer4.append(strArr[6]);
            logger4.log(1, stringBuffer4.toString());
        }
        return strArr;
    }

    public String getFingerPrint() {
        HASH hash;
        try {
            hash = (HASH) Class.forName(this.session.getConfig("md5")).newInstance();
        } catch (Exception e) {
            PrintStream printStream = System.err;
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("getFingerPrint: ");
            stringBuffer.append(e);
            printStream.println(stringBuffer.toString());
            hash = null;
        }
        return Util.getFingerPrint(hash, getHostKey());
    }

    /* access modifiers changed from: package-private */
    public byte[] getK() {
        return this.K;
    }

    /* access modifiers changed from: package-private */
    public byte[] getH() {
        return this.H;
    }

    /* access modifiers changed from: package-private */
    public HASH getHash() {
        return this.sha;
    }

    /* access modifiers changed from: package-private */
    public byte[] getHostKey() {
        return this.K_S;
    }

    /* access modifiers changed from: protected */
    public byte[] normalize(byte[] bArr) {
        if (bArr.length <= 1 || bArr[0] != 0 || (bArr[1] & 128) != 0) {
            return bArr;
        }
        byte[] bArr2 = new byte[(bArr.length - 1)];
        System.arraycopy(bArr, 1, bArr2, 0, bArr2.length);
        return normalize(bArr2);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0094  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0180  */
    /* JADX WARNING: Removed duplicated region for block: B:46:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:48:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean verify(java.lang.String r10, byte[] r11, int r12, byte[] r13) throws java.lang.Exception {
        /*
            r9 = this;
            java.lang.String r0 = "ssh-rsa"
            boolean r0 = r10.equals(r0)
            r1 = 0
            r2 = 65280(0xff00, float:9.1477E-41)
            r3 = 16711680(0xff0000, float:2.3418052E-38)
            r4 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r5 = 0
            r6 = 1
            if (r0 == 0) goto L_0x00ae
            r9.type = r5
            r9.key_alg_name = r10
            int r10 = r12 + 1
            byte r12 = r11[r12]
            int r12 = r12 << 24
            r12 = r12 & r4
            int r0 = r10 + 1
            byte r10 = r11[r10]
            int r10 = r10 << 16
            r10 = r10 & r3
            r10 = r10 | r12
            int r12 = r0 + 1
            byte r0 = r11[r0]
            int r0 = r0 << 8
            r0 = r0 & r2
            r10 = r10 | r0
            int r0 = r12 + 1
            byte r12 = r11[r12]
            r12 = r12 & 255(0xff, float:3.57E-43)
            r10 = r10 | r12
            byte[] r12 = new byte[r10]
            java.lang.System.arraycopy(r11, r0, r12, r5, r10)
            int r0 = r0 + r10
            int r10 = r0 + 1
            byte r0 = r11[r0]
            int r0 = r0 << 24
            r0 = r0 & r4
            int r4 = r10 + 1
            byte r10 = r11[r10]
            int r10 = r10 << 16
            r10 = r10 & r3
            r10 = r10 | r0
            int r0 = r4 + 1
            byte r3 = r11[r4]
            int r3 = r3 << 8
            r2 = r2 & r3
            r10 = r10 | r2
            int r2 = r0 + 1
            byte r0 = r11[r0]
            r0 = r0 & 255(0xff, float:3.57E-43)
            r10 = r10 | r0
            byte[] r0 = new byte[r10]
            java.lang.System.arraycopy(r11, r2, r0, r5, r10)
            com.jcraft.jsch.Session r10 = r9.session     // Catch:{ Exception -> 0x0077 }
            java.lang.String r11 = "signature.rsa"
            java.lang.String r10 = r10.getConfig(r11)     // Catch:{ Exception -> 0x0077 }
            java.lang.Class r10 = java.lang.Class.forName(r10)     // Catch:{ Exception -> 0x0077 }
            java.lang.Object r10 = r10.newInstance()     // Catch:{ Exception -> 0x0077 }
            com.jcraft.jsch.SignatureRSA r10 = (com.jcraft.jsch.SignatureRSA) r10     // Catch:{ Exception -> 0x0077 }
            com.jcraft.jsch.SignatureRSA r10 = (com.jcraft.jsch.SignatureRSA) r10     // Catch:{ Exception -> 0x0077 }
            r10.init()     // Catch:{ Exception -> 0x0075 }
            goto L_0x007e
        L_0x0075:
            r11 = move-exception
            goto L_0x0079
        L_0x0077:
            r11 = move-exception
            r10 = r1
        L_0x0079:
            java.io.PrintStream r1 = java.lang.System.err
            r1.println(r11)
        L_0x007e:
            r10.setPubKey(r12, r0)
            byte[] r11 = r9.H
            r10.update(r11)
            boolean r5 = r10.verify(r13)
            com.jcraft.jsch.Logger r10 = com.jcraft.jsch.JSch.getLogger()
            boolean r10 = r10.isEnabled(r6)
            if (r10 == 0) goto L_0x0240
            com.jcraft.jsch.Logger r10 = com.jcraft.jsch.JSch.getLogger()
            java.lang.StringBuffer r11 = new java.lang.StringBuffer
            r11.<init>()
            java.lang.String r12 = "ssh_rsa_verify: signature "
            r11.append(r12)
            r11.append(r5)
            java.lang.String r11 = r11.toString()
            r10.log(r6, r11)
            goto L_0x0240
        L_0x00ae:
            java.lang.String r0 = "ssh-dss"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x019a
            r9.type = r6
            r9.key_alg_name = r10
            int r10 = r12 + 1
            byte r12 = r11[r12]
            int r12 = r12 << 24
            r12 = r12 & r4
            int r0 = r10 + 1
            byte r10 = r11[r10]
            int r10 = r10 << 16
            r10 = r10 & r3
            r10 = r10 | r12
            int r12 = r0 + 1
            byte r0 = r11[r0]
            int r0 = r0 << 8
            r0 = r0 & r2
            r10 = r10 | r0
            int r0 = r12 + 1
            byte r12 = r11[r12]
            r12 = r12 & 255(0xff, float:3.57E-43)
            r10 = r10 | r12
            byte[] r12 = new byte[r10]
            java.lang.System.arraycopy(r11, r0, r12, r5, r10)
            int r0 = r0 + r10
            int r10 = r0 + 1
            byte r0 = r11[r0]
            int r0 = r0 << 24
            r0 = r0 & r4
            int r7 = r10 + 1
            byte r10 = r11[r10]
            int r10 = r10 << 16
            r10 = r10 & r3
            r10 = r10 | r0
            int r0 = r7 + 1
            byte r7 = r11[r7]
            int r7 = r7 << 8
            r7 = r7 & r2
            r10 = r10 | r7
            int r7 = r0 + 1
            byte r0 = r11[r0]
            r0 = r0 & 255(0xff, float:3.57E-43)
            r10 = r10 | r0
            byte[] r0 = new byte[r10]
            java.lang.System.arraycopy(r11, r7, r0, r5, r10)
            int r7 = r7 + r10
            int r10 = r7 + 1
            byte r7 = r11[r7]
            int r7 = r7 << 24
            r7 = r7 & r4
            int r8 = r10 + 1
            byte r10 = r11[r10]
            int r10 = r10 << 16
            r10 = r10 & r3
            r10 = r10 | r7
            int r7 = r8 + 1
            byte r8 = r11[r8]
            int r8 = r8 << 8
            r8 = r8 & r2
            r10 = r10 | r8
            int r8 = r7 + 1
            byte r7 = r11[r7]
            r7 = r7 & 255(0xff, float:3.57E-43)
            r10 = r10 | r7
            byte[] r7 = new byte[r10]
            java.lang.System.arraycopy(r11, r8, r7, r5, r10)
            int r8 = r8 + r10
            int r10 = r8 + 1
            byte r8 = r11[r8]
            int r8 = r8 << 24
            r4 = r4 & r8
            int r8 = r10 + 1
            byte r10 = r11[r10]
            int r10 = r10 << 16
            r10 = r10 & r3
            r10 = r10 | r4
            int r3 = r8 + 1
            byte r4 = r11[r8]
            int r4 = r4 << 8
            r2 = r2 & r4
            r10 = r10 | r2
            int r2 = r3 + 1
            byte r3 = r11[r3]
            r3 = r3 & 255(0xff, float:3.57E-43)
            r10 = r10 | r3
            byte[] r3 = new byte[r10]
            java.lang.System.arraycopy(r11, r2, r3, r5, r10)
            com.jcraft.jsch.Session r10 = r9.session     // Catch:{ Exception -> 0x0163 }
            java.lang.String r11 = "signature.dss"
            java.lang.String r10 = r10.getConfig(r11)     // Catch:{ Exception -> 0x0163 }
            java.lang.Class r10 = java.lang.Class.forName(r10)     // Catch:{ Exception -> 0x0163 }
            java.lang.Object r10 = r10.newInstance()     // Catch:{ Exception -> 0x0163 }
            com.jcraft.jsch.SignatureDSA r10 = (com.jcraft.jsch.SignatureDSA) r10     // Catch:{ Exception -> 0x0163 }
            com.jcraft.jsch.SignatureDSA r10 = (com.jcraft.jsch.SignatureDSA) r10     // Catch:{ Exception -> 0x0163 }
            r10.init()     // Catch:{ Exception -> 0x0161 }
            goto L_0x016a
        L_0x0161:
            r11 = move-exception
            goto L_0x0165
        L_0x0163:
            r11 = move-exception
            r10 = r1
        L_0x0165:
            java.io.PrintStream r1 = java.lang.System.err
            r1.println(r11)
        L_0x016a:
            r10.setPubKey(r3, r12, r0, r7)
            byte[] r11 = r9.H
            r10.update(r11)
            boolean r5 = r10.verify(r13)
            com.jcraft.jsch.Logger r10 = com.jcraft.jsch.JSch.getLogger()
            boolean r10 = r10.isEnabled(r6)
            if (r10 == 0) goto L_0x0240
            com.jcraft.jsch.Logger r10 = com.jcraft.jsch.JSch.getLogger()
            java.lang.StringBuffer r11 = new java.lang.StringBuffer
            r11.<init>()
            java.lang.String r12 = "ssh_dss_verify: signature "
            r11.append(r12)
            r11.append(r5)
            java.lang.String r11 = r11.toString()
            r10.log(r6, r11)
            goto L_0x0240
        L_0x019a:
            java.lang.String r0 = "ecdsa-sha2-nistp256"
            boolean r0 = r10.equals(r0)
            if (r0 != 0) goto L_0x01bc
            java.lang.String r0 = "ecdsa-sha2-nistp384"
            boolean r0 = r10.equals(r0)
            if (r0 != 0) goto L_0x01bc
            java.lang.String r0 = "ecdsa-sha2-nistp521"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x01b3
            goto L_0x01bc
        L_0x01b3:
            java.io.PrintStream r10 = java.lang.System.err
            java.lang.String r11 = "unknown alg"
            r10.println(r11)
            goto L_0x0240
        L_0x01bc:
            r0 = 2
            r9.type = r0
            r9.key_alg_name = r10
            int r10 = r12 + 1
            byte r12 = r11[r12]
            int r12 = r12 << 24
            r12 = r12 & r4
            int r7 = r10 + 1
            byte r10 = r11[r10]
            int r10 = r10 << 16
            r10 = r10 & r3
            r10 = r10 | r12
            int r12 = r7 + 1
            byte r7 = r11[r7]
            int r7 = r7 << 8
            r7 = r7 & r2
            r10 = r10 | r7
            int r7 = r12 + 1
            byte r12 = r11[r12]
            r12 = r12 & 255(0xff, float:3.57E-43)
            r10 = r10 | r12
            byte[] r12 = new byte[r10]
            java.lang.System.arraycopy(r11, r7, r12, r5, r10)
            int r7 = r7 + r10
            int r10 = r7 + 1
            byte r12 = r11[r7]
            int r12 = r12 << 24
            r12 = r12 & r4
            int r4 = r10 + 1
            byte r10 = r11[r10]
            int r10 = r10 << 16
            r10 = r10 & r3
            r10 = r10 | r12
            int r12 = r4 + 1
            byte r3 = r11[r4]
            int r3 = r3 << 8
            r2 = r2 & r3
            r10 = r10 | r2
            int r2 = r12 + 1
            byte r12 = r11[r12]
            r12 = r12 & 255(0xff, float:3.57E-43)
            r10 = r10 | r12
            int r2 = r2 + r6
            int r10 = r10 - r6
            int r10 = r10 / r0
            byte[] r12 = new byte[r10]
            int r0 = r12.length
            java.lang.System.arraycopy(r11, r2, r12, r5, r0)
            int r2 = r2 + r10
            byte[] r10 = new byte[r10]
            int r0 = r10.length
            java.lang.System.arraycopy(r11, r2, r10, r5, r0)
            com.jcraft.jsch.Session r11 = r9.session     // Catch:{ Exception -> 0x022d }
            java.lang.String r0 = "signature.ecdsa"
            java.lang.String r11 = r11.getConfig(r0)     // Catch:{ Exception -> 0x022d }
            java.lang.Class r11 = java.lang.Class.forName(r11)     // Catch:{ Exception -> 0x022d }
            java.lang.Object r11 = r11.newInstance()     // Catch:{ Exception -> 0x022d }
            com.jcraft.jsch.SignatureECDSA r11 = (com.jcraft.jsch.SignatureECDSA) r11     // Catch:{ Exception -> 0x022d }
            com.jcraft.jsch.SignatureECDSA r11 = (com.jcraft.jsch.SignatureECDSA) r11     // Catch:{ Exception -> 0x022d }
            r11.init()     // Catch:{ Exception -> 0x022b }
            goto L_0x0234
        L_0x022b:
            r0 = move-exception
            goto L_0x022f
        L_0x022d:
            r0 = move-exception
            r11 = r1
        L_0x022f:
            java.io.PrintStream r1 = java.lang.System.err
            r1.println(r0)
        L_0x0234:
            r11.setPubKey(r12, r10)
            byte[] r10 = r9.H
            r11.update(r10)
            boolean r5 = r11.verify(r13)
        L_0x0240:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.KeyExchange.verify(java.lang.String, byte[], int, byte[]):boolean");
    }
}
