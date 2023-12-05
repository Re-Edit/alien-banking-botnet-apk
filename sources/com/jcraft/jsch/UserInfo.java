package com.jcraft.jsch;

public interface UserInfo {
    String getPassphrase();

    String getPassword();

    boolean promptPassphrase(String str);

    boolean promptPassword(String str);

    boolean promptYesNo(String str);

    void showMessage(String str);
}
