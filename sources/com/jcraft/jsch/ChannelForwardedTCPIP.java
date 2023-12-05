package com.jcraft.jsch;

import com.jcraft.jsch.Channel;
import java.io.PipedOutputStream;
import java.net.Socket;
import java.util.Vector;

public class ChannelForwardedTCPIP extends Channel {
    private static final int LOCAL_MAXIMUM_PACKET_SIZE = 16384;
    private static final int LOCAL_WINDOW_SIZE_MAX = 131072;
    private static final int TIMEOUT = 10000;
    private static Vector pool = new Vector();
    private Config config = null;
    private ForwardedTCPIPDaemon daemon = null;
    private Socket socket = null;

    ChannelForwardedTCPIP() {
        setLocalWindowSizeMax(131072);
        setLocalWindowSize(131072);
        setLocalPacketSize(16384);
        this.io = new IO();
        this.connected = true;
    }

    public void run() {
        try {
            if (this.config instanceof ConfigDaemon) {
                ConfigDaemon configDaemon = (ConfigDaemon) this.config;
                this.daemon = (ForwardedTCPIPDaemon) Class.forName(configDaemon.target).newInstance();
                PipedOutputStream pipedOutputStream = new PipedOutputStream();
                this.io.setInputStream(new Channel.PassiveInputStream(pipedOutputStream, 32768), false);
                this.daemon.setChannel(this, getInputStream(), pipedOutputStream);
                this.daemon.setArg(configDaemon.arg);
                new Thread(this.daemon).start();
            } else {
                ConfigLHost configLHost = (ConfigLHost) this.config;
                this.socket = configLHost.factory == null ? Util.createSocket(configLHost.target, configLHost.lport, TIMEOUT) : configLHost.factory.createSocket(configLHost.target, configLHost.lport);
                this.socket.setTcpNoDelay(true);
                this.io.setInputStream(this.socket.getInputStream());
                this.io.setOutputStream(this.socket.getOutputStream());
            }
            sendOpenConfirmation();
            this.thread = Thread.currentThread();
            Buffer buffer = new Buffer(this.rmpsize);
            Packet packet = new Packet(buffer);
            try {
                Session session = getSession();
                while (true) {
                    if (this.thread == null || this.io == null || this.io.in == null) {
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
            } catch (Exception unused) {
            }
            disconnect();
        } catch (Exception unused2) {
            sendOpenFailure(1);
            this.close = true;
            disconnect();
        }
    }

    /* access modifiers changed from: package-private */
    public void getData(Buffer buffer) {
        Session session;
        setRecipient(buffer.getInt());
        setRemoteWindowSize(buffer.getUInt());
        setRemotePacketSize(buffer.getInt());
        byte[] string = buffer.getString();
        int i = buffer.getInt();
        buffer.getString();
        buffer.getInt();
        try {
            session = getSession();
        } catch (JSchException unused) {
            session = null;
        }
        this.config = getPort(session, Util.byte2str(string), i);
        if (this.config == null) {
            this.config = getPort(session, (String) null, i);
        }
        if (this.config == null && JSch.getLogger().isEnabled(3)) {
            Logger logger = JSch.getLogger();
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("ChannelForwardedTCPIP: ");
            stringBuffer.append(Util.byte2str(string));
            stringBuffer.append(":");
            stringBuffer.append(i);
            stringBuffer.append(" is not registered.");
            logger.log(3, stringBuffer.toString());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0036, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.jcraft.jsch.ChannelForwardedTCPIP.Config getPort(com.jcraft.jsch.Session r4, java.lang.String r5, int r6) {
        /*
            java.util.Vector r0 = pool
            monitor-enter(r0)
            r1 = 0
        L_0x0004:
            java.util.Vector r2 = pool     // Catch:{ all -> 0x003a }
            int r2 = r2.size()     // Catch:{ all -> 0x003a }
            if (r1 >= r2) goto L_0x0037
            java.util.Vector r2 = pool     // Catch:{ all -> 0x003a }
            java.lang.Object r2 = r2.elementAt(r1)     // Catch:{ all -> 0x003a }
            com.jcraft.jsch.ChannelForwardedTCPIP$Config r2 = (com.jcraft.jsch.ChannelForwardedTCPIP.Config) r2     // Catch:{ all -> 0x003a }
            com.jcraft.jsch.ChannelForwardedTCPIP$Config r2 = (com.jcraft.jsch.ChannelForwardedTCPIP.Config) r2     // Catch:{ all -> 0x003a }
            com.jcraft.jsch.Session r3 = r2.session     // Catch:{ all -> 0x003a }
            if (r3 == r4) goto L_0x001b
            goto L_0x0032
        L_0x001b:
            int r3 = r2.rport     // Catch:{ all -> 0x003a }
            if (r3 == r6) goto L_0x0028
            int r3 = r2.rport     // Catch:{ all -> 0x003a }
            if (r3 != 0) goto L_0x0032
            int r3 = r2.allocated_rport     // Catch:{ all -> 0x003a }
            if (r3 == r6) goto L_0x0028
            goto L_0x0032
        L_0x0028:
            if (r5 == 0) goto L_0x0035
            java.lang.String r3 = r2.address_to_bind     // Catch:{ all -> 0x003a }
            boolean r3 = r3.equals(r5)     // Catch:{ all -> 0x003a }
            if (r3 != 0) goto L_0x0035
        L_0x0032:
            int r1 = r1 + 1
            goto L_0x0004
        L_0x0035:
            monitor-exit(r0)     // Catch:{ all -> 0x003a }
            return r2
        L_0x0037:
            r4 = 0
            monitor-exit(r0)     // Catch:{ all -> 0x003a }
            return r4
        L_0x003a:
            r4 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x003a }
            throw r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.ChannelForwardedTCPIP.getPort(com.jcraft.jsch.Session, java.lang.String, int):com.jcraft.jsch.ChannelForwardedTCPIP$Config");
    }

    static String[] getPortForwarding(Session session) {
        int i;
        Vector vector = new Vector();
        synchronized (pool) {
            for (int i2 = 0; i2 < pool.size(); i2++) {
                Config config2 = (Config) pool.elementAt(i2);
                if (config2 instanceof ConfigDaemon) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(config2.allocated_rport);
                    stringBuffer.append(":");
                    stringBuffer.append(config2.target);
                    stringBuffer.append(":");
                    vector.addElement(stringBuffer.toString());
                } else {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append(config2.allocated_rport);
                    stringBuffer2.append(":");
                    stringBuffer2.append(config2.target);
                    stringBuffer2.append(":");
                    stringBuffer2.append(((ConfigLHost) config2).lport);
                    vector.addElement(stringBuffer2.toString());
                }
            }
        }
        String[] strArr = new String[vector.size()];
        for (i = 0; i < vector.size(); i++) {
            strArr[i] = (String) vector.elementAt(i);
        }
        return strArr;
    }

    static String normalize(String str) {
        if (str == null) {
            return "localhost";
        }
        return (str.length() == 0 || str.equals("*")) ? "" : str;
    }

    static void addPort(Session session, String str, int i, int i2, String str2, int i3, SocketFactory socketFactory) throws JSchException {
        String normalize = normalize(str);
        synchronized (pool) {
            if (getPort(session, normalize, i) == null) {
                ConfigLHost configLHost = new ConfigLHost();
                configLHost.session = session;
                configLHost.rport = i;
                configLHost.allocated_rport = i2;
                configLHost.target = str2;
                configLHost.lport = i3;
                configLHost.address_to_bind = normalize;
                configLHost.factory = socketFactory;
                pool.addElement(configLHost);
            } else {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("PortForwardingR: remote port ");
                stringBuffer.append(i);
                stringBuffer.append(" is already registered.");
                throw new JSchException(stringBuffer.toString());
            }
        }
    }

    static void addPort(Session session, String str, int i, int i2, String str2, Object[] objArr) throws JSchException {
        String normalize = normalize(str);
        synchronized (pool) {
            if (getPort(session, normalize, i) == null) {
                ConfigDaemon configDaemon = new ConfigDaemon();
                configDaemon.session = session;
                configDaemon.rport = i;
                configDaemon.allocated_rport = i;
                configDaemon.target = str2;
                configDaemon.arg = objArr;
                configDaemon.address_to_bind = normalize;
                pool.addElement(configDaemon);
            } else {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("PortForwardingR: remote port ");
                stringBuffer.append(i);
                stringBuffer.append(" is already registered.");
                throw new JSchException(stringBuffer.toString());
            }
        }
    }

    static void delPort(ChannelForwardedTCPIP channelForwardedTCPIP) {
        Session session;
        Config config2;
        try {
            session = channelForwardedTCPIP.getSession();
        } catch (JSchException unused) {
            session = null;
        }
        if (session != null && (config2 = channelForwardedTCPIP.config) != null) {
            delPort(session, config2.rport);
        }
    }

    static void delPort(Session session, int i) {
        delPort(session, (String) null, i);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0024, code lost:
        r0 = new com.jcraft.jsch.Buffer(100);
        r1 = new com.jcraft.jsch.Packet(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r1.reset();
        r0.putByte((byte) 80);
        r0.putString(com.jcraft.jsch.Util.str2byte("cancel-tcpip-forward"));
        r0.putByte((byte) 0);
        r0.putString(com.jcraft.jsch.Util.str2byte(r4));
        r0.putInt(r5);
        r3.write(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void delPort(com.jcraft.jsch.Session r3, java.lang.String r4, int r5) {
        /*
            java.util.Vector r0 = pool
            monitor-enter(r0)
            java.lang.String r1 = normalize(r4)     // Catch:{ all -> 0x0053 }
            com.jcraft.jsch.ChannelForwardedTCPIP$Config r1 = getPort(r3, r1, r5)     // Catch:{ all -> 0x0053 }
            if (r1 != 0) goto L_0x0012
            r1 = 0
            com.jcraft.jsch.ChannelForwardedTCPIP$Config r1 = getPort(r3, r1, r5)     // Catch:{ all -> 0x0053 }
        L_0x0012:
            if (r1 != 0) goto L_0x0016
            monitor-exit(r0)     // Catch:{ all -> 0x0053 }
            return
        L_0x0016:
            java.util.Vector r2 = pool     // Catch:{ all -> 0x0053 }
            r2.removeElement(r1)     // Catch:{ all -> 0x0053 }
            if (r4 != 0) goto L_0x001f
            java.lang.String r4 = r1.address_to_bind     // Catch:{ all -> 0x0053 }
        L_0x001f:
            if (r4 != 0) goto L_0x0023
            java.lang.String r4 = "0.0.0.0"
        L_0x0023:
            monitor-exit(r0)     // Catch:{ all -> 0x0053 }
            com.jcraft.jsch.Buffer r0 = new com.jcraft.jsch.Buffer
            r1 = 100
            r0.<init>((int) r1)
            com.jcraft.jsch.Packet r1 = new com.jcraft.jsch.Packet
            r1.<init>(r0)
            r1.reset()     // Catch:{ Exception -> 0x0052 }
            r2 = 80
            r0.putByte((byte) r2)     // Catch:{ Exception -> 0x0052 }
            java.lang.String r2 = "cancel-tcpip-forward"
            byte[] r2 = com.jcraft.jsch.Util.str2byte(r2)     // Catch:{ Exception -> 0x0052 }
            r0.putString(r2)     // Catch:{ Exception -> 0x0052 }
            r2 = 0
            r0.putByte((byte) r2)     // Catch:{ Exception -> 0x0052 }
            byte[] r4 = com.jcraft.jsch.Util.str2byte(r4)     // Catch:{ Exception -> 0x0052 }
            r0.putString(r4)     // Catch:{ Exception -> 0x0052 }
            r0.putInt(r5)     // Catch:{ Exception -> 0x0052 }
            r3.write(r1)     // Catch:{ Exception -> 0x0052 }
        L_0x0052:
            return
        L_0x0053:
            r3 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0053 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.ChannelForwardedTCPIP.delPort(com.jcraft.jsch.Session, java.lang.String, int):void");
    }

    static void delPort(Session session) {
        int[] iArr;
        int i;
        int i2;
        synchronized (pool) {
            iArr = new int[pool.size()];
            i2 = 0;
            for (int i3 = 0; i3 < pool.size(); i3++) {
                Config config2 = (Config) pool.elementAt(i3);
                if (config2.session == session) {
                    iArr[i2] = config2.rport;
                    i2++;
                }
            }
        }
        for (i = 0; i < i2; i++) {
            delPort(session, iArr[i]);
        }
    }

    public int getRemotePort() {
        Config config2 = this.config;
        if (config2 != null) {
            return config2.rport;
        }
        return 0;
    }

    private void setSocketFactory(SocketFactory socketFactory) {
        Config config2 = this.config;
        if (config2 != null && (config2 instanceof ConfigLHost)) {
            ((ConfigLHost) config2).factory = socketFactory;
        }
    }

    static abstract class Config {
        String address_to_bind;
        int allocated_rport;
        int rport;
        Session session;
        String target;

        Config() {
        }
    }

    static class ConfigDaemon extends Config {
        Object[] arg;

        ConfigDaemon() {
        }
    }

    static class ConfigLHost extends Config {
        SocketFactory factory;
        int lport;

        ConfigLHost() {
        }
    }
}
