package com.jcraft.jsch;

import com.jcraft.jsch.ConfigRepository;
import com.jcraft.jsch.IdentityRepository;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

public class Session implements Runnable {
    private static final int PACKET_MAX_SIZE = 262144;
    static final int SSH_MSG_CHANNEL_CLOSE = 97;
    static final int SSH_MSG_CHANNEL_DATA = 94;
    static final int SSH_MSG_CHANNEL_EOF = 96;
    static final int SSH_MSG_CHANNEL_EXTENDED_DATA = 95;
    static final int SSH_MSG_CHANNEL_FAILURE = 100;
    static final int SSH_MSG_CHANNEL_OPEN = 90;
    static final int SSH_MSG_CHANNEL_OPEN_CONFIRMATION = 91;
    static final int SSH_MSG_CHANNEL_OPEN_FAILURE = 92;
    static final int SSH_MSG_CHANNEL_REQUEST = 98;
    static final int SSH_MSG_CHANNEL_SUCCESS = 99;
    static final int SSH_MSG_CHANNEL_WINDOW_ADJUST = 93;
    static final int SSH_MSG_DEBUG = 4;
    static final int SSH_MSG_DISCONNECT = 1;
    static final int SSH_MSG_GLOBAL_REQUEST = 80;
    static final int SSH_MSG_IGNORE = 2;
    static final int SSH_MSG_KEXDH_INIT = 30;
    static final int SSH_MSG_KEXDH_REPLY = 31;
    static final int SSH_MSG_KEXINIT = 20;
    static final int SSH_MSG_KEX_DH_GEX_GROUP = 31;
    static final int SSH_MSG_KEX_DH_GEX_INIT = 32;
    static final int SSH_MSG_KEX_DH_GEX_REPLY = 33;
    static final int SSH_MSG_KEX_DH_GEX_REQUEST = 34;
    static final int SSH_MSG_NEWKEYS = 21;
    static final int SSH_MSG_REQUEST_FAILURE = 82;
    static final int SSH_MSG_REQUEST_SUCCESS = 81;
    static final int SSH_MSG_SERVICE_ACCEPT = 6;
    static final int SSH_MSG_SERVICE_REQUEST = 5;
    static final int SSH_MSG_UNIMPLEMENTED = 3;
    static final int buffer_margin = 128;
    private static final byte[] keepalivemsg = Util.str2byte("keepalive@jcraft.com");
    private static final byte[] nomoresessions = Util.str2byte("no-more-sessions@openssh.com");
    static Random random;
    private byte[] Ec2s;
    private byte[] Es2c;
    private byte[] IVc2s;
    private byte[] IVs2c;
    private byte[] I_C;
    private byte[] I_S;
    private byte[] K_S;
    private byte[] MACc2s;
    private byte[] MACs2c;
    private byte[] V_C = Util.str2byte("SSH-2.0-JSCH-0.1.54");
    private byte[] V_S;
    boolean agent_forwarding = false;
    int auth_failures = 0;
    Buffer buf;
    private Cipher c2scipher;
    private int c2scipher_size = 8;
    private MAC c2smac;
    int[] compress_len = new int[1];
    private Hashtable config = null;
    private Thread connectThread = null;
    protected boolean daemon_thread = false;
    private Compression deflater;
    private GlobalRequestReply grr = new GlobalRequestReply();
    String[] guess = null;
    String host = "127.0.0.1";
    private String hostKeyAlias = null;
    private HostKey hostkey = null;
    private HostKeyRepository hostkeyRepository = null;
    private IdentityRepository identityRepository = null;
    InputStream in = null;
    private volatile boolean in_kex = false;
    private volatile boolean in_prompt = false;
    private Compression inflater;
    private IO io;
    private boolean isAuthed = false;
    private volatile boolean isConnected = false;
    JSch jsch;
    private long kex_start_time = 0;
    private Object lock = new Object();
    int max_auth_tries = 6;
    String org_host = "127.0.0.1";
    OutputStream out = null;
    Packet packet;
    byte[] password = null;
    int port = 22;
    private Proxy proxy = null;
    private Cipher s2ccipher;
    private int s2ccipher_size = 8;
    private MAC s2cmac;
    private byte[] s2cmac_result1;
    private byte[] s2cmac_result2;
    private int seqi = 0;
    private int seqo = 0;
    private int serverAliveCountMax = 1;
    private int serverAliveInterval = 0;
    private byte[] session_id;
    private Socket socket;
    SocketFactory socket_factory = null;
    Runnable thread;
    private int timeout = 0;
    int[] uncompress_len = new int[1];
    private UserInfo userinfo;
    String username = null;
    boolean x11_forwarding = false;

    Session(JSch jSch, String str, String str2, int i) throws JSchException {
        this.jsch = jSch;
        this.buf = new Buffer();
        this.packet = new Packet(this.buf);
        this.username = str;
        this.host = str2;
        this.org_host = str2;
        this.port = i;
        applyConfig();
        if (this.username == null) {
            try {
                this.username = (String) System.getProperties().get("user.name");
            } catch (SecurityException unused) {
            }
        }
        if (this.username == null) {
            throw new JSchException("username is not given.");
        }
    }

    public void connect() throws JSchException {
        connect(this.timeout);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(11:247|248|249|250|(1:252)|253|254|255|257|258|(2:260|(2:262|263)(2:264|265))(2:266|267)) */
    /* JADX WARNING: Can't wrap try/catch for region: R(4:141|142|(1:144)|145) */
    /* JADX WARNING: Code restructure failed: missing block: B:143:0x03a7, code lost:
        if (com.jcraft.jsch.JSch.getLogger().isEnabled(2) != false) goto L_0x03a9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x03a9, code lost:
        r3 = com.jcraft.jsch.JSch.getLogger();
        r5 = new java.lang.StringBuffer();
        r5.append("failed to load ");
        r5.append(r9);
        r5.append(" method");
        r3.log(2, r5.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:145:0x03c6, code lost:
        r3 = null;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:141:0x039f */
    /* JADX WARNING: Missing exception handler attribute for start block: B:253:0x05f5 */
    /* JADX WARNING: Removed duplicated region for block: B:260:0x05ff A[Catch:{ all -> 0x05ad }] */
    /* JADX WARNING: Removed duplicated region for block: B:266:0x061d A[Catch:{ all -> 0x05ad }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void connect(int r17) throws com.jcraft.jsch.JSchException {
        /*
            r16 = this;
            r1 = r16
            r2 = r17
            boolean r3 = r1.isConnected
            if (r3 != 0) goto L_0x0628
            com.jcraft.jsch.IO r3 = new com.jcraft.jsch.IO
            r3.<init>()
            r1.io = r3
            com.jcraft.jsch.Random r3 = random
            if (r3 != 0) goto L_0x0034
            java.lang.String r3 = "random"
            java.lang.String r3 = r1.getConfig(r3)     // Catch:{ Exception -> 0x0028 }
            java.lang.Class r3 = java.lang.Class.forName(r3)     // Catch:{ Exception -> 0x0028 }
            java.lang.Object r3 = r3.newInstance()     // Catch:{ Exception -> 0x0028 }
            com.jcraft.jsch.Random r3 = (com.jcraft.jsch.Random) r3     // Catch:{ Exception -> 0x0028 }
            com.jcraft.jsch.Random r3 = (com.jcraft.jsch.Random) r3     // Catch:{ Exception -> 0x0028 }
            random = r3     // Catch:{ Exception -> 0x0028 }
            goto L_0x0034
        L_0x0028:
            r0 = move-exception
            r2 = r0
            com.jcraft.jsch.JSchException r3 = new com.jcraft.jsch.JSchException
            java.lang.String r4 = r2.toString()
            r3.<init>(r4, r2)
            throw r3
        L_0x0034:
            com.jcraft.jsch.Random r3 = random
            com.jcraft.jsch.Packet.setRandom(r3)
            com.jcraft.jsch.Logger r3 = com.jcraft.jsch.JSch.getLogger()
            r4 = 1
            boolean r3 = r3.isEnabled(r4)
            if (r3 == 0) goto L_0x0068
            com.jcraft.jsch.Logger r3 = com.jcraft.jsch.JSch.getLogger()
            java.lang.StringBuffer r5 = new java.lang.StringBuffer
            r5.<init>()
            java.lang.String r6 = "Connecting to "
            r5.append(r6)
            java.lang.String r6 = r1.host
            r5.append(r6)
            java.lang.String r6 = " port "
            r5.append(r6)
            int r6 = r1.port
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r3.log(r4, r5)
        L_0x0068:
            r3 = 13
            r5 = 3
            r6 = 0
            r7 = 2
            r8 = 0
            com.jcraft.jsch.Proxy r9 = r1.proxy     // Catch:{ Exception -> 0x05b0 }
            if (r9 != 0) goto L_0x00b9
            com.jcraft.jsch.SocketFactory r9 = r1.socket_factory     // Catch:{ Exception -> 0x05b0 }
            if (r9 != 0) goto L_0x008d
            java.lang.String r9 = r1.host     // Catch:{ Exception -> 0x05b0 }
            int r10 = r1.port     // Catch:{ Exception -> 0x05b0 }
            java.net.Socket r9 = com.jcraft.jsch.Util.createSocket(r9, r10, r2)     // Catch:{ Exception -> 0x05b0 }
            r1.socket = r9     // Catch:{ Exception -> 0x05b0 }
            java.net.Socket r9 = r1.socket     // Catch:{ Exception -> 0x05b0 }
            java.io.InputStream r9 = r9.getInputStream()     // Catch:{ Exception -> 0x05b0 }
            java.net.Socket r10 = r1.socket     // Catch:{ Exception -> 0x05b0 }
            java.io.OutputStream r10 = r10.getOutputStream()     // Catch:{ Exception -> 0x05b0 }
            goto L_0x00a9
        L_0x008d:
            com.jcraft.jsch.SocketFactory r9 = r1.socket_factory     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r10 = r1.host     // Catch:{ Exception -> 0x05b0 }
            int r11 = r1.port     // Catch:{ Exception -> 0x05b0 }
            java.net.Socket r9 = r9.createSocket(r10, r11)     // Catch:{ Exception -> 0x05b0 }
            r1.socket = r9     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.SocketFactory r9 = r1.socket_factory     // Catch:{ Exception -> 0x05b0 }
            java.net.Socket r10 = r1.socket     // Catch:{ Exception -> 0x05b0 }
            java.io.InputStream r9 = r9.getInputStream(r10)     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.SocketFactory r10 = r1.socket_factory     // Catch:{ Exception -> 0x05b0 }
            java.net.Socket r11 = r1.socket     // Catch:{ Exception -> 0x05b0 }
            java.io.OutputStream r10 = r10.getOutputStream(r11)     // Catch:{ Exception -> 0x05b0 }
        L_0x00a9:
            java.net.Socket r11 = r1.socket     // Catch:{ Exception -> 0x05b0 }
            r11.setTcpNoDelay(r4)     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.IO r11 = r1.io     // Catch:{ Exception -> 0x05b0 }
            r11.setInputStream(r9)     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.IO r9 = r1.io     // Catch:{ Exception -> 0x05b0 }
            r9.setOutputStream(r10)     // Catch:{ Exception -> 0x05b0 }
            goto L_0x00e6
        L_0x00b9:
            com.jcraft.jsch.Proxy r9 = r1.proxy     // Catch:{ Exception -> 0x05b0 }
            monitor-enter(r9)     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.Proxy r10 = r1.proxy     // Catch:{ all -> 0x05a9 }
            com.jcraft.jsch.SocketFactory r11 = r1.socket_factory     // Catch:{ all -> 0x05a9 }
            java.lang.String r12 = r1.host     // Catch:{ all -> 0x05a9 }
            int r13 = r1.port     // Catch:{ all -> 0x05a9 }
            r10.connect(r11, r12, r13, r2)     // Catch:{ all -> 0x05a9 }
            com.jcraft.jsch.IO r10 = r1.io     // Catch:{ all -> 0x05a9 }
            com.jcraft.jsch.Proxy r11 = r1.proxy     // Catch:{ all -> 0x05a9 }
            java.io.InputStream r11 = r11.getInputStream()     // Catch:{ all -> 0x05a9 }
            r10.setInputStream(r11)     // Catch:{ all -> 0x05a9 }
            com.jcraft.jsch.IO r10 = r1.io     // Catch:{ all -> 0x05a9 }
            com.jcraft.jsch.Proxy r11 = r1.proxy     // Catch:{ all -> 0x05a9 }
            java.io.OutputStream r11 = r11.getOutputStream()     // Catch:{ all -> 0x05a9 }
            r10.setOutputStream(r11)     // Catch:{ all -> 0x05a9 }
            com.jcraft.jsch.Proxy r10 = r1.proxy     // Catch:{ all -> 0x05a9 }
            java.net.Socket r10 = r10.getSocket()     // Catch:{ all -> 0x05a9 }
            r1.socket = r10     // Catch:{ all -> 0x05a9 }
            monitor-exit(r9)     // Catch:{ all -> 0x05a9 }
        L_0x00e6:
            if (r2 <= 0) goto L_0x00f1
            java.net.Socket r9 = r1.socket     // Catch:{ Exception -> 0x05b0 }
            if (r9 == 0) goto L_0x00f1
            java.net.Socket r9 = r1.socket     // Catch:{ Exception -> 0x05b0 }
            r9.setSoTimeout(r2)     // Catch:{ Exception -> 0x05b0 }
        L_0x00f1:
            r1.isConnected = r4     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.Logger r9 = com.jcraft.jsch.JSch.getLogger()     // Catch:{ Exception -> 0x05b0 }
            boolean r9 = r9.isEnabled(r4)     // Catch:{ Exception -> 0x05b0 }
            if (r9 == 0) goto L_0x0106
            com.jcraft.jsch.Logger r9 = com.jcraft.jsch.JSch.getLogger()     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r10 = "Connection established"
            r9.log(r4, r10)     // Catch:{ Exception -> 0x05b0 }
        L_0x0106:
            com.jcraft.jsch.JSch r9 = r1.jsch     // Catch:{ Exception -> 0x05b0 }
            r9.addSession(r1)     // Catch:{ Exception -> 0x05b0 }
            byte[] r9 = r1.V_C     // Catch:{ Exception -> 0x05b0 }
            int r9 = r9.length     // Catch:{ Exception -> 0x05b0 }
            int r9 = r9 + r4
            byte[] r9 = new byte[r9]     // Catch:{ Exception -> 0x05b0 }
            byte[] r10 = r1.V_C     // Catch:{ Exception -> 0x05b0 }
            byte[] r11 = r1.V_C     // Catch:{ Exception -> 0x05b0 }
            int r11 = r11.length     // Catch:{ Exception -> 0x05b0 }
            java.lang.System.arraycopy(r10, r8, r9, r8, r11)     // Catch:{ Exception -> 0x05b0 }
            int r10 = r9.length     // Catch:{ Exception -> 0x05b0 }
            int r10 = r10 - r4
            r11 = 10
            r9[r10] = r11     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.IO r10 = r1.io     // Catch:{ Exception -> 0x05b0 }
            int r12 = r9.length     // Catch:{ Exception -> 0x05b0 }
            r10.put(r9, r8, r12)     // Catch:{ Exception -> 0x05b0 }
        L_0x0125:
            r9 = 0
            r10 = 0
        L_0x0127:
            com.jcraft.jsch.Buffer r12 = r1.buf     // Catch:{ Exception -> 0x05b0 }
            byte[] r12 = r12.buffer     // Catch:{ Exception -> 0x05b0 }
            int r12 = r12.length     // Catch:{ Exception -> 0x05b0 }
            if (r9 >= r12) goto L_0x0142
            com.jcraft.jsch.IO r10 = r1.io     // Catch:{ Exception -> 0x05b0 }
            int r10 = r10.getByte()     // Catch:{ Exception -> 0x05b0 }
            if (r10 >= 0) goto L_0x0137
            goto L_0x0142
        L_0x0137:
            com.jcraft.jsch.Buffer r12 = r1.buf     // Catch:{ Exception -> 0x05b0 }
            byte[] r12 = r12.buffer     // Catch:{ Exception -> 0x05b0 }
            byte r13 = (byte) r10     // Catch:{ Exception -> 0x05b0 }
            r12[r9] = r13     // Catch:{ Exception -> 0x05b0 }
            int r9 = r9 + 1
            if (r10 != r11) goto L_0x0127
        L_0x0142:
            if (r10 < 0) goto L_0x05a1
            com.jcraft.jsch.Buffer r10 = r1.buf     // Catch:{ Exception -> 0x05b0 }
            byte[] r10 = r10.buffer     // Catch:{ Exception -> 0x05b0 }
            int r12 = r9 + -1
            byte r10 = r10[r12]     // Catch:{ Exception -> 0x05b0 }
            if (r10 != r11) goto L_0x015e
            int r9 = r9 + -1
            if (r9 <= 0) goto L_0x015e
            com.jcraft.jsch.Buffer r10 = r1.buf     // Catch:{ Exception -> 0x05b0 }
            byte[] r10 = r10.buffer     // Catch:{ Exception -> 0x05b0 }
            int r12 = r9 + -1
            byte r10 = r10[r12]     // Catch:{ Exception -> 0x05b0 }
            if (r10 != r3) goto L_0x015e
            int r9 = r9 + -1
        L_0x015e:
            if (r9 <= r5) goto L_0x0125
            com.jcraft.jsch.Buffer r10 = r1.buf     // Catch:{ Exception -> 0x05b0 }
            byte[] r10 = r10.buffer     // Catch:{ Exception -> 0x05b0 }
            int r10 = r10.length     // Catch:{ Exception -> 0x05b0 }
            if (r9 == r10) goto L_0x018e
            com.jcraft.jsch.Buffer r10 = r1.buf     // Catch:{ Exception -> 0x05b0 }
            byte[] r10 = r10.buffer     // Catch:{ Exception -> 0x05b0 }
            byte r10 = r10[r8]     // Catch:{ Exception -> 0x05b0 }
            r12 = 83
            if (r10 != r12) goto L_0x0125
            com.jcraft.jsch.Buffer r10 = r1.buf     // Catch:{ Exception -> 0x05b0 }
            byte[] r10 = r10.buffer     // Catch:{ Exception -> 0x05b0 }
            byte r10 = r10[r4]     // Catch:{ Exception -> 0x05b0 }
            if (r10 != r12) goto L_0x0125
            com.jcraft.jsch.Buffer r10 = r1.buf     // Catch:{ Exception -> 0x05b0 }
            byte[] r10 = r10.buffer     // Catch:{ Exception -> 0x05b0 }
            byte r10 = r10[r7]     // Catch:{ Exception -> 0x05b0 }
            r12 = 72
            if (r10 != r12) goto L_0x0125
            com.jcraft.jsch.Buffer r10 = r1.buf     // Catch:{ Exception -> 0x05b0 }
            byte[] r10 = r10.buffer     // Catch:{ Exception -> 0x05b0 }
            byte r10 = r10[r5]     // Catch:{ Exception -> 0x05b0 }
            r12 = 45
            if (r10 == r12) goto L_0x018e
            goto L_0x0125
        L_0x018e:
            com.jcraft.jsch.Buffer r10 = r1.buf     // Catch:{ Exception -> 0x05b0 }
            byte[] r10 = r10.buffer     // Catch:{ Exception -> 0x05b0 }
            int r10 = r10.length     // Catch:{ Exception -> 0x05b0 }
            if (r9 == r10) goto L_0x0599
            r10 = 7
            if (r9 < r10) goto L_0x0599
            com.jcraft.jsch.Buffer r10 = r1.buf     // Catch:{ Exception -> 0x05b0 }
            byte[] r10 = r10.buffer     // Catch:{ Exception -> 0x05b0 }
            r11 = 4
            byte r10 = r10[r11]     // Catch:{ Exception -> 0x05b0 }
            r11 = 49
            if (r10 != r11) goto L_0x01ae
            com.jcraft.jsch.Buffer r10 = r1.buf     // Catch:{ Exception -> 0x05b0 }
            byte[] r10 = r10.buffer     // Catch:{ Exception -> 0x05b0 }
            r11 = 6
            byte r10 = r10[r11]     // Catch:{ Exception -> 0x05b0 }
            r11 = 57
            if (r10 != r11) goto L_0x0599
        L_0x01ae:
            byte[] r10 = new byte[r9]     // Catch:{ Exception -> 0x05b0 }
            r1.V_S = r10     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.Buffer r10 = r1.buf     // Catch:{ Exception -> 0x05b0 }
            byte[] r10 = r10.buffer     // Catch:{ Exception -> 0x05b0 }
            byte[] r11 = r1.V_S     // Catch:{ Exception -> 0x05b0 }
            java.lang.System.arraycopy(r10, r8, r11, r8, r9)     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.Logger r9 = com.jcraft.jsch.JSch.getLogger()     // Catch:{ Exception -> 0x05b0 }
            boolean r9 = r9.isEnabled(r4)     // Catch:{ Exception -> 0x05b0 }
            if (r9 == 0) goto L_0x0201
            com.jcraft.jsch.Logger r9 = com.jcraft.jsch.JSch.getLogger()     // Catch:{ Exception -> 0x05b0 }
            java.lang.StringBuffer r10 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x05b0 }
            r10.<init>()     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r11 = "Remote version string: "
            r10.append(r11)     // Catch:{ Exception -> 0x05b0 }
            byte[] r11 = r1.V_S     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r11 = com.jcraft.jsch.Util.byte2str(r11)     // Catch:{ Exception -> 0x05b0 }
            r10.append(r11)     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x05b0 }
            r9.log(r4, r10)     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.Logger r9 = com.jcraft.jsch.JSch.getLogger()     // Catch:{ Exception -> 0x05b0 }
            java.lang.StringBuffer r10 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x05b0 }
            r10.<init>()     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r11 = "Local version string: "
            r10.append(r11)     // Catch:{ Exception -> 0x05b0 }
            byte[] r11 = r1.V_C     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r11 = com.jcraft.jsch.Util.byte2str(r11)     // Catch:{ Exception -> 0x05b0 }
            r10.append(r11)     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x05b0 }
            r9.log(r4, r10)     // Catch:{ Exception -> 0x05b0 }
        L_0x0201:
            r16.send_kexinit()     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.Buffer r9 = r1.buf     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.Buffer r9 = r1.read(r9)     // Catch:{ Exception -> 0x05b0 }
            r1.buf = r9     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.Buffer r9 = r1.buf     // Catch:{ Exception -> 0x05b0 }
            byte r9 = r9.getCommand()     // Catch:{ Exception -> 0x05b0 }
            r10 = 20
            if (r9 != r10) goto L_0x0579
            com.jcraft.jsch.Logger r9 = com.jcraft.jsch.JSch.getLogger()     // Catch:{ Exception -> 0x05b0 }
            boolean r9 = r9.isEnabled(r4)     // Catch:{ Exception -> 0x05b0 }
            if (r9 == 0) goto L_0x0229
            com.jcraft.jsch.Logger r9 = com.jcraft.jsch.JSch.getLogger()     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r10 = "SSH_MSG_KEXINIT received"
            r9.log(r4, r10)     // Catch:{ Exception -> 0x05b0 }
        L_0x0229:
            com.jcraft.jsch.Buffer r9 = r1.buf     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.KeyExchange r9 = r1.receive_kexinit(r9)     // Catch:{ Exception -> 0x05b0 }
        L_0x022f:
            com.jcraft.jsch.Buffer r10 = r1.buf     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.Buffer r10 = r1.read(r10)     // Catch:{ Exception -> 0x05b0 }
            r1.buf = r10     // Catch:{ Exception -> 0x05b0 }
            int r10 = r9.getState()     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.Buffer r11 = r1.buf     // Catch:{ Exception -> 0x05b0 }
            byte r11 = r11.getCommand()     // Catch:{ Exception -> 0x05b0 }
            if (r10 != r11) goto L_0x0559
            long r10 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x05b0 }
            r1.kex_start_time = r10     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.Buffer r10 = r1.buf     // Catch:{ Exception -> 0x05b0 }
            boolean r10 = r9.next(r10)     // Catch:{ Exception -> 0x05b0 }
            if (r10 == 0) goto L_0x053f
            int r10 = r9.getState()     // Catch:{ Exception -> 0x05b0 }
            if (r10 != 0) goto L_0x022f
            long r10 = java.lang.System.currentTimeMillis()     // Catch:{ JSchException -> 0x0534 }
            r1.in_prompt = r4     // Catch:{ JSchException -> 0x0534 }
            java.lang.String r12 = r1.host     // Catch:{ JSchException -> 0x0534 }
            int r13 = r1.port     // Catch:{ JSchException -> 0x0534 }
            r1.checkHost(r12, r13, r9)     // Catch:{ JSchException -> 0x0534 }
            r1.in_prompt = r8     // Catch:{ JSchException -> 0x0534 }
            long r12 = r1.kex_start_time     // Catch:{ JSchException -> 0x0534 }
            long r14 = java.lang.System.currentTimeMillis()     // Catch:{ JSchException -> 0x0534 }
            long r14 = r14 - r10
            long r12 = r12 + r14
            r1.kex_start_time = r12     // Catch:{ JSchException -> 0x0534 }
            r16.send_newkeys()     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.Buffer r10 = r1.buf     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.Buffer r10 = r1.read(r10)     // Catch:{ Exception -> 0x05b0 }
            r1.buf = r10     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.Buffer r10 = r1.buf     // Catch:{ Exception -> 0x05b0 }
            byte r10 = r10.getCommand()     // Catch:{ Exception -> 0x05b0 }
            r11 = 21
            if (r10 != r11) goto L_0x0514
            com.jcraft.jsch.Logger r10 = com.jcraft.jsch.JSch.getLogger()     // Catch:{ Exception -> 0x05b0 }
            boolean r10 = r10.isEnabled(r4)     // Catch:{ Exception -> 0x05b0 }
            if (r10 == 0) goto L_0x0298
            com.jcraft.jsch.Logger r10 = com.jcraft.jsch.JSch.getLogger()     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r11 = "SSH_MSG_NEWKEYS received"
            r10.log(r4, r11)     // Catch:{ Exception -> 0x05b0 }
        L_0x0298:
            com.jcraft.jsch.Buffer r10 = r1.buf     // Catch:{ Exception -> 0x05b0 }
            r1.receive_newkeys(r10, r9)     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r9 = "MaxAuthTries"
            java.lang.String r9 = r1.getConfig(r9)     // Catch:{ NumberFormatException -> 0x04f5 }
            if (r9 == 0) goto L_0x02ab
            int r9 = java.lang.Integer.parseInt(r9)     // Catch:{ NumberFormatException -> 0x04f5 }
            r1.max_auth_tries = r9     // Catch:{ NumberFormatException -> 0x04f5 }
        L_0x02ab:
            java.lang.String r9 = "userauth.none"
            java.lang.String r9 = r1.getConfig(r9)     // Catch:{ Exception -> 0x04e9 }
            java.lang.Class r9 = java.lang.Class.forName(r9)     // Catch:{ Exception -> 0x04e9 }
            java.lang.Object r9 = r9.newInstance()     // Catch:{ Exception -> 0x04e9 }
            com.jcraft.jsch.UserAuth r9 = (com.jcraft.jsch.UserAuth) r9     // Catch:{ Exception -> 0x04e9 }
            com.jcraft.jsch.UserAuth r9 = (com.jcraft.jsch.UserAuth) r9     // Catch:{ Exception -> 0x04e9 }
            boolean r10 = r9.start(r1)     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r11 = "PreferredAuthentications"
            java.lang.String r11 = r1.getConfig(r11)     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r12 = ","
            java.lang.String[] r12 = com.jcraft.jsch.Util.split(r11, r12)     // Catch:{ Exception -> 0x05b0 }
            if (r10 != 0) goto L_0x02de
            com.jcraft.jsch.UserAuthNone r9 = (com.jcraft.jsch.UserAuthNone) r9     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r9 = r9.getMethods()     // Catch:{ Exception -> 0x05b0 }
            if (r9 == 0) goto L_0x02dc
            java.lang.String r9 = r9.toLowerCase()     // Catch:{ Exception -> 0x05b0 }
            goto L_0x02df
        L_0x02dc:
            r9 = r11
            goto L_0x02df
        L_0x02de:
            r9 = r6
        L_0x02df:
            java.lang.String r11 = ","
            java.lang.String[] r11 = com.jcraft.jsch.Util.split(r9, r11)     // Catch:{ Exception -> 0x05b0 }
            r14 = r9
            r9 = 0
            r13 = 0
        L_0x02e8:
            if (r10 != 0) goto L_0x044d
            if (r12 == 0) goto L_0x044d
            int r15 = r12.length     // Catch:{ Exception -> 0x05b0 }
            if (r9 >= r15) goto L_0x044d
            int r15 = r9 + 1
            r9 = r12[r9]     // Catch:{ Exception -> 0x05b0 }
            r5 = 0
        L_0x02f4:
            int r3 = r11.length     // Catch:{ Exception -> 0x05b0 }
            if (r5 >= r3) goto L_0x0304
            r3 = r11[r5]     // Catch:{ Exception -> 0x05b0 }
            boolean r3 = r3.equals(r9)     // Catch:{ Exception -> 0x05b0 }
            if (r3 == 0) goto L_0x0301
            r3 = 1
            goto L_0x0305
        L_0x0301:
            int r5 = r5 + 1
            goto L_0x02f4
        L_0x0304:
            r3 = 0
        L_0x0305:
            if (r3 != 0) goto L_0x030c
            r9 = r15
            r3 = 13
            r5 = 3
            goto L_0x02e8
        L_0x030c:
            com.jcraft.jsch.Logger r3 = com.jcraft.jsch.JSch.getLogger()     // Catch:{ Exception -> 0x05b0 }
            boolean r3 = r3.isEnabled(r4)     // Catch:{ Exception -> 0x05b0 }
            if (r3 == 0) goto L_0x0364
            java.lang.String r3 = "Authentications that can continue: "
            int r5 = r15 + -1
        L_0x031a:
            int r8 = r12.length     // Catch:{ Exception -> 0x05b0 }
            if (r5 >= r8) goto L_0x0345
            java.lang.StringBuffer r8 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x05b0 }
            r8.<init>()     // Catch:{ Exception -> 0x05b0 }
            r8.append(r3)     // Catch:{ Exception -> 0x05b0 }
            r3 = r12[r5]     // Catch:{ Exception -> 0x05b0 }
            r8.append(r3)     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r3 = r8.toString()     // Catch:{ Exception -> 0x05b0 }
            int r5 = r5 + 1
            int r8 = r12.length     // Catch:{ Exception -> 0x05b0 }
            if (r5 >= r8) goto L_0x031a
            java.lang.StringBuffer r8 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x05b0 }
            r8.<init>()     // Catch:{ Exception -> 0x05b0 }
            r8.append(r3)     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r3 = ","
            r8.append(r3)     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r3 = r8.toString()     // Catch:{ Exception -> 0x05b0 }
            goto L_0x031a
        L_0x0345:
            com.jcraft.jsch.Logger r5 = com.jcraft.jsch.JSch.getLogger()     // Catch:{ Exception -> 0x05b0 }
            r5.log(r4, r3)     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.Logger r3 = com.jcraft.jsch.JSch.getLogger()     // Catch:{ Exception -> 0x05b0 }
            java.lang.StringBuffer r5 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x05b0 }
            r5.<init>()     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r8 = "Next authentication method: "
            r5.append(r8)     // Catch:{ Exception -> 0x05b0 }
            r5.append(r9)     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x05b0 }
            r3.log(r4, r5)     // Catch:{ Exception -> 0x05b0 }
        L_0x0364:
            java.lang.StringBuffer r3 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x039f }
            r3.<init>()     // Catch:{ Exception -> 0x039f }
            java.lang.String r5 = "userauth."
            r3.append(r5)     // Catch:{ Exception -> 0x039f }
            r3.append(r9)     // Catch:{ Exception -> 0x039f }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x039f }
            java.lang.String r3 = r1.getConfig(r3)     // Catch:{ Exception -> 0x039f }
            if (r3 == 0) goto L_0x039d
            java.lang.StringBuffer r3 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x039f }
            r3.<init>()     // Catch:{ Exception -> 0x039f }
            java.lang.String r5 = "userauth."
            r3.append(r5)     // Catch:{ Exception -> 0x039f }
            r3.append(r9)     // Catch:{ Exception -> 0x039f }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x039f }
            java.lang.String r3 = r1.getConfig(r3)     // Catch:{ Exception -> 0x039f }
            java.lang.Class r3 = java.lang.Class.forName(r3)     // Catch:{ Exception -> 0x039f }
            java.lang.Object r3 = r3.newInstance()     // Catch:{ Exception -> 0x039f }
            com.jcraft.jsch.UserAuth r3 = (com.jcraft.jsch.UserAuth) r3     // Catch:{ Exception -> 0x039f }
            com.jcraft.jsch.UserAuth r3 = (com.jcraft.jsch.UserAuth) r3     // Catch:{ Exception -> 0x039f }
            goto L_0x03c7
        L_0x039d:
            r3 = r6
            goto L_0x03c7
        L_0x039f:
            com.jcraft.jsch.Logger r3 = com.jcraft.jsch.JSch.getLogger()     // Catch:{ Exception -> 0x05b0 }
            boolean r3 = r3.isEnabled(r7)     // Catch:{ Exception -> 0x05b0 }
            if (r3 == 0) goto L_0x03c6
            com.jcraft.jsch.Logger r3 = com.jcraft.jsch.JSch.getLogger()     // Catch:{ Exception -> 0x05b0 }
            java.lang.StringBuffer r5 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x05b0 }
            r5.<init>()     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r8 = "failed to load "
            r5.append(r8)     // Catch:{ Exception -> 0x05b0 }
            r5.append(r9)     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r8 = " method"
            r5.append(r8)     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x05b0 }
            r3.log(r7, r5)     // Catch:{ Exception -> 0x05b0 }
        L_0x03c6:
            r3 = r6
        L_0x03c7:
            if (r3 == 0) goto L_0x0446
            boolean r10 = r3.start(r1)     // Catch:{ JSchAuthCancelException -> 0x0445, JSchPartialAuthException -> 0x0428, RuntimeException -> 0x0425, JSchException -> 0x0422, Exception -> 0x03f8 }
            if (r10 == 0) goto L_0x03f6
            com.jcraft.jsch.Logger r3 = com.jcraft.jsch.JSch.getLogger()     // Catch:{ JSchAuthCancelException -> 0x0445, JSchPartialAuthException -> 0x0428, RuntimeException -> 0x0425, JSchException -> 0x0422, Exception -> 0x03f8 }
            boolean r3 = r3.isEnabled(r4)     // Catch:{ JSchAuthCancelException -> 0x0445, JSchPartialAuthException -> 0x0428, RuntimeException -> 0x0425, JSchException -> 0x0422, Exception -> 0x03f8 }
            if (r3 == 0) goto L_0x03f6
            com.jcraft.jsch.Logger r3 = com.jcraft.jsch.JSch.getLogger()     // Catch:{ JSchAuthCancelException -> 0x0445, JSchPartialAuthException -> 0x0428, RuntimeException -> 0x0425, JSchException -> 0x0422, Exception -> 0x03f8 }
            java.lang.StringBuffer r5 = new java.lang.StringBuffer     // Catch:{ JSchAuthCancelException -> 0x0445, JSchPartialAuthException -> 0x0428, RuntimeException -> 0x0425, JSchException -> 0x0422, Exception -> 0x03f8 }
            r5.<init>()     // Catch:{ JSchAuthCancelException -> 0x0445, JSchPartialAuthException -> 0x0428, RuntimeException -> 0x0425, JSchException -> 0x0422, Exception -> 0x03f8 }
            java.lang.String r8 = "Authentication succeeded ("
            r5.append(r8)     // Catch:{ JSchAuthCancelException -> 0x0445, JSchPartialAuthException -> 0x0428, RuntimeException -> 0x0425, JSchException -> 0x0422, Exception -> 0x03f8 }
            r5.append(r9)     // Catch:{ JSchAuthCancelException -> 0x0445, JSchPartialAuthException -> 0x0428, RuntimeException -> 0x0425, JSchException -> 0x0422, Exception -> 0x03f8 }
            java.lang.String r8 = ")."
            r5.append(r8)     // Catch:{ JSchAuthCancelException -> 0x0445, JSchPartialAuthException -> 0x0428, RuntimeException -> 0x0425, JSchException -> 0x0422, Exception -> 0x03f8 }
            java.lang.String r5 = r5.toString()     // Catch:{ JSchAuthCancelException -> 0x0445, JSchPartialAuthException -> 0x0428, RuntimeException -> 0x0425, JSchException -> 0x0422, Exception -> 0x03f8 }
            r3.log(r4, r5)     // Catch:{ JSchAuthCancelException -> 0x0445, JSchPartialAuthException -> 0x0428, RuntimeException -> 0x0425, JSchException -> 0x0422, Exception -> 0x03f8 }
        L_0x03f6:
            r13 = 0
            goto L_0x0446
        L_0x03f8:
            r0 = move-exception
            r3 = r0
            com.jcraft.jsch.Logger r5 = com.jcraft.jsch.JSch.getLogger()     // Catch:{ Exception -> 0x05b0 }
            boolean r5 = r5.isEnabled(r7)     // Catch:{ Exception -> 0x05b0 }
            if (r5 == 0) goto L_0x0420
            com.jcraft.jsch.Logger r5 = com.jcraft.jsch.JSch.getLogger()     // Catch:{ Exception -> 0x05b0 }
            java.lang.StringBuffer r8 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x05b0 }
            r8.<init>()     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r9 = "an exception during authentication\n"
            r8.append(r9)     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x05b0 }
            r8.append(r3)     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r3 = r8.toString()     // Catch:{ Exception -> 0x05b0 }
            r5.log(r7, r3)     // Catch:{ Exception -> 0x05b0 }
        L_0x0420:
            r13 = 0
            goto L_0x044d
        L_0x0422:
            r0 = move-exception
            r2 = r0
            throw r2     // Catch:{ Exception -> 0x05b0 }
        L_0x0425:
            r0 = move-exception
            r2 = r0
            throw r2     // Catch:{ Exception -> 0x05b0 }
        L_0x0428:
            r0 = move-exception
            r3 = r0
            java.lang.String r3 = r3.getMethods()     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r5 = ","
            java.lang.String[] r11 = com.jcraft.jsch.Util.split(r3, r5)     // Catch:{ Exception -> 0x05b0 }
            boolean r5 = r14.equals(r3)     // Catch:{ Exception -> 0x05b0 }
            if (r5 != 0) goto L_0x043c
            r9 = 0
            goto L_0x043d
        L_0x043c:
            r9 = r15
        L_0x043d:
            r14 = r3
            r3 = 13
            r5 = 3
            r8 = 0
            r13 = 0
            goto L_0x02e8
        L_0x0445:
            r13 = 1
        L_0x0446:
            r9 = r15
            r3 = 13
            r5 = 3
            r8 = 0
            goto L_0x02e8
        L_0x044d:
            if (r10 != 0) goto L_0x048b
            int r2 = r1.auth_failures     // Catch:{ Exception -> 0x05b0 }
            int r3 = r1.max_auth_tries     // Catch:{ Exception -> 0x05b0 }
            if (r2 < r3) goto L_0x0479
            com.jcraft.jsch.Logger r2 = com.jcraft.jsch.JSch.getLogger()     // Catch:{ Exception -> 0x05b0 }
            boolean r2 = r2.isEnabled(r4)     // Catch:{ Exception -> 0x05b0 }
            if (r2 == 0) goto L_0x0479
            com.jcraft.jsch.Logger r2 = com.jcraft.jsch.JSch.getLogger()     // Catch:{ Exception -> 0x05b0 }
            java.lang.StringBuffer r3 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x05b0 }
            r3.<init>()     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r5 = "Login trials exceeds "
            r3.append(r5)     // Catch:{ Exception -> 0x05b0 }
            int r5 = r1.max_auth_tries     // Catch:{ Exception -> 0x05b0 }
            r3.append(r5)     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x05b0 }
            r2.log(r4, r3)     // Catch:{ Exception -> 0x05b0 }
        L_0x0479:
            if (r13 == 0) goto L_0x0483
            com.jcraft.jsch.JSchException r2 = new com.jcraft.jsch.JSchException     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r3 = "Auth cancel"
            r2.<init>(r3)     // Catch:{ Exception -> 0x05b0 }
            throw r2     // Catch:{ Exception -> 0x05b0 }
        L_0x0483:
            com.jcraft.jsch.JSchException r2 = new com.jcraft.jsch.JSchException     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r3 = "Auth fail"
            r2.<init>(r3)     // Catch:{ Exception -> 0x05b0 }
            throw r2     // Catch:{ Exception -> 0x05b0 }
        L_0x048b:
            java.net.Socket r3 = r1.socket     // Catch:{ Exception -> 0x05b0 }
            if (r3 == 0) goto L_0x049c
            if (r2 > 0) goto L_0x0495
            int r2 = r1.timeout     // Catch:{ Exception -> 0x05b0 }
            if (r2 <= 0) goto L_0x049c
        L_0x0495:
            java.net.Socket r2 = r1.socket     // Catch:{ Exception -> 0x05b0 }
            int r3 = r1.timeout     // Catch:{ Exception -> 0x05b0 }
            r2.setSoTimeout(r3)     // Catch:{ Exception -> 0x05b0 }
        L_0x049c:
            r1.isAuthed = r4     // Catch:{ Exception -> 0x05b0 }
            java.lang.Object r2 = r1.lock     // Catch:{ Exception -> 0x05b0 }
            monitor-enter(r2)     // Catch:{ Exception -> 0x05b0 }
            boolean r3 = r1.isConnected     // Catch:{ all -> 0x04e5 }
            if (r3 == 0) goto L_0x04dc
            java.lang.Thread r3 = new java.lang.Thread     // Catch:{ all -> 0x04e5 }
            r3.<init>(r1)     // Catch:{ all -> 0x04e5 }
            r1.connectThread = r3     // Catch:{ all -> 0x04e5 }
            java.lang.Thread r3 = r1.connectThread     // Catch:{ all -> 0x04e5 }
            java.lang.StringBuffer r5 = new java.lang.StringBuffer     // Catch:{ all -> 0x04e5 }
            r5.<init>()     // Catch:{ all -> 0x04e5 }
            java.lang.String r8 = "Connect thread "
            r5.append(r8)     // Catch:{ all -> 0x04e5 }
            java.lang.String r8 = r1.host     // Catch:{ all -> 0x04e5 }
            r5.append(r8)     // Catch:{ all -> 0x04e5 }
            java.lang.String r8 = " session"
            r5.append(r8)     // Catch:{ all -> 0x04e5 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x04e5 }
            r3.setName(r5)     // Catch:{ all -> 0x04e5 }
            boolean r3 = r1.daemon_thread     // Catch:{ all -> 0x04e5 }
            if (r3 == 0) goto L_0x04d4
            java.lang.Thread r3 = r1.connectThread     // Catch:{ all -> 0x04e5 }
            boolean r5 = r1.daemon_thread     // Catch:{ all -> 0x04e5 }
            r3.setDaemon(r5)     // Catch:{ all -> 0x04e5 }
        L_0x04d4:
            java.lang.Thread r3 = r1.connectThread     // Catch:{ all -> 0x04e5 }
            r3.start()     // Catch:{ all -> 0x04e5 }
            r16.requestPortForwarding()     // Catch:{ all -> 0x04e5 }
        L_0x04dc:
            monitor-exit(r2)     // Catch:{ all -> 0x04e5 }
            byte[] r2 = r1.password
            com.jcraft.jsch.Util.bzero(r2)
            r1.password = r6
            return
        L_0x04e5:
            r0 = move-exception
            r3 = r0
            monitor-exit(r2)     // Catch:{ all -> 0x04e5 }
            throw r3     // Catch:{ Exception -> 0x05b0 }
        L_0x04e9:
            r0 = move-exception
            r2 = r0
            com.jcraft.jsch.JSchException r3 = new com.jcraft.jsch.JSchException     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r5 = r2.toString()     // Catch:{ Exception -> 0x05b0 }
            r3.<init>(r5, r2)     // Catch:{ Exception -> 0x05b0 }
            throw r3     // Catch:{ Exception -> 0x05b0 }
        L_0x04f5:
            r0 = move-exception
            r2 = r0
            com.jcraft.jsch.JSchException r3 = new com.jcraft.jsch.JSchException     // Catch:{ Exception -> 0x05b0 }
            java.lang.StringBuffer r5 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x05b0 }
            r5.<init>()     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r8 = "MaxAuthTries: "
            r5.append(r8)     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r8 = "MaxAuthTries"
            java.lang.String r8 = r1.getConfig(r8)     // Catch:{ Exception -> 0x05b0 }
            r5.append(r8)     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x05b0 }
            r3.<init>(r5, r2)     // Catch:{ Exception -> 0x05b0 }
            throw r3     // Catch:{ Exception -> 0x05b0 }
        L_0x0514:
            r2 = 0
            r1.in_kex = r2     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.JSchException r2 = new com.jcraft.jsch.JSchException     // Catch:{ Exception -> 0x05b0 }
            java.lang.StringBuffer r3 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x05b0 }
            r3.<init>()     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r5 = "invalid protocol(newkyes): "
            r3.append(r5)     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.Buffer r5 = r1.buf     // Catch:{ Exception -> 0x05b0 }
            byte r5 = r5.getCommand()     // Catch:{ Exception -> 0x05b0 }
            r3.append(r5)     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x05b0 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x05b0 }
            throw r2     // Catch:{ Exception -> 0x05b0 }
        L_0x0534:
            r0 = move-exception
            r2 = r0
            r3 = 0
            r1.in_kex = r3     // Catch:{ Exception -> 0x053c }
            r1.in_prompt = r3     // Catch:{ Exception -> 0x053c }
            throw r2     // Catch:{ Exception -> 0x05b0 }
        L_0x053c:
            r0 = move-exception
            r2 = r0
            goto L_0x05b3
        L_0x053f:
            r2 = 0
            r1.in_kex = r2     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.JSchException r2 = new com.jcraft.jsch.JSchException     // Catch:{ Exception -> 0x05b0 }
            java.lang.StringBuffer r3 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x05b0 }
            r3.<init>()     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r5 = "verify: "
            r3.append(r5)     // Catch:{ Exception -> 0x05b0 }
            r3.append(r10)     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x05b0 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x05b0 }
            throw r2     // Catch:{ Exception -> 0x05b0 }
        L_0x0559:
            r2 = 0
            r1.in_kex = r2     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.JSchException r2 = new com.jcraft.jsch.JSchException     // Catch:{ Exception -> 0x05b0 }
            java.lang.StringBuffer r3 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x05b0 }
            r3.<init>()     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r5 = "invalid protocol(kex): "
            r3.append(r5)     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.Buffer r5 = r1.buf     // Catch:{ Exception -> 0x05b0 }
            byte r5 = r5.getCommand()     // Catch:{ Exception -> 0x05b0 }
            r3.append(r5)     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x05b0 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x05b0 }
            throw r2     // Catch:{ Exception -> 0x05b0 }
        L_0x0579:
            r2 = 0
            r1.in_kex = r2     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.JSchException r2 = new com.jcraft.jsch.JSchException     // Catch:{ Exception -> 0x05b0 }
            java.lang.StringBuffer r3 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x05b0 }
            r3.<init>()     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r5 = "invalid protocol: "
            r3.append(r5)     // Catch:{ Exception -> 0x05b0 }
            com.jcraft.jsch.Buffer r5 = r1.buf     // Catch:{ Exception -> 0x05b0 }
            byte r5 = r5.getCommand()     // Catch:{ Exception -> 0x05b0 }
            r3.append(r5)     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x05b0 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x05b0 }
            throw r2     // Catch:{ Exception -> 0x05b0 }
        L_0x0599:
            com.jcraft.jsch.JSchException r2 = new com.jcraft.jsch.JSchException     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r3 = "invalid server's version string"
            r2.<init>(r3)     // Catch:{ Exception -> 0x05b0 }
            throw r2     // Catch:{ Exception -> 0x05b0 }
        L_0x05a1:
            com.jcraft.jsch.JSchException r2 = new com.jcraft.jsch.JSchException     // Catch:{ Exception -> 0x05b0 }
            java.lang.String r3 = "connection is closed by foreign host"
            r2.<init>(r3)     // Catch:{ Exception -> 0x05b0 }
            throw r2     // Catch:{ Exception -> 0x05b0 }
        L_0x05a9:
            r0 = move-exception
            r2 = r0
            monitor-exit(r9)     // Catch:{ all -> 0x05a9 }
            throw r2     // Catch:{ Exception -> 0x05b0 }
        L_0x05ad:
            r0 = move-exception
            r2 = r0
            goto L_0x0620
        L_0x05b0:
            r0 = move-exception
            r2 = r0
            r3 = 0
        L_0x05b3:
            r1.in_kex = r3     // Catch:{ all -> 0x05ad }
            boolean r3 = r1.isConnected     // Catch:{ Exception -> 0x05f5 }
            if (r3 == 0) goto L_0x05f5
            java.lang.String r3 = r2.toString()     // Catch:{ Exception -> 0x05f5 }
            com.jcraft.jsch.Packet r5 = r1.packet     // Catch:{ Exception -> 0x05f5 }
            r5.reset()     // Catch:{ Exception -> 0x05f5 }
            com.jcraft.jsch.Buffer r5 = r1.buf     // Catch:{ Exception -> 0x05f5 }
            int r8 = r3.length()     // Catch:{ Exception -> 0x05f5 }
            r9 = 13
            int r8 = r8 + r9
            int r8 = r8 + r7
            int r8 = r8 + 128
            r5.checkFreeSize(r8)     // Catch:{ Exception -> 0x05f5 }
            com.jcraft.jsch.Buffer r5 = r1.buf     // Catch:{ Exception -> 0x05f5 }
            r5.putByte((byte) r4)     // Catch:{ Exception -> 0x05f5 }
            com.jcraft.jsch.Buffer r4 = r1.buf     // Catch:{ Exception -> 0x05f5 }
            r5 = 3
            r4.putInt(r5)     // Catch:{ Exception -> 0x05f5 }
            com.jcraft.jsch.Buffer r4 = r1.buf     // Catch:{ Exception -> 0x05f5 }
            byte[] r3 = com.jcraft.jsch.Util.str2byte(r3)     // Catch:{ Exception -> 0x05f5 }
            r4.putString(r3)     // Catch:{ Exception -> 0x05f5 }
            com.jcraft.jsch.Buffer r3 = r1.buf     // Catch:{ Exception -> 0x05f5 }
            java.lang.String r4 = "en"
            byte[] r4 = com.jcraft.jsch.Util.str2byte(r4)     // Catch:{ Exception -> 0x05f5 }
            r3.putString(r4)     // Catch:{ Exception -> 0x05f5 }
            com.jcraft.jsch.Packet r3 = r1.packet     // Catch:{ Exception -> 0x05f5 }
            r1.write(r3)     // Catch:{ Exception -> 0x05f5 }
        L_0x05f5:
            r16.disconnect()     // Catch:{ Exception -> 0x05f8 }
        L_0x05f8:
            r3 = 0
            r1.isConnected = r3     // Catch:{ all -> 0x05ad }
            boolean r3 = r2 instanceof java.lang.RuntimeException     // Catch:{ all -> 0x05ad }
            if (r3 != 0) goto L_0x061d
            boolean r3 = r2 instanceof com.jcraft.jsch.JSchException     // Catch:{ all -> 0x05ad }
            if (r3 == 0) goto L_0x0606
            com.jcraft.jsch.JSchException r2 = (com.jcraft.jsch.JSchException) r2     // Catch:{ all -> 0x05ad }
            throw r2     // Catch:{ all -> 0x05ad }
        L_0x0606:
            com.jcraft.jsch.JSchException r3 = new com.jcraft.jsch.JSchException     // Catch:{ all -> 0x05ad }
            java.lang.StringBuffer r4 = new java.lang.StringBuffer     // Catch:{ all -> 0x05ad }
            r4.<init>()     // Catch:{ all -> 0x05ad }
            java.lang.String r5 = "Session.connect: "
            r4.append(r5)     // Catch:{ all -> 0x05ad }
            r4.append(r2)     // Catch:{ all -> 0x05ad }
            java.lang.String r2 = r4.toString()     // Catch:{ all -> 0x05ad }
            r3.<init>(r2)     // Catch:{ all -> 0x05ad }
            throw r3     // Catch:{ all -> 0x05ad }
        L_0x061d:
            java.lang.RuntimeException r2 = (java.lang.RuntimeException) r2     // Catch:{ all -> 0x05ad }
            throw r2     // Catch:{ all -> 0x05ad }
        L_0x0620:
            byte[] r3 = r1.password
            com.jcraft.jsch.Util.bzero(r3)
            r1.password = r6
            throw r2
        L_0x0628:
            com.jcraft.jsch.JSchException r2 = new com.jcraft.jsch.JSchException
            java.lang.String r3 = "session is already connected"
            r2.<init>(r3)
            throw r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.Session.connect(int):void");
    }

    private KeyExchange receive_kexinit(Buffer buffer) throws Exception {
        int i = buffer.getInt();
        if (i != buffer.getLength()) {
            buffer.getByte();
            this.I_S = new byte[(buffer.index - 5)];
        } else {
            this.I_S = new byte[((i - 1) - buffer.getByte())];
        }
        byte[] bArr = buffer.buffer;
        int i2 = buffer.s;
        byte[] bArr2 = this.I_S;
        System.arraycopy(bArr, i2, bArr2, 0, bArr2.length);
        if (!this.in_kex) {
            send_kexinit();
        }
        this.guess = KeyExchange.guess(this.I_S, this.I_C);
        String[] strArr = this.guess;
        if (strArr == null) {
            throw new JSchException("Algorithm negotiation fail");
        } else if (this.isAuthed || (!strArr[2].equals("none") && !this.guess[3].equals("none"))) {
            try {
                KeyExchange keyExchange = (KeyExchange) Class.forName(getConfig(this.guess[0])).newInstance();
                keyExchange.init(this, this.V_S, this.V_C, this.I_S, this.I_C);
                return keyExchange;
            } catch (Exception e) {
                throw new JSchException(e.toString(), e);
            }
        } else {
            throw new JSchException("NONE Cipher should not be chosen before authentification is successed.");
        }
    }

    public void rekey() throws Exception {
        send_kexinit();
    }

    private void send_kexinit() throws Exception {
        if (!this.in_kex) {
            String config2 = getConfig("cipher.c2s");
            String config3 = getConfig("cipher.s2c");
            String[] checkCiphers = checkCiphers(getConfig("CheckCiphers"));
            if (checkCiphers != null && checkCiphers.length > 0) {
                config2 = Util.diffString(config2, checkCiphers);
                config3 = Util.diffString(config3, checkCiphers);
                if (config2 == null || config3 == null) {
                    throw new JSchException("There are not any available ciphers.");
                }
            }
            String config4 = getConfig("kex");
            String[] checkKexes = checkKexes(getConfig("CheckKexes"));
            if (checkKexes == null || checkKexes.length <= 0 || (config4 = Util.diffString(config4, checkKexes)) != null) {
                String config5 = getConfig("server_host_key");
                String[] checkSignatures = checkSignatures(getConfig("CheckSignatures"));
                if (checkSignatures == null || checkSignatures.length <= 0 || (config5 = Util.diffString(config5, checkSignatures)) != null) {
                    this.in_kex = true;
                    this.kex_start_time = System.currentTimeMillis();
                    Buffer buffer = new Buffer();
                    Packet packet2 = new Packet(buffer);
                    packet2.reset();
                    buffer.putByte((byte) 20);
                    synchronized (random) {
                        random.fill(buffer.buffer, buffer.index, 16);
                        buffer.skip(16);
                    }
                    buffer.putString(Util.str2byte(config4));
                    buffer.putString(Util.str2byte(config5));
                    buffer.putString(Util.str2byte(config2));
                    buffer.putString(Util.str2byte(config3));
                    buffer.putString(Util.str2byte(getConfig("mac.c2s")));
                    buffer.putString(Util.str2byte(getConfig("mac.s2c")));
                    buffer.putString(Util.str2byte(getConfig("compression.c2s")));
                    buffer.putString(Util.str2byte(getConfig("compression.s2c")));
                    buffer.putString(Util.str2byte(getConfig("lang.c2s")));
                    buffer.putString(Util.str2byte(getConfig("lang.s2c")));
                    buffer.putByte((byte) 0);
                    buffer.putInt(0);
                    buffer.setOffSet(5);
                    this.I_C = new byte[buffer.getLength()];
                    buffer.getByte(this.I_C);
                    write(packet2);
                    if (JSch.getLogger().isEnabled(1)) {
                        JSch.getLogger().log(1, "SSH_MSG_KEXINIT sent");
                        return;
                    }
                    return;
                }
                throw new JSchException("There are not any available sig algorithm.");
            }
            throw new JSchException("There are not any available kexes.");
        }
    }

    private void send_newkeys() throws Exception {
        this.packet.reset();
        this.buf.putByte((byte) 21);
        write(this.packet);
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "SSH_MSG_NEWKEYS sent");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x00f5  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0104  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void checkHost(java.lang.String r12, int r13, com.jcraft.jsch.KeyExchange r14) throws com.jcraft.jsch.JSchException {
        /*
            r11 = this;
            java.lang.String r0 = "StrictHostKeyChecking"
            java.lang.String r0 = r11.getConfig(r0)
            java.lang.String r1 = r11.hostKeyAlias
            if (r1 == 0) goto L_0x000b
            r12 = r1
        L_0x000b:
            byte[] r1 = r14.getHostKey()
            java.lang.String r2 = r14.getKeyType()
            java.lang.String r3 = r14.getFingerPrint()
            java.lang.String r4 = r11.hostKeyAlias
            if (r4 != 0) goto L_0x0038
            r4 = 22
            if (r13 == r4) goto L_0x0038
            java.lang.StringBuffer r4 = new java.lang.StringBuffer
            r4.<init>()
            java.lang.String r5 = "["
            r4.append(r5)
            r4.append(r12)
            java.lang.String r12 = "]:"
            r4.append(r12)
            r4.append(r13)
            java.lang.String r12 = r4.toString()
        L_0x0038:
            com.jcraft.jsch.HostKeyRepository r13 = r11.getHostKeyRepository()
            java.lang.String r4 = "HashKnownHosts"
            java.lang.String r4 = r11.getConfig(r4)
            java.lang.String r5 = "yes"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0058
            boolean r4 = r13 instanceof com.jcraft.jsch.KnownHosts
            if (r4 == 0) goto L_0x0058
            r4 = r13
            com.jcraft.jsch.KnownHosts r4 = (com.jcraft.jsch.KnownHosts) r4
            com.jcraft.jsch.HostKey r4 = r4.createHashedHostKey(r12, r1)
            r11.hostkey = r4
            goto L_0x005f
        L_0x0058:
            com.jcraft.jsch.HostKey r4 = new com.jcraft.jsch.HostKey
            r4.<init>(r12, r1)
            r11.hostkey = r4
        L_0x005f:
            monitor-enter(r13)
            int r4 = r13.check(r12, r1)     // Catch:{ all -> 0x0312 }
            monitor-exit(r13)     // Catch:{ all -> 0x0312 }
            java.lang.String r5 = "ask"
            boolean r5 = r0.equals(r5)
            r6 = 2
            r7 = 0
            r8 = 1
            if (r5 != 0) goto L_0x0078
            java.lang.String r5 = "yes"
            boolean r5 = r0.equals(r5)
            if (r5 == 0) goto L_0x011e
        L_0x0078:
            if (r4 != r6) goto L_0x011e
            monitor-enter(r13)
            java.lang.String r5 = r13.getKnownHostsRepositoryID()     // Catch:{ all -> 0x011b }
            monitor-exit(r13)     // Catch:{ all -> 0x011b }
            if (r5 != 0) goto L_0x0084
            java.lang.String r5 = "known_hosts"
        L_0x0084:
            com.jcraft.jsch.UserInfo r9 = r11.userinfo
            if (r9 == 0) goto L_0x00f2
            java.lang.StringBuffer r9 = new java.lang.StringBuffer
            r9.<init>()
            java.lang.String r10 = "WARNING: REMOTE HOST IDENTIFICATION HAS CHANGED!\nIT IS POSSIBLE THAT SOMEONE IS DOING SOMETHING NASTY!\nSomeone could be eavesdropping on you right now (man-in-the-middle attack)!\nIt is also possible that the "
            r9.append(r10)
            r9.append(r2)
            java.lang.String r10 = " host key has just been changed.\n"
            r9.append(r10)
            java.lang.String r10 = "The fingerprint for the "
            r9.append(r10)
            r9.append(r2)
            java.lang.String r10 = " key sent by the remote host "
            r9.append(r10)
            r9.append(r12)
            java.lang.String r10 = " is\n"
            r9.append(r10)
            r9.append(r3)
            java.lang.String r10 = ".\n"
            r9.append(r10)
            java.lang.String r10 = "Please contact your system administrator.\n"
            r9.append(r10)
            java.lang.String r10 = "Add correct host key in "
            r9.append(r10)
            r9.append(r5)
            java.lang.String r5 = " to get rid of this message."
            r9.append(r5)
            java.lang.String r5 = r9.toString()
            java.lang.String r9 = "ask"
            boolean r9 = r0.equals(r9)
            if (r9 == 0) goto L_0x00ed
            com.jcraft.jsch.UserInfo r9 = r11.userinfo
            java.lang.StringBuffer r10 = new java.lang.StringBuffer
            r10.<init>()
            r10.append(r5)
            java.lang.String r5 = "\nDo you want to delete the old key and insert the new key?"
            r10.append(r5)
            java.lang.String r5 = r10.toString()
            boolean r5 = r9.promptYesNo(r5)
            goto L_0x00f3
        L_0x00ed:
            com.jcraft.jsch.UserInfo r9 = r11.userinfo
            r9.showMessage(r5)
        L_0x00f2:
            r5 = 0
        L_0x00f3:
            if (r5 == 0) goto L_0x0104
            monitor-enter(r13)
            java.lang.String r5 = r14.getKeyAlgorithName()     // Catch:{ all -> 0x0101 }
            r9 = 0
            r13.remove(r12, r5, r9)     // Catch:{ all -> 0x0101 }
            monitor-exit(r13)     // Catch:{ all -> 0x0101 }
            r5 = 1
            goto L_0x011f
        L_0x0101:
            r12 = move-exception
            monitor-exit(r13)     // Catch:{ all -> 0x0101 }
            throw r12
        L_0x0104:
            com.jcraft.jsch.JSchException r13 = new com.jcraft.jsch.JSchException
            java.lang.StringBuffer r14 = new java.lang.StringBuffer
            r14.<init>()
            java.lang.String r0 = "HostKey has been changed: "
            r14.append(r0)
            r14.append(r12)
            java.lang.String r12 = r14.toString()
            r13.<init>(r12)
            throw r13
        L_0x011b:
            r12 = move-exception
            monitor-exit(r13)     // Catch:{ all -> 0x011b }
            throw r12
        L_0x011e:
            r5 = 0
        L_0x011f:
            java.lang.String r9 = "ask"
            boolean r9 = r0.equals(r9)
            if (r9 != 0) goto L_0x012f
            java.lang.String r9 = "yes"
            boolean r9 = r0.equals(r9)
            if (r9 == 0) goto L_0x01ea
        L_0x012f:
            if (r4 == 0) goto L_0x01ea
            if (r5 != 0) goto L_0x01ea
            java.lang.String r5 = "yes"
            boolean r5 = r0.equals(r5)
            if (r5 != 0) goto L_0x01d1
            com.jcraft.jsch.UserInfo r5 = r11.userinfo
            if (r5 == 0) goto L_0x018d
            java.lang.StringBuffer r9 = new java.lang.StringBuffer
            r9.<init>()
            java.lang.String r10 = "The authenticity of host '"
            r9.append(r10)
            java.lang.String r10 = r11.host
            r9.append(r10)
            java.lang.String r10 = "' can't be established.\n"
            r9.append(r10)
            r9.append(r2)
            java.lang.String r10 = " key fingerprint is "
            r9.append(r10)
            r9.append(r3)
            java.lang.String r3 = ".\n"
            r9.append(r3)
            java.lang.String r3 = "Are you sure you want to continue connecting?"
            r9.append(r3)
            java.lang.String r3 = r9.toString()
            boolean r3 = r5.promptYesNo(r3)
            if (r3 == 0) goto L_0x0174
            r5 = 1
            goto L_0x01ea
        L_0x0174:
            com.jcraft.jsch.JSchException r12 = new com.jcraft.jsch.JSchException
            java.lang.StringBuffer r13 = new java.lang.StringBuffer
            r13.<init>()
            java.lang.String r14 = "reject HostKey: "
            r13.append(r14)
            java.lang.String r14 = r11.host
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            r12.<init>(r13)
            throw r12
        L_0x018d:
            if (r4 != r8) goto L_0x01b8
            com.jcraft.jsch.JSchException r12 = new com.jcraft.jsch.JSchException
            java.lang.StringBuffer r13 = new java.lang.StringBuffer
            r13.<init>()
            java.lang.String r14 = "UnknownHostKey: "
            r13.append(r14)
            java.lang.String r14 = r11.host
            r13.append(r14)
            java.lang.String r14 = ". "
            r13.append(r14)
            r13.append(r2)
            java.lang.String r14 = " key fingerprint is "
            r13.append(r14)
            r13.append(r3)
            java.lang.String r13 = r13.toString()
            r12.<init>(r13)
            throw r12
        L_0x01b8:
            com.jcraft.jsch.JSchException r12 = new com.jcraft.jsch.JSchException
            java.lang.StringBuffer r13 = new java.lang.StringBuffer
            r13.<init>()
            java.lang.String r14 = "HostKey has been changed: "
            r13.append(r14)
            java.lang.String r14 = r11.host
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            r12.<init>(r13)
            throw r12
        L_0x01d1:
            com.jcraft.jsch.JSchException r12 = new com.jcraft.jsch.JSchException
            java.lang.StringBuffer r13 = new java.lang.StringBuffer
            r13.<init>()
            java.lang.String r14 = "reject HostKey: "
            r13.append(r14)
            java.lang.String r14 = r11.host
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            r12.<init>(r13)
            throw r12
        L_0x01ea:
            java.lang.String r3 = "no"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x01f5
            if (r8 != r4) goto L_0x01f5
            r5 = 1
        L_0x01f5:
            if (r4 != 0) goto L_0x029c
            java.lang.String r14 = r14.getKeyAlgorithName()
            com.jcraft.jsch.HostKey[] r12 = r13.getHostKey(r12, r14)
            int r14 = r1.length
            byte[] r14 = com.jcraft.jsch.Util.toBase64(r1, r7, r14)
            java.lang.String r14 = com.jcraft.jsch.Util.byte2str(r14)
        L_0x0208:
            int r0 = r12.length
            if (r7 >= r0) goto L_0x029c
            r0 = r12[r4]
            java.lang.String r0 = r0.getKey()
            boolean r0 = r0.equals(r14)
            if (r0 == 0) goto L_0x0298
            r0 = r12[r7]
            java.lang.String r0 = r0.getMarker()
            java.lang.String r1 = "@revoked"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0298
            com.jcraft.jsch.UserInfo r12 = r11.userinfo
            if (r12 == 0) goto L_0x0256
            java.lang.StringBuffer r13 = new java.lang.StringBuffer
            r13.<init>()
            java.lang.String r14 = "The "
            r13.append(r14)
            r13.append(r2)
            java.lang.String r14 = " host key for "
            r13.append(r14)
            java.lang.String r14 = r11.host
            r13.append(r14)
            java.lang.String r14 = " is marked as revoked.\n"
            r13.append(r14)
            java.lang.String r14 = "This could mean that a stolen key is being used to "
            r13.append(r14)
            java.lang.String r14 = "impersonate this host."
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            r12.showMessage(r13)
        L_0x0256:
            com.jcraft.jsch.Logger r12 = com.jcraft.jsch.JSch.getLogger()
            boolean r12 = r12.isEnabled(r8)
            if (r12 == 0) goto L_0x027f
            com.jcraft.jsch.Logger r12 = com.jcraft.jsch.JSch.getLogger()
            java.lang.StringBuffer r13 = new java.lang.StringBuffer
            r13.<init>()
            java.lang.String r14 = "Host '"
            r13.append(r14)
            java.lang.String r14 = r11.host
            r13.append(r14)
            java.lang.String r14 = "' has provided revoked key."
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            r12.log(r8, r13)
        L_0x027f:
            com.jcraft.jsch.JSchException r12 = new com.jcraft.jsch.JSchException
            java.lang.StringBuffer r13 = new java.lang.StringBuffer
            r13.<init>()
            java.lang.String r14 = "revoked HostKey: "
            r13.append(r14)
            java.lang.String r14 = r11.host
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            r12.<init>(r13)
            throw r12
        L_0x0298:
            int r7 = r7 + 1
            goto L_0x0208
        L_0x029c:
            if (r4 != 0) goto L_0x02cf
            com.jcraft.jsch.Logger r12 = com.jcraft.jsch.JSch.getLogger()
            boolean r12 = r12.isEnabled(r8)
            if (r12 == 0) goto L_0x02cf
            com.jcraft.jsch.Logger r12 = com.jcraft.jsch.JSch.getLogger()
            java.lang.StringBuffer r14 = new java.lang.StringBuffer
            r14.<init>()
            java.lang.String r0 = "Host '"
            r14.append(r0)
            java.lang.String r0 = r11.host
            r14.append(r0)
            java.lang.String r0 = "' is known and matches the "
            r14.append(r0)
            r14.append(r2)
            java.lang.String r0 = " host key"
            r14.append(r0)
            java.lang.String r14 = r14.toString()
            r12.log(r8, r14)
        L_0x02cf:
            if (r5 == 0) goto L_0x0302
            com.jcraft.jsch.Logger r12 = com.jcraft.jsch.JSch.getLogger()
            boolean r12 = r12.isEnabled(r6)
            if (r12 == 0) goto L_0x0302
            com.jcraft.jsch.Logger r12 = com.jcraft.jsch.JSch.getLogger()
            java.lang.StringBuffer r14 = new java.lang.StringBuffer
            r14.<init>()
            java.lang.String r0 = "Permanently added '"
            r14.append(r0)
            java.lang.String r0 = r11.host
            r14.append(r0)
            java.lang.String r0 = "' ("
            r14.append(r0)
            r14.append(r2)
            java.lang.String r0 = ") to the list of known hosts."
            r14.append(r0)
            java.lang.String r14 = r14.toString()
            r12.log(r6, r14)
        L_0x0302:
            if (r5 == 0) goto L_0x0311
            monitor-enter(r13)
            com.jcraft.jsch.HostKey r12 = r11.hostkey     // Catch:{ all -> 0x030e }
            com.jcraft.jsch.UserInfo r14 = r11.userinfo     // Catch:{ all -> 0x030e }
            r13.add(r12, r14)     // Catch:{ all -> 0x030e }
            monitor-exit(r13)     // Catch:{ all -> 0x030e }
            goto L_0x0311
        L_0x030e:
            r12 = move-exception
            monitor-exit(r13)     // Catch:{ all -> 0x030e }
            throw r12
        L_0x0311:
            return
        L_0x0312:
            r12 = move-exception
            monitor-exit(r13)     // Catch:{ all -> 0x0312 }
            throw r12
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.Session.checkHost(java.lang.String, int, com.jcraft.jsch.KeyExchange):void");
    }

    public Channel openChannel(String str) throws JSchException {
        if (this.isConnected) {
            try {
                Channel channel = Channel.getChannel(str);
                addChannel(channel);
                channel.init();
                if (channel instanceof ChannelSession) {
                    applyConfigChannel((ChannelSession) channel);
                }
                return channel;
            } catch (Exception unused) {
                return null;
            }
        } else {
            throw new JSchException("session is down");
        }
    }

    public void encode(Packet packet2) throws Exception {
        if (this.deflater != null) {
            this.compress_len[0] = packet2.buffer.index;
            packet2.buffer.buffer = this.deflater.compress(packet2.buffer.buffer, 5, this.compress_len);
            packet2.buffer.index = this.compress_len[0];
        }
        if (this.c2scipher != null) {
            packet2.padding(this.c2scipher_size);
            byte b = packet2.buffer.buffer[4];
            synchronized (random) {
                random.fill(packet2.buffer.buffer, packet2.buffer.index - b, b);
            }
        } else {
            packet2.padding(8);
        }
        MAC mac = this.c2smac;
        if (mac != null) {
            mac.update(this.seqo);
            this.c2smac.update(packet2.buffer.buffer, 0, packet2.buffer.index);
            this.c2smac.doFinal(packet2.buffer.buffer, packet2.buffer.index);
        }
        if (this.c2scipher != null) {
            byte[] bArr = packet2.buffer.buffer;
            this.c2scipher.update(bArr, 0, packet2.buffer.index, bArr, 0);
        }
        if (this.c2smac != null) {
            packet2.buffer.skip(this.c2smac.getBlockSize());
        }
    }

    public Buffer read(Buffer buffer) throws Exception {
        byte b;
        int i;
        Buffer buffer2 = buffer;
        while (true) {
            buffer.reset();
            this.io.getByte(buffer2.buffer, buffer2.index, this.s2ccipher_size);
            buffer2.index += this.s2ccipher_size;
            Cipher cipher = this.s2ccipher;
            if (cipher != null) {
                cipher.update(buffer2.buffer, 0, this.s2ccipher_size, buffer2.buffer, 0);
            }
            byte b2 = ((buffer2.buffer[0] << 24) & -16777216) | ((buffer2.buffer[1] << 16) & 16711680) | ((buffer2.buffer[2] << 8) & 65280) | (buffer2.buffer[3] & 255);
            if (b2 < 5 || b2 > 262144) {
                start_discard(buffer, this.s2ccipher, this.s2cmac, b2, 262144);
            }
            int i2 = (b2 + 4) - this.s2ccipher_size;
            if (buffer2.index + i2 > buffer2.buffer.length) {
                byte[] bArr = new byte[(buffer2.index + i2)];
                System.arraycopy(buffer2.buffer, 0, bArr, 0, buffer2.index);
                buffer2.buffer = bArr;
            }
            if (i2 % this.s2ccipher_size != 0) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Bad packet length ");
                stringBuffer.append(i2);
                String stringBuffer2 = stringBuffer.toString();
                if (JSch.getLogger().isEnabled(4)) {
                    JSch.getLogger().log(4, stringBuffer2);
                }
                b = 4;
                start_discard(buffer, this.s2ccipher, this.s2cmac, b2, 262144 - this.s2ccipher_size);
            } else {
                b = 4;
            }
            if (i2 > 0) {
                this.io.getByte(buffer2.buffer, buffer2.index, i2);
                buffer2.index += i2;
                Cipher cipher2 = this.s2ccipher;
                if (cipher2 != null) {
                    i = i2;
                    cipher2.update(buffer2.buffer, this.s2ccipher_size, i, buffer2.buffer, this.s2ccipher_size);
                } else {
                    i = i2;
                }
            } else {
                i = i2;
            }
            MAC mac = this.s2cmac;
            if (mac != null) {
                mac.update(this.seqi);
                this.s2cmac.update(buffer2.buffer, 0, buffer2.index);
                this.s2cmac.doFinal(this.s2cmac_result1, 0);
                IO io2 = this.io;
                byte[] bArr2 = this.s2cmac_result2;
                io2.getByte(bArr2, 0, bArr2.length);
                if (!Arrays.equals(this.s2cmac_result1, this.s2cmac_result2)) {
                    if (i <= 262144) {
                        start_discard(buffer, this.s2ccipher, this.s2cmac, b2, 262144 - i);
                    } else {
                        throw new IOException("MAC Error");
                    }
                }
            }
            this.seqi++;
            if (this.inflater != null) {
                this.uncompress_len[0] = (buffer2.index - 5) - buffer2.buffer[b];
                byte[] uncompress = this.inflater.uncompress(buffer2.buffer, 5, this.uncompress_len);
                if (uncompress == null) {
                    System.err.println("fail in inflater");
                    break;
                }
                buffer2.buffer = uncompress;
                buffer2.index = this.uncompress_len[0] + 5;
            }
            byte command = buffer.getCommand() & 255;
            if (command == 1) {
                buffer.rewind();
                buffer.getInt();
                buffer.getShort();
                int i3 = buffer.getInt();
                byte[] string = buffer.getString();
                byte[] string2 = buffer.getString();
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("SSH_MSG_DISCONNECT: ");
                stringBuffer3.append(i3);
                stringBuffer3.append(" ");
                stringBuffer3.append(Util.byte2str(string));
                stringBuffer3.append(" ");
                stringBuffer3.append(Util.byte2str(string2));
                throw new JSchException(stringBuffer3.toString());
            } else if (command == 2) {
                continue;
            } else if (command == 3) {
                buffer.rewind();
                buffer.getInt();
                buffer.getShort();
                int i4 = buffer.getInt();
                if (JSch.getLogger().isEnabled(1)) {
                    Logger logger = JSch.getLogger();
                    StringBuffer stringBuffer4 = new StringBuffer();
                    stringBuffer4.append("Received SSH_MSG_UNIMPLEMENTED for ");
                    stringBuffer4.append(i4);
                    logger.log(1, stringBuffer4.toString());
                }
            } else if (command == b) {
                buffer.rewind();
                buffer.getInt();
                buffer.getShort();
            } else if (command == SSH_MSG_CHANNEL_WINDOW_ADJUST) {
                buffer.rewind();
                buffer.getInt();
                buffer.getShort();
                Channel channel = Channel.getChannel(buffer.getInt(), this);
                if (channel != null) {
                    channel.addRemoteWindowSize(buffer.getUInt());
                }
            } else if (command == 52) {
                this.isAuthed = true;
                if (this.inflater == null && this.deflater == null) {
                    initDeflater(this.guess[6]);
                    initInflater(this.guess[7]);
                }
            }
        }
        buffer.rewind();
        return buffer2;
    }

    private void start_discard(Buffer buffer, Cipher cipher, MAC mac, int i, int i2) throws JSchException, IOException {
        if (cipher.isCBC()) {
            if (i == 262144 || mac == null) {
                mac = null;
            }
            int i3 = i2 - buffer.index;
            while (i3 > 0) {
                buffer.reset();
                int length = i3 > buffer.buffer.length ? buffer.buffer.length : i3;
                this.io.getByte(buffer.buffer, 0, length);
                if (mac != null) {
                    mac.update(buffer.buffer, 0, length);
                }
                i3 -= length;
            }
            if (mac != null) {
                mac.doFinal(buffer.buffer, 0);
            }
            throw new JSchException("Packet corrupt");
        }
        throw new JSchException("Packet corrupt");
    }

    /* access modifiers changed from: package-private */
    public byte[] getSessionId() {
        return this.session_id;
    }

    private void receive_newkeys(Buffer buffer, KeyExchange keyExchange) throws Exception {
        updateKeys(keyExchange);
        this.in_kex = false;
    }

    private void updateKeys(KeyExchange keyExchange) throws Exception {
        byte[] k = keyExchange.getK();
        byte[] h = keyExchange.getH();
        HASH hash = keyExchange.getHash();
        if (this.session_id == null) {
            this.session_id = new byte[h.length];
            System.arraycopy(h, 0, this.session_id, 0, h.length);
        }
        this.buf.reset();
        this.buf.putMPInt(k);
        this.buf.putByte(h);
        this.buf.putByte((byte) 65);
        this.buf.putByte(this.session_id);
        hash.update(this.buf.buffer, 0, this.buf.index);
        this.IVc2s = hash.digest();
        int length = (this.buf.index - this.session_id.length) - 1;
        byte[] bArr = this.buf.buffer;
        bArr[length] = (byte) (bArr[length] + 1);
        hash.update(this.buf.buffer, 0, this.buf.index);
        this.IVs2c = hash.digest();
        byte[] bArr2 = this.buf.buffer;
        bArr2[length] = (byte) (bArr2[length] + 1);
        hash.update(this.buf.buffer, 0, this.buf.index);
        this.Ec2s = hash.digest();
        byte[] bArr3 = this.buf.buffer;
        bArr3[length] = (byte) (bArr3[length] + 1);
        hash.update(this.buf.buffer, 0, this.buf.index);
        this.Es2c = hash.digest();
        byte[] bArr4 = this.buf.buffer;
        bArr4[length] = (byte) (bArr4[length] + 1);
        hash.update(this.buf.buffer, 0, this.buf.index);
        this.MACc2s = hash.digest();
        byte[] bArr5 = this.buf.buffer;
        bArr5[length] = (byte) (bArr5[length] + 1);
        hash.update(this.buf.buffer, 0, this.buf.index);
        this.MACs2c = hash.digest();
        try {
            this.s2ccipher = (Cipher) Class.forName(getConfig(this.guess[3])).newInstance();
            while (this.s2ccipher.getBlockSize() > this.Es2c.length) {
                this.buf.reset();
                this.buf.putMPInt(k);
                this.buf.putByte(h);
                this.buf.putByte(this.Es2c);
                hash.update(this.buf.buffer, 0, this.buf.index);
                byte[] digest = hash.digest();
                byte[] bArr6 = new byte[(this.Es2c.length + digest.length)];
                System.arraycopy(this.Es2c, 0, bArr6, 0, this.Es2c.length);
                System.arraycopy(digest, 0, bArr6, this.Es2c.length, digest.length);
                this.Es2c = bArr6;
            }
            this.s2ccipher.init(1, this.Es2c, this.IVs2c);
            this.s2ccipher_size = this.s2ccipher.getIVSize();
            this.s2cmac = (MAC) Class.forName(getConfig(this.guess[5])).newInstance();
            this.MACs2c = expandKey(this.buf, k, h, this.MACs2c, hash, this.s2cmac.getBlockSize());
            this.s2cmac.init(this.MACs2c);
            this.s2cmac_result1 = new byte[this.s2cmac.getBlockSize()];
            this.s2cmac_result2 = new byte[this.s2cmac.getBlockSize()];
            this.c2scipher = (Cipher) Class.forName(getConfig(this.guess[2])).newInstance();
            while (this.c2scipher.getBlockSize() > this.Ec2s.length) {
                this.buf.reset();
                this.buf.putMPInt(k);
                this.buf.putByte(h);
                this.buf.putByte(this.Ec2s);
                hash.update(this.buf.buffer, 0, this.buf.index);
                byte[] digest2 = hash.digest();
                byte[] bArr7 = new byte[(this.Ec2s.length + digest2.length)];
                System.arraycopy(this.Ec2s, 0, bArr7, 0, this.Ec2s.length);
                System.arraycopy(digest2, 0, bArr7, this.Ec2s.length, digest2.length);
                this.Ec2s = bArr7;
            }
            this.c2scipher.init(0, this.Ec2s, this.IVc2s);
            this.c2scipher_size = this.c2scipher.getIVSize();
            this.c2smac = (MAC) Class.forName(getConfig(this.guess[4])).newInstance();
            this.MACc2s = expandKey(this.buf, k, h, this.MACc2s, hash, this.c2smac.getBlockSize());
            this.c2smac.init(this.MACc2s);
            initDeflater(this.guess[6]);
            initInflater(this.guess[7]);
        } catch (Exception e) {
            if (e instanceof JSchException) {
                throw e;
            }
            throw new JSchException(e.toString(), e);
        }
    }

    private byte[] expandKey(Buffer buffer, byte[] bArr, byte[] bArr2, byte[] bArr3, HASH hash, int i) throws Exception {
        int blockSize = hash.getBlockSize();
        while (bArr3.length < i) {
            buffer.reset();
            buffer.putMPInt(bArr);
            buffer.putByte(bArr2);
            buffer.putByte(bArr3);
            hash.update(buffer.buffer, 0, buffer.index);
            byte[] bArr4 = new byte[(bArr3.length + blockSize)];
            System.arraycopy(bArr3, 0, bArr4, 0, bArr3.length);
            System.arraycopy(hash.digest(), 0, bArr4, bArr3.length, blockSize);
            Util.bzero(bArr3);
            bArr3 = bArr4;
        }
        return bArr3;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v0, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v3, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v5, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v8, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v9, resolved type: byte} */
    /* JADX WARNING: type inference failed for: r15v10, types: [int] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0065, code lost:
        if (r14.close != false) goto L_0x00df;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x006b, code lost:
        if (r14.isConnected() == false) goto L_0x00df;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x006d, code lost:
        r5 = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x006e, code lost:
        monitor-enter(r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        r6 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0074, code lost:
        if (r14.rwsize <= 0) goto L_0x00af;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0076, code lost:
        r3 = r14.rwsize;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x007a, code lost:
        if (r3 <= r7) goto L_0x007d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x007c, code lost:
        r3 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x007f, code lost:
        if (r3 == r7) goto L_0x009a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0081, code lost:
        r15 = (int) r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0084, code lost:
        if (r12.c2scipher == null) goto L_0x0089;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0086, code lost:
        r5 = r12.c2scipher_size;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0089, code lost:
        r5 = 8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x008d, code lost:
        if (r12.c2smac == null) goto L_0x0095;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x008f, code lost:
        r6 = r12.c2smac.getBlockSize();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0095, code lost:
        r6 = r13.shift(r15, r5, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x009a, code lost:
        r15 = r13.buffer.getCommand();
        r5 = r14.getRecipient();
        r14.rwsize -= r3;
        r3 = r6;
        r6 = r15;
        r15 = (int) (r7 - r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00af, code lost:
        r2 = false;
        r3 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00b1, code lost:
        monitor-exit(r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00b2, code lost:
        if (r2 == false) goto L_0x00bd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x00b4, code lost:
        _write(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00b7, code lost:
        if (r15 != 0) goto L_0x00ba;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x00b9, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00ba, code lost:
        r13.unshift(r6, r5, r3, r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00bd, code lost:
        monitor-enter(r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x00c0, code lost:
        if (r12.in_kex == false) goto L_0x00c5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x00c2, code lost:
        monitor-exit(r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x00c5, code lost:
        r4 = (long) r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x00ca, code lost:
        if (r14.rwsize < r4) goto L_0x00d6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x00cc, code lost:
        r14.rwsize -= r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x00d1, code lost:
        monitor-exit(r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x00d2, code lost:
        _write(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x00d5, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:?, code lost:
        monitor-exit(r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x00e6, code lost:
        throw new java.io.IOException("channel is broken");
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void write(com.jcraft.jsch.Packet r13, com.jcraft.jsch.Channel r14, int r15) throws java.lang.Exception {
        /*
            r12 = this;
            int r0 = r12.getTimeout()
            long r0 = (long) r0
        L_0x0005:
            boolean r2 = r12.in_kex
            r3 = 0
            if (r2 == 0) goto L_0x002b
            int r2 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r2 <= 0) goto L_0x0023
            long r2 = java.lang.System.currentTimeMillis()
            long r4 = r12.kex_start_time
            long r2 = r2 - r4
            int r4 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r4 > 0) goto L_0x001b
            goto L_0x0023
        L_0x001b:
            com.jcraft.jsch.JSchException r13 = new com.jcraft.jsch.JSchException
            java.lang.String r14 = "timeout in waiting for rekeying process."
            r13.<init>(r14)
            throw r13
        L_0x0023:
            r2 = 10
            java.lang.Thread.sleep(r2)     // Catch:{ InterruptedException -> 0x0029 }
            goto L_0x0005
        L_0x0029:
            goto L_0x0005
        L_0x002b:
            monitor-enter(r14)
            long r5 = r14.rwsize     // Catch:{ all -> 0x00e7 }
            long r7 = (long) r15
            r2 = 1
            int r9 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r9 >= 0) goto L_0x004e
            int r5 = r14.notifyme     // Catch:{ InterruptedException -> 0x004b, all -> 0x0044 }
            int r5 = r5 + r2
            r14.notifyme = r5     // Catch:{ InterruptedException -> 0x004b, all -> 0x0044 }
            r5 = 100
            r14.wait(r5)     // Catch:{ InterruptedException -> 0x004b, all -> 0x0044 }
            int r5 = r14.notifyme     // Catch:{ all -> 0x00e7 }
        L_0x0040:
            int r5 = r5 - r2
            r14.notifyme = r5     // Catch:{ all -> 0x00e7 }
            goto L_0x004e
        L_0x0044:
            r13 = move-exception
            int r15 = r14.notifyme     // Catch:{ all -> 0x00e7 }
            int r15 = r15 - r2
            r14.notifyme = r15     // Catch:{ all -> 0x00e7 }
            throw r13     // Catch:{ all -> 0x00e7 }
        L_0x004b:
            int r5 = r14.notifyme     // Catch:{ all -> 0x00e7 }
            goto L_0x0040
        L_0x004e:
            boolean r5 = r12.in_kex     // Catch:{ all -> 0x00e7 }
            if (r5 == 0) goto L_0x0054
            monitor-exit(r14)     // Catch:{ all -> 0x00e7 }
            goto L_0x0005
        L_0x0054:
            long r5 = r14.rwsize     // Catch:{ all -> 0x00e7 }
            int r9 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r9 < 0) goto L_0x0062
            long r0 = r14.rwsize     // Catch:{ all -> 0x00e7 }
            long r0 = r0 - r7
            r14.rwsize = r0     // Catch:{ all -> 0x00e7 }
            monitor-exit(r14)     // Catch:{ all -> 0x00e7 }
            goto L_0x00d2
        L_0x0062:
            monitor-exit(r14)     // Catch:{ all -> 0x00e7 }
            boolean r5 = r14.close
            if (r5 != 0) goto L_0x00df
            boolean r5 = r14.isConnected()
            if (r5 == 0) goto L_0x00df
            r5 = -1
            monitor-enter(r14)
            long r9 = r14.rwsize     // Catch:{ all -> 0x00dc }
            r6 = 0
            int r11 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r11 <= 0) goto L_0x00af
            long r3 = r14.rwsize     // Catch:{ all -> 0x00dc }
            int r15 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r15 <= 0) goto L_0x007d
            r3 = r7
        L_0x007d:
            int r15 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r15 == 0) goto L_0x009a
            int r15 = (int) r3     // Catch:{ all -> 0x00dc }
            com.jcraft.jsch.Cipher r5 = r12.c2scipher     // Catch:{ all -> 0x00dc }
            if (r5 == 0) goto L_0x0089
            int r5 = r12.c2scipher_size     // Catch:{ all -> 0x00dc }
            goto L_0x008b
        L_0x0089:
            r5 = 8
        L_0x008b:
            com.jcraft.jsch.MAC r9 = r12.c2smac     // Catch:{ all -> 0x00dc }
            if (r9 == 0) goto L_0x0095
            com.jcraft.jsch.MAC r6 = r12.c2smac     // Catch:{ all -> 0x00dc }
            int r6 = r6.getBlockSize()     // Catch:{ all -> 0x00dc }
        L_0x0095:
            int r15 = r13.shift(r15, r5, r6)     // Catch:{ all -> 0x00dc }
            r6 = r15
        L_0x009a:
            com.jcraft.jsch.Buffer r15 = r13.buffer     // Catch:{ all -> 0x00dc }
            byte r15 = r15.getCommand()     // Catch:{ all -> 0x00dc }
            int r5 = r14.getRecipient()     // Catch:{ all -> 0x00dc }
            long r7 = r7 - r3
            int r8 = (int) r7     // Catch:{ all -> 0x00dc }
            long r9 = r14.rwsize     // Catch:{ all -> 0x00dc }
            long r9 = r9 - r3
            r14.rwsize = r9     // Catch:{ all -> 0x00dc }
            r3 = r6
            r6 = r15
            r15 = r8
            goto L_0x00b1
        L_0x00af:
            r2 = 0
            r3 = 0
        L_0x00b1:
            monitor-exit(r14)     // Catch:{ all -> 0x00dc }
            if (r2 == 0) goto L_0x00bd
            r12._write(r13)
            if (r15 != 0) goto L_0x00ba
            return
        L_0x00ba:
            r13.unshift(r6, r5, r3, r15)
        L_0x00bd:
            monitor-enter(r14)
            boolean r2 = r12.in_kex     // Catch:{ all -> 0x00d9 }
            if (r2 == 0) goto L_0x00c5
            monitor-exit(r14)     // Catch:{ all -> 0x00d9 }
            goto L_0x0005
        L_0x00c5:
            long r2 = r14.rwsize     // Catch:{ all -> 0x00d9 }
            long r4 = (long) r15     // Catch:{ all -> 0x00d9 }
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 < 0) goto L_0x00d6
            long r0 = r14.rwsize     // Catch:{ all -> 0x00d9 }
            long r0 = r0 - r4
            r14.rwsize = r0     // Catch:{ all -> 0x00d9 }
            monitor-exit(r14)     // Catch:{ all -> 0x00d9 }
        L_0x00d2:
            r12._write(r13)
            return
        L_0x00d6:
            monitor-exit(r14)     // Catch:{ all -> 0x00d9 }
            goto L_0x0005
        L_0x00d9:
            r13 = move-exception
            monitor-exit(r14)     // Catch:{ all -> 0x00d9 }
            throw r13
        L_0x00dc:
            r13 = move-exception
            monitor-exit(r14)     // Catch:{ all -> 0x00dc }
            throw r13
        L_0x00df:
            java.io.IOException r13 = new java.io.IOException
            java.lang.String r14 = "channel is broken"
            r13.<init>(r14)
            throw r13
        L_0x00e7:
            r13 = move-exception
            monitor-exit(r14)     // Catch:{ all -> 0x00e7 }
            throw r13
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.Session.write(com.jcraft.jsch.Packet, com.jcraft.jsch.Channel, int):void");
    }

    public void write(Packet packet2) throws Exception {
        long timeout2 = (long) getTimeout();
        while (this.in_kex) {
            if (timeout2 <= 0 || System.currentTimeMillis() - this.kex_start_time <= timeout2 || this.in_prompt) {
                byte command = packet2.buffer.getCommand();
                if (command == 20 || command == 21 || command == SSH_MSG_KEXDH_INIT || command == 31 || command == 31 || command == 32 || command == 33 || command == 34 || command == 1) {
                    break;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException unused) {
                }
            } else {
                throw new JSchException("timeout in waiting for rekeying process.");
            }
        }
        _write(packet2);
    }

    private void _write(Packet packet2) throws Exception {
        synchronized (this.lock) {
            encode(packet2);
            if (this.io != null) {
                this.io.put(packet2);
                this.seqo++;
            }
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:95|96) */
    /* JADX WARNING: Code restructure failed: missing block: B:96:?, code lost:
        r8.disconnect();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:95:0x01d0 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r13 = this;
            r13.thread = r13
            com.jcraft.jsch.Buffer r0 = new com.jcraft.jsch.Buffer
            r0.<init>()
            com.jcraft.jsch.Packet r1 = new com.jcraft.jsch.Packet
            r1.<init>(r0)
            r2 = 1
            int[] r3 = new int[r2]
            int[] r4 = new int[r2]
            r5 = 0
            r6 = 0
            r7 = r5
            r8 = 0
        L_0x0015:
            boolean r9 = r13.isConnected     // Catch:{ Exception -> 0x0342 }
            if (r9 == 0) goto L_0x036b
            java.lang.Runnable r9 = r13.thread     // Catch:{ Exception -> 0x0342 }
            if (r9 == 0) goto L_0x036b
            com.jcraft.jsch.Buffer r0 = r13.read(r0)     // Catch:{ InterruptedIOException -> 0x0325 }
            byte r8 = r0.getCommand()     // Catch:{ Exception -> 0x0342 }
            r8 = r8 & 255(0xff, float:3.57E-43)
            if (r7 == 0) goto L_0x0054
            int r9 = r7.getState()     // Catch:{ Exception -> 0x0342 }
            if (r9 != r8) goto L_0x0054
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0342 }
            r13.kex_start_time = r8     // Catch:{ Exception -> 0x0342 }
            boolean r8 = r7.next(r0)     // Catch:{ Exception -> 0x0342 }
            if (r8 == 0) goto L_0x003d
            r8 = 0
            goto L_0x0015
        L_0x003d:
            com.jcraft.jsch.JSchException r0 = new com.jcraft.jsch.JSchException     // Catch:{ Exception -> 0x0342 }
            java.lang.StringBuffer r1 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x0342 }
            r1.<init>()     // Catch:{ Exception -> 0x0342 }
            java.lang.String r3 = "verify: "
            r1.append(r3)     // Catch:{ Exception -> 0x0342 }
            r1.append(r8)     // Catch:{ Exception -> 0x0342 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0342 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x0342 }
            throw r0     // Catch:{ Exception -> 0x0342 }
        L_0x0054:
            switch(r8) {
                case 20: goto L_0x031e;
                case 21: goto L_0x0316;
                default: goto L_0x0057;
            }     // Catch:{ Exception -> 0x0342 }
        L_0x0057:
            switch(r8) {
                case 80: goto L_0x02f6;
                case 81: goto L_0x02c5;
                case 82: goto L_0x02c5;
                default: goto L_0x005a;
            }     // Catch:{ Exception -> 0x0342 }
        L_0x005a:
            r9 = 93
            switch(r8) {
                case 90: goto L_0x0239;
                case 91: goto L_0x0210;
                case 92: goto L_0x01f0;
                case 93: goto L_0x01d5;
                case 94: goto L_0x0172;
                case 95: goto L_0x0114;
                case 96: goto L_0x00ff;
                case 97: goto L_0x00ea;
                case 98: goto L_0x00a2;
                case 99: goto L_0x008c;
                case 100: goto L_0x0076;
                default: goto L_0x005f;
            }     // Catch:{ Exception -> 0x0342 }
        L_0x005f:
            java.io.IOException r0 = new java.io.IOException     // Catch:{ Exception -> 0x0342 }
            java.lang.StringBuffer r1 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x0342 }
            r1.<init>()     // Catch:{ Exception -> 0x0342 }
            java.lang.String r3 = "Unknown SSH message type "
            r1.append(r3)     // Catch:{ Exception -> 0x0342 }
            r1.append(r8)     // Catch:{ Exception -> 0x0342 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0342 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x0342 }
            throw r0     // Catch:{ Exception -> 0x0342 }
        L_0x0076:
            r0.getInt()     // Catch:{ Exception -> 0x0342 }
            r0.getShort()     // Catch:{ Exception -> 0x0342 }
            int r8 = r0.getInt()     // Catch:{ Exception -> 0x0342 }
            com.jcraft.jsch.Channel r8 = com.jcraft.jsch.Channel.getChannel(r8, r13)     // Catch:{ Exception -> 0x0342 }
            if (r8 != 0) goto L_0x0088
            goto L_0x0322
        L_0x0088:
            r8.reply = r6     // Catch:{ Exception -> 0x0342 }
            goto L_0x0322
        L_0x008c:
            r0.getInt()     // Catch:{ Exception -> 0x0342 }
            r0.getShort()     // Catch:{ Exception -> 0x0342 }
            int r8 = r0.getInt()     // Catch:{ Exception -> 0x0342 }
            com.jcraft.jsch.Channel r8 = com.jcraft.jsch.Channel.getChannel(r8, r13)     // Catch:{ Exception -> 0x0342 }
            if (r8 != 0) goto L_0x009e
            goto L_0x0322
        L_0x009e:
            r8.reply = r2     // Catch:{ Exception -> 0x0342 }
            goto L_0x0322
        L_0x00a2:
            r0.getInt()     // Catch:{ Exception -> 0x0342 }
            r0.getShort()     // Catch:{ Exception -> 0x0342 }
            int r8 = r0.getInt()     // Catch:{ Exception -> 0x0342 }
            byte[] r9 = r0.getString()     // Catch:{ Exception -> 0x0342 }
            int r10 = r0.getByte()     // Catch:{ Exception -> 0x0342 }
            if (r10 == 0) goto L_0x00b8
            r10 = 1
            goto L_0x00b9
        L_0x00b8:
            r10 = 0
        L_0x00b9:
            com.jcraft.jsch.Channel r8 = com.jcraft.jsch.Channel.getChannel(r8, r13)     // Catch:{ Exception -> 0x0342 }
            if (r8 == 0) goto L_0x0322
            r11 = 100
            java.lang.String r9 = com.jcraft.jsch.Util.byte2str(r9)     // Catch:{ Exception -> 0x0342 }
            java.lang.String r12 = "exit-status"
            boolean r9 = r9.equals(r12)     // Catch:{ Exception -> 0x0342 }
            if (r9 == 0) goto L_0x00d6
            int r9 = r0.getInt()     // Catch:{ Exception -> 0x0342 }
            r8.setExitStatus(r9)     // Catch:{ Exception -> 0x0342 }
            r11 = 99
        L_0x00d6:
            if (r10 == 0) goto L_0x0322
            r1.reset()     // Catch:{ Exception -> 0x0342 }
            r0.putByte((byte) r11)     // Catch:{ Exception -> 0x0342 }
            int r8 = r8.getRecipient()     // Catch:{ Exception -> 0x0342 }
            r0.putInt(r8)     // Catch:{ Exception -> 0x0342 }
            r13.write(r1)     // Catch:{ Exception -> 0x0342 }
            goto L_0x0322
        L_0x00ea:
            r0.getInt()     // Catch:{ Exception -> 0x0342 }
            r0.getShort()     // Catch:{ Exception -> 0x0342 }
            int r8 = r0.getInt()     // Catch:{ Exception -> 0x0342 }
            com.jcraft.jsch.Channel r8 = com.jcraft.jsch.Channel.getChannel(r8, r13)     // Catch:{ Exception -> 0x0342 }
            if (r8 == 0) goto L_0x0322
            r8.disconnect()     // Catch:{ Exception -> 0x0342 }
            goto L_0x0322
        L_0x00ff:
            r0.getInt()     // Catch:{ Exception -> 0x0342 }
            r0.getShort()     // Catch:{ Exception -> 0x0342 }
            int r8 = r0.getInt()     // Catch:{ Exception -> 0x0342 }
            com.jcraft.jsch.Channel r8 = com.jcraft.jsch.Channel.getChannel(r8, r13)     // Catch:{ Exception -> 0x0342 }
            if (r8 == 0) goto L_0x0322
            r8.eof_remote()     // Catch:{ Exception -> 0x0342 }
            goto L_0x0322
        L_0x0114:
            r0.getInt()     // Catch:{ Exception -> 0x0342 }
            r0.getShort()     // Catch:{ Exception -> 0x0342 }
            int r8 = r0.getInt()     // Catch:{ Exception -> 0x0342 }
            com.jcraft.jsch.Channel r8 = com.jcraft.jsch.Channel.getChannel(r8, r13)     // Catch:{ Exception -> 0x0342 }
            r0.getInt()     // Catch:{ Exception -> 0x0342 }
            byte[] r10 = r0.getString(r3, r4)     // Catch:{ Exception -> 0x0342 }
            if (r8 != 0) goto L_0x012d
            goto L_0x0322
        L_0x012d:
            r11 = r4[r6]     // Catch:{ Exception -> 0x0342 }
            if (r11 != 0) goto L_0x0133
            goto L_0x0322
        L_0x0133:
            r11 = r3[r6]     // Catch:{ Exception -> 0x0342 }
            r12 = r4[r6]     // Catch:{ Exception -> 0x0342 }
            r8.write_ext(r10, r11, r12)     // Catch:{ Exception -> 0x0342 }
            r10 = r4[r6]     // Catch:{ Exception -> 0x0342 }
            int r11 = r8.lwsize     // Catch:{ Exception -> 0x0342 }
            int r11 = r11 - r10
            r8.setLocalWindowSize(r11)     // Catch:{ Exception -> 0x0342 }
            int r10 = r8.lwsize     // Catch:{ Exception -> 0x0342 }
            int r11 = r8.lwsize_max     // Catch:{ Exception -> 0x0342 }
            int r11 = r11 / 2
            if (r10 >= r11) goto L_0x0322
            r1.reset()     // Catch:{ Exception -> 0x0342 }
            r0.putByte((byte) r9)     // Catch:{ Exception -> 0x0342 }
            int r9 = r8.getRecipient()     // Catch:{ Exception -> 0x0342 }
            r0.putInt(r9)     // Catch:{ Exception -> 0x0342 }
            int r9 = r8.lwsize_max     // Catch:{ Exception -> 0x0342 }
            int r10 = r8.lwsize     // Catch:{ Exception -> 0x0342 }
            int r9 = r9 - r10
            r0.putInt(r9)     // Catch:{ Exception -> 0x0342 }
            monitor-enter(r8)     // Catch:{ Exception -> 0x0342 }
            boolean r9 = r8.close     // Catch:{ all -> 0x016f }
            if (r9 != 0) goto L_0x0167
            r13.write(r1)     // Catch:{ all -> 0x016f }
        L_0x0167:
            monitor-exit(r8)     // Catch:{ all -> 0x016f }
            int r9 = r8.lwsize_max     // Catch:{ Exception -> 0x0342 }
            r8.setLocalWindowSize(r9)     // Catch:{ Exception -> 0x0342 }
            goto L_0x0322
        L_0x016f:
            r0 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x016f }
            throw r0     // Catch:{ Exception -> 0x0342 }
        L_0x0172:
            r0.getInt()     // Catch:{ Exception -> 0x0342 }
            r0.getByte()     // Catch:{ Exception -> 0x0342 }
            r0.getByte()     // Catch:{ Exception -> 0x0342 }
            int r8 = r0.getInt()     // Catch:{ Exception -> 0x0342 }
            com.jcraft.jsch.Channel r8 = com.jcraft.jsch.Channel.getChannel(r8, r13)     // Catch:{ Exception -> 0x0342 }
            byte[] r10 = r0.getString(r3, r4)     // Catch:{ Exception -> 0x0342 }
            if (r8 != 0) goto L_0x018b
            goto L_0x0322
        L_0x018b:
            r11 = r4[r6]     // Catch:{ Exception -> 0x0342 }
            if (r11 != 0) goto L_0x0191
            goto L_0x0322
        L_0x0191:
            r11 = r3[r6]     // Catch:{ Exception -> 0x01d0 }
            r12 = r4[r6]     // Catch:{ Exception -> 0x01d0 }
            r8.write(r10, r11, r12)     // Catch:{ Exception -> 0x01d0 }
            r10 = r4[r6]     // Catch:{ Exception -> 0x0342 }
            int r11 = r8.lwsize     // Catch:{ Exception -> 0x0342 }
            int r11 = r11 - r10
            r8.setLocalWindowSize(r11)     // Catch:{ Exception -> 0x0342 }
            int r10 = r8.lwsize     // Catch:{ Exception -> 0x0342 }
            int r11 = r8.lwsize_max     // Catch:{ Exception -> 0x0342 }
            int r11 = r11 / 2
            if (r10 >= r11) goto L_0x0322
            r1.reset()     // Catch:{ Exception -> 0x0342 }
            r0.putByte((byte) r9)     // Catch:{ Exception -> 0x0342 }
            int r9 = r8.getRecipient()     // Catch:{ Exception -> 0x0342 }
            r0.putInt(r9)     // Catch:{ Exception -> 0x0342 }
            int r9 = r8.lwsize_max     // Catch:{ Exception -> 0x0342 }
            int r10 = r8.lwsize     // Catch:{ Exception -> 0x0342 }
            int r9 = r9 - r10
            r0.putInt(r9)     // Catch:{ Exception -> 0x0342 }
            monitor-enter(r8)     // Catch:{ Exception -> 0x0342 }
            boolean r9 = r8.close     // Catch:{ all -> 0x01cd }
            if (r9 != 0) goto L_0x01c5
            r13.write(r1)     // Catch:{ all -> 0x01cd }
        L_0x01c5:
            monitor-exit(r8)     // Catch:{ all -> 0x01cd }
            int r9 = r8.lwsize_max     // Catch:{ Exception -> 0x0342 }
            r8.setLocalWindowSize(r9)     // Catch:{ Exception -> 0x0342 }
            goto L_0x0322
        L_0x01cd:
            r0 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x01cd }
            throw r0     // Catch:{ Exception -> 0x0342 }
        L_0x01d0:
            r8.disconnect()     // Catch:{ Exception -> 0x0322 }
            goto L_0x0322
        L_0x01d5:
            r0.getInt()     // Catch:{ Exception -> 0x0342 }
            r0.getShort()     // Catch:{ Exception -> 0x0342 }
            int r8 = r0.getInt()     // Catch:{ Exception -> 0x0342 }
            com.jcraft.jsch.Channel r8 = com.jcraft.jsch.Channel.getChannel(r8, r13)     // Catch:{ Exception -> 0x0342 }
            if (r8 != 0) goto L_0x01e7
            goto L_0x0322
        L_0x01e7:
            long r9 = r0.getUInt()     // Catch:{ Exception -> 0x0342 }
            r8.addRemoteWindowSize(r9)     // Catch:{ Exception -> 0x0342 }
            goto L_0x0322
        L_0x01f0:
            r0.getInt()     // Catch:{ Exception -> 0x0342 }
            r0.getShort()     // Catch:{ Exception -> 0x0342 }
            int r8 = r0.getInt()     // Catch:{ Exception -> 0x0342 }
            com.jcraft.jsch.Channel r8 = com.jcraft.jsch.Channel.getChannel(r8, r13)     // Catch:{ Exception -> 0x0342 }
            if (r8 == 0) goto L_0x0322
            int r9 = r0.getInt()     // Catch:{ Exception -> 0x0342 }
            r8.setExitStatus(r9)     // Catch:{ Exception -> 0x0342 }
            r8.close = r2     // Catch:{ Exception -> 0x0342 }
            r8.eof_remote = r2     // Catch:{ Exception -> 0x0342 }
            r8.setRecipient(r6)     // Catch:{ Exception -> 0x0342 }
            goto L_0x0322
        L_0x0210:
            r0.getInt()     // Catch:{ Exception -> 0x0342 }
            r0.getShort()     // Catch:{ Exception -> 0x0342 }
            int r8 = r0.getInt()     // Catch:{ Exception -> 0x0342 }
            com.jcraft.jsch.Channel r8 = com.jcraft.jsch.Channel.getChannel(r8, r13)     // Catch:{ Exception -> 0x0342 }
            int r9 = r0.getInt()     // Catch:{ Exception -> 0x0342 }
            long r10 = r0.getUInt()     // Catch:{ Exception -> 0x0342 }
            int r12 = r0.getInt()     // Catch:{ Exception -> 0x0342 }
            if (r8 == 0) goto L_0x0322
            r8.setRemoteWindowSize(r10)     // Catch:{ Exception -> 0x0342 }
            r8.setRemotePacketSize(r12)     // Catch:{ Exception -> 0x0342 }
            r8.open_confirmation = r2     // Catch:{ Exception -> 0x0342 }
            r8.setRecipient(r9)     // Catch:{ Exception -> 0x0342 }
            goto L_0x0322
        L_0x0239:
            r0.getInt()     // Catch:{ Exception -> 0x0342 }
            r0.getShort()     // Catch:{ Exception -> 0x0342 }
            byte[] r8 = r0.getString()     // Catch:{ Exception -> 0x0342 }
            java.lang.String r8 = com.jcraft.jsch.Util.byte2str(r8)     // Catch:{ Exception -> 0x0342 }
            java.lang.String r9 = "forwarded-tcpip"
            boolean r9 = r9.equals(r8)     // Catch:{ Exception -> 0x0342 }
            if (r9 != 0) goto L_0x0288
            java.lang.String r9 = "x11"
            boolean r9 = r9.equals(r8)     // Catch:{ Exception -> 0x0342 }
            if (r9 == 0) goto L_0x025b
            boolean r9 = r13.x11_forwarding     // Catch:{ Exception -> 0x0342 }
            if (r9 != 0) goto L_0x0288
        L_0x025b:
            java.lang.String r9 = "auth-agent@openssh.com"
            boolean r9 = r9.equals(r8)     // Catch:{ Exception -> 0x0342 }
            if (r9 == 0) goto L_0x0267
            boolean r9 = r13.agent_forwarding     // Catch:{ Exception -> 0x0342 }
            if (r9 != 0) goto L_0x0288
        L_0x0267:
            r1.reset()     // Catch:{ Exception -> 0x0342 }
            r8 = 92
            r0.putByte((byte) r8)     // Catch:{ Exception -> 0x0342 }
            int r8 = r0.getInt()     // Catch:{ Exception -> 0x0342 }
            r0.putInt(r8)     // Catch:{ Exception -> 0x0342 }
            r0.putInt(r2)     // Catch:{ Exception -> 0x0342 }
            byte[] r8 = com.jcraft.jsch.Util.empty     // Catch:{ Exception -> 0x0342 }
            r0.putString(r8)     // Catch:{ Exception -> 0x0342 }
            byte[] r8 = com.jcraft.jsch.Util.empty     // Catch:{ Exception -> 0x0342 }
            r0.putString(r8)     // Catch:{ Exception -> 0x0342 }
            r13.write(r1)     // Catch:{ Exception -> 0x0342 }
            goto L_0x0322
        L_0x0288:
            com.jcraft.jsch.Channel r9 = com.jcraft.jsch.Channel.getChannel(r8)     // Catch:{ Exception -> 0x0342 }
            r13.addChannel(r9)     // Catch:{ Exception -> 0x0342 }
            r9.getData(r0)     // Catch:{ Exception -> 0x0342 }
            r9.init()     // Catch:{ Exception -> 0x0342 }
            java.lang.Thread r10 = new java.lang.Thread     // Catch:{ Exception -> 0x0342 }
            r10.<init>(r9)     // Catch:{ Exception -> 0x0342 }
            java.lang.StringBuffer r9 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x0342 }
            r9.<init>()     // Catch:{ Exception -> 0x0342 }
            java.lang.String r11 = "Channel "
            r9.append(r11)     // Catch:{ Exception -> 0x0342 }
            r9.append(r8)     // Catch:{ Exception -> 0x0342 }
            java.lang.String r8 = " "
            r9.append(r8)     // Catch:{ Exception -> 0x0342 }
            java.lang.String r8 = r13.host     // Catch:{ Exception -> 0x0342 }
            r9.append(r8)     // Catch:{ Exception -> 0x0342 }
            java.lang.String r8 = r9.toString()     // Catch:{ Exception -> 0x0342 }
            r10.setName(r8)     // Catch:{ Exception -> 0x0342 }
            boolean r8 = r13.daemon_thread     // Catch:{ Exception -> 0x0342 }
            if (r8 == 0) goto L_0x02c1
            boolean r8 = r13.daemon_thread     // Catch:{ Exception -> 0x0342 }
            r10.setDaemon(r8)     // Catch:{ Exception -> 0x0342 }
        L_0x02c1:
            r10.start()     // Catch:{ Exception -> 0x0342 }
            goto L_0x0322
        L_0x02c5:
            com.jcraft.jsch.Session$GlobalRequestReply r9 = r13.grr     // Catch:{ Exception -> 0x0342 }
            java.lang.Thread r9 = r9.getThread()     // Catch:{ Exception -> 0x0342 }
            if (r9 == 0) goto L_0x0322
            com.jcraft.jsch.Session$GlobalRequestReply r10 = r13.grr     // Catch:{ Exception -> 0x0342 }
            r11 = 81
            if (r8 != r11) goto L_0x02d5
            r12 = 1
            goto L_0x02d6
        L_0x02d5:
            r12 = 0
        L_0x02d6:
            r10.setReply(r12)     // Catch:{ Exception -> 0x0342 }
            if (r8 != r11) goto L_0x02f2
            com.jcraft.jsch.Session$GlobalRequestReply r8 = r13.grr     // Catch:{ Exception -> 0x0342 }
            int r8 = r8.getPort()     // Catch:{ Exception -> 0x0342 }
            if (r8 != 0) goto L_0x02f2
            r0.getInt()     // Catch:{ Exception -> 0x0342 }
            r0.getShort()     // Catch:{ Exception -> 0x0342 }
            com.jcraft.jsch.Session$GlobalRequestReply r8 = r13.grr     // Catch:{ Exception -> 0x0342 }
            int r10 = r0.getInt()     // Catch:{ Exception -> 0x0342 }
            r8.setPort(r10)     // Catch:{ Exception -> 0x0342 }
        L_0x02f2:
            r9.interrupt()     // Catch:{ Exception -> 0x0342 }
            goto L_0x0322
        L_0x02f6:
            r0.getInt()     // Catch:{ Exception -> 0x0342 }
            r0.getShort()     // Catch:{ Exception -> 0x0342 }
            r0.getString()     // Catch:{ Exception -> 0x0342 }
            int r8 = r0.getByte()     // Catch:{ Exception -> 0x0342 }
            if (r8 == 0) goto L_0x0307
            r8 = 1
            goto L_0x0308
        L_0x0307:
            r8 = 0
        L_0x0308:
            if (r8 == 0) goto L_0x0322
            r1.reset()     // Catch:{ Exception -> 0x0342 }
            r8 = 82
            r0.putByte((byte) r8)     // Catch:{ Exception -> 0x0342 }
            r13.write(r1)     // Catch:{ Exception -> 0x0342 }
            goto L_0x0322
        L_0x0316:
            r13.send_newkeys()     // Catch:{ Exception -> 0x0342 }
            r13.receive_newkeys(r0, r7)     // Catch:{ Exception -> 0x0342 }
            r7 = r5
            goto L_0x0322
        L_0x031e:
            com.jcraft.jsch.KeyExchange r7 = r13.receive_kexinit(r0)     // Catch:{ Exception -> 0x0342 }
        L_0x0322:
            r8 = 0
            goto L_0x0015
        L_0x0325:
            r9 = move-exception
            boolean r10 = r13.in_kex     // Catch:{ Exception -> 0x0342 }
            if (r10 != 0) goto L_0x0335
            int r10 = r13.serverAliveCountMax     // Catch:{ Exception -> 0x0342 }
            if (r8 >= r10) goto L_0x0335
            r13.sendKeepAliveMsg()     // Catch:{ Exception -> 0x0342 }
            int r8 = r8 + 1
            goto L_0x0015
        L_0x0335:
            boolean r10 = r13.in_kex     // Catch:{ Exception -> 0x0342 }
            if (r10 == 0) goto L_0x0341
            int r10 = r13.serverAliveCountMax     // Catch:{ Exception -> 0x0342 }
            if (r8 >= r10) goto L_0x0341
            int r8 = r8 + 1
            goto L_0x0015
        L_0x0341:
            throw r9     // Catch:{ Exception -> 0x0342 }
        L_0x0342:
            r0 = move-exception
            r13.in_kex = r6
            com.jcraft.jsch.Logger r1 = com.jcraft.jsch.JSch.getLogger()
            boolean r1 = r1.isEnabled(r2)
            if (r1 == 0) goto L_0x036b
            com.jcraft.jsch.Logger r1 = com.jcraft.jsch.JSch.getLogger()
            java.lang.StringBuffer r3 = new java.lang.StringBuffer
            r3.<init>()
            java.lang.String r4 = "Caught an exception, leaving main loop due to "
            r3.append(r4)
            java.lang.String r0 = r0.getMessage()
            r3.append(r0)
            java.lang.String r0 = r3.toString()
            r1.log(r2, r0)
        L_0x036b:
            r13.disconnect()     // Catch:{ Exception | NullPointerException -> 0x036e }
        L_0x036e:
            r13.isConnected = r6
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.Session.run():void");
    }

    public void disconnect() {
        if (this.isConnected) {
            if (JSch.getLogger().isEnabled(1)) {
                Logger logger = JSch.getLogger();
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Disconnecting from ");
                stringBuffer.append(this.host);
                stringBuffer.append(" port ");
                stringBuffer.append(this.port);
                logger.log(1, stringBuffer.toString());
            }
            Channel.disconnect(this);
            this.isConnected = false;
            PortWatcher.delPort(this);
            ChannelForwardedTCPIP.delPort(this);
            ChannelX11.removeFakedCookie(this);
            synchronized (this.lock) {
                if (this.connectThread != null) {
                    Thread.yield();
                    this.connectThread.interrupt();
                    this.connectThread = null;
                }
            }
            this.thread = null;
            try {
                if (this.io != null) {
                    if (this.io.in != null) {
                        this.io.in.close();
                    }
                    if (this.io.out != null) {
                        this.io.out.close();
                    }
                    if (this.io.out_ext != null) {
                        this.io.out_ext.close();
                    }
                }
                if (this.proxy != null) {
                    synchronized (this.proxy) {
                        this.proxy.close();
                    }
                    this.proxy = null;
                } else if (this.socket != null) {
                    this.socket.close();
                }
            } catch (Exception unused) {
            }
            this.io = null;
            this.socket = null;
            this.jsch.removeSession(this);
        }
    }

    public int setPortForwardingL(int i, String str, int i2) throws JSchException {
        return setPortForwardingL("127.0.0.1", i, str, i2);
    }

    public int setPortForwardingL(String str, int i, String str2, int i2) throws JSchException {
        return setPortForwardingL(str, i, str2, i2, (ServerSocketFactory) null);
    }

    public int setPortForwardingL(String str, int i, String str2, int i2, ServerSocketFactory serverSocketFactory) throws JSchException {
        return setPortForwardingL(str, i, str2, i2, serverSocketFactory, 0);
    }

    public int setPortForwardingL(String str, int i, String str2, int i2, ServerSocketFactory serverSocketFactory, int i3) throws JSchException {
        PortWatcher addPort = PortWatcher.addPort(this, str, i, str2, i2, serverSocketFactory);
        addPort.setConnectTimeout(i3);
        Thread thread2 = new Thread(addPort);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("PortWatcher Thread for ");
        stringBuffer.append(str2);
        thread2.setName(stringBuffer.toString());
        boolean z = this.daemon_thread;
        if (z) {
            thread2.setDaemon(z);
        }
        thread2.start();
        return addPort.lport;
    }

    public void delPortForwardingL(int i) throws JSchException {
        delPortForwardingL("127.0.0.1", i);
    }

    public void delPortForwardingL(String str, int i) throws JSchException {
        PortWatcher.delPort(this, str, i);
    }

    public String[] getPortForwardingL() throws JSchException {
        return PortWatcher.getPortForwarding(this);
    }

    public void setPortForwardingR(int i, String str, int i2) throws JSchException {
        setPortForwardingR((String) null, i, str, i2, (SocketFactory) null);
    }

    public void setPortForwardingR(String str, int i, String str2, int i2) throws JSchException {
        setPortForwardingR(str, i, str2, i2, (SocketFactory) null);
    }

    public void setPortForwardingR(int i, String str, int i2, SocketFactory socketFactory) throws JSchException {
        setPortForwardingR((String) null, i, str, i2, socketFactory);
    }

    public void setPortForwardingR(String str, int i, String str2, int i2, SocketFactory socketFactory) throws JSchException {
        ChannelForwardedTCPIP.addPort(this, str, i, _setPortForwardingR(str, i), str2, i2, socketFactory);
    }

    public void setPortForwardingR(int i, String str) throws JSchException {
        setPortForwardingR((String) null, i, str, (Object[]) null);
    }

    public void setPortForwardingR(int i, String str, Object[] objArr) throws JSchException {
        setPortForwardingR((String) null, i, str, objArr);
    }

    public void setPortForwardingR(String str, int i, String str2, Object[] objArr) throws JSchException {
        ChannelForwardedTCPIP.addPort(this, str, i, _setPortForwardingR(str, i), str2, objArr);
    }

    public String[] getPortForwardingR() throws JSchException {
        return ChannelForwardedTCPIP.getPortForwarding(this);
    }

    private class Forwarding {
        String bind_address;
        String host;
        int hostport;
        int port;

        private Forwarding() {
            this.bind_address = null;
            this.port = -1;
            this.host = null;
            this.hostport = -1;
        }
    }

    private Forwarding parseForwarding(String str) throws JSchException {
        String[] split = str.split(" ");
        if (split.length > 1) {
            Vector vector = new Vector();
            for (int i = 0; i < split.length; i++) {
                if (split[i].length() != 0) {
                    vector.addElement(split[i].trim());
                }
            }
            StringBuffer stringBuffer = new StringBuffer();
            int i2 = 0;
            while (i2 < vector.size()) {
                stringBuffer.append((String) vector.elementAt(i2));
                i2++;
                if (i2 < vector.size()) {
                    stringBuffer.append(":");
                }
            }
            str = stringBuffer.toString();
        }
        Forwarding forwarding = new Forwarding();
        try {
            if (str.lastIndexOf(":") != -1) {
                forwarding.hostport = Integer.parseInt(str.substring(str.lastIndexOf(":") + 1));
                String substring = str.substring(0, str.lastIndexOf(":"));
                if (substring.lastIndexOf(":") != -1) {
                    forwarding.host = substring.substring(substring.lastIndexOf(":") + 1);
                    String substring2 = substring.substring(0, substring.lastIndexOf(":"));
                    if (substring2.lastIndexOf(":") != -1) {
                        forwarding.port = Integer.parseInt(substring2.substring(substring2.lastIndexOf(":") + 1));
                        String substring3 = substring2.substring(0, substring2.lastIndexOf(":"));
                        if (substring3.length() == 0 || substring3.equals("*")) {
                            substring3 = "0.0.0.0";
                        }
                        if (substring3.equals("localhost")) {
                            substring3 = "127.0.0.1";
                        }
                        forwarding.bind_address = substring3;
                    } else {
                        forwarding.port = Integer.parseInt(substring2);
                        forwarding.bind_address = "127.0.0.1";
                    }
                    return forwarding;
                }
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("parseForwarding: ");
                stringBuffer2.append(str);
                throw new JSchException(stringBuffer2.toString());
            }
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("parseForwarding: ");
            stringBuffer3.append(str);
            throw new JSchException(stringBuffer3.toString());
        } catch (NumberFormatException e) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("parseForwarding: ");
            stringBuffer4.append(e.toString());
            throw new JSchException(stringBuffer4.toString());
        }
    }

    public int setPortForwardingL(String str) throws JSchException {
        Forwarding parseForwarding = parseForwarding(str);
        return setPortForwardingL(parseForwarding.bind_address, parseForwarding.port, parseForwarding.host, parseForwarding.hostport);
    }

    public int setPortForwardingR(String str) throws JSchException {
        Forwarding parseForwarding = parseForwarding(str);
        int _setPortForwardingR = _setPortForwardingR(parseForwarding.bind_address, parseForwarding.port);
        ChannelForwardedTCPIP.addPort(this, parseForwarding.bind_address, parseForwarding.port, _setPortForwardingR, parseForwarding.host, parseForwarding.hostport, (SocketFactory) null);
        return _setPortForwardingR;
    }

    public Channel getStreamForwarder(String str, int i) throws JSchException {
        ChannelDirectTCPIP channelDirectTCPIP = new ChannelDirectTCPIP();
        channelDirectTCPIP.init();
        addChannel(channelDirectTCPIP);
        channelDirectTCPIP.setHost(str);
        channelDirectTCPIP.setPort(i);
        return channelDirectTCPIP;
    }

    private class GlobalRequestReply {
        private int port;
        private int reply;
        private Thread thread;

        private GlobalRequestReply() {
            this.thread = null;
            this.reply = -1;
            this.port = 0;
        }

        /* access modifiers changed from: package-private */
        public void setThread(Thread thread2) {
            this.thread = thread2;
            this.reply = -1;
        }

        /* access modifiers changed from: package-private */
        public Thread getThread() {
            return this.thread;
        }

        /* access modifiers changed from: package-private */
        public void setReply(int i) {
            this.reply = i;
        }

        /* access modifiers changed from: package-private */
        public int getReply() {
            return this.reply;
        }

        /* access modifiers changed from: package-private */
        public int getPort() {
            return this.port;
        }

        /* access modifiers changed from: package-private */
        public void setPort(int i) {
            this.port = i;
        }
    }

    private int _setPortForwardingR(String str, int i) throws JSchException {
        int port2;
        synchronized (this.grr) {
            Buffer buffer = new Buffer((int) SSH_MSG_CHANNEL_FAILURE);
            Packet packet2 = new Packet(buffer);
            String normalize = ChannelForwardedTCPIP.normalize(str);
            this.grr.setThread(Thread.currentThread());
            this.grr.setPort(i);
            try {
                packet2.reset();
                buffer.putByte((byte) 80);
                buffer.putString(Util.str2byte("tcpip-forward"));
                buffer.putByte((byte) 1);
                buffer.putString(Util.str2byte(normalize));
                buffer.putInt(i);
                write(packet2);
                int i2 = 0;
                int reply = this.grr.getReply();
                while (i2 < 10 && reply == -1) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception unused) {
                    }
                    i2++;
                    reply = this.grr.getReply();
                }
                this.grr.setThread((Thread) null);
                if (reply == 1) {
                    port2 = this.grr.getPort();
                } else {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("remote port forwarding failed for listen port ");
                    stringBuffer.append(i);
                    throw new JSchException(stringBuffer.toString());
                }
            } catch (Exception e) {
                this.grr.setThread((Thread) null);
                if (e instanceof Throwable) {
                    throw new JSchException(e.toString(), e);
                }
                throw new JSchException(e.toString());
            }
        }
        return port2;
    }

    public void delPortForwardingR(int i) throws JSchException {
        delPortForwardingR((String) null, i);
    }

    public void delPortForwardingR(String str, int i) throws JSchException {
        ChannelForwardedTCPIP.delPort(this, str, i);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0051, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005b, code lost:
        throw new com.jcraft.jsch.JSchException(r3.toString(), r3);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x003f */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0051 A[ExcHandler: NoClassDefFoundError (r3v1 'e' java.lang.NoClassDefFoundError A[CUSTOM_DECLARE]), Splitter:B:12:0x0026] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void initDeflater(java.lang.String r3) throws com.jcraft.jsch.JSchException {
        /*
            r2 = this;
            java.lang.String r0 = "none"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x000c
            r3 = 0
            r2.deflater = r3
            return
        L_0x000c:
            java.lang.String r0 = r2.getConfig(r3)
            if (r0 == 0) goto L_0x005c
            java.lang.String r1 = "zlib"
            boolean r1 = r3.equals(r1)
            if (r1 != 0) goto L_0x0026
            boolean r1 = r2.isAuthed
            if (r1 == 0) goto L_0x005c
            java.lang.String r1 = "zlib@openssh.com"
            boolean r3 = r3.equals(r1)
            if (r3 == 0) goto L_0x005c
        L_0x0026:
            java.lang.Class r3 = java.lang.Class.forName(r0)     // Catch:{ NoClassDefFoundError -> 0x0051, Exception -> 0x0046 }
            java.lang.Object r3 = r3.newInstance()     // Catch:{ NoClassDefFoundError -> 0x0051, Exception -> 0x0046 }
            com.jcraft.jsch.Compression r3 = (com.jcraft.jsch.Compression) r3     // Catch:{ NoClassDefFoundError -> 0x0051, Exception -> 0x0046 }
            com.jcraft.jsch.Compression r3 = (com.jcraft.jsch.Compression) r3     // Catch:{ NoClassDefFoundError -> 0x0051, Exception -> 0x0046 }
            r2.deflater = r3     // Catch:{ NoClassDefFoundError -> 0x0051, Exception -> 0x0046 }
            r3 = 6
            java.lang.String r0 = "compression_level"
            java.lang.String r0 = r2.getConfig(r0)     // Catch:{ Exception -> 0x003f, NoClassDefFoundError -> 0x0051 }
            int r3 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x003f, NoClassDefFoundError -> 0x0051 }
        L_0x003f:
            com.jcraft.jsch.Compression r0 = r2.deflater     // Catch:{ NoClassDefFoundError -> 0x0051, Exception -> 0x0046 }
            r1 = 1
            r0.init(r1, r3)     // Catch:{ NoClassDefFoundError -> 0x0051, Exception -> 0x0046 }
            goto L_0x005c
        L_0x0046:
            r3 = move-exception
            com.jcraft.jsch.JSchException r0 = new com.jcraft.jsch.JSchException
            java.lang.String r1 = r3.toString()
            r0.<init>(r1, r3)
            throw r0
        L_0x0051:
            r3 = move-exception
            com.jcraft.jsch.JSchException r0 = new com.jcraft.jsch.JSchException
            java.lang.String r1 = r3.toString()
            r0.<init>(r1, r3)
            throw r0
        L_0x005c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.Session.initDeflater(java.lang.String):void");
    }

    private void initInflater(String str) throws JSchException {
        if (str.equals("none")) {
            this.inflater = null;
            return;
        }
        String config2 = getConfig(str);
        if (config2 == null) {
            return;
        }
        if (str.equals("zlib") || (this.isAuthed && str.equals("zlib@openssh.com"))) {
            try {
                this.inflater = (Compression) Class.forName(config2).newInstance();
                this.inflater.init(0, 0);
            } catch (Exception e) {
                throw new JSchException(e.toString(), e);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void addChannel(Channel channel) {
        channel.setSession(this);
    }

    public void setProxy(Proxy proxy2) {
        this.proxy = proxy2;
    }

    public void setHost(String str) {
        this.host = str;
    }

    public void setPort(int i) {
        this.port = i;
    }

    /* access modifiers changed from: package-private */
    public void setUserName(String str) {
        this.username = str;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userinfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return this.userinfo;
    }

    public void setInputStream(InputStream inputStream) {
        this.in = inputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.out = outputStream;
    }

    public void setX11Host(String str) {
        ChannelX11.setHost(str);
    }

    public void setX11Port(int i) {
        ChannelX11.setPort(i);
    }

    public void setX11Cookie(String str) {
        ChannelX11.setCookie(str);
    }

    public void setPassword(String str) {
        if (str != null) {
            this.password = Util.str2byte(str);
        }
    }

    public void setPassword(byte[] bArr) {
        if (bArr != null) {
            this.password = new byte[bArr.length];
            System.arraycopy(bArr, 0, this.password, 0, bArr.length);
        }
    }

    public void setConfig(Properties properties) {
        setConfig((Hashtable) properties);
    }

    public void setConfig(Hashtable hashtable) {
        synchronized (this.lock) {
            if (this.config == null) {
                this.config = new Hashtable();
            }
            Enumeration keys = hashtable.keys();
            while (keys.hasMoreElements()) {
                String str = (String) keys.nextElement();
                this.config.put(str, (String) hashtable.get(str));
            }
        }
    }

    public void setConfig(String str, String str2) {
        synchronized (this.lock) {
            if (this.config == null) {
                this.config = new Hashtable();
            }
            this.config.put(str, str2);
        }
    }

    public String getConfig(String str) {
        Hashtable hashtable = this.config;
        if (hashtable != null) {
            Object obj = hashtable.get(str);
            if (obj instanceof String) {
                return (String) obj;
            }
        }
        JSch jSch = this.jsch;
        String config2 = JSch.getConfig(str);
        if (config2 instanceof String) {
            return config2;
        }
        return null;
    }

    public void setSocketFactory(SocketFactory socketFactory) {
        this.socket_factory = socketFactory;
    }

    public boolean isConnected() {
        return this.isConnected;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int i) throws JSchException {
        Socket socket2 = this.socket;
        if (socket2 != null) {
            try {
                socket2.setSoTimeout(i);
                this.timeout = i;
            } catch (Exception e) {
                if (e instanceof Throwable) {
                    throw new JSchException(e.toString(), e);
                }
                throw new JSchException(e.toString());
            }
        } else if (i >= 0) {
            this.timeout = i;
        } else {
            throw new JSchException("invalid timeout value");
        }
    }

    public String getServerVersion() {
        return Util.byte2str(this.V_S);
    }

    public String getClientVersion() {
        return Util.byte2str(this.V_C);
    }

    public void setClientVersion(String str) {
        this.V_C = Util.str2byte(str);
    }

    public void sendIgnore() throws Exception {
        Buffer buffer = new Buffer();
        Packet packet2 = new Packet(buffer);
        packet2.reset();
        buffer.putByte((byte) 2);
        write(packet2);
    }

    public void sendKeepAliveMsg() throws Exception {
        Buffer buffer = new Buffer();
        Packet packet2 = new Packet(buffer);
        packet2.reset();
        buffer.putByte((byte) 80);
        buffer.putString(keepalivemsg);
        buffer.putByte((byte) 1);
        write(packet2);
    }

    public void noMoreSessionChannels() throws Exception {
        Buffer buffer = new Buffer();
        Packet packet2 = new Packet(buffer);
        packet2.reset();
        buffer.putByte((byte) 80);
        buffer.putString(nomoresessions);
        buffer.putByte((byte) 0);
        write(packet2);
    }

    public HostKey getHostKey() {
        return this.hostkey;
    }

    public String getHost() {
        return this.host;
    }

    public String getUserName() {
        return this.username;
    }

    public int getPort() {
        return this.port;
    }

    public void setHostKeyAlias(String str) {
        this.hostKeyAlias = str;
    }

    public String getHostKeyAlias() {
        return this.hostKeyAlias;
    }

    public void setServerAliveInterval(int i) throws JSchException {
        setTimeout(i);
        this.serverAliveInterval = i;
    }

    public int getServerAliveInterval() {
        return this.serverAliveInterval;
    }

    public void setServerAliveCountMax(int i) {
        this.serverAliveCountMax = i;
    }

    public int getServerAliveCountMax() {
        return this.serverAliveCountMax;
    }

    public void setDaemonThread(boolean z) {
        this.daemon_thread = z;
    }

    private String[] checkCiphers(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        if (JSch.getLogger().isEnabled(1)) {
            Logger logger = JSch.getLogger();
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("CheckCiphers: ");
            stringBuffer.append(str);
            logger.log(1, stringBuffer.toString());
        }
        String config2 = getConfig("cipher.c2s");
        String config3 = getConfig("cipher.s2c");
        Vector vector = new Vector();
        String[] split = Util.split(str, ",");
        for (String str2 : split) {
            if (!(config3.indexOf(str2) == -1 && config2.indexOf(str2) == -1) && !checkCipher(getConfig(str2))) {
                vector.addElement(str2);
            }
        }
        if (vector.size() == 0) {
            return null;
        }
        String[] strArr = new String[vector.size()];
        System.arraycopy(vector.toArray(), 0, strArr, 0, vector.size());
        if (JSch.getLogger().isEnabled(1)) {
            for (String append : strArr) {
                Logger logger2 = JSch.getLogger();
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append(append);
                stringBuffer2.append(" is not available.");
                logger2.log(1, stringBuffer2.toString());
            }
        }
        return strArr;
    }

    static boolean checkCipher(String str) {
        try {
            Cipher cipher = (Cipher) Class.forName(str).newInstance();
            cipher.init(0, new byte[cipher.getBlockSize()], new byte[cipher.getIVSize()]);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    private String[] checkKexes(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        if (JSch.getLogger().isEnabled(1)) {
            Logger logger = JSch.getLogger();
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("CheckKexes: ");
            stringBuffer.append(str);
            logger.log(1, stringBuffer.toString());
        }
        Vector vector = new Vector();
        String[] split = Util.split(str, ",");
        for (int i = 0; i < split.length; i++) {
            if (!checkKex(this, getConfig(split[i]))) {
                vector.addElement(split[i]);
            }
        }
        if (vector.size() == 0) {
            return null;
        }
        String[] strArr = new String[vector.size()];
        System.arraycopy(vector.toArray(), 0, strArr, 0, vector.size());
        if (JSch.getLogger().isEnabled(1)) {
            for (String append : strArr) {
                Logger logger2 = JSch.getLogger();
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append(append);
                stringBuffer2.append(" is not available.");
                logger2.log(1, stringBuffer2.toString());
            }
        }
        return strArr;
    }

    static boolean checkKex(Session session, String str) {
        try {
            ((KeyExchange) Class.forName(str).newInstance()).init(session, (byte[]) null, (byte[]) null, (byte[]) null, (byte[]) null);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    private String[] checkSignatures(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        if (JSch.getLogger().isEnabled(1)) {
            Logger logger = JSch.getLogger();
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("CheckSignatures: ");
            stringBuffer.append(str);
            logger.log(1, stringBuffer.toString());
        }
        Vector vector = new Vector();
        String[] split = Util.split(str, ",");
        for (int i = 0; i < split.length; i++) {
            try {
                JSch jSch = this.jsch;
                ((Signature) Class.forName(JSch.getConfig(split[i])).newInstance()).init();
            } catch (Exception unused) {
                vector.addElement(split[i]);
            }
        }
        if (vector.size() == 0) {
            return null;
        }
        String[] strArr = new String[vector.size()];
        System.arraycopy(vector.toArray(), 0, strArr, 0, vector.size());
        if (JSch.getLogger().isEnabled(1)) {
            for (String append : strArr) {
                Logger logger2 = JSch.getLogger();
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append(append);
                stringBuffer2.append(" is not available.");
                logger2.log(1, stringBuffer2.toString());
            }
        }
        return strArr;
    }

    public void setIdentityRepository(IdentityRepository identityRepository2) {
        this.identityRepository = identityRepository2;
    }

    /* access modifiers changed from: package-private */
    public IdentityRepository getIdentityRepository() {
        IdentityRepository identityRepository2 = this.identityRepository;
        return identityRepository2 == null ? this.jsch.getIdentityRepository() : identityRepository2;
    }

    public void setHostKeyRepository(HostKeyRepository hostKeyRepository) {
        this.hostkeyRepository = hostKeyRepository;
    }

    public HostKeyRepository getHostKeyRepository() {
        HostKeyRepository hostKeyRepository = this.hostkeyRepository;
        return hostKeyRepository == null ? this.jsch.getHostKeyRepository() : hostKeyRepository;
    }

    private void applyConfig() throws JSchException {
        ConfigRepository configRepository = this.jsch.getConfigRepository();
        if (configRepository != null) {
            ConfigRepository.Config config2 = configRepository.getConfig(this.org_host);
            String user = config2.getUser();
            if (user != null) {
                this.username = user;
            }
            String hostname = config2.getHostname();
            if (hostname != null) {
                this.host = hostname;
            }
            int port2 = config2.getPort();
            if (port2 != -1) {
                this.port = port2;
            }
            checkConfig(config2, "kex");
            checkConfig(config2, "server_host_key");
            checkConfig(config2, "cipher.c2s");
            checkConfig(config2, "cipher.s2c");
            checkConfig(config2, "mac.c2s");
            checkConfig(config2, "mac.s2c");
            checkConfig(config2, "compression.c2s");
            checkConfig(config2, "compression.s2c");
            checkConfig(config2, "compression_level");
            checkConfig(config2, "StrictHostKeyChecking");
            checkConfig(config2, "HashKnownHosts");
            checkConfig(config2, "PreferredAuthentications");
            checkConfig(config2, "MaxAuthTries");
            checkConfig(config2, "ClearAllForwardings");
            String value = config2.getValue("HostKeyAlias");
            if (value != null) {
                setHostKeyAlias(value);
            }
            String value2 = config2.getValue("UserKnownHostsFile");
            if (value2 != null) {
                KnownHosts knownHosts = new KnownHosts(this.jsch);
                knownHosts.setKnownHosts(value2);
                setHostKeyRepository(knownHosts);
            }
            String[] values = config2.getValues("IdentityFile");
            if (values != null) {
                String[] values2 = configRepository.getConfig("").getValues("IdentityFile");
                if (values2 != null) {
                    for (String addIdentity : values2) {
                        this.jsch.addIdentity(addIdentity);
                    }
                } else {
                    values2 = new String[0];
                }
                if (values.length - values2.length > 0) {
                    IdentityRepository.Wrapper wrapper = new IdentityRepository.Wrapper(this.jsch.getIdentityRepository(), true);
                    for (String str : values) {
                        int i = 0;
                        while (true) {
                            if (i < values2.length) {
                                if (str.equals(values2[i])) {
                                    str = null;
                                    break;
                                }
                                i++;
                            } else {
                                break;
                            }
                        }
                        if (str != null) {
                            wrapper.add((Identity) IdentityFile.newInstance(str, (String) null, this.jsch));
                        }
                    }
                    setIdentityRepository(wrapper);
                }
            }
            String value3 = config2.getValue("ServerAliveInterval");
            if (value3 != null) {
                try {
                    setServerAliveInterval(Integer.parseInt(value3));
                } catch (NumberFormatException unused) {
                }
            }
            String value4 = config2.getValue("ConnectTimeout");
            if (value4 != null) {
                try {
                    setTimeout(Integer.parseInt(value4));
                } catch (NumberFormatException unused2) {
                }
            }
            String value5 = config2.getValue("MaxAuthTries");
            if (value5 != null) {
                setConfig("MaxAuthTries", value5);
            }
            String value6 = config2.getValue("ClearAllForwardings");
            if (value6 != null) {
                setConfig("ClearAllForwardings", value6);
            }
        }
    }

    private void applyConfigChannel(ChannelSession channelSession) throws JSchException {
        ConfigRepository configRepository = this.jsch.getConfigRepository();
        if (configRepository != null) {
            ConfigRepository.Config config2 = configRepository.getConfig(this.org_host);
            String value = config2.getValue("ForwardAgent");
            if (value != null) {
                channelSession.setAgentForwarding(value.equals("yes"));
            }
            String value2 = config2.getValue("RequestTTY");
            if (value2 != null) {
                channelSession.setPty(value2.equals("yes"));
            }
        }
    }

    private void requestPortForwarding() throws JSchException {
        ConfigRepository configRepository;
        if (!getConfig("ClearAllForwardings").equals("yes") && (configRepository = this.jsch.getConfigRepository()) != null) {
            ConfigRepository.Config config2 = configRepository.getConfig(this.org_host);
            String[] values = config2.getValues("LocalForward");
            if (values != null) {
                for (String portForwardingL : values) {
                    setPortForwardingL(portForwardingL);
                }
            }
            String[] values2 = config2.getValues("RemoteForward");
            if (values2 != null) {
                for (String portForwardingR : values2) {
                    setPortForwardingR(portForwardingR);
                }
            }
        }
    }

    private void checkConfig(ConfigRepository.Config config2, String str) {
        String value = config2.getValue(str);
        if (value != null) {
            setConfig(str, value);
        }
    }
}
