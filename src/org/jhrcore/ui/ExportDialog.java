/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ExportDialog.java
 *
 * Created on 2009-3-28, 22:35:58
 */
package org.jhrcore.ui;

import java.awt.event.MouseEvent;
import java.util.Date;
import com.foundercy.pf.control.table.FBaseTableColumn;
import com.foundercy.pf.control.table.FTable;
import com.foundercy.pf.control.table.FTableModel;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFWriter;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingbinding.JListBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.jhrcore.client.CommUtil;
import org.jhrcore.util.SysUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.comm.CodeManager;
import org.jhrcore.util.PublicUtil;
import org.jhrcore.entity.ExportDetail;
import org.jhrcore.entity.ExportScheme;
import org.jhrcore.util.UtilTool;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.iservice.impl.CommImpl;
import org.jhrcore.msg.CommMsg;
import org.jhrcore.rebuild.EntityBuilder;
import org.jhrcore.ui.action.CloseAction;
import org.jhrcore.ui.renderer.HRRendererView;
import org.jhrcore.util.ExportUtil;
import org.jhrcore.util.FileChooserUtil;
import org.jhrcore.util.MsgUtil;

/**
 *
 * @author mxliteboss
 */
public class ExportDialog extends javax.swing.JDialog {

    private JList jlsRight = new JList();
    private List<TempFieldInfo> right_list = new ArrayList<TempFieldInfo>();
    private List scheme_list = new ArrayList();
    private JListBinding right_binding;
    private Class cur_class;
    private ExportScheme curExportScheme = null;
    private JComboBoxBinding scheme_binding;
    private FTable fTable;
//    private JFileChooser fileChooser;
    private List<TempFieldInfo> all_fields = new ArrayList<TempFieldInfo>();
    private JTree fieldTree;
    private HashSet<String> exist_fields = new HashSet<String>();
    private ShowFieldTreeModel model;
    private Logger log = Logger.getLogger(ExportDialog.class.getName());
    private Hashtable<String, String> oname_table = new Hashtable<String, String>();//其他表名
    private Hashtable<String, String> zhu_table = new Hashtable<String, String>();

    private void setCurExportScheme(ExportScheme curExportScheme, List<TempFieldInfo> all_fields) {
        this.curExportScheme = curExportScheme;
        right_binding.unbind();
        right_list.clear();
        List<TempFieldInfo> un_select_fields = new ArrayList<TempFieldInfo>();
        un_select_fields.addAll(all_fields);
        model.rebuild();
        if (curExportScheme != null) {
            jcbPlus.setSelected(curExportScheme.isPlus_flag());
            jcbOrder.setSelected(curExportScheme.isOrder_flag());
            if (curExportScheme.getExportDetails() != null && curExportScheme.getExportDetails().size() > 0) {
                List<ExportDetail> details = new ArrayList<ExportDetail>();
                details.addAll(curExportScheme.getExportDetails());
                SysUtil.sortListByInteger(details, "order_no");
                List<DefaultMutableTreeNode> nodes = new ArrayList<DefaultMutableTreeNode>();
                for (ExportDetail ed : details) {
                    Enumeration enumt = ((DefaultMutableTreeNode) model.getRoot()).depthFirstEnumeration();
                    while (enumt.hasMoreElements()) {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumt.nextElement();
                        Object obj = node.getUserObject();
                        if (obj instanceof String) {
                            continue;
                        }
                        TempFieldInfo tfi = (TempFieldInfo) obj;
                        if (tfi.getField_name().equals(ed.getField_name())) {
                            right_list.add(tfi);
                            nodes.add(node);
                            break;
                        }
                    }
                }
                for (DefaultMutableTreeNode node : nodes) {
                    node.removeFromParent();
                }
            }
            fieldTree.updateUI();
        } else {
            jcbPlus.setSelected(false);
            jcbOrder.setSelected(false);
        }
        right_binding.bind();
    }

    /** Creates new form ExportDialog */
    public ExportDialog(Class entity_class, FTable fTable, String module_code, List<TempFieldInfo> fields) {
        super();
        this.setTitle("选择输出字段");
        this.fTable = fTable;
        this.all_fields = fields;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cur_class = entity_class;
        initComponents();
        initOthers();
        setupEvents();
    }

    protected void refreshScheme() {
        curExportScheme.setEntity_name(cur_class.getSimpleName());
        curExportScheme.setPerson_code(UserContext.person_code);
        curExportScheme.setScheme_titile(jtfTitle.getText().equals("") ? "JHR导出文件" : jtfTitle.getText());
        curExportScheme.setOrder_flag(jcbOrder.isSelected());
        curExportScheme.setPlus_flag(jcbPlus.isSelected());
        List<ExportDetail> details = new ArrayList<ExportDetail>();
        for (int i = 0; i < right_list.size(); i++) {
            TempFieldInfo tmp_detail = (TempFieldInfo) right_list.get(i);
            ExportDetail detail = (ExportDetail) UtilTool.createUIDEntity(ExportDetail.class);
            detail.setField_name(tmp_detail.getField_name());
            detail.setField_caption(tmp_detail.getCaption_name());
            detail.setOrder_no(i);
            detail.setEntity_name(tmp_detail.getEntity_name());
            detail.setEntity_caption(tmp_detail.getEntity_caption());
            detail.setExportScheme(curExportScheme);
            detail.setField_type(tmp_detail.getField_type());
            detail.setFormat(tmp_detail.getFormat());
            details.add(detail);
        }
        curExportScheme.setExportDetails(details);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlLeft = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jcbbScheme = new javax.swing.JComboBox();
        btnSave = new javax.swing.JButton();
        btnDel = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jcbPlus = new javax.swing.JCheckBox();
        jcbOrder = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pnlRight = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnRemoveAll = new javax.swing.JButton();
        btnUp = new javax.swing.JButton();
        btnDown = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jtfTitle = new javax.swing.JTextField();
        btnAddall = new javax.swing.JButton();
        jcbOpen = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jcbType = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);

        pnlLeft.setLayout(new java.awt.BorderLayout());

        jLabel1.setText("方案：");

        btnSave.setText("保存");

        btnDel.setText("删除");

        btnOk.setText("确定");

        btnCancel.setText("取消");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbbScheme, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jcbbScheme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave)
                    .addComponent(btnDel)
                    .addComponent(btnOk)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        jcbPlus.setText("是否要汇总行");

        jcbOrder.setText("是否显示序号");

        jLabel2.setText("未选字段：");

        jLabel3.setText("已选字段：");

        pnlRight.setLayout(new java.awt.BorderLayout());

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/select_one.png"))); // NOI18N

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/remove_one.png"))); // NOI18N

        btnRemoveAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/remove_all.png"))); // NOI18N

        btnUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/move_up.png"))); // NOI18N

        btnDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/move_down.png"))); // NOI18N

        jLabel4.setText("报表标题：");

        btnAddall.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/select_all.png"))); // NOI18N

        jcbOpen.setText("导出直接打开");

        jLabel5.setText("导出格式：");

        jcbType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "XLS", "DBF" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(pnlLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAddall, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnRemoveAll, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel2))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(btnUp, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(btnDown, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3)
                            .addComponent(pnlRight, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jcbPlus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jcbOrder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbOpen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbType, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(btnAddall, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRemoveAll, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnlLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(pnlRight, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnUp, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnDown, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtfTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbPlus)
                    .addComponent(jcbOrder)
                    .addComponent(jcbOpen)
                    .addComponent(jLabel5)
                    .addComponent(jcbType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddall;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnDown;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnRemoveAll;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JCheckBox jcbOpen;
    private javax.swing.JCheckBox jcbOrder;
    private javax.swing.JCheckBox jcbPlus;
    private javax.swing.JComboBox jcbType;
    private javax.swing.JComboBox jcbbScheme;
    private javax.swing.JTextField jtfTitle;
    private javax.swing.JPanel pnlLeft;
    private javax.swing.JPanel pnlRight;
    // End of variables declaration//GEN-END:variables

    private void initOthers() {
        if (all_fields.isEmpty()) {
            all_fields.addAll(EntityBuilder.getCommFieldInfoListOf(cur_class, EntityBuilder.COMM_FIELD_VISIBLE));
        }
        right_binding = SwingBindings.createJListBinding(UpdateStrategy.READ_WRITE, right_list, jlsRight);
        right_binding.bind();
        model = new ShowFieldTreeModel(all_fields);
        fieldTree = new JTree(model);
        HRRendererView.getCommMap().initTree(fieldTree);
        fieldTree.setRootVisible(false);
        fieldTree.setShowsRootHandles(true);
        pnlLeft.add(new JScrollPane(fieldTree), BorderLayout.CENTER);
        pnlRight.add(new JScrollPane(jlsRight), BorderLayout.CENTER);
        scheme_list.addAll(CommUtil.fetchEntities("from ExportScheme es left join fetch es.exportDetails where es.entity_name='" + cur_class.getSimpleName() + "' and es.person_code='" + UserContext.person_code + "'"));
        scheme_list.add(0, createNewScheme());
        scheme_binding = SwingBindings.createJComboBoxBinding(UpdateStrategy.READ_WRITE, scheme_list, jcbbScheme);
        scheme_binding.bind();
//        fileChooser = UserContext.getFileChooser();
//        fileChooser.setDialogTitle("选择您要导出的Excel文件:");
//        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        SearchTreeFieldDialog.doQuickSearch("未选字段树", fieldTree);
        SearchListFieldDialog.doQuickSearch("已选字段", jlsRight);
    }
    //创建一个新的方案，并且将当前界面的字段加入到默认字段

    private ExportScheme createNewScheme() {
        ExportScheme scheme = (ExportScheme) UtilTool.createUIDEntity(ExportScheme.class);
        List<String> fields = fTable.getFields();
        int i = 0;
        List<ExportDetail> details = new ArrayList<ExportDetail>();
        for (String field : fields) {
            for (TempFieldInfo tfi : all_fields) {
                if (tfi.getField_name().equals(field)) {
                    ExportDetail ed = (ExportDetail) UtilTool.createUIDEntity(ExportDetail.class);
                    ed.setEntity_name(tfi.getEntity_name());
                    ed.setEntity_caption(tfi.getEntity_caption());
                    ed.setField_caption(tfi.getCaption_name());
                    ed.setField_name(tfi.getField_name());
                    ed.setField_type(tfi.getField_type());
                    ed.setExportScheme(scheme);
                    ed.setOrder_no(i);
                    details.add(ed);
                    i++;
                    break;
                }
            }
        }
        scheme.setExportDetails(details);
        return scheme;
    }

    private void setupEvents() {
        jlsRight.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    delDetail(false);
                }
            }
        });
        fieldTree.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (fieldTree.getSelectionPath() == null) {
                        return;
                    }
                    Object obj = ((DefaultMutableTreeNode) fieldTree.getSelectionPath().getLastPathComponent()).getUserObject();
                    if (obj instanceof TempFieldInfo) {
                        addDetail(false);
                    }
                }
            }
        });
        jcbbScheme.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Object obj = jcbbScheme.getSelectedItem();
                if (obj == null) {
                    return;
                }
                setCurExportScheme((ExportScheme) obj, all_fields);
            }
        });
        btnSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                saveScheme();
            }
        });
        btnOk.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                refreshScheme();
                export();
            }
        });
        CloseAction.doCloseAction(btnCancel);
        btnAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addDetail(false);
            }
        });
        btnAddall.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addDetail(true);
            }
        });
        btnRemove.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                delDetail(false);
            }
        });
        btnRemoveAll.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                delDetail(true);
            }
        });
        btnDel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Object obj = jcbbScheme.getSelectedItem();
                if (obj == null || ((ExportScheme) obj).getNew_flag() == 1) {
                    return;
                }
                ExportScheme exportScheme = (ExportScheme) obj;
                if (JOptionPane.showConfirmDialog(null, "确认方案\"" + exportScheme.getScheme_name() + "\"删除吗？", "确认方案删除", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null) == JOptionPane.YES_OPTION) {
                    String ex_sql = "delete from ExportDetail where exportScheme_key='" + exportScheme.getExportScheme_key() + "';";
                    ex_sql += "delete from ExportScheme where exportScheme_key='" + exportScheme.getExportScheme_key() + "';";
                    ValidateSQLResult result = CommUtil.excuteSQLs(ex_sql, ";");
                    if (result.getResult() == 0) {
                        scheme_list.remove(obj);
                        setCurExportScheme(null, all_fields);
                        scheme_binding.unbind();
                        scheme_binding.bind();
                    } else {
                        MsgUtil.showHRSaveErrorMsg(result);
                    }
                }

            }
        });
        btnUp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int select_index = jlsRight.getSelectedIndex();
                if (select_index != -1) {
                    upDetail(select_index);
                }
            }
        });
        btnDown.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int select_index = jlsRight.getSelectedIndex();
                if (select_index != -1) {
                    downDetail(select_index);
                }
            }
        });
        jcbbScheme.setSelectedIndex(0);
    }

    private void upDetail(int selectIndex) {
        TempFieldInfo tfi = (TempFieldInfo) right_list.get(selectIndex);
        right_list.remove(selectIndex);
        int cur_index = 0;
        if (selectIndex == 0) {
            cur_index = right_list.size();
            right_list.add(tfi);
        } else {
            right_list.add(selectIndex - 1, tfi);
            cur_index = selectIndex - 1;
        }
        right_binding.unbind();
        right_binding.bind();
        jlsRight.setSelectedIndex(cur_index);
    }

    private void downDetail(int selectIndex) {
        int cur_index = 0;
        TempFieldInfo tfi = (TempFieldInfo) right_list.get(selectIndex);
        right_list.remove(selectIndex);
        if (selectIndex == right_list.size()) {
            right_list.add(0, tfi);

        } else {
            right_list.add(selectIndex + 1, tfi);
            cur_index = selectIndex + 1;
        }
        right_binding.unbind();
        right_binding.bind();
        jlsRight.setSelectedIndex(cur_index);
    }

    private void saveScheme() {
        if (curExportScheme == null) {
            return;
        }
        if (right_list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请选择显示字段!");
            return;
        }
        boolean isNew = curExportScheme.getNew_flag() == 1;
        if (isNew) {
            String scheme_name = JOptionPane.showInputDialog(this, "请输入方案名：");
            if (scheme_name == null) {
                return;
            }
            if (scheme_name.replace(" ", "").equals("")) {
                JOptionPane.showMessageDialog(this, "方案名不可为空!");
                return;
            }
            curExportScheme.setScheme_name(scheme_name);
        }
        refreshScheme();
        ValidateSQLResult result = CommImpl.saveExportScheme(curExportScheme);
        if (result.getResult() == 0) {
            int index = jcbbScheme.getSelectedIndex();
            curExportScheme.setNew_flag(0);
            scheme_binding.unbind();
            scheme_list.set(index, curExportScheme);
            if (isNew) {
                scheme_list.add(0, createNewScheme());
            }
            scheme_binding.bind();
            jcbbScheme.setSelectedIndex(index + 1);
        } else {
            MsgUtil.showHRSaveErrorMsg(result);
        }
    }

    private void addDetail(boolean all_flag) {
        List<TempFieldInfo> field_infos = new ArrayList<TempFieldInfo>();
        DefaultMutableTreeNode next_node = null;
        if (all_flag) {
            Enumeration enumt = ((DefaultMutableTreeNode) model.getRoot()).breadthFirstEnumeration();
            List<DefaultMutableTreeNode> nodes = new ArrayList<DefaultMutableTreeNode>();
            while (enumt.hasMoreElements()) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumt.nextElement();
                Object obj = node.getUserObject();
                if (obj instanceof String) {
                    continue;
                }
                TempFieldInfo tfi = (TempFieldInfo) obj;
                field_infos.add(tfi);
                nodes.add(node);
            }
            for (DefaultMutableTreeNode node : nodes) {
                next_node = node.getNextSibling();
                if (next_node == null) {
                    next_node = (DefaultMutableTreeNode) node.getParent();
                }
                node.removeFromParent();
            }
        } else {
            TreePath[] select_path = fieldTree.getSelectionPaths();
            Hashtable<String, TreePath> tree_path_keys = new Hashtable<String, TreePath>();
            for (TreePath tp : select_path) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tp.getLastPathComponent();
                if (!(node.getUserObject() instanceof TempFieldInfo)) {
                    Enumeration enumt = node.children();
                    while (enumt.hasMoreElements()) {
                        DefaultMutableTreeNode child = (DefaultMutableTreeNode) enumt.nextElement();
                        TempFieldInfo tfi = (TempFieldInfo) child.getUserObject();
                        if (tree_path_keys.get(tfi.getField_name()) == null) {
                            tree_path_keys.put(tfi.getField_name(), new TreePath(child.getPath()));
                        }
                    }
                } else {
                    tree_path_keys.put(((TempFieldInfo) node.getUserObject()).getField_name(), tp);
                }
            }
            for (TreePath tp : tree_path_keys.values()) {
                Object[] objs = tp.getPath();
                for (Object obj : objs) {
                    if (obj instanceof DefaultMutableTreeNode) {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) obj;
                        next_node = node.getNextSibling();
                        if (next_node == null) {
                            next_node = (DefaultMutableTreeNode) node.getParent();
                        }
                        if (node.getUserObject() instanceof TempFieldInfo) {
                            field_infos.add((TempFieldInfo) node.getUserObject());
                            node.removeFromParent();
                        }
                    }
                }
            }
        }
        fieldTree.clearSelection();
        fieldTree.addSelectionPath(new TreePath(next_node.getPath()));
        fieldTree.updateUI();
        SysUtil.sortListByInteger(field_infos, "order_no");
        Object select_obj = null;
        for (TempFieldInfo tfi : field_infos) {
            if (exist_fields.contains(tfi.getField_name())) {
                continue;
            }
            right_list.add(tfi);
            select_obj = tfi;
            exist_fields.add(tfi.getField_name());
        }
        right_binding.unbind();
        right_binding.bind();
        if (select_obj != null) {
            jlsRight.setSelectedValue(select_obj, true);
        }
    }

    private void delDetail(boolean all_flag) {
        if (jlsRight.getSelectedValue() == null && !all_flag) {
            return;
        }
        DefaultMutableTreeNode last_node = null;
        int[] scheme_indexs = jlsRight.getSelectedIndices();
        int last_index = 0;
        if (all_flag) {
            if (right_list.isEmpty()) {
                return;
            }
            exist_fields.clear();
            right_list.clear();
            ((ShowFieldTreeModel) fieldTree.getModel()).rebuild();
        } else {
            for (int cur_index : scheme_indexs) {
                TempFieldInfo tfi = right_list.get(cur_index);
                exist_fields.remove(tfi.getField_name());
                last_node = ((ShowFieldTreeModel) fieldTree.getModel()).addNode(tfi);
            }
            for (int i = scheme_indexs.length - 1; i >= 0; i--) {
                last_index = scheme_indexs[i];
                right_list.remove(scheme_indexs[i]);
            }
        }
        if (last_node == null) {
            last_node = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) fieldTree.getModel().getRoot()).getFirstChild();
        }
        if (last_node == null) {
            last_node = (DefaultMutableTreeNode) fieldTree.getModel().getRoot();
        }
        fieldTree.clearSelection();
        TreePath path = new TreePath(last_node.getPath());
        fieldTree.expandPath(path);
        fieldTree.scrollPathToVisible(path);
        fieldTree.addSelectionPath(path);
        fieldTree.updateUI();
        last_index = last_index - 1;
        if (last_index < 0) {
            last_index = 0;
        }
        right_binding.unbind();
        right_binding.bind();
        if (right_list.size() > 0) {
            jlsRight.setSelectedIndex(last_index);
        }
        jlsRight.updateUI();
    }

    private void export() {
        String type = jcbType.getSelectedItem().toString();
        File file = FileChooserUtil.getFileForExport(CommMsg.SELECTFILE_MESSAGE, type);
        if (file == null) {
            return;
        }
        boolean isXls = type.equals("XLS");
        if (isXls) {
            exportExcel(file, jcbOrder.isSelected());
        } else {
            exportDBF(file);
        }

    }

    private void exportExcel(final File selectedFile, final boolean needNo) {
        ShowProcessDlg.startProcess(ExportDialog.this);
        ExportDialog.this.setEnabled(false);
        Runnable run = new Runnable() {

            @Override
            public void run() {
                boolean isopen = false;
                try {
                    File file = null;
                    String file_path = selectedFile.getPath();
                    Hashtable<String, WritableCellFormat> formats = new Hashtable<String, WritableCellFormat>();
                    if (!file_path.toLowerCase().endsWith(".xls") && !file_path.toLowerCase().endsWith(".xlsx")) {
                        file_path = file_path + ".xls";
                    }
                    file = new File(file_path);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    Hashtable<String, WritableCellFormat> commFormats = ExportUtil.getCommCellFormats();
                    WritableWorkbook workbook = Workbook.createWorkbook(file);
                    WritableSheet sheet = workbook.createSheet("First Sheet", 0);
                    Label label = new Label(0, 0, curExportScheme.getScheme_titile(), commFormats.get("title"));
                    WritableCellFeatures cellFeatures = new WritableCellFeatures();
                    cellFeatures.setComment(curExportScheme.getEntity_name());
                    label.setCellFeatures(cellFeatures);
                    sheet.addCell(label);
                    sheet.mergeCells(0, 0, Math.max(curExportScheme.getExportDetails().size() - (needNo ? 0 : 1), 0), 0);
                    int j = 0;
                    List<ExportDetail> exportDetails = new ArrayList<ExportDetail>();
                    exportDetails.addAll(curExportScheme.getExportDetails());
                    SysUtil.sortListByInteger(exportDetails, "order_no");
                    if (needNo) {
                        label = new Label(j, 1, "序号", commFormats.get("title1"));
                        cellFeatures = new WritableCellFeatures();
                        cellFeatures.setComment("序号");
                        label.setCellFeatures(cellFeatures);
                        sheet.addCell(label);
                        j++;
                    }
                    for (ExportDetail exportDetail : exportDetails) {
                        for (int i = 0; i < fTable.getColumnModel().getColumnCount(); i++) {
                            FBaseTableColumn column = fTable.getColumnModel().getColumn(i);
                            if (column.getId().equals(exportDetail.getField_name())) {
                                sheet.setColumnView(j, column.getPreferredWidth() / 5);
                                break;
                            }
                        }
                        label = new Label(j, 1, exportDetail.getField_caption(), commFormats.get("title1"));
                        cellFeatures = new WritableCellFeatures();
                        cellFeatures.setComment(exportDetail.getField_name());
                        label.setCellFeatures(cellFeatures);
                        sheet.addCell(label);
                        j++;
                    }
                    Hashtable<String, HashMap<String, Object>> other_datas = new Hashtable<String, HashMap<String, Object>>();
                    Hashtable<String, String> sql_keys = new Hashtable<String, String>();
                    Hashtable<String, TempFieldInfo> field_keys = new Hashtable<String, TempFieldInfo>();
                    FTableModel fm = (FTableModel) fTable.getModel();
                    String key = EntityBuilder.getEntityKey(fm.getEntityClass());
                    oname_table.clear();
                    zhu_table.clear();
                    for (String t_entity_name : fTable.getOther_entitys().keySet()) {
                        String temp_t = fTable.getOther_entitys().get(t_entity_name);
                        String[] temp_ts = temp_t.split(",");
                        for (String temp_table : temp_ts) {
                            temp_table = temp_table.trim();
                            if (temp_table.indexOf(" ") > 0 && t_entity_name.equalsIgnoreCase(temp_table.substring(0, temp_table.indexOf(" ")))
                                    && oname_table.get(t_entity_name) == null) {
                                String t_other_name = temp_table.substring(temp_table.lastIndexOf(" ") + 1, temp_table.length()) + ".";
                                oname_table.put(t_entity_name, t_other_name);
                            }
                            if (temp_table.indexOf(" ") > 0 && cur_class.getSimpleName().equalsIgnoreCase(temp_table.substring(0, temp_table.indexOf(" ")))
                                    && zhu_table.get(t_entity_name) == null) {
                                String t_other_name = temp_table.substring(temp_table.lastIndexOf(" ") + 1, temp_table.length()) + ".";
                                zhu_table.put(t_entity_name, t_other_name);
                            }
                        }
                    }
                    for (ExportDetail exportDetail : exportDetails) {
                        String fieldName = exportDetail.getField_name();
                        TempFieldInfo tfi = null;
                        String other_tname = "";//加别名，用于查询，防止有相当字段名不同表的字段
                        if (!fieldName.startsWith("#")) {
                            tfi = EntityBuilder.getTempFieldInfoByName(fm.getEntityClass().getName(), fieldName, true);
                        } else {
                            tfi = EntityBuilder.getTempFieldInfoByName(fieldName);//此处返回的tfi.getField_name  不包括#
                            other_tname = oname_table.get(tfi.getEntity_name());
                        }
                        if (tfi == null) {
                            continue;
                        }
                        if (other_tname == null) {
                            other_tname = "";
                        }
                        String tmp = tfi.getField_name();
                        tfi.setField_name(fieldName);
                        initFormats(tfi, formats);
                        String[] fieldNames = fieldName.split("\\.");
                        String entityName = fieldNames[0].substring(1);
                        String sql = sql_keys.get(entityName);
                        if (sql == null) {
                            sql = "";
                        }
                        tfi.setField_name(tmp.replace("_code_", ""));
                        field_keys.put(fieldName, tfi);
                        sql = sql + "," + other_tname + tfi.getField_name();
                        sql_keys.put(entityName, sql);
                    }
                    int i = 2;
                    for (Object obj : fm.getAllObjects()) {
                        int col = 0;
                        if (needNo) {
                            label = new Label(0, i, (i - 1) + "", commFormats.get("text"));
                            sheet.addCell(label);
                            col++;
                        }
                        for (ExportDetail exportDetail : exportDetails) {
                            String fieldName = exportDetail.getField_name();
                            Object tmp_obj;
                            WritableCell cell = null;
                            if (fieldName.startsWith("#")) {
                                TempFieldInfo tfi = field_keys.get(fieldName);
                                HashMap<String, Object> hm = getOtherData(fTable, tfi.getEntity_name(), sql_keys.get(tfi.getEntity_name()), other_datas, i - 2, key);
                                String temp_tname = oname_table.get(tfi.getEntity_name());
                                if (temp_tname == null) {
                                    temp_tname = "";
                                }
                                tmp_obj = hm.get(temp_tname + tfi.getField_name());
                                if (tfi.getField_type().equals("Code")) {
                                    tmp_obj = CodeManager.getCodeManager().getCodeBy(tfi.getCode_type_name(), tmp_obj == null ? "@@@" : tmp_obj.toString());
                                }
                            } else {
                                tmp_obj = EditorFactory.getValueBy(obj, fieldName);
                            }
                            String type = exportDetail.getField_type().toLowerCase();
                            if (type.equals("boolean")) {
                                if (tmp_obj == null || tmp_obj.toString().equals("false") || tmp_obj.toString().equals("0")) {
                                    tmp_obj = "否";
                                } else {
                                    tmp_obj = "是";
                                }
                                label = new Label(col, i, tmp_obj == null ? "" : tmp_obj.toString(), commFormats.get("text"));
                            } else if (type.equals("date")) {
                                WritableCellFormat f = formats.get(fieldName);
                                if (tmp_obj != null && f != null) {
                                    cell = new jxl.write.DateTime(col, i, (Date) tmp_obj, f);
                                } else {
                                    label = new Label(col, i, tmp_obj == null ? "" : tmp_obj.toString(), commFormats.get("text"));
                                }
                            } else if (type.equals("int") || type.equals("integer")) {
                                Object tt_obj = SysUtil.objToInteger(tmp_obj, null);
                                if (tt_obj == null) {
                                    label = new Label(col, i, "", commFormats.get("int"));
                                } else {
                                    cell = new jxl.write.Number(col, i, new Float(tt_obj.toString()), commFormats.get("int"));
                                }
                            } else if (type.equals("float") || type.equals("bigdecimal") || type.equals("double")) {
                                Object tt_obj = SysUtil.objToFloat(tmp_obj, null);
                                if (tt_obj == null) {
                                    label = new Label(col, i, "", commFormats.get("text"));
                                } else {
                                    WritableCellFormat f = formats.get(fieldName);
                                    if (f != null) {
                                        cell = new jxl.write.Number(col, i, new BigDecimal(tmp_obj.toString()).doubleValue(), f);
                                    } else {
                                        label = new Label(col, i, tt_obj.toString(), commFormats.get("text"));
                                    }
                                }
                            } else {
                                label = new Label(col, i, tmp_obj == null ? "" : tmp_obj.toString(), commFormats.get("text"));
                            }
                            if (cell != null) {
                                sheet.addCell(cell);
                            } else if (label != null) {
                                sheet.addCell(label);
                            }
                            col++;
                            cell = null;
                            label = null;
                        }
                        i++;
                    }
                    workbook.write();
                    workbook.close();
                    if (jcbOpen.isSelected()) {
                        Runtime.getRuntime().exec("cmd /c \"" + file_path + "\"");
                    }
                } catch (WriteException ex) {
                    log.error(ex);
                } catch (IOException ex) {
                    if (ex instanceof FileNotFoundException) {
                        isopen = true;
                    }
                    log.error(ex);
                } finally {
                    ShowProcessDlg.endProcess();
                    ExportDialog.this.dispose();
                    if (isopen) {
                        JOptionPane.showMessageDialog(null, "另一个程序正在使用此文件，进程无法访问", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };
        new Thread(run).start();
    }

    private void initFormats(TempFieldInfo tfi, Hashtable<String, WritableCellFormat> formats) {
        WritableCellFormat df = null;
        if (tfi.getField_type().toLowerCase().equals("date")) {
            String format_str = tfi.getFormat();
            if (tfi.getFormat() == null || tfi.getFormat().trim().equals("")) {
                format_str = "yyyy-MM-dd";
            }
            df = new WritableCellFormat(new jxl.write.DateFormat(format_str));
        } else if (tfi.getField_type().toLowerCase().equals("float") || tfi.getField_type().toLowerCase().equals("bigdecimal")) {
            String format_str = tfi.getFormat();
            if (tfi.getFormat() == null || tfi.getFormat().trim().equals("")) {
                format_str = "0.";
                for (int i = 0; i < tfi.getField_scale(); i++) {
                    format_str = format_str + "0";
                }
            }
            format_str = format_str.replace("#.", "0.").replace(".#", ".0");
            if (format_str.equals("0.")) {
                format_str = "0";
            }
            df = new WritableCellFormat(new jxl.write.NumberFormat(format_str));
        }
        if (df != null) {
            try {
                df.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
            } catch (WriteException ex) {
                log.error(ex);
            }
            formats.put(tfi.getField_name(), df);
        }
    }

    private void exportDBF(final File selectedFile) {
        ShowProcessDlg.startProcess(ExportDialog.this);
        ExportDialog.this.setEnabled(false);
        Runnable run = new Runnable() {

            @Override
            public void run() {
                boolean isopen = false;
                try {
                    File file = null;
                    String file_path = selectedFile.getPath();
                    if (!file_path.toLowerCase().endsWith(".dbf")) {
                        file_path = file_path + ".dbf";
                    }
                    System.out.println("f:" + file_path);
                    file = new File(file_path);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    DBFWriter writer = new DBFWriter();
                    writer.setCharactersetName("GBK");
                    FileOutputStream fos = new FileOutputStream(file);
                    List<ExportDetail> exportDetails = new ArrayList<ExportDetail>();
                    exportDetails.addAll(curExportScheme.getExportDetails());
                    SysUtil.sortListByInteger(exportDetails, "order_no");
                    DBFField fields[] = new DBFField[exportDetails.size()];
                    int j = 0;
                    for (ExportDetail exportDetail : exportDetails) {
                        String type = exportDetail.getField_type().toLowerCase();
                        fields[j] = new DBFField();
                        String caption = exportDetail.getField_caption();
                        Pattern pWord = Pattern.compile("[\u4e00-\u9fa5]");//校验中文的正则表达式
                        if (pWord.matcher(caption).find() && caption.length() > 5) {//如果是中文，就只要前5个字，原因是dbf文件的列名只支持最多10个字符
                            fields[j].setName(caption.substring(0, 5));
                        } else {
//  ********这一段代码避免列名超过10个字符，造成的异常 end***************************************************//
                            fields[j].setName(caption);
                        }
//                        fields[j].setName(exportDetail.getField_caption());
                        if (type.equals("int") || type.equals("integer")) {
                            fields[j].setDataType(DBFField.FIELD_TYPE_N);
                            fields[j].setFieldLength(10);
                        } else if (type.equals("float") || type.equals("bigdecimal") || type.equals("double")) {
                            fields[j].setDataType(DBFField.FIELD_TYPE_N);
                            fields[j].setFieldLength(30);
                            fields[j].setDecimalCount(2);
                        } else if (type.equals("date")) {
                            fields[j].setDataType(DBFField.FIELD_TYPE_D);
                        } else {
                            fields[j].setDataType(DBFField.FIELD_TYPE_C);
                            fields[j].setFieldLength(255);
                        }
                        j++;
                    }
                    writer.setFields(fields);
                    Hashtable<String, HashMap<String, Object>> other_datas = new Hashtable<String, HashMap<String, Object>>();
                    Hashtable<String, String> sql_keys = new Hashtable<String, String>();
                    Hashtable<String, TempFieldInfo> field_keys = new Hashtable<String, TempFieldInfo>();
                    FTableModel fm = (FTableModel) fTable.getModel();
                    String key = EntityBuilder.getEntityKey(fm.getEntityClass());
                    oname_table.clear();
                    zhu_table.clear();
                    for (String t_entity_name : fTable.getOther_entitys().keySet()) {
                        String temp_t = fTable.getOther_entitys().get(t_entity_name);
                        String[] temp_ts = temp_t.split(",");
                        for (String temp_table : temp_ts) {
                            temp_table = temp_table.trim();
                            if (temp_table.indexOf(" ") > 0 && t_entity_name.equalsIgnoreCase(temp_table.substring(0, temp_table.indexOf(" ")))
                                    && oname_table.get(t_entity_name) == null) {
                                String t_other_name = temp_table.substring(temp_table.lastIndexOf(" ") + 1, temp_table.length()) + ".";
                                oname_table.put(t_entity_name, t_other_name);
                            }
                            if (temp_table.indexOf(" ") > 0 && cur_class.getSimpleName().equalsIgnoreCase(temp_table.substring(0, temp_table.indexOf(" ")))
                                    && zhu_table.get(t_entity_name) == null) {
                                String t_other_name = temp_table.substring(temp_table.lastIndexOf(" ") + 1, temp_table.length()) + ".";
                                zhu_table.put(t_entity_name, t_other_name);
                            }
                        }
                    }
                    for (ExportDetail exportDetail : exportDetails) {
                        String fieldName = exportDetail.getField_name();
                        TempFieldInfo tfi = null;
                        String other_tname = "";//加别名，用于查询，防止有相当字段名不同表的字段
                        if (!fieldName.startsWith("#")) {
                            tfi = EntityBuilder.getTempFieldInfoByName(fm.getEntityClass().getName(), fieldName, true);
                        } else {
                            tfi = EntityBuilder.getTempFieldInfoByName(fieldName);//此处返回的tfi.getField_name  不包括#
                            other_tname = oname_table.get(tfi.getEntity_name());
                        }
                        if (tfi == null) {
                            continue;
                        }
                        if (other_tname == null) {
                            other_tname = "";
                        }
                        String tmp = tfi.getField_name();
                        tfi.setField_name(fieldName);
                        String[] fieldNames = fieldName.split("\\.");
                        String entityName = fieldNames[0].substring(1);
                        String sql = sql_keys.get(entityName);
                        if (sql == null) {
                            sql = "";
                        }
                        tfi.setField_name(tmp.replace("_code_", ""));
                        field_keys.put(fieldName, tfi);
                        sql = sql + "," + other_tname + tfi.getField_name();
                        sql_keys.put(entityName, sql);
                    }
                    int i = 2;
                    for (Object obj : fm.getAllObjects()) {
                        Object rowData[] = new Object[exportDetails.size()];
                        int col = 0;
                        for (ExportDetail exportDetail : exportDetails) {
                            String fieldName = exportDetail.getField_name();
                            Object tmp_obj;
                            if (fieldName.startsWith("#")) {
                                TempFieldInfo tfi = field_keys.get(fieldName);
                                HashMap<String, Object> hm = getOtherData(fTable, tfi.getEntity_name(), sql_keys.get(tfi.getEntity_name()), other_datas, i - 2, key);
                                String temp_tname = oname_table.get(tfi.getEntity_name());
                                if (temp_tname == null) {
                                    temp_tname = "";
                                }
                                tmp_obj = hm.get(temp_tname + tfi.getField_name());
                                if (tfi.getField_type().equals("Code")) {
                                    tmp_obj = CodeManager.getCodeManager().getCodeBy(tfi.getCode_type_name(), tmp_obj == null ? "@@@" : tmp_obj.toString());
                                }
                            } else {
                                tmp_obj = EditorFactory.getValueBy(obj, fieldName);
                            }
                            String type = exportDetail.getField_type().toLowerCase();
                            if (type.equals("boolean")) {
                                if (tmp_obj == null || tmp_obj.toString().equals("false") || tmp_obj.toString().equals("0")) {
                                    tmp_obj = "否";
                                } else {
                                    tmp_obj = "是";
                                }
                            } else if (type.equals("date")) {
                            } else if (type.equals("float") || type.equals("bigdecimal") || type.equals("double") || type.equals("int") || type.equals("integer")) {
                                tmp_obj = SysUtil.objToDouble(tmp_obj);
                            } else {
                                tmp_obj = SysUtil.objToStr(tmp_obj);
                            }
                            rowData[col] = tmp_obj;
                            col++;
                        }
                        writer.addRecord(rowData);
                        i++;
                    }

                    writer.write(fos);
                    fos.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    log.error(ex);
                } finally {
                    ShowProcessDlg.endProcess();
                    ExportDialog.this.dispose();
                }
            }
        };
        new Thread(run).start();
    }

    private HashMap getOtherData(FTable ftable, String entityName, String fields, Hashtable<String, HashMap<String, Object>> other_datas, int index, String key_field) {
        HashMap hm = other_datas.get(entityName + "@" + index);
        if (hm != null) {
            return hm;
        }
        String entity_name = ftable.getOther_entitys().get(entityName);
        String s_where = ftable.getOther_entity_keys().get(entityName);
        if (entity_name == null || entity_name.trim().equals("")) {
            entity_name = entityName;
        }
        String the_tname = zhu_table.get(entityName);
        if (the_tname == null) {
            the_tname = "";
        }
        String sql = fields + " from " + entity_name + " where " + s_where;
        String keys = "'@@@@@@@@@'";
        int len = ftable.getObjects().size();
        for (int i = Math.max(0, index - 25); i < Math.min(ftable.getObjects().size(), index + 25); i++) {
            if (other_datas.get(entityName + "@" + i) != null) {
                continue;
            }
            Object obj_2 = ftable.getObjects().get(i);
            if (obj_2 instanceof String) {
                keys = keys + ",'" + obj_2 + "'";
            } else {
                Object key_obj = PublicUtil.getProperty(obj_2, key_field);
                keys = keys + ",'" + key_obj + "'";
            }
        }
        String tmp_sql = "select " + the_tname + key_field + sql + " in (" + keys + ")";
        ArrayList objs = CommUtil.selectSQL(tmp_sql);
        String[] sel_fields = fields.substring(1).split(",");
        for (int i = Math.max(0, index - 25); i < Math.min(len, index + 25); i++) {
            if (other_datas.get(entityName + "@" + i) == null) {
                HashMap tmp_hm = new HashMap();
                other_datas.put(entityName + "@" + i, tmp_hm);
                Object obj_2 = ftable.getObjects().get(i);
                for (int m = 0; m < objs.size(); m++) {
                    Object[] tmp_objs = (Object[]) objs.get(m);
                    Object key_val = obj_2;
                    if (!(key_val instanceof String)) {
                        key_val = PublicUtil.getProperty(obj_2, key_field);
                    }
                    if (key_val.equals(tmp_objs[0])) {
                        for (int ind = 1; ind < tmp_objs.length; ind++) {
                            tmp_hm.put(sel_fields[ind - 1].trim(), tmp_objs[ind]);
                        }
                    }
                }
                if (i == index) {
                    hm = tmp_hm;
                }
            }
        }
        return hm;
    }
}
