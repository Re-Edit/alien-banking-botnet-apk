package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzed;
import com.google.android.gms.internal.zzef;

public final class zzbf extends zzed implements zzbe {
    zzbf(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.ISignInButtonCreator");
    }

    public final IObjectWrapper zza(IObjectWrapper iObjectWrapper, zzbw zzbw) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (IInterface) iObjectWrapper);
        zzef.zza(zzY, (Parcelable) zzbw);
        Parcel zza = zza(2, zzY);
        IObjectWrapper zzM = IObjectWrapper.zza.zzM(zza.readStrongBinder());
        zza.recycle();
        return zzM;
    }
}
