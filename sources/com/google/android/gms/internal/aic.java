package com.google.android.gms.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class aic implements Cloneable {
    private Object value;
    private aia<?, ?> zzcvc;
    private List<aii> zzcvd = new ArrayList();

    aic() {
    }

    private final byte[] toByteArray() throws IOException {
        byte[] bArr = new byte[zzn()];
        zza(ahx.zzJ(bArr));
        return bArr;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzMf */
    public aic clone() {
        Object clone;
        aic aic = new aic();
        try {
            aic.zzcvc = this.zzcvc;
            if (this.zzcvd == null) {
                aic.zzcvd = null;
            } else {
                aic.zzcvd.addAll(this.zzcvd);
            }
            if (this.value != null) {
                if (this.value instanceof aif) {
                    clone = (aif) ((aif) this.value).clone();
                } else if (this.value instanceof byte[]) {
                    clone = ((byte[]) this.value).clone();
                } else {
                    int i = 0;
                    if (this.value instanceof byte[][]) {
                        byte[][] bArr = (byte[][]) this.value;
                        byte[][] bArr2 = new byte[bArr.length][];
                        aic.value = bArr2;
                        while (i < bArr.length) {
                            bArr2[i] = (byte[]) bArr[i].clone();
                            i++;
                        }
                    } else if (this.value instanceof boolean[]) {
                        clone = ((boolean[]) this.value).clone();
                    } else if (this.value instanceof int[]) {
                        clone = ((int[]) this.value).clone();
                    } else if (this.value instanceof long[]) {
                        clone = ((long[]) this.value).clone();
                    } else if (this.value instanceof float[]) {
                        clone = ((float[]) this.value).clone();
                    } else if (this.value instanceof double[]) {
                        clone = ((double[]) this.value).clone();
                    } else if (this.value instanceof aif[]) {
                        aif[] aifArr = (aif[]) this.value;
                        aif[] aifArr2 = new aif[aifArr.length];
                        aic.value = aifArr2;
                        while (i < aifArr.length) {
                            aifArr2[i] = (aif) aifArr[i].clone();
                            i++;
                        }
                    }
                }
                aic.value = clone;
            }
            return aic;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        List<aii> list;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof aic)) {
            return false;
        }
        aic aic = (aic) obj;
        if (this.value == null || aic.value == null) {
            List<aii> list2 = this.zzcvd;
            if (list2 != null && (list = aic.zzcvd) != null) {
                return list2.equals(list);
            }
            try {
                return Arrays.equals(toByteArray(), aic.toByteArray());
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        } else {
            aia<?, ?> aia = this.zzcvc;
            if (aia != aic.zzcvc) {
                return false;
            }
            if (!aia.zzcmA.isArray()) {
                return this.value.equals(aic.value);
            }
            Object obj2 = this.value;
            return obj2 instanceof byte[] ? Arrays.equals((byte[]) obj2, (byte[]) aic.value) : obj2 instanceof int[] ? Arrays.equals((int[]) obj2, (int[]) aic.value) : obj2 instanceof long[] ? Arrays.equals((long[]) obj2, (long[]) aic.value) : obj2 instanceof float[] ? Arrays.equals((float[]) obj2, (float[]) aic.value) : obj2 instanceof double[] ? Arrays.equals((double[]) obj2, (double[]) aic.value) : obj2 instanceof boolean[] ? Arrays.equals((boolean[]) obj2, (boolean[]) aic.value) : Arrays.deepEquals((Object[]) obj2, (Object[]) aic.value);
        }
    }

    public final int hashCode() {
        try {
            return Arrays.hashCode(toByteArray()) + 527;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /* access modifiers changed from: package-private */
    public final void zza(ahx ahx) throws IOException {
        Object obj = this.value;
        if (obj != null) {
            this.zzcvc.zza(obj, ahx);
            return;
        }
        for (aii next : this.zzcvd) {
            ahx.zzct(next.tag);
            ahx.zzL(next.zzbww);
        }
    }

    /* access modifiers changed from: package-private */
    public final void zza(aii aii) {
        this.zzcvd.add(aii);
    }

    /* access modifiers changed from: package-private */
    public final <T> T zzb(aia<?, T> aia) {
        if (this.value == null) {
            this.zzcvc = aia;
            this.value = aia.zzY(this.zzcvd);
            this.zzcvd = null;
        } else if (!this.zzcvc.equals(aia)) {
            throw new IllegalStateException("Tried to getExtension with a different Extension.");
        }
        return this.value;
    }

    /* access modifiers changed from: package-private */
    public final int zzn() {
        Object obj = this.value;
        if (obj != null) {
            return this.zzcvc.zzav(obj);
        }
        int i = 0;
        for (aii next : this.zzcvd) {
            i += ahx.zzcu(next.tag) + 0 + next.zzbww.length;
        }
        return i;
    }
}
