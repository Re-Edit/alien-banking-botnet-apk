package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbs;

public final class zzcvh extends zza {
    public static final Parcelable.Creator<zzcvh> CREATOR = new zzcvi();
    private int zzakw;
    private zzbs zzbCY;

    zzcvh(int i, zzbs zzbs) {
        this.zzakw = i;
        this.zzbCY = zzbs;
    }

    public zzcvh(zzbs zzbs) {
        this(1, zzbs);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzakw);
        zzd.zza(parcel, 2, (Parcelable) this.zzbCY, i, false);
        zzd.zzI(parcel, zze);
    }
}
