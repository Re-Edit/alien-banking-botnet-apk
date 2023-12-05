package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.annotation.KeepForSdkWithMembers;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.zze;
import com.google.android.gms.common.zzo;
import com.google.android.gms.internal.zzfd;
import com.google.android.gms.internal.zzfe;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@KeepForSdkWithMembers
public class AdvertisingIdClient {
    private final Context mContext;
    @Nullable
    private com.google.android.gms.common.zza zzsA;
    @Nullable
    private zzfd zzsB;
    private boolean zzsC;
    private Object zzsD;
    @Nullable
    private zza zzsE;
    private long zzsF;

    public static final class Info {
        private final String zzsL;
        private final boolean zzsM;

        public Info(String str, boolean z) {
            this.zzsL = str;
            this.zzsM = z;
        }

        public final String getId() {
            return this.zzsL;
        }

        public final boolean isLimitAdTrackingEnabled() {
            return this.zzsM;
        }

        public final String toString() {
            String str = this.zzsL;
            boolean z = this.zzsM;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 7);
            sb.append("{");
            sb.append(str);
            sb.append("}");
            sb.append(z);
            return sb.toString();
        }
    }

    static class zza extends Thread {
        private WeakReference<AdvertisingIdClient> zzsH;
        private long zzsI;
        CountDownLatch zzsJ = new CountDownLatch(1);
        boolean zzsK = false;

        public zza(AdvertisingIdClient advertisingIdClient, long j) {
            this.zzsH = new WeakReference<>(advertisingIdClient);
            this.zzsI = j;
            start();
        }

        private final void disconnect() {
            AdvertisingIdClient advertisingIdClient = (AdvertisingIdClient) this.zzsH.get();
            if (advertisingIdClient != null) {
                advertisingIdClient.finish();
                this.zzsK = true;
            }
        }

        public final void run() {
            try {
                if (!this.zzsJ.await(this.zzsI, TimeUnit.MILLISECONDS)) {
                    disconnect();
                }
            } catch (InterruptedException unused) {
                disconnect();
            }
        }
    }

    public AdvertisingIdClient(Context context) {
        this(context, 30000, false);
    }

    public AdvertisingIdClient(Context context, long j, boolean z) {
        Context applicationContext;
        this.zzsD = new Object();
        zzbr.zzu(context);
        if (z && (applicationContext = context.getApplicationContext()) != null) {
            context = applicationContext;
        }
        this.mContext = context;
        this.zzsC = false;
        this.zzsF = j;
    }

    @Nullable
    public static Info getAdvertisingIdInfo(Context context) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        boolean z;
        AdvertisingIdClient advertisingIdClient;
        float f = 0.0f;
        try {
            Context remoteContext = zzo.getRemoteContext(context);
            if (remoteContext != null) {
                SharedPreferences sharedPreferences = remoteContext.getSharedPreferences("google_ads_flags", 0);
                z = sharedPreferences.getBoolean("gads:ad_id_app_context:enabled", false);
                try {
                    f = sharedPreferences.getFloat("gads:ad_id_app_context:ping_ratio", 0.0f);
                } catch (Exception e) {
                    e = e;
                    Log.w("AdvertisingIdClient", "Error while reading from SharedPreferences ", e);
                    advertisingIdClient = new AdvertisingIdClient(context, -1, z);
                    advertisingIdClient.start(false);
                    Info info = advertisingIdClient.getInfo();
                    advertisingIdClient.zza(info, z, f, (Throwable) null);
                    return info;
                }
            } else {
                z = false;
            }
        } catch (Exception e2) {
            e = e2;
            z = false;
            Log.w("AdvertisingIdClient", "Error while reading from SharedPreferences ", e);
            advertisingIdClient = new AdvertisingIdClient(context, -1, z);
            advertisingIdClient.start(false);
            Info info2 = advertisingIdClient.getInfo();
            advertisingIdClient.zza(info2, z, f, (Throwable) null);
            return info2;
        }
        advertisingIdClient = new AdvertisingIdClient(context, -1, z);
        try {
            advertisingIdClient.start(false);
            Info info22 = advertisingIdClient.getInfo();
            advertisingIdClient.zza(info22, z, f, (Throwable) null);
            return info22;
        } catch (Throwable th) {
            advertisingIdClient.zza((Info) null, z, f, th);
            return null;
        } finally {
            advertisingIdClient.finish();
        }
    }

    public static void setShouldSkipGmsCoreVersionCheck(boolean z) {
    }

    private final void start(boolean z) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        zzbr.zzcG("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (this.zzsC) {
                finish();
            }
            this.zzsA = zzd(this.mContext);
            this.zzsB = zza(this.mContext, this.zzsA);
            this.zzsC = true;
            if (z) {
                zzai();
            }
        }
    }

    private static zzfd zza(Context context, com.google.android.gms.common.zza zza2) throws IOException {
        try {
            return zzfe.zzc(zza2.zza(10000, TimeUnit.MILLISECONDS));
        } catch (InterruptedException unused) {
            throw new IOException("Interrupted exception");
        } catch (Throwable th) {
            throw new IOException(th);
        }
    }

    private final void zza(Info info, boolean z, float f, Throwable th) {
        if (Math.random() <= ((double) f)) {
            Bundle bundle = new Bundle();
            bundle.putString("app_context", z ? "1" : "0");
            if (info != null) {
                bundle.putString("limit_ad_tracking", info.isLimitAdTrackingEnabled() ? "1" : "0");
            }
            if (!(info == null || info.getId() == null)) {
                bundle.putString("ad_id_size", Integer.toString(info.getId().length()));
            }
            if (th != null) {
                bundle.putString("error", th.getClass().getName());
            }
            Uri.Builder buildUpon = Uri.parse("https://pagead2.googlesyndication.com/pagead/gen_204?id=gmob-apps").buildUpon();
            for (String str : bundle.keySet()) {
                buildUpon.appendQueryParameter(str, bundle.getString(str));
            }
            new zza(this, buildUpon.build().toString()).start();
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(7:2|3|(3:5|6|7)|8|9|(1:11)|12) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x0013 */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x001b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zzai() {
        /*
            r6 = this;
            java.lang.Object r0 = r6.zzsD
            monitor-enter(r0)
            com.google.android.gms.ads.identifier.AdvertisingIdClient$zza r1 = r6.zzsE     // Catch:{ all -> 0x0026 }
            if (r1 == 0) goto L_0x0013
            com.google.android.gms.ads.identifier.AdvertisingIdClient$zza r1 = r6.zzsE     // Catch:{ all -> 0x0026 }
            java.util.concurrent.CountDownLatch r1 = r1.zzsJ     // Catch:{ all -> 0x0026 }
            r1.countDown()     // Catch:{ all -> 0x0026 }
            com.google.android.gms.ads.identifier.AdvertisingIdClient$zza r1 = r6.zzsE     // Catch:{ InterruptedException -> 0x0013 }
            r1.join()     // Catch:{ InterruptedException -> 0x0013 }
        L_0x0013:
            long r1 = r6.zzsF     // Catch:{ all -> 0x0026 }
            r3 = 0
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 <= 0) goto L_0x0024
            com.google.android.gms.ads.identifier.AdvertisingIdClient$zza r1 = new com.google.android.gms.ads.identifier.AdvertisingIdClient$zza     // Catch:{ all -> 0x0026 }
            long r2 = r6.zzsF     // Catch:{ all -> 0x0026 }
            r1.<init>(r6, r2)     // Catch:{ all -> 0x0026 }
            r6.zzsE = r1     // Catch:{ all -> 0x0026 }
        L_0x0024:
            monitor-exit(r0)     // Catch:{ all -> 0x0026 }
            return
        L_0x0026:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0026 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.identifier.AdvertisingIdClient.zzai():void");
    }

    private static com.google.android.gms.common.zza zzd(Context context) throws IOException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        try {
            context.getPackageManager().getPackageInfo("com.android.vending", 0);
            int isGooglePlayServicesAvailable = zze.zzoU().isGooglePlayServicesAvailable(context);
            if (isGooglePlayServicesAvailable == 0 || isGooglePlayServicesAvailable == 2) {
                com.google.android.gms.common.zza zza2 = new com.google.android.gms.common.zza();
                Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
                intent.setPackage("com.google.android.gms");
                try {
                    if (com.google.android.gms.common.stats.zza.zzrT().zza(context, intent, zza2, 1)) {
                        return zza2;
                    }
                    throw new IOException("Connection failure");
                } catch (Throwable th) {
                    throw new IOException(th);
                }
            } else {
                throw new IOException("Google Play services not available");
            }
        } catch (PackageManager.NameNotFoundException unused) {
            throw new GooglePlayServicesNotAvailableException(9);
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        finish();
        super.finalize();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0038, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void finish() {
        /*
            r3 = this;
            java.lang.String r0 = "Calling this from your main thread can lead to deadlock"
            com.google.android.gms.common.internal.zzbr.zzcG(r0)
            monitor-enter(r3)
            android.content.Context r0 = r3.mContext     // Catch:{ all -> 0x0039 }
            if (r0 == 0) goto L_0x0037
            com.google.android.gms.common.zza r0 = r3.zzsA     // Catch:{ all -> 0x0039 }
            if (r0 != 0) goto L_0x000f
            goto L_0x0037
        L_0x000f:
            boolean r0 = r3.zzsC     // Catch:{ IllegalArgumentException -> 0x0027, Throwable -> 0x001e }
            if (r0 == 0) goto L_0x002d
            com.google.android.gms.common.stats.zza.zzrT()     // Catch:{ IllegalArgumentException -> 0x0027, Throwable -> 0x001e }
            android.content.Context r0 = r3.mContext     // Catch:{ IllegalArgumentException -> 0x0027, Throwable -> 0x001e }
            com.google.android.gms.common.zza r1 = r3.zzsA     // Catch:{ IllegalArgumentException -> 0x0027, Throwable -> 0x001e }
            r0.unbindService(r1)     // Catch:{ IllegalArgumentException -> 0x0027, Throwable -> 0x001e }
            goto L_0x002d
        L_0x001e:
            r0 = move-exception
            java.lang.String r1 = "AdvertisingIdClient"
            java.lang.String r2 = "AdvertisingIdClient unbindService failed."
        L_0x0023:
            android.util.Log.i(r1, r2, r0)     // Catch:{ all -> 0x0039 }
            goto L_0x002d
        L_0x0027:
            r0 = move-exception
            java.lang.String r1 = "AdvertisingIdClient"
            java.lang.String r2 = "AdvertisingIdClient unbindService failed."
            goto L_0x0023
        L_0x002d:
            r0 = 0
            r3.zzsC = r0     // Catch:{ all -> 0x0039 }
            r0 = 0
            r3.zzsB = r0     // Catch:{ all -> 0x0039 }
            r3.zzsA = r0     // Catch:{ all -> 0x0039 }
            monitor-exit(r3)     // Catch:{ all -> 0x0039 }
            return
        L_0x0037:
            monitor-exit(r3)     // Catch:{ all -> 0x0039 }
            return
        L_0x0039:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0039 }
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.identifier.AdvertisingIdClient.finish():void");
    }

    public Info getInfo() throws IOException {
        Info info;
        zzbr.zzcG("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (!this.zzsC) {
                synchronized (this.zzsD) {
                    if (this.zzsE == null || !this.zzsE.zzsK) {
                        throw new IOException("AdvertisingIdClient is not connected.");
                    }
                }
                try {
                    start(false);
                    if (!this.zzsC) {
                        throw new IOException("AdvertisingIdClient cannot reconnect.");
                    }
                } catch (RemoteException e) {
                    Log.i("AdvertisingIdClient", "GMS remote exception ", e);
                    throw new IOException("Remote exception");
                } catch (Exception e2) {
                    throw new IOException("AdvertisingIdClient cannot reconnect.", e2);
                }
            }
            zzbr.zzu(this.zzsA);
            zzbr.zzu(this.zzsB);
            info = new Info(this.zzsB.getId(), this.zzsB.zzb(true));
        }
        zzai();
        return info;
    }

    public void start() throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        start(true);
    }
}
