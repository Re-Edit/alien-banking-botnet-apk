package com.google.android.gms.common.data;

import android.os.Parcelable;

public final class zzf implements Parcelable.Creator<DataHolder> {
    /* JADX WARNING: type inference failed for: r1v3, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object createFromParcel(android.os.Parcel r10) {
        /*
            r9 = this;
            int r0 = com.google.android.gms.common.internal.safeparcel.zzb.zzd(r10)
            r1 = 0
            r2 = 0
            r5 = r2
            r6 = r5
            r8 = r6
            r4 = 0
            r7 = 0
        L_0x000b:
            int r1 = r10.dataPosition()
            if (r1 >= r0) goto L_0x0042
            int r1 = r10.readInt()
            r2 = 65535(0xffff, float:9.1834E-41)
            r2 = r2 & r1
            r3 = 1000(0x3e8, float:1.401E-42)
            if (r2 == r3) goto L_0x003d
            switch(r2) {
                case 1: goto L_0x0038;
                case 2: goto L_0x002e;
                case 3: goto L_0x0029;
                case 4: goto L_0x0024;
                default: goto L_0x0020;
            }
        L_0x0020:
            com.google.android.gms.common.internal.safeparcel.zzb.zzb(r10, r1)
            goto L_0x000b
        L_0x0024:
            android.os.Bundle r8 = com.google.android.gms.common.internal.safeparcel.zzb.zzs(r10, r1)
            goto L_0x000b
        L_0x0029:
            int r7 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(r10, r1)
            goto L_0x000b
        L_0x002e:
            android.os.Parcelable$Creator r2 = android.database.CursorWindow.CREATOR
            java.lang.Object[] r1 = com.google.android.gms.common.internal.safeparcel.zzb.zzb(r10, r1, r2)
            r6 = r1
            android.database.CursorWindow[] r6 = (android.database.CursorWindow[]) r6
            goto L_0x000b
        L_0x0038:
            java.lang.String[] r5 = com.google.android.gms.common.internal.safeparcel.zzb.zzA(r10, r1)
            goto L_0x000b
        L_0x003d:
            int r4 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(r10, r1)
            goto L_0x000b
        L_0x0042:
            com.google.android.gms.common.internal.safeparcel.zzb.zzF(r10, r0)
            com.google.android.gms.common.data.DataHolder r10 = new com.google.android.gms.common.data.DataHolder
            r3 = r10
            r3.<init>(r4, r5, r6, r7, r8)
            r10.zzqP()
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.data.zzf.createFromParcel(android.os.Parcel):java.lang.Object");
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new DataHolder[i];
    }
}
