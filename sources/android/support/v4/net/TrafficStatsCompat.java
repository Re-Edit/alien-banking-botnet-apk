package android.support.v4.net;

import android.os.Build;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

public final class TrafficStatsCompat {
    private static final TrafficStatsCompatImpl IMPL;

    interface TrafficStatsCompatImpl {
        void clearThreadStatsTag();

        int getThreadStatsTag();

        void incrementOperationCount(int i);

        void incrementOperationCount(int i, int i2);

        void setThreadStatsTag(int i);

        void tagDatagramSocket(DatagramSocket datagramSocket) throws SocketException;

        void tagSocket(Socket socket) throws SocketException;

        void untagDatagramSocket(DatagramSocket datagramSocket) throws SocketException;

        void untagSocket(Socket socket) throws SocketException;
    }

    static class BaseTrafficStatsCompatImpl implements TrafficStatsCompatImpl {
        private ThreadLocal<SocketTags> mThreadSocketTags = new ThreadLocal<SocketTags>() {
            /* access modifiers changed from: protected */
            public SocketTags initialValue() {
                return new SocketTags();
            }
        };

        public void incrementOperationCount(int i) {
        }

        public void incrementOperationCount(int i, int i2) {
        }

        public void tagDatagramSocket(DatagramSocket datagramSocket) {
        }

        public void tagSocket(Socket socket) {
        }

        public void untagDatagramSocket(DatagramSocket datagramSocket) {
        }

        public void untagSocket(Socket socket) {
        }

        BaseTrafficStatsCompatImpl() {
        }

        private static class SocketTags {
            public int statsTag = -1;

            SocketTags() {
            }
        }

        public void clearThreadStatsTag() {
            this.mThreadSocketTags.get().statsTag = -1;
        }

        public int getThreadStatsTag() {
            return this.mThreadSocketTags.get().statsTag;
        }

        public void setThreadStatsTag(int i) {
            this.mThreadSocketTags.get().statsTag = i;
        }
    }

    static class IcsTrafficStatsCompatImpl implements TrafficStatsCompatImpl {
        IcsTrafficStatsCompatImpl() {
        }

        public void clearThreadStatsTag() {
            TrafficStatsCompatIcs.clearThreadStatsTag();
        }

        public int getThreadStatsTag() {
            return TrafficStatsCompatIcs.getThreadStatsTag();
        }

        public void incrementOperationCount(int i) {
            TrafficStatsCompatIcs.incrementOperationCount(i);
        }

        public void incrementOperationCount(int i, int i2) {
            TrafficStatsCompatIcs.incrementOperationCount(i, i2);
        }

        public void setThreadStatsTag(int i) {
            TrafficStatsCompatIcs.setThreadStatsTag(i);
        }

        public void tagSocket(Socket socket) throws SocketException {
            TrafficStatsCompatIcs.tagSocket(socket);
        }

        public void untagSocket(Socket socket) throws SocketException {
            TrafficStatsCompatIcs.untagSocket(socket);
        }

        public void tagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
            TrafficStatsCompatIcs.tagDatagramSocket(datagramSocket);
        }

        public void untagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
            TrafficStatsCompatIcs.untagDatagramSocket(datagramSocket);
        }
    }

    static class Api24TrafficStatsCompatImpl extends IcsTrafficStatsCompatImpl {
        Api24TrafficStatsCompatImpl() {
        }

        public void tagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
            TrafficStatsCompatApi24.tagDatagramSocket(datagramSocket);
        }

        public void untagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
            TrafficStatsCompatApi24.untagDatagramSocket(datagramSocket);
        }
    }

    static {
        if ("N".equals(Build.VERSION.CODENAME)) {
            IMPL = new Api24TrafficStatsCompatImpl();
        } else if (Build.VERSION.SDK_INT >= 14) {
            IMPL = new IcsTrafficStatsCompatImpl();
        } else {
            IMPL = new BaseTrafficStatsCompatImpl();
        }
    }

    public static void clearThreadStatsTag() {
        IMPL.clearThreadStatsTag();
    }

    public static int getThreadStatsTag() {
        return IMPL.getThreadStatsTag();
    }

    public static void incrementOperationCount(int i) {
        IMPL.incrementOperationCount(i);
    }

    public static void incrementOperationCount(int i, int i2) {
        IMPL.incrementOperationCount(i, i2);
    }

    public static void setThreadStatsTag(int i) {
        IMPL.setThreadStatsTag(i);
    }

    public static void tagSocket(Socket socket) throws SocketException {
        IMPL.tagSocket(socket);
    }

    public static void untagSocket(Socket socket) throws SocketException {
        IMPL.untagSocket(socket);
    }

    public static void tagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
        IMPL.tagDatagramSocket(datagramSocket);
    }

    public static void untagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
        IMPL.untagDatagramSocket(datagramSocket);
    }

    private TrafficStatsCompat() {
    }
}
