package com.google.android.gms.internal;

import java.lang.ref.WeakReference;

final class zzbeh extends zzbex {
    private WeakReference<zzbeb> zzaDT;

    zzbeh(zzbeb zzbeb) {
        this.zzaDT = new WeakReference<>(zzbeb);
    }

    public final void zzpy() {
        zzbeb zzbeb = (zzbeb) this.zzaDT.get();
        if (zzbeb != null) {
            zzbeb.resume();
        }
    }
}
