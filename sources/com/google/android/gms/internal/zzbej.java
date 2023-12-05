package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.zze;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public final class zzbej implements zzbcv, zzbfb {
    private final Context mContext;
    private Api.zza<? extends zzcuw, zzcux> zzaBg;
    private zzq zzaCC;
    private Map<Api<?>, Boolean> zzaCF;
    private final zze zzaCH;
    final zzbeb zzaCn;
    /* access modifiers changed from: private */
    public final Lock zzaCx;
    final Map<Api.zzc<?>, Api.zze> zzaDH;
    private final Condition zzaDU;
    private final zzbel zzaDV;
    final Map<Api.zzc<?>, ConnectionResult> zzaDW = new HashMap();
    /* access modifiers changed from: private */
    public volatile zzbei zzaDX;
    private ConnectionResult zzaDY = null;
    int zzaDZ;
    final zzbfc zzaEa;

    public zzbej(Context context, zzbeb zzbeb, Lock lock, Looper looper, zze zze, Map<Api.zzc<?>, Api.zze> map, zzq zzq, Map<Api<?>, Boolean> map2, Api.zza<? extends zzcuw, zzcux> zza, ArrayList<zzbcu> arrayList, zzbfc zzbfc) {
        this.mContext = context;
        this.zzaCx = lock;
        this.zzaCH = zze;
        this.zzaDH = map;
        this.zzaCC = zzq;
        this.zzaCF = map2;
        this.zzaBg = zza;
        this.zzaCn = zzbeb;
        this.zzaEa = zzbfc;
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            ((zzbcu) obj).zza(this);
        }
        this.zzaDV = new zzbel(this, looper);
        this.zzaDU = lock.newCondition();
        this.zzaDX = new zzbea(this);
    }

    public final ConnectionResult blockingConnect() {
        connect();
        while (isConnecting()) {
            try {
                this.zzaDU.await();
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, (PendingIntent) null);
            }
        }
        if (isConnected()) {
            return ConnectionResult.zzazZ;
        }
        ConnectionResult connectionResult = this.zzaDY;
        return connectionResult != null ? connectionResult : new ConnectionResult(13, (PendingIntent) null);
    }

    public final ConnectionResult blockingConnect(long j, TimeUnit timeUnit) {
        connect();
        long nanos = timeUnit.toNanos(j);
        while (isConnecting()) {
            if (nanos <= 0) {
                try {
                    disconnect();
                    return new ConnectionResult(14, (PendingIntent) null);
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                    return new ConnectionResult(15, (PendingIntent) null);
                }
            } else {
                nanos = this.zzaDU.awaitNanos(nanos);
            }
        }
        if (isConnected()) {
            return ConnectionResult.zzazZ;
        }
        ConnectionResult connectionResult = this.zzaDY;
        return connectionResult != null ? connectionResult : new ConnectionResult(13, (PendingIntent) null);
    }

    public final void connect() {
        this.zzaDX.connect();
    }

    public final void disconnect() {
        if (this.zzaDX.disconnect()) {
            this.zzaDW.clear();
        }
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String concat = String.valueOf(str).concat("  ");
        printWriter.append(str).append("mState=").println(this.zzaDX);
        for (Api next : this.zzaCF.keySet()) {
            printWriter.append(str).append(next.getName()).println(":");
            this.zzaDH.get(next.zzpb()).dump(concat, fileDescriptor, printWriter, strArr);
        }
    }

    @Nullable
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        Api.zzc<?> zzpb = api.zzpb();
        if (!this.zzaDH.containsKey(zzpb)) {
            return null;
        }
        if (this.zzaDH.get(zzpb).isConnected()) {
            return ConnectionResult.zzazZ;
        }
        if (this.zzaDW.containsKey(zzpb)) {
            return this.zzaDW.get(zzpb);
        }
        return null;
    }

    public final boolean isConnected() {
        return this.zzaDX instanceof zzbdm;
    }

    public final boolean isConnecting() {
        return this.zzaDX instanceof zzbdp;
    }

    public final void onConnected(@Nullable Bundle bundle) {
        this.zzaCx.lock();
        try {
            this.zzaDX.onConnected(bundle);
        } finally {
            this.zzaCx.unlock();
        }
    }

    public final void onConnectionSuspended(int i) {
        this.zzaCx.lock();
        try {
            this.zzaDX.onConnectionSuspended(i);
        } finally {
            this.zzaCx.unlock();
        }
    }

    public final void zza(@NonNull ConnectionResult connectionResult, @NonNull Api<?> api, boolean z) {
        this.zzaCx.lock();
        try {
            this.zzaDX.zza(connectionResult, api, z);
        } finally {
            this.zzaCx.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzbek zzbek) {
        this.zzaDV.sendMessage(this.zzaDV.obtainMessage(1, zzbek));
    }

    /* access modifiers changed from: package-private */
    public final void zza(RuntimeException runtimeException) {
        this.zzaDV.sendMessage(this.zzaDV.obtainMessage(2, runtimeException));
    }

    public final boolean zza(zzbfu zzbfu) {
        return false;
    }

    public final <A extends Api.zzb, R extends Result, T extends zzbck<R, A>> T zzd(@NonNull T t) {
        t.zzpA();
        return this.zzaDX.zzd(t);
    }

    public final <A extends Api.zzb, T extends zzbck<? extends Result, A>> T zze(@NonNull T t) {
        t.zzpA();
        return this.zzaDX.zze(t);
    }

    /* access modifiers changed from: package-private */
    public final void zzg(ConnectionResult connectionResult) {
        this.zzaCx.lock();
        try {
            this.zzaDY = connectionResult;
            this.zzaDX = new zzbea(this);
            this.zzaDX.begin();
            this.zzaDU.signalAll();
        } finally {
            this.zzaCx.unlock();
        }
    }

    public final void zzpC() {
        if (isConnected()) {
            ((zzbdm) this.zzaDX).zzpS();
        }
    }

    public final void zzpj() {
    }

    /* access modifiers changed from: package-private */
    public final void zzqf() {
        this.zzaCx.lock();
        try {
            this.zzaDX = new zzbdp(this, this.zzaCC, this.zzaCF, this.zzaCH, this.zzaBg, this.zzaCx, this.mContext);
            this.zzaDX.begin();
            this.zzaDU.signalAll();
        } finally {
            this.zzaCx.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzqg() {
        this.zzaCx.lock();
        try {
            this.zzaCn.zzqc();
            this.zzaDX = new zzbdm(this);
            this.zzaDX.begin();
            this.zzaDU.signalAll();
        } finally {
            this.zzaCx.unlock();
        }
    }
}
