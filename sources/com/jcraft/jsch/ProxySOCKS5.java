package com.jcraft.jsch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ProxySOCKS5 implements Proxy {
    private static int DEFAULTPORT = 1080;
    private InputStream in;
    private OutputStream out;
    private String passwd;
    private String proxy_host;
    private int proxy_port;
    private Socket socket;
    private String user;

    public ProxySOCKS5(String str) {
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

    public ProxySOCKS5(String str, int i) {
        this.proxy_host = str;
        this.proxy_port = i;
    }

    public void setUserPasswd(String str, String str2) {
        this.user = str;
        this.passwd = str2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:52:0x017b, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x017c, code lost:
        throw r9;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:32:0x0126 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:37:0x0144 */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x017b A[ExcHandler: RuntimeException (r9v1 'e' java.lang.RuntimeException A[CUSTOM_DECLARE]), Splitter:B:1:0x0002] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:37:0x0144=Splitter:B:37:0x0144, B:32:0x0126=Splitter:B:32:0x0126} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void connect(com.jcraft.jsch.SocketFactory r9, java.lang.String r10, int r11, int r12) throws com.jcraft.jsch.JSchException {
        /*
            r8 = this;
            if (r9 != 0) goto L_0x001d
            java.lang.String r9 = r8.proxy_host     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            int r0 = r8.proxy_port     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.net.Socket r9 = com.jcraft.jsch.Util.createSocket(r9, r0, r12)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r8.socket = r9     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.net.Socket r9 = r8.socket     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.io.InputStream r9 = r9.getInputStream()     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r8.in = r9     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.net.Socket r9 = r8.socket     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.io.OutputStream r9 = r9.getOutputStream()     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r8.out = r9     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            goto L_0x0037
        L_0x001d:
            java.lang.String r0 = r8.proxy_host     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            int r1 = r8.proxy_port     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.net.Socket r0 = r9.createSocket(r0, r1)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r8.socket = r0     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.net.Socket r0 = r8.socket     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.io.InputStream r0 = r9.getInputStream(r0)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r8.in = r0     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.net.Socket r0 = r8.socket     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.io.OutputStream r9 = r9.getOutputStream(r0)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r8.out = r9     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
        L_0x0037:
            if (r12 <= 0) goto L_0x003e
            java.net.Socket r9 = r8.socket     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r9.setSoTimeout(r12)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
        L_0x003e:
            java.net.Socket r9 = r8.socket     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r12 = 1
            r9.setTcpNoDelay(r12)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r9 = 1024(0x400, float:1.435E-42)
            byte[] r9 = new byte[r9]     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r0 = 5
            r1 = 0
            r9[r1] = r0     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r2 = 2
            r9[r12] = r2     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r9[r2] = r1     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r3 = 3
            r9[r3] = r2     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.io.OutputStream r4 = r8.out     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r5 = 4
            r4.write(r9, r1, r5)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.io.InputStream r4 = r8.in     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r8.fill(r4, r9, r2)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            byte r4 = r9[r12]     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r4 = r4 & 255(0xff, float:3.57E-43)
            if (r4 == 0) goto L_0x00c5
            if (r4 == r2) goto L_0x0068
            goto L_0x00c3
        L_0x0068:
            java.lang.String r4 = r8.user     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            if (r4 == 0) goto L_0x00c3
            java.lang.String r4 = r8.passwd     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            if (r4 != 0) goto L_0x0071
            goto L_0x00c3
        L_0x0071:
            r9[r1] = r12     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.lang.String r4 = r8.user     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            int r4 = r4.length()     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            byte r4 = (byte) r4     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r9[r12] = r4     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.lang.String r4 = r8.user     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            byte[] r4 = com.jcraft.jsch.Util.str2byte(r4)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.lang.String r6 = r8.user     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            int r6 = r6.length()     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.lang.System.arraycopy(r4, r1, r9, r2, r6)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.lang.String r4 = r8.user     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            int r4 = r4.length()     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            int r4 = r4 + r2
            int r6 = r4 + 1
            java.lang.String r7 = r8.passwd     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            int r7 = r7.length()     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            byte r7 = (byte) r7     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r9[r4] = r7     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.lang.String r4 = r8.passwd     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            byte[] r4 = com.jcraft.jsch.Util.str2byte(r4)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.lang.String r7 = r8.passwd     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            int r7 = r7.length()     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.lang.System.arraycopy(r4, r1, r9, r6, r7)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.lang.String r4 = r8.passwd     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            int r4 = r4.length()     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            int r6 = r6 + r4
            java.io.OutputStream r4 = r8.out     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r4.write(r9, r1, r6)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.io.InputStream r4 = r8.in     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r8.fill(r4, r9, r2)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            byte r4 = r9[r12]     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            if (r4 != 0) goto L_0x00c3
            r4 = 1
            goto L_0x00c6
        L_0x00c3:
            r4 = 0
            goto L_0x00c6
        L_0x00c5:
            r4 = 1
        L_0x00c6:
            if (r4 == 0) goto L_0x013f
            r9[r1] = r0     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r9[r12] = r12     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r9[r2] = r1     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            byte[] r10 = com.jcraft.jsch.Util.str2byte(r10)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            int r4 = r10.length     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r9[r3] = r3     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            byte r6 = (byte) r4     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r9[r5] = r6     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.lang.System.arraycopy(r10, r1, r9, r0, r4)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            int r0 = r0 + r4
            int r10 = r0 + 1
            int r4 = r11 >>> 8
            byte r4 = (byte) r4     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r9[r0] = r4     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            int r0 = r10 + 1
            r11 = r11 & 255(0xff, float:3.57E-43)
            byte r11 = (byte) r11     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r9[r10] = r11     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.io.OutputStream r10 = r8.out     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r10.write(r9, r1, r0)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.io.InputStream r10 = r8.in     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r8.fill(r10, r9, r5)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            byte r10 = r9[r12]     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            if (r10 != 0) goto L_0x0121
            byte r10 = r9[r3]     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r10 = r10 & 255(0xff, float:3.57E-43)
            if (r10 == r12) goto L_0x011a
            switch(r10) {
                case 3: goto L_0x010a;
                case 4: goto L_0x0102;
                default: goto L_0x0101;
            }     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
        L_0x0101:
            goto L_0x0120
        L_0x0102:
            java.io.InputStream r10 = r8.in     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r11 = 18
            r8.fill(r10, r9, r11)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            goto L_0x0120
        L_0x010a:
            java.io.InputStream r10 = r8.in     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r8.fill(r10, r9, r12)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.io.InputStream r10 = r8.in     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            byte r11 = r9[r1]     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r11 = r11 & 255(0xff, float:3.57E-43)
            int r11 = r11 + r2
            r8.fill(r10, r9, r11)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            goto L_0x0120
        L_0x011a:
            java.io.InputStream r10 = r8.in     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r11 = 6
            r8.fill(r10, r9, r11)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
        L_0x0120:
            return
        L_0x0121:
            java.net.Socket r10 = r8.socket     // Catch:{ Exception -> 0x0126, RuntimeException -> 0x017b }
            r10.close()     // Catch:{ Exception -> 0x0126, RuntimeException -> 0x017b }
        L_0x0126:
            com.jcraft.jsch.JSchException r10 = new com.jcraft.jsch.JSchException     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.lang.StringBuffer r11 = new java.lang.StringBuffer     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r11.<init>()     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.lang.String r0 = "ProxySOCKS5: server returns "
            r11.append(r0)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            byte r9 = r9[r12]     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r11.append(r9)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.lang.String r9 = r11.toString()     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            r10.<init>(r9)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            throw r10     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
        L_0x013f:
            java.net.Socket r9 = r8.socket     // Catch:{ Exception -> 0x0144, RuntimeException -> 0x017b }
            r9.close()     // Catch:{ Exception -> 0x0144, RuntimeException -> 0x017b }
        L_0x0144:
            com.jcraft.jsch.JSchException r9 = new com.jcraft.jsch.JSchException     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            java.lang.String r10 = "fail in SOCKS5 proxy"
            r9.<init>(r10)     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
            throw r9     // Catch:{ RuntimeException -> 0x017b, Exception -> 0x014c }
        L_0x014c:
            r9 = move-exception
            java.net.Socket r10 = r8.socket     // Catch:{ Exception -> 0x0156 }
            if (r10 == 0) goto L_0x0156
            java.net.Socket r10 = r8.socket     // Catch:{ Exception -> 0x0156 }
            r10.close()     // Catch:{ Exception -> 0x0156 }
        L_0x0156:
            java.lang.StringBuffer r10 = new java.lang.StringBuffer
            r10.<init>()
            java.lang.String r11 = "ProxySOCKS5: "
            r10.append(r11)
            java.lang.String r11 = r9.toString()
            r10.append(r11)
            java.lang.String r10 = r10.toString()
            boolean r11 = r9 instanceof java.lang.Throwable
            if (r11 == 0) goto L_0x0175
            com.jcraft.jsch.JSchException r11 = new com.jcraft.jsch.JSchException
            r11.<init>(r10, r9)
            throw r11
        L_0x0175:
            com.jcraft.jsch.JSchException r9 = new com.jcraft.jsch.JSchException
            r9.<init>(r10)
            throw r9
        L_0x017b:
            r9 = move-exception
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.ProxySOCKS5.connect(com.jcraft.jsch.SocketFactory, java.lang.String, int, int):void");
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

    private void fill(InputStream inputStream, byte[] bArr, int i) throws JSchException, IOException {
        int i2 = 0;
        while (i2 < i) {
            int read = inputStream.read(bArr, i2, i - i2);
            if (read > 0) {
                i2 += read;
            } else {
                throw new JSchException("ProxySOCKS5: stream is closed");
            }
        }
    }
}
