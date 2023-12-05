package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.annotation.KeepName;

@KeepName
public final class BinderWrapper implements Parcelable {
    public static final Parcelable.Creator<BinderWrapper> CREATOR = new zzp();
    private IBinder zzaHl;

    public BinderWrapper() {
        this.zzaHl = null;
    }

    public BinderWrapper(IBinder iBinder) {
        this.zzaHl = null;
        this.zzaHl = iBinder;
    }

    private BinderWrapper(Parcel parcel) {
        this.zzaHl = null;
        this.zzaHl = parcel.readStrongBinder();
    }

    /* synthetic */ BinderWrapper(Parcel parcel, zzp zzp) {
        this(parcel);
    }

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.zzaHl);
    }
}
