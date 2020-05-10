package com.juancho85.injection;


import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import com.juancho85.http.*;
import com.juancho85.statistics.MonitorAspect;
import com.juancho85.statistics.Timed;
import lombok.Builder;

@Builder
public class DownloaderModule extends AbstractModule {

    private DownloaderType downloaderType;

    @Override
    protected void configure() {
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(Timed.class), new MonitorAspect());
        if(downloaderType == null) {
            downloaderType = DownloaderType.ASYNC;
        }
        switch(downloaderType) {
            case IO:
                bind(FileDownloader.class).to(IoDownloader.class);
                break;
            case NIO:
                bind(FileDownloader.class).to(NioDownloader.class);
                break;
            case ASYNC:
            default:
                bind(FileDownloader.class).to(AsyncDownloader.class);
        }
    }

}
