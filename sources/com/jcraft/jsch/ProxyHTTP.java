package com.jcraft.jsch;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ProxyHTTP implements Proxy {
    private static int DEFAULTPORT = 80;
    private InputStream in;
    private OutputStream out;
    private String passwd;
    private String proxy_host;
    private int proxy_port;
    private Socket socket;
    private String user;

    public ProxyHTTP(String str) {
        int i = DEFAULTPORT;
        if (str.indexOf(58) != -1) {
            try {
                String substring = str.substring(0, str.indexOf(58));
                try {
                    i = Integer.parseInt(str.substring(str.indexOf(58) + 1));
                    str = substring;
                } catch (Exception unused) {
                    str = substring;
                }
            } catch (Exception unused2) {
            }
        }
        this.proxy_host = str;
        this.proxy_port = i;
    }

    public ProxyHTTP(String str, int i) {
        this.proxy_host = str;
        this.proxy_port = i;
    }

    public void setUserPasswd(String str, String str2) {
        this.user = str;
        this.passwd = str2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0176, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0177, code lost:
        throw r7;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0176 A[ExcHandler: RuntimeException (r7v1 'e' java.lang.RuntimeException A[CUSTOM_DECLARE]), Splitter:B:1:0x0002] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void connect(com.jcraft.jsch.SocketFactory r7, java.lang.String r8, int r9, int r10) throws com.jcraft.jsch.JSchException {
        /*
            r6 = this;
            if (r7 != 0) goto L_0x001d
            java.lang.String r7 = r6.proxy_host     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            int r0 = r6.proxy_port     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.net.Socket r7 = com.jcraft.jsch.Util.createSocket(r7, r0, r10)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r6.socket = r7     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.net.Socket r7 = r6.socket     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.io.InputStream r7 = r7.getInputStream()     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r6.in = r7     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.net.Socket r7 = r6.socket     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.io.OutputStream r7 = r7.getOutputStream()     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r6.out = r7     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            goto L_0x0037
        L_0x001d:
            java.lang.String r0 = r6.proxy_host     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            int r1 = r6.proxy_port     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.net.Socket r0 = r7.createSocket(r0, r1)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r6.socket = r0     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.net.Socket r0 = r6.socket     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.io.InputStream r0 = r7.getInputStream(r0)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r6.in = r0     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.net.Socket r0 = r6.socket     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.io.OutputStream r7 = r7.getOutputStream(r0)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r6.out = r7     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
        L_0x0037:
            if (r10 <= 0) goto L_0x003e
            java.net.Socket r7 = r6.socket     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r7.setSoTimeout(r10)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
        L_0x003e:
            java.net.Socket r7 = r6.socket     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r10 = 1
            r7.setTcpNoDelay(r10)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.io.OutputStream r7 = r6.out     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.lang.StringBuffer r0 = new java.lang.StringBuffer     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r0.<init>()     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.lang.String r1 = "CONNECT "
            r0.append(r1)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r0.append(r8)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.lang.String r8 = ":"
            r0.append(r8)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r0.append(r9)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.lang.String r8 = " HTTP/1.0\r\n"
            r0.append(r8)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.lang.String r8 = r0.toString()     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            byte[] r8 = com.jcraft.jsch.Util.str2byte(r8)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r7.write(r8)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.lang.String r7 = r6.user     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r8 = 0
            if (r7 == 0) goto L_0x00b0
            java.lang.String r7 = r6.passwd     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            if (r7 == 0) goto L_0x00b0
            java.lang.StringBuffer r7 = new java.lang.StringBuffer     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r7.<init>()     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.lang.String r9 = r6.user     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r7.append(r9)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.lang.String r9 = ":"
            r7.append(r9)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.lang.String r9 = r6.passwd     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r7.append(r9)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.lang.String r7 = r7.toString()     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            byte[] r7 = com.jcraft.jsch.Util.str2byte(r7)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            int r9 = r7.length     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            byte[] r7 = com.jcraft.jsch.Util.toBase64(r7, r8, r9)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.io.OutputStream r9 = r6.out     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.lang.String r0 = "Proxy-Authorization: Basic "
            byte[] r0 = com.jcraft.jsch.Util.str2byte(r0)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r9.write(r0)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.io.OutputStream r9 = r6.out     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r9.write(r7)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.io.OutputStream r7 = r6.out     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.lang.String r9 = "\r\n"
            byte[] r9 = com.jcraft.jsch.Util.str2byte(r9)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r7.write(r9)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
        L_0x00b0:
            java.io.OutputStream r7 = r6.out     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.lang.String r9 = "\r\n"
            byte[] r9 = com.jcraft.jsch.Util.str2byte(r9)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r7.write(r9)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.io.OutputStream r7 = r6.out     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r7.flush()     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.lang.StringBuffer r7 = new java.lang.StringBuffer     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r7.<init>()     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r9 = 0
        L_0x00c6:
            r0 = 10
            r1 = 13
            if (r9 < 0) goto L_0x00e2
            java.io.InputStream r9 = r6.in     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            int r9 = r9.read()     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            if (r9 == r1) goto L_0x00d9
            char r0 = (char) r9     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r7.append(r0)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            goto L_0x00c6
        L_0x00d9:
            java.io.InputStream r9 = r6.in     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            int r9 = r9.read()     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            if (r9 == r0) goto L_0x00e2
            goto L_0x00c6
        L_0x00e2:
            if (r9 < 0) goto L_0x0141
            java.lang.String r7 = r7.toString()     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.lang.String r2 = "Unknow reason"
            r3 = -1
            r4 = 32
            int r9 = r7.indexOf(r4)     // Catch:{ Exception -> 0x0104, RuntimeException -> 0x0176 }
            int r5 = r9 + 1
            int r4 = r7.indexOf(r4, r5)     // Catch:{ Exception -> 0x0104, RuntimeException -> 0x0176 }
            java.lang.String r5 = r7.substring(r5, r4)     // Catch:{ Exception -> 0x0104, RuntimeException -> 0x0176 }
            int r3 = java.lang.Integer.parseInt(r5)     // Catch:{ Exception -> 0x0104, RuntimeException -> 0x0176 }
            int r4 = r4 + r10
            java.lang.String r2 = r7.substring(r4)     // Catch:{ Exception -> 0x0104, RuntimeException -> 0x0176 }
        L_0x0104:
            r7 = 200(0xc8, float:2.8E-43)
            if (r3 != r7) goto L_0x012a
        L_0x0108:
            r7 = 0
        L_0x0109:
            if (r9 < 0) goto L_0x011f
            java.io.InputStream r9 = r6.in     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            int r9 = r9.read()     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            if (r9 == r1) goto L_0x0116
            int r7 = r7 + 1
            goto L_0x0109
        L_0x0116:
            java.io.InputStream r9 = r6.in     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            int r9 = r9.read()     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            if (r9 == r0) goto L_0x011f
            goto L_0x0109
        L_0x011f:
            if (r9 < 0) goto L_0x0124
            if (r7 != 0) goto L_0x0108
            return
        L_0x0124:
            java.io.IOException r7 = new java.io.IOException     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r7.<init>()     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            throw r7     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
        L_0x012a:
            java.io.IOException r7 = new java.io.IOException     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.lang.StringBuffer r8 = new java.lang.StringBuffer     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r8.<init>()     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.lang.String r9 = "proxy error: "
            r8.append(r9)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r8.append(r2)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            java.lang.String r8 = r8.toString()     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r7.<init>(r8)     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            throw r7     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
        L_0x0141:
            java.io.IOException r7 = new java.io.IOException     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            r7.<init>()     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
            throw r7     // Catch:{ RuntimeException -> 0x0176, Exception -> 0x0147 }
        L_0x0147:
            r7 = move-exception
            java.net.Socket r8 = r6.socket     // Catch:{ Exception -> 0x0151 }
            if (r8 == 0) goto L_0x0151
            java.net.Socket r8 = r6.socket     // Catch:{ Exception -> 0x0151 }
            r8.close()     // Catch:{ Exception -> 0x0151 }
        L_0x0151:
            java.lang.StringBuffer r8 = new java.lang.StringBuffer
            r8.<init>()
            java.lang.String r9 = "ProxyHTTP: "
            r8.append(r9)
            java.lang.String r9 = r7.toString()
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            boolean r9 = r7 instanceof java.lang.Throwable
            if (r9 == 0) goto L_0x0170
            com.jcraft.jsch.JSchException r9 = new com.jcraft.jsch.JSchException
            r9.<init>(r8, r7)
            throw r9
        L_0x0170:
            com.jcraft.jsch.JSchException r7 = new com.jcraft.jsch.JSchException
            r7.<init>(r8)
            throw r7
        L_0x0176:
            r7 = move-exception
            throw r7
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.ProxyHTTP.connect(com.jcraft.jsch.SocketFactory, java.lang.String, int, int):void");
    }

    public InputStream getInputStream() {
        return this.in;
    }

    public OutputStream getOutputStream() {
        return this.out;
    }

    public Socket getSocket() {
        return this.socket;
    }

    public void close() {
        try {
            if (this.in != null) {
                this.in.close();
            }
            if (this.out != null) {
                this.out.close();
            }
            if (this.socket != null) {
                this.socket.close();
            }
        } catch (Exception unused) {
        }
        this.in = null;
        this.out = null;
        this.socket = null;
    }

    public static int getDefaultPort() {
        return DEFAULTPORT;
    }
}
