package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.zze;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public final class zzbdb implements zzbfb {
    private final zzben zzaAP;
    private final zzq zzaCC;
    /* access modifiers changed from: private */
    public final Map<Api.zzc<?>, zzbda<?>> zzaCD = new HashMap();
    /* access modifiers changed from: private */
    public final Map<Api.zzc<?>, zzbda<?>> zzaCE = new HashMap();
    private final Map<Api<?>, Boolean> zzaCF;
    /* access modifiers changed from: private */
    public final zzbeb zzaCG;
    private final zze zzaCH;
    /* access modifiers changed from: private */
    public final Condition zzaCI;
    private final boolean zzaCJ;
    /* access modifiers changed from: private */
    public final boolean zzaCK;
    private final Queue<zzbck<?, ?>> zzaCL = new LinkedList();
    /* access modifiers changed from: private */
    public boolean zzaCM;
    /* access modifiers changed from: private */
    public Map<zzbcf<?>, ConnectionResult> zzaCN;
    /* access modifiers changed from: private */
    public Map<zzbcf<?>, ConnectionResult> zzaCO;
    private zzbde zzaCP;
    /* access modifiers changed from: private */
    public ConnectionResult zzaCQ;
    /* access modifiers changed from: private */
    public final Lock zzaCx;
    private final Looper zzrP;

    public zzbdb(Context context, Lock lock, Looper looper, zze zze, Map<Api.zzc<?>, Api.zze> map, zzq zzq, Map<Api<?>, Boolean> map2, Api.zza<? extends zzcuw, zzcux> zza, ArrayList<zzbcu> arrayList, zzbeb zzbeb, boolean z) {
        boolean z2;
        boolean z3;
        boolean z4;
        this.zzaCx = lock;
        this.zzrP = looper;
        this.zzaCI = lock.newCondition();
        this.zzaCH = zze;
        this.zzaCG = zzbeb;
        this.zzaCF = map2;
        this.zzaCC = zzq;
        this.zzaCJ = z;
        HashMap hashMap = new HashMap();
        for (Api next : map2.keySet()) {
            hashMap.put(next.zzpb(), next);
        }
        HashMap hashMap2 = new HashMap();
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            zzbcu zzbcu = (zzbcu) obj;
            hashMap2.put(zzbcu.zzayY, zzbcu);
        }
        boolean z5 = true;
        boolean z6 = false;
        boolean z7 = true;
        boolean z8 = false;
        for (Map.Entry next2 : map.entrySet()) {
            Api api = (Api) hashMap.get(next2.getKey());
            Api.zze zze2 = (Api.zze) next2.getValue();
            if (zze2.zzpc()) {
                if (!this.zzaCF.get(api).booleanValue()) {
                    z3 = z7;
                    z4 = true;
                } else {
                    z3 = z7;
                    z4 = z8;
                }
                z2 = true;
            } else {
                z2 = z6;
                z4 = z8;
                z3 = false;
            }
            zzbda zzbda = r1;
            zzbda zzbda2 = new zzbda(context, api, looper, zze2, (zzbcu) hashMap2.get(api), zzq, zza);
            this.zzaCD.put((Api.zzc) next2.getKey(), zzbda);
            if (zze2.zzmt()) {
                this.zzaCE.put((Api.zzc) next2.getKey(), zzbda);
            }
            z8 = z4;
            z7 = z3;
            z6 = z2;
        }
        this.zzaCK = (!z6 || z7 || z8) ? false : z5;
        this.zzaAP = zzben.zzqi();
    }

    /* access modifiers changed from: private */
    public final boolean zza(zzbda<?> zzbda, ConnectionResult connectionResult) {
        return !connectionResult.isSuccess() && !connectionResult.hasResolution() && this.zzaCF.get(zzbda.zzpe()).booleanValue() && zzbda.zzpH().zzpc() && this.zzaCH.isUserResolvableError(connectionResult.getErrorCode());
    }

    @Nullable
    private final ConnectionResult zzb(@NonNull Api.zzc<?> zzc) {
        this.zzaCx.lock();
        try {
            zzbda zzbda = this.zzaCD.get(zzc);
            if (this.zzaCN != null && zzbda != null) {
                return this.zzaCN.get(zzbda.zzpf());
            }
            this.zzaCx.unlock();
            return null;
        } finally {
            this.zzaCx.unlock();
        }
    }

    private final <T extends zzbck<? extends Result, ? extends Api.zzb>> boolean zzg(@NonNull T t) {
        Api.zzc zzpb = t.zzpb();
        ConnectionResult zzb = zzb((Api.zzc<?>) zzpb);
        if (zzb == null || zzb.getErrorCode() != 4) {
            return false;
        }
        t.zzr(new Status(4, (String) null, this.zzaAP.zza((zzbcf<?>) this.zzaCD.get(zzpb).zzpf(), System.identityHashCode(this.zzaCG))));
        return true;
    }

    private final boolean zzpI() {
        this.zzaCx.lock();
        try {
            if (this.zzaCM) {
                if (this.zzaCJ) {
                    for (Api.zzc<?> zzb : this.zzaCE.keySet()) {
                        ConnectionResult zzb2 = zzb(zzb);
                        if (zzb2 != null) {
                            if (!zzb2.isSuccess()) {
                            }
                        }
                    }
                    this.zzaCx.unlock();
                    return true;
                }
            }
            return false;
        } finally {
            this.zzaCx.unlock();
        }
    }

    /* access modifiers changed from: private */
    public final void zzpJ() {
        Set<Scope> hashSet;
        zzbeb zzbeb;
        zzq zzq = this.zzaCC;
        if (zzq == null) {
            zzbeb = this.zzaCG;
            hashSet = Collections.emptySet();
        } else {
            hashSet = new HashSet<>(zzq.zzrl());
            Map<Api<?>, zzr> zzrn = this.zzaCC.zzrn();
            for (Api next : zzrn.keySet()) {
                ConnectionResult connectionResult = getConnectionResult(next);
                if (connectionResult != null && connectionResult.isSuccess()) {
                    hashSet.addAll(zzrn.get(next).zzamg);
                }
            }
            zzbeb = this.zzaCG;
        }
        zzbeb.zzaDI = hashSet;
    }

    /* access modifiers changed from: private */
    public final void zzpK() {
        while (!this.zzaCL.isEmpty()) {
            zze(this.zzaCL.remove());
        }
        this.zzaCG.zzm((Bundle) null);
    }

    /* access modifiers changed from: private */
    @Nullable
    public final ConnectionResult zzpL() {
        ConnectionResult connectionResult = null;
        ConnectionResult connectionResult2 = null;
        int i = 0;
        int i2 = 0;
        for (zzbda next : this.zzaCD.values()) {
            Api zzpe = next.zzpe();
            ConnectionResult connectionResult3 = this.zzaCN.get(next.zzpf());
            if (!connectionResult3.isSuccess() && (!this.zzaCF.get(zzpe).booleanValue() || connectionResult3.hasResolution() || this.zzaCH.isUserResolvableError(connectionResult3.getErrorCode()))) {
                if (connectionResult3.getErrorCode() != 4 || !this.zzaCJ) {
                    int priority = zzpe.zzoZ().getPriority();
                    if (connectionResult == null || i > priority) {
                        connectionResult = connectionResult3;
                        i = priority;
                    }
                } else {
                    int priority2 = zzpe.zzoZ().getPriority();
                    if (connectionResult2 == null || i2 > priority2) {
                        connectionResult2 = connectionResult3;
                        i2 = priority2;
                    }
                }
            }
        }
        return (connectionResult == null || connectionResult2 == null || i <= i2) ? connectionResult : connectionResult2;
    }

    public final ConnectionResult blockingConnect() {
        connect();
        while (isConnecting()) {
            try {
                this.zzaCI.await();
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, (PendingIntent) null);
            }
        }
        if (isConnected()) {
            return ConnectionResult.zzazZ;
        }
        ConnectionResult connectionResult = this.zzaCQ;
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
                nanos = this.zzaCI.awaitNanos(nanos);
            }
        }
        if (isConnected()) {
            return ConnectionResult.zzazZ;
        }
        ConnectionResult connectionResult = this.zzaCQ;
        return connectionResult != null ? connectionResult : new ConnectionResult(13, (PendingIntent) null);
    }

    public final void connect() {
        this.zzaCx.lock();
        try {
            if (!this.zzaCM) {
                this.zzaCM = true;
                this.zzaCN = null;
                this.zzaCO = null;
                this.zzaCP = null;
                this.zzaCQ = null;
                this.zzaAP.zzpq();
                this.zzaAP.zza((Iterable<? extends GoogleApi<?>>) this.zzaCD.values()).addOnCompleteListener((Executor) new zzbih(this.zzrP), new zzbdd(this));
            }
        } finally {
            this.zzaCx.unlock();
        }
    }

    public final void disconnect() {
        this.zzaCx.lock();
        try {
            this.zzaCM = false;
            this.zzaCN = null;
            this.zzaCO = null;
            if (this.zzaCP != null) {
                this.zzaCP.cancel();
                this.zzaCP = null;
            }
            this.zzaCQ = null;
            while (!this.zzaCL.isEmpty()) {
                zzbck remove = this.zzaCL.remove();
                remove.zza((zzbgj) null);
                remove.cancel();
            }
            this.zzaCI.signalAll();
        } finally {
            this.zzaCx.unlock();
        }
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    @Nullable
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        return zzb(api.zzpb());
    }

    public final boolean isConnected() {
        this.zzaCx.lock();
        try {
            return this.zzaCN != null && this.zzaCQ == null;
        } finally {
            this.zzaCx.unlock();
        }
    }

    public final boolean isConnecting() {
        this.zzaCx.lock();
        try {
            return this.zzaCN == null && this.zzaCM;
        } finally {
            this.zzaCx.unlock();
        }
    }

    /* JADX INFO: finally extract failed */
    public final boolean zza(zzbfu zzbfu) {
        this.zzaCx.lock();
        try {
            if (!this.zzaCM || zzpI()) {
                this.zzaCx.unlock();
                return false;
            }
            this.zzaAP.zzpq();
            this.zzaCP = new zzbde(this, zzbfu);
            this.zzaAP.zza((Iterable<? extends GoogleApi<?>>) this.zzaCE.values()).addOnCompleteListener((Executor) new zzbih(this.zzrP), this.zzaCP);
            this.zzaCx.unlock();
            return true;
        } catch (Throwable th) {
            this.zzaCx.unlock();
            throw th;
        }
    }

    public final <A extends Api.zzb, R extends Result, T extends zzbck<R, A>> T zzd(@NonNull T t) {
        if (this.zzaCJ && zzg(t)) {
            return t;
        }
        if (!isConnected()) {
            this.zzaCL.add(t);
            return t;
        }
        this.zzaCG.zzaDN.zzb(t);
        return this.zzaCD.get(t.zzpb()).zza(t);
    }

    public final <A extends Api.zzb, T extends zzbck<? extends Result, A>> T zze(@NonNull T t) {
        Api.zzc zzpb = t.zzpb();
        if (this.zzaCJ && zzg(t)) {
            return t;
        }
        this.zzaCG.zzaDN.zzb(t);
        return this.zzaCD.get(zzpb).zzb(t);
    }

    public final void zzpC() {
    }

    public final void zzpj() {
        this.zzaCx.lock();
        try {
            this.zzaAP.zzpj();
            if (this.zzaCP != null) {
                this.zzaCP.cancel();
                this.zzaCP = null;
            }
            if (this.zzaCO == null) {
                this.zzaCO = new ArrayMap(this.zzaCE.size());
            }
            ConnectionResult connectionResult = new ConnectionResult(4);
            for (zzbda<?> zzpf : this.zzaCE.values()) {
                this.zzaCO.put(zzpf.zzpf(), connectionResult);
            }
            if (this.zzaCN != null) {
                this.zzaCN.putAll(this.zzaCO);
            }
        } finally {
            this.zzaCx.unlock();
        }
    }
}
