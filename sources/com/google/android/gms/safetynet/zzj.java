package com.google.android.gms.safetynet;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.internal.zzbgc;
import com.google.android.gms.internal.zzctk;
import com.google.android.gms.internal.zzctz;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzj extends zzbgc<zzctz, Void> {
    zzj(SafetyNetClient safetyNetClient) {
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzctk) ((zzctz) zzb).zzrd()).zza(new zzk(this, taskCompletionSource));
    }
}
