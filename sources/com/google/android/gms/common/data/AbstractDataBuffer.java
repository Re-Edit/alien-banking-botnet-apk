package com.google.android.gms.common.data;

import android.os.Bundle;
import java.util.Iterator;

public abstract class AbstractDataBuffer<T> implements DataBuffer<T> {
    protected final DataHolder zzaCZ;

    protected AbstractDataBuffer(DataHolder dataHolder) {
        this.zzaCZ = dataHolder;
    }

    @Deprecated
    public final void close() {
        release();
    }

    public abstract T get(int i);

    public int getCount() {
        DataHolder dataHolder = this.zzaCZ;
        if (dataHolder == null) {
            return 0;
        }
        return dataHolder.zzaFI;
    }

    @Deprecated
    public boolean isClosed() {
        DataHolder dataHolder = this.zzaCZ;
        return dataHolder == null || dataHolder.isClosed();
    }

    public Iterator<T> iterator() {
        return new zzb(this);
    }

    public void release() {
        DataHolder dataHolder = this.zzaCZ;
        if (dataHolder != null) {
            dataHolder.close();
        }
    }

    public Iterator<T> singleRefIterator() {
        return new zzh(this);
    }

    public final Bundle zzqL() {
        return this.zzaCZ.zzqL();
    }
}
