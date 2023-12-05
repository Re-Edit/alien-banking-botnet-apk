package com.jcraft.jsch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Vector;

class Util {
    private static final byte[] b64 = str2byte("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=");
    private static String[] chars = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    static final byte[] empty = str2byte("");

    private static int skipUTF8Char(byte b) {
        if (((byte) (b & 128)) == 0) {
            return 1;
        }
        if (((byte) (b & 224)) == -64) {
            return 2;
        }
        return ((byte) (b & 240)) == -32 ? 3 : 1;
    }

    Util() {
    }

    private static byte val(byte b) {
        if (b == 61) {
            return 0;
        }
        int i = 0;
        while (true) {
            byte[] bArr = b64;
            if (i >= bArr.length) {
                return 0;
            }
            if (b == bArr[i]) {
                return (byte) i;
            }
            i++;
        }
    }

    static byte[] fromBase64(byte[] bArr, int i, int i2) throws JSchException {
        try {
            byte[] bArr2 = new byte[i2];
            int i3 = i;
            int i4 = 0;
            while (true) {
                if (i3 >= i + i2) {
                    break;
                }
                int i5 = i3 + 1;
                bArr2[i4] = (byte) ((val(bArr[i3]) << 2) | ((val(bArr[i5]) & 48) >>> 4));
                int i6 = i3 + 2;
                if (bArr[i6] == 61) {
                    i4++;
                    break;
                }
                bArr2[i4 + 1] = (byte) (((val(bArr[i5]) & 15) << 4) | ((val(bArr[i6]) & 60) >>> 2));
                int i7 = i3 + 3;
                if (bArr[i7] == 61) {
                    i4 += 2;
                    break;
                }
                bArr2[i4 + 2] = (byte) (((val(bArr[i6]) & 3) << 6) | (val(bArr[i7]) & 63));
                i4 += 3;
                i3 += 4;
            }
            byte[] bArr3 = new byte[i4];
            System.arraycopy(bArr2, 0, bArr3, 0, i4);
            return bArr3;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new JSchException("fromBase64: invalid base64 data", e);
        }
    }

    static byte[] toBase64(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[(i2 * 2)];
        int i3 = ((i2 / 3) * 3) + i;
        int i4 = i;
        int i5 = 0;
        while (i4 < i3) {
            int i6 = i5 + 1;
            byte[] bArr3 = b64;
            bArr2[i5] = bArr3[(bArr[i4] >>> 2) & 63];
            int i7 = i4 + 1;
            int i8 = i6 + 1;
            bArr2[i6] = bArr3[((bArr[i4] & 3) << 4) | ((bArr[i7] >>> 4) & 15)];
            int i9 = i4 + 2;
            int i10 = ((bArr[i7] & 15) << 2) | ((bArr[i9] >>> 6) & 3);
            int i11 = i8 + 1;
            bArr2[i8] = bArr3[i10];
            bArr2[i11] = bArr3[bArr[i9] & 63];
            i4 += 3;
            i5 = i11 + 1;
        }
        int i12 = (i + i2) - i3;
        if (i12 == 1) {
            int i13 = i5 + 1;
            byte[] bArr4 = b64;
            bArr2[i5] = bArr4[(bArr[i4] >>> 2) & 63];
            int i14 = i13 + 1;
            bArr2[i13] = bArr4[((bArr[i4] & 3) << 4) & 63];
            int i15 = i14 + 1;
            bArr2[i14] = 61;
            i5 = i15 + 1;
            bArr2[i15] = 61;
        } else if (i12 == 2) {
            int i16 = i5 + 1;
            byte[] bArr5 = b64;
            bArr2[i5] = bArr5[(bArr[i4] >>> 2) & 63];
            int i17 = i4 + 1;
            int i18 = i16 + 1;
            bArr2[i16] = bArr5[((bArr[i4] & 3) << 4) | ((bArr[i17] >>> 4) & 15)];
            int i19 = i18 + 1;
            bArr2[i18] = bArr5[((bArr[i17] & 15) << 2) & 63];
            i5 = i19 + 1;
            bArr2[i19] = 61;
        }
        byte[] bArr6 = new byte[i5];
        System.arraycopy(bArr2, 0, bArr6, 0, i5);
        return bArr6;
    }

    static String[] split(String str, String str2) {
        if (str == null) {
            return null;
        }
        byte[] str2byte = str2byte(str);
        Vector vector = new Vector();
        int i = 0;
        while (true) {
            int indexOf = str.indexOf(str2, i);
            if (indexOf < 0) {
                break;
            }
            vector.addElement(byte2str(str2byte, i, indexOf - i));
            i = indexOf + 1;
        }
        vector.addElement(byte2str(str2byte, i, str2byte.length - i));
        String[] strArr = new String[vector.size()];
        for (int i2 = 0; i2 < strArr.length; i2++) {
            strArr[i2] = (String) vector.elementAt(i2);
        }
        return strArr;
    }

    static boolean glob(byte[] bArr, byte[] bArr2) {
        return glob0(bArr, 0, bArr2, 0);
    }

    private static boolean glob0(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (bArr2.length <= 0 || bArr2[0] != 46) {
            return glob(bArr, i, bArr2, i2);
        }
        if (bArr.length <= 0 || bArr[0] != 46) {
            return false;
        }
        if (bArr.length == 2 && bArr[1] == 42) {
            return true;
        }
        return glob(bArr, i + 1, bArr2, i2 + 1);
    }

    private static boolean glob(byte[] bArr, int i, byte[] bArr2, int i2) {
        int length = bArr.length;
        if (length == 0) {
            return false;
        }
        int length2 = bArr2.length;
        while (i < length && i2 < length2) {
            if (bArr[i] == 92) {
                int i3 = i + 1;
                if (i3 == length || bArr[i3] != bArr2[i2]) {
                    return false;
                }
                i = i3 + skipUTF8Char(bArr[i3]);
                i2 += skipUTF8Char(bArr2[i2]);
            } else if (bArr[i] == 42) {
                while (i < length && bArr[i] == 42) {
                    i++;
                }
                if (length == i) {
                    return true;
                }
                byte b = bArr[i];
                if (b == 63) {
                    while (i2 < length2) {
                        if (glob(bArr, i, bArr2, i2)) {
                            return true;
                        }
                        i2 += skipUTF8Char(bArr2[i2]);
                    }
                    return false;
                } else if (b == 92) {
                    int i4 = i + 1;
                    if (i4 == length) {
                        return false;
                    }
                    byte b2 = bArr[i4];
                    while (i2 < length2) {
                        if (b2 == bArr2[i2] && glob(bArr, skipUTF8Char(b2) + i4, bArr2, skipUTF8Char(bArr2[i2]) + i2)) {
                            return true;
                        }
                        i2 += skipUTF8Char(bArr2[i2]);
                    }
                    return false;
                } else {
                    while (i2 < length2) {
                        if (b == bArr2[i2] && glob(bArr, i, bArr2, i2)) {
                            return true;
                        }
                        i2 += skipUTF8Char(bArr2[i2]);
                    }
                    return false;
                }
            } else if (bArr[i] == 63) {
                i++;
                i2 += skipUTF8Char(bArr2[i2]);
            } else if (bArr[i] != bArr2[i2]) {
                return false;
            } else {
                i += skipUTF8Char(bArr[i]);
                i2 += skipUTF8Char(bArr2[i2]);
                if (i2 < length2) {
                    continue;
                } else if (i >= length) {
                    return true;
                } else {
                    if (bArr[i] == 42) {
                        break;
                    }
                }
            }
        }
        if (i == length && i2 == length2) {
            return true;
        }
        if (i2 < length2 || bArr[i] != 42) {
            return false;
        }
        while (i < length) {
            int i5 = i + 1;
            if (bArr[i] != 42) {
                return false;
            }
            i = i5;
        }
        return true;
    }

    static String quote(String str) {
        byte[] str2byte = str2byte(str);
        int i = 0;
        int i2 = 0;
        for (byte b : str2byte) {
            if (b == 92 || b == 63 || b == 42) {
                i2++;
            }
        }
        if (i2 == 0) {
            return str;
        }
        byte[] bArr = new byte[(str2byte.length + i2)];
        int i3 = 0;
        while (i < str2byte.length) {
            byte b2 = str2byte[i];
            if (b2 == 92 || b2 == 63 || b2 == 42) {
                bArr[i3] = 92;
                i3++;
            }
            bArr[i3] = b2;
            i++;
            i3++;
        }
        return byte2str(bArr);
    }

    static String unquote(String str) {
        byte[] str2byte = str2byte(str);
        byte[] unquote = unquote(str2byte);
        if (str2byte.length == unquote.length) {
            return str;
        }
        return byte2str(unquote);
    }

    static byte[] unquote(byte[] bArr) {
        int length = bArr.length;
        int i = 0;
        while (i < length) {
            if (bArr[i] == 92) {
                int i2 = i + 1;
                if (i2 == length) {
                    break;
                }
                System.arraycopy(bArr, i2, bArr, i, bArr.length - i2);
                length--;
                i = i2;
            } else {
                i++;
            }
        }
        if (length == bArr.length) {
            return bArr;
        }
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        return bArr2;
    }

    static String getFingerPrint(HASH hash, byte[] bArr) {
        try {
            hash.init();
            int i = 0;
            hash.update(bArr, 0, bArr.length);
            byte[] digest = hash.digest();
            StringBuffer stringBuffer = new StringBuffer();
            while (i < digest.length) {
                byte b = digest[i] & 255;
                stringBuffer.append(chars[(b >>> 4) & 15]);
                stringBuffer.append(chars[b & 15]);
                i++;
                if (i < digest.length) {
                    stringBuffer.append(":");
                }
            }
            return stringBuffer.toString();
        } catch (Exception unused) {
            return "???";
        }
    }

    static boolean array_equals(byte[] bArr, byte[] bArr2) {
        int length = bArr.length;
        if (length != bArr2.length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }

    static Socket createSocket(final String str, final int i, int i2) throws JSchException {
        if (i2 == 0) {
            try {
                return new Socket(str, i);
            } catch (Exception e) {
                String exc = e.toString();
                if (e instanceof Throwable) {
                    throw new JSchException(exc, e);
                }
                throw new JSchException(exc);
            }
        } else {
            final Socket[] socketArr = new Socket[1];
            final Exception[] excArr = new Exception[1];
            String str2 = "";
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    Socket[] socketArr = socketArr;
                    socketArr[0] = null;
                    try {
                        socketArr[0] = new Socket(str, i);
                    } catch (Exception e) {
                        excArr[0] = e;
                        Socket[] socketArr2 = socketArr;
                        if (socketArr2[0] != null && socketArr2[0].isConnected()) {
                            try {
                                socketArr[0].close();
                            } catch (Exception unused) {
                            }
                        }
                        socketArr[0] = null;
                    }
                }
            });
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Opening Socket ");
            stringBuffer.append(str);
            thread.setName(stringBuffer.toString());
            thread.start();
            try {
                thread.join((long) i2);
                str2 = "timeout: ";
            } catch (InterruptedException unused) {
            }
            if (socketArr[0] != null && socketArr[0].isConnected()) {
                return socketArr[0];
            }
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(str2);
            stringBuffer2.append("socket is not established");
            String stringBuffer3 = stringBuffer2.toString();
            if (excArr[0] != null) {
                stringBuffer3 = excArr[0].toString();
            }
            thread.interrupt();
            throw new JSchException(stringBuffer3, excArr[0]);
        }
    }

    static byte[] str2byte(String str, String str2) {
        if (str == null) {
            return null;
        }
        try {
            return str.getBytes(str2);
        } catch (UnsupportedEncodingException unused) {
            return str.getBytes();
        }
    }

    static byte[] str2byte(String str) {
        return str2byte(str, "UTF-8");
    }

    static String byte2str(byte[] bArr, String str) {
        return byte2str(bArr, 0, bArr.length, str);
    }

    static String byte2str(byte[] bArr, int i, int i2, String str) {
        try {
            return new String(bArr, i, i2, str);
        } catch (UnsupportedEncodingException unused) {
            return new String(bArr, i, i2);
        }
    }

    static String byte2str(byte[] bArr) {
        return byte2str(bArr, 0, bArr.length, "UTF-8");
    }

    static String byte2str(byte[] bArr, int i, int i2) {
        return byte2str(bArr, i, i2, "UTF-8");
    }

    static String toHex(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (i < bArr.length) {
            String hexString = Integer.toHexString(bArr[i] & 255);
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("0x");
            stringBuffer2.append(hexString.length() == 1 ? "0" : "");
            stringBuffer2.append(hexString);
            stringBuffer.append(stringBuffer2.toString());
            i++;
            if (i < bArr.length) {
                stringBuffer.append(":");
            }
        }
        return stringBuffer.toString();
    }

    static void bzero(byte[] bArr) {
        if (bArr != null) {
            for (int i = 0; i < bArr.length; i++) {
                bArr[i] = 0;
            }
        }
    }

    static String diffString(String str, String[] strArr) {
        String[] split = split(str, ",");
        String str2 = null;
        for (int i = 0; i < split.length; i++) {
            int i2 = 0;
            while (true) {
                if (i2 < strArr.length) {
                    if (split[i].equals(strArr[i2])) {
                        break;
                    }
                    i2++;
                } else if (str2 == null) {
                    str2 = split[i];
                } else {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(str2);
                    stringBuffer.append(",");
                    stringBuffer.append(split[i]);
                    str2 = stringBuffer.toString();
                }
            }
        }
        return str2;
    }

    static String checkTilde(String str) {
        try {
            return str.startsWith("~") ? str.replace("~", System.getProperty("user.home")) : str;
        } catch (SecurityException unused) {
            return str;
        }
    }

    static byte[] fromFile(String str) throws IOException {
        String checkTilde = checkTilde(str);
        File file = new File(checkTilde);
        FileInputStream fileInputStream = new FileInputStream(checkTilde);
        try {
            byte[] bArr = new byte[((int) file.length())];
            int i = 0;
            while (true) {
                int read = fileInputStream.read(bArr, i, bArr.length - i);
                if (read <= 0) {
                    fileInputStream.close();
                    return bArr;
                }
                i += read;
            }
        } finally {
            fileInputStream.close();
        }
    }
}
