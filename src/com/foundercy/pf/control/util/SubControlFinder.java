/*
 * $Id: SubControlFinder.java,v 1.1.1.1 2009/04/07 08:12:35 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control.util;

import java.util.List;

import com.foundercy.pf.control.Compound;
import com.foundercy.pf.control.Control;

/**
 * <p>Title: 子控件查找类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: 北京方正春元</p>
 * @author a
 * @version $Revision: 1.1.1.1 $
 */

public class SubControlFinder {
	
	/**
	 * 在一个复合控件中通过级联id字符串定位子控件
	 * @param comp 复合控件
	 * @param serialIdStr 级联id字符串
	 * @return 
	 */
	public static Control getSubControlBySerialId(Compound comp, String serialIdStr) {
 
	    if (serialIdStr == null || serialIdStr.equals("")) return null;
	    
	    //得到第一个"."的下标
	    int firstDotIndex = serialIdStr.indexOf(".");
	    
	    String firstId = null;
	    String reminderIdStr = null;
	 
	    if (firstDotIndex >= 0) {
	    	//如果字符串中存在".",则取得第一个"."前后的两个字符串
	    	firstId = serialIdStr.substring(0, firstDotIndex);
	    	reminderIdStr = serialIdStr.substring(firstDotIndex+1);
		} else {
			//如果字符串中不存在".",则表示此字符串为单一控件Id,那么直接返回此字符串指定id的子控件
			return getSubControlBySingleId(comp, serialIdStr);
	    }
	    
	    //得到id字符串中第一个id指定的子控件
	    Control firstControl = getSubControlBySingleId(comp, firstId);

	    Control subControl = null;
	    
	    //得到第二个id字符串,及其后的剩余字符串
	    String secondId = getStrBeforeFirstDot(reminderIdStr);
	    String secRminderIdStr = getStrAfterFirstDot(reminderIdStr);
	    
	    //如果id字符串中第一个id指定的子控件是Compound,则在这个Compound中查找id与第二个字符串相同的子控件
	    if (firstControl != null && firstControl instanceof Compound) {
	    	Control tempSubCtrl = null;
		    List subCtrls = ((Compound) firstControl).getSubControls();
		    for (int i = 0; subCtrls != null && i < subCtrls.size(); i++) {
		    	tempSubCtrl = (Control)subCtrls.get(i);
		        if (tempSubCtrl.getId() != null && tempSubCtrl.getId().equals(secondId)) {
		        	subControl = tempSubCtrl;
		        	break;
		        }
		    }
	    }
	    
	    //如果找到了id与第二个字符串相同的子控件,并且这个控件是Compound类型,
	    //并且第二个id字符串并不是此大字符串的结尾,则用递归继续查找.
	    if (subControl != null && subControl instanceof Compound
	    	&& secRminderIdStr != null && !secRminderIdStr.equals("")) {
	       return getSubControlBySerialId((Compound) subControl, secRminderIdStr);
	    }

	    return subControl;
	}
	
	/**
	 * 在一个复合控件中通过单一id定位子控件
	 * @param comp 复合控件
	 * @param singleId 单一id字符串
	 * @return
	 */
	public static Control getSubControlBySingleId(Compound comp, String singleId) {
		
		if (singleId == null || singleId.equals("")) return null;
		
		Control result = null;
		
		//在复合控件comp的直接子控件中寻找id与singleId相同的子控件,如果找到则返回.
		//如果在复合控件comp的直接子控件中没有找到id与singleId相同的子控件,
	    //则用递归算法,到comp的所有直接子控件的下级子控件中查找.
		List subCtrls = comp.getSubControls();
	    for (int i = 0; subCtrls != null && i < subCtrls.size(); i++) {	 
            if(singleId.equals(((Control)subCtrls.get(i)).getId())) 
            { 
                 result = (Control)subCtrls.get(i); 
                 break; 
            } 
            else if(subCtrls.get(i) instanceof Compound) 
            { 
                 result = getSubControlBySingleId((Compound)subCtrls.get(i),singleId); 
                 if(result != null) return result; 
            } 

	    }
		
		return result;

	}
	
	/**
	 * 在一个包含"."的字符串中,得到第一个"."前的字符串
	 * @param strIncludedDot
	 * @return
	 */
	private static String getStrBeforeFirstDot(String strIncludedDot) {
		if (strIncludedDot == null || strIncludedDot.equals("")) return null;
		
		int firstDotIndex = strIncludedDot.indexOf(".");
	 
	    if (firstDotIndex >= 0) 
	    	return  strIncludedDot.substring(0, firstDotIndex);
	    
	    return strIncludedDot;
	}
	/**
	 * 在一个包含"."的字符串中,得到第一个"."后的字符串
	 * @param strIncludedDot
	 * @return
	 */
	private static String getStrAfterFirstDot(String strIncludedDot) {
		if (strIncludedDot == null || strIncludedDot.equals("")) return null;
		
		int firstDotIndex = strIncludedDot.indexOf(".");
	 
	    if (firstDotIndex >= 0) 
	    	return strIncludedDot.substring(firstDotIndex+1);
	    
	    return null;
	}

}
