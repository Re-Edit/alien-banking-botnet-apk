package com.google.android.gms.internal;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Pattern;

final class zzcuc {
    private static final Pattern zzbCj = Pattern.compile("[.]");
    private static final Inet4Address zzbCk = ((Inet4Address) zzeO("127.0.0.1"));
    private static final Inet4Address zzbCl = ((Inet4Address) zzeO("0.0.0.0"));

    static String zza(InetAddress inetAddress) {
        String str;
        if (inetAddress instanceof Inet4Address) {
            return inetAddress.getHostAddress();
        }
        byte[] address = inetAddress.getAddress();
        int[] iArr = new int[8];
        for (int i = 0; i < 8; i++) {
            int i2 = i * 2;
            iArr[i] = (address[i2 + 1] & 255) | ((address[i2] & 255) << 8) | 0;
        }
        int i3 = -1;
        int i4 = -1;
        int i5 = -1;
        for (int i6 = 0; i6 < 9; i6++) {
            if (i6 >= 8 || iArr[i6] != 0) {
                if (i5 >= 0) {
                    int i7 = i6 - i5;
                    if (i7 > i3) {
                        i4 = i5;
                        i3 = i7;
                    }
                    i5 = -1;
                }
            } else if (i5 < 0) {
                i5 = i6;
            }
        }
        if (i3 >= 2) {
            Arrays.fill(iArr, i4, i3 + i4, -1);
        }
        StringBuilder sb = new StringBuilder(39);
        int i8 = 0;
        boolean z = false;
        while (i8 < 8) {
            boolean z2 = iArr[i8] >= 0;
            if (z2) {
                if (z) {
                    sb.append(':');
                }
                str = Integer.toHexString(iArr[i8]);
            } else if (i8 == 0 || z) {
                str = "::";
            } else {
                i8++;
                z = z2;
            }
            sb.append(str);
            i8++;
            z = z2;
        }
        return sb.toString();
    }

    static boolean zzeJ(String str) {
        return zzeK(str) != null;
    }

    private static byte[] zzeK(String str) {
        boolean z = false;
        boolean z2 = false;
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == '.') {
                z2 = true;
            } else if (charAt == ':') {
                if (z2) {
                    return null;
                }
                z = true;
            } else if (Character.digit(charAt, 16) == -1) {
                return null;
            }
        }
        if (z) {
            if (z2) {
                int lastIndexOf = str.lastIndexOf(58) + 1;
                String substring = str.substring(0, lastIndexOf);
                byte[] zzeL = zzeL(str.substring(lastIndexOf));
                if (zzeL == null) {
                    str = null;
                } else {
                    String hexString = Integer.toHexString(((zzeL[0] & 255) << 8) | (zzeL[1] & 255));
                    String hexString2 = Integer.toHexString((zzeL[3] & 255) | ((zzeL[2] & 255) << 8));
                    StringBuilder sb = new StringBuilder(String.valueOf(substring).length() + 1 + String.valueOf(hexString).length() + String.valueOf(hexString2).length());
                    sb.append(substring);
                    sb.append(hexString);
                    sb.append(":");
                    sb.append(hexString2);
                    str = sb.toString();
                }
                if (str == null) {
                    return null;
                }
            }
            return zzeM(str);
        } else if (z2) {
            return zzeL(str);
        } else {
            return null;
        }
    }

    private static byte[] zzeL(String str) {
        byte[] bArr = new byte[4];
        try {
            String[] split = zzbCj.split(str, 4);
            int length = split.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                String str2 = split[i];
                int i3 = i2 + 1;
                int parseInt = Integer.parseInt(str2);
                if (parseInt > 255 || (str2.startsWith("0") && str2.length() > 1)) {
                    throw new NumberFormatException();
                }
                bArr[i2] = (byte) parseInt;
                i++;
                i2 = i3;
            }
            if (i2 == 4) {
                return bArr;
            }
            return null;
        } catch (NumberFormatException unused) {
        }
    }

    private static byte[] zzeM(String str) {
        int i;
        int i2;
        String[] split = str.split(":", 10);
        if (split.length < 3 || split.length > 9) {
            return null;
        }
        int i3 = -1;
        for (int i4 = 1; i4 < split.length - 1; i4++) {
            if (split[i4].length() == 0) {
                if (i3 >= 0) {
                    return null;
                }
                i3 = i4;
            }
        }
        if (i3 >= 0) {
            int length = (split.length - i3) - 1;
            if (split[0].length() == 0) {
                i = i3 - 1;
                if (i != 0) {
                    return null;
                }
            } else {
                i = i3;
            }
            if (split[split.length - 1].length() == 0) {
                i2 = length - 1;
                if (i2 != 0) {
                    return null;
                }
            } else {
                i2 = length;
            }
        } else {
            i = split.length;
            i2 = 0;
        }
        int i5 = 8 - (i + i2);
        if (i3 < 0 ? i5 != 0 : i5 <= 0) {
            return null;
        }
        ByteBuffer allocate = ByteBuffer.allocate(16);
        int i6 = 0;
        while (i6 < i) {
            try {
                allocate.putShort(zzeN(split[i6]));
                i6++;
            } catch (NumberFormatException unused) {
                return null;
            }
        }
        for (int i7 = 0; i7 < i5; i7++) {
            allocate.putShort(0);
        }
        while (i2 > 0) {
            allocate.putShort(zzeN(split[split.length - i2]));
            i2--;
        }
        return allocate.array();
    }

    private static short zzeN(String str) {
        int parseInt = Integer.parseInt(str, 16);
        if (parseInt <= 65535) {
            return (short) parseInt;
        }
        throw new NumberFormatException();
    }

    static InetAddress zzeO(String str) {
        byte[] zzeK = zzeK(str);
        if (zzeK != null) {
            return zzr(zzeK);
        }
        throw new IllegalArgumentException(String.format(Locale.ROOT, "'%s' is not an IP string literal.", new Object[]{str}));
    }

    private static InetAddress zzr(byte[] bArr) {
        try {
            return InetAddress.getByAddress(bArr);
        } catch (UnknownHostException e) {
            throw new AssertionError(e);
        }
    }
}
