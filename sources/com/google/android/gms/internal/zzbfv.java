package com.google.android.gms.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.BinderThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;
import android.view.View;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.internal.zzy;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzbu;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzr;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class zzbfv extends zzcvb implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static Api.zza<? extends zzcuw, zzcux> zzaEX = zzcus.zzajU;
    private final Context mContext;
    private final Handler mHandler;
    private final Api.zza<? extends zzcuw, zzcux> zzaAz;
    private zzq zzaCC;
    private zzcuw zzaDj;
    private final boolean zzaEY;
    private zzbfx zzaEZ;
    private Set<Scope> zzamg;

    @WorkerThread
    public zzbfv(Context context, Handler handler) {
        this.mContext = context;
        this.mHandler = handler;
        this.zzaAz = zzaEX;
        this.zzaEY = true;
    }

    @WorkerThread
    public zzbfv(Context context, Handler handler, @NonNull zzq zzq, Api.zza<? extends zzcuw, zzcux> zza) {
        this.mContext = context;
        this.mHandler = handler;
        this.zzaCC = (zzq) zzbr.zzb(zzq, (Object) "ClientSettings must not be null");
        this.zzamg = zzq.zzrl();
        this.zzaAz = zza;
        this.zzaEY = false;
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zzc(zzcvj zzcvj) {
        ConnectionResult zzpx = zzcvj.zzpx();
        if (zzpx.isSuccess()) {
            zzbu zzAv = zzcvj.zzAv();
            zzpx = zzAv.zzpx();
            if (!zzpx.isSuccess()) {
                String valueOf = String.valueOf(zzpx);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 48);
                sb.append("Sign-in succeeded with resolve account failure: ");
                sb.append(valueOf);
                Log.wtf("SignInCoordinator", sb.toString(), new Exception());
            } else {
                this.zzaEZ.zzb(zzAv.zzrG(), this.zzamg);
                this.zzaDj.disconnect();
            }
        }
        this.zzaEZ.zzh(zzpx);
        this.zzaDj.disconnect();
    }

    @WorkerThread
    public final void onConnected(@Nullable Bundle bundle) {
        this.zzaDj.zza(this);
    }

    @WorkerThread
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zzaEZ.zzh(connectionResult);
    }

    @WorkerThread
    public final void onConnectionSuspended(int i) {
        this.zzaDj.disconnect();
    }

    @WorkerThread
    public final void zza(zzbfx zzbfx) {
        zzcuw zzcuw = this.zzaDj;
        if (zzcuw != null) {
            zzcuw.disconnect();
        }
        if (this.zzaEY) {
            GoogleSignInOptions zzmM = zzy.zzaj(this.mContext).zzmM();
            this.zzamg = zzmM == null ? new HashSet() : new HashSet(zzmM.zzmy());
            this.zzaCC = new zzq((Account) null, this.zzamg, (Map<Api<?>, zzr>) null, 0, (View) null, (String) null, (String) null, zzcux.zzbCQ);
        }
        this.zzaCC.zzc(Integer.valueOf(System.identityHashCode(this)));
        Api.zza<? extends zzcuw, zzcux> zza = this.zzaAz;
        Context context = this.mContext;
        Looper looper = this.mHandler.getLooper();
        zzq zzq = this.zzaCC;
        this.zzaDj = (zzcuw) zza.zza(context, looper, zzq, zzq.zzrr(), this, this);
        this.zzaEZ = zzbfx;
        this.zzaDj.connect();
    }

    @BinderThread
    public final void zzb(zzcvj zzcvj) {
        this.mHandler.post(new zzbfw(this, zzcvj));
    }

    public final void zzqG() {
        zzcuw zzcuw = this.zzaDj;
        if (zzcuw != null) {
            zzcuw.disconnect();
        }
    }

    public final zzcuw zzqw() {
        return this.zzaDj;
    }
}
