package com.juancho85.http;

import com.juancho85.statistics.Timed;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

@Log4j2
public class IoDownloader implements FileDownloader{

    @Timed
    public boolean downloadFile(String fileUrl, String fileName) {
        try (BufferedInputStream in = new BufferedInputStream(new URL(fileUrl).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            return true;
        } catch (IOException e) {
            log.error(e);
            return false;
        }
    }
}
