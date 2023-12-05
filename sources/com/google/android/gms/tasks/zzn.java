package com.google.android.gms.tasks;

import android.app.Activity;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.internal.zzbfe;
import com.google.android.gms.internal.zzbff;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

final class zzn<TResult> extends Task<TResult> {
    private final Object mLock = new Object();
    private final zzl<TResult> zzbMi = new zzl<>();
    private boolean zzbMj;
    private TResult zzbMk;
    private Exception zzbMl;

    static class zza extends zzbfe {
        private final List<WeakReference<zzk<?>>> mListeners = new ArrayList();

        private zza(zzbff zzbff) {
            super(zzbff);
            this.zzaEI.zza("TaskOnStopCallback", (zzbfe) this);
        }

        public static zza zzr(Activity activity) {
            zzbff zzn = zzn(activity);
            zza zza = (zza) zzn.zza("TaskOnStopCallback", zza.class);
            return zza == null ? new zza(zzn) : zza;
        }

        @MainThread
        public final void onStop() {
            synchronized (this.mListeners) {
                for (WeakReference<zzk<?>> weakReference : this.mListeners) {
                    zzk zzk = (zzk) weakReference.get();
                    if (zzk != null) {
                        zzk.cancel();
                    }
                }
                this.mListeners.clear();
            }
        }

        public final <T> void zzb(zzk<T> zzk) {
            synchronized (this.mListeners) {
                this.mListeners.add(new WeakReference(zzk));
            }
        }
    }

    zzn() {
    }

    private final void zzDD() {
        zzbr.zza(this.zzbMj, (Object) "Task is not yet complete");
    }

    private final void zzDE() {
        zzbr.zza(!this.zzbMj, (Object) "Task is already complete");
    }

    private final void zzDF() {
        synchronized (this.mLock) {
            if (this.zzbMj) {
                this.zzbMi.zza(this);
            }
        }
    }

    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull Activity activity, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        zze zze = new zze(TaskExecutors.MAIN_THREAD, onCompleteListener);
        this.zzbMi.zza(zze);
        zza.zzr(activity).zzb(zze);
        zzDF();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull OnCompleteListener<TResult> onCompleteListener) {
        return addOnCompleteListener(TaskExecutors.MAIN_THREAD, onCompleteListener);
    }

    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull Executor executor, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        this.zzbMi.zza(new zze(executor, onCompleteListener));
        zzDF();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
        zzg zzg = new zzg(TaskExecutors.MAIN_THREAD, onFailureListener);
        this.zzbMi.zza(zzg);
        zza.zzr(activity).zzb(zzg);
        zzDF();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
        return addOnFailureListener(TaskExecutors.MAIN_THREAD, onFailureListener);
    }

    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        this.zzbMi.zza(new zzg(executor, onFailureListener));
        zzDF();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        zzi zzi = new zzi(TaskExecutors.MAIN_THREAD, onSuccessListener);
        this.zzbMi.zza(zzi);
        zza.zzr(activity).zzb(zzi);
        zzDF();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        return addOnSuccessListener(TaskExecutors.MAIN_THREAD, onSuccessListener);
    }

    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        this.zzbMi.zza(new zzi(executor, onSuccessListener));
        zzDF();
        return this;
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Continuation<TResult, TContinuationResult> continuation) {
        return continueWith(TaskExecutors.MAIN_THREAD, continuation);
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation) {
        zzn zzn = new zzn();
        this.zzbMi.zza(new zza(executor, continuation, zzn));
        zzDF();
        return zzn;
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        return continueWithTask(TaskExecutors.MAIN_THREAD, continuation);
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Executor executor, @NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        zzn zzn = new zzn();
        this.zzbMi.zza(new zzc(executor, continuation, zzn));
        zzDF();
        return zzn;
    }

    @Nullable
    public final Exception getException() {
        Exception exc;
        synchronized (this.mLock) {
            exc = this.zzbMl;
        }
        return exc;
    }

    public final TResult getResult() {
        TResult tresult;
        synchronized (this.mLock) {
            zzDD();
            if (this.zzbMl == null) {
                tresult = this.zzbMk;
            } else {
                throw new RuntimeExecutionException(this.zzbMl);
            }
        }
        return tresult;
    }

    public final <X extends Throwable> TResult getResult(@NonNull Class<X> cls) throws Throwable {
        TResult tresult;
        synchronized (this.mLock) {
            zzDD();
            if (cls.isInstance(this.zzbMl)) {
                throw ((Throwable) cls.cast(this.zzbMl));
            } else if (this.zzbMl == null) {
                tresult = this.zzbMk;
            } else {
                throw new RuntimeExecutionException(this.zzbMl);
            }
        }
        return tresult;
    }

    public final boolean isComplete() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzbMj;
        }
        return z;
    }

    public final boolean isSuccessful() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzbMj && this.zzbMl == null;
        }
        return z;
    }

    public final void setException(@NonNull Exception exc) {
        zzbr.zzb(exc, (Object) "Exception must not be null");
        synchronized (this.mLock) {
            zzDE();
            this.zzbMj = true;
            this.zzbMl = exc;
        }
        this.zzbMi.zza(this);
    }

    public final void setResult(TResult tresult) {
        synchronized (this.mLock) {
            zzDE();
            this.zzbMj = true;
            this.zzbMk = tresult;
        }
        this.zzbMi.zza(this);
    }

    public final boolean trySetException(@NonNull Exception exc) {
        zzbr.zzb(exc, (Object) "Exception must not be null");
        synchronized (this.mLock) {
            if (this.zzbMj) {
                return false;
            }
            this.zzbMj = true;
            this.zzbMl = exc;
            this.zzbMi.zza(this);
            return true;
        }
    }

    public final boolean trySetResult(TResult tresult) {
        synchronized (this.mLock) {
            if (this.zzbMj) {
                return false;
            }
            this.zzbMj = true;
            this.zzbMk = tresult;
            this.zzbMi.zza(this);
            return true;
        }
    }
}
