package com.google.android.gms.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.internal.zzy;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzam;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzbs;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.common.internal.zzm;
import com.google.android.gms.common.internal.zzq;

public final class zzcvg extends zzaa<zzcve> implements zzcuw {
    private final zzq zzaCC;
    private Integer zzaHp;
    private final Bundle zzbCP;
    private final boolean zzbCX;

    public zzcvg(Context context, Looper looper, boolean z, zzq zzq, Bundle bundle, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 44, zzq, connectionCallbacks, onConnectionFailedListener);
        this.zzbCX = z;
        this.zzaCC = zzq;
        this.zzbCP = bundle;
        this.zzaHp = zzq.zzrs();
    }

    public zzcvg(Context context, Looper looper, boolean z, zzq zzq, zzcux zzcux, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, true, zzq, zza(zzq), connectionCallbacks, onConnectionFailedListener);
    }

    public static Bundle zza(zzq zzq) {
        zzcux zzrr = zzq.zzrr();
        Integer zzrs = zzq.zzrs();
        Bundle bundle = new Bundle();
        bundle.putParcelable("com.google.android.gms.signin.internal.clientRequestedAccount", zzq.getAccount());
        if (zzrs != null) {
            bundle.putInt("com.google.android.gms.common.internal.ClientSettings.sessionId", zzrs.intValue());
        }
        if (zzrr != null) {
            bundle.putBoolean("com.google.android.gms.signin.internal.offlineAccessRequested", zzrr.zzAp());
            bundle.putBoolean("com.google.android.gms.signin.internal.idTokenRequested", zzrr.isIdTokenRequested());
            bundle.putString("com.google.android.gms.signin.internal.serverClientId", zzrr.getServerClientId());
            bundle.putBoolean("com.google.android.gms.signin.internal.usePromptModeForAuthCode", true);
            bundle.putBoolean("com.google.android.gms.signin.internal.forceCodeForRefreshToken", zzrr.zzAq());
            bundle.putString("com.google.android.gms.signin.internal.hostedDomain", zzrr.zzAr());
            bundle.putBoolean("com.google.android.gms.signin.internal.waitForAccessTokenRefresh", zzrr.zzAs());
            if (zzrr.zzAt() != null) {
                bundle.putLong("com.google.android.gms.signin.internal.authApiSignInModuleVersion", zzrr.zzAt().longValue());
            }
            if (zzrr.zzAu() != null) {
                bundle.putLong("com.google.android.gms.signin.internal.realClientLibraryVersion", zzrr.zzAu().longValue());
            }
        }
        return bundle;
    }

    public final void connect() {
        zza((zzj) new zzm(this));
    }

    public final void zzAo() {
        try {
            ((zzcve) zzrd()).zzbu(this.zzaHp.intValue());
        } catch (RemoteException unused) {
            Log.w("SignInClientImpl", "Remote service probably died when clearAccountFromSessionStore is called");
        }
    }

    public final void zza(zzam zzam, boolean z) {
        try {
            ((zzcve) zzrd()).zza(zzam, this.zzaHp.intValue(), z);
        } catch (RemoteException unused) {
            Log.w("SignInClientImpl", "Remote service probably died when saveDefaultAccount is called");
        }
    }

    public final void zza(zzcvc zzcvc) {
        zzbr.zzb(zzcvc, (Object) "Expecting a valid ISignInCallbacks");
        try {
            Account zzrj = this.zzaCC.zzrj();
            GoogleSignInAccount googleSignInAccount = null;
            if ("<<default account>>".equals(zzrj.name)) {
                googleSignInAccount = zzy.zzaj(getContext()).zzmL();
            }
            ((zzcve) zzrd()).zza(new zzcvh(new zzbs(zzrj, this.zzaHp.intValue(), googleSignInAccount)), zzcvc);
        } catch (RemoteException e) {
            Log.w("SignInClientImpl", "Remote service probably died when signIn is called");
            try {
                zzcvc.zzb(new zzcvj(8));
            } catch (RemoteException unused) {
                Log.wtf("SignInClientImpl", "ISignInCallbacks#onSignInComplete should be executed from the same process, unexpected RemoteException.", e);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.signin.internal.ISignInService");
        return queryLocalInterface instanceof zzcve ? (zzcve) queryLocalInterface : new zzcvf(iBinder);
    }

    /* access modifiers changed from: protected */
    public final String zzda() {
        return "com.google.android.gms.signin.service.START";
    }

    /* access modifiers changed from: protected */
    public final String zzdb() {
        return "com.google.android.gms.signin.internal.ISignInService";
    }

    /* access modifiers changed from: protected */
    public final Bundle zzmm() {
        if (!getContext().getPackageName().equals(this.zzaCC.zzro())) {
            this.zzbCP.putString("com.google.android.gms.signin.internal.realClientPackageName", this.zzaCC.zzro());
        }
        return this.zzbCP;
    }

    public final boolean zzmt() {
        return this.zzbCX;
    }
}
