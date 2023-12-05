package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

public abstract class zzcbp extends zzee implements zzcbo {
    public zzcbp() {
        attachInterface(this, "com.google.android.gms.flags.IFlagProvider");
    }

    public static zzcbo asInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.flags.IFlagProvider");
        return queryLocalInterface instanceof zzcbo ? (zzcbo) queryLocalInterface : new zzcbq(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                init(IObjectWrapper.zza.zzM(parcel.readStrongBinder()));
                parcel2.writeNoException();
                break;
            case 2:
                boolean booleanFlagValue = getBooleanFlagValue(parcel.readString(), zzef.zza(parcel), parcel.readInt());
                parcel2.writeNoException();
                zzef.zza(parcel2, booleanFlagValue);
                break;
            case 3:
                int intFlagValue = getIntFlagValue(parcel.readString(), parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(intFlagValue);
                break;
            case 4:
                long longFlagValue = getLongFlagValue(parcel.readString(), parcel.readLong(), parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeLong(longFlagValue);
                break;
            case 5:
                String stringFlagValue = getStringFlagValue(parcel.readString(), parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeString(stringFlagValue);
                break;
            default:
                return false;
        }
        return true;
    }
}
