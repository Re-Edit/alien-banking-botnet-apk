package com.jcraft.jsch;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public interface ServerSocketFactory {
    ServerSocket createServerSocket(int i, int i2, InetAddress inetAddress) throws IOException;
}
