package com.google.android.gms.common.data;

import com.google.android.gms.common.internal.zzbr;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class zzb<T> implements Iterator<T> {
    protected final DataBuffer<T> zzaFw;
    protected int zzaFx = -1;

    public zzb(DataBuffer<T> dataBuffer) {
        this.zzaFw = (DataBuffer) zzbr.zzu(dataBuffer);
    }

    public boolean hasNext() {
        return this.zzaFx < this.zzaFw.getCount() - 1;
    }

    public T next() {
        if (hasNext()) {
            DataBuffer<T> dataBuffer = this.zzaFw;
            int i = this.zzaFx + 1;
            this.zzaFx = i;
            return dataBuffer.get(i);
        }
        int i2 = this.zzaFx;
        StringBuilder sb = new StringBuilder(46);
        sb.append("Cannot advance the iterator beyond ");
        sb.append(i2);
        throw new NoSuchElementException(sb.toString());
    }

    public void remove() {
        throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
    }
}
