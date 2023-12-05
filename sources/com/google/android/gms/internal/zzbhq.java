package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.ArrayList;
import java.util.HashMap;

public final class zzbhq extends zza implements zzbhw<String, Integer> {
    public static final Parcelable.Creator<zzbhq> CREATOR = new zzbhs();
    private final HashMap<String, Integer> zzaIE;
    private final SparseArray<String> zzaIF;
    private final ArrayList<zzbhr> zzaIG;
    private int zzakw;

    public zzbhq() {
        this.zzakw = 1;
        this.zzaIE = new HashMap<>();
        this.zzaIF = new SparseArray<>();
        this.zzaIG = null;
    }

    zzbhq(int i, ArrayList<zzbhr> arrayList) {
        this.zzakw = i;
        this.zzaIE = new HashMap<>();
        this.zzaIF = new SparseArray<>();
        this.zzaIG = null;
        zzd(arrayList);
    }

    private final void zzd(ArrayList<zzbhr> arrayList) {
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            zzbhr zzbhr = (zzbhr) obj;
            zzi(zzbhr.zzaIH, zzbhr.zzaII);
        }
    }

    public final /* synthetic */ Object convertBack(Object obj) {
        String str = this.zzaIF.get(((Integer) obj).intValue());
        return (str != null || !this.zzaIE.containsKey("gms_unknown")) ? str : "gms_unknown";
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzakw);
        ArrayList arrayList = new ArrayList();
        for (String next : this.zzaIE.keySet()) {
            arrayList.add(new zzbhr(next, this.zzaIE.get(next).intValue()));
        }
        zzd.zzc(parcel, 2, arrayList, false);
        zzd.zzI(parcel, zze);
    }

    public final zzbhq zzi(String str, int i) {
        this.zzaIE.put(str, Integer.valueOf(i));
        this.zzaIF.put(i, str);
        return this;
    }
}
