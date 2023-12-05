package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzq;
import java.util.List;

public final class zzctz extends zzaa<zzctk> {
    private final Context mContext;

    public zzctz(Context context, Looper looper, zzq zzq, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 45, zzq, connectionCallbacks, onConnectionFailedListener);
        this.mContext = context;
    }

    private final String zzeI(String str) {
        ApplicationInfo applicationInfo;
        Bundle bundle;
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            if (packageManager == null || (applicationInfo = packageManager.getApplicationInfo(this.mContext.getPackageName(), 128)) == null || (bundle = applicationInfo.metaData) == null) {
                return null;
            }
            return (String) bundle.get(str);
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    public final void zza(zzcti zzcti, List<Integer> list, int i, String str, String str2) throws RemoteException {
        if (str2 == null) {
            str2 = zzeI("com.google.android.safetynet.API_KEY");
        }
        String str3 = str2;
        int[] iArr = new int[list.size()];
        for (int i2 = 0; i2 < list.size(); i2++) {
            iArr[i2] = list.get(i2).intValue();
        }
        ((zzctk) zzrd()).zza(zzcti, str3, iArr, i, str);
    }

    public final void zzb(zzcti zzcti, byte[] bArr, String str) throws RemoteException {
        if (TextUtils.isEmpty(str)) {
            str = zzeI("com.google.android.safetynet.ATTEST_API_KEY");
        }
        ((zzctk) zzrd()).zza(zzcti, bArr, str);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.safetynet.internal.ISafetyNetService");
        return queryLocalInterface instanceof zzctk ? (zzctk) queryLocalInterface : new zzctl(iBinder);
    }

    /* access modifiers changed from: protected */
    public final String zzda() {
        return "com.google.android.gms.safetynet.service.START";
    }

    /* access modifiers changed from: protected */
    public final String zzdb() {
        return "com.google.android.gms.safetynet.internal.ISafetyNetService";
    }
}
