package com.jcraft.jsch;

class RequestAgentForwarding extends Request {
    RequestAgentForwarding() {
    }

    public void request(Session session, Channel channel) throws Exception {
        super.request(session, channel);
        setReply(false);
        Buffer buffer = new Buffer();
        Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte) 98);
        buffer.putInt(channel.getRecipient());
        buffer.putString(Util.str2byte("auth-agent-req@openssh.com"));
        buffer.putByte(waitForReply() ? (byte) 1 : 0);
        write(packet);
        session.agent_forwarding = true;
    }
}
