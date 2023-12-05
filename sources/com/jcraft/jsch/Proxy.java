package com.jcraft.jsch;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public interface Proxy {
    void close();

    void connect(SocketFactory socketFactory, String str, int i, int i2) throws Exception;

    InputStream getInputStream();

    OutputStream getOutputStream();

    Socket getSocket();
}
