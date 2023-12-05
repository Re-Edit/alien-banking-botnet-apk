package com.jcraft.jsch;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ProxySOCKS4 implements Proxy {
    private static int DEFAULTPORT = 1080;
    private InputStream in;
    private OutputStream out;
    private String passwd;
    private String proxy_host;
    private int proxy_port;
    private Socket socket;
    private String user;

    public ProxySOCKS4(String str) {
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

    public ProxySOCKS4(String str, int i) {
        this.proxy_host = str;
        this.proxy_port = i;
    }

    public void setUserPasswd(String str, String str2) {
        this.user = str;
        this.passwd = str2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00ed, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00ee, code lost:
        r8 = new java.lang.StringBuffer();
        r8.append("ProxySOCKS4: ");
        r8.append(r6.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0108, code lost:
        throw new com.jcraft.jsch.JSchException(r8.toString(), r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0109, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x010c, code lost:
        if (r5.socket != null) goto L_0x010e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x010e, code lost:
        r5.socket.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0113, code lost:
        r8 = new java.lang.StringBuffer();
        r8.append("ProxySOCKS4: ");
        r8.append(r6.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x012d, code lost:
        throw new com.jcraft.jsch.JSchException(r8.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x012e, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x012f, code lost:
        throw r6;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:33:0x00bb */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x012e A[ExcHandler: RuntimeException (r6v1 'e' java.lang.RuntimeException A[CUSTOM_DECLARE]), Splitter:B:1:0x0002] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void connect(com.jcraft.jsch.SocketFactory r6, java.lang.String r7, int r8, int r9) throws com.jcraft.jsch.JSchException {
        /*
            r5 = this;
            if (r6 != 0) goto L_0x001d
            java.lang.String r6 = r5.proxy_host     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            int r0 = r5.proxy_port     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            java.net.Socket r6 = com.jcraft.jsch.Util.createSocket(r6, r0, r9)     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r5.socket = r6     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            java.net.Socket r6 = r5.socket     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            java.io.InputStream r6 = r6.getInputStream()     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r5.in = r6     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            java.net.Socket r6 = r5.socket     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            java.io.OutputStream r6 = r6.getOutputStream()     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r5.out = r6     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            goto L_0x0037
        L_0x001d:
            java.lang.String r0 = r5.proxy_host     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            int r1 = r5.proxy_port     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            java.net.Socket r0 = r6.createSocket(r0, r1)     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r5.socket = r0     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            java.net.Socket r0 = r5.socket     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            java.io.InputStream r0 = r6.getInputStream(r0)     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r5.in = r0     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            java.net.Socket r0 = r5.socket     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            java.io.OutputStream r6 = r6.getOutputStream(r0)     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r5.out = r6     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
        L_0x0037:
            if (r9 <= 0) goto L_0x003e
            java.net.Socket r6 = r5.socket     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r6.setSoTimeout(r9)     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
        L_0x003e:
            java.net.Socket r6 = r5.socket     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r9 = 1
            r6.setTcpNoDelay(r9)     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r6 = 1024(0x400, float:1.435E-42)
            byte[] r6 = new byte[r6]     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r0 = 4
            r1 = 0
            r6[r1] = r0     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r2 = 2
            r6[r9] = r9     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r3 = 3
            int r4 = r8 >>> 8
            byte r4 = (byte) r4     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r6[r2] = r4     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r8 = r8 & 255(0xff, float:3.57E-43)
            byte r8 = (byte) r8     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r6[r3] = r8     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            java.net.InetAddress r7 = java.net.InetAddress.getByName(r7)     // Catch:{ UnknownHostException -> 0x00ed }
            byte[] r7 = r7.getAddress()     // Catch:{ UnknownHostException -> 0x00ed }
            r8 = 0
        L_0x0063:
            int r2 = r7.length     // Catch:{ UnknownHostException -> 0x00ed }
            if (r8 >= r2) goto L_0x0070
            int r2 = r0 + 1
            byte r3 = r7[r8]     // Catch:{ UnknownHostException -> 0x00ed }
            r6[r0] = r3     // Catch:{ UnknownHostException -> 0x00ed }
            int r8 = r8 + 1
            r0 = r2
            goto L_0x0063
        L_0x0070:
            java.lang.String r7 = r5.user     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            if (r7 == 0) goto L_0x008a
            java.lang.String r7 = r5.user     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            byte[] r7 = com.jcraft.jsch.Util.str2byte(r7)     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            java.lang.String r8 = r5.user     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            int r8 = r8.length()     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            java.lang.System.arraycopy(r7, r1, r6, r0, r8)     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            java.lang.String r7 = r5.user     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            int r7 = r7.length()     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            int r0 = r0 + r7
        L_0x008a:
            int r7 = r0 + 1
            r6[r0] = r1     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            java.io.OutputStream r8 = r5.out     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r8.write(r6, r1, r7)     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r7 = 0
        L_0x0094:
            r8 = 8
            if (r7 >= r8) goto L_0x00ab
            java.io.InputStream r0 = r5.in     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            int r8 = r8 - r7
            int r8 = r0.read(r6, r7, r8)     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            if (r8 <= 0) goto L_0x00a3
            int r7 = r7 + r8
            goto L_0x0094
        L_0x00a3:
            com.jcraft.jsch.JSchException r6 = new com.jcraft.jsch.JSchException     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            java.lang.String r7 = "ProxySOCKS4: stream is closed"
            r6.<init>(r7)     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            throw r6     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
        L_0x00ab:
            byte r7 = r6[r1]     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            if (r7 != 0) goto L_0x00d4
            byte r7 = r6[r9]     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r8 = 90
            if (r7 != r8) goto L_0x00b6
            return
        L_0x00b6:
            java.net.Socket r7 = r5.socket     // Catch:{ Exception -> 0x00bb, RuntimeException -> 0x012e }
            r7.close()     // Catch:{ Exception -> 0x00bb, RuntimeException -> 0x012e }
        L_0x00bb:
            java.lang.StringBuffer r7 = new java.lang.StringBuffer     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r7.<init>()     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            java.lang.String r8 = "ProxySOCKS4: server returns CD "
            r7.append(r8)     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            byte r6 = r6[r9]     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r7.append(r6)     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            java.lang.String r6 = r7.toString()     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            com.jcraft.jsch.JSchException r7 = new com.jcraft.jsch.JSchException     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r7.<init>(r6)     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            throw r7     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
        L_0x00d4:
            com.jcraft.jsch.JSchException r7 = new com.jcraft.jsch.JSchException     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            java.lang.StringBuffer r8 = new java.lang.StringBuffer     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r8.<init>()     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            java.lang.String r9 = "ProxySOCKS4: server returns VN "
            r8.append(r9)     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            byte r6 = r6[r1]     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r8.append(r6)     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            java.lang.String r6 = r8.toString()     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r7.<init>(r6)     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            throw r7     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
        L_0x00ed:
            r6 = move-exception
            com.jcraft.jsch.JSchException r7 = new com.jcraft.jsch.JSchException     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            java.lang.StringBuffer r8 = new java.lang.StringBuffer     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r8.<init>()     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            java.lang.String r9 = "ProxySOCKS4: "
            r8.append(r9)     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            java.lang.String r9 = r6.toString()     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r8.append(r9)     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            java.lang.String r8 = r8.toString()     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            r7.<init>(r8, r6)     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
            throw r7     // Catch:{ RuntimeException -> 0x012e, Exception -> 0x0109 }
        L_0x0109:
            r6 = move-exception
            java.net.Socket r7 = r5.socket     // Catch:{ Exception -> 0x0113 }
            if (r7 == 0) goto L_0x0113
            java.net.Socket r7 = r5.socket     // Catch:{ Exception -> 0x0113 }
            r7.close()     // Catch:{ Exception -> 0x0113 }
        L_0x0113:
            com.jcraft.jsch.JSchException r7 = new com.jcraft.jsch.JSchException
            java.lang.StringBuffer r8 = new java.lang.StringBuffer
            r8.<init>()
            java.lang.String r9 = "ProxySOCKS4: "
            r8.append(r9)
            java.lang.String r6 = r6.toString()
            r8.append(r6)
            java.lang.String r6 = r8.toString()
            r7.<init>(r6)
            throw r7
        L_0x012e:
            r6 = move-exception
            throw r6
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.ProxySOCKS4.connect(com.jcraft.jsch.SocketFactory, java.lang.String, int, int):void");
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
