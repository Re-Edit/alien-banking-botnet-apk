package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class zzd<T extends SafeParcelable> extends AbstractDataBuffer<T> {
    private static final String[] zzaFB = {"data"};
    private final Parcelable.Creator<T> zzaFC;

    public zzd(DataHolder dataHolder, Parcelable.Creator<T> creator) {
        super(dataHolder);
        this.zzaFC = creator;
    }

    public static <T extends SafeParcelable> void zza(DataHolder.zza zza, T t) {
        Parcel obtain = Parcel.obtain();
        t.writeToParcel(obtain, 0);
        ContentValues contentValues = new ContentValues();
        contentValues.put("data", obtain.marshall());
        zza.zza(contentValues);
        obtain.recycle();
    }

    public static DataHolder.zza zzqO() {
        return DataHolder.zza(zzaFB);
    }

    /* renamed from: zzas */
    public T get(int i) {
        byte[] zzg = this.zzaCZ.zzg("data", i, this.zzaCZ.zzat(i));
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(zzg, 0, zzg.length);
        obtain.setDataPosition(0);
        T t = (SafeParcelable) this.zzaFC.createFromParcel(obtain);
        obtain.recycle();
        return t;
    }
}
