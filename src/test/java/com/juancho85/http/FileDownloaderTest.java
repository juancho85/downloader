package com.juancho85.http;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.juancho85.injection.DownloaderModule;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FileDownloaderTest {

    private Injector injector = Guice.createInjector(DownloaderModule.builder().build());

    private static final String SMALL_FILE_URL = "https://raw.githubusercontent.com/juancho85/downloader/master/src/test/resources/examples/csvs/books.csv";
    private static final String SMALL_FILE_NAME = "/tmp/csvs/books.csv";

    private static final String MEDIUM_FILE_URL = "https://raw.githubusercontent.com/juancho85/downloader/master/src/test/resources/examples/csvs/movies.csv";
    private static final String MEDIUM_FILE_NAME = "/tmp/csvs/movies.csv";

    private static final String LARGE_FILE_URL = "https://raw.githubusercontent.com/juancho85/downloader/master/src/test/resources/examples/csvs/ratings.csv";
    private static final String LARGE_FILE_NAME = "/tmp/csvs/ratings.csv";

    @ParameterizedTest
    @MethodSource("provideUrlsAndPaths")
    void downloadFileWithNio(String fileUrl, String fileName) {
        FileDownloader downloader = injector.getInstance(IoDownloader.class);
        assertTrue(downloader.downloadFile(fileUrl, fileName));
    }

    @ParameterizedTest
    @MethodSource("provideUrlsAndPaths")
    void downloadFileWithIo(String fileUrl, String fileName) {
        FileDownloader downloader = injector.getInstance(NioDownloader.class);
        assertTrue(downloader.downloadFile(fileUrl, fileName));
    }

    @ParameterizedTest
    @MethodSource("provideUrlsAndPaths")
    void downloadFileWithAsyncHttp(String fileUrl, String fileName) {
        FileDownloader downloader = injector.getInstance(AsyncDownloader.class);
        downloader.downloadFile(fileUrl, fileName);
    }

    private static Stream<Arguments> provideUrlsAndPaths() {
        return Stream.of(
                Arguments.of(SMALL_FILE_URL, SMALL_FILE_NAME),
                Arguments.of(MEDIUM_FILE_URL, MEDIUM_FILE_NAME),
                Arguments.of(LARGE_FILE_URL, LARGE_FILE_NAME)
        );
    }


}