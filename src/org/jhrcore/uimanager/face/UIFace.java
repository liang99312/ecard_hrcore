/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.face;

import java.awt.Color;
import java.awt.Image;
import java.io.InputStream;
import javax.swing.Icon;

/**
 *
 * @author mxliteboss
 */
public interface UIFace {

    public Color getIndexToolForegound();

    public Icon getMyHelpIcon();

    public Icon getHelpIcon();

    public Icon getAboutIcon();

    public Icon getIndexIcon();

    public Icon getQuitIcon();

    public InputStream getTopBackIconStream();

    public InputStream getTopTitleIconStream();

    public Icon getLogoIcon();

    public Image getSmallLogo();
}
