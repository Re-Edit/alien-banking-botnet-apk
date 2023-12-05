package com.google.android.gms.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafeBrowsingData;
import com.google.android.gms.safetynet.zza;
import com.google.android.gms.safetynet.zzd;
import com.google.android.gms.safetynet.zzf;

public interface zzcti extends IInterface {
    void zzF(Status status) throws RemoteException;

    void zza(Status status, SafeBrowsingData safeBrowsingData) throws RemoteException;

    void zza(Status status, zza zza) throws RemoteException;

    void zza(Status status, zzd zzd) throws RemoteException;

    void zza(Status status, zzf zzf) throws RemoteException;

    void zza(Status status, boolean z) throws RemoteException;

    void zzb(Status status, boolean z) throws RemoteException;

    void zzc(Status status, boolean z) throws RemoteException;

    void zzd(Status status, boolean z) throws RemoteException;

    void zzeH(String str) throws RemoteException;
}
