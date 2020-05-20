package crawler;

import file.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipOutputStream;

public class CrawlerMain {
    private static final Logger logger = LoggerFactory.getLogger(CrawlerMain.class);

    public static void main(String[] args) {
        Path savePath = Paths.get("C:\\Users\\user\\Downloads", "download.zip");
        List<String> resourceUrlList = new ArrayList<>();

        try (WebDriverBase driverBase = new WebDriverBase()) {
            MediaCrawlingOptions options = new MediaCrawlingOptions("https://www.naver.com");
            options.setExtensions(Collections.singletonList("png"));

            AnchorImageCrawler crawler = new AnchorImageCrawler(driverBase, options) {
                @Override
                public void accept(AnchorImageCrawlingResult result) {
                    resourceUrlList.add(result.getAbsSrc());
                }
            };
            crawler.start();
        }

        createZip(resourceUrlList, savePath);
    }

    private static void createZip(List<String> urlList, Path savePath) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(savePath.toFile()))) {
            int i = 0;

            for (String url : urlList) {
                try {
                    URL resource = new URL(url);
                    InputStream is = resource.openStream();

                    String fileExtension = url.substring(url.indexOf('.', url.lastIndexOf('/') + 1));
                    FileUtils.addToZipFile(zos, is, i++ + fileExtension);
                } catch (IOException e) {
                    logger.error("파일 다운로드 실패 : {}", url, e);
                }
            }
        } catch (IOException e) {
            logger.error("파일 다운로드 실패", e);
        }
    }
}
