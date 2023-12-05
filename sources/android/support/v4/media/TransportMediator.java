package android.support.v4.media;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import java.util.ArrayList;

public class TransportMediator extends TransportController {
    public static final int FLAG_KEY_MEDIA_FAST_FORWARD = 64;
    public static final int FLAG_KEY_MEDIA_NEXT = 128;
    public static final int FLAG_KEY_MEDIA_PAUSE = 16;
    public static final int FLAG_KEY_MEDIA_PLAY = 4;
    public static final int FLAG_KEY_MEDIA_PLAY_PAUSE = 8;
    public static final int FLAG_KEY_MEDIA_PREVIOUS = 1;
    public static final int FLAG_KEY_MEDIA_REWIND = 2;
    public static final int FLAG_KEY_MEDIA_STOP = 32;
    public static final int KEYCODE_MEDIA_PAUSE = 127;
    public static final int KEYCODE_MEDIA_PLAY = 126;
    public static final int KEYCODE_MEDIA_RECORD = 130;
    final AudioManager mAudioManager;
    final TransportPerformer mCallbacks;
    final Context mContext;
    final TransportMediatorJellybeanMR2 mController;
    final Object mDispatcherState;
    final KeyEvent.Callback mKeyEventCallback;
    final ArrayList<TransportStateListener> mListeners;
    final TransportMediatorCallback mTransportKeyCallback;
    final View mView;

    static boolean isMediaKey(int i) {
        if (i == 79 || i == 130) {
            return true;
        }
        switch (i) {
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
                return true;
            default:
                switch (i) {
                    case KEYCODE_MEDIA_PLAY /*126*/:
                    case KEYCODE_MEDIA_PAUSE /*127*/:
                        return true;
                    default:
                        return false;
                }
        }
    }

    public TransportMediator(Activity activity, TransportPerformer transportPerformer) {
        this(activity, (View) null, transportPerformer);
    }

    public TransportMediator(View view, TransportPerformer transportPerformer) {
        this((Activity) null, view, transportPerformer);
    }

    private TransportMediator(Activity activity, View view, TransportPerformer transportPerformer) {
        this.mListeners = new ArrayList<>();
        this.mTransportKeyCallback = new TransportMediatorCallback() {
            public void handleKey(KeyEvent keyEvent) {
                keyEvent.dispatch(TransportMediator.this.mKeyEventCallback);
            }

            public void handleAudioFocusChange(int i) {
                TransportMediator.this.mCallbacks.onAudioFocusChange(i);
            }

            public long getPlaybackPosition() {
                return TransportMediator.this.mCallbacks.onGetCurrentPosition();
            }

            public void playbackPositionUpdate(long j) {
                TransportMediator.this.mCallbacks.onSeekTo(j);
            }
        };
        this.mKeyEventCallback = new KeyEvent.Callback() {
            public boolean onKeyLongPress(int i, KeyEvent keyEvent) {
                return false;
            }

            public boolean onKeyMultiple(int i, int i2, KeyEvent keyEvent) {
                return false;
            }

            public boolean onKeyDown(int i, KeyEvent keyEvent) {
                if (TransportMediator.isMediaKey(i)) {
                    return TransportMediator.this.mCallbacks.onMediaButtonDown(i, keyEvent);
                }
                return false;
            }

            public boolean onKeyUp(int i, KeyEvent keyEvent) {
                if (TransportMediator.isMediaKey(i)) {
                    return TransportMediator.this.mCallbacks.onMediaButtonUp(i, keyEvent);
                }
                return false;
            }
        };
        this.mContext = activity != null ? activity : view.getContext();
        this.mCallbacks = transportPerformer;
        this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
        this.mView = activity != null ? activity.getWindow().getDecorView() : view;
        this.mDispatcherState = this.mView.getKeyDispatcherState();
        if (Build.VERSION.SDK_INT >= 18) {
            this.mController = new TransportMediatorJellybeanMR2(this.mContext, this.mAudioManager, this.mView, this.mTransportKeyCallback);
        } else {
            this.mController = null;
        }
    }

    public Object getRemoteControlClient() {
        TransportMediatorJellybeanMR2 transportMediatorJellybeanMR2 = this.mController;
        if (transportMediatorJellybeanMR2 != null) {
            return transportMediatorJellybeanMR2.getRemoteControlClient();
        }
        return null;
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return keyEvent.dispatch(this.mKeyEventCallback, (KeyEvent.DispatcherState) this.mDispatcherState, this);
    }

    public void registerStateListener(TransportStateListener transportStateListener) {
        this.mListeners.add(transportStateListener);
    }

    public void unregisterStateListener(TransportStateListener transportStateListener) {
        this.mListeners.remove(transportStateListener);
    }

    private TransportStateListener[] getListeners() {
        if (this.mListeners.size() <= 0) {
            return null;
        }
        TransportStateListener[] transportStateListenerArr = new TransportStateListener[this.mListeners.size()];
        this.mListeners.toArray(transportStateListenerArr);
        return transportStateListenerArr;
    }

    private void reportPlayingChanged() {
        TransportStateListener[] listeners = getListeners();
        if (listeners != null) {
            for (TransportStateListener onPlayingChanged : listeners) {
                onPlayingChanged.onPlayingChanged(this);
            }
        }
    }

    private void reportTransportControlsChanged() {
        TransportStateListener[] listeners = getListeners();
        if (listeners != null) {
            for (TransportStateListener onTransportControlsChanged : listeners) {
                onTransportControlsChanged.onTransportControlsChanged(this);
            }
        }
    }

    private void pushControllerState() {
        TransportMediatorJellybeanMR2 transportMediatorJellybeanMR2 = this.mController;
        if (transportMediatorJellybeanMR2 != null) {
            transportMediatorJellybeanMR2.refreshState(this.mCallbacks.onIsPlaying(), this.mCallbacks.onGetCurrentPosition(), this.mCallbacks.onGetTransportControlFlags());
        }
    }

    public void refreshState() {
        pushControllerState();
        reportPlayingChanged();
        reportTransportControlsChanged();
    }

    public void startPlaying() {
        TransportMediatorJellybeanMR2 transportMediatorJellybeanMR2 = this.mController;
        if (transportMediatorJellybeanMR2 != null) {
            transportMediatorJellybeanMR2.startPlaying();
        }
        this.mCallbacks.onStart();
        pushControllerState();
        reportPlayingChanged();
    }

    public void pausePlaying() {
        TransportMediatorJellybeanMR2 transportMediatorJellybeanMR2 = this.mController;
        if (transportMediatorJellybeanMR2 != null) {
            transportMediatorJellybeanMR2.pausePlaying();
        }
        this.mCallbacks.onPause();
        pushControllerState();
        reportPlayingChanged();
    }

    public void stopPlaying() {
        TransportMediatorJellybeanMR2 transportMediatorJellybeanMR2 = this.mController;
        if (transportMediatorJellybeanMR2 != null) {
            transportMediatorJellybeanMR2.stopPlaying();
        }
        this.mCallbacks.onStop();
        pushControllerState();
        reportPlayingChanged();
    }

    public long getDuration() {
        return this.mCallbacks.onGetDuration();
    }

    public long getCurrentPosition() {
        return this.mCallbacks.onGetCurrentPosition();
    }

    public void seekTo(long j) {
        this.mCallbacks.onSeekTo(j);
    }

    public boolean isPlaying() {
        return this.mCallbacks.onIsPlaying();
    }

    public int getBufferPercentage() {
        return this.mCallbacks.onGetBufferPercentage();
    }

    public int getTransportControlFlags() {
        return this.mCallbacks.onGetTransportControlFlags();
    }

    public void destroy() {
        this.mController.destroy();
    }
}
