package org.jhrcore.zui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import org.jhrcore.util.ImageUtil;
import org.jhrcore.util.SysUtil;

public class FreeUtil {

    public static final int DEFAULT_ICON_SIZE = 16;
    public static final int DEFAULT_BUTTON_SIZE = 20;
    public static final Insets ZERO_INSETS = new Insets(0, 0, 0, 0);
    public static final int LIST_SHRINKED_WIDTH = 37;
    public static final int OUTLOOK_SHRINKED_WIDTH = 37;
    public static final int DEFAULT_SPLIT_WIDTH = 4;
    public static final int TABLE_CELL_LEADING_SPACE = 5;
    public static final Color TABLE_HEADER_BACKGROUND_COLOR = new Color(239, 240, 241);
    public static final Color TABLE_HEADER_BORDER_BRIGHT_COLOR = Color.white;
    public static final Color TABLE_HEADER_BORDER_DARK_COLOR = new Color(215, 219, 223);
    public static final Color TABLE_ODD_ROW_COLOR = new Color(236, 242, 245);
    public static final Color TABLE_TEXT_COLOR = new Color(74, 74, 81);
    public static final Color TAB_BOTTOM_LINE_COLOR = new Color(167, 173, 175);
    public static final Color OUTLOOK_SPLIT_COLOR = new Color(174, 171, 162);
    public static final Color DEFAULT_TEXT_COLOR = new Color(37, 81, 54);
    public static final Color TASKCOLOR = new Color(234, 244, 254);//×Ó²Ëµ¥±³¾°É«
    public static final Font FONT_14_BOLD = new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14);
    public static final Font FONT_12_BOLD = new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12);

    public static Font getFontBySize(String fontType, String fontSize) {
        int size = SysUtil.objToInt(fontSize, 12);
        return new Font(fontSize, Font.PLAIN, size);
    }

    public static TexturePaint createTexturePaint(String imageURL) {
        return createTexturePaint(ImageUtil.getImage(imageURL));
    }

    public static TexturePaint createTexturePaint(Image image) {
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        BufferedImage bi = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return new TexturePaint(bi, new Rectangle(0, 0, imageWidth, imageHeight));
    }
}
