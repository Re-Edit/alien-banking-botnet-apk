package com.google.android.gms.internal;

final class zzbfh implements Runnable {
    private /* synthetic */ String zzO;
    private /* synthetic */ zzbfe zzaEM;
    private /* synthetic */ zzbfg zzaEN;

    zzbfh(zzbfg zzbfg, zzbfe zzbfe, String str) {
        this.zzaEN = zzbfg;
        this.zzaEM = zzbfe;
        this.zzO = str;
    }

    public final void run() {
        if (this.zzaEN.zzLj > 0) {
            this.zzaEM.onCreate(this.zzaEN.zzaEL != null ? this.zzaEN.zzaEL.getBundle(this.zzO) : null);
        }
        if (this.zzaEN.zzLj >= 2) {
            this.zzaEM.onStart();
        }
        if (this.zzaEN.zzLj >= 3) {
            this.zzaEM.onResume();
        }
        if (this.zzaEN.zzLj >= 4) {
            this.zzaEM.onStop();
        }
        if (this.zzaEN.zzLj >= 5) {
            this.zzaEM.onDestroy();
        }
    }
}
