package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;

final class zzbcz implements zzbfc {
    private /* synthetic */ zzbcw zzaCz;

    private zzbcz(zzbcw zzbcw) {
        this.zzaCz = zzbcw;
    }

    /* synthetic */ zzbcz(zzbcw zzbcw, zzbcx zzbcx) {
        this(zzbcw);
    }

    public final void zzc(@NonNull ConnectionResult connectionResult) {
        this.zzaCz.zzaCx.lock();
        try {
            ConnectionResult unused = this.zzaCz.zzaCv = connectionResult;
            this.zzaCz.zzpD();
        } finally {
            this.zzaCz.zzaCx.unlock();
        }
    }

    public final void zze(int i, boolean z) {
        this.zzaCz.zzaCx.lock();
        try {
            if (this.zzaCz.zzaCw) {
                boolean unused = this.zzaCz.zzaCw = false;
                this.zzaCz.zzd(i, z);
            } else {
                boolean unused2 = this.zzaCz.zzaCw = true;
                this.zzaCz.zzaCo.onConnectionSuspended(i);
            }
        } finally {
            this.zzaCz.zzaCx.unlock();
        }
    }

    public final void zzm(@Nullable Bundle bundle) {
        this.zzaCz.zzaCx.lock();
        try {
            ConnectionResult unused = this.zzaCz.zzaCv = ConnectionResult.zzazZ;
            this.zzaCz.zzpD();
        } finally {
            this.zzaCz.zzaCx.unlock();
        }
    }
}
