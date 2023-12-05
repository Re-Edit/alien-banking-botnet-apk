package com.google.android.gms.common;

import java.lang.ref.WeakReference;

abstract class zzi extends zzg {
    private static final WeakReference<byte[]> zzaAl = new WeakReference<>((Object) null);
    private WeakReference<byte[]> zzaAk = zzaAl;

    zzi(byte[] bArr) {
        super(bArr);
    }

    /* access modifiers changed from: package-private */
    public final byte[] getBytes() {
        byte[] bArr;
        synchronized (this) {
            bArr = (byte[]) this.zzaAk.get();
            if (bArr == null) {
                bArr = zzoY();
                this.zzaAk = new WeakReference<>(bArr);
            }
        }
        return bArr;
    }

    /* access modifiers changed from: protected */
    public abstract byte[] zzoY();
}
