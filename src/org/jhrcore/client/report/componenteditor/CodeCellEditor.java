/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.client.report.componenteditor;

import org.jhrcore.ui.CodeSelectDialog;
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
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import org.jhrcore.entity.Code;
import org.jhrcore.comm.CodeManager;
import org.jhrcore.ui.ContextManager;
import org.jhrcore.ui.CheckTreeNode;
import org.jhrcore.ui.CodeSelectTreeModel;
import org.jhrcore.ui.TreeSelectMod;
import org.jhrcore.ui.renderer.HRRendererView;

/**
 *
 * @author wangzhenhua
 */
public class CodeCellEditor extends AbstractCellEditor {

    private transient JTextField textField = new JTextField("");
    // 返回值的类型，0:编码 1:编码名称
    private int valueType = 0;
    // 是否可以多选
    private boolean mutil_value = false;
    // 编码类型
    private String code_type = "";
    private JTree code_tree;

    public CodeCellEditor(int valueType, boolean mutil_value, String code_type) {
        super();
        this.valueType = valueType;
        this.mutil_value = mutil_value;
        this.code_type = code_type;
    }

    private void getAllNodeOf(TreeNode rootNode, List<CheckTreeNode> list) {
        CheckTreeNode cnode = (CheckTreeNode) rootNode;
        if (cnode.isSelected()) {
            list.add(cnode);
            return;
        }
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
            CheckTreeNode rootNode = (CheckTreeNode) code_tree.getModel().getRoot();
            List<CheckTreeNode> list = new ArrayList<CheckTreeNode>();
            getAllNodeOf(rootNode, list);
            String[] vals = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                Code code = (Code) list.get(i).getUserObject();
                vals[i] = valueType == 0 ? (code.getCode_id()) : code.getCode_name();
            }
            return vals;
        }
    }

    @Override
    public Component getCellEditorComponent(Grid arg0, CellElement arg1) {
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
//                    Code code_0 = (Code) CommUtil.fetchEntityBy("from Code c1 where c1.code_type='" + code_type + "' and c1.code_level=1");
//                    List codes = CommUtil.fetchEntities("from Code c where c.parent_id <> 'ROOT' and c.code_tag like '" + code_0.getCode_id() + "%' order by c.code_tag");
//                    CodeSelectDialog dlg = new CodeSelectDialog(codes, code_type, null, TreeSelectMod.nodeSelectMod);
                    CodeSelectDialog dlg = new CodeSelectDialog(CodeManager.getCodeManager().getCodeListBy(code_type), code_type, null, TreeSelectMod.nodeSelectMod);
                    ContextManager.locateOnScreenCenter(dlg);
                    dlg.setVisible(true);
                    if (dlg.isClick_ok()) {
                        DefaultMutableTreeNode node = dlg.getCur_node();
                        Code code = (Code) node.getUserObject();
                        String s = valueType == 0 ? code.getCode_id() : code.getCode_name();
                        textField.setText(s);
                    }
                }
            });
            return jpanel;
        } else {
//            Code code_0 = (Code)CommUtil.fetchEntityBy("from Code c1 where c1.code_type='" + code_type + "' and c1.code_level=1");
//            code_0.setCode_id("");
//            CheckTreeNode rootNode = new CheckTreeNode(code_0);
            List codes = CodeManager.getCodeManager().getCodeListBy(code_type);
            code_tree = new JTree(new CodeSelectTreeModel(codes, code_type));// CodeSelectManyDlg.createCodeTreeBy(rootNode, code_0.getCode_name(), null, TreeSelectMod.nodeCheckMod);
            JScrollPane sp = new JScrollPane(code_tree);
            HRRendererView.getCommMap().initTree(code_tree, TreeSelectMod.nodeCheckMod);
            sp.setPreferredSize(new Dimension(EditorPara.editorWidth, EditorPara.multiHeight));
            return sp;
        }
    }
}