/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui.renderer;

import java.awt.Component;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import org.jhrcore.ui.task.IModulePanel;
import org.jhrcore.util.SysUtil;
import org.jhrcore.util.PublicUtil;
import org.jhrcore.ui.TreeSelectMod;
import org.jhrcore.util.ComponentUtil;
import org.jhrcore.zui.FreeTabbedPane;

/**
 *
 * @author mxliteboss
 */
public class RenderderMap {

    private Hashtable<String, String> iconKeys = new Hashtable<String, String>();
    private Hashtable<Integer, Hashtable<String, String>> advanceIconKeys = new Hashtable();
    private Hashtable<Integer, List<String>> advanceIndexKeys = new Hashtable();

    public RenderderMap() {
    }

    public RenderderMap(Hashtable<String, String> iconKeys) {
        this.iconKeys = iconKeys;
    }

    public void clearTag(JTree tree) {
        if (tree == null) {
            return;
        }
        advanceIndexKeys.remove(tree.hashCode());
        advanceIconKeys.remove(tree.hashCode());
    }

    public String getIconByTag(String str) {
        if (iconKeys == null) {
            return null;
        }
        return iconKeys.get(str);
    }

    public String getIconTag(JTree tree, Object obj, int lvl) {
        if (obj == null || tree == null) {
            return null;
        }
        Hashtable<String, String> ht = advanceIconKeys.get(tree.hashCode());
        if (ht != null) {
            List<String> keys = advanceIndexKeys.get(tree.hashCode());
            String tag = null;
            for (String key : keys) {
                String[] keyStrs = key.split("\\;");
                boolean exists = true;
                for (String keyStr : keyStrs) {
                    if (keyStr.startsWith("lvl|")) {
                        if (!(lvl + "").equals(keyStr.substring(4))) {
                            exists = false;
                            break;
                        }
                    } else if (keyStr.startsWith("class|")) {
                        if (!(obj.getClass().getSimpleName()).equals(keyStr.substring(6))) {
                            exists = false;
                            break;
                        }
                    } else {
                        String[] strs = keyStr.split("\\|");
                        if (strs.length == 2) {
                            Object value = PublicUtil.getProperty(obj, strs[0]);
                            if (!strs[1].equals(SysUtil.objToStr(value))) {
                                exists = false;
                                break;
                            }
                        }
                    }
                }
                if (exists) {
                    tag = ht.get(key);
                }
                if (tag != null) {
                    break;
                }
            }
            return tag;
        }
        return iconKeys.get(obj.getClass().getSimpleName());
    }

    public String getIconTag(Object obj) {
        if (obj == null) {
            return null;
        }
        return iconKeys.get(obj.getClass().getSimpleName());
    }

    public void setIcon(JTree tree, String key, String iconName) {
        if (tree == null || iconName == null || key == null) {
            return;
        }
        Hashtable<String, String> ht = advanceIconKeys.get(tree.hashCode());
        List<String> htIndex = advanceIndexKeys.get(tree.hashCode());
        if (ht == null) {
            ht = new Hashtable<String, String>();
            htIndex = new ArrayList();
            advanceIconKeys.put(tree.hashCode(), ht);
            advanceIndexKeys.put(tree.hashCode(), htIndex);
        }
        htIndex.add(key);
        ht.put(key, iconName);
    }

    public void setIcon(Class c, String iconName) {
        iconKeys.put(c.getSimpleName(), iconName);
    }

    public void setIcon(String key, String iconName) {
        iconKeys.put(key, iconName);
    }

    public void initTree(final JTree tree) {
        initTree(tree, TreeSelectMod.nodeSelectMod);
    }

    public void initTree(final JTree tree, int selectMod) {
        tree.setEditable(selectMod > TreeSelectMod.nodeSelectMod);
        if (tree.isEditable()) {
            HRCommTreeEditor editor = new HRCommTreeEditor(tree, selectMod, this);
            tree.setCellEditor(editor);
            tree.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() >= 2) {
                        DefaultMutableTreeNode node = ComponentUtil.getSelectNode(tree);
                        TreePath tp = new TreePath(node.getPath());
                        if (tree.isCollapsed(tp)) {
                            tree.expandPath(tp);
                        } else {
                            tree.collapsePath(tp);
                        }
                    }
                }
            });
        }
        HRCommTreeRenderer renderder = new HRCommTreeRenderer(this);
        tree.setCellRenderer(renderder);
        tree.getSelectionModel().setSelectionMode(
                selectMod == TreeSelectMod.nodeManySelectMod ? TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION : TreeSelectionModel.SINGLE_TREE_SELECTION);
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                int i = 25;
                Component c = tree;
                while (c.getParent() != null && i > 0) {
                    c = c.getParent();
                    if (c instanceof JFrame) {
                        ((JFrame) c).addWindowListener(new WindowAdapter() {

                            @Override
                            public void windowClosing(WindowEvent e) {
                                clearTag(tree);
                            }
                        });
                        break;
                    } else if (c instanceof JDialog) {
                        ((JDialog) c).addWindowListener(new WindowAdapter() {

                            @Override
                            public void windowClosing(WindowEvent e) {
                                clearTag(tree);
                            }
                        });
                        break;
                    } else if (c instanceof IModulePanel) {
                        final JPanel pnl = (JPanel) c;
                        if (pnl.getParent() instanceof FreeTabbedPane) {
                            FreeTabbedPane ftp = (FreeTabbedPane) pnl.getParent();
                            ftp.addContainerListener(new ContainerAdapter() {

                                public void componentRemoved(ContainerEvent e) {
                                    if (e.getChild() == pnl) {
                                        clearTag(tree);
                                    }
                                }
                            });
                        }
                    }
                    i--;
                }
            }
        });
    }
}
