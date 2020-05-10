package com.juancho85.http;

import lombok.extern.log4j.Log4j2;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.Response;

import java.nio.channels.FileChannel;

@Log4j2
public class AsyncFileCompletionHandler extends AsyncCompletionHandler<FileChannel> {

    private FileChannel fileChannel;

    public AsyncFileCompletionHandler(FileChannel fileChannel) {
        this.fileChannel = fileChannel;
    }

    @Override
    public State onBodyPartReceived(HttpResponseBodyPart bodyPart)
            throws Exception {
        fileChannel.write(bodyPart.getBodyByteBuffer());
        return State.CONTINUE;
    }

    @Override
    public FileChannel onCompleted(Response response)
            throws Exception {
        fileChannel.close();
        return fileChannel;
    }
}
