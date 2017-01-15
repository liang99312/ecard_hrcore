package com.foundercy.pf.control.table;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;

import com.foundercy.pf.control.Compound;
import com.foundercy.pf.control.Control;
import com.foundercy.pf.control.FCheckBox;
import com.foundercy.pf.control.listener.IPickColumnReplaceListener;
import com.foundercy.pf.control.util.SubControlFinder;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.DateFormatter;
import org.jhrcore.client.CommUtil;
import org.jhrcore.util.SysUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.util.PublicUtil;
import com.foundercy.pf.control.listener.IPickColumnSumListener;
import com.foundercy.pf.control.listener.IPickFieldOrderListener;
import com.foundercy.pf.control.listener.IPickFieldSetListener;
import com.foundercy.pf.control.listener.IPickPopupListener;
import com.foundercy.pf.control.listener.IPickQueryExListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.HashSet;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import org.apache.log4j.Logger;
import org.jfree.ui.PaintSample;
import org.jhrcore.comm.BeanManager;
import org.jhrcore.util.DbUtil;
import org.jhrcore.client.report.ReportPanel;
import org.jhrcore.comm.ConfigManager;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.EnumHint;
import org.jhrcore.entity.annotation.FieldAnnotation;
import org.jhrcore.entity.annotation.ObjectListHint;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.entity.query.QueryScheme;
import org.jhrcore.entity.showstyle.ColumnSum;
import org.jhrcore.entity.showstyle.ShowScheme;
import org.jhrcore.entity.showstyle.ShowSchemeDetail;
import org.jhrcore.entity.showstyle.ShowSchemeOrder;
import org.jhrcore.query3.QueryParamDialog;
import org.jhrcore.query3.QuerySchemeDialog;
import org.jhrcore.rebuild.EntityBuilder;
import org.jhrcore.ui.ColumnReplacePanel;
import org.jhrcore.ui.ColumnSumDialog;
import org.jhrcore.ui.ContextManager;
import org.jhrcore.ui.ExportDataDialog;
import org.jhrcore.ui.ExportDialog;
import org.jhrcore.ui.FormulaTextDialog;
import org.jhrcore.ui.ModelFrame;
import org.jhrcore.ui.ShowFieldDialog2;
import org.jhrcore.ui.ShowOrderDialog;
import org.jhrcore.ui.renderer.TempCodeEditor;
import org.jhrcore.ui.UIItemDetail;
import org.jhrcore.ui.UIItemGroup;
import org.jhrcore.ui.listener.IPickSelectListener;
import org.jhrcore.ui.task.IModuleCode;
import org.jhrcore.util.ComponentUtil;
import org.jhrcore.util.MsgUtil;

public class FTable extends FBaseTable implements Control, Compound, IModuleCode {

    private static final long serialVersionUID = -6683006015001107234L;
    private static Logger log = Logger.getLogger(FTable.class.getName());
    private Control parent = null;
    private FTableModel model;
    private String title = "";
    private boolean isCheck = false; // �Ƿ����ѡ��
    /**
     * ֧�ָ�ѡ��
     */
    public final static boolean MULTI_CHOICE = true;
    /**
     * ��ѡ��
     */
    public final static boolean SINGLE_CHOICE = false;
    private java.util.List subControls = new ArrayList();
    private boolean isLocalmemo = true;// �Ƿ��Զ����ؼ��䴦��
    private String defaultXML = null;// ���ڻָ�����е�Ĭ����ʾ��ʽ
    // ���б�����ʾ�ֶε���ϸ������person.person_code,�Ѽ��������ֶβ��
//    private List<List<String>> field_details = new ArrayList<List<String>>();
    // �ұ�ͷ
    public GroupableTableHeader header = new GroupableTableHeader(this.getRightActiveTable().getColumnModel());
    // ���ͷ
    public GroupableTableHeader leftHeader = new GroupableTableHeader(this.getLeftLockedTable().getColumnModel());
    //��ǰ��ʾ�ֶ�
    private List<String> fields = new ArrayList<String>();
    //��ǰ����������ʾ�������ֶΣ������ԣ�
    private List<TempFieldInfo> all_fields = new ArrayList<TempFieldInfo>();
    private List<TempFieldInfo> default_fields = new ArrayList<TempFieldInfo>();
    private List<TempFieldInfo> order_fields = new ArrayList<TempFieldInfo>();
    //�Ƿ��ֹ����Ҽ�����
    private boolean right_allow_flag = false;
    //�����ģ���ʶ����������ͬģ��ͬһ�ű����ʾ������������
    private String useModuleCode = "Sys";
    private String module_code = "HRUIComm.FTable";
    //���ڼ�¼������
    private List<String> hide_columns = new ArrayList<String>();
    //���ڼ�¼�ֶ�ԭ��˳�򣨻��ع���˳��
    private Hashtable<String, Integer> field_orders = new Hashtable<String, Integer>();
    //��ѯ������
    private List<IPickQueryExListener> iPickQueryExListeners = new ArrayList<IPickQueryExListener>();
    //���������
    private List<IPickFieldOrderListener> iPickFieldOrderListeners = new ArrayList<IPickFieldOrderListener>();
    //�ֶ����ü�����
    private List<IPickFieldSetListener> iPickFieldSetListeners = new ArrayList<IPickFieldSetListener>();
    //�л������ݱ仯������
    private List<IPickColumnSumListener> iPickColumnSumListeners = new ArrayList<IPickColumnSumListener>();
//    private List<IPickColumnReplaceListener> iPickColumnReplaceListeners = new ArrayList<IPickColumnReplaceListener>();
    private List<IPickPopupListener> pplListeners = new ArrayList();
    //��ǰ��ʾ����
    private ShowScheme cur_show_scheme;
    private List<TempFieldInfo> default_orders = new ArrayList<TempFieldInfo>();
    private QuerySchemeDialog schemeDlg;
    private boolean fetch_show_scheme_flag = true;
    private boolean fetch_query_scheme_flag = true;
    private boolean fetch_sum_scheme_flag = false;
    private boolean module_changed = true;
    private QueryScheme cur_query_scheme;
    private List<ColumnSum> col_sums = null;
    private String cur_hql;//���ֵ�ǰ�Ĳ�ѯ��䣬��Ҫ���ڻ���
    private String alias_name = "";//���ѯ�������磺from A01 bp,�����Ϊbp
    private List alias_data = null;
    private Hashtable<String, String> other_entity_keys = new Hashtable<String, String>();//����װ�طǵ�ǰ��������where������ͨ�������Ϊ�������ɣ����ڹ�����ʱдwhere����,�磺C21.DeptCode_key=DeptCode.DeptCode_key and C21.pay_key ..
    private Hashtable<String, List<String>> field_keys;//������������������ʾ
    private Hashtable<String, String> other_entitys = new Hashtable<String, String>();//����װ�طǵ�ǰ�������ı��ѯ��䣬ͨ�����������װ�أ��ڹ�����ʱ����Ҫ���磺DeptCode DeptCode,C21 C21
    private boolean deptCode_chind_only_flag = true;//����deptCode��ʱ������ѡ��ʱ�Ƿ�����ѡ�з�Ҷ�ӽڵ�
    private boolean editForChangeScheme = false;//��Ա�����Ƿ�����ѡ�����в���
    private List scheme_list = new ArrayList();
    private ShowScheme curOrderScheme = null;
    private Hashtable<String, JMenuItem> item_table = new Hashtable<String, JMenuItem>();
    private JPopupMenu popMenu_right = new JPopupMenu();
    private JPopupMenu.Separator sep1 = new JPopupMenu.Separator();
    private JPopupMenu.Separator sep2 = new JPopupMenu.Separator();
    private JPopupMenu.Separator sep3 = new JPopupMenu.Separator();
    private JMenuItem miQuery = new JMenuItem("��ѯ");
    private JMenu miColSet = new JMenu("��������");
    private JMenuItem miLocalCol = new JMenuItem("��λ��");
    private JMenuItem miFreezeCol = new JMenuItem("������");
    private JMenuItem miUnFreezeCol = new JMenuItem("�ⶳ��");
    private JMenuItem miHideCol = new JMenuItem("������");
    private JMenu miShowCol = new JMenu("��ʾ��");
    private JMenuItem miRestoreColWidth = new JMenuItem("��ԭĬ�Ͽ��");
    private JMenuItem miExport = new JMenuItem("����Excel");
    private JMenu mSetShowScheme = new JMenu("������ʾ����");
    private JMenuItem miSetColShow = new JMenuItem("������ʾ�ֶ�");
    private JMenuItem miSetColOrder = new JMenuItem("��������");
    private JMenuItem miSetColSum = new JMenuItem("���û�����");
    private JMenuItem miColReplace = new JMenuItem("�滻");
    private JMenuItem miShowZero = new JMenuItem("��ʾ��ֵ");
    private JMenuItem miPrint = new JMenuItem("Ԥ����ӡ");
    private JMenuItem miDetail = new JMenuItem("��ϸ");
    private JMenu mEnter = new JMenu("Enter���ƶ�����");
    private JMenuItem miEnterRight = new JMenuItem("����");
    private JMenuItem miEnterDown = new JMenuItem("����");
    private HashSet<String> hideItems = new HashSet();//����ʾ�Ĳ˵�
    private List<String> fieldHeaders = new ArrayList<String>();//��������λ��

    public void setEditForChangeScheme(boolean editForChangeScheme) {
        this.editForChangeScheme = editForChangeScheme;
    }

    public boolean isEditForChangeScheme() {
        return editForChangeScheme;
    }

    public boolean isDeptCode_chind_only_flag() {
        return deptCode_chind_only_flag;
    }

    public void setDeptCode_chind_only_flag(boolean deptCode_chind_only_flag) {
        this.deptCode_chind_only_flag = deptCode_chind_only_flag;
        createColumns(this.fields);
    }

    public QueryScheme getCur_query_scheme() {
        return cur_query_scheme;
    }

    public void setCur_query_scheme(QueryScheme cur_query_scheme) {
        this.cur_query_scheme = cur_query_scheme;
    }

    public Hashtable<String, String> getOther_entitys() {
        return other_entitys;
    }

    public Hashtable<String, String> getOther_entity_keys() {
        return other_entity_keys;
    }

    public Hashtable<String, List<String>> getField_keys() {
        return field_keys;
    }

    public void setOther_entitys(Hashtable<String, String> other_entitys) {
        this.other_entitys = other_entitys;
    }

    public void setOther_entity_keys(Hashtable<String, String> other_entity_keys) {
        this.other_entity_keys = other_entity_keys;
    }

    public void setFetch_sum_scheme_flag(boolean fetch_sum_scheme_flag) {
        this.fetch_sum_scheme_flag = fetch_sum_scheme_flag;
    }

    public void setFetch_query_scheme_flag(boolean fetch_query_scheme_flag) {
        this.fetch_query_scheme_flag = fetch_query_scheme_flag;
    }

    public void setFetch_show_scheme_flag(boolean fetch_show_scheme_flag) {
        this.fetch_show_scheme_flag = fetch_show_scheme_flag;
    }

    public void setAlias_name(String alias_name) {
        this.alias_name = alias_name;
    }

    public ShowScheme getCur_show_scheme() {
        return cur_show_scheme;
    }

    public void setCur_show_scheme(ShowScheme cur_show_scheme) {
        this.cur_show_scheme = cur_show_scheme;
    }

    public void setITableCellEditable(ITableCellEditable iTableCellEditable) {
        model.setITableCellEditable(iTableCellEditable);
    }

    public void setDisableCells(List<String> disableCells) {
        model.setDisableCells(disableCells);
    }

    public List<String> getDisable_fields() {
        return model.getDisable_fields();
    }

    public void setDisable_fields(List<String> disable_fields) {
        model.setDisable_fields(disable_fields);
        this.updateUI();
    }

//    public void addPickColumnReplaceListener(IPickColumnReplaceListener listener) {
//        iPickColumnReplaceListeners.add(listener);
//    }
//
//    public void delPickColumnReplaceListener(IPickColumnReplaceListener listener) {
//        iPickColumnReplaceListeners.remove(listener);
//    }

    public void addPickQueryExListener(IPickQueryExListener listener) {
        iPickQueryExListeners.add(listener);
    }

    public void delPickQueryExListener(IPickQueryExListener listener) {
        iPickQueryExListeners.remove(listener);
    }

    public void addPickFieldOrderListener(IPickFieldOrderListener listener) {
        iPickFieldOrderListeners.add(listener);
    }

    public void delPickFieldOrderListener(IPickFieldOrderListener listener) {
        iPickFieldOrderListeners.remove(listener);
    }

    public void addPickFieldSetListener(IPickFieldSetListener listener) {
        iPickFieldSetListeners.add(listener);
    }

    public void delPickFieldSetListener(IPickFieldSetListener listener) {
        iPickFieldSetListeners.remove(listener);
    }

    public void addPickColumnSumListener(IPickColumnSumListener listener) {
        iPickColumnSumListeners.add(listener);
    }

    public void delPickColumnSumListener(IPickColumnSumListener listener) {
        iPickColumnSumListeners.remove(listener);
    }

    public void addPickPopupListener(IPickPopupListener listener) {
        pplListeners.add(listener);
    }

    public void delPickPopupListener(IPickPopupListener listener) {
        pplListeners.remove(listener);
    }

    public void addRowChangeListner(RowChangeListner listner) {
        model.addRowChangeListner(listner);
    }

    public boolean isRight_allow_flag() {
        return right_allow_flag;
    }

    public void setRight_allow_flag(boolean right_allow_flag) {
        this.right_allow_flag = right_allow_flag;
    }

    public String getUseModuleCode() {
        return useModuleCode;
    }

    public void removeQueryItem() {
        hideItems.add("query");
    }

    public void removeReplaceItem() {
        hideItems.add("replace");
    }

    public void removeColSumItem() {
        hideItems.add("sum");
    }

    public void removeShowOrderItem() {
        hideItems.add("order");
    }

    private void removeShowSchemeItem() {
        hideItems.add("show");
    }

    public void removeSumAndReplaceItem() {
        hideItems.add("sum");
        hideItems.add("replace");
    }

    public void removeAllFunItems() {
        removeQueryItem();
        removeShowSchemeItem();
        removeShowOrderItem();
        removeColSumItem();
        removeReplaceItem();
    }

    public void removeItemByCodes(String codestr) {
        if (codestr == null) {
            return;
        }
        String[] codes = codestr.split(";");
        for (String code : codes) {
            hideItems.add(code);
        }
    }

    public void removeItemQSR() {
        removeQueryItem();
        removeColSumItem();
        removeReplaceItem();
    }

    public void removeItemOSR() {
        removeShowOrderItem();
        removeColSumItem();
        removeReplaceItem();
    }

    public void removeItemQOSR() {
        removeQueryItem();
        removeShowOrderItem();
        removeColSumItem();
        removeReplaceItem();
    }

    public void editingStopped() {
        if (getRightActiveTable().getCellEditor() != null) {
            getRightActiveTable().getCellEditor().stopCellEditing();
        }
    }

    public void removeRowChangeListner(RowChangeListner listner) {
        model.removeRowChangeListner(listner);
    }

    public void addQueryEntity(Class cs, String entity_name, String para) {
        if (schemeDlg == null) {
            schemeDlg = new QuerySchemeDialog(JOptionPane.getFrameForComponent(this), model.getEntityClass(), useModuleCode + "." + model.getEntityClass().getSimpleName());
        }
        ClassAnnotation ca = (ClassAnnotation) cs.getAnnotation(ClassAnnotation.class);
        String entity_caption = ca == null ? "" : ca.displayName();
        schemeDlg.getQuerySchemePanel().addQueryEntity(cs, entity_caption, entity_name, para);
    }

    /**
     * 
     * @param cs
     * @param entity_caption:�������
     * @param entity_name������
     * @param para�����������ڲ�ѯ��ϵ��λ��
     * �����ѯʵ��ΪA01��Ҫ���ӵı�ΪC21��addQueryEntity(C21.class, "��������", "C21", "2"); ����2��ʾA01����һ��C21������
     * �����ѯʵ��ΪA01��Ҫ���ӵı�ΪC21��addQueryEntity(C21.class, "��������", "C21", "4"); ����4��ʾC21����A01������
     * �����ѯʵ��ΪC21��Ҫ���ӵı�ΪA01��addQueryEntity(A01.class, "��Ա��Ϣ", "A01", "5"); ����5��ʾ��ѯ��������ӵı������
     * �����ѯʵ��ΪDept��Ҫ���ӵı�ΪC21��addQueryEntity(C21.class, "��������", "A01;C21", "3;4"); ����4��ʾ���ӵı����A01��������3��ʾA01����һ��Dept������
     *
     */
    private void addQueryEntity(Class cs, String entity_caption, String entity_name, String para) {
        if (schemeDlg == null) {
            schemeDlg = new QuerySchemeDialog(JOptionPane.getFrameForComponent(this), model.getEntityClass(), useModuleCode + "." + model.getEntityClass().getSimpleName());
        }
        schemeDlg.getQuerySchemePanel().addQueryEntity(cs, entity_caption, entity_name, para);
    }

    public void setAll_fields(List<TempFieldInfo> all_fields, List<TempFieldInfo> default_fields, String module_code) {
        this.setAll_fields(all_fields, default_fields, null, module_code);
    }
    //���ÿ������ֶΣ����û�����ã�����all_fields

    public void setOrder_fields(List<TempFieldInfo> order_fields) {
        this.order_fields = order_fields;
    }

    /**
     * ���ڳ�ʼ��������ʾ����
     * @param all_fields��������ʾ�������ֶ�
     * @param default_fields��Ĭ����ʾ���ֶ�
     * @param module_code������ģ���ʶ
     */
    public void setAll_fields(List<TempFieldInfo> all_fields, List<TempFieldInfo> default_fields, List<TempFieldInfo> default_order_fields, String module_code) {
        module_changed = false;
        if (module_code == null || this.useModuleCode == null || !this.useModuleCode.equals(module_code)) {
            module_changed = true;
            scheme_list.clear();
        }
        this.useModuleCode = module_code;
        this.default_fields = default_fields;
        this.all_fields = all_fields;
        if (default_order_fields != null) {
            this.default_orders = default_order_fields;
        }
        setFields(module_code);
        fetchOrderScheme();
        addColumnSum(null, null);
        module_changed = false;
    }

    private void setFields(String module_code) {
        List<TempFieldInfo> afields = new ArrayList<TempFieldInfo>();
        if (cur_show_scheme != null && "-1".equals(cur_show_scheme.getShowScheme_key())) {
            if (!all_fields.isEmpty()) {
                for (TempFieldInfo tfi : all_fields) {
                    if (!tfi.getField_name().contains(".")) {
                        afields.add(tfi);
                    }
                }
            } else {
                afields.addAll(EntityBuilder.getCommFieldInfoListOf(model.getEntityClass(), EntityBuilder.COMM_FIELD_VISIBLE));
            }
        }
        this.useModuleCode = module_code;
        IPickSelectListener listener = new IPickSelectListener() {

            @Override
            public void select(boolean select) {
                setObjects(getObjects());
            }
        };
        ComponentUtil.setBooleanIcon(miShowZero, useModuleCode + ".showZero", listener);
        if (all_fields.isEmpty()) {
            all_fields.addAll(EntityBuilder.getCommFieldInfoListOf(model.getEntityClass(), EntityBuilder.COMM_FIELD_VISIBLE));
        }
        if (default_fields.isEmpty()) {
            default_fields.addAll(all_fields);
        }
        rebuildSchemeItem();
        Hashtable<String, TempFieldInfo> all_keys = new Hashtable<String, TempFieldInfo>();
        for (TempFieldInfo tfi : all_fields) {
            all_keys.put(tfi.getField_name().replace("_code_", ""), tfi);
        }
        List<String> show_fields = new ArrayList<String>();
        if (cur_show_scheme != null && "-1".equals(cur_show_scheme.getShowScheme_key())) {
            for (TempFieldInfo tfi : afields) {
                show_fields.add(tfi.getField_name());
            }
        } else if (cur_show_scheme != null && cur_show_scheme.getShowSchemeDetails() != null) {
            if (all_fields.size() > 0) {
                List<ShowSchemeDetail> ssds = new ArrayList<ShowSchemeDetail>();
                ssds.addAll(cur_show_scheme.getShowSchemeDetails());
                SysUtil.sortListByInteger(ssds, "order_no");
                for (ShowSchemeDetail ssd : ssds) {
                    TempFieldInfo tfi = all_keys.get(ssd.getField_name().replace("_code_", ""));
                    if (tfi != null) {
                        if (tfi.getField_name().startsWith("#")) {
                            ssd.setField_name(tfi.getField_name().substring(0, 2).toUpperCase() + tfi.getField_name().substring(2));
                        } else {
                            ssd.setField_name(tfi.getField_name());
                        }
                        show_fields.add(ssd.getField_name());
                    }
                }
            }
        } else {
            if (default_fields.size() > 0) {
                for (TempFieldInfo tfi : default_fields) {
                    show_fields.add(tfi.getField_name());
                }
            }
        }
        if (show_fields.size() > 0) {
            Hashtable<String, List<TempFieldInfo>> other_table_keys = new Hashtable<String, List<TempFieldInfo>>();
            for (String field : show_fields) {
                if (field.startsWith("#")) {
                    TempFieldInfo tfi = all_keys.get(field.replace("_code_", ""));
                    if (tfi == null) {
                        continue;
                    }
                    List<TempFieldInfo> list = other_table_keys.get(tfi.getEntity_name());
                    if (list == null) {
                        list = new ArrayList<TempFieldInfo>();
                    }
                    list.add(tfi);
                    other_table_keys.put(tfi.getEntity_name(), list);
                }
            }
            model.getHt_OtherTable().clear();
            for (String entityName : other_table_keys.keySet()) {
                List<TempFieldInfo> other_fields = other_table_keys.get(entityName);
                if (other_fields.isEmpty()) {
                    continue;
                }
                String key = other_entity_keys.get(entityName);
                if (key == null) {
                    continue;
                }
                String other_tname = "";//�ӱ��������ڲ�ѯ
                if (other_entitys.get(entityName) != null) {
                    String temp_t = other_entitys.get(entityName);
                    String[] temp_ts = temp_t.split(",");
                    for (String temp_table : temp_ts) {
                        temp_table = temp_table.trim();
                        if (temp_table.indexOf(" ") > 0 && entityName.equalsIgnoreCase(temp_table.substring(0, temp_table.indexOf(" ")))) {
                            other_tname = temp_table.substring(temp_table.lastIndexOf(" ") + 1, temp_table.length()) + ".";
                        }
                    }
                }
                String hql = "";
                for (TempFieldInfo tfi : other_fields) {
                    hql += "," + other_tname + tfi.getField_name().substring(tfi.getField_name().indexOf(".") + 1).replace("_code_", "");
                }
                if (other_entitys.get(entityName) != null) {
                    hql = hql.substring(1) + " from " + other_entitys.get(entityName) + " where " + key + "";
                } else {
                    hql = hql.substring(1) + " from " + entityName + " where " + key + "";
                }
                model.getHt_OtherTableSql().put(entityName, hql);
            }
            setFields(show_fields);
        }
    }

    public void changeOtherTableSQL() {
        if (((FTableModel) this.getModel()).getEntityClass() == null) {
            return;
        }
        Hashtable<String, List<TempFieldInfo>> other_table_keys = new Hashtable<String, List<TempFieldInfo>>();
        List<String> show_fields = this.getFields();
        for (String field : show_fields) {
            if (field.startsWith("#")) {
                field = SysUtil.tranField(field).substring(1);
                TempFieldInfo tfi = new TempFieldInfo();
                String[] fields = field.split("\\.");
                if (fields.length < 2) {
                    continue;
                }
                tfi.setEntity_name(fields[fields.length - 2]);
                tfi.setField_name(fields[fields.length - 1]);
                List<TempFieldInfo> list = other_table_keys.get(tfi.getEntity_name());
                if (list == null) {
                    list = new ArrayList<TempFieldInfo>();
                }
                list.add(tfi);
                other_table_keys.put(tfi.getEntity_name(), list);
            }
        }
        model.getHt_OtherTable().clear();
        for (String entityName : other_table_keys.keySet()) {
            List<TempFieldInfo> other_fields = other_table_keys.get(entityName);
            if (other_fields.isEmpty()) {
                continue;
            }
            String key = other_entity_keys.get(entityName);
            if (key == null) {
                continue;
            }
            String other_tname = "";//�ӱ��������ڲ�ѯ
            if (other_entitys.get(entityName) != null) {
                String temp_t = other_entitys.get(entityName);
                String[] temp_ts = temp_t.split(",");
                for (String temp_table : temp_ts) {
                    temp_table = temp_table.trim();
                    if (temp_table.indexOf(" ") > 0 && entityName.equalsIgnoreCase(temp_table.substring(0, temp_table.indexOf(" ")))) {
                        other_tname = temp_table.substring(temp_table.lastIndexOf(" ") + 1, temp_table.length()) + ".";
                    }
                }
            }
            String hql = "";
            for (TempFieldInfo tfi : other_fields) {
                hql += "," + other_tname + tfi.getField_name().substring(tfi.getField_name().indexOf(".") + 1).replace("_code_", "");
            }
            if (other_entitys.get(entityName) != null) {
                hql = hql.substring(1) + " from " + other_entitys.get(entityName) + " where " + key + "";
            } else {
                hql = hql.substring(1) + " from " + entityName + " where " + key + "";
            }
            model.getHt_OtherTableSql().put(entityName, hql);
        }
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
        createColumns(this.fields);
    }

    /**
     * �÷������ڻ�ȡ���ڵ�ǰ�еļ���SQL
     * @param start_name����ı���
     * @param val������ֵ
     * @return������SQL
     */
    public String getQuickSearchSQL(String start_name, String val) {
        return getQuickSearchSQL(start_name,val,false);
    }
    
     /**
     * �÷������ڻ�ȡ���ڵ�ǰ�еļ���SQL(���Ծ�ȷ������Ҳ����ģ������)
     * @param start_name����ı���
     * @param val������ֵ
     * @return������SQL
     */
    public String getQuickSearchSQL(String start_name, String val,boolean jc_flag) {
        String bd_str = "like";
        if(jc_flag){
            bd_str = "=";
        }
        String hql = "";
        String field_name = this.getColumnModel().getColumn(this.getCurrentColumnIndex()).getId();
        if (field_name == null || field_name.trim().equals("") || field_name.toLowerCase().equals("row_no")) {
            return "1=0";
        }
        String field = field_name;
        if (field.contains(".")) {
            String[] fs = field.split("\\.");
            field = fs[fs.length - 1];
            start_name = fs[fs.length - 2].replace("#", "");
        }
        if (field.endsWith("_code_")) {
            List<TempFieldInfo> fieldinfos = this.getAll_fields();
            TempFieldInfo cur_info = null;
            for (TempFieldInfo tfi : fieldinfos) {
                if (tfi.getField_name().equals(field_name)) {
                    cur_info = tfi;
                    break;
                }
            }
            field = field.substring(0, field.length() - 6);
            if (cur_info == null) {
                return "1=0";
            }
            String code_type_name = cur_info.getCode_type_name();
            hql += start_name + "." + field + " in (select code_id from Code where code_type='" + code_type_name + "' and (code_name "+bd_str+" '" + val + "' or pym "+bd_str+" '" + val + "'))";
        } else {
            hql += "upper(" + start_name + "." + field + ") "+bd_str+" '" + val + "'";
        }
        return hql;
    }

    public List<String> getFields() {
        return fields;
    }

    public List<TempFieldInfo> getAll_fields() {
        return all_fields;
    }

    public List<TempFieldInfo> getDefault_fields() {
        return default_fields;
    }

    /**
     * ��ʼ���к���
     */
    @Override
    protected void updateRowNumberColumn() {
        if (model == null) {
            super.updateRowNumberColumn();
            return;
        }
        String row_num = "aaaaaaaaaaaaaaaaaaaaaaWWWWWWWWWWWWWWWWWWWWWWWWWWWWW";
        row_num = row_num.substring(0, String.valueOf(model.getRowCount()).length() + 1);

        FBaseTableColumn col = (FBaseTableColumn) getLockedTable().getColumn(ROWNUMBER_COLUMN_NAME);
        FontMetrics met = this.getFontMetrics(getLockedTable().getFont());
        if (this.isShowRowNumber()) {
            col.setVisible(true);
            col.setWidth(met.stringWidth(row_num));//(FBaseTable.ROWNUMBER_COLUMN_WIDTH);
            col.setMaxWidth(met.stringWidth(row_num));//(FBaseTable.ROWNUMBER_COLUMN_WIDTH);
            col.setMinWidth(met.stringWidth(row_num));//(FBaseTable.ROWNUMBER_COLUMN_WIDTH);
            col.setPreferredWidth(met.stringWidth(row_num));//(FBaseTable.ROWNUMBER_COLUMN_WIDTH);
        } else {
            col.setVisible(false);
        }
        this.adjustLeftLockedTableWidth();
    }

    public void buildSchemeItem() {
        item_table.clear();
        mSetShowScheme.removeAll();
        mSetShowScheme.add(miSetColShow);
        mSetShowScheme.addSeparator();
        rebuildSchemeItem();
    }

    private void rebuildSchemeItem() {
        if (model != null && model.getEntityClass() != null && fetch_show_scheme_flag && module_changed) {
            if (scheme_list.isEmpty()) {
                String hql = "from ShowScheme ss left join fetch ss.showSchemeDetails  where ss.entity_name='" + useModuleCode + "." + model.getEntityClass().getSimpleName() + "' and ss.person_code in('" + UserContext.person_code + "'";
                if (!UserContext.isSA) {
                    hql += ",'" + UserContext.sysManName + "'";
                }
                hql += ") order by showScheme_name";
                scheme_list.addAll(CommUtil.fetchEntities(hql));
            }
        }
        for (String key : item_table.keySet()) {
            mSetShowScheme.remove(item_table.get(key));
        }
        ShowScheme myScheme = null;
        ShowScheme saScheme = null;
        for (Object obj : scheme_list) {
            ShowScheme au = (ShowScheme) obj;
            if (au.getShowScheme_key() == null) {
                continue;
            }
            JMenuItem item = new JMenuItem(new ShowSchemeAction(au));
            ComponentUtil.setIcon(item, "blank");
            if ("1".equals(au.getDefault_flag())) {
                if (au.getPerson_code().equals(UserContext.person_code)) {
                    myScheme = au;
                } else {
                    saScheme = au;
                }
            }
            item_table.put(au.getShowScheme_key(), item);
            mSetShowScheme.add(item);
        }
        cur_show_scheme = myScheme == null ? saScheme : myScheme;
        if (cur_show_scheme != null) {
            ComponentUtil.setIcon(item_table.get(cur_show_scheme.getShowScheme_key()), "select");
        }
    }
    /* ��ͷ����Ҽ�����¼�����������Ϣ�Ի��� */
    private MouseAdapter headerMouseRightClickedListener = new MouseAdapter() {

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (col_width_runnable != null) {
                SwingUtilities.invokeLater(col_width_runnable);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (cur_column == -1) {
                    FTable.this.selectAll();
                }
            }

        }
    };

    public boolean isEditable() {
        return model.isEditable();
    }

    public void setEditable(boolean editable) {
        model.setEditable(editable);
        this.updateUI();
    }

    public void addObjects(List objects) {
        this.removeAllListSelectionListener();
        model.addObjects(objects);
        updateRowNumberColumn();
        int ind = model.getObjects().size() - 1;
        if (ind >= 0) {
            this.setRowSelectionInterval(ind, ind);
        }
        this.refreshListSelectionListener();
    }

    public void addObject(Object obj) {
        this.removeAllListSelectionListener();
        model.addObject(obj);
        updateRowNumberColumn();
        int ind = model.getObjects().size() - 1;
        if (ind >= 0) {
            this.setRowSelectionInterval(ind, ind);
        }
        this.refreshListSelectionListener();
    }
    
    public void addObject(Object obj,int index) {
        //������Ӷ���ָ����
        this.removeAllListSelectionListener();
        model.addObject(obj,index);
        updateRowNumberColumn();
        this.setRowSelectionInterval(index, index);
        this.refreshListSelectionListener();
    }

    public void clear() {
        model.clear();
    }

    public List<Object> getAllObjects() {
        return model.getAllObjects();
    }

    public List getObjects() {
        if (model == null) {
            return new ArrayList();
        }
        return model.getObjects();
    }

    public List getSelectObjects() {
        return this.getSelectedData();
    }

    public List<String> getSelectKeys() {
        return getKeys(this.getSelectedData());
    }

    public List<String> getAllKeys() {
        return getKeys(this.getObjects());
    }

    private List<String> getKeys(List list) {
        List<String> resultKeys = new ArrayList<String>();
        if (model.getEntityClass() != null) {
            Class c = model.getEntityClass();
            String className = c.getSimpleName();
            String classKey = className + "_key";
            Class super_class = model.getEntityClass().getSuperclass();
            if (super_class != null && super_class.getName().startsWith("org.jhrcore.entity")) {
                classKey = super_class.getSimpleName() + "_key";
            }
            classKey = classKey.substring(0, 1).toLowerCase() + classKey.substring(1);
            for (Object obj : list) {
                if (obj == null) {
                    continue;
                } else if (c.isAssignableFrom(obj.getClass()) || obj.getClass() == c) {
                    resultKeys.add(PublicUtil.getProperty(obj, classKey).toString());
                } else {
                    resultKeys.add(obj.toString());
                }
            }
        }
        return resultKeys;
    }

    public void setObjects(List list) {
        if (model == null) {
            return;
        }
        this.removeAllListSelectionListener();
        Class c = model.getEntityClass();
        if (c != null) {
            String name = c.getSimpleName();
            List del = new ArrayList();
            for (Object obj : BeanManager.change_objs.keySet()) {
                if (obj.getClass().getSimpleName().equals(name)) {
                    del.add(obj);
                }
            }
            for (Object obj : del) {
                BeanManager.change_objs.remove(obj);
            }
        }
        changeOtherTableSQL();
        int active_ind = this.activeTable.getSelectedColumn();
        int lock_ind = this.getLockedTable().getSelectedColumn();
        activeTable.editingCanceled(null);
        model.setNotShowZero(!"1".equals(ConfigManager.getConfigManager().getProperty(useModuleCode + ".showZero")));
        model.setObjects(list);
        updateRowNumberColumn();
        if (iPickColumnSumListeners.size() > 0) {
            cur_hql = "";
            String hql = null;
            for (IPickColumnSumListener listener : iPickColumnSumListeners) {
                hql = listener.pickSumSQL();
            }
            addColumnSum(null, hql);
        }
        if (active_ind != -1) {
            this.activeTable.setColumnSelectionInterval(active_ind, active_ind);
        } else if (lock_ind != -1) {
            this.getLockedTable().setColumnSelectionInterval(lock_ind, lock_ind);
        }
        setRowSelectionInterval(0, 0);
        this.refreshListSelectionListener();
    }
    // �����п��ִ���¼�
    private Runnable col_width_runnable = null;

    private void createColumns(List<String> fields) {
        fieldHeaders.clear();
        this.removeAllSubControls();
        List<FBaseTableColumn> alist = new ArrayList<FBaseTableColumn>();
        for (int i = 0; i < this.getColumnCount(); i++) {
            FBaseTableColumn fc = this.getColumnModel().getColumn(i);
            alist.add(fc);
        }
        for (FBaseTableColumn fc : alist) {
            this.removeColumn(fc);
        }
        List<UIItemDetail> details = UIItemGroup.getUIItemDetails(model.getEntityClass(), fields);
        for (UIItemDetail detail : details) {
            fieldHeaders.add(detail.getField_caption() + ";" + detail.getField().getName());
            final FBaseTableColumn column = new FBaseTableColumn();
            column.setId(detail.getBindName());
            column.setTitle(detail.getField_caption());
            column.setEditable(detail.isEditable());
            column.setEditable_when_new(detail.isEditable_when_new());
            String tmp = ConfigManager.getConfigManager().getProperty("table_column_width." + column.getId());
            if (tmp != null && !tmp.equals("")) {
                column.setWidth(Integer.valueOf(tmp));
            } else {
                FontMetrics met = this.getFontMetrics(getLockedTable().getFont());
                column.setWidth(detail.getView_width() * met.stringWidth("W") / 2);
            }
            createTableCellEditor(column, detail);
            addColumn(column);
            column.addPropertyChangeListener(new PropertyChangeListener() {

                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if (!evt.getPropertyName().equals("width")) {
                        return;
                    }
                    col_width_runnable = new Runnable() {

                        @Override
                        public void run() {
                            ConfigManager.getConfigManager().setProperty("table_column_width." + column.getId(), "" + column.getWidth());
                            ConfigManager.getConfigManager().save2();
                            col_width_runnable = null;
                        }
                    };
                }
            });
        }
        FTable.this.setFieldHeaders(fieldHeaders);
    }

    private void restoreColumnWidth() {
        for (int i = 0; i < getColumnModel().getColumnCount(); i++) {
            FBaseTableColumn column = getColumnModel().getColumn(i);
            String fieldName2 = column.getId();
            List<String> tmp_list = new ArrayList<String>();
            int start_ind = 0;
            for (int i2 = 0; i2 < fieldName2.length(); i2++) {
                if (fieldName2.charAt(i2) == '.') {
                    tmp_list.add(fieldName2.substring(start_ind, i2));
                    start_ind = i2 + 1;
                } else if (i2 == fieldName2.length() - 1) {
                    tmp_list.add(fieldName2.substring(start_ind));
                }
            }

            Class aclass = model.getEntityClass();
            FieldAnnotation fieldAnnotation = null;
            for (String fieldName : tmp_list) {
                try {
                    Field field = aclass.getField(fieldName);
                    fieldAnnotation = field.getAnnotation(FieldAnnotation.class);
                    aclass = field.getType();
                } catch (NoSuchFieldException ex) {
                    log.error(ex);
                } catch (SecurityException ex) {
                    log.error(ex);
                }
            }
            if (fieldAnnotation != null) {
                FontMetrics met = this.getFontMetrics(getLockedTable().getFont());
                column.setWidth(fieldAnnotation.view_width() * met.stringWidth("W") / 2);
                column.setPreferredWidth(fieldAnnotation.view_width() * met.stringWidth("W") / 2);
                ConfigManager.getConfigManager().setProperty("table_column_width." + column.getId(), "" + column.getWidth());
            }

        }
        ConfigManager.getConfigManager().save2();
    }

    private void createCodeCellRenderer(FBaseTableColumn column, final int alignment, final ObjectListHint objectListHint) {
        column.setCellRenderer(new TableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final JTextField textField = new JTextField("");
                textField.setBorder(null);
                textField.setHorizontalAlignment(alignment);
                String text = "";
                if (getObjects().size() > 0) {
                    TempCodeEditor editor = TempCodeEditor.getCodeEditor(getObjects().get(row), objectListHint, value);
                    text = editor.getCell_value() == null ? "" : editor.getCell_value().toString();
                }
                textField.setText(text);
                if (isSelected) {
                    textField.setForeground(table.getSelectionForeground());
                    textField.setBackground(table.getSelectionBackground());
                } else {
                    textField.setForeground(table.getForeground());
                    textField.setBackground(table.getBackground());
                }
                return textField;
            }
        });
    }

    private void createDefaultTableCellEditor(FBaseTableColumn column, int alignment) {
        final JTextField textField = new JTextField("");
        textField.setBorder(null);
        textField.setHorizontalAlignment(alignment);
        column.setCellRenderer(new TableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                textField.setText(value == null ? "" : value.toString());
                if (isSelected) {
                    textField.setForeground(table.getSelectionForeground());
                    textField.setBackground(table.getSelectionBackground());
                } else {
                    textField.setForeground(table.getForeground());
                    textField.setBackground(table.getBackground());
                }
                return textField;
            }
        });
    }

    private void createTableCellEditor(FBaseTableColumn column, final UIItemDetail detail) {
        Field field = detail.getField();
        if (field == null) {
            return;
        }
        final FieldAnnotation fieldAnnotation = field.getAnnotation(FieldAnnotation.class);
        final int align_ment = getAlign_ment(fieldAnnotation);
        Class typeCalss = field.getType();
        String fieldType = typeCalss.getSimpleName().toLowerCase();
        String fullType = typeCalss.getName();
        EnumHint enumHint = field.getAnnotation(EnumHint.class);
        ObjectListHint objHint = field.getAnnotation(ObjectListHint.class);
        final String format = fieldAnnotation == null ? "" : fieldAnnotation.format();
        if (enumHint != null) {
            List<String> list = new ArrayList();
            for (String s : enumHint.enumList().split(
                    ";")) {
                list.add(s);
            }
            column.setCellEditor(new TableComboboxCellEditor(list, null, field.getName(), null));
        } else if (objHint != null) {
            if (objHint.hqlForObjectList().contains("from Code ")) {
                column.setCellEditor(new TableCodeCellEditor(this, objHint, align_ment));
                createCodeCellRenderer(column, align_ment, objHint);//�޸ı������ֶ��޲鿴Ȩ��ʱ��ʾΪ***��By ����
            } else {
                column.setCellEditor(new TableComboboxCellEditor(null, this, field.getName(), objHint));
                createDefaultTableCellEditor(column, align_ment);//100818���޸��������ֶ��ı�������Ч�Ĵ��� by ����
            }
        } else if (fieldType.equals("float") || fieldType.contains("bigdecimal") || fieldType.equals("int") || fieldType.equals("integer")) {
            column.setCellEditor(new TableNumberCellEditor(align_ment, format, fieldType.equals("int") || fieldType.equals("integer")));
            column.setCellRenderer(new TableCellRenderer() {

                private JTextField ftf = null;
                private DecimalFormat df = null;

                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    if (ftf == null) {
                        ftf = new JTextField(value == null ? "" : value.toString());
                        if (!format.equals("")) {
                            df = new DecimalFormat(format);
                        }
                        ftf.setBorder(null);
                    }
                    if (df == null) {
                        ftf.setText(value == null ? "" : value.toString());
                    } else {
                        if (value != null && !value.toString().equals("")) {
                            ftf.setText(df.format(value));
                        } else {
                            ftf.setText("");
                        }
                    }
                    if (isSelected) {
                        ftf.setForeground(table.getSelectionForeground());
                        ftf.setBackground(table.getSelectionBackground());
                    } else {
                        ftf.setForeground(table.getForeground());
                        ftf.setBackground(table.getBackground());
                    }
                    ftf.setHorizontalAlignment(align_ment);
                    return ftf;
                }
            });
        } else if (typeCalss.equals(Date.class)) {
            String temp = "yyyy-MM-dd";
            if (!format.equals("")) {
                temp = format;
            }
            final String sformat = temp;
            column.setCellEditor(new TableDateCellEditor(sformat));
            column.setCellRenderer(new TableCellRenderer() {

                private JFormattedTextField ftf = null;

                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    if (ftf == null) {
                        DateFormat format = new SimpleDateFormat(sformat);
                        DateFormatter df = new DateFormatter(format);
                        ftf = new JFormattedTextField(df);
                        ftf.setBorder(null);
                    }
                    ftf.setBorder(null);
                    ftf.setValue(value);
                    if (isSelected) {
                        ftf.setForeground(table.getSelectionForeground());
                        ftf.setBackground(table.getSelectionBackground());
                    } else {
                        ftf.setForeground(table.getForeground());
                        ftf.setBackground(table.getBackground());
                    }
                    ftf.setHorizontalAlignment(align_ment);
                    return ftf;
                }
            });
        } else if (fieldType.equals("boolean")) {
            column.setCellEditor(new TableBooleanCellEditor());
            column.setCellRenderer(new TableCellRenderer() {

                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    final JCheckBox checkBox = new JCheckBox("", false);
                    checkBox.setHorizontalAlignment(SwingConstants.CENTER);
                    if (value != null) {
                        if (value instanceof Boolean) {
                            checkBox.setSelected((Boolean) value);
                        } else {
                            checkBox.setSelected(SysUtil.objToBoolean(value));
                        }
                    }
                    if (isSelected) {
                        checkBox.setForeground(table.getSelectionForeground());
                        checkBox.setBackground(table.getSelectionBackground());
                    } else {
                        checkBox.setForeground(table.getForeground());
                        checkBox.setBackground(table.getBackground());
                    }
                    return checkBox;
                }
            });
        } else if (typeCalss.equals(String.class)) {
            if (detail.getView_width() <= 80) {
                column.setCellEditor(new TableStringCellEditor(align_ment));
                column.setCellRenderer(new TableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        JTextField textField = new JTextField("");
                        textField.setBorder(null);
                        textField.setHorizontalAlignment(align_ment);
                        String text = SysUtil.objToStr(value);
                        textField.setText(text);
                        if (isSelected) {
                            textField.setForeground(table.getSelectionForeground());
                            textField.setBackground(table.getSelectionBackground());
                        } else {
                            textField.setForeground(table.getForeground());
                            textField.setBackground(table.getBackground());
                        }
                        return textField;
                    }
                });
            } else {
                column.setLargeText(true);
                column.setCellEditor(new TableAreaCellEditor(align_ment));
                column.setCellRenderer(new TableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        final JTextField textField = new JTextField("");
                        final JButton btnSelect = new JButton("...");
                        JPanel pnl = new JPanel(new BorderLayout());
                        pnl.add(textField, BorderLayout.CENTER);
                        pnl.add(btnSelect, BorderLayout.EAST);
                        String text = SysUtil.objToStr(value);
                        final String finalText = SysUtil.objToStr(value);
                        text = text.length() > 40 ? (text.substring(0, 10) + "...") : text;
                        textField.setText(text);
                        textField.setEditable(false);
                        textField.setBorder(null);
                        pnl.setBorder(null);
                        pnl.setFocusTraversalKeysEnabled(false);
                        if (detail.isEditable()) {
                            textField.setBackground(Color.WHITE);
                        }
                        btnSelect.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                FormulaTextDialog dlg = new FormulaTextDialog(finalText, "", false);
                                dlg.setLocationRelativeTo(btnSelect);
                                dlg.setVisible(true);
                            }
                        });

                        return pnl;
                    }
                });
            }
        } else if (fullType.equals("org.jhrcore.entity.DeptCode")) {
            column.setCellEditor(new TableDeptCellEditor(this));
        } else if (typeCalss.equals((new ArrayList()).getClass())) {
            ParameterizedType ltType = (ParameterizedType) field.getGenericType();
            Class<?> ctype = (Class<?>) ltType.getActualTypeArguments()[0];
            if ("EntityDef".equals(ctype.getSimpleName())) {
                column.setCellEditor(new TablePersonClassCellEditor(this));
            }
        } else if (typeCalss.equals(Color.class)) {
            column.setCellEditor(new TableColorCellEditor(this));
            final PaintSample sample = new PaintSample(this.getBackground());
            column.setCellRenderer(new TableCellRenderer() {

                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    if (value != null) {
                        sample.setPaint((Color) value);
                    } else {
                        sample.setPaint(getBackground());
                    }
                    return sample;
                }
            });
        }
    }

    private int getAlign_ment(FieldAnnotation fieldAnnotation) {
        if (fieldAnnotation != null) {
            if ("����".equals(fieldAnnotation.field_align())) {
                return JTextField.CENTER;
            } else if ("�Ҷ���".equals(fieldAnnotation.field_align())) {
                return JTextField.RIGHT;
            }
        }
        return JTextField.LEFT;
    }

    /**
     * �������õ��еĶ��뷽ʽ
     * @param column
     * @param alignment
     */
    public void setAlignMent(FBaseTableColumn column, int alignment) {
        createDefaultTableCellEditor(column, alignment);
    }

    public void setAlignMent(int col_index, int alignment) {
        createDefaultTableCellEditor(getColumnModel().getColumn(col_index), alignment);
    }

    public void setAlignMent(Object col_id, int alignment) {
        createDefaultTableCellEditor(getColumn(col_id), alignment);
    }

    /**
     * ����������������ʾ��֧�ֶ��ͷ����ͷͨ�������������
     * @param fields�����field_keysΪnull,���list��Ϊ��ʾ��ͷ�������Ϊnull,���list ��Ϊ��ͷ����
     * @param field_keys�����б�ͷ����
     */
    public FTable(List fields, Hashtable<String, List<String>> field_keys) {
        this();
        IPickSelectListener listener = new IPickSelectListener() {

            @Override
            public void select(boolean select) {
                setObjects(getObjects());
            }
        };
        ComponentUtil.setBooleanIcon(miShowZero, useModuleCode + ".showZero", listener);
        int i = 0;
        this.fields.addAll(fields);
        this.field_keys = field_keys;
        setSortable(true);
        if (field_keys == null) {
            for (Object field : fields) {
                i++;
                FBaseTableColumn column = new FBaseTableColumn();
                column.setId(field.toString());
                column.setTitle(field.toString());
                column.setEditable(true);
                createDefaultTableCellEditor(column, JTextField.RIGHT);
                this.addColumn(column);
            }
        } else {
            for (Object f : fields) {
                String group_key = f.toString();
                List<String> group_fields = field_keys.get(group_key);
                i++;
                if (group_fields == null || group_fields.size() == 0) {
                    FBaseTableColumn column = new FBaseTableColumn();
                    column.setId(i + "");
                    column.setTitle(group_key);
                    column.setEditable(false);
                    createDefaultTableCellEditor(column, JTextField.RIGHT);
                    this.addColumn(column);
                } else {
                    ColumnGroup cg = new ColumnGroup(group_key);
                    cg.setId(i + "");
                    cg.setColumnMargin(group_fields.size());
                    for (String field : group_fields) {
                        i++;
                        FBaseTableColumn column = new FBaseTableColumn();
                        column.setId(i + "");
                        column.setTitle(field);
                        column.setEditable(false);
                        createDefaultTableCellEditor(column, JTextField.RIGHT);
                        cg.addControl(column);
                    }
                    this.addColumnGroup(cg);
                }

            }
        }
    }

    public FTable(Class<?> entityClass) {
        this(entityClass, EntityBuilder.getCommFieldNameListOf(entityClass, EntityBuilder.COMM_FIELD_VISIBLE), true, true, false, "");
    }

    public FTable(Class<?> entityClass, List<String> fields) {
        this(entityClass, fields, true, true, false, "");
    }

    public FTable(Class<?> entityClass, boolean fetch_show_scheme_flag, boolean fetch_query_scheme_flag) {
        this(entityClass, EntityBuilder.getCommFieldNameListOf(entityClass, EntityBuilder.COMM_FIELD_VISIBLE), fetch_show_scheme_flag, fetch_query_scheme_flag, false, "");
    }

    public FTable(Class<?> entityClass, boolean fetch_show_scheme_flag, boolean fetch_query_scheme_flag, boolean fetch_sum_scheme_flag) {
        this(entityClass, EntityBuilder.getCommFieldNameListOf(entityClass, EntityBuilder.COMM_FIELD_VISIBLE), fetch_show_scheme_flag, fetch_query_scheme_flag, fetch_sum_scheme_flag, "");
    }

    public FTable(Class<?> entityClass, boolean fetch_show_scheme_flag, boolean fetch_query_scheme_flag, boolean fetch_sum_scheme_flag, String module_code) {
        this(entityClass, EntityBuilder.getCommFieldNameListOf(entityClass, EntityBuilder.COMM_FIELD_VISIBLE), fetch_show_scheme_flag, fetch_query_scheme_flag, fetch_sum_scheme_flag, module_code);
    }

    public FTable(Class<?> entityClass, String[] fields, boolean fetch_show_scheme_flag, boolean fetch_query_scheme_flag, boolean fetch_sum_scheme_flag, String module_code) {
        this(entityClass, Arrays.asList(fields), fetch_show_scheme_flag, fetch_query_scheme_flag, fetch_sum_scheme_flag, module_code);
    }

    public FTable(Class<?> entityClass, List<String> fields, boolean fetch_show_scheme_flag, boolean fetch_query_scheme_flag, boolean fetch_sum_scheme_flag, String module_code) {
        this();
        this.fetch_show_scheme_flag = fetch_show_scheme_flag;
        this.fetch_query_scheme_flag = fetch_query_scheme_flag;
        this.fetch_sum_scheme_flag = fetch_sum_scheme_flag;
        this.useModuleCode = module_code;
        model.setEntityClass(entityClass);
        setFields(fields);
        addColumnSum(null, null);
        buildSchemeItem();
        fetchOrderScheme();
        this.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                BeanManager.manager(FTable.this.getCurrentRow());
            }
        });
    }

    private void fetchOrderScheme() {
        if (curOrderScheme == null && model != null && model.getEntityClass() != null && fetch_show_scheme_flag && module_changed) {
            curOrderScheme = (ShowScheme) CommUtil.fetchEntityBy("from ShowScheme ss left join fetch ss.showSchemeOrders where ss.showScheme_key='" + UserContext.person_code + "." + useModuleCode + "." + model.getEntityClass().getSimpleName() + ".order'");
        }
    }
    //��ӻ�����

    public void addColumnSum(List<ColumnSum> column_sums, String hql) {
        if (column_sums == null) {
            if (fetch_sum_scheme_flag && module_changed) {
                List list = CommUtil.fetchEntities("from ColumnSum cs where cs.entity_name='" + useModuleCode + "." + model.getEntityClass().getSimpleName() + "' and cs.user_code ='" + UserContext.person_code + "'");
                if (list.size() > 0) {
                    List<ColumnSum> user_sum = new ArrayList<ColumnSum>();
                    for (Object obj : list) {
                        ColumnSum cs = (ColumnSum) obj;
                        user_sum.add(cs);
                    }
                    col_sums = user_sum;
                }
            }
        } else {
            col_sums = column_sums;
        }
        if (fetch_sum_scheme_flag) {
            if (col_sums != null) {
                this.removeAllColumnStat();
                int ind = 0;
                List<ColumnSum> sums = new ArrayList<ColumnSum>();
                List<ColumnSum> int_sums = new ArrayList<ColumnSum>();
                Hashtable<String, DecimalFormat> dfKeys = new Hashtable<String, DecimalFormat>();
                for (ColumnSum cs : col_sums) {
                    if (fields.contains(cs.getField_name())) {
                        this.addColumnStat(new FColumnStat(this, cs.getField_name(), cs.getType(), 0, dfKeys.get(cs.getField_name())));
                        if (cs.getType() != 2) {
                            sums.add(cs);
                            ind++;
                            if ("int".equalsIgnoreCase(cs.getField_type()) || "integer".equalsIgnoreCase(cs.getField_type())) {
                                int_sums.add(cs);
                                dfKeys.put(cs.getField_name(), new DecimalFormat("0"));
                            } else {
                                String field_name = cs.getField_name();
                                if (!cs.getField_name().startsWith("#")) {
                                    field_name = cs.getEntity_name().substring(cs.getEntity_name().lastIndexOf(".") + 1) + "." + field_name;
                                }
                                TempFieldInfo tfi = EntityBuilder.getTempFieldInfoByName(field_name);
                                if (tfi != null) {
                                    //����Ϊfloat��bigdecimal��û�и�ʽ���ֶΣ���ʽ��0.###���ڻ�����
                                    if ((tfi.getFormat() == null || "".equals(tfi.getFormat()))
                                            && ("float".equalsIgnoreCase(tfi.getField_type()) || "bigdecimal".equalsIgnoreCase(tfi.getField_type()))) {
                                        tfi.setFormat("0.###");
                                    }
                                    dfKeys.put(cs.getField_name(), new DecimalFormat(tfi.getFormat()));
                                }
                            }
                        }
                    }
                }
                if (hql != null && hql.equals(cur_hql)) {
                } else {
                    cur_hql = hql;
                    String fetch_hql;
                    String s_select = "";
                    String s_table = "";
                    boolean pay_flag = useModuleCode.startsWith("PayAppendix_") || useModuleCode.equals("PayHistoryPanel") || "PayBonus".equals(useModuleCode) || "PayBonus3".equals(useModuleCode);
                    boolean in_flag = useModuleCode.startsWith("BXCBMng") || useModuleCode.equals("BXCheck") || useModuleCode.equals("MonthResultPanel") || useModuleCode.equals("DayResultPanel");
                    HashSet<String> entitys = new HashSet<String>();
                    if (pay_flag) {
                        entitys.add("C21");
                    }
                    String s_where = "1=1 ";
                    String key = EntityBuilder.getEntityKey(model.getEntityClass());
                    int col = 0;
                    for (ColumnSum cs : col_sums) {
                        if (cs.getType() == 2) {
                            continue;
                        }
                        if (fields.contains(cs.getField_name())) {
                            col++;
                            String fieldName = SysUtil.tranField(cs.getField_name());
                            String entityName = alias_name;
                            if (pay_flag) {
                                String oldEntityName = cur_hql == null ? "" : cur_hql;
                                if (oldEntityName.indexOf("from") != -1 && oldEntityName.indexOf("where") != -1) {
                                    oldEntityName = oldEntityName.substring(oldEntityName.indexOf("from") + 5, oldEntityName.indexOf("where"));
                                    oldEntityName = oldEntityName.trim() + ",";
                                }
                                if (fieldName.startsWith("#")) {
                                    fieldName = fieldName.substring(1);
                                    entityName = fieldName.split("\\.")[0];
                                } else {
                                    fieldName = entityName + "." + fieldName;
                                }
                                if (!entitys.contains(entityName) && !oldEntityName.contains(entityName + ",")) {
                                    entitys.add(entityName);
                                    s_table = s_table + entityName + " " + entityName + ",";
                                    s_where += " and C21." + key + "=" + entityName + "." + key;
                                }
                            } else if (fieldName.startsWith("#")) {
                                fieldName = fieldName.substring(1);
                                entityName = fieldName.split("\\.")[0];
                            } else if (fieldName.contains(".") && cur_hql != null && cur_hql.endsWith("@sql")) {
                                entityName = fieldName.split("\\.")[0];
                            }
                            s_select += makeSumSQL(entityName, fieldName.substring(fieldName.indexOf(".") + 1), cs, col);
                        }
                    }
                    if (!s_select.equals("") && cur_hql != null && !cur_hql.equals("")) {
                        cur_hql = cur_hql.startsWith("select") ? cur_hql.substring(cur_hql.indexOf("from ")) : cur_hql;
                        s_select = s_select.substring(1);
                        if (s_table.equals("")) {
                            fetch_hql = "select " + s_select + " " + cur_hql;
                        } else {
                            fetch_hql = "select " + s_select + " from " + s_table + " " + cur_hql.substring(5) + " and (" + s_where + ")";
                        }
                        if (pay_flag || in_flag || fetch_hql.endsWith("@sql")) {
                            if (fetch_hql.endsWith("@sql")) {
                                fetch_hql = fetch_hql.substring(0, fetch_hql.length() - 4);
                            }
                            fetch_hql = DbUtil.tranSQL(fetch_hql, "rule");
                            alias_data = CommUtil.selectSQL(fetch_hql);
                        } else {
                            alias_data = CommUtil.fetchEntities(fetch_hql);
                        }
                    }
                }
                if (alias_data != null) {
                    if (ind > 0 && alias_data.size() > 0) {
                        Object[] row_data = null;
                        if (ind == 1) {
                            row_data = new Object[1];
                            row_data[0] = alias_data.get(0);
                        } else {
                            row_data = (Object[]) alias_data.get(0);
                        }
                        for (int i = 0; i < ind; i++) {
                            ColumnSum cs = (ColumnSum) sums.get(i);
                            double value = 0;
                            if (i >= row_data.length) {
                                continue;
                            }
                            if (int_sums.contains(cs)) {
                                int data = SysUtil.objToInt(row_data[i]);
                                value = data + 0;
                            } else {
                                Double obj = (Double) SysUtil.objToDouble(row_data[i]);
                                if (obj != null) {
                                    value = obj.doubleValue();
                                }
                            }
                            this.addColumnStat(new FColumnStat(this, cs.getField_name(), cs.getType(), value, dfKeys.get(cs.getField_name())));
                        }
                    }
                }
            } else {
                cur_hql = hql;
            }
        }
        FTable.this.updateUI();
    }

    private String makeSumSQL(String entityName, String fieldName, ColumnSum cs, int col) {
        String sql = "";
        if (cs.getField_type() == null) {
            return sql;
        }
        String field_type = cs.getField_type().toLowerCase();
        String sumEntityName = entityName.equals("") ? "" : (entityName + ".");
        if (field_type.equals("int") || field_type.equals("integer")) {
            sql = ",cast(" + (cs.getType() == 0 ? "sum" : "avg") + "(" + DbUtil.getNull_strForDB(UserContext.sql_dialect) + "(" + sumEntityName + fieldName + ",0)) as "+DbUtil.getInteger_strForDB(UserContext.sql_dialect)+") as ed" + col;
        } else {
            sql = "," + (cs.getType() == 0 ? "sum" : "avg") + "(" + DbUtil.getNull_strForDB(UserContext.sql_dialect) + "(" + sumEntityName + fieldName + ",0)) as ed" + col;
        }
        return sql;
    }

    /**
     * ���ò�ѯItem��enable����
     * @param enable
     */
    public void setQueryItemEnable(boolean enable) {
        miQuery.setEnabled(enable);
    }

    /**
     * ������ʾ����Item��enable����
     * @param enable
     */
    public void setFieldItemEnable(boolean enable) {
        mSetShowScheme.setEnabled(enable);
    }

    /**
     * �����滻Item��enable����
     * @param enable
     */
    public void setReplaceItemEnable(boolean enable) {
        miColReplace.setEnabled(enable);
    }

    /**
     * ���õ���Item��enable����
     * @param enable
     */
    public void setExportItemEnable(boolean enable) {
        miExport.setEnabled(enable);
    }

    private void refreshPop() {
        popMenu_right.removeAll();
        popMenu_right.add(miQuery);
        popMenu_right.add(sep1);
        popMenu_right.add(miLocalCol);
        popMenu_right.add(miFreezeCol);
        popMenu_right.add(miUnFreezeCol);
        popMenu_right.add(miColSet);
        popMenu_right.add(sep2);
        popMenu_right.add(mSetShowScheme);
        popMenu_right.add(miSetColOrder);
        popMenu_right.add(miSetColSum);
        popMenu_right.add(miColReplace);
        popMenu_right.add(sep3);
        popMenu_right.add(miExport);
        popMenu_right.add(miPrint);
        mEnter.add(miEnterRight);
        mEnter.add(miEnterDown);
        miColSet.add(miHideCol);
        miColSet.add(miShowCol);
        miColSet.add(miRestoreColWidth);
        miColSet.add(miShowZero);
        miColSet.add(miDetail);
        miColSet.add(mEnter);
        for (String key : hideItems) {
            if (key.equals("query")) {
                popMenu_right.remove(miQuery);
                popMenu_right.remove(sep1);
            } else if (key.equals("order")) {
                popMenu_right.remove(miSetColOrder);
            } else if (key.equals("sum")) {
                popMenu_right.remove(miSetColSum);
            } else if (key.equals("replace")) {
                popMenu_right.remove(miColReplace);
            } else if (key.equals("show")) {
                popMenu_right.remove(mSetShowScheme);
            }
        }
        int i = popMenu_right.getComponentIndex(sep2);
        int i1 = popMenu_right.getComponentIndex(sep3);
        if (i + 1 == i1) {
            popMenu_right.remove(sep3);
        }
        buildSchemeItem();
        defineEnterWay(ConfigManager.getConfigManager().getProperty("table.enterTo"), miEnterRight, miEnterDown);
        ComponentUtil.setIcon(miQuery, "query");
    }

    /**
     * ���캯��
     *
     */
    public FTable() {
        super();
        refreshPop();
        ComponentUtil.setSysFuntionNew(FTable.this);
        miQuery.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                query();
            }
        });
        miSetColShow.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setShowFields();
            }
        });
        miFreezeCol.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                FTable.this.lockColumns(cur_column);
            }
        });
        miLocalCol.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                FTable.this.addLocalColumnPanel();
            }
        });
        miUnFreezeCol.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                FTable.this.lockColumns(-1);
            }
        });
        miHideCol.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                lockColumns(-1);
                if (field_orders.isEmpty()) {
                    if (fields == null) {
                        fields = new ArrayList();
                    }
                    if (all_fields.isEmpty()) {
                        all_fields.addAll(EntityBuilder.getCommFieldInfoListOf(model.getEntityClass(), EntityBuilder.COMM_FIELD_VISIBLE));
                    }
                    if (fields.isEmpty() && model.getEntityClass() != null) {
                        fields.addAll(EntityBuilder.getCommFieldNameListOf(model.getEntityClass(), EntityBuilder.COMM_FIELD_VISIBLE));
                    }
                    for (int i = 0; i < fields.size(); i++) {
                        field_orders.put((String) fields.get(i), i);
                    }
                }
                int i = getCurrentColumnIndex();
                hide_columns.add((String) fields.get(i));
                fields.remove(i);
                setFields(fields);
                buildViewColumn(hide_columns);
            }
        });
        miEnterRight.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                defineEnterWay("Right", miEnterRight, miEnterDown);
            }
        });
        miEnterDown.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                defineEnterWay("Down", miEnterRight, miEnterDown);
            }
        });
        miRestoreColWidth.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                restoreColumnWidth();
            }
        });
        miExport.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                exportData();
            }
        });
        miSetColOrder.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (order_fields.isEmpty()) {
                    order_fields.addAll(all_fields);
                }
                ShowOrderDialog eDlg = new ShowOrderDialog(model.getEntityClass(), order_fields, default_orders, useModuleCode, curOrderScheme);
                ContextManager.locateOnMainScreenCenter(eDlg);
                eDlg.setVisible(true);
                if (eDlg.getCurOrderScheme() != null) {
                    curOrderScheme = eDlg.getCurOrderScheme();
                    for (IPickFieldOrderListener listener : iPickFieldOrderListeners) {
                        listener.pickOrder(curOrderScheme);
                    }
                }
            }
        });
        miSetColSum.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ColumnSumDialog eDlg = new ColumnSumDialog(model.getEntityClass(), all_fields, col_sums, useModuleCode);
                ContextManager.locateOnMainScreenCenter(eDlg);
                eDlg.setVisible(true);
                if (eDlg.isClick_ok()) {
                    String hql = cur_hql;
                    cur_hql = "@@@";
                    addColumnSum(eDlg.getCol_sums(), hql);
                }
            }
        });
        miColReplace.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                replaceData();
            }
        });
        miDetail.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int row = FTable.this.getSelectedRowModelIndex();
                int col = getCurrentColumnIndex();
                Object obj = FTable.this.model.getValueAt(row, col);
                String s = obj == null ? "" : obj.toString();
                MsgUtil.showHRMsg(s.toString(), "��ϸ��Ϣ");
            }
        });
        miPrint.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ReportPanel.previewBy("", FTable.this);
            }
        });

        model = new FTableModel();
        this.setModel(model);
        this.addTableHeaderMouseListener(headerMouseRightClickedListener);
        /**
         * �������һ�У���ѡ�򲻿���
         *
         * @author jerry
         */
        /** ************************begin*********************** */
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (right_allow_flag) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        refreshPop();
                        for (IPickPopupListener listener : pplListeners) {
                            listener.addMenuItem(popMenu_right);
                        }
                        for (int j = 0; j < popMenu_right.getComponentCount(); j++) {
                            Component c = popMenu_right.getComponent(j);
                            if (c instanceof JMenuItem) {
                                ComponentUtil.setMenuIcon((JMenuItem) c);
                                JMenuItem mi = (JMenuItem) c;
                                if (mi.getIcon() == null) {
                                    ComponentUtil.setIcon(mi, "blank");
                                }
                            }
                        }
                        popMenu_right.show((Component) e.getSource(), e.getX() - 35, e.getY());
                    }
                }
                if (e.getButton() != MouseEvent.BUTTON1 || !(e.getSource() instanceof JTable)) {
                    return;
                }
                JTable tableView = (JTable) e.getSource();
                tableView.grabFocus();
                try {
                    clickRow(tableView);
                } catch (IllegalArgumentException iae) {
                    // ������ѡ�����
                    if (tableView == getRightActiveTable()) {
                        clickRow(getLeftLockedTable());
                    } else if (tableView == getLeftLockedTable()) {
                        clickRow(getRightActiveTable());
                    }
                }
            }

            /**
             * ��굥������У��л�ѡ����ѡ��״̬
             *
             * @param table
             *            �������ı��
             */
            private void clickRow(JTable table) {

                int colIndex = table.getSelectedColumn();
                if (colIndex < 0) {
                    return;
                }
                String colName = (String) table.getColumnModel().getColumn(
                        colIndex).getIdentifier();
                if (!isCheckBoxAffectedByClickRow() && !colName.equals("isCheck")) {
                    return;
                }

                int rowIndex = getLeftLockedTable().getSelectedRow();
                if (rowIndex < 0) {
                    return;
                }

                Object v = getValueAt(rowIndex, "isCheck");// ((Map)
                // model.getData().get(rowIndex)).get("isCheck");
                Boolean checkBoxValue = null;// new Boolean(false);
                if (v instanceof Boolean) {
                    checkBoxValue = (Boolean) v;
                } else {
                    /**
                     * ����������һ���Ǹ�ѡ��ʧЧ���Լ��ϼ��и�ѡ��ʧЧ
                     *
                     * @author jerry
                     */
                    /** ******************begin****************** */
                    if (v != null && !(v instanceof SumRowValueTypes)) {
                        if (!v.equals("")) {
                            checkBoxValue = new Boolean(true);
                        }
                        if (v.equals("")) {
                            checkBoxValue = new Boolean(false);
                        }

                    }
                    if (v == null && !(v instanceof SumRowValueTypes) && isCheck) {
                        checkBoxValue = new Boolean(false);
                    }
                    /** ******************end******************** */
                }
                if (checkBoxValue != null) {
                    setValueAt(new Boolean(!checkBoxValue.booleanValue()),
                            rowIndex, "isCheck");
                    table.repaint();
                }
            }
        });
        updateRowNumberColumn();
        this.addTableHeaderMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                stopEditing();
                if (e.getClickCount() < 2) {
                    return;
                }
                JTable tableView = ((JTableHeader) e.getSource()).getTable();
                TableColumnModel columnModel = tableView.getColumnModel();
                int viewColumn = columnModel.getColumnIndexAtX(e.getX());
                if (tableView == activeTable) {
                    cur_column = tableView.convertColumnIndexToModel(viewColumn);
                } else {
                    cur_column = tableView.convertColumnIndexToModel(viewColumn) - 1;
                }
                // ˫����ͷ����
                if (cur_column != -1) {
                    FBaseTableColumn sortCol = (FBaseTableColumn) columnModel.getColumn(viewColumn);
                    FBaseTableColumn tempCol = null;
                    int columns = FTable.this.getColumnModel().getColumnCount();
                    if (model.getEntityClass() == null) {
                        for (int i = 0; i < columns; i++) {
                            tempCol = (FBaseTableColumn) FTable.this.getColumnModel().getColumn(i);
                            if (tempCol.getIdentifier().equals("isCheck")) {
                                continue;
                            }
                            DefaultTableCellRenderer hdr = (DefaultTableCellRenderer) tempCol.getHeaderRenderer();

                            Icon hdrIcon = null;

                            // lgc add start
                            if (tempCol == sortCol && isSortable()) {
                                sortByColumn(sortCol, ascending);
                                hdrIcon = ascending ? ASCENDING_ICON
                                        : DESCENDING_ICON;
                            }
                            // lgc add end
                            hdr.setIcon(hdrIcon);

                            hdr.setIcon(hdrIcon);

                        }
                        ascending = !ascending;
                        repaint();
                    } else {
                        for (int i = 0; i < columns; i++) {
                            tempCol = (FBaseTableColumn) FTable.this.getColumnModel().getColumn(i);
                            if (tempCol.getIdentifier().equals("isCheck")) {
                                continue;
                            }
                            DefaultTableCellRenderer hdr = (DefaultTableCellRenderer) tempCol.getHeaderRenderer();

                            Icon hdrIcon = null;

                            // lgc add start
                            if (tempCol == sortCol && sortCol.isSortable()) {
                                String id = tempCol.getId();
                                System.out.println("id "+id);
                                ShowScheme ss = new ShowScheme();
                                ShowSchemeOrder sso = new ShowSchemeOrder();
                                sso.setShowScheme(ss);
                                sso.setField_name(id);
                                sso.setField_order(ascending ? "ASC" : "DESC");
                                ss.getShowSchemeOrders().add(sso);
                                hdrIcon = ascending ? ASCENDING_ICON
                                        : DESCENDING_ICON;
                                for (IPickFieldOrderListener listener : iPickFieldOrderListeners) {
                                    listener.pickOrder(ss);
                                }
                            }
                            // lgc add end
                            hdr.setIcon(hdrIcon);
                        }
                        ascending = !ascending;
                        repaint();

                    }
                } else {
                    int i = tableView.getRowCount();
                    if (i > 0) {
                        tableView.setRowSelectionInterval(0, i - 1);
                    }
                }
            }
        });
        /** ************************end************************* */
    }

    /**
     * �÷���������������س����ƶ����򣬲��ı�˵�ͼ��
     * @param way��Down�����£�Right�����ң�Ĭ��Right
     * @param rightItem�����Ҳ˵�
     * @param downItem�����²˵�
     */
    private void defineEnterWay(String way, JMenuItem rightItem, JMenuItem downItem) {
        if (way == null || way.trim().equals("")) {
            way = "Right";
        }
        ComponentUtil.setIcon(rightItem, "Down".equals(way) ? "blank" : "editWay");
        ComponentUtil.setIcon(downItem, "Down".equals(way) ? "editWay" : "blank");
        if ("Down".equals(way)) {
            enterToDown();
        } else {
            enterToRight();
        }
    }

    /**
     * ������ʾ�ֶ�
     */
    public void setShowFields() {
        ShowFieldDialog2 eDlg = new ShowFieldDialog2(JOptionPane.getFrameForComponent(FTable.this), model.getEntityClass(), all_fields, default_fields, useModuleCode, cur_show_scheme, scheme_list);
        ContextManager.locateOnMainScreenCenter(eDlg);
        eDlg.setVisible(true);
        if (eDlg.isClick_ok()) {
            scheme_list.clear();
            scheme_list.addAll(eDlg.getScheme_list());
            rebuildSchemeItem();
            String key = cur_show_scheme.getShowScheme_key();
            item_table.get(key).getAction().actionPerformed(null);
        }
    }

    /**
     * �滻����
     */
    public void replaceData() {
        ColumnReplacePanel pnlCr = new ColumnReplacePanel(FTable.this);
//        for (IPickColumnReplaceListener listener : iPickColumnReplaceListeners) {
//            pnlCr.setContainer_data(listener.getPersonContainerData(), listener.isContainerVisible());
//        }
        pnlCr.addPickColumnReplaceListener(new IPickColumnReplaceListener() {

            @Override
            public void pickReplace() {
                cur_hql = null;
                List<String> keys=FTable.this.getAllKeys();
                FTable.this.setObjects(keys);
//                for (IPickColumnReplaceListener listener : iPickColumnReplaceListeners) {
//                    listener.pickReplace();
//                    FTable.this.updateUI();
//                }
            }

//            @Override
//            public List getPersonContainerData() {
//                return null;
//            }
//
//            @Override
//            public boolean isContainerVisible() {
//                return true;
//            }
        });
        ModelFrame.showModel((JFrame) JOptionPane.getFrameForComponent(FTable.this), pnlCr, true, "�滻", 800, 600);
    }

    /**
     * ��ѯ
     */
    public void query() {
        if (fetch_query_scheme_flag) {
            if (schemeDlg == null) {
                schemeDlg = new QuerySchemeDialog(JOptionPane.getFrameForComponent(this), model.getEntityClass(), useModuleCode + "." + model.getEntityClass());
            }
            ContextManager.locateOnScreenCenter(schemeDlg);
            schemeDlg.setVisible(true);
            if (schemeDlg.getQueryScheme() != null) {
                if (!QueryParamDialog.ShowQueryParamDialog(FTable.this, schemeDlg.getQueryScheme())) {
                    return;
                }
                for (IPickQueryExListener listener : iPickQueryExListeners) {
                    listener.pickQuery(schemeDlg.getQueryScheme());
                }
                this.setCur_query_scheme(schemeDlg.getQueryScheme());
            }
        }
    }

    /**
     * ��������
     */
    public void exportData() {
        if (model.getEntityClass() != null) {
            ExportDialog eDlg = new ExportDialog(model.getEntityClass(), FTable.this, useModuleCode, all_fields);
            ContextManager.locateOnMainScreenCenter(eDlg);
            eDlg.setVisible(true);
        } else {
            ExportDataDialog dlg = new ExportDataDialog(JOptionPane.getFrameForComponent(FTable.this), FTable.this);
            ContextManager.locateOnMainScreenCenter(dlg);
            dlg.setVisible(true);
        }
    }

    private void buildViewColumn(List<String> hide_columns) {
        miShowCol.removeAll();
        for (final String column_name : hide_columns) {
            String field_caption = column_name;
            for (TempFieldInfo tfi : all_fields) {
                if (tfi.getField_name().equals(column_name)) {
                    field_caption = tfi.getCaption_name();
                    break;
                }
            }
            JMenuItem mi = new JMenuItem(field_caption);
            ComponentUtil.setIcon(mi, "blank");
            mi.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = field_orders.get(column_name);
                    if (index >= FTable.this.fields.size()) {
                        fields.add(column_name);
                    } else {
                        fields.add(index, column_name);
                    }
                    FTable.this.hide_columns.remove(column_name);
                    setFields(fields);
                    buildViewColumn(FTable.this.hide_columns);
                }
            });
            miShowCol.add(mi);
        }
    }

    public FTable(List field_names) {
        this(field_names, null);
    }

    /**
     * ���캯��
     *
     * @param allowMultiChoice
     *            �Ƿ�������ж�ѡ
     */
    public FTable(boolean allowMultiChoice) {
        // super();
        // model = new FTableModel();
        // this.setModel(model);
        this();
        isCheck = allowMultiChoice;
        if (isCheck) {
            addCheckChoice();
        }
    }

    /**
     * ���ӿɶ�ѡ�Ĺ���
     *
     */
    private void addCheckChoice() {
        FBaseTableColumn column = new FBaseTableColumn();
        column.setHeaderRenderer(new FCheckBoxTableHeaderRenderer());
        column.setId("isCheck");
        column.setSortable(false);
        // column.setTitle("ѡ��");
        column.setWidth(20);
        // column.setEditable(true);
        FCheckBox box = new FCheckBox();
        column.addControl(box);
        column.setResizable(false);
        // column.set
        column.sizeWidthToFit();
        // this.addColumn(column);
        // ((JLabel)column.getHeaderRenderer()).setIcon(this.UNCHECKED_ICON);
        this.addControl(column);
    }

    /**
     * �����Ƿ�֧�ֶ�ѡ
     *
     * @param isCheck
     *            trueΪ֧�ֶ�ѡ��falseΪ��֧��
     */
    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
        if (isCheck) {
            addCheckChoice();
        } else {
            if (this.getSubControl("isCheck") != null) {
                this.removeColumn((FBaseTableColumn) this.getSubControl("isCheck"));

            }
        }
    }

    /**
     * �õ���ǰ�Ƿ�֧�ֶ�ѡ
     *
     * @return trueΪ֧�ֶ�ѡ��falseΪ��֧��
     */
    public boolean getIsCheck() {
        return this.isCheck;
    }

    /**
     * ����һ��
     *
     * @param column
     *            ����ж���
     */
    @Override
    public void addColumn(FBaseTableColumn column) {

        if (this.getModel() != null) {
            FBaseTableColumn[] cols = column.getAllSubTableColumns();
            for (int i = 0; i < cols.length; i++) {
                int index;
                FTableColumnItem item = new FTableColumnItem();
                item.setId(cols[i].getId());
                item.setTitle(cols[i].getTitle());
                item.setEditable(cols[i].isEditable());
                item.setEditable_when_new(cols[i].isEditable_when_new());
                item.setLargeText(cols[i].isLargeText());
                index = ((FTableModel) this.getModel()).addColumn(item);
                // �����в��ɹ�
                if (index == -1) {
                    return;
                }
                cols[i].setModelIndex(index);
            }

            column.setParentControl(this);

            super.addColumn(column);
            if (cols.length > 1) {
                this.getRightActiveTable().getTableHeader().setReorderingAllowed(false);
                this.getLeftLockedTable().getTableHeader().setReorderingAllowed(false);
            }
        }

    }

    /**
     * ���Ӷ��ͷ
     *
     * @param columnGroup
     *            ���ͷ��
     */
    public void addColumnGroup(ColumnGroup columnGroup) {
        // �����ͷ�а����ĵ��в��뵽����
        this.addColFromColGroup(columnGroup);
        // �����ͷ���뵽����
        header.addColumnGroup(columnGroup);
        // �����ұ�ͷ
        this.getRightActiveTable().setTableHeader(header);

        // �������ͷ���ұ�ͷ�߶�һ��
        leftHeader.setSignHeight(header.getHeaderHeight());
        // �������ͷ
        this.getLeftLockedTable().setTableHeader(leftHeader);

        // ��ͷ����������
        this.initEventListeners();
    }

    /**
     * �����ͷ�а����ĵ��в��뵽����
     *
     * @param columnGroup
     *            ���ͷ
     */
    public void addColFromColGroup(ColumnGroup columnGroup) {
        // ȡ�ö��ͷ�а������ӿؼ�
        List colList = columnGroup.getSubControls();
        // ���ͷ�ؼ�����
        int colListSize = colList.size();
        // �����ͷ�а����ĵ��в��뵽����
        for (int i = 0; i < colListSize; i++) {
            Control control = (Control) colList.get(i);
            // ����ؼ�Ϊ���ͷ����ݹ����
            if (control instanceof ColumnGroup) {
                ColumnGroup colGroup = (ColumnGroup) control;
                this.addColFromColGroup(colGroup);
            } // ����ؼ�Ϊ���У�����뵽����
            else if (control instanceof FBaseTableColumn) {
                this.addColumn((FBaseTableColumn) control);
                control.setParentControl(this);
            }
        }
    }

    /**
     * ɾ������һ����Ϣ
     *
     * @param column
     *            ����Ϣ
     */
    /*
    public void removeColumn(FBaseTableColumn column) {
    if (this.getModel() != null) {
    //			FBaseTableColumn[] cols = column.getAllSubTableColumns();
    //			for (int i = 0; i < cols.length; i++) {
    //				FTableColumnItem item = new FTableColumnItem();
    //				item.setId(cols[i].getId());
    //				item.setTitle(cols[i].getTitle());
    //				item.setEditable(cols[i].isEditable());
    //				((FTableModel) this.getModel()).removeColumn(item);
    //			}
    //
    super.removeColumn(column);
    }
    }
     */
    /**
     * ��ձ�����
     */
    public void removeData() {
        setData((List) null);
    }

    /**
     * ������������
     *
     * @param dataList
     *            ������Ϣ����
     */
    public void setData(List dataList) {
        model.setData(dataList, isCheck);
    }

    /**
     * �õ�����е�����
     *
     * @return �ɱ���¼Map��ɵ�����
     */
    public List getData() {
        List dataList = model.getData();
        if (dataList != null && dataList.size() == 0) {
            return null;
        }
        return dataList;
    }

    /**
     * ɾ��������
     */
    public void deleteAllRows() {
        this.removeAllListSelectionListener();
        if (model.getData() != null) {
            model.getData().clear();
            model.fireTableDataChanged();
        }
        this.refreshListSelectionListener();
    }

    /**
     * ɾ����ѡ��
     */
    public void deleteSelectedRows() {
        this.removeAllListSelectionListener();
        int tmp_ind = this.getSelectedRowModelIndex();
        model.deleteBy(getSelectedData());
        tmp_ind = Math.min(tmp_ind, model.getRowCount() - 1);
        if (tmp_ind >= 0) {
            this.setRowSelectionInterval(tmp_ind, tmp_ind);
        }
        this.refreshListSelectionListener();
        if (iPickColumnSumListeners.size() > 0) {
            cur_hql = "";
            String hql = null;
            for (IPickColumnSumListener listener : iPickColumnSumListeners) {
                hql = listener.pickSumSQL();
            }
            addColumnSum(null, hql);
        }
    }

    /**
     * ��ñ���и�ѡ��ѡ�е����ݣ����Խ��ж�ѡ�����Է��ض������ݡ�
     *
     * @return ����ѡ��ѡ�е����ݣ����û��ѡ���κ����ݣ��򷵻�SizeΪ0��List
     */
    public List getSelectedDataByCheckBox() {
        List retList = new ArrayList();
        List dataList = model.getData();
        if (isCheck) {
            for (int i = 0; dataList != null && i < dataList.size(); i++) {
                Map map = (Map) dataList.get(i);
                if ((map.get("isCheck") != null) && (map.get("isCheck") != "") && ((Boolean) map.get("isCheck")).booleanValue()) {
                    retList.add(map);
                }
            }
        } else {
            if (this.getCurrentRowIndex() == -1) {
                return retList;
            }

            Map map = (Map) dataList.get(this.getSelectedRowModelIndex());

            retList.add(map);
        }
        return retList;
    }

    public List getAllSelectObjects() {
        return model.getAllSelectObjectsByIndexs(this.getSelectedRowModelIndexes());
    }

    /**
     * ��ñ���и�ѡ��ѡ�е����ݣ����Խ��ж�ѡ�����Է��ض������ݡ�
     *
     *  �˷����Ѿ���������ʹ��getSelectedDataByCheckBox();
     * @return ����ѡ�е����ݣ����ѡ������Ϊ�գ�����һ��SizeΪ0��List
     */
    public List getSelectedData() {
        List retList = new ArrayList();
        List dataList = model.getData();

        for (int i : this.getSelectedRowModelIndexes()) {
            if (i < dataList.size()) {
                retList.add(dataList.get(i));
            }
        }
        return retList;
    }

    /**
     * �õ���ǰ����ѡ�������ݣ�������Խ��ж��и���ѡ���򷵻ص�һ������ѡ������ݡ�
     *
     * @return ����ѡ�е�����
     */
    public Object getCurrentRow() {
        if (model.getData().size() == 0) {
            return model.getBlack_object();
        }
        if (this.getCurrentRowIndex() < 0) {
            return null;
        }

        Object obj = model.getData().get(this.getSelectedRowModelIndex());

        if (obj instanceof String || obj instanceof Integer) {
            model.lazyFetch(this.getCurrentRowIndex());
        }
        obj = model.getData().get(this.getSelectedRowModelIndex());
        return obj;
    }

    public void replaceRow(Object obj, String key) {
        if (model.getData().size() == 0) {
            return;
        }
        if (this.getCurrentRowIndex() < 0) {
            return;
        }
        String keyValue = (String) PublicUtil.getProperty(obj, key);
        int len = model.getData().size();
        int index = -1;
        for (int i = 0; i < len; i++) {
            Object data = model.getData().get(i);
            if (data instanceof String) {
                if (data.equals(keyValue)) {
                    index = i;
                    break;
                }
            } else if (data instanceof Integer) {
                continue;
            } else {
                String value = (String) PublicUtil.getProperty(data, key);
                if (value.equals(keyValue)) {
                    index = i;
                    break;
                }
            }
        }
        if (index != -1) {
            model.getData().set(index, obj);
        }
        BeanManager.manager(obj);
        this.updateUI();
    }

    /**
     * �õ���ǰ����ѡ�������ݣ�������Խ��ж��и���ѡ���򷵻ص�һ������ѡ������ݡ�
     *
     * @return ����ѡ�е�����
     */
    public void setCurrentRow(Object obj) {
        if (model.getData().size() == 0) {
            return;
        }
        if (this.getCurrentRowIndex() < 0) {
            return;
        }

        model.getData().set(this.getSelectedRowModelIndex(), obj);
    }

    /**
     * �õ�����ѡ�е�����
     *
     * @return ѡ�е����ݣ���������List��XMLData��
     */
    public List getHighLightRows() {
        List retList = new ArrayList();
        List dataList = model.getData();
        if (dataList != null) {
            int[] indices = this.getRightActiveTable().getSelectedRows();
            for (int i = 0; i < indices.length; i++) {

                retList.add(dataList.get(convertTableRowToModelRow(indices[i])));
            }
        }
        return retList;
    }

    /**
     * ɾ������ѡ�е�����
     *
     */
    public void deleteHighLightRows() {
        List hlRows = getHighLightRows();
        for (int i = 0; i < hlRows.size(); i++) {
            model.getData().remove(hlRows.get(i));
        }
        model.fireTableDataChanged();
    }

    /**
     * �õ�ĳһ�е�����
     *
     * @param index
     *            ָ��ĳһ��
     *
     * @return ����Map
     */
    public Map getDataByIndex(int index) {
        List dataList = model.getData();
        Map map = (Map) dataList.get(convertViewRowIndexToModel(index));
        return map;
    }

    /**
     * �ڱ���ĩβ����һ������
     */
    public void addBlankRow() {
        model.addBlankRow();
    }

    /**
     * �ڱ���ָ��λ�ú�����һ������
     *
     * @param index
     *            �ƶ����к�
     */
    public void addBlankRow(int index) {
        model.addBlankRow(index);
    }

    /**
     * �ڵ�ǰ����ĩβ����һ��ֵ
     *
     * @param map
     *            ����Ϣֵ��
     */
    public void addRow(Map map) {
        model.addRow(map);
    }

    /**
     * ����һ�е�ֵ
     *
     * @param map
     *            ����Ϣֵ��
     * @param index
     *            �����λ��
     */
    public void addRow(Map map, int index) {
        model.addRow(map, index);
    }

    /**
     * �޸�һ��
     *
     * @param map
     *            �µ�Map
     * @param index
     *            λ��
     */
    public void modifyRow(Map map, int index) {
        model.modifyRow(map, index);
    }

    /**
     * ɾ��һ�е�ֵ
     *
     * @param index
     *            ��ɾ��λ�õ����
     */
    public void deleteRow(int index) {
        if (index == -1) {
            return;
        }
        int tmp_ind = index;
        //model.deleteBy(getSelectedData());

        //this.stopEditing();
        model.deleteRow(index);
        tmp_ind = Math.max(tmp_ind - 1, 0);
        if (tmp_ind >= 0) {
            this.setRowSelectionInterval(tmp_ind, tmp_ind);
        }
    }

    /**
     * ����ָ���еĸ�ѡ���Ƿ�ѡ��
     *
     * @param rowIndex
     *            ָ�������
     * @param isSelected
     *            �Ƿ�ѡ��
     */
    public void setCheckBoxSelectedAtRow(int rowIndex, boolean isSelected) {
        model.setCheckBoxSelectedAtRow(rowIndex, isSelected);
    }

    /**
     * ��ȡ�������µĿؼ�
     *
     * @return �ؼ��б�
     *
     */
    @Override
    public List getSubControls() {
        if (this.subControls.isEmpty()) {
            return null;
        } else {
            return this.subControls;
        }
    }

    /**
     * ����id��ȡ�ؼ�
     *
     * @param id
     *            �ؼ�id
     *
     * @return �ؼ�
     *
     */
    @Override
    public Control getSubControl(String id) {
        return SubControlFinder.getSubControlBySerialId(this, id);
    }

    /**
     * ��ȡ���ؼ�
     *
     * @return ���ؼ�
     *
     */
    @Override
    public Control getParentControl() {
        return this.parent;
    }

    /**
     * ���ø��ؼ�
     *
     * @param parent
     *            ���ؼ�
     *
     */
    @Override
    public void setParentControl(Control parent) {
        this.parent = parent;
    }

    /**
     * �����ӿؼ�
     *
     * @param control
     *            FPanel�е��ӿؼ�
     */
    @Override
    public void addControl(Control control) {
        // this.addControl(control, new TableConstraints(1, 1, false));
        // this.subCtrlsMap.put(control.getId(), control);
        if (control instanceof FBaseTableColumn) {
            this.addColumn((FBaseTableColumn) control);
        } else if (control instanceof ColumnGroup) {
            this.addColumnGroup((ColumnGroup) control);
        }
        this.subControls.add(control);
    }

    /**
     * �����ӿؼ�,ͬʱ����Լ��
     *
     * @param control
     *            �ӿؼ�
     * @param contraint
     *            �ؼ���������
     */
    @Override
    public void addControl(Control control, Object contraint) {
        if (control instanceof FBaseTableColumn) {
            this.addColumn((FBaseTableColumn) control);
        } else if (control instanceof ColumnGroup) {
            this.addColumnGroup((ColumnGroup) control);
        }
        // this.addControl(control, new TableConstraints(1, 1, false));
        this.subControls.add(control);
    }

    /**
     * ���ñ���
     *
     * @param title
     *            ����
     *
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * ��ȡ����
     *
     * @return ����
     *
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * ���������б�ͷ
     *
     * @param newCols
     *            �µ��ж�������
     */
    public void resetHeader(List newCols) {
        if (newCols != null) {
            FBaseTableColumn[] columns = new FBaseTableColumn[newCols.size()];
            for (int i = 0; i < newCols.size(); i++) {
                columns[i] = (FBaseTableColumn) newCols.get(i);
            }
            this.resetHeader(columns);
        }
    }

    /**
     * �������ñ�ͷ
     *
     * @param newCols
     *            ��ͷ�б�
     *
     */
    public void resetHeader(FBaseTableColumn[] newCols) {

        // FTableColumnItem chkColItem = removeAllSubControls();
        // List colItems = ((FTableModel)getModel()).getColumnList();
        // if (chkColItem != null) colItems.add(chkColItem);

        boolean isExistChkCol = false;
        for (int i = 0; newCols != null && i < newCols.length; i++) {
            FBaseTableColumn col = newCols[i];
            if (col != null && "isCheck".equals(col.getId())) {
                isExistChkCol = true;
                break;
            }
        }
        if (this.isCheck && !isExistChkCol) {
            this.addCheckChoice();
        }

        for (int j = 0; newCols != null && j < newCols.length; j++) {
            this.addControl(newCols[j]);
        }
    }

    /**
     * ���ȫ���ӿؼ�
     *
     * @return ���ض�ѡ��
     */
    public FTableColumnItem removeAllSubControls() {
        for (int i = 0; i < this.subControls.size(); i++) {
            FBaseTableColumn col = (FBaseTableColumn) this.subControls.get(i);
            FBaseTableColumn[] subCols = col.getAllSubTableColumns();
            if (subCols.length > 1) {
                for (int s = 0; s < subCols.length; s++) {
                    this.removeColumn(subCols[s]);
                }
            } else {
                this.removeColumn(col);
            }
        }
        this.subControls.clear();

        List colItems = ((FTableModel) getModel()).getColumnList();
        FTableColumnItem chkColItem = null;
        for (int i = 0; i < model.getColumnList().size(); i++) {
            FTableColumnItem colItem = (FTableColumnItem) colItems.get(i);
            if (colItem.getId().equals("isCheck")) {
                chkColItem = colItem;
                break;
            }
        }
        colItems.clear();

        return chkColItem;
    }

    /**
     * ��ȡ�Ƿ��Զ����ؼ��䴦��
     *
     * @return true���ǣ�false����
     */
    public boolean isLocalmemo() {
        return isLocalmemo;
    }

    /**
     * �Ƿ��Զ����ؼ��䴦��
     *
     * @param isLocalmemo
     *            true���ǣ�false����
     *
     */
    public void setLocalmemo(boolean isLocalmemo) {
        this.isLocalmemo = isLocalmemo;
    }

    /**
     * ��ȡ����Ĭ����ʾ��ʽ
     *
     * @return ��ʾ��ʽ
     */
    public String getDefaultXML() {
        return defaultXML;
    }

    /**
     * ���ñ���е�Ĭ����ʾ��ʽ
     *
     * @param defaultXML
     *            ��ʾ��ʽ
     */
    public void setDefaultXML(String defaultXML) {
        this.defaultXML = defaultXML;
    }

    /**
     * �÷������ڸ���stepֵ����/���ƶ���ǰ��
     * @param step��1������һ�У�-1������һ��
     */
    public void moveRowPosition(int step) {
        Object obj = this.getCurrentRow();
        if (obj == null) {
            return;
        }
        int ind = this.getCurrentRowIndex();
        ind = ind + step;
        this.getObjects().remove(obj);
        if (ind > this.getObjects().size()) {
            ind = 0;
        } else if (ind < 0) {
            ind = this.getObjects().size();
        }
        this.getObjects().add(ind, obj);
        this.setRowSelectionInterval(ind, ind);
    }

    @Override
    public String getModuleCode() {
        return module_code;
    }

    class ShowSchemeAction extends AbstractAction {

        private ShowScheme ss;

        public ShowSchemeAction(ShowScheme ss) {
            super(ss.getShowScheme_name());
            this.ss = ss;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (ss == null) {
                return;
            }
            cur_show_scheme = ss;
            FTable.this.lockColumns(-1);
            List<String> list = new ArrayList<String>();
            for (ShowSchemeDetail ssd : ss.getShowSchemeDetails()) {
                if (ssd.getEntity_name().equalsIgnoreCase(model.getEntityClass().getSimpleName())) {
                    list.add(ssd.getField_name());
                } else {
                    list.add(ssd.getEntity_name() + "." + ssd.getField_name());
                }
            }
            field_orders.clear();
            for (int i = 0; i < list.size(); i++) {
                field_orders.put(list.get(i), i);
            }
            buildViewColumn(hide_columns);
            for (Object obj : scheme_list) {
                ((ShowScheme) obj).setDefault_flag("0");
            }
            ss.setDefault_flag("1");
            FTable.this.setFields(useModuleCode);
            hide_columns.clear();
            for (IPickFieldSetListener listener : iPickFieldSetListeners) {
                listener.pickField(ss);
            }
            if (ss.getShowScheme_key() != null) {
                String sql_s = "update ShowScheme set default_flag = '0' where entity_name='" + ss.getEntity_name() + "' and person_code='" + UserContext.person_code + "';";
                if (!ss.getShowScheme_key().equals("-1")) {
                    sql_s += "update ShowScheme set default_flag = '1' where showScheme_key = '" + ss.getShowScheme_key() + "';";
                }
                CommUtil.excuteSQLs(sql_s, ";");
            }
            for (String ss_key : item_table.keySet()) {
                JMenuItem item = item_table.get(ss_key);
                ComponentUtil.setIcon(item, ss_key.equals(ss.getShowScheme_key()) ? "select" : "blank");
            }
        }
    }

    public ShowScheme getCurOrderScheme() {
        return curOrderScheme;
    }

    public static void main(String[] arg0) {
//        System.out.println(new DecimalFormat("0.0").format(20));
//        System.out.println("sss".indexOf("1"));
//        List<String> fields = new ArrayList<String>();
//        fields.add("�й�");
//        fields.add("����");
//        Hashtable<String, List<String>> field_keys = new Hashtable<String, List<String>>();
//        List<String> china_field = new ArrayList<String>();
//        china_field.add("���");
//        china_field.add("̨��");
//        field_keys.put("�й�", china_field);
//        List<String> ama_field = new ArrayList<String>();
//        //ama_field.add("���������");
//        //ama_field.add("���������1");
//        field_keys.put("����", ama_field);
//        JFrame jf = new JFrame();
//        FTable table = new FTable(fields, field_keys);
////        FBaseTableColumn column = new FBaseTableColumn();
////        column.setId("001");
////        column.setTitle("����");
////        column.setEditable(false);
////
////        FBaseTableColumn column1 = new FBaseTableColumn();
////        column1.setId("002");
////        column1.setTitle("����---");
////        column1.setEditable(false);
////
////        ColumnGroup cg = new ColumnGroup("11");
////        cg.addControl(column);
////        cg.addControl(column1);
////        ColumnGroup cg1 = new ColumnGroup("112");
////        table.addColumnGroup(cg);
////        //table.addColFromColGroup(column);
//////        table.addColumn(column);
//////        table.addColumn(column1);
////        System.out.println(table.getColumnCount());
////        List<Object[]> objs = new ArrayList<Object[]>();
////        Object[] obj = {"1111", "2222"};
////        objs.add(obj);
////        table.setObjects(objs);
//        // table.removeColumn(column);
//        jf.add(table);
//        jf.setSize(500, 500);
//        jf.setVisible(true);
        //System.out.println(table.getColumnCount());
        // table.addColumn(column);
        // table.removeColumn(column1);
    }
}
