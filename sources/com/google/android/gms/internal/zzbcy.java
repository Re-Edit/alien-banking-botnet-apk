package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;

final class zzbcy implements zzbfc {
    private /* synthetic */ zzbcw zzaCz;

    private zzbcy(zzbcw zzbcw) {
        this.zzaCz = zzbcw;
    }

    /* synthetic */ zzbcy(zzbcw zzbcw, zzbcx zzbcx) {
        this(zzbcw);
    }

    public final void zzc(@NonNull ConnectionResult connectionResult) {
        this.zzaCz.zzaCx.lock();
        try {
            ConnectionResult unused = this.zzaCz.zzaCu = connectionResult;
            this.zzaCz.zzpD();
        } finally {
            this.zzaCz.zzaCx.unlock();
        }
    }

    public final void zze(int i, boolean z) {
        this.zzaCz.zzaCx.lock();
        try {
            if (!this.zzaCz.zzaCw && this.zzaCz.zzaCv != null) {
                if (this.zzaCz.zzaCv.isSuccess()) {
                    boolean unused = this.zzaCz.zzaCw = true;
                    this.zzaCz.zzaCp.onConnectionSuspended(i);
                }
            }
            boolean unused2 = this.zzaCz.zzaCw = false;
            this.zzaCz.zzd(i, z);
        } finally {
            this.zzaCz.zzaCx.unlock();
        }
    }

    public final void zzm(@Nullable Bundle bundle) {
        this.zzaCz.zzaCx.lock();
        try {
            this.zzaCz.zzl(bundle);
            ConnectionResult unused = this.zzaCz.zzaCu = ConnectionResult.zzazZ;
            this.zzaCz.zzpD();
        } finally {
            this.zzaCz.zzaCx.unlock();
        }
    }
}
