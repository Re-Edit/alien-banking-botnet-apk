package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class zzbhr extends zza {
    public static final Parcelable.Creator<zzbhr> CREATOR = new zzbht();
    private int versionCode;
    final String zzaIH;
    final int zzaII;

    zzbhr(int i, String str, int i2) {
        this.versionCode = i;
        this.zzaIH = str;
        this.zzaII = i2;
    }

    zzbhr(String str, int i) {
        this.versionCode = 1;
        this.zzaIH = str;
        this.zzaII = i;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.versionCode);
        zzd.zza(parcel, 2, this.zzaIH, false);
        zzd.zzc(parcel, 3, this.zzaII);
        zzd.zzI(parcel, zze);
    }
}
