/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf.ch3_button;

import org.jhrcore.uimanager.lnf.utils.NinePatchHelper;
import org.jhrcore.uimanager.lnf.utils.RawCache;
import org.jhrcore.uimanager.ninepatch4j.NinePatch;

/**
 *
 * @author mxliteboss
 */
public class __Icon9Factory__ extends RawCache<NinePatch>
{
  public static final String IMGS_ROOT = "imgs/np";
  private static __Icon9Factory__ instance = null;

  public static __Icon9Factory__ getInstance()
  {
    if (instance == null)
      instance = new __Icon9Factory__();
    return instance;
  }

  protected NinePatch getResource(String relativePath, Class baseClass)
  {
    return NinePatchHelper.createNinePatch(baseClass.getResource(relativePath), false);
  }

  public NinePatch getRaw(String relativePath)
  {
    return (NinePatch)getRaw(relativePath, getClass());
  }

  public NinePatch getButtonIcon_NormalGreen()
  {
    return getRaw("imgs/np/btn_special_default.9.png");
  }

  public NinePatch getButtonIcon_NormalGray()
  {
    return getRaw("imgs/np/btn_general_default.9.png");
  }

  public NinePatch getButtonIcon_DisableGray()
  {
    return getRaw("imgs/np/btn_special_disabled.9.png");
  }

  public NinePatch getButtonIcon_PressedOrange()
  {
    return getRaw("imgs/np/btn_general_pressed.9.png");
  }

  public NinePatch getButtonIcon_rover()
  {
    return getRaw("imgs/np/btn_general_rover.9.png");
  }

  public NinePatch getButtonIcon_NormalLightBlue()
  {
    return getRaw("imgs/np/btn_special_lightblue.9.png");
  }

  public NinePatch getButtonIcon_NormalRed()
  {
    return getRaw("imgs/np/btn_special_red.9.png");
  }

  public NinePatch getButtonIcon_NormalBlue()
  {
    return getRaw("imgs/np/btn_special_blue.9.png");
  }

  public NinePatch getToggleButtonIcon_CheckedGreen()
  {
    return getRaw("imgs/np/toggle_button_selected.9.png");
  }

  public NinePatch getToggleButtonIcon_RoverGreen()
  {
    return getRaw("imgs/np/toggle_button_rover.9.png");
  }
}