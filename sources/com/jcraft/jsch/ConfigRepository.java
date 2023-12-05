package com.jcraft.jsch;

public interface ConfigRepository {
    public static final Config defaultConfig = new Config() {
        public String getHostname() {
            return null;
        }

        public int getPort() {
            return -1;
        }

        public String getUser() {
            return null;
        }

        public String getValue(String str) {
            return null;
        }

        public String[] getValues(String str) {
            return null;
        }
    };
    public static final ConfigRepository nullConfig = new ConfigRepository() {
        public Config getConfig(String str) {
            return defaultConfig;
        }
    };

    public interface Config {
        String getHostname();

        int getPort();

        String getUser();

        String getValue(String str);

        String[] getValues(String str);
    }

    Config getConfig(String str);
}
