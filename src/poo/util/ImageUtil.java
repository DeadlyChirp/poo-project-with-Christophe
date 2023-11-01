package poo.util;

import poo.model.tile.cc.TerrainType;
import poo.model.tile.cc.TileCc;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.HashMap;
import java.util.Map;

public final class ImageUtil {

    private static final BufferedImage MASK_IMAGE = ImageLoadingUtil.MASK.createBufferedImage();
    private static final BufferedImage HIGHLIGHT_IMAGE = ImageLoadingUtil.HIGHLIGHT.createBufferedImage();
    private static final BufferedImage EMBLEM_IMAGE = ImageLoadingUtil.EMBLEM.createBufferedImage();

    private static final int MAXIMAL_ALPHA = 255;

    private static final Map<TerrainType, BufferedImage> templateMap = buildMeepleImageMap(true);
    private static final Map<TerrainType, BufferedImage> imageMap = buildMeepleImageMap(false);

    private ImageUtil() {}

    public static ImageIcon getColoredHighlight(Color color, int size) {
        BufferedImage coloredImage = colorMaskBased(HIGHLIGHT_IMAGE, MASK_IMAGE, color);
        Image scaledImage = coloredImage.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public static ImageIcon getColoredMeeple(TerrainType meepleType, Color color, int size) {
        Image paintedMeeple = paintMeeple(meepleType, color.getRGB(), size);
        return new ImageIcon(paintedMeeple);
    }

    public static ImageIcon getPreviewMeeple(TerrainType meepleType, int size) {
        Image preview = imageMap.get(meepleType).getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(preview);
    }

    public static ImageIcon getTileImageIcon(TileCc tile, int size) {
        BufferedImage image = tile.getBufferedImage();
        BufferedImage emblemTile = addEmblem(image);
        Image scaledImage = emblemTile.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private static Image paintMeeple(TerrainType meepleType, int color, int size) {
        BufferedImage image = deepCopy(imageMap.get(meepleType));
        BufferedImage template = templateMap.get(meepleType);
        for (int x = 0; x < template.getWidth(); x++) {
            for (int y = 0; y < template.getHeight(); y++) {
                if (template.getRGB(x, y) == Color.BLACK.getRGB()) {
                    image.setRGB(x, y, color);
                }
            }
        }
        return image.getScaledInstance(size, size, Image.SCALE_SMOOTH);
    }

    private static BufferedImage addEmblem(BufferedImage originalTile) {
        BufferedImage copy = deepCopy(originalTile);
        for (int x = 0; x < EMBLEM_IMAGE.getWidth(); x++) {
            for (int y = 0; y < EMBLEM_IMAGE.getHeight(); y++) {
                Color emblemPixel = new Color(EMBLEM_IMAGE.getRGB(x, y), true);
                Color imagePixel = new Color(copy.getRGB(x, y), true);
                Color blendedColor = blend(imagePixel, emblemPixel, false);
                copy.setRGB(x, y, blendedColor.getRGB());
            }
        }

        return copy;
    }

    private static BufferedImage colorMaskBased(BufferedImage imageToColor, BufferedImage maskImage, Color targetColor) {
        BufferedImage image = deepCopy(imageToColor);
        for (int x = 0; x < maskImage.getWidth(); x++) {
            for (int y = 0; y < maskImage.getHeight(); y++) {
                Color maskPixel = new Color(maskImage.getRGB(x, y), true);
                Color targetPixel = new Color(targetColor.getRed(), targetColor.getGreen(), targetColor.getBlue(), maskPixel.getAlpha());
                Color imagePixel = new Color(image.getRGB(x, y), true);
                Color blendedColor = blend(imagePixel, targetPixel, true);
                image.setRGB(x, y, blendedColor.getRGB());
            }
        }
        return image;
    }

    private static BufferedImage deepCopy(BufferedImage image) {
        ColorModel model = image.getColorModel();
        boolean isAlphaPremultiplied = model.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(image.getRaster().createCompatibleWritableRaster());
        return ImageLoadingUtil.makeCompatible(new BufferedImage(model, raster, isAlphaPremultiplied, null));
    }

    private static Color blend(Color first, Color second, boolean blendEqually) {
        double totalAlpha = blendEqually ? first.getAlpha() + second.getAlpha() : MAXIMAL_ALPHA;
        double firstWeight = blendEqually ? first.getAlpha() : MAXIMAL_ALPHA - second.getAlpha();
        firstWeight /= totalAlpha;
        double secondWeight = second.getAlpha() / totalAlpha;
        double red = firstWeight * first.getRed() + secondWeight * second.getRed();
        double green = firstWeight * first.getGreen() + secondWeight * second.getGreen();
        double blue = firstWeight * first.getBlue() + secondWeight * second.getBlue();
        int alpha = Math.max(first.getAlpha(), second.getAlpha());
        return new Color((int) red, (int) green, (int) blue, alpha);
    }

    private static Map<TerrainType, BufferedImage> buildMeepleImageMap(boolean isTemplate) {
        Map<TerrainType, BufferedImage> map = new HashMap<>();
        for (TerrainType terrainType : TerrainType.values()) {
            BufferedImage meepleImage = ImageLoadingUtil.createBufferedImage(TileUtil.getMeeplePath(terrainType, isTemplate));
            map.put(terrainType, meepleImage);
        }
        return map;
    }
}
