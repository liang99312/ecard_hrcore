/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.entity;

/**
 *
 * @author wangzhenhua
 * 该接口用于定义对象的一些额外属性，比如该对象是否是新增对象
 */
public interface IObjectAttribute {
    public static int EDITTYPE_NEW = 0;
    public static int EDITTYPE_EDIT = 1;
    
    public int editType();  // 编辑类型，0：新增 1：修改
}
