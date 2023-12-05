package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

public final class zzcbq extends zzed implements zzcbo {
    zzcbq(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.flags.IFlagProvider");
    }

    public final boolean getBooleanFlagValue(String str, boolean z, int i) throws RemoteException {
        Parcel zzY = zzY();
        zzY.writeString(str);
        zzef.zza(zzY, z);
        zzY.writeInt(i);
        Parcel zza = zza(2, zzY);
        boolean zza2 = zzef.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final int getIntFlagValue(String str, int i, int i2) throws RemoteException {
        Parcel zzY = zzY();
        zzY.writeString(str);
        zzY.writeInt(i);
        zzY.writeInt(i2);
        Parcel zza = zza(3, zzY);
        int readInt = zza.readInt();
        zza.recycle();
        return readInt;
    }

    public final long getLongFlagValue(String str, long j, int i) throws RemoteException {
        Parcel zzY = zzY();
        zzY.writeString(str);
        zzY.writeLong(j);
        zzY.writeInt(i);
        Parcel zza = zza(4, zzY);
        long readLong = zza.readLong();
        zza.recycle();
        return readLong;
    }

    public final String getStringFlagValue(String str, String str2, int i) throws RemoteException {
        Parcel zzY = zzY();
        zzY.writeString(str);
        zzY.writeString(str2);
        zzY.writeInt(i);
        Parcel zza = zza(5, zzY);
        String readString = zza.readString();
        zza.recycle();
        return readString;
    }

    public final void init(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (IInterface) iObjectWrapper);
        zzb(1, zzY);
    }
}
