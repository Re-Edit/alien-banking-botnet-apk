package com.google.android.gms.internal;

import android.app.Dialog;

final class zzbcp extends zzbex {
    private /* synthetic */ Dialog zzaBV;
    private /* synthetic */ zzbco zzaBW;

    zzbcp(zzbco zzbco, Dialog dialog) {
        this.zzaBW = zzbco;
        this.zzaBV = dialog;
    }

    public final void zzpy() {
        this.zzaBW.zzaBU.zzpv();
        if (this.zzaBV.isShowing()) {
            this.zzaBV.dismiss();
        }
    }
}
