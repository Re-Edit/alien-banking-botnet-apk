package com.google.android.gms.dynamite;

import android.content.Context;
import android.database.Cursor;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import java.lang.reflect.Field;

public final class DynamiteModule {
    private static Boolean zzaSJ;
    private static zzj zzaSK;
    private static zzl zzaSL;
    private static String zzaSM;
    private static final ThreadLocal<zza> zzaSN = new ThreadLocal<>();
    private static final zzh zzaSO = new zza();
    public static final zzd zzaSP = new zzb();
    private static zzd zzaSQ = new zzc();
    public static final zzd zzaSR = new zzd();
    public static final zzd zzaSS = new zze();
    public static final zzd zzaST = new zzf();
    private final Context zzaSU;

    @DynamiteApi
    public static class DynamiteLoaderClassLoader {
        public static ClassLoader sClassLoader;
    }

    static class zza {
        public Cursor zzaSV;

        private zza() {
        }

        /* synthetic */ zza(zza zza) {
            this();
        }
    }

    static class zzb implements zzh {
        private final int zzaSW;
        private final int zzaSX = 0;

        public zzb(int i, int i2) {
            this.zzaSW = i;
        }

        public final int zzF(Context context, String str) {
            return this.zzaSW;
        }

        public final int zzb(Context context, String str, boolean z) {
            return 0;
        }
    }

    public static class zzc extends Exception {
        private zzc(String str) {
            super(str);
        }

        /* synthetic */ zzc(String str, zza zza) {
            this(str);
        }

        private zzc(String str, Throwable th) {
            super(str, th);
        }

        /* synthetic */ zzc(String str, Throwable th, zza zza) {
            this(str, th);
        }
    }

    public interface zzd {
        zzi zza(Context context, String str, zzh zzh) throws zzc;
    }

    private DynamiteModule(Context context) {
        this.zzaSU = (Context) zzbr.zzu(context);
    }

    public static int zzF(Context context, String str) {
        try {
            ClassLoader classLoader = context.getApplicationContext().getClassLoader();
            String valueOf = String.valueOf("com.google.android.gms.dynamite.descriptors.");
            String valueOf2 = String.valueOf("ModuleDescriptor");
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 1 + String.valueOf(str).length() + String.valueOf(valueOf2).length());
            sb.append(valueOf);
            sb.append(str);
            sb.append(".");
            sb.append(valueOf2);
            Class<?> loadClass = classLoader.loadClass(sb.toString());
            Field declaredField = loadClass.getDeclaredField("MODULE_ID");
            Field declaredField2 = loadClass.getDeclaredField("MODULE_VERSION");
            if (declaredField.get((Object) null).equals(str)) {
                return declaredField2.getInt((Object) null);
            }
            String valueOf3 = String.valueOf(declaredField.get((Object) null));
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf3).length() + 51 + String.valueOf(str).length());
            sb2.append("Module descriptor id '");
            sb2.append(valueOf3);
            sb2.append("' didn't match expected id '");
            sb2.append(str);
            sb2.append("'");
            Log.e("DynamiteModule", sb2.toString());
            return 0;
        } catch (ClassNotFoundException unused) {
            StringBuilder sb3 = new StringBuilder(String.valueOf(str).length() + 45);
            sb3.append("Local module descriptor class for ");
            sb3.append(str);
            sb3.append(" not found.");
            Log.w("DynamiteModule", sb3.toString());
            return 0;
        } catch (Exception e) {
            String valueOf4 = String.valueOf(e.getMessage());
            Log.e("DynamiteModule", valueOf4.length() != 0 ? "Failed to load module descriptor class: ".concat(valueOf4) : new String("Failed to load module descriptor class: "));
            return 0;
        }
    }

    public static int zzG(Context context, String str) {
        return zzb(context, str, false);
    }

    private static DynamiteModule zzH(Context context, String str) {
        String valueOf = String.valueOf(str);
        Log.i("DynamiteModule", valueOf.length() != 0 ? "Selected local version of ".concat(valueOf) : new String("Selected local version of "));
        return new DynamiteModule(context.getApplicationContext());
    }

    private static Context zza(Context context, String str, int i, Cursor cursor, zzl zzl) {
        try {
            return (Context) zzn.zzE(zzl.zza(zzn.zzw(context), str, i, zzn.zzw(cursor)));
        } catch (Exception e) {
            String valueOf = String.valueOf(e.toString());
            Log.e("DynamiteModule", valueOf.length() != 0 ? "Failed to load DynamiteLoader: ".concat(valueOf) : new String("Failed to load DynamiteLoader: "));
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x007e, code lost:
        if (r1.zzaSV != null) goto L_0x0080;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0097, code lost:
        if (r1.zzaSV != null) goto L_0x0080;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00d5, code lost:
        if (r1.zzaSV != null) goto L_0x0080;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.dynamite.DynamiteModule zza(android.content.Context r10, com.google.android.gms.dynamite.DynamiteModule.zzd r11, java.lang.String r12) throws com.google.android.gms.dynamite.DynamiteModule.zzc {
        /*
            java.lang.ThreadLocal<com.google.android.gms.dynamite.DynamiteModule$zza> r0 = zzaSN
            java.lang.Object r0 = r0.get()
            com.google.android.gms.dynamite.DynamiteModule$zza r0 = (com.google.android.gms.dynamite.DynamiteModule.zza) r0
            com.google.android.gms.dynamite.DynamiteModule$zza r1 = new com.google.android.gms.dynamite.DynamiteModule$zza
            r2 = 0
            r1.<init>(r2)
            java.lang.ThreadLocal<com.google.android.gms.dynamite.DynamiteModule$zza> r3 = zzaSN
            r3.set(r1)
            com.google.android.gms.dynamite.zzh r3 = zzaSO     // Catch:{ all -> 0x0125 }
            com.google.android.gms.dynamite.zzi r3 = r11.zza(r10, r12, r3)     // Catch:{ all -> 0x0125 }
            java.lang.String r4 = "DynamiteModule"
            int r5 = r3.zzaSY     // Catch:{ all -> 0x0125 }
            int r6 = r3.zzaSZ     // Catch:{ all -> 0x0125 }
            java.lang.String r7 = java.lang.String.valueOf(r12)     // Catch:{ all -> 0x0125 }
            int r7 = r7.length()     // Catch:{ all -> 0x0125 }
            int r7 = r7 + 68
            java.lang.String r8 = java.lang.String.valueOf(r12)     // Catch:{ all -> 0x0125 }
            int r8 = r8.length()     // Catch:{ all -> 0x0125 }
            int r7 = r7 + r8
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0125 }
            r8.<init>(r7)     // Catch:{ all -> 0x0125 }
            java.lang.String r7 = "Considering local module "
            r8.append(r7)     // Catch:{ all -> 0x0125 }
            r8.append(r12)     // Catch:{ all -> 0x0125 }
            java.lang.String r7 = ":"
            r8.append(r7)     // Catch:{ all -> 0x0125 }
            r8.append(r5)     // Catch:{ all -> 0x0125 }
            java.lang.String r5 = " and remote module "
            r8.append(r5)     // Catch:{ all -> 0x0125 }
            r8.append(r12)     // Catch:{ all -> 0x0125 }
            java.lang.String r5 = ":"
            r8.append(r5)     // Catch:{ all -> 0x0125 }
            r8.append(r6)     // Catch:{ all -> 0x0125 }
            java.lang.String r5 = r8.toString()     // Catch:{ all -> 0x0125 }
            android.util.Log.i(r4, r5)     // Catch:{ all -> 0x0125 }
            int r4 = r3.zzaTa     // Catch:{ all -> 0x0125 }
            if (r4 == 0) goto L_0x00fb
            int r4 = r3.zzaTa     // Catch:{ all -> 0x0125 }
            r5 = -1
            if (r4 != r5) goto L_0x006b
            int r4 = r3.zzaSY     // Catch:{ all -> 0x0125 }
            if (r4 == 0) goto L_0x00fb
        L_0x006b:
            int r4 = r3.zzaTa     // Catch:{ all -> 0x0125 }
            r6 = 1
            if (r4 != r6) goto L_0x0074
            int r4 = r3.zzaSZ     // Catch:{ all -> 0x0125 }
            if (r4 == 0) goto L_0x00fb
        L_0x0074:
            int r4 = r3.zzaTa     // Catch:{ all -> 0x0125 }
            if (r4 != r5) goto L_0x008b
            com.google.android.gms.dynamite.DynamiteModule r10 = zzH(r10, r12)     // Catch:{ all -> 0x0125 }
            android.database.Cursor r11 = r1.zzaSV
            if (r11 == 0) goto L_0x0085
        L_0x0080:
            android.database.Cursor r11 = r1.zzaSV
            r11.close()
        L_0x0085:
            java.lang.ThreadLocal<com.google.android.gms.dynamite.DynamiteModule$zza> r11 = zzaSN
            r11.set(r0)
            return r10
        L_0x008b:
            int r4 = r3.zzaTa     // Catch:{ all -> 0x0125 }
            if (r4 != r6) goto L_0x00e0
            int r4 = r3.zzaSZ     // Catch:{ zzc -> 0x009a }
            com.google.android.gms.dynamite.DynamiteModule r10 = zza((android.content.Context) r10, (java.lang.String) r12, (int) r4)     // Catch:{ zzc -> 0x009a }
            android.database.Cursor r11 = r1.zzaSV
            if (r11 == 0) goto L_0x0085
            goto L_0x0080
        L_0x009a:
            r4 = move-exception
            java.lang.String r6 = "DynamiteModule"
            java.lang.String r7 = "Failed to load remote module: "
            java.lang.String r8 = r4.getMessage()     // Catch:{ all -> 0x0125 }
            java.lang.String r8 = java.lang.String.valueOf(r8)     // Catch:{ all -> 0x0125 }
            int r9 = r8.length()     // Catch:{ all -> 0x0125 }
            if (r9 == 0) goto L_0x00b2
            java.lang.String r7 = r7.concat(r8)     // Catch:{ all -> 0x0125 }
            goto L_0x00b8
        L_0x00b2:
            java.lang.String r8 = new java.lang.String     // Catch:{ all -> 0x0125 }
            r8.<init>(r7)     // Catch:{ all -> 0x0125 }
            r7 = r8
        L_0x00b8:
            android.util.Log.w(r6, r7)     // Catch:{ all -> 0x0125 }
            int r6 = r3.zzaSY     // Catch:{ all -> 0x0125 }
            if (r6 == 0) goto L_0x00d8
            com.google.android.gms.dynamite.DynamiteModule$zzb r6 = new com.google.android.gms.dynamite.DynamiteModule$zzb     // Catch:{ all -> 0x0125 }
            int r3 = r3.zzaSY     // Catch:{ all -> 0x0125 }
            r7 = 0
            r6.<init>(r3, r7)     // Catch:{ all -> 0x0125 }
            com.google.android.gms.dynamite.zzi r11 = r11.zza(r10, r12, r6)     // Catch:{ all -> 0x0125 }
            int r11 = r11.zzaTa     // Catch:{ all -> 0x0125 }
            if (r11 != r5) goto L_0x00d8
            com.google.android.gms.dynamite.DynamiteModule r10 = zzH(r10, r12)     // Catch:{ all -> 0x0125 }
            android.database.Cursor r11 = r1.zzaSV
            if (r11 == 0) goto L_0x0085
            goto L_0x0080
        L_0x00d8:
            com.google.android.gms.dynamite.DynamiteModule$zzc r10 = new com.google.android.gms.dynamite.DynamiteModule$zzc     // Catch:{ all -> 0x0125 }
            java.lang.String r11 = "Remote load failed. No local fallback found."
            r10.<init>(r11, r4, r2)     // Catch:{ all -> 0x0125 }
            throw r10     // Catch:{ all -> 0x0125 }
        L_0x00e0:
            com.google.android.gms.dynamite.DynamiteModule$zzc r10 = new com.google.android.gms.dynamite.DynamiteModule$zzc     // Catch:{ all -> 0x0125 }
            int r11 = r3.zzaTa     // Catch:{ all -> 0x0125 }
            r12 = 47
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0125 }
            r3.<init>(r12)     // Catch:{ all -> 0x0125 }
            java.lang.String r12 = "VersionPolicy returned invalid code:"
            r3.append(r12)     // Catch:{ all -> 0x0125 }
            r3.append(r11)     // Catch:{ all -> 0x0125 }
            java.lang.String r11 = r3.toString()     // Catch:{ all -> 0x0125 }
            r10.<init>((java.lang.String) r11, (com.google.android.gms.dynamite.zza) r2)     // Catch:{ all -> 0x0125 }
            throw r10     // Catch:{ all -> 0x0125 }
        L_0x00fb:
            com.google.android.gms.dynamite.DynamiteModule$zzc r10 = new com.google.android.gms.dynamite.DynamiteModule$zzc     // Catch:{ all -> 0x0125 }
            int r11 = r3.zzaSY     // Catch:{ all -> 0x0125 }
            int r12 = r3.zzaSZ     // Catch:{ all -> 0x0125 }
            r3 = 91
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0125 }
            r4.<init>(r3)     // Catch:{ all -> 0x0125 }
            java.lang.String r3 = "No acceptable module found. Local version is "
            r4.append(r3)     // Catch:{ all -> 0x0125 }
            r4.append(r11)     // Catch:{ all -> 0x0125 }
            java.lang.String r11 = " and remote version is "
            r4.append(r11)     // Catch:{ all -> 0x0125 }
            r4.append(r12)     // Catch:{ all -> 0x0125 }
            java.lang.String r11 = "."
            r4.append(r11)     // Catch:{ all -> 0x0125 }
            java.lang.String r11 = r4.toString()     // Catch:{ all -> 0x0125 }
            r10.<init>((java.lang.String) r11, (com.google.android.gms.dynamite.zza) r2)     // Catch:{ all -> 0x0125 }
            throw r10     // Catch:{ all -> 0x0125 }
        L_0x0125:
            r10 = move-exception
            android.database.Cursor r11 = r1.zzaSV
            if (r11 == 0) goto L_0x012f
            android.database.Cursor r11 = r1.zzaSV
            r11.close()
        L_0x012f:
            java.lang.ThreadLocal<com.google.android.gms.dynamite.DynamiteModule$zza> r11 = zzaSN
            r11.set(r0)
            throw r10
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.zza(android.content.Context, com.google.android.gms.dynamite.DynamiteModule$zzd, java.lang.String):com.google.android.gms.dynamite.DynamiteModule");
    }

    private static DynamiteModule zza(Context context, String str, int i) throws zzc {
        Boolean bool;
        synchronized (DynamiteModule.class) {
            bool = zzaSJ;
        }
        if (bool != null) {
            return bool.booleanValue() ? zzc(context, str, i) : zzb(context, str, i);
        }
        throw new zzc("Failed to determine which loading route to use.", (zza) null);
    }

    /* JADX WARNING: type inference failed for: r1v5, types: [android.os.IInterface] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void zza(java.lang.ClassLoader r3) throws com.google.android.gms.dynamite.DynamiteModule.zzc {
        /*
            r0 = 0
            java.lang.String r1 = "com.google.android.gms.dynamiteloader.DynamiteLoaderV2"
            java.lang.Class r3 = r3.loadClass(r1)     // Catch:{ ClassNotFoundException -> 0x0039, IllegalAccessException -> 0x0037, InstantiationException -> 0x0035, InvocationTargetException -> 0x0033, NoSuchMethodException -> 0x0031 }
            r1 = 0
            java.lang.Class[] r2 = new java.lang.Class[r1]     // Catch:{ ClassNotFoundException -> 0x0039, IllegalAccessException -> 0x0037, InstantiationException -> 0x0035, InvocationTargetException -> 0x0033, NoSuchMethodException -> 0x0031 }
            java.lang.reflect.Constructor r3 = r3.getConstructor(r2)     // Catch:{ ClassNotFoundException -> 0x0039, IllegalAccessException -> 0x0037, InstantiationException -> 0x0035, InvocationTargetException -> 0x0033, NoSuchMethodException -> 0x0031 }
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ ClassNotFoundException -> 0x0039, IllegalAccessException -> 0x0037, InstantiationException -> 0x0035, InvocationTargetException -> 0x0033, NoSuchMethodException -> 0x0031 }
            java.lang.Object r3 = r3.newInstance(r1)     // Catch:{ ClassNotFoundException -> 0x0039, IllegalAccessException -> 0x0037, InstantiationException -> 0x0035, InvocationTargetException -> 0x0033, NoSuchMethodException -> 0x0031 }
            android.os.IBinder r3 = (android.os.IBinder) r3     // Catch:{ ClassNotFoundException -> 0x0039, IllegalAccessException -> 0x0037, InstantiationException -> 0x0035, InvocationTargetException -> 0x0033, NoSuchMethodException -> 0x0031 }
            if (r3 != 0) goto L_0x001a
            r3 = r0
            goto L_0x002e
        L_0x001a:
            java.lang.String r1 = "com.google.android.gms.dynamite.IDynamiteLoaderV2"
            android.os.IInterface r1 = r3.queryLocalInterface(r1)     // Catch:{ ClassNotFoundException -> 0x0039, IllegalAccessException -> 0x0037, InstantiationException -> 0x0035, InvocationTargetException -> 0x0033, NoSuchMethodException -> 0x0031 }
            boolean r2 = r1 instanceof com.google.android.gms.dynamite.zzl     // Catch:{ ClassNotFoundException -> 0x0039, IllegalAccessException -> 0x0037, InstantiationException -> 0x0035, InvocationTargetException -> 0x0033, NoSuchMethodException -> 0x0031 }
            if (r2 == 0) goto L_0x0028
            r3 = r1
            com.google.android.gms.dynamite.zzl r3 = (com.google.android.gms.dynamite.zzl) r3     // Catch:{ ClassNotFoundException -> 0x0039, IllegalAccessException -> 0x0037, InstantiationException -> 0x0035, InvocationTargetException -> 0x0033, NoSuchMethodException -> 0x0031 }
            goto L_0x002e
        L_0x0028:
            com.google.android.gms.dynamite.zzm r1 = new com.google.android.gms.dynamite.zzm     // Catch:{ ClassNotFoundException -> 0x0039, IllegalAccessException -> 0x0037, InstantiationException -> 0x0035, InvocationTargetException -> 0x0033, NoSuchMethodException -> 0x0031 }
            r1.<init>(r3)     // Catch:{ ClassNotFoundException -> 0x0039, IllegalAccessException -> 0x0037, InstantiationException -> 0x0035, InvocationTargetException -> 0x0033, NoSuchMethodException -> 0x0031 }
            r3 = r1
        L_0x002e:
            zzaSL = r3     // Catch:{ ClassNotFoundException -> 0x0039, IllegalAccessException -> 0x0037, InstantiationException -> 0x0035, InvocationTargetException -> 0x0033, NoSuchMethodException -> 0x0031 }
            return
        L_0x0031:
            r3 = move-exception
            goto L_0x003a
        L_0x0033:
            r3 = move-exception
            goto L_0x003a
        L_0x0035:
            r3 = move-exception
            goto L_0x003a
        L_0x0037:
            r3 = move-exception
            goto L_0x003a
        L_0x0039:
            r3 = move-exception
        L_0x003a:
            com.google.android.gms.dynamite.DynamiteModule$zzc r1 = new com.google.android.gms.dynamite.DynamiteModule$zzc
            java.lang.String r2 = "Failed to instantiate dynamite loader"
            r1.<init>(r2, r3, r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.zza(java.lang.ClassLoader):void");
    }

    /* JADX WARNING: type inference failed for: r1v7, types: [android.os.IInterface] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.google.android.gms.dynamite.zzj zzaT(android.content.Context r5) {
        /*
            java.lang.Class<com.google.android.gms.dynamite.DynamiteModule> r0 = com.google.android.gms.dynamite.DynamiteModule.class
            monitor-enter(r0)
            com.google.android.gms.dynamite.zzj r1 = zzaSK     // Catch:{ all -> 0x006f }
            if (r1 == 0) goto L_0x000b
            com.google.android.gms.dynamite.zzj r5 = zzaSK     // Catch:{ all -> 0x006f }
            monitor-exit(r0)     // Catch:{ all -> 0x006f }
            return r5
        L_0x000b:
            com.google.android.gms.common.zze r1 = com.google.android.gms.common.zze.zzoU()     // Catch:{ all -> 0x006f }
            int r1 = r1.isGooglePlayServicesAvailable(r5)     // Catch:{ all -> 0x006f }
            r2 = 0
            if (r1 == 0) goto L_0x0018
            monitor-exit(r0)     // Catch:{ all -> 0x006f }
            return r2
        L_0x0018:
            java.lang.String r1 = "com.google.android.gms"
            r3 = 3
            android.content.Context r5 = r5.createPackageContext(r1, r3)     // Catch:{ Exception -> 0x004d }
            java.lang.ClassLoader r5 = r5.getClassLoader()     // Catch:{ Exception -> 0x004d }
            java.lang.String r1 = "com.google.android.gms.chimera.container.DynamiteLoaderImpl"
            java.lang.Class r5 = r5.loadClass(r1)     // Catch:{ Exception -> 0x004d }
            java.lang.Object r5 = r5.newInstance()     // Catch:{ Exception -> 0x004d }
            android.os.IBinder r5 = (android.os.IBinder) r5     // Catch:{ Exception -> 0x004d }
            if (r5 != 0) goto L_0x0033
            r5 = r2
            goto L_0x0047
        L_0x0033:
            java.lang.String r1 = "com.google.android.gms.dynamite.IDynamiteLoader"
            android.os.IInterface r1 = r5.queryLocalInterface(r1)     // Catch:{ Exception -> 0x004d }
            boolean r3 = r1 instanceof com.google.android.gms.dynamite.zzj     // Catch:{ Exception -> 0x004d }
            if (r3 == 0) goto L_0x0041
            r5 = r1
            com.google.android.gms.dynamite.zzj r5 = (com.google.android.gms.dynamite.zzj) r5     // Catch:{ Exception -> 0x004d }
            goto L_0x0047
        L_0x0041:
            com.google.android.gms.dynamite.zzk r1 = new com.google.android.gms.dynamite.zzk     // Catch:{ Exception -> 0x004d }
            r1.<init>(r5)     // Catch:{ Exception -> 0x004d }
            r5 = r1
        L_0x0047:
            if (r5 == 0) goto L_0x006d
            zzaSK = r5     // Catch:{ Exception -> 0x004d }
            monitor-exit(r0)     // Catch:{ all -> 0x006f }
            return r5
        L_0x004d:
            r5 = move-exception
            java.lang.String r1 = "DynamiteModule"
            java.lang.String r3 = "Failed to load IDynamiteLoader from GmsCore: "
            java.lang.String r5 = r5.getMessage()     // Catch:{ all -> 0x006f }
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ all -> 0x006f }
            int r4 = r5.length()     // Catch:{ all -> 0x006f }
            if (r4 == 0) goto L_0x0065
            java.lang.String r5 = r3.concat(r5)     // Catch:{ all -> 0x006f }
            goto L_0x006a
        L_0x0065:
            java.lang.String r5 = new java.lang.String     // Catch:{ all -> 0x006f }
            r5.<init>(r3)     // Catch:{ all -> 0x006f }
        L_0x006a:
            android.util.Log.e(r1, r5)     // Catch:{ all -> 0x006f }
        L_0x006d:
            monitor-exit(r0)     // Catch:{ all -> 0x006f }
            return r2
        L_0x006f:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x006f }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.zzaT(android.content.Context):com.google.android.gms.dynamite.zzj");
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:659)
        	at java.util.ArrayList.get(ArrayList.java:435)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:22:0x0050=Splitter:B:22:0x0050, B:17:0x0035=Splitter:B:17:0x0035, B:34:0x0079=Splitter:B:34:0x0079} */
    public static int zzb(android.content.Context r8, java.lang.String r9, boolean r10) {
        /*
            java.lang.Class<com.google.android.gms.dynamite.DynamiteModule> r0 = com.google.android.gms.dynamite.DynamiteModule.class
            monitor-enter(r0)
            java.lang.Boolean r1 = zzaSJ     // Catch:{ all -> 0x00ea }
            if (r1 != 0) goto L_0x00b7
            android.content.Context r1 = r8.getApplicationContext()     // Catch:{ ClassNotFoundException -> 0x008e, IllegalAccessException -> 0x008c, NoSuchFieldException -> 0x008a }
            java.lang.ClassLoader r1 = r1.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x008e, IllegalAccessException -> 0x008c, NoSuchFieldException -> 0x008a }
            java.lang.Class<com.google.android.gms.dynamite.DynamiteModule$DynamiteLoaderClassLoader> r2 = com.google.android.gms.dynamite.DynamiteModule.DynamiteLoaderClassLoader.class
            java.lang.String r2 = r2.getName()     // Catch:{ ClassNotFoundException -> 0x008e, IllegalAccessException -> 0x008c, NoSuchFieldException -> 0x008a }
            java.lang.Class r1 = r1.loadClass(r2)     // Catch:{ ClassNotFoundException -> 0x008e, IllegalAccessException -> 0x008c, NoSuchFieldException -> 0x008a }
            java.lang.String r2 = "sClassLoader"
            java.lang.reflect.Field r2 = r1.getDeclaredField(r2)     // Catch:{ ClassNotFoundException -> 0x008e, IllegalAccessException -> 0x008c, NoSuchFieldException -> 0x008a }
            monitor-enter(r1)     // Catch:{ ClassNotFoundException -> 0x008e, IllegalAccessException -> 0x008c, NoSuchFieldException -> 0x008a }
            r3 = 0
            java.lang.Object r4 = r2.get(r3)     // Catch:{ all -> 0x0087 }
            java.lang.ClassLoader r4 = (java.lang.ClassLoader) r4     // Catch:{ all -> 0x0087 }
            if (r4 == 0) goto L_0x0038
            java.lang.ClassLoader r2 = java.lang.ClassLoader.getSystemClassLoader()     // Catch:{ all -> 0x0087 }
            if (r4 != r2) goto L_0x0032
        L_0x002f:
            java.lang.Boolean r2 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x0087 }
            goto L_0x0084
        L_0x0032:
            zza(r4)     // Catch:{ zzc -> 0x0035 }
        L_0x0035:
            java.lang.Boolean r2 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x0087 }
            goto L_0x0084
        L_0x0038:
            java.lang.String r4 = "com.google.android.gms"
            android.content.Context r5 = r8.getApplicationContext()     // Catch:{ all -> 0x0087 }
            java.lang.String r5 = r5.getPackageName()     // Catch:{ all -> 0x0087 }
            boolean r4 = r4.equals(r5)     // Catch:{ all -> 0x0087 }
            if (r4 == 0) goto L_0x0050
            java.lang.ClassLoader r4 = java.lang.ClassLoader.getSystemClassLoader()     // Catch:{ all -> 0x0087 }
            r2.set(r3, r4)     // Catch:{ all -> 0x0087 }
            goto L_0x002f
        L_0x0050:
            int r4 = zzd(r8, r9, r10)     // Catch:{ zzc -> 0x007c }
            java.lang.String r5 = zzaSM     // Catch:{ zzc -> 0x007c }
            if (r5 == 0) goto L_0x0079
            java.lang.String r5 = zzaSM     // Catch:{ zzc -> 0x007c }
            boolean r5 = r5.isEmpty()     // Catch:{ zzc -> 0x007c }
            if (r5 == 0) goto L_0x0061
            goto L_0x0079
        L_0x0061:
            com.google.android.gms.dynamite.zzg r5 = new com.google.android.gms.dynamite.zzg     // Catch:{ zzc -> 0x007c }
            java.lang.String r6 = zzaSM     // Catch:{ zzc -> 0x007c }
            java.lang.ClassLoader r7 = java.lang.ClassLoader.getSystemClassLoader()     // Catch:{ zzc -> 0x007c }
            r5.<init>(r6, r7)     // Catch:{ zzc -> 0x007c }
            zza(r5)     // Catch:{ zzc -> 0x007c }
            r2.set(r3, r5)     // Catch:{ zzc -> 0x007c }
            java.lang.Boolean r5 = java.lang.Boolean.TRUE     // Catch:{ zzc -> 0x007c }
            zzaSJ = r5     // Catch:{ zzc -> 0x007c }
            monitor-exit(r1)     // Catch:{ all -> 0x0087 }
            monitor-exit(r0)     // Catch:{ all -> 0x00ea }
            return r4
        L_0x0079:
            monitor-exit(r1)     // Catch:{ all -> 0x0087 }
            monitor-exit(r0)     // Catch:{ all -> 0x00ea }
            return r4
        L_0x007c:
            java.lang.ClassLoader r4 = java.lang.ClassLoader.getSystemClassLoader()     // Catch:{ all -> 0x0087 }
            r2.set(r3, r4)     // Catch:{ all -> 0x0087 }
            goto L_0x002f
        L_0x0084:
            monitor-exit(r1)     // Catch:{ all -> 0x0087 }
            r1 = r2
            goto L_0x00b5
        L_0x0087:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0087 }
            throw r2     // Catch:{ ClassNotFoundException -> 0x008e, IllegalAccessException -> 0x008c, NoSuchFieldException -> 0x008a }
        L_0x008a:
            r1 = move-exception
            goto L_0x008f
        L_0x008c:
            r1 = move-exception
            goto L_0x008f
        L_0x008e:
            r1 = move-exception
        L_0x008f:
            java.lang.String r2 = "DynamiteModule"
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x00ea }
            java.lang.String r3 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x00ea }
            int r3 = r3.length()     // Catch:{ all -> 0x00ea }
            int r3 = r3 + 30
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ea }
            r4.<init>(r3)     // Catch:{ all -> 0x00ea }
            java.lang.String r3 = "Failed to load module via V2: "
            r4.append(r3)     // Catch:{ all -> 0x00ea }
            r4.append(r1)     // Catch:{ all -> 0x00ea }
            java.lang.String r1 = r4.toString()     // Catch:{ all -> 0x00ea }
            android.util.Log.w(r2, r1)     // Catch:{ all -> 0x00ea }
            java.lang.Boolean r1 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x00ea }
        L_0x00b5:
            zzaSJ = r1     // Catch:{ all -> 0x00ea }
        L_0x00b7:
            monitor-exit(r0)     // Catch:{ all -> 0x00ea }
            boolean r0 = r1.booleanValue()
            if (r0 == 0) goto L_0x00e5
            int r8 = zzd(r8, r9, r10)     // Catch:{ zzc -> 0x00c3 }
            return r8
        L_0x00c3:
            r8 = move-exception
            java.lang.String r9 = "DynamiteModule"
            java.lang.String r10 = "Failed to retrieve remote module version: "
            java.lang.String r8 = r8.getMessage()
            java.lang.String r8 = java.lang.String.valueOf(r8)
            int r0 = r8.length()
            if (r0 == 0) goto L_0x00db
            java.lang.String r8 = r10.concat(r8)
            goto L_0x00e0
        L_0x00db:
            java.lang.String r8 = new java.lang.String
            r8.<init>(r10)
        L_0x00e0:
            android.util.Log.w(r9, r8)
            r8 = 0
            return r8
        L_0x00e5:
            int r8 = zzc((android.content.Context) r8, (java.lang.String) r9, (boolean) r10)
            return r8
        L_0x00ea:
            r8 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00ea }
            throw r8
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.zzb(android.content.Context, java.lang.String, boolean):int");
    }

    private static DynamiteModule zzb(Context context, String str, int i) throws zzc {
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 51);
        sb.append("Selected remote version of ");
        sb.append(str);
        sb.append(", version >= ");
        sb.append(i);
        Log.i("DynamiteModule", sb.toString());
        zzj zzaT = zzaT(context);
        if (zzaT != null) {
            try {
                IObjectWrapper zza2 = zzaT.zza(zzn.zzw(context), str, i);
                if (zzn.zzE(zza2) != null) {
                    return new DynamiteModule((Context) zzn.zzE(zza2));
                }
                throw new zzc("Failed to load remote module.", (zza) null);
            } catch (RemoteException e) {
                throw new zzc("Failed to load remote module.", e, (zza) null);
            }
        } else {
            throw new zzc("Failed to create IDynamiteLoader.", (zza) null);
        }
    }

    private static int zzc(Context context, String str, boolean z) {
        zzj zzaT = zzaT(context);
        if (zzaT == null) {
            return 0;
        }
        try {
            return zzaT.zza(zzn.zzw(context), str, z);
        } catch (RemoteException e) {
            String valueOf = String.valueOf(e.getMessage());
            Log.w("DynamiteModule", valueOf.length() != 0 ? "Failed to retrieve remote module version: ".concat(valueOf) : new String("Failed to retrieve remote module version: "));
            return 0;
        }
    }

    private static DynamiteModule zzc(Context context, String str, int i) throws zzc {
        zzl zzl;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 51);
        sb.append("Selected remote version of ");
        sb.append(str);
        sb.append(", version >= ");
        sb.append(i);
        Log.i("DynamiteModule", sb.toString());
        synchronized (DynamiteModule.class) {
            zzl = zzaSL;
        }
        if (zzl != null) {
            zza zza2 = zzaSN.get();
            if (zza2 == null || zza2.zzaSV == null) {
                throw new zzc("No result cursor", (zza) null);
            }
            Context zza3 = zza(context.getApplicationContext(), str, i, zza2.zzaSV, zzl);
            if (zza3 != null) {
                return new DynamiteModule(zza3);
            }
            throw new zzc("Failed to get module context", (zza) null);
        }
        throw new zzc("DynamiteLoaderV2 was not cached.", (zza) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:54:0x00b3  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int zzd(android.content.Context r8, java.lang.String r9, boolean r10) throws com.google.android.gms.dynamite.DynamiteModule.zzc {
        /*
            r0 = 0
            if (r10 == 0) goto L_0x0006
            java.lang.String r10 = "api_force_staging"
            goto L_0x0008
        L_0x0006:
            java.lang.String r10 = "api"
        L_0x0008:
            java.lang.String r1 = "content://com.google.android.gms.chimera/"
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ Exception -> 0x00a0, all -> 0x009e }
            java.lang.String r2 = java.lang.String.valueOf(r1)     // Catch:{ Exception -> 0x00a0, all -> 0x009e }
            int r2 = r2.length()     // Catch:{ Exception -> 0x00a0, all -> 0x009e }
            int r2 = r2 + 1
            java.lang.String r3 = java.lang.String.valueOf(r10)     // Catch:{ Exception -> 0x00a0, all -> 0x009e }
            int r3 = r3.length()     // Catch:{ Exception -> 0x00a0, all -> 0x009e }
            int r2 = r2 + r3
            java.lang.String r3 = java.lang.String.valueOf(r9)     // Catch:{ Exception -> 0x00a0, all -> 0x009e }
            int r3 = r3.length()     // Catch:{ Exception -> 0x00a0, all -> 0x009e }
            int r2 = r2 + r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a0, all -> 0x009e }
            r3.<init>(r2)     // Catch:{ Exception -> 0x00a0, all -> 0x009e }
            r3.append(r1)     // Catch:{ Exception -> 0x00a0, all -> 0x009e }
            r3.append(r10)     // Catch:{ Exception -> 0x00a0, all -> 0x009e }
            java.lang.String r10 = "/"
            r3.append(r10)     // Catch:{ Exception -> 0x00a0, all -> 0x009e }
            r3.append(r9)     // Catch:{ Exception -> 0x00a0, all -> 0x009e }
            java.lang.String r9 = r3.toString()     // Catch:{ Exception -> 0x00a0, all -> 0x009e }
            android.net.Uri r2 = android.net.Uri.parse(r9)     // Catch:{ Exception -> 0x00a0, all -> 0x009e }
            android.content.ContentResolver r1 = r8.getContentResolver()     // Catch:{ Exception -> 0x00a0, all -> 0x009e }
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            android.database.Cursor r8 = r1.query(r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x00a0, all -> 0x009e }
            if (r8 == 0) goto L_0x0086
            boolean r9 = r8.moveToFirst()     // Catch:{ Exception -> 0x0099, all -> 0x0095 }
            if (r9 == 0) goto L_0x0086
            r9 = 0
            int r9 = r8.getInt(r9)     // Catch:{ Exception -> 0x0099, all -> 0x0095 }
            if (r9 <= 0) goto L_0x0080
            java.lang.Class<com.google.android.gms.dynamite.DynamiteModule> r10 = com.google.android.gms.dynamite.DynamiteModule.class
            monitor-enter(r10)     // Catch:{ Exception -> 0x0099, all -> 0x0095 }
            r1 = 2
            java.lang.String r1 = r8.getString(r1)     // Catch:{ all -> 0x007d }
            zzaSM = r1     // Catch:{ all -> 0x007d }
            monitor-exit(r10)     // Catch:{ all -> 0x007d }
            java.lang.ThreadLocal<com.google.android.gms.dynamite.DynamiteModule$zza> r10 = zzaSN     // Catch:{ Exception -> 0x0099, all -> 0x0095 }
            java.lang.Object r10 = r10.get()     // Catch:{ Exception -> 0x0099, all -> 0x0095 }
            com.google.android.gms.dynamite.DynamiteModule$zza r10 = (com.google.android.gms.dynamite.DynamiteModule.zza) r10     // Catch:{ Exception -> 0x0099, all -> 0x0095 }
            if (r10 == 0) goto L_0x0080
            android.database.Cursor r1 = r10.zzaSV     // Catch:{ Exception -> 0x0099, all -> 0x0095 }
            if (r1 != 0) goto L_0x0080
            r10.zzaSV = r8     // Catch:{ Exception -> 0x0099, all -> 0x0095 }
            r8 = r0
            goto L_0x0080
        L_0x007d:
            r9 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x007d }
            throw r9     // Catch:{ Exception -> 0x0099, all -> 0x0095 }
        L_0x0080:
            if (r8 == 0) goto L_0x0085
            r8.close()
        L_0x0085:
            return r9
        L_0x0086:
            java.lang.String r9 = "DynamiteModule"
            java.lang.String r10 = "Failed to retrieve remote module version."
            android.util.Log.w(r9, r10)     // Catch:{ Exception -> 0x0099, all -> 0x0095 }
            com.google.android.gms.dynamite.DynamiteModule$zzc r9 = new com.google.android.gms.dynamite.DynamiteModule$zzc     // Catch:{ Exception -> 0x0099, all -> 0x0095 }
            java.lang.String r10 = "Failed to connect to dynamite module ContentResolver."
            r9.<init>((java.lang.String) r10, (com.google.android.gms.dynamite.zza) r0)     // Catch:{ Exception -> 0x0099, all -> 0x0095 }
            throw r9     // Catch:{ Exception -> 0x0099, all -> 0x0095 }
        L_0x0095:
            r9 = move-exception
            r0 = r8
            r8 = r9
            goto L_0x00b1
        L_0x0099:
            r9 = move-exception
            r7 = r9
            r9 = r8
            r8 = r7
            goto L_0x00a2
        L_0x009e:
            r8 = move-exception
            goto L_0x00b1
        L_0x00a0:
            r8 = move-exception
            r9 = r0
        L_0x00a2:
            boolean r10 = r8 instanceof com.google.android.gms.dynamite.DynamiteModule.zzc     // Catch:{ all -> 0x00af }
            if (r10 == 0) goto L_0x00a7
            throw r8     // Catch:{ all -> 0x00af }
        L_0x00a7:
            com.google.android.gms.dynamite.DynamiteModule$zzc r10 = new com.google.android.gms.dynamite.DynamiteModule$zzc     // Catch:{ all -> 0x00af }
            java.lang.String r1 = "V2 version check failed"
            r10.<init>(r1, r8, r0)     // Catch:{ all -> 0x00af }
            throw r10     // Catch:{ all -> 0x00af }
        L_0x00af:
            r8 = move-exception
            r0 = r9
        L_0x00b1:
            if (r0 == 0) goto L_0x00b6
            r0.close()
        L_0x00b6:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.zzd(android.content.Context, java.lang.String, boolean):int");
    }

    public final IBinder zzcW(String str) throws zzc {
        try {
            return (IBinder) this.zzaSU.getClassLoader().loadClass(str).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            String valueOf = String.valueOf(str);
            throw new zzc(valueOf.length() != 0 ? "Failed to instantiate module class: ".concat(valueOf) : new String("Failed to instantiate module class: "), e, (zza) null);
        }
    }

    public final Context zztB() {
        return this.zzaSU;
    }
}
