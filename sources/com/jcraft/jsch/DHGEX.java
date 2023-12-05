package com.jcraft.jsch;

import android.support.v4.view.InputDeviceCompat;
import java.io.PrintStream;

public class DHGEX extends KeyExchange {
    private static final int SSH_MSG_KEX_DH_GEX_GROUP = 31;
    private static final int SSH_MSG_KEX_DH_GEX_INIT = 32;
    private static final int SSH_MSG_KEX_DH_GEX_REPLY = 33;
    private static final int SSH_MSG_KEX_DH_GEX_REQUEST = 34;
    static int min = 1024;
    static int preferred = 1024;
    byte[] I_C;
    byte[] I_S;
    byte[] V_C;
    byte[] V_S;
    private Buffer buf;
    DH dh;
    private byte[] e;
    private byte[] g;
    protected String hash = "sha-1";
    int max = 1024;
    private byte[] p;
    private Packet packet;
    private int state;

    public void init(Session session, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) throws Exception {
        this.session = session;
        this.V_S = bArr;
        this.V_C = bArr2;
        this.I_S = bArr3;
        this.I_C = bArr4;
        try {
            this.sha = (HASH) Class.forName(session.getConfig(this.hash)).newInstance();
            this.sha.init();
        } catch (Exception e2) {
            System.err.println(e2);
        }
        this.buf = new Buffer();
        this.packet = new Packet(this.buf);
        try {
            Class<?> cls = Class.forName(session.getConfig("dh"));
            int check2048 = check2048(cls, this.max);
            this.max = check2048;
            preferred = check2048;
            this.dh = (DH) cls.newInstance();
            this.dh.init();
            this.packet.reset();
            this.buf.putByte((byte) 34);
            this.buf.putInt(min);
            this.buf.putInt(preferred);
            this.buf.putInt(this.max);
            session.write(this.packet);
            if (JSch.getLogger().isEnabled(1)) {
                Logger logger = JSch.getLogger();
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("SSH_MSG_KEX_DH_GEX_REQUEST(");
                stringBuffer.append(min);
                stringBuffer.append("<");
                stringBuffer.append(preferred);
                stringBuffer.append("<");
                stringBuffer.append(this.max);
                stringBuffer.append(") sent");
                logger.log(1, stringBuffer.toString());
                JSch.getLogger().log(1, "expecting SSH_MSG_KEX_DH_GEX_GROUP");
            }
            this.state = SSH_MSG_KEX_DH_GEX_GROUP;
        } catch (Exception e3) {
            throw e3;
        }
    }

    public boolean next(Buffer buffer) throws Exception {
        int i = this.state;
        if (i == SSH_MSG_KEX_DH_GEX_GROUP) {
            buffer.getInt();
            buffer.getByte();
            int i2 = buffer.getByte();
            if (i2 != SSH_MSG_KEX_DH_GEX_GROUP) {
                PrintStream printStream = System.err;
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("type: must be SSH_MSG_KEX_DH_GEX_GROUP ");
                stringBuffer.append(i2);
                printStream.println(stringBuffer.toString());
                return false;
            }
            this.p = buffer.getMPInt();
            this.g = buffer.getMPInt();
            this.dh.setP(this.p);
            this.dh.setG(this.g);
            this.e = this.dh.getE();
            this.packet.reset();
            this.buf.putByte((byte) 32);
            this.buf.putMPInt(this.e);
            this.session.write(this.packet);
            if (JSch.getLogger().isEnabled(1)) {
                JSch.getLogger().log(1, "SSH_MSG_KEX_DH_GEX_INIT sent");
                JSch.getLogger().log(1, "expecting SSH_MSG_KEX_DH_GEX_REPLY");
            }
            this.state = 33;
            return true;
        } else if (i != 33) {
            return false;
        } else {
            buffer.getInt();
            buffer.getByte();
            int i3 = buffer.getByte();
            if (i3 != 33) {
                PrintStream printStream2 = System.err;
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("type: must be SSH_MSG_KEX_DH_GEX_REPLY ");
                stringBuffer2.append(i3);
                printStream2.println(stringBuffer2.toString());
                return false;
            }
            this.K_S = buffer.getString();
            byte[] mPInt = buffer.getMPInt();
            byte[] string = buffer.getString();
            this.dh.setF(mPInt);
            this.dh.checkRange();
            this.K = normalize(this.dh.getK());
            this.buf.reset();
            this.buf.putString(this.V_C);
            this.buf.putString(this.V_S);
            this.buf.putString(this.I_C);
            this.buf.putString(this.I_S);
            this.buf.putString(this.K_S);
            this.buf.putInt(min);
            this.buf.putInt(preferred);
            this.buf.putInt(this.max);
            this.buf.putMPInt(this.p);
            this.buf.putMPInt(this.g);
            this.buf.putMPInt(this.e);
            this.buf.putMPInt(mPInt);
            this.buf.putMPInt(this.K);
            byte[] bArr = new byte[this.buf.getLength()];
            this.buf.getByte(bArr);
            this.sha.update(bArr, 0, bArr.length);
            this.H = this.sha.digest();
            byte b = ((this.K_S[0] << 24) & -16777216) | ((this.K_S[1] << 16) & 16711680) | ((this.K_S[2] << 8) & 65280) | (this.K_S[3] & 255);
            boolean verify = verify(Util.byte2str(this.K_S, 4, b), this.K_S, 4 + b, string);
            this.state = 0;
            return verify;
        }
    }

    public int getState() {
        return this.state;
    }

    /* access modifiers changed from: protected */
    public int check2048(Class cls, int i) throws Exception {
        DH dh2 = (DH) cls.newInstance();
        dh2.init();
        byte[] bArr = new byte[InputDeviceCompat.SOURCE_KEYBOARD];
        bArr[1] = -35;
        bArr[256] = 115;
        dh2.setP(bArr);
        dh2.setG(new byte[]{2});
        try {
            dh2.getE();
            return 2048;
        } catch (Exception unused) {
            return i;
        }
    }
}
