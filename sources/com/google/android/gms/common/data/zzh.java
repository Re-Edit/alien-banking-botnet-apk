package com.google.android.gms.common.data;

import java.util.NoSuchElementException;

public final class zzh<T> extends zzb<T> {
    private T zzaFS;

    public zzh(DataBuffer<T> dataBuffer) {
        super(dataBuffer);
    }

    public final T next() {
        if (hasNext()) {
            this.zzaFx++;
            if (this.zzaFx == 0) {
                this.zzaFS = this.zzaFw.get(0);
                T t = this.zzaFS;
                if (!(t instanceof zzc)) {
                    String valueOf = String.valueOf(t.getClass());
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 44);
                    sb.append("DataBuffer reference of type ");
                    sb.append(valueOf);
                    sb.append(" is not movable");
                    throw new IllegalStateException(sb.toString());
                }
            } else {
                ((zzc) this.zzaFS).zzar(this.zzaFx);
            }
            return this.zzaFS;
        }
        int i = this.zzaFx;
        StringBuilder sb2 = new StringBuilder(46);
        sb2.append("Cannot advance the iterator beyond ");
        sb2.append(i);
        throw new NoSuchElementException(sb2.toString());
    }
}
