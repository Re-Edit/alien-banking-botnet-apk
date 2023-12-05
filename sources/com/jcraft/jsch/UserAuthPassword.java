package com.jcraft.jsch;

class UserAuthPassword extends UserAuth {
    private final int SSH_MSG_USERAUTH_PASSWD_CHANGEREQ = 60;

    UserAuthPassword() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:60:0x01b2, code lost:
        if (r1 != 51) goto L_0x01e8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:?, code lost:
        r12.buf.getInt();
        r12.buf.getByte();
        r12.buf.getByte();
        r1 = r12.buf.getString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x01cf, code lost:
        if (r12.buf.getByte() != 0) goto L_0x01de;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x01d1, code lost:
        r13.auth_failures++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x01d6, code lost:
        if (r0 == null) goto L_0x003c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x01d8, code lost:
        com.jcraft.jsch.Util.bzero(r0);
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x01e7, code lost:
        throw new com.jcraft.jsch.JSchPartialAuthException(com.jcraft.jsch.Util.byte2str(r1));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x01e8, code lost:
        if (r0 == null) goto L_0x01ed;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x01ea, code lost:
        com.jcraft.jsch.Util.bzero(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x01ed, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean start(com.jcraft.jsch.Session r13) throws java.lang.Exception {
        /*
            r12 = this;
            super.start(r13)
            byte[] r0 = r13.password
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            r1.<init>()
            java.lang.String r2 = r12.username
            r1.append(r2)
            java.lang.String r2 = "@"
            r1.append(r2)
            java.lang.String r2 = r13.host
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            int r2 = r13.port
            r3 = 22
            if (r2 == r3) goto L_0x003b
            java.lang.StringBuffer r2 = new java.lang.StringBuffer
            r2.<init>()
            r2.append(r1)
            java.lang.String r1 = ":"
            r2.append(r1)
            int r1 = r13.port
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            r7 = r1
            goto L_0x003c
        L_0x003b:
            r7 = r1
        L_0x003c:
            int r1 = r13.auth_failures     // Catch:{ all -> 0x01ee }
            int r2 = r13.max_auth_tries     // Catch:{ all -> 0x01ee }
            r8 = 0
            if (r1 < r2) goto L_0x0049
            if (r0 == 0) goto L_0x0048
            com.jcraft.jsch.Util.bzero(r0)
        L_0x0048:
            return r8
        L_0x0049:
            if (r0 != 0) goto L_0x008b
            com.jcraft.jsch.UserInfo r1 = r12.userinfo     // Catch:{ all -> 0x01ee }
            if (r1 != 0) goto L_0x0055
            if (r0 == 0) goto L_0x0054
            com.jcraft.jsch.Util.bzero(r0)
        L_0x0054:
            return r8
        L_0x0055:
            com.jcraft.jsch.UserInfo r1 = r12.userinfo     // Catch:{ all -> 0x01ee }
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch:{ all -> 0x01ee }
            r2.<init>()     // Catch:{ all -> 0x01ee }
            java.lang.String r3 = "Password for "
            r2.append(r3)     // Catch:{ all -> 0x01ee }
            r2.append(r7)     // Catch:{ all -> 0x01ee }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x01ee }
            boolean r1 = r1.promptPassword(r2)     // Catch:{ all -> 0x01ee }
            if (r1 == 0) goto L_0x0083
            com.jcraft.jsch.UserInfo r1 = r12.userinfo     // Catch:{ all -> 0x01ee }
            java.lang.String r1 = r1.getPassword()     // Catch:{ all -> 0x01ee }
            if (r1 == 0) goto L_0x007b
            byte[] r0 = com.jcraft.jsch.Util.str2byte(r1)     // Catch:{ all -> 0x01ee }
            goto L_0x008b
        L_0x007b:
            com.jcraft.jsch.JSchAuthCancelException r13 = new com.jcraft.jsch.JSchAuthCancelException     // Catch:{ all -> 0x01ee }
            java.lang.String r1 = "password"
            r13.<init>(r1)     // Catch:{ all -> 0x01ee }
            throw r13     // Catch:{ all -> 0x01ee }
        L_0x0083:
            com.jcraft.jsch.JSchAuthCancelException r13 = new com.jcraft.jsch.JSchAuthCancelException     // Catch:{ all -> 0x01ee }
            java.lang.String r1 = "password"
            r13.<init>(r1)     // Catch:{ all -> 0x01ee }
            throw r13     // Catch:{ all -> 0x01ee }
        L_0x008b:
            java.lang.String r1 = r12.username     // Catch:{ all -> 0x01ee }
            byte[] r9 = com.jcraft.jsch.Util.str2byte(r1)     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Packet r1 = r12.packet     // Catch:{ all -> 0x01ee }
            r1.reset()     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r1 = r12.buf     // Catch:{ all -> 0x01ee }
            r10 = 50
            r1.putByte((byte) r10)     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r1 = r12.buf     // Catch:{ all -> 0x01ee }
            r1.putString(r9)     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r1 = r12.buf     // Catch:{ all -> 0x01ee }
            java.lang.String r2 = "ssh-connection"
            byte[] r2 = com.jcraft.jsch.Util.str2byte(r2)     // Catch:{ all -> 0x01ee }
            r1.putString(r2)     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r1 = r12.buf     // Catch:{ all -> 0x01ee }
            java.lang.String r2 = "password"
            byte[] r2 = com.jcraft.jsch.Util.str2byte(r2)     // Catch:{ all -> 0x01ee }
            r1.putString(r2)     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r1 = r12.buf     // Catch:{ all -> 0x01ee }
            r1.putByte((byte) r8)     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r1 = r12.buf     // Catch:{ all -> 0x01ee }
            r1.putString(r0)     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Packet r1 = r12.packet     // Catch:{ all -> 0x01ee }
            r13.write(r1)     // Catch:{ all -> 0x01ee }
        L_0x00c7:
            com.jcraft.jsch.Buffer r1 = r12.buf     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r1 = r13.read(r1)     // Catch:{ all -> 0x01ee }
            r12.buf = r1     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r1 = r12.buf     // Catch:{ all -> 0x01ee }
            byte r1 = r1.getCommand()     // Catch:{ all -> 0x01ee }
            r1 = r1 & 255(0xff, float:3.57E-43)
            r2 = 52
            r11 = 1
            if (r1 != r2) goto L_0x00e2
            if (r0 == 0) goto L_0x00e1
            com.jcraft.jsch.Util.bzero(r0)
        L_0x00e1:
            return r11
        L_0x00e2:
            r2 = 53
            if (r1 != r2) goto L_0x010e
            com.jcraft.jsch.Buffer r1 = r12.buf     // Catch:{ all -> 0x01ee }
            r1.getInt()     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r1 = r12.buf     // Catch:{ all -> 0x01ee }
            r1.getByte()     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r1 = r12.buf     // Catch:{ all -> 0x01ee }
            r1.getByte()     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r1 = r12.buf     // Catch:{ all -> 0x01ee }
            byte[] r1 = r1.getString()     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r2 = r12.buf     // Catch:{ all -> 0x01ee }
            r2.getString()     // Catch:{ all -> 0x01ee }
            java.lang.String r1 = com.jcraft.jsch.Util.byte2str(r1)     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.UserInfo r2 = r12.userinfo     // Catch:{ all -> 0x01ee }
            if (r2 == 0) goto L_0x00c7
            com.jcraft.jsch.UserInfo r2 = r12.userinfo     // Catch:{ all -> 0x01ee }
            r2.showMessage(r1)     // Catch:{ all -> 0x01ee }
            goto L_0x00c7
        L_0x010e:
            r2 = 60
            if (r1 != r2) goto L_0x01b0
            com.jcraft.jsch.Buffer r1 = r12.buf     // Catch:{ all -> 0x01ee }
            r1.getInt()     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r1 = r12.buf     // Catch:{ all -> 0x01ee }
            r1.getByte()     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r1 = r12.buf     // Catch:{ all -> 0x01ee }
            r1.getByte()     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r1 = r12.buf     // Catch:{ all -> 0x01ee }
            byte[] r1 = r1.getString()     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r2 = r12.buf     // Catch:{ all -> 0x01ee }
            r2.getString()     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.UserInfo r2 = r12.userinfo     // Catch:{ all -> 0x01ee }
            if (r2 == 0) goto L_0x019f
            com.jcraft.jsch.UserInfo r2 = r12.userinfo     // Catch:{ all -> 0x01ee }
            boolean r2 = r2 instanceof com.jcraft.jsch.UIKeyboardInteractive     // Catch:{ all -> 0x01ee }
            if (r2 != 0) goto L_0x0137
            goto L_0x019f
        L_0x0137:
            com.jcraft.jsch.UserInfo r2 = r12.userinfo     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.UIKeyboardInteractive r2 = (com.jcraft.jsch.UIKeyboardInteractive) r2     // Catch:{ all -> 0x01ee }
            java.lang.String r3 = "Password Change Required"
            java.lang.String[] r5 = new java.lang.String[r11]     // Catch:{ all -> 0x01ee }
            java.lang.String r4 = "New Password: "
            r5[r8] = r4     // Catch:{ all -> 0x01ee }
            boolean[] r6 = new boolean[r11]     // Catch:{ all -> 0x01ee }
            r6[r8] = r8     // Catch:{ all -> 0x01ee }
            java.lang.String r4 = com.jcraft.jsch.Util.byte2str(r1)     // Catch:{ all -> 0x01ee }
            r1 = r2
            r2 = r7
            java.lang.String[] r1 = r1.promptKeyboardInteractive(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x01ee }
            if (r1 == 0) goto L_0x0197
            r1 = r1[r8]     // Catch:{ all -> 0x01ee }
            byte[] r1 = com.jcraft.jsch.Util.str2byte(r1)     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Packet r2 = r12.packet     // Catch:{ all -> 0x01ee }
            r2.reset()     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r2 = r12.buf     // Catch:{ all -> 0x01ee }
            r2.putByte((byte) r10)     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r2 = r12.buf     // Catch:{ all -> 0x01ee }
            r2.putString(r9)     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r2 = r12.buf     // Catch:{ all -> 0x01ee }
            java.lang.String r3 = "ssh-connection"
            byte[] r3 = com.jcraft.jsch.Util.str2byte(r3)     // Catch:{ all -> 0x01ee }
            r2.putString(r3)     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r2 = r12.buf     // Catch:{ all -> 0x01ee }
            java.lang.String r3 = "password"
            byte[] r3 = com.jcraft.jsch.Util.str2byte(r3)     // Catch:{ all -> 0x01ee }
            r2.putString(r3)     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r2 = r12.buf     // Catch:{ all -> 0x01ee }
            r2.putByte((byte) r11)     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r2 = r12.buf     // Catch:{ all -> 0x01ee }
            r2.putString(r0)     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r2 = r12.buf     // Catch:{ all -> 0x01ee }
            r2.putString(r1)     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Util.bzero(r1)     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Packet r1 = r12.packet     // Catch:{ all -> 0x01ee }
            r13.write(r1)     // Catch:{ all -> 0x01ee }
            goto L_0x00c7
        L_0x0197:
            com.jcraft.jsch.JSchAuthCancelException r13 = new com.jcraft.jsch.JSchAuthCancelException     // Catch:{ all -> 0x01ee }
            java.lang.String r1 = "password"
            r13.<init>(r1)     // Catch:{ all -> 0x01ee }
            throw r13     // Catch:{ all -> 0x01ee }
        L_0x019f:
            com.jcraft.jsch.UserInfo r13 = r12.userinfo     // Catch:{ all -> 0x01ee }
            if (r13 == 0) goto L_0x01aa
            com.jcraft.jsch.UserInfo r13 = r12.userinfo     // Catch:{ all -> 0x01ee }
            java.lang.String r1 = "Password must be changed."
            r13.showMessage(r1)     // Catch:{ all -> 0x01ee }
        L_0x01aa:
            if (r0 == 0) goto L_0x01af
            com.jcraft.jsch.Util.bzero(r0)
        L_0x01af:
            return r8
        L_0x01b0:
            r2 = 51
            if (r1 != r2) goto L_0x01e8
            com.jcraft.jsch.Buffer r1 = r12.buf     // Catch:{ all -> 0x01ee }
            r1.getInt()     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r1 = r12.buf     // Catch:{ all -> 0x01ee }
            r1.getByte()     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r1 = r12.buf     // Catch:{ all -> 0x01ee }
            r1.getByte()     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r1 = r12.buf     // Catch:{ all -> 0x01ee }
            byte[] r1 = r1.getString()     // Catch:{ all -> 0x01ee }
            com.jcraft.jsch.Buffer r2 = r12.buf     // Catch:{ all -> 0x01ee }
            int r2 = r2.getByte()     // Catch:{ all -> 0x01ee }
            if (r2 != 0) goto L_0x01de
            int r1 = r13.auth_failures     // Catch:{ all -> 0x01ee }
            int r1 = r1 + r11
            r13.auth_failures = r1     // Catch:{ all -> 0x01ee }
            if (r0 == 0) goto L_0x003c
            com.jcraft.jsch.Util.bzero(r0)     // Catch:{ all -> 0x01ee }
            r0 = 0
            goto L_0x003c
        L_0x01de:
            com.jcraft.jsch.JSchPartialAuthException r13 = new com.jcraft.jsch.JSchPartialAuthException     // Catch:{ all -> 0x01ee }
            java.lang.String r1 = com.jcraft.jsch.Util.byte2str(r1)     // Catch:{ all -> 0x01ee }
            r13.<init>(r1)     // Catch:{ all -> 0x01ee }
            throw r13     // Catch:{ all -> 0x01ee }
        L_0x01e8:
            if (r0 == 0) goto L_0x01ed
            com.jcraft.jsch.Util.bzero(r0)
        L_0x01ed:
            return r8
        L_0x01ee:
            r13 = move-exception
            if (r0 == 0) goto L_0x01f4
            com.jcraft.jsch.Util.bzero(r0)
        L_0x01f4:
            throw r13
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.UserAuthPassword.start(com.jcraft.jsch.Session):boolean");
    }
}
