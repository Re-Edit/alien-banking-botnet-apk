package com.google.firebase;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbfy;

public final class zzb implements zzbfy {
    public final Exception zzq(Status status) {
        return status.getStatusCode() == 8 ? new FirebaseException(status.zzpo()) : new FirebaseApiNotAvailableException(status.zzpo());
    }
}
