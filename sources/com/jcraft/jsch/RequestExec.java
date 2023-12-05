package com.jcraft.jsch;

class RequestExec extends Request {
    private byte[] command = new byte[0];

    RequestExec(byte[] bArr) {
        this.command = bArr;
    }

    public void request(Session session, Channel channel) throws Exception {
        super.request(session, channel);
        Buffer buffer = new Buffer();
        Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte) 98);
        buffer.putInt(channel.getRecipient());
        buffer.putString(Util.str2byte("exec"));
        buffer.putByte(waitForReply() ? (byte) 1 : 0);
        buffer.checkFreeSize(this.command.length + 4);
        buffer.putString(this.command);
        write(packet);
    }
}
