package com.google.android.gms.common.util;

import android.text.TextUtils;
import java.util.regex.Pattern;

public final class zzv {
    private static final Pattern zzaKc = Pattern.compile("\\$\\{(.*?)\\}");

    public static String zzcL(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return str;
    }

    public static boolean zzcM(String str) {
        return str == null || str.trim().isEmpty();
    }
}
