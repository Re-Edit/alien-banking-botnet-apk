package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

public final class zzff extends zzed implements zzfd {
    zzff(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
    }

    public final String getId() throws RemoteException {
        Parcel zza = zza(1, zzY());
        String readString = zza.readString();
        zza.recycle();
        return readString;
    }

    public final boolean zzb(boolean z) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, z);
        Parcel zza = zza(2, zzY);
        boolean zza2 = zzef.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final void zzc(String str, boolean z) throws RemoteException {
        Parcel zzY = zzY();
        zzY.writeString(str);
        zzef.zza(zzY, z);
        zzb(4, zzY);
    }

    public final String zzq(String str) throws RemoteException {
        Parcel zzY = zzY();
        zzY.writeString(str);
        Parcel zza = zza(3, zzY);
        String readString = zza.readString();
        zza.recycle();
        return readString;
    }
}
