package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public final class zzctl extends zzed implements zzctk {
    zzctl(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.safetynet.internal.ISafetyNetService");
    }

    public final void zzAh() throws RemoteException {
        zzb(13, zzY());
    }

    public final void zza(zzcti zzcti) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (IInterface) zzcti);
        zzb(12, zzY);
    }

    public final void zza(zzcti zzcti, String str) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (IInterface) zzcti);
        zzY.writeString(str);
        zzb(6, zzY);
    }

    public final void zza(zzcti zzcti, String str, int[] iArr, int i, String str2) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (IInterface) zzcti);
        zzY.writeString(str);
        zzY.writeIntArray(iArr);
        zzY.writeInt(i);
        zzY.writeString(str2);
        zzb(3, zzY);
    }

    public final void zza(zzcti zzcti, byte[] bArr, String str) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (IInterface) zzcti);
        zzY.writeByteArray(bArr);
        zzY.writeString(str);
        zzb(7, zzY);
    }

    public final void zzb(zzcti zzcti) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (IInterface) zzcti);
        zzb(14, zzY);
    }

    public final void zzc(zzcti zzcti) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (IInterface) zzcti);
        zzb(4, zzY);
    }

    public final void zzd(zzcti zzcti) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (IInterface) zzcti);
        zzb(5, zzY);
    }
}
