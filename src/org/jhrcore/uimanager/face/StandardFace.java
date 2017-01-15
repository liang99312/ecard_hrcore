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
public class StandardFace implements UIFace {

    @Override
    public Color getIndexToolForegound() {
        return Color.BLACK;
    }

    public Icon getMyHelpIcon() {
        return ImageUtil.getIcon("hr_myhelp1.png");
    }

    public Icon getHelpIcon() {
        return ImageUtil.getIcon("hr_help1.png");
    }

    public Icon getAboutIcon() {
        return ImageUtil.getIcon("hr_about1.png");
    }

    public Icon getIndexIcon() {
        return ImageUtil.getIcon("hr_index1.png");
    }

    public Icon getQuitIcon() {
        return ImageUtil.getIcon("hr_loginout1.png");
    }

    public InputStream getTopBackIconStream() {
        return ImageUtil.class.getResourceAsStream("/resources/images/hr_top_back.png");
    }

    public InputStream getTopTitleIconStream() {
        return ImageUtil.class.getResourceAsStream("/resources/images/hr_top_title.png");
    }

    @Override
    public Icon getLogoIcon() {
        return ImageUtil.getIcon("hr_logo.png");
    }

    @Override
    public Image getSmallLogo() {
        return ImageUtil.getImage("frame_icon.png").getScaledInstance(30, 30, Image.SCALE_DEFAULT);
    }

    public String toString() {
        return "æ≠µ‰”¿∫„";
    }
}
