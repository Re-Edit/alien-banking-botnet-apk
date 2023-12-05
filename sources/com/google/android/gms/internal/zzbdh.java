package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzbdh implements OnCompleteListener<TResult> {
    private /* synthetic */ zzbdf zzaCW;
    private /* synthetic */ TaskCompletionSource zzaCX;

    zzbdh(zzbdf zzbdf, TaskCompletionSource taskCompletionSource) {
        this.zzaCW = zzbdf;
        this.zzaCX = taskCompletionSource;
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        this.zzaCW.zzaCU.remove(this.zzaCX);
    }
}
