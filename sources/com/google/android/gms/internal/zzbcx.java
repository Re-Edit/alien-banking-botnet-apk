package com.google.android.gms.internal;

final class zzbcx implements Runnable {
    private /* synthetic */ zzbcw zzaCz;

    zzbcx(zzbcw zzbcw) {
        this.zzaCz = zzbcw;
    }

    public final void run() {
        this.zzaCz.zzaCx.lock();
        try {
            this.zzaCz.zzpD();
        } finally {
            this.zzaCz.zzaCx.unlock();
        }
    }
}
