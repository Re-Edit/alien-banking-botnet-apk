package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbu;

public final class zzcvj extends zza {
    public static final Parcelable.Creator<zzcvj> CREATOR = new zzcvk();
    private final ConnectionResult zzaBS;
    private int zzakw;
    private final zzbu zzbCZ;

    public zzcvj(int i) {
        this(new ConnectionResult(8, (PendingIntent) null), (zzbu) null);
    }

    zzcvj(int i, ConnectionResult connectionResult, zzbu zzbu) {
        this.zzakw = i;
        this.zzaBS = connectionResult;
        this.zzbCZ = zzbu;
    }

    private zzcvj(ConnectionResult connectionResult, zzbu zzbu) {
        this(1, connectionResult, (zzbu) null);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzakw);
        zzd.zza(parcel, 2, (Parcelable) this.zzaBS, i, false);
        zzd.zza(parcel, 3, (Parcelable) this.zzbCZ, i, false);
        zzd.zzI(parcel, zze);
    }

    public final zzbu zzAv() {
        return this.zzbCZ;
    }

    public final ConnectionResult zzpx() {
        return this.zzaBS;
    }
}
