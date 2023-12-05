package com.google.android.gms.internal;

import com.google.android.gms.common.data.DataHolder;

public abstract class zzbdj<L> implements zzbfl<L> {
    private final DataHolder zzaCZ;

    protected zzbdj(DataHolder dataHolder) {
        this.zzaCZ = dataHolder;
    }

    /* access modifiers changed from: protected */
    public abstract void zza(L l, DataHolder dataHolder);

    public final void zzpR() {
        DataHolder dataHolder = this.zzaCZ;
        if (dataHolder != null) {
            dataHolder.close();
        }
    }

    public final void zzq(L l) {
        zza(l, this.zzaCZ);
    }
}
