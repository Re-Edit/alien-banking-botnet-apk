package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.zzm;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzed;
import com.google.android.gms.internal.zzef;

public final class zzbd extends zzed implements zzbb {
    zzbd(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.IGoogleCertificatesApi");
    }

    public final boolean zza(zzm zzm, IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (Parcelable) zzm);
        zzef.zza(zzY, (IInterface) iObjectWrapper);
        Parcel zza = zza(5, zzY);
        boolean zza2 = zzef.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final boolean zze(String str, IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel zzY = zzY();
        zzY.writeString(str);
        zzef.zza(zzY, (IInterface) iObjectWrapper);
        Parcel zza = zza(3, zzY);
        boolean zza2 = zzef.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final boolean zzf(String str, IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel zzY = zzY();
        zzY.writeString(str);
        zzef.zza(zzY, (IInterface) iObjectWrapper);
        Parcel zza = zza(4, zzY);
        boolean zza2 = zzef.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final IObjectWrapper zzrE() throws RemoteException {
        Parcel zza = zza(1, zzY());
        IObjectWrapper zzM = IObjectWrapper.zza.zzM(zza.readStrongBinder());
        zza.recycle();
        return zzM;
    }

    public final IObjectWrapper zzrF() throws RemoteException {
        Parcel zza = zza(2, zzY());
        IObjectWrapper zzM = IObjectWrapper.zza.zzM(zza.readStrongBinder());
        zza.recycle();
        return zzM;
    }
}
