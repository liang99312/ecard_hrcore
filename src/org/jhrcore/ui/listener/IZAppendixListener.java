/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.ui.listener;

import com.foundercy.pf.control.table.FTable;
import java.util.List;

/*
 * 接口用于显示应聘人员附表操作
 * @author：梁秀荣
 * Created on 2013-3-21, 14:53:44
 */
public interface IZAppendixListener {

    //设置网格数据
    public void setObjects(String z_a01_key);

    //添加数据到网格
    public void addObjects(List list);

    //删除网格选择行
    public void delSelectObjects();

    //获取网格对象
    public FTable getFTable();

    //获取网格设置class
    public Class getAppendixClass();
}
