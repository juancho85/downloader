package com.juancho85.http;

import com.juancho85.statistics.Timed;
import lombok.extern.log4j.Log4j2;
import org.asynchttpclient.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

@Log4j2
public class AsyncDownloader implements FileDownloader{

    @Timed
    public boolean downloadFile(String fileUrl, String fileName) {
        try(FileChannel channel = new FileOutputStream(fileName).getChannel();
            AsyncHttpClient client = Dsl.asyncHttpClient()) {
            client.prepareGet(fileUrl)
                    .setFollowRedirect(true)
                    .execute(new AsyncFileCompletionHandler(channel))
                    .toCompletableFuture()
                    .join();
            return true;
        } catch (IOException e) {
            log.error(e);
            return false;
        }
    }
}
