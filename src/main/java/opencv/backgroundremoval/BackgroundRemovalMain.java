package opencv.backgroundremoval;

import nu.pattern.OpenCV;
import opencv.utils.OpenCVUtils;
import org.opencv.core.Mat;

public class BackgroundRemovalMain {
    public static void main(String[] args) {
        // Initialize the OpenCV library
        OpenCV.loadShared();

        String imagePath = "C:\\Users\\user\\Downloads\\image.png";
        String savePath = "C:\\Users\\user\\Downloads\\image_bg_remove.png";

        Mat source = OpenCVUtils.loadImage(imagePath);
        Mat dest = new Mat();
        BackgroundRemoval.remove(source, dest);

        OpenCVUtils.save(dest, savePath);
    }
}
