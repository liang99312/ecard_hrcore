/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui.property;

import java.util.ArrayList;
import java.util.List;
import org.jhrcore.comm.ConfigManager;
import org.jhrcore.entity.SysParameter;

/**
 *
 * @author mxliteboss
 */
public class ClientProperty {

    private SysParameter UI_Font_size = new SysParameter();
    private SysParameter UI_BeanPanel = new SysParameter();
    private SysParameter UI_BeanPanel_columns = new SysParameter();
    private SysParameter UI_BeanPanel_colspan = new SysParameter();
    private SysParameter UI_BeanPanel_rowspan = new SysParameter();
    private SysParameter UI_BeanPanel_width = new SysParameter();
    private SysParameter UI_List = new SysParameter();
    private SysParameter UI_List_fixedCellHeight = new SysParameter();
    private SysParameter UI_Tree = new SysParameter();
    private SysParameter UI_Tree_rowHeight = new SysParameter();
    private SysParameter UI_Tree_rowSelect = new SysParameter();
    private SysParameter UI_Tree_deptSelect = new SysParameter();
    private SysParameter UI_ScrollBar = new SysParameter();
    private SysParameter UI_ScrollBar_width = new SysParameter();
    private List allParas = new ArrayList();
    private static ClientProperty clientProperty;

    public static ClientProperty getInstance() {
        if (clientProperty == null) {
            clientProperty = new ClientProperty();
            clientProperty.init();
        }
        return clientProperty;
    }

    private void init() {
        createNewPara(UI_Font_size, "UI.Font_size", "�����С", "12");
        createNewPara(UI_BeanPanel, "UI.BeanPanel", "��Ƭ", "");
        createNewPara(UI_BeanPanel_columns, "UI.BeanPanel.columns", "����", "2");
        createNewPara(UI_BeanPanel_colspan, "UI.BeanPanel.colspan", "�м��", "15");
        createNewPara(UI_BeanPanel_rowspan, "UI.BeanPanel.rowspan", "�м��", "3");
        createNewPara(UI_BeanPanel_width, "UI.BeanPanel.width", "�п�", "0");
        createNewPara(UI_List, "UIManager.List", "�б�", "");
        createNewPara(UI_List_fixedCellHeight, "UIManager.List.fixedCellHeight", "�и�", "25");
        createNewPara(UI_Tree, "UIManager.Tree", "��", "");
        createNewPara(UI_Tree_rowHeight, "UIManager.Tree.rowHeight", "�и�", "22");
        createNewPara(UI_Tree_rowSelect, "UIManager.Tree.rowSelect", "Ĭ��ѡ����", "4");
        createNewPara(UI_Tree_deptSelect, "UIManager.Tree.dept", "Ĭ��ѡ����", "");
        createNewPara(UI_ScrollBar, "UIManager.ScrollBar", "������", "");
        createNewPara(UI_ScrollBar_width, "UIManager.ScrollBar.width", "���", "12");

    }

    private void createNewPara(SysParameter sp, String id, String name, String value) {
        sp.setSysparameter_code(id);
        sp.setSysparameter_name(name);
        String v = ConfigManager.getConfigManager().getProperty(id);
        sp.setSysparameter_value((v == null || v.equals("")) ? value : v);
        ConfigManager.getConfigManager().save2();
        allParas.add(sp);
    }

    public SysParameter getUI_BeanPanel() {
        return UI_BeanPanel;
    }

    public SysParameter getUI_BeanPanel_colspan() {
        return UI_BeanPanel_colspan;
    }

    public SysParameter getUI_BeanPanel_columns() {
        return UI_BeanPanel_columns;
    }

    public SysParameter getUI_BeanPanel_rowspan() {
        return UI_BeanPanel_rowspan;
    }

    public SysParameter getUI_BeanPanel_width() {
        return UI_BeanPanel_width;
    }

    public SysParameter getUI_Font_size() {
        return UI_Font_size;
    }

    public SysParameter getUI_List() {
        return UI_List;
    }

    public SysParameter getUI_List_fixedCellHeight() {
        return UI_List_fixedCellHeight;
    }

    public SysParameter getUI_ScrollBar() {
        return UI_ScrollBar;
    }

    public SysParameter getUI_ScrollBar_width() {
        return UI_ScrollBar_width;
    }

    public SysParameter getUI_Tree() {
        return UI_Tree;
    }

    public SysParameter getUI_Tree_rowHeight() {
        return UI_Tree_rowHeight;
    }

    public SysParameter getUI_Tree_rowSelect() {
        return UI_Tree_rowSelect;
    }

    public SysParameter getUI_Tree_deptSelect() {
        return UI_Tree_deptSelect;
    }

    public List getAllParas() {
        return allParas;
    }
}
