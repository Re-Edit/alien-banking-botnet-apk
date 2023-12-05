package com.jcraft.jsch;

import com.jcraft.jsch.ConfigRepository;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Hashtable;
import java.util.Vector;

public class OpenSSHConfig implements ConfigRepository {
    /* access modifiers changed from: private */
    public static final Hashtable keymap = new Hashtable();
    /* access modifiers changed from: private */
    public final Hashtable config = new Hashtable();
    /* access modifiers changed from: private */
    public final Vector hosts = new Vector();

    public static OpenSSHConfig parse(String str) throws IOException {
        StringReader stringReader = new StringReader(str);
        try {
            return new OpenSSHConfig(stringReader);
        } finally {
            stringReader.close();
        }
    }

    public static OpenSSHConfig parseFile(String str) throws IOException {
        FileReader fileReader = new FileReader(Util.checkTilde(str));
        try {
            return new OpenSSHConfig(fileReader);
        } finally {
            fileReader.close();
        }
    }

    OpenSSHConfig(Reader reader) throws IOException {
        _parse(reader);
    }

    private void _parse(Reader reader) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        String str = "";
        Vector vector = new Vector();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine != null) {
                String trim = readLine.trim();
                if (trim.length() != 0 && !trim.startsWith("#")) {
                    String[] split = trim.split("[= \t]", 2);
                    for (int i = 0; i < split.length; i++) {
                        split[i] = split[i].trim();
                    }
                    if (split.length > 1) {
                        if (split[0].equals("Host")) {
                            this.config.put(str, vector);
                            this.hosts.addElement(str);
                            str = split[1];
                            vector = new Vector();
                        } else {
                            vector.addElement(split);
                        }
                    }
                }
            } else {
                this.config.put(str, vector);
                this.hosts.addElement(str);
                return;
            }
        }
    }

    public ConfigRepository.Config getConfig(String str) {
        return new MyConfig(str);
    }

    static {
        keymap.put("kex", "KexAlgorithms");
        keymap.put("server_host_key", "HostKeyAlgorithms");
        keymap.put("cipher.c2s", "Ciphers");
        keymap.put("cipher.s2c", "Ciphers");
        keymap.put("mac.c2s", "Macs");
        keymap.put("mac.s2c", "Macs");
        keymap.put("compression.s2c", "Compression");
        keymap.put("compression.c2s", "Compression");
        keymap.put("compression_level", "CompressionLevel");
        keymap.put("MaxAuthTries", "NumberOfPasswordPrompts");
    }

    class MyConfig implements ConfigRepository.Config {
        private Vector _configs = new Vector();
        private String host;

        MyConfig(String str) {
            boolean z;
            this.host = str;
            this._configs.addElement(OpenSSHConfig.this.config.get(""));
            byte[] str2byte = Util.str2byte(str);
            if (OpenSSHConfig.this.hosts.size() > 1) {
                for (int i = 1; i < OpenSSHConfig.this.hosts.size(); i++) {
                    String[] split = ((String) OpenSSHConfig.this.hosts.elementAt(i)).split("[ \t]");
                    for (String trim : split) {
                        String trim2 = trim.trim();
                        if (trim2.startsWith("!")) {
                            trim2 = trim2.substring(1).trim();
                            z = true;
                        } else {
                            z = false;
                        }
                        if (Util.glob(Util.str2byte(trim2), str2byte)) {
                            if (!z) {
                                this._configs.addElement(OpenSSHConfig.this.config.get((String) OpenSSHConfig.this.hosts.elementAt(i)));
                            }
                        } else if (z) {
                            this._configs.addElement(OpenSSHConfig.this.config.get((String) OpenSSHConfig.this.hosts.elementAt(i)));
                        }
                    }
                }
            }
        }

        private String find(String str) {
            if (OpenSSHConfig.keymap.get(str) != null) {
                str = (String) OpenSSHConfig.keymap.get(str);
            }
            String upperCase = str.toUpperCase();
            String str2 = null;
            for (int i = 0; i < this._configs.size(); i++) {
                Vector vector = (Vector) this._configs.elementAt(i);
                int i2 = 0;
                while (true) {
                    if (i2 >= vector.size()) {
                        break;
                    }
                    String[] strArr = (String[]) vector.elementAt(i2);
                    if (strArr[0].toUpperCase().equals(upperCase)) {
                        str2 = strArr[1];
                        break;
                    }
                    i2++;
                }
                if (str2 != null) {
                    break;
                }
            }
            return str2;
        }

        private String[] multiFind(String str) {
            String str2;
            String upperCase = str.toUpperCase();
            Vector vector = new Vector();
            for (int i = 0; i < this._configs.size(); i++) {
                Vector vector2 = (Vector) this._configs.elementAt(i);
                for (int i2 = 0; i2 < vector2.size(); i2++) {
                    String[] strArr = (String[]) vector2.elementAt(i2);
                    if (strArr[0].toUpperCase().equals(upperCase) && (str2 = strArr[1]) != null) {
                        vector.remove(str2);
                        vector.addElement(str2);
                    }
                }
            }
            String[] strArr2 = new String[vector.size()];
            vector.toArray(strArr2);
            return strArr2;
        }

        public String getHostname() {
            return find("Hostname");
        }

        public String getUser() {
            return find("User");
        }

        public int getPort() {
            try {
                return Integer.parseInt(find("Port"));
            } catch (NumberFormatException unused) {
                return -1;
            }
        }

        public String getValue(String str) {
            if (!str.equals("compression.s2c") && !str.equals("compression.c2s")) {
                return find(str);
            }
            String find = find(str);
            return (find == null || find.equals("no")) ? "none,zlib@openssh.com,zlib" : "zlib@openssh.com,zlib,none";
        }

        public String[] getValues(String str) {
            return multiFind(str);
        }
    }
}
