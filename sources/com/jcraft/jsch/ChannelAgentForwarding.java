package com.jcraft.jsch;

class ChannelAgentForwarding extends Channel {
    private static final int LOCAL_MAXIMUM_PACKET_SIZE = 16384;
    private static final int LOCAL_WINDOW_SIZE_MAX = 131072;
    private final byte SSH2_AGENTC_ADD_IDENTITY = 17;
    private final byte SSH2_AGENTC_REMOVE_ALL_IDENTITIES = 19;
    private final byte SSH2_AGENTC_REMOVE_IDENTITY = 18;
    private final byte SSH2_AGENTC_REQUEST_IDENTITIES = 11;
    private final byte SSH2_AGENTC_SIGN_REQUEST = 13;
    private final byte SSH2_AGENT_FAILURE = 30;
    private final byte SSH2_AGENT_IDENTITIES_ANSWER = 12;
    private final byte SSH2_AGENT_SIGN_RESPONSE = 14;
    private final byte SSH_AGENTC_ADD_RSA_IDENTITY = 7;
    private final byte SSH_AGENTC_REMOVE_ALL_RSA_IDENTITIES = 9;
    private final byte SSH_AGENTC_REMOVE_RSA_IDENTITY = 8;
    private final byte SSH_AGENTC_REQUEST_RSA_IDENTITIES = 1;
    private final byte SSH_AGENTC_RSA_CHALLENGE = 3;
    private final byte SSH_AGENT_FAILURE = 5;
    private final byte SSH_AGENT_RSA_IDENTITIES_ANSWER = 2;
    private final byte SSH_AGENT_RSA_RESPONSE = 4;
    private final byte SSH_AGENT_SUCCESS = 6;
    boolean init = true;
    private Buffer mbuf = null;
    private Packet packet = null;
    private Buffer rbuf = null;
    private Buffer wbuf = null;

    ChannelAgentForwarding() {
        setLocalWindowSizeMax(131072);
        setLocalWindowSize(131072);
        setLocalPacketSize(16384);
        this.type = Util.str2byte("auth-agent@openssh.com");
        this.rbuf = new Buffer();
        this.rbuf.reset();
        this.mbuf = new Buffer();
        this.connected = true;
    }

    public void run() {
        try {
            sendOpenConfirmation();
        } catch (Exception unused) {
            this.close = true;
            disconnect();
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Can't wrap try/catch for region: R(5:54|(1:(2:57|117)(1:(3:58|(2:60|(2:120|62)(2:63|(2:119|65)(3:66|67|68)))(0)|69)))(0)|70|71|(2:113|73)(1:118)) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:70:0x0153 */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x0159 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x015a A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void write(byte[] r7, int r8, int r9) throws java.io.IOException {
        /*
            r6 = this;
            com.jcraft.jsch.Packet r0 = r6.packet
            if (r0 != 0) goto L_0x0016
            com.jcraft.jsch.Buffer r0 = new com.jcraft.jsch.Buffer
            int r1 = r6.rmpsize
            r0.<init>((int) r1)
            r6.wbuf = r0
            com.jcraft.jsch.Packet r0 = new com.jcraft.jsch.Packet
            com.jcraft.jsch.Buffer r1 = r6.wbuf
            r0.<init>(r1)
            r6.packet = r0
        L_0x0016:
            com.jcraft.jsch.Buffer r0 = r6.rbuf
            r0.shift()
            com.jcraft.jsch.Buffer r0 = r6.rbuf
            byte[] r0 = r0.buffer
            int r0 = r0.length
            com.jcraft.jsch.Buffer r1 = r6.rbuf
            int r1 = r1.index
            int r1 = r1 + r9
            r2 = 0
            if (r0 >= r1) goto L_0x003f
            com.jcraft.jsch.Buffer r0 = r6.rbuf
            int r0 = r0.s
            int r0 = r0 + r9
            byte[] r0 = new byte[r0]
            com.jcraft.jsch.Buffer r1 = r6.rbuf
            byte[] r1 = r1.buffer
            com.jcraft.jsch.Buffer r3 = r6.rbuf
            byte[] r3 = r3.buffer
            int r3 = r3.length
            java.lang.System.arraycopy(r1, r2, r0, r2, r3)
            com.jcraft.jsch.Buffer r1 = r6.rbuf
            r1.buffer = r0
        L_0x003f:
            com.jcraft.jsch.Buffer r0 = r6.rbuf
            r0.putByte(r7, r8, r9)
            com.jcraft.jsch.Buffer r7 = r6.rbuf
            int r7 = r7.getInt()
            com.jcraft.jsch.Buffer r8 = r6.rbuf
            int r8 = r8.getLength()
            if (r7 <= r8) goto L_0x005b
            com.jcraft.jsch.Buffer r7 = r6.rbuf
            int r8 = r7.s
            int r8 = r8 + -4
            r7.s = r8
            return
        L_0x005b:
            com.jcraft.jsch.Buffer r7 = r6.rbuf
            int r7 = r7.getByte()
            com.jcraft.jsch.Session r8 = r6.getSession()     // Catch:{ JSchException -> 0x01ea }
            com.jcraft.jsch.IdentityRepository r9 = r8.getIdentityRepository()
            com.jcraft.jsch.UserInfo r8 = r8.getUserInfo()
            com.jcraft.jsch.Buffer r0 = r6.mbuf
            r0.reset()
            r0 = 11
            if (r7 != r0) goto L_0x00cc
            com.jcraft.jsch.Buffer r7 = r6.mbuf
            r8 = 12
            r7.putByte((byte) r8)
            java.util.Vector r0 = r9.getIdentities()
            monitor-enter(r0)
            r7 = 0
            r8 = 0
        L_0x0084:
            int r9 = r0.size()     // Catch:{ all -> 0x00c9 }
            if (r7 >= r9) goto L_0x009d
            java.lang.Object r9 = r0.elementAt(r7)     // Catch:{ all -> 0x00c9 }
            com.jcraft.jsch.Identity r9 = (com.jcraft.jsch.Identity) r9     // Catch:{ all -> 0x00c9 }
            com.jcraft.jsch.Identity r9 = (com.jcraft.jsch.Identity) r9     // Catch:{ all -> 0x00c9 }
            byte[] r9 = r9.getPublicKeyBlob()     // Catch:{ all -> 0x00c9 }
            if (r9 == 0) goto L_0x009a
            int r8 = r8 + 1
        L_0x009a:
            int r7 = r7 + 1
            goto L_0x0084
        L_0x009d:
            com.jcraft.jsch.Buffer r7 = r6.mbuf     // Catch:{ all -> 0x00c9 }
            r7.putInt(r8)     // Catch:{ all -> 0x00c9 }
        L_0x00a2:
            int r7 = r0.size()     // Catch:{ all -> 0x00c9 }
            if (r2 >= r7) goto L_0x00c6
            java.lang.Object r7 = r0.elementAt(r2)     // Catch:{ all -> 0x00c9 }
            com.jcraft.jsch.Identity r7 = (com.jcraft.jsch.Identity) r7     // Catch:{ all -> 0x00c9 }
            com.jcraft.jsch.Identity r7 = (com.jcraft.jsch.Identity) r7     // Catch:{ all -> 0x00c9 }
            byte[] r7 = r7.getPublicKeyBlob()     // Catch:{ all -> 0x00c9 }
            if (r7 != 0) goto L_0x00b7
            goto L_0x00c3
        L_0x00b7:
            com.jcraft.jsch.Buffer r8 = r6.mbuf     // Catch:{ all -> 0x00c9 }
            r8.putString(r7)     // Catch:{ all -> 0x00c9 }
            com.jcraft.jsch.Buffer r7 = r6.mbuf     // Catch:{ all -> 0x00c9 }
            byte[] r8 = com.jcraft.jsch.Util.empty     // Catch:{ all -> 0x00c9 }
            r7.putString(r8)     // Catch:{ all -> 0x00c9 }
        L_0x00c3:
            int r2 = r2 + 1
            goto L_0x00a2
        L_0x00c6:
            monitor-exit(r0)     // Catch:{ all -> 0x00c9 }
            goto L_0x01d9
        L_0x00c9:
            r7 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00c9 }
            throw r7
        L_0x00cc:
            r0 = 1
            if (r7 != r0) goto L_0x00dc
            com.jcraft.jsch.Buffer r7 = r6.mbuf
            r8 = 2
            r7.putByte((byte) r8)
            com.jcraft.jsch.Buffer r7 = r6.mbuf
            r7.putInt(r2)
            goto L_0x01d9
        L_0x00dc:
            r1 = 13
            if (r7 != r1) goto L_0x017f
            com.jcraft.jsch.Buffer r7 = r6.rbuf
            byte[] r7 = r7.getString()
            com.jcraft.jsch.Buffer r0 = r6.rbuf
            byte[] r0 = r0.getString()
            com.jcraft.jsch.Buffer r1 = r6.rbuf
            r1.getInt()
            java.util.Vector r1 = r9.getIdentities()
            monitor-enter(r1)
        L_0x00f6:
            int r9 = r1.size()     // Catch:{ all -> 0x017c }
            r3 = 0
            if (r2 >= r9) goto L_0x015d
            java.lang.Object r9 = r1.elementAt(r2)     // Catch:{ all -> 0x017c }
            com.jcraft.jsch.Identity r9 = (com.jcraft.jsch.Identity) r9     // Catch:{ all -> 0x017c }
            com.jcraft.jsch.Identity r9 = (com.jcraft.jsch.Identity) r9     // Catch:{ all -> 0x017c }
            byte[] r4 = r9.getPublicKeyBlob()     // Catch:{ all -> 0x017c }
            if (r4 != 0) goto L_0x010c
            goto L_0x015a
        L_0x010c:
            byte[] r4 = r9.getPublicKeyBlob()     // Catch:{ all -> 0x017c }
            boolean r4 = com.jcraft.jsch.Util.array_equals(r7, r4)     // Catch:{ all -> 0x017c }
            if (r4 != 0) goto L_0x0117
            goto L_0x015a
        L_0x0117:
            boolean r4 = r9.isEncrypted()     // Catch:{ all -> 0x017c }
            if (r4 == 0) goto L_0x0153
            if (r8 != 0) goto L_0x0120
            goto L_0x015a
        L_0x0120:
            boolean r4 = r9.isEncrypted()     // Catch:{ all -> 0x017c }
            if (r4 == 0) goto L_0x0153
            java.lang.StringBuffer r4 = new java.lang.StringBuffer     // Catch:{ all -> 0x017c }
            r4.<init>()     // Catch:{ all -> 0x017c }
            java.lang.String r5 = "Passphrase for "
            r4.append(r5)     // Catch:{ all -> 0x017c }
            java.lang.String r5 = r9.getName()     // Catch:{ all -> 0x017c }
            r4.append(r5)     // Catch:{ all -> 0x017c }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x017c }
            boolean r4 = r8.promptPassphrase(r4)     // Catch:{ all -> 0x017c }
            if (r4 != 0) goto L_0x0142
            goto L_0x0153
        L_0x0142:
            java.lang.String r4 = r8.getPassphrase()     // Catch:{ all -> 0x017c }
            if (r4 != 0) goto L_0x0149
            goto L_0x0153
        L_0x0149:
            byte[] r4 = com.jcraft.jsch.Util.str2byte(r4)     // Catch:{ all -> 0x017c }
            boolean r4 = r9.setPassphrase(r4)     // Catch:{ JSchException -> 0x0153 }
            if (r4 == 0) goto L_0x0120
        L_0x0153:
            boolean r4 = r9.isEncrypted()     // Catch:{ all -> 0x017c }
            if (r4 != 0) goto L_0x015a
            goto L_0x015e
        L_0x015a:
            int r2 = r2 + 1
            goto L_0x00f6
        L_0x015d:
            r9 = r3
        L_0x015e:
            monitor-exit(r1)     // Catch:{ all -> 0x017c }
            if (r9 == 0) goto L_0x0165
            byte[] r3 = r9.getSignature(r0)
        L_0x0165:
            if (r3 != 0) goto L_0x016f
            com.jcraft.jsch.Buffer r7 = r6.mbuf
            r8 = 30
            r7.putByte((byte) r8)
            goto L_0x01d9
        L_0x016f:
            com.jcraft.jsch.Buffer r7 = r6.mbuf
            r8 = 14
            r7.putByte((byte) r8)
            com.jcraft.jsch.Buffer r7 = r6.mbuf
            r7.putString(r3)
            goto L_0x01d9
        L_0x017c:
            r7 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x017c }
            throw r7
        L_0x017f:
            r8 = 18
            r1 = 6
            if (r7 != r8) goto L_0x0193
            com.jcraft.jsch.Buffer r7 = r6.rbuf
            byte[] r7 = r7.getString()
            r9.remove(r7)
            com.jcraft.jsch.Buffer r7 = r6.mbuf
            r7.putByte((byte) r1)
            goto L_0x01d9
        L_0x0193:
            r8 = 9
            if (r7 != r8) goto L_0x019d
            com.jcraft.jsch.Buffer r7 = r6.mbuf
            r7.putByte((byte) r1)
            goto L_0x01d9
        L_0x019d:
            r8 = 19
            if (r7 != r8) goto L_0x01aa
            r9.removeAll()
            com.jcraft.jsch.Buffer r7 = r6.mbuf
            r7.putByte((byte) r1)
            goto L_0x01d9
        L_0x01aa:
            r8 = 17
            r2 = 5
            if (r7 != r8) goto L_0x01ca
            com.jcraft.jsch.Buffer r7 = r6.rbuf
            int r7 = r7.getLength()
            byte[] r7 = new byte[r7]
            com.jcraft.jsch.Buffer r8 = r6.rbuf
            r8.getByte((byte[]) r7)
            boolean r7 = r9.add(r7)
            com.jcraft.jsch.Buffer r8 = r6.mbuf
            if (r7 == 0) goto L_0x01c5
            goto L_0x01c6
        L_0x01c5:
            r1 = 5
        L_0x01c6:
            r8.putByte((byte) r1)
            goto L_0x01d9
        L_0x01ca:
            com.jcraft.jsch.Buffer r7 = r6.rbuf
            int r8 = r7.getLength()
            int r8 = r8 - r0
            r7.skip(r8)
            com.jcraft.jsch.Buffer r7 = r6.mbuf
            r7.putByte((byte) r2)
        L_0x01d9:
            com.jcraft.jsch.Buffer r7 = r6.mbuf
            int r7 = r7.getLength()
            byte[] r7 = new byte[r7]
            com.jcraft.jsch.Buffer r8 = r6.mbuf
            r8.getByte((byte[]) r7)
            r6.send(r7)
            return
        L_0x01ea:
            r7 = move-exception
            java.io.IOException r8 = new java.io.IOException
            java.lang.String r7 = r7.toString()
            r8.<init>(r7)
            throw r8
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.ChannelAgentForwarding.write(byte[], int, int):void");
    }

    private void send(byte[] bArr) {
        this.packet.reset();
        this.wbuf.putByte((byte) 94);
        this.wbuf.putInt(this.recipient);
        this.wbuf.putInt(bArr.length + 4);
        this.wbuf.putString(bArr);
        try {
            getSession().write(this.packet, this, bArr.length + 4);
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: package-private */
    public void eof_remote() {
        super.eof_remote();
        eof();
    }
}
