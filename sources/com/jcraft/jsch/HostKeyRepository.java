package com.jcraft.jsch;

public interface HostKeyRepository {
    public static final int CHANGED = 2;
    public static final int NOT_INCLUDED = 1;
    public static final int OK = 0;

    void add(HostKey hostKey, UserInfo userInfo);

    int check(String str, byte[] bArr);

    HostKey[] getHostKey();

    HostKey[] getHostKey(String str, String str2);

    String getKnownHostsRepositoryID();

    void remove(String str, String str2);

    void remove(String str, String str2, byte[] bArr);
}
