package com.google.android.gms.dynamite;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzed;
import com.google.android.gms.internal.zzef;

public final class zzk extends zzed implements zzj {
    zzk(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.dynamite.IDynamiteLoader");
    }

    public final int zza(IObjectWrapper iObjectWrapper, String str, boolean z) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (IInterface) iObjectWrapper);
        zzY.writeString(str);
        zzef.zza(zzY, z);
        Parcel zza = zza(3, zzY);
        int readInt = zza.readInt();
        zza.recycle();
        return readInt;
    }

    public final IObjectWrapper zza(IObjectWrapper iObjectWrapper, String str, int i) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (IInterface) iObjectWrapper);
        zzY.writeString(str);
        zzY.writeInt(i);
        Parcel zza = zza(2, zzY);
        IObjectWrapper zzM = IObjectWrapper.zza.zzM(zza.readStrongBinder());
        zza.recycle();
        return zzM;
    }
}
