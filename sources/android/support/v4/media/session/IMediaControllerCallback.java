package android.support.v4.media.session;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import java.util.List;

public interface IMediaControllerCallback extends IInterface {
    void onEvent(String str, Bundle bundle) throws RemoteException;

    void onExtrasChanged(Bundle bundle) throws RemoteException;

    void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) throws RemoteException;

    void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) throws RemoteException;

    void onQueueChanged(List<MediaSessionCompat.QueueItem> list) throws RemoteException;

    void onQueueTitleChanged(CharSequence charSequence) throws RemoteException;

    void onSessionDestroyed() throws RemoteException;

    void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements IMediaControllerCallback {
        private static final String DESCRIPTOR = "android.support.v4.media.session.IMediaControllerCallback";
        static final int TRANSACTION_onEvent = 1;
        static final int TRANSACTION_onExtrasChanged = 7;
        static final int TRANSACTION_onMetadataChanged = 4;
        static final int TRANSACTION_onPlaybackStateChanged = 3;
        static final int TRANSACTION_onQueueChanged = 5;
        static final int TRANSACTION_onQueueTitleChanged = 6;
        static final int TRANSACTION_onSessionDestroyed = 2;
        static final int TRANSACTION_onVolumeInfoChanged = 8;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMediaControllerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMediaControllerCallback)) {
                return new Proxy(iBinder);
            }
            return (IMediaControllerCallback) queryLocalInterface;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: android.support.v4.media.session.PlaybackStateCompat} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: android.support.v4.media.MediaMetadataCompat} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v14, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v17, resolved type: android.support.v4.media.session.ParcelableVolumeInfo} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v11, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r0v20 */
        /* JADX WARNING: type inference failed for: r0v21 */
        /* JADX WARNING: type inference failed for: r0v22 */
        /* JADX WARNING: type inference failed for: r0v23 */
        /* JADX WARNING: type inference failed for: r0v24 */
        /* JADX WARNING: type inference failed for: r0v25 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r3, android.os.Parcel r4, android.os.Parcel r5, int r6) throws android.os.RemoteException {
            /*
                r2 = this;
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r1 = 1
                if (r3 == r0) goto L_0x00bb
                r0 = 0
                switch(r3) {
                    case 1: goto L_0x009f;
                    case 2: goto L_0x0096;
                    case 3: goto L_0x007e;
                    case 4: goto L_0x0066;
                    case 5: goto L_0x0057;
                    case 6: goto L_0x003f;
                    case 7: goto L_0x0027;
                    case 8: goto L_0x000f;
                    default: goto L_0x000a;
                }
            L_0x000a:
                boolean r3 = super.onTransact(r3, r4, r5, r6)
                return r3
            L_0x000f:
                java.lang.String r3 = "android.support.v4.media.session.IMediaControllerCallback"
                r4.enforceInterface(r3)
                int r3 = r4.readInt()
                if (r3 == 0) goto L_0x0023
                android.os.Parcelable$Creator<android.support.v4.media.session.ParcelableVolumeInfo> r3 = android.support.v4.media.session.ParcelableVolumeInfo.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r4)
                r0 = r3
                android.support.v4.media.session.ParcelableVolumeInfo r0 = (android.support.v4.media.session.ParcelableVolumeInfo) r0
            L_0x0023:
                r2.onVolumeInfoChanged(r0)
                return r1
            L_0x0027:
                java.lang.String r3 = "android.support.v4.media.session.IMediaControllerCallback"
                r4.enforceInterface(r3)
                int r3 = r4.readInt()
                if (r3 == 0) goto L_0x003b
                android.os.Parcelable$Creator r3 = android.os.Bundle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r4)
                r0 = r3
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x003b:
                r2.onExtrasChanged(r0)
                return r1
            L_0x003f:
                java.lang.String r3 = "android.support.v4.media.session.IMediaControllerCallback"
                r4.enforceInterface(r3)
                int r3 = r4.readInt()
                if (r3 == 0) goto L_0x0053
                android.os.Parcelable$Creator r3 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r3 = r3.createFromParcel(r4)
                r0 = r3
                java.lang.CharSequence r0 = (java.lang.CharSequence) r0
            L_0x0053:
                r2.onQueueTitleChanged(r0)
                return r1
            L_0x0057:
                java.lang.String r3 = "android.support.v4.media.session.IMediaControllerCallback"
                r4.enforceInterface(r3)
                android.os.Parcelable$Creator<android.support.v4.media.session.MediaSessionCompat$QueueItem> r3 = android.support.v4.media.session.MediaSessionCompat.QueueItem.CREATOR
                java.util.ArrayList r3 = r4.createTypedArrayList(r3)
                r2.onQueueChanged(r3)
                return r1
            L_0x0066:
                java.lang.String r3 = "android.support.v4.media.session.IMediaControllerCallback"
                r4.enforceInterface(r3)
                int r3 = r4.readInt()
                if (r3 == 0) goto L_0x007a
                android.os.Parcelable$Creator<android.support.v4.media.MediaMetadataCompat> r3 = android.support.v4.media.MediaMetadataCompat.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r4)
                r0 = r3
                android.support.v4.media.MediaMetadataCompat r0 = (android.support.v4.media.MediaMetadataCompat) r0
            L_0x007a:
                r2.onMetadataChanged(r0)
                return r1
            L_0x007e:
                java.lang.String r3 = "android.support.v4.media.session.IMediaControllerCallback"
                r4.enforceInterface(r3)
                int r3 = r4.readInt()
                if (r3 == 0) goto L_0x0092
                android.os.Parcelable$Creator<android.support.v4.media.session.PlaybackStateCompat> r3 = android.support.v4.media.session.PlaybackStateCompat.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r4)
                r0 = r3
                android.support.v4.media.session.PlaybackStateCompat r0 = (android.support.v4.media.session.PlaybackStateCompat) r0
            L_0x0092:
                r2.onPlaybackStateChanged(r0)
                return r1
            L_0x0096:
                java.lang.String r3 = "android.support.v4.media.session.IMediaControllerCallback"
                r4.enforceInterface(r3)
                r2.onSessionDestroyed()
                return r1
            L_0x009f:
                java.lang.String r3 = "android.support.v4.media.session.IMediaControllerCallback"
                r4.enforceInterface(r3)
                java.lang.String r3 = r4.readString()
                int r5 = r4.readInt()
                if (r5 == 0) goto L_0x00b7
                android.os.Parcelable$Creator r5 = android.os.Bundle.CREATOR
                java.lang.Object r4 = r5.createFromParcel(r4)
                r0 = r4
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x00b7:
                r2.onEvent(r3, r0)
                return r1
            L_0x00bb:
                java.lang.String r3 = "android.support.v4.media.session.IMediaControllerCallback"
                r5.writeString(r3)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.session.IMediaControllerCallback.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IMediaControllerCallback {
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public void onEvent(String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(1, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void onSessionDestroyed() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (playbackStateCompat != null) {
                        obtain.writeInt(1);
                        playbackStateCompat.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(3, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (mediaMetadataCompat != null) {
                        obtain.writeInt(1);
                        mediaMetadataCompat.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(4, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void onQueueChanged(List<MediaSessionCompat.QueueItem> list) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list);
                    this.mRemote.transact(5, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void onQueueTitleChanged(CharSequence charSequence) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(6, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void onExtrasChanged(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(7, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelableVolumeInfo != null) {
                        obtain.writeInt(1);
                        parcelableVolumeInfo.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(8, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
