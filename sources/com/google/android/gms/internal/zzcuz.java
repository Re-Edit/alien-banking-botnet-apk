package com.google.android.gms.internal;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class zzcuz extends zza implements Result {
    public static final Parcelable.Creator<zzcuz> CREATOR = new zzcva();
    private int zzakw;
    private int zzbCV;
    private Intent zzbCW;

    public zzcuz() {
        this(0, (Intent) null);
    }

    zzcuz(int i, int i2, Intent intent) {
        this.zzakw = i;
        this.zzbCV = i2;
        this.zzbCW = intent;
    }

    private zzcuz(int i, Intent intent) {
        this(2, 0, (Intent) null);
    }

    public final Status getStatus() {
        return this.zzbCV == 0 ? Status.zzaBo : Status.zzaBs;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzakw);
        zzd.zzc(parcel, 2, this.zzbCV);
        zzd.zza(parcel, 3, (Parcelable) this.zzbCW, i, false);
        zzd.zzI(parcel, zze);
    }
}
