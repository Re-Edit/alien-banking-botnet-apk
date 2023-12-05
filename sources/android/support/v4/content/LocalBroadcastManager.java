package android.support.v4.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import java.util.ArrayList;
import java.util.HashMap;

public final class LocalBroadcastManager {
    private static final boolean DEBUG = false;
    static final int MSG_EXEC_PENDING_BROADCASTS = 1;
    private static final String TAG = "LocalBroadcastManager";
    private static LocalBroadcastManager mInstance;
    private static final Object mLock = new Object();
    private final HashMap<String, ArrayList<ReceiverRecord>> mActions = new HashMap<>();
    private final Context mAppContext;
    private final Handler mHandler;
    private final ArrayList<BroadcastRecord> mPendingBroadcasts = new ArrayList<>();
    private final HashMap<BroadcastReceiver, ArrayList<IntentFilter>> mReceivers = new HashMap<>();

    private static class ReceiverRecord {
        boolean broadcasting;
        final IntentFilter filter;
        final BroadcastReceiver receiver;

        ReceiverRecord(IntentFilter intentFilter, BroadcastReceiver broadcastReceiver) {
            this.filter = intentFilter;
            this.receiver = broadcastReceiver;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("Receiver{");
            sb.append(this.receiver);
            sb.append(" filter=");
            sb.append(this.filter);
            sb.append("}");
            return sb.toString();
        }
    }

    private static class BroadcastRecord {
        final Intent intent;
        final ArrayList<ReceiverRecord> receivers;

        BroadcastRecord(Intent intent2, ArrayList<ReceiverRecord> arrayList) {
            this.intent = intent2;
            this.receivers = arrayList;
        }
    }

    public static LocalBroadcastManager getInstance(Context context) {
        LocalBroadcastManager localBroadcastManager;
        synchronized (mLock) {
            if (mInstance == null) {
                mInstance = new LocalBroadcastManager(context.getApplicationContext());
            }
            localBroadcastManager = mInstance;
        }
        return localBroadcastManager;
    }

    private LocalBroadcastManager(Context context) {
        this.mAppContext = context;
        this.mHandler = new Handler(context.getMainLooper()) {
            public void handleMessage(Message message) {
                if (message.what != 1) {
                    super.handleMessage(message);
                } else {
                    LocalBroadcastManager.this.executePendingBroadcasts();
                }
            }
        };
    }

    public void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        synchronized (this.mReceivers) {
            ReceiverRecord receiverRecord = new ReceiverRecord(intentFilter, broadcastReceiver);
            ArrayList arrayList = this.mReceivers.get(broadcastReceiver);
            if (arrayList == null) {
                arrayList = new ArrayList(1);
                this.mReceivers.put(broadcastReceiver, arrayList);
            }
            arrayList.add(intentFilter);
            for (int i = 0; i < intentFilter.countActions(); i++) {
                String action = intentFilter.getAction(i);
                ArrayList arrayList2 = this.mActions.get(action);
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList(1);
                    this.mActions.put(action, arrayList2);
                }
                arrayList2.add(receiverRecord);
            }
        }
    }

    public void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        synchronized (this.mReceivers) {
            ArrayList remove = this.mReceivers.remove(broadcastReceiver);
            if (remove != null) {
                for (int i = 0; i < remove.size(); i++) {
                    IntentFilter intentFilter = (IntentFilter) remove.get(i);
                    for (int i2 = 0; i2 < intentFilter.countActions(); i2++) {
                        String action = intentFilter.getAction(i2);
                        ArrayList arrayList = this.mActions.get(action);
                        if (arrayList != null) {
                            int i3 = 0;
                            while (i3 < arrayList.size()) {
                                if (((ReceiverRecord) arrayList.get(i3)).receiver == broadcastReceiver) {
                                    arrayList.remove(i3);
                                    i3--;
                                }
                                i3++;
                            }
                            if (arrayList.size() <= 0) {
                                this.mActions.remove(action);
                            }
                        }
                    }
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0174, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0176, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean sendBroadcast(android.content.Intent r22) {
        /*
            r21 = this;
            r1 = r21
            r0 = r22
            java.util.HashMap<android.content.BroadcastReceiver, java.util.ArrayList<android.content.IntentFilter>> r2 = r1.mReceivers
            monitor-enter(r2)
            java.lang.String r10 = r22.getAction()     // Catch:{ all -> 0x0178 }
            android.content.Context r3 = r1.mAppContext     // Catch:{ all -> 0x0178 }
            android.content.ContentResolver r3 = r3.getContentResolver()     // Catch:{ all -> 0x0178 }
            java.lang.String r11 = r0.resolveTypeIfNeeded(r3)     // Catch:{ all -> 0x0178 }
            android.net.Uri r12 = r22.getData()     // Catch:{ all -> 0x0178 }
            java.lang.String r13 = r22.getScheme()     // Catch:{ all -> 0x0178 }
            java.util.Set r14 = r22.getCategories()     // Catch:{ all -> 0x0178 }
            int r3 = r22.getFlags()     // Catch:{ all -> 0x0178 }
            r3 = r3 & 8
            if (r3 == 0) goto L_0x002c
            r16 = 1
            goto L_0x002e
        L_0x002c:
            r16 = 0
        L_0x002e:
            if (r16 == 0) goto L_0x0056
            java.lang.String r3 = "LocalBroadcastManager"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0178 }
            r4.<init>()     // Catch:{ all -> 0x0178 }
            java.lang.String r5 = "Resolving type "
            r4.append(r5)     // Catch:{ all -> 0x0178 }
            r4.append(r11)     // Catch:{ all -> 0x0178 }
            java.lang.String r5 = " scheme "
            r4.append(r5)     // Catch:{ all -> 0x0178 }
            r4.append(r13)     // Catch:{ all -> 0x0178 }
            java.lang.String r5 = " of intent "
            r4.append(r5)     // Catch:{ all -> 0x0178 }
            r4.append(r0)     // Catch:{ all -> 0x0178 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0178 }
            android.util.Log.v(r3, r4)     // Catch:{ all -> 0x0178 }
        L_0x0056:
            java.util.HashMap<java.lang.String, java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$ReceiverRecord>> r3 = r1.mActions     // Catch:{ all -> 0x0178 }
            java.lang.String r4 = r22.getAction()     // Catch:{ all -> 0x0178 }
            java.lang.Object r3 = r3.get(r4)     // Catch:{ all -> 0x0178 }
            r8 = r3
            java.util.ArrayList r8 = (java.util.ArrayList) r8     // Catch:{ all -> 0x0178 }
            if (r8 == 0) goto L_0x0175
            if (r16 == 0) goto L_0x007d
            java.lang.String r3 = "LocalBroadcastManager"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0178 }
            r4.<init>()     // Catch:{ all -> 0x0178 }
            java.lang.String r5 = "Action list: "
            r4.append(r5)     // Catch:{ all -> 0x0178 }
            r4.append(r8)     // Catch:{ all -> 0x0178 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0178 }
            android.util.Log.v(r3, r4)     // Catch:{ all -> 0x0178 }
        L_0x007d:
            r3 = 0
            r6 = r3
            r7 = 0
        L_0x0080:
            int r3 = r8.size()     // Catch:{ all -> 0x0178 }
            if (r7 >= r3) goto L_0x0145
            java.lang.Object r3 = r8.get(r7)     // Catch:{ all -> 0x0178 }
            r5 = r3
            android.support.v4.content.LocalBroadcastManager$ReceiverRecord r5 = (android.support.v4.content.LocalBroadcastManager.ReceiverRecord) r5     // Catch:{ all -> 0x0178 }
            if (r16 == 0) goto L_0x00a7
            java.lang.String r3 = "LocalBroadcastManager"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0178 }
            r4.<init>()     // Catch:{ all -> 0x0178 }
            java.lang.String r9 = "Matching against filter "
            r4.append(r9)     // Catch:{ all -> 0x0178 }
            android.content.IntentFilter r9 = r5.filter     // Catch:{ all -> 0x0178 }
            r4.append(r9)     // Catch:{ all -> 0x0178 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0178 }
            android.util.Log.v(r3, r4)     // Catch:{ all -> 0x0178 }
        L_0x00a7:
            boolean r3 = r5.broadcasting     // Catch:{ all -> 0x0178 }
            if (r3 == 0) goto L_0x00cc
            if (r16 == 0) goto L_0x00c0
            java.lang.String r3 = "LocalBroadcastManager"
            java.lang.String r4 = "  Filter's target already added"
            android.util.Log.v(r3, r4)     // Catch:{ all -> 0x0178 }
            r18 = r7
            r19 = r8
            r17 = r10
            r20 = r11
            r11 = 1
            r10 = r6
            goto L_0x013a
        L_0x00c0:
            r18 = r7
            r19 = r8
            r17 = r10
            r20 = r11
            r11 = 1
            r10 = r6
            goto L_0x013a
        L_0x00cc:
            android.content.IntentFilter r3 = r5.filter     // Catch:{ all -> 0x0178 }
            java.lang.String r9 = "LocalBroadcastManager"
            r4 = r10
            r15 = r5
            r5 = r11
            r17 = r10
            r10 = r6
            r6 = r13
            r18 = r7
            r7 = r12
            r19 = r8
            r8 = r14
            r20 = r11
            r11 = 1
            int r3 = r3.match(r4, r5, r6, r7, r8, r9)     // Catch:{ all -> 0x0178 }
            if (r3 < 0) goto L_0x0111
            if (r16 == 0) goto L_0x0102
            java.lang.String r4 = "LocalBroadcastManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0178 }
            r5.<init>()     // Catch:{ all -> 0x0178 }
            java.lang.String r6 = "  Filter matched!  match=0x"
            r5.append(r6)     // Catch:{ all -> 0x0178 }
            java.lang.String r3 = java.lang.Integer.toHexString(r3)     // Catch:{ all -> 0x0178 }
            r5.append(r3)     // Catch:{ all -> 0x0178 }
            java.lang.String r3 = r5.toString()     // Catch:{ all -> 0x0178 }
            android.util.Log.v(r4, r3)     // Catch:{ all -> 0x0178 }
        L_0x0102:
            if (r10 != 0) goto L_0x010a
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch:{ all -> 0x0178 }
            r6.<init>()     // Catch:{ all -> 0x0178 }
            goto L_0x010b
        L_0x010a:
            r6 = r10
        L_0x010b:
            r6.add(r15)     // Catch:{ all -> 0x0178 }
            r15.broadcasting = r11     // Catch:{ all -> 0x0178 }
            goto L_0x013b
        L_0x0111:
            if (r16 == 0) goto L_0x013a
            switch(r3) {
                case -4: goto L_0x0122;
                case -3: goto L_0x011f;
                case -2: goto L_0x011c;
                case -1: goto L_0x0119;
                default: goto L_0x0116;
            }     // Catch:{ all -> 0x0178 }
        L_0x0116:
            java.lang.String r3 = "unknown reason"
            goto L_0x0124
        L_0x0119:
            java.lang.String r3 = "type"
            goto L_0x0124
        L_0x011c:
            java.lang.String r3 = "data"
            goto L_0x0124
        L_0x011f:
            java.lang.String r3 = "action"
            goto L_0x0124
        L_0x0122:
            java.lang.String r3 = "category"
        L_0x0124:
            java.lang.String r4 = "LocalBroadcastManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0178 }
            r5.<init>()     // Catch:{ all -> 0x0178 }
            java.lang.String r6 = "  Filter did not match: "
            r5.append(r6)     // Catch:{ all -> 0x0178 }
            r5.append(r3)     // Catch:{ all -> 0x0178 }
            java.lang.String r3 = r5.toString()     // Catch:{ all -> 0x0178 }
            android.util.Log.v(r4, r3)     // Catch:{ all -> 0x0178 }
        L_0x013a:
            r6 = r10
        L_0x013b:
            int r7 = r18 + 1
            r10 = r17
            r8 = r19
            r11 = r20
            goto L_0x0080
        L_0x0145:
            r10 = r6
            r11 = 1
            if (r10 == 0) goto L_0x0175
            r3 = 0
        L_0x014a:
            int r4 = r10.size()     // Catch:{ all -> 0x0178 }
            if (r3 >= r4) goto L_0x015c
            java.lang.Object r4 = r10.get(r3)     // Catch:{ all -> 0x0178 }
            android.support.v4.content.LocalBroadcastManager$ReceiverRecord r4 = (android.support.v4.content.LocalBroadcastManager.ReceiverRecord) r4     // Catch:{ all -> 0x0178 }
            r5 = 0
            r4.broadcasting = r5     // Catch:{ all -> 0x0178 }
            int r3 = r3 + 1
            goto L_0x014a
        L_0x015c:
            java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$BroadcastRecord> r3 = r1.mPendingBroadcasts     // Catch:{ all -> 0x0178 }
            android.support.v4.content.LocalBroadcastManager$BroadcastRecord r4 = new android.support.v4.content.LocalBroadcastManager$BroadcastRecord     // Catch:{ all -> 0x0178 }
            r4.<init>(r0, r10)     // Catch:{ all -> 0x0178 }
            r3.add(r4)     // Catch:{ all -> 0x0178 }
            android.os.Handler r0 = r1.mHandler     // Catch:{ all -> 0x0178 }
            boolean r0 = r0.hasMessages(r11)     // Catch:{ all -> 0x0178 }
            if (r0 != 0) goto L_0x0173
            android.os.Handler r0 = r1.mHandler     // Catch:{ all -> 0x0178 }
            r0.sendEmptyMessage(r11)     // Catch:{ all -> 0x0178 }
        L_0x0173:
            monitor-exit(r2)     // Catch:{ all -> 0x0178 }
            return r11
        L_0x0175:
            monitor-exit(r2)     // Catch:{ all -> 0x0178 }
            r0 = 0
            return r0
        L_0x0178:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0178 }
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.content.LocalBroadcastManager.sendBroadcast(android.content.Intent):boolean");
    }

    public void sendBroadcastSync(Intent intent) {
        if (sendBroadcast(intent)) {
            executePendingBroadcasts();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001d, code lost:
        if (r2 >= r1.length) goto L_0x0000;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001f, code lost:
        r3 = r1[r2];
        r4 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0028, code lost:
        if (r4 >= r3.receivers.size()) goto L_0x003e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002a, code lost:
        r3.receivers.get(r4).receiver.onReceive(r8.mAppContext, r3.intent);
        r4 = r4 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003e, code lost:
        r2 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001a, code lost:
        r2 = 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void executePendingBroadcasts() {
        /*
            r8 = this;
        L_0x0000:
            java.util.HashMap<android.content.BroadcastReceiver, java.util.ArrayList<android.content.IntentFilter>> r0 = r8.mReceivers
            monitor-enter(r0)
            java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$BroadcastRecord> r1 = r8.mPendingBroadcasts     // Catch:{ all -> 0x0041 }
            int r1 = r1.size()     // Catch:{ all -> 0x0041 }
            if (r1 > 0) goto L_0x000d
            monitor-exit(r0)     // Catch:{ all -> 0x0041 }
            return
        L_0x000d:
            android.support.v4.content.LocalBroadcastManager$BroadcastRecord[] r1 = new android.support.v4.content.LocalBroadcastManager.BroadcastRecord[r1]     // Catch:{ all -> 0x0041 }
            java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$BroadcastRecord> r2 = r8.mPendingBroadcasts     // Catch:{ all -> 0x0041 }
            r2.toArray(r1)     // Catch:{ all -> 0x0041 }
            java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$BroadcastRecord> r2 = r8.mPendingBroadcasts     // Catch:{ all -> 0x0041 }
            r2.clear()     // Catch:{ all -> 0x0041 }
            monitor-exit(r0)     // Catch:{ all -> 0x0041 }
            r0 = 0
            r2 = 0
        L_0x001c:
            int r3 = r1.length
            if (r2 >= r3) goto L_0x0000
            r3 = r1[r2]
            r4 = 0
        L_0x0022:
            java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$ReceiverRecord> r5 = r3.receivers
            int r5 = r5.size()
            if (r4 >= r5) goto L_0x003e
            java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$ReceiverRecord> r5 = r3.receivers
            java.lang.Object r5 = r5.get(r4)
            android.support.v4.content.LocalBroadcastManager$ReceiverRecord r5 = (android.support.v4.content.LocalBroadcastManager.ReceiverRecord) r5
            android.content.BroadcastReceiver r5 = r5.receiver
            android.content.Context r6 = r8.mAppContext
            android.content.Intent r7 = r3.intent
            r5.onReceive(r6, r7)
            int r4 = r4 + 1
            goto L_0x0022
        L_0x003e:
            int r2 = r2 + 1
            goto L_0x001c
        L_0x0041:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0041 }
            throw r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.content.LocalBroadcastManager.executePendingBroadcasts():void");
    }
}
