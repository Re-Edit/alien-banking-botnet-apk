package com.jcraft.jsch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ChannelExec extends ChannelSession {
    byte[] command = new byte[0];

    public void start() throws JSchException {
        Session session = getSession();
        try {
            sendRequests();
            new RequestExec(this.command).request(session, this);
            if (this.io.in != null) {
                this.thread = new Thread(this);
                Thread thread = this.thread;
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Exec thread ");
                stringBuffer.append(session.getHost());
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
                throw new JSchException("ChannelExec", e);
            } else {
                throw new JSchException("ChannelExec");
            }
        }
    }

    public void setCommand(String str) {
        this.command = Util.str2byte(str);
    }

    public void setCommand(byte[] bArr) {
        this.command = bArr;
    }

    /* access modifiers changed from: package-private */
    public void init() throws JSchException {
        this.io.setInputStream(getSession().in);
        this.io.setOutputStream(getSession().out);
    }

    public void setErrStream(OutputStream outputStream) {
        setExtOutputStream(outputStream);
    }

    public void setErrStream(OutputStream outputStream, boolean z) {
        setExtOutputStream(outputStream, z);
    }

    public InputStream getErrStream() throws IOException {
        return getExtInputStream();
    }
}
