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
    public void changeobject(int method);//改变替换对象范围
    public void preview();//预览
    public ValidateSQLResult replace();//替换
    public void check();//校验
    public void clear();//清空
    public void setReplaceField(TempFieldInfo tfi);
//    public void setContainerData(List list);
    public void setSelectedObject(List list);
    public void refreshData();
}
