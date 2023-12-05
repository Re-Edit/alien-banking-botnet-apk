package com.google.android.gms.internal;

final class zzbeo implements zzbcj {
    private /* synthetic */ zzben zzaEo;

    zzbeo(zzben zzben) {
        this.zzaEo = zzben;
    }

    public final void zzac(boolean z) {
        this.zzaEo.mHandler.sendMessage(this.zzaEo.mHandler.obtainMessage(1, Boolean.valueOf(z)));
    }
}
