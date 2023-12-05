package com.google.android.gms.dynamic;

import android.os.Bundle;
import java.util.Iterator;

final class zzb implements zzo<T> {
    private /* synthetic */ zza zzaSz;

    zzb(zza zza) {
        this.zzaSz = zza;
    }

    public final void zza(T t) {
        LifecycleDelegate unused = this.zzaSz.zzaSv = t;
        Iterator it = this.zzaSz.zzaSx.iterator();
        while (it.hasNext()) {
            ((zzi) it.next()).zzb(this.zzaSz.zzaSv);
        }
        this.zzaSz.zzaSx.clear();
        Bundle unused2 = this.zzaSz.zzaSw = null;
    }
}
