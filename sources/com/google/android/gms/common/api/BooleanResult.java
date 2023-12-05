package com.google.android.gms.common.api;

import com.google.android.gms.common.internal.zzbr;

public class BooleanResult implements Result {
    private final Status mStatus;
    private final boolean zzaAK;

    public BooleanResult(Status status, boolean z) {
        this.mStatus = (Status) zzbr.zzb(status, (Object) "Status must not be null");
        this.zzaAK = z;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BooleanResult)) {
            return false;
        }
        BooleanResult booleanResult = (BooleanResult) obj;
        return this.mStatus.equals(booleanResult.mStatus) && this.zzaAK == booleanResult.zzaAK;
    }

    public Status getStatus() {
        return this.mStatus;
    }

    public boolean getValue() {
        return this.zzaAK;
    }

    public final int hashCode() {
        return ((this.mStatus.hashCode() + 527) * 31) + (this.zzaAK ? 1 : 0);
    }
}
