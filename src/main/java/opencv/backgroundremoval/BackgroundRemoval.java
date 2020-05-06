package opencv.backgroundremoval;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * grabcut을 사용한 백그라운드 제거 유틸리티 클래스
 * OpenCV를 제외한 다른 라이브러리 사용 금지
 */
public class BackgroundRemoval {
    public static void remove(Mat src, Mat dest) {
        // 이미지 복사
        src.copyTo(dest);
        convertBGRA2BGR(dest);

        // 회색조 이미지로 변경
        Mat grayImage = new Mat();
        Imgproc.cvtColor(dest, grayImage, Imgproc.COLOR_BGR2GRAY);

        // Closing
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Imgproc.morphologyEx(grayImage, grayImage, Imgproc.MORPH_CLOSE, kernel);

        // 노이즈 제거
        Imgproc.blur(grayImage, grayImage, new Size(5, 5));

        // Canny
        double threshold = 25;
        Mat detectedEdges = new Mat();
        Imgproc.Canny(dest, detectedEdges, threshold, threshold * 3, 3, false);

        // 팽창
        Imgproc.dilate(detectedEdges, detectedEdges, new Mat(), new Point(-1, -1), 3);

        // 오브젝트 외곽선 구하기
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(detectedEdges, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // 모든 영역을 백그라운드(Imgproc.GC_BGD)로 설정
        Mat mask = new Mat(dest.size(), CvType.CV_8U, new Scalar(Imgproc.GC_BGD, Imgproc.GC_BGD, Imgproc.GC_BGD));

        // 발견된 오브젝트를 포그라운드 추정(Imgproc.GC_PR_FGD)으로 설정
        Imgproc.fillPoly(mask, contours, new Scalar(Imgproc.GC_PR_FGD, Imgproc.GC_PR_FGD, Imgproc.GC_PR_FGD));

        Mat bgdModel = new Mat();
        Mat fgdModel = new Mat();
        try {
            Imgproc.grabCut(dest, mask, new Rect(), bgdModel, fgdModel, 3, Imgproc.GC_INIT_WITH_MASK);
            grabcutMask(dest, mask);
            makeBlackTransparent(dest, dest);
        } catch (CvException e) {
            // 마스크가 제대로 안구해지면 익셉션 남...
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static void grabcutMask(Mat src, Mat mask) {
        for (int i = 0; i < src.rows(); i++) {
            for (int j = 0; j < src.cols(); j++) {
                if (mask.get(i, j)[0] == Imgproc.GC_BGD || mask.get(i, j)[0] == Imgproc.GC_PR_BGD) {
                    src.put(i, j, 0, 0, 0);
                }
            }
        }
    }

    private static void makeBlackTransparent(Mat src, Mat dest) {
        Mat dst = new Mat(src.width(), src.height(), CvType.CV_8UC4);
        Mat tmp = new Mat(src.width(), src.height(), CvType.CV_8UC4);
        Mat alpha = new Mat(src.width(), src.height(), CvType.CV_8UC4);

        Imgproc.cvtColor(src, tmp, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(tmp, alpha, 0, 255, Imgproc.THRESH_BINARY);

        List<Mat> rgb = new ArrayList<>(3);
        Core.split(src, rgb);

        List<Mat> rgba = new ArrayList<>(4);
        rgba.addAll(rgb);
        rgba.add(alpha);
        Core.merge(rgba, dst);

        dst.copyTo(dest);
    }

    private static void convertBGRA2BGR(Mat src) {
        if (src.channels() > 3) {
            Imgproc.cvtColor(src, src, Imgproc.COLOR_BGRA2BGR);
        }
    }
}
