/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.jhrcore.entity.SysParameter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.apache.log4j.Logger;
import org.jhrcore.client.CommUtil;
import org.jhrcore.iservice.impl.RSImpl;

/**
 *
 * @author yangzhou
 */
public class TransferAccessory {

    private static Logger log = Logger.getLogger(TransferAccessory.class.getName());

    public static void deletePicture(String pic_path) {
        RSImpl.deletePicture(pic_path);
    }

    public static BufferedImage downloadPicture(String pic_path) {
        byte[] imgbyte = RSImpl.downloadPicture(pic_path);
        if (imgbyte == null || imgbyte.length == 0) {
            return null;
        }
        InputStream is = new ByteArrayInputStream(imgbyte);
        BufferedImage origImage = null;
        try {
            origImage = ImageIO.read(is);
        } catch (IOException e1) {
            log.error(e1);
        }
        return origImage;
    }

    public static void uploadPicture(File file, String pic_path) {
        byte[] buffer = null;
        try {
            if (file != null) {
                buffer = new byte[(int) file.length()];
                BufferedInputStream input = new BufferedInputStream(
                        new FileInputStream(file));
                input.read(buffer, 0, buffer.length);
                input.close();
            }
            RSImpl.uploadPicture(buffer, pic_path);
        } catch (Exception e) {
            log.error(e);
        }
    }

    public static void uploadPicture(File file, String dept_code, String a0190) {
        uploadPicture(file, dept_code + "/" + a0190 + file.getName().substring(file.getName().lastIndexOf(".")));
    }

    /**
     * 该方法用于根据指定文件获得指定宽度*高宽的Icon
     * @param fm：文件
     * @param width：宽度
     * @param height：高度
     * @return:Icon
     */
    public static Icon getIconFromFile(File fm, int width, int height) {
        BufferedInputStream input = null;
        try {
            byte[] buffer = new byte[(int) fm.length()];
            input = new BufferedInputStream(new FileInputStream(fm));
            input.read(buffer, 0, buffer.length);
            input.close();
            InputStream is = new ByteArrayInputStream(buffer);
            return getIconFromInputStream(is, width, height);
        } catch (Exception e) {
            log.error(e);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ex) {
                log.error(ex);
            }
        }
        return null;
    }

    /**
     *  该方法用于根据指定Image获得指定宽度*高宽的Icon
     * @param image
     * @param width:宽度
     * @param height：高度
     * @return:Icon
     */
    public static Icon getIconFromBufferImage(BufferedImage image, int width, int height) {
        Icon tmpIcon = null;
        if (image != null) {
            tmpIcon = new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_DEFAULT));
        }
        return tmpIcon;
    }

    public static Icon getIconFromInputStream(InputStream is, int width, int height) {
        BufferedImage origImage = null;
        try {
            origImage = ImageIO.read(is);
        } catch (IOException e1) {
            log.error(e1);
        }
        return getIconFromBufferImage(origImage, width, height);
    }

    public static int checkPic(File file) {
        SysParameter sp = (SysParameter) CommUtil.fetchEntityBy("from SysParameter sp where sp.sysParameter_key='Emp_pic_size'");
        int pic_size = 300;
        if (sp != null) {
            pic_size = SysUtil.objToInt(sp.getSysparameter_value());
            pic_size = (pic_size < 0 && pic_size != -1) ? 0 : pic_size;
        }
        if (pic_size != -1) {
            if ((file.length() / 1024) > pic_size) {
                return pic_size;//图片太大
            }
        }
        if (!(file.getName().toLowerCase().endsWith("jpg")
                || file.getName().toLowerCase().endsWith("png") || file.getName().toLowerCase().endsWith("gif") || file.getName().toLowerCase().endsWith("bmp"))) {
            return -2;//图片格式不对
        }
        return -1;
    }
}
