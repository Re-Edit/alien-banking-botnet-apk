package com.jcraft.jsch;

public class ChannelShell extends ChannelSession {
    ChannelShell() {
        this.pty = true;
    }

    public void start() throws JSchException {
        Session session = getSession();
        try {
            sendRequests();
            new RequestShell().request(session, this);
            if (this.io.in != null) {
                this.thread = new Thread(this);
                Thread thread = this.thread;
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Shell for ");
                stringBuffer.append(session.host);
                thread.setName(stringBuffer.toString());
                if (session.daemon_thread) {
                    this.thread.setDaemon(session.daemon_thread);
                }
                this.thread.start();
            }
        } catch (Exception e) {
            if (e instanceof JSchException) {
                throw ((JSchException) e);
            } else if (e instanceof Throwable) {
                throw new JSchException("ChannelShell", e);
            } else {
                throw new JSchException("ChannelShell");
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void init() throws JSchException {
        this.io.setInputStream(getSession().in);
        this.io.setOutputStream(getSession().out);
    }
}
