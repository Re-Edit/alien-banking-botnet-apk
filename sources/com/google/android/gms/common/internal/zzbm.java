package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.TimeUnit;

final class zzbm implements PendingResult.zza {
    private /* synthetic */ PendingResult zzaIl;
    private /* synthetic */ TaskCompletionSource zzaIm;
    private /* synthetic */ zzbp zzaIn;
    private /* synthetic */ zzbq zzaIo;

    zzbm(PendingResult pendingResult, TaskCompletionSource taskCompletionSource, zzbp zzbp, zzbq zzbq) {
        this.zzaIl = pendingResult;
        this.zzaIm = taskCompletionSource;
        this.zzaIn = zzbp;
        this.zzaIo = zzbq;
    }

    public final void zzo(Status status) {
        if (status.isSuccess()) {
            this.zzaIm.setResult(this.zzaIn.zzd(this.zzaIl.await(0, TimeUnit.MILLISECONDS)));
            return;
        }
        this.zzaIm.setException(this.zzaIo.zzy(status));
    }
}
