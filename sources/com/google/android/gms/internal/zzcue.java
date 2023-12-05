package com.google.android.gms.internal;

import android.net.Uri;
import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class zzcue {
    private static final String TAG = "zzcue";
    private static final char[] zzbCn = "0123456789ABCDEF".toCharArray();
    private static final Pattern zzbCo = Pattern.compile("/\\.\\.");
    private static final Pattern zzbCp = Pattern.compile("0[1-7][0-7]*");
    private static final Pattern zzbCq = Pattern.compile("0x[0-9a-f]*", 2);
    private static final Pattern zzbCr = Pattern.compile("^((?:0x[0-9a-f]+|[0-9\\\\.])+)$", 2);
    private final String mPath;
    private final String zzD;
    private final String zzbCs;
    private final String zzbCt;
    private final int zzbCu;
    private final String zzvl;

    static class zza {
        private static final String[] zzbCv = {"http", "https", "ftp"};
        private final String zzajf;
        private final String zzbCs;
        private final Uri zzbCw;
        private final URI zzbCx;
        private final Boolean zzbCy;
        private final Integer zzbCz;

        private zza(String str) {
            int port;
            this.zzajf = str;
            this.zzbCw = Uri.parse(this.zzajf);
            try {
                new URI(this.zzajf);
            } catch (URISyntaxException unused) {
            } catch (Throwable th) {
                this.zzbCx = null;
                throw th;
            }
            this.zzbCx = null;
            this.zzbCs = getScheme();
            this.zzbCy = Boolean.valueOf(zzAl());
            Integer num = this.zzbCz;
            int i = -1;
            if (num != null) {
                i = num.intValue();
            } else {
                Uri uri = this.zzbCw;
                if (!(uri == null || (port = uri.getPort()) == -1)) {
                    i = port;
                }
            }
            this.zzbCz = Integer.valueOf(i);
        }

        /* access modifiers changed from: private */
        public final int getPort() {
            return this.zzbCz.intValue();
        }

        /* access modifiers changed from: private */
        public final String getScheme() {
            String str = this.zzbCs;
            if (str != null) {
                return str;
            }
            Uri uri = this.zzbCw;
            String scheme = uri != null ? uri.getScheme() : null;
            TextUtils.isEmpty(scheme);
            if (TextUtils.isEmpty(scheme) && !TextUtils.isEmpty(this.zzajf)) {
                int indexOf = this.zzajf.indexOf(":");
                if (indexOf != -1) {
                    String lowerCase = this.zzajf.substring(0, indexOf).toLowerCase(Locale.US);
                    if (zzeV(lowerCase)) {
                        scheme = lowerCase;
                    }
                }
                if (TextUtils.isEmpty(scheme)) {
                    this.zzajf.startsWith("www.");
                    scheme = "http";
                }
            }
            if (scheme != null) {
                return scheme.toLowerCase(Locale.US);
            }
            return null;
        }

        /* access modifiers changed from: private */
        public final boolean zzAl() {
            Boolean bool = this.zzbCy;
            return bool != null ? bool.booleanValue() : zzeV(this.zzbCs);
        }

        private static boolean zzeV(String str) {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            int i = 0;
            while (true) {
                String[] strArr = zzbCv;
                if (i >= strArr.length) {
                    return false;
                }
                if (strArr[i].equals(str)) {
                    return true;
                }
                i++;
            }
        }
    }

    public zzcue(String str) {
        String str2;
        String str3;
        if (!(!TextUtils.isEmpty(str))) {
            this.zzD = null;
            this.zzbCs = null;
            this.zzbCt = null;
            this.zzbCu = -1;
            this.mPath = null;
            this.zzvl = null;
            return;
        }
        String replaceAll = str.replaceAll("^\\s+", "").replaceAll("\\s+$", "").replaceAll("[\\t\\n\\r]", "");
        int indexOf = replaceAll.indexOf(35);
        replaceAll = indexOf != -1 ? replaceAll.substring(0, indexOf) : replaceAll;
        zza zza2 = new zza(replaceAll);
        if (!zza2.zzAl()) {
            this.zzD = null;
            this.zzbCs = null;
            this.zzbCt = null;
            this.zzbCu = -1;
            this.mPath = null;
            this.zzvl = null;
            return;
        }
        this.zzbCs = zza2.getScheme();
        this.zzbCu = zza2.getPort();
        String str4 = this.zzbCs;
        if (str4 != null) {
            StringBuilder sb = new StringBuilder(String.valueOf(str4).length() + 2);
            sb.append("^");
            sb.append(str4);
            sb.append(":");
            replaceAll = replaceAll.replaceAll(sb.toString(), "");
        }
        String zzeT = zzeT(replaceAll.replaceAll("^/+", ""));
        int indexOf2 = zzeT.indexOf(63);
        if (indexOf2 != -1) {
            int i = indexOf2 + 1;
            str2 = i < zzeT.length() ? zzeT.substring(i) : "";
            zzeT = zzeT.substring(0, indexOf2);
        } else {
            str2 = null;
        }
        if (TextUtils.isEmpty(zzeT)) {
            str3 = null;
        } else {
            int indexOf3 = zzeT.indexOf(47);
            String substring = indexOf3 != -1 ? zzeT.substring(0, indexOf3) : zzeT;
            int indexOf4 = substring.indexOf(64);
            substring = indexOf4 != -1 ? substring.substring(indexOf4 + 1) : substring;
            int i2 = this.zzbCu;
            if (i2 != -1) {
                StringBuilder sb2 = new StringBuilder(13);
                sb2.append(":");
                sb2.append(i2);
                sb2.append("$");
                substring = substring.replaceAll(sb2.toString(), "");
            }
            String replaceAll2 = substring.replaceAll("^\\.*", "").replaceAll("\\.*$", "").replaceAll("\\.+", ".");
            String zzeP = zzeP(replaceAll2);
            str3 = (zzeP != null ? zzeP : replaceAll2).toLowerCase(Locale.getDefault());
        }
        if (TextUtils.isEmpty(str3)) {
            this.zzD = null;
            this.zzbCt = null;
            this.mPath = null;
            this.zzvl = null;
            return;
        }
        String zzeR = zzeR(zzeT);
        this.zzbCt = zzeS(str3);
        this.mPath = zzeS(zzeR);
        this.zzvl = !TextUtils.isEmpty(str2) ? zzeS(str2) : str2;
        this.zzD = zzeT;
    }

    private static boolean isHexDigit(char c) {
        if (c >= '0' && c <= '9') {
            return true;
        }
        if (c < 'A' || c > 'F') {
            return c >= 'a' && c <= 'f';
        }
        return true;
    }

    private final List<String> zzAj() {
        if (TextUtils.isEmpty(this.zzbCt)) {
            return null;
        }
        boolean z = false;
        ArrayList arrayList = new ArrayList();
        char[] charArray = this.zzbCt.toCharArray();
        for (int length = charArray.length - 2; length > 0 && arrayList.size() < 4; length--) {
            if (charArray[length] == '.') {
                if (z) {
                    arrayList.add(this.zzbCt.substring(length + 1));
                } else {
                    z = true;
                }
            }
        }
        arrayList.add(this.zzbCt);
        return arrayList;
    }

    private final List<String> zzAk() {
        if (TextUtils.isEmpty(this.mPath)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        char[] charArray = this.mPath.toCharArray();
        for (int i = 0; i < charArray.length && arrayList.size() < 4; i++) {
            if (charArray[i] == '/') {
                arrayList.add(this.mPath.substring(0, i + 1));
            }
        }
        if (!arrayList.isEmpty() && !((String) arrayList.get(arrayList.size() - 1)).equals(this.mPath)) {
            arrayList.add(this.mPath);
        }
        if (!TextUtils.isEmpty(this.zzvl)) {
            String str = this.mPath;
            String str2 = this.zzvl;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(str2).length());
            sb.append(str);
            sb.append("?");
            sb.append(str2);
            arrayList.add(sb.toString());
        }
        return arrayList;
    }

    private static String zzeP(String str) {
        String str2;
        Object[] objArr;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String replaceAll = str.replaceAll("^\\[", "").replaceAll("\\]$", "");
        if (zzcuc.zzeJ(replaceAll)) {
            String zza2 = zzcuc.zza(zzcuc.zzeO(replaceAll));
            if (!zza2.contains(":")) {
                return zza2;
            }
            str2 = "[%s]";
            objArr = new Object[]{zza2};
        } else {
            if (TextUtils.isDigitsOnly(str)) {
                String zzeQ = zzeQ(str);
                if (!TextUtils.isEmpty(zzeQ)) {
                    return zzeQ;
                }
            } else if (zzbCr.matcher(replaceAll).find()) {
                Matcher matcher = zzbCp.matcher(replaceAll);
                StringBuffer stringBuffer = new StringBuffer();
                while (matcher.find()) {
                    int parseInt = Integer.parseInt(matcher.group(), 8);
                    StringBuilder sb = new StringBuilder(11);
                    sb.append(parseInt);
                    matcher.appendReplacement(stringBuffer, sb.toString());
                }
                matcher.appendTail(stringBuffer);
                Matcher matcher2 = zzbCq.matcher(stringBuffer.toString());
                StringBuffer stringBuffer2 = new StringBuffer();
                while (matcher2.find()) {
                    int parseInt2 = Integer.parseInt(matcher2.group().substring(2), 16);
                    StringBuilder sb2 = new StringBuilder(11);
                    sb2.append(parseInt2);
                    matcher2.appendReplacement(stringBuffer2, sb2.toString());
                }
                matcher2.appendTail(stringBuffer2);
                String stringBuffer3 = stringBuffer2.toString();
                if (!stringBuffer3.contains(":")) {
                    return stringBuffer3;
                }
                str2 = "[%s]";
                objArr = new Object[]{stringBuffer3};
            }
            return null;
        }
        return String.format(str2, objArr);
    }

    private static String zzeQ(String str) {
        byte[] bArr;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            BigInteger bigInteger = new BigInteger(str);
            byte[] byteArray = bigInteger.toByteArray();
            if (byteArray.length < 4) {
                return null;
            }
            byte[] copyOfRange = Arrays.copyOfRange(byteArray, byteArray.length - 4, byteArray.length);
            if (bigInteger.equals(new BigInteger(new byte[]{0, copyOfRange[0], copyOfRange[1], copyOfRange[2], copyOfRange[3]}))) {
                return Inet4Address.getByAddress(copyOfRange).getHostAddress();
            }
            if (byteArray.length >= 16) {
                bArr = Arrays.copyOfRange(byteArray, byteArray.length - 16, byteArray.length);
            } else {
                byte[] bArr2 = new byte[16];
                int length = 16 - byteArray.length;
                int i = 1;
                int i2 = 0;
                while (i <= length) {
                    bArr2[i2] = 0;
                    i++;
                    i2++;
                }
                int i3 = 0;
                while (i3 < byteArray.length) {
                    bArr2[i2] = byteArray[i3];
                    i3++;
                    i2++;
                }
                bArr = bArr2;
            }
            return String.format("[%s]", new Object[]{Inet6Address.getByAddress(bArr).getHostAddress()});
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException | UnknownHostException unused) {
            return null;
        }
    }

    private final String zzeR(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int indexOf = str.indexOf(47);
        String replaceAll = (indexOf != -1 ? str.substring(indexOf) : "/").replaceAll("/\\./", "/").replaceAll("/\\.$", "/");
        if (zzbCo.matcher(replaceAll).find()) {
            try {
                replaceAll = new URI(this.zzbCs, "host", replaceAll, (String) null).normalize().getRawPath();
            } catch (URISyntaxException unused) {
            }
        }
        return replaceAll.replaceAll("/+", "/");
    }

    private static String zzeS(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            byte[] bytes = str.getBytes("UTF-8");
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                char c = (char) (b & 255);
                if (c <= ' ' || c > '~' || c == '#' || c == '%') {
                    sb.append("%");
                    sb.append(zzbCn[c >>> 4]);
                    c = zzbCn[c & 15];
                }
                sb.append(c);
            }
            return sb.toString();
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    private static String zzeT(String str) {
        String str2 = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int i = 0;
        while (true) {
            String str3 = str2;
            str2 = str;
            if (str2.equals(str3) || i >= 1024) {
                return str2;
            }
            str = zzeU(str2);
            i++;
        }
        return str2;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0077  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String zzeU(java.lang.String r10) {
        /*
            r0 = 0
            java.lang.String r1 = "\\x"
            java.lang.String r2 = "%"
            java.lang.String r10 = r10.replace(r1, r2)     // Catch:{ UnsupportedEncodingException -> 0x0091 }
            java.lang.String r1 = "UTF-8"
            byte[] r10 = r10.getBytes(r1)     // Catch:{ UnsupportedEncodingException -> 0x0091 }
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream
            int r2 = r10.length
            r1.<init>(r2)
            r2 = 0
        L_0x0016:
            int r3 = r10.length
            if (r2 >= r3) goto L_0x0085
            byte r3 = r10[r2]
            r4 = r3 & 255(0xff, float:3.57E-43)
            char r4 = (char) r4
            r5 = 128(0x80, float:1.794E-43)
            r6 = 1
            if (r4 >= r5) goto L_0x0080
            int r4 = r2 + 2
            int r5 = r10.length
            if (r4 >= r5) goto L_0x0074
            byte r5 = r10[r2]
            r5 = r5 & 255(0xff, float:3.57E-43)
            char r5 = (char) r5
            r7 = 37
            if (r5 != r7) goto L_0x0074
            int r5 = r2 + 1
            byte r5 = r10[r5]
            r5 = r5 & 255(0xff, float:3.57E-43)
            char r5 = (char) r5
            byte r7 = r10[r4]
            r7 = r7 & 255(0xff, float:3.57E-43)
            char r7 = (char) r7
            boolean r8 = isHexDigit(r5)
            if (r8 == 0) goto L_0x0074
            boolean r8 = isHexDigit(r7)
            if (r8 == 0) goto L_0x0074
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>(r6)
            r8.append(r5)
            java.lang.String r5 = r8.toString()
            r8 = 16
            int r5 = java.lang.Integer.parseInt(r5, r8)
            int r5 = r5 << 4
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>(r6)
            r9.append(r7)
            java.lang.String r7 = r9.toString()
            int r7 = java.lang.Integer.parseInt(r7, r8)
            int r5 = r5 + r7
            byte r5 = (byte) r5
            java.lang.Byte r5 = java.lang.Byte.valueOf(r5)
            goto L_0x0075
        L_0x0074:
            r5 = r0
        L_0x0075:
            if (r5 == 0) goto L_0x0080
            byte r2 = r5.byteValue()
            r1.write(r2)
            r2 = r4
            goto L_0x0083
        L_0x0080:
            r1.write(r3)
        L_0x0083:
            int r2 = r2 + r6
            goto L_0x0016
        L_0x0085:
            java.lang.String r10 = new java.lang.String     // Catch:{  }
            byte[] r1 = r1.toByteArray()     // Catch:{  }
            java.lang.String r2 = "UTF-8"
            r10.<init>(r1, r2)     // Catch:{  }
            return r10
        L_0x0091:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcue.zzeU(java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0079, code lost:
        if (r3.isEmpty() == false) goto L_0x007d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List<com.google.android.gms.internal.zzcub> zzAi() {
        /*
            r9 = this;
            java.lang.String r0 = r9.zzD
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            r1 = 0
            if (r0 != 0) goto L_0x007c
            java.lang.String r0 = r9.zzbCt
            java.lang.String r0 = zzeP(r0)
            if (r0 == 0) goto L_0x001a
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r2.add(r0)
            goto L_0x001e
        L_0x001a:
            java.util.List r2 = r9.zzAj()
        L_0x001e:
            if (r2 == 0) goto L_0x007c
            boolean r0 = r2.isEmpty()
            if (r0 == 0) goto L_0x0027
            goto L_0x007c
        L_0x0027:
            java.util.List r0 = r9.zzAk()
            if (r0 == 0) goto L_0x007c
            boolean r3 = r0.isEmpty()
            if (r3 == 0) goto L_0x0034
            goto L_0x007c
        L_0x0034:
            java.util.LinkedList r3 = new java.util.LinkedList
            r3.<init>()
            java.util.Iterator r2 = r2.iterator()
        L_0x003d:
            boolean r4 = r2.hasNext()
            if (r4 == 0) goto L_0x0075
            java.lang.Object r4 = r2.next()
            java.lang.String r4 = (java.lang.String) r4
            java.util.Iterator r5 = r0.iterator()
        L_0x004d:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x003d
            java.lang.Object r6 = r5.next()
            java.lang.String r6 = (java.lang.String) r6
            java.lang.String r7 = java.lang.String.valueOf(r4)
            java.lang.String r6 = java.lang.String.valueOf(r6)
            int r8 = r6.length()
            if (r8 == 0) goto L_0x006c
            java.lang.String r6 = r7.concat(r6)
            goto L_0x0071
        L_0x006c:
            java.lang.String r6 = new java.lang.String
            r6.<init>(r7)
        L_0x0071:
            r3.add(r6)
            goto L_0x004d
        L_0x0075:
            boolean r0 = r3.isEmpty()
            if (r0 != 0) goto L_0x007c
            goto L_0x007d
        L_0x007c:
            r3 = r1
        L_0x007d:
            if (r3 == 0) goto L_0x00cd
            boolean r0 = r3.isEmpty()
            if (r0 == 0) goto L_0x0086
            goto L_0x00cd
        L_0x0086:
            java.lang.String r0 = "SHA-256"
            java.security.MessageDigest r0 = java.security.MessageDigest.getInstance(r0)     // Catch:{ NoSuchAlgorithmException -> 0x008d }
            goto L_0x008e
        L_0x008d:
            r0 = r1
        L_0x008e:
            if (r0 != 0) goto L_0x0091
            return r1
        L_0x0091:
            java.util.ArrayList r2 = new java.util.ArrayList
            int r4 = r3.size()
            r2.<init>(r4)
            java.util.Iterator r3 = r3.iterator()
        L_0x009e:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x00c6
            java.lang.Object r4 = r3.next()
            java.lang.String r4 = (java.lang.String) r4
            boolean r5 = android.text.TextUtils.isEmpty(r4)
            if (r5 != 0) goto L_0x009e
            java.lang.String r5 = "UTF-8"
            byte[] r4 = r4.getBytes(r5)     // Catch:{ UnsupportedEncodingException -> 0x00c2 }
            byte[] r4 = r0.digest(r4)     // Catch:{ UnsupportedEncodingException -> 0x00c2 }
            com.google.android.gms.internal.zzcub r5 = new com.google.android.gms.internal.zzcub     // Catch:{ UnsupportedEncodingException -> 0x00c2 }
            r5.<init>(r4)     // Catch:{ UnsupportedEncodingException -> 0x00c2 }
            r2.add(r5)     // Catch:{ UnsupportedEncodingException -> 0x00c2 }
        L_0x00c2:
            r0.reset()
            goto L_0x009e
        L_0x00c6:
            boolean r0 = r2.isEmpty()
            if (r0 != 0) goto L_0x00cd
            return r2
        L_0x00cd:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcue.zzAi():java.util.List");
    }
}
