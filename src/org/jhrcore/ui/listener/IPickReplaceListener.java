/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.ui.listener;

import java.util.List;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.entity.salary.ValidateSQLResult;

/**
 *
 * @author mxliteboss
 */
public interface IPickReplaceListener {
    public void changeobject(int method);//�ı��滻����Χ
    public void preview();//Ԥ��
    public ValidateSQLResult replace();//�滻
    public void check();//У��
    public void clear();//���
    public void setReplaceField(TempFieldInfo tfi);
//    public void setContainerData(List list);
    public void setSelectedObject(List list);
    public void refreshData();
}
