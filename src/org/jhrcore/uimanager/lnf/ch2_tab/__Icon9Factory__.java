/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf.ch2_tab;

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

  public NinePatch getTabbedPaneBgSelected()
  {
    return getRaw("imgs/np/t1.9.png");
  }

  public NinePatch getTabbedPaneBgNormal()
  {
    return getRaw("imgs/np/t2.9.png");
  }

  public NinePatch getTabbedPaneBgNormal_rover()
  {
    return getRaw("imgs/np/tab_rover1.9.png");
  }
}