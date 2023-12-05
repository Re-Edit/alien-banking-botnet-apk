package com.jcraft.jsch;

class RequestPtyReq extends Request {
    private int tcol = 80;
    private byte[] terminal_mode = Util.empty;
    private int thp = 480;
    private int trow = 24;
    private String ttype = "vt100";
    private int twp = 640;

    /* access modifiers changed from: package-private */
    public void setCode(String str) {
    }

    RequestPtyReq() {
    }

    /* access modifiers changed from: package-private */
    public void setTType(String str) {
        this.ttype = str;
    }

    /* access modifiers changed from: package-private */
    public void setTerminalMode(byte[] bArr) {
        this.terminal_mode = bArr;
    }

    /* access modifiers changed from: package-private */
    public void setTSize(int i, int i2, int i3, int i4) {
        this.tcol = i;
        this.trow = i2;
        this.twp = i3;
        this.thp = i4;
    }

    public void request(Session session, Channel channel) throws Exception {
        super.request(session, channel);
        Buffer buffer = new Buffer();
        Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte) 98);
        buffer.putInt(channel.getRecipient());
        buffer.putString(Util.str2byte("pty-req"));
        buffer.putByte(waitForReply() ? (byte) 1 : 0);
        buffer.putString(Util.str2byte(this.ttype));
        buffer.putInt(this.tcol);
        buffer.putInt(this.trow);
        buffer.putInt(this.twp);
        buffer.putInt(this.thp);
        buffer.putString(this.terminal_mode);
        write(packet);
    }
}
