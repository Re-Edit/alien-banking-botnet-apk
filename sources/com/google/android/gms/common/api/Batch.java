package com.google.android.gms.common.api;

import com.google.android.gms.internal.zzbcq;
import java.util.ArrayList;
import java.util.List;

public final class Batch extends zzbcq<BatchResult> {
    /* access modifiers changed from: private */
    public final Object mLock;
    /* access modifiers changed from: private */
    public int zzaAE;
    /* access modifiers changed from: private */
    public boolean zzaAF;
    /* access modifiers changed from: private */
    public boolean zzaAG;
    /* access modifiers changed from: private */
    public final PendingResult<?>[] zzaAH;

    public static final class Builder {
        private List<PendingResult<?>> zzaAJ = new ArrayList();
        private GoogleApiClient zzapw;

        public Builder(GoogleApiClient googleApiClient) {
            this.zzapw = googleApiClient;
        }

        public final <R extends Result> BatchResultToken<R> add(PendingResult<R> pendingResult) {
            BatchResultToken<R> batchResultToken = new BatchResultToken<>(this.zzaAJ.size());
            this.zzaAJ.add(pendingResult);
            return batchResultToken;
        }

        public final Batch build() {
            return new Batch(this.zzaAJ, this.zzapw, (zzb) null);
        }
    }

    private Batch(List<PendingResult<?>> list, GoogleApiClient googleApiClient) {
        super(googleApiClient);
        this.mLock = new Object();
        this.zzaAE = list.size();
        this.zzaAH = new PendingResult[this.zzaAE];
        if (list.isEmpty()) {
            setResult(new BatchResult(Status.zzaBo, this.zzaAH));
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            PendingResult<?> pendingResult = list.get(i);
            this.zzaAH[i] = pendingResult;
            pendingResult.zza(new zzb(this));
        }
    }

    /* synthetic */ Batch(List list, GoogleApiClient googleApiClient, zzb zzb) {
        this(list, googleApiClient);
    }

    static /* synthetic */ int zzb(Batch batch) {
        int i = batch.zzaAE;
        batch.zzaAE = i - 1;
        return i;
    }

    public final void cancel() {
        super.cancel();
        for (PendingResult<?> cancel : this.zzaAH) {
            cancel.cancel();
        }
    }

    /* renamed from: createFailedResult */
    public final BatchResult zzb(Status status) {
        return new BatchResult(status, this.zzaAH);
    }
}
