package image;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class ImageUtils {
    /**
     * 이미지 압축
     * @param image 원본 이미지
     * @param format 이미지 확장자
     * @param quality 압축 품질 0.0 ~ 1.0
     * @return 압축된 이미지 / 압축을 지원하지 않는 경우는 원본 이미지 그대로 리턴
     * @throws IOException 이미지 압축 과정에서 에러 발생
     */
    public static BufferedImage compressImage(BufferedImage image, String format, float quality) throws IOException {
        Iterator<ImageWriter> iterator = ImageIO.getImageWritersByFormatName(format);
        if (!iterator.hasNext()) {
            return image;
        }

        ImageWriter writer = iterator.next();
        ImageWriteParam param = writer.getDefaultWriteParam();
        if (!param.canWriteCompressed()) {
            return image;
        }

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);
        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageOutputStream os = ImageIO.createImageOutputStream(baos)
        ) {
            writer.setOutput(os);
            writer.write(null, new IIOImage(image, null, null), param);

            byte[] data = baos.toByteArray();
            writer.dispose();

            try (ByteArrayInputStream bis = new ByteArrayInputStream(data)) {
                return ImageIO.read(bis);
            }
        }
    }
}
