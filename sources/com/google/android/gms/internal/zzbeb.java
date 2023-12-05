package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzad;
import com.google.android.gms.common.internal.zzae;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.zze;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;

public final class zzbeb extends GoogleApiClient implements zzbfc {
    /* access modifiers changed from: private */
    public final Context mContext;
    private final int zzaBd;
    private final GoogleApiAvailability zzaBf;
    private Api.zza<? extends zzcuw, zzcux> zzaBg;
    private boolean zzaBj;
    private zzq zzaCC;
    private Map<Api<?>, Boolean> zzaCF;
    final Queue<zzbck<?, ?>> zzaCL = new LinkedList();
    private final Lock zzaCx;
    private final zzad zzaDA;
    private zzbfb zzaDB = null;
    private volatile boolean zzaDC;
    private long zzaDD = 120000;
    private long zzaDE = 5000;
    private final zzbeg zzaDF;
    private zzbew zzaDG;
    final Map<Api.zzc<?>, Api.zze> zzaDH;
    Set<Scope> zzaDI = new HashSet();
    private final zzbfm zzaDJ = new zzbfm();
    private final ArrayList<zzbcu> zzaDK;
    private Integer zzaDL = null;
    Set<zzbge> zzaDM = null;
    final zzbgh zzaDN;
    private final zzae zzaDO = new zzbec(this);
    private final Looper zzrP;

    public zzbeb(Context context, Lock lock, Looper looper, zzq zzq, GoogleApiAvailability googleApiAvailability, Api.zza<? extends zzcuw, zzcux> zza, Map<Api<?>, Boolean> map, List<GoogleApiClient.ConnectionCallbacks> list, List<GoogleApiClient.OnConnectionFailedListener> list2, Map<Api.zzc<?>, Api.zze> map2, int i, int i2, ArrayList<zzbcu> arrayList, boolean z) {
        this.mContext = context;
        this.zzaCx = lock;
        this.zzaBj = false;
        this.zzaDA = new zzad(looper, this.zzaDO);
        this.zzrP = looper;
        this.zzaDF = new zzbeg(this, looper);
        this.zzaBf = googleApiAvailability;
        this.zzaBd = i;
        if (this.zzaBd >= 0) {
            this.zzaDL = Integer.valueOf(i2);
        }
        this.zzaCF = map;
        this.zzaDH = map2;
        this.zzaDK = arrayList;
        this.zzaDN = new zzbgh(this.zzaDH);
        for (GoogleApiClient.ConnectionCallbacks registerConnectionCallbacks : list) {
            this.zzaDA.registerConnectionCallbacks(registerConnectionCallbacks);
        }
        for (GoogleApiClient.OnConnectionFailedListener registerConnectionFailedListener : list2) {
            this.zzaDA.registerConnectionFailedListener(registerConnectionFailedListener);
        }
        this.zzaCC = zzq;
        this.zzaBg = zza;
    }

    /* access modifiers changed from: private */
    public final void resume() {
        this.zzaCx.lock();
        try {
            if (this.zzaDC) {
                zzqa();
            }
        } finally {
            this.zzaCx.unlock();
        }
    }

    public static int zza(Iterable<Api.zze> iterable, boolean z) {
        boolean z2 = false;
        boolean z3 = false;
        for (Api.zze next : iterable) {
            if (next.zzmt()) {
                z2 = true;
            }
            if (next.zzmE()) {
                z3 = true;
            }
        }
        if (z2) {
            return (!z3 || !z) ? 1 : 2;
        }
        return 3;
    }

    /* access modifiers changed from: private */
    public final void zza(GoogleApiClient googleApiClient, zzbfz zzbfz, boolean z) {
        zzbha.zzaIA.zzd(googleApiClient).setResultCallback(new zzbef(this, zzbfz, z, googleApiClient));
    }

    private final void zzap(int i) {
        Integer num = this.zzaDL;
        if (num == null) {
            this.zzaDL = Integer.valueOf(i);
        } else if (num.intValue() != i) {
            String valueOf = String.valueOf(zzaq(i));
            String valueOf2 = String.valueOf(zzaq(this.zzaDL.intValue()));
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 51 + String.valueOf(valueOf2).length());
            sb.append("Cannot use sign-in mode: ");
            sb.append(valueOf);
            sb.append(". Mode was already set to ");
            sb.append(valueOf2);
            throw new IllegalStateException(sb.toString());
        }
        if (this.zzaDB == null) {
            boolean z = false;
            boolean z2 = false;
            for (Api.zze next : this.zzaDH.values()) {
                if (next.zzmt()) {
                    z = true;
                }
                if (next.zzmE()) {
                    z2 = true;
                }
            }
            switch (this.zzaDL.intValue()) {
                case 1:
                    if (!z) {
                        throw new IllegalStateException("SIGN_IN_MODE_REQUIRED cannot be used on a GoogleApiClient that does not contain any authenticated APIs. Use connect() instead.");
                    } else if (z2) {
                        throw new IllegalStateException("Cannot use SIGN_IN_MODE_REQUIRED with GOOGLE_SIGN_IN_API. Use connect(SIGN_IN_MODE_OPTIONAL) instead.");
                    }
                    break;
                case 2:
                    if (z) {
                        if (this.zzaBj) {
                            this.zzaDB = new zzbdb(this.mContext, this.zzaCx, this.zzrP, this.zzaBf, this.zzaDH, this.zzaCC, this.zzaCF, this.zzaBg, this.zzaDK, this, true);
                            return;
                        }
                        this.zzaDB = zzbcw.zza(this.mContext, this, this.zzaCx, this.zzrP, this.zzaBf, this.zzaDH, this.zzaCC, this.zzaCF, this.zzaBg, this.zzaDK);
                        return;
                    }
                    break;
            }
            if (!this.zzaBj || z2) {
                this.zzaDB = new zzbej(this.mContext, this, this.zzaCx, this.zzrP, this.zzaBf, this.zzaDH, this.zzaCC, this.zzaCF, this.zzaBg, this.zzaDK, this);
                return;
            }
            this.zzaDB = new zzbdb(this.mContext, this.zzaCx, this.zzrP, this.zzaBf, this.zzaDH, this.zzaCC, this.zzaCF, this.zzaBg, this.zzaDK, this, false);
        }
    }

    private static String zzaq(int i) {
        switch (i) {
            case 1:
                return "SIGN_IN_MODE_REQUIRED";
            case 2:
                return "SIGN_IN_MODE_OPTIONAL";
            case 3:
                return "SIGN_IN_MODE_NONE";
            default:
                return "UNKNOWN";
        }
    }

    private final void zzqa() {
        this.zzaDA.zzrz();
        this.zzaDB.connect();
    }

    /* access modifiers changed from: private */
    public final void zzqb() {
        this.zzaCx.lock();
        try {
            if (zzqc()) {
                zzqa();
            }
        } finally {
            this.zzaCx.unlock();
        }
    }

    public final ConnectionResult blockingConnect() {
        boolean z = true;
        zzbr.zza(Looper.myLooper() != Looper.getMainLooper(), (Object) "blockingConnect must not be called on the UI thread");
        this.zzaCx.lock();
        try {
            if (this.zzaBd >= 0) {
                if (this.zzaDL == null) {
                    z = false;
                }
                zzbr.zza(z, (Object) "Sign-in mode should have been set explicitly by auto-manage.");
            } else if (this.zzaDL == null) {
                this.zzaDL = Integer.valueOf(zza(this.zzaDH.values(), false));
            } else if (this.zzaDL.intValue() == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            zzap(this.zzaDL.intValue());
            this.zzaDA.zzrz();
            return this.zzaDB.blockingConnect();
        } finally {
            this.zzaCx.unlock();
        }
    }

    public final ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit) {
        zzbr.zza(Looper.myLooper() != Looper.getMainLooper(), (Object) "blockingConnect must not be called on the UI thread");
        zzbr.zzb(timeUnit, (Object) "TimeUnit must not be null");
        this.zzaCx.lock();
        try {
            if (this.zzaDL == null) {
                this.zzaDL = Integer.valueOf(zza(this.zzaDH.values(), false));
            } else if (this.zzaDL.intValue() == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            zzap(this.zzaDL.intValue());
            this.zzaDA.zzrz();
            return this.zzaDB.blockingConnect(j, timeUnit);
        } finally {
            this.zzaCx.unlock();
        }
    }

    public final PendingResult<Status> clearDefaultAccountAndReconnect() {
        zzbr.zza(isConnected(), (Object) "GoogleApiClient is not connected yet.");
        zzbr.zza(this.zzaDL.intValue() != 2, (Object) "Cannot use clearDefaultAccountAndReconnect with GOOGLE_SIGN_IN_API");
        zzbfz zzbfz = new zzbfz((GoogleApiClient) this);
        if (this.zzaDH.containsKey(zzbha.zzajT)) {
            zza(this, zzbfz, false);
        } else {
            AtomicReference atomicReference = new AtomicReference();
            GoogleApiClient build = new GoogleApiClient.Builder(this.mContext).addApi(zzbha.API).addConnectionCallbacks(new zzbed(this, atomicReference, zzbfz)).addOnConnectionFailedListener(new zzbee(this, zzbfz)).setHandler(this.zzaDF).build();
            atomicReference.set(build);
            build.connect();
        }
        return zzbfz;
    }

    public final void connect() {
        this.zzaCx.lock();
        try {
            boolean z = false;
            if (this.zzaBd >= 0) {
                if (this.zzaDL != null) {
                    z = true;
                }
                zzbr.zza(z, (Object) "Sign-in mode should have been set explicitly by auto-manage.");
            } else if (this.zzaDL == null) {
                this.zzaDL = Integer.valueOf(zza(this.zzaDH.values(), false));
            } else if (this.zzaDL.intValue() == 2) {
                throw new IllegalStateException("Cannot call connect() when SignInMode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            connect(this.zzaDL.intValue());
        } finally {
            this.zzaCx.unlock();
        }
    }

    public final void connect(int i) {
        this.zzaCx.lock();
        boolean z = true;
        if (!(i == 3 || i == 1 || i == 2)) {
            z = false;
        }
        try {
            StringBuilder sb = new StringBuilder(33);
            sb.append("Illegal sign-in mode: ");
            sb.append(i);
            zzbr.zzb(z, (Object) sb.toString());
            zzap(i);
            zzqa();
        } finally {
            this.zzaCx.unlock();
        }
    }

    public final void disconnect() {
        this.zzaCx.lock();
        try {
            this.zzaDN.release();
            if (this.zzaDB != null) {
                this.zzaDB.disconnect();
            }
            this.zzaDJ.release();
            for (zzbck zzbck : this.zzaCL) {
                zzbck.zza((zzbgj) null);
                zzbck.cancel();
            }
            this.zzaCL.clear();
            if (this.zzaDB != null) {
                zzqc();
                this.zzaDA.zzry();
            }
        } finally {
            this.zzaCx.unlock();
        }
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.append(str).append("mContext=").println(this.mContext);
        printWriter.append(str).append("mResuming=").print(this.zzaDC);
        printWriter.append(" mWorkQueue.size()=").print(this.zzaCL.size());
        printWriter.append(" mUnconsumedApiCalls.size()=").println(this.zzaDN.zzaFn.size());
        zzbfb zzbfb = this.zzaDB;
        if (zzbfb != null) {
            zzbfb.dump(str, fileDescriptor, printWriter, strArr);
        }
    }

    @NonNull
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        ConnectionResult connectionResult;
        this.zzaCx.lock();
        try {
            if (!isConnected()) {
                if (!this.zzaDC) {
                    throw new IllegalStateException("Cannot invoke getConnectionResult unless GoogleApiClient is connected");
                }
            }
            if (this.zzaDH.containsKey(api.zzpb())) {
                ConnectionResult connectionResult2 = this.zzaDB.getConnectionResult(api);
                if (connectionResult2 == null) {
                    if (this.zzaDC) {
                        connectionResult = ConnectionResult.zzazZ;
                    } else {
                        Log.w("GoogleApiClientImpl", zzqe());
                        Log.wtf("GoogleApiClientImpl", String.valueOf(api.getName()).concat(" requested in getConnectionResult is not connected but is not present in the failed  connections map"), new Exception());
                        connectionResult = new ConnectionResult(8, (PendingIntent) null);
                    }
                    return connectionResult;
                }
                this.zzaCx.unlock();
                return connectionResult2;
            }
            throw new IllegalArgumentException(String.valueOf(api.getName()).concat(" was never registered with GoogleApiClient"));
        } finally {
            this.zzaCx.unlock();
        }
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final Looper getLooper() {
        return this.zzrP;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0008, code lost:
        r3 = r2.zzaDH.get(r3.zzpb());
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean hasConnectedApi(@android.support.annotation.NonNull com.google.android.gms.common.api.Api<?> r3) {
        /*
            r2 = this;
            boolean r0 = r2.isConnected()
            r1 = 0
            if (r0 != 0) goto L_0x0008
            return r1
        L_0x0008:
            java.util.Map<com.google.android.gms.common.api.Api$zzc<?>, com.google.android.gms.common.api.Api$zze> r0 = r2.zzaDH
            com.google.android.gms.common.api.Api$zzc r3 = r3.zzpb()
            java.lang.Object r3 = r0.get(r3)
            com.google.android.gms.common.api.Api$zze r3 = (com.google.android.gms.common.api.Api.zze) r3
            if (r3 == 0) goto L_0x001e
            boolean r3 = r3.isConnected()
            if (r3 == 0) goto L_0x001e
            r3 = 1
            return r3
        L_0x001e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbeb.hasConnectedApi(com.google.android.gms.common.api.Api):boolean");
    }

    public final boolean isConnected() {
        zzbfb zzbfb = this.zzaDB;
        return zzbfb != null && zzbfb.isConnected();
    }

    public final boolean isConnecting() {
        zzbfb zzbfb = this.zzaDB;
        return zzbfb != null && zzbfb.isConnecting();
    }

    public final boolean isConnectionCallbacksRegistered(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        return this.zzaDA.isConnectionCallbacksRegistered(connectionCallbacks);
    }

    public final boolean isConnectionFailedListenerRegistered(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return this.zzaDA.isConnectionFailedListenerRegistered(onConnectionFailedListener);
    }

    public final void reconnect() {
        disconnect();
        connect();
    }

    public final void registerConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zzaDA.registerConnectionCallbacks(connectionCallbacks);
    }

    public final void registerConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zzaDA.registerConnectionFailedListener(onConnectionFailedListener);
    }

    public final void stopAutoManage(@NonNull FragmentActivity fragmentActivity) {
        zzbfd zzbfd = new zzbfd(fragmentActivity);
        if (this.zzaBd >= 0) {
            zzbcg.zza(zzbfd).zzal(this.zzaBd);
            return;
        }
        throw new IllegalStateException("Called stopAutoManage but automatic lifecycle management is not enabled.");
    }

    public final void unregisterConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zzaDA.unregisterConnectionCallbacks(connectionCallbacks);
    }

    public final void unregisterConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zzaDA.unregisterConnectionFailedListener(onConnectionFailedListener);
    }

    @NonNull
    public final <C extends Api.zze> C zza(@NonNull Api.zzc<C> zzc) {
        C c = (Api.zze) this.zzaDH.get(zzc);
        zzbr.zzb(c, (Object) "Appropriate Api was not requested.");
        return c;
    }

    public final void zza(zzbge zzbge) {
        this.zzaCx.lock();
        try {
            if (this.zzaDM == null) {
                this.zzaDM = new HashSet();
            }
            this.zzaDM.add(zzbge);
        } finally {
            this.zzaCx.unlock();
        }
    }

    public final boolean zza(@NonNull Api<?> api) {
        return this.zzaDH.containsKey(api.zzpb());
    }

    public final boolean zza(zzbfu zzbfu) {
        zzbfb zzbfb = this.zzaDB;
        return zzbfb != null && zzbfb.zza(zzbfu);
    }

    public final void zzb(zzbge zzbge) {
        String str;
        String str2;
        Exception exc;
        this.zzaCx.lock();
        try {
            if (this.zzaDM == null) {
                str = "GoogleApiClientImpl";
                str2 = "Attempted to remove pending transform when no transforms are registered.";
                exc = new Exception();
            } else if (!this.zzaDM.remove(zzbge)) {
                str = "GoogleApiClientImpl";
                str2 = "Failed to remove pending transform - this may lead to memory leaks!";
                exc = new Exception();
            } else {
                if (!zzqd()) {
                    this.zzaDB.zzpC();
                }
            }
            Log.wtf(str, str2, exc);
        } finally {
            this.zzaCx.unlock();
        }
    }

    public final void zzc(ConnectionResult connectionResult) {
        if (!zze.zze(this.mContext, connectionResult.getErrorCode())) {
            zzqc();
        }
        if (!this.zzaDC) {
            this.zzaDA.zzk(connectionResult);
            this.zzaDA.zzry();
        }
    }

    public final <A extends Api.zzb, R extends Result, T extends zzbck<R, A>> T zzd(@NonNull T t) {
        zzbr.zzb(t.zzpb() != null, (Object) "This task can not be enqueued (it's probably a Batch or malformed)");
        boolean containsKey = this.zzaDH.containsKey(t.zzpb());
        String name = t.zzpe() != null ? t.zzpe().getName() : "the API";
        StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 65);
        sb.append("GoogleApiClient is not configured to use ");
        sb.append(name);
        sb.append(" required for this call.");
        zzbr.zzb(containsKey, (Object) sb.toString());
        this.zzaCx.lock();
        try {
            if (this.zzaDB == null) {
                this.zzaCL.add(t);
            } else {
                t = this.zzaDB.zzd(t);
            }
            return t;
        } finally {
            this.zzaCx.unlock();
        }
    }

    public final <A extends Api.zzb, T extends zzbck<? extends Result, A>> T zze(@NonNull T t) {
        zzbr.zzb(t.zzpb() != null, (Object) "This task can not be executed (it's probably a Batch or malformed)");
        boolean containsKey = this.zzaDH.containsKey(t.zzpb());
        String name = t.zzpe() != null ? t.zzpe().getName() : "the API";
        StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 65);
        sb.append("GoogleApiClient is not configured to use ");
        sb.append(name);
        sb.append(" required for this call.");
        zzbr.zzb(containsKey, (Object) sb.toString());
        this.zzaCx.lock();
        try {
            if (this.zzaDB != null) {
                if (this.zzaDC) {
                    this.zzaCL.add(t);
                    while (!this.zzaCL.isEmpty()) {
                        zzbck remove = this.zzaCL.remove();
                        this.zzaDN.zzb(remove);
                        remove.zzr(Status.zzaBq);
                    }
                } else {
                    t = this.zzaDB.zze(t);
                }
                return t;
            }
            throw new IllegalStateException("GoogleApiClient is not connected yet.");
        } finally {
            this.zzaCx.unlock();
        }
    }

    public final void zze(int i, boolean z) {
        if (i == 1 && !z && !this.zzaDC) {
            this.zzaDC = true;
            if (this.zzaDG == null) {
                this.zzaDG = GoogleApiAvailability.zza(this.mContext.getApplicationContext(), (zzbex) new zzbeh(this));
            }
            zzbeg zzbeg = this.zzaDF;
            zzbeg.sendMessageDelayed(zzbeg.obtainMessage(1), this.zzaDD);
            zzbeg zzbeg2 = this.zzaDF;
            zzbeg2.sendMessageDelayed(zzbeg2.obtainMessage(2), this.zzaDE);
        }
        this.zzaDN.zzqK();
        this.zzaDA.zzaA(i);
        this.zzaDA.zzry();
        if (i == 2) {
            zzqa();
        }
    }

    public final void zzm(Bundle bundle) {
        while (!this.zzaCL.isEmpty()) {
            zze(this.zzaCL.remove());
        }
        this.zzaDA.zzn(bundle);
    }

    public final <L> zzbfi<L> zzp(@NonNull L l) {
        this.zzaCx.lock();
        try {
            return this.zzaDJ.zza(l, this.zzrP, "NO_TYPE");
        } finally {
            this.zzaCx.unlock();
        }
    }

    public final void zzpj() {
        zzbfb zzbfb = this.zzaDB;
        if (zzbfb != null) {
            zzbfb.zzpj();
        }
    }

    /* access modifiers changed from: package-private */
    public final boolean zzqc() {
        if (!this.zzaDC) {
            return false;
        }
        this.zzaDC = false;
        this.zzaDF.removeMessages(2);
        this.zzaDF.removeMessages(1);
        zzbew zzbew = this.zzaDG;
        if (zzbew != null) {
            zzbew.unregister();
            this.zzaDG = null;
        }
        return true;
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    public final boolean zzqd() {
        this.zzaCx.lock();
        try {
            if (this.zzaDM == null) {
                this.zzaCx.unlock();
                return false;
            }
            boolean z = !this.zzaDM.isEmpty();
            this.zzaCx.unlock();
            return z;
        } catch (Throwable th) {
            this.zzaCx.unlock();
            throw th;
        }
    }

    /* access modifiers changed from: package-private */
    public final String zzqe() {
        StringWriter stringWriter = new StringWriter();
        dump("", (FileDescriptor) null, new PrintWriter(stringWriter), (String[]) null);
        return stringWriter.toString();
    }
}
