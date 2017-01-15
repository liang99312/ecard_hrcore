/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.client.report.componenteditor;

import com.fr.cell.Grid;
import com.fr.cell.editor.AbstractCellEditor;
import com.fr.report.CellElement;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.TreeNode;
import org.jhrcore.client.UserContext;
import org.jhrcore.entity.DeptCode;
import org.jhrcore.ui.CheckTreeNode;
import org.jhrcore.ui.ContextManager;
import org.jhrcore.ui.DeptSelectDlg;
import org.jhrcore.ui.DeptSelectPanel;
import org.jhrcore.ui.TreeSelectMod;

/**
 *
 * @author wangzhenhua
 */
public class DeptCodeCellEditor extends AbstractCellEditor {

    private transient JTextField textField = new JTextField("");
    // 返回值的类型，0:编码 1:部门名称
    private int valueType = 0;
    // 是否可以多选
    private boolean mutil_value = false;
    private JTree dept_tree;

    public DeptCodeCellEditor(int valueType, boolean mutil_value) {
        super();
        this.valueType = valueType;
        this.mutil_value = mutil_value;
    }

    private void getAllNodeOf(TreeNode rootNode, List<CheckTreeNode> list) {
        for (int i = 0; i < rootNode.getChildCount(); i++) {
            CheckTreeNode node = (CheckTreeNode) rootNode.getChildAt(i);
            if (node.isSelected()) {
                list.add(node);
            }
            if (!node.isLeaf()) {
                getAllNodeOf(node, list);
            }
        }
    }

    @Override
    public Object getCellEditorValue() throws Exception {
        if (!mutil_value) {
            return textField.getText();
        } else {
            CheckTreeNode rootNode = (CheckTreeNode) dept_tree.getModel().getRoot();
            List<CheckTreeNode> list = new ArrayList<CheckTreeNode>();
            getAllNodeOf(rootNode, list);
            String[] vals = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                DeptCode dept = (DeptCode) list.get(i).getUserObject();
                vals[i] = valueType == 0 ? dept.getDept_code() : dept.getContent();
            }
            return vals;
        }
    }

    @Override
    public Component getCellEditorComponent(Grid arg0, CellElement arg1) {
        //Component com = EditorFactory.createEditorOf(new A01(), "dept");
        if (!mutil_value) {
            final JPanel jpanel = new JPanel(new BorderLayout());
            jpanel.setFocusTraversalKeysEnabled(false);
            JButton btnSelelect = new JButton("...");
            btnSelelect.setPreferredSize(new Dimension(18, btnSelelect.getPreferredSize().height));
            jpanel.add(textField, BorderLayout.CENTER);
            jpanel.add(btnSelelect, BorderLayout.EAST);
            btnSelelect.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    DeptSelectDlg dlg = new DeptSelectDlg(UserContext.getDepts(false));
                    ContextManager.locateOnScreenCenter(dlg);
                    dlg.setVisible(true);
                    if (dlg.isClick_ok()) {
                        String s = valueType == 0 ? dlg.getCurDept().getDept_code() : dlg.getCurDept().getContent();
                        textField.setText(s);
                    }
                }
            });
            return jpanel;
        } else {
            DeptSelectPanel pnl = new DeptSelectPanel(UserContext.getDepts(false), null, TreeSelectMod.nodeCheckMod);
            dept_tree = pnl.getDept_tree();
            JScrollPane sp = new JScrollPane(dept_tree);
            sp.setPreferredSize(new Dimension(EditorPara.editorWidth, EditorPara.multiHeight));
            return sp;
        }
    }
}
