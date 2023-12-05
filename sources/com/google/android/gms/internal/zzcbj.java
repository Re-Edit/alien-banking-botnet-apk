package com.google.android.gms.internal;

import android.os.RemoteException;

public final class zzcbj extends zzcbg<Integer> {
    public zzcbj(int i, String str, Integer num) {
        super(0, str, num);
    }

    /* access modifiers changed from: private */
    /* renamed from: zzc */
    public final Integer zza(zzcbo zzcbo) {
        try {
            return Integer.valueOf(zzcbo.getIntFlagValue(getKey(), ((Integer) zzdH()).intValue(), getSource()));
        } catch (RemoteException unused) {
            return (Integer) zzdH();
        }
    }
}
