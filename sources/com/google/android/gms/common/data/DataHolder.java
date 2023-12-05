package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzc;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@KeepName
public final class DataHolder extends com.google.android.gms.common.internal.safeparcel.zza implements Closeable {
    public static final Parcelable.Creator<DataHolder> CREATOR = new zzf();
    private static final zza zzaFK = new zze(new String[0], (String) null);
    private boolean mClosed;
    private final String[] zzaFD;
    private Bundle zzaFE;
    private final CursorWindow[] zzaFF;
    private final Bundle zzaFG;
    private int[] zzaFH;
    int zzaFI;
    private boolean zzaFJ;
    private int zzakw;
    private final int zzaxw;

    public static class zza {
        /* access modifiers changed from: private */
        public final String[] zzaFD;
        /* access modifiers changed from: private */
        public final ArrayList<HashMap<String, Object>> zzaFL;
        private final String zzaFM;
        private final HashMap<Object, Integer> zzaFN;
        private boolean zzaFO;
        private String zzaFP;

        private zza(String[] strArr, String str) {
            this.zzaFD = (String[]) zzbr.zzu(strArr);
            this.zzaFL = new ArrayList<>();
            this.zzaFM = str;
            this.zzaFN = new HashMap<>();
            this.zzaFO = false;
            this.zzaFP = null;
        }

        /* synthetic */ zza(String[] strArr, String str, zze zze) {
            this(strArr, (String) null);
        }

        public zza zza(ContentValues contentValues) {
            zzc.zzr(contentValues);
            HashMap hashMap = new HashMap(contentValues.size());
            for (Map.Entry next : contentValues.valueSet()) {
                hashMap.put((String) next.getKey(), next.getValue());
            }
            return zza((HashMap<String, Object>) hashMap);
        }

        /* JADX WARNING: Removed duplicated region for block: B:10:0x0031  */
        /* JADX WARNING: Removed duplicated region for block: B:11:0x0037  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public com.google.android.gms.common.data.DataHolder.zza zza(java.util.HashMap<java.lang.String, java.lang.Object> r5) {
            /*
                r4 = this;
                com.google.android.gms.common.internal.zzc.zzr(r5)
                java.lang.String r0 = r4.zzaFM
                r1 = -1
                if (r0 != 0) goto L_0x000a
            L_0x0008:
                r0 = -1
                goto L_0x002f
            L_0x000a:
                java.lang.Object r0 = r5.get(r0)
                if (r0 != 0) goto L_0x0011
                goto L_0x0008
            L_0x0011:
                java.util.HashMap<java.lang.Object, java.lang.Integer> r2 = r4.zzaFN
                java.lang.Object r2 = r2.get(r0)
                java.lang.Integer r2 = (java.lang.Integer) r2
                if (r2 != 0) goto L_0x002b
                java.util.HashMap<java.lang.Object, java.lang.Integer> r2 = r4.zzaFN
                java.util.ArrayList<java.util.HashMap<java.lang.String, java.lang.Object>> r3 = r4.zzaFL
                int r3 = r3.size()
                java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
                r2.put(r0, r3)
                goto L_0x0008
            L_0x002b:
                int r0 = r2.intValue()
            L_0x002f:
                if (r0 != r1) goto L_0x0037
                java.util.ArrayList<java.util.HashMap<java.lang.String, java.lang.Object>> r0 = r4.zzaFL
                r0.add(r5)
                goto L_0x0041
            L_0x0037:
                java.util.ArrayList<java.util.HashMap<java.lang.String, java.lang.Object>> r1 = r4.zzaFL
                r1.remove(r0)
                java.util.ArrayList<java.util.HashMap<java.lang.String, java.lang.Object>> r1 = r4.zzaFL
                r1.add(r0, r5)
            L_0x0041:
                r5 = 0
                r4.zzaFO = r5
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.data.DataHolder.zza.zza(java.util.HashMap):com.google.android.gms.common.data.DataHolder$zza");
        }

        public final DataHolder zzav(int i) {
            return new DataHolder(this, 0, (Bundle) null, (zze) null);
        }
    }

    public static class zzb extends RuntimeException {
        public zzb(String str) {
            super(str);
        }
    }

    DataHolder(int i, String[] strArr, CursorWindow[] cursorWindowArr, int i2, Bundle bundle) {
        this.mClosed = false;
        this.zzaFJ = true;
        this.zzakw = i;
        this.zzaFD = strArr;
        this.zzaFF = cursorWindowArr;
        this.zzaxw = i2;
        this.zzaFG = bundle;
    }

    private DataHolder(zza zza2, int i, Bundle bundle) {
        this(zza2.zzaFD, zza(zza2, -1), i, (Bundle) null);
    }

    /* synthetic */ DataHolder(zza zza2, int i, Bundle bundle, zze zze) {
        this(zza2, 0, (Bundle) null);
    }

    private DataHolder(String[] strArr, CursorWindow[] cursorWindowArr, int i, Bundle bundle) {
        this.mClosed = false;
        this.zzaFJ = true;
        this.zzakw = 1;
        this.zzaFD = (String[]) zzbr.zzu(strArr);
        this.zzaFF = (CursorWindow[]) zzbr.zzu(cursorWindowArr);
        this.zzaxw = i;
        this.zzaFG = bundle;
        zzqP();
    }

    public static zza zza(String[] strArr) {
        return new zza(strArr, (String) null, (zze) null);
    }

    private static CursorWindow[] zza(zza zza2, int i) {
        long j;
        if (zza2.zzaFD.length == 0) {
            return new CursorWindow[0];
        }
        ArrayList zzb2 = zza2.zzaFL;
        int size = zzb2.size();
        CursorWindow cursorWindow = new CursorWindow(false);
        ArrayList arrayList = new ArrayList();
        arrayList.add(cursorWindow);
        cursorWindow.setNumColumns(zza2.zzaFD.length);
        CursorWindow cursorWindow2 = cursorWindow;
        int i2 = 0;
        boolean z = false;
        while (i2 < size) {
            try {
                if (!cursorWindow2.allocRow()) {
                    StringBuilder sb = new StringBuilder(72);
                    sb.append("Allocating additional cursor window for large data set (row ");
                    sb.append(i2);
                    sb.append(")");
                    Log.d("DataHolder", sb.toString());
                    cursorWindow2 = new CursorWindow(false);
                    cursorWindow2.setStartPosition(i2);
                    cursorWindow2.setNumColumns(zza2.zzaFD.length);
                    arrayList.add(cursorWindow2);
                    if (!cursorWindow2.allocRow()) {
                        Log.e("DataHolder", "Unable to allocate row to hold data.");
                        arrayList.remove(cursorWindow2);
                        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
                    }
                }
                Map map = (Map) zzb2.get(i2);
                boolean z2 = true;
                for (int i3 = 0; i3 < zza2.zzaFD.length && z2; i3++) {
                    String str = zza2.zzaFD[i3];
                    Object obj = map.get(str);
                    if (obj == null) {
                        z2 = cursorWindow2.putNull(i2, i3);
                    } else if (obj instanceof String) {
                        z2 = cursorWindow2.putString((String) obj, i2, i3);
                    } else {
                        if (obj instanceof Long) {
                            j = ((Long) obj).longValue();
                        } else if (obj instanceof Integer) {
                            z2 = cursorWindow2.putLong((long) ((Integer) obj).intValue(), i2, i3);
                        } else if (obj instanceof Boolean) {
                            j = ((Boolean) obj).booleanValue() ? 1 : 0;
                        } else if (obj instanceof byte[]) {
                            z2 = cursorWindow2.putBlob((byte[]) obj, i2, i3);
                        } else if (obj instanceof Double) {
                            z2 = cursorWindow2.putDouble(((Double) obj).doubleValue(), i2, i3);
                        } else if (obj instanceof Float) {
                            z2 = cursorWindow2.putDouble((double) ((Float) obj).floatValue(), i2, i3);
                        } else {
                            String valueOf = String.valueOf(obj);
                            StringBuilder sb2 = new StringBuilder(String.valueOf(str).length() + 32 + String.valueOf(valueOf).length());
                            sb2.append("Unsupported object for column ");
                            sb2.append(str);
                            sb2.append(": ");
                            sb2.append(valueOf);
                            throw new IllegalArgumentException(sb2.toString());
                        }
                        z2 = cursorWindow2.putLong(j, i2, i3);
                    }
                }
                if (z2) {
                    z = false;
                } else if (!z) {
                    StringBuilder sb3 = new StringBuilder(74);
                    sb3.append("Couldn't populate window data for row ");
                    sb3.append(i2);
                    sb3.append(" - allocating new window.");
                    Log.d("DataHolder", sb3.toString());
                    cursorWindow2.freeLastRow();
                    cursorWindow2 = new CursorWindow(false);
                    cursorWindow2.setStartPosition(i2);
                    cursorWindow2.setNumColumns(zza2.zzaFD.length);
                    arrayList.add(cursorWindow2);
                    i2--;
                    z = true;
                } else {
                    throw new zzb("Could not add the value to a new CursorWindow. The size of value may be larger than what a CursorWindow can handle.");
                }
                i2++;
            } catch (RuntimeException e) {
                int size2 = arrayList.size();
                for (int i4 = 0; i4 < size2; i4++) {
                    ((CursorWindow) arrayList.get(i4)).close();
                }
                throw e;
            }
        }
        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
    }

    public static DataHolder zzau(int i) {
        return new DataHolder(zzaFK, i, (Bundle) null);
    }

    private final void zzh(String str, int i) {
        Bundle bundle = this.zzaFE;
        if (bundle == null || !bundle.containsKey(str)) {
            String valueOf = String.valueOf(str);
            throw new IllegalArgumentException(valueOf.length() != 0 ? "No such column: ".concat(valueOf) : new String("No such column: "));
        } else if (isClosed()) {
            throw new IllegalArgumentException("Buffer is closed.");
        } else if (i < 0 || i >= this.zzaFI) {
            throw new CursorIndexOutOfBoundsException(i, this.zzaFI);
        }
    }

    public final void close() {
        synchronized (this) {
            if (!this.mClosed) {
                this.mClosed = true;
                for (CursorWindow close : this.zzaFF) {
                    close.close();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void finalize() throws Throwable {
        try {
            if (this.zzaFJ && this.zzaFF.length > 0 && !isClosed()) {
                close();
                String valueOf = String.valueOf(toString());
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 178);
                sb.append("Internal data leak within a DataBuffer object detected!  Be sure to explicitly call release() on all DataBuffer extending objects when you are done with them. (internal object: ");
                sb.append(valueOf);
                sb.append(")");
                Log.e("DataBuffer", sb.toString());
            }
        } finally {
            super.finalize();
        }
    }

    public final int getCount() {
        return this.zzaFI;
    }

    public final int getStatusCode() {
        return this.zzaxw;
    }

    public final boolean isClosed() {
        boolean z;
        synchronized (this) {
            z = this.mClosed;
        }
        return z;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzaFD, false);
        zzd.zza(parcel, 2, (T[]) this.zzaFF, i, false);
        zzd.zzc(parcel, 3, this.zzaxw);
        zzd.zza(parcel, 4, this.zzaFG, false);
        zzd.zzc(parcel, 1000, this.zzakw);
        zzd.zzI(parcel, zze);
    }

    public final void zza(String str, int i, int i2, CharArrayBuffer charArrayBuffer) {
        zzh(str, i);
        this.zzaFF[i2].copyStringToBuffer(i, this.zzaFE.getInt(str), charArrayBuffer);
    }

    public final int zzat(int i) {
        int i2 = 0;
        zzbr.zzae(i >= 0 && i < this.zzaFI);
        while (true) {
            int[] iArr = this.zzaFH;
            if (i2 >= iArr.length) {
                break;
            } else if (i < iArr[i2]) {
                i2--;
                break;
            } else {
                i2++;
            }
        }
        return i2 == this.zzaFH.length ? i2 - 1 : i2;
    }

    public final long zzb(String str, int i, int i2) {
        zzh(str, i);
        return this.zzaFF[i2].getLong(i, this.zzaFE.getInt(str));
    }

    public final int zzc(String str, int i, int i2) {
        zzh(str, i);
        return this.zzaFF[i2].getInt(i, this.zzaFE.getInt(str));
    }

    public final boolean zzcv(String str) {
        return this.zzaFE.containsKey(str);
    }

    public final String zzd(String str, int i, int i2) {
        zzh(str, i);
        return this.zzaFF[i2].getString(i, this.zzaFE.getInt(str));
    }

    public final boolean zze(String str, int i, int i2) {
        zzh(str, i);
        return Long.valueOf(this.zzaFF[i2].getLong(i, this.zzaFE.getInt(str))).longValue() == 1;
    }

    public final float zzf(String str, int i, int i2) {
        zzh(str, i);
        return this.zzaFF[i2].getFloat(i, this.zzaFE.getInt(str));
    }

    public final byte[] zzg(String str, int i, int i2) {
        zzh(str, i);
        return this.zzaFF[i2].getBlob(i, this.zzaFE.getInt(str));
    }

    public final boolean zzh(String str, int i, int i2) {
        zzh(str, i);
        return this.zzaFF[i2].isNull(i, this.zzaFE.getInt(str));
    }

    public final Bundle zzqL() {
        return this.zzaFG;
    }

    public final void zzqP() {
        this.zzaFE = new Bundle();
        int i = 0;
        int i2 = 0;
        while (true) {
            String[] strArr = this.zzaFD;
            if (i2 >= strArr.length) {
                break;
            }
            this.zzaFE.putInt(strArr[i2], i2);
            i2++;
        }
        this.zzaFH = new int[this.zzaFF.length];
        int i3 = 0;
        while (true) {
            CursorWindow[] cursorWindowArr = this.zzaFF;
            if (i < cursorWindowArr.length) {
                this.zzaFH[i] = i3;
                i3 += this.zzaFF[i].getNumRows() - (i3 - cursorWindowArr[i].getStartPosition());
                i++;
            } else {
                this.zzaFI = i3;
                return;
            }
        }
    }
}
