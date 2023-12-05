package com.jcraft.jsch;

class UserAuthNone extends UserAuth {
    private static final int SSH_MSG_SERVICE_ACCEPT = 6;
    private String methods = null;

    UserAuthNone() {
    }

    public boolean start(Session session) throws Exception {
        super.start(session);
        this.packet.reset();
        this.buf.putByte((byte) 5);
        this.buf.putString(Util.str2byte("ssh-userauth"));
        session.write(this.packet);
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "SSH_MSG_SERVICE_REQUEST sent");
        }
        this.buf = session.read(this.buf);
        boolean z = this.buf.getCommand() == 6;
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "SSH_MSG_SERVICE_ACCEPT received");
        }
        if (!z) {
            return false;
        }
        byte[] str2byte = Util.str2byte(this.username);
        this.packet.reset();
        this.buf.putByte((byte) 50);
        this.buf.putString(str2byte);
        this.buf.putString(Util.str2byte("ssh-connection"));
        this.buf.putString(Util.str2byte("none"));
        session.write(this.packet);
        while (true) {
            this.buf = session.read(this.buf);
            byte command = this.buf.getCommand() & 255;
            if (command == 52) {
                return true;
            }
            if (command == 53) {
                this.buf.getInt();
                this.buf.getByte();
                this.buf.getByte();
                byte[] string = this.buf.getString();
                this.buf.getString();
                String byte2str = Util.byte2str(string);
                if (this.userinfo != null) {
                    try {
                        this.userinfo.showMessage(byte2str);
                    } catch (RuntimeException unused) {
                    }
                }
            } else if (command == 51) {
                this.buf.getInt();
                this.buf.getByte();
                this.buf.getByte();
                byte[] string2 = this.buf.getString();
                this.buf.getByte();
                this.methods = Util.byte2str(string2);
                return false;
            } else {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("USERAUTH fail (");
                stringBuffer.append(command);
                stringBuffer.append(")");
                throw new JSchException(stringBuffer.toString());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public String getMethods() {
        return this.methods;
    }
}
