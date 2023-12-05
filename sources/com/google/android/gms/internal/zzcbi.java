package com.google.android.gms.internal;

import android.os.RemoteException;

public final class zzcbi extends zzcbg<Boolean> {
    public zzcbi(int i, String str, Boolean bool) {
        super(0, str, bool);
    }

    /* access modifiers changed from: private */
    /* renamed from: zzb */
    public final Boolean zza(zzcbo zzcbo) {
        try {
            return Boolean.valueOf(zzcbo.getBooleanFlagValue(getKey(), ((Boolean) zzdH()).booleanValue(), getSource()));
        } catch (RemoteException unused) {
            return (Boolean) zzdH();
        }
    }
}
