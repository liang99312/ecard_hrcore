/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.face;

import java.awt.Color;
import java.awt.Image;
import java.io.InputStream;
import javax.swing.Icon;
import org.jhrcore.util.ImageUtil;

/**
 *
 * @author mxliteboss
 */
public class BlueFace implements UIFace {

    @Override
    public Color getIndexToolForegound() {
        return new Color(255, 255, 255);
    }

    public Icon getMyHelpIcon() {
        return ImageUtil.getIcon("nav1.gif");
    }

    public Icon getHelpIcon() {
        return ImageUtil.getIcon("nav3.gif");
    }

    public Icon getAboutIcon() {
        return ImageUtil.getIcon("nav4.gif");
    }

    public Icon getIndexIcon() {
        return ImageUtil.getIcon("nav2.gif");
    }

    public Icon getQuitIcon() {
        return ImageUtil.getIcon("nav5.gif");
    }

    public InputStream getTopBackIconStream() {
        return ImageUtil.class.getResourceAsStream("/resources/images/bannerleft.jpg");
    }

    public InputStream getTopTitleIconStream() {
        return ImageUtil.class.getResourceAsStream("");
    }

    @Override
    public Icon getLogoIcon() {
        return ImageUtil.getIcon("logo.png");
    }

    @Override
    public Image getSmallLogo() {
        return ImageUtil.getImage("frame_icon.png");
    }

    public String toString() {
        return "—§¿∂”’ªÛ";
    }
}
