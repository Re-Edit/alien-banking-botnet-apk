package com.google.android.gms.common.internal;

import java.util.ArrayList;
import java.util.List;

public final class zzbj {
    private final List<String> zzaIj;
    private final Object zzaay;

    private zzbj(Object obj) {
        this.zzaay = zzbr.zzu(obj);
        this.zzaIj = new ArrayList();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(100);
        sb.append(this.zzaay.getClass().getSimpleName());
        sb.append('{');
        int size = this.zzaIj.size();
        for (int i = 0; i < size; i++) {
            sb.append(this.zzaIj.get(i));
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public final zzbj zzg(String str, Object obj) {
        List<String> list = this.zzaIj;
        String str2 = (String) zzbr.zzu(str);
        String valueOf = String.valueOf(String.valueOf(obj));
        StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 1 + String.valueOf(valueOf).length());
        sb.append(str2);
        sb.append("=");
        sb.append(valueOf);
        list.add(sb.toString());
        return this;
    }
}
