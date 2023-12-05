package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.zze;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

final class zzbcw implements zzbfb {
    private final Context mContext;
    private final zzbeb zzaCn;
    /* access modifiers changed from: private */
    public final zzbej zzaCo;
    /* access modifiers changed from: private */
    public final zzbej zzaCp;
    private final Map<Api.zzc<?>, zzbej> zzaCq;
    private final Set<zzbfu> zzaCr = Collections.newSetFromMap(new WeakHashMap());
    private final Api.zze zzaCs;
    private Bundle zzaCt;
    /* access modifiers changed from: private */
    public ConnectionResult zzaCu = null;
    /* access modifiers changed from: private */
    public ConnectionResult zzaCv = null;
    /* access modifiers changed from: private */
    public boolean zzaCw = false;
    /* access modifiers changed from: private */
    public final Lock zzaCx;
    private int zzaCy = 0;
    private final Looper zzrP;

    private zzbcw(Context context, zzbeb zzbeb, Lock lock, Looper looper, zze zze, Map<Api.zzc<?>, Api.zze> map, Map<Api.zzc<?>, Api.zze> map2, zzq zzq, Api.zza<? extends zzcuw, zzcux> zza, Api.zze zze2, ArrayList<zzbcu> arrayList, ArrayList<zzbcu> arrayList2, Map<Api<?>, Boolean> map3, Map<Api<?>, Boolean> map4) {
        this.mContext = context;
        this.zzaCn = zzbeb;
        this.zzaCx = lock;
        this.zzrP = looper;
        this.zzaCs = zze2;
        Context context2 = context;
        Lock lock2 = lock;
        Looper looper2 = looper;
        zze zze3 = zze;
        zzbej zzbej = r3;
        zzbej zzbej2 = new zzbej(context2, this.zzaCn, lock2, looper2, zze3, map2, (zzq) null, map4, (Api.zza<? extends zzcuw, zzcux>) null, arrayList2, new zzbcy(this, (zzbcx) null));
        this.zzaCo = zzbej;
        this.zzaCp = new zzbej(context2, this.zzaCn, lock2, looper2, zze3, map, zzq, map3, zza, arrayList, new zzbcz(this, (zzbcx) null));
        ArrayMap arrayMap = new ArrayMap();
        for (Api.zzc<?> put : map2.keySet()) {
            arrayMap.put(put, this.zzaCo);
        }
        for (Api.zzc<?> put2 : map.keySet()) {
            arrayMap.put(put2, this.zzaCp);
        }
        this.zzaCq = Collections.unmodifiableMap(arrayMap);
    }

    public static zzbcw zza(Context context, zzbeb zzbeb, Lock lock, Looper looper, zze zze, Map<Api.zzc<?>, Api.zze> map, zzq zzq, Map<Api<?>, Boolean> map2, Api.zza<? extends zzcuw, zzcux> zza, ArrayList<zzbcu> arrayList) {
        Map<Api<?>, Boolean> map3 = map2;
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap();
        Api.zze zze2 = null;
        for (Map.Entry next : map.entrySet()) {
            Api.zze zze3 = (Api.zze) next.getValue();
            if (zze3.zzmE()) {
                zze2 = zze3;
            }
            if (zze3.zzmt()) {
                arrayMap.put((Api.zzc) next.getKey(), zze3);
            } else {
                arrayMap2.put((Api.zzc) next.getKey(), zze3);
            }
        }
        zzbr.zza(!arrayMap.isEmpty(), (Object) "CompositeGoogleApiClient should not be used without any APIs that require sign-in.");
        ArrayMap arrayMap3 = new ArrayMap();
        ArrayMap arrayMap4 = new ArrayMap();
        for (Api next2 : map2.keySet()) {
            Api.zzc<?> zzpb = next2.zzpb();
            if (arrayMap.containsKey(zzpb)) {
                arrayMap3.put(next2, map3.get(next2));
            } else if (arrayMap2.containsKey(zzpb)) {
                arrayMap4.put(next2, map3.get(next2));
            } else {
                throw new IllegalStateException("Each API in the isOptionalMap must have a corresponding client in the clients map.");
            }
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = arrayList;
        int size = arrayList4.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList4.get(i);
            i++;
            zzbcu zzbcu = (zzbcu) obj;
            if (arrayMap3.containsKey(zzbcu.zzayY)) {
                arrayList2.add(zzbcu);
            } else if (arrayMap4.containsKey(zzbcu.zzayY)) {
                arrayList3.add(zzbcu);
            } else {
                throw new IllegalStateException("Each ClientCallbacks must have a corresponding API in the isOptionalMap");
            }
        }
        return new zzbcw(context, zzbeb, lock, looper, zze, arrayMap, arrayMap2, zzq, zza, zze2, arrayList2, arrayList3, arrayMap3, arrayMap4);
    }

    private final void zza(ConnectionResult connectionResult) {
        switch (this.zzaCy) {
            case 1:
                break;
            case 2:
                this.zzaCn.zzc(connectionResult);
                break;
            default:
                Log.wtf("CompositeGAC", "Attempted to call failure callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new Exception());
                break;
        }
        zzpE();
        this.zzaCy = 0;
    }

    private static boolean zzb(ConnectionResult connectionResult) {
        return connectionResult != null && connectionResult.isSuccess();
    }

    /* access modifiers changed from: private */
    public final void zzd(int i, boolean z) {
        this.zzaCn.zze(i, z);
        this.zzaCv = null;
        this.zzaCu = null;
    }

    private final boolean zzf(zzbck<? extends Result, ? extends Api.zzb> zzbck) {
        Api.zzc<? extends Api.zzb> zzpb = zzbck.zzpb();
        zzbr.zzb(this.zzaCq.containsKey(zzpb), (Object) "GoogleApiClient is not configured to use the API required for this call.");
        return this.zzaCq.get(zzpb).equals(this.zzaCp);
    }

    /* access modifiers changed from: private */
    public final void zzl(Bundle bundle) {
        Bundle bundle2 = this.zzaCt;
        if (bundle2 == null) {
            this.zzaCt = bundle;
        } else if (bundle != null) {
            bundle2.putAll(bundle);
        }
    }

    /* access modifiers changed from: private */
    public final void zzpD() {
        if (zzb(this.zzaCu)) {
            if (zzb(this.zzaCv) || zzpF()) {
                switch (this.zzaCy) {
                    case 1:
                        break;
                    case 2:
                        this.zzaCn.zzm(this.zzaCt);
                        break;
                    default:
                        Log.wtf("CompositeGAC", "Attempted to call success callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new AssertionError());
                        break;
                }
                zzpE();
                this.zzaCy = 0;
                return;
            }
            ConnectionResult connectionResult = this.zzaCv;
            if (connectionResult == null) {
                return;
            }
            if (this.zzaCy == 1) {
                zzpE();
                return;
            }
            zza(connectionResult);
            this.zzaCo.disconnect();
        } else if (this.zzaCu == null || !zzb(this.zzaCv)) {
            ConnectionResult connectionResult2 = this.zzaCu;
            if (connectionResult2 != null && this.zzaCv != null) {
                if (this.zzaCp.zzaDZ < this.zzaCo.zzaDZ) {
                    connectionResult2 = this.zzaCv;
                }
                zza(connectionResult2);
            }
        } else {
            this.zzaCp.disconnect();
            zza(this.zzaCu);
        }
    }

    private final void zzpE() {
        for (zzbfu zzmD : this.zzaCr) {
            zzmD.zzmD();
        }
        this.zzaCr.clear();
    }

    private final boolean zzpF() {
        ConnectionResult connectionResult = this.zzaCv;
        return connectionResult != null && connectionResult.getErrorCode() == 4;
    }

    @Nullable
    private final PendingIntent zzpG() {
        if (this.zzaCs == null) {
            return null;
        }
        return PendingIntent.getActivity(this.mContext, System.identityHashCode(this.zzaCn), this.zzaCs.zzmF(), 134217728);
    }

    public final ConnectionResult blockingConnect() {
        throw new UnsupportedOperationException();
    }

    public final ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    public final void connect() {
        this.zzaCy = 2;
        this.zzaCw = false;
        this.zzaCv = null;
        this.zzaCu = null;
        this.zzaCo.connect();
        this.zzaCp.connect();
    }

    public final void disconnect() {
        this.zzaCv = null;
        this.zzaCu = null;
        this.zzaCy = 0;
        this.zzaCo.disconnect();
        this.zzaCp.disconnect();
        zzpE();
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.append(str).append("authClient").println(":");
        this.zzaCp.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
        printWriter.append(str).append("anonClient").println(":");
        this.zzaCo.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
    }

    @Nullable
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        return this.zzaCq.get(api.zzpb()).equals(this.zzaCp) ? zzpF() ? new ConnectionResult(4, zzpG()) : this.zzaCp.getConnectionResult(api) : this.zzaCo.getConnectionResult(api);
    }

    public final boolean isConnected() {
        this.zzaCx.lock();
        try {
            boolean z = true;
            if (!this.zzaCo.isConnected() || (!this.zzaCp.isConnected() && !zzpF() && this.zzaCy != 1)) {
                z = false;
            }
            return z;
        } finally {
            this.zzaCx.unlock();
        }
    }

    public final boolean isConnecting() {
        this.zzaCx.lock();
        try {
            return this.zzaCy == 2;
        } finally {
            this.zzaCx.unlock();
        }
    }

    public final boolean zza(zzbfu zzbfu) {
        this.zzaCx.lock();
        try {
            if ((isConnecting() || isConnected()) && !this.zzaCp.isConnected()) {
                this.zzaCr.add(zzbfu);
                if (this.zzaCy == 0) {
                    this.zzaCy = 1;
                }
                this.zzaCv = null;
                this.zzaCp.connect();
                return true;
            }
            this.zzaCx.unlock();
            return false;
        } finally {
            this.zzaCx.unlock();
        }
    }

    public final <A extends Api.zzb, R extends Result, T extends zzbck<R, A>> T zzd(@NonNull T t) {
        if (!zzf((zzbck<? extends Result, ? extends Api.zzb>) t)) {
            return this.zzaCo.zzd(t);
        }
        if (!zzpF()) {
            return this.zzaCp.zzd(t);
        }
        t.zzr(new Status(4, (String) null, zzpG()));
        return t;
    }

    public final <A extends Api.zzb, T extends zzbck<? extends Result, A>> T zze(@NonNull T t) {
        if (!zzf((zzbck<? extends Result, ? extends Api.zzb>) t)) {
            return this.zzaCo.zze(t);
        }
        if (!zzpF()) {
            return this.zzaCp.zze(t);
        }
        t.zzr(new Status(4, (String) null, zzpG()));
        return t;
    }

    public final void zzpC() {
        this.zzaCo.zzpC();
        this.zzaCp.zzpC();
    }

    public final void zzpj() {
        this.zzaCx.lock();
        try {
            boolean isConnecting = isConnecting();
            this.zzaCp.disconnect();
            this.zzaCv = new ConnectionResult(4);
            if (isConnecting) {
                new Handler(this.zzrP).post(new zzbcx(this));
            } else {
                zzpE();
            }
        } finally {
            this.zzaCx.unlock();
        }
    }
}
