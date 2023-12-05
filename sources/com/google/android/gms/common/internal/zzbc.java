package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.internal.zzee;

public abstract class zzbc extends zzee implements zzbb {
    public static zzbb zzJ(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGoogleCertificatesApi");
        return queryLocalInterface instanceof zzbb ? (zzbb) queryLocalInterface : new zzbd(iBinder);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0053, code lost:
        r4.writeNoException();
        com.google.android.gms.internal.zzef.zza(r4, (android.os.IInterface) r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0059, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0043, code lost:
        r4.writeNoException();
        com.google.android.gms.internal.zzef.zza(r4, r2);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTransact(int r2, android.os.Parcel r3, android.os.Parcel r4, int r5) throws android.os.RemoteException {
        /*
            r1 = this;
            boolean r5 = r1.zza(r2, r3, r4, r5)
            r0 = 1
            if (r5 == 0) goto L_0x0008
            return r0
        L_0x0008:
            switch(r2) {
                case 1: goto L_0x004f;
                case 2: goto L_0x004a;
                case 3: goto L_0x0033;
                case 4: goto L_0x0022;
                case 5: goto L_0x000d;
                default: goto L_0x000b;
            }
        L_0x000b:
            r2 = 0
            return r2
        L_0x000d:
            android.os.Parcelable$Creator<com.google.android.gms.common.zzm> r2 = com.google.android.gms.common.zzm.CREATOR
            android.os.Parcelable r2 = com.google.android.gms.internal.zzef.zza((android.os.Parcel) r3, r2)
            com.google.android.gms.common.zzm r2 = (com.google.android.gms.common.zzm) r2
            android.os.IBinder r3 = r3.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r3 = com.google.android.gms.dynamic.IObjectWrapper.zza.zzM(r3)
            boolean r2 = r1.zza(r2, r3)
            goto L_0x0043
        L_0x0022:
            java.lang.String r2 = r3.readString()
            android.os.IBinder r3 = r3.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r3 = com.google.android.gms.dynamic.IObjectWrapper.zza.zzM(r3)
            boolean r2 = r1.zzf(r2, r3)
            goto L_0x0043
        L_0x0033:
            java.lang.String r2 = r3.readString()
            android.os.IBinder r3 = r3.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r3 = com.google.android.gms.dynamic.IObjectWrapper.zza.zzM(r3)
            boolean r2 = r1.zze(r2, r3)
        L_0x0043:
            r4.writeNoException()
            com.google.android.gms.internal.zzef.zza((android.os.Parcel) r4, (boolean) r2)
            goto L_0x0059
        L_0x004a:
            com.google.android.gms.dynamic.IObjectWrapper r2 = r1.zzrF()
            goto L_0x0053
        L_0x004f:
            com.google.android.gms.dynamic.IObjectWrapper r2 = r1.zzrE()
        L_0x0053:
            r4.writeNoException()
            com.google.android.gms.internal.zzef.zza((android.os.Parcel) r4, (android.os.IInterface) r2)
        L_0x0059:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzbc.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
    }
}
