package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzed;
import com.google.android.gms.internal.zzef;

public final class zzao extends zzed implements zzam {
    zzao(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.IAccountAccessor");
    }

    public final Account getAccount() throws RemoteException {
        Parcel zza = zza(2, zzY());
        Account account = (Account) zzef.zza(zza, Account.CREATOR);
        zza.recycle();
        return account;
    }
}
