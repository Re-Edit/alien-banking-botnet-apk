package com.google.android.gms.common.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.zzm;
import com.google.android.gms.dynamic.IObjectWrapper;

public interface zzbb extends IInterface {
    boolean zza(zzm zzm, IObjectWrapper iObjectWrapper) throws RemoteException;

    boolean zze(String str, IObjectWrapper iObjectWrapper) throws RemoteException;

    boolean zzf(String str, IObjectWrapper iObjectWrapper) throws RemoteException;

    IObjectWrapper zzrE() throws RemoteException;

    IObjectWrapper zzrF() throws RemoteException;
}
