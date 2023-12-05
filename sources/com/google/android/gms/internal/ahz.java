package com.google.android.gms.internal;

import com.google.android.gms.internal.ahz;
import java.io.IOException;

public abstract class ahz<M extends ahz<M>> extends aif {
    protected aib zzcuW;

    /* renamed from: zzMd */
    public M clone() throws CloneNotSupportedException {
        M m = (ahz) super.clone();
        aid.zza(this, (ahz) m);
        return m;
    }

    public /* synthetic */ aif zzMe() throws CloneNotSupportedException {
        return (ahz) clone();
    }

    public final <T> T zza(aia<M, T> aia) {
        aic zzcw;
        aib aib = this.zzcuW;
        if (aib == null || (zzcw = aib.zzcw(aia.tag >>> 3)) == null) {
            return null;
        }
        return zzcw.zzb(aia);
    }

    public void zza(ahx ahx) throws IOException {
        if (this.zzcuW != null) {
            for (int i = 0; i < this.zzcuW.size(); i++) {
                this.zzcuW.zzcx(i).zza(ahx);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final boolean zza(ahw ahw, int i) throws IOException {
        int position = ahw.getPosition();
        if (!ahw.zzcl(i)) {
            return false;
        }
        int i2 = i >>> 3;
        aii aii = new aii(i, ahw.zzp(position, ahw.getPosition() - position));
        aic aic = null;
        aib aib = this.zzcuW;
        if (aib == null) {
            this.zzcuW = new aib();
        } else {
            aic = aib.zzcw(i2);
        }
        if (aic == null) {
            aic = new aic();
            this.zzcuW.zza(i2, aic);
        }
        aic.zza(aii);
        return true;
    }

    /* access modifiers changed from: protected */
    public int zzn() {
        if (this.zzcuW == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.zzcuW.size(); i2++) {
            i += this.zzcuW.zzcx(i2).zzn();
        }
        return i;
    }
}
