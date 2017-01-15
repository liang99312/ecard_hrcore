package com.foundercy.pf.control;

import java.util.HashMap;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: ���������¸���</p>
 * @author not attributable
 * @version 1.0
 */

public class RefModelFactory {
	private static HashMap refModelHash = new HashMap();

	/**
	 * ���RefModel
	 * @param refmodel
	 * @return
	 */
	public static RefModel getRefModel(String refmodel) {
		if (refmodel == null)
			return null;
		if (refModelHash.containsKey(refmodel)) {
			return (RefModel) refModelHash.get(refmodel);
		} else {
			try {
				Class c = Class.forName(refmodel);
				RefModel model = (RefModel) c.newInstance();
				refModelHash.put(refmodel, model);
				return (RefModel) model;
			} catch (ClassNotFoundException ex) {
				System.out.print("����¼��ؼ���ָ��������ģ�����Ʋ��Ϸ��������ڸò���ģ���ࡣ");
			} catch (InstantiationException ex) {
				System.out.print("����¼��ؼ���ָ��������ģ�ͳ�ʼ�������쳣��");
			} catch (IllegalAccessException ex) {
				System.out.print("����¼��ؼ���ָ��������ģ�ͷǷ����ʡ�");
			}

		}
		return null;
	}

//	/**
//	 * ���RefModel
//	 * @param refmodel
//	 * @return
//	 */
//	public static RefModel getTypeCodeRefModel(Object type) {
//
//		if (type == null)
//			return null;
//		
//		String refmodel = "TypeCodeRefModel";
//		
//		String key = refmodel +"_" + type.toString().trim().toLowerCase();
//		
//		if (refModelHash.containsKey(key)) {
//			return (RefModel) refModelHash.get(key);
//		} else {
//			TypeCodeRefModel model = new TypeCodeRefModel(type);
//			refModelHash.put(key, model);
//			return (RefModel) model;
//		}
//	}

}