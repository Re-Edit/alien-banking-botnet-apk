package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.common.util.zzr;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class zzbhu {
    protected static <O, I> I zza(zzbhv<I, O> zzbhv, Object obj) {
        return zzbhv.zzaIS != null ? zzbhv.convertBack(obj) : obj;
    }

    private static void zza(StringBuilder sb, zzbhv zzbhv, Object obj) {
        String str;
        if (zzbhv.zzaIJ == 11) {
            str = ((zzbhu) zzbhv.zzaIP.cast(obj)).toString();
        } else if (zzbhv.zzaIJ == 7) {
            sb.append("\"");
            sb.append(zzq.zzcK((String) obj));
            str = "\"";
        } else {
            sb.append(obj);
            return;
        }
        sb.append(str);
    }

    private static void zza(StringBuilder sb, zzbhv zzbhv, ArrayList<Object> arrayList) {
        sb.append("[");
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(",");
            }
            Object obj = arrayList.get(i);
            if (obj != null) {
                zza(sb, zzbhv, obj);
            }
        }
        sb.append("]");
    }

    public String toString() {
        String str;
        String str2;
        Map<String, zzbhv<?, ?>> zzrK = zzrK();
        StringBuilder sb = new StringBuilder(100);
        for (String next : zzrK.keySet()) {
            zzbhv zzbhv = zzrK.get(next);
            if (zza(zzbhv)) {
                Object zza = zza(zzbhv, zzb(zzbhv));
                sb.append(sb.length() == 0 ? "{" : ",");
                sb.append("\"");
                sb.append(next);
                sb.append("\":");
                if (zza == null) {
                    str2 = "null";
                } else {
                    switch (zzbhv.zzaIL) {
                        case 8:
                            sb.append("\"");
                            str = zzd.zzg((byte[]) zza);
                            break;
                        case 9:
                            sb.append("\"");
                            str = zzd.zzh((byte[]) zza);
                            break;
                        case 10:
                            zzr.zza(sb, (HashMap) zza);
                            continue;
                        default:
                            if (!zzbhv.zzaIK) {
                                zza(sb, zzbhv, zza);
                                break;
                            } else {
                                zza(sb, zzbhv, (ArrayList<Object>) (ArrayList) zza);
                                continue;
                            }
                    }
                    sb.append(str);
                    str2 = "\"";
                }
                sb.append(str2);
            }
        }
        sb.append(sb.length() > 0 ? "}" : "{}");
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public boolean zza(zzbhv zzbhv) {
        if (zzbhv.zzaIL != 11) {
            return zzcI(zzbhv.zzaIN);
        }
        if (zzbhv.zzaIM) {
            String str = zzbhv.zzaIN;
            throw new UnsupportedOperationException("Concrete type arrays not supported");
        }
        String str2 = zzbhv.zzaIN;
        throw new UnsupportedOperationException("Concrete types not supported");
    }

    /* access modifiers changed from: protected */
    public Object zzb(zzbhv zzbhv) {
        String str = zzbhv.zzaIN;
        if (zzbhv.zzaIP == null) {
            return zzcH(zzbhv.zzaIN);
        }
        zzcH(zzbhv.zzaIN);
        zzbr.zza(true, "Concrete field shouldn't be value object: %s", zzbhv.zzaIN);
        boolean z = zzbhv.zzaIM;
        try {
            char upperCase = Character.toUpperCase(str.charAt(0));
            String valueOf = String.valueOf(str.substring(1));
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 4);
            sb.append("get");
            sb.append(upperCase);
            sb.append(valueOf);
            return getClass().getMethod(sb.toString(), new Class[0]).invoke(this, new Object[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* access modifiers changed from: protected */
    public abstract Object zzcH(String str);

    /* access modifiers changed from: protected */
    public abstract boolean zzcI(String str);

    public abstract Map<String, zzbhv<?, ?>> zzrK();
}
