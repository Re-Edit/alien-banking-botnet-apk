package com.jcraft.jsch;

import java.io.PrintStream;

public abstract class DHECN extends KeyExchange {
    private static final int SSH_MSG_KEX_ECDH_INIT = 30;
    private static final int SSH_MSG_KEX_ECDH_REPLY = 31;
    byte[] I_C;
    byte[] I_S;
    byte[] Q_C;
    byte[] V_C;
    byte[] V_S;
    private Buffer buf;
    byte[] e;
    private ECDH ecdh;
    protected int key_size;
    private Packet packet;
    protected String sha_name;
    private int state;

    public void init(Session session, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) throws Exception {
        this.session = session;
        this.V_S = bArr;
        this.V_C = bArr2;
        this.I_S = bArr3;
        this.I_C = bArr4;
        try {
            this.sha = (HASH) Class.forName(session.getConfig(this.sha_name)).newInstance();
            this.sha.init();
        } catch (Exception e2) {
            System.err.println(e2);
        }
        this.buf = new Buffer();
        this.packet = new Packet(this.buf);
        this.packet.reset();
        this.buf.putByte((byte) 30);
        try {
            this.ecdh = (ECDH) Class.forName(session.getConfig("ecdh-sha2-nistp")).newInstance();
            this.ecdh.init(this.key_size);
            this.Q_C = this.ecdh.getQ();
            this.buf.putString(this.Q_C);
            if (bArr != null) {
                session.write(this.packet);
                if (JSch.getLogger().isEnabled(1)) {
                    JSch.getLogger().log(1, "SSH_MSG_KEX_ECDH_INIT sent");
                    JSch.getLogger().log(1, "expecting SSH_MSG_KEX_ECDH_REPLY");
                }
                this.state = SSH_MSG_KEX_ECDH_REPLY;
            }
        } catch (Exception e3) {
            if (e3 instanceof Throwable) {
                throw new JSchException(e3.toString(), e3);
            }
            throw new JSchException(e3.toString());
        }
    }

    public boolean next(Buffer buffer) throws Exception {
        if (this.state != SSH_MSG_KEX_ECDH_REPLY) {
            return false;
        }
        buffer.getInt();
        buffer.getByte();
        int i = buffer.getByte();
        if (i != SSH_MSG_KEX_ECDH_REPLY) {
            PrintStream printStream = System.err;
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("type: must be 31 ");
            stringBuffer.append(i);
            printStream.println(stringBuffer.toString());
            return false;
        }
        this.K_S = buffer.getString();
        byte[] string = buffer.getString();
        byte[][] fromPoint = KeyPairECDSA.fromPoint(string);
        if (!this.ecdh.validate(fromPoint[0], fromPoint[1])) {
            return false;
        }
        this.K = this.ecdh.getSecret(fromPoint[0], fromPoint[1]);
        this.K = normalize(this.K);
        byte[] string2 = buffer.getString();
        this.buf.reset();
        this.buf.putString(this.V_C);
        this.buf.putString(this.V_S);
        this.buf.putString(this.I_C);
        this.buf.putString(this.I_S);
        this.buf.putString(this.K_S);
        this.buf.putString(this.Q_C);
        this.buf.putString(string);
        this.buf.putMPInt(this.K);
        byte[] bArr = new byte[this.buf.getLength()];
        this.buf.getByte(bArr);
        this.sha.update(bArr, 0, bArr.length);
        this.H = this.sha.digest();
        byte b = ((this.K_S[0] << 24) & -16777216) | ((this.K_S[1] << 16) & 16711680) | ((this.K_S[2] << 8) & 65280) | (this.K_S[3] & 255);
        boolean verify = verify(Util.byte2str(this.K_S, 4, b), this.K_S, 4 + b, string2);
        this.state = 0;
        return verify;
    }

    public int getState() {
        return this.state;
    }
}
