package com.google.android.gms.internal;

final class zzq implements Runnable {
    private /* synthetic */ String zzO;
    private /* synthetic */ long zzP;
    private /* synthetic */ zzp zzQ;

    zzq(zzp zzp, String str, long j) {
        this.zzQ = zzp;
        this.zzO = str;
        this.zzP = j;
    }

    public final void run() {
        this.zzQ.zzB.zza(this.zzO, this.zzP);
        this.zzQ.zzB.zzc(toString());
    }
}
