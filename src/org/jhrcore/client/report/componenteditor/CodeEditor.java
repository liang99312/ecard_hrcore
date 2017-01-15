/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.client.report.componenteditor;

import com.fr.cell.ParameterJWorkSheet;
import com.fr.design.gui.core.componenteditor.Editor;
import com.fr.cell.core.layout.LayoutFactory;
import com.fr.data.TableData;
import com.fr.data.impl.DBTableData;
import com.fr.design.mainframe.ValueEditorPane;
import com.fr.report.WorkBookTemplate;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import org.jhrcore.client.CommUtil;
import org.jhrcore.client.report.ReportPanel;
import org.jhrcore.comm.CodeManager;
import org.jhrcore.entity.Code;
import org.jhrcore.ui.CodeSelectDialog;
import org.jhrcore.ui.ContextManager;
import org.jhrcore.ui.TreeSelectMod;

/**
 *
 * @author Administrator
 */
public class CodeEditor extends Editor {

    private JPopupMenu popMenu = new JPopupMenu("SA");

    public CodeEditor() {
        this(((String) (null)), "系统编码");
    }

    public CodeEditor(Object obj) {
        this(obj, "系统编码");
    }

    public CodeEditor(Object obj, String s) {
        oldValue = "";
        setLayout(LayoutFactory.createBorderLayout());
        textField = new JTextField();
        add(textField, "Center");
        add(btn_sel, "East");
        textField.addKeyListener(textKeyListener);
        btn_sel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ParameterJWorkSheet jWorkSheet = ReportPanel.getJWorkSheet();
                WorkBookTemplate workbooktemplate = jWorkSheet.getEditingReport().getWorkBook();

                if (!ValueEditorPane.cur_col_name.equals("")) {
                    TableData td = workbooktemplate.getTableData(ValueEditorPane.cur_col_name);
                    if (!(td instanceof DBTableData)) {
                        return;
                    }
                    DBTableData dtd = (DBTableData) td;
                    String sql = dtd.getQuery();
                    int int1 = sql.lastIndexOf("code_type=") + 10;
                    if (int1 < 0) {
                        return;
                    }
                    final String code_type = sql.substring(int1).replace("'", "");
                    Code code_0 = (Code) CommUtil.fetchEntityBy("from Code c1 where c1.code_type='" + code_type + "' and c1.code_level=1");
                    List codes = CommUtil.fetchEntities("from Code c where c.parent_id <> 'ROOT' and c.code_tag like '" + code_0.getCode_id() + "%' order by c.code_tag");
                    CodeSelectDialog dlg = new CodeSelectDialog(codes, code_type, null, TreeSelectMod.nodeCheckMod);
                    ContextManager.locateOnScreenCenter(dlg);
                    dlg.setVisible(true);
                    if (dlg.isClick_ok()) {
                        DefaultMutableTreeNode node = dlg.getCur_node();
                        Code code = (Code) node.getUserObject();
                        //String s = valueType == 0 ? code.getCode_id() : code.getCode_name();
                        String s = code.getCode_id();
                        textField.setText(s);
                    }
                    return;
                }

                popMenu.removeAll();
                JMenuItem tmp_mi0;


                Iterator iterator = workbooktemplate.getTableDataNameIterator();
                while (iterator.hasNext()) {
                    final String dit_name = (String) iterator.next();
                    TableData td = workbooktemplate.getTableData(dit_name);
                    if (!(td instanceof DBTableData)) {
                        continue;
                    }
                    DBTableData dtd = (DBTableData) td;
                    String sql = dtd.getQuery();
                    int int1 = sql.lastIndexOf("code_type=") + 10;
                    if (int1 < 0) {
                        continue;
                    }
                    final String code_type = sql.substring(int1).replace("'", "");
                    tmp_mi0 = new JMenuItem(dit_name);
                    popMenu.add(tmp_mi0);
                    tmp_mi0.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
//                            Code code_0 = (Code) CommUtil.fetchEntityBy("from Code c1 where c1.code_type='" + code_type + "' and c1.code_level=1");
//                            List codes = CommUtil.fetchEntities("from Code c where c.parent_id <> 'ROOT' and c.code_tag like '" + code_0.getCode_id() + "%' order by c.code_tag");
//                            CodeSelectDialog dlg = new CodeSelectDialog(codes, code_type, TreeSelectMod.nodeCheckMod);
                            CodeSelectDialog dlg = new CodeSelectDialog(CodeManager.getCodeManager().getCodeListBy(code_type), code_type, null, TreeSelectMod.nodeCheckMod);
                            ContextManager.locateOnScreenCenter(dlg);
                            dlg.setVisible(true);
                            if (dlg.isClick_ok()) {
                                DefaultMutableTreeNode node = dlg.getCur_node();
                                Code code = (Code) node.getUserObject();
                                //String s = valueType == 0 ? code.getCode_id() : code.getCode_name();
                                String s = code.getCode_id();
                                textField.setText(s);
                            }
                        }
                    });
                }
                popMenu.show(btn_sel, 0, 25);
            }
        });
        setValue(obj);
        setName(s);
    }

    public int getHorizontalAlignment() {
        return textField.getHorizontalAlignment();
    }

    public void setHorizontalAlignment(int i) {
        textField.setHorizontalAlignment(i);
    }

    public Object getValue() {
        return textField.getText();
    }

    public void setValue(Object obj) {
        if (obj == null) {
            obj = new String("");
        }
        oldValue = obj.toString();
        textField.setText(oldValue);
    }

    public void setEnabled(boolean flag) {
        textField.setEditable(flag);
    }

    public void requestFocus() {
        textField.requestFocus();
    }

    public JTextField getTextField() {
        return textField;
    }

    public boolean accept(Object obj) {
        return obj instanceof String;
    }
    KeyListener textKeyListener = new KeyListener() {

        public void keyTyped(KeyEvent keyevent) {
        }

        public void keyPressed(KeyEvent keyevent) {
        }

        public void keyReleased(KeyEvent keyevent) {
            int i = keyevent.getKeyCode();
            if (i == 27) {
                textField.setText(oldValue);
            } else if (i == 10) {
                fireEditingStopped();
            }
        }
    };
    private JButton btn_sel = new JButton("...");
    private JTextField textField;
    private String oldValue;
}
