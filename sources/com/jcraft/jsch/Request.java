package com.jcraft.jsch;

abstract class Request {
    private Channel channel = null;
    private boolean reply = false;
    private Session session = null;

    Request() {
    }

    /* access modifiers changed from: package-private */
    public void request(Session session2, Channel channel2) throws Exception {
        this.session = session2;
        this.channel = channel2;
        if (channel2.connectTimeout > 0) {
            setReply(true);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean waitForReply() {
        return this.reply;
    }

    /* access modifiers changed from: package-private */
    public void setReply(boolean z) {
        this.reply = z;
    }

    /* access modifiers changed from: package-private */
    public void write(Packet packet) throws Exception {
        if (this.reply) {
            this.channel.reply = -1;
        }
        this.session.write(packet);
        if (this.reply) {
            long currentTimeMillis = System.currentTimeMillis();
            long j = (long) this.channel.connectTimeout;
            while (this.channel.isConnected() && this.channel.reply == -1) {
                try {
                    Thread.sleep(10);
                } catch (Exception unused) {
                }
                if (j > 0 && System.currentTimeMillis() - currentTimeMillis > j) {
                    this.channel.reply = 0;
                    throw new JSchException("channel request: timeout");
                }
            }
            if (this.channel.reply == 0) {
                throw new JSchException("failed to send channel request");
            }
        }
    }
}
