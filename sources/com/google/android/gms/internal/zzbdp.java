package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzam;
import com.google.android.gms.common.internal.zzbu;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.zze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;

public final class zzbdp implements zzbei {
    /* access modifiers changed from: private */
    public final Context mContext;
    private final Api.zza<? extends zzcuw, zzcux> zzaBg;
    private final zzq zzaCC;
    private final Map<Api<?>, Boolean> zzaCF;
    /* access modifiers changed from: private */
    public final zze zzaCH;
    private ConnectionResult zzaCQ;
    /* access modifiers changed from: private */
    public final Lock zzaCx;
    /* access modifiers changed from: private */
    public final zzbej zzaDb;
    private int zzaDe;
    private int zzaDf = 0;
    private int zzaDg;
    private final Bundle zzaDh = new Bundle();
    private final Set<Api.zzc> zzaDi = new HashSet();
    /* access modifiers changed from: private */
    public zzcuw zzaDj;
    private boolean zzaDk;
    /* access modifiers changed from: private */
    public boolean zzaDl;
    private boolean zzaDm;
    /* access modifiers changed from: private */
    public zzam zzaDn;
    private boolean zzaDo;
    private boolean zzaDp;
    private ArrayList<Future<?>> zzaDq = new ArrayList<>();

    public zzbdp(zzbej zzbej, zzq zzq, Map<Api<?>, Boolean> map, zze zze, Api.zza<? extends zzcuw, zzcux> zza, Lock lock, Context context) {
        this.zzaDb = zzbej;
        this.zzaCC = zzq;
        this.zzaCF = map;
        this.zzaCH = zze;
        this.zzaBg = zza;
        this.zzaCx = lock;
        this.mContext = context;
    }

    /* access modifiers changed from: private */
    public final void zza(zzcvj zzcvj) {
        if (zzan(0)) {
            ConnectionResult zzpx = zzcvj.zzpx();
            if (zzpx.isSuccess()) {
                zzbu zzAv = zzcvj.zzAv();
                ConnectionResult zzpx2 = zzAv.zzpx();
                if (!zzpx2.isSuccess()) {
                    String valueOf = String.valueOf(zzpx2);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 48);
                    sb.append("Sign-in succeeded with resolve account failure: ");
                    sb.append(valueOf);
                    Log.wtf("GoogleApiClientConnecting", sb.toString(), new Exception());
                    zze(zzpx2);
                    return;
                }
                this.zzaDm = true;
                this.zzaDn = zzAv.zzrG();
                this.zzaDo = zzAv.zzrH();
                this.zzaDp = zzAv.zzrI();
                zzpV();
            } else if (zzd(zzpx)) {
                zzpX();
                zzpV();
            } else {
                zze(zzpx);
            }
        }
    }

    private final void zzad(boolean z) {
        zzcuw zzcuw = this.zzaDj;
        if (zzcuw != null) {
            if (zzcuw.isConnected() && z) {
                this.zzaDj.zzAo();
            }
            this.zzaDj.disconnect();
            this.zzaDn = null;
        }
    }

    /* access modifiers changed from: private */
    public final boolean zzan(int i) {
        if (this.zzaDf == i) {
            return true;
        }
        Log.w("GoogleApiClientConnecting", this.zzaDb.zzaCn.zzqe());
        String valueOf = String.valueOf(this);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 23);
        sb.append("Unexpected callback in ");
        sb.append(valueOf);
        Log.w("GoogleApiClientConnecting", sb.toString());
        int i2 = this.zzaDg;
        StringBuilder sb2 = new StringBuilder(33);
        sb2.append("mRemainingConnections=");
        sb2.append(i2);
        Log.w("GoogleApiClientConnecting", sb2.toString());
        String valueOf2 = String.valueOf(zzao(this.zzaDf));
        String valueOf3 = String.valueOf(zzao(i));
        StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf2).length() + 70 + String.valueOf(valueOf3).length());
        sb3.append("GoogleApiClient connecting is in step ");
        sb3.append(valueOf2);
        sb3.append(" but received callback for step ");
        sb3.append(valueOf3);
        Log.wtf("GoogleApiClientConnecting", sb3.toString(), new Exception());
        zze(new ConnectionResult(8, (PendingIntent) null));
        return false;
    }

    private static String zzao(int i) {
        switch (i) {
            case 0:
                return "STEP_SERVICE_BINDINGS_AND_SIGN_IN";
            case 1:
                return "STEP_GETTING_REMOTE_SERVICE";
            default:
                return "UNKNOWN";
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0022, code lost:
        if ((r5.hasResolution() || r4.zzaCH.zzak(r5.getErrorCode()) != null) != false) goto L_0x0024;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzb(com.google.android.gms.common.ConnectionResult r5, com.google.android.gms.common.api.Api<?> r6, boolean r7) {
        /*
            r4 = this;
            com.google.android.gms.common.api.Api$zzd r0 = r6.zzoZ()
            int r0 = r0.getPriority()
            r1 = 0
            r2 = 1
            if (r7 == 0) goto L_0x0024
            boolean r7 = r5.hasResolution()
            if (r7 == 0) goto L_0x0014
        L_0x0012:
            r7 = 1
            goto L_0x0022
        L_0x0014:
            com.google.android.gms.common.zze r7 = r4.zzaCH
            int r3 = r5.getErrorCode()
            android.content.Intent r7 = r7.zzak(r3)
            if (r7 == 0) goto L_0x0021
            goto L_0x0012
        L_0x0021:
            r7 = 0
        L_0x0022:
            if (r7 == 0) goto L_0x002d
        L_0x0024:
            com.google.android.gms.common.ConnectionResult r7 = r4.zzaCQ
            if (r7 == 0) goto L_0x002c
            int r7 = r4.zzaDe
            if (r0 >= r7) goto L_0x002d
        L_0x002c:
            r1 = 1
        L_0x002d:
            if (r1 == 0) goto L_0x0033
            r4.zzaCQ = r5
            r4.zzaDe = r0
        L_0x0033:
            com.google.android.gms.internal.zzbej r7 = r4.zzaDb
            java.util.Map<com.google.android.gms.common.api.Api$zzc<?>, com.google.android.gms.common.ConnectionResult> r7 = r7.zzaDW
            com.google.android.gms.common.api.Api$zzc r6 = r6.zzpb()
            r7.put(r6, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbdp.zzb(com.google.android.gms.common.ConnectionResult, com.google.android.gms.common.api.Api, boolean):void");
    }

    /* access modifiers changed from: private */
    public final boolean zzd(ConnectionResult connectionResult) {
        return this.zzaDk && !connectionResult.hasResolution();
    }

    /* access modifiers changed from: private */
    public final void zze(ConnectionResult connectionResult) {
        zzpY();
        zzad(!connectionResult.hasResolution());
        this.zzaDb.zzg(connectionResult);
        this.zzaDb.zzaEa.zzc(connectionResult);
    }

    /* access modifiers changed from: private */
    public final boolean zzpU() {
        ConnectionResult connectionResult;
        this.zzaDg--;
        int i = this.zzaDg;
        if (i > 0) {
            return false;
        }
        if (i < 0) {
            Log.w("GoogleApiClientConnecting", this.zzaDb.zzaCn.zzqe());
            Log.wtf("GoogleApiClientConnecting", "GoogleApiClient received too many callbacks for the given step. Clients may be in an unexpected state; GoogleApiClient will now disconnect.", new Exception());
            connectionResult = new ConnectionResult(8, (PendingIntent) null);
        } else {
            connectionResult = this.zzaCQ;
            if (connectionResult == null) {
                return true;
            }
            this.zzaDb.zzaDZ = this.zzaDe;
        }
        zze(connectionResult);
        return false;
    }

    /* access modifiers changed from: private */
    public final void zzpV() {
        if (this.zzaDg == 0) {
            if (!this.zzaDl || this.zzaDm) {
                ArrayList arrayList = new ArrayList();
                this.zzaDf = 1;
                this.zzaDg = this.zzaDb.zzaDH.size();
                for (Api.zzc next : this.zzaDb.zzaDH.keySet()) {
                    if (!this.zzaDb.zzaDW.containsKey(next)) {
                        arrayList.add(this.zzaDb.zzaDH.get(next));
                    } else if (zzpU()) {
                        zzpW();
                    }
                }
                if (!arrayList.isEmpty()) {
                    this.zzaDq.add(zzbem.zzqh().submit(new zzbdv(this, arrayList)));
                }
            }
        }
    }

    private final void zzpW() {
        this.zzaDb.zzqg();
        zzbem.zzqh().execute(new zzbdq(this));
        zzcuw zzcuw = this.zzaDj;
        if (zzcuw != null) {
            if (this.zzaDo) {
                zzcuw.zza(this.zzaDn, this.zzaDp);
            }
            zzad(false);
        }
        for (Api.zzc<?> zzc : this.zzaDb.zzaDW.keySet()) {
            this.zzaDb.zzaDH.get(zzc).disconnect();
        }
        this.zzaDb.zzaEa.zzm(this.zzaDh.isEmpty() ? null : this.zzaDh);
    }

    /* access modifiers changed from: private */
    public final void zzpX() {
        this.zzaDl = false;
        this.zzaDb.zzaCn.zzaDI = Collections.emptySet();
        for (Api.zzc next : this.zzaDi) {
            if (!this.zzaDb.zzaDW.containsKey(next)) {
                this.zzaDb.zzaDW.put(next, new ConnectionResult(17, (PendingIntent) null));
            }
        }
    }

    private final void zzpY() {
        ArrayList arrayList = this.zzaDq;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Future) obj).cancel(true);
        }
        this.zzaDq.clear();
    }

    /* access modifiers changed from: private */
    public final Set<Scope> zzpZ() {
        zzq zzq = this.zzaCC;
        if (zzq == null) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet(zzq.zzrl());
        Map<Api<?>, zzr> zzrn = this.zzaCC.zzrn();
        for (Api next : zzrn.keySet()) {
            if (!this.zzaDb.zzaDW.containsKey(next.zzpb())) {
                hashSet.addAll(zzrn.get(next).zzamg);
            }
        }
        return hashSet;
    }

    public final void begin() {
        this.zzaDb.zzaDW.clear();
        this.zzaDl = false;
        this.zzaCQ = null;
        this.zzaDf = 0;
        this.zzaDk = true;
        this.zzaDm = false;
        this.zzaDo = false;
        HashMap hashMap = new HashMap();
        boolean z = false;
        for (Api next : this.zzaCF.keySet()) {
            Api.zze zze = this.zzaDb.zzaDH.get(next.zzpb());
            z |= next.zzoZ().getPriority() == 1;
            boolean booleanValue = this.zzaCF.get(next).booleanValue();
            if (zze.zzmt()) {
                this.zzaDl = true;
                if (booleanValue) {
                    this.zzaDi.add(next.zzpb());
                } else {
                    this.zzaDk = false;
                }
            }
            hashMap.put(zze, new zzbdr(this, next, booleanValue));
        }
        if (z) {
            this.zzaDl = false;
        }
        if (this.zzaDl) {
            this.zzaCC.zzc(Integer.valueOf(System.identityHashCode(this.zzaDb.zzaCn)));
            zzbdy zzbdy = new zzbdy(this, (zzbdq) null);
            Api.zza<? extends zzcuw, zzcux> zza = this.zzaBg;
            Context context = this.mContext;
            Looper looper = this.zzaDb.zzaCn.getLooper();
            zzq zzq = this.zzaCC;
            this.zzaDj = (zzcuw) zza.zza(context, looper, zzq, zzq.zzrr(), zzbdy, zzbdy);
        }
        this.zzaDg = this.zzaDb.zzaDH.size();
        this.zzaDq.add(zzbem.zzqh().submit(new zzbds(this, hashMap)));
    }

    public final void connect() {
    }

    public final boolean disconnect() {
        zzpY();
        zzad(true);
        this.zzaDb.zzg((ConnectionResult) null);
        return true;
    }

    public final void onConnected(Bundle bundle) {
        if (zzan(1)) {
            if (bundle != null) {
                this.zzaDh.putAll(bundle);
            }
            if (zzpU()) {
                zzpW();
            }
        }
    }

    public final void onConnectionSuspended(int i) {
        zze(new ConnectionResult(8, (PendingIntent) null));
    }

    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean z) {
        if (zzan(1)) {
            zzb(connectionResult, api, z);
            if (zzpU()) {
                zzpW();
            }
        }
    }

    public final <A extends Api.zzb, R extends Result, T extends zzbck<R, A>> T zzd(T t) {
        this.zzaDb.zzaCn.zzaCL.add(t);
        return t;
    }

    public final <A extends Api.zzb, T extends zzbck<? extends Result, A>> T zze(T t) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }
}
