/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import org.jhrcore.entity.base.LoginUser;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.util.ComponentUtil;

/**
 *
 * @author yangzhou
 */
public class TableListCellRender extends JLabel implements ListCellRenderer {

    private final String entityDefIcon = "entityDefIcon.png";
    //字段图标(整形)
    private final String fieldDefIconI = "fieldDefIconI.png";
    //字段图标(字符型)
    private final String fieldDefIconS = "fieldDefIconS.png";
    //字段图标(数字型)
    private final String fieldDefIconN = "fieldDefIconN.png";
    //字段图标(日期型)
    private final String fieldDefIconD = "fieldDefIconD.png";
    //字段图标(日期型)
    private final String fieldDefIconB = "fieldDefIconB.png";
    //在线图标
    private final String onlineIcon = "user.png";
    //离线图标
    private final String offlineIcon = "offline.png";
    //word图标
    private final String fileWordIcon = "page_white_word.png";
    //文件图标    
    private final String fileIcon = "file.gif";
    //xls图标
    private static final String fileXlsIcon = "page_white_excel.png";
    //rar图标
    private static final String fileRarIcon = "rar.png";
    //ppt图标
    private static final String filePptIcon = "images/ppt.png";

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label1 = new JLabel();
        JLabel label2 = new JLabel();
        String tag1 = entityDefIcon;
        if (value instanceof TempFieldInfo) {
            TempFieldInfo fieldDef = (TempFieldInfo) value;
            String field_type = fieldDef.getField_type().toLowerCase();
            if (field_type.equals("integer") || field_type.equals("int")) {
                tag1 = fieldDefIconI;
            } else if (field_type.equals("string") || field_type.equals("code")) {
                tag1 = fieldDefIconS;
            } else if (field_type.equals("float")) {
                tag1 = fieldDefIconN;
            } else if (field_type.equals("date")) {
                tag1 = fieldDefIconD;
            } else if (field_type.equals("boolean")) {
                tag1 = fieldDefIconB;
            } else if (field_type.equals("bigdecimal")) {
                tag1 = fieldDefIconN;
            }
        } else if (value instanceof LoginUser) {
            LoginUser lu = (LoginUser) value;
            if (lu.getUser_state().equals("在线")) {
                tag1 = onlineIcon;
            } else {
                tag1 = offlineIcon;
            }
        } else if (value instanceof File) {
            File file = (File) value;
            String filename = file.getName();
            if (filename.toLowerCase().endsWith("doc") || filename.toLowerCase().endsWith("docx")) {
                tag1 = fileWordIcon;
            } else if (filename.toLowerCase().endsWith("xls")) {
                tag1 = fileXlsIcon;
            } else if (filename.toLowerCase().endsWith("rar") || filename.toLowerCase().endsWith("zip")) {
                tag1 = fileRarIcon;
            } else if (filename.toLowerCase().endsWith("ppt")) {
                tag1 = filePptIcon;
            } else {
                tag1 = fileIcon;
            }
        }
        ComponentUtil.setIcon(label1, tag1);
        label2.setText(value.toString());
        if (isSelected) {
            label2.setBackground(new Color(184, 207, 229));
            label2.setForeground(Color.WHITE);
            label2.setOpaque(true);
        } else {
            label2.setForeground(Color.BLACK);
            label2.setBackground(Color.WHITE);
        }
        p.add(label1);
        p.add(label2);
        p.setBackground(Color.WHITE);
        return p;
    }
}
