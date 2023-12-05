package com.google.android.gms.common.internal;

import android.os.Parcelable;

public final class zzz implements Parcelable.Creator<zzy> {
    /* JADX WARNING: type inference failed for: r1v3, types: [java.lang.Object[]] */
    /* JADX WARNING: type inference failed for: r1v4, types: [android.os.Parcelable] */
    /* JADX WARNING: type inference failed for: r1v5, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object createFromParcel(android.os.Parcel r14) {
        /*
            r13 = this;
            int r0 = com.google.android.gms.common.internal.safeparcel.zzb.zzd(r14)
            r1 = 0
            r2 = 0
            r7 = r2
            r8 = r7
            r9 = r8
            r10 = r9
            r11 = r10
            r12 = r11
            r4 = 0
            r5 = 0
            r6 = 0
        L_0x000f:
            int r1 = r14.dataPosition()
            if (r1 >= r0) goto L_0x0060
            int r1 = r14.readInt()
            r2 = 65535(0xffff, float:9.1834E-41)
            r2 = r2 & r1
            switch(r2) {
                case 1: goto L_0x005b;
                case 2: goto L_0x0056;
                case 3: goto L_0x0051;
                case 4: goto L_0x004c;
                case 5: goto L_0x0047;
                case 6: goto L_0x003d;
                case 7: goto L_0x0038;
                case 8: goto L_0x002e;
                case 9: goto L_0x0020;
                case 10: goto L_0x0024;
                default: goto L_0x0020;
            }
        L_0x0020:
            com.google.android.gms.common.internal.safeparcel.zzb.zzb(r14, r1)
            goto L_0x000f
        L_0x0024:
            android.os.Parcelable$Creator<com.google.android.gms.common.zzc> r2 = com.google.android.gms.common.zzc.CREATOR
            java.lang.Object[] r1 = com.google.android.gms.common.internal.safeparcel.zzb.zzb(r14, r1, r2)
            r12 = r1
            com.google.android.gms.common.zzc[] r12 = (com.google.android.gms.common.zzc[]) r12
            goto L_0x000f
        L_0x002e:
            android.os.Parcelable$Creator r2 = android.accounts.Account.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.common.internal.safeparcel.zzb.zza((android.os.Parcel) r14, (int) r1, r2)
            r11 = r1
            android.accounts.Account r11 = (android.accounts.Account) r11
            goto L_0x000f
        L_0x0038:
            android.os.Bundle r10 = com.google.android.gms.common.internal.safeparcel.zzb.zzs(r14, r1)
            goto L_0x000f
        L_0x003d:
            android.os.Parcelable$Creator<com.google.android.gms.common.api.Scope> r2 = com.google.android.gms.common.api.Scope.CREATOR
            java.lang.Object[] r1 = com.google.android.gms.common.internal.safeparcel.zzb.zzb(r14, r1, r2)
            r9 = r1
            com.google.android.gms.common.api.Scope[] r9 = (com.google.android.gms.common.api.Scope[]) r9
            goto L_0x000f
        L_0x0047:
            android.os.IBinder r8 = com.google.android.gms.common.internal.safeparcel.zzb.zzr(r14, r1)
            goto L_0x000f
        L_0x004c:
            java.lang.String r7 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(r14, r1)
            goto L_0x000f
        L_0x0051:
            int r6 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(r14, r1)
            goto L_0x000f
        L_0x0056:
            int r5 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(r14, r1)
            goto L_0x000f
        L_0x005b:
            int r4 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(r14, r1)
            goto L_0x000f
        L_0x0060:
            com.google.android.gms.common.internal.safeparcel.zzb.zzF(r14, r0)
            com.google.android.gms.common.internal.zzy r14 = new com.google.android.gms.common.internal.zzy
            r3 = r14
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11, r12)
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzz.createFromParcel(android.os.Parcel):java.lang.Object");
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzy[i];
    }
}
