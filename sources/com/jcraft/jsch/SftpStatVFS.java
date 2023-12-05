package com.jcraft.jsch;

import android.support.v4.media.session.PlaybackStateCompat;

public class SftpStatVFS {
    int atime;
    private long bavail;
    private long bfree;
    private long blocks;
    private long bsize;
    String[] extended = null;
    private long favail;
    private long ffree;
    private long files;
    private long flag;
    int flags = 0;
    private long frsize;
    private long fsid;
    int gid;
    int mtime;
    private long namemax;
    int permissions;
    long size;
    int uid;

    private SftpStatVFS() {
    }

    static SftpStatVFS getStatVFS(Buffer buffer) {
        SftpStatVFS sftpStatVFS = new SftpStatVFS();
        sftpStatVFS.bsize = buffer.getLong();
        sftpStatVFS.frsize = buffer.getLong();
        sftpStatVFS.blocks = buffer.getLong();
        sftpStatVFS.bfree = buffer.getLong();
        sftpStatVFS.bavail = buffer.getLong();
        sftpStatVFS.files = buffer.getLong();
        sftpStatVFS.ffree = buffer.getLong();
        sftpStatVFS.favail = buffer.getLong();
        sftpStatVFS.fsid = buffer.getLong();
        int i = (int) buffer.getLong();
        sftpStatVFS.namemax = buffer.getLong();
        long j = 0;
        sftpStatVFS.flag = (i & 1) != 0 ? 1 : 0;
        long j2 = sftpStatVFS.flag;
        if ((i & 2) != 0) {
            j = 2;
        }
        sftpStatVFS.flag = j2 | j;
        return sftpStatVFS;
    }

    public long getBlockSize() {
        return this.bsize;
    }

    public long getFragmentSize() {
        return this.frsize;
    }

    public long getBlocks() {
        return this.blocks;
    }

    public long getFreeBlocks() {
        return this.bfree;
    }

    public long getAvailBlocks() {
        return this.bavail;
    }

    public long getINodes() {
        return this.files;
    }

    public long getFreeINodes() {
        return this.ffree;
    }

    public long getAvailINodes() {
        return this.favail;
    }

    public long getFileSystemID() {
        return this.fsid;
    }

    public long getMountFlag() {
        return this.flag;
    }

    public long getMaximumFilenameLength() {
        return this.namemax;
    }

    public long getSize() {
        return (getFragmentSize() * getBlocks()) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
    }

    public long getUsed() {
        return (getFragmentSize() * (getBlocks() - getFreeBlocks())) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
    }

    public long getAvailForNonRoot() {
        return (getFragmentSize() * getAvailBlocks()) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
    }

    public long getAvail() {
        return (getFragmentSize() * getFreeBlocks()) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
    }

    public int getCapacity() {
        return (int) (((getBlocks() - getFreeBlocks()) * 100) / getBlocks());
    }
}
