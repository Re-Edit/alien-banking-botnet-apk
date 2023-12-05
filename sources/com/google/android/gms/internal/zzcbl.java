package com.google.android.gms.internal;

import android.os.RemoteException;

public final class zzcbl extends zzcbg<String> {
    public zzcbl(int i, String str, String str2) {
        super(0, str, str2);
    }

    /* access modifiers changed from: private */
    /* renamed from: zze */
    public final String zza(zzcbo zzcbo) {
        try {
            return zzcbo.getStringFlagValue(getKey(), (String) zzdH(), getSource());
        } catch (RemoteException unused) {
            return (String) zzdH();
        }
    }
}
