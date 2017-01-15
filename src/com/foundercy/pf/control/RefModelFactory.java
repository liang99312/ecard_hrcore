package com.foundercy.pf.control;

import java.util.HashMap;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: 北京世纪新干线</p>
 * @author not attributable
 * @version 1.0
 */

public class RefModelFactory {
	private static HashMap refModelHash = new HashMap();

	/**
	 * 获得RefModel
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
				System.out.print("辅助录入控件所指定的数据模型名称不合法，不存在该参照模型类。");
			} catch (InstantiationException ex) {
				System.out.print("辅助录入控件所指定的数据模型初始化出现异常。");
			} catch (IllegalAccessException ex) {
				System.out.print("辅助录入控件所指定的数据模型非法访问。");
			}

		}
		return null;
	}

//	/**
//	 * 获得RefModel
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