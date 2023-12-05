package com.jcraft.jsch;

class RequestSignal extends Request {
    private String signal = "KILL";

    RequestSignal() {
    }

    public void setSignal(String str) {
        this.signal = str;
    }

    public void request(Session session, Channel channel) throws Exception {
        super.request(session, channel);
        Buffer buffer = new Buffer();
        Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte) 98);
        buffer.putInt(channel.getRecipient());
        buffer.putString(Util.str2byte("signal"));
        buffer.putByte(waitForReply() ? (byte) 1 : 0);
        buffer.putString(Util.str2byte(this.signal));
        write(packet);
    }
}
