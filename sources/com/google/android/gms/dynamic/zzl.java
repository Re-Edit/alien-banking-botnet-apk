package com.google.android.gms.dynamic;

import com.google.android.gms.internal.zzee;

public abstract class zzl extends zzee implements zzk {
    public zzl() {
        attachInterface(this, "com.google.android.gms.dynamic.IFragmentWrapper");
    }

    /* JADX WARNING: type inference failed for: r3v2, types: [android.os.IInterface] */
    /* JADX WARNING: type inference failed for: r3v5, types: [android.os.IInterface] */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0067, code lost:
        r4.writeNoException();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00d0, code lost:
        r4.writeNoException();
        com.google.android.gms.internal.zzef.zza(r4, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00e5, code lost:
        r4.writeNoException();
        r4.writeInt(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00fb, code lost:
        r4.writeNoException();
        com.google.android.gms.internal.zzef.zza(r4, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0101, code lost:
        return true;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTransact(int r2, android.os.Parcel r3, android.os.Parcel r4, int r5) throws android.os.RemoteException {
        /*
            r1 = this;
            boolean r5 = r1.zza(r2, r3, r4, r5)
            r0 = 1
            if (r5 == 0) goto L_0x0008
            return r0
        L_0x0008:
            r5 = 0
            switch(r2) {
                case 2: goto L_0x00f7;
                case 3: goto L_0x00ec;
                case 4: goto L_0x00e1;
                case 5: goto L_0x00dc;
                case 6: goto L_0x00d7;
                case 7: goto L_0x00cc;
                case 8: goto L_0x00c1;
                case 9: goto L_0x00bc;
                case 10: goto L_0x00b7;
                case 11: goto L_0x00b2;
                case 12: goto L_0x00ad;
                case 13: goto L_0x00a8;
                case 14: goto L_0x00a3;
                case 15: goto L_0x009e;
                case 16: goto L_0x0099;
                case 17: goto L_0x0094;
                case 18: goto L_0x008f;
                case 19: goto L_0x008a;
                case 20: goto L_0x006c;
                case 21: goto L_0x0060;
                case 22: goto L_0x0058;
                case 23: goto L_0x0050;
                case 24: goto L_0x0048;
                case 25: goto L_0x003c;
                case 26: goto L_0x002c;
                case 27: goto L_0x000e;
                default: goto L_0x000c;
            }
        L_0x000c:
            r2 = 0
            return r2
        L_0x000e:
            android.os.IBinder r2 = r3.readStrongBinder()
            if (r2 != 0) goto L_0x0015
            goto L_0x0028
        L_0x0015:
            java.lang.String r3 = "com.google.android.gms.dynamic.IObjectWrapper"
            android.os.IInterface r3 = r2.queryLocalInterface(r3)
            boolean r5 = r3 instanceof com.google.android.gms.dynamic.IObjectWrapper
            if (r5 == 0) goto L_0x0023
            r5 = r3
            com.google.android.gms.dynamic.IObjectWrapper r5 = (com.google.android.gms.dynamic.IObjectWrapper) r5
            goto L_0x0028
        L_0x0023:
            com.google.android.gms.dynamic.zzm r5 = new com.google.android.gms.dynamic.zzm
            r5.<init>(r2)
        L_0x0028:
            r1.zzD(r5)
            goto L_0x0067
        L_0x002c:
            android.os.Parcelable$Creator r2 = android.content.Intent.CREATOR
            android.os.Parcelable r2 = com.google.android.gms.internal.zzef.zza((android.os.Parcel) r3, r2)
            android.content.Intent r2 = (android.content.Intent) r2
            int r3 = r3.readInt()
            r1.startActivityForResult(r2, r3)
            goto L_0x0067
        L_0x003c:
            android.os.Parcelable$Creator r2 = android.content.Intent.CREATOR
            android.os.Parcelable r2 = com.google.android.gms.internal.zzef.zza((android.os.Parcel) r3, r2)
            android.content.Intent r2 = (android.content.Intent) r2
            r1.startActivity(r2)
            goto L_0x0067
        L_0x0048:
            boolean r2 = com.google.android.gms.internal.zzef.zza(r3)
            r1.setUserVisibleHint(r2)
            goto L_0x0067
        L_0x0050:
            boolean r2 = com.google.android.gms.internal.zzef.zza(r3)
            r1.setRetainInstance(r2)
            goto L_0x0067
        L_0x0058:
            boolean r2 = com.google.android.gms.internal.zzef.zza(r3)
            r1.setMenuVisibility(r2)
            goto L_0x0067
        L_0x0060:
            boolean r2 = com.google.android.gms.internal.zzef.zza(r3)
            r1.setHasOptionsMenu(r2)
        L_0x0067:
            r4.writeNoException()
            goto L_0x0101
        L_0x006c:
            android.os.IBinder r2 = r3.readStrongBinder()
            if (r2 != 0) goto L_0x0073
            goto L_0x0086
        L_0x0073:
            java.lang.String r3 = "com.google.android.gms.dynamic.IObjectWrapper"
            android.os.IInterface r3 = r2.queryLocalInterface(r3)
            boolean r5 = r3 instanceof com.google.android.gms.dynamic.IObjectWrapper
            if (r5 == 0) goto L_0x0081
            r5 = r3
            com.google.android.gms.dynamic.IObjectWrapper r5 = (com.google.android.gms.dynamic.IObjectWrapper) r5
            goto L_0x0086
        L_0x0081:
            com.google.android.gms.dynamic.zzm r5 = new com.google.android.gms.dynamic.zzm
            r5.<init>(r2)
        L_0x0086:
            r1.zzC(r5)
            goto L_0x0067
        L_0x008a:
            boolean r2 = r1.isVisible()
            goto L_0x00d0
        L_0x008f:
            boolean r2 = r1.isResumed()
            goto L_0x00d0
        L_0x0094:
            boolean r2 = r1.isRemoving()
            goto L_0x00d0
        L_0x0099:
            boolean r2 = r1.isInLayout()
            goto L_0x00d0
        L_0x009e:
            boolean r2 = r1.isHidden()
            goto L_0x00d0
        L_0x00a3:
            boolean r2 = r1.isDetached()
            goto L_0x00d0
        L_0x00a8:
            boolean r2 = r1.isAdded()
            goto L_0x00d0
        L_0x00ad:
            com.google.android.gms.dynamic.IObjectWrapper r2 = r1.getView()
            goto L_0x00fb
        L_0x00b2:
            boolean r2 = r1.getUserVisibleHint()
            goto L_0x00d0
        L_0x00b7:
            int r2 = r1.getTargetRequestCode()
            goto L_0x00e5
        L_0x00bc:
            com.google.android.gms.dynamic.zzk r2 = r1.zztA()
            goto L_0x00fb
        L_0x00c1:
            java.lang.String r2 = r1.getTag()
            r4.writeNoException()
            r4.writeString(r2)
            goto L_0x0101
        L_0x00cc:
            boolean r2 = r1.getRetainInstance()
        L_0x00d0:
            r4.writeNoException()
            com.google.android.gms.internal.zzef.zza((android.os.Parcel) r4, (boolean) r2)
            goto L_0x0101
        L_0x00d7:
            com.google.android.gms.dynamic.IObjectWrapper r2 = r1.zztz()
            goto L_0x00fb
        L_0x00dc:
            com.google.android.gms.dynamic.zzk r2 = r1.zzty()
            goto L_0x00fb
        L_0x00e1:
            int r2 = r1.getId()
        L_0x00e5:
            r4.writeNoException()
            r4.writeInt(r2)
            goto L_0x0101
        L_0x00ec:
            android.os.Bundle r2 = r1.getArguments()
            r4.writeNoException()
            com.google.android.gms.internal.zzef.zzb(r4, r2)
            goto L_0x0101
        L_0x00f7:
            com.google.android.gms.dynamic.IObjectWrapper r2 = r1.zztx()
        L_0x00fb:
            r4.writeNoException()
            com.google.android.gms.internal.zzef.zza((android.os.Parcel) r4, (android.os.IInterface) r2)
        L_0x0101:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamic.zzl.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
    }
}
