package com.jcraft.jsch;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

class PortWatcher implements Runnable {
    private static InetAddress anyLocalAddress;
    private static Vector pool = new Vector();
    InetAddress boundaddress;
    int connectTimeout = 0;
    String host;
    int lport;
    int rport;
    Session session;
    ServerSocket ss;
    Runnable thread;

    static {
        anyLocalAddress = null;
        try {
            anyLocalAddress = InetAddress.getByName("0.0.0.0");
        } catch (UnknownHostException unused) {
        }
    }

    static String[] getPortForwarding(Session session2) {
        int i;
        Vector vector = new Vector();
        synchronized (pool) {
            for (int i2 = 0; i2 < pool.size(); i2++) {
                PortWatcher portWatcher = (PortWatcher) pool.elementAt(i2);
                if (portWatcher.session == session2) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(portWatcher.lport);
                    stringBuffer.append(":");
                    stringBuffer.append(portWatcher.host);
                    stringBuffer.append(":");
                    stringBuffer.append(portWatcher.rport);
                    vector.addElement(stringBuffer.toString());
                }
            }
        }
        String[] strArr = new String[vector.size()];
        for (i = 0; i < vector.size(); i++) {
            strArr[i] = (String) vector.elementAt(i);
        }
        return strArr;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0039, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.jcraft.jsch.PortWatcher getPort(com.jcraft.jsch.Session r5, java.lang.String r6, int r7) throws com.jcraft.jsch.JSchException {
        /*
            java.net.InetAddress r6 = java.net.InetAddress.getByName(r6)     // Catch:{ UnknownHostException -> 0x0043 }
            java.util.Vector r0 = pool
            monitor-enter(r0)
            r1 = 0
        L_0x0008:
            java.util.Vector r2 = pool     // Catch:{ all -> 0x0040 }
            int r2 = r2.size()     // Catch:{ all -> 0x0040 }
            if (r1 >= r2) goto L_0x003d
            java.util.Vector r2 = pool     // Catch:{ all -> 0x0040 }
            java.lang.Object r2 = r2.elementAt(r1)     // Catch:{ all -> 0x0040 }
            com.jcraft.jsch.PortWatcher r2 = (com.jcraft.jsch.PortWatcher) r2     // Catch:{ all -> 0x0040 }
            com.jcraft.jsch.PortWatcher r2 = (com.jcraft.jsch.PortWatcher) r2     // Catch:{ all -> 0x0040 }
            com.jcraft.jsch.Session r3 = r2.session     // Catch:{ all -> 0x0040 }
            if (r3 != r5) goto L_0x003a
            int r3 = r2.lport     // Catch:{ all -> 0x0040 }
            if (r3 != r7) goto L_0x003a
            java.net.InetAddress r3 = anyLocalAddress     // Catch:{ all -> 0x0040 }
            if (r3 == 0) goto L_0x0030
            java.net.InetAddress r3 = r2.boundaddress     // Catch:{ all -> 0x0040 }
            java.net.InetAddress r4 = anyLocalAddress     // Catch:{ all -> 0x0040 }
            boolean r3 = r3.equals(r4)     // Catch:{ all -> 0x0040 }
            if (r3 != 0) goto L_0x0038
        L_0x0030:
            java.net.InetAddress r3 = r2.boundaddress     // Catch:{ all -> 0x0040 }
            boolean r3 = r3.equals(r6)     // Catch:{ all -> 0x0040 }
            if (r3 == 0) goto L_0x003a
        L_0x0038:
            monitor-exit(r0)     // Catch:{ all -> 0x0040 }
            return r2
        L_0x003a:
            int r1 = r1 + 1
            goto L_0x0008
        L_0x003d:
            r5 = 0
            monitor-exit(r0)     // Catch:{ all -> 0x0040 }
            return r5
        L_0x0040:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0040 }
            throw r5
        L_0x0043:
            r5 = move-exception
            com.jcraft.jsch.JSchException r7 = new com.jcraft.jsch.JSchException
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            java.lang.String r1 = "PortForwardingL: invalid address "
            r0.append(r1)
            r0.append(r6)
            java.lang.String r6 = " specified."
            r0.append(r6)
            java.lang.String r6 = r0.toString()
            r7.<init>(r6, r5)
            throw r7
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.PortWatcher.getPort(com.jcraft.jsch.Session, java.lang.String, int):com.jcraft.jsch.PortWatcher");
    }

    private static String normalize(String str) {
        if (str == null) {
            return str;
        }
        if (str.length() == 0 || str.equals("*")) {
            return "0.0.0.0";
        }
        return str.equals("localhost") ? "127.0.0.1" : str;
    }

    static PortWatcher addPort(Session session2, String str, int i, String str2, int i2, ServerSocketFactory serverSocketFactory) throws JSchException {
        String normalize = normalize(str);
        if (getPort(session2, normalize, i) == null) {
            PortWatcher portWatcher = new PortWatcher(session2, normalize, i, str2, i2, serverSocketFactory);
            pool.addElement(portWatcher);
            return portWatcher;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("PortForwardingL: local port ");
        stringBuffer.append(normalize);
        stringBuffer.append(":");
        stringBuffer.append(i);
        stringBuffer.append(" is already registered.");
        throw new JSchException(stringBuffer.toString());
    }

    static void delPort(Session session2, String str, int i) throws JSchException {
        String normalize = normalize(str);
        PortWatcher port = getPort(session2, normalize, i);
        if (port != null) {
            port.delete();
            pool.removeElement(port);
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("PortForwardingL: local port ");
        stringBuffer.append(normalize);
        stringBuffer.append(":");
        stringBuffer.append(i);
        stringBuffer.append(" is not registered.");
        throw new JSchException(stringBuffer.toString());
    }

    static void delPort(Session session2) {
        synchronized (pool) {
            PortWatcher[] portWatcherArr = new PortWatcher[pool.size()];
            int i = 0;
            for (int i2 = 0; i2 < pool.size(); i2++) {
                PortWatcher portWatcher = (PortWatcher) pool.elementAt(i2);
                if (portWatcher.session == session2) {
                    portWatcher.delete();
                    portWatcherArr[i] = portWatcher;
                    i++;
                }
            }
            for (int i3 = 0; i3 < i; i3++) {
                pool.removeElement(portWatcherArr[i3]);
            }
        }
    }

    PortWatcher(Session session2, String str, int i, String str2, int i2, ServerSocketFactory serverSocketFactory) throws JSchException {
        int localPort;
        this.session = session2;
        this.lport = i;
        this.host = str2;
        this.rport = i2;
        try {
            this.boundaddress = InetAddress.getByName(str);
            this.ss = serverSocketFactory == null ? new ServerSocket(i, 0, this.boundaddress) : serverSocketFactory.createServerSocket(i, 0, this.boundaddress);
            if (i == 0 && (localPort = this.ss.getLocalPort()) != -1) {
                this.lport = localPort;
            }
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("PortForwardingL: local port ");
            stringBuffer.append(str);
            stringBuffer.append(":");
            stringBuffer.append(i);
            stringBuffer.append(" cannot be bound.");
            String stringBuffer2 = stringBuffer.toString();
            if (e instanceof Throwable) {
                throw new JSchException(stringBuffer2, e);
            }
            throw new JSchException(stringBuffer2);
        }
    }

    public void run() {
        this.thread = this;
        while (this.thread != null) {
            try {
                Socket accept = this.ss.accept();
                accept.setTcpNoDelay(true);
                InputStream inputStream = accept.getInputStream();
                OutputStream outputStream = accept.getOutputStream();
                ChannelDirectTCPIP channelDirectTCPIP = new ChannelDirectTCPIP();
                channelDirectTCPIP.init();
                channelDirectTCPIP.setInputStream(inputStream);
                channelDirectTCPIP.setOutputStream(outputStream);
                this.session.addChannel(channelDirectTCPIP);
                channelDirectTCPIP.setHost(this.host);
                channelDirectTCPIP.setPort(this.rport);
                channelDirectTCPIP.setOrgIPAddress(accept.getInetAddress().getHostAddress());
                channelDirectTCPIP.setOrgPort(accept.getPort());
                channelDirectTCPIP.connect(this.connectTimeout);
                int i = channelDirectTCPIP.exitstatus;
            } catch (Exception unused) {
            }
        }
        delete();
    }

    /* access modifiers changed from: package-private */
    public void delete() {
        this.thread = null;
        try {
            if (this.ss != null) {
                this.ss.close();
            }
            this.ss = null;
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: package-private */
    public void setConnectTimeout(int i) {
        this.connectTimeout = i;
    }
}
