package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

final class zzbhf extends zzbgz {
    private final zzbcl<Status> zzaIB;

    public zzbhf(zzbcl<Status> zzbcl) {
        this.zzaIB = zzbcl;
    }

    public final void zzaC(int i) throws RemoteException {
        this.zzaIB.setResult(new Status(i));
    }
}
