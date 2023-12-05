package com.google.android.gms.dynamite;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzed;
import com.google.android.gms.internal.zzef;

public final class zzm extends zzed implements zzl {
    zzm(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.dynamite.IDynamiteLoaderV2");
    }

    public final IObjectWrapper zza(IObjectWrapper iObjectWrapper, String str, int i, IObjectWrapper iObjectWrapper2) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (IInterface) iObjectWrapper);
        zzY.writeString(str);
        zzY.writeInt(i);
        zzef.zza(zzY, (IInterface) iObjectWrapper2);
        Parcel zza = zza(2, zzY);
        IObjectWrapper zzM = IObjectWrapper.zza.zzM(zza.readStrongBinder());
        zza.recycle();
        return zzM;
    }
}
