package com.google.android.gms.auth.api.signin;

import android.os.Parcelable;

public final class zzd implements Parcelable.Creator<GoogleSignInOptions> {
    /* JADX WARNING: type inference failed for: r1v3, types: [android.os.Parcelable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object createFromParcel(android.os.Parcel r14) {
        /*
            r13 = this;
            int r0 = com.google.android.gms.common.internal.safeparcel.zzb.zzd(r14)
            r1 = 0
            r2 = 0
            r5 = r2
            r6 = r5
            r10 = r6
            r11 = r10
            r12 = r11
            r4 = 0
            r7 = 0
            r8 = 0
            r9 = 0
        L_0x000f:
            int r1 = r14.dataPosition()
            if (r1 >= r0) goto L_0x005a
            int r1 = r14.readInt()
            r2 = 65535(0xffff, float:9.1834E-41)
            r2 = r2 & r1
            switch(r2) {
                case 1: goto L_0x0055;
                case 2: goto L_0x004e;
                case 3: goto L_0x0044;
                case 4: goto L_0x003f;
                case 5: goto L_0x003a;
                case 6: goto L_0x0035;
                case 7: goto L_0x0030;
                case 8: goto L_0x002b;
                case 9: goto L_0x0024;
                default: goto L_0x0020;
            }
        L_0x0020:
            com.google.android.gms.common.internal.safeparcel.zzb.zzb(r14, r1)
            goto L_0x000f
        L_0x0024:
            android.os.Parcelable$Creator<com.google.android.gms.auth.api.signin.internal.zzn> r2 = com.google.android.gms.auth.api.signin.internal.zzn.CREATOR
            java.util.ArrayList r12 = com.google.android.gms.common.internal.safeparcel.zzb.zzc(r14, r1, r2)
            goto L_0x000f
        L_0x002b:
            java.lang.String r11 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(r14, r1)
            goto L_0x000f
        L_0x0030:
            java.lang.String r10 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(r14, r1)
            goto L_0x000f
        L_0x0035:
            boolean r9 = com.google.android.gms.common.internal.safeparcel.zzb.zzc(r14, r1)
            goto L_0x000f
        L_0x003a:
            boolean r8 = com.google.android.gms.common.internal.safeparcel.zzb.zzc(r14, r1)
            goto L_0x000f
        L_0x003f:
            boolean r7 = com.google.android.gms.common.internal.safeparcel.zzb.zzc(r14, r1)
            goto L_0x000f
        L_0x0044:
            android.os.Parcelable$Creator r2 = android.accounts.Account.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.common.internal.safeparcel.zzb.zza((android.os.Parcel) r14, (int) r1, r2)
            r6 = r1
            android.accounts.Account r6 = (android.accounts.Account) r6
            goto L_0x000f
        L_0x004e:
            android.os.Parcelable$Creator<com.google.android.gms.common.api.Scope> r2 = com.google.android.gms.common.api.Scope.CREATOR
            java.util.ArrayList r5 = com.google.android.gms.common.internal.safeparcel.zzb.zzc(r14, r1, r2)
            goto L_0x000f
        L_0x0055:
            int r4 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(r14, r1)
            goto L_0x000f
        L_0x005a:
            com.google.android.gms.common.internal.safeparcel.zzb.zzF(r14, r0)
            com.google.android.gms.auth.api.signin.GoogleSignInOptions r14 = new com.google.android.gms.auth.api.signin.GoogleSignInOptions
            r3 = r14
            r3.<init>((int) r4, (java.util.ArrayList<com.google.android.gms.common.api.Scope>) r5, (android.accounts.Account) r6, (boolean) r7, (boolean) r8, (boolean) r9, (java.lang.String) r10, (java.lang.String) r11, (java.util.ArrayList<com.google.android.gms.auth.api.signin.internal.zzn>) r12)
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.api.signin.zzd.createFromParcel(android.os.Parcel):java.lang.Object");
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new GoogleSignInOptions[i];
    }
}
