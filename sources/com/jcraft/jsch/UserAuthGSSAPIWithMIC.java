package com.jcraft.jsch;

public class UserAuthGSSAPIWithMIC extends UserAuth {
    private static final int SSH_MSG_USERAUTH_GSSAPI_ERROR = 64;
    private static final int SSH_MSG_USERAUTH_GSSAPI_ERRTOK = 65;
    private static final int SSH_MSG_USERAUTH_GSSAPI_EXCHANGE_COMPLETE = 63;
    private static final int SSH_MSG_USERAUTH_GSSAPI_MIC = 66;
    private static final int SSH_MSG_USERAUTH_GSSAPI_RESPONSE = 60;
    private static final int SSH_MSG_USERAUTH_GSSAPI_TOKEN = 61;
    private static final String[] supported_method = {"gssapi-with-mic.krb5"};
    private static final byte[][] supported_oid = {new byte[]{6, 9, 42, -122, 72, -122, -9, 18, 1, 2, 2}};

    public boolean start(Session session) throws Exception {
        super.start(session);
        byte[] str2byte = Util.str2byte(this.username);
        this.packet.reset();
        this.buf.putByte((byte) 50);
        this.buf.putString(str2byte);
        this.buf.putString(Util.str2byte("ssh-connection"));
        this.buf.putString(Util.str2byte("gssapi-with-mic"));
        this.buf.putInt(supported_oid.length);
        for (byte[] putString : supported_oid) {
            this.buf.putString(putString);
        }
        session.write(this.packet);
        String str = null;
        while (true) {
            this.buf = session.read(this.buf);
            byte command = this.buf.getCommand() & 255;
            if (command == 51) {
                return false;
            }
            if (command == SSH_MSG_USERAUTH_GSSAPI_RESPONSE) {
                this.buf.getInt();
                this.buf.getByte();
                this.buf.getByte();
                byte[] string = this.buf.getString();
                int i = 0;
                while (true) {
                    byte[][] bArr = supported_oid;
                    if (i >= bArr.length) {
                        break;
                    } else if (Util.array_equals(string, bArr[i])) {
                        str = supported_method[i];
                        break;
                    } else {
                        i++;
                    }
                }
                if (str == null) {
                    return false;
                }
                try {
                    GSSContext gSSContext = (GSSContext) Class.forName(session.getConfig(str)).newInstance();
                    try {
                        gSSContext.create(this.username, session.host);
                        byte[] bArr2 = new byte[0];
                        while (!gSSContext.isEstablished()) {
                            try {
                                bArr2 = gSSContext.init(bArr2, 0, bArr2.length);
                                if (bArr2 != null) {
                                    this.packet.reset();
                                    this.buf.putByte((byte) 61);
                                    this.buf.putString(bArr2);
                                    session.write(this.packet);
                                }
                                if (!gSSContext.isEstablished()) {
                                    this.buf = session.read(this.buf);
                                    byte command2 = this.buf.getCommand() & 255;
                                    if (command2 == 64) {
                                        this.buf = session.read(this.buf);
                                        command2 = this.buf.getCommand() & 255;
                                    } else if (command2 == SSH_MSG_USERAUTH_GSSAPI_ERRTOK) {
                                        this.buf = session.read(this.buf);
                                        command2 = this.buf.getCommand() & 255;
                                    }
                                    if (command2 == 51) {
                                        return false;
                                    }
                                    this.buf.getInt();
                                    this.buf.getByte();
                                    this.buf.getByte();
                                    bArr2 = this.buf.getString();
                                }
                            } catch (JSchException unused) {
                                return false;
                            }
                        }
                        Buffer buffer = new Buffer();
                        buffer.putString(session.getSessionId());
                        buffer.putByte((byte) 50);
                        buffer.putString(str2byte);
                        buffer.putString(Util.str2byte("ssh-connection"));
                        buffer.putString(Util.str2byte("gssapi-with-mic"));
                        byte[] mic = gSSContext.getMIC(buffer.buffer, 0, buffer.getLength());
                        if (mic == null) {
                            return false;
                        }
                        this.packet.reset();
                        this.buf.putByte((byte) 66);
                        this.buf.putString(mic);
                        session.write(this.packet);
                        gSSContext.dispose();
                        this.buf = session.read(this.buf);
                        byte command3 = this.buf.getCommand() & 255;
                        if (command3 == 52) {
                            return true;
                        }
                        if (command3 == 51) {
                            this.buf.getInt();
                            this.buf.getByte();
                            this.buf.getByte();
                            byte[] string2 = this.buf.getString();
                            if (this.buf.getByte() != 0) {
                                throw new JSchPartialAuthException(Util.byte2str(string2));
                            }
                        }
                        return false;
                    } catch (JSchException unused2) {
                        return false;
                    }
                } catch (Exception unused3) {
                    return false;
                }
            } else if (command != 53) {
                return false;
            } else {
                this.buf.getInt();
                this.buf.getByte();
                this.buf.getByte();
                byte[] string3 = this.buf.getString();
                this.buf.getString();
                String byte2str = Util.byte2str(string3);
                if (this.userinfo != null) {
                    this.userinfo.showMessage(byte2str);
                }
            }
        }
    }
}
