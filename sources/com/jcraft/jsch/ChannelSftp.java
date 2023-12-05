package com.jcraft.jsch;

import com.jcraft.jsch.Channel;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.util.Hashtable;
import java.util.Vector;

public class ChannelSftp extends ChannelSession {
    public static final int APPEND = 2;
    private static final int LOCAL_MAXIMUM_PACKET_SIZE = 32768;
    private static final int LOCAL_WINDOW_SIZE_MAX = 2097152;
    private static final int MAX_MSG_LENGTH = 262144;
    public static final int OVERWRITE = 0;
    public static final int RESUME = 1;
    private static final int SSH_FILEXFER_ATTR_ACMODTIME = 8;
    private static final int SSH_FILEXFER_ATTR_EXTENDED = Integer.MIN_VALUE;
    private static final int SSH_FILEXFER_ATTR_PERMISSIONS = 4;
    private static final int SSH_FILEXFER_ATTR_SIZE = 1;
    private static final int SSH_FILEXFER_ATTR_UIDGID = 2;
    private static final int SSH_FXF_APPEND = 4;
    private static final int SSH_FXF_CREAT = 8;
    private static final int SSH_FXF_EXCL = 32;
    private static final int SSH_FXF_READ = 1;
    private static final int SSH_FXF_TRUNC = 16;
    private static final int SSH_FXF_WRITE = 2;
    private static final byte SSH_FXP_ATTRS = 105;
    private static final byte SSH_FXP_CLOSE = 4;
    private static final byte SSH_FXP_DATA = 103;
    private static final byte SSH_FXP_EXTENDED = -56;
    private static final byte SSH_FXP_EXTENDED_REPLY = -55;
    private static final byte SSH_FXP_FSETSTAT = 10;
    private static final byte SSH_FXP_FSTAT = 8;
    private static final byte SSH_FXP_HANDLE = 102;
    private static final byte SSH_FXP_INIT = 1;
    private static final byte SSH_FXP_LSTAT = 7;
    private static final byte SSH_FXP_MKDIR = 14;
    private static final byte SSH_FXP_NAME = 104;
    private static final byte SSH_FXP_OPEN = 3;
    private static final byte SSH_FXP_OPENDIR = 11;
    private static final byte SSH_FXP_READ = 5;
    private static final byte SSH_FXP_READDIR = 12;
    private static final byte SSH_FXP_READLINK = 19;
    private static final byte SSH_FXP_REALPATH = 16;
    private static final byte SSH_FXP_REMOVE = 13;
    private static final byte SSH_FXP_RENAME = 18;
    private static final byte SSH_FXP_RMDIR = 15;
    private static final byte SSH_FXP_SETSTAT = 9;
    private static final byte SSH_FXP_STAT = 17;
    private static final byte SSH_FXP_STATUS = 101;
    private static final byte SSH_FXP_SYMLINK = 20;
    private static final byte SSH_FXP_VERSION = 2;
    private static final byte SSH_FXP_WRITE = 6;
    public static final int SSH_FX_BAD_MESSAGE = 5;
    public static final int SSH_FX_CONNECTION_LOST = 7;
    public static final int SSH_FX_EOF = 1;
    public static final int SSH_FX_FAILURE = 4;
    public static final int SSH_FX_NO_CONNECTION = 6;
    public static final int SSH_FX_NO_SUCH_FILE = 2;
    public static final int SSH_FX_OK = 0;
    public static final int SSH_FX_OP_UNSUPPORTED = 8;
    public static final int SSH_FX_PERMISSION_DENIED = 3;
    private static final String UTF8 = "UTF-8";
    private static final String file_separator = File.separator;
    private static final char file_separatorc = File.separatorChar;
    private static boolean fs_is_bs = (((byte) File.separatorChar) == 92);
    private int[] ackid = new int[1];
    /* access modifiers changed from: private */
    public Buffer buf;
    private int client_version = 3;
    private String cwd;
    private boolean extension_hardlink = false;
    private boolean extension_posix_rename = false;
    private boolean extension_statvfs = false;
    private Hashtable extensions = null;
    private String fEncoding = UTF8;
    private boolean fEncoding_is_utf8 = true;
    private String home;
    private boolean interactive = false;
    /* access modifiers changed from: private */
    public InputStream io_in = null;
    private String lcwd;
    private Buffer obuf;
    private Packet opacket;
    private Packet packet;
    /* access modifiers changed from: private */
    public RequestQueue rq = new RequestQueue(16);
    /* access modifiers changed from: private */
    public int seq = 1;
    /* access modifiers changed from: private */
    public int server_version = 3;
    private String version = String.valueOf(this.client_version);

    public interface LsEntrySelector {
        public static final int BREAK = 1;
        public static final int CONTINUE = 0;

        int select(LsEntry lsEntry);
    }

    /* access modifiers changed from: package-private */
    public void init() {
    }

    public void setBulkRequests(int i) throws JSchException {
        if (i > 0) {
            this.rq = new RequestQueue(i);
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("setBulkRequests: ");
        stringBuffer.append(i);
        stringBuffer.append(" must be greater than 0.");
        throw new JSchException(stringBuffer.toString());
    }

    public int getBulkRequests() {
        return this.rq.size();
    }

    public ChannelSftp() {
        setLocalWindowSizeMax(2097152);
        setLocalWindowSize(2097152);
        setLocalPacketSize(32768);
    }

    public void start() throws JSchException {
        try {
            PipedOutputStream pipedOutputStream = new PipedOutputStream();
            this.io.setOutputStream(pipedOutputStream);
            this.io.setInputStream(new Channel.MyPipedInputStream(pipedOutputStream, this.rmpsize));
            this.io_in = this.io.in;
            if (this.io_in != null) {
                new RequestSftp().request(getSession(), this);
                this.buf = new Buffer(this.lmpsize);
                this.packet = new Packet(this.buf);
                this.obuf = new Buffer(this.rmpsize);
                this.opacket = new Packet(this.obuf);
                sendINIT();
                Header header = header(this.buf, new Header());
                int i = header.length;
                if (i <= 262144) {
                    int i2 = header.type;
                    this.server_version = header.rid;
                    this.extensions = new Hashtable();
                    if (i > 0) {
                        fill(this.buf, i);
                        while (i > 0) {
                            byte[] string = this.buf.getString();
                            int length = i - (string.length + 4);
                            byte[] string2 = this.buf.getString();
                            i = length - (string2.length + 4);
                            this.extensions.put(Util.byte2str(string), Util.byte2str(string2));
                        }
                    }
                    if (this.extensions.get("posix-rename@openssh.com") != null && this.extensions.get("posix-rename@openssh.com").equals("1")) {
                        this.extension_posix_rename = true;
                    }
                    if (this.extensions.get("statvfs@openssh.com") != null && this.extensions.get("statvfs@openssh.com").equals("2")) {
                        this.extension_statvfs = true;
                    }
                    if (this.extensions.get("hardlink@openssh.com") != null && this.extensions.get("hardlink@openssh.com").equals("1")) {
                        this.extension_hardlink = true;
                    }
                    this.lcwd = new File(".").getCanonicalPath();
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Received message is too long: ");
                stringBuffer.append(i);
                throw new SftpException(4, stringBuffer.toString());
            }
            throw new JSchException("channel is down");
        } catch (Exception e) {
            if (e instanceof JSchException) {
                throw ((JSchException) e);
            } else if (e instanceof Throwable) {
                throw new JSchException(e.toString(), e);
            } else {
                throw new JSchException(e.toString());
            }
        }
    }

    public void quit() {
        disconnect();
    }

    public void exit() {
        disconnect();
    }

    public void lcd(String str) throws SftpException {
        String localAbsolutePath = localAbsolutePath(str);
        if (new File(localAbsolutePath).isDirectory()) {
            try {
                localAbsolutePath = new File(localAbsolutePath).getCanonicalPath();
            } catch (Exception unused) {
            }
            this.lcwd = localAbsolutePath;
            return;
        }
        throw new SftpException(2, "No such directory");
    }

    public void cd(String str) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            String isUnique = isUnique(remoteAbsolutePath(str));
            byte[] _realpath = _realpath(isUnique);
            SftpATTRS _stat = _stat(_realpath);
            if ((_stat.getFlags() & 4) == 0) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Can't change directory: ");
                stringBuffer.append(isUnique);
                throw new SftpException(4, stringBuffer.toString());
            } else if (_stat.isDir()) {
                setCwd(Util.byte2str(_realpath, this.fEncoding));
            } else {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Can't change directory: ");
                stringBuffer2.append(isUnique);
                throw new SftpException(4, stringBuffer2.toString());
            }
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            } else if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            } else {
                throw new SftpException(4, "");
            }
        }
    }

    public void put(String str, String str2) throws SftpException {
        put(str, str2, (SftpProgressMonitor) null, 0);
    }

    public void put(String str, String str2, int i) throws SftpException {
        put(str, str2, (SftpProgressMonitor) null, i);
    }

    public void put(String str, String str2, SftpProgressMonitor sftpProgressMonitor) throws SftpException {
        put(str, str2, sftpProgressMonitor, 0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0134, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0135, code lost:
        r2 = null;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:40:0x00cb */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00d8 A[Catch:{ Exception -> 0x0146 }] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0138 A[Catch:{ Exception -> 0x0146 }] */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x00dd A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void put(java.lang.String r24, java.lang.String r25, com.jcraft.jsch.SftpProgressMonitor r26, int r27) throws com.jcraft.jsch.SftpException {
        /*
            r23 = this;
            r1 = r23
            r0 = r26
            r8 = r27
            r9 = 4
            java.io.InputStream r2 = r1.io_in     // Catch:{ Exception -> 0x0146 }
            com.jcraft.jsch.Channel$MyPipedInputStream r2 = (com.jcraft.jsch.Channel.MyPipedInputStream) r2     // Catch:{ Exception -> 0x0146 }
            r2.updateReadSide()     // Catch:{ Exception -> 0x0146 }
            java.lang.String r2 = r23.localAbsolutePath(r24)     // Catch:{ Exception -> 0x0146 }
            r3 = r25
            java.lang.String r3 = r1.remoteAbsolutePath(r3)     // Catch:{ Exception -> 0x0146 }
            java.util.Vector r4 = r1.glob_remote(r3)     // Catch:{ Exception -> 0x0146 }
            int r5 = r4.size()     // Catch:{ Exception -> 0x0146 }
            r10 = 1
            if (r5 == r10) goto L_0x003e
            if (r5 != 0) goto L_0x0034
            boolean r0 = r1.isPattern((java.lang.String) r3)     // Catch:{ Exception -> 0x0146 }
            if (r0 == 0) goto L_0x0031
            com.jcraft.jsch.SftpException r0 = new com.jcraft.jsch.SftpException     // Catch:{ Exception -> 0x0146 }
            r0.<init>(r9, r3)     // Catch:{ Exception -> 0x0146 }
            throw r0     // Catch:{ Exception -> 0x0146 }
        L_0x0031:
            com.jcraft.jsch.Util.unquote((java.lang.String) r3)     // Catch:{ Exception -> 0x0146 }
        L_0x0034:
            com.jcraft.jsch.SftpException r0 = new com.jcraft.jsch.SftpException     // Catch:{ Exception -> 0x0146 }
            java.lang.String r2 = r4.toString()     // Catch:{ Exception -> 0x0146 }
            r0.<init>(r9, r2)     // Catch:{ Exception -> 0x0146 }
            throw r0     // Catch:{ Exception -> 0x0146 }
        L_0x003e:
            r3 = 0
            java.lang.Object r4 = r4.elementAt(r3)     // Catch:{ Exception -> 0x0146 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Exception -> 0x0146 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Exception -> 0x0146 }
            boolean r11 = r1.isRemoteDir(r4)     // Catch:{ Exception -> 0x0146 }
            java.util.Vector r12 = r1.glob_local(r2)     // Catch:{ Exception -> 0x0146 }
            int r13 = r12.size()     // Catch:{ Exception -> 0x0146 }
            if (r11 == 0) goto L_0x0076
            java.lang.String r2 = "/"
            boolean r2 = r4.endsWith(r2)     // Catch:{ Exception -> 0x0146 }
            if (r2 != 0) goto L_0x006e
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x0146 }
            r2.<init>()     // Catch:{ Exception -> 0x0146 }
            r2.append(r4)     // Catch:{ Exception -> 0x0146 }
            java.lang.String r4 = "/"
            r2.append(r4)     // Catch:{ Exception -> 0x0146 }
            java.lang.String r4 = r2.toString()     // Catch:{ Exception -> 0x0146 }
        L_0x006e:
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x0146 }
            r2.<init>(r4)     // Catch:{ Exception -> 0x0146 }
            r6 = r2
            r15 = r4
            goto L_0x007a
        L_0x0076:
            if (r13 > r10) goto L_0x013d
            r15 = r4
            r6 = 0
        L_0x007a:
            r7 = 0
        L_0x007b:
            if (r7 >= r13) goto L_0x013c
            java.lang.Object r2 = r12.elementAt(r7)     // Catch:{ Exception -> 0x0146 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Exception -> 0x0146 }
            r5 = r2
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ Exception -> 0x0146 }
            if (r11 == 0) goto L_0x00be
            char r2 = file_separatorc     // Catch:{ Exception -> 0x0146 }
            int r2 = r5.lastIndexOf(r2)     // Catch:{ Exception -> 0x0146 }
            boolean r3 = fs_is_bs     // Catch:{ Exception -> 0x0146 }
            r4 = -1
            if (r3 == 0) goto L_0x009e
            r3 = 47
            int r3 = r5.lastIndexOf(r3)     // Catch:{ Exception -> 0x0146 }
            if (r3 == r4) goto L_0x009e
            if (r3 <= r2) goto L_0x009e
            r2 = r3
        L_0x009e:
            if (r2 != r4) goto L_0x00a4
            r6.append(r5)     // Catch:{ Exception -> 0x0146 }
            goto L_0x00ad
        L_0x00a4:
            int r2 = r2 + 1
            java.lang.String r2 = r5.substring(r2)     // Catch:{ Exception -> 0x0146 }
            r6.append(r2)     // Catch:{ Exception -> 0x0146 }
        L_0x00ad:
            java.lang.String r2 = r6.toString()     // Catch:{ Exception -> 0x0146 }
            int r3 = r15.length()     // Catch:{ Exception -> 0x0146 }
            int r4 = r2.length()     // Catch:{ Exception -> 0x0146 }
            r6.delete(r3, r4)     // Catch:{ Exception -> 0x0146 }
            r4 = r2
            goto L_0x00bf
        L_0x00be:
            r4 = r15
        L_0x00bf:
            r2 = 0
            if (r8 != r10) goto L_0x00f4
            com.jcraft.jsch.SftpATTRS r16 = r1._stat((java.lang.String) r4)     // Catch:{ Exception -> 0x00cb }
            long r2 = r16.getSize()     // Catch:{ Exception -> 0x00cb }
        L_0x00cb:
            java.io.File r14 = new java.io.File     // Catch:{ Exception -> 0x0146 }
            r14.<init>(r5)     // Catch:{ Exception -> 0x0146 }
            long r16 = r14.length()     // Catch:{ Exception -> 0x0146 }
            int r14 = (r16 > r2 ? 1 : (r16 == r2 ? 0 : -1))
            if (r14 < 0) goto L_0x00dd
            int r14 = (r16 > r2 ? 1 : (r16 == r2 ? 0 : -1))
            if (r14 != 0) goto L_0x00f4
            return
        L_0x00dd:
            com.jcraft.jsch.SftpException r0 = new com.jcraft.jsch.SftpException     // Catch:{ Exception -> 0x0146 }
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x0146 }
            r2.<init>()     // Catch:{ Exception -> 0x0146 }
            java.lang.String r3 = "failed to resume for "
            r2.append(r3)     // Catch:{ Exception -> 0x0146 }
            r2.append(r4)     // Catch:{ Exception -> 0x0146 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0146 }
            r0.<init>(r9, r2)     // Catch:{ Exception -> 0x0146 }
            throw r0     // Catch:{ Exception -> 0x0146 }
        L_0x00f4:
            if (r0 == 0) goto L_0x011a
            r14 = 0
            java.io.File r9 = new java.io.File     // Catch:{ Exception -> 0x0146 }
            r9.<init>(r5)     // Catch:{ Exception -> 0x0146 }
            long r17 = r9.length()     // Catch:{ Exception -> 0x0146 }
            r19 = r2
            r2 = r26
            r3 = r14
            r9 = r4
            r4 = r5
            r14 = r5
            r5 = r9
            r22 = r6
            r21 = r7
            r6 = r17
            r2.init(r3, r4, r5, r6)     // Catch:{ Exception -> 0x0146 }
            if (r8 != r10) goto L_0x0120
            r2 = r19
            r0.count(r2)     // Catch:{ Exception -> 0x0146 }
            goto L_0x0120
        L_0x011a:
            r9 = r4
            r14 = r5
            r22 = r6
            r21 = r7
        L_0x0120:
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ all -> 0x0134 }
            r2.<init>(r14)     // Catch:{ all -> 0x0134 }
            r1._put(r2, r9, r0, r8)     // Catch:{ all -> 0x0132 }
            r2.close()     // Catch:{ Exception -> 0x0146 }
            int r7 = r21 + 1
            r6 = r22
            r9 = 4
            goto L_0x007b
        L_0x0132:
            r0 = move-exception
            goto L_0x0136
        L_0x0134:
            r0 = move-exception
            r2 = 0
        L_0x0136:
            if (r2 == 0) goto L_0x013b
            r2.close()     // Catch:{ Exception -> 0x0146 }
        L_0x013b:
            throw r0     // Catch:{ Exception -> 0x0146 }
        L_0x013c:
            return
        L_0x013d:
            com.jcraft.jsch.SftpException r0 = new com.jcraft.jsch.SftpException     // Catch:{ Exception -> 0x0146 }
            java.lang.String r2 = "Copying multiple files, but the destination is missing or a file."
            r3 = 4
            r0.<init>(r3, r2)     // Catch:{ Exception -> 0x0146 }
            throw r0     // Catch:{ Exception -> 0x0146 }
        L_0x0146:
            r0 = move-exception
            boolean r2 = r0 instanceof com.jcraft.jsch.SftpException
            if (r2 != 0) goto L_0x0165
            boolean r2 = r0 instanceof java.lang.Throwable
            if (r2 == 0) goto L_0x015a
            com.jcraft.jsch.SftpException r2 = new com.jcraft.jsch.SftpException
            java.lang.String r3 = r0.toString()
            r4 = 4
            r2.<init>(r4, r3, r0)
            throw r2
        L_0x015a:
            r4 = 4
            com.jcraft.jsch.SftpException r2 = new com.jcraft.jsch.SftpException
            java.lang.String r0 = r0.toString()
            r2.<init>(r4, r0)
            throw r2
        L_0x0165:
            com.jcraft.jsch.SftpException r0 = (com.jcraft.jsch.SftpException) r0
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.ChannelSftp.put(java.lang.String, java.lang.String, com.jcraft.jsch.SftpProgressMonitor, int):void");
    }

    public void put(InputStream inputStream, String str) throws SftpException {
        put(inputStream, str, (SftpProgressMonitor) null, 0);
    }

    public void put(InputStream inputStream, String str, int i) throws SftpException {
        put(inputStream, str, (SftpProgressMonitor) null, i);
    }

    public void put(InputStream inputStream, String str, SftpProgressMonitor sftpProgressMonitor) throws SftpException {
        put(inputStream, str, sftpProgressMonitor, 0);
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0055  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0079  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void put(java.io.InputStream r9, java.lang.String r10, com.jcraft.jsch.SftpProgressMonitor r11, int r12) throws com.jcraft.jsch.SftpException {
        /*
            r8 = this;
            r0 = 4
            java.io.InputStream r1 = r8.io_in     // Catch:{ Exception -> 0x0050 }
            com.jcraft.jsch.Channel$MyPipedInputStream r1 = (com.jcraft.jsch.Channel.MyPipedInputStream) r1     // Catch:{ Exception -> 0x0050 }
            r1.updateReadSide()     // Catch:{ Exception -> 0x0050 }
            java.lang.String r10 = r8.remoteAbsolutePath(r10)     // Catch:{ Exception -> 0x0050 }
            java.util.Vector r1 = r8.glob_remote(r10)     // Catch:{ Exception -> 0x0050 }
            int r2 = r1.size()     // Catch:{ Exception -> 0x0050 }
            r3 = 1
            if (r2 == r3) goto L_0x0034
            if (r2 != 0) goto L_0x002a
            boolean r9 = r8.isPattern((java.lang.String) r10)     // Catch:{ Exception -> 0x0050 }
            if (r9 == 0) goto L_0x0025
            com.jcraft.jsch.SftpException r9 = new com.jcraft.jsch.SftpException     // Catch:{ Exception -> 0x0050 }
            r9.<init>(r0, r10)     // Catch:{ Exception -> 0x0050 }
            throw r9     // Catch:{ Exception -> 0x0050 }
        L_0x0025:
            java.lang.String r9 = com.jcraft.jsch.Util.unquote((java.lang.String) r10)     // Catch:{ Exception -> 0x0050 }
            r10 = r9
        L_0x002a:
            com.jcraft.jsch.SftpException r9 = new com.jcraft.jsch.SftpException     // Catch:{ Exception -> 0x0050 }
            java.lang.String r11 = r1.toString()     // Catch:{ Exception -> 0x0050 }
            r9.<init>(r0, r11)     // Catch:{ Exception -> 0x0050 }
            throw r9     // Catch:{ Exception -> 0x0050 }
        L_0x0034:
            r2 = 0
            java.lang.Object r1 = r1.elementAt(r2)     // Catch:{ Exception -> 0x0050 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Exception -> 0x0050 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Exception -> 0x0050 }
            if (r11 == 0) goto L_0x0049
            r3 = 0
            java.lang.String r4 = "-"
            r6 = -1
            r2 = r11
            r5 = r1
            r2.init(r3, r4, r5, r6)     // Catch:{ Exception -> 0x004d }
        L_0x0049:
            r8._put(r9, r1, r11, r12)     // Catch:{ Exception -> 0x004d }
            return
        L_0x004d:
            r9 = move-exception
            r10 = r1
            goto L_0x0051
        L_0x0050:
            r9 = move-exception
        L_0x0051:
            boolean r11 = r9 instanceof com.jcraft.jsch.SftpException
            if (r11 == 0) goto L_0x0079
            com.jcraft.jsch.SftpException r9 = (com.jcraft.jsch.SftpException) r9
            int r11 = r9.id
            if (r11 != r0) goto L_0x0078
            boolean r11 = r8.isRemoteDir(r10)
            if (r11 == 0) goto L_0x0078
            com.jcraft.jsch.SftpException r9 = new com.jcraft.jsch.SftpException
            java.lang.StringBuffer r11 = new java.lang.StringBuffer
            r11.<init>()
            r11.append(r10)
            java.lang.String r10 = " is a directory"
            r11.append(r10)
            java.lang.String r10 = r11.toString()
            r9.<init>(r0, r10)
            throw r9
        L_0x0078:
            throw r9
        L_0x0079:
            boolean r10 = r9 instanceof java.lang.Throwable
            if (r10 == 0) goto L_0x0087
            com.jcraft.jsch.SftpException r10 = new com.jcraft.jsch.SftpException
            java.lang.String r11 = r9.toString()
            r10.<init>(r0, r11, r9)
            throw r10
        L_0x0087:
            com.jcraft.jsch.SftpException r10 = new com.jcraft.jsch.SftpException
            java.lang.String r9 = r9.toString()
            r10.<init>(r0, r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.ChannelSftp.put(java.io.InputStream, java.lang.String, com.jcraft.jsch.SftpProgressMonitor, int):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0038 A[Catch:{ Exception -> 0x01cc }] */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0039 A[Catch:{ Exception -> 0x01cc }] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x017e A[Catch:{ Exception -> 0x01cc }] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0192 A[Catch:{ Exception -> 0x01cc }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void _put(java.io.InputStream r23, java.lang.String r24, com.jcraft.jsch.SftpProgressMonitor r25, int r26) throws com.jcraft.jsch.SftpException {
        /*
            r22 = this;
            r8 = r22
            r0 = r23
            r1 = r24
            r9 = r25
            r2 = r26
            r10 = 4
            java.io.InputStream r3 = r8.io_in     // Catch:{ Exception -> 0x01cc }
            com.jcraft.jsch.Channel$MyPipedInputStream r3 = (com.jcraft.jsch.Channel.MyPipedInputStream) r3     // Catch:{ Exception -> 0x01cc }
            r3.updateReadSide()     // Catch:{ Exception -> 0x01cc }
            java.lang.String r3 = r8.fEncoding     // Catch:{ Exception -> 0x01cc }
            byte[] r3 = com.jcraft.jsch.Util.str2byte(r1, r3)     // Catch:{ Exception -> 0x01cc }
            r4 = 2
            r5 = 0
            r11 = 1
            if (r2 == r11) goto L_0x0020
            if (r2 != r4) goto L_0x0029
        L_0x0020:
            com.jcraft.jsch.SftpATTRS r7 = r8._stat((byte[]) r3)     // Catch:{ Exception -> 0x0029 }
            long r12 = r7.getSize()     // Catch:{ Exception -> 0x0029 }
            goto L_0x002a
        L_0x0029:
            r12 = r5
        L_0x002a:
            if (r2 != r11) goto L_0x0050
            int r7 = (r12 > r5 ? 1 : (r12 == r5 ? 0 : -1))
            if (r7 <= 0) goto L_0x0050
            long r14 = r0.skip(r12)     // Catch:{ Exception -> 0x01cc }
            int r7 = (r14 > r12 ? 1 : (r14 == r12 ? 0 : -1))
            if (r7 < 0) goto L_0x0039
            goto L_0x0050
        L_0x0039:
            com.jcraft.jsch.SftpException r0 = new com.jcraft.jsch.SftpException     // Catch:{ Exception -> 0x01cc }
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x01cc }
            r2.<init>()     // Catch:{ Exception -> 0x01cc }
            java.lang.String r3 = "failed to resume for "
            r2.append(r3)     // Catch:{ Exception -> 0x01cc }
            r2.append(r1)     // Catch:{ Exception -> 0x01cc }
            java.lang.String r1 = r2.toString()     // Catch:{ Exception -> 0x01cc }
            r0.<init>(r10, r1)     // Catch:{ Exception -> 0x01cc }
            throw r0     // Catch:{ Exception -> 0x01cc }
        L_0x0050:
            if (r2 != 0) goto L_0x0056
            r8.sendOPENW(r3)     // Catch:{ Exception -> 0x01cc }
            goto L_0x0059
        L_0x0056:
            r8.sendOPENA(r3)     // Catch:{ Exception -> 0x01cc }
        L_0x0059:
            com.jcraft.jsch.ChannelSftp$Header r1 = new com.jcraft.jsch.ChannelSftp$Header     // Catch:{ Exception -> 0x01cc }
            r1.<init>()     // Catch:{ Exception -> 0x01cc }
            com.jcraft.jsch.Buffer r3 = r8.buf     // Catch:{ Exception -> 0x01cc }
            com.jcraft.jsch.ChannelSftp$Header r14 = r8.header(r3, r1)     // Catch:{ Exception -> 0x01cc }
            int r1 = r14.length     // Catch:{ Exception -> 0x01cc }
            int r3 = r14.type     // Catch:{ Exception -> 0x01cc }
            com.jcraft.jsch.Buffer r7 = r8.buf     // Catch:{ Exception -> 0x01cc }
            r8.fill(r7, r1)     // Catch:{ Exception -> 0x01cc }
            r1 = 101(0x65, float:1.42E-43)
            if (r3 == r1) goto L_0x008d
            r7 = 102(0x66, float:1.43E-43)
            if (r3 != r7) goto L_0x0076
            goto L_0x008d
        L_0x0076:
            com.jcraft.jsch.SftpException r0 = new com.jcraft.jsch.SftpException     // Catch:{ Exception -> 0x01cc }
            java.lang.StringBuffer r1 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x01cc }
            r1.<init>()     // Catch:{ Exception -> 0x01cc }
            java.lang.String r2 = "invalid type="
            r1.append(r2)     // Catch:{ Exception -> 0x01cc }
            r1.append(r3)     // Catch:{ Exception -> 0x01cc }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x01cc }
            r0.<init>(r10, r1)     // Catch:{ Exception -> 0x01cc }
            throw r0     // Catch:{ Exception -> 0x01cc }
        L_0x008d:
            if (r3 != r1) goto L_0x009a
            com.jcraft.jsch.Buffer r1 = r8.buf     // Catch:{ Exception -> 0x01cc }
            int r1 = r1.getInt()     // Catch:{ Exception -> 0x01cc }
            com.jcraft.jsch.Buffer r3 = r8.buf     // Catch:{ Exception -> 0x01cc }
            r8.throwStatusError(r3, r1)     // Catch:{ Exception -> 0x01cc }
        L_0x009a:
            com.jcraft.jsch.Buffer r1 = r8.buf     // Catch:{ Exception -> 0x01cc }
            byte[] r15 = r1.getString()     // Catch:{ Exception -> 0x01cc }
            if (r2 == r11) goto L_0x00a4
            if (r2 != r4) goto L_0x00a5
        L_0x00a4:
            long r5 = r5 + r12
        L_0x00a5:
            int r12 = r8.seq     // Catch:{ Exception -> 0x01cc }
            com.jcraft.jsch.Buffer r1 = r8.obuf     // Catch:{ Exception -> 0x01cc }
            byte[] r1 = r1.buffer     // Catch:{ Exception -> 0x01cc }
            int r2 = r15.length     // Catch:{ Exception -> 0x01cc }
            int r13 = r2 + 39
            com.jcraft.jsch.Buffer r2 = r8.obuf     // Catch:{ Exception -> 0x01cc }
            byte[] r2 = r2.buffer     // Catch:{ Exception -> 0x01cc }
            int r2 = r2.length     // Catch:{ Exception -> 0x01cc }
            int r2 = r2 - r13
            int r2 = r2 + -128
            com.jcraft.jsch.ChannelSftp$RequestQueue r3 = r8.rq     // Catch:{ Exception -> 0x01cc }
            int r7 = r3.size()     // Catch:{ Exception -> 0x01cc }
            r16 = 0
            r17 = r5
            r3 = 0
        L_0x00c1:
            r5 = r2
            r4 = r13
            r6 = 0
        L_0x00c4:
            int r19 = r0.read(r1, r4, r5)     // Catch:{ Exception -> 0x01cc }
            if (r19 <= 0) goto L_0x00d0
            int r4 = r4 + r19
            int r5 = r5 - r19
            int r6 = r6 + r19
        L_0x00d0:
            if (r5 <= 0) goto L_0x00d4
            if (r19 > 0) goto L_0x00c4
        L_0x00d4:
            if (r6 > 0) goto L_0x00d8
            goto L_0x01aa
        L_0x00d8:
            r5 = r1
            r20 = r2
            r19 = r6
        L_0x00dd:
            if (r19 <= 0) goto L_0x019b
            int r1 = r8.seq     // Catch:{ Exception -> 0x01cc }
            int r1 = r1 - r11
            if (r1 == r12) goto L_0x00ed
            int r1 = r8.seq     // Catch:{ Exception -> 0x01cc }
            int r1 = r1 - r12
            int r1 = r1 - r3
            if (r1 < r7) goto L_0x00eb
            goto L_0x00ed
        L_0x00eb:
            r11 = r3
            goto L_0x0160
        L_0x00ed:
            int r1 = r8.seq     // Catch:{ Exception -> 0x01cc }
            int r1 = r1 - r12
            int r1 = r1 - r3
            if (r1 < r7) goto L_0x015f
            int[] r1 = r8.ackid     // Catch:{ Exception -> 0x01cc }
            boolean r1 = r8.checkStatus(r1, r14)     // Catch:{ Exception -> 0x01cc }
            if (r1 == 0) goto L_0x015f
            int[] r1 = r8.ackid     // Catch:{ Exception -> 0x01cc }
            r1 = r1[r16]     // Catch:{ Exception -> 0x01cc }
            if (r12 > r1) goto L_0x0106
            int r2 = r8.seq     // Catch:{ Exception -> 0x01cc }
            int r2 = r2 - r11
            if (r1 <= r2) goto L_0x0132
        L_0x0106:
            int r2 = r8.seq     // Catch:{ Exception -> 0x01cc }
            if (r1 != r2) goto L_0x0136
            java.io.PrintStream r2 = java.lang.System.err     // Catch:{ Exception -> 0x01cc }
            java.lang.StringBuffer r4 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x01cc }
            r4.<init>()     // Catch:{ Exception -> 0x01cc }
            java.lang.String r11 = "ack error: startid="
            r4.append(r11)     // Catch:{ Exception -> 0x01cc }
            r4.append(r12)     // Catch:{ Exception -> 0x01cc }
            java.lang.String r11 = " seq="
            r4.append(r11)     // Catch:{ Exception -> 0x01cc }
            int r11 = r8.seq     // Catch:{ Exception -> 0x01cc }
            r4.append(r11)     // Catch:{ Exception -> 0x01cc }
            java.lang.String r11 = " _ackid="
            r4.append(r11)     // Catch:{ Exception -> 0x01cc }
            r4.append(r1)     // Catch:{ Exception -> 0x01cc }
            java.lang.String r1 = r4.toString()     // Catch:{ Exception -> 0x01cc }
            r2.println(r1)     // Catch:{ Exception -> 0x01cc }
        L_0x0132:
            int r3 = r3 + 1
            r11 = 1
            goto L_0x00ed
        L_0x0136:
            com.jcraft.jsch.SftpException r0 = new com.jcraft.jsch.SftpException     // Catch:{ Exception -> 0x01cc }
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x01cc }
            r2.<init>()     // Catch:{ Exception -> 0x01cc }
            java.lang.String r3 = "ack error: startid="
            r2.append(r3)     // Catch:{ Exception -> 0x01cc }
            r2.append(r12)     // Catch:{ Exception -> 0x01cc }
            java.lang.String r3 = " seq="
            r2.append(r3)     // Catch:{ Exception -> 0x01cc }
            int r3 = r8.seq     // Catch:{ Exception -> 0x01cc }
            r2.append(r3)     // Catch:{ Exception -> 0x01cc }
            java.lang.String r3 = " _ackid="
            r2.append(r3)     // Catch:{ Exception -> 0x01cc }
            r2.append(r1)     // Catch:{ Exception -> 0x01cc }
            java.lang.String r1 = r2.toString()     // Catch:{ Exception -> 0x01cc }
            r0.<init>(r10, r1)     // Catch:{ Exception -> 0x01cc }
            throw r0     // Catch:{ Exception -> 0x01cc }
        L_0x015f:
            r11 = r3
        L_0x0160:
            r21 = 0
            r1 = r22
            r2 = r15
            r3 = r17
            r24 = r5
            r10 = r6
            r6 = r21
            r21 = r7
            r7 = r19
            int r1 = r1.sendWRITE(r2, r3, r5, r6, r7)     // Catch:{ Exception -> 0x01cc }
            int r19 = r19 - r1
            com.jcraft.jsch.Buffer r1 = r8.obuf     // Catch:{ Exception -> 0x01cc }
            byte[] r1 = r1.buffer     // Catch:{ Exception -> 0x01cc }
            r2 = r24
            if (r2 == r1) goto L_0x0192
            com.jcraft.jsch.Buffer r1 = r8.obuf     // Catch:{ Exception -> 0x01cc }
            byte[] r5 = r1.buffer     // Catch:{ Exception -> 0x01cc }
            com.jcraft.jsch.Buffer r1 = r8.obuf     // Catch:{ Exception -> 0x01cc }
            byte[] r1 = r1.buffer     // Catch:{ Exception -> 0x01cc }
            int r1 = r1.length     // Catch:{ Exception -> 0x01cc }
            int r1 = r1 - r13
            int r20 = r1 + -128
            r6 = r10
            r3 = r11
            r7 = r21
            r10 = 4
            r11 = 1
            goto L_0x00dd
        L_0x0192:
            r5 = r2
            r6 = r10
            r3 = r11
            r7 = r21
            r10 = 4
            r11 = 1
            goto L_0x00dd
        L_0x019b:
            r2 = r5
            r10 = r6
            r21 = r7
            long r4 = (long) r10     // Catch:{ Exception -> 0x01cc }
            long r17 = r17 + r4
            if (r9 == 0) goto L_0x01c3
            boolean r1 = r9.count(r4)     // Catch:{ Exception -> 0x01cc }
            if (r1 != 0) goto L_0x01c3
        L_0x01aa:
            int r0 = r8.seq     // Catch:{ Exception -> 0x01cc }
            int r0 = r0 - r12
        L_0x01ad:
            if (r0 <= r3) goto L_0x01ba
            r1 = 0
            boolean r1 = r8.checkStatus(r1, r14)     // Catch:{ Exception -> 0x01cc }
            if (r1 != 0) goto L_0x01b7
            goto L_0x01ba
        L_0x01b7:
            int r3 = r3 + 1
            goto L_0x01ad
        L_0x01ba:
            if (r9 == 0) goto L_0x01bf
            r25.end()     // Catch:{ Exception -> 0x01cc }
        L_0x01bf:
            r8._sendCLOSE(r15, r14)     // Catch:{ Exception -> 0x01cc }
            return
        L_0x01c3:
            r1 = r2
            r2 = r20
            r7 = r21
            r10 = 4
            r11 = 1
            goto L_0x00c1
        L_0x01cc:
            r0 = move-exception
            boolean r1 = r0 instanceof com.jcraft.jsch.SftpException
            if (r1 != 0) goto L_0x01eb
            boolean r1 = r0 instanceof java.lang.Throwable
            if (r1 == 0) goto L_0x01e0
            com.jcraft.jsch.SftpException r1 = new com.jcraft.jsch.SftpException
            java.lang.String r2 = r0.toString()
            r3 = 4
            r1.<init>(r3, r2, r0)
            throw r1
        L_0x01e0:
            r3 = 4
            com.jcraft.jsch.SftpException r1 = new com.jcraft.jsch.SftpException
            java.lang.String r0 = r0.toString()
            r1.<init>(r3, r0)
            throw r1
        L_0x01eb:
            com.jcraft.jsch.SftpException r0 = (com.jcraft.jsch.SftpException) r0
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.ChannelSftp._put(java.io.InputStream, java.lang.String, com.jcraft.jsch.SftpProgressMonitor, int):void");
    }

    public OutputStream put(String str) throws SftpException {
        return put(str, (SftpProgressMonitor) null, 0);
    }

    public OutputStream put(String str, int i) throws SftpException {
        return put(str, (SftpProgressMonitor) null, i);
    }

    public OutputStream put(String str, SftpProgressMonitor sftpProgressMonitor, int i) throws SftpException {
        return put(str, sftpProgressMonitor, i, 0);
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0042 A[Catch:{ Exception -> 0x00ad }] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0046 A[Catch:{ Exception -> 0x00ad }] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0061 A[Catch:{ Exception -> 0x00ad }] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0070 A[Catch:{ Exception -> 0x00ad }] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0083 A[Catch:{ Exception -> 0x00ad }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.io.OutputStream put(java.lang.String r16, com.jcraft.jsch.SftpProgressMonitor r17, int r18, long r19) throws com.jcraft.jsch.SftpException {
        /*
            r15 = this;
            r1 = r15
            r0 = r17
            r8 = r18
            r9 = 4
            java.io.InputStream r2 = r1.io_in     // Catch:{ Exception -> 0x00ad }
            com.jcraft.jsch.Channel$MyPipedInputStream r2 = (com.jcraft.jsch.Channel.MyPipedInputStream) r2     // Catch:{ Exception -> 0x00ad }
            r2.updateReadSide()     // Catch:{ Exception -> 0x00ad }
            java.lang.String r2 = r15.remoteAbsolutePath(r16)     // Catch:{ Exception -> 0x00ad }
            java.lang.String r5 = r15.isUnique(r2)     // Catch:{ Exception -> 0x00ad }
            boolean r2 = r15.isRemoteDir(r5)     // Catch:{ Exception -> 0x00ad }
            if (r2 != 0) goto L_0x0096
            java.lang.String r2 = r1.fEncoding     // Catch:{ Exception -> 0x00ad }
            byte[] r10 = com.jcraft.jsch.Util.str2byte(r5, r2)     // Catch:{ Exception -> 0x00ad }
            r2 = 0
            r11 = 2
            r12 = 1
            if (r8 == r12) goto L_0x0029
            if (r8 != r11) goto L_0x0033
        L_0x0029:
            com.jcraft.jsch.SftpATTRS r4 = r15._stat((byte[]) r10)     // Catch:{ Exception -> 0x0033 }
            long r2 = r4.getSize()     // Catch:{ Exception -> 0x0033 }
            r13 = r2
            goto L_0x0034
        L_0x0033:
            r13 = r2
        L_0x0034:
            if (r0 == 0) goto L_0x0040
            r3 = 0
            java.lang.String r4 = "-"
            r6 = -1
            r2 = r17
            r2.init(r3, r4, r5, r6)     // Catch:{ Exception -> 0x00ad }
        L_0x0040:
            if (r8 != 0) goto L_0x0046
            r15.sendOPENW(r10)     // Catch:{ Exception -> 0x00ad }
            goto L_0x0049
        L_0x0046:
            r15.sendOPENA(r10)     // Catch:{ Exception -> 0x00ad }
        L_0x0049:
            com.jcraft.jsch.ChannelSftp$Header r2 = new com.jcraft.jsch.ChannelSftp$Header     // Catch:{ Exception -> 0x00ad }
            r2.<init>()     // Catch:{ Exception -> 0x00ad }
            com.jcraft.jsch.Buffer r3 = r1.buf     // Catch:{ Exception -> 0x00ad }
            com.jcraft.jsch.ChannelSftp$Header r2 = r15.header(r3, r2)     // Catch:{ Exception -> 0x00ad }
            int r3 = r2.length     // Catch:{ Exception -> 0x00ad }
            int r2 = r2.type     // Catch:{ Exception -> 0x00ad }
            com.jcraft.jsch.Buffer r4 = r1.buf     // Catch:{ Exception -> 0x00ad }
            r15.fill(r4, r3)     // Catch:{ Exception -> 0x00ad }
            r3 = 101(0x65, float:1.42E-43)
            if (r2 == r3) goto L_0x006e
            r4 = 102(0x66, float:1.43E-43)
            if (r2 != r4) goto L_0x0066
            goto L_0x006e
        L_0x0066:
            com.jcraft.jsch.SftpException r0 = new com.jcraft.jsch.SftpException     // Catch:{ Exception -> 0x00ad }
            java.lang.String r2 = ""
            r0.<init>(r9, r2)     // Catch:{ Exception -> 0x00ad }
            throw r0     // Catch:{ Exception -> 0x00ad }
        L_0x006e:
            if (r2 != r3) goto L_0x007b
            com.jcraft.jsch.Buffer r2 = r1.buf     // Catch:{ Exception -> 0x00ad }
            int r2 = r2.getInt()     // Catch:{ Exception -> 0x00ad }
            com.jcraft.jsch.Buffer r3 = r1.buf     // Catch:{ Exception -> 0x00ad }
            r15.throwStatusError(r3, r2)     // Catch:{ Exception -> 0x00ad }
        L_0x007b:
            com.jcraft.jsch.Buffer r2 = r1.buf     // Catch:{ Exception -> 0x00ad }
            byte[] r2 = r2.getString()     // Catch:{ Exception -> 0x00ad }
            if (r8 == r12) goto L_0x0089
            if (r8 != r11) goto L_0x0086
            goto L_0x0089
        L_0x0086:
            r3 = r19
            goto L_0x008b
        L_0x0089:
            long r3 = r19 + r13
        L_0x008b:
            long[] r5 = new long[r12]     // Catch:{ Exception -> 0x00ad }
            r6 = 0
            r5[r6] = r3     // Catch:{ Exception -> 0x00ad }
            com.jcraft.jsch.ChannelSftp$1 r3 = new com.jcraft.jsch.ChannelSftp$1     // Catch:{ Exception -> 0x00ad }
            r3.<init>(r2, r5, r0)     // Catch:{ Exception -> 0x00ad }
            return r3
        L_0x0096:
            com.jcraft.jsch.SftpException r0 = new com.jcraft.jsch.SftpException     // Catch:{ Exception -> 0x00ad }
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x00ad }
            r2.<init>()     // Catch:{ Exception -> 0x00ad }
            r2.append(r5)     // Catch:{ Exception -> 0x00ad }
            java.lang.String r3 = " is a directory"
            r2.append(r3)     // Catch:{ Exception -> 0x00ad }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00ad }
            r0.<init>(r9, r2)     // Catch:{ Exception -> 0x00ad }
            throw r0     // Catch:{ Exception -> 0x00ad }
        L_0x00ad:
            r0 = move-exception
            boolean r2 = r0 instanceof com.jcraft.jsch.SftpException
            if (r2 != 0) goto L_0x00c6
            boolean r2 = r0 instanceof java.lang.Throwable
            if (r2 == 0) goto L_0x00be
            com.jcraft.jsch.SftpException r2 = new com.jcraft.jsch.SftpException
            java.lang.String r3 = ""
            r2.<init>(r9, r3, r0)
            throw r2
        L_0x00be:
            com.jcraft.jsch.SftpException r0 = new com.jcraft.jsch.SftpException
            java.lang.String r2 = ""
            r0.<init>(r9, r2)
            throw r0
        L_0x00c6:
            com.jcraft.jsch.SftpException r0 = (com.jcraft.jsch.SftpException) r0
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.ChannelSftp.put(java.lang.String, com.jcraft.jsch.SftpProgressMonitor, int, long):java.io.OutputStream");
    }

    public void get(String str, String str2) throws SftpException {
        get(str, str2, (SftpProgressMonitor) null, 0);
    }

    public void get(String str, String str2, SftpProgressMonitor sftpProgressMonitor) throws SftpException {
        get(str, str2, sftpProgressMonitor, 0);
    }

    /* JADX WARNING: Removed duplicated region for block: B:104:0x01f7  */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x020d  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0199 A[Catch:{ Exception -> 0x019d }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void get(java.lang.String r26, java.lang.String r27, com.jcraft.jsch.SftpProgressMonitor r28, int r29) throws com.jcraft.jsch.SftpException {
        /*
            r25 = this;
            r8 = r25
            r0 = r28
            r9 = r29
            java.io.InputStream r1 = r8.io_in     // Catch:{ Exception -> 0x01d2 }
            com.jcraft.jsch.Channel$MyPipedInputStream r1 = (com.jcraft.jsch.Channel.MyPipedInputStream) r1     // Catch:{ Exception -> 0x01d2 }
            r1.updateReadSide()     // Catch:{ Exception -> 0x01d2 }
            java.lang.String r1 = r25.remoteAbsolutePath(r26)     // Catch:{ Exception -> 0x01d2 }
            r2 = r27
            java.lang.String r2 = r8.localAbsolutePath(r2)     // Catch:{ Exception -> 0x01d2 }
            java.util.Vector r13 = r8.glob_remote(r1)     // Catch:{ Exception -> 0x01d2 }
            int r14 = r13.size()     // Catch:{ Exception -> 0x01d2 }
            if (r14 == 0) goto L_0x01c6
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x01d2 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x01d2 }
            boolean r15 = r1.isDirectory()     // Catch:{ Exception -> 0x01d2 }
            r7 = 1
            if (r15 == 0) goto L_0x004e
            java.lang.String r1 = file_separator     // Catch:{ Exception -> 0x01d0 }
            boolean r1 = r2.endsWith(r1)     // Catch:{ Exception -> 0x01d0 }
            if (r1 != 0) goto L_0x0046
            java.lang.StringBuffer r1 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x01d0 }
            r1.<init>()     // Catch:{ Exception -> 0x01d0 }
            r1.append(r2)     // Catch:{ Exception -> 0x01d0 }
            java.lang.String r2 = file_separator     // Catch:{ Exception -> 0x01d0 }
            r1.append(r2)     // Catch:{ Exception -> 0x01d0 }
            java.lang.String r2 = r1.toString()     // Catch:{ Exception -> 0x01d0 }
        L_0x0046:
            java.lang.StringBuffer r1 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x01d0 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x01d0 }
            r6 = r1
            r5 = r2
            goto L_0x0052
        L_0x004e:
            if (r14 > r7) goto L_0x01bc
            r5 = r2
            r6 = 0
        L_0x0052:
            r1 = 0
            r4 = 0
            r16 = 0
        L_0x0056:
            if (r4 >= r14) goto L_0x01bb
            java.lang.Object r2 = r13.elementAt(r4)     // Catch:{ Exception -> 0x01b9 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Exception -> 0x01b9 }
            r3 = r2
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ Exception -> 0x01b9 }
            com.jcraft.jsch.SftpATTRS r2 = r8._stat((java.lang.String) r3)     // Catch:{ Exception -> 0x01b9 }
            boolean r17 = r2.isDir()     // Catch:{ Exception -> 0x01b9 }
            if (r17 != 0) goto L_0x01a0
            if (r15 == 0) goto L_0x00f8
            r1 = 47
            int r1 = r3.lastIndexOf(r1)     // Catch:{ Exception -> 0x00f4 }
            r11 = -1
            if (r1 != r11) goto L_0x007a
            r6.append(r3)     // Catch:{ Exception -> 0x00f4 }
            goto L_0x0083
        L_0x007a:
            int r1 = r1 + 1
            java.lang.String r1 = r3.substring(r1)     // Catch:{ Exception -> 0x00f4 }
            r6.append(r1)     // Catch:{ Exception -> 0x00f4 }
        L_0x0083:
            java.lang.String r1 = r6.toString()     // Catch:{ Exception -> 0x00f4 }
            java.lang.String r12 = ".."
            int r12 = r1.indexOf(r12)     // Catch:{ Exception -> 0x01b9 }
            if (r12 == r11) goto L_0x00e6
            java.io.File r11 = new java.io.File     // Catch:{ Exception -> 0x01b9 }
            r11.<init>(r5)     // Catch:{ Exception -> 0x01b9 }
            java.lang.String r11 = r11.getCanonicalPath()     // Catch:{ Exception -> 0x01b9 }
            java.io.File r12 = new java.io.File     // Catch:{ Exception -> 0x01b9 }
            r12.<init>(r1)     // Catch:{ Exception -> 0x01b9 }
            java.lang.String r12 = r12.getCanonicalPath()     // Catch:{ Exception -> 0x01b9 }
            int r10 = r12.length()     // Catch:{ Exception -> 0x01b9 }
            int r7 = r11.length()     // Catch:{ Exception -> 0x01b9 }
            if (r10 <= r7) goto L_0x00ce
            int r7 = r11.length()     // Catch:{ Exception -> 0x01b9 }
            r10 = 1
            int r7 = r7 + r10
            r10 = 0
            java.lang.String r7 = r12.substring(r10, r7)     // Catch:{ Exception -> 0x01b9 }
            java.lang.StringBuffer r12 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x01b9 }
            r12.<init>()     // Catch:{ Exception -> 0x01b9 }
            r12.append(r11)     // Catch:{ Exception -> 0x01b9 }
            java.lang.String r11 = file_separator     // Catch:{ Exception -> 0x01b9 }
            r12.append(r11)     // Catch:{ Exception -> 0x01b9 }
            java.lang.String r11 = r12.toString()     // Catch:{ Exception -> 0x01b9 }
            boolean r7 = r7.equals(r11)     // Catch:{ Exception -> 0x01b9 }
            if (r7 == 0) goto L_0x00ce
            goto L_0x00e7
        L_0x00ce:
            com.jcraft.jsch.SftpException r0 = new com.jcraft.jsch.SftpException     // Catch:{ Exception -> 0x01b9 }
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x01b9 }
            r2.<init>()     // Catch:{ Exception -> 0x01b9 }
            java.lang.String r4 = "writing to an unexpected file "
            r2.append(r4)     // Catch:{ Exception -> 0x01b9 }
            r2.append(r3)     // Catch:{ Exception -> 0x01b9 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x01b9 }
            r3 = 4
            r0.<init>(r3, r2)     // Catch:{ Exception -> 0x01b9 }
            throw r0     // Catch:{ Exception -> 0x01b9 }
        L_0x00e6:
            r10 = 0
        L_0x00e7:
            int r7 = r5.length()     // Catch:{ Exception -> 0x01b9 }
            int r11 = r1.length()     // Catch:{ Exception -> 0x01b9 }
            r6.delete(r7, r11)     // Catch:{ Exception -> 0x01b9 }
            r11 = r1
            goto L_0x00fa
        L_0x00f4:
            r0 = move-exception
            r1 = 0
            goto L_0x01d7
        L_0x00f8:
            r10 = 0
            r11 = r5
        L_0x00fa:
            java.io.File r7 = new java.io.File     // Catch:{ Exception -> 0x019d }
            r7.<init>(r11)     // Catch:{ Exception -> 0x019d }
            r1 = 1
            if (r9 != r1) goto L_0x012b
            long r18 = r2.getSize()     // Catch:{ Exception -> 0x019d }
            long r20 = r7.length()     // Catch:{ Exception -> 0x019d }
            int r1 = (r20 > r18 ? 1 : (r20 == r18 ? 0 : -1))
            if (r1 > 0) goto L_0x0113
            int r1 = (r20 > r18 ? 1 : (r20 == r18 ? 0 : -1))
            if (r1 != 0) goto L_0x012b
            return
        L_0x0113:
            com.jcraft.jsch.SftpException r0 = new com.jcraft.jsch.SftpException     // Catch:{ Exception -> 0x019d }
            java.lang.StringBuffer r1 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x019d }
            r1.<init>()     // Catch:{ Exception -> 0x019d }
            java.lang.String r2 = "failed to resume for "
            r1.append(r2)     // Catch:{ Exception -> 0x019d }
            r1.append(r11)     // Catch:{ Exception -> 0x019d }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x019d }
            r2 = 4
            r0.<init>(r2, r1)     // Catch:{ Exception -> 0x019d }
            throw r0     // Catch:{ Exception -> 0x019d }
        L_0x012b:
            if (r0 == 0) goto L_0x014d
            r12 = 1
            long r18 = r2.getSize()     // Catch:{ Exception -> 0x019d }
            r1 = r28
            r2 = r12
            r12 = r3
            r20 = r4
            r4 = r11
            r21 = r5
            r22 = r6
            r5 = r18
            r1.init(r2, r3, r4, r5)     // Catch:{ Exception -> 0x019d }
            r1 = 1
            if (r9 != r1) goto L_0x0154
            long r1 = r7.length()     // Catch:{ Exception -> 0x019d }
            r0.count(r1)     // Catch:{ Exception -> 0x019d }
            goto L_0x0154
        L_0x014d:
            r12 = r3
            r20 = r4
            r21 = r5
            r22 = r6
        L_0x0154:
            boolean r16 = r7.exists()     // Catch:{ Exception -> 0x019d }
            if (r9 != 0) goto L_0x0163
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ all -> 0x0194 }
            r1.<init>(r11)     // Catch:{ all -> 0x0194 }
            r18 = r1
            r6 = 1
            goto L_0x016b
        L_0x0163:
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ all -> 0x0194 }
            r6 = 1
            r1.<init>(r11, r6)     // Catch:{ all -> 0x0194 }
            r18 = r1
        L_0x016b:
            java.io.File r1 = new java.io.File     // Catch:{ all -> 0x0190 }
            r1.<init>(r11)     // Catch:{ all -> 0x0190 }
            long r23 = r1.length()     // Catch:{ all -> 0x0190 }
            r1 = r25
            r2 = r12
            r3 = r18
            r4 = r28
            r5 = r29
            r12 = 1
            r6 = r23
            r1._get(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x0190 }
            r18.close()     // Catch:{ Exception -> 0x019d }
            int r4 = r20 + 1
            r1 = r11
            r5 = r21
            r6 = r22
            r7 = 1
            goto L_0x0056
        L_0x0190:
            r0 = move-exception
            r17 = r18
            goto L_0x0197
        L_0x0194:
            r0 = move-exception
            r17 = 0
        L_0x0197:
            if (r17 == 0) goto L_0x019c
            r17.close()     // Catch:{ Exception -> 0x019d }
        L_0x019c:
            throw r0     // Catch:{ Exception -> 0x019d }
        L_0x019d:
            r0 = move-exception
            r1 = r11
            goto L_0x01d7
        L_0x01a0:
            r12 = r3
            com.jcraft.jsch.SftpException r0 = new com.jcraft.jsch.SftpException     // Catch:{ Exception -> 0x01b9 }
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x01b9 }
            r2.<init>()     // Catch:{ Exception -> 0x01b9 }
            java.lang.String r3 = "not supported to get directory "
            r2.append(r3)     // Catch:{ Exception -> 0x01b9 }
            r2.append(r12)     // Catch:{ Exception -> 0x01b9 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x01b9 }
            r3 = 4
            r0.<init>(r3, r2)     // Catch:{ Exception -> 0x01b9 }
            throw r0     // Catch:{ Exception -> 0x01b9 }
        L_0x01b9:
            r0 = move-exception
            goto L_0x01d7
        L_0x01bb:
            return
        L_0x01bc:
            r10 = 0
            com.jcraft.jsch.SftpException r0 = new com.jcraft.jsch.SftpException     // Catch:{ Exception -> 0x01d0 }
            java.lang.String r1 = "Copying multiple files, but destination is missing or a file."
            r2 = 4
            r0.<init>(r2, r1)     // Catch:{ Exception -> 0x01d0 }
            throw r0     // Catch:{ Exception -> 0x01d0 }
        L_0x01c6:
            r10 = 0
            com.jcraft.jsch.SftpException r0 = new com.jcraft.jsch.SftpException     // Catch:{ Exception -> 0x01d0 }
            r1 = 2
            java.lang.String r2 = "No such file"
            r0.<init>(r1, r2)     // Catch:{ Exception -> 0x01d0 }
            throw r0     // Catch:{ Exception -> 0x01d0 }
        L_0x01d0:
            r0 = move-exception
            goto L_0x01d4
        L_0x01d2:
            r0 = move-exception
            r10 = 0
        L_0x01d4:
            r1 = 0
            r16 = 0
        L_0x01d7:
            if (r16 != 0) goto L_0x01f3
            if (r1 == 0) goto L_0x01f3
            java.io.File r2 = new java.io.File
            r2.<init>(r1)
            boolean r1 = r2.exists()
            if (r1 == 0) goto L_0x01f3
            long r3 = r2.length()
            r5 = 0
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 != 0) goto L_0x01f3
            r2.delete()
        L_0x01f3:
            boolean r1 = r0 instanceof com.jcraft.jsch.SftpException
            if (r1 != 0) goto L_0x020d
            boolean r1 = r0 instanceof java.lang.Throwable
            if (r1 == 0) goto L_0x0204
            com.jcraft.jsch.SftpException r1 = new com.jcraft.jsch.SftpException
            java.lang.String r2 = ""
            r3 = 4
            r1.<init>(r3, r2, r0)
            throw r1
        L_0x0204:
            r3 = 4
            com.jcraft.jsch.SftpException r0 = new com.jcraft.jsch.SftpException
            java.lang.String r1 = ""
            r0.<init>(r3, r1)
            throw r0
        L_0x020d:
            com.jcraft.jsch.SftpException r0 = (com.jcraft.jsch.SftpException) r0
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jcraft.jsch.ChannelSftp.get(java.lang.String, java.lang.String, com.jcraft.jsch.SftpProgressMonitor, int):void");
    }

    public void get(String str, OutputStream outputStream) throws SftpException {
        get(str, outputStream, (SftpProgressMonitor) null, 0, 0);
    }

    public void get(String str, OutputStream outputStream, SftpProgressMonitor sftpProgressMonitor) throws SftpException {
        get(str, outputStream, sftpProgressMonitor, 0, 0);
    }

    public void get(String str, OutputStream outputStream, SftpProgressMonitor sftpProgressMonitor, int i, long j) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            String isUnique = isUnique(remoteAbsolutePath(str));
            if (sftpProgressMonitor != null) {
                sftpProgressMonitor.init(1, isUnique, "??", _stat(isUnique).getSize());
                if (i == 1) {
                    sftpProgressMonitor.count(j);
                }
            }
            _get(isUnique, outputStream, sftpProgressMonitor, i, j);
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            } else if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            } else {
                throw new SftpException(4, "");
            }
        }
    }

    private void _get(String str, OutputStream outputStream, SftpProgressMonitor sftpProgressMonitor, int i, long j) throws SftpException {
        long j2;
        int i2;
        Header header;
        int i3;
        Header header2;
        SftpProgressMonitor sftpProgressMonitor2 = sftpProgressMonitor;
        try {
            sendOPENR(Util.str2byte(str, this.fEncoding));
            Header header3 = header(this.buf, new Header());
            int i4 = header3.length;
            int i5 = header3.type;
            fill(this.buf, i4);
            int i6 = 101;
            if (i5 != 101) {
                if (i5 != 102) {
                    throw new SftpException(4, "");
                }
            }
            if (i5 == 101) {
                throwStatusError(this.buf, this.buf.getInt());
            }
            byte[] string = this.buf.getString();
            long j3 = 0;
            int i7 = 1;
            if (i == 1) {
                j3 = j + 0;
            }
            this.rq.init();
            int length = this.buf.buffer.length - 13;
            if (this.server_version == 0) {
                j2 = j3;
                i2 = 1024;
            } else {
                j2 = j3;
                i2 = length;
            }
            int i8 = 1;
            loop0:
            while (true) {
                if (this.rq.count() < i8) {
                    int i9 = i2;
                    sendREAD(string, j2, i2, this.rq);
                    j2 += (long) i9;
                    i3 = i9;
                } else {
                    int i10 = i2;
                    header = header(this.buf, header3);
                    int i11 = header.length;
                    int i12 = header.type;
                    try {
                        RequestQueue.Request request = this.rq.get(header.rid);
                        if (i12 == i6) {
                            fill(this.buf, i11);
                            int i13 = this.buf.getInt();
                            if (i13 == i7) {
                                OutputStream outputStream2 = outputStream;
                                break;
                            }
                            throwStatusError(this.buf, i13);
                        }
                        if (i12 != 103) {
                            OutputStream outputStream3 = outputStream;
                            break;
                        }
                        this.buf.rewind();
                        fill(this.buf.buffer, 0, 4);
                        int i14 = this.buf.getInt();
                        int i15 = (i11 - 4) - i14;
                        int i16 = i14;
                        while (i16 > 0) {
                            int read = this.io_in.read(this.buf.buffer, 0, i16 > this.buf.buffer.length ? this.buf.buffer.length : i16);
                            if (read < 0) {
                                OutputStream outputStream4 = outputStream;
                                break loop0;
                            }
                            outputStream.write(this.buf.buffer, 0, read);
                            int i17 = i10;
                            long j4 = (long) read;
                            i16 -= read;
                            if (sftpProgressMonitor2 == null || sftpProgressMonitor2.count(j4)) {
                                i10 = i17;
                            } else {
                                skip((long) i16);
                                if (i15 > 0) {
                                    skip((long) i15);
                                }
                            }
                        }
                        OutputStream outputStream5 = outputStream;
                        int i18 = i10;
                        if (i15 > 0) {
                            skip((long) i15);
                        }
                        long j5 = (long) i14;
                        if (j5 < request.length) {
                            this.rq.cancel(header, this.buf);
                            RequestQueue.Request request2 = request;
                            header2 = header;
                            sendREAD(string, request.offset + j5, (int) (request.length - j5), this.rq);
                            j2 = request2.offset + request2.length;
                        } else {
                            header2 = header;
                        }
                        if (i8 < this.rq.size()) {
                            i8++;
                        }
                        i3 = i18;
                        header3 = header2;
                        i6 = 101;
                        i7 = 1;
                    } catch (RequestQueue.OutOfOrderException e) {
                        OutputStream outputStream6 = outputStream;
                        Header header4 = header;
                        int i19 = i10;
                        j2 = e.offset;
                        skip((long) header4.length);
                        this.rq.cancel(header4, this.buf);
                        i3 = i19;
                        header3 = header4;
                        i6 = 101;
                        i7 = 1;
                    }
                }
            }
            outputStream.flush();
            if (sftpProgressMonitor2 != null) {
                sftpProgressMonitor.end();
            }
            this.rq.cancel(header, this.buf);
            _sendCLOSE(string, header);
        } catch (Exception e2) {
            if (e2 instanceof SftpException) {
                throw ((SftpException) e2);
            } else if (e2 instanceof Throwable) {
                throw new SftpException(4, "", e2);
            } else {
                throw new SftpException(4, "");
            }
        }
    }

    private class RequestQueue {
        int count;
        int head;
        Request[] rrq = null;

        class OutOfOrderException extends Exception {
            long offset;

            OutOfOrderException(long j) {
                this.offset = j;
            }
        }

        class Request {
            int id;
            long length;
            long offset;

            Request() {
            }
        }

        RequestQueue(int i) {
            this.rrq = new Request[i];
            int i2 = 0;
            while (true) {
                Request[] requestArr = this.rrq;
                if (i2 < requestArr.length) {
                    requestArr[i2] = new Request();
                    i2++;
                } else {
                    init();
                    return;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void init() {
            this.count = 0;
            this.head = 0;
        }

        /* access modifiers changed from: package-private */
        public void add(int i, long j, int i2) {
            if (this.count == 0) {
                this.head = 0;
            }
            int i3 = this.head + this.count;
            Request[] requestArr = this.rrq;
            if (i3 >= requestArr.length) {
                i3 -= requestArr.length;
            }
            Request[] requestArr2 = this.rrq;
            requestArr2[i3].id = i;
            requestArr2[i3].offset = j;
            requestArr2[i3].length = (long) i2;
            this.count++;
        }

        /* access modifiers changed from: package-private */
        public Request get(int i) throws OutOfOrderException, SftpException {
            boolean z = true;
            this.count--;
            int i2 = this.head;
            this.head = i2 + 1;
            if (this.head == this.rrq.length) {
                this.head = 0;
            }
            if (this.rrq[i2].id != i) {
                long offset = getOffset();
                int i3 = 0;
                while (true) {
                    Request[] requestArr = this.rrq;
                    if (i3 >= requestArr.length) {
                        z = false;
                        break;
                    } else if (requestArr[i3].id == i) {
                        this.rrq[i3].id = 0;
                        break;
                    } else {
                        i3++;
                    }
                }
                if (z) {
                    throw new OutOfOrderException(offset);
                }
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("RequestQueue: unknown request id ");
                stringBuffer.append(i);
                throw new SftpException(4, stringBuffer.toString());
            }
            Request[] requestArr2 = this.rrq;
            requestArr2[i2].id = 0;
            return requestArr2[i2];
        }

        /* access modifiers changed from: package-private */
        public int count() {
            return this.count;
        }

        /* access modifiers changed from: package-private */
        public int size() {
            return this.rrq.length;
        }

        /* access modifiers changed from: package-private */
        public void cancel(Header header, Buffer buffer) throws IOException {
            int i = this.count;
            Header header2 = header;
            for (int i2 = 0; i2 < i; i2++) {
                header2 = ChannelSftp.this.header(buffer, header2);
                int i3 = header2.length;
                int i4 = 0;
                while (true) {
                    Request[] requestArr = this.rrq;
                    if (i4 >= requestArr.length) {
                        break;
                    } else if (requestArr[i4].id == header2.rid) {
                        this.rrq[i4].id = 0;
                        break;
                    } else {
                        i4++;
                    }
                }
                ChannelSftp.this.skip((long) i3);
            }
            init();
        }

        /* access modifiers changed from: package-private */
        public long getOffset() {
            long j = Long.MAX_VALUE;
            int i = 0;
            while (true) {
                Request[] requestArr = this.rrq;
                if (i >= requestArr.length) {
                    return j;
                }
                if (requestArr[i].id != 0 && j > this.rrq[i].offset) {
                    j = this.rrq[i].offset;
                }
                i++;
            }
        }
    }

    public InputStream get(String str) throws SftpException {
        return get(str, (SftpProgressMonitor) null, 0);
    }

    public InputStream get(String str, SftpProgressMonitor sftpProgressMonitor) throws SftpException {
        return get(str, sftpProgressMonitor, 0);
    }

    public InputStream get(String str, int i) throws SftpException {
        return get(str, (SftpProgressMonitor) null, 0);
    }

    public InputStream get(String str, SftpProgressMonitor sftpProgressMonitor, int i) throws SftpException {
        return get(str, sftpProgressMonitor, 0);
    }

    public InputStream get(String str, SftpProgressMonitor sftpProgressMonitor, long j) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            String isUnique = isUnique(remoteAbsolutePath(str));
            byte[] str2byte = Util.str2byte(isUnique, this.fEncoding);
            SftpATTRS _stat = _stat(str2byte);
            if (sftpProgressMonitor != null) {
                sftpProgressMonitor.init(1, isUnique, "??", _stat.getSize());
            }
            sendOPENR(str2byte);
            Header header = header(this.buf, new Header());
            int i = header.length;
            int i2 = header.type;
            fill(this.buf, i);
            if (i2 != 101) {
                if (i2 != 102) {
                    throw new SftpException(4, "");
                }
            }
            if (i2 == 101) {
                throwStatusError(this.buf, this.buf.getInt());
            }
            final byte[] string = this.buf.getString();
            this.rq.init();
            final long j2 = j;
            final SftpProgressMonitor sftpProgressMonitor2 = sftpProgressMonitor;
            return new InputStream() {
                byte[] _data = new byte[1];
                boolean closed = false;
                Header header = new Header();
                long offset = j2;
                int request_max = 1;
                long request_offset = this.offset;
                byte[] rest_byte = new byte[1024];
                int rest_length = 0;

                public int read() throws IOException {
                    if (!this.closed && read(this._data, 0, 1) != -1) {
                        return this._data[0] & 255;
                    }
                    return -1;
                }

                public int read(byte[] bArr) throws IOException {
                    if (this.closed) {
                        return -1;
                    }
                    return read(bArr, 0, bArr.length);
                }

                public int read(byte[] bArr, int i, int i2) throws IOException {
                    byte[] bArr2 = bArr;
                    int i3 = i;
                    int i4 = i2;
                    if (this.closed) {
                        return -1;
                    }
                    if (bArr2 == null) {
                        throw new NullPointerException();
                    } else if (i3 < 0 || i4 < 0 || i3 + i4 > bArr2.length) {
                        throw new IndexOutOfBoundsException();
                    } else {
                        int i5 = 0;
                        if (i4 == 0) {
                            return 0;
                        }
                        int i6 = this.rest_length;
                        if (i6 > 0) {
                            if (i6 <= i4) {
                                i4 = i6;
                            }
                            System.arraycopy(this.rest_byte, 0, bArr2, i3, i4);
                            int i7 = this.rest_length;
                            if (i4 != i7) {
                                byte[] bArr3 = this.rest_byte;
                                System.arraycopy(bArr3, i4, bArr3, 0, i7 - i4);
                            }
                            SftpProgressMonitor sftpProgressMonitor = sftpProgressMonitor2;
                            if (sftpProgressMonitor == null || sftpProgressMonitor.count((long) i4)) {
                                this.rest_length -= i4;
                                return i4;
                            }
                            close();
                            return -1;
                        }
                        if (ChannelSftp.this.buf.buffer.length - 13 < i4) {
                            i4 = ChannelSftp.this.buf.buffer.length - 13;
                        }
                        if (ChannelSftp.this.server_version == 0 && i4 > 1024) {
                            i4 = 1024;
                        }
                        ChannelSftp.this.rq.count();
                        int length = ChannelSftp.this.buf.buffer.length - 13;
                        if (ChannelSftp.this.server_version == 0) {
                            length = 1024;
                        }
                        while (ChannelSftp.this.rq.count() < this.request_max) {
                            try {
                                ChannelSftp.this.sendREAD(string, this.request_offset, length, ChannelSftp.this.rq);
                                this.request_offset += (long) length;
                            } catch (Exception unused) {
                                throw new IOException("error");
                            }
                        }
                        ChannelSftp channelSftp = ChannelSftp.this;
                        this.header = channelSftp.header(channelSftp.buf, this.header);
                        this.rest_length = this.header.length;
                        int i8 = this.header.type;
                        int i9 = this.header.rid;
                        try {
                            RequestQueue.Request request = ChannelSftp.this.rq.get(this.header.rid);
                            if (i8 != 101 && i8 != 103) {
                                throw new IOException("error");
                            } else if (i8 == 101) {
                                ChannelSftp channelSftp2 = ChannelSftp.this;
                                channelSftp2.fill(channelSftp2.buf, this.rest_length);
                                int i10 = ChannelSftp.this.buf.getInt();
                                this.rest_length = 0;
                                if (i10 == 1) {
                                    close();
                                    return -1;
                                }
                                throw new IOException("error");
                            } else {
                                ChannelSftp.this.buf.rewind();
                                ChannelSftp channelSftp3 = ChannelSftp.this;
                                int unused2 = channelSftp3.fill(channelSftp3.buf.buffer, 0, 4);
                                int i11 = ChannelSftp.this.buf.getInt();
                                this.rest_length -= 4;
                                int i12 = this.rest_length - i11;
                                long j = (long) i11;
                                this.offset += j;
                                if (i11 <= 0) {
                                    return 0;
                                }
                                if (i11 <= i4) {
                                    i4 = i11;
                                }
                                int read = ChannelSftp.this.io_in.read(bArr2, i3, i4);
                                if (read < 0) {
                                    return -1;
                                }
                                int i13 = i11 - read;
                                this.rest_length = i13;
                                if (i13 > 0) {
                                    if (this.rest_byte.length < i13) {
                                        this.rest_byte = new byte[i13];
                                    }
                                    while (i13 > 0) {
                                        int read2 = ChannelSftp.this.io_in.read(this.rest_byte, i5, i13);
                                        if (read2 <= 0) {
                                            break;
                                        }
                                        i5 += read2;
                                        i13 -= read2;
                                    }
                                }
                                if (i12 > 0) {
                                    ChannelSftp.this.io_in.skip((long) i12);
                                }
                                if (j < request.length) {
                                    ChannelSftp.this.rq.cancel(this.header, ChannelSftp.this.buf);
                                    try {
                                        ChannelSftp.this.sendREAD(string, request.offset + j, (int) (request.length - j), ChannelSftp.this.rq);
                                        this.request_offset = request.offset + request.length;
                                    } catch (Exception unused3) {
                                        throw new IOException("error");
                                    }
                                }
                                if (this.request_max < ChannelSftp.this.rq.size()) {
                                    this.request_max++;
                                }
                                SftpProgressMonitor sftpProgressMonitor2 = sftpProgressMonitor2;
                                if (sftpProgressMonitor2 == null || sftpProgressMonitor2.count((long) read)) {
                                    return read;
                                }
                                close();
                                return -1;
                            }
                        } catch (RequestQueue.OutOfOrderException e) {
                            this.request_offset = e.offset;
                            skip((long) this.header.length);
                            ChannelSftp.this.rq.cancel(this.header, ChannelSftp.this.buf);
                            return 0;
                        } catch (SftpException e2) {
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append("error: ");
                            stringBuffer.append(e2.toString());
                            throw new IOException(stringBuffer.toString());
                        }
                    }
                }

                public void close() throws IOException {
                    if (!this.closed) {
                        this.closed = true;
                        SftpProgressMonitor sftpProgressMonitor = sftpProgressMonitor2;
                        if (sftpProgressMonitor != null) {
                            sftpProgressMonitor.end();
                        }
                        ChannelSftp.this.rq.cancel(this.header, ChannelSftp.this.buf);
                        try {
                            boolean unused = ChannelSftp.this._sendCLOSE(string, this.header);
                        } catch (Exception unused2) {
                            throw new IOException("error");
                        }
                    }
                }
            };
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            } else if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            } else {
                throw new SftpException(4, "");
            }
        }
    }

    public Vector ls(String str) throws SftpException {
        final Vector vector = new Vector();
        ls(str, new LsEntrySelector() {
            public int select(LsEntry lsEntry) {
                vector.addElement(lsEntry);
                return 0;
            }
        });
        return vector;
    }

    public void ls(String str, LsEntrySelector lsEntrySelector) throws SftpException {
        byte[] bArr;
        String str2;
        String str3;
        byte[] bArr2;
        String str4;
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            String remoteAbsolutePath = remoteAbsolutePath(str);
            new Vector();
            int lastIndexOf = remoteAbsolutePath.lastIndexOf(47);
            boolean z = true;
            String substring = remoteAbsolutePath.substring(0, lastIndexOf == 0 ? 1 : lastIndexOf);
            String substring2 = remoteAbsolutePath.substring(lastIndexOf + 1);
            String unquote = Util.unquote(substring);
            byte[][] bArr3 = new byte[1][];
            boolean isPattern = isPattern(substring2, bArr3);
            if (isPattern) {
                bArr = bArr3[0];
            } else {
                String unquote2 = Util.unquote(remoteAbsolutePath);
                if (_stat(unquote2).isDir()) {
                    unquote = unquote2;
                    bArr = null;
                } else if (this.fEncoding_is_utf8) {
                    bArr = Util.unquote(bArr3[0]);
                } else {
                    bArr = Util.str2byte(Util.unquote(substring2), this.fEncoding);
                }
            }
            sendOPENDIR(Util.str2byte(unquote, this.fEncoding));
            Header header = header(this.buf, new Header());
            int i = header.length;
            int i2 = header.type;
            fill(this.buf, i);
            int i3 = 101;
            if (i2 != 101) {
                if (i2 != 102) {
                    throw new SftpException(4, "");
                }
            }
            if (i2 == 101) {
                throwStatusError(this.buf, this.buf.getInt());
            }
            byte[] string = this.buf.getString();
            Header header2 = header;
            int i4 = 0;
            while (true) {
                if (i4 != 0) {
                    break;
                }
                sendREADDIR(string);
                header2 = header(this.buf, header2);
                int i5 = header2.length;
                int i6 = header2.type;
                if (i6 != i3) {
                    if (i6 != 104) {
                        throw new SftpException(4, "");
                    }
                }
                if (i6 == i3) {
                    fill(this.buf, i5);
                    int i7 = this.buf.getInt();
                    if (i7 == z) {
                        break;
                    }
                    throwStatusError(this.buf, i7);
                }
                this.buf.rewind();
                fill(this.buf.buffer, 0, 4);
                int i8 = i5 - 4;
                int i9 = this.buf.getInt();
                this.buf.reset();
                while (i9 > 0) {
                    if (i8 > 0) {
                        this.buf.shift();
                        int fill = fill(this.buf.buffer, this.buf.index, this.buf.buffer.length > this.buf.index + i8 ? i8 : this.buf.buffer.length - this.buf.index);
                        this.buf.index += fill;
                        i8 -= fill;
                    }
                    byte[] string2 = this.buf.getString();
                    byte[] string3 = this.server_version <= 3 ? this.buf.getString() : null;
                    SftpATTRS attr = SftpATTRS.getATTR(this.buf);
                    if (i4 == z) {
                        i9--;
                    } else {
                        if (bArr == null) {
                            str2 = null;
                        } else if (!isPattern) {
                            str2 = null;
                            z = Util.array_equals(bArr, string2);
                        } else {
                            if (!this.fEncoding_is_utf8) {
                                str4 = Util.byte2str(string2, this.fEncoding);
                                bArr2 = Util.str2byte(str4, UTF8);
                            } else {
                                str4 = null;
                                bArr2 = string2;
                            }
                            boolean glob = Util.glob(bArr, bArr2);
                            str2 = str4;
                            z = glob;
                        }
                        if (z) {
                            if (str2 == null) {
                                str2 = Util.byte2str(string2, this.fEncoding);
                            }
                            if (string3 == null) {
                                StringBuffer stringBuffer = new StringBuffer();
                                stringBuffer.append(attr.toString());
                                stringBuffer.append(" ");
                                stringBuffer.append(str2);
                                str3 = stringBuffer.toString();
                            } else {
                                str3 = Util.byte2str(string3, this.fEncoding);
                            }
                            i4 = lsEntrySelector.select(new LsEntry(str2, str3, attr));
                        } else {
                            LsEntrySelector lsEntrySelector2 = lsEntrySelector;
                        }
                        i9--;
                        z = true;
                    }
                }
                LsEntrySelector lsEntrySelector3 = lsEntrySelector;
                i3 = 101;
            }
            _sendCLOSE(string, header2);
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            } else if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            } else {
                throw new SftpException(4, "");
            }
        }
    }

    public String readlink(String str) throws SftpException {
        try {
            if (this.server_version >= 3) {
                ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
                sendREADLINK(Util.str2byte(isUnique(remoteAbsolutePath(str)), this.fEncoding));
                Header header = header(this.buf, new Header());
                int i = header.length;
                int i2 = header.type;
                fill(this.buf, i);
                if (i2 != 101) {
                    if (i2 != 104) {
                        throw new SftpException(4, "");
                    }
                }
                byte[] bArr = null;
                if (i2 == 104) {
                    int i3 = this.buf.getInt();
                    for (int i4 = 0; i4 < i3; i4++) {
                        bArr = this.buf.getString();
                        if (this.server_version <= 3) {
                            this.buf.getString();
                        }
                        SftpATTRS.getATTR(this.buf);
                    }
                    return Util.byte2str(bArr, this.fEncoding);
                }
                throwStatusError(this.buf, this.buf.getInt());
                return null;
            }
            throw new SftpException(8, "The remote sshd is too old to support symlink operation.");
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            } else if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            } else {
                throw new SftpException(4, "");
            }
        }
    }

    public void symlink(String str, String str2) throws SftpException {
        if (this.server_version >= 3) {
            try {
                ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
                String remoteAbsolutePath = remoteAbsolutePath(str);
                String remoteAbsolutePath2 = remoteAbsolutePath(str2);
                String isUnique = isUnique(remoteAbsolutePath);
                int i = 0;
                if (str.charAt(0) != '/') {
                    String cwd2 = getCwd();
                    int length = cwd2.length();
                    if (!cwd2.endsWith("/")) {
                        i = 1;
                    }
                    isUnique = isUnique.substring(length + i);
                }
                if (!isPattern(remoteAbsolutePath2)) {
                    sendSYMLINK(Util.str2byte(isUnique, this.fEncoding), Util.str2byte(Util.unquote(remoteAbsolutePath2), this.fEncoding));
                    Header header = header(this.buf, new Header());
                    int i2 = header.length;
                    int i3 = header.type;
                    fill(this.buf, i2);
                    if (i3 == 101) {
                        int i4 = this.buf.getInt();
                        if (i4 != 0) {
                            throwStatusError(this.buf, i4);
                            return;
                        }
                        return;
                    }
                    throw new SftpException(4, "");
                }
                throw new SftpException(4, remoteAbsolutePath2);
            } catch (Exception e) {
                if (e instanceof SftpException) {
                    throw ((SftpException) e);
                } else if (e instanceof Throwable) {
                    throw new SftpException(4, "", e);
                } else {
                    throw new SftpException(4, "");
                }
            }
        } else {
            throw new SftpException(8, "The remote sshd is too old to support symlink operation.");
        }
    }

    public void hardlink(String str, String str2) throws SftpException {
        if (this.extension_hardlink) {
            try {
                ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
                String remoteAbsolutePath = remoteAbsolutePath(str);
                String remoteAbsolutePath2 = remoteAbsolutePath(str2);
                String isUnique = isUnique(remoteAbsolutePath);
                int i = 0;
                if (str.charAt(0) != '/') {
                    String cwd2 = getCwd();
                    int length = cwd2.length();
                    if (!cwd2.endsWith("/")) {
                        i = 1;
                    }
                    isUnique = isUnique.substring(length + i);
                }
                if (!isPattern(remoteAbsolutePath2)) {
                    sendHARDLINK(Util.str2byte(isUnique, this.fEncoding), Util.str2byte(Util.unquote(remoteAbsolutePath2), this.fEncoding));
                    Header header = header(this.buf, new Header());
                    int i2 = header.length;
                    int i3 = header.type;
                    fill(this.buf, i2);
                    if (i3 == 101) {
                        int i4 = this.buf.getInt();
                        if (i4 != 0) {
                            throwStatusError(this.buf, i4);
                            return;
                        }
                        return;
                    }
                    throw new SftpException(4, "");
                }
                throw new SftpException(4, remoteAbsolutePath2);
            } catch (Exception e) {
                if (e instanceof SftpException) {
                    throw ((SftpException) e);
                } else if (e instanceof Throwable) {
                    throw new SftpException(4, "", e);
                } else {
                    throw new SftpException(4, "");
                }
            }
        } else {
            throw new SftpException(8, "hardlink@openssh.com is not supported");
        }
    }

    public void rename(String str, String str2) throws SftpException {
        String str3;
        if (this.server_version >= 2) {
            try {
                ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
                String remoteAbsolutePath = remoteAbsolutePath(str);
                String remoteAbsolutePath2 = remoteAbsolutePath(str2);
                String isUnique = isUnique(remoteAbsolutePath);
                Vector glob_remote = glob_remote(remoteAbsolutePath2);
                int size = glob_remote.size();
                if (size < 2) {
                    if (size == 1) {
                        str3 = (String) glob_remote.elementAt(0);
                    } else if (!isPattern(remoteAbsolutePath2)) {
                        str3 = Util.unquote(remoteAbsolutePath2);
                    } else {
                        throw new SftpException(4, remoteAbsolutePath2);
                    }
                    sendRENAME(Util.str2byte(isUnique, this.fEncoding), Util.str2byte(str3, this.fEncoding));
                    Header header = header(this.buf, new Header());
                    int i = header.length;
                    int i2 = header.type;
                    fill(this.buf, i);
                    if (i2 == 101) {
                        int i3 = this.buf.getInt();
                        if (i3 != 0) {
                            throwStatusError(this.buf, i3);
                            return;
                        }
                        return;
                    }
                    throw new SftpException(4, "");
                }
                throw new SftpException(4, glob_remote.toString());
            } catch (Exception e) {
                if (e instanceof SftpException) {
                    throw ((SftpException) e);
                } else if (e instanceof Throwable) {
                    throw new SftpException(4, "", e);
                } else {
                    throw new SftpException(4, "");
                }
            }
        } else {
            throw new SftpException(8, "The remote sshd is too old to support rename operation.");
        }
    }

    public void rm(String str) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            Vector glob_remote = glob_remote(remoteAbsolutePath(str));
            int size = glob_remote.size();
            Header header = new Header();
            int i = 0;
            while (i < size) {
                sendREMOVE(Util.str2byte((String) glob_remote.elementAt(i), this.fEncoding));
                header = header(this.buf, header);
                int i2 = header.length;
                int i3 = header.type;
                fill(this.buf, i2);
                if (i3 == 101) {
                    int i4 = this.buf.getInt();
                    if (i4 != 0) {
                        throwStatusError(this.buf, i4);
                    }
                    i++;
                } else {
                    throw new SftpException(4, "");
                }
            }
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            } else if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            } else {
                throw new SftpException(4, "");
            }
        }
    }

    private boolean isRemoteDir(String str) {
        try {
            sendSTAT(Util.str2byte(str, this.fEncoding));
            Header header = header(this.buf, new Header());
            int i = header.length;
            int i2 = header.type;
            fill(this.buf, i);
            if (i2 != 105) {
                return false;
            }
            return SftpATTRS.getATTR(this.buf).isDir();
        } catch (Exception unused) {
            return false;
        }
    }

    public void chgrp(int i, String str) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            Vector glob_remote = glob_remote(remoteAbsolutePath(str));
            int size = glob_remote.size();
            for (int i2 = 0; i2 < size; i2++) {
                String str2 = (String) glob_remote.elementAt(i2);
                SftpATTRS _stat = _stat(str2);
                _stat.setFLAGS(0);
                _stat.setUIDGID(_stat.uid, i);
                _setStat(str2, _stat);
            }
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            } else if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            } else {
                throw new SftpException(4, "");
            }
        }
    }

    public void chown(int i, String str) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            Vector glob_remote = glob_remote(remoteAbsolutePath(str));
            int size = glob_remote.size();
            for (int i2 = 0; i2 < size; i2++) {
                String str2 = (String) glob_remote.elementAt(i2);
                SftpATTRS _stat = _stat(str2);
                _stat.setFLAGS(0);
                _stat.setUIDGID(i, _stat.gid);
                _setStat(str2, _stat);
            }
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            } else if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            } else {
                throw new SftpException(4, "");
            }
        }
    }

    public void chmod(int i, String str) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            Vector glob_remote = glob_remote(remoteAbsolutePath(str));
            int size = glob_remote.size();
            for (int i2 = 0; i2 < size; i2++) {
                String str2 = (String) glob_remote.elementAt(i2);
                SftpATTRS _stat = _stat(str2);
                _stat.setFLAGS(0);
                _stat.setPERMISSIONS(i);
                _setStat(str2, _stat);
            }
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            } else if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            } else {
                throw new SftpException(4, "");
            }
        }
    }

    public void setMtime(String str, int i) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            Vector glob_remote = glob_remote(remoteAbsolutePath(str));
            int size = glob_remote.size();
            for (int i2 = 0; i2 < size; i2++) {
                String str2 = (String) glob_remote.elementAt(i2);
                SftpATTRS _stat = _stat(str2);
                _stat.setFLAGS(0);
                _stat.setACMODTIME(_stat.getATime(), i);
                _setStat(str2, _stat);
            }
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            } else if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            } else {
                throw new SftpException(4, "");
            }
        }
    }

    public void rmdir(String str) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            Vector glob_remote = glob_remote(remoteAbsolutePath(str));
            int size = glob_remote.size();
            Header header = new Header();
            int i = 0;
            while (i < size) {
                sendRMDIR(Util.str2byte((String) glob_remote.elementAt(i), this.fEncoding));
                header = header(this.buf, header);
                int i2 = header.length;
                int i3 = header.type;
                fill(this.buf, i2);
                if (i3 == 101) {
                    int i4 = this.buf.getInt();
                    if (i4 != 0) {
                        throwStatusError(this.buf, i4);
                    }
                    i++;
                } else {
                    throw new SftpException(4, "");
                }
            }
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            } else if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            } else {
                throw new SftpException(4, "");
            }
        }
    }

    public void mkdir(String str) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            sendMKDIR(Util.str2byte(remoteAbsolutePath(str), this.fEncoding), (SftpATTRS) null);
            Header header = header(this.buf, new Header());
            int i = header.length;
            int i2 = header.type;
            fill(this.buf, i);
            if (i2 == 101) {
                int i3 = this.buf.getInt();
                if (i3 != 0) {
                    throwStatusError(this.buf, i3);
                    return;
                }
                return;
            }
            throw new SftpException(4, "");
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            } else if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            } else {
                throw new SftpException(4, "");
            }
        }
    }

    public SftpATTRS stat(String str) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            return _stat(isUnique(remoteAbsolutePath(str)));
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            } else if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            } else {
                throw new SftpException(4, "");
            }
        }
    }

    private SftpATTRS _stat(byte[] bArr) throws SftpException {
        try {
            sendSTAT(bArr);
            Header header = header(this.buf, new Header());
            int i = header.length;
            int i2 = header.type;
            fill(this.buf, i);
            if (i2 == 105) {
                return SftpATTRS.getATTR(this.buf);
            }
            if (i2 == 101) {
                throwStatusError(this.buf, this.buf.getInt());
            }
            throw new SftpException(4, "");
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            } else if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            } else {
                throw new SftpException(4, "");
            }
        }
    }

    private SftpATTRS _stat(String str) throws SftpException {
        return _stat(Util.str2byte(str, this.fEncoding));
    }

    public SftpStatVFS statVFS(String str) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            return _statVFS(isUnique(remoteAbsolutePath(str)));
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            } else if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            } else {
                throw new SftpException(4, "");
            }
        }
    }

    private SftpStatVFS _statVFS(byte[] bArr) throws SftpException {
        if (this.extension_statvfs) {
            try {
                sendSTATVFS(bArr);
                Header header = header(this.buf, new Header());
                int i = header.length;
                int i2 = header.type;
                fill(this.buf, i);
                if (i2 == 201) {
                    return SftpStatVFS.getStatVFS(this.buf);
                }
                if (i2 == 101) {
                    throwStatusError(this.buf, this.buf.getInt());
                }
                throw new SftpException(4, "");
            } catch (Exception e) {
                if (e instanceof SftpException) {
                    throw ((SftpException) e);
                } else if (e instanceof Throwable) {
                    throw new SftpException(4, "", e);
                } else {
                    throw new SftpException(4, "");
                }
            }
        } else {
            throw new SftpException(8, "statvfs@openssh.com is not supported");
        }
    }

    private SftpStatVFS _statVFS(String str) throws SftpException {
        return _statVFS(Util.str2byte(str, this.fEncoding));
    }

    public SftpATTRS lstat(String str) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            return _lstat(isUnique(remoteAbsolutePath(str)));
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            } else if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            } else {
                throw new SftpException(4, "");
            }
        }
    }

    private SftpATTRS _lstat(String str) throws SftpException {
        try {
            sendLSTAT(Util.str2byte(str, this.fEncoding));
            Header header = header(this.buf, new Header());
            int i = header.length;
            int i2 = header.type;
            fill(this.buf, i);
            if (i2 == 105) {
                return SftpATTRS.getATTR(this.buf);
            }
            if (i2 == 101) {
                throwStatusError(this.buf, this.buf.getInt());
            }
            throw new SftpException(4, "");
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            } else if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            } else {
                throw new SftpException(4, "");
            }
        }
    }

    private byte[] _realpath(String str) throws SftpException, IOException, Exception {
        sendREALPATH(Util.str2byte(str, this.fEncoding));
        Header header = header(this.buf, new Header());
        int i = header.length;
        int i2 = header.type;
        fill(this.buf, i);
        if (i2 == 101 || i2 == 104) {
            if (i2 == 101) {
                throwStatusError(this.buf, this.buf.getInt());
            }
            int i3 = this.buf.getInt();
            byte[] bArr = null;
            while (true) {
                int i4 = i3 - 1;
                if (i3 <= 0) {
                    return bArr;
                }
                bArr = this.buf.getString();
                if (this.server_version <= 3) {
                    this.buf.getString();
                }
                SftpATTRS.getATTR(this.buf);
                i3 = i4;
            }
        } else {
            throw new SftpException(4, "");
        }
    }

    public void setStat(String str, SftpATTRS sftpATTRS) throws SftpException {
        try {
            ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
            Vector glob_remote = glob_remote(remoteAbsolutePath(str));
            int size = glob_remote.size();
            for (int i = 0; i < size; i++) {
                _setStat((String) glob_remote.elementAt(i), sftpATTRS);
            }
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            } else if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            } else {
                throw new SftpException(4, "");
            }
        }
    }

    private void _setStat(String str, SftpATTRS sftpATTRS) throws SftpException {
        try {
            sendSETSTAT(Util.str2byte(str, this.fEncoding), sftpATTRS);
            Header header = header(this.buf, new Header());
            int i = header.length;
            int i2 = header.type;
            fill(this.buf, i);
            if (i2 == 101) {
                int i3 = this.buf.getInt();
                if (i3 != 0) {
                    throwStatusError(this.buf, i3);
                    return;
                }
                return;
            }
            throw new SftpException(4, "");
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            } else if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            } else {
                throw new SftpException(4, "");
            }
        }
    }

    public String pwd() throws SftpException {
        return getCwd();
    }

    public String lpwd() {
        return this.lcwd;
    }

    public String version() {
        return this.version;
    }

    public String getHome() throws SftpException {
        if (this.home == null) {
            try {
                ((Channel.MyPipedInputStream) this.io_in).updateReadSide();
                this.home = Util.byte2str(_realpath(""), this.fEncoding);
            } catch (Exception e) {
                if (e instanceof SftpException) {
                    throw ((SftpException) e);
                } else if (e instanceof Throwable) {
                    throw new SftpException(4, "", e);
                } else {
                    throw new SftpException(4, "");
                }
            }
        }
        return this.home;
    }

    private String getCwd() throws SftpException {
        if (this.cwd == null) {
            this.cwd = getHome();
        }
        return this.cwd;
    }

    private void setCwd(String str) {
        this.cwd = str;
    }

    private void read(byte[] bArr, int i, int i2) throws IOException, SftpException {
        while (i2 > 0) {
            int read = this.io_in.read(bArr, i, i2);
            if (read > 0) {
                i += read;
                i2 -= read;
            } else {
                throw new SftpException(4, "");
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean checkStatus(int[] iArr, Header header) throws IOException, SftpException {
        Header header2 = header(this.buf, header);
        int i = header2.length;
        int i2 = header2.type;
        if (iArr != null) {
            iArr[0] = header2.rid;
        }
        fill(this.buf, i);
        if (i2 == 101) {
            int i3 = this.buf.getInt();
            if (i3 == 0) {
                return true;
            }
            throwStatusError(this.buf, i3);
            return true;
        }
        throw new SftpException(4, "");
    }

    /* access modifiers changed from: private */
    public boolean _sendCLOSE(byte[] bArr, Header header) throws Exception {
        sendCLOSE(bArr);
        return checkStatus((int[]) null, header);
    }

    private void sendINIT() throws Exception {
        this.packet.reset();
        putHEAD(SSH_FXP_INIT, 5);
        this.buf.putInt(3);
        getSession().write(this.packet, this, 9);
    }

    private void sendREALPATH(byte[] bArr) throws Exception {
        sendPacketPath(SSH_FXP_REALPATH, bArr);
    }

    private void sendSTAT(byte[] bArr) throws Exception {
        sendPacketPath(SSH_FXP_STAT, bArr);
    }

    private void sendSTATVFS(byte[] bArr) throws Exception {
        sendPacketPath((byte) 0, bArr, "statvfs@openssh.com");
    }

    private void sendLSTAT(byte[] bArr) throws Exception {
        sendPacketPath(SSH_FXP_LSTAT, bArr);
    }

    private void sendFSTAT(byte[] bArr) throws Exception {
        sendPacketPath(SSH_FXP_FSTAT, bArr);
    }

    private void sendSETSTAT(byte[] bArr, SftpATTRS sftpATTRS) throws Exception {
        this.packet.reset();
        putHEAD(SSH_FXP_SETSTAT, bArr.length + 9 + sftpATTRS.length());
        Buffer buffer = this.buf;
        int i = this.seq;
        this.seq = i + 1;
        buffer.putInt(i);
        this.buf.putString(bArr);
        sftpATTRS.dump(this.buf);
        getSession().write(this.packet, this, bArr.length + 9 + sftpATTRS.length() + 4);
    }

    private void sendREMOVE(byte[] bArr) throws Exception {
        sendPacketPath(SSH_FXP_REMOVE, bArr);
    }

    private void sendMKDIR(byte[] bArr, SftpATTRS sftpATTRS) throws Exception {
        this.packet.reset();
        putHEAD(SSH_FXP_MKDIR, bArr.length + 9 + (sftpATTRS != null ? sftpATTRS.length() : 4));
        Buffer buffer = this.buf;
        int i = this.seq;
        this.seq = i + 1;
        buffer.putInt(i);
        this.buf.putString(bArr);
        if (sftpATTRS != null) {
            sftpATTRS.dump(this.buf);
        } else {
            this.buf.putInt(0);
        }
        getSession().write(this.packet, this, bArr.length + 9 + (sftpATTRS != null ? sftpATTRS.length() : 4) + 4);
    }

    private void sendRMDIR(byte[] bArr) throws Exception {
        sendPacketPath(SSH_FXP_RMDIR, bArr);
    }

    private void sendSYMLINK(byte[] bArr, byte[] bArr2) throws Exception {
        sendPacketPath((byte) SSH_FXP_SYMLINK, bArr, bArr2);
    }

    private void sendHARDLINK(byte[] bArr, byte[] bArr2) throws Exception {
        sendPacketPath((byte) 0, bArr, bArr2, "hardlink@openssh.com");
    }

    private void sendREADLINK(byte[] bArr) throws Exception {
        sendPacketPath(SSH_FXP_READLINK, bArr);
    }

    private void sendOPENDIR(byte[] bArr) throws Exception {
        sendPacketPath(SSH_FXP_OPENDIR, bArr);
    }

    private void sendREADDIR(byte[] bArr) throws Exception {
        sendPacketPath(SSH_FXP_READDIR, bArr);
    }

    private void sendRENAME(byte[] bArr, byte[] bArr2) throws Exception {
        sendPacketPath(SSH_FXP_RENAME, bArr, bArr2, this.extension_posix_rename ? "posix-rename@openssh.com" : null);
    }

    private void sendCLOSE(byte[] bArr) throws Exception {
        sendPacketPath(SSH_FXP_CLOSE, bArr);
    }

    private void sendOPENR(byte[] bArr) throws Exception {
        sendOPEN(bArr, 1);
    }

    private void sendOPENW(byte[] bArr) throws Exception {
        sendOPEN(bArr, 26);
    }

    private void sendOPENA(byte[] bArr) throws Exception {
        sendOPEN(bArr, 10);
    }

    private void sendOPEN(byte[] bArr, int i) throws Exception {
        this.packet.reset();
        putHEAD(SSH_FXP_OPEN, bArr.length + 17);
        Buffer buffer = this.buf;
        int i2 = this.seq;
        this.seq = i2 + 1;
        buffer.putInt(i2);
        this.buf.putString(bArr);
        this.buf.putInt(i);
        this.buf.putInt(0);
        getSession().write(this.packet, this, bArr.length + 17 + 4);
    }

    private void sendPacketPath(byte b, byte[] bArr) throws Exception {
        sendPacketPath(b, bArr, (String) null);
    }

    private void sendPacketPath(byte b, byte[] bArr, String str) throws Exception {
        this.packet.reset();
        int length = bArr.length + 9;
        if (str == null) {
            putHEAD(b, length);
            Buffer buffer = this.buf;
            int i = this.seq;
            this.seq = i + 1;
            buffer.putInt(i);
        } else {
            length += str.length() + 4;
            putHEAD(SSH_FXP_EXTENDED, length);
            Buffer buffer2 = this.buf;
            int i2 = this.seq;
            this.seq = i2 + 1;
            buffer2.putInt(i2);
            this.buf.putString(Util.str2byte(str));
        }
        this.buf.putString(bArr);
        getSession().write(this.packet, this, length + 4);
    }

    private void sendPacketPath(byte b, byte[] bArr, byte[] bArr2) throws Exception {
        sendPacketPath(b, bArr, bArr2, (String) null);
    }

    private void sendPacketPath(byte b, byte[] bArr, byte[] bArr2, String str) throws Exception {
        this.packet.reset();
        int length = bArr.length + 13 + bArr2.length;
        if (str == null) {
            putHEAD(b, length);
            Buffer buffer = this.buf;
            int i = this.seq;
            this.seq = i + 1;
            buffer.putInt(i);
        } else {
            length += str.length() + 4;
            putHEAD(SSH_FXP_EXTENDED, length);
            Buffer buffer2 = this.buf;
            int i2 = this.seq;
            this.seq = i2 + 1;
            buffer2.putInt(i2);
            this.buf.putString(Util.str2byte(str));
        }
        this.buf.putString(bArr);
        this.buf.putString(bArr2);
        getSession().write(this.packet, this, length + 4);
    }

    /* access modifiers changed from: private */
    public int sendWRITE(byte[] bArr, long j, byte[] bArr2, int i, int i2) throws Exception {
        this.opacket.reset();
        if (this.obuf.buffer.length < this.obuf.index + 13 + 21 + bArr.length + i2 + 128) {
            i2 = this.obuf.buffer.length - ((((this.obuf.index + 13) + 21) + bArr.length) + 128);
        }
        putHEAD(this.obuf, SSH_FXP_WRITE, bArr.length + 21 + i2);
        Buffer buffer = this.obuf;
        int i3 = this.seq;
        this.seq = i3 + 1;
        buffer.putInt(i3);
        this.obuf.putString(bArr);
        this.obuf.putLong(j);
        if (this.obuf.buffer != bArr2) {
            this.obuf.putString(bArr2, i, i2);
        } else {
            this.obuf.putInt(i2);
            this.obuf.skip(i2);
        }
        getSession().write(this.opacket, this, bArr.length + 21 + i2 + 4);
        return i2;
    }

    private void sendREAD(byte[] bArr, long j, int i) throws Exception {
        sendREAD(bArr, j, i, (RequestQueue) null);
    }

    /* access modifiers changed from: private */
    public void sendREAD(byte[] bArr, long j, int i, RequestQueue requestQueue) throws Exception {
        this.packet.reset();
        putHEAD(SSH_FXP_READ, bArr.length + 21);
        Buffer buffer = this.buf;
        int i2 = this.seq;
        this.seq = i2 + 1;
        buffer.putInt(i2);
        this.buf.putString(bArr);
        this.buf.putLong(j);
        this.buf.putInt(i);
        getSession().write(this.packet, this, bArr.length + 21 + 4);
        if (requestQueue != null) {
            requestQueue.add(this.seq - 1, j, i);
        }
    }

    private void putHEAD(Buffer buffer, byte b, int i) throws Exception {
        buffer.putByte((byte) 94);
        buffer.putInt(this.recipient);
        buffer.putInt(i + 4);
        buffer.putInt(i);
        buffer.putByte(b);
    }

    private void putHEAD(byte b, int i) throws Exception {
        putHEAD(this.buf, b, i);
    }

    private Vector glob_remote(String str) throws Exception {
        byte[] bArr;
        String str2;
        String str3 = str;
        Vector vector = new Vector();
        int lastIndexOf = str3.lastIndexOf(47);
        if (lastIndexOf < 0) {
            vector.addElement(Util.unquote(str));
            return vector;
        }
        String substring = str3.substring(0, lastIndexOf == 0 ? 1 : lastIndexOf);
        String substring2 = str3.substring(lastIndexOf + 1);
        String unquote = Util.unquote(substring);
        byte[][] bArr2 = new byte[1][];
        if (!isPattern(substring2, bArr2)) {
            if (!unquote.equals("/")) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(unquote);
                stringBuffer.append("/");
                unquote = stringBuffer.toString();
            }
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(unquote);
            stringBuffer2.append(Util.unquote(substring2));
            vector.addElement(stringBuffer2.toString());
            return vector;
        }
        byte[] bArr3 = bArr2[0];
        sendOPENDIR(Util.str2byte(unquote, this.fEncoding));
        Header header = header(this.buf, new Header());
        int i = header.length;
        int i2 = header.type;
        fill(this.buf, i);
        int i3 = 4;
        if (i2 == 101 || i2 == 102) {
            if (i2 == 101) {
                throwStatusError(this.buf, this.buf.getInt());
            }
            byte[] string = this.buf.getString();
            String str4 = null;
            while (true) {
                sendREADDIR(string);
                header = header(this.buf, header);
                int i4 = header.length;
                int i5 = header.type;
                if (i5 != 101 && i5 != 104) {
                    throw new SftpException(i3, "");
                } else if (i5 == 101) {
                    fill(this.buf, i4);
                    if (_sendCLOSE(string, header)) {
                        return vector;
                    }
                    return null;
                } else {
                    this.buf.rewind();
                    fill(this.buf.buffer, 0, i3);
                    int i6 = i4 - 4;
                    this.buf.reset();
                    for (int i7 = this.buf.getInt(); i7 > 0; i7--) {
                        if (i6 > 0) {
                            this.buf.shift();
                            int read = this.io_in.read(this.buf.buffer, this.buf.index, this.buf.buffer.length > this.buf.index + i6 ? i6 : this.buf.buffer.length - this.buf.index);
                            if (read <= 0) {
                                break;
                            }
                            this.buf.index += read;
                            i6 -= read;
                        }
                        byte[] string2 = this.buf.getString();
                        if (this.server_version <= 3) {
                            this.buf.getString();
                        }
                        SftpATTRS.getATTR(this.buf);
                        if (!this.fEncoding_is_utf8) {
                            str2 = Util.byte2str(string2, this.fEncoding);
                            bArr = Util.str2byte(str2, UTF8);
                        } else {
                            bArr = string2;
                            str2 = null;
                        }
                        if (Util.glob(bArr3, bArr)) {
                            if (str2 == null) {
                                str2 = Util.byte2str(string2, this.fEncoding);
                            }
                            if (str4 == null) {
                                if (!unquote.endsWith("/")) {
                                    StringBuffer stringBuffer3 = new StringBuffer();
                                    stringBuffer3.append(unquote);
                                    stringBuffer3.append("/");
                                    str4 = stringBuffer3.toString();
                                } else {
                                    str4 = unquote;
                                }
                            }
                            StringBuffer stringBuffer4 = new StringBuffer();
                            stringBuffer4.append(str4);
                            stringBuffer4.append(str2);
                            vector.addElement(stringBuffer4.toString());
                        }
                    }
                    i3 = 4;
                }
            }
        } else {
            throw new SftpException(4, "");
        }
    }

    private boolean isPattern(byte[] bArr) {
        int i;
        int length = bArr.length;
        int i2 = 0;
        while (i2 < length) {
            if (bArr[i2] == 42 || bArr[i2] == 63) {
                return true;
            }
            if (bArr[i2] == 92 && (i = i2 + 1) < length) {
                i2 = i;
            }
            i2++;
        }
        return false;
    }

    private Vector glob_local(String str) throws Exception {
        byte[] bArr;
        Vector vector = new Vector();
        byte[] str2byte = Util.str2byte(str, UTF8);
        int length = str2byte.length - 1;
        while (length >= 0) {
            if (str2byte[length] == 42 || str2byte[length] == 63) {
                if (fs_is_bs || length <= 0 || str2byte[length - 1] != 92 || length - 1 <= 0 || str2byte[length - 1] != 92) {
                    break;
                }
                length = (length - 1) - 1;
            } else {
                length--;
            }
        }
        if (length < 0) {
            if (!fs_is_bs) {
                str = Util.unquote(str);
            }
            vector.addElement(str);
            return vector;
        }
        while (length >= 0 && str2byte[length] != file_separatorc && (!fs_is_bs || str2byte[length] != 47)) {
            length--;
        }
        if (length < 0) {
            if (!fs_is_bs) {
                str = Util.unquote(str);
            }
            vector.addElement(str);
            return vector;
        }
        if (length == 0) {
            bArr = new byte[]{(byte) file_separatorc};
        } else {
            bArr = new byte[length];
            System.arraycopy(str2byte, 0, bArr, 0, length);
        }
        byte[] bArr2 = new byte[((str2byte.length - length) - 1)];
        System.arraycopy(str2byte, length + 1, bArr2, 0, bArr2.length);
        try {
            String[] list = new File(Util.byte2str(bArr, UTF8)).list();
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(Util.byte2str(bArr));
            stringBuffer.append(file_separator);
            String stringBuffer2 = stringBuffer.toString();
            for (int i = 0; i < list.length; i++) {
                if (Util.glob(bArr2, Util.str2byte(list[i], UTF8))) {
                    StringBuffer stringBuffer3 = new StringBuffer();
                    stringBuffer3.append(stringBuffer2);
                    stringBuffer3.append(list[i]);
                    vector.addElement(stringBuffer3.toString());
                }
            }
        } catch (Exception unused) {
        }
        return vector;
    }

    private void throwStatusError(Buffer buffer, int i) throws SftpException {
        if (this.server_version < 3 || buffer.getLength() < 4) {
            throw new SftpException(i, "Failure");
        }
        throw new SftpException(i, Util.byte2str(buffer.getString(), UTF8));
    }

    private static boolean isLocalAbsolutePath(String str) {
        return new File(str).isAbsolute();
    }

    public void disconnect() {
        super.disconnect();
    }

    private boolean isPattern(String str, byte[][] bArr) {
        byte[] str2byte = Util.str2byte(str, UTF8);
        if (bArr != null) {
            bArr[0] = str2byte;
        }
        return isPattern(str2byte);
    }

    private boolean isPattern(String str) {
        return isPattern(str, (byte[][]) null);
    }

    /* access modifiers changed from: private */
    public void fill(Buffer buffer, int i) throws IOException {
        buffer.reset();
        fill(buffer.buffer, 0, i);
        buffer.skip(i);
    }

    /* access modifiers changed from: private */
    public int fill(byte[] bArr, int i, int i2) throws IOException {
        int i3 = i;
        while (i2 > 0) {
            int read = this.io_in.read(bArr, i3, i2);
            if (read > 0) {
                i3 += read;
                i2 -= read;
            } else {
                throw new IOException("inputstream is closed");
            }
        }
        return i3 - i;
    }

    /* access modifiers changed from: private */
    public void skip(long j) throws IOException {
        while (j > 0) {
            long skip = this.io_in.skip(j);
            if (skip > 0) {
                j -= skip;
            } else {
                return;
            }
        }
    }

    class Header {
        int length;
        int rid;
        int type;

        Header() {
        }
    }

    /* access modifiers changed from: private */
    public Header header(Buffer buffer, Header header) throws IOException {
        buffer.rewind();
        fill(buffer.buffer, 0, 9);
        header.length = buffer.getInt() - 5;
        header.type = buffer.getByte() & 255;
        header.rid = buffer.getInt();
        return header;
    }

    private String remoteAbsolutePath(String str) throws SftpException {
        if (str.charAt(0) == '/') {
            return str;
        }
        String cwd2 = getCwd();
        if (cwd2.endsWith("/")) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(cwd2);
            stringBuffer.append(str);
            return stringBuffer.toString();
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append(cwd2);
        stringBuffer2.append("/");
        stringBuffer2.append(str);
        return stringBuffer2.toString();
    }

    private String localAbsolutePath(String str) {
        if (isLocalAbsolutePath(str)) {
            return str;
        }
        if (this.lcwd.endsWith(file_separator)) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(this.lcwd);
            stringBuffer.append(str);
            return stringBuffer.toString();
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append(this.lcwd);
        stringBuffer2.append(file_separator);
        stringBuffer2.append(str);
        return stringBuffer2.toString();
    }

    private String isUnique(String str) throws SftpException, Exception {
        Vector glob_remote = glob_remote(str);
        if (glob_remote.size() == 1) {
            return (String) glob_remote.elementAt(0);
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append(" is not unique: ");
        stringBuffer.append(glob_remote.toString());
        throw new SftpException(4, stringBuffer.toString());
    }

    public int getServerVersion() throws SftpException {
        if (isConnected()) {
            return this.server_version;
        }
        throw new SftpException(4, "The channel is not connected.");
    }

    public void setFilenameEncoding(String str) throws SftpException {
        int serverVersion = getServerVersion();
        if (3 > serverVersion || serverVersion > 5 || str.equals(UTF8)) {
            if (str.equals(UTF8)) {
                str = UTF8;
            }
            this.fEncoding = str;
            this.fEncoding_is_utf8 = this.fEncoding.equals(UTF8);
            return;
        }
        throw new SftpException(4, "The encoding can not be changed for this sftp server.");
    }

    public String getExtension(String str) {
        Hashtable hashtable = this.extensions;
        if (hashtable == null) {
            return null;
        }
        return (String) hashtable.get(str);
    }

    public String realpath(String str) throws SftpException {
        try {
            return Util.byte2str(_realpath(remoteAbsolutePath(str)), this.fEncoding);
        } catch (Exception e) {
            if (e instanceof SftpException) {
                throw ((SftpException) e);
            } else if (e instanceof Throwable) {
                throw new SftpException(4, "", e);
            } else {
                throw new SftpException(4, "");
            }
        }
    }

    public class LsEntry implements Comparable {
        private SftpATTRS attrs;
        private String filename;
        private String longname;

        LsEntry(String str, String str2, SftpATTRS sftpATTRS) {
            setFilename(str);
            setLongname(str2);
            setAttrs(sftpATTRS);
        }

        public String getFilename() {
            return this.filename;
        }

        /* access modifiers changed from: package-private */
        public void setFilename(String str) {
            this.filename = str;
        }

        public String getLongname() {
            return this.longname;
        }

        /* access modifiers changed from: package-private */
        public void setLongname(String str) {
            this.longname = str;
        }

        public SftpATTRS getAttrs() {
            return this.attrs;
        }

        /* access modifiers changed from: package-private */
        public void setAttrs(SftpATTRS sftpATTRS) {
            this.attrs = sftpATTRS;
        }

        public String toString() {
            return this.longname;
        }

        public int compareTo(Object obj) throws ClassCastException {
            if (obj instanceof LsEntry) {
                return this.filename.compareTo(((LsEntry) obj).getFilename());
            }
            throw new ClassCastException("a decendent of LsEntry must be given.");
        }
    }
}
