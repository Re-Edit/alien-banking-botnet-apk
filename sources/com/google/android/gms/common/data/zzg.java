package com.google.android.gms.common.data;

import java.util.ArrayList;

public abstract class zzg<T> extends AbstractDataBuffer<T> {
    private boolean zzaFQ = false;
    private ArrayList<Integer> zzaFR;

    protected zzg(DataHolder dataHolder) {
        super(dataHolder);
    }

    private final int zzaw(int i) {
        if (i >= 0 && i < this.zzaFR.size()) {
            return this.zzaFR.get(i).intValue();
        }
        StringBuilder sb = new StringBuilder(53);
        sb.append("Position ");
        sb.append(i);
        sb.append(" is out of bounds for this buffer");
        throw new IllegalArgumentException(sb.toString());
    }

    private final void zzqR() {
        synchronized (this) {
            if (!this.zzaFQ) {
                int i = this.zzaCZ.zzaFI;
                this.zzaFR = new ArrayList<>();
                if (i > 0) {
                    this.zzaFR.add(0);
                    String zzqQ = zzqQ();
                    String zzd = this.zzaCZ.zzd(zzqQ, 0, this.zzaCZ.zzat(0));
                    int i2 = 1;
                    while (i2 < i) {
                        int zzat = this.zzaCZ.zzat(i2);
                        String zzd2 = this.zzaCZ.zzd(zzqQ, i2, zzat);
                        if (zzd2 != null) {
                            if (!zzd2.equals(zzd)) {
                                this.zzaFR.add(Integer.valueOf(i2));
                                zzd = zzd2;
                            }
                            i2++;
                        } else {
                            StringBuilder sb = new StringBuilder(String.valueOf(zzqQ).length() + 78);
                            sb.append("Missing value for markerColumn: ");
                            sb.append(zzqQ);
                            sb.append(", at row: ");
                            sb.append(i2);
                            sb.append(", for window: ");
                            sb.append(zzat);
                            throw new NullPointerException(sb.toString());
                        }
                    }
                }
                this.zzaFQ = true;
            }
        }
    }

    public final T get(int i) {
        int i2;
        zzqR();
        int zzaw = zzaw(i);
        if (i < 0 || i == this.zzaFR.size()) {
            i2 = 0;
        } else {
            i2 = (i == this.zzaFR.size() - 1 ? this.zzaCZ.zzaFI : this.zzaFR.get(i + 1).intValue()) - this.zzaFR.get(i).intValue();
            if (i2 == 1) {
                this.zzaCZ.zzat(zzaw(i));
            }
        }
        return zzi(zzaw, i2);
    }

    public int getCount() {
        zzqR();
        return this.zzaFR.size();
    }

    /* access modifiers changed from: protected */
    public abstract T zzi(int i, int i2);

    /* access modifiers changed from: protected */
    public abstract String zzqQ();
}
