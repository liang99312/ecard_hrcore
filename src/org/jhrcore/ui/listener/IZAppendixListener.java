/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.ui.listener;

import com.foundercy.pf.control.table.FTable;
import java.util.List;

/*
 * �ӿ�������ʾӦƸ��Ա�������
 * @author��������
 * Created on 2013-3-21, 14:53:44
 */
public interface IZAppendixListener {

    //������������
    public void setObjects(String z_a01_key);

    //������ݵ�����
    public void addObjects(List list);

    //ɾ������ѡ����
    public void delSelectObjects();

    //��ȡ�������
    public FTable getFTable();

    //��ȡ��������class
    public Class getAppendixClass();
}
