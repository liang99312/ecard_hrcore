/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.entity;

/**
 *
 * @author wangzhenhua
 * �ýӿ����ڶ�������һЩ�������ԣ�����ö����Ƿ�����������
 */
public interface IObjectAttribute {
    public static int EDITTYPE_NEW = 0;
    public static int EDITTYPE_EDIT = 1;
    
    public int editType();  // �༭���ͣ�0������ 1���޸�
}
