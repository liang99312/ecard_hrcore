/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.jhrcore.uimanager.ninepatch4j.NinePatch;

/**
 *
 * @author mxliteboss
 */
public class NinePatchHelper {

    public static NinePatch createNinePatch(URL fileUrl, boolean convert) {
        try {
            return NinePatch.load(fileUrl, convert);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static NinePatch createNinePatch(InputStream stream, boolean is9Patch, boolean convert)
            throws IOException {
        return NinePatch.load(stream, is9Patch, convert);
    }

    public static NinePatch createNinePatch(BufferedImage image, boolean is9Patch, boolean convert) {
        return NinePatch.load(image, is9Patch, convert);
    }
}
