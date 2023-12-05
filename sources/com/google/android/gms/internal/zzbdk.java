package com.google.android.gms.internal;

import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;

public class zzbdk implements Releasable, Result {
    private Status mStatus;
    protected final DataHolder zzaCZ;

    protected zzbdk(DataHolder dataHolder, Status status) {
        this.mStatus = status;
        this.zzaCZ = dataHolder;
    }

    public Status getStatus() {
        return this.mStatus;
    }

    public void release() {
        DataHolder dataHolder = this.zzaCZ;
        if (dataHolder != null) {
            dataHolder.close();
        }
    }
}
