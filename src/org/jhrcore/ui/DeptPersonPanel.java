/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DeptPersonPanel1.java
 *
 * Created on 2009-4-1, 14:54:51
 */
package org.jhrcore.ui;

import org.jhrcore.ui.listener.IPickDeptListener;
import com.foundercy.pf.control.listener.IPickQueryExListener;
import com.foundercy.pf.control.table.FTable;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.swingbinding.SwingBindings;
import org.jhrcore.util.SysUtil;
import org.jhrcore.entity.A01;
import org.jhrcore.entity.DeptCode;
import org.jhrcore.entity.base.EntityDef;
import org.jhrcore.entity.query.QueryScheme;
import org.jhrcore.msg.CommMsg;
import org.jhrcore.msg.dept.DeptMngMsg;
import org.jhrcore.query3.IPickQuerySchemeListner;
import org.jhrcore.query3.QueryParamDialog;
import org.jhrcore.query3.QueryPanel;
import org.jhrcore.ui.listener.IPickPersonClassListener;
import org.jhrcore.ui.listener.IPickPersonListener;
import org.jhrcore.ui.listener.IPickStyleListner;
import org.jhrcore.util.ComponentUtil;
import org.jhrcore.util.ImageUtil;

/**
 *
 * @author mxliteboss
 */
public class DeptPersonPanel extends javax.swing.JPanel {

    private Class mainClass = null;//主类
    private Class personClass = A01.class;
    private String cur_person_class_name = "所有人员";
    private Class deptClass = DeptCode.class;
    private Object curDept = null;
    private Object curPerson = null;
    private QueryPanel queryPanel;
    protected List list_person = new ArrayList();
    private JButton btnShowStyle = new JButton("", ImageUtil.getIcon("refresh.png"));
    private JComboBox cmbPersonType = new JComboBox();
    public FTable ftable;
    private List<String> columnList2;
    private JTabbedPane tp_dept_query;
    private int i_showstyle = 1; // 0
    private DeptPanel deptPanel;
    private CheckTreeNode cur_node;
    private List<IPickPersonClassListener> iPickPersonClassListeners = new ArrayList<IPickPersonClassListener>();
    private List<IPickDeptListener> iPickDeptListners = new ArrayList<IPickDeptListener>();
    private List<IPickPersonListener> iPickPersonListners = new ArrayList<IPickPersonListener>();
    private List<IPickStyleListner> iPickStyleListners = new ArrayList<IPickStyleListner>();
    private List<IPickQueryExListener> iPickQueryExListeners = new ArrayList<IPickQueryExListener>();
    private ListSelectionListener person_listener;
    private TreeSelectionListener tree_listener;
    private int tabIndex = 0;
    private boolean init_flag = true;
    private boolean person_class_flag = true;
    private boolean query_tab_flag = true;
    private boolean dept_init_flag = true;
    private boolean data_table_init_flag = true;
    private boolean dept_init_selection_mode = false;
    private int show_type = 0;//为0时，按默认显示，为1时则无论如何都不会显示岗位，也不会显示右键功能
    private List<DeptCode> depts = new ArrayList();
    private Logger log = Logger.getLogger(DeptPersonPanel.class.getName());
    private ActionListener cmbPersonTypeActionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            changePersonType();
        }
    };

    public DeptPersonPanel() {
        initComponents();
    }

    public DeptPersonPanel(boolean init_flag, Class main_class, List<DeptCode> depts, int show_type) {
        this.depts = depts;
        this.init_flag = init_flag;
        this.mainClass = main_class;
        this.show_type = show_type;
        initComponents();
        if (init_flag) {
            init();
        }
    }

    public DeptPersonPanel(boolean init_flag, List<DeptCode> depts, int show_type) {
        this(init_flag, A01.class, depts, show_type);
    }

    public DeptPersonPanel(Class main_class, int show_type) {
        this(true, main_class, new ArrayList(), show_type);
    }

    public DeptPersonPanel(Class main_class, List<DeptCode> depts, int show_type) {
        this(true, main_class, depts, show_type);
    }

    public DeptPersonPanel(boolean init_flag, Class main_class, List<DeptCode> depts) {
        this(init_flag, main_class, depts, 0);
    }

    public DeptPersonPanel(boolean init_flag, List<DeptCode> depts) {
        this(init_flag, A01.class, depts, 0);
    }

    /**
     * Creates new form DeptPersonPanel1
     */
    public DeptPersonPanel(Class main_class) {
        this(true, main_class, new ArrayList(), 0);
    }

    public DeptPersonPanel(Class main_class, List<DeptCode> depts) {
        this(true, main_class, depts, 0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlDept = new javax.swing.JPanel();
        pnlPeopelClass = new javax.swing.JToolBar();

        pnlDept.setPreferredSize(new java.awt.Dimension(200, 0));

        javax.swing.GroupLayout pnlDeptLayout = new javax.swing.GroupLayout(pnlDept);
        pnlDept.setLayout(pnlDeptLayout);
        pnlDeptLayout.setHorizontalGroup(
            pnlDeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 182, Short.MAX_VALUE)
        );
        pnlDeptLayout.setVerticalGroup(
            pnlDeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );

        pnlPeopelClass.setFloatable(false);
        pnlPeopelClass.setRollover(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPeopelClass, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
            .addComponent(pnlDept, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlPeopelClass, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlDept, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pnlDept;
    private javax.swing.JToolBar pnlPeopelClass;
    // End of variables declaration//GEN-END:variables

    private void initOthers() {
        if (person_class_flag) {
            cmbPersonType.setPreferredSize(new Dimension(150, this.getHeight()));
            pnlPeopelClass.add(cmbPersonType);
            pnlPeopelClass.add(btnShowStyle);
            List<EntityDef> list = SysUtil.getPersonClass();
            Binding tmp_binding = SwingBindings.createJComboBoxBinding(UpdateStrategy.READ, list, cmbPersonType);
            tmp_binding.bind();
            cmbPersonType.setSize(130, 20);
        } else {
            removePersonClass();
        }
        pnlDept.setLayout(new BorderLayout());
        if (dept_init_flag) {
            deptPanel = new DeptPanel(depts, show_type, dept_init_selection_mode);
        } else {
            deptPanel = new DeptPanel(new ArrayList(), show_type);
        }
        tp_dept_query = new JTabbedPane();
        tp_dept_query.add(DeptMngMsg.msgDept.toString(), deptPanel);
        if (query_tab_flag) {
            queryPanel = new QueryPanel(mainClass, CommMsg.msgCommQuery.toString());
            tp_dept_query.add(CommMsg.msgCommQuery.toString(), queryPanel);
        }
        if (data_table_init_flag) {
            columnList2 = new ArrayList<String>();
            columnList2.add("a0190");
            columnList2.add("a0101");
            ftable = new FTable(mainClass, false, false);
            ftable.setFields(columnList2);
        }
    }

    public void init() {
        initOthers();
        setupEvents();
        changeShowStyle();
    }

    private void setupEvents() {
        tree_listener = new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                Object obj = e.getPath().getLastPathComponent();
                if (obj instanceof CheckTreeNode) {
                    CheckTreeNode node = (CheckTreeNode) obj;
                    cur_node = node;
                    curDept = node.getUserObject();
                    for (IPickDeptListener listner : iPickDeptListners) {
                        listner.pickDept(curDept);
                    }
                }
            }
        };
        cmbPersonType.addActionListener(cmbPersonTypeActionListener);

        person_listener = new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (curPerson == ftable.getCurrentRow()) {
                    return;
                }
                curPerson = ftable.getCurrentRow();
                for (IPickPersonListener listener : iPickPersonListners) {
                    listener.pickPerson(curPerson);
                }
            }
        };

        if (query_tab_flag) {
            queryPanel.addPickQuerySchemeListner(new IPickQuerySchemeListner() {

                @Override
                public void addPickQueryScheme(QueryScheme qs) {
                    if (!QueryParamDialog.ShowQueryParamDialog(queryPanel, qs)) {
                        return;
                    }
                    for (IPickQueryExListener listener : iPickQueryExListeners) {
                        listener.pickQuery(qs);
                    }
                }
            });
        }
        if (data_table_init_flag) {
            btnShowStyle.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    changeShowStyleWithListener();
                }
            });
            ftable.addListSelectionListener(person_listener);
        }
        initSelection();
        changePersonType();

    }

    public void changeShowStyleWithListener() {
        changeShowStyle();
        for (IPickStyleListner listner : iPickStyleListners) {
            listner.pickStyle(i_showstyle);
        }

    }

    public CheckTreeNode getCur_node() {
        return cur_node;
    }

    public void refreshListener() {
        person_listener.valueChanged(null);
    }
    //移除查询TAB页

    public void removeQueryTab() {
        tp_dept_query.remove(1);
        tp_dept_query.updateUI();
    }

    public void removePersonClass() {
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(pnlDept, BorderLayout.CENTER);
    }

    public void initSelection() {
        deptPanel.getDeptTree().removeTreeSelectionListener(tree_listener);
        deptPanel.initSelection();
        Object obj = ((CheckTreeNode) deptPanel.getDeptTree().getLastSelectedPathComponent()).getUserObject();
        if (obj instanceof DeptCode) {
            curDept = obj;
        } else {
            curDept = null;
        }
        deptPanel.getDeptTree().addTreeSelectionListener(tree_listener);
    }

    private void changePersonType() {
        EntityDef ed = (EntityDef) cmbPersonType.getSelectedItem();
        if (ed == null) {
            return;
        }
        String tmp_class = "org.jhrcore.entity." + ed.getEntityName();
        try {
            personClass = Class.forName(tmp_class);
            cur_person_class_name = ed.getEntityCaption();
            for (IPickPersonClassListener listener : iPickPersonClassListeners) {
                listener.pickPersonClass(personClass);
            }
        } catch (ClassNotFoundException ex) {
            log.error(ex);
        }
    }

    public void changeShowStyle() {
        i_showstyle = Math.abs(1 - i_showstyle);
        pnlDept.removeAll();
        if (i_showstyle == 0) {
            pnlDept.add(tp_dept_query, BorderLayout.CENTER);
        } else {
            JSplitPane sp_tmp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tp_dept_query, ftable);
            sp_tmp.setDividerSize(2);
            sp_tmp.setDividerLocation(270);
            pnlDept.add(sp_tmp, BorderLayout.CENTER);
        }
        pnlDept.updateUI();
        if (ftable != null) {
            List<String> keys = ftable.getAllKeys();
            list_person = keys;
            //解决替换功能导致的数据不对称问题
            int i = ftable.getCurrentRowIndex();
            ftable.removeListSelectionListener(person_listener);
            ftable.setObjects(keys);
            ftable.addListSelectionListener(person_listener);
            lacatePerson(i);
        }
    }

    public boolean locateDeptObj(Object obj) {
        if (obj == null) {
            return false;
        }
        CheckTreeNode locate_node = DeptPanel.locateDept(deptPanel.getDeptTree(), obj);
        if (locate_node != null) {
            locateDept(locate_node);
            return true;
        }
        return false;
    }

    /**
     * 定位到某个节点
     *
     * @param node:定位节点
     */
    public void locateDept(CheckTreeNode node) {
        deptPanel.getDeptTree().removeTreeSelectionListener(tree_listener);
        ComponentUtil.initTreeSelection(deptPanel.getDeptTree(), node);
        curDept = node.getUserObject();
        deptPanel.getDeptTree().addTreeSelectionListener(tree_listener);
    }

    public void locateRoot() {
        locateType("所有人员");
        CheckTreeNode node = (CheckTreeNode) ((CheckTreeNode) getDeptPanel().getDeptTree().getModel().getRoot()).getFirstChild();
        locateDept(node);
    }

    /**
     * 定位人员到某一行
     *
     * @param row：行数
     */
    public void lacatePerson(int row) {
        ftable.removeListSelectionListener(person_listener);
        if (row < ftable.getObjects().size() && row >= 0) {
            ftable.setRowSelectionInterval(row, row);
            ftable.getVerticalScrollBar().setValue((row - 5) * ftable.getRowHeight());
        }
        ftable.addListSelectionListener(person_listener);
        ftable.updateUI();
    }

    public void locateDeptAndType(A01 bp) {
        locateType(bp.getA0191());
        locateDeptObj(bp.getDeptCode());
        locatePerson(bp);
    }

    public void locatePerson(A01 bp) {
        ftable.removeListSelectionListener(person_listener);
        int row = 0;
        if (bp != null) {
            List list = ftable.getObjects();
            for (int i = 0; i < list.size(); i++) {
                Object obj = list.get(i);
                if (obj instanceof A01) {
                    A01 bp1 = (A01) list.get(i);
                    if (bp1.getA01_key().equals(bp.getA01_key())) {
                        row = i;
                        break;
                    }
                } else {
                    if (obj.toString().equals(bp.getA01_key())) {
                        row = i;
                        break;
                    }
                }
            }
        }
        ftable.setRowSelectionInterval(row, row);
        int cur_height = ftable.getVerticalScrollBar().getValue();
        int row_height = ftable.getRowHeight();
        int row_heights = row * row_height;
        if (cur_height > row_heights || cur_height < (row_heights - ftable.getVerticalScrollBar().getHeight())) {
            ftable.getVerticalScrollBar().setValue((row - 5) * row_height);
        }
        ftable.addListSelectionListener(person_listener);
        person_listener.valueChanged(null);
        ftable.updateUI();
    }

    public void locatePerson(String bp) {
        ftable.removeListSelectionListener(person_listener);
        int row = 0;
        if (bp != null) {
            List list = ftable.getAllKeys();
            for (int i = 0; i < list.size(); i++) {
                Object obj = list.get(i);
                if (obj.toString().equals(bp)) {
                    row = i;
                    break;
                }
            }
        }
        ftable.setRowSelectionInterval(row, row);
        int cur_height = ftable.getVerticalScrollBar().getValue();
        int row_height = ftable.getRowHeight();
        int row_heights = row * row_height;
        if (cur_height > row_heights || cur_height < (row_heights - ftable.getVerticalScrollBar().getHeight())) {
            ftable.getVerticalScrollBar().setValue((row - 5) * row_height);
        }
        ftable.addListSelectionListener(person_listener);
        person_listener.valueChanged(null);
        ftable.updateUI();
    }

    /**
     * 定位人员类别
     *
     * @param entity_name：传入的人员类别名称,如：在职人员
     */
    public void locateType(String entity_name) {
        cmbPersonType.removeActionListener(cmbPersonTypeActionListener);
        for (int i = 0; i < cmbPersonType.getItemCount(); i++) {
            EntityDef entityDef = (EntityDef) cmbPersonType.getItemAt(i);
            if (entity_name.equals(entityDef.getEntityCaption())) {
                cmbPersonType.setSelectedIndex(i);
                String tmp_class = "org.jhrcore.entity." + entityDef.getEntityName();
                try {
                    personClass = Class.forName(tmp_class);
                    cur_person_class_name = entityDef.getEntityCaption();
                } catch (ClassNotFoundException ex) {
                    log.error(ex);
                }
                break;
            }
        }
        cmbPersonType.addActionListener(cmbPersonTypeActionListener);
    }

    public void delCurRow() {
        int si = ftable.getObjects().size();
        int a = ftable.getCurrentRowIndex();
        if (a == -1) {
            return;
        }
        ftable.deleteRow(a);
        if (si > 0) {
            if (a >= si - 1) {
                ftable.setRowSelectionInterval(si - 2, si - 2);
            } else {
                ftable.setRowSelectionInterval(a, a);
            }
        }
        ftable.updateUI();
    }

    public int getCurRow() {
        return ftable.getCurrentRowIndex();
    }

    public Object getCurDept() {
        return curDept;
    }

    public Object getCurPerson() {
        return curPerson;
    }

    public Class getDeptClass() {
        return deptClass;
    }

    public int getI_showstyle() {
        return i_showstyle;
    }

    public Class getMainClass() {
        return mainClass;
    }

    public Class getPersonClass() {
        return personClass;
    }

    public String getPersonClassName() {
        return cur_person_class_name;
    }

    public List getList_person() {
        return list_person;
    }

    public void setList_person(List list_person) {
        this.list_person.clear();
        this.list_person.addAll(list_person);
        ftable.setObjects(this.list_person);
        ftable.setRowSelectionInterval(0, 0);
    }

    public void setList_person(List list_person, int row) {
        this.list_person.clear();
        this.list_person.addAll(list_person);
        ftable.setObjects(this.list_person);
        if (row == -1) {
            ftable.setRowSelectionInterval(0, 0);
        } else {
            ftable.setRowSelectionInterval(row, row);
        }
        int cur_height = ftable.getVerticalScrollBar().getValue();
        int row_height = ftable.getRowHeight();
        int row_heights = row * row_height;
        if (cur_height > row_heights || cur_height < (row_heights - ftable.getVerticalScrollBar().getHeight())) {
            ftable.getVerticalScrollBar().setValue((row - 5) * row_height);
        }
        ftable.updateUI();
    }

    public FTable getFTable() {
        return ftable;
    }

    public DeptPanel getDeptPanel() {
        return deptPanel;
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public boolean isDept_init_flag() {
        return dept_init_flag;
    }

    public void setDept_init_flag(boolean dept_init_flag) {
        this.dept_init_flag = dept_init_flag;
    }

    public boolean isInit_flag() {
        return init_flag;
    }

    public void setInit_flag(boolean init_flag) {
        this.init_flag = init_flag;
    }

    public boolean isPerson_class_flag() {
        return person_class_flag;
    }

    public void setPerson_class_flag(boolean person_class_flag) {
        this.person_class_flag = person_class_flag;
    }

    public boolean isQuery_tab_flag() {
        return query_tab_flag;
    }

    public void setQuery_tab_flag(boolean query_tab_flag) {
        this.query_tab_flag = query_tab_flag;
    }

    public boolean isData_table_init_flag() {
        return data_table_init_flag;
    }

    public void setData_table_init_flag(boolean data_table_init_flag) {
        this.data_table_init_flag = data_table_init_flag;
    }

    public void addPickPersonClassListner(IPickPersonClassListener listner) {
        iPickPersonClassListeners.add(listner);
    }

    public void addPickDeptListener(IPickDeptListener listner) {
        iPickDeptListners.add(listner);
    }

    public void addPickPersonListener(IPickPersonListener listner) {
        iPickPersonListners.add(listner);
    }

    public void addPickStyleListner(IPickStyleListner listner) {
        iPickStyleListners.add(listner);
    }

    public void addPickQueryExListener(IPickQueryExListener listener) {
        iPickQueryExListeners.add(listener);
    }

    public void delPickQueryExListener(IPickQueryExListener listener) {
        iPickQueryExListeners.remove(listener);
    }

    public void delPickDeptListner(IPickDeptListener listner) {
        iPickDeptListners.remove(listner);
    }

    public void delPickPersonListner(IPickPersonListener listner) {
        iPickPersonListners.remove(listner);
    }

    public void delPickStyleListner(IPickStyleListner listner) {
        iPickStyleListners.remove(listner);
    }

    public void delPickPersonClassListener(IPickPersonClassListener listner) {
        iPickPersonClassListeners.remove(listner);
    }

    public void setI_showstyle(int i_showstyle) {
        this.i_showstyle = i_showstyle;
    }

    public JPanel getPnlDept() {
        return pnlDept;
    }

    public void setMainClass(Class mainClass) {
        this.mainClass = mainClass;
    }

    public List getSelectedPersons() {
        return ftable.getAllSelectObjects();
    }

    public QueryPanel getQueryPanel() {
        return queryPanel;
    }

    public boolean isDept_init_selection_mode() {
        return dept_init_selection_mode;
    }

    public void setDept_init_selection_mode(boolean dept_init_selection_mode) {
        this.dept_init_selection_mode = dept_init_selection_mode;
    }

    public void removecmBPersonType() {
        pnlPeopelClass.remove(0);
    }

    public void removecmBShowStyle() {
        pnlPeopelClass.remove(1);
    }
}
