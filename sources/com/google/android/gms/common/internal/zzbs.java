package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class zzbs extends zza {
    public static final Parcelable.Creator<zzbs> CREATOR = new zzbt();
    private final int zzaIq;
    private final GoogleSignInAccount zzaIr;
    private final Account zzajd;
    private int zzakw;

    zzbs(int i, Account account, int i2, GoogleSignInAccount googleSignInAccount) {
        this.zzakw = i;
        this.zzajd = account;
        this.zzaIq = i2;
        this.zzaIr = googleSignInAccount;
    }

    public zzbs(Account account, int i, GoogleSignInAccount googleSignInAccount) {
        this(2, account, i, googleSignInAccount);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzakw);
        zzd.zza(parcel, 2, (Parcelable) this.zzajd, i, false);
        zzd.zzc(parcel, 3, this.zzaIq);
        zzd.zza(parcel, 4, (Parcelable) this.zzaIr, i, false);
        zzd.zzI(parcel, zze);
    }
}
