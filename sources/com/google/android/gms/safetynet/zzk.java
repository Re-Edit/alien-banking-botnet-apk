package com.google.android.gms.safetynet;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbgd;
import com.google.android.gms.internal.zzctg;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzk extends zzctg {
    private /* synthetic */ TaskCompletionSource zzaCX;

    zzk(zzj zzj, TaskCompletionSource taskCompletionSource) {
        this.zzaCX = taskCompletionSource;
    }

    public final void zzF(Status status) {
        zzbgd.zza(status, null, this.zzaCX);
    }
}
