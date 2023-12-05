package com.google.android.gms.internal;

final class zzbgb implements Runnable {
    private /* synthetic */ String zzO;
    private /* synthetic */ zzbfe zzaEM;
    private /* synthetic */ zzbga zzaFb;

    zzbgb(zzbga zzbga, zzbfe zzbfe, String str) {
        this.zzaFb = zzbga;
        this.zzaEM = zzbfe;
        this.zzO = str;
    }

    public final void run() {
        if (this.zzaFb.zzLj > 0) {
            this.zzaEM.onCreate(this.zzaFb.zzaEL != null ? this.zzaFb.zzaEL.getBundle(this.zzO) : null);
        }
        if (this.zzaFb.zzLj >= 2) {
            this.zzaEM.onStart();
        }
        if (this.zzaFb.zzLj >= 3) {
            this.zzaEM.onResume();
        }
        if (this.zzaFb.zzLj >= 4) {
            this.zzaEM.onStop();
        }
        if (this.zzaFb.zzLj >= 5) {
            this.zzaEM.onDestroy();
        }
    }
}
