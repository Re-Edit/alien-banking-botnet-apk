package com.google.protobuf;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class zzd {
    private static volatile boolean zzcux = false;
    private static final Class<?> zzcuy = zzLI();
    static final zzd zzcuz = new zzd(true);
    private final Map<Object, Object> zzcuA;

    zzd() {
        this.zzcuA = new HashMap();
    }

    zzd(boolean z) {
        this.zzcuA = Collections.emptyMap();
    }

    private static Class<?> zzLI() {
        try {
            return Class.forName("com.google.protobuf.Extension");
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }
}
