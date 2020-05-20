package file;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 파일 처리 유틸
 */
public class FileUtils {
    public static void addToZipFile(ZipOutputStream zos, InputStream is, String filename) throws IOException {
        ZipEntry zipEntry = new ZipEntry(filename);
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;

        while ((length = is.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }

        zos.closeEntry();
        is.close();
    }
}
