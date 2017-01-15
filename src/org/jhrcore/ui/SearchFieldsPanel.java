/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import org.jhrcore.util.SysUtil;
import org.jhrcore.ui.listener.IPickCloseSearchPanelListener;
import org.jhrcore.ui.listener.IPickSearchPanelListener;
import org.jhrcore.util.ComponentUtil;
import org.jhrcore.util.ImageUtil;

/**
 *
 * @author Administrator
 */
public class SearchFieldsPanel extends JPanel {

    private JButton btnLastPage = new JButton("", ImageUtil.getIcon("remove_one.png"));
    private JButton btnAfterPage = new JButton("", ImageUtil.getIcon("select_one.png"));
    private JButton btnClose = new JButton("", ImageUtil.getIcon("close.png"));
    private JButton searchBtn = new JButton("", ImageUtil.getSearchIcon());
    private JTextField searchText = new JTextField();
    private JTree tree = null;
    private JList jList = null;
    private List list;
    private DefaultMutableTreeNode cur_node;
    private int sum = 0;
    private int index = 0;
    public String cur_str = "";
    private List<DefaultMutableTreeNode> set = new ArrayList<DefaultMutableTreeNode>();
    private List<IPickCloseSearchPanelListener> listeners = new ArrayList<IPickCloseSearchPanelListener>();
    private List<IPickSearchPanelListener> searchlisteners = new ArrayList<IPickSearchPanelListener>();

    public void addPickSearchPanelListener(IPickSearchPanelListener listener) {
        searchlisteners.add(listener);
    }

    public void delPickSearchPanelListener(IPickSearchPanelListener listener) {
        searchlisteners.remove(listener);
    }

    public void addPickCloseSearchPanelListener(IPickCloseSearchPanelListener listener) {
        listeners.add(listener);
    }

    public void delPickCloseSearchPanelListener(IPickCloseSearchPanelListener listener) {
        listeners.remove(listener);
    }

    public SearchFieldsPanel(JTree tree) {
        super(new FlowLayout(FlowLayout.LEFT));
        this.tree = tree;
        initOthers();
        setupEvents();
    }

    public SearchFieldsPanel(JList jList, List list) {
        super(new FlowLayout(FlowLayout.LEFT));
        this.jList = jList;
        this.list = list;
        initOthers();
        setupEvents();
    }

    private void initOthers() {
        btnLastPage.setPreferredSize(new Dimension(22, 22));
        btnAfterPage.setPreferredSize(new Dimension(22, 22));
        btnClose.setPreferredSize(new Dimension(22, 22));
        searchText.setPreferredSize(new Dimension(200, 22));
        this.setLayout(new BorderLayout());
        this.add(searchText, BorderLayout.CENTER);
        this.add(searchBtn, BorderLayout.EAST);
        ComponentUtil.setSize(searchBtn, 35, 22);
    }

    private void setFieldSize(int s) {
        int le = s - 90;
        if (le > 0) {
            searchText.setPreferredSize(new Dimension(le, 22));
        }
        this.updateUI();
    }

    private void setupEvents() {
        this.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                setFieldSize(e.getComponent().getWidth());
            }
        });
        btnClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (IPickCloseSearchPanelListener listener : listeners) {
                    listener.closeSearchPanel();
                }
            }
        });
        searchText.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Locate(1);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        searchBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Locate(1);
            }
        });
        btnLastPage.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Locate(-1);
            }
        });
        btnAfterPage.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Locate(1);
            }
        });
        setFieldSize(this.getWidth());
    }

    private void Locate(int step) {
        for (IPickSearchPanelListener listener : searchlisteners) {
            boolean isChange = (searchText.getText() == null || searchText.getText().trim().length() == 0) ? false
                    : !searchText.getText().trim().equals(cur_str);
            listener.searchPanel(searchText.getText(), isChange);
        }

        if (searchText.getText() == null || searchText.getText().replace(" ", "").equals("")) {
            return;
        }
        String tmp_str = searchText.getText().trim();
        index = index + step;
        if (!tmp_str.equals(cur_str)) {
            index = 0;
            cur_str = tmp_str;
            set.clear();
            if (tree != null) {
                set.addAll(SysUtil.locateEmp(tree, cur_str));
            }
            if (jList != null) {
                set.addAll(SysUtil.locateEmp(list, cur_str));
            }
            sum = set.size();
        }
        if (sum == 0) {
            return;
        }
        if (index == -1) {
            index = sum - 1;
        } else if (index == sum) {
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
            TreeNode[] nodes = cur_node.getPath();
            TreePath path = new TreePath(nodes);
            tree.expandPath(path);
            tree.setSelectionPath(path);
            tree.scrollPathToVisible(path);
        }
        if (jList != null) {
            jList.setSelectedIndex(list.indexOf(set.get(i)));
            jList.ensureIndexIsVisible(list.indexOf(set.get(i)));
        }
    }
}
