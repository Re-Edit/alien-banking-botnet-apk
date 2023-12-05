package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzca;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public final class zzbep<O extends Api.ApiOptions> implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, zzbcv {
    private final zzbcf<O> zzaAM;
    private final Api.zze zzaCA;
    private boolean zzaDC;
    private /* synthetic */ zzben zzaEo;
    private final Queue<zzbby> zzaEp = new LinkedList();
    private final Api.zzb zzaEq;
    private final zzbdf zzaEr;
    private final Set<zzbch> zzaEs = new HashSet();
    private final Map<zzbfk<?>, zzbfr> zzaEt = new HashMap();
    private final int zzaEu;
    private final zzbfv zzaEv;
    private ConnectionResult zzaEw = null;

    @WorkerThread
    public zzbep(zzben zzben, GoogleApi<O> googleApi) {
        this.zzaEo = zzben;
        this.zzaCA = googleApi.zza(zzben.mHandler.getLooper(), (zzbep<O>) this);
        Api.zze zze = this.zzaCA;
        if (zze instanceof zzca) {
            zzca zzca = (zzca) zze;
            this.zzaEq = null;
        } else {
            this.zzaEq = zze;
        }
        this.zzaAM = googleApi.zzpf();
        this.zzaEr = new zzbdf();
        this.zzaEu = googleApi.getInstanceId();
        if (this.zzaCA.zzmt()) {
            this.zzaEv = googleApi.zza(zzben.mContext, zzben.mHandler);
        } else {
            this.zzaEv = null;
        }
    }

    @WorkerThread
    private final void zzb(zzbby zzbby) {
        zzbby.zza(this.zzaEr, zzmt());
        try {
            zzbby.zza((zzbep<?>) this);
        } catch (DeadObjectException unused) {
            onConnectionSuspended(1);
            this.zzaCA.disconnect();
        }
    }

    @WorkerThread
    private final void zzi(ConnectionResult connectionResult) {
        for (zzbch zza : this.zzaEs) {
            zza.zza(this.zzaAM, connectionResult);
        }
        this.zzaEs.clear();
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zzqo() {
        zzqr();
        zzi(ConnectionResult.zzazZ);
        zzqt();
        for (zzbfr zzbfr : this.zzaEt.values()) {
            try {
                zzbfr.zzaBw.zzb(this.zzaEq, new TaskCompletionSource());
            } catch (DeadObjectException unused) {
                onConnectionSuspended(1);
                this.zzaCA.disconnect();
            } catch (RemoteException unused2) {
            }
        }
        while (this.zzaCA.isConnected() && !this.zzaEp.isEmpty()) {
            zzb(this.zzaEp.remove());
        }
        zzqu();
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zzqp() {
        zzqr();
        this.zzaDC = true;
        this.zzaEr.zzpO();
        this.zzaEo.mHandler.sendMessageDelayed(Message.obtain(this.zzaEo.mHandler, 9, this.zzaAM), this.zzaEo.zzaDE);
        this.zzaEo.mHandler.sendMessageDelayed(Message.obtain(this.zzaEo.mHandler, 11, this.zzaAM), this.zzaEo.zzaDD);
        int unused = this.zzaEo.zzaEi = -1;
    }

    @WorkerThread
    private final void zzqt() {
        if (this.zzaDC) {
            this.zzaEo.mHandler.removeMessages(11, this.zzaAM);
            this.zzaEo.mHandler.removeMessages(9, this.zzaAM);
            this.zzaDC = false;
        }
    }

    private final void zzqu() {
        this.zzaEo.mHandler.removeMessages(12, this.zzaAM);
        this.zzaEo.mHandler.sendMessageDelayed(this.zzaEo.mHandler.obtainMessage(12, this.zzaAM), this.zzaEo.zzaEg);
    }

    @WorkerThread
    public final void connect() {
        zzbr.zza(this.zzaEo.mHandler);
        if (!this.zzaCA.isConnected() && !this.zzaCA.isConnecting()) {
            if (this.zzaCA.zzpc() && this.zzaEo.zzaEi != 0) {
                zzben zzben = this.zzaEo;
                int unused = zzben.zzaEi = zzben.zzaBf.isGooglePlayServicesAvailable(this.zzaEo.mContext);
                if (this.zzaEo.zzaEi != 0) {
                    onConnectionFailed(new ConnectionResult(this.zzaEo.zzaEi, (PendingIntent) null));
                    return;
                }
            }
            zzbet zzbet = new zzbet(this.zzaEo, this.zzaCA, this.zzaAM);
            if (this.zzaCA.zzmt()) {
                this.zzaEv.zza(zzbet);
            }
            this.zzaCA.zza(zzbet);
        }
    }

    public final int getInstanceId() {
        return this.zzaEu;
    }

    /* access modifiers changed from: package-private */
    public final boolean isConnected() {
        return this.zzaCA.isConnected();
    }

    public final void onConnected(@Nullable Bundle bundle) {
        if (Looper.myLooper() == this.zzaEo.mHandler.getLooper()) {
            zzqo();
        } else {
            this.zzaEo.mHandler.post(new zzbeq(this));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0067, code lost:
        if (r4.zzaEo.zzc(r5, r4.zzaEu) != false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x006f, code lost:
        if (r5.getErrorCode() != 18) goto L_0x0074;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0071, code lost:
        r4.zzaDC = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0076, code lost:
        if (r4.zzaDC == false) goto L_0x0096;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0078, code lost:
        r4.zzaEo.mHandler.sendMessageDelayed(android.os.Message.obtain(r4.zzaEo.mHandler, 9, r4.zzaAM), r4.zzaEo.zzaDE);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0095, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0096, code lost:
        r1 = java.lang.String.valueOf(r4.zzaAM.zzpp());
        r3 = new java.lang.StringBuilder(java.lang.String.valueOf(r1).length() + 38);
        r3.append("API: ");
        r3.append(r1);
        r3.append(" is not available on this device.");
        zzt(new com.google.android.gms.common.api.Status(17, r3.toString()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        return;
     */
    @android.support.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onConnectionFailed(@android.support.annotation.NonNull com.google.android.gms.common.ConnectionResult r5) {
        /*
            r4 = this;
            com.google.android.gms.internal.zzben r0 = r4.zzaEo
            android.os.Handler r0 = r0.mHandler
            com.google.android.gms.common.internal.zzbr.zza(r0)
            com.google.android.gms.internal.zzbfv r0 = r4.zzaEv
            if (r0 == 0) goto L_0x0010
            r0.zzqG()
        L_0x0010:
            r4.zzqr()
            com.google.android.gms.internal.zzben r0 = r4.zzaEo
            r1 = -1
            int unused = r0.zzaEi = r1
            r4.zzi(r5)
            int r0 = r5.getErrorCode()
            r1 = 4
            if (r0 != r1) goto L_0x002b
            com.google.android.gms.common.api.Status r5 = com.google.android.gms.internal.zzben.zzaEf
            r4.zzt(r5)
            return
        L_0x002b:
            java.util.Queue<com.google.android.gms.internal.zzbby> r0 = r4.zzaEp
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L_0x0036
            r4.zzaEw = r5
            return
        L_0x0036:
            java.lang.Object r0 = com.google.android.gms.internal.zzben.zzuI
            monitor-enter(r0)
            com.google.android.gms.internal.zzben r1 = r4.zzaEo     // Catch:{ all -> 0x00cb }
            com.google.android.gms.internal.zzbdi r1 = r1.zzaEl     // Catch:{ all -> 0x00cb }
            if (r1 == 0) goto L_0x005e
            com.google.android.gms.internal.zzben r1 = r4.zzaEo     // Catch:{ all -> 0x00cb }
            java.util.Set r1 = r1.zzaEm     // Catch:{ all -> 0x00cb }
            com.google.android.gms.internal.zzbcf<O> r2 = r4.zzaAM     // Catch:{ all -> 0x00cb }
            boolean r1 = r1.contains(r2)     // Catch:{ all -> 0x00cb }
            if (r1 == 0) goto L_0x005e
            com.google.android.gms.internal.zzben r1 = r4.zzaEo     // Catch:{ all -> 0x00cb }
            com.google.android.gms.internal.zzbdi r1 = r1.zzaEl     // Catch:{ all -> 0x00cb }
            int r2 = r4.zzaEu     // Catch:{ all -> 0x00cb }
            r1.zzb(r5, r2)     // Catch:{ all -> 0x00cb }
            monitor-exit(r0)     // Catch:{ all -> 0x00cb }
            return
        L_0x005e:
            monitor-exit(r0)     // Catch:{ all -> 0x00cb }
            com.google.android.gms.internal.zzben r0 = r4.zzaEo
            int r1 = r4.zzaEu
            boolean r0 = r0.zzc(r5, r1)
            if (r0 != 0) goto L_0x00ca
            int r5 = r5.getErrorCode()
            r0 = 18
            if (r5 != r0) goto L_0x0074
            r5 = 1
            r4.zzaDC = r5
        L_0x0074:
            boolean r5 = r4.zzaDC
            if (r5 == 0) goto L_0x0096
            com.google.android.gms.internal.zzben r5 = r4.zzaEo
            android.os.Handler r5 = r5.mHandler
            com.google.android.gms.internal.zzben r0 = r4.zzaEo
            android.os.Handler r0 = r0.mHandler
            r1 = 9
            com.google.android.gms.internal.zzbcf<O> r2 = r4.zzaAM
            android.os.Message r0 = android.os.Message.obtain(r0, r1, r2)
            com.google.android.gms.internal.zzben r1 = r4.zzaEo
            long r1 = r1.zzaDE
            r5.sendMessageDelayed(r0, r1)
            return
        L_0x0096:
            com.google.android.gms.common.api.Status r5 = new com.google.android.gms.common.api.Status
            r0 = 17
            com.google.android.gms.internal.zzbcf<O> r1 = r4.zzaAM
            java.lang.String r1 = r1.zzpp()
            java.lang.String r1 = java.lang.String.valueOf(r1)
            java.lang.String r2 = java.lang.String.valueOf(r1)
            int r2 = r2.length()
            int r2 = r2 + 38
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>(r2)
            java.lang.String r2 = "API: "
            r3.append(r2)
            r3.append(r1)
            java.lang.String r1 = " is not available on this device."
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            r5.<init>(r0, r1)
            r4.zzt(r5)
        L_0x00ca:
            return
        L_0x00cb:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00cb }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbep.onConnectionFailed(com.google.android.gms.common.ConnectionResult):void");
    }

    public final void onConnectionSuspended(int i) {
        if (Looper.myLooper() == this.zzaEo.mHandler.getLooper()) {
            zzqp();
        } else {
            this.zzaEo.mHandler.post(new zzber(this));
        }
    }

    @WorkerThread
    public final void resume() {
        zzbr.zza(this.zzaEo.mHandler);
        if (this.zzaDC) {
            connect();
        }
    }

    @WorkerThread
    public final void signOut() {
        zzbr.zza(this.zzaEo.mHandler);
        zzt(zzben.zzaEe);
        this.zzaEr.zzpN();
        for (zzbfk<?> zzbcd : this.zzaEt.keySet()) {
            zza((zzbby) new zzbcd(zzbcd, new TaskCompletionSource()));
        }
        zzi(new ConnectionResult(4));
        this.zzaCA.disconnect();
    }

    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean z) {
        if (Looper.myLooper() == this.zzaEo.mHandler.getLooper()) {
            onConnectionFailed(connectionResult);
        } else {
            this.zzaEo.mHandler.post(new zzbes(this, connectionResult));
        }
    }

    @WorkerThread
    public final void zza(zzbby zzbby) {
        zzbr.zza(this.zzaEo.mHandler);
        if (this.zzaCA.isConnected()) {
            zzb(zzbby);
            zzqu();
            return;
        }
        this.zzaEp.add(zzbby);
        ConnectionResult connectionResult = this.zzaEw;
        if (connectionResult == null || !connectionResult.hasResolution()) {
            connect();
        } else {
            onConnectionFailed(this.zzaEw);
        }
    }

    @WorkerThread
    public final void zza(zzbch zzbch) {
        zzbr.zza(this.zzaEo.mHandler);
        this.zzaEs.add(zzbch);
    }

    @WorkerThread
    public final void zzh(@NonNull ConnectionResult connectionResult) {
        zzbr.zza(this.zzaEo.mHandler);
        this.zzaCA.disconnect();
        onConnectionFailed(connectionResult);
    }

    public final boolean zzmt() {
        return this.zzaCA.zzmt();
    }

    public final Api.zze zzpH() {
        return this.zzaCA;
    }

    @WorkerThread
    public final void zzqb() {
        zzbr.zza(this.zzaEo.mHandler);
        if (this.zzaDC) {
            zzqt();
            zzt(this.zzaEo.zzaBf.isGooglePlayServicesAvailable(this.zzaEo.mContext) == 18 ? new Status(8, "Connection timed out while waiting for Google Play services update to complete.") : new Status(8, "API failed to connect while resuming due to an unknown error."));
            this.zzaCA.disconnect();
        }
    }

    public final Map<zzbfk<?>, zzbfr> zzqq() {
        return this.zzaEt;
    }

    @WorkerThread
    public final void zzqr() {
        zzbr.zza(this.zzaEo.mHandler);
        this.zzaEw = null;
    }

    @WorkerThread
    public final ConnectionResult zzqs() {
        zzbr.zza(this.zzaEo.mHandler);
        return this.zzaEw;
    }

    @WorkerThread
    public final void zzqv() {
        zzbr.zza(this.zzaEo.mHandler);
        if (this.zzaCA.isConnected() && this.zzaEt.size() == 0) {
            if (this.zzaEr.zzpM()) {
                zzqu();
            } else {
                this.zzaCA.disconnect();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final zzcuw zzqw() {
        zzbfv zzbfv = this.zzaEv;
        if (zzbfv == null) {
            return null;
        }
        return zzbfv.zzqw();
    }

    @WorkerThread
    public final void zzt(Status status) {
        zzbr.zza(this.zzaEo.mHandler);
        for (zzbby zzp : this.zzaEp) {
            zzp.zzp(status);
        }
        this.zzaEp.clear();
    }
}
