package com.jcraft.jsch;

class UserAuthKeyboardInteractive extends UserAuth {
    UserAuthKeyboardInteractive() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00da, code lost:
        r0.buf.getInt();
        r0.buf.getByte();
        r0.buf.getByte();
        r5 = r0.buf.getString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00f5, code lost:
        if (r0.buf.getByte() != 0) goto L_0x010d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00f7, code lost:
        if (r4 == false) goto L_0x00fa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00f9, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00fa, code lost:
        r1.auth_failures++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00ff, code lost:
        if (r12 != false) goto L_0x0105;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0101, code lost:
        r5 = r12;
        r4 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x010c, code lost:
        throw new com.jcraft.jsch.JSchAuthCancelException("keyboard-interactive");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0116, code lost:
        throw new com.jcraft.jsch.JSchPartialAuthException(com.jcraft.jsch.Util.byte2str(r5));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean start(com.jcraft.jsch.Session r17) throws java.lang.Exception {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            super.start(r17)
            com.jcraft.jsch.UserInfo r2 = r0.userinfo
            r3 = 0
            if (r2 == 0) goto L_0x0013
            com.jcraft.jsch.UserInfo r2 = r0.userinfo
            boolean r2 = r2 instanceof com.jcraft.jsch.UIKeyboardInteractive
            if (r2 != 0) goto L_0x0013
            return r3
        L_0x0013:
            java.lang.StringBuffer r2 = new java.lang.StringBuffer
            r2.<init>()
            java.lang.String r4 = r0.username
            r2.append(r4)
            java.lang.String r4 = "@"
            r2.append(r4)
            java.lang.String r4 = r1.host
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            int r4 = r1.port
            r5 = 22
            if (r4 == r5) goto L_0x0047
            java.lang.StringBuffer r4 = new java.lang.StringBuffer
            r4.<init>()
            r4.append(r2)
            java.lang.String r2 = ":"
            r4.append(r2)
            int r2 = r1.port
            r4.append(r2)
            java.lang.String r2 = r4.toString()
        L_0x0047:
            byte[] r4 = r1.password
            java.lang.String r5 = r0.username
            byte[] r10 = com.jcraft.jsch.Util.str2byte(r5)
            r5 = 0
        L_0x0050:
            int r6 = r1.auth_failures
            int r7 = r1.max_auth_tries
            if (r6 < r7) goto L_0x0057
            return r3
        L_0x0057:
            com.jcraft.jsch.Packet r6 = r0.packet
            r6.reset()
            com.jcraft.jsch.Buffer r6 = r0.buf
            r7 = 50
            r6.putByte((byte) r7)
            com.jcraft.jsch.Buffer r6 = r0.buf
            r6.putString(r10)
            com.jcraft.jsch.Buffer r6 = r0.buf
            java.lang.String r7 = "ssh-connection"
            byte[] r7 = com.jcraft.jsch.Util.str2byte(r7)
            r6.putString(r7)
            com.jcraft.jsch.Buffer r6 = r0.buf
            java.lang.String r7 = "keyboard-interactive"
            byte[] r7 = com.jcraft.jsch.Util.str2byte(r7)
            r6.putString(r7)
            com.jcraft.jsch.Buffer r6 = r0.buf
            byte[] r7 = com.jcraft.jsch.Util.empty
            r6.putString(r7)
            com.jcraft.jsch.Buffer r6 = r0.buf
            byte[] r7 = com.jcraft.jsch.Util.empty
            r6.putString(r7)
            com.jcraft.jsch.Packet r6 = r0.packet
            r1.write(r6)
            r11 = 1
            r13 = r4
            r12 = r5
            r4 = 1
        L_0x0095:
            com.jcraft.jsch.Buffer r5 = r0.buf
            com.jcraft.jsch.Buffer r5 = r1.read(r5)
            r0.buf = r5
            com.jcraft.jsch.Buffer r5 = r0.buf
            byte r5 = r5.getCommand()
            r5 = r5 & 255(0xff, float:3.57E-43)
            r6 = 52
            if (r5 != r6) goto L_0x00aa
            return r11
        L_0x00aa:
            r6 = 53
            if (r5 != r6) goto L_0x00d6
            com.jcraft.jsch.Buffer r5 = r0.buf
            r5.getInt()
            com.jcraft.jsch.Buffer r5 = r0.buf
            r5.getByte()
            com.jcraft.jsch.Buffer r5 = r0.buf
            r5.getByte()
            com.jcraft.jsch.Buffer r5 = r0.buf
            byte[] r5 = r5.getString()
            com.jcraft.jsch.Buffer r6 = r0.buf
            r6.getString()
            java.lang.String r5 = com.jcraft.jsch.Util.byte2str(r5)
            com.jcraft.jsch.UserInfo r6 = r0.userinfo
            if (r6 == 0) goto L_0x0095
            com.jcraft.jsch.UserInfo r6 = r0.userinfo
            r6.showMessage(r5)
            goto L_0x0095
        L_0x00d6:
            r6 = 51
            if (r5 != r6) goto L_0x0117
            com.jcraft.jsch.Buffer r5 = r0.buf
            r5.getInt()
            com.jcraft.jsch.Buffer r5 = r0.buf
            r5.getByte()
            com.jcraft.jsch.Buffer r5 = r0.buf
            r5.getByte()
            com.jcraft.jsch.Buffer r5 = r0.buf
            byte[] r5 = r5.getString()
            com.jcraft.jsch.Buffer r6 = r0.buf
            int r6 = r6.getByte()
            if (r6 != 0) goto L_0x010d
            if (r4 == 0) goto L_0x00fa
            return r3
        L_0x00fa:
            int r4 = r1.auth_failures
            int r4 = r4 + r11
            r1.auth_failures = r4
            if (r12 != 0) goto L_0x0105
            r5 = r12
            r4 = r13
            goto L_0x0050
        L_0x0105:
            com.jcraft.jsch.JSchAuthCancelException r1 = new com.jcraft.jsch.JSchAuthCancelException
            java.lang.String r2 = "keyboard-interactive"
            r1.<init>(r2)
            throw r1
        L_0x010d:
            com.jcraft.jsch.JSchPartialAuthException r1 = new com.jcraft.jsch.JSchPartialAuthException
            java.lang.String r2 = com.jcraft.jsch.Util.byte2str(r5)
            r1.<init>(r2)
            throw r1
        L_0x0117:
            r4 = 60
            if (r5 != r4) goto L_0x020a
            com.jcraft.jsch.Buffer r4 = r0.buf
            r4.getInt()
            com.jcraft.jsch.Buffer r4 = r0.buf
            r4.getByte()
            com.jcraft.jsch.Buffer r4 = r0.buf
            r4.getByte()
            com.jcraft.jsch.Buffer r4 = r0.buf
            byte[] r4 = r4.getString()
            java.lang.String r6 = com.jcraft.jsch.Util.byte2str(r4)
            com.jcraft.jsch.Buffer r4 = r0.buf
            byte[] r4 = r4.getString()
            java.lang.String r7 = com.jcraft.jsch.Util.byte2str(r4)
            com.jcraft.jsch.Buffer r4 = r0.buf
            byte[] r4 = r4.getString()
            com.jcraft.jsch.Util.byte2str(r4)
            com.jcraft.jsch.Buffer r4 = r0.buf
            int r14 = r4.getInt()
            java.lang.String[] r8 = new java.lang.String[r14]
            boolean[] r9 = new boolean[r14]
            r4 = 0
        L_0x0152:
            if (r4 >= r14) goto L_0x0170
            com.jcraft.jsch.Buffer r5 = r0.buf
            byte[] r5 = r5.getString()
            java.lang.String r5 = com.jcraft.jsch.Util.byte2str(r5)
            r8[r4] = r5
            com.jcraft.jsch.Buffer r5 = r0.buf
            int r5 = r5.getByte()
            if (r5 == 0) goto L_0x016a
            r5 = 1
            goto L_0x016b
        L_0x016a:
            r5 = 0
        L_0x016b:
            r9[r4] = r5
            int r4 = r4 + 1
            goto L_0x0152
        L_0x0170:
            r4 = 0
            r15 = r4
            byte[][] r15 = (byte[][]) r15
            if (r13 == 0) goto L_0x0191
            int r5 = r8.length
            if (r5 != r11) goto L_0x0191
            boolean r5 = r9[r3]
            if (r5 != 0) goto L_0x0191
            r5 = r8[r3]
            java.lang.String r5 = r5.toLowerCase()
            java.lang.String r4 = "password:"
            int r4 = r5.indexOf(r4)
            if (r4 < 0) goto L_0x0191
            byte[][] r15 = new byte[r11][]
            r15[r3] = r13
            r13 = 0
            goto L_0x01c0
        L_0x0191:
            if (r14 > 0) goto L_0x019f
            int r4 = r6.length()
            if (r4 > 0) goto L_0x019f
            int r4 = r7.length()
            if (r4 <= 0) goto L_0x01c0
        L_0x019f:
            com.jcraft.jsch.UserInfo r4 = r0.userinfo
            if (r4 == 0) goto L_0x01c0
            com.jcraft.jsch.UserInfo r4 = r0.userinfo
            com.jcraft.jsch.UIKeyboardInteractive r4 = (com.jcraft.jsch.UIKeyboardInteractive) r4
            r5 = r2
            java.lang.String[] r4 = r4.promptKeyboardInteractive(r5, r6, r7, r8, r9)
            if (r4 == 0) goto L_0x01c0
            int r5 = r4.length
            byte[][] r15 = new byte[r5][]
            r5 = 0
        L_0x01b2:
            int r6 = r4.length
            if (r5 >= r6) goto L_0x01c0
            r6 = r4[r5]
            byte[] r6 = com.jcraft.jsch.Util.str2byte(r6)
            r15[r5] = r6
            int r5 = r5 + 1
            goto L_0x01b2
        L_0x01c0:
            com.jcraft.jsch.Packet r4 = r0.packet
            r4.reset()
            com.jcraft.jsch.Buffer r4 = r0.buf
            r5 = 61
            r4.putByte((byte) r5)
            if (r14 <= 0) goto L_0x01f0
            if (r15 == 0) goto L_0x01d3
            int r4 = r15.length
            if (r14 == r4) goto L_0x01f0
        L_0x01d3:
            if (r15 != 0) goto L_0x01e7
            com.jcraft.jsch.Buffer r4 = r0.buf
            r4.putInt(r14)
            r4 = 0
        L_0x01db:
            if (r4 >= r14) goto L_0x01ec
            com.jcraft.jsch.Buffer r5 = r0.buf
            byte[] r6 = com.jcraft.jsch.Util.empty
            r5.putString(r6)
            int r4 = r4 + 1
            goto L_0x01db
        L_0x01e7:
            com.jcraft.jsch.Buffer r4 = r0.buf
            r4.putInt(r3)
        L_0x01ec:
            if (r15 != 0) goto L_0x0202
            r12 = 1
            goto L_0x0202
        L_0x01f0:
            com.jcraft.jsch.Buffer r4 = r0.buf
            r4.putInt(r14)
            r4 = 0
        L_0x01f6:
            if (r4 >= r14) goto L_0x0202
            com.jcraft.jsch.Buffer r5 = r0.buf
            r6 = r15[r4]
            r5.putString(r6)
            int r4 = r4 + 1
            goto L_0x01f6
        L_0x0202:
            com.jcraft.jsch.Packet r4 = r0.packet
            r1.write(r4)
            r4 = 0
            goto L_0x0095
        L_0x020a:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.UserAuthKeyboardInteractive.start(com.jcraft.jsch.Session):boolean");
    }
}
