package com.jcraft.jsch;

public interface Compression {
    public static final int DEFLATER = 1;
    public static final int INFLATER = 0;

    byte[] compress(byte[] bArr, int i, int[] iArr);

    void init(int i, int i2);

    byte[] uncompress(byte[] bArr, int i, int[] iArr);
}
