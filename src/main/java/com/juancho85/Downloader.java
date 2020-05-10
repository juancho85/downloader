package com.juancho85;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.juancho85.http.DownloaderType;
import com.juancho85.http.FileDownloader;
import com.juancho85.injection.DownloaderModule;
import lombok.extern.log4j.Log4j2;
import picocli.CommandLine;

import java.util.concurrent.Callable;

import static picocli.CommandLine.*;

@Command(description = "benchmark downloading techniques",
        name = "com.juancho85.downloader",
        mixinStandardHelpOptions = true,
        version = "com.juancho85.downloader 1.0")
@Log4j2
class Downloader implements Callable<Integer> {

    @Parameters(index = "0", description = "The url to download the file.")
    private String downloadUrl;

    @Option(names = {"-o", "--output-path"}, description = "Output path")
    private String outputPath;

    @Option(names = {"-t", "--type-downloader"}, description = "Downloader type: ASYNC, IO, NIO")
    private DownloaderType downloaderType = DownloaderType.ASYNC;

    public static void main(String... args) throws Exception {
        int exitCode = new CommandLine(new Downloader()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        final Injector injector = Guice.createInjector(DownloaderModule.builder()
                .downloaderType(downloaderType)
                .build()
        );

        final FileDownloader fileDownloader = injector.getInstance(FileDownloader.class);
        fileDownloader.downloadFile(downloadUrl, outputPath);
        return 0;
    }

}
