package com.google.android.gms.common.internal;

import android.support.annotation.Nullable;

public final class zzbh {
    public static boolean equal(@Nullable Object obj, @Nullable Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    public static zzbj zzt(Object obj) {
        return new zzbj(obj);
    }
}
