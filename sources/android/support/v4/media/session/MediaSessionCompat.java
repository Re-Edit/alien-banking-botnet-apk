package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.annotation.RestrictTo;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.VolumeProviderCompat;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompatApi19;
import android.support.v4.media.session.MediaSessionCompatApi21;
import android.support.v4.media.session.MediaSessionCompatApi23;
import android.support.v4.media.session.MediaSessionCompatApi24;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MediaSessionCompat {
    static final String ACTION_ARGUMENT_EXTRAS = "android.support.v4.media.session.action.ARGUMENT_EXTRAS";
    static final String ACTION_ARGUMENT_MEDIA_ID = "android.support.v4.media.session.action.ARGUMENT_MEDIA_ID";
    static final String ACTION_ARGUMENT_QUERY = "android.support.v4.media.session.action.ARGUMENT_QUERY";
    static final String ACTION_ARGUMENT_URI = "android.support.v4.media.session.action.ARGUMENT_URI";
    static final String ACTION_PLAY_FROM_URI = "android.support.v4.media.session.action.PLAY_FROM_URI";
    static final String ACTION_PREPARE = "android.support.v4.media.session.action.PREPARE";
    static final String ACTION_PREPARE_FROM_MEDIA_ID = "android.support.v4.media.session.action.PREPARE_FROM_MEDIA_ID";
    static final String ACTION_PREPARE_FROM_SEARCH = "android.support.v4.media.session.action.PREPARE_FROM_SEARCH";
    static final String ACTION_PREPARE_FROM_URI = "android.support.v4.media.session.action.PREPARE_FROM_URI";
    static final String EXTRA_BINDER = "android.support.v4.media.session.EXTRA_BINDER";
    public static final int FLAG_HANDLES_MEDIA_BUTTONS = 1;
    public static final int FLAG_HANDLES_TRANSPORT_CONTROLS = 2;
    private static final int MAX_BITMAP_SIZE_IN_DP = 320;
    static final String TAG = "MediaSessionCompat";
    static int sMaxBitmapSize;
    private final ArrayList<OnActiveChangeListener> mActiveListeners;
    private final MediaControllerCompat mController;
    private final MediaSessionImpl mImpl;

    interface MediaSessionImpl {
        String getCallingPackage();

        Object getMediaSession();

        Object getRemoteControlClient();

        Token getSessionToken();

        boolean isActive();

        void release();

        void sendSessionEvent(String str, Bundle bundle);

        void setActive(boolean z);

        void setCallback(Callback callback, Handler handler);

        void setExtras(Bundle bundle);

        void setFlags(int i);

        void setMediaButtonReceiver(PendingIntent pendingIntent);

        void setMetadata(MediaMetadataCompat mediaMetadataCompat);

        void setPlaybackState(PlaybackStateCompat playbackStateCompat);

        void setPlaybackToLocal(int i);

        void setPlaybackToRemote(VolumeProviderCompat volumeProviderCompat);

        void setQueue(List<QueueItem> list);

        void setQueueTitle(CharSequence charSequence);

        void setRatingType(int i);

        void setSessionActivity(PendingIntent pendingIntent);
    }

    public interface OnActiveChangeListener {
        void onActiveChanged();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SessionFlags {
    }

    public MediaSessionCompat(Context context, String str) {
        this(context, str, (ComponentName) null, (PendingIntent) null);
    }

    public MediaSessionCompat(Context context, String str, ComponentName componentName, PendingIntent pendingIntent) {
        this.mActiveListeners = new ArrayList<>();
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        } else if (!TextUtils.isEmpty(str)) {
            if (componentName == null && (componentName = MediaButtonReceiver.getMediaButtonReceiverComponent(context)) == null) {
                Log.w(TAG, "Couldn't find a unique registered media button receiver in the given context.");
            }
            if (componentName != null && pendingIntent == null) {
                Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                intent.setComponent(componentName);
                pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            }
            if (Build.VERSION.SDK_INT >= 21) {
                this.mImpl = new MediaSessionImplApi21(context, str);
                this.mImpl.setMediaButtonReceiver(pendingIntent);
            } else {
                this.mImpl = new MediaSessionImplBase(context, str, componentName, pendingIntent);
            }
            this.mController = new MediaControllerCompat(context, this);
            if (sMaxBitmapSize == 0) {
                sMaxBitmapSize = (int) TypedValue.applyDimension(1, 320.0f, context.getResources().getDisplayMetrics());
            }
        } else {
            throw new IllegalArgumentException("tag must not be null or empty");
        }
    }

    private MediaSessionCompat(Context context, MediaSessionImpl mediaSessionImpl) {
        this.mActiveListeners = new ArrayList<>();
        this.mImpl = mediaSessionImpl;
        if (Build.VERSION.SDK_INT >= 21) {
            setCallback(new Callback() {
            });
        }
        this.mController = new MediaControllerCompat(context, this);
    }

    public void setCallback(Callback callback) {
        setCallback(callback, (Handler) null);
    }

    public void setCallback(Callback callback, Handler handler) {
        MediaSessionImpl mediaSessionImpl = this.mImpl;
        if (handler == null) {
            handler = new Handler();
        }
        mediaSessionImpl.setCallback(callback, handler);
    }

    public void setSessionActivity(PendingIntent pendingIntent) {
        this.mImpl.setSessionActivity(pendingIntent);
    }

    public void setMediaButtonReceiver(PendingIntent pendingIntent) {
        this.mImpl.setMediaButtonReceiver(pendingIntent);
    }

    public void setFlags(int i) {
        this.mImpl.setFlags(i);
    }

    public void setPlaybackToLocal(int i) {
        this.mImpl.setPlaybackToLocal(i);
    }

    public void setPlaybackToRemote(VolumeProviderCompat volumeProviderCompat) {
        if (volumeProviderCompat != null) {
            this.mImpl.setPlaybackToRemote(volumeProviderCompat);
            return;
        }
        throw new IllegalArgumentException("volumeProvider may not be null!");
    }

    public void setActive(boolean z) {
        this.mImpl.setActive(z);
        Iterator<OnActiveChangeListener> it = this.mActiveListeners.iterator();
        while (it.hasNext()) {
            it.next().onActiveChanged();
        }
    }

    public boolean isActive() {
        return this.mImpl.isActive();
    }

    public void sendSessionEvent(String str, Bundle bundle) {
        if (!TextUtils.isEmpty(str)) {
            this.mImpl.sendSessionEvent(str, bundle);
            return;
        }
        throw new IllegalArgumentException("event cannot be null or empty");
    }

    public void release() {
        this.mImpl.release();
    }

    public Token getSessionToken() {
        return this.mImpl.getSessionToken();
    }

    public MediaControllerCompat getController() {
        return this.mController;
    }

    public void setPlaybackState(PlaybackStateCompat playbackStateCompat) {
        this.mImpl.setPlaybackState(playbackStateCompat);
    }

    public void setMetadata(MediaMetadataCompat mediaMetadataCompat) {
        this.mImpl.setMetadata(mediaMetadataCompat);
    }

    public void setQueue(List<QueueItem> list) {
        this.mImpl.setQueue(list);
    }

    public void setQueueTitle(CharSequence charSequence) {
        this.mImpl.setQueueTitle(charSequence);
    }

    public void setRatingType(int i) {
        this.mImpl.setRatingType(i);
    }

    public void setExtras(Bundle bundle) {
        this.mImpl.setExtras(bundle);
    }

    public Object getMediaSession() {
        return this.mImpl.getMediaSession();
    }

    public Object getRemoteControlClient() {
        return this.mImpl.getRemoteControlClient();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public String getCallingPackage() {
        return this.mImpl.getCallingPackage();
    }

    public void addOnActiveChangeListener(OnActiveChangeListener onActiveChangeListener) {
        if (onActiveChangeListener != null) {
            this.mActiveListeners.add(onActiveChangeListener);
            return;
        }
        throw new IllegalArgumentException("Listener may not be null");
    }

    public void removeOnActiveChangeListener(OnActiveChangeListener onActiveChangeListener) {
        if (onActiveChangeListener != null) {
            this.mActiveListeners.remove(onActiveChangeListener);
            return;
        }
        throw new IllegalArgumentException("Listener may not be null");
    }

    @Deprecated
    public static MediaSessionCompat obtain(Context context, Object obj) {
        return fromMediaSession(context, obj);
    }

    public static MediaSessionCompat fromMediaSession(Context context, Object obj) {
        if (context == null || obj == null || Build.VERSION.SDK_INT < 21) {
            return null;
        }
        return new MediaSessionCompat(context, (MediaSessionImpl) new MediaSessionImplApi21(obj));
    }

    public static abstract class Callback {
        final Object mCallbackObj;
        WeakReference<MediaSessionImpl> mSessionImpl;

        public void onCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
        }

        public void onCustomAction(String str, Bundle bundle) {
        }

        public void onFastForward() {
        }

        public boolean onMediaButtonEvent(Intent intent) {
            return false;
        }

        public void onPause() {
        }

        public void onPlay() {
        }

        public void onPlayFromMediaId(String str, Bundle bundle) {
        }

        public void onPlayFromSearch(String str, Bundle bundle) {
        }

        public void onPlayFromUri(Uri uri, Bundle bundle) {
        }

        public void onPrepare() {
        }

        public void onPrepareFromMediaId(String str, Bundle bundle) {
        }

        public void onPrepareFromSearch(String str, Bundle bundle) {
        }

        public void onPrepareFromUri(Uri uri, Bundle bundle) {
        }

        public void onRewind() {
        }

        public void onSeekTo(long j) {
        }

        public void onSetRating(RatingCompat ratingCompat) {
        }

        public void onSkipToNext() {
        }

        public void onSkipToPrevious() {
        }

        public void onSkipToQueueItem(long j) {
        }

        public void onStop() {
        }

        public Callback() {
            if (Build.VERSION.SDK_INT >= 24) {
                this.mCallbackObj = MediaSessionCompatApi24.createCallback(new StubApi24());
            } else if (Build.VERSION.SDK_INT >= 23) {
                this.mCallbackObj = MediaSessionCompatApi23.createCallback(new StubApi23());
            } else if (Build.VERSION.SDK_INT >= 21) {
                this.mCallbackObj = MediaSessionCompatApi21.createCallback(new StubApi21());
            } else {
                this.mCallbackObj = null;
            }
        }

        private class StubApi21 implements MediaSessionCompatApi21.Callback {
            StubApi21() {
            }

            public void onCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
                if (str.equals("android.support.v4.media.session.command.GET_EXTRA_BINDER")) {
                    MediaSessionImplApi21 mediaSessionImplApi21 = (MediaSessionImplApi21) Callback.this.mSessionImpl.get();
                    if (mediaSessionImplApi21 != null) {
                        Bundle bundle2 = new Bundle();
                        BundleCompat.putBinder(bundle2, MediaSessionCompat.EXTRA_BINDER, mediaSessionImplApi21.getExtraSessionBinder());
                        resultReceiver.send(0, bundle2);
                        return;
                    }
                    return;
                }
                Callback.this.onCommand(str, bundle, resultReceiver);
            }

            public boolean onMediaButtonEvent(Intent intent) {
                return Callback.this.onMediaButtonEvent(intent);
            }

            public void onPlay() {
                Callback.this.onPlay();
            }

            public void onPlayFromMediaId(String str, Bundle bundle) {
                Callback.this.onPlayFromMediaId(str, bundle);
            }

            public void onPlayFromSearch(String str, Bundle bundle) {
                Callback.this.onPlayFromSearch(str, bundle);
            }

            public void onSkipToQueueItem(long j) {
                Callback.this.onSkipToQueueItem(j);
            }

            public void onPause() {
                Callback.this.onPause();
            }

            public void onSkipToNext() {
                Callback.this.onSkipToNext();
            }

            public void onSkipToPrevious() {
                Callback.this.onSkipToPrevious();
            }

            public void onFastForward() {
                Callback.this.onFastForward();
            }

            public void onRewind() {
                Callback.this.onRewind();
            }

            public void onStop() {
                Callback.this.onStop();
            }

            public void onSeekTo(long j) {
                Callback.this.onSeekTo(j);
            }

            public void onSetRating(Object obj) {
                Callback.this.onSetRating(RatingCompat.fromRating(obj));
            }

            public void onCustomAction(String str, Bundle bundle) {
                if (str.equals(MediaSessionCompat.ACTION_PLAY_FROM_URI)) {
                    Callback.this.onPlayFromUri((Uri) bundle.getParcelable(MediaSessionCompat.ACTION_ARGUMENT_URI), (Bundle) bundle.getParcelable(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS));
                } else if (str.equals(MediaSessionCompat.ACTION_PREPARE)) {
                    Callback.this.onPrepare();
                } else if (str.equals(MediaSessionCompat.ACTION_PREPARE_FROM_MEDIA_ID)) {
                    Callback.this.onPrepareFromMediaId(bundle.getString(MediaSessionCompat.ACTION_ARGUMENT_MEDIA_ID), bundle.getBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS));
                } else if (str.equals(MediaSessionCompat.ACTION_PREPARE_FROM_SEARCH)) {
                    Callback.this.onPrepareFromSearch(bundle.getString(MediaSessionCompat.ACTION_ARGUMENT_QUERY), bundle.getBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS));
                } else if (str.equals(MediaSessionCompat.ACTION_PREPARE_FROM_URI)) {
                    Bundle bundle2 = bundle.getBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS);
                    Callback.this.onPrepareFromUri((Uri) bundle.getParcelable(MediaSessionCompat.ACTION_ARGUMENT_URI), bundle2);
                } else {
                    Callback.this.onCustomAction(str, bundle);
                }
            }
        }

        private class StubApi23 extends StubApi21 implements MediaSessionCompatApi23.Callback {
            StubApi23() {
                super();
            }

            public void onPlayFromUri(Uri uri, Bundle bundle) {
                Callback.this.onPlayFromUri(uri, bundle);
            }
        }

        private class StubApi24 extends StubApi23 implements MediaSessionCompatApi24.Callback {
            StubApi24() {
                super();
            }

            public void onPrepare() {
                Callback.this.onPrepare();
            }

            public void onPrepareFromMediaId(String str, Bundle bundle) {
                Callback.this.onPrepareFromMediaId(str, bundle);
            }

            public void onPrepareFromSearch(String str, Bundle bundle) {
                Callback.this.onPrepareFromSearch(str, bundle);
            }

            public void onPrepareFromUri(Uri uri, Bundle bundle) {
                Callback.this.onPrepareFromUri(uri, bundle);
            }
        }
    }

    public static final class Token implements Parcelable {
        public static final Parcelable.Creator<Token> CREATOR = new Parcelable.Creator<Token>() {
            public Token createFromParcel(Parcel parcel) {
                Object obj;
                if (Build.VERSION.SDK_INT >= 21) {
                    obj = parcel.readParcelable((ClassLoader) null);
                } else {
                    obj = parcel.readStrongBinder();
                }
                return new Token(obj);
            }

            public Token[] newArray(int i) {
                return new Token[i];
            }
        };
        private final Object mInner;

        public int describeContents() {
            return 0;
        }

        Token(Object obj) {
            this.mInner = obj;
        }

        public static Token fromToken(Object obj) {
            if (obj == null || Build.VERSION.SDK_INT < 21) {
                return null;
            }
            return new Token(MediaSessionCompatApi21.verifyToken(obj));
        }

        public void writeToParcel(Parcel parcel, int i) {
            if (Build.VERSION.SDK_INT >= 21) {
                parcel.writeParcelable((Parcelable) this.mInner, i);
            } else {
                parcel.writeStrongBinder((IBinder) this.mInner);
            }
        }

        public int hashCode() {
            Object obj = this.mInner;
            if (obj == null) {
                return 0;
            }
            return obj.hashCode();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Token)) {
                return false;
            }
            Token token = (Token) obj;
            Object obj2 = this.mInner;
            if (obj2 != null) {
                Object obj3 = token.mInner;
                if (obj3 == null) {
                    return false;
                }
                return obj2.equals(obj3);
            } else if (token.mInner == null) {
                return true;
            } else {
                return false;
            }
        }

        public Object getToken() {
            return this.mInner;
        }
    }

    public static final class QueueItem implements Parcelable {
        public static final Parcelable.Creator<QueueItem> CREATOR = new Parcelable.Creator<QueueItem>() {
            public QueueItem createFromParcel(Parcel parcel) {
                return new QueueItem(parcel);
            }

            public QueueItem[] newArray(int i) {
                return new QueueItem[i];
            }
        };
        public static final int UNKNOWN_ID = -1;
        private final MediaDescriptionCompat mDescription;
        private final long mId;
        private Object mItem;

        public int describeContents() {
            return 0;
        }

        public QueueItem(MediaDescriptionCompat mediaDescriptionCompat, long j) {
            this((Object) null, mediaDescriptionCompat, j);
        }

        private QueueItem(Object obj, MediaDescriptionCompat mediaDescriptionCompat, long j) {
            if (mediaDescriptionCompat == null) {
                throw new IllegalArgumentException("Description cannot be null.");
            } else if (j != -1) {
                this.mDescription = mediaDescriptionCompat;
                this.mId = j;
                this.mItem = obj;
            } else {
                throw new IllegalArgumentException("Id cannot be QueueItem.UNKNOWN_ID");
            }
        }

        QueueItem(Parcel parcel) {
            this.mDescription = MediaDescriptionCompat.CREATOR.createFromParcel(parcel);
            this.mId = parcel.readLong();
        }

        public MediaDescriptionCompat getDescription() {
            return this.mDescription;
        }

        public long getQueueId() {
            return this.mId;
        }

        public void writeToParcel(Parcel parcel, int i) {
            this.mDescription.writeToParcel(parcel, i);
            parcel.writeLong(this.mId);
        }

        public Object getQueueItem() {
            if (this.mItem != null || Build.VERSION.SDK_INT < 21) {
                return this.mItem;
            }
            this.mItem = MediaSessionCompatApi21.QueueItem.createItem(this.mDescription.getMediaDescription(), this.mId);
            return this.mItem;
        }

        @Deprecated
        public static QueueItem obtain(Object obj) {
            return fromQueueItem(obj);
        }

        public static QueueItem fromQueueItem(Object obj) {
            if (obj == null || Build.VERSION.SDK_INT < 21) {
                return null;
            }
            return new QueueItem(obj, MediaDescriptionCompat.fromMediaDescription(MediaSessionCompatApi21.QueueItem.getDescription(obj)), MediaSessionCompatApi21.QueueItem.getQueueId(obj));
        }

        public static List<QueueItem> fromQueueItemList(List<?> list) {
            if (list == null || Build.VERSION.SDK_INT < 21) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (Object fromQueueItem : list) {
                arrayList.add(fromQueueItem(fromQueueItem));
            }
            return arrayList;
        }

        public String toString() {
            return "MediaSession.QueueItem {Description=" + this.mDescription + ", Id=" + this.mId + " }";
        }
    }

    static final class ResultReceiverWrapper implements Parcelable {
        public static final Parcelable.Creator<ResultReceiverWrapper> CREATOR = new Parcelable.Creator<ResultReceiverWrapper>() {
            public ResultReceiverWrapper createFromParcel(Parcel parcel) {
                return new ResultReceiverWrapper(parcel);
            }

            public ResultReceiverWrapper[] newArray(int i) {
                return new ResultReceiverWrapper[i];
            }
        };
        /* access modifiers changed from: private */
        public ResultReceiver mResultReceiver;

        public int describeContents() {
            return 0;
        }

        public ResultReceiverWrapper(ResultReceiver resultReceiver) {
            this.mResultReceiver = resultReceiver;
        }

        ResultReceiverWrapper(Parcel parcel) {
            this.mResultReceiver = (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(parcel);
        }

        public void writeToParcel(Parcel parcel, int i) {
            this.mResultReceiver.writeToParcel(parcel, i);
        }
    }

    static class MediaSessionImplBase implements MediaSessionImpl {
        final AudioManager mAudioManager;
        volatile Callback mCallback;
        private final Context mContext;
        final RemoteCallbackList<IMediaControllerCallback> mControllerCallbacks = new RemoteCallbackList<>();
        boolean mDestroyed = false;
        Bundle mExtras;
        int mFlags;
        private MessageHandler mHandler;
        private boolean mIsActive = false;
        private boolean mIsMbrRegistered = false;
        private boolean mIsRccRegistered = false;
        int mLocalStream;
        final Object mLock = new Object();
        private final ComponentName mMediaButtonReceiverComponentName;
        private final PendingIntent mMediaButtonReceiverIntent;
        MediaMetadataCompat mMetadata;
        final String mPackageName;
        List<QueueItem> mQueue;
        CharSequence mQueueTitle;
        int mRatingType;
        private final Object mRccObj;
        PendingIntent mSessionActivity;
        PlaybackStateCompat mState;
        private final MediaSessionStub mStub;
        final String mTag;
        private final Token mToken;
        private VolumeProviderCompat.Callback mVolumeCallback = new VolumeProviderCompat.Callback() {
            public void onVolumeChanged(VolumeProviderCompat volumeProviderCompat) {
                if (MediaSessionImplBase.this.mVolumeProvider == volumeProviderCompat) {
                    MediaSessionImplBase.this.sendVolumeInfoChanged(new ParcelableVolumeInfo(MediaSessionImplBase.this.mVolumeType, MediaSessionImplBase.this.mLocalStream, volumeProviderCompat.getVolumeControl(), volumeProviderCompat.getMaxVolume(), volumeProviderCompat.getCurrentVolume()));
                }
            }
        };
        VolumeProviderCompat mVolumeProvider;
        int mVolumeType;

        public String getCallingPackage() {
            return null;
        }

        public Object getMediaSession() {
            return null;
        }

        public void setMediaButtonReceiver(PendingIntent pendingIntent) {
        }

        public MediaSessionImplBase(Context context, String str, ComponentName componentName, PendingIntent pendingIntent) {
            if (componentName != null) {
                this.mContext = context;
                this.mPackageName = context.getPackageName();
                this.mAudioManager = (AudioManager) context.getSystemService("audio");
                this.mTag = str;
                this.mMediaButtonReceiverComponentName = componentName;
                this.mMediaButtonReceiverIntent = pendingIntent;
                this.mStub = new MediaSessionStub();
                this.mToken = new Token(this.mStub);
                this.mRatingType = 0;
                this.mVolumeType = 1;
                this.mLocalStream = 3;
                if (Build.VERSION.SDK_INT >= 14) {
                    this.mRccObj = MediaSessionCompatApi14.createRemoteControlClient(pendingIntent);
                } else {
                    this.mRccObj = null;
                }
            } else {
                throw new IllegalArgumentException("MediaButtonReceiver component may not be null.");
            }
        }

        public void setCallback(Callback callback, Handler handler) {
            this.mCallback = callback;
            if (callback == null) {
                if (Build.VERSION.SDK_INT >= 18) {
                    MediaSessionCompatApi18.setOnPlaybackPositionUpdateListener(this.mRccObj, (Object) null);
                }
                if (Build.VERSION.SDK_INT >= 19) {
                    MediaSessionCompatApi19.setOnMetadataUpdateListener(this.mRccObj, (Object) null);
                    return;
                }
                return;
            }
            if (handler == null) {
                handler = new Handler();
            }
            synchronized (this.mLock) {
                this.mHandler = new MessageHandler(handler.getLooper());
            }
            AnonymousClass2 r4 = new MediaSessionCompatApi19.Callback() {
                public void onSetRating(Object obj) {
                    MediaSessionImplBase.this.postToHandler(19, RatingCompat.fromRating(obj));
                }

                public void onSeekTo(long j) {
                    MediaSessionImplBase.this.postToHandler(18, Long.valueOf(j));
                }
            };
            if (Build.VERSION.SDK_INT >= 18) {
                MediaSessionCompatApi18.setOnPlaybackPositionUpdateListener(this.mRccObj, MediaSessionCompatApi18.createPlaybackPositionUpdateListener(r4));
            }
            if (Build.VERSION.SDK_INT >= 19) {
                MediaSessionCompatApi19.setOnMetadataUpdateListener(this.mRccObj, MediaSessionCompatApi19.createMetadataUpdateListener(r4));
            }
        }

        /* access modifiers changed from: package-private */
        public void postToHandler(int i) {
            postToHandler(i, (Object) null);
        }

        /* access modifiers changed from: package-private */
        public void postToHandler(int i, Object obj) {
            postToHandler(i, obj, (Bundle) null);
        }

        /* access modifiers changed from: package-private */
        public void postToHandler(int i, Object obj, Bundle bundle) {
            synchronized (this.mLock) {
                if (this.mHandler != null) {
                    this.mHandler.post(i, obj, bundle);
                }
            }
        }

        public void setFlags(int i) {
            synchronized (this.mLock) {
                this.mFlags = i;
            }
            update();
        }

        public void setPlaybackToLocal(int i) {
            VolumeProviderCompat volumeProviderCompat = this.mVolumeProvider;
            if (volumeProviderCompat != null) {
                volumeProviderCompat.setCallback((VolumeProviderCompat.Callback) null);
            }
            this.mVolumeType = 1;
            int i2 = this.mVolumeType;
            int i3 = this.mLocalStream;
            sendVolumeInfoChanged(new ParcelableVolumeInfo(i2, i3, 2, this.mAudioManager.getStreamMaxVolume(i3), this.mAudioManager.getStreamVolume(this.mLocalStream)));
        }

        public void setPlaybackToRemote(VolumeProviderCompat volumeProviderCompat) {
            if (volumeProviderCompat != null) {
                VolumeProviderCompat volumeProviderCompat2 = this.mVolumeProvider;
                if (volumeProviderCompat2 != null) {
                    volumeProviderCompat2.setCallback((VolumeProviderCompat.Callback) null);
                }
                this.mVolumeType = 2;
                this.mVolumeProvider = volumeProviderCompat;
                sendVolumeInfoChanged(new ParcelableVolumeInfo(this.mVolumeType, this.mLocalStream, this.mVolumeProvider.getVolumeControl(), this.mVolumeProvider.getMaxVolume(), this.mVolumeProvider.getCurrentVolume()));
                volumeProviderCompat.setCallback(this.mVolumeCallback);
                return;
            }
            throw new IllegalArgumentException("volumeProvider may not be null");
        }

        public void setActive(boolean z) {
            if (z != this.mIsActive) {
                this.mIsActive = z;
                if (update()) {
                    setMetadata(this.mMetadata);
                    setPlaybackState(this.mState);
                }
            }
        }

        public boolean isActive() {
            return this.mIsActive;
        }

        public void sendSessionEvent(String str, Bundle bundle) {
            sendEvent(str, bundle);
        }

        public void release() {
            this.mIsActive = false;
            this.mDestroyed = true;
            update();
            sendSessionDestroyed();
        }

        public Token getSessionToken() {
            return this.mToken;
        }

        public void setPlaybackState(PlaybackStateCompat playbackStateCompat) {
            synchronized (this.mLock) {
                this.mState = playbackStateCompat;
            }
            sendState(playbackStateCompat);
            if (this.mIsActive) {
                if (playbackStateCompat != null) {
                    if (Build.VERSION.SDK_INT >= 18) {
                        MediaSessionCompatApi18.setState(this.mRccObj, playbackStateCompat.getState(), playbackStateCompat.getPosition(), playbackStateCompat.getPlaybackSpeed(), playbackStateCompat.getLastPositionUpdateTime());
                    } else if (Build.VERSION.SDK_INT >= 14) {
                        MediaSessionCompatApi14.setState(this.mRccObj, playbackStateCompat.getState());
                    }
                    if (Build.VERSION.SDK_INT >= 19) {
                        MediaSessionCompatApi19.setTransportControlFlags(this.mRccObj, playbackStateCompat.getActions());
                    } else if (Build.VERSION.SDK_INT >= 18) {
                        MediaSessionCompatApi18.setTransportControlFlags(this.mRccObj, playbackStateCompat.getActions());
                    } else if (Build.VERSION.SDK_INT >= 14) {
                        MediaSessionCompatApi14.setTransportControlFlags(this.mRccObj, playbackStateCompat.getActions());
                    }
                } else if (Build.VERSION.SDK_INT >= 14) {
                    MediaSessionCompatApi14.setState(this.mRccObj, 0);
                    MediaSessionCompatApi14.setTransportControlFlags(this.mRccObj, 0);
                }
            }
        }

        public void setMetadata(MediaMetadataCompat mediaMetadataCompat) {
            long j;
            if (mediaMetadataCompat != null) {
                mediaMetadataCompat = new MediaMetadataCompat.Builder(mediaMetadataCompat, MediaSessionCompat.sMaxBitmapSize).build();
            }
            synchronized (this.mLock) {
                this.mMetadata = mediaMetadataCompat;
            }
            sendMetadata(mediaMetadataCompat);
            if (this.mIsActive) {
                Bundle bundle = null;
                if (Build.VERSION.SDK_INT >= 19) {
                    Object obj = this.mRccObj;
                    if (mediaMetadataCompat != null) {
                        bundle = mediaMetadataCompat.getBundle();
                    }
                    PlaybackStateCompat playbackStateCompat = this.mState;
                    if (playbackStateCompat == null) {
                        j = 0;
                    } else {
                        j = playbackStateCompat.getActions();
                    }
                    MediaSessionCompatApi19.setMetadata(obj, bundle, j);
                } else if (Build.VERSION.SDK_INT >= 14) {
                    Object obj2 = this.mRccObj;
                    if (mediaMetadataCompat != null) {
                        bundle = mediaMetadataCompat.getBundle();
                    }
                    MediaSessionCompatApi14.setMetadata(obj2, bundle);
                }
            }
        }

        public void setSessionActivity(PendingIntent pendingIntent) {
            synchronized (this.mLock) {
                this.mSessionActivity = pendingIntent;
            }
        }

        public void setQueue(List<QueueItem> list) {
            this.mQueue = list;
            sendQueue(list);
        }

        public void setQueueTitle(CharSequence charSequence) {
            this.mQueueTitle = charSequence;
            sendQueueTitle(charSequence);
        }

        public Object getRemoteControlClient() {
            return this.mRccObj;
        }

        public void setRatingType(int i) {
            this.mRatingType = i;
        }

        public void setExtras(Bundle bundle) {
            this.mExtras = bundle;
            sendExtras(bundle);
        }

        private boolean update() {
            if (this.mIsActive) {
                if (!this.mIsMbrRegistered && (this.mFlags & 1) != 0) {
                    if (Build.VERSION.SDK_INT >= 18) {
                        MediaSessionCompatApi18.registerMediaButtonEventReceiver(this.mContext, this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName);
                    } else {
                        ((AudioManager) this.mContext.getSystemService("audio")).registerMediaButtonEventReceiver(this.mMediaButtonReceiverComponentName);
                    }
                    this.mIsMbrRegistered = true;
                } else if (this.mIsMbrRegistered && (this.mFlags & 1) == 0) {
                    if (Build.VERSION.SDK_INT >= 18) {
                        MediaSessionCompatApi18.unregisterMediaButtonEventReceiver(this.mContext, this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName);
                    } else {
                        ((AudioManager) this.mContext.getSystemService("audio")).unregisterMediaButtonEventReceiver(this.mMediaButtonReceiverComponentName);
                    }
                    this.mIsMbrRegistered = false;
                }
                if (Build.VERSION.SDK_INT >= 14) {
                    if (!this.mIsRccRegistered && (this.mFlags & 2) != 0) {
                        MediaSessionCompatApi14.registerRemoteControlClient(this.mContext, this.mRccObj);
                        this.mIsRccRegistered = true;
                        return true;
                    } else if (this.mIsRccRegistered && (this.mFlags & 2) == 0) {
                        MediaSessionCompatApi14.setState(this.mRccObj, 0);
                        MediaSessionCompatApi14.unregisterRemoteControlClient(this.mContext, this.mRccObj);
                        this.mIsRccRegistered = false;
                    }
                }
            } else {
                if (this.mIsMbrRegistered) {
                    if (Build.VERSION.SDK_INT >= 18) {
                        MediaSessionCompatApi18.unregisterMediaButtonEventReceiver(this.mContext, this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName);
                    } else {
                        ((AudioManager) this.mContext.getSystemService("audio")).unregisterMediaButtonEventReceiver(this.mMediaButtonReceiverComponentName);
                    }
                    this.mIsMbrRegistered = false;
                }
                if (this.mIsRccRegistered) {
                    MediaSessionCompatApi14.setState(this.mRccObj, 0);
                    MediaSessionCompatApi14.unregisterRemoteControlClient(this.mContext, this.mRccObj);
                    this.mIsRccRegistered = false;
                }
            }
            return false;
        }

        /* access modifiers changed from: package-private */
        public void adjustVolume(int i, int i2) {
            if (this.mVolumeType == 2) {
                VolumeProviderCompat volumeProviderCompat = this.mVolumeProvider;
                if (volumeProviderCompat != null) {
                    volumeProviderCompat.onAdjustVolume(i);
                    return;
                }
                return;
            }
            this.mAudioManager.adjustStreamVolume(this.mLocalStream, i, i2);
        }

        /* access modifiers changed from: package-private */
        public void setVolumeTo(int i, int i2) {
            if (this.mVolumeType == 2) {
                VolumeProviderCompat volumeProviderCompat = this.mVolumeProvider;
                if (volumeProviderCompat != null) {
                    volumeProviderCompat.onSetVolumeTo(i);
                    return;
                }
                return;
            }
            this.mAudioManager.setStreamVolume(this.mLocalStream, i, i2);
        }

        /* access modifiers changed from: package-private */
        public PlaybackStateCompat getStateWithUpdatedPosition() {
            PlaybackStateCompat playbackStateCompat;
            long j;
            synchronized (this.mLock) {
                playbackStateCompat = this.mState;
                j = (this.mMetadata == null || !this.mMetadata.containsKey(MediaMetadataCompat.METADATA_KEY_DURATION)) ? -1 : this.mMetadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION);
            }
            PlaybackStateCompat playbackStateCompat2 = null;
            if (playbackStateCompat != null && (playbackStateCompat.getState() == 3 || playbackStateCompat.getState() == 4 || playbackStateCompat.getState() == 5)) {
                long lastPositionUpdateTime = playbackStateCompat.getLastPositionUpdateTime();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                if (lastPositionUpdateTime > 0) {
                    long playbackSpeed = ((long) (playbackStateCompat.getPlaybackSpeed() * ((float) (elapsedRealtime - lastPositionUpdateTime)))) + playbackStateCompat.getPosition();
                    long j2 = (j < 0 || playbackSpeed <= j) ? playbackSpeed < 0 ? 0 : playbackSpeed : j;
                    PlaybackStateCompat.Builder builder = new PlaybackStateCompat.Builder(playbackStateCompat);
                    builder.setState(playbackStateCompat.getState(), j2, playbackStateCompat.getPlaybackSpeed(), elapsedRealtime);
                    playbackStateCompat2 = builder.build();
                }
            }
            return playbackStateCompat2 == null ? playbackStateCompat : playbackStateCompat2;
        }

        /* access modifiers changed from: package-private */
        public void sendVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) {
            for (int beginBroadcast = this.mControllerCallbacks.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.mControllerCallbacks.getBroadcastItem(beginBroadcast).onVolumeInfoChanged(parcelableVolumeInfo);
                } catch (RemoteException unused) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendSessionDestroyed() {
            for (int beginBroadcast = this.mControllerCallbacks.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.mControllerCallbacks.getBroadcastItem(beginBroadcast).onSessionDestroyed();
                } catch (RemoteException unused) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
            this.mControllerCallbacks.kill();
        }

        private void sendEvent(String str, Bundle bundle) {
            for (int beginBroadcast = this.mControllerCallbacks.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.mControllerCallbacks.getBroadcastItem(beginBroadcast).onEvent(str, bundle);
                } catch (RemoteException unused) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendState(PlaybackStateCompat playbackStateCompat) {
            for (int beginBroadcast = this.mControllerCallbacks.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.mControllerCallbacks.getBroadcastItem(beginBroadcast).onPlaybackStateChanged(playbackStateCompat);
                } catch (RemoteException unused) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendMetadata(MediaMetadataCompat mediaMetadataCompat) {
            for (int beginBroadcast = this.mControllerCallbacks.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.mControllerCallbacks.getBroadcastItem(beginBroadcast).onMetadataChanged(mediaMetadataCompat);
                } catch (RemoteException unused) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendQueue(List<QueueItem> list) {
            for (int beginBroadcast = this.mControllerCallbacks.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.mControllerCallbacks.getBroadcastItem(beginBroadcast).onQueueChanged(list);
                } catch (RemoteException unused) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendQueueTitle(CharSequence charSequence) {
            for (int beginBroadcast = this.mControllerCallbacks.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.mControllerCallbacks.getBroadcastItem(beginBroadcast).onQueueTitleChanged(charSequence);
                } catch (RemoteException unused) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendExtras(Bundle bundle) {
            for (int beginBroadcast = this.mControllerCallbacks.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.mControllerCallbacks.getBroadcastItem(beginBroadcast).onExtrasChanged(bundle);
                } catch (RemoteException unused) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        class MediaSessionStub extends IMediaSession.Stub {
            MediaSessionStub() {
            }

            public void sendCommand(String str, Bundle bundle, ResultReceiverWrapper resultReceiverWrapper) {
                MediaSessionImplBase.this.postToHandler(1, new Command(str, bundle, resultReceiverWrapper.mResultReceiver));
            }

            public boolean sendMediaButton(KeyEvent keyEvent) {
                boolean z = true;
                if ((MediaSessionImplBase.this.mFlags & 1) == 0) {
                    z = false;
                }
                if (z) {
                    MediaSessionImplBase.this.postToHandler(21, keyEvent);
                }
                return z;
            }

            public void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                if (MediaSessionImplBase.this.mDestroyed) {
                    try {
                        iMediaControllerCallback.onSessionDestroyed();
                    } catch (Exception unused) {
                    }
                } else {
                    MediaSessionImplBase.this.mControllerCallbacks.register(iMediaControllerCallback);
                }
            }

            public void unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                MediaSessionImplBase.this.mControllerCallbacks.unregister(iMediaControllerCallback);
            }

            public String getPackageName() {
                return MediaSessionImplBase.this.mPackageName;
            }

            public String getTag() {
                return MediaSessionImplBase.this.mTag;
            }

            public PendingIntent getLaunchPendingIntent() {
                PendingIntent pendingIntent;
                synchronized (MediaSessionImplBase.this.mLock) {
                    pendingIntent = MediaSessionImplBase.this.mSessionActivity;
                }
                return pendingIntent;
            }

            public long getFlags() {
                long j;
                synchronized (MediaSessionImplBase.this.mLock) {
                    j = (long) MediaSessionImplBase.this.mFlags;
                }
                return j;
            }

            public ParcelableVolumeInfo getVolumeAttributes() {
                int i;
                int i2;
                int i3;
                int i4;
                int i5;
                synchronized (MediaSessionImplBase.this.mLock) {
                    i = MediaSessionImplBase.this.mVolumeType;
                    i2 = MediaSessionImplBase.this.mLocalStream;
                    VolumeProviderCompat volumeProviderCompat = MediaSessionImplBase.this.mVolumeProvider;
                    if (i == 2) {
                        int volumeControl = volumeProviderCompat.getVolumeControl();
                        int maxVolume = volumeProviderCompat.getMaxVolume();
                        i3 = volumeProviderCompat.getCurrentVolume();
                        i4 = maxVolume;
                        i5 = volumeControl;
                    } else {
                        i4 = MediaSessionImplBase.this.mAudioManager.getStreamMaxVolume(i2);
                        i3 = MediaSessionImplBase.this.mAudioManager.getStreamVolume(i2);
                        i5 = 2;
                    }
                }
                return new ParcelableVolumeInfo(i, i2, i5, i4, i3);
            }

            public void adjustVolume(int i, int i2, String str) {
                MediaSessionImplBase.this.adjustVolume(i, i2);
            }

            public void setVolumeTo(int i, int i2, String str) {
                MediaSessionImplBase.this.setVolumeTo(i, i2);
            }

            public void prepare() throws RemoteException {
                MediaSessionImplBase.this.postToHandler(3);
            }

            public void prepareFromMediaId(String str, Bundle bundle) throws RemoteException {
                MediaSessionImplBase.this.postToHandler(4, str, bundle);
            }

            public void prepareFromSearch(String str, Bundle bundle) throws RemoteException {
                MediaSessionImplBase.this.postToHandler(5, str, bundle);
            }

            public void prepareFromUri(Uri uri, Bundle bundle) throws RemoteException {
                MediaSessionImplBase.this.postToHandler(6, uri, bundle);
            }

            public void play() throws RemoteException {
                MediaSessionImplBase.this.postToHandler(7);
            }

            public void playFromMediaId(String str, Bundle bundle) throws RemoteException {
                MediaSessionImplBase.this.postToHandler(8, str, bundle);
            }

            public void playFromSearch(String str, Bundle bundle) throws RemoteException {
                MediaSessionImplBase.this.postToHandler(9, str, bundle);
            }

            public void playFromUri(Uri uri, Bundle bundle) throws RemoteException {
                MediaSessionImplBase.this.postToHandler(10, uri, bundle);
            }

            public void skipToQueueItem(long j) {
                MediaSessionImplBase.this.postToHandler(11, Long.valueOf(j));
            }

            public void pause() throws RemoteException {
                MediaSessionImplBase.this.postToHandler(12);
            }

            public void stop() throws RemoteException {
                MediaSessionImplBase.this.postToHandler(13);
            }

            public void next() throws RemoteException {
                MediaSessionImplBase.this.postToHandler(14);
            }

            public void previous() throws RemoteException {
                MediaSessionImplBase.this.postToHandler(15);
            }

            public void fastForward() throws RemoteException {
                MediaSessionImplBase.this.postToHandler(16);
            }

            public void rewind() throws RemoteException {
                MediaSessionImplBase.this.postToHandler(17);
            }

            public void seekTo(long j) throws RemoteException {
                MediaSessionImplBase.this.postToHandler(18, Long.valueOf(j));
            }

            public void rate(RatingCompat ratingCompat) throws RemoteException {
                MediaSessionImplBase.this.postToHandler(19, ratingCompat);
            }

            public void sendCustomAction(String str, Bundle bundle) throws RemoteException {
                MediaSessionImplBase.this.postToHandler(20, str, bundle);
            }

            public MediaMetadataCompat getMetadata() {
                return MediaSessionImplBase.this.mMetadata;
            }

            public PlaybackStateCompat getPlaybackState() {
                return MediaSessionImplBase.this.getStateWithUpdatedPosition();
            }

            public List<QueueItem> getQueue() {
                List<QueueItem> list;
                synchronized (MediaSessionImplBase.this.mLock) {
                    list = MediaSessionImplBase.this.mQueue;
                }
                return list;
            }

            public CharSequence getQueueTitle() {
                return MediaSessionImplBase.this.mQueueTitle;
            }

            public Bundle getExtras() {
                Bundle bundle;
                synchronized (MediaSessionImplBase.this.mLock) {
                    bundle = MediaSessionImplBase.this.mExtras;
                }
                return bundle;
            }

            public int getRatingType() {
                return MediaSessionImplBase.this.mRatingType;
            }

            public boolean isTransportControlEnabled() {
                return (MediaSessionImplBase.this.mFlags & 2) != 0;
            }
        }

        private static final class Command {
            public final String command;
            public final Bundle extras;
            public final ResultReceiver stub;

            public Command(String str, Bundle bundle, ResultReceiver resultReceiver) {
                this.command = str;
                this.extras = bundle;
                this.stub = resultReceiver;
            }
        }

        private class MessageHandler extends Handler {
            private static final int KEYCODE_MEDIA_PAUSE = 127;
            private static final int KEYCODE_MEDIA_PLAY = 126;
            private static final int MSG_ADJUST_VOLUME = 2;
            private static final int MSG_COMMAND = 1;
            private static final int MSG_CUSTOM_ACTION = 20;
            private static final int MSG_FAST_FORWARD = 16;
            private static final int MSG_MEDIA_BUTTON = 21;
            private static final int MSG_NEXT = 14;
            private static final int MSG_PAUSE = 12;
            private static final int MSG_PLAY = 7;
            private static final int MSG_PLAY_MEDIA_ID = 8;
            private static final int MSG_PLAY_SEARCH = 9;
            private static final int MSG_PLAY_URI = 10;
            private static final int MSG_PREPARE = 3;
            private static final int MSG_PREPARE_MEDIA_ID = 4;
            private static final int MSG_PREPARE_SEARCH = 5;
            private static final int MSG_PREPARE_URI = 6;
            private static final int MSG_PREVIOUS = 15;
            private static final int MSG_RATE = 19;
            private static final int MSG_REWIND = 17;
            private static final int MSG_SEEK_TO = 18;
            private static final int MSG_SET_VOLUME = 22;
            private static final int MSG_SKIP_TO_ITEM = 11;
            private static final int MSG_STOP = 13;

            public MessageHandler(Looper looper) {
                super(looper);
            }

            public void post(int i, Object obj, Bundle bundle) {
                Message obtainMessage = obtainMessage(i, obj);
                obtainMessage.setData(bundle);
                obtainMessage.sendToTarget();
            }

            public void post(int i, Object obj) {
                obtainMessage(i, obj).sendToTarget();
            }

            public void post(int i) {
                post(i, (Object) null);
            }

            public void post(int i, Object obj, int i2) {
                obtainMessage(i, i2, 0, obj).sendToTarget();
            }

            public void handleMessage(Message message) {
                Callback callback = MediaSessionImplBase.this.mCallback;
                if (callback != null) {
                    switch (message.what) {
                        case 1:
                            Command command = (Command) message.obj;
                            callback.onCommand(command.command, command.extras, command.stub);
                            return;
                        case 2:
                            MediaSessionImplBase.this.adjustVolume(((Integer) message.obj).intValue(), 0);
                            return;
                        case 3:
                            callback.onPrepare();
                            return;
                        case 4:
                            callback.onPrepareFromMediaId((String) message.obj, message.getData());
                            return;
                        case 5:
                            callback.onPrepareFromSearch((String) message.obj, message.getData());
                            return;
                        case 6:
                            callback.onPrepareFromUri((Uri) message.obj, message.getData());
                            return;
                        case 7:
                            callback.onPlay();
                            return;
                        case 8:
                            callback.onPlayFromMediaId((String) message.obj, message.getData());
                            return;
                        case 9:
                            callback.onPlayFromSearch((String) message.obj, message.getData());
                            return;
                        case 10:
                            callback.onPlayFromUri((Uri) message.obj, message.getData());
                            return;
                        case 11:
                            callback.onSkipToQueueItem(((Long) message.obj).longValue());
                            return;
                        case 12:
                            callback.onPause();
                            return;
                        case 13:
                            callback.onStop();
                            return;
                        case 14:
                            callback.onSkipToNext();
                            return;
                        case 15:
                            callback.onSkipToPrevious();
                            return;
                        case 16:
                            callback.onFastForward();
                            return;
                        case 17:
                            callback.onRewind();
                            return;
                        case 18:
                            callback.onSeekTo(((Long) message.obj).longValue());
                            return;
                        case 19:
                            callback.onSetRating((RatingCompat) message.obj);
                            return;
                        case 20:
                            callback.onCustomAction((String) message.obj, message.getData());
                            return;
                        case 21:
                            KeyEvent keyEvent = (KeyEvent) message.obj;
                            Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                            intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
                            if (!callback.onMediaButtonEvent(intent)) {
                                onMediaButtonEvent(keyEvent, callback);
                                return;
                            }
                            return;
                        case 22:
                            MediaSessionImplBase.this.setVolumeTo(((Integer) message.obj).intValue(), 0);
                            return;
                        default:
                            return;
                    }
                }
            }

            private void onMediaButtonEvent(KeyEvent keyEvent, Callback callback) {
                if (keyEvent != null && keyEvent.getAction() == 0) {
                    long actions = MediaSessionImplBase.this.mState == null ? 0 : MediaSessionImplBase.this.mState.getActions();
                    int keyCode = keyEvent.getKeyCode();
                    if (keyCode != 79) {
                        switch (keyCode) {
                            case 85:
                                break;
                            case 86:
                                if ((actions & 1) != 0) {
                                    callback.onStop();
                                    return;
                                }
                                return;
                            case 87:
                                if ((actions & 32) != 0) {
                                    callback.onSkipToNext();
                                    return;
                                }
                                return;
                            case 88:
                                if ((actions & 16) != 0) {
                                    callback.onSkipToPrevious();
                                    return;
                                }
                                return;
                            case 89:
                                if ((actions & 8) != 0) {
                                    callback.onRewind();
                                    return;
                                }
                                return;
                            case 90:
                                if ((actions & 64) != 0) {
                                    callback.onFastForward();
                                    return;
                                }
                                return;
                            default:
                                switch (keyCode) {
                                    case 126:
                                        if ((actions & 4) != 0) {
                                            callback.onPlay();
                                            return;
                                        }
                                        return;
                                    case 127:
                                        if ((actions & 2) != 0) {
                                            callback.onPause();
                                            return;
                                        }
                                        return;
                                    default:
                                        return;
                                }
                        }
                    }
                    boolean z = true;
                    boolean z2 = MediaSessionImplBase.this.mState != null && MediaSessionImplBase.this.mState.getState() == 3;
                    boolean z3 = (516 & actions) != 0;
                    if ((actions & 514) == 0) {
                        z = false;
                    }
                    if (z2 && z) {
                        callback.onPause();
                    } else if (!z2 && z3) {
                        callback.onPlay();
                    }
                }
            }
        }
    }

    static class MediaSessionImplApi21 implements MediaSessionImpl {
        /* access modifiers changed from: private */
        public boolean mDestroyed = false;
        /* access modifiers changed from: private */
        public final RemoteCallbackList<IMediaControllerCallback> mExtraControllerCallbacks = new RemoteCallbackList<>();
        private ExtraSession mExtraSessionBinder;
        /* access modifiers changed from: private */
        public PlaybackStateCompat mPlaybackState;
        int mRatingType;
        private final Object mSessionObj;
        private final Token mToken;

        public Object getRemoteControlClient() {
            return null;
        }

        public MediaSessionImplApi21(Context context, String str) {
            this.mSessionObj = MediaSessionCompatApi21.createSession(context, str);
            this.mToken = new Token(MediaSessionCompatApi21.getSessionToken(this.mSessionObj));
        }

        public MediaSessionImplApi21(Object obj) {
            this.mSessionObj = MediaSessionCompatApi21.verifySession(obj);
            this.mToken = new Token(MediaSessionCompatApi21.getSessionToken(this.mSessionObj));
        }

        public void setCallback(Callback callback, Handler handler) {
            MediaSessionCompatApi21.setCallback(this.mSessionObj, callback == null ? null : callback.mCallbackObj, handler);
            if (callback != null) {
                callback.mSessionImpl = new WeakReference<>(this);
            }
        }

        public void setFlags(int i) {
            MediaSessionCompatApi21.setFlags(this.mSessionObj, i);
        }

        public void setPlaybackToLocal(int i) {
            MediaSessionCompatApi21.setPlaybackToLocal(this.mSessionObj, i);
        }

        public void setPlaybackToRemote(VolumeProviderCompat volumeProviderCompat) {
            MediaSessionCompatApi21.setPlaybackToRemote(this.mSessionObj, volumeProviderCompat.getVolumeProvider());
        }

        public void setActive(boolean z) {
            MediaSessionCompatApi21.setActive(this.mSessionObj, z);
        }

        public boolean isActive() {
            return MediaSessionCompatApi21.isActive(this.mSessionObj);
        }

        public void sendSessionEvent(String str, Bundle bundle) {
            if (Build.VERSION.SDK_INT < 23) {
                for (int beginBroadcast = this.mExtraControllerCallbacks.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                    try {
                        this.mExtraControllerCallbacks.getBroadcastItem(beginBroadcast).onEvent(str, bundle);
                    } catch (RemoteException unused) {
                    }
                }
                this.mExtraControllerCallbacks.finishBroadcast();
            }
            MediaSessionCompatApi21.sendSessionEvent(this.mSessionObj, str, bundle);
        }

        public void release() {
            this.mDestroyed = true;
            MediaSessionCompatApi21.release(this.mSessionObj);
        }

        public Token getSessionToken() {
            return this.mToken;
        }

        public void setPlaybackState(PlaybackStateCompat playbackStateCompat) {
            Object obj;
            if (Build.VERSION.SDK_INT < 22) {
                this.mPlaybackState = playbackStateCompat;
                for (int beginBroadcast = this.mExtraControllerCallbacks.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                    try {
                        this.mExtraControllerCallbacks.getBroadcastItem(beginBroadcast).onPlaybackStateChanged(playbackStateCompat);
                    } catch (RemoteException unused) {
                    }
                }
                this.mExtraControllerCallbacks.finishBroadcast();
            }
            Object obj2 = this.mSessionObj;
            if (playbackStateCompat == null) {
                obj = null;
            } else {
                obj = playbackStateCompat.getPlaybackState();
            }
            MediaSessionCompatApi21.setPlaybackState(obj2, obj);
        }

        public void setMetadata(MediaMetadataCompat mediaMetadataCompat) {
            Object obj;
            Object obj2 = this.mSessionObj;
            if (mediaMetadataCompat == null) {
                obj = null;
            } else {
                obj = mediaMetadataCompat.getMediaMetadata();
            }
            MediaSessionCompatApi21.setMetadata(obj2, obj);
        }

        public void setSessionActivity(PendingIntent pendingIntent) {
            MediaSessionCompatApi21.setSessionActivity(this.mSessionObj, pendingIntent);
        }

        public void setMediaButtonReceiver(PendingIntent pendingIntent) {
            MediaSessionCompatApi21.setMediaButtonReceiver(this.mSessionObj, pendingIntent);
        }

        public void setQueue(List<QueueItem> list) {
            ArrayList arrayList;
            if (list != null) {
                arrayList = new ArrayList();
                for (QueueItem queueItem : list) {
                    arrayList.add(queueItem.getQueueItem());
                }
            } else {
                arrayList = null;
            }
            MediaSessionCompatApi21.setQueue(this.mSessionObj, arrayList);
        }

        public void setQueueTitle(CharSequence charSequence) {
            MediaSessionCompatApi21.setQueueTitle(this.mSessionObj, charSequence);
        }

        public void setRatingType(int i) {
            if (Build.VERSION.SDK_INT < 22) {
                this.mRatingType = i;
            } else {
                MediaSessionCompatApi22.setRatingType(this.mSessionObj, i);
            }
        }

        public void setExtras(Bundle bundle) {
            MediaSessionCompatApi21.setExtras(this.mSessionObj, bundle);
        }

        public Object getMediaSession() {
            return this.mSessionObj;
        }

        public String getCallingPackage() {
            if (Build.VERSION.SDK_INT < 24) {
                return null;
            }
            return MediaSessionCompatApi24.getCallingPackage(this.mSessionObj);
        }

        /* access modifiers changed from: package-private */
        public ExtraSession getExtraSessionBinder() {
            if (this.mExtraSessionBinder == null) {
                this.mExtraSessionBinder = new ExtraSession();
            }
            return this.mExtraSessionBinder;
        }

        class ExtraSession extends IMediaSession.Stub {
            public List<QueueItem> getQueue() {
                return null;
            }

            ExtraSession() {
            }

            public void sendCommand(String str, Bundle bundle, ResultReceiverWrapper resultReceiverWrapper) {
                throw new AssertionError();
            }

            public boolean sendMediaButton(KeyEvent keyEvent) {
                throw new AssertionError();
            }

            public void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                if (!MediaSessionImplApi21.this.mDestroyed) {
                    MediaSessionImplApi21.this.mExtraControllerCallbacks.register(iMediaControllerCallback);
                }
            }

            public void unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                MediaSessionImplApi21.this.mExtraControllerCallbacks.unregister(iMediaControllerCallback);
            }

            public String getPackageName() {
                throw new AssertionError();
            }

            public String getTag() {
                throw new AssertionError();
            }

            public PendingIntent getLaunchPendingIntent() {
                throw new AssertionError();
            }

            public long getFlags() {
                throw new AssertionError();
            }

            public ParcelableVolumeInfo getVolumeAttributes() {
                throw new AssertionError();
            }

            public void adjustVolume(int i, int i2, String str) {
                throw new AssertionError();
            }

            public void setVolumeTo(int i, int i2, String str) {
                throw new AssertionError();
            }

            public void prepare() throws RemoteException {
                throw new AssertionError();
            }

            public void prepareFromMediaId(String str, Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            public void prepareFromSearch(String str, Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            public void prepareFromUri(Uri uri, Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            public void play() throws RemoteException {
                throw new AssertionError();
            }

            public void playFromMediaId(String str, Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            public void playFromSearch(String str, Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            public void playFromUri(Uri uri, Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            public void skipToQueueItem(long j) {
                throw new AssertionError();
            }

            public void pause() throws RemoteException {
                throw new AssertionError();
            }

            public void stop() throws RemoteException {
                throw new AssertionError();
            }

            public void next() throws RemoteException {
                throw new AssertionError();
            }

            public void previous() throws RemoteException {
                throw new AssertionError();
            }

            public void fastForward() throws RemoteException {
                throw new AssertionError();
            }

            public void rewind() throws RemoteException {
                throw new AssertionError();
            }

            public void seekTo(long j) throws RemoteException {
                throw new AssertionError();
            }

            public void rate(RatingCompat ratingCompat) throws RemoteException {
                throw new AssertionError();
            }

            public void sendCustomAction(String str, Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            public MediaMetadataCompat getMetadata() {
                throw new AssertionError();
            }

            public PlaybackStateCompat getPlaybackState() {
                return MediaSessionImplApi21.this.mPlaybackState;
            }

            public CharSequence getQueueTitle() {
                throw new AssertionError();
            }

            public Bundle getExtras() {
                throw new AssertionError();
            }

            public int getRatingType() {
                return MediaSessionImplApi21.this.mRatingType;
            }

            public boolean isTransportControlEnabled() {
                throw new AssertionError();
            }
        }
    }
}
