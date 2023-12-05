package com.google.firebase;

import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzbr;

public class FirebaseException extends Exception {
    @Deprecated
    protected FirebaseException() {
    }

    public FirebaseException(@NonNull String str) {
        super(zzbr.zzh(str, "Detail message must not be empty"));
    }

    public FirebaseException(@NonNull String str, Throwable th) {
        super(zzbr.zzh(str, "Detail message must not be empty"), th);
    }
}
