package com.google.android.gms.internal;

import android.content.DialogInterface;
import android.support.annotation.MainThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiActivity;

final class zzbco implements Runnable {
    private final zzbcn zzaBT;
    final /* synthetic */ zzbcm zzaBU;

    zzbco(zzbcm zzbcm, zzbcn zzbcn) {
        this.zzaBU = zzbcm;
        this.zzaBT = zzbcn;
    }

    @MainThread
    public final void run() {
        if (this.zzaBU.mStarted) {
            ConnectionResult zzpx = this.zzaBT.zzpx();
            if (zzpx.hasResolution()) {
                this.zzaBU.zzaEI.startActivityForResult(GoogleApiActivity.zza(this.zzaBU.getActivity(), zzpx.getResolution(), this.zzaBT.zzpw(), false), 1);
            } else if (this.zzaBU.zzaBf.isUserResolvableError(zzpx.getErrorCode())) {
                this.zzaBU.zzaBf.zza(this.zzaBU.getActivity(), this.zzaBU.zzaEI, zzpx.getErrorCode(), 2, this.zzaBU);
            } else if (zzpx.getErrorCode() == 18) {
                GoogleApiAvailability.zza(this.zzaBU.getActivity().getApplicationContext(), (zzbex) new zzbcp(this, GoogleApiAvailability.zza(this.zzaBU.getActivity(), (DialogInterface.OnCancelListener) this.zzaBU)));
            } else {
                this.zzaBU.zza(zzpx, this.zzaBT.zzpw());
            }
        }
    }
}
