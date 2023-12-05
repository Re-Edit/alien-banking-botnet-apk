package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.common.util.zzr;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class zzbif extends zzbhx {
    public static final Parcelable.Creator<zzbif> CREATOR = new zzbig();
    private final String mClassName;
    private final zzbia zzaIR;
    private final Parcel zzaIY;
    private final int zzaIZ = 2;
    private int zzaJa;
    private int zzaJb;
    private final int zzakw;

    zzbif(int i, Parcel parcel, zzbia zzbia) {
        this.zzakw = i;
        this.zzaIY = (Parcel) zzbr.zzu(parcel);
        this.zzaIR = zzbia;
        zzbia zzbia2 = this.zzaIR;
        this.mClassName = zzbia2 == null ? null : zzbia2.zzrQ();
        this.zzaJa = 2;
    }

    private static void zza(StringBuilder sb, int i, Object obj) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                sb.append(obj);
                return;
            case 7:
                sb.append("\"");
                sb.append(zzq.zzcK(obj.toString()));
                sb.append("\"");
                return;
            case 8:
                sb.append("\"");
                sb.append(zzd.zzg((byte[]) obj));
                sb.append("\"");
                return;
            case 9:
                sb.append("\"");
                sb.append(zzd.zzh((byte[]) obj));
                sb.append("\"");
                return;
            case 10:
                zzr.zza(sb, (HashMap) obj);
                return;
            case 11:
                throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
                StringBuilder sb2 = new StringBuilder(26);
                sb2.append("Unknown type = ");
                sb2.append(i);
                throw new IllegalArgumentException(sb2.toString());
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: double[]} */
    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zza(java.lang.StringBuilder r6, com.google.android.gms.internal.zzbhv<?, ?> r7, android.os.Parcel r8, int r9) {
        /*
            r5 = this;
            boolean r0 = r7.zzaIM
            r1 = 0
            if (r0 == 0) goto L_0x00cb
            java.lang.String r0 = "["
            r6.append(r0)
            int r0 = r7.zzaIL
            r2 = 0
            switch(r0) {
                case 0: goto L_0x00ab;
                case 1: goto L_0x0082;
                case 2: goto L_0x007a;
                case 3: goto L_0x0072;
                case 4: goto L_0x005b;
                case 5: goto L_0x0052;
                case 6: goto L_0x0049;
                case 7: goto L_0x0040;
                case 8: goto L_0x0038;
                case 9: goto L_0x0038;
                case 10: goto L_0x0038;
                case 11: goto L_0x0018;
                default: goto L_0x0010;
            }
        L_0x0010:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "Unknown field type out."
            r6.<init>(r7)
            throw r6
        L_0x0018:
            android.os.Parcel[] r8 = com.google.android.gms.common.internal.safeparcel.zzb.zzE(r8, r9)
            int r9 = r8.length
            r0 = 0
        L_0x001e:
            if (r0 >= r9) goto L_0x00c5
            if (r0 <= 0) goto L_0x0027
            java.lang.String r2 = ","
            r6.append(r2)
        L_0x0027:
            r2 = r8[r0]
            r2.setDataPosition(r1)
            java.util.Map r2 = r7.zzrO()
            r3 = r8[r0]
            r5.zza((java.lang.StringBuilder) r6, (java.util.Map<java.lang.String, com.google.android.gms.internal.zzbhv<?, ?>>) r2, (android.os.Parcel) r3)
            int r0 = r0 + 1
            goto L_0x001e
        L_0x0038:
            java.lang.UnsupportedOperationException r6 = new java.lang.UnsupportedOperationException
            java.lang.String r7 = "List of type BASE64, BASE64_URL_SAFE, or STRING_MAP is not supported"
            r6.<init>(r7)
            throw r6
        L_0x0040:
            java.lang.String[] r7 = com.google.android.gms.common.internal.safeparcel.zzb.zzA(r8, r9)
            com.google.android.gms.common.util.zzc.zza((java.lang.StringBuilder) r6, (java.lang.String[]) r7)
            goto L_0x00c5
        L_0x0049:
            boolean[] r7 = com.google.android.gms.common.internal.safeparcel.zzb.zzv(r8, r9)
            com.google.android.gms.common.util.zzc.zza((java.lang.StringBuilder) r6, (boolean[]) r7)
            goto L_0x00c5
        L_0x0052:
            java.math.BigDecimal[] r7 = com.google.android.gms.common.internal.safeparcel.zzb.zzz(r8, r9)
            com.google.android.gms.common.util.zzc.zza((java.lang.StringBuilder) r6, (T[]) r7)
            goto L_0x00c5
        L_0x005b:
            int r7 = com.google.android.gms.common.internal.safeparcel.zzb.zza(r8, r9)
            int r9 = r8.dataPosition()
            if (r7 != 0) goto L_0x0066
            goto L_0x006e
        L_0x0066:
            double[] r2 = r8.createDoubleArray()
            int r9 = r9 + r7
            r8.setDataPosition(r9)
        L_0x006e:
            com.google.android.gms.common.util.zzc.zza((java.lang.StringBuilder) r6, (double[]) r2)
            goto L_0x00c5
        L_0x0072:
            float[] r7 = com.google.android.gms.common.internal.safeparcel.zzb.zzy(r8, r9)
            com.google.android.gms.common.util.zzc.zza((java.lang.StringBuilder) r6, (float[]) r7)
            goto L_0x00c5
        L_0x007a:
            long[] r7 = com.google.android.gms.common.internal.safeparcel.zzb.zzx(r8, r9)
            com.google.android.gms.common.util.zzc.zza((java.lang.StringBuilder) r6, (long[]) r7)
            goto L_0x00c5
        L_0x0082:
            int r7 = com.google.android.gms.common.internal.safeparcel.zzb.zza(r8, r9)
            int r9 = r8.dataPosition()
            if (r7 != 0) goto L_0x008d
            goto L_0x00a7
        L_0x008d:
            int r0 = r8.readInt()
            java.math.BigInteger[] r2 = new java.math.BigInteger[r0]
        L_0x0093:
            if (r1 >= r0) goto L_0x00a3
            java.math.BigInteger r3 = new java.math.BigInteger
            byte[] r4 = r8.createByteArray()
            r3.<init>(r4)
            r2[r1] = r3
            int r1 = r1 + 1
            goto L_0x0093
        L_0x00a3:
            int r9 = r9 + r7
            r8.setDataPosition(r9)
        L_0x00a7:
            com.google.android.gms.common.util.zzc.zza((java.lang.StringBuilder) r6, (T[]) r2)
            goto L_0x00c5
        L_0x00ab:
            int[] r7 = com.google.android.gms.common.internal.safeparcel.zzb.zzw(r8, r9)
            int r8 = r7.length
        L_0x00b0:
            if (r1 >= r8) goto L_0x00c5
            if (r1 == 0) goto L_0x00b9
            java.lang.String r9 = ","
            r6.append(r9)
        L_0x00b9:
            r9 = r7[r1]
            java.lang.String r9 = java.lang.Integer.toString(r9)
            r6.append(r9)
            int r1 = r1 + 1
            goto L_0x00b0
        L_0x00c5:
            java.lang.String r7 = "]"
            r6.append(r7)
            return
        L_0x00cb:
            int r0 = r7.zzaIL
            switch(r0) {
                case 0: goto L_0x01b0;
                case 1: goto L_0x01a8;
                case 2: goto L_0x01a0;
                case 3: goto L_0x0198;
                case 4: goto L_0x0190;
                case 5: goto L_0x0188;
                case 6: goto L_0x0180;
                case 7: goto L_0x016a;
                case 8: goto L_0x0154;
                case 9: goto L_0x013e;
                case 10: goto L_0x00e7;
                case 11: goto L_0x00d8;
                default: goto L_0x00d0;
            }
        L_0x00d0:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "Unknown field type out"
            r6.<init>(r7)
            throw r6
        L_0x00d8:
            android.os.Parcel r8 = com.google.android.gms.common.internal.safeparcel.zzb.zzD(r8, r9)
            r8.setDataPosition(r1)
            java.util.Map r7 = r7.zzrO()
            r5.zza((java.lang.StringBuilder) r6, (java.util.Map<java.lang.String, com.google.android.gms.internal.zzbhv<?, ?>>) r7, (android.os.Parcel) r8)
            return
        L_0x00e7:
            android.os.Bundle r7 = com.google.android.gms.common.internal.safeparcel.zzb.zzs(r8, r9)
            java.util.Set r8 = r7.keySet()
            r8.size()
            java.lang.String r9 = "{"
            r6.append(r9)
            java.util.Iterator r8 = r8.iterator()
            r9 = 1
        L_0x00fc:
            boolean r0 = r8.hasNext()
            if (r0 == 0) goto L_0x0138
            java.lang.Object r0 = r8.next()
            java.lang.String r0 = (java.lang.String) r0
            if (r9 != 0) goto L_0x010f
            java.lang.String r9 = ","
            r6.append(r9)
        L_0x010f:
            java.lang.String r9 = "\""
            r6.append(r9)
            r6.append(r0)
            java.lang.String r9 = "\""
            r6.append(r9)
            java.lang.String r9 = ":"
            r6.append(r9)
            java.lang.String r9 = "\""
            r6.append(r9)
            java.lang.String r9 = r7.getString(r0)
            java.lang.String r9 = com.google.android.gms.common.util.zzq.zzcK(r9)
            r6.append(r9)
            java.lang.String r9 = "\""
            r6.append(r9)
            r9 = 0
            goto L_0x00fc
        L_0x0138:
            java.lang.String r7 = "}"
            r6.append(r7)
            return
        L_0x013e:
            byte[] r7 = com.google.android.gms.common.internal.safeparcel.zzb.zzt(r8, r9)
            java.lang.String r8 = "\""
            r6.append(r8)
            java.lang.String r7 = com.google.android.gms.common.util.zzd.zzh(r7)
            r6.append(r7)
            java.lang.String r7 = "\""
            r6.append(r7)
            return
        L_0x0154:
            byte[] r7 = com.google.android.gms.common.internal.safeparcel.zzb.zzt(r8, r9)
            java.lang.String r8 = "\""
            r6.append(r8)
            java.lang.String r7 = com.google.android.gms.common.util.zzd.zzg(r7)
            r6.append(r7)
            java.lang.String r7 = "\""
            r6.append(r7)
            return
        L_0x016a:
            java.lang.String r7 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(r8, r9)
            java.lang.String r8 = "\""
            r6.append(r8)
            java.lang.String r7 = com.google.android.gms.common.util.zzq.zzcK(r7)
            r6.append(r7)
            java.lang.String r7 = "\""
            r6.append(r7)
            return
        L_0x0180:
            boolean r7 = com.google.android.gms.common.internal.safeparcel.zzb.zzc(r8, r9)
            r6.append(r7)
            return
        L_0x0188:
            java.math.BigDecimal r7 = com.google.android.gms.common.internal.safeparcel.zzb.zzp(r8, r9)
            r6.append(r7)
            return
        L_0x0190:
            double r7 = com.google.android.gms.common.internal.safeparcel.zzb.zzn(r8, r9)
            r6.append(r7)
            return
        L_0x0198:
            float r7 = com.google.android.gms.common.internal.safeparcel.zzb.zzl(r8, r9)
            r6.append(r7)
            return
        L_0x01a0:
            long r7 = com.google.android.gms.common.internal.safeparcel.zzb.zzi(r8, r9)
            r6.append(r7)
            return
        L_0x01a8:
            java.math.BigInteger r7 = com.google.android.gms.common.internal.safeparcel.zzb.zzk(r8, r9)
            r6.append(r7)
            return
        L_0x01b0:
            int r7 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(r8, r9)
            r6.append(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbif.zza(java.lang.StringBuilder, com.google.android.gms.internal.zzbhv, android.os.Parcel, int):void");
    }

    private final void zza(StringBuilder sb, Map<String, zzbhv<?, ?>> map, Parcel parcel) {
        Object obj;
        SparseArray sparseArray = new SparseArray();
        for (Map.Entry next : map.entrySet()) {
            sparseArray.put(((zzbhv) next.getValue()).zzaIO, next);
        }
        sb.append('{');
        int zzd = zzb.zzd(parcel);
        boolean z = false;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            Map.Entry entry = (Map.Entry) sparseArray.get(65535 & readInt);
            if (entry != null) {
                if (z) {
                    sb.append(",");
                }
                zzbhv zzbhv = (zzbhv) entry.getValue();
                sb.append("\"");
                sb.append((String) entry.getKey());
                sb.append("\":");
                if (zzbhv.zzrN()) {
                    switch (zzbhv.zzaIL) {
                        case 0:
                            obj = Integer.valueOf(zzb.zzg(parcel, readInt));
                            break;
                        case 1:
                            obj = zzb.zzk(parcel, readInt);
                            break;
                        case 2:
                            obj = Long.valueOf(zzb.zzi(parcel, readInt));
                            break;
                        case 3:
                            obj = Float.valueOf(zzb.zzl(parcel, readInt));
                            break;
                        case 4:
                            obj = Double.valueOf(zzb.zzn(parcel, readInt));
                            break;
                        case 5:
                            obj = zzb.zzp(parcel, readInt);
                            break;
                        case 6:
                            obj = Boolean.valueOf(zzb.zzc(parcel, readInt));
                            break;
                        case 7:
                            obj = zzb.zzq(parcel, readInt);
                            break;
                        case 8:
                        case 9:
                            obj = zzb.zzt(parcel, readInt);
                            break;
                        case 10:
                            obj = zzo(zzb.zzs(parcel, readInt));
                            break;
                        case 11:
                            throw new IllegalArgumentException("Method does not accept concrete type.");
                        default:
                            int i = zzbhv.zzaIL;
                            StringBuilder sb2 = new StringBuilder(36);
                            sb2.append("Unknown field out type = ");
                            sb2.append(i);
                            throw new IllegalArgumentException(sb2.toString());
                    }
                    zzb(sb, zzbhv, zza(zzbhv, obj));
                } else {
                    zza(sb, zzbhv, parcel, readInt);
                }
                z = true;
            }
        }
        if (parcel.dataPosition() == zzd) {
            sb.append('}');
            return;
        }
        StringBuilder sb3 = new StringBuilder(37);
        sb3.append("Overread allowed size end=");
        sb3.append(zzd);
        throw new zzc(sb3.toString(), parcel);
    }

    private final void zzb(StringBuilder sb, zzbhv<?, ?> zzbhv, Object obj) {
        if (zzbhv.zzaIK) {
            ArrayList arrayList = (ArrayList) obj;
            sb.append("[");
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (i != 0) {
                    sb.append(",");
                }
                zza(sb, zzbhv.zzaIJ, arrayList.get(i));
            }
            sb.append("]");
            return;
        }
        zza(sb, zzbhv.zzaIJ, obj);
    }

    private static HashMap<String, String> zzo(Bundle bundle) {
        HashMap<String, String> hashMap = new HashMap<>();
        for (String str : bundle.keySet()) {
            hashMap.put(str, bundle.getString(str));
        }
        return hashMap;
    }

    private Parcel zzrS() {
        switch (this.zzaJa) {
            case 0:
                this.zzaJb = com.google.android.gms.common.internal.safeparcel.zzd.zze(this.zzaIY);
                break;
            case 1:
                break;
        }
        com.google.android.gms.common.internal.safeparcel.zzd.zzI(this.zzaIY, this.zzaJb);
        this.zzaJa = 2;
        return this.zzaIY;
    }

    public String toString() {
        zzbr.zzb(this.zzaIR, (Object) "Cannot convert to JSON on client side.");
        Parcel zzrS = zzrS();
        zzrS.setDataPosition(0);
        StringBuilder sb = new StringBuilder(100);
        zza(sb, this.zzaIR.zzcJ(this.mClassName), zzrS);
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzbia zzbia;
        int zze = com.google.android.gms.common.internal.safeparcel.zzd.zze(parcel);
        com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 1, this.zzakw);
        com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 2, zzrS(), false);
        int i2 = this.zzaIZ;
        switch (i2) {
            case 0:
                zzbia = null;
                break;
            case 1:
            case 2:
                zzbia = this.zzaIR;
                break;
            default:
                StringBuilder sb = new StringBuilder(34);
                sb.append("Invalid creation type: ");
                sb.append(i2);
                throw new IllegalStateException(sb.toString());
        }
        com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, 3, (Parcelable) zzbia, i, false);
        com.google.android.gms.common.internal.safeparcel.zzd.zzI(parcel, zze);
    }

    public final Object zzcH(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    public final boolean zzcI(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    public final Map<String, zzbhv<?, ?>> zzrK() {
        zzbia zzbia = this.zzaIR;
        if (zzbia == null) {
            return null;
        }
        return zzbia.zzcJ(this.mClassName);
    }
}
