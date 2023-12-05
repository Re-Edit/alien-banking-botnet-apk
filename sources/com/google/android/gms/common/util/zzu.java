package com.google.android.gms.common.util;

import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzbr;
import java.util.Set;

public final class zzu {
    public static String[] zzc(Set<Scope> set) {
        zzbr.zzb(set, (Object) "scopes can't be null.");
        Scope[] scopeArr = (Scope[]) set.toArray(new Scope[set.size()]);
        zzbr.zzb(scopeArr, (Object) "scopes can't be null.");
        String[] strArr = new String[scopeArr.length];
        for (int i = 0; i < scopeArr.length; i++) {
            strArr[i] = scopeArr[i].zzpn();
        }
        return strArr;
    }
}
