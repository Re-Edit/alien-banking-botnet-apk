package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class zzbu extends zza {
    public static final Parcelable.Creator<zzbu> CREATOR = new zzbv();
    private ConnectionResult zzaBS;
    private boolean zzaDo;
    private IBinder zzaIs;
    private boolean zzaIt;
    private int zzakw;

    zzbu(int i, IBinder iBinder, ConnectionResult connectionResult, boolean z, boolean z2) {
        this.zzakw = i;
        this.zzaIs = iBinder;
        this.zzaBS = connectionResult;
        this.zzaDo = z;
        this.zzaIt = z2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzbu)) {
            return false;
        }
        zzbu zzbu = (zzbu) obj;
        return this.zzaBS.equals(zzbu.zzaBS) && zzrG().equals(zzbu.zzrG());
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzakw);
        zzd.zza(parcel, 2, this.zzaIs, false);
        zzd.zza(parcel, 3, (Parcelable) this.zzaBS, i, false);
        zzd.zza(parcel, 4, this.zzaDo);
        zzd.zza(parcel, 5, this.zzaIt);
        zzd.zzI(parcel, zze);
    }

    public final ConnectionResult zzpx() {
        return this.zzaBS;
    }

    public final zzam zzrG() {
        IBinder iBinder = this.zzaIs;
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IAccountAccessor");
        return queryLocalInterface instanceof zzam ? (zzam) queryLocalInterface : new zzao(iBinder);
    }

    public final boolean zzrH() {
        return this.zzaDo;
    }

    public final boolean zzrI() {
        return this.zzaIt;
    }
}
