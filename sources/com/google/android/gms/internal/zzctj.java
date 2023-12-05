package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafeBrowsingData;
import com.google.android.gms.safetynet.zza;
import com.google.android.gms.safetynet.zzd;
import com.google.android.gms.safetynet.zzf;

public abstract class zzctj extends zzee implements zzcti {
    public zzctj() {
        attachInterface(this, "com.google.android.gms.safetynet.internal.ISafetyNetCallbacks");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                zza((Status) zzef.zza(parcel, Status.CREATOR), (zza) zzef.zza(parcel, zza.CREATOR));
                break;
            case 2:
                zzeH(parcel.readString());
                break;
            case 3:
                zza((Status) zzef.zza(parcel, Status.CREATOR), (SafeBrowsingData) zzef.zza(parcel, SafeBrowsingData.CREATOR));
                break;
            case 4:
                zza((Status) zzef.zza(parcel, Status.CREATOR), zzef.zza(parcel));
                break;
            case 6:
                zza((Status) zzef.zza(parcel, Status.CREATOR), (zzf) zzef.zza(parcel, zzf.CREATOR));
                break;
            case 7:
                zzb((Status) zzef.zza(parcel, Status.CREATOR), zzef.zza(parcel));
                break;
            case 8:
                zza((Status) zzef.zza(parcel, Status.CREATOR), (zzd) zzef.zza(parcel, zzd.CREATOR));
                break;
            case 10:
                zzc((Status) zzef.zza(parcel, Status.CREATOR), zzef.zza(parcel));
                break;
            case 11:
                zzF((Status) zzef.zza(parcel, Status.CREATOR));
                break;
            case 12:
                zzd((Status) zzef.zza(parcel, Status.CREATOR), zzef.zza(parcel));
                break;
            default:
                return false;
        }
        return true;
    }
}
