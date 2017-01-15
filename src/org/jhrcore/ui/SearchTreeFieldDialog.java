/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SearchFieldDialog.java
 *
 * Created on 2011-6-25, 12:29:32
 */
package org.jhrcore.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;
import org.jhrcore.util.PinYinMa;
import org.jhrcore.util.SysUtil;
import org.jhrcore.util.ComponentUtil;

/**
 *
 * @author hflj
 */
public class SearchTreeFieldDialog extends javax.swing.JDialog {

    public JTree tree = null;
    private DefaultMutableTreeNode cur_node;
    private int sum = 0;
    private int index = 0;
    private String cur_str = "";
    private List<DefaultMutableTreeNode> set = new ArrayList<DefaultMutableTreeNode>();
    private static SearchTreeFieldDialog searchFieldDialog = null;
    private Hashtable<JTree, String> treeKeys = new Hashtable<JTree, String>();

    /** Creates new form SearchFieldDialog */
    public SearchTreeFieldDialog() {
        initComponents();
        setupEvents();
    }

    public static void doQuickSearch(String title, final JTree tree) {
        doQuickSearch(title, tree, 0);
    }
    public static void doQuickSearch(String title, final JTree tree, JPopupMenu pp) {
        doQuickSearch(title,tree,pp,1);
    }
    public static void doQuickSearch(String title, final JTree tree, int mod) {
        doQuickSearch(title,tree,null,mod);
    }
    /**
     * 该方法用于给指定树增加快速查找功能
     * @param title：标题
     * @param tree：指定树
     * @param mod：查找参数，0：支持右键（CTRL+F）；1：不支持右键
     */
    public static void doQuickSearch(String title, final JTree tree,JPopupMenu pp, int mod) {
        if (searchFieldDialog == null) {
            searchFieldDialog = new SearchTreeFieldDialog();
        }
        searchFieldDialog.tree = tree;
        Container c = tree;
        while (c != null && !(c instanceof JDialog || c instanceof JFrame)) {
            c = c.getParent();
        }
        if (c instanceof JDialog) {
            ((JDialog) c).addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosed(WindowEvent e) {
                    cancelQuickSearch();
                }
            });
        } else if (c instanceof JFrame) {
            ((JFrame) c).addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosed(WindowEvent e) {
                    cancelQuickSearch();
                }
            });
        }
        tree.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                searchFieldDialog.cur_str = "";
                searchFieldDialog.set.clear();
                searchFieldDialog.tree = tree;
            }
        });
        tree.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() || searchFieldDialog.isVisible()) {
                    return;
                }
                int kCode = e.getKeyCode();
                if ((kCode >= 97 && kCode <= 122) || (kCode >= 65 && kCode <= 90)) {
                    String val = KeyEvent.getKeyText(kCode).toUpperCase();
                    if (!val.equals(searchFieldDialog.cur_str)) {
                        searchFieldDialog.cur_str = val;
                        List<DefaultMutableTreeNode> selectNodes = new ArrayList<DefaultMutableTreeNode>();
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getModel().getRoot();
                        Enumeration deptEnum = node.depthFirstEnumeration();
                        while (deptEnum.hasMoreElements()) {
                            DefaultMutableTreeNode tmpNode = (DefaultMutableTreeNode) deptEnum.nextElement();
                            if (tmpNode == node) {
                                continue;
                            }
                            String field_val = tmpNode.getUserObject().toString();
                            int fisrt = field_val.indexOf("[");
                            if (fisrt != -1) {
                                field_val = field_val.substring(0, fisrt);
                            }
                            if (field_val != null && field_val.length() >= 1) {
                                if (val.equals(field_val.substring(0, 1))) {
                                    selectNodes.add(tmpNode);
                                    continue;
                                }
                                String pinyin = PinYinMa.ctoE(field_val.substring(0, 1));
                                if (pinyin != null && pinyin.substring(0, 1).toUpperCase().equals(val)) {
                                    if (!selectNodes.contains(tmpNode)) {
                                        selectNodes.add(tmpNode);
                                    }
                                }
                            }
                        }
                        searchFieldDialog.set.clear();
                        searchFieldDialog.set.addAll(selectNodes);
                        searchFieldDialog.sum = searchFieldDialog.set.size();
                    }
                    if (searchFieldDialog.sum == 0) {
                        return;
                    }
                    if (searchFieldDialog.index == -1) {
                        searchFieldDialog.index = searchFieldDialog.sum - 1;
                    } else if (searchFieldDialog.index >= searchFieldDialog.sum) {
                        searchFieldDialog.index = 0;
                    }
                    searchFieldDialog.locateNode(searchFieldDialog.index);
                    searchFieldDialog.index++;
                }
            }
        });
        
        if(pp ==null)
            pp= new JPopupMenu();
        final JPopupMenu popupMenu = pp;
        if (mod == 0) {
            tree.addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == 3) {
                        popupMenu.show(tree, e.getX(), e.getY());
                    }
                }
            });
        }
        Action alSearch = new AbstractAction("查找") {

            @Override
            public void actionPerformed(ActionEvent e) {
                showSearchPanel();
            }
        };
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_F, java.awt.Event.CTRL_MASK, false);
        tree.getInputMap().put(stroke, "Search");
        tree.getActionMap().put("Search", alSearch);
        JMenuItem mi = new JMenuItem(alSearch);
        ComponentUtil.setIcon(mi, "blank");
        mi.setMnemonic(KeyEvent.VK_F);
        mi.setAccelerator(stroke);
        popupMenu.add(mi);
        searchFieldDialog.treeKeys.put(tree, title);
    }

    private static void showSearchPanel() {
        if (searchFieldDialog.isVisible()) {
            return;
        }
        Runnable run = new Runnable() {

            @Override
            public void run() {
                searchFieldDialog.setMinimumSize(new Dimension(250, 70));
                searchFieldDialog.setPreferredSize(new Dimension(250, 70));
                searchFieldDialog.setTitle("基于" + searchFieldDialog.treeKeys.get(searchFieldDialog.tree) + "的搜索：");
                searchFieldDialog.setModal(true);
                searchFieldDialog.setLocationRelativeTo((Component) searchFieldDialog.tree.getParent());
                searchFieldDialog.setVisible(true);
            }
        };
        new Thread(run).run();
    }

    public static void cancelQuickSearch() {
        searchFieldDialog.treeKeys.clear();
        searchFieldDialog.cur_str = "";
        searchFieldDialog.set.clear();
    }

    private void setupEvents() {
        searchText.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    Locate(1, searchText.getText());
                }
            }
        });
        btnLastPage.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Locate(-1, searchText.getText());
            }
        });
        btnAfterPage.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Locate(1, searchText.getText());
            }
        });
    }

    public void Locate(int step, String text) {
        if (text == null || text.replace(" ", "").equals("")) {
            return;
        }
        String tmp_str = text.replace(" ", "");
        index = index + step;
        if (!tmp_str.equals(cur_str)) {
            index = 0;
            cur_str = tmp_str;
            set.clear();
            if (tree != null) {
                set.addAll(SysUtil.locateEmp(tree, cur_str));
            }
            sum = set.size();
        }
        if (sum == 0) {
            return;
        }
        if (index == -1) {
            index = sum - 1;
        } else if (index >= sum) {
            index = 0;
        }
        locateNode(index);
    }

    private void locateNode(int i) {
        if (tree != null) {
            cur_node = (DefaultMutableTreeNode) set.get(i);
            if (cur_node == null) {
                return;
            }
            ComponentUtil.initTreeSelection(tree, cur_node);
        }
    }

    public static SearchTreeFieldDialog getSearchFieldDialog() {
        return searchFieldDialog;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchText = new javax.swing.JTextField();
        btnLastPage = new javax.swing.JButton();
        btnAfterPage = new javax.swing.JButton();

        searchText.setToolTipText("请输入搜索内容：");

        btnLastPage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/remove_one.png"))); // NOI18N

        btnAfterPage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/select_one.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLastPage, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAfterPage, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAfterPage, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLastPage, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAfterPage;
    private javax.swing.JButton btnLastPage;
    private javax.swing.JTextField searchText;
    // End of variables declaration//GEN-END:variables
}
