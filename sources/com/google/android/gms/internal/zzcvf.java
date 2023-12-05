package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzam;

public final class zzcvf extends zzed implements zzcve {
    zzcvf(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.signin.internal.ISignInService");
    }

    public final void zza(zzam zzam, int i, boolean z) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (IInterface) zzam);
        zzY.writeInt(i);
        zzef.zza(zzY, z);
        zzb(9, zzY);
    }

    public final void zza(zzcvh zzcvh, zzcvc zzcvc) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (Parcelable) zzcvh);
        zzef.zza(zzY, (IInterface) zzcvc);
        zzb(12, zzY);
    }

    public final void zzbu(int i) throws RemoteException {
        Parcel zzY = zzY();
        zzY.writeInt(i);
        zzb(7, zzY);
    }
}
