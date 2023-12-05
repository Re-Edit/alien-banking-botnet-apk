package com.jcraft.jsch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Vector;

public class KnownHosts implements HostKeyRepository {
    private static final String _known_hosts = "known_hosts";
    private static final byte[] cr = Util.str2byte("\n");
    private static final byte[] space = {32};
    private MAC hmacsha1 = null;
    private JSch jsch = null;
    private String known_hosts = null;
    private Vector pool = null;

    KnownHosts(JSch jSch) {
        this.jsch = jSch;
        this.hmacsha1 = getHMACSHA1();
        this.pool = new Vector();
    }

    /* access modifiers changed from: package-private */
    public void setKnownHosts(String str) throws JSchException {
        try {
            this.known_hosts = str;
            setKnownHosts((InputStream) new FileInputStream(Util.checkTilde(str)));
        } catch (FileNotFoundException unused) {
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x0127, code lost:
        if (r3 == 32) goto L_0x0132;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x0129, code lost:
        if (r3 != 9) goto L_0x012c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x012c, code lost:
        r0.append((char) r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x0130, code lost:
        r3 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x0132, code lost:
        r3 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x0133, code lost:
        r4 = r0.toString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x013b, code lost:
        if (r4.length() != 0) goto L_0x0145;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x013d, code lost:
        addInvalidLine(com.jcraft.jsch.Util.byte2str(r9, 0, r1));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x0145, code lost:
        if (r3 >= r1) goto L_0x0150;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x0147, code lost:
        r14 = r9[r3];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x0149, code lost:
        if (r14 == 32) goto L_0x014d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x014b, code lost:
        if (r14 != 9) goto L_0x0150;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x014d, code lost:
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x0150, code lost:
        r7 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x0151, code lost:
        if (r3 >= r1) goto L_0x016d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x0153, code lost:
        r0.setLength(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x0156, code lost:
        if (r3 >= r1) goto L_0x0168;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x0158, code lost:
        r7 = r3 + 1;
        r3 = r9[r3];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x015c, code lost:
        if (r3 != 13) goto L_0x015f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x015f, code lost:
        if (r3 != 10) goto L_0x0162;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x0162, code lost:
        r0.append((char) r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x0166, code lost:
        r3 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x0168, code lost:
        r7 = r0.toString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x016d, code lost:
        r8.pool.addElement(new com.jcraft.jsch.KnownHosts.HashedHostKey(r17, r12, r11, r13, com.jcraft.jsch.Util.fromBase64(com.jcraft.jsch.Util.str2byte(r4), 0, r4.length()), r7));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x018a, code lost:
        addInvalidLine(com.jcraft.jsch.Util.byte2str(r9, 0, r1));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x005b, code lost:
        if (r3 < r1) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x005d, code lost:
        addInvalidLine(com.jcraft.jsch.Util.byte2str(r9, 0, r1));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0066, code lost:
        r0.setLength(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0069, code lost:
        if (r3 >= r1) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x006b, code lost:
        r11 = r3 + 1;
        r3 = r9[r3];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x006f, code lost:
        if (r3 == 32) goto L_0x007a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0071, code lost:
        if (r3 != 9) goto L_0x0074;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0074, code lost:
        r0.append((char) r3);
        r3 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x007a, code lost:
        r3 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x007b, code lost:
        r11 = r0.toString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x007f, code lost:
        if (r3 >= r1) goto L_0x018a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0085, code lost:
        if (r11.length() != 0) goto L_0x0089;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0089, code lost:
        if (r3 >= r1) goto L_0x0094;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x008b, code lost:
        r12 = r9[r3];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x008d, code lost:
        if (r12 == 32) goto L_0x0091;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x008f, code lost:
        if (r12 != 9) goto L_0x0094;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0091, code lost:
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0094, code lost:
        r12 = "";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x009c, code lost:
        if (r11.charAt(0) != '@') goto L_0x00da;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x009e, code lost:
        r0.setLength(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00a1, code lost:
        if (r3 >= r1) goto L_0x00b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00a3, code lost:
        r12 = r3 + 1;
        r3 = r9[r3];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00a7, code lost:
        if (r3 == 32) goto L_0x00b2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00a9, code lost:
        if (r3 != 9) goto L_0x00ac;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00ac, code lost:
        r0.append((char) r3);
        r3 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00b2, code lost:
        r3 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00b3, code lost:
        r12 = r0.toString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00b7, code lost:
        if (r3 >= r1) goto L_0x00d1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00bd, code lost:
        if (r12.length() != 0) goto L_0x00c0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00c0, code lost:
        if (r3 >= r1) goto L_0x00cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00c2, code lost:
        r13 = r9[r3];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x00c4, code lost:
        if (r13 == 32) goto L_0x00c8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x00c6, code lost:
        if (r13 != 9) goto L_0x00cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x00c8, code lost:
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x00cb, code lost:
        r16 = r12;
        r12 = r11;
        r11 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x00d1, code lost:
        addInvalidLine(com.jcraft.jsch.Util.byte2str(r9, 0, r1));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x00da, code lost:
        r0.setLength(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x00dd, code lost:
        if (r3 >= r1) goto L_0x00ef;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x00df, code lost:
        r13 = r3 + 1;
        r3 = r9[r3];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x00e3, code lost:
        if (r3 == 32) goto L_0x00ee;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x00e5, code lost:
        if (r3 != 9) goto L_0x00e8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x00e8, code lost:
        r0.append((char) r3);
        r3 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x00ee, code lost:
        r3 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x00ef, code lost:
        r13 = r0.toString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x00f8, code lost:
        if (com.jcraft.jsch.HostKey.name2type(r13) == 6) goto L_0x0100;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x00fa, code lost:
        r13 = com.jcraft.jsch.HostKey.name2type(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x0100, code lost:
        r3 = r1;
        r13 = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x0102, code lost:
        if (r3 < r1) goto L_0x010d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x0104, code lost:
        addInvalidLine(com.jcraft.jsch.Util.byte2str(r9, 0, r1));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x010d, code lost:
        if (r3 >= r1) goto L_0x0118;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x010f, code lost:
        r4 = r9[r3];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x0111, code lost:
        if (r4 == 32) goto L_0x0115;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x0113, code lost:
        if (r4 != 9) goto L_0x0118;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0115, code lost:
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0118, code lost:
        r0.setLength(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x011b, code lost:
        if (r3 >= r1) goto L_0x0133;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x011d, code lost:
        r4 = r3 + 1;
        r3 = r9[r3];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x0121, code lost:
        if (r3 != 13) goto L_0x0124;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x0124, code lost:
        if (r3 != 10) goto L_0x0127;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setKnownHosts(java.io.InputStream r18) throws com.jcraft.jsch.JSchException {
        /*
            r17 = this;
            r8 = r17
            java.util.Vector r0 = r8.pool
            r0.removeAllElements()
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            r1 = 1024(0x400, float:1.435E-42)
            byte[] r1 = new byte[r1]     // Catch:{ Exception -> 0x01a8 }
        L_0x0010:
            r2 = 0
            r9 = r1
            r1 = 0
        L_0x0013:
            int r3 = r18.read()     // Catch:{ Exception -> 0x01a8 }
            r4 = -1
            r5 = 10
            r6 = 13
            if (r3 != r4) goto L_0x0030
            if (r1 != 0) goto L_0x003d
            r18.close()     // Catch:{ IOException -> 0x0024 }
            return
        L_0x0024:
            r0 = move-exception
            r1 = r0
            com.jcraft.jsch.JSchException r0 = new com.jcraft.jsch.JSchException
            java.lang.String r2 = r1.toString()
            r0.<init>(r2, r1)
            throw r0
        L_0x0030:
            if (r3 != r6) goto L_0x0033
            goto L_0x0013
        L_0x0033:
            if (r3 != r5) goto L_0x0036
            goto L_0x003d
        L_0x0036:
            int r7 = r9.length     // Catch:{ Exception -> 0x01a8 }
            if (r7 > r1) goto L_0x019e
            r7 = 10240(0x2800, float:1.4349E-41)
            if (r1 <= r7) goto L_0x0194
        L_0x003d:
            r3 = 0
        L_0x003e:
            r7 = 9
            r10 = 32
            if (r3 >= r1) goto L_0x005b
            byte r11 = r9[r3]     // Catch:{ Exception -> 0x01a8 }
            if (r11 == r10) goto L_0x0058
            if (r11 != r7) goto L_0x004b
            goto L_0x0058
        L_0x004b:
            r12 = 35
            if (r11 != r12) goto L_0x005b
            java.lang.String r1 = com.jcraft.jsch.Util.byte2str(r9, r2, r1)     // Catch:{ Exception -> 0x01a8 }
            r8.addInvalidLine(r1)     // Catch:{ Exception -> 0x01a8 }
            goto L_0x0191
        L_0x0058:
            int r3 = r3 + 1
            goto L_0x003e
        L_0x005b:
            if (r3 < r1) goto L_0x0066
            java.lang.String r1 = com.jcraft.jsch.Util.byte2str(r9, r2, r1)     // Catch:{ Exception -> 0x01a8 }
            r8.addInvalidLine(r1)     // Catch:{ Exception -> 0x01a8 }
            goto L_0x0191
        L_0x0066:
            r0.setLength(r2)     // Catch:{ Exception -> 0x01a8 }
        L_0x0069:
            if (r3 >= r1) goto L_0x007b
            int r11 = r3 + 1
            byte r3 = r9[r3]     // Catch:{ Exception -> 0x01a8 }
            if (r3 == r10) goto L_0x007a
            if (r3 != r7) goto L_0x0074
            goto L_0x007a
        L_0x0074:
            char r3 = (char) r3     // Catch:{ Exception -> 0x01a8 }
            r0.append(r3)     // Catch:{ Exception -> 0x01a8 }
            r3 = r11
            goto L_0x0069
        L_0x007a:
            r3 = r11
        L_0x007b:
            java.lang.String r11 = r0.toString()     // Catch:{ Exception -> 0x01a8 }
            if (r3 >= r1) goto L_0x018a
            int r12 = r11.length()     // Catch:{ Exception -> 0x01a8 }
            if (r12 != 0) goto L_0x0089
            goto L_0x018a
        L_0x0089:
            if (r3 >= r1) goto L_0x0094
            byte r12 = r9[r3]     // Catch:{ Exception -> 0x01a8 }
            if (r12 == r10) goto L_0x0091
            if (r12 != r7) goto L_0x0094
        L_0x0091:
            int r3 = r3 + 1
            goto L_0x0089
        L_0x0094:
            java.lang.String r12 = ""
            char r13 = r11.charAt(r2)     // Catch:{ Exception -> 0x01a8 }
            r14 = 64
            if (r13 != r14) goto L_0x00da
            r0.setLength(r2)     // Catch:{ Exception -> 0x01a8 }
        L_0x00a1:
            if (r3 >= r1) goto L_0x00b3
            int r12 = r3 + 1
            byte r3 = r9[r3]     // Catch:{ Exception -> 0x01a8 }
            if (r3 == r10) goto L_0x00b2
            if (r3 != r7) goto L_0x00ac
            goto L_0x00b2
        L_0x00ac:
            char r3 = (char) r3     // Catch:{ Exception -> 0x01a8 }
            r0.append(r3)     // Catch:{ Exception -> 0x01a8 }
            r3 = r12
            goto L_0x00a1
        L_0x00b2:
            r3 = r12
        L_0x00b3:
            java.lang.String r12 = r0.toString()     // Catch:{ Exception -> 0x01a8 }
            if (r3 >= r1) goto L_0x00d1
            int r13 = r12.length()     // Catch:{ Exception -> 0x01a8 }
            if (r13 != 0) goto L_0x00c0
            goto L_0x00d1
        L_0x00c0:
            if (r3 >= r1) goto L_0x00cb
            byte r13 = r9[r3]     // Catch:{ Exception -> 0x01a8 }
            if (r13 == r10) goto L_0x00c8
            if (r13 != r7) goto L_0x00cb
        L_0x00c8:
            int r3 = r3 + 1
            goto L_0x00c0
        L_0x00cb:
            r16 = r12
            r12 = r11
            r11 = r16
            goto L_0x00da
        L_0x00d1:
            java.lang.String r1 = com.jcraft.jsch.Util.byte2str(r9, r2, r1)     // Catch:{ Exception -> 0x01a8 }
            r8.addInvalidLine(r1)     // Catch:{ Exception -> 0x01a8 }
            goto L_0x0191
        L_0x00da:
            r0.setLength(r2)     // Catch:{ Exception -> 0x01a8 }
        L_0x00dd:
            if (r3 >= r1) goto L_0x00ef
            int r13 = r3 + 1
            byte r3 = r9[r3]     // Catch:{ Exception -> 0x01a8 }
            if (r3 == r10) goto L_0x00ee
            if (r3 != r7) goto L_0x00e8
            goto L_0x00ee
        L_0x00e8:
            char r3 = (char) r3     // Catch:{ Exception -> 0x01a8 }
            r0.append(r3)     // Catch:{ Exception -> 0x01a8 }
            r3 = r13
            goto L_0x00dd
        L_0x00ee:
            r3 = r13
        L_0x00ef:
            java.lang.String r13 = r0.toString()     // Catch:{ Exception -> 0x01a8 }
            int r14 = com.jcraft.jsch.HostKey.name2type(r13)     // Catch:{ Exception -> 0x01a8 }
            r15 = 6
            if (r14 == r15) goto L_0x0100
            int r4 = com.jcraft.jsch.HostKey.name2type(r13)     // Catch:{ Exception -> 0x01a8 }
            r13 = r4
            goto L_0x0102
        L_0x0100:
            r3 = r1
            r13 = -1
        L_0x0102:
            if (r3 < r1) goto L_0x010d
            java.lang.String r1 = com.jcraft.jsch.Util.byte2str(r9, r2, r1)     // Catch:{ Exception -> 0x01a8 }
            r8.addInvalidLine(r1)     // Catch:{ Exception -> 0x01a8 }
            goto L_0x0191
        L_0x010d:
            if (r3 >= r1) goto L_0x0118
            byte r4 = r9[r3]     // Catch:{ Exception -> 0x01a8 }
            if (r4 == r10) goto L_0x0115
            if (r4 != r7) goto L_0x0118
        L_0x0115:
            int r3 = r3 + 1
            goto L_0x010d
        L_0x0118:
            r0.setLength(r2)     // Catch:{ Exception -> 0x01a8 }
        L_0x011b:
            if (r3 >= r1) goto L_0x0133
            int r4 = r3 + 1
            byte r3 = r9[r3]     // Catch:{ Exception -> 0x01a8 }
            if (r3 != r6) goto L_0x0124
            goto L_0x0130
        L_0x0124:
            if (r3 != r5) goto L_0x0127
            goto L_0x0132
        L_0x0127:
            if (r3 == r10) goto L_0x0132
            if (r3 != r7) goto L_0x012c
            goto L_0x0132
        L_0x012c:
            char r3 = (char) r3     // Catch:{ Exception -> 0x01a8 }
            r0.append(r3)     // Catch:{ Exception -> 0x01a8 }
        L_0x0130:
            r3 = r4
            goto L_0x011b
        L_0x0132:
            r3 = r4
        L_0x0133:
            java.lang.String r4 = r0.toString()     // Catch:{ Exception -> 0x01a8 }
            int r14 = r4.length()     // Catch:{ Exception -> 0x01a8 }
            if (r14 != 0) goto L_0x0145
            java.lang.String r1 = com.jcraft.jsch.Util.byte2str(r9, r2, r1)     // Catch:{ Exception -> 0x01a8 }
            r8.addInvalidLine(r1)     // Catch:{ Exception -> 0x01a8 }
            goto L_0x0191
        L_0x0145:
            if (r3 >= r1) goto L_0x0150
            byte r14 = r9[r3]     // Catch:{ Exception -> 0x01a8 }
            if (r14 == r10) goto L_0x014d
            if (r14 != r7) goto L_0x0150
        L_0x014d:
            int r3 = r3 + 1
            goto L_0x0145
        L_0x0150:
            r7 = 0
            if (r3 >= r1) goto L_0x016d
            r0.setLength(r2)     // Catch:{ Exception -> 0x01a8 }
        L_0x0156:
            if (r3 >= r1) goto L_0x0168
            int r7 = r3 + 1
            byte r3 = r9[r3]     // Catch:{ Exception -> 0x01a8 }
            if (r3 != r6) goto L_0x015f
            goto L_0x0166
        L_0x015f:
            if (r3 != r5) goto L_0x0162
            goto L_0x0168
        L_0x0162:
            char r3 = (char) r3     // Catch:{ Exception -> 0x01a8 }
            r0.append(r3)     // Catch:{ Exception -> 0x01a8 }
        L_0x0166:
            r3 = r7
            goto L_0x0156
        L_0x0168:
            java.lang.String r1 = r0.toString()     // Catch:{ Exception -> 0x01a8 }
            r7 = r1
        L_0x016d:
            com.jcraft.jsch.KnownHosts$HashedHostKey r10 = new com.jcraft.jsch.KnownHosts$HashedHostKey     // Catch:{ Exception -> 0x01a8 }
            byte[] r1 = com.jcraft.jsch.Util.str2byte(r4)     // Catch:{ Exception -> 0x01a8 }
            int r3 = r4.length()     // Catch:{ Exception -> 0x01a8 }
            byte[] r6 = com.jcraft.jsch.Util.fromBase64(r1, r2, r3)     // Catch:{ Exception -> 0x01a8 }
            r1 = r10
            r2 = r17
            r3 = r12
            r4 = r11
            r5 = r13
            r1.<init>(r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x01a8 }
            java.util.Vector r1 = r8.pool     // Catch:{ Exception -> 0x01a8 }
            r1.addElement(r10)     // Catch:{ Exception -> 0x01a8 }
            goto L_0x0191
        L_0x018a:
            java.lang.String r1 = com.jcraft.jsch.Util.byte2str(r9, r2, r1)     // Catch:{ Exception -> 0x01a8 }
            r8.addInvalidLine(r1)     // Catch:{ Exception -> 0x01a8 }
        L_0x0191:
            r1 = r9
            goto L_0x0010
        L_0x0194:
            int r4 = r9.length     // Catch:{ Exception -> 0x01a8 }
            int r4 = r4 * 2
            byte[] r4 = new byte[r4]     // Catch:{ Exception -> 0x01a8 }
            int r5 = r9.length     // Catch:{ Exception -> 0x01a8 }
            java.lang.System.arraycopy(r9, r2, r4, r2, r5)     // Catch:{ Exception -> 0x01a8 }
            r9 = r4
        L_0x019e:
            int r4 = r1 + 1
            byte r3 = (byte) r3     // Catch:{ Exception -> 0x01a8 }
            r9[r1] = r3     // Catch:{ Exception -> 0x01a8 }
            r1 = r4
            goto L_0x0013
        L_0x01a6:
            r0 = move-exception
            goto L_0x01c8
        L_0x01a8:
            r0 = move-exception
            boolean r1 = r0 instanceof com.jcraft.jsch.JSchException     // Catch:{ all -> 0x01a6 }
            if (r1 != 0) goto L_0x01c5
            boolean r1 = r0 instanceof java.lang.Throwable     // Catch:{ all -> 0x01a6 }
            if (r1 == 0) goto L_0x01bb
            com.jcraft.jsch.JSchException r1 = new com.jcraft.jsch.JSchException     // Catch:{ all -> 0x01a6 }
            java.lang.String r2 = r0.toString()     // Catch:{ all -> 0x01a6 }
            r1.<init>(r2, r0)     // Catch:{ all -> 0x01a6 }
            throw r1     // Catch:{ all -> 0x01a6 }
        L_0x01bb:
            com.jcraft.jsch.JSchException r1 = new com.jcraft.jsch.JSchException     // Catch:{ all -> 0x01a6 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x01a6 }
            r1.<init>(r0)     // Catch:{ all -> 0x01a6 }
            throw r1     // Catch:{ all -> 0x01a6 }
        L_0x01c5:
            com.jcraft.jsch.JSchException r0 = (com.jcraft.jsch.JSchException) r0     // Catch:{ all -> 0x01a6 }
            throw r0     // Catch:{ all -> 0x01a6 }
        L_0x01c8:
            r18.close()     // Catch:{ IOException -> 0x01cc }
            throw r0
        L_0x01cc:
            r0 = move-exception
            r1 = r0
            com.jcraft.jsch.JSchException r0 = new com.jcraft.jsch.JSchException
            java.lang.String r2 = r1.toString()
            r0.<init>(r2, r1)
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.KnownHosts.setKnownHosts(java.io.InputStream):void");
    }

    private void addInvalidLine(String str) throws JSchException {
        this.pool.addElement(new HostKey(str, 6, (byte[]) null));
    }

    /* access modifiers changed from: package-private */
    public String getKnownHostsFile() {
        return this.known_hosts;
    }

    public String getKnownHostsRepositoryID() {
        return this.known_hosts;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003c, code lost:
        if (r5 != 1) goto L_0x005d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0044, code lost:
        if (r10.startsWith("[") == false) goto L_0x005d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x004c, code lost:
        if (r10.indexOf("]:") <= 1) goto L_0x005d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x005c, code lost:
        return check(r10.substring(1, r10.indexOf("]:")), r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x005d, code lost:
        return r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int check(java.lang.String r10, byte[] r11) {
        /*
            r9 = this;
            r0 = 1
            if (r10 != 0) goto L_0x0004
            return r0
        L_0x0004:
            com.jcraft.jsch.HostKey r1 = new com.jcraft.jsch.HostKey     // Catch:{ JSchException -> 0x0061 }
            r2 = 0
            r1.<init>(r10, r2, r11)     // Catch:{ JSchException -> 0x0061 }
            java.util.Vector r3 = r9.pool
            monitor-enter(r3)
            r4 = 0
            r5 = 1
        L_0x000f:
            java.util.Vector r6 = r9.pool     // Catch:{ all -> 0x005e }
            int r6 = r6.size()     // Catch:{ all -> 0x005e }
            if (r4 >= r6) goto L_0x003b
            java.util.Vector r6 = r9.pool     // Catch:{ all -> 0x005e }
            java.lang.Object r6 = r6.elementAt(r4)     // Catch:{ all -> 0x005e }
            com.jcraft.jsch.HostKey r6 = (com.jcraft.jsch.HostKey) r6     // Catch:{ all -> 0x005e }
            com.jcraft.jsch.HostKey r6 = (com.jcraft.jsch.HostKey) r6     // Catch:{ all -> 0x005e }
            boolean r7 = r6.isMatched(r10)     // Catch:{ all -> 0x005e }
            if (r7 == 0) goto L_0x0038
            int r7 = r6.type     // Catch:{ all -> 0x005e }
            int r8 = r1.type     // Catch:{ all -> 0x005e }
            if (r7 != r8) goto L_0x0038
            byte[] r5 = r6.key     // Catch:{ all -> 0x005e }
            boolean r5 = com.jcraft.jsch.Util.array_equals(r5, r11)     // Catch:{ all -> 0x005e }
            if (r5 == 0) goto L_0x0037
            monitor-exit(r3)     // Catch:{ all -> 0x005e }
            return r2
        L_0x0037:
            r5 = 2
        L_0x0038:
            int r4 = r4 + 1
            goto L_0x000f
        L_0x003b:
            monitor-exit(r3)     // Catch:{ all -> 0x005e }
            if (r5 != r0) goto L_0x005d
            java.lang.String r1 = "["
            boolean r1 = r10.startsWith(r1)
            if (r1 == 0) goto L_0x005d
            java.lang.String r1 = "]:"
            int r1 = r10.indexOf(r1)
            if (r1 <= r0) goto L_0x005d
            java.lang.String r1 = "]:"
            int r1 = r10.indexOf(r1)
            java.lang.String r10 = r10.substring(r0, r1)
            int r10 = r9.check(r10, r11)
            return r10
        L_0x005d:
            return r5
        L_0x005e:
            r10 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x005e }
            throw r10
        L_0x0061:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.KnownHosts.check(java.lang.String, byte[]):int");
    }

    public void add(HostKey hostKey, UserInfo userInfo) {
        boolean z;
        int i = hostKey.type;
        String host = hostKey.getHost();
        byte[] bArr = hostKey.key;
        synchronized (this.pool) {
            z = false;
            for (int i2 = 0; i2 < this.pool.size(); i2++) {
                HostKey hostKey2 = (HostKey) this.pool.elementAt(i2);
                if (hostKey2.isMatched(host)) {
                    int i3 = hostKey2.type;
                }
            }
        }
        this.pool.addElement(hostKey);
        String knownHostsRepositoryID = getKnownHostsRepositoryID();
        if (knownHostsRepositoryID != null) {
            File file = new File(Util.checkTilde(knownHostsRepositoryID));
            if (file.exists()) {
                z = true;
            } else if (userInfo != null) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(knownHostsRepositoryID);
                stringBuffer.append(" does not exist.\n");
                stringBuffer.append("Are you sure you want to create it?");
                boolean promptYesNo = userInfo.promptYesNo(stringBuffer.toString());
                File parentFile = file.getParentFile();
                if (promptYesNo && parentFile != null && !parentFile.exists()) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("The parent directory ");
                    stringBuffer2.append(parentFile);
                    stringBuffer2.append(" does not exist.\n");
                    stringBuffer2.append("Are you sure you want to create it?");
                    promptYesNo = userInfo.promptYesNo(stringBuffer2.toString());
                    if (promptYesNo) {
                        if (!parentFile.mkdirs()) {
                            StringBuffer stringBuffer3 = new StringBuffer();
                            stringBuffer3.append(parentFile);
                            stringBuffer3.append(" has not been created.");
                            userInfo.showMessage(stringBuffer3.toString());
                            promptYesNo = false;
                        } else {
                            StringBuffer stringBuffer4 = new StringBuffer();
                            stringBuffer4.append(parentFile);
                            stringBuffer4.append(" has been succesfully created.\nPlease check its access permission.");
                            userInfo.showMessage(stringBuffer4.toString());
                        }
                    }
                }
                if (parentFile != null) {
                    z = promptYesNo;
                }
            }
            if (z) {
                try {
                    sync(knownHostsRepositoryID);
                } catch (Exception e) {
                    PrintStream printStream = System.err;
                    StringBuffer stringBuffer5 = new StringBuffer();
                    stringBuffer5.append("sync known_hosts: ");
                    stringBuffer5.append(e);
                    printStream.println(stringBuffer5.toString());
                }
            }
        }
    }

    public HostKey[] getHostKey() {
        return getHostKey((String) null, (String) null);
    }

    public HostKey[] getHostKey(String str, String str2) {
        HostKey[] hostKeyArr;
        synchronized (this.pool) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < this.pool.size(); i++) {
                HostKey hostKey = (HostKey) this.pool.elementAt(i);
                if (hostKey.type != 6) {
                    if (str == null || (hostKey.isMatched(str) && (str2 == null || hostKey.getType().equals(str2)))) {
                        arrayList.add(hostKey);
                    }
                }
            }
            HostKey[] hostKeyArr2 = new HostKey[arrayList.size()];
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                hostKeyArr2[i2] = (HostKey) arrayList.get(i2);
            }
            if (str != null && str.startsWith("[") && str.indexOf("]:") > 1) {
                HostKey[] hostKey2 = getHostKey(str.substring(1, str.indexOf("]:")), str2);
                if (hostKey2.length > 0) {
                    hostKeyArr = new HostKey[(hostKeyArr2.length + hostKey2.length)];
                    System.arraycopy(hostKeyArr2, 0, hostKeyArr, 0, hostKeyArr2.length);
                    System.arraycopy(hostKey2, 0, hostKeyArr, hostKeyArr2.length, hostKey2.length);
                }
            }
            hostKeyArr = hostKeyArr2;
        }
        return hostKeyArr;
    }

    public void remove(String str, String str2) {
        remove(str, str2, (byte[]) null);
    }

    public void remove(String str, String str2, byte[] bArr) {
        boolean z;
        synchronized (this.pool) {
            z = false;
            for (int i = 0; i < this.pool.size(); i++) {
                HostKey hostKey = (HostKey) this.pool.elementAt(i);
                if (str == null || (hostKey.isMatched(str) && (str2 == null || (hostKey.getType().equals(str2) && (bArr == null || Util.array_equals(bArr, hostKey.key)))))) {
                    String host = hostKey.getHost();
                    if (!host.equals(str)) {
                        if (!(hostKey instanceof HashedHostKey) || !((HashedHostKey) hostKey).isHashed()) {
                            hostKey.host = deleteSubString(host, str);
                            z = true;
                        }
                    }
                    this.pool.removeElement(hostKey);
                    z = true;
                }
            }
        }
        if (z) {
            try {
                sync();
            } catch (Exception unused) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public void sync() throws IOException {
        String str = this.known_hosts;
        if (str != null) {
            sync(str);
        }
    }

    /* access modifiers changed from: protected */
    public synchronized void sync(String str) throws IOException {
        if (str != null) {
            FileOutputStream fileOutputStream = new FileOutputStream(Util.checkTilde(str));
            dump(fileOutputStream);
            fileOutputStream.close();
        }
    }

    /* access modifiers changed from: package-private */
    public void dump(OutputStream outputStream) throws IOException {
        try {
            synchronized (this.pool) {
                for (int i = 0; i < this.pool.size(); i++) {
                    HostKey hostKey = (HostKey) this.pool.elementAt(i);
                    String marker = hostKey.getMarker();
                    String host = hostKey.getHost();
                    String type = hostKey.getType();
                    String comment = hostKey.getComment();
                    if (type.equals("UNKNOWN")) {
                        outputStream.write(Util.str2byte(host));
                        outputStream.write(cr);
                    } else {
                        if (marker.length() != 0) {
                            outputStream.write(Util.str2byte(marker));
                            outputStream.write(space);
                        }
                        outputStream.write(Util.str2byte(host));
                        outputStream.write(space);
                        outputStream.write(Util.str2byte(type));
                        outputStream.write(space);
                        outputStream.write(Util.str2byte(hostKey.getKey()));
                        if (comment != null) {
                            outputStream.write(space);
                            outputStream.write(Util.str2byte(comment));
                        }
                        outputStream.write(cr);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private String deleteSubString(String str, String str2) {
        int length = str2.length();
        int length2 = str.length();
        int i = 0;
        while (i < length2) {
            int indexOf = str.indexOf(44, i);
            if (indexOf == -1) {
                break;
            } else if (!str2.equals(str.substring(i, indexOf))) {
                i = indexOf + 1;
            } else {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(str.substring(0, i));
                stringBuffer.append(str.substring(indexOf + 1));
                return stringBuffer.toString();
            }
        }
        if (!str.endsWith(str2) || length2 - i != length) {
            return str;
        }
        return str.substring(0, length == length2 ? 0 : (length2 - length) - 1);
    }

    /* access modifiers changed from: private */
    public MAC getHMACSHA1() {
        if (this.hmacsha1 == null) {
            try {
                JSch jSch = this.jsch;
                this.hmacsha1 = (MAC) Class.forName(JSch.getConfig("hmac-sha1")).newInstance();
            } catch (Exception e) {
                PrintStream printStream = System.err;
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("hmacsha1: ");
                stringBuffer.append(e);
                printStream.println(stringBuffer.toString());
            }
        }
        return this.hmacsha1;
    }

    /* access modifiers changed from: package-private */
    public HostKey createHashedHostKey(String str, byte[] bArr) throws JSchException {
        HashedHostKey hashedHostKey = new HashedHostKey(this, str, bArr);
        hashedHostKey.hash();
        return hashedHostKey;
    }

    class HashedHostKey extends HostKey {
        private static final String HASH_DELIM = "|";
        private static final String HASH_MAGIC = "|1|";
        byte[] hash;
        private boolean hashed;
        byte[] salt;

        HashedHostKey(KnownHosts knownHosts, String str, byte[] bArr) throws JSchException {
            this(knownHosts, str, 0, bArr);
        }

        HashedHostKey(KnownHosts knownHosts, String str, int i, byte[] bArr) throws JSchException {
            this("", str, i, bArr, (String) null);
        }

        HashedHostKey(String str, String str2, int i, byte[] bArr, String str3) throws JSchException {
            super(str, str2, i, bArr, str3);
            this.hashed = false;
            this.salt = null;
            this.hash = null;
            if (this.host.startsWith(HASH_MAGIC) && this.host.substring(3).indexOf(HASH_DELIM) > 0) {
                String substring = this.host.substring(3);
                String substring2 = substring.substring(0, substring.indexOf(HASH_DELIM));
                String substring3 = substring.substring(substring.indexOf(HASH_DELIM) + 1);
                this.salt = Util.fromBase64(Util.str2byte(substring2), 0, substring2.length());
                this.hash = Util.fromBase64(Util.str2byte(substring3), 0, substring3.length());
                if (this.salt.length == 20 && this.hash.length == 20) {
                    this.hashed = true;
                    return;
                }
                this.salt = null;
                this.hash = null;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean isMatched(String str) {
            boolean array_equals;
            if (!this.hashed) {
                return super.isMatched(str);
            }
            MAC access$000 = KnownHosts.this.getHMACSHA1();
            try {
                synchronized (access$000) {
                    access$000.init(this.salt);
                    byte[] str2byte = Util.str2byte(str);
                    access$000.update(str2byte, 0, str2byte.length);
                    byte[] bArr = new byte[access$000.getBlockSize()];
                    access$000.doFinal(bArr, 0);
                    array_equals = Util.array_equals(this.hash, bArr);
                }
                return array_equals;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean isHashed() {
            return this.hashed;
        }

        /* access modifiers changed from: package-private */
        public void hash() {
            if (!this.hashed) {
                MAC access$000 = KnownHosts.this.getHMACSHA1();
                if (this.salt == null) {
                    Random random = Session.random;
                    synchronized (random) {
                        this.salt = new byte[access$000.getBlockSize()];
                        random.fill(this.salt, 0, this.salt.length);
                    }
                }
                try {
                    synchronized (access$000) {
                        access$000.init(this.salt);
                        byte[] str2byte = Util.str2byte(this.host);
                        access$000.update(str2byte, 0, str2byte.length);
                        this.hash = new byte[access$000.getBlockSize()];
                        access$000.doFinal(this.hash, 0);
                    }
                } catch (Exception unused) {
                }
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(HASH_MAGIC);
                byte[] bArr = this.salt;
                stringBuffer.append(Util.byte2str(Util.toBase64(bArr, 0, bArr.length)));
                stringBuffer.append(HASH_DELIM);
                byte[] bArr2 = this.hash;
                stringBuffer.append(Util.byte2str(Util.toBase64(bArr2, 0, bArr2.length)));
                this.host = stringBuffer.toString();
                this.hashed = true;
            }
        }
    }
}
