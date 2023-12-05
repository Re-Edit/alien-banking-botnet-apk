package com.google.android.gms.common.util;

import android.support.v4.util.ArrayMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;

@Deprecated
public final class zzb<E> extends AbstractSet<E> {
    private final ArrayMap<E, E> zzaJE;

    public zzb() {
        this.zzaJE = new ArrayMap<>();
    }

    public zzb(int i) {
        this.zzaJE = new ArrayMap<>(i);
    }

    public final boolean add(E e) {
        if (this.zzaJE.containsKey(e)) {
            return false;
        }
        this.zzaJE.put(e, e);
        return true;
    }

    public final boolean addAll(Collection<? extends E> collection) {
        if (!(collection instanceof zzb)) {
            return super.addAll(collection);
        }
        int size = size();
        this.zzaJE.putAll(((zzb) collection).zzaJE);
        return size() > size;
    }

    public final void clear() {
        this.zzaJE.clear();
    }

    public final boolean contains(Object obj) {
        return this.zzaJE.containsKey(obj);
    }

    public final Iterator<E> iterator() {
        return this.zzaJE.keySet().iterator();
    }

    public final boolean remove(Object obj) {
        if (!this.zzaJE.containsKey(obj)) {
            return false;
        }
        this.zzaJE.remove(obj);
        return true;
    }

    public final int size() {
        return this.zzaJE.size();
    }
}
