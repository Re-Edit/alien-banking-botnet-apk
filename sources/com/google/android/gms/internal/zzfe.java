package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract class zzfe extends zzee implements zzfd {
    public static zzfd zzc(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
        return queryLocalInterface instanceof zzfd ? (zzfd) queryLocalInterface : new zzff(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        String str;
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                str = getId();
                break;
            case 2:
                boolean zzb = zzb(zzef.zza(parcel));
                parcel2.writeNoException();
                zzef.zza(parcel2, zzb);
                break;
            case 3:
                str = zzq(parcel.readString());
                break;
            case 4:
                zzc(parcel.readString(), zzef.zza(parcel));
                parcel2.writeNoException();
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        parcel2.writeString(str);
        return true;
    }
}
