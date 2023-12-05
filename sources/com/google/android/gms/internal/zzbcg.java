package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzbr;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class zzbcg extends zzbcm {
    private final SparseArray<zza> zzaBD = new SparseArray<>();

    class zza implements GoogleApiClient.OnConnectionFailedListener {
        public final int zzaBE;
        public final GoogleApiClient zzaBF;
        public final GoogleApiClient.OnConnectionFailedListener zzaBG;

        public zza(int i, GoogleApiClient googleApiClient, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            this.zzaBE = i;
            this.zzaBF = googleApiClient;
            this.zzaBG = onConnectionFailedListener;
            googleApiClient.registerConnectionFailedListener(this);
        }

        public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            String valueOf = String.valueOf(connectionResult);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 27);
            sb.append("beginFailureResolution for ");
            sb.append(valueOf);
            Log.d("AutoManageHelper", sb.toString());
            zzbcg.this.zzb(connectionResult, this.zzaBE);
        }
    }

    private zzbcg(zzbff zzbff) {
        super(zzbff);
        this.zzaEI.zza("AutoManageHelper", (zzbfe) this);
    }

    public static zzbcg zza(zzbfd zzbfd) {
        zzbff zzb = zzb(zzbfd);
        zzbcg zzbcg = (zzbcg) zzb.zza("AutoManageHelper", zzbcg.class);
        return zzbcg != null ? zzbcg : new zzbcg(zzb);
    }

    @Nullable
    private final zza zzam(int i) {
        if (this.zzaBD.size() <= i) {
            return null;
        }
        SparseArray<zza> sparseArray = this.zzaBD;
        return sparseArray.get(sparseArray.keyAt(i));
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        for (int i = 0; i < this.zzaBD.size(); i++) {
            zza zzam = zzam(i);
            if (zzam != null) {
                printWriter.append(str).append("GoogleApiClient #").print(zzam.zzaBE);
                printWriter.println(":");
                zzam.zzaBF.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
            }
        }
    }

    public final void onStart() {
        super.onStart();
        boolean z = this.mStarted;
        String valueOf = String.valueOf(this.zzaBD);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 14);
        sb.append("onStart ");
        sb.append(z);
        sb.append(" ");
        sb.append(valueOf);
        Log.d("AutoManageHelper", sb.toString());
        if (this.zzaBP.get() == null) {
            for (int i = 0; i < this.zzaBD.size(); i++) {
                zza zzam = zzam(i);
                if (zzam != null) {
                    zzam.zzaBF.connect();
                }
            }
        }
    }

    public final void onStop() {
        super.onStop();
        for (int i = 0; i < this.zzaBD.size(); i++) {
            zza zzam = zzam(i);
            if (zzam != null) {
                zzam.zzaBF.disconnect();
            }
        }
    }

    public final void zza(int i, GoogleApiClient googleApiClient, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        zzbr.zzb(googleApiClient, (Object) "GoogleApiClient instance cannot be null");
        boolean z = this.zzaBD.indexOfKey(i) < 0;
        StringBuilder sb = new StringBuilder(54);
        sb.append("Already managing a GoogleApiClient with id ");
        sb.append(i);
        zzbr.zza(z, (Object) sb.toString());
        zzbcn zzbcn = (zzbcn) this.zzaBP.get();
        boolean z2 = this.mStarted;
        String valueOf = String.valueOf(zzbcn);
        StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 49);
        sb2.append("starting AutoManage for client ");
        sb2.append(i);
        sb2.append(" ");
        sb2.append(z2);
        sb2.append(" ");
        sb2.append(valueOf);
        Log.d("AutoManageHelper", sb2.toString());
        this.zzaBD.put(i, new zza(i, googleApiClient, onConnectionFailedListener));
        if (this.mStarted && zzbcn == null) {
            String valueOf2 = String.valueOf(googleApiClient);
            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf2).length() + 11);
            sb3.append("connecting ");
            sb3.append(valueOf2);
            Log.d("AutoManageHelper", sb3.toString());
            googleApiClient.connect();
        }
    }

    /* access modifiers changed from: protected */
    public final void zza(ConnectionResult connectionResult, int i) {
        Log.w("AutoManageHelper", "Unresolved error while connecting client. Stopping auto-manage.");
        if (i < 0) {
            Log.wtf("AutoManageHelper", "AutoManageLifecycleHelper received onErrorResolutionFailed callback but no failing client ID is set", new Exception());
            return;
        }
        zza zza2 = this.zzaBD.get(i);
        if (zza2 != null) {
            zzal(i);
            GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = zza2.zzaBG;
            if (onConnectionFailedListener != null) {
                onConnectionFailedListener.onConnectionFailed(connectionResult);
            }
        }
    }

    public final void zzal(int i) {
        zza zza2 = this.zzaBD.get(i);
        this.zzaBD.remove(i);
        if (zza2 != null) {
            zza2.zzaBF.unregisterConnectionFailedListener(zza2);
            zza2.zzaBF.disconnect();
        }
    }

    /* access modifiers changed from: protected */
    public final void zzpq() {
        for (int i = 0; i < this.zzaBD.size(); i++) {
            zza zzam = zzam(i);
            if (zzam != null) {
                zzam.zzaBF.connect();
            }
        }
    }
}
