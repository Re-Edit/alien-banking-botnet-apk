package com.google.android.gms.iid;

import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.internal.ReflectedParcelable;

public class MessengerCompat implements ReflectedParcelable {
    public static final Parcelable.Creator<MessengerCompat> CREATOR = new zzd();
    private Messenger zzbhb;
    private zzb zzbhc;

    /* JADX WARNING: type inference failed for: r0v2, types: [android.os.IInterface] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MessengerCompat(android.os.IBinder r3) {
        /*
            r2 = this;
            r2.<init>()
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 21
            if (r0 < r1) goto L_0x0011
            android.os.Messenger r0 = new android.os.Messenger
            r0.<init>(r3)
            r2.zzbhb = r0
            return
        L_0x0011:
            if (r3 != 0) goto L_0x0015
            r3 = 0
            goto L_0x0029
        L_0x0015:
            java.lang.String r0 = "com.google.android.gms.iid.IMessengerCompat"
            android.os.IInterface r0 = r3.queryLocalInterface(r0)
            boolean r1 = r0 instanceof com.google.android.gms.iid.zzb
            if (r1 == 0) goto L_0x0023
            r3 = r0
            com.google.android.gms.iid.zzb r3 = (com.google.android.gms.iid.zzb) r3
            goto L_0x0029
        L_0x0023:
            com.google.android.gms.iid.zzc r0 = new com.google.android.gms.iid.zzc
            r0.<init>(r3)
            r3 = r0
        L_0x0029:
            r2.zzbhc = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.iid.MessengerCompat.<init>(android.os.IBinder):void");
    }

    private final IBinder getBinder() {
        Messenger messenger = this.zzbhb;
        return messenger != null ? messenger.getBinder() : this.zzbhc.asBinder();
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            return getBinder().equals(((MessengerCompat) obj).getBinder());
        } catch (ClassCastException unused) {
            return false;
        }
    }

    public int hashCode() {
        return getBinder().hashCode();
    }

    public final void send(Message message) throws RemoteException {
        Messenger messenger = this.zzbhb;
        if (messenger != null) {
            messenger.send(message);
        } else {
            this.zzbhc.send(message);
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        Messenger messenger = this.zzbhb;
        parcel.writeStrongBinder(messenger != null ? messenger.getBinder() : this.zzbhc.asBinder());
    }
}
