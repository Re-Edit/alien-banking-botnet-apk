package com.google.android.gms.safetynet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public class HarmfulAppsData extends zza {
    public static final Parcelable.Creator<HarmfulAppsData> CREATOR = new zzc();
    public final int apkCategory;
    public final String apkPackageName;
    public final byte[] apkSha256;

    public HarmfulAppsData(String str, byte[] bArr, int i) {
        this.apkPackageName = str;
        this.apkSha256 = bArr;
        this.apkCategory = i;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, this.apkPackageName, false);
        zzd.zza(parcel, 3, this.apkSha256, false);
        zzd.zzc(parcel, 4, this.apkCategory);
        zzd.zzI(parcel, zze);
    }
}
