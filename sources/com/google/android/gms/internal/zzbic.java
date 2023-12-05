package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class zzbic extends zza {
    public static final Parcelable.Creator<zzbic> CREATOR = new zzbhz();
    final String key;
    private int versionCode;
    final zzbhv<?, ?> zzaIX;

    zzbic(int i, String str, zzbhv<?, ?> zzbhv) {
        this.versionCode = i;
        this.key = str;
        this.zzaIX = zzbhv;
    }

    zzbic(String str, zzbhv<?, ?> zzbhv) {
        this.versionCode = 1;
        this.key = str;
        this.zzaIX = zzbhv;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.versionCode);
        zzd.zza(parcel, 2, this.key, false);
        zzd.zza(parcel, 3, (Parcelable) this.zzaIX, i, false);
        zzd.zzI(parcel, zze);
    }
}
