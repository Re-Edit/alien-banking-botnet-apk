package com.google.android.gms.common.images;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.internal.zzbgy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ImageManager {
    /* access modifiers changed from: private */
    public static final Object zzaFT = new Object();
    /* access modifiers changed from: private */
    public static HashSet<Uri> zzaFU = new HashSet<>();
    private static ImageManager zzaFV;
    /* access modifiers changed from: private */
    public final Context mContext;
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public final ExecutorService zzaFW = Executors.newFixedThreadPool(4);
    /* access modifiers changed from: private */
    public final zza zzaFX = null;
    /* access modifiers changed from: private */
    public final zzbgy zzaFY = new zzbgy();
    /* access modifiers changed from: private */
    public final Map<zza, ImageReceiver> zzaFZ = new HashMap();
    /* access modifiers changed from: private */
    public final Map<Uri, ImageReceiver> zzaGa = new HashMap();
    /* access modifiers changed from: private */
    public final Map<Uri, Long> zzaGb = new HashMap();

    @KeepName
    final class ImageReceiver extends ResultReceiver {
        private final Uri mUri;
        /* access modifiers changed from: private */
        public final ArrayList<zza> zzaGc = new ArrayList<>();

        ImageReceiver(Uri uri) {
            super(new Handler(Looper.getMainLooper()));
            this.mUri = uri;
        }

        public final void onReceiveResult(int i, Bundle bundle) {
            ImageManager.this.zzaFW.execute(new zzb(this.mUri, (ParcelFileDescriptor) bundle.getParcelable("com.google.android.gms.extra.fileDescriptor")));
        }

        public final void zzb(zza zza) {
            com.google.android.gms.common.internal.zzc.zzcz("ImageReceiver.addImageRequest() must be called in the main thread");
            this.zzaGc.add(zza);
        }

        public final void zzc(zza zza) {
            com.google.android.gms.common.internal.zzc.zzcz("ImageReceiver.removeImageRequest() must be called in the main thread");
            this.zzaGc.remove(zza);
        }

        public final void zzqT() {
            Intent intent = new Intent("com.google.android.gms.common.images.LOAD_IMAGE");
            intent.putExtra("com.google.android.gms.extras.uri", this.mUri);
            intent.putExtra("com.google.android.gms.extras.resultReceiver", this);
            intent.putExtra("com.google.android.gms.extras.priority", 3);
            ImageManager.this.mContext.sendBroadcast(intent);
        }
    }

    public interface OnImageLoadedListener {
        void onImageLoaded(Uri uri, Drawable drawable, boolean z);
    }

    static final class zza extends LruCache<zzb, Bitmap> {
        /* access modifiers changed from: protected */
        public final /* synthetic */ void entryRemoved(boolean z, Object obj, Object obj2, Object obj3) {
            super.entryRemoved(z, (zzb) obj, (Bitmap) obj2, (Bitmap) obj3);
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ int sizeOf(Object obj, Object obj2) {
            Bitmap bitmap = (Bitmap) obj2;
            return bitmap.getHeight() * bitmap.getRowBytes();
        }
    }

    final class zzb implements Runnable {
        private final Uri mUri;
        private final ParcelFileDescriptor zzaGe;

        public zzb(Uri uri, ParcelFileDescriptor parcelFileDescriptor) {
            this.mUri = uri;
            this.zzaGe = parcelFileDescriptor;
        }

        public final void run() {
            boolean z;
            Bitmap bitmap;
            if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
                boolean z2 = false;
                Bitmap bitmap2 = null;
                ParcelFileDescriptor parcelFileDescriptor = this.zzaGe;
                if (parcelFileDescriptor != null) {
                    try {
                        bitmap2 = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor());
                    } catch (OutOfMemoryError e) {
                        String valueOf = String.valueOf(this.mUri);
                        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 34);
                        sb.append("OOM while loading bitmap for uri: ");
                        sb.append(valueOf);
                        Log.e("ImageManager", sb.toString(), e);
                        z2 = true;
                    }
                    try {
                        this.zzaGe.close();
                    } catch (IOException e2) {
                        Log.e("ImageManager", "closed failed", e2);
                    }
                    z = z2;
                    bitmap = bitmap2;
                } else {
                    bitmap = null;
                    z = false;
                }
                CountDownLatch countDownLatch = new CountDownLatch(1);
                ImageManager.this.mHandler.post(new zzd(this.mUri, bitmap, z, countDownLatch));
                try {
                    countDownLatch.await();
                } catch (InterruptedException unused) {
                    String valueOf2 = String.valueOf(this.mUri);
                    StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 32);
                    sb2.append("Latch interrupted while posting ");
                    sb2.append(valueOf2);
                    Log.w("ImageManager", sb2.toString());
                }
            } else {
                String valueOf3 = String.valueOf(Thread.currentThread());
                String valueOf4 = String.valueOf(Looper.getMainLooper().getThread());
                StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf3).length() + 56 + String.valueOf(valueOf4).length());
                sb3.append("checkNotMainThread: current thread ");
                sb3.append(valueOf3);
                sb3.append(" IS the main thread ");
                sb3.append(valueOf4);
                sb3.append("!");
                Log.e("Asserts", sb3.toString());
                throw new IllegalStateException("LoadBitmapFromDiskRunnable can't be executed in the main thread");
            }
        }
    }

    final class zzc implements Runnable {
        private final zza zzaGf;

        public zzc(zza zza) {
            this.zzaGf = zza;
        }

        public final void run() {
            com.google.android.gms.common.internal.zzc.zzcz("LoadImageRunnable must be executed on the main thread");
            ImageReceiver imageReceiver = (ImageReceiver) ImageManager.this.zzaFZ.get(this.zzaGf);
            if (imageReceiver != null) {
                ImageManager.this.zzaFZ.remove(this.zzaGf);
                imageReceiver.zzc(this.zzaGf);
            }
            zzb zzb = this.zzaGf.zzaGh;
            if (zzb.uri == null) {
                this.zzaGf.zza(ImageManager.this.mContext, ImageManager.this.zzaFY, true);
                return;
            }
            Bitmap zza = ImageManager.this.zza(zzb);
            if (zza != null) {
                this.zzaGf.zza(ImageManager.this.mContext, zza, true);
                return;
            }
            Long l = (Long) ImageManager.this.zzaGb.get(zzb.uri);
            if (l != null) {
                if (SystemClock.elapsedRealtime() - l.longValue() < 3600000) {
                    this.zzaGf.zza(ImageManager.this.mContext, ImageManager.this.zzaFY, true);
                    return;
                }
                ImageManager.this.zzaGb.remove(zzb.uri);
            }
            this.zzaGf.zza(ImageManager.this.mContext, ImageManager.this.zzaFY);
            ImageReceiver imageReceiver2 = (ImageReceiver) ImageManager.this.zzaGa.get(zzb.uri);
            if (imageReceiver2 == null) {
                imageReceiver2 = new ImageReceiver(zzb.uri);
                ImageManager.this.zzaGa.put(zzb.uri, imageReceiver2);
            }
            imageReceiver2.zzb(this.zzaGf);
            if (!(this.zzaGf instanceof zzd)) {
                ImageManager.this.zzaFZ.put(this.zzaGf, imageReceiver2);
            }
            synchronized (ImageManager.zzaFT) {
                if (!ImageManager.zzaFU.contains(zzb.uri)) {
                    ImageManager.zzaFU.add(zzb.uri);
                    imageReceiver2.zzqT();
                }
            }
        }
    }

    final class zzd implements Runnable {
        private final Bitmap mBitmap;
        private final Uri mUri;
        private boolean zzaGg;
        private final CountDownLatch zztM;

        public zzd(Uri uri, Bitmap bitmap, boolean z, CountDownLatch countDownLatch) {
            this.mUri = uri;
            this.mBitmap = bitmap;
            this.zzaGg = z;
            this.zztM = countDownLatch;
        }

        public final void run() {
            com.google.android.gms.common.internal.zzc.zzcz("OnBitmapLoadedRunnable must be executed in the main thread");
            boolean z = this.mBitmap != null;
            if (ImageManager.this.zzaFX != null) {
                if (this.zzaGg) {
                    ImageManager.this.zzaFX.evictAll();
                    System.gc();
                    this.zzaGg = false;
                    ImageManager.this.mHandler.post(this);
                    return;
                } else if (z) {
                    ImageManager.this.zzaFX.put(new zzb(this.mUri), this.mBitmap);
                }
            }
            ImageReceiver imageReceiver = (ImageReceiver) ImageManager.this.zzaGa.remove(this.mUri);
            if (imageReceiver != null) {
                ArrayList zza = imageReceiver.zzaGc;
                int size = zza.size();
                for (int i = 0; i < size; i++) {
                    zza zza2 = (zza) zza.get(i);
                    if (z) {
                        zza2.zza(ImageManager.this.mContext, this.mBitmap, false);
                    } else {
                        ImageManager.this.zzaGb.put(this.mUri, Long.valueOf(SystemClock.elapsedRealtime()));
                        zza2.zza(ImageManager.this.mContext, ImageManager.this.zzaFY, false);
                    }
                    if (!(zza2 instanceof zzd)) {
                        ImageManager.this.zzaFZ.remove(zza2);
                    }
                }
            }
            this.zztM.countDown();
            synchronized (ImageManager.zzaFT) {
                ImageManager.zzaFU.remove(this.mUri);
            }
        }
    }

    private ImageManager(Context context, boolean z) {
        this.mContext = context.getApplicationContext();
    }

    public static ImageManager create(Context context) {
        if (zzaFV == null) {
            zzaFV = new ImageManager(context, false);
        }
        return zzaFV;
    }

    /* access modifiers changed from: private */
    public final Bitmap zza(zzb zzb2) {
        zza zza2 = this.zzaFX;
        if (zza2 == null) {
            return null;
        }
        return (Bitmap) zza2.get(zzb2);
    }

    private final void zza(zza zza2) {
        com.google.android.gms.common.internal.zzc.zzcz("ImageManager.loadImage() must be called in the main thread");
        new zzc(zza2).run();
    }

    public final void loadImage(ImageView imageView, int i) {
        zza((zza) new zzc(imageView, i));
    }

    public final void loadImage(ImageView imageView, Uri uri) {
        zza((zza) new zzc(imageView, uri));
    }

    public final void loadImage(ImageView imageView, Uri uri, int i) {
        zzc zzc2 = new zzc(imageView, uri);
        zzc2.zzaGj = i;
        zza((zza) zzc2);
    }

    public final void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri) {
        zza((zza) new zzd(onImageLoadedListener, uri));
    }

    public final void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri, int i) {
        zzd zzd2 = new zzd(onImageLoadedListener, uri);
        zzd2.zzaGj = i;
        zza((zza) zzd2);
    }
}
