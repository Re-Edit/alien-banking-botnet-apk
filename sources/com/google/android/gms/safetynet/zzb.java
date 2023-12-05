package com.google.android.gms.safetynet;

import android.os.Parcel;
import android.os.Parcelable;

public final class zzb implements Parcelable.Creator<zza> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = com.google.android.gms.common.internal.safeparcel.zzb.zzd(parcel);
        String str = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            if ((65535 & readInt) != 2) {
                com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, readInt);
            } else {
                str = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, readInt);
            }
        }
        com.google.android.gms.common.internal.safeparcel.zzb.zzF(parcel, zzd);
        return new zza(str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zza[i];
    }
}
