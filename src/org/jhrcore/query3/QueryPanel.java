package org.jhrcore.query3;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.jhrcore.client.CommUtil;
import org.jhrcore.entity.query.QueryScheme;
import org.jhrcore.ui.CheckTreeNode;
import org.jhrcore.ui.ContextManager;
import org.jhrcore.ui.renderer.HRRendererView;
import org.jhrcore.util.ComponentUtil;

public class QueryPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    // 表示要查询的对象所属于的类
    private Class entityClass;
    // 表示要查询的对象
    private String queryEntity;
    //方案类型，用来区分方案用在什么地方
    private String scheme_type;
    private List<QueryScheme> listQuerySchemes = new ArrayList<QueryScheme>();
    private JTree schemeTree;
    private QueryTreeModel treeModel;
    private QueryScheme cur_queryScheme;
    private DefaultMutableTreeNode cur_node = null;
    private List<IPickQuerySchemeListner> pickQuerySchemeListners = new ArrayList<IPickQuerySchemeListner>();
    private JPopupMenu popupMenu = new JPopupMenu();
    private JMenuItem mi_query_scheme = new JMenuItem("方案管理");
    private JMenuItem mi_del = new JMenuItem("删除方案");

    public void addPickQuerySchemeListner(IPickQuerySchemeListner listner) {
        pickQuerySchemeListners.add(listner);
    }

    public void removePickQuerySchemeListner(IPickQuerySchemeListner listner) {
        pickQuerySchemeListners.remove(listner);
    }

    public QueryPanel(Class entityClass, String scheme_type) {
        super(new BorderLayout());
        this.entityClass = entityClass;
        this.queryEntity = entityClass.getSimpleName();
        this.scheme_type = scheme_type;
        initUI();
        setupEvents();
    }

    private void loadQueryScheme() {
//        if ("A01".equalsIgnoreCase(entityClass.getSimpleName())) {
//            if (!CommUtil.exists("select 1 from QueryScheme where queryScheme_key='cxwfbry'")) {
//                CommUtil.excuteSQL("insert into queryscheme(queryscheme_key,queryentity,queryscheme_name,query_type,scheme_type,share_flag) values('cxwfbry','A01','无附表记录人员',2,'常用查询',0)");
//            }
//        }
        listQuerySchemes = (List<QueryScheme>) CommUtil.fetchEntities("from QueryScheme qs left join fetch qs.conditions where qs.queryEntity='" + queryEntity + "' and qs.scheme_type='" + scheme_type + "'");
    }

    private void initUI() {
        loadQueryScheme();
        popupMenu.add(mi_query_scheme);
        popupMenu.add(mi_del);
        ComponentUtil.setIcon(new Object[]{mi_query_scheme, mi_del}, "blank");
        treeModel = new QueryTreeModel(listQuerySchemes);
        schemeTree = new JTree(treeModel);
        schemeTree.setShowsRootHandles(true);
        schemeTree.setRootVisible(false);
        HRRendererView.getCommMap().initTree(schemeTree);
        this.add(schemeTree, BorderLayout.CENTER);
    }

    public CheckTreeNode getRootNode() {
        return treeModel.getR();
    }

    private void setupEvents() {
        mi_del.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (cur_queryScheme == null) {
                    return;
                }
                if (JOptionPane.showConfirmDialog(ContextManager.getMainFrame(), "确定要删除方案[" + cur_queryScheme + "]吗?", "询问",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.YES_OPTION) {
                    return;
                }
                String sql = "delete from condition where queryScheme_key ='" + cur_queryScheme.getQueryScheme_key() + "';";
                sql += "delete from queryScheme where queryScheme_key ='" + cur_queryScheme.getQueryScheme_key() + "';";
                CommUtil.excuteSQLs(sql, ";");
                DefaultMutableTreeNode new_node = null;
                if (cur_node.getNextSibling() != null) {
                    new_node = cur_node.getNextSibling();
                } else if (cur_node.getPreviousSibling() != null) {
                    new_node = cur_node.getPreviousSibling();
                }
                cur_node.removeFromParent();
                if (new_node != null) {
                    cur_node = new_node;
                    schemeTree.clearSelection();
                    schemeTree.addSelectionPath(new TreePath(new_node.getPath()));
                } else {
                    cur_node = null;
                }
                schemeTree.updateUI();
            }
        });
        mi_query_scheme.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                if (queryEntity.equals("A01") && "常用查询".equals(scheme_type)) {
                    PersonQuerySchemeDialog pspDialog = new PersonQuerySchemeDialog("常用查询");
                    pspDialog.addIPicQuerySchemeListner(new IPickQuerySchemeListner() {

                        @Override
                        public void addPickQueryScheme(QueryScheme qs) {
                            CheckTreeNode tmp_node = new CheckTreeNode(qs);
                            treeModel.addQueryNode(tmp_node);
                            cur_node = tmp_node;
                            schemeTree.clearSelection();
                            schemeTree.addSelectionPath(new TreePath(tmp_node.getPath()));
                            cur_queryScheme = qs;
                            schemeTree.updateUI();
                        }
                    });
                    ContextManager.locateOnMainScreenCenter(pspDialog);
                    pspDialog.setVisible(true);
                } else {
                    QuerySchemeDialog schemeDlg = new QuerySchemeDialog(JOptionPane.getFrameForComponent(mi_query_scheme), entityClass, scheme_type);
                    schemeDlg.addIPicQuerySchemeListner(new IPickQuerySchemeListner() {

                        @Override
                        public void addPickQueryScheme(QueryScheme qs) {
                            CheckTreeNode tmp_node = new CheckTreeNode(qs);
                            treeModel.addQueryNode(tmp_node);
                            cur_node = tmp_node;
                            schemeTree.clearSelection();
                            schemeTree.addSelectionPath(new TreePath(tmp_node.getPath()));
                            cur_queryScheme = qs;
                            schemeTree.updateUI();
                        }
                    });
                    ContextManager.locateOnScreenCenter(schemeDlg);
                    schemeDlg.setVisible(true);
                    if (schemeDlg.getQueryScheme() != null) {
                        for (IPickQuerySchemeListner listner : pickQuerySchemeListners) {
                            listner.addPickQueryScheme(schemeDlg.getQueryScheme());
                        }
                    }
                }
            }
        });
        schemeTree.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                if (schemeTree.getSelectionPath() == null) {
                    cur_node = null;
                    cur_queryScheme = null;
                    return;
                }
                cur_node = (DefaultMutableTreeNode) schemeTree.getSelectionPath().getLastPathComponent();
                if (cur_node.getUserObject() instanceof QueryScheme) {
                    cur_queryScheme = (QueryScheme) cur_node.getUserObject();
                } else {
                    cur_queryScheme = null;
                }
            }
        });
        schemeTree.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    popupMenu.show(schemeTree, e.getX(), e.getY());
                } else if (cur_queryScheme != null && e.getClickCount() == 2) {
                    schemeTree.updateUI();
                    QueryScheme qs = cur_queryScheme;
                    if (qs.getQueryScheme_key() != null) {
                        qs = (QueryScheme) CommUtil.fetchEntityBy("from QueryScheme qs left join fetch qs.conditions where qs.queryScheme_key='" + qs.getQueryScheme_key() + "'");
                    }
                    for (IPickQuerySchemeListner listner : pickQuerySchemeListners) {
                        listner.addPickQueryScheme(qs);
                    }
                }
            }
        });
    }

    public QueryScheme getCommScheme() {
        QueryScheme qs = null;
        Enumeration enumt = ((CheckTreeNode) treeModel.getRoot()).breadthFirstEnumeration();
        while (enumt.hasMoreElements()) {
            CheckTreeNode c = (CheckTreeNode) enumt.nextElement();
            if (c.isSelected() && c.getUserObject() instanceof QueryScheme) {
                qs = (QueryScheme) c.getUserObject();
                break;
            }
        }
        return qs;
    }

    public List<QueryScheme> getSelectedScheme() {
        QueryScheme qs = null;
        List<QueryScheme> list = new ArrayList<QueryScheme>();
        Enumeration enumt = ((CheckTreeNode) treeModel.getRoot()).breadthFirstEnumeration();
        while (enumt.hasMoreElements()) {
            CheckTreeNode c = (CheckTreeNode) enumt.nextElement();
            if (c.isSelected() && c.getUserObject() instanceof QueryScheme) {
                qs = (QueryScheme) c.getUserObject();
                list.add(qs);
            }
        }
        return list;
    }

    public JTree getSchemeTree() {
        return schemeTree;
    }

    class QueryTreeModel extends DefaultTreeModel {

        private CheckTreeNode rootNode = new CheckTreeNode("所有方案");

        public CheckTreeNode getR() {
            return rootNode;
        }

        private void buildTree(List list) {
            rootNode.removeAllChildren();
            CheckTreeNode tmp = rootNode;
            for (Object obj : list) {
                QueryScheme position = (QueryScheme) obj;
                CheckTreeNode newNode = new CheckTreeNode(position);
                tmp.add(newNode);
            }
        }

        public QueryTreeModel(List list) {
            super(new CheckTreeNode());
            this.setRoot(rootNode);
            buildTree(list);
        }

        public void addQueryNode(CheckTreeNode qs) {
            rootNode.add(qs);
        }
    }
}
