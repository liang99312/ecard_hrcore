/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf.utils;

import java.util.HashMap;

/**
 *
 * @author mxliteboss
 */
public abstract class RawCache<T> {

    private HashMap<String, T> rawCache = new HashMap();

    public T getRaw(String relativePath, Class baseClass) {
        T ic = null;

        String key = relativePath + baseClass.getCanonicalName();
        if (this.rawCache.containsKey(key)) {
            ic = this.rawCache.get(key);
        } else {
            try {
                ic = getResource(relativePath, baseClass);
                this.rawCache.put(key, ic);
            } catch (Exception e) {
                System.out.println("取本地磁盘资源文件出错,path=" + key + "," + e.getMessage());
                e.printStackTrace();
            }
        }
        return ic;
    }

    protected abstract T getResource(String paramString, Class paramClass);
}
