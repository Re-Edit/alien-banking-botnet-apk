package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.zzbu;

public final class zzcvk implements Parcelable.Creator<zzcvj> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        ConnectionResult connectionResult = null;
        int i = 0;
        zzbu zzbu = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 2:
                    connectionResult = (ConnectionResult) zzb.zza(parcel, readInt, ConnectionResult.CREATOR);
                    break;
                case 3:
                    zzbu = (zzbu) zzb.zza(parcel, readInt, zzbu.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzcvj(i, connectionResult, zzbu);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcvj[i];
    }
}
