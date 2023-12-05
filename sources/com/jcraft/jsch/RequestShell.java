package com.jcraft.jsch;

class RequestShell extends Request {
    RequestShell() {
    }

    public void request(Session session, Channel channel) throws Exception {
        super.request(session, channel);
        Buffer buffer = new Buffer();
        Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte) 98);
        buffer.putInt(channel.getRecipient());
        buffer.putString(Util.str2byte("shell"));
        buffer.putByte(waitForReply() ? (byte) 1 : 0);
        write(packet);
    }
}
