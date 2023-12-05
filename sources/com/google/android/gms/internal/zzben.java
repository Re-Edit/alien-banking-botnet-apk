package com.google.android.gms.internal;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.util.zzb;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzben implements Handler.Callback {
    public static final Status zzaEe = new Status(4, "Sign-out occurred while this API call was in progress.");
    /* access modifiers changed from: private */
    public static final Status zzaEf = new Status(4, "The user must be signed in to make this API call.");
    private static zzben zzaEh;
    /* access modifiers changed from: private */
    public static final Object zzuI = new Object();
    /* access modifiers changed from: private */
    public final Context mContext;
    /* access modifiers changed from: private */
    public final Handler mHandler;
    /* access modifiers changed from: private */
    public final GoogleApiAvailability zzaBf;
    /* access modifiers changed from: private */
    public final Map<zzbcf<?>, zzbep<?>> zzaCD = new ConcurrentHashMap(5, 0.75f, 1);
    /* access modifiers changed from: private */
    public long zzaDD = 120000;
    /* access modifiers changed from: private */
    public long zzaDE = 5000;
    /* access modifiers changed from: private */
    public long zzaEg = 10000;
    /* access modifiers changed from: private */
    public int zzaEi = -1;
    private final AtomicInteger zzaEj = new AtomicInteger(1);
    private final AtomicInteger zzaEk = new AtomicInteger(0);
    /* access modifiers changed from: private */
    public zzbdi zzaEl = null;
    /* access modifiers changed from: private */
    public final Set<zzbcf<?>> zzaEm = new zzb();
    private final Set<zzbcf<?>> zzaEn = new zzb();

    private zzben(Context context, Looper looper, GoogleApiAvailability googleApiAvailability) {
        this.mContext = context;
        this.mHandler = new Handler(looper, this);
        this.zzaBf = googleApiAvailability;
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(6));
    }

    public static zzben zzay(Context context) {
        zzben zzben;
        synchronized (zzuI) {
            if (zzaEh == null) {
                HandlerThread handlerThread = new HandlerThread("GoogleApiHandler", 9);
                handlerThread.start();
                zzaEh = new zzben(context.getApplicationContext(), handlerThread.getLooper(), GoogleApiAvailability.getInstance());
            }
            zzben = zzaEh;
        }
        return zzben;
    }

    @WorkerThread
    private final void zzc(GoogleApi<?> googleApi) {
        zzbcf<?> zzpf = googleApi.zzpf();
        zzbep zzbep = this.zzaCD.get(zzpf);
        if (zzbep == null) {
            zzbep = new zzbep(this, googleApi);
            this.zzaCD.put(zzpf, zzbep);
        }
        if (zzbep.zzmt()) {
            this.zzaEn.add(zzpf);
        }
        zzbep.connect();
    }

    public static zzben zzqi() {
        zzben zzben;
        synchronized (zzuI) {
            zzbr.zzb(zzaEh, (Object) "Must guarantee manager is non-null before using getInstance");
            zzben = zzaEh;
        }
        return zzben;
    }

    public static void zzqj() {
        synchronized (zzuI) {
            if (zzaEh != null) {
                zzben zzben = zzaEh;
                zzben.zzaEk.incrementAndGet();
                zzben.mHandler.sendMessageAtFrontOfQueue(zzben.mHandler.obtainMessage(10));
            }
        }
    }

    @WorkerThread
    private final void zzql() {
        for (zzbcf<?> remove : this.zzaEn) {
            this.zzaCD.remove(remove).signOut();
        }
        this.zzaEn.clear();
    }

    @WorkerThread
    public final boolean handleMessage(Message message) {
        ConnectionResult zzqs;
        long j = 300000;
        switch (message.what) {
            case 1:
                if (((Boolean) message.obj).booleanValue()) {
                    j = 10000;
                }
                this.zzaEg = j;
                this.mHandler.removeMessages(12);
                for (zzbcf<?> obtainMessage : this.zzaCD.keySet()) {
                    Handler handler = this.mHandler;
                    handler.sendMessageDelayed(handler.obtainMessage(12, obtainMessage), this.zzaEg);
                }
                break;
            case 2:
                zzbch zzbch = (zzbch) message.obj;
                Iterator<zzbcf<?>> it = zzbch.zzpr().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    } else {
                        zzbcf next = it.next();
                        zzbep zzbep = this.zzaCD.get(next);
                        if (zzbep == null) {
                            zzbch.zza(next, new ConnectionResult(13));
                            break;
                        } else {
                            if (zzbep.isConnected()) {
                                zzqs = ConnectionResult.zzazZ;
                            } else if (zzbep.zzqs() != null) {
                                zzqs = zzbep.zzqs();
                            } else {
                                zzbep.zza(zzbch);
                            }
                            zzbch.zza(next, zzqs);
                        }
                    }
                }
            case 3:
                for (zzbep next2 : this.zzaCD.values()) {
                    next2.zzqr();
                    next2.connect();
                }
                break;
            case 4:
            case 8:
            case 13:
                zzbfp zzbfp = (zzbfp) message.obj;
                zzbep zzbep2 = this.zzaCD.get(zzbfp.zzaEV.zzpf());
                if (zzbep2 == null) {
                    zzc(zzbfp.zzaEV);
                    zzbep2 = this.zzaCD.get(zzbfp.zzaEV.zzpf());
                }
                if (zzbep2.zzmt() && this.zzaEk.get() != zzbfp.zzaEU) {
                    zzbfp.zzaET.zzp(zzaEe);
                    zzbep2.signOut();
                    break;
                } else {
                    zzbep2.zza(zzbfp.zzaET);
                    break;
                }
                break;
            case 5:
                int i = message.arg1;
                ConnectionResult connectionResult = (ConnectionResult) message.obj;
                zzbep zzbep3 = null;
                Iterator<zzbep<?>> it2 = this.zzaCD.values().iterator();
                while (true) {
                    if (it2.hasNext()) {
                        zzbep next3 = it2.next();
                        if (next3.getInstanceId() == i) {
                            zzbep3 = next3;
                        }
                    }
                }
                if (zzbep3 == null) {
                    StringBuilder sb = new StringBuilder(76);
                    sb.append("Could not find API instance ");
                    sb.append(i);
                    sb.append(" while trying to fail enqueued calls.");
                    Log.wtf("GoogleApiManager", sb.toString(), new Exception());
                    break;
                } else {
                    String valueOf = String.valueOf(this.zzaBf.getErrorString(connectionResult.getErrorCode()));
                    String valueOf2 = String.valueOf(connectionResult.getErrorMessage());
                    StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 69 + String.valueOf(valueOf2).length());
                    sb2.append("Error resolution was canceled by the user, original error message: ");
                    sb2.append(valueOf);
                    sb2.append(": ");
                    sb2.append(valueOf2);
                    zzbep3.zzt(new Status(17, sb2.toString()));
                    break;
                }
            case 6:
                if (this.mContext.getApplicationContext() instanceof Application) {
                    zzbci.zza((Application) this.mContext.getApplicationContext());
                    zzbci.zzpt().zza((zzbcj) new zzbeo(this));
                    if (!zzbci.zzpt().zzab(true)) {
                        this.zzaEg = 300000;
                        break;
                    }
                }
                break;
            case 7:
                zzc((GoogleApi<?>) (GoogleApi) message.obj);
                break;
            case 9:
                if (this.zzaCD.containsKey(message.obj)) {
                    this.zzaCD.get(message.obj).resume();
                    break;
                }
                break;
            case 10:
                zzql();
                break;
            case 11:
                if (this.zzaCD.containsKey(message.obj)) {
                    this.zzaCD.get(message.obj).zzqb();
                    break;
                }
                break;
            case 12:
                if (this.zzaCD.containsKey(message.obj)) {
                    this.zzaCD.get(message.obj).zzqv();
                    break;
                }
                break;
            default:
                int i2 = message.what;
                StringBuilder sb3 = new StringBuilder(31);
                sb3.append("Unknown message id: ");
                sb3.append(i2);
                Log.w("GoogleApiManager", sb3.toString());
                return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public final PendingIntent zza(zzbcf<?> zzbcf, int i) {
        zzcuw zzqw;
        zzbep zzbep = this.zzaCD.get(zzbcf);
        if (zzbep == null || (zzqw = zzbep.zzqw()) == null) {
            return null;
        }
        return PendingIntent.getActivity(this.mContext, i, zzqw.zzmF(), 134217728);
    }

    public final <O extends Api.ApiOptions> Task<Void> zza(@NonNull GoogleApi<O> googleApi, @NonNull zzbfk<?> zzbfk) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        zzbcd zzbcd = new zzbcd(zzbfk, taskCompletionSource);
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(13, new zzbfp(zzbcd, this.zzaEk.get(), googleApi)));
        return taskCompletionSource.getTask();
    }

    public final <O extends Api.ApiOptions> Task<Void> zza(@NonNull GoogleApi<O> googleApi, @NonNull zzbfq<Api.zzb, ?> zzbfq, @NonNull zzbgk<Api.zzb, ?> zzbgk) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        zzbcb zzbcb = new zzbcb(new zzbfr(zzbfq, zzbgk), taskCompletionSource);
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(8, new zzbfp(zzbcb, this.zzaEk.get(), googleApi)));
        return taskCompletionSource.getTask();
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0038 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:3:0x000f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.tasks.Task<java.lang.Void> zza(java.lang.Iterable<? extends com.google.android.gms.common.api.GoogleApi<?>> r4) {
        /*
            r3 = this;
            com.google.android.gms.internal.zzbch r0 = new com.google.android.gms.internal.zzbch
            r0.<init>(r4)
            java.util.Iterator r4 = r4.iterator()
        L_0x0009:
            boolean r1 = r4.hasNext()
            if (r1 == 0) goto L_0x0038
            java.lang.Object r1 = r4.next()
            com.google.android.gms.common.api.GoogleApi r1 = (com.google.android.gms.common.api.GoogleApi) r1
            java.util.Map<com.google.android.gms.internal.zzbcf<?>, com.google.android.gms.internal.zzbep<?>> r2 = r3.zzaCD
            com.google.android.gms.internal.zzbcf r1 = r1.zzpf()
            java.lang.Object r1 = r2.get(r1)
            com.google.android.gms.internal.zzbep r1 = (com.google.android.gms.internal.zzbep) r1
            if (r1 == 0) goto L_0x0029
            boolean r1 = r1.isConnected()
            if (r1 != 0) goto L_0x0009
        L_0x0029:
            android.os.Handler r4 = r3.mHandler
            r1 = 2
            android.os.Message r1 = r4.obtainMessage(r1, r0)
            r4.sendMessage(r1)
        L_0x0033:
            com.google.android.gms.tasks.Task r4 = r0.getTask()
            return r4
        L_0x0038:
            r0.zzps()
            goto L_0x0033
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzben.zza(java.lang.Iterable):com.google.android.gms.tasks.Task");
    }

    public final void zza(ConnectionResult connectionResult, int i) {
        if (!zzc(connectionResult, i)) {
            Handler handler = this.mHandler;
            handler.sendMessage(handler.obtainMessage(5, i, 0, connectionResult));
        }
    }

    public final <O extends Api.ApiOptions> void zza(GoogleApi<O> googleApi, int i, zzbck<? extends Result, Api.zzb> zzbck) {
        zzbca zzbca = new zzbca(i, zzbck);
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(4, new zzbfp(zzbca, this.zzaEk.get(), googleApi)));
    }

    public final <O extends Api.ApiOptions, TResult> void zza(GoogleApi<O> googleApi, int i, zzbgc<Api.zzb, TResult> zzbgc, TaskCompletionSource<TResult> taskCompletionSource, zzbfy zzbfy) {
        zzbcc zzbcc = new zzbcc(i, zzbgc, taskCompletionSource, zzbfy);
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(4, new zzbfp(zzbcc, this.zzaEk.get(), googleApi)));
    }

    public final void zza(@NonNull zzbdi zzbdi) {
        synchronized (zzuI) {
            if (this.zzaEl != zzbdi) {
                this.zzaEl = zzbdi;
                this.zzaEm.clear();
                this.zzaEm.addAll(zzbdi.zzpP());
            }
        }
    }

    public final void zzb(GoogleApi<?> googleApi) {
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(7, googleApi));
    }

    /* access modifiers changed from: package-private */
    public final void zzb(@NonNull zzbdi zzbdi) {
        synchronized (zzuI) {
            if (this.zzaEl == zzbdi) {
                this.zzaEl = null;
                this.zzaEm.clear();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final boolean zzc(ConnectionResult connectionResult, int i) {
        return this.zzaBf.zza(this.mContext, connectionResult, i);
    }

    /* access modifiers changed from: package-private */
    public final void zzpj() {
        this.zzaEk.incrementAndGet();
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(10));
    }

    public final void zzpq() {
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(3));
    }

    public final int zzqk() {
        return this.zzaEj.getAndIncrement();
    }
}
