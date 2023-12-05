package com.google.android.gms.security;

import android.content.Context;
import android.os.AsyncTask;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.zze;
import com.google.android.gms.security.ProviderInstaller;

final class zza extends AsyncTask<Void, Void, Integer> {
    private /* synthetic */ ProviderInstaller.ProviderInstallListener zzbCM;
    private /* synthetic */ Context zztI;

    zza(Context context, ProviderInstaller.ProviderInstallListener providerInstallListener) {
        this.zztI = context;
        this.zzbCM = providerInstallListener;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzb */
    public final Integer doInBackground(Void... voidArr) {
        int connectionStatusCode;
        try {
            ProviderInstaller.installIfNeeded(this.zztI);
            connectionStatusCode = 0;
        } catch (GooglePlayServicesRepairableException e) {
            connectionStatusCode = e.getConnectionStatusCode();
        } catch (GooglePlayServicesNotAvailableException e2) {
            connectionStatusCode = e2.errorCode;
        }
        return Integer.valueOf(connectionStatusCode);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void onPostExecute(Object obj) {
        Integer num = (Integer) obj;
        if (num.intValue() == 0) {
            this.zzbCM.onProviderInstalled();
            return;
        }
        zze unused = ProviderInstaller.zzbCK;
        this.zzbCM.onProviderInstallFailed(num.intValue(), zze.zza(this.zztI, num.intValue(), "pi"));
    }
}
