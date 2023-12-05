package com.jcraft.jsch;

import java.io.InputStream;
import java.io.OutputStream;

public interface ForwardedTCPIPDaemon extends Runnable {
    void setArg(Object[] objArr);

    void setChannel(ChannelForwardedTCPIP channelForwardedTCPIP, InputStream inputStream, OutputStream outputStream);
}
