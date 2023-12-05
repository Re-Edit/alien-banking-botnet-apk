package com.google.android.gms.common.api;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.internal.zzbce;
import com.google.android.gms.internal.zzbcf;
import com.google.android.gms.internal.zzbck;
import com.google.android.gms.internal.zzbdi;
import com.google.android.gms.internal.zzben;
import com.google.android.gms.internal.zzbep;
import com.google.android.gms.internal.zzbev;
import com.google.android.gms.internal.zzbfv;
import com.google.android.gms.internal.zzbfy;
import com.google.android.gms.internal.zzbgc;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

public class GoogleApi<O extends Api.ApiOptions> {
    private final Context mContext;
    private final int mId;
    private final O zzaAL;
    private final zzbcf<O> zzaAM;
    private final GoogleApiClient zzaAN;
    private final zzbfy zzaAO;
    protected final zzben zzaAP;
    private final Account zzajd;
    private final Api<O> zzayY;
    private final Looper zzrP;

    public static class zza {
        public static final zza zzaAQ = new zzd().zzph();
        public final Account account;
        public final zzbfy zzaAR;
        public final Looper zzaAS;

        private zza(zzbfy zzbfy, Account account2, Looper looper) {
            this.zzaAR = zzbfy;
            this.account = account2;
            this.zzaAS = looper;
        }
    }

    @MainThread
    private GoogleApi(@NonNull Activity activity, Api<O> api, O o, zza zza2) {
        zzbr.zzb(activity, (Object) "Null activity is not permitted.");
        zzbr.zzb(api, (Object) "Api must not be null.");
        zzbr.zzb(zza2, (Object) "Settings must not be null; use Settings.DEFAULT_SETTINGS instead.");
        this.mContext = activity.getApplicationContext();
        this.zzayY = api;
        this.zzaAL = null;
        this.zzrP = zza2.zzaAS;
        this.zzaAM = zzbcf.zza(this.zzayY, this.zzaAL);
        this.zzaAN = new zzbev(this);
        this.zzaAP = zzben.zzay(this.mContext);
        this.mId = this.zzaAP.zzqk();
        this.zzaAO = zza2.zzaAR;
        this.zzajd = zza2.account;
        zzbdi.zza(activity, this.zzaAP, this.zzaAM);
        this.zzaAP.zzb((GoogleApi<?>) this);
    }

    @Deprecated
    public GoogleApi(@NonNull Activity activity, Api<O> api, O o, zzbfy zzbfy) {
        this(activity, api, (Api.ApiOptions) null, new zzd().zza(zzbfy).zza(activity.getMainLooper()).zzph());
    }

    protected GoogleApi(@NonNull Context context, Api<O> api, Looper looper) {
        zzbr.zzb(context, (Object) "Null context is not permitted.");
        zzbr.zzb(api, (Object) "Api must not be null.");
        zzbr.zzb(looper, (Object) "Looper must not be null.");
        this.mContext = context.getApplicationContext();
        this.zzayY = api;
        this.zzaAL = null;
        this.zzrP = looper;
        this.zzaAM = zzbcf.zzb(api);
        this.zzaAN = new zzbev(this);
        this.zzaAP = zzben.zzay(this.mContext);
        this.mId = this.zzaAP.zzqk();
        this.zzaAO = new zzbce();
        this.zzajd = null;
    }

    @Deprecated
    public GoogleApi(@NonNull Context context, Api<O> api, O o, Looper looper, zzbfy zzbfy) {
        this(context, api, (Api.ApiOptions) null, new zzd().zza(looper).zza(zzbfy).zzph());
    }

    public GoogleApi(@NonNull Context context, Api<O> api, O o, zza zza2) {
        zzbr.zzb(context, (Object) "Null context is not permitted.");
        zzbr.zzb(api, (Object) "Api must not be null.");
        zzbr.zzb(zza2, (Object) "Settings must not be null; use Settings.DEFAULT_SETTINGS instead.");
        this.mContext = context.getApplicationContext();
        this.zzayY = api;
        this.zzaAL = o;
        this.zzrP = zza2.zzaAS;
        this.zzaAM = zzbcf.zza(this.zzayY, this.zzaAL);
        this.zzaAN = new zzbev(this);
        this.zzaAP = zzben.zzay(this.mContext);
        this.mId = this.zzaAP.zzqk();
        this.zzaAO = zza2.zzaAR;
        this.zzajd = zza2.account;
        this.zzaAP.zzb((GoogleApi<?>) this);
    }

    @Deprecated
    public GoogleApi(@NonNull Context context, Api<O> api, O o, zzbfy zzbfy) {
        this(context, api, o, new zzd().zza(zzbfy).zzph());
    }

    private final <A extends Api.zzb, T extends zzbck<? extends Result, A>> T zza(int i, @NonNull T t) {
        t.zzpA();
        this.zzaAP.zza(this, i, (zzbck<? extends Result, Api.zzb>) t);
        return t;
    }

    private final <TResult, A extends Api.zzb> Task<TResult> zza(int i, @NonNull zzbgc<A, TResult> zzbgc) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.zzaAP.zza(this, i, zzbgc, taskCompletionSource, this.zzaAO);
        return taskCompletionSource.getTask();
    }

    public final Context getApplicationContext() {
        return this.mContext;
    }

    public final int getInstanceId() {
        return this.mId;
    }

    public final Looper getLooper() {
        return this.zzrP;
    }

    @WorkerThread
    public Api.zze zza(Looper looper, zzbep<O> zzbep) {
        return this.zzayY.zzpa().zza(this.mContext, looper, new GoogleApiClient.Builder(this.mContext).zze(this.zzajd).zzpl(), this.zzaAL, zzbep, zzbep);
    }

    public final <A extends Api.zzb, T extends zzbck<? extends Result, A>> T zza(@NonNull T t) {
        return zza(0, t);
    }

    public zzbfv zza(Context context, Handler handler) {
        return new zzbfv(context, handler);
    }

    public final <TResult, A extends Api.zzb> Task<TResult> zza(zzbgc<A, TResult> zzbgc) {
        return zza(0, zzbgc);
    }

    public final <A extends Api.zzb, T extends zzbck<? extends Result, A>> T zzb(@NonNull T t) {
        return zza(1, t);
    }

    public final <TResult, A extends Api.zzb> Task<TResult> zzb(zzbgc<A, TResult> zzbgc) {
        return zza(1, zzbgc);
    }

    public final <A extends Api.zzb, T extends zzbck<? extends Result, A>> T zzc(@NonNull T t) {
        return zza(2, t);
    }

    public final Api<O> zzpe() {
        return this.zzayY;
    }

    public final zzbcf<O> zzpf() {
        return this.zzaAM;
    }

    public final GoogleApiClient zzpg() {
        return this.zzaAN;
    }
}
