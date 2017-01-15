/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf.ch22_checkbox;

import javax.swing.ImageIcon;
import org.jhrcore.uimanager.lnf.utils.RawCache;

/**
 *
 * @author mxliteboss
 */
public class __IconFactory__ extends RawCache<ImageIcon>
{
  public static final String IMGS_ROOT = "imgs";
  private static __IconFactory__ instance = null;

  public static __IconFactory__ getInstance()
  {
    if (instance == null)
      instance = new __IconFactory__();
    return instance;
  }

  protected ImageIcon getResource(String relativePath, Class baseClass)
  {
    return new ImageIcon(baseClass.getResource(relativePath));
  }

  public ImageIcon getImage(String relativePath)
  {
    return (ImageIcon)getRaw(relativePath, getClass());
  }

  public ImageIcon getRadioButtonIcon_disable()
  {
    return getImage("imgs/rb_disable.png");
  }

  public ImageIcon getRadioButtonIcon_normal()
  {
    return getImage("imgs/rb_normal.png");
  }

  public ImageIcon getRadioButtonIcon_pressed()
  {
    return getImage("imgs/rb_pressed.png");
  }

  public ImageIcon getRadioButtonIcon_unchecked_disable()
  {
    return getImage("imgs/rb_un_disable.png");
  }

  public ImageIcon getRadioButtonIcon_unchecked_normal()
  {
    return getImage("imgs/rb_un_normal.png");
  }

  public ImageIcon getRadioButtonIcon_unchecked_pressed()
  {
    return getImage("imgs/rb_un_pressed.png");
  }

  public ImageIcon getCheckBoxButtonIcon_disable()
  {
    return getImage("imgs/cb_disable.png");
  }

  public ImageIcon getCheckBoxButtonIcon_normal()
  {
    return getImage("imgs/cb_normal.png");
  }

  public ImageIcon getCheckBoxButtonIcon_pressed()
  {
    return getImage("imgs/cb_pressed.png");
  }

  public ImageIcon getCheckBoxButtonIcon_unchecked_disable()
  {
    return getImage("imgs/cb_un_disable.png");
  }

  public ImageIcon getCheckBoxButtonIcon_unchecked_normal()
  {
    return getImage("imgs/cb_un_normal.png");
  }

  public ImageIcon getCheckBoxButtonIcon_unchecked_pressed()
  {
    return getImage("imgs/cb_un_pressed.png");
  }
}