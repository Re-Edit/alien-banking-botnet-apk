package com.google.android.gms.internal;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public final class zzbdf {
    /* access modifiers changed from: private */
    public final Map<zzbcq<?>, Boolean> zzaCT = Collections.synchronizedMap(new WeakHashMap());
    /* access modifiers changed from: private */
    public final Map<TaskCompletionSource<?>, Boolean> zzaCU = Collections.synchronizedMap(new WeakHashMap());

    private final void zza(boolean z, Status status) {
        HashMap hashMap;
        HashMap hashMap2;
        synchronized (this.zzaCT) {
            hashMap = new HashMap(this.zzaCT);
        }
        synchronized (this.zzaCU) {
            hashMap2 = new HashMap(this.zzaCU);
        }
        for (Map.Entry entry : hashMap.entrySet()) {
            if (z || ((Boolean) entry.getValue()).booleanValue()) {
                ((zzbcq) entry.getKey()).zzs(status);
            }
        }
        for (Map.Entry entry2 : hashMap2.entrySet()) {
            if (z || ((Boolean) entry2.getValue()).booleanValue()) {
                ((TaskCompletionSource) entry2.getKey()).trySetException(new ApiException(status));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzbcq<? extends Result> zzbcq, boolean z) {
        this.zzaCT.put(zzbcq, Boolean.valueOf(z));
        zzbcq.zza((PendingResult.zza) new zzbdg(this, zzbcq));
    }

    /* access modifiers changed from: package-private */
    public final <TResult> void zza(TaskCompletionSource<TResult> taskCompletionSource, boolean z) {
        this.zzaCU.put(taskCompletionSource, Boolean.valueOf(z));
        taskCompletionSource.getTask().addOnCompleteListener(new zzbdh(this, taskCompletionSource));
    }

    /* access modifiers changed from: package-private */
    public final boolean zzpM() {
        return !this.zzaCT.isEmpty() || !this.zzaCU.isEmpty();
    }

    public final void zzpN() {
        zza(false, zzben.zzaEe);
    }

    public final void zzpO() {
        zza(true, zzbgh.zzaFl);
    }
}
