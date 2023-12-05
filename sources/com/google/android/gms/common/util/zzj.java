package com.google.android.gms.common.util;

import android.os.SystemClock;

public final class zzj implements zzf {
    private static zzj zzaJK = new zzj();

    private zzj() {
    }

    public static zzf zzrX() {
        return zzaJK;
    }

    public final long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public final long elapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    public final long nanoTime() {
        return System.nanoTime();
    }
}
