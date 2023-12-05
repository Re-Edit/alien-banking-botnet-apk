package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;

public abstract class zzat extends zzee implements zzas {
    public zzat() {
        attachInterface(this, "com.google.android.gms.common.internal.ICertData");
    }

    public static zzas zzI(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.ICertData");
        return queryLocalInterface instanceof zzas ? (zzas) queryLocalInterface : new zzau(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                IObjectWrapper zzoW = zzoW();
                parcel2.writeNoException();
                zzef.zza(parcel2, (IInterface) zzoW);
                break;
            case 2:
                int zzoX = zzoX();
                parcel2.writeNoException();
                parcel2.writeInt(zzoX);
                break;
            default:
                return false;
        }
        return true;
    }
}
