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
 * <p>Title: �ӿؼ�������</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: ����������Ԫ</p>
 * @author a
 * @version $Revision: 1.1.1.1 $
 */

public class SubControlFinder {
	
	/**
	 * ��һ�����Ͽؼ���ͨ������id�ַ�����λ�ӿؼ�
	 * @param comp ���Ͽؼ�
	 * @param serialIdStr ����id�ַ���
	 * @return 
	 */
	public static Control getSubControlBySerialId(Compound comp, String serialIdStr) {
 
	    if (serialIdStr == null || serialIdStr.equals("")) return null;
	    
	    //�õ���һ��"."���±�
	    int firstDotIndex = serialIdStr.indexOf(".");
	    
	    String firstId = null;
	    String reminderIdStr = null;
	 
	    if (firstDotIndex >= 0) {
	    	//����ַ����д���".",��ȡ�õ�һ��"."ǰ��������ַ���
	    	firstId = serialIdStr.substring(0, firstDotIndex);
	    	reminderIdStr = serialIdStr.substring(firstDotIndex+1);
		} else {
			//����ַ����в�����".",���ʾ���ַ���Ϊ��һ�ؼ�Id,��ôֱ�ӷ��ش��ַ���ָ��id���ӿؼ�
			return getSubControlBySingleId(comp, serialIdStr);
	    }
	    
	    //�õ�id�ַ����е�һ��idָ�����ӿؼ�
	    Control firstControl = getSubControlBySingleId(comp, firstId);

	    Control subControl = null;
	    
	    //�õ��ڶ���id�ַ���,������ʣ���ַ���
	    String secondId = getStrBeforeFirstDot(reminderIdStr);
	    String secRminderIdStr = getStrAfterFirstDot(reminderIdStr);
	    
	    //���id�ַ����е�һ��idָ�����ӿؼ���Compound,�������Compound�в���id��ڶ����ַ�����ͬ���ӿؼ�
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
	    
	    //����ҵ���id��ڶ����ַ�����ͬ���ӿؼ�,��������ؼ���Compound����,
	    //���ҵڶ���id�ַ��������Ǵ˴��ַ����Ľ�β,���õݹ��������.
	    if (subControl != null && subControl instanceof Compound
	    	&& secRminderIdStr != null && !secRminderIdStr.equals("")) {
	       return getSubControlBySerialId((Compound) subControl, secRminderIdStr);
	    }

	    return subControl;
	}
	
	/**
	 * ��һ�����Ͽؼ���ͨ����һid��λ�ӿؼ�
	 * @param comp ���Ͽؼ�
	 * @param singleId ��һid�ַ���
	 * @return
	 */
	public static Control getSubControlBySingleId(Compound comp, String singleId) {
		
		if (singleId == null || singleId.equals("")) return null;
		
		Control result = null;
		
		//�ڸ��Ͽؼ�comp��ֱ���ӿؼ���Ѱ��id��singleId��ͬ���ӿؼ�,����ҵ��򷵻�.
		//����ڸ��Ͽؼ�comp��ֱ���ӿؼ���û���ҵ�id��singleId��ͬ���ӿؼ�,
	    //���õݹ��㷨,��comp������ֱ���ӿؼ����¼��ӿؼ��в���.
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
	 * ��һ������"."���ַ�����,�õ���һ��"."ǰ���ַ���
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
	 * ��һ������"."���ַ�����,�õ���һ��"."����ַ���
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
