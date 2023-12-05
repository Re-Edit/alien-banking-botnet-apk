package com.jcraft.jsch;

import java.util.Enumeration;
import java.util.Hashtable;

class ChannelSession extends Channel {
    private static byte[] _session = Util.str2byte("session");
    protected boolean agent_forwarding = false;
    protected Hashtable env = null;
    protected boolean pty = false;
    protected int tcol = 80;
    protected byte[] terminal_mode = null;
    protected int thp = 480;
    protected int trow = 24;
    protected String ttype = "vt100";
    protected int twp = 640;
    protected boolean xforwading = false;

    ChannelSession() {
        this.type = _session;
        this.io = new IO();
    }

    public void setAgentForwarding(boolean z) {
        this.agent_forwarding = z;
    }

    public void setXForwarding(boolean z) {
        this.xforwading = z;
    }

    public void setEnv(Hashtable hashtable) {
        synchronized (this) {
            this.env = hashtable;
        }
    }

    public void setEnv(String str, String str2) {
        setEnv(Util.str2byte(str), Util.str2byte(str2));
    }

    public void setEnv(byte[] bArr, byte[] bArr2) {
        synchronized (this) {
            getEnv().put(bArr, bArr2);
        }
    }

    private Hashtable getEnv() {
        if (this.env == null) {
            this.env = new Hashtable();
        }
        return this.env;
    }

    public void setPty(boolean z) {
        this.pty = z;
    }

    public void setTerminalMode(byte[] bArr) {
        this.terminal_mode = bArr;
    }

    public void setPtySize(int i, int i2, int i3, int i4) {
        setPtyType(this.ttype, i, i2, i3, i4);
        if (this.pty && isConnected()) {
            try {
                RequestWindowChange requestWindowChange = new RequestWindowChange();
                requestWindowChange.setSize(i, i2, i3, i4);
                requestWindowChange.request(getSession(), this);
            } catch (Exception unused) {
            }
        }
    }

    public void setPtyType(String str) {
        setPtyType(str, 80, 24, 640, 480);
    }

    public void setPtyType(String str, int i, int i2, int i3, int i4) {
        this.ttype = str;
        this.tcol = i;
        this.trow = i2;
        this.twp = i3;
        this.thp = i4;
    }

    /* access modifiers changed from: protected */
    public void sendRequests() throws Exception {
        Session session = getSession();
        if (this.agent_forwarding) {
            new RequestAgentForwarding().request(session, this);
        }
        if (this.xforwading) {
            new RequestX11().request(session, this);
        }
        if (this.pty) {
            RequestPtyReq requestPtyReq = new RequestPtyReq();
            RequestPtyReq requestPtyReq2 = requestPtyReq;
            requestPtyReq2.setTType(this.ttype);
            requestPtyReq2.setTSize(this.tcol, this.trow, this.twp, this.thp);
            byte[] bArr = this.terminal_mode;
            if (bArr != null) {
                requestPtyReq2.setTerminalMode(bArr);
            }
            requestPtyReq.request(session, this);
        }
        Hashtable hashtable = this.env;
        if (hashtable != null) {
            Enumeration keys = hashtable.keys();
            while (keys.hasMoreElements()) {
                Object nextElement = keys.nextElement();
                Object obj = this.env.get(nextElement);
                RequestEnv requestEnv = new RequestEnv();
                requestEnv.setEnv(toByteArray(nextElement), toByteArray(obj));
                requestEnv.request(session, this);
            }
        }
    }

    private byte[] toByteArray(Object obj) {
        if (obj instanceof String) {
            return Util.str2byte((String) obj);
        }
        return (byte[]) obj;
    }

    public void run() {
        Buffer buffer = new Buffer(this.rmpsize);
        Packet packet = new Packet(buffer);
        while (true) {
            try {
                if (!isConnected() || this.thread == null || this.io == null || this.io.in == null) {
                    break;
                }
                int read = this.io.in.read(buffer.buffer, 14, (buffer.buffer.length - 14) - 128);
                if (read != 0) {
                    if (read == -1) {
                        eof();
                        break;
                    } else if (this.close) {
                        break;
                    } else {
                        packet.reset();
                        buffer.putByte((byte) 94);
                        buffer.putInt(this.recipient);
                        buffer.putInt(read);
                        buffer.skip(read);
                        getSession().write(packet, this, read);
                    }
                }
            } catch (Exception unused) {
            }
        }
        Thread thread = this.thread;
        if (thread != null) {
            synchronized (thread) {
                thread.notifyAll();
            }
        }
        this.thread = null;
    }
}
