package com.google.android.gms.internal;

final class zzbfw implements Runnable {
    private /* synthetic */ zzcvj zzaDz;
    private /* synthetic */ zzbfv zzaFa;

    zzbfw(zzbfv zzbfv, zzcvj zzcvj) {
        this.zzaFa = zzbfv;
        this.zzaDz = zzcvj;
    }

    public final void run() {
        this.zzaFa.zzc(this.zzaDz);
    }
}
