package com.jcraft.jsch;

public class RequestSftp extends Request {
    RequestSftp() {
        setReply(true);
    }

    public void request(Session session, Channel channel) throws Exception {
        super.request(session, channel);
        Buffer buffer = new Buffer();
        Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte) 98);
        buffer.putInt(channel.getRecipient());
        buffer.putString(Util.str2byte("subsystem"));
        buffer.putByte(waitForReply() ? (byte) 1 : 0);
        buffer.putString(Util.str2byte("sftp"));
        write(packet);
    }
}
