package pooig.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public enum ImageLoadingUtil {
    EMBLEM("emblem.png"),
    MASK("mask.png"),
    HIGHLIGHT("tiles/Null0.png"),
    LEFT("icons/left.png"),
    RIGHT("icons/right.png"),
    SKIP("icons/skip.png");

    private final String path;

    ImageLoadingUtil(String path) {
        this.path = path;
    }

    public BufferedImage createBufferedImage() {
        return createBufferedImage(path);
    }

    public static BufferedImage createBufferedImage(String path) {
        BufferedImage image = loadBufferedImage(path);
        return makeCompatible(image);
    }


    public ImageIcon createImageIcon(int size) {
        BufferedImage image = createBufferedImage();
        Image scaledImage = image.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public static ImageIcon createImageIcon(String path, int size) {
        BufferedImage image = loadBufferedImage(path);
        Image scaledImage = image.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public static BufferedImage makeCompatible(BufferedImage image) {
        return makeCompatibleImage(image, image.getWidth(), image.getHeight(), image.getTransparency());
    }

    private static BufferedImage makeCompatibleImage(Image image, int width, int height, int transparency) {
        GraphicsConfiguration configuration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage convertedImage = configuration.createCompatibleImage(width, height, transparency);
        Graphics2D graphics2D = convertedImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return convertedImage;
    }

    private static BufferedImage loadBufferedImage(String path) {
        try {
            return ImageIO.read(ImageLoadingUtil.class.getClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
