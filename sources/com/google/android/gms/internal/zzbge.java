package com.google.android.gms.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.zzbr;
import java.lang.ref.WeakReference;

public final class zzbge<R extends Result> extends TransformedResult<R> implements ResultCallback<R> {
    /* access modifiers changed from: private */
    public final Object zzaBY = new Object();
    /* access modifiers changed from: private */
    public final WeakReference<GoogleApiClient> zzaCa;
    /* access modifiers changed from: private */
    public ResultTransform<? super R, ? extends Result> zzaFc = null;
    /* access modifiers changed from: private */
    public zzbge<? extends Result> zzaFd = null;
    private volatile ResultCallbacks<? super R> zzaFe = null;
    private PendingResult<R> zzaFf = null;
    private Status zzaFg = null;
    /* access modifiers changed from: private */
    public final zzbgg zzaFh;
    private boolean zzaFi = false;

    public zzbge(WeakReference<GoogleApiClient> weakReference) {
        zzbr.zzb(weakReference, (Object) "GoogleApiClient reference must not be null");
        this.zzaCa = weakReference;
        GoogleApiClient googleApiClient = (GoogleApiClient) this.zzaCa.get();
        this.zzaFh = new zzbgg(this, googleApiClient != null ? googleApiClient.getLooper() : Looper.getMainLooper());
    }

    /* access modifiers changed from: private */
    public static void zzc(Result result) {
        if (result instanceof Releasable) {
            try {
                ((Releasable) result).release();
            } catch (RuntimeException e) {
                String valueOf = String.valueOf(result);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 18);
                sb.append("Unable to release ");
                sb.append(valueOf);
                Log.w("TransformedResultImpl", sb.toString(), e);
            }
        }
    }

    private final void zzqH() {
        if (this.zzaFc != null || this.zzaFe != null) {
            GoogleApiClient googleApiClient = (GoogleApiClient) this.zzaCa.get();
            if (!(this.zzaFi || this.zzaFc == null || googleApiClient == null)) {
                googleApiClient.zza(this);
                this.zzaFi = true;
            }
            Status status = this.zzaFg;
            if (status != null) {
                zzw(status);
                return;
            }
            PendingResult<R> pendingResult = this.zzaFf;
            if (pendingResult != null) {
                pendingResult.setResultCallback(this);
            }
        }
    }

    private final boolean zzqJ() {
        return (this.zzaFe == null || ((GoogleApiClient) this.zzaCa.get()) == null) ? false : true;
    }

    /* access modifiers changed from: private */
    public final void zzv(Status status) {
        synchronized (this.zzaBY) {
            this.zzaFg = status;
            zzw(this.zzaFg);
        }
    }

    private final void zzw(Status status) {
        synchronized (this.zzaBY) {
            if (this.zzaFc != null) {
                Status onFailure = this.zzaFc.onFailure(status);
                zzbr.zzb(onFailure, (Object) "onFailure must not return null");
                this.zzaFd.zzv(onFailure);
            } else if (zzqJ()) {
                this.zzaFe.onFailure(status);
            }
        }
    }

    public final void andFinally(@NonNull ResultCallbacks<? super R> resultCallbacks) {
        synchronized (this.zzaBY) {
            boolean z = true;
            zzbr.zza(this.zzaFe == null, (Object) "Cannot call andFinally() twice.");
            if (this.zzaFc != null) {
                z = false;
            }
            zzbr.zza(z, (Object) "Cannot call then() and andFinally() on the same TransformedResult.");
            this.zzaFe = resultCallbacks;
            zzqH();
        }
    }

    public final void onResult(R r) {
        synchronized (this.zzaBY) {
            if (!r.getStatus().isSuccess()) {
                zzv(r.getStatus());
                zzc((Result) r);
            } else if (this.zzaFc != null) {
                zzbfs.zzqh().submit(new zzbgf(this, r));
            } else if (zzqJ()) {
                this.zzaFe.onSuccess(r);
            }
        }
    }

    @NonNull
    public final <S extends Result> TransformedResult<S> then(@NonNull ResultTransform<? super R, ? extends S> resultTransform) {
        zzbge<? extends Result> zzbge;
        synchronized (this.zzaBY) {
            boolean z = true;
            zzbr.zza(this.zzaFc == null, (Object) "Cannot call then() twice.");
            if (this.zzaFe != null) {
                z = false;
            }
            zzbr.zza(z, (Object) "Cannot call then() and andFinally() on the same TransformedResult.");
            this.zzaFc = resultTransform;
            zzbge = new zzbge<>(this.zzaCa);
            this.zzaFd = zzbge;
            zzqH();
        }
        return zzbge;
    }

    public final void zza(PendingResult<?> pendingResult) {
        synchronized (this.zzaBY) {
            this.zzaFf = pendingResult;
            zzqH();
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzqI() {
        this.zzaFe = null;
    }
}
