package com.jcraft.jsch;

public interface SftpProgressMonitor {
    public static final int GET = 1;
    public static final int PUT = 0;
    public static final long UNKNOWN_SIZE = -1;

    boolean count(long j);

    void end();

    void init(int i, String str, String str2, long j);
}
