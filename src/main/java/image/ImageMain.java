package image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ImageMain {
    public static void main(String[] args) {
        String format = "jpg";
        File source = Paths.get("C:\\Users\\user\\Downloads", "image." + format).toFile();
        File target = Paths.get("C:\\Users\\user\\Downloads", "image_compression." + format).toFile();

        try {
            BufferedImage sourceImage = ImageIO.read(source);
            BufferedImage compressionImage = ImageUtils.compressImage(sourceImage, format, 1f);

            System.out.println(sourceImage == compressionImage);
            ImageIO.write(compressionImage, format, target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
