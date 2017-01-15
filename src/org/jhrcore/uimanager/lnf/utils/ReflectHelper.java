/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf.utils;

import java.lang.reflect.Method;
import java.util.Arrays;
import org.jhrcore.uimanager.lnf.BeautyEyeLNFHelper;

/**
 *
 * @author mxliteboss
 */
public class ReflectHelper {

    public static Object invokeStaticMethod(String theClassFullName, String methodName, Class[] paramsType, Object[] paramsValue) {
        return invokeStaticMethod(getClass(theClassFullName), methodName, paramsType, paramsValue);
    }

    public static Object invokeStaticMethod(Class theClass, String methodName, Class[] paramsType, Object[] paramsValue) {
        return invokeMethod(theClass, theClass, methodName, paramsType, paramsValue);
    }

    public static Object invokeMethod(String theClassFullName, Object theObject, String methodName, Class[] paramsType, Object[] paramsValue) {
        Class theClass = getClass(theClassFullName);

        return invokeMethod(theClass, theClass, methodName, paramsType, paramsValue);
    }

    public static Object invokeMethod(Class theClass, Object theObject, String methodName, Class[] paramsType, Object[] paramsValue) {
        Object ret = null;
        if (theClass != null) {
            try {
                Method m = theClass.getMethod(methodName, paramsType);
                ret = m.invoke(theObject, paramsValue);
            } catch (Exception e) {
//                if (BeautyEyeLNFHelper.debug) {
//                    e.printStackTrace();
//                }
//                LogHelper.error("ͨ��������÷���" + theClass.getName() + "." + methodName
//                        + "(" + Arrays.toString(paramsType) + ")ʧ�ܣ�����JRE�汾������Ҫ���£�" + e.getMessage());
            }
        }
        return ret;
    }

    public static Class getClass(String className) {
        try {
            return Class.forName(className);
        } catch (Exception e) {
//            LogHelper.error("ͨ��������" + className + "��Class����ʧ�ܣ�����JRE�汾������Ҫ���£�" + e.getMessage());
        }
        return null;
    }
}
