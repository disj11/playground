package opencv.utils;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenCVUtils {
    public static Mat loadImage(String imagePath) {
        return Imgcodecs.imread(imagePath);
    }

    public static Mat toMat(BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        return Imgcodecs.imdecode(new MatOfByte(byteArrayOutputStream.toByteArray()), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
    }

    public static Mat toMat(String url) throws IOException {
        return toMat(getInputStream(url));
    }

    public static Mat toMat(InputStream is) throws IOException {
        BufferedImage image = ImageIO.read(is);
        return toMat(image);
    }

    public static byte[] toByteArray(Mat mat) {
        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(".png", mat, mob);
        return mob.toArray();
    }

    public static InputStream toInputStream(Mat mat) {
        return new ByteArrayInputStream(toByteArray(mat));
    }

    public static BufferedImage toBufferedImage(Mat mat) throws IOException {
        return ImageIO.read(toInputStream(mat));
    }

    private static InputStream getInputStream(String url) throws IOException {
        try (
                InputStream is = handleRedirects((url));
                ByteArrayOutputStream buffer = new ByteArrayOutputStream()
        ) {
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();
            byte[] bytes = buffer.toByteArray();
            return new ByteArrayInputStream(bytes);
        }
    }

    private static InputStream handleRedirects(String url) throws IOException {
        URL obj = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setInstanceFollowRedirects(false);
        conn.setRequestProperty("User-Agent", "curl/7.30.0");
        conn.connect();

        boolean redirect = false;
        int status = conn.getResponseCode();

        if (status != HttpURLConnection.HTTP_OK) {
            if (status == HttpURLConnection.HTTP_MOVED_TEMP
                    || status == HttpURLConnection.HTTP_MOVED_PERM
                    || status == HttpURLConnection.HTTP_SEE_OTHER)
                redirect = true;
        }

        if (redirect) {
            String newUrl = conn.getHeaderField("Location");
            conn.getInputStream().close();
            conn.disconnect();

            return handleRedirects(newUrl);
        }

        return conn.getInputStream();
    }

    public static void save(Mat image, String path) {
        Imgcodecs.imwrite(path, image);
    }
}
