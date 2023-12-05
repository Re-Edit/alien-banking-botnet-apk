package com.jcraft.jsch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Vector;

public abstract class Channel implements Runnable {
    static final int SSH_MSG_CHANNEL_OPEN_CONFIRMATION = 91;
    static final int SSH_MSG_CHANNEL_OPEN_FAILURE = 92;
    static final int SSH_MSG_CHANNEL_WINDOW_ADJUST = 93;
    static final int SSH_OPEN_ADMINISTRATIVELY_PROHIBITED = 1;
    static final int SSH_OPEN_CONNECT_FAILED = 2;
    static final int SSH_OPEN_RESOURCE_SHORTAGE = 4;
    static final int SSH_OPEN_UNKNOWN_CHANNEL_TYPE = 3;
    static int index;
    private static Vector pool = new Vector();
    volatile boolean close = false;
    volatile int connectTimeout = 0;
    volatile boolean connected = false;
    volatile boolean eof_local = false;
    volatile boolean eof_remote = false;
    volatile int exitstatus = -1;
    int id;
    IO io = null;
    volatile int lmpsize = 16384;
    volatile int lwsize = this.lwsize_max;
    volatile int lwsize_max = 1048576;
    int notifyme = 0;
    volatile boolean open_confirmation = false;
    volatile int recipient = -1;
    volatile int reply = 0;
    volatile int rmpsize = 0;
    volatile long rwsize = 0;
    private Session session;
    Thread thread = null;
    protected byte[] type = Util.str2byte("foo");

    /* access modifiers changed from: package-private */
    public void init() throws JSchException {
    }

    public void run() {
    }

    public void setXForwarding(boolean z) {
    }

    public void start() throws JSchException {
    }

    static Channel getChannel(String str) {
        if (str.equals("session")) {
            return new ChannelSession();
        }
        if (str.equals("shell")) {
            return new ChannelShell();
        }
        if (str.equals("exec")) {
            return new ChannelExec();
        }
        if (str.equals("x11")) {
            return new ChannelX11();
        }
        if (str.equals("auth-agent@openssh.com")) {
            return new ChannelAgentForwarding();
        }
        if (str.equals("direct-tcpip")) {
            return new ChannelDirectTCPIP();
        }
        if (str.equals("forwarded-tcpip")) {
            return new ChannelForwardedTCPIP();
        }
        if (str.equals("sftp")) {
            return new ChannelSftp();
        }
        if (str.equals("subsystem")) {
            return new ChannelSubsystem();
        }
        return null;
    }

    static Channel getChannel(int i, Session session2) {
        synchronized (pool) {
            for (int i2 = 0; i2 < pool.size(); i2++) {
                Channel channel = (Channel) pool.elementAt(i2);
                if (channel.id == i && channel.session == session2) {
                    return channel;
                }
            }
            return null;
        }
    }

    static void del(Channel channel) {
        synchronized (pool) {
            pool.removeElement(channel);
        }
    }

    Channel() {
        synchronized (pool) {
            int i = index;
            index = i + 1;
            this.id = i;
            pool.addElement(this);
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void setRecipient(int i) {
        this.recipient = i;
        if (this.notifyme > 0) {
            notifyAll();
        }
    }

    /* access modifiers changed from: package-private */
    public int getRecipient() {
        return this.recipient;
    }

    public void connect() throws JSchException {
        connect(0);
    }

    public void connect(int i) throws JSchException {
        this.connectTimeout = i;
        try {
            sendChannelOpen();
            start();
        } catch (Exception e) {
            this.connected = false;
            disconnect();
            if (e instanceof JSchException) {
                throw ((JSchException) e);
            }
            throw new JSchException(e.toString(), e);
        }
    }

    public boolean isEOF() {
        return this.eof_remote;
    }

    /* access modifiers changed from: package-private */
    public void getData(Buffer buffer) {
        setRecipient(buffer.getInt());
        setRemoteWindowSize(buffer.getUInt());
        setRemotePacketSize(buffer.getInt());
    }

    public void setInputStream(InputStream inputStream) {
        this.io.setInputStream(inputStream, false);
    }

    public void setInputStream(InputStream inputStream, boolean z) {
        this.io.setInputStream(inputStream, z);
    }

    public void setOutputStream(OutputStream outputStream) {
        this.io.setOutputStream(outputStream, false);
    }

    public void setOutputStream(OutputStream outputStream, boolean z) {
        this.io.setOutputStream(outputStream, z);
    }

    public void setExtOutputStream(OutputStream outputStream) {
        this.io.setExtOutputStream(outputStream, false);
    }

    public void setExtOutputStream(OutputStream outputStream, boolean z) {
        this.io.setExtOutputStream(outputStream, z);
    }

    public InputStream getInputStream() throws IOException {
        int i;
        try {
            i = Integer.parseInt(getSession().getConfig("max_input_buffer_size"));
        } catch (Exception unused) {
            i = 32768;
        }
        MyPipedInputStream myPipedInputStream = new MyPipedInputStream(this, 32768, i);
        this.io.setOutputStream(new PassiveOutputStream(myPipedInputStream, 32768 < i), false);
        return myPipedInputStream;
    }

    public InputStream getExtInputStream() throws IOException {
        int i;
        try {
            i = Integer.parseInt(getSession().getConfig("max_input_buffer_size"));
        } catch (Exception unused) {
            i = 32768;
        }
        MyPipedInputStream myPipedInputStream = new MyPipedInputStream(this, 32768, i);
        this.io.setExtOutputStream(new PassiveOutputStream(myPipedInputStream, 32768 < i), false);
        return myPipedInputStream;
    }

    public OutputStream getOutputStream() throws IOException {
        return new OutputStream() {
            byte[] b = new byte[1];
            private Buffer buffer = null;
            private boolean closed = false;
            private int dataLen = 0;
            private Packet packet = null;

            private synchronized void init() throws IOException {
                this.buffer = new Buffer(Channel.this.rmpsize);
                this.packet = new Packet(this.buffer);
                if ((this.buffer.buffer.length - 14) - 128 <= 0) {
                    this.buffer = null;
                    this.packet = null;
                    throw new IOException("failed to initialize the channel.");
                }
            }

            public void write(int i) throws IOException {
                byte[] bArr = this.b;
                bArr[0] = (byte) i;
                write(bArr, 0, 1);
            }

            public void write(byte[] bArr, int i, int i2) throws IOException {
                if (this.packet == null) {
                    init();
                }
                if (!this.closed) {
                    byte[] bArr2 = this.buffer.buffer;
                    int length = bArr2.length;
                    while (i2 > 0) {
                        int i3 = this.dataLen;
                        int i4 = i2 > (length - (i3 + 14)) + -128 ? (length - (i3 + 14)) - 128 : i2;
                        if (i4 <= 0) {
                            flush();
                        } else {
                            System.arraycopy(bArr, i, bArr2, this.dataLen + 14, i4);
                            this.dataLen += i4;
                            i += i4;
                            i2 -= i4;
                        }
                    }
                    return;
                }
                throw new IOException("Already closed");
            }

            public void flush() throws IOException {
                if (this.closed) {
                    throw new IOException("Already closed");
                } else if (this.dataLen != 0) {
                    this.packet.reset();
                    this.buffer.putByte((byte) 94);
                    this.buffer.putInt(Channel.this.recipient);
                    this.buffer.putInt(this.dataLen);
                    this.buffer.skip(this.dataLen);
                    try {
                        int i = this.dataLen;
                        this.dataLen = 0;
                        synchronized (this) {
                            if (!this.close) {
                                Channel.this.getSession().write(this.packet, this, i);
                            }
                        }
                    } catch (Exception e) {
                        close();
                        throw new IOException(e.toString());
                    }
                }
            }

            public void close() throws IOException {
                if (this.packet == null) {
                    try {
                        init();
                    } catch (IOException unused) {
                        return;
                    }
                }
                if (!this.closed) {
                    if (this.dataLen > 0) {
                        flush();
                    }
                    this.eof();
                    this.closed = true;
                }
            }
        };
    }

    class MyPipedInputStream extends PipedInputStream {
        private int BUFFER_SIZE;
        private int max_buffer_size;

        MyPipedInputStream() throws IOException {
            this.BUFFER_SIZE = 1024;
            this.max_buffer_size = this.BUFFER_SIZE;
        }

        MyPipedInputStream(int i) throws IOException {
            this.BUFFER_SIZE = 1024;
            this.max_buffer_size = this.BUFFER_SIZE;
            this.buffer = new byte[i];
            this.BUFFER_SIZE = i;
            this.max_buffer_size = i;
        }

        MyPipedInputStream(Channel channel, int i, int i2) throws IOException {
            this(i);
            this.max_buffer_size = i2;
        }

        MyPipedInputStream(PipedOutputStream pipedOutputStream) throws IOException {
            super(pipedOutputStream);
            this.BUFFER_SIZE = 1024;
            this.max_buffer_size = this.BUFFER_SIZE;
        }

        MyPipedInputStream(PipedOutputStream pipedOutputStream, int i) throws IOException {
            super(pipedOutputStream);
            this.BUFFER_SIZE = 1024;
            this.max_buffer_size = this.BUFFER_SIZE;
            this.buffer = new byte[i];
            this.BUFFER_SIZE = i;
        }

        public synchronized void updateReadSide() throws IOException {
            if (available() == 0) {
                this.in = 0;
                this.out = 0;
                byte[] bArr = this.buffer;
                int i = this.in;
                this.in = i + 1;
                bArr[i] = 0;
                read();
            }
        }

        private int freeSpace() {
            if (this.out < this.in) {
                return this.buffer.length - this.in;
            }
            if (this.in >= this.out) {
                return 0;
            }
            if (this.in == -1) {
                return this.buffer.length;
            }
            return this.out - this.in;
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x0092, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public synchronized void checkSpace(int r6) throws java.io.IOException {
            /*
                r5 = this;
                monitor-enter(r5)
                int r0 = r5.freeSpace()     // Catch:{ all -> 0x0093 }
                if (r0 >= r6) goto L_0x007c
                byte[] r1 = r5.buffer     // Catch:{ all -> 0x0093 }
                int r1 = r1.length     // Catch:{ all -> 0x0093 }
                int r1 = r1 - r0
                byte[] r0 = r5.buffer     // Catch:{ all -> 0x0093 }
                int r0 = r0.length     // Catch:{ all -> 0x0093 }
            L_0x000e:
                int r2 = r0 - r1
                if (r2 >= r6) goto L_0x0015
                int r0 = r0 * 2
                goto L_0x000e
            L_0x0015:
                int r2 = r5.max_buffer_size     // Catch:{ all -> 0x0093 }
                if (r0 <= r2) goto L_0x001b
                int r0 = r5.max_buffer_size     // Catch:{ all -> 0x0093 }
            L_0x001b:
                int r1 = r0 - r1
                if (r1 >= r6) goto L_0x0021
                monitor-exit(r5)
                return
            L_0x0021:
                byte[] r6 = new byte[r0]     // Catch:{ all -> 0x0093 }
                int r0 = r5.out     // Catch:{ all -> 0x0093 }
                int r1 = r5.in     // Catch:{ all -> 0x0093 }
                r2 = 0
                if (r0 >= r1) goto L_0x0033
                byte[] r0 = r5.buffer     // Catch:{ all -> 0x0093 }
                byte[] r1 = r5.buffer     // Catch:{ all -> 0x0093 }
                int r1 = r1.length     // Catch:{ all -> 0x0093 }
                java.lang.System.arraycopy(r0, r2, r6, r2, r1)     // Catch:{ all -> 0x0093 }
                goto L_0x0079
            L_0x0033:
                int r0 = r5.in     // Catch:{ all -> 0x0093 }
                int r1 = r5.out     // Catch:{ all -> 0x0093 }
                if (r0 >= r1) goto L_0x0066
                int r0 = r5.in     // Catch:{ all -> 0x0093 }
                r1 = -1
                if (r0 != r1) goto L_0x003f
                goto L_0x0079
            L_0x003f:
                byte[] r0 = r5.buffer     // Catch:{ all -> 0x0093 }
                int r1 = r5.in     // Catch:{ all -> 0x0093 }
                java.lang.System.arraycopy(r0, r2, r6, r2, r1)     // Catch:{ all -> 0x0093 }
                byte[] r0 = r5.buffer     // Catch:{ all -> 0x0093 }
                int r1 = r5.out     // Catch:{ all -> 0x0093 }
                int r2 = r6.length     // Catch:{ all -> 0x0093 }
                byte[] r3 = r5.buffer     // Catch:{ all -> 0x0093 }
                int r3 = r3.length     // Catch:{ all -> 0x0093 }
                int r4 = r5.out     // Catch:{ all -> 0x0093 }
                int r3 = r3 - r4
                int r2 = r2 - r3
                byte[] r3 = r5.buffer     // Catch:{ all -> 0x0093 }
                int r3 = r3.length     // Catch:{ all -> 0x0093 }
                int r4 = r5.out     // Catch:{ all -> 0x0093 }
                int r3 = r3 - r4
                java.lang.System.arraycopy(r0, r1, r6, r2, r3)     // Catch:{ all -> 0x0093 }
                int r0 = r6.length     // Catch:{ all -> 0x0093 }
                byte[] r1 = r5.buffer     // Catch:{ all -> 0x0093 }
                int r1 = r1.length     // Catch:{ all -> 0x0093 }
                int r2 = r5.out     // Catch:{ all -> 0x0093 }
                int r1 = r1 - r2
                int r0 = r0 - r1
                r5.out = r0     // Catch:{ all -> 0x0093 }
                goto L_0x0079
            L_0x0066:
                int r0 = r5.in     // Catch:{ all -> 0x0093 }
                int r1 = r5.out     // Catch:{ all -> 0x0093 }
                if (r0 != r1) goto L_0x0079
                byte[] r0 = r5.buffer     // Catch:{ all -> 0x0093 }
                byte[] r1 = r5.buffer     // Catch:{ all -> 0x0093 }
                int r1 = r1.length     // Catch:{ all -> 0x0093 }
                java.lang.System.arraycopy(r0, r2, r6, r2, r1)     // Catch:{ all -> 0x0093 }
                byte[] r0 = r5.buffer     // Catch:{ all -> 0x0093 }
                int r0 = r0.length     // Catch:{ all -> 0x0093 }
                r5.in = r0     // Catch:{ all -> 0x0093 }
            L_0x0079:
                r5.buffer = r6     // Catch:{ all -> 0x0093 }
                goto L_0x0091
            L_0x007c:
                byte[] r6 = r5.buffer     // Catch:{ all -> 0x0093 }
                int r6 = r6.length     // Catch:{ all -> 0x0093 }
                if (r6 != r0) goto L_0x0091
                int r6 = r5.BUFFER_SIZE     // Catch:{ all -> 0x0093 }
                if (r0 <= r6) goto L_0x0091
                int r0 = r0 / 2
                int r6 = r5.BUFFER_SIZE     // Catch:{ all -> 0x0093 }
                if (r0 >= r6) goto L_0x008d
                int r0 = r5.BUFFER_SIZE     // Catch:{ all -> 0x0093 }
            L_0x008d:
                byte[] r6 = new byte[r0]     // Catch:{ all -> 0x0093 }
                r5.buffer = r6     // Catch:{ all -> 0x0093 }
            L_0x0091:
                monitor-exit(r5)
                return
            L_0x0093:
                r6 = move-exception
                monitor-exit(r5)
                throw r6
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.Channel.MyPipedInputStream.checkSpace(int):void");
        }
    }

    /* access modifiers changed from: package-private */
    public void setLocalWindowSizeMax(int i) {
        this.lwsize_max = i;
    }

    /* access modifiers changed from: package-private */
    public void setLocalWindowSize(int i) {
        this.lwsize = i;
    }

    /* access modifiers changed from: package-private */
    public void setLocalPacketSize(int i) {
        this.lmpsize = i;
    }

    /* access modifiers changed from: package-private */
    public synchronized void setRemoteWindowSize(long j) {
        this.rwsize = j;
    }

    /* access modifiers changed from: package-private */
    public synchronized void addRemoteWindowSize(long j) {
        this.rwsize += j;
        if (this.notifyme > 0) {
            notifyAll();
        }
    }

    /* access modifiers changed from: package-private */
    public void setRemotePacketSize(int i) {
        this.rmpsize = i;
    }

    /* access modifiers changed from: package-private */
    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    /* access modifiers changed from: package-private */
    public void write(byte[] bArr, int i, int i2) throws IOException {
        try {
            this.io.put(bArr, i, i2);
        } catch (NullPointerException unused) {
        }
    }

    /* access modifiers changed from: package-private */
    public void write_ext(byte[] bArr, int i, int i2) throws IOException {
        try {
            this.io.put_ext(bArr, i, i2);
        } catch (NullPointerException unused) {
        }
    }

    /* access modifiers changed from: package-private */
    public void eof_remote() {
        this.eof_remote = true;
        try {
            this.io.out_close();
        } catch (NullPointerException unused) {
        }
    }

    /* access modifiers changed from: package-private */
    public void eof() {
        if (!this.eof_local) {
            this.eof_local = true;
            int recipient2 = getRecipient();
            if (recipient2 != -1) {
                try {
                    Buffer buffer = new Buffer(100);
                    Packet packet = new Packet(buffer);
                    packet.reset();
                    buffer.putByte((byte) 96);
                    buffer.putInt(recipient2);
                    synchronized (this) {
                        if (!this.close) {
                            getSession().write(packet);
                        }
                    }
                } catch (Exception unused) {
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void close() {
        if (!this.close) {
            this.close = true;
            this.eof_remote = true;
            this.eof_local = true;
            int recipient2 = getRecipient();
            if (recipient2 != -1) {
                try {
                    Buffer buffer = new Buffer(100);
                    Packet packet = new Packet(buffer);
                    packet.reset();
                    buffer.putByte((byte) 97);
                    buffer.putInt(recipient2);
                    synchronized (this) {
                        getSession().write(packet);
                    }
                } catch (Exception unused) {
                }
            }
        }
    }

    public boolean isClosed() {
        return this.close;
    }

    static void disconnect(Session session2) {
        Channel[] channelArr;
        int i;
        int i2;
        synchronized (pool) {
            channelArr = new Channel[pool.size()];
            i2 = 0;
            for (int i3 = 0; i3 < pool.size(); i3++) {
                try {
                    Channel channel = (Channel) pool.elementAt(i3);
                    if (channel.session == session2) {
                        int i4 = i2 + 1;
                        try {
                            channelArr[i2] = channel;
                            i2 = i4;
                        } catch (Exception unused) {
                            i2 = i4;
                        }
                    }
                } catch (Exception unused2) {
                }
            }
        }
        for (i = 0; i < i2; i++) {
            channelArr[i].disconnect();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        close();
        r1.eof_local = true;
        r1.eof_remote = true;
        r1.thread = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x001b, code lost:
        if (r1.io == null) goto L_0x0022;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x001d, code lost:
        r1.io.close();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void disconnect() {
        /*
            r1 = this;
            monitor-enter(r1)     // Catch:{ all -> 0x0029 }
            boolean r0 = r1.connected     // Catch:{ all -> 0x0026 }
            if (r0 != 0) goto L_0x000a
            monitor-exit(r1)     // Catch:{ all -> 0x0026 }
            del(r1)
            return
        L_0x000a:
            r0 = 0
            r1.connected = r0     // Catch:{ all -> 0x0026 }
            monitor-exit(r1)     // Catch:{ all -> 0x0026 }
            r1.close()     // Catch:{ all -> 0x0029 }
            r0 = 1
            r1.eof_local = r0     // Catch:{ all -> 0x0029 }
            r1.eof_remote = r0     // Catch:{ all -> 0x0029 }
            r0 = 0
            r1.thread = r0     // Catch:{ all -> 0x0029 }
            com.jcraft.jsch.IO r0 = r1.io     // Catch:{ Exception -> 0x0022 }
            if (r0 == 0) goto L_0x0022
            com.jcraft.jsch.IO r0 = r1.io     // Catch:{ Exception -> 0x0022 }
            r0.close()     // Catch:{ Exception -> 0x0022 }
        L_0x0022:
            del(r1)
            return
        L_0x0026:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0026 }
            throw r0     // Catch:{ all -> 0x0029 }
        L_0x0029:
            r0 = move-exception
            del(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.Channel.disconnect():void");
    }

    public boolean isConnected() {
        Session session2 = this.session;
        if (session2 == null || !session2.isConnected() || !this.connected) {
            return false;
        }
        return true;
    }

    public void sendSignal(String str) throws Exception {
        RequestSignal requestSignal = new RequestSignal();
        requestSignal.setSignal(str);
        requestSignal.request(getSession(), this);
    }

    class PassiveInputStream extends MyPipedInputStream {
        PipedOutputStream out;

        PassiveInputStream(PipedOutputStream pipedOutputStream, int i) throws IOException {
            super(pipedOutputStream, i);
            this.out = pipedOutputStream;
        }

        PassiveInputStream(PipedOutputStream pipedOutputStream) throws IOException {
            super(pipedOutputStream);
            this.out = pipedOutputStream;
        }

        public void close() throws IOException {
            PipedOutputStream pipedOutputStream = this.out;
            if (pipedOutputStream != null) {
                pipedOutputStream.close();
            }
            this.out = null;
        }
    }

    class PassiveOutputStream extends PipedOutputStream {
        private MyPipedInputStream _sink = null;

        PassiveOutputStream(PipedInputStream pipedInputStream, boolean z) throws IOException {
            super(pipedInputStream);
            if (z && (pipedInputStream instanceof MyPipedInputStream)) {
                this._sink = (MyPipedInputStream) pipedInputStream;
            }
        }

        public void write(int i) throws IOException {
            MyPipedInputStream myPipedInputStream = this._sink;
            if (myPipedInputStream != null) {
                myPipedInputStream.checkSpace(1);
            }
            super.write(i);
        }

        public void write(byte[] bArr, int i, int i2) throws IOException {
            MyPipedInputStream myPipedInputStream = this._sink;
            if (myPipedInputStream != null) {
                myPipedInputStream.checkSpace(i2);
            }
            super.write(bArr, i, i2);
        }
    }

    /* access modifiers changed from: package-private */
    public void setExitStatus(int i) {
        this.exitstatus = i;
    }

    public int getExitStatus() {
        return this.exitstatus;
    }

    /* access modifiers changed from: package-private */
    public void setSession(Session session2) {
        this.session = session2;
    }

    public Session getSession() throws JSchException {
        Session session2 = this.session;
        if (session2 != null) {
            return session2;
        }
        throw new JSchException("session is not available");
    }

    public int getId() {
        return this.id;
    }

    /* access modifiers changed from: protected */
    public void sendOpenConfirmation() throws Exception {
        Buffer buffer = new Buffer(100);
        Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte) 91);
        buffer.putInt(getRecipient());
        buffer.putInt(this.id);
        buffer.putInt(this.lwsize);
        buffer.putInt(this.lmpsize);
        getSession().write(packet);
    }

    /* access modifiers changed from: protected */
    public void sendOpenFailure(int i) {
        try {
            Buffer buffer = new Buffer(100);
            Packet packet = new Packet(buffer);
            packet.reset();
            buffer.putByte((byte) 92);
            buffer.putInt(getRecipient());
            buffer.putInt(i);
            buffer.putString(Util.str2byte("open failed"));
            buffer.putString(Util.empty);
            getSession().write(packet);
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: protected */
    public Packet genChannelOpenPacket() {
        Buffer buffer = new Buffer(100);
        Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte) 90);
        buffer.putString(this.type);
        buffer.putInt(this.id);
        buffer.putInt(this.lwsize);
        buffer.putInt(this.lmpsize);
        return packet;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x004f */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void sendChannelOpen() throws java.lang.Exception {
        /*
            r13 = this;
            com.jcraft.jsch.Session r0 = r13.getSession()
            boolean r1 = r0.isConnected()
            if (r1 == 0) goto L_0x0088
            com.jcraft.jsch.Packet r1 = r13.genChannelOpenPacket()
            r0.write(r1)
            r1 = 2000(0x7d0, float:2.803E-42)
            long r2 = java.lang.System.currentTimeMillis()
            int r4 = r13.connectTimeout
            long r4 = (long) r4
            r6 = 1
            r7 = 0
            int r9 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r9 == 0) goto L_0x0022
            r1 = 1
        L_0x0022:
            monitor-enter(r13)
            r9 = 0
        L_0x0024:
            int r10 = r13.getRecipient()     // Catch:{ all -> 0x0085 }
            r11 = -1
            if (r10 != r11) goto L_0x0059
            boolean r10 = r0.isConnected()     // Catch:{ all -> 0x0085 }
            if (r10 == 0) goto L_0x0059
            if (r1 <= 0) goto L_0x0059
            int r10 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r10 <= 0) goto L_0x0042
            long r10 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0085 }
            long r10 = r10 - r2
            int r12 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r12 <= 0) goto L_0x0042
            r1 = 0
            goto L_0x0024
        L_0x0042:
            int r10 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r10 != 0) goto L_0x0049
            r10 = 10
            goto L_0x004a
        L_0x0049:
            r10 = r4
        L_0x004a:
            r13.notifyme = r6     // Catch:{ InterruptedException -> 0x004f, all -> 0x0052 }
            r13.wait(r10)     // Catch:{ InterruptedException -> 0x004f, all -> 0x0052 }
        L_0x004f:
            r13.notifyme = r9     // Catch:{ all -> 0x0085 }
            goto L_0x0056
        L_0x0052:
            r0 = move-exception
            r13.notifyme = r9     // Catch:{ all -> 0x0085 }
            throw r0     // Catch:{ all -> 0x0085 }
        L_0x0056:
            int r1 = r1 + -1
            goto L_0x0024
        L_0x0059:
            monitor-exit(r13)     // Catch:{ all -> 0x0085 }
            boolean r0 = r0.isConnected()
            if (r0 == 0) goto L_0x007d
            int r0 = r13.getRecipient()
            if (r0 == r11) goto L_0x0075
            boolean r0 = r13.open_confirmation
            if (r0 == 0) goto L_0x006d
            r13.connected = r6
            return
        L_0x006d:
            com.jcraft.jsch.JSchException r0 = new com.jcraft.jsch.JSchException
            java.lang.String r1 = "channel is not opened."
            r0.<init>(r1)
            throw r0
        L_0x0075:
            com.jcraft.jsch.JSchException r0 = new com.jcraft.jsch.JSchException
            java.lang.String r1 = "channel is not opened."
            r0.<init>(r1)
            throw r0
        L_0x007d:
            com.jcraft.jsch.JSchException r0 = new com.jcraft.jsch.JSchException
            java.lang.String r1 = "session is down"
            r0.<init>(r1)
            throw r0
        L_0x0085:
            r0 = move-exception
            monitor-exit(r13)     // Catch:{ all -> 0x0085 }
            throw r0
        L_0x0088:
            com.jcraft.jsch.JSchException r0 = new com.jcraft.jsch.JSchException
            java.lang.String r1 = "session is down"
            r0.<init>(r1)
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.Channel.sendChannelOpen():void");
    }
}
