package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.support.annotation.BinderThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;

public final class zzn extends zze {
    private /* synthetic */ zzd zzaHg;
    private IBinder zzaHk;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    @BinderThread
    public zzn(zzd zzd, int i, IBinder iBinder, Bundle bundle) {
        super(zzd, i, bundle);
        this.zzaHg = zzd;
        this.zzaHk = iBinder;
    }

    /* access modifiers changed from: protected */
    public final void zzj(ConnectionResult connectionResult) {
        if (this.zzaHg.zzaGY != null) {
            this.zzaHg.zzaGY.onConnectionFailed(connectionResult);
        }
        this.zzaHg.onConnectionFailed(connectionResult);
    }

    /* access modifiers changed from: protected */
    public final boolean zzrh() {
        try {
            String interfaceDescriptor = this.zzaHk.getInterfaceDescriptor();
            if (!this.zzaHg.zzdb().equals(interfaceDescriptor)) {
                String valueOf = String.valueOf(this.zzaHg.zzdb());
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 34 + String.valueOf(interfaceDescriptor).length());
                sb.append("service descriptor mismatch: ");
                sb.append(valueOf);
                sb.append(" vs. ");
                sb.append(interfaceDescriptor);
                Log.e("GmsClient", sb.toString());
                return false;
            }
            IInterface zzd = this.zzaHg.zzd(this.zzaHk);
            if (zzd == null) {
                return false;
            }
            if (!this.zzaHg.zza(2, 4, zzd) && !this.zzaHg.zza(3, 4, zzd)) {
                return false;
            }
            ConnectionResult unused = this.zzaHg.zzaHb = null;
            Bundle zzoA = this.zzaHg.zzoA();
            if (this.zzaHg.zzaGX != null) {
                this.zzaHg.zzaGX.onConnected(zzoA);
            }
            return true;
        } catch (RemoteException unused2) {
            Log.w("GmsClient", "service probably died");
            return false;
        }
    }
}
