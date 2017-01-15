/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf.ch17_split;

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

  public NinePatch getSplitTouchBg1()
  {
    return getRaw("imgs/np/split_touch_bg1.9.png");
  }
}