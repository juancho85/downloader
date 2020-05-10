package com.juancho85.http;

import com.juancho85.statistics.Timed;
import lombok.extern.log4j.Log4j2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

@Log4j2
public class NioDownloader implements FileDownloader {

    @Timed
    public boolean downloadFile(String fileUrl, String fileName) {
        try (ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(fileUrl).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            return true;
        } catch (IOException e) {
            log.error(e);
            return false;
        }

    }
}
