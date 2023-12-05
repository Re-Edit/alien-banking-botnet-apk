package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzbr;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class Tasks {

    static final class zza implements zzb {
        private final CountDownLatch zztM;

        private zza() {
            this.zztM = new CountDownLatch(1);
        }

        /* synthetic */ zza(zzo zzo) {
            this();
        }

        public final void await() throws InterruptedException {
            this.zztM.await();
        }

        public final boolean await(long j, TimeUnit timeUnit) throws InterruptedException {
            return this.zztM.await(j, timeUnit);
        }

        public final void onFailure(@NonNull Exception exc) {
            this.zztM.countDown();
        }

        public final void onSuccess(Object obj) {
            this.zztM.countDown();
        }
    }

    interface zzb extends OnFailureListener, OnSuccessListener<Object> {
    }

    static final class zzc implements zzb {
        private final Object mLock = new Object();
        private final zzn<Void> zzbMg;
        private Exception zzbMl;
        private final int zzbMn;
        private int zzbMo;
        private int zzbMp;

        public zzc(int i, zzn<Void> zzn) {
            this.zzbMn = i;
            this.zzbMg = zzn;
        }

        private final void zzDG() {
            int i = this.zzbMo;
            int i2 = this.zzbMp;
            int i3 = i + i2;
            int i4 = this.zzbMn;
            if (i3 != i4) {
                return;
            }
            if (this.zzbMl == null) {
                this.zzbMg.setResult(null);
                return;
            }
            zzn<Void> zzn = this.zzbMg;
            StringBuilder sb = new StringBuilder(54);
            sb.append(i2);
            sb.append(" out of ");
            sb.append(i4);
            sb.append(" underlying tasks failed");
            zzn.setException(new ExecutionException(sb.toString(), this.zzbMl));
        }

        public final void onFailure(@NonNull Exception exc) {
            synchronized (this.mLock) {
                this.zzbMp++;
                this.zzbMl = exc;
                zzDG();
            }
        }

        public final void onSuccess(Object obj) {
            synchronized (this.mLock) {
                this.zzbMo++;
                zzDG();
            }
        }
    }

    private Tasks() {
    }

    public static <TResult> TResult await(@NonNull Task<TResult> task) throws ExecutionException, InterruptedException {
        zzbr.zzcG("Must not be called on the main application thread");
        zzbr.zzb(task, (Object) "Task must not be null");
        if (task.isComplete()) {
            return zzb(task);
        }
        zza zza2 = new zza((zzo) null);
        zza(task, zza2);
        zza2.await();
        return zzb(task);
    }

    public static <TResult> TResult await(@NonNull Task<TResult> task, long j, @NonNull TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        zzbr.zzcG("Must not be called on the main application thread");
        zzbr.zzb(task, (Object) "Task must not be null");
        zzbr.zzb(timeUnit, (Object) "TimeUnit must not be null");
        if (task.isComplete()) {
            return zzb(task);
        }
        zza zza2 = new zza((zzo) null);
        zza(task, zza2);
        if (zza2.await(j, timeUnit)) {
            return zzb(task);
        }
        throw new TimeoutException("Timed out waiting for Task");
    }

    public static <TResult> Task<TResult> call(@NonNull Callable<TResult> callable) {
        return call(TaskExecutors.MAIN_THREAD, callable);
    }

    public static <TResult> Task<TResult> call(@NonNull Executor executor, @NonNull Callable<TResult> callable) {
        zzbr.zzb(executor, (Object) "Executor must not be null");
        zzbr.zzb(callable, (Object) "Callback must not be null");
        zzn zzn = new zzn();
        executor.execute(new zzo(zzn, callable));
        return zzn;
    }

    public static <TResult> Task<TResult> forException(@NonNull Exception exc) {
        zzn zzn = new zzn();
        zzn.setException(exc);
        return zzn;
    }

    public static <TResult> Task<TResult> forResult(TResult tresult) {
        zzn zzn = new zzn();
        zzn.setResult(tresult);
        return zzn;
    }

    public static Task<Void> whenAll(Collection<? extends Task<?>> collection) {
        if (collection.isEmpty()) {
            return forResult((Object) null);
        }
        for (Task task : collection) {
            if (task == null) {
                throw new NullPointerException("null tasks are not accepted");
            }
        }
        zzn zzn = new zzn();
        zzc zzc2 = new zzc(collection.size(), zzn);
        for (Task zza2 : collection) {
            zza(zza2, zzc2);
        }
        return zzn;
    }

    public static Task<Void> whenAll(Task<?>... taskArr) {
        return taskArr.length == 0 ? forResult((Object) null) : whenAll((Collection<? extends Task<?>>) Arrays.asList(taskArr));
    }

    private static void zza(Task<?> task, zzb zzb2) {
        task.addOnSuccessListener(TaskExecutors.zzbMh, (OnSuccessListener<? super Object>) zzb2);
        task.addOnFailureListener(TaskExecutors.zzbMh, (OnFailureListener) zzb2);
    }

    private static <TResult> TResult zzb(Task<TResult> task) throws ExecutionException {
        if (task.isSuccessful()) {
            return task.getResult();
        }
        throw new ExecutionException(task.getException());
    }
}
