package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;

final class zzp implements Parcelable.Creator<BinderWrapper> {
    zzp() {
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        return new BinderWrapper(parcel, (zzp) null);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new BinderWrapper[i];
    }
}
