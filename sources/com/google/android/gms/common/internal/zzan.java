package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;

public abstract class zzan extends zzee implements zzam {
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        if (i != 2) {
            return false;
        }
        Account account = getAccount();
        parcel2.writeNoException();
        zzef.zzb(parcel2, account);
        return true;
    }
}
