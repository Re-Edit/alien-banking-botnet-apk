package android.support.v4.text;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import java.util.Locale;

public final class TextUtilsCompat {
    static String ARAB_SCRIPT_SUBTAG = "Arab";
    static String HEBR_SCRIPT_SUBTAG = "Hebr";
    private static final TextUtilsCompatImpl IMPL;
    public static final Locale ROOT = new Locale("", "");

    private static class TextUtilsCompatImpl {
        TextUtilsCompatImpl() {
        }

        @NonNull
        public String htmlEncode(@NonNull String str) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                char charAt = str.charAt(i);
                if (charAt == '\"') {
                    sb.append("&quot;");
                } else if (charAt == '<') {
                    sb.append("&lt;");
                } else if (charAt != '>') {
                    switch (charAt) {
                        case MotionEventCompat.AXIS_GENERIC_7 /*38*/:
                            sb.append("&amp;");
                            break;
                        case MotionEventCompat.AXIS_GENERIC_8 /*39*/:
                            sb.append("&#39;");
                            break;
                        default:
                            sb.append(charAt);
                            break;
                    }
                } else {
                    sb.append("&gt;");
                }
            }
            return sb.toString();
        }

        public int getLayoutDirectionFromLocale(@Nullable Locale locale) {
            if (locale == null || locale.equals(TextUtilsCompat.ROOT)) {
                return 0;
            }
            String maximizeAndGetScript = ICUCompat.maximizeAndGetScript(locale);
            if (maximizeAndGetScript == null) {
                return getLayoutDirectionFromFirstChar(locale);
            }
            return (maximizeAndGetScript.equalsIgnoreCase(TextUtilsCompat.ARAB_SCRIPT_SUBTAG) || maximizeAndGetScript.equalsIgnoreCase(TextUtilsCompat.HEBR_SCRIPT_SUBTAG)) ? 1 : 0;
        }

        private static int getLayoutDirectionFromFirstChar(@NonNull Locale locale) {
            switch (Character.getDirectionality(locale.getDisplayName(locale).charAt(0))) {
                case 1:
                case 2:
                    return 1;
                default:
                    return 0;
            }
        }
    }

    private static class TextUtilsCompatJellybeanMr1Impl extends TextUtilsCompatImpl {
        TextUtilsCompatJellybeanMr1Impl() {
        }

        @NonNull
        public String htmlEncode(@NonNull String str) {
            return TextUtilsCompatJellybeanMr1.htmlEncode(str);
        }

        public int getLayoutDirectionFromLocale(@Nullable Locale locale) {
            return TextUtilsCompatJellybeanMr1.getLayoutDirectionFromLocale(locale);
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 17) {
            IMPL = new TextUtilsCompatJellybeanMr1Impl();
        } else {
            IMPL = new TextUtilsCompatImpl();
        }
    }

    @NonNull
    public static String htmlEncode(@NonNull String str) {
        return IMPL.htmlEncode(str);
    }

    public static int getLayoutDirectionFromLocale(@Nullable Locale locale) {
        return IMPL.getLayoutDirectionFromLocale(locale);
    }

    private TextUtilsCompat() {
    }
}
