package com.google.android.gms.internal;

import android.os.RemoteException;

public final class zzcbk extends zzcbg<Long> {
    public zzcbk(int i, String str, Long l) {
        super(0, str, l);
    }

    /* access modifiers changed from: private */
    /* renamed from: zzd */
    public final Long zza(zzcbo zzcbo) {
        try {
            return Long.valueOf(zzcbo.getLongFlagValue(getKey(), ((Long) zzdH()).longValue(), getSource()));
        } catch (RemoteException unused) {
            return (Long) zzdH();
        }
    }
}
