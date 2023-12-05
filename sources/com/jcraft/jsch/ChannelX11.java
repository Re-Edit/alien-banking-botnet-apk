package com.jcraft.jsch;

import android.support.v4.view.MotionEventCompat;
import java.io.IOException;
import java.net.Socket;
import java.util.Hashtable;

class ChannelX11 extends Channel {
    private static final int LOCAL_MAXIMUM_PACKET_SIZE = 16384;
    private static final int LOCAL_WINDOW_SIZE_MAX = 131072;
    private static final int TIMEOUT = 10000;
    static byte[] cookie = null;
    private static byte[] cookie_hex = null;
    private static Hashtable faked_cookie_hex_pool = new Hashtable();
    private static Hashtable faked_cookie_pool = new Hashtable();
    private static String host = "127.0.0.1";
    private static int port = 6000;
    private static byte[] table = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
    private byte[] cache = new byte[0];
    private boolean init = true;
    private Socket socket = null;

    static int revtable(byte b) {
        int i = 0;
        while (true) {
            byte[] bArr = table;
            if (i >= bArr.length) {
                return 0;
            }
            if (bArr[i] == b) {
                return i;
            }
            i++;
        }
    }

    static void setCookie(String str) {
        cookie_hex = Util.str2byte(str);
        cookie = new byte[16];
        for (int i = 0; i < 16; i++) {
            int i2 = i * 2;
            cookie[i] = (byte) (((revtable(cookie_hex[i2]) << 4) & 240) | (revtable(cookie_hex[i2 + 1]) & 15));
        }
    }

    static void setHost(String str) {
        host = str;
    }

    static void setPort(int i) {
        port = i;
    }

    static byte[] getFakedCookie(Session session) {
        byte[] bArr;
        int i;
        synchronized (faked_cookie_hex_pool) {
            bArr = (byte[]) faked_cookie_hex_pool.get(session);
            if (bArr == null) {
                Random random = Session.random;
                byte[] bArr2 = new byte[16];
                synchronized (random) {
                    random.fill(bArr2, 0, 16);
                }
                faked_cookie_pool.put(session, bArr2);
                bArr = new byte[32];
                for (i = 0; i < 16; i++) {
                    int i2 = i * 2;
                    bArr[i2] = table[(bArr2[i] >>> 4) & 15];
                    bArr[i2 + 1] = table[bArr2[i] & 15];
                }
                faked_cookie_hex_pool.put(session, bArr);
            }
        }
        return bArr;
    }

    static void removeFakedCookie(Session session) {
        synchronized (faked_cookie_hex_pool) {
            faked_cookie_hex_pool.remove(session);
            faked_cookie_pool.remove(session);
        }
    }

    ChannelX11() {
        setLocalWindowSizeMax(131072);
        setLocalWindowSize(131072);
        setLocalPacketSize(16384);
        this.type = Util.str2byte("x11");
        this.connected = true;
    }

    public void run() {
        try {
            this.socket = Util.createSocket(host, port, TIMEOUT);
            this.socket.setTcpNoDelay(true);
            this.io = new IO();
            this.io.setInputStream(this.socket.getInputStream());
            this.io.setOutputStream(this.socket.getOutputStream());
            sendOpenConfirmation();
            this.thread = Thread.currentThread();
            Buffer buffer = new Buffer(this.rmpsize);
            Packet packet = new Packet(buffer);
            while (true) {
                try {
                    if (this.thread == null || this.io == null || this.io.in == null) {
                        break;
                    }
                    int read = this.io.in.read(buffer.buffer, 14, (buffer.buffer.length - 14) - 128);
                    if (read <= 0) {
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
                } catch (Exception unused) {
                }
            }
            disconnect();
        } catch (Exception unused2) {
            sendOpenFailure(1);
            this.close = true;
            disconnect();
        }
    }

    private byte[] addCache(byte[] bArr, int i, int i2) {
        byte[] bArr2 = this.cache;
        byte[] bArr3 = new byte[(bArr2.length + i2)];
        System.arraycopy(bArr, i, bArr3, bArr2.length, i2);
        byte[] bArr4 = this.cache;
        if (bArr4.length > 0) {
            System.arraycopy(bArr4, 0, bArr3, 0, bArr4.length);
        }
        this.cache = bArr3;
        return this.cache;
    }

    /* access modifiers changed from: package-private */
    public void write(byte[] bArr, int i, int i2) throws IOException {
        byte[] bArr2;
        if (this.init) {
            try {
                Session session = getSession();
                byte[] addCache = addCache(bArr, i, i2);
                int length = addCache.length;
                if (length >= 9) {
                    int i3 = ((addCache[6] & 255) * 256) + (addCache[7] & 255);
                    int i4 = ((addCache[8] & 255) * 256) + (addCache[9] & 255);
                    if ((addCache[0] & 255) != 66 && (addCache[0] & 255) == 108) {
                        i3 = ((i3 << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | ((i3 >>> 8) & 255);
                        i4 = ((i4 << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | ((i4 >>> 8) & 255);
                    }
                    int i5 = (-i3) & 3;
                    if (length >= i3 + 12 + i5 + i4) {
                        byte[] bArr3 = new byte[i4];
                        int i6 = 12 + i3 + i5;
                        System.arraycopy(addCache, i6, bArr3, 0, i4);
                        synchronized (faked_cookie_pool) {
                            bArr2 = (byte[]) faked_cookie_pool.get(session);
                        }
                        if (equals(bArr3, bArr2)) {
                            byte[] bArr4 = cookie;
                            if (bArr4 != null) {
                                System.arraycopy(bArr4, 0, addCache, i6, i4);
                            }
                        } else {
                            this.thread = null;
                            eof();
                            this.io.close();
                            disconnect();
                        }
                        this.init = false;
                        this.io.put(addCache, 0, length);
                        this.cache = null;
                    }
                }
            } catch (JSchException e) {
                throw new IOException(e.toString());
            }
        } else {
            this.io.put(bArr, i, i2);
        }
    }

    private static boolean equals(byte[] bArr, byte[] bArr2) {
        if (bArr.length != bArr2.length) {
            return false;
        }
        for (int i = 0; i < bArr.length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }
}
