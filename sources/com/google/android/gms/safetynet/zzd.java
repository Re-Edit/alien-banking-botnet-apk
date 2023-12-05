package com.google.android.gms.safetynet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

public final class zzd extends zza {
    public static final Parcelable.Creator<zzd> CREATOR = new zze();
    public final long zzbBK;
    public final HarmfulAppsData[] zzbBL;

    public zzd(long j, HarmfulAppsData[] harmfulAppsDataArr) {
        this.zzbBK = j;
        this.zzbBL = harmfulAppsDataArr;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = com.google.android.gms.common.internal.safeparcel.zzd.zze(parcel);
        com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 2, this.zzbBK);
        com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 3, (T[]) this.zzbBL, i, false);
        com.google.android.gms.common.internal.safeparcel.zzd.zzI(parcel, zze);
    }
}
