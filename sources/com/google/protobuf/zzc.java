package com.google.protobuf;

final class zzc {
    private static Class<?> zzcuw = zzLG();

    private static Class<?> zzLG() {
        try {
            return Class.forName("com.google.protobuf.ExtensionRegistry");
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public static zzd zzLH() {
        Class<?> cls = zzcuw;
        if (cls != null) {
            try {
                return (zzd) cls.getMethod("getEmptyRegistry", new Class[0]).invoke((Object) null, new Object[0]);
            } catch (Exception unused) {
            }
        }
        return zzd.zzcuz;
    }
}
