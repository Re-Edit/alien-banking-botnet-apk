package com.jcraft.jsch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ChannelSubsystem extends ChannelSession {
    boolean pty = false;
    String subsystem = "";
    boolean want_reply = true;
    boolean xforwading = false;

    public void setXForwarding(boolean z) {
        this.xforwading = z;
    }

    public void setPty(boolean z) {
        this.pty = z;
    }

    public void setWantReply(boolean z) {
        this.want_reply = z;
    }

    public void setSubsystem(String str) {
        this.subsystem = str;
    }

    public void start() throws JSchException {
        Session session = getSession();
        try {
            if (this.xforwading) {
                new RequestX11().request(session, this);
            }
            if (this.pty) {
                new RequestPtyReq().request(session, this);
            }
            new RequestSubsystem().request(session, this, this.subsystem, this.want_reply);
            if (this.io.in != null) {
                this.thread = new Thread(this);
                Thread thread = this.thread;
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Subsystem for ");
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
                throw new JSchException("ChannelSubsystem", e);
            } else {
                throw new JSchException("ChannelSubsystem");
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void init() throws JSchException {
        this.io.setInputStream(getSession().in);
        this.io.setOutputStream(getSession().out);
    }

    public void setErrStream(OutputStream outputStream) {
        setExtOutputStream(outputStream);
    }

    public InputStream getErrStream() throws IOException {
        return getExtInputStream();
    }
}
