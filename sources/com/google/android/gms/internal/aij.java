package com.google.android.gms.internal;

import java.io.IOException;

public final class aij {
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    private static int zzcvi = 11;
    private static int zzcvj = 12;
    private static int zzcvk = 16;
    private static int zzcvl = 26;
    public static final int[] zzcvm = new int[0];
    public static final long[] zzcvn = new long[0];
    public static final float[] zzcvo = new float[0];
    private static double[] zzcvp = new double[0];
    public static final boolean[] zzcvq = new boolean[0];
    public static final byte[][] zzcvr = new byte[0][];
    public static final byte[] zzcvs = new byte[0];

    public static final int zzb(ahw ahw, int i) throws IOException {
        int position = ahw.getPosition();
        ahw.zzcl(i);
        int i2 = 1;
        while (ahw.zzLQ() == i) {
            ahw.zzcl(i);
            i2++;
        }
        ahw.zzq(position, i);
        return i2;
    }
}
