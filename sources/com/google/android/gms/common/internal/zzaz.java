package com.google.android.gms.common.internal;

import android.os.Binder;
import android.os.IBinder;

public abstract class zzaz extends Binder implements zzay {
    public zzaz() {
        attachInterface(this, "com.google.android.gms.common.internal.IGmsServiceBroker");
    }

    public IBinder asBinder() {
        return this;
    }

    /* JADX WARNING: type inference failed for: r1v2, types: [android.os.IInterface] */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0092, code lost:
        if (r5.readInt() != 0) goto L_0x00c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00af, code lost:
        if (r5.readInt() != 0) goto L_0x00c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00bf, code lost:
        if (r5.readInt() != 0) goto L_0x00c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00c1, code lost:
        android.os.Bundle.CREATOR.createFromParcel(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00d5, code lost:
        if (r5.readInt() != 0) goto L_0x00c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00dc, code lost:
        if (r5.readInt() != 0) goto L_0x00c1;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTransact(int r4, android.os.Parcel r5, android.os.Parcel r6, int r7) throws android.os.RemoteException {
        /*
            r3 = this;
            r0 = 16777215(0xffffff, float:2.3509886E-38)
            if (r4 <= r0) goto L_0x000a
            boolean r4 = super.onTransact(r4, r5, r6, r7)
            return r4
        L_0x000a:
            java.lang.String r7 = "com.google.android.gms.common.internal.IGmsServiceBroker"
            r5.enforceInterface(r7)
            android.os.IBinder r7 = r5.readStrongBinder()
            r0 = 0
            if (r7 != 0) goto L_0x0018
            r7 = r0
            goto L_0x002c
        L_0x0018:
            java.lang.String r1 = "com.google.android.gms.common.internal.IGmsCallbacks"
            android.os.IInterface r1 = r7.queryLocalInterface(r1)
            boolean r2 = r1 instanceof com.google.android.gms.common.internal.zzav
            if (r2 == 0) goto L_0x0026
            r7 = r1
            com.google.android.gms.common.internal.zzav r7 = (com.google.android.gms.common.internal.zzav) r7
            goto L_0x002c
        L_0x0026:
            com.google.android.gms.common.internal.zzax r1 = new com.google.android.gms.common.internal.zzax
            r1.<init>(r7)
            r7 = r1
        L_0x002c:
            r1 = 46
            if (r4 != r1) goto L_0x0047
            int r4 = r5.readInt()
            if (r4 == 0) goto L_0x003f
            android.os.Parcelable$Creator<com.google.android.gms.common.internal.zzy> r4 = com.google.android.gms.common.internal.zzy.CREATOR
            java.lang.Object r4 = r4.createFromParcel(r5)
            r0 = r4
            com.google.android.gms.common.internal.zzy r0 = (com.google.android.gms.common.internal.zzy) r0
        L_0x003f:
            r3.zza(r7, r0)
            r6.writeNoException()
            r4 = 1
            return r4
        L_0x0047:
            r6 = 47
            if (r4 != r6) goto L_0x005c
            int r4 = r5.readInt()
            if (r4 == 0) goto L_0x0056
            android.os.Parcelable$Creator<com.google.android.gms.common.internal.zzcc> r4 = com.google.android.gms.common.internal.zzcc.CREATOR
            r4.createFromParcel(r5)
        L_0x0056:
            java.lang.UnsupportedOperationException r4 = new java.lang.UnsupportedOperationException
            r4.<init>()
            throw r4
        L_0x005c:
            r5.readInt()
            r6 = 4
            if (r4 == r6) goto L_0x0065
            r5.readString()
        L_0x0065:
            r6 = 23
            if (r4 == r6) goto L_0x00d8
            r6 = 25
            if (r4 == r6) goto L_0x00d8
            r6 = 27
            if (r4 == r6) goto L_0x00d8
            r6 = 30
            if (r4 == r6) goto L_0x00cb
            r6 = 34
            if (r4 == r6) goto L_0x00c7
            r6 = 41
            if (r4 == r6) goto L_0x00d8
            r6 = 43
            if (r4 == r6) goto L_0x00d8
            switch(r4) {
                case 1: goto L_0x00b2;
                case 2: goto L_0x00d8;
                default: goto L_0x0084;
            }
        L_0x0084:
            switch(r4) {
                case 5: goto L_0x00d8;
                case 6: goto L_0x00d8;
                case 7: goto L_0x00d8;
                case 8: goto L_0x00d8;
                case 9: goto L_0x009c;
                case 10: goto L_0x0095;
                case 11: goto L_0x00d8;
                case 12: goto L_0x00d8;
                case 13: goto L_0x00d8;
                case 14: goto L_0x00d8;
                case 15: goto L_0x00d8;
                case 16: goto L_0x00d8;
                case 17: goto L_0x00d8;
                case 18: goto L_0x00d8;
                case 19: goto L_0x008b;
                case 20: goto L_0x00cb;
                default: goto L_0x0087;
            }
        L_0x0087:
            switch(r4) {
                case 37: goto L_0x00d8;
                case 38: goto L_0x00d8;
                default: goto L_0x008a;
            }
        L_0x008a:
            goto L_0x00df
        L_0x008b:
            r5.readStrongBinder()
            int r4 = r5.readInt()
            if (r4 == 0) goto L_0x00df
            goto L_0x00c1
        L_0x0095:
            r5.readString()
            r5.createStringArray()
            goto L_0x00df
        L_0x009c:
            r5.readString()
            r5.createStringArray()
            r5.readString()
            r5.readStrongBinder()
            r5.readString()
            int r4 = r5.readInt()
            if (r4 == 0) goto L_0x00df
            goto L_0x00c1
        L_0x00b2:
            r5.readString()
            r5.createStringArray()
            r5.readString()
            int r4 = r5.readInt()
            if (r4 == 0) goto L_0x00df
        L_0x00c1:
            android.os.Parcelable$Creator r4 = android.os.Bundle.CREATOR
            r4.createFromParcel(r5)
            goto L_0x00df
        L_0x00c7:
            r5.readString()
            goto L_0x00df
        L_0x00cb:
            r5.createStringArray()
            r5.readString()
            int r4 = r5.readInt()
            if (r4 == 0) goto L_0x00df
            goto L_0x00c1
        L_0x00d8:
            int r4 = r5.readInt()
            if (r4 == 0) goto L_0x00df
            goto L_0x00c1
        L_0x00df:
            java.lang.UnsupportedOperationException r4 = new java.lang.UnsupportedOperationException
            r4.<init>()
            throw r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzaz.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
    }
}
