package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

final class zzba implements zzay {
    private final IBinder zzrG;

    zzba(IBinder iBinder) {
        this.zzrG = iBinder;
    }

    public final IBinder asBinder() {
        return this.zzrG;
    }

    public final void zza(zzav zzav, zzy zzy) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
            obtain.writeStrongBinder(zzav != null ? zzav.asBinder() : null);
            if (zzy != null) {
                obtain.writeInt(1);
                zzy.writeToParcel(obtain, 0);
            } else {
                obtain.writeInt(0);
            }
            this.zzrG.transact(46, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }
}
