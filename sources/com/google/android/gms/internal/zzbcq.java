package com.google.android.gms.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.zzap;
import com.google.android.gms.common.internal.zzbr;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public abstract class zzbcq<R extends Result> extends PendingResult<R> {
    static final ThreadLocal<Boolean> zzaBX = new zzbcr();
    private Status mStatus;
    private boolean zzJ;
    private final Object zzaBY;
    private zzbcs<R> zzaBZ;
    /* access modifiers changed from: private */
    public R zzaBl;
    private WeakReference<GoogleApiClient> zzaCa;
    private final ArrayList<PendingResult.zza> zzaCb;
    private ResultCallback<? super R> zzaCc;
    private final AtomicReference<zzbgj> zzaCd;
    private zzbct zzaCe;
    private volatile boolean zzaCf;
    private boolean zzaCg;
    private zzap zzaCh;
    private volatile zzbge<R> zzaCi;
    private boolean zzaCj;
    private final CountDownLatch zztM;

    @Deprecated
    zzbcq() {
        this.zzaBY = new Object();
        this.zztM = new CountDownLatch(1);
        this.zzaCb = new ArrayList<>();
        this.zzaCd = new AtomicReference<>();
        this.zzaCj = false;
        this.zzaBZ = new zzbcs<>(Looper.getMainLooper());
        this.zzaCa = new WeakReference<>((Object) null);
    }

    @Deprecated
    protected zzbcq(Looper looper) {
        this.zzaBY = new Object();
        this.zztM = new CountDownLatch(1);
        this.zzaCb = new ArrayList<>();
        this.zzaCd = new AtomicReference<>();
        this.zzaCj = false;
        this.zzaBZ = new zzbcs<>(looper);
        this.zzaCa = new WeakReference<>((Object) null);
    }

    protected zzbcq(GoogleApiClient googleApiClient) {
        this.zzaBY = new Object();
        this.zztM = new CountDownLatch(1);
        this.zzaCb = new ArrayList<>();
        this.zzaCd = new AtomicReference<>();
        this.zzaCj = false;
        this.zzaBZ = new zzbcs<>(googleApiClient != null ? googleApiClient.getLooper() : Looper.getMainLooper());
        this.zzaCa = new WeakReference<>(googleApiClient);
    }

    private final R get() {
        R r;
        synchronized (this.zzaBY) {
            zzbr.zza(!this.zzaCf, (Object) "Result has already been consumed.");
            zzbr.zza(isReady(), (Object) "Result is not ready.");
            r = this.zzaBl;
            this.zzaBl = null;
            this.zzaCc = null;
            this.zzaCf = true;
        }
        zzbgj andSet = this.zzaCd.getAndSet((Object) null);
        if (andSet != null) {
            andSet.zzc(this);
        }
        return r;
    }

    private final void zzb(R r) {
        this.zzaBl = r;
        this.zzaCh = null;
        this.zztM.countDown();
        this.mStatus = this.zzaBl.getStatus();
        if (this.zzJ) {
            this.zzaCc = null;
        } else if (this.zzaCc != null) {
            this.zzaBZ.removeMessages(2);
            this.zzaBZ.zza(this.zzaCc, get());
        } else if (this.zzaBl instanceof Releasable) {
            this.zzaCe = new zzbct(this, (zzbcr) null);
        }
        ArrayList arrayList = this.zzaCb;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((PendingResult.zza) obj).zzo(this.mStatus);
        }
        this.zzaCb.clear();
    }

    public static void zzc(Result result) {
        if (result instanceof Releasable) {
            try {
                ((Releasable) result).release();
            } catch (RuntimeException e) {
                String valueOf = String.valueOf(result);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 18);
                sb.append("Unable to release ");
                sb.append(valueOf);
                Log.w("BasePendingResult", sb.toString(), e);
            }
        }
    }

    public final R await() {
        boolean z = false;
        zzbr.zza(Looper.myLooper() != Looper.getMainLooper(), (Object) "await must not be called on the UI thread");
        zzbr.zza(!this.zzaCf, (Object) "Result has already been consumed");
        if (this.zzaCi == null) {
            z = true;
        }
        zzbr.zza(z, (Object) "Cannot await if then() has been called.");
        try {
            this.zztM.await();
        } catch (InterruptedException unused) {
            zzs(Status.zzaBp);
        }
        zzbr.zza(isReady(), (Object) "Result is not ready.");
        return get();
    }

    public final R await(long j, TimeUnit timeUnit) {
        boolean z = false;
        zzbr.zza(j <= 0 || Looper.myLooper() != Looper.getMainLooper(), (Object) "await must not be called on the UI thread when time is greater than zero.");
        zzbr.zza(!this.zzaCf, (Object) "Result has already been consumed.");
        if (this.zzaCi == null) {
            z = true;
        }
        zzbr.zza(z, (Object) "Cannot await if then() has been called.");
        try {
            if (!this.zztM.await(j, timeUnit)) {
                zzs(Status.zzaBr);
            }
        } catch (InterruptedException unused) {
            zzs(Status.zzaBp);
        }
        zzbr.zza(isReady(), (Object) "Result is not ready.");
        return get();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:8|(2:10|11)|12|13|14|15) */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0029, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x0015 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void cancel() {
        /*
            r2 = this;
            java.lang.Object r0 = r2.zzaBY
            monitor-enter(r0)
            boolean r1 = r2.zzJ     // Catch:{ all -> 0x002a }
            if (r1 != 0) goto L_0x0028
            boolean r1 = r2.zzaCf     // Catch:{ all -> 0x002a }
            if (r1 == 0) goto L_0x000c
            goto L_0x0028
        L_0x000c:
            com.google.android.gms.common.internal.zzap r1 = r2.zzaCh     // Catch:{ all -> 0x002a }
            if (r1 == 0) goto L_0x0015
            com.google.android.gms.common.internal.zzap r1 = r2.zzaCh     // Catch:{ RemoteException -> 0x0015 }
            r1.cancel()     // Catch:{ RemoteException -> 0x0015 }
        L_0x0015:
            R r1 = r2.zzaBl     // Catch:{ all -> 0x002a }
            zzc(r1)     // Catch:{ all -> 0x002a }
            r1 = 1
            r2.zzJ = r1     // Catch:{ all -> 0x002a }
            com.google.android.gms.common.api.Status r1 = com.google.android.gms.common.api.Status.zzaBs     // Catch:{ all -> 0x002a }
            com.google.android.gms.common.api.Result r1 = r2.zzb((com.google.android.gms.common.api.Status) r1)     // Catch:{ all -> 0x002a }
            r2.zzb(r1)     // Catch:{ all -> 0x002a }
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            return
        L_0x0028:
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            return
        L_0x002a:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbcq.cancel():void");
    }

    public boolean isCanceled() {
        boolean z;
        synchronized (this.zzaBY) {
            z = this.zzJ;
        }
        return z;
    }

    public final boolean isReady() {
        return this.zztM.getCount() == 0;
    }

    public final void setResult(R r) {
        synchronized (this.zzaBY) {
            if (this.zzaCg || this.zzJ) {
                zzc(r);
                return;
            }
            isReady();
            boolean z = true;
            zzbr.zza(!isReady(), (Object) "Results have already been set");
            if (this.zzaCf) {
                z = false;
            }
            zzbr.zza(z, (Object) "Result has already been consumed");
            zzb(r);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x003e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setResultCallback(com.google.android.gms.common.api.ResultCallback<? super R> r6) {
        /*
            r5 = this;
            java.lang.Object r0 = r5.zzaBY
            monitor-enter(r0)
            if (r6 != 0) goto L_0x000a
            r6 = 0
            r5.zzaCc = r6     // Catch:{ all -> 0x003f }
            monitor-exit(r0)     // Catch:{ all -> 0x003f }
            return
        L_0x000a:
            boolean r1 = r5.zzaCf     // Catch:{ all -> 0x003f }
            r2 = 1
            r3 = 0
            if (r1 != 0) goto L_0x0012
            r1 = 1
            goto L_0x0013
        L_0x0012:
            r1 = 0
        L_0x0013:
            java.lang.String r4 = "Result has already been consumed."
            com.google.android.gms.common.internal.zzbr.zza((boolean) r1, (java.lang.Object) r4)     // Catch:{ all -> 0x003f }
            com.google.android.gms.internal.zzbge<R> r1 = r5.zzaCi     // Catch:{ all -> 0x003f }
            if (r1 != 0) goto L_0x001d
            goto L_0x001e
        L_0x001d:
            r2 = 0
        L_0x001e:
            java.lang.String r1 = "Cannot set callbacks if then() has been called."
            com.google.android.gms.common.internal.zzbr.zza((boolean) r2, (java.lang.Object) r1)     // Catch:{ all -> 0x003f }
            boolean r1 = r5.isCanceled()     // Catch:{ all -> 0x003f }
            if (r1 == 0) goto L_0x002b
            monitor-exit(r0)     // Catch:{ all -> 0x003f }
            return
        L_0x002b:
            boolean r1 = r5.isReady()     // Catch:{ all -> 0x003f }
            if (r1 == 0) goto L_0x003b
            com.google.android.gms.internal.zzbcs<R> r1 = r5.zzaBZ     // Catch:{ all -> 0x003f }
            com.google.android.gms.common.api.Result r2 = r5.get()     // Catch:{ all -> 0x003f }
            r1.zza(r6, r2)     // Catch:{ all -> 0x003f }
            goto L_0x003d
        L_0x003b:
            r5.zzaCc = r6     // Catch:{ all -> 0x003f }
        L_0x003d:
            monitor-exit(r0)     // Catch:{ all -> 0x003f }
            return
        L_0x003f:
            r6 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x003f }
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbcq.setResultCallback(com.google.android.gms.common.api.ResultCallback):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setResultCallback(com.google.android.gms.common.api.ResultCallback<? super R> r6, long r7, java.util.concurrent.TimeUnit r9) {
        /*
            r5 = this;
            java.lang.Object r0 = r5.zzaBY
            monitor-enter(r0)
            if (r6 != 0) goto L_0x000a
            r6 = 0
            r5.zzaCc = r6     // Catch:{ all -> 0x004d }
            monitor-exit(r0)     // Catch:{ all -> 0x004d }
            return
        L_0x000a:
            boolean r1 = r5.zzaCf     // Catch:{ all -> 0x004d }
            r2 = 1
            r3 = 0
            if (r1 != 0) goto L_0x0012
            r1 = 1
            goto L_0x0013
        L_0x0012:
            r1 = 0
        L_0x0013:
            java.lang.String r4 = "Result has already been consumed."
            com.google.android.gms.common.internal.zzbr.zza((boolean) r1, (java.lang.Object) r4)     // Catch:{ all -> 0x004d }
            com.google.android.gms.internal.zzbge<R> r1 = r5.zzaCi     // Catch:{ all -> 0x004d }
            if (r1 != 0) goto L_0x001d
            goto L_0x001e
        L_0x001d:
            r2 = 0
        L_0x001e:
            java.lang.String r1 = "Cannot set callbacks if then() has been called."
            com.google.android.gms.common.internal.zzbr.zza((boolean) r2, (java.lang.Object) r1)     // Catch:{ all -> 0x004d }
            boolean r1 = r5.isCanceled()     // Catch:{ all -> 0x004d }
            if (r1 == 0) goto L_0x002b
            monitor-exit(r0)     // Catch:{ all -> 0x004d }
            return
        L_0x002b:
            boolean r1 = r5.isReady()     // Catch:{ all -> 0x004d }
            if (r1 == 0) goto L_0x003b
            com.google.android.gms.internal.zzbcs<R> r7 = r5.zzaBZ     // Catch:{ all -> 0x004d }
            com.google.android.gms.common.api.Result r8 = r5.get()     // Catch:{ all -> 0x004d }
            r7.zza(r6, r8)     // Catch:{ all -> 0x004d }
            goto L_0x004b
        L_0x003b:
            r5.zzaCc = r6     // Catch:{ all -> 0x004d }
            com.google.android.gms.internal.zzbcs<R> r6 = r5.zzaBZ     // Catch:{ all -> 0x004d }
            long r7 = r9.toMillis(r7)     // Catch:{ all -> 0x004d }
            r9 = 2
            android.os.Message r9 = r6.obtainMessage(r9, r5)     // Catch:{ all -> 0x004d }
            r6.sendMessageDelayed(r9, r7)     // Catch:{ all -> 0x004d }
        L_0x004b:
            monitor-exit(r0)     // Catch:{ all -> 0x004d }
            return
        L_0x004d:
            r6 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x004d }
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbcq.setResultCallback(com.google.android.gms.common.api.ResultCallback, long, java.util.concurrent.TimeUnit):void");
    }

    public <S extends Result> TransformedResult<S> then(ResultTransform<? super R, ? extends S> resultTransform) {
        TransformedResult<S> then;
        zzbr.zza(!this.zzaCf, (Object) "Result has already been consumed.");
        synchronized (this.zzaBY) {
            boolean z = false;
            zzbr.zza(this.zzaCi == null, (Object) "Cannot call then() twice.");
            zzbr.zza(this.zzaCc == null, (Object) "Cannot call then() if callbacks are set.");
            if (!this.zzJ) {
                z = true;
            }
            zzbr.zza(z, (Object) "Cannot call then() if result was canceled.");
            this.zzaCj = true;
            this.zzaCi = new zzbge<>(this.zzaCa);
            then = this.zzaCi.then(resultTransform);
            if (isReady()) {
                this.zzaBZ.zza(this.zzaCi, get());
            } else {
                this.zzaCc = this.zzaCi;
            }
        }
        return then;
    }

    public final void zza(PendingResult.zza zza) {
        zzbr.zzb(zza != null, (Object) "Callback cannot be null.");
        synchronized (this.zzaBY) {
            if (isReady()) {
                zza.zzo(this.mStatus);
            } else {
                this.zzaCb.add(zza);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void zza(zzap zzap) {
        synchronized (this.zzaBY) {
            this.zzaCh = zzap;
        }
    }

    public final void zza(zzbgj zzbgj) {
        this.zzaCd.set(zzbgj);
    }

    /* access modifiers changed from: protected */
    @NonNull
    public abstract R zzb(Status status);

    public final void zzpA() {
        this.zzaCj = this.zzaCj || zzaBX.get().booleanValue();
    }

    public final Integer zzpm() {
        return null;
    }

    public final boolean zzpz() {
        boolean isCanceled;
        synchronized (this.zzaBY) {
            if (((GoogleApiClient) this.zzaCa.get()) == null || !this.zzaCj) {
                cancel();
            }
            isCanceled = isCanceled();
        }
        return isCanceled;
    }

    public final void zzs(Status status) {
        synchronized (this.zzaBY) {
            if (!isReady()) {
                setResult(zzb(status));
                this.zzaCg = true;
            }
        }
    }
}
