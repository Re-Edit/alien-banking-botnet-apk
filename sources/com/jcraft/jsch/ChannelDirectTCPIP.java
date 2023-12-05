package com.jcraft.jsch;

import java.io.InputStream;
import java.io.OutputStream;

public class ChannelDirectTCPIP extends Channel {
    private static final int LOCAL_MAXIMUM_PACKET_SIZE = 16384;
    private static final int LOCAL_WINDOW_SIZE_MAX = 131072;
    private static final byte[] _type = Util.str2byte("direct-tcpip");
    String host;
    String originator_IP_address = "127.0.0.1";
    int originator_port = 0;
    int port;

    ChannelDirectTCPIP() {
        this.type = _type;
        setLocalWindowSizeMax(131072);
        setLocalWindowSize(131072);
        setLocalPacketSize(16384);
    }

    /* access modifiers changed from: package-private */
    public void init() {
        this.io = new IO();
    }

    public void connect(int i) throws JSchException {
        this.connectTimeout = i;
        try {
            Session session = getSession();
            if (!session.isConnected()) {
                throw new JSchException("session is down");
            } else if (this.io.in != null) {
                this.thread = new Thread(this);
                Thread thread = this.thread;
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("DirectTCPIP thread ");
                stringBuffer.append(session.getHost());
                thread.setName(stringBuffer.toString());
                if (session.daemon_thread) {
                    this.thread.setDaemon(session.daemon_thread);
                }
                this.thread.start();
            } else {
                sendChannelOpen();
            }
        } catch (Exception e) {
            this.io.close();
            this.io = null;
            Channel.del(this);
            if (e instanceof JSchException) {
                throw ((JSchException) e);
            }
        }
    }

    public void run() {
        try {
            sendChannelOpen();
            Buffer buffer = new Buffer(this.rmpsize);
            Packet packet = new Packet(buffer);
            Session session = getSession();
            while (true) {
                if (!isConnected() || this.thread == null || this.io == null || this.io.in == null) {
                    break;
                }
                int read = this.io.in.read(buffer.buffer, 14, (buffer.buffer.length - 14) - 128);
                if (read <= 0) {
                    eof();
                    break;
                }
                packet.reset();
                buffer.putByte((byte) 94);
                buffer.putInt(this.recipient);
                buffer.putInt(read);
                buffer.skip(read);
                synchronized (this) {
                    if (!this.close) {
                        session.write(packet, this, read);
                    }
                }
            }
            eof();
            disconnect();
        } catch (Exception unused) {
            if (!this.connected) {
                this.connected = true;
            }
            disconnect();
        }
    }

    public void setInputStream(InputStream inputStream) {
        this.io.setInputStream(inputStream);
    }

    public void setOutputStream(OutputStream outputStream) {
        this.io.setOutputStream(outputStream);
    }

    public void setHost(String str) {
        this.host = str;
    }

    public void setPort(int i) {
        this.port = i;
    }

    public void setOrgIPAddress(String str) {
        this.originator_IP_address = str;
    }

    public void setOrgPort(int i) {
        this.originator_port = i;
    }

    /* access modifiers changed from: protected */
    public Packet genChannelOpenPacket() {
        Buffer buffer = new Buffer(this.host.length() + 50 + this.originator_IP_address.length() + 128);
        Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte) 90);
        buffer.putString(this.type);
        buffer.putInt(this.id);
        buffer.putInt(this.lwsize);
        buffer.putInt(this.lmpsize);
        buffer.putString(Util.str2byte(this.host));
        buffer.putInt(this.port);
        buffer.putString(Util.str2byte(this.originator_IP_address));
        buffer.putInt(this.originator_port);
        return packet;
    }
}
