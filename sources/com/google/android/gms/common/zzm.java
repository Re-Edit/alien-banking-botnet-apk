package com.google.android.gms.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzat;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;

public final class zzm extends zza {
    public static final Parcelable.Creator<zzm> CREATOR = new zzn();
    private final String zzaAn;
    private final zzg zzaAo;
    private final boolean zzaAp;

    zzm(String str, IBinder iBinder, boolean z) {
        this.zzaAn = str;
        this.zzaAo = zzG(iBinder);
        this.zzaAp = z;
    }

    zzm(String str, zzg zzg, boolean z) {
        this.zzaAn = str;
        this.zzaAo = zzg;
        this.zzaAp = z;
    }

    private static zzg zzG(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        try {
            IObjectWrapper zzoW = zzat.zzI(iBinder).zzoW();
            byte[] bArr = zzoW == null ? null : (byte[]) zzn.zzE(zzoW);
            if (bArr != null) {
                return new zzh(bArr);
            }
            Log.e("GoogleCertificatesQuery", "Could not unwrap certificate");
            return null;
        } catch (RemoteException e) {
            Log.e("GoogleCertificatesQuery", "Could not unwrap certificate", e);
            return null;
        }
    }

    public final void writeToParcel(Parcel parcel, int i) {
        IBinder iBinder;
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzaAn, false);
        zzg zzg = this.zzaAo;
        if (zzg == null) {
            Log.w("GoogleCertificatesQuery", "certificate binder is null");
            iBinder = null;
        } else {
            iBinder = zzg.asBinder();
        }
        zzd.zza(parcel, 2, iBinder, false);
        zzd.zza(parcel, 3, this.zzaAp);
        zzd.zzI(parcel, zze);
    }
}
