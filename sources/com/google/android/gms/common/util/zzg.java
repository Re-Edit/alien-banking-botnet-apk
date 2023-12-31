package com.google.android.gms.common.util;

import android.support.v4.util.ArrayMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class zzg {
    public static <K, V> Map<K, V> zza(K k, V v, K k2, V v2) {
        Map zzg = zzg(2, false);
        zzg.put(k, v);
        zzg.put(k2, v2);
        return Collections.unmodifiableMap(zzg);
    }

    public static <K, V> Map<K, V> zza(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
        Map zzg = zzg(6, false);
        zzg.put(k, v);
        zzg.put(k2, v2);
        zzg.put(k3, v3);
        zzg.put(k4, v4);
        zzg.put(k5, v5);
        zzg.put(k6, v6);
        return Collections.unmodifiableMap(zzg);
    }

    public static <T> Set<T> zza(T t, T t2, T t3) {
        Set zzf = zzf(3, false);
        zzf.add(t);
        zzf.add(t2);
        zzf.add(t3);
        return Collections.unmodifiableSet(zzf);
    }

    public static <T> Set<T> zzb(T... tArr) {
        switch (tArr.length) {
            case 0:
                return Collections.emptySet();
            case 1:
                return Collections.singleton(tArr[0]);
            case 2:
                T t = tArr[0];
                T t2 = tArr[1];
                Set zzf = zzf(2, false);
                zzf.add(t);
                zzf.add(t2);
                return Collections.unmodifiableSet(zzf);
            case 3:
                return zza(tArr[0], tArr[1], tArr[2]);
            case 4:
                T t3 = tArr[0];
                T t4 = tArr[1];
                T t5 = tArr[2];
                T t6 = tArr[3];
                Set zzf2 = zzf(4, false);
                zzf2.add(t3);
                zzf2.add(t4);
                zzf2.add(t5);
                zzf2.add(t6);
                return Collections.unmodifiableSet(zzf2);
            default:
                Set zzf3 = zzf(tArr.length, false);
                Collections.addAll(zzf3, tArr);
                return Collections.unmodifiableSet(zzf3);
        }
    }

    private static <T> Set<T> zzf(int i, boolean z) {
        return i <= 256 ? new zzb(i) : new HashSet(i, 1.0f);
    }

    private static <K, V> Map<K, V> zzg(int i, boolean z) {
        return i <= 256 ? new ArrayMap(i) : new HashMap(i, 1.0f);
    }
}
