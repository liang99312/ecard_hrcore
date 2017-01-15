/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.undo.UndoManager;
import org.jhrcore.entity.Code;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.util.ImageUtil;

/**
 *
 * @author hflj
 */
public class HrTextPane extends JScrollPane {

    private JPopupMenu pp_right = new JPopupMenu();
    private JMenuItem mi_cut = null;
    private JMenuItem mi_copy = null;
    private JMenuItem mi_paste = null;
    private JMenuItem mi_del = null;
    private JMenuItem mi_all = null;
    private JMenuItem mi_undo = null;
    private JMenuItem mi_redo = null;
    private UndoManager um = new UndoManager();//撤销管理类
    private int limit = 50;//默认最大取消次数
    private JTextPane textPane = null;
    private SyntaxDocument docment;

    public HrTextPane() {
        this(new SyntaxDocument());
    }

    public HrTextPane(SyntaxDocument sd) {
        this.docment = sd;
        textPane = new JTextPane(sd);
        um.setLimit(limit);
        this.setViewportView(textPane);
        setupEvents();
    }

    public void setText(String text) {
        textPane.setText(text);
        um.discardAllEdits();
    }

    public boolean isTextChanged() {
        return um.canRedo() || um.canUndo();
    }

    private void setupEvents() {
        textPane.getDocument().addUndoableEditListener(um);
        Action alCut = new AbstractAction("剪切", ImageUtil.getIcon("cut.png")) {

            @Override
            public void actionPerformed(ActionEvent e) {
                String s = HrTextPane.this.textPane.getSelectedText();
                if (s == null || s.length() == 0) {
                    return;
                }
                HrTextPane.this.textPane.cut();
            }
        };
        addKeyStroke(alCut, mi_cut, "Cut", 'X');

        Action alCopy = new AbstractAction("复制", ImageUtil.getIcon("copy.png")) {

            @Override
            public void actionPerformed(ActionEvent e) {
                HrTextPane.this.textPane.copy();
            }
        };
        addKeyStroke(alCopy, mi_copy, "Copy", 'C');

        Action alPaste = new AbstractAction("粘贴", ImageUtil.getIcon("paste.png")) {

            @Override
            public void actionPerformed(ActionEvent e) {
                HrTextPane.this.textPane.paste();
            }
        };
        addKeyStroke(alPaste, mi_paste, "Paste", 'V');

        Action alDel = new AbstractAction("删除", ImageUtil.getIcon("blank_2.png")) {

            @Override
            public void actionPerformed(ActionEvent e) {
                HrTextPane.this.textPane.replaceSelection("");
            }
        };
        addKeyStroke(alDel, mi_del, "Del", 'D');

        Action alAll = new AbstractAction("全选", ImageUtil.getIcon("blank_2.png")) {

            @Override
            public void actionPerformed(ActionEvent e) {
                HrTextPane.this.textPane.selectAll();
            }
        };
        addKeyStroke(alAll, mi_all, "All", 'A');

        pp_right.addSeparator();
        final Action alUndo = new AbstractAction("撤销", ImageUtil.getIcon("undo.png")) {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (um.canUndo()) {
                    um.undo();
                } else {
                    JOptionPane.showMessageDialog(null, "无法撤销", "警告", JOptionPane.WARNING_MESSAGE);
                }
            }
        };
        addKeyStroke(alUndo, mi_undo, "Undo", 'Z');

        final Action alRedo = new AbstractAction("重做", ImageUtil.getIcon("redo.png")) {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (um.canRedo()) {
                    um.redo();
                } else {
                    JOptionPane.showMessageDialog(null, "无法恢复", "警告", JOptionPane.WARNING_MESSAGE);
                }
            }
        };
        addKeyStroke(alRedo, mi_redo, "Redo", 'Y');
        textPane.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    alUndo.setEnabled(um.canUndo());
                    alRedo.setEnabled(um.canRedo());
                    pp_right.show((Component) e.getSource(), e.getX() - 35, e.getY());
                }
                if (e.getClickCount() != 2) {
                    return; // 去掉关键字选择
                }
                int caret_p = textPane.getCaretPosition();
                Point p2 = null;
                for (Point p : getKeywordPositions()) {
                    if (caret_p > p.x && caret_p <= p.y) {
                        p2 = p;
                        break;
                    }
                }
                if (p2 != null) {
                    textChange(p2);
                }
            }
        });
    }

    /**
     * SQL编辑区双击产生的响应，弹出对话框，当双击区域为字段时，显示该表的所有字段，当双击区域为编码时，显示该编码对应编码项下所有编码
     * @param p2：当前选择点
     */
    private void textChange(Point p2) {
        textPane.setSelectionStart(p2.x);
        textPane.setSelectionEnd(p2.y);
        try {
            String s = textPane.getText(p2.x, p2.y - p2.x);
            String group = getKeyword_groups().get(s);
            List list = getLookups2().get(group);
            if (list.size() == 0) {
                return;
            }
            String title = "";
            boolean isField = list.get(0) instanceof TempFieldInfo;
            boolean isCode = list.get(0) instanceof Code;
            boolean isPay = !s.contains(".");
            DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("所有属性");
            DefaultMutableTreeNode select_node = null;
//            TreeCellRenderer renderer = null;
            boolean select_many = false;
//            List<String> search_fields = new ArrayList<String>();
            if (isField) {
//                search_fields.add("field_name");
//                search_fields.add("caption_name");
//                search_fields.add("pym");
                title = "请选择字段";
                for (Object obj : list) {
                    TempFieldInfo tfi = (TempFieldInfo) obj;
                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(tfi);
                    rootNode.add(node);
                    if (isPay) {
                        if (("[" + tfi.getCaption_name() + "]").equals(s)) {
                            select_node = node;
                        }
                    } else {
                        if (("[" + tfi.getEntity_caption() + "." + tfi.getCaption_name() + "]").equals(s)) {
                            select_node = node;
                        }
                    }
                }
//                renderer = new QueryParaRenderer();
            } else if (isCode) {
//                search_fields.add("code_id");
//                search_fields.add("code_name");
//                search_fields.add("pym");
                String start = textPane.getText(p2.x - 10, 10);
                select_many = true;
                if (start.toLowerCase().contains("like")) {
                    select_many = false;
                }
//                renderer = new TreeCellCheckRenderer();
                title = "请选择编码";
                if (select_many) {
                    rootNode = new CheckTreeNode(group);
                    CheckTreeNode cur_node = (CheckTreeNode) rootNode;
                    for (Object obj : list) {
                        Code code = (Code) obj;
                        while (cur_node != rootNode && !code.getParent_id().equals(((Code) cur_node.getUserObject()).getCode_id())) {
                            cur_node = (CheckTreeNode) cur_node.getParent();
                        }
                        CheckTreeNode node = new CheckTreeNode(code);
                        cur_node.add(node);
                        cur_node = node;
                    }
                } else {
                    DefaultMutableTreeNode cur_node = (DefaultMutableTreeNode) rootNode;
                    String code_parent_name = s.substring(0, s.indexOf("."));
                    for (Object obj : list) {
                        Code code = (Code) obj;
                        while (cur_node != rootNode && !code.getParent_id().equals(((Code) cur_node.getUserObject()).getCode_id())) {
                            cur_node = (DefaultMutableTreeNode) cur_node.getParent();
                        }
                        DefaultMutableTreeNode node = new DefaultMutableTreeNode(code);
                        if ((code_parent_name + "." + code.getCode_name() + "]").equals(s)) {
                            select_node = node;
                        }
                        cur_node.add(node);
                        cur_node = node;
                    }
                }
            } else {
                title = "请选择参数";
                for (Object obj : list) {
                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(obj);
                    rootNode.add(node);
                    if (s.equals(obj.toString())) {
                        select_node = node;
                    }
                }
//                renderer = new QueryParaRenderer();
            }
            ModelTreeDialog mtDlg = new ModelTreeDialog(JOptionPane.getFrameForComponent(textPane), title, select_many, rootNode, select_node);//, renderer);
//            mtDlg.setSearch_fields(search_fields);
            ContextManager.locateOnMainScreenCenter(mtDlg);
            mtDlg.setVisible(true);
            if (mtDlg.isClick_ok()) {
                List select_objs = mtDlg.getSelect_objs();
                if (select_objs.size() == 0) {
                    return;
                }
                String tmp = null;
                if (isField) {
                    TempFieldInfo tfi = ((TempFieldInfo) select_objs.get(0));
                    if (tfi.getEntity_caption().endsWith("工资所有项目")) {
                        tmp = "[" + tfi.getCaption_name() + "]";
                    } else {
                        tmp = "[" + tfi.getEntity_caption() + "." + tfi.getCaption_name() + "]";
                    }
                } else if (isCode) {
                    if (select_objs.size() == 1) {
                        Code c = (Code) select_objs.get(0);
                        tmp = "[" + c.getCode_type() + "." + c.getCode_name() + "]";
                    } else {
                        StringBuffer str = new StringBuffer();
                        for (Object obj : select_objs) {
                            Code c = (Code) obj;
                            str.append("'[");
                            str.append(c.getCode_type());
                            str.append(".");
                            str.append(c.getCode_name());
                            str.append("]',");
                        }
                        tmp = str.toString();
                        if (!tmp.equals("")) {
                            tmp = tmp.substring(1);
                            tmp = tmp.substring(0, tmp.length() - 2);
                        }
                    }
                } else {
                    tmp = select_objs.get(0).toString();
                }
                if (tmp == null) {
                    return;
                }
                textPane.replaceSelection(tmp);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

    }

    private void addKeyStroke(Action ac, JMenuItem mi, String ac_key, char stroke_key) {
        KeyStroke stroke = KeyStroke.getKeyStroke(stroke_key, java.awt.Event.CTRL_MASK, false);
        textPane.getInputMap().put(stroke, ac_key);
        textPane.getActionMap().put(ac_key, ac);
        mi = new JMenuItem(ac);
        mi.setMnemonic(stroke_key);
        mi.setAccelerator(stroke);
        pp_right.add(mi);
    }

    public void setEditable(boolean editable) {
        textPane.setEditable(editable);
    }

    public JTextPane getTextPane() {
        return textPane;
    }

    public String getText() {
        return textPane.getText();
    }

    public String getText(int ind_start, int ind_end) throws BadLocationException {
        return textPane.getText(ind_start, ind_end);
    }

    public int getSelectionStart() {
        return textPane.getSelectionStart();
    }

    public void setSelectionStart(int ind) {
        textPane.setSelectionStart(ind);
    }

    public void setSelectionEnd(int ind) {
        textPane.setSelectionEnd(ind);
    }

    public void replaceSelection(String str) {
        textPane.replaceSelection(str);
    }

    public void setCaretPosition(int ind) {
        textPane.setCaretPosition(ind);
    }

    public int getCaretPosition() {
        return textPane.getCaretPosition();
    }

    public StyledDocument getDocument() {
        return textPane.getStyledDocument();
    }

    /**
     * 获得所有关键字的坐标
     * @return：所有关键字的坐标集合
     */
    public List<Point> getKeywordPositions() {
        return ((SyntaxDocument) textPane.getDocument()).getKeywordPositions();
    }

    public Hashtable<String, String> getKeyword_groups() {
        return docment.getKeyword_groups();
    }

    public Hashtable<String, List> getLookups2() {
        return docment.getLookups2();
    }

    public Hashtable<String, String> getK_keywords() {
        return docment.getK_keywords();
    }

    public void revokeDocumentKeys(Hashtable<String, List> lookup, Hashtable<String, String> keyword_group, Hashtable<String, String> k_keyword) {
        docment.revokeKeys(lookup, keyword_group, k_keyword);
    }

    public SyntaxDocument getDocment() {
        return docment;
    }
}
