package com.jcraft.jsch;

class UserAuthPublicKey extends UserAuth {
    UserAuthPublicKey() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:86:0x024e, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean start(com.jcraft.jsch.Session r17) throws java.lang.Exception {
        /*
            r16 = this;
            r1 = r16
            r0 = r17
            super.start(r17)
            com.jcraft.jsch.IdentityRepository r2 = r17.getIdentityRepository()
            java.util.Vector r2 = r2.getIdentities()
            monitor-enter(r2)
            int r3 = r2.size()     // Catch:{ all -> 0x024f }
            r4 = 0
            if (r3 > 0) goto L_0x0019
            monitor-exit(r2)     // Catch:{ all -> 0x024f }
            return r4
        L_0x0019:
            java.lang.String r3 = r1.username     // Catch:{ all -> 0x024f }
            byte[] r3 = com.jcraft.jsch.Util.str2byte(r3)     // Catch:{ all -> 0x024f }
            r5 = 0
        L_0x0020:
            int r6 = r2.size()     // Catch:{ all -> 0x024f }
            if (r5 >= r6) goto L_0x024d
            int r6 = r0.auth_failures     // Catch:{ all -> 0x024f }
            int r7 = r0.max_auth_tries     // Catch:{ all -> 0x024f }
            if (r6 < r7) goto L_0x002e
            monitor-exit(r2)     // Catch:{ all -> 0x024f }
            return r4
        L_0x002e:
            java.lang.Object r6 = r2.elementAt(r5)     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Identity r6 = (com.jcraft.jsch.Identity) r6     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Identity r6 = (com.jcraft.jsch.Identity) r6     // Catch:{ all -> 0x024f }
            byte[] r7 = r6.getPublicKeyBlob()     // Catch:{ all -> 0x024f }
            r8 = 53
            r9 = 51
            r10 = 50
            if (r7 == 0) goto L_0x00c9
            com.jcraft.jsch.Packet r11 = r1.packet     // Catch:{ all -> 0x024f }
            r11.reset()     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r11 = r1.buf     // Catch:{ all -> 0x024f }
            r11.putByte((byte) r10)     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r11 = r1.buf     // Catch:{ all -> 0x024f }
            r11.putString(r3)     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r11 = r1.buf     // Catch:{ all -> 0x024f }
            java.lang.String r12 = "ssh-connection"
            byte[] r12 = com.jcraft.jsch.Util.str2byte(r12)     // Catch:{ all -> 0x024f }
            r11.putString(r12)     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r11 = r1.buf     // Catch:{ all -> 0x024f }
            java.lang.String r12 = "publickey"
            byte[] r12 = com.jcraft.jsch.Util.str2byte(r12)     // Catch:{ all -> 0x024f }
            r11.putString(r12)     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r11 = r1.buf     // Catch:{ all -> 0x024f }
            r11.putByte((byte) r4)     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r11 = r1.buf     // Catch:{ all -> 0x024f }
            java.lang.String r12 = r6.getAlgName()     // Catch:{ all -> 0x024f }
            byte[] r12 = com.jcraft.jsch.Util.str2byte(r12)     // Catch:{ all -> 0x024f }
            r11.putString(r12)     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r11 = r1.buf     // Catch:{ all -> 0x024f }
            r11.putString(r7)     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Packet r11 = r1.packet     // Catch:{ all -> 0x024f }
            r0.write(r11)     // Catch:{ all -> 0x024f }
        L_0x0083:
            com.jcraft.jsch.Buffer r11 = r1.buf     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r11 = r0.read(r11)     // Catch:{ all -> 0x024f }
            r1.buf = r11     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r11 = r1.buf     // Catch:{ all -> 0x024f }
            byte r11 = r11.getCommand()     // Catch:{ all -> 0x024f }
            r11 = r11 & 255(0xff, float:3.57E-43)
            r12 = 60
            if (r11 != r12) goto L_0x0098
            goto L_0x00c5
        L_0x0098:
            if (r11 != r9) goto L_0x009b
            goto L_0x00c5
        L_0x009b:
            if (r11 != r8) goto L_0x00c5
            com.jcraft.jsch.Buffer r11 = r1.buf     // Catch:{ all -> 0x024f }
            r11.getInt()     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r11 = r1.buf     // Catch:{ all -> 0x024f }
            r11.getByte()     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r11 = r1.buf     // Catch:{ all -> 0x024f }
            r11.getByte()     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r11 = r1.buf     // Catch:{ all -> 0x024f }
            byte[] r11 = r11.getString()     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r12 = r1.buf     // Catch:{ all -> 0x024f }
            r12.getString()     // Catch:{ all -> 0x024f }
            java.lang.String r11 = com.jcraft.jsch.Util.byte2str(r11)     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.UserInfo r12 = r1.userinfo     // Catch:{ all -> 0x024f }
            if (r12 == 0) goto L_0x0083
            com.jcraft.jsch.UserInfo r12 = r1.userinfo     // Catch:{ all -> 0x024f }
            r12.showMessage(r11)     // Catch:{ all -> 0x024f }
            goto L_0x0083
        L_0x00c5:
            if (r11 == r12) goto L_0x00c9
            goto L_0x0249
        L_0x00c9:
            r11 = 5
            r12 = 5
        L_0x00cb:
            boolean r13 = r6.isEncrypted()     // Catch:{ all -> 0x024f }
            if (r13 == 0) goto L_0x0116
            com.jcraft.jsch.UserInfo r13 = r1.userinfo     // Catch:{ all -> 0x024f }
            if (r13 == 0) goto L_0x010e
            boolean r13 = r6.isEncrypted()     // Catch:{ all -> 0x024f }
            if (r13 == 0) goto L_0x0101
            com.jcraft.jsch.UserInfo r13 = r1.userinfo     // Catch:{ all -> 0x024f }
            java.lang.StringBuffer r15 = new java.lang.StringBuffer     // Catch:{ all -> 0x024f }
            r15.<init>()     // Catch:{ all -> 0x024f }
            java.lang.String r14 = "Passphrase for "
            r15.append(r14)     // Catch:{ all -> 0x024f }
            java.lang.String r14 = r6.getName()     // Catch:{ all -> 0x024f }
            r15.append(r14)     // Catch:{ all -> 0x024f }
            java.lang.String r14 = r15.toString()     // Catch:{ all -> 0x024f }
            boolean r13 = r13.promptPassphrase(r14)     // Catch:{ all -> 0x024f }
            if (r13 == 0) goto L_0x00f9
            goto L_0x0101
        L_0x00f9:
            com.jcraft.jsch.JSchAuthCancelException r0 = new com.jcraft.jsch.JSchAuthCancelException     // Catch:{ all -> 0x024f }
            java.lang.String r3 = "publickey"
            r0.<init>(r3)     // Catch:{ all -> 0x024f }
            throw r0     // Catch:{ all -> 0x024f }
        L_0x0101:
            com.jcraft.jsch.UserInfo r13 = r1.userinfo     // Catch:{ all -> 0x024f }
            java.lang.String r13 = r13.getPassphrase()     // Catch:{ all -> 0x024f }
            if (r13 == 0) goto L_0x0116
            byte[] r14 = com.jcraft.jsch.Util.str2byte(r13)     // Catch:{ all -> 0x024f }
            goto L_0x0117
        L_0x010e:
            com.jcraft.jsch.JSchException r0 = new com.jcraft.jsch.JSchException     // Catch:{ all -> 0x024f }
            java.lang.String r3 = "USERAUTH fail"
            r0.<init>(r3)     // Catch:{ all -> 0x024f }
            throw r0     // Catch:{ all -> 0x024f }
        L_0x0116:
            r14 = 0
        L_0x0117:
            boolean r13 = r6.isEncrypted()     // Catch:{ all -> 0x024f }
            if (r13 == 0) goto L_0x011f
            if (r14 == 0) goto L_0x0139
        L_0x011f:
            boolean r13 = r6.setPassphrase(r14)     // Catch:{ all -> 0x024f }
            if (r13 == 0) goto L_0x0139
            if (r14 == 0) goto L_0x0141
            com.jcraft.jsch.IdentityRepository r12 = r17.getIdentityRepository()     // Catch:{ all -> 0x024f }
            boolean r12 = r12 instanceof com.jcraft.jsch.IdentityRepository.Wrapper     // Catch:{ all -> 0x024f }
            if (r12 == 0) goto L_0x0141
            com.jcraft.jsch.IdentityRepository r12 = r17.getIdentityRepository()     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.IdentityRepository$Wrapper r12 = (com.jcraft.jsch.IdentityRepository.Wrapper) r12     // Catch:{ all -> 0x024f }
            r12.check()     // Catch:{ all -> 0x024f }
            goto L_0x0141
        L_0x0139:
            com.jcraft.jsch.Util.bzero(r14)     // Catch:{ all -> 0x024f }
            int r12 = r12 + -1
            if (r12 != 0) goto L_0x00cb
            r14 = 0
        L_0x0141:
            com.jcraft.jsch.Util.bzero(r14)     // Catch:{ all -> 0x024f }
            boolean r12 = r6.isEncrypted()     // Catch:{ all -> 0x024f }
            if (r12 == 0) goto L_0x014c
            goto L_0x0249
        L_0x014c:
            if (r7 != 0) goto L_0x0152
            byte[] r7 = r6.getPublicKeyBlob()     // Catch:{ all -> 0x024f }
        L_0x0152:
            if (r7 != 0) goto L_0x0156
            goto L_0x0249
        L_0x0156:
            com.jcraft.jsch.Packet r12 = r1.packet     // Catch:{ all -> 0x024f }
            r12.reset()     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r12 = r1.buf     // Catch:{ all -> 0x024f }
            r12.putByte((byte) r10)     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r10 = r1.buf     // Catch:{ all -> 0x024f }
            r10.putString(r3)     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r10 = r1.buf     // Catch:{ all -> 0x024f }
            java.lang.String r12 = "ssh-connection"
            byte[] r12 = com.jcraft.jsch.Util.str2byte(r12)     // Catch:{ all -> 0x024f }
            r10.putString(r12)     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r10 = r1.buf     // Catch:{ all -> 0x024f }
            java.lang.String r12 = "publickey"
            byte[] r12 = com.jcraft.jsch.Util.str2byte(r12)     // Catch:{ all -> 0x024f }
            r10.putString(r12)     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r10 = r1.buf     // Catch:{ all -> 0x024f }
            r12 = 1
            r10.putByte((byte) r12)     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r10 = r1.buf     // Catch:{ all -> 0x024f }
            java.lang.String r13 = r6.getAlgName()     // Catch:{ all -> 0x024f }
            byte[] r13 = com.jcraft.jsch.Util.str2byte(r13)     // Catch:{ all -> 0x024f }
            r10.putString(r13)     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r10 = r1.buf     // Catch:{ all -> 0x024f }
            r10.putString(r7)     // Catch:{ all -> 0x024f }
            byte[] r7 = r17.getSessionId()     // Catch:{ all -> 0x024f }
            int r10 = r7.length     // Catch:{ all -> 0x024f }
            int r13 = r10 + 4
            com.jcraft.jsch.Buffer r14 = r1.buf     // Catch:{ all -> 0x024f }
            int r14 = r14.index     // Catch:{ all -> 0x024f }
            int r14 = r14 + r13
            int r14 = r14 - r11
            byte[] r14 = new byte[r14]     // Catch:{ all -> 0x024f }
            int r15 = r10 >>> 24
            byte r15 = (byte) r15     // Catch:{ all -> 0x024f }
            r14[r4] = r15     // Catch:{ all -> 0x024f }
            int r15 = r10 >>> 16
            byte r15 = (byte) r15     // Catch:{ all -> 0x024f }
            r14[r12] = r15     // Catch:{ all -> 0x024f }
            r15 = 2
            int r9 = r10 >>> 8
            byte r9 = (byte) r9     // Catch:{ all -> 0x024f }
            r14[r15] = r9     // Catch:{ all -> 0x024f }
            r9 = 3
            byte r15 = (byte) r10     // Catch:{ all -> 0x024f }
            r14[r9] = r15     // Catch:{ all -> 0x024f }
            r9 = 4
            java.lang.System.arraycopy(r7, r4, r14, r9, r10)     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r7 = r1.buf     // Catch:{ all -> 0x024f }
            byte[] r7 = r7.buffer     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r9 = r1.buf     // Catch:{ all -> 0x024f }
            int r9 = r9.index     // Catch:{ all -> 0x024f }
            int r9 = r9 - r11
            java.lang.System.arraycopy(r7, r11, r14, r13, r9)     // Catch:{ all -> 0x024f }
            byte[] r6 = r6.getSignature(r14)     // Catch:{ all -> 0x024f }
            if (r6 != 0) goto L_0x01ce
            goto L_0x024d
        L_0x01ce:
            com.jcraft.jsch.Buffer r7 = r1.buf     // Catch:{ all -> 0x024f }
            r7.putString(r6)     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Packet r6 = r1.packet     // Catch:{ all -> 0x024f }
            r0.write(r6)     // Catch:{ all -> 0x024f }
        L_0x01d8:
            com.jcraft.jsch.Buffer r6 = r1.buf     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r6 = r0.read(r6)     // Catch:{ all -> 0x024f }
            r1.buf = r6     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r6 = r1.buf     // Catch:{ all -> 0x024f }
            byte r6 = r6.getCommand()     // Catch:{ all -> 0x024f }
            r6 = r6 & 255(0xff, float:3.57E-43)
            r7 = 52
            if (r6 != r7) goto L_0x01ee
            monitor-exit(r2)     // Catch:{ all -> 0x024f }
            return r12
        L_0x01ee:
            if (r6 != r8) goto L_0x0218
            com.jcraft.jsch.Buffer r6 = r1.buf     // Catch:{ all -> 0x024f }
            r6.getInt()     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r6 = r1.buf     // Catch:{ all -> 0x024f }
            r6.getByte()     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r6 = r1.buf     // Catch:{ all -> 0x024f }
            r6.getByte()     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r6 = r1.buf     // Catch:{ all -> 0x024f }
            byte[] r6 = r6.getString()     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r7 = r1.buf     // Catch:{ all -> 0x024f }
            r7.getString()     // Catch:{ all -> 0x024f }
            java.lang.String r6 = com.jcraft.jsch.Util.byte2str(r6)     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.UserInfo r7 = r1.userinfo     // Catch:{ all -> 0x024f }
            if (r7 == 0) goto L_0x01d8
            com.jcraft.jsch.UserInfo r7 = r1.userinfo     // Catch:{ all -> 0x024f }
            r7.showMessage(r6)     // Catch:{ all -> 0x024f }
            goto L_0x01d8
        L_0x0218:
            r9 = 51
            if (r6 != r9) goto L_0x0249
            com.jcraft.jsch.Buffer r6 = r1.buf     // Catch:{ all -> 0x024f }
            r6.getInt()     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r6 = r1.buf     // Catch:{ all -> 0x024f }
            r6.getByte()     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r6 = r1.buf     // Catch:{ all -> 0x024f }
            r6.getByte()     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r6 = r1.buf     // Catch:{ all -> 0x024f }
            byte[] r6 = r6.getString()     // Catch:{ all -> 0x024f }
            com.jcraft.jsch.Buffer r7 = r1.buf     // Catch:{ all -> 0x024f }
            int r7 = r7.getByte()     // Catch:{ all -> 0x024f }
            if (r7 != 0) goto L_0x023f
            int r6 = r0.auth_failures     // Catch:{ all -> 0x024f }
            int r6 = r6 + r12
            r0.auth_failures = r6     // Catch:{ all -> 0x024f }
            goto L_0x0249
        L_0x023f:
            com.jcraft.jsch.JSchPartialAuthException r0 = new com.jcraft.jsch.JSchPartialAuthException     // Catch:{ all -> 0x024f }
            java.lang.String r3 = com.jcraft.jsch.Util.byte2str(r6)     // Catch:{ all -> 0x024f }
            r0.<init>(r3)     // Catch:{ all -> 0x024f }
            throw r0     // Catch:{ all -> 0x024f }
        L_0x0249:
            int r5 = r5 + 1
            goto L_0x0020
        L_0x024d:
            monitor-exit(r2)     // Catch:{ all -> 0x024f }
            return r4
        L_0x024f:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x024f }
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.UserAuthPublicKey.start(com.jcraft.jsch.Session):boolean");
    }
}
