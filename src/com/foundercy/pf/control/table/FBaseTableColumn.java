package com.foundercy.pf.control.table;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.foundercy.pf.control.AbstractDataField;
import com.foundercy.pf.control.AbstractRefDataField;
import com.foundercy.pf.control.Compound;
import com.foundercy.pf.control.Control;
import com.foundercy.pf.control.FCheckBox;
import com.foundercy.pf.control.FComboBox;
//import com.foundercy.pf.control.FDecimalField;
//import com.foundercy.pf.control.FIntegerField;
import com.foundercy.pf.control.FRadioGroup;
import com.foundercy.pf.control.FormatManager;
import com.foundercy.pf.control.RefModel;
import com.foundercy.pf.control.RefModelFactory;
//import com.foundercy.pf.control.TreeAssistRefModel;
import com.foundercy.pf.control.UIControl;

/**
 * <p>
 * Title: ����У�ʵ���˸�����
 * </p>
 * <p>
 * Description:����У�ʵ���˸�����
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * <p>
 * Company:����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * 
 * @author fangyi
 * @author �ƽ� 2008��4��10������
 * @version 1.0
 */
public class FBaseTableColumn extends TableColumn implements UIControl,
        Compound {

    private static final long serialVersionUID = -2073957592742841133L;
    /**
     * ���ؼ�
     */
    private Control parentControl;
    /**
     * ���з�ʽ������
     */
    public static final String LEFT = "left";
    /**
     * ���з�ʽ������
     */
    public static final String RIGHT = "right";
    /**
     * ���з�ʽ������
     */
    public static final String CENTER = "center";
    /**
     * Ĭ����С�п��
     */
    public static final int DEFAULT_MIN_COLWIDTH = 20;
    /**
     * Ĭ���п��
     */
    public static final int DEFAULT_COLWIDTH = 100;
    // public static final int DEFAULT_SHOW_COLWIDTH = 100;
    /**
     * Ĭ������п��
     */
    public static final int DEFAULT_MAX_COLWIDTH = Integer.MAX_VALUE;
    /**
     *
     * @uml.property name="locked" multiplicity="(0 1)"
     */
    // �Ƿ��������ģ���ʼ��ʱ����Ϊtrue��������
    private boolean locked = false;
    /**
     *
     * @uml.property name="editable" multiplicity="(0 1)"
     */
    // �Ƿ�ɱ༭
    private boolean editable = false;
    private boolean is_must_input = false;
    
    /*
     * �Ƿ���ı�
     */
    private boolean isLargeText = false;
    
    /**
     *
     * @uml.property name="sortable" multiplicity="(0 1)"
     */
    // �Ƿ������
    private boolean sortable = true;
    /**
     *
     * @uml.property name="subColumns"
     * @uml.associationEnd elementType="xingx.smartbiz.control.table.NLTableColumn"
     *                     multiplicity= "(0 -1)"
     */
    private Vector subColumns = new Vector();
    /**
     * �Ƿ���ʾС��ֵ
     */
    private boolean isTotal = false;
    /**
     * �Ƿ���ʾ�ܼ���ֵ
     *
     * @author jerry
     */
    private boolean isAllTotal = false;
    /**
     * �ϼ��кϼ��㷨�ӿڶ����������ƺϼ��㷨
     *
     * @author jerry
     */
    private ISumRowCount sumRowCount = null;
    /**
     * ֧���ж���༭�����л�
     *
     * @author jerry
     */
    private FTableModel tableModel = null;
    /**
     * �ܼƳ�ʼֵ��������Ҫ��ʾ�ܼ�ʱ��������и�ֵ��Ĭ��Ϊ0
     *
     * @author jerry
     */
    private Number sumRowData = null;
    /**
     * ���ڼ����ܼ�������
     *
     * @author jerry
     */
    private Number sumRowDataOld = null;
    private boolean editable_when_new; //������¼�¼�Ƿ�ɱ༭

    public boolean isEditable_when_new() {
        return editable_when_new;
    }

    public void setEditable_when_new(boolean editable_when_new) {
        this.editable_when_new = editable_when_new;
    }

    /**
     * ��ȡͳ������
     *
     * @return ͳ�Ƶ�����
     *
     */
    public Number getSumRowData() {
        return sumRowData;
    }

    /**
     * ����ͳ������
     *
     * @param sumRowData
     *            ����
     *
     */
    public void setSumRowData(Number sumRowData) {
        this.sumRowData = sumRowData;
    }

    /**
     * ��ȡ�ϼ��кϼ��㷨�ӿڶ���
     *
     * @return �ϼ��кϼ��㷨�ӿ�
     *
     */
    public ISumRowCount getSumRowCount() {
        return sumRowCount;
    }

    /**
     * ���úϼ��кϼ��㷨�ӿڶ���
     *
     * @param sumRowCount
     *            �ϼ��кϼ��㷨�ӿ�
     *
     */
    public void setSumRowCount(ISumRowCount sumRowCount) {
        this.sumRowCount = sumRowCount;
    }

    /**
     * ���캯��
     */
    public FBaseTableColumn() {
        init();
    }

    private void init() {
        // ����ȱʡ�ı�ʶ
        this.setIdentifier("");
        this.setHeaderValue(this.getIdentifier());
        DefaultTableCellRenderer headcr = new FBaseTableHeaderRenderer();
        headcr.setHorizontalTextPosition(JLabel.LEFT);
        this.setHeaderRenderer(headcr);

//        this.setCellRenderer(new FBaseTableCellRenderer());
        //this.setCellRenderer(new FBaseTableRowCellRenderer(new FBaseTableCellRenderer()));
        //this.setCellEditor(new FBaseTableRowCellEditor(new FBaseTableCellEditor()));
        //this.setCellEditor(new FBaseTableCellEditor());

        this.setMaxWidth(FBaseTableColumn.DEFAULT_MAX_COLWIDTH);
        this.setMinWidth(FBaseTableColumn.DEFAULT_MIN_COLWIDTH);
        // ȱʡ���
        this.setPreferredWidth(this.width);
        this.width = FBaseTableColumn.DEFAULT_COLWIDTH;
    }

    /**
     * ��ȡ�������µĿؼ�
     *
     * @return �ؼ��б�
     *
     */
    public List getSubControls() {
        ArrayList list = new ArrayList();
        list.add(this.getBodyEditor().getComponent());
        return list;
        // return new Control[] {
        // (Control)this.getBodyEditor().getComponent()};
    }

    /**
     * �����ӿؼ�
     *
     * @param control
     *            FPanel�е��ӿؼ�
     */
    public void addControl(Control control) {

        // ��������
        if (control instanceof FBaseTableColumn) {
            this.subColumns.add(control);
        } else // ����һ������ؼ�ʱ������CellRenderer,CellEditor;
        if (control instanceof AbstractDataField) {

            FBaseTableCellEditor ce = this.getBodyEditor();
            ce.setDataField((AbstractDataField) control);

            FBaseTableCellRenderer cr = this.getBodyRenderer();

            if (control instanceof FRadioGroup || control instanceof FCheckBox) {
                cr.setDataField((AbstractDataField) control);
                return;
            }

            // ����ȱʡ�Ĳ���ģ�͡�
            if (cr.getRefModel() == null) {
                RefModel model = null;
                if (control instanceof FComboBox) {
                    model = ((FComboBox) control).getRefModel();
                }
                if (control instanceof AbstractRefDataField) {
                    model = ((AbstractRefDataField) control).getRefModel();
                    //if (model instanceof TreeAssistRefModel) {
                    // ((TreeAssistRefModel) model).excuteQuery();
                    //}
                }
                cr.setRefModel(model);
            }
        }
        control.setParentControl(this);
    }

    /**
     * ��ȡ�Ų����ͣ���ʱ���ã�
     *
     * @return null
     *
     */
    public String getLayoutType() {
        return null;
    }

    /**
     * ��ȡ���ؼ�
     *
     * @return ���ؼ�
     *
     */
    public AbstractDataField getEditDataField() {
        return (AbstractDataField) this.getBodyEditor().getComponent();
    }

    /**
     * �����ӿؼ�
     *
     * @param field
     *            �ӿؼ�
     *
     */
    public void setEditDataField(AbstractDataField field) {
        this.addControl(field);
    }

    /**
     * �жϿؼ��Ƿ���Ա༭
     *
     * @return falseΪ���ɱ༭��trueΪ�ɱ༭
     */
    public boolean isEditable() {
        return this.editable;
    }

    /**
     * ���ÿؼ��Ƿ�ɱ༭�����ڸ���JComponent�޴˷�������˽�������
     *
     * @param editable
     *            falseΪ���ɱ༭��trueΪ�ɱ༭
     */
    public void setEditable(boolean editable) {
        
        this.editable = editable;
        /**
         * ���ж����ʼ���󣬱༭���Կ��л����Ķ��˴�
         *
         * @author jerry
         */
        /** ******************begin******************* */
        if (this.tableModel != null) {
            if (this.tableModel.getColumnList().size() != 0 && this.getTableModel().getColumnList() != null) {
                for (int i = 0; i < this.tableModel.getColumnList().size(); i++) {
                    if (this.getId().equals(
                            ((FTableColumnItem) this.tableModel.getColumnList().get(i)).getId())) {
                        ((FTableColumnItem) this.tableModel.getColumnList().get(i)).setEditable(editable);                        
                    }
                }
            }
        }
        /** ******************end********************* */
    }
      
    /**
     * ��ѯ�Ƿ������
     *
     * @return true��������false����������
     *
     */
    public boolean isSortable() {
        return this.sortable;
    }

    /**
     * �����Ƿ������
     *
     * @param sortable
     *            true��������false����������
     *
     */
    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    /**
     * ��ѯ�Ƿ�ɼ�
     *
     * @return true-�ɼ���false�����ɼ�
     *
     */
    public boolean isVisible() {
        if (this.getWidth() == 0) {
            return false;
        }
        return true;
    }

    /**
     * �����Ƿ�ɼ�
     *
     * @param visible
     *            true-�ɼ���false�����ɼ�
     *
     */
    public void setVisible(boolean visible) {
        if (visible) {
            this.setMaxWidth(FBaseTableColumn.DEFAULT_MAX_COLWIDTH);
            this.setMinWidth(FBaseTableColumn.DEFAULT_MIN_COLWIDTH);
            int width = getWidth() == 0 ? FBaseTableColumn.DEFAULT_COLWIDTH
                    : getWidth();
            this.setPreferredWidth(width);
            this.setWidth(width);
            this.setResizable(true);
        } else {
            this.setMaxWidth(0);
            this.setMinWidth(0);
            this.setPreferredWidth(0);
            this.setWidth(0);
            this.setResizable(false);
        }
    }

    /**
     * ��ȡ����ˮƽ����ʽ
     *
     * @return "left"�����󣬡�right�������ң���center��������
     *
     */
    public String getAlignment() {
        int align = this.getBodyRenderer().getTextAlignment();
        if (align == JLabel.CENTER) {
            return FBaseTableColumn.CENTER;
        }
        if (align == JLabel.LEFT) {
            return FBaseTableColumn.LEFT;
        }
        if (align == JLabel.RIGHT) {
            return FBaseTableColumn.RIGHT;
        }
        return null;
    }

    /**
     * �����ı�����ˮƽ���뷽ʽ
     *
     * @param alignment
     *            FBaseTableColumn.LEFT������룻FBaseTableColumn.RIGHT���Ҷ��룻FBaseTableColumn.CENTER�����ж���
     */
    public void setAlignment(String alignment) {
        if (alignment == null) {
            return;
        }
        if (alignment.equalsIgnoreCase(LEFT)) {
            this.getBodyRenderer().setTextAlignment(JLabel.LEFT);
            this.getBodyEditor().setTextAlignment(JTextField.LEFT);
        }
        if (alignment.equalsIgnoreCase(RIGHT)) {
            this.getBodyRenderer().setTextAlignment(JLabel.RIGHT);
            this.getBodyEditor().setTextAlignment(JTextField.RIGHT);
        }
        if (alignment.equalsIgnoreCase(CENTER)) {
            this.getBodyRenderer().setTextAlignment(JLabel.CENTER);
            this.getBodyEditor().setTextAlignment(JTextField.CENTER);
        }
    }

    /**
     * ��ȡ��ͷˮƽ����ʽ
     *
     * @return "left"�����󣬡�right�������ң���center��������
     *
     */
    public String getHeadAlignment() {
        int align = ((DefaultTableCellRenderer) this.getHeaderRenderer()).getHorizontalAlignment();
        if (align == JLabel.CENTER) {
            return FBaseTableColumn.CENTER;
        }
        if (align == JLabel.LEFT) {
            return FBaseTableColumn.LEFT;
        }
        if (align == JLabel.RIGHT) {
            return FBaseTableColumn.RIGHT;
        }
        return null;
    }

    /**
     * ���ñ�ͷˮƽ���뷽ʽ
     *
     * @param alignment
     *            FBaseTableColumn.LEFT������룻FBaseTableColumn.RIGHT���Ҷ��룻FBaseTableColumn.CENTER�����ж���
     */
    public void setHeadAlignment(String alignment) {
        if (alignment == null) {
            return;
        }

        if (alignment.equalsIgnoreCase(LEFT)) {
            this.getHeadRenderer().setHorizontalAlignment(JLabel.LEFT);
        }
        if (alignment.equalsIgnoreCase(RIGHT)) {
            this.getHeadRenderer().setHorizontalAlignment(JLabel.RIGHT);
        }
        if (alignment.equalsIgnoreCase(CENTER)) {
            this.getHeadRenderer().setHorizontalAlignment(JLabel.CENTER);
        }

    }

    /**
     * ��ȡ����ɫ
     *
     * @return ��ɫ����
     *
     */
    public java.awt.Color getBackground() {
        return this.getBodyRenderer().getBackColor();
    }

    /**
     * ���ñ���ɫ����
     *
     * @param color
     *            color��һ��ʮ������������"#FF00EE".
     */
    public void setBackground(String color) {
        try {
            if (!color.startsWith("#")) {
                color = "#" + color;
            }
            java.awt.Color c = java.awt.Color.decode(color);
            this.getBodyRenderer().setBackColor(c);
        } catch (Exception ex) {
        }
    }

    /**
     * ��ȡǰ��ɫ����
     *
     * @return ��ɫ����
     *
     */
    public java.awt.Color getForeground() {
        return this.getBodyRenderer().getForeColor();
    }

    /**
     * ���ñ���ɫ����
     *
     * @param color
     *            color��һ��ʮ������������"#FF00EE".
     */
    public void setForeground(String color) {
        try {
            if (!color.startsWith("#")) {
                color = "#" + color;
            }
            java.awt.Color c = java.awt.Color.decode(color);
            this.getBodyRenderer().setForeColor(c);
        } catch (Exception ex) {
        }
    }

    /**
     * ��ȡ��ͷ��ɫ����
     *
     * @return ��ɫ����
     *
     */
    public java.awt.Color getHeadBackground() {
        return ((DefaultTableCellRenderer) this.getHeaderRenderer()).getBackground();
    }

    /**
     * ���ñ�ͷ��ɫ
     *
     * @param color
     *            ��ɫ�ַ���
     *
     */
    public void setHeadBackground(String color) {
        try {
            if (!color.startsWith("#")) {
                color = "#" + color;
            }
            java.awt.Color c = java.awt.Color.decode(color);
            this.getHeadRenderer().setBackground(c);
        } catch (Exception ex) {
        }
    }

    /**
     * ��ȡ��ͷǰ��ɫ����
     *
     * @return ��ɫ����
     *
     */
    public java.awt.Color getHeadForeground() {
        return ((DefaultTableCellRenderer) this.getHeaderRenderer()).getForeground();
    }

    /**
     * ���ñ�ͷǰ��ɫ
     *
     * @param color
     *            ��ɫ�ַ���
     *
     */
    public void setHeadForeground(String color) {
        try {
            if (!color.startsWith("#")) {
                color = "#" + color;
            }
            java.awt.Color c = java.awt.Color.decode(color);
            this.getHeadRenderer().setForeground(c);
        } catch (Exception ex) {
        }
    }

    /**
     * ��ѯ���߿��Ƿ��Ǵ������
     *
     * @return true���ǣ�false����
     *
     */
    public boolean isBold() {
        return this.getBodyRenderer().isBold();
    }

    /**
     * ���ñ��߿��Ƿ��Ǵ������
     *
     * @param bold
     *            true���ǣ�false����
     *
     */
    public void setBold(boolean bold) {
        this.getBodyRenderer().setBold(bold);
    }

    /**
     * ��ѯ��ͷ�Ƿ��Ǵ���
     *
     * @return true���ǣ�false����
     *
     */
    public boolean isHeadBold() {
        return this.getHeadRenderer().isBold();

    }

    /**
     * ���ñ�ͷ�Ƿ��Ǵ���
     *
     * @param bold
     *            true���ǣ�false����
     *
     */
    public void setHeadBold(boolean bold) {
        this.getHeadRenderer().setBold(bold);
    }

    /**
     * �����Ƿ���ʾС��ֵ
     *
     * @param isTotal
     *            true���ǣ�false����
     *
     */
    public void setIsTotal(boolean isTotal) {
        this.isTotal = isTotal;
    }

    /**
     * ��ѯ�Ƿ���ʾС��ֵ
     *
     * @return true���ǣ�false����
     *
     */
    public boolean isTotal() {
        return this.isTotal;
    }

    /**
     * ����id
     *
     * @param id
     *            ���õ�id
     */
    public void setId(String id) {
        this.setIdentifier(id);
        // this.setHeaderValue(id);
    }

    /**
     * ��ȡid
     *
     * @return id
     */
    public String getId() {
        return this.getIdentifier().toString();
    }

    /**
     * ���ñ���
     *
     * @param title
     *            ����
     */
    public void setTitle(String title) {
        this.getHeadRenderer().setTitle(title);
        // this.updateHeader();
        this.setHeaderValue(title);
    }

    /**
     * ��ȡ����
     *
     * @return ����
     *
     */
    public String getTitle() {
        return ((FBaseTableHeaderRenderer) this.getHeaderRenderer()).getTitle();
    }

    /**
     * ���ñ����Ƿ�ɼ�
     *
     * @param titleVisible
     *            true���ǣ�false����
     */
    public void setTitleVisible(boolean titleVisible) {
//        ((FBaseTableHeaderRenderer) this.getHeaderRenderer()).setTitleVisible(titleVisible);
    }

    /**
     * �жϱ����Ƿ�ɼ�
     *
     * @return true���ǣ�false����
     */
    public boolean isTitleVisible() {
        return true;
//        return this.getHeadRenderer().isTitleVisible();
    }

    /**
     * ��������ģ��
     *
     * @param model
     *            ����ģ��
     */
    public void setRefModel(RefModel model) {
        this.getBodyRenderer().setRefModel(model);
    }

    /**
     * ��������ģ��
     *
     * @param modelClass
     *            ����ģ����
     */
    public void setRefModel(String modelClass) {
        if (modelClass != null) {
            try {
                this.getBodyRenderer().setRefModel(
                        RefModelFactory.getRefModel(modelClass));
            } catch (Exception ex) {
                System.out.println("��������ݲ���ģ�����ò���ȷ��");
            }
        }

    }

    /**
     * ��ȡ����ģ��
     *
     * @return ����ģ�Ͷ���
     *
     */
    public String getRefModel() {
        if (this.getBodyRenderer().getRefModel() != null) {
            return this.getBodyRenderer().getRefModel().getClass().getName();
        }
        return null;
    }

    protected FBaseTableHeaderRenderer getHeadRenderer() {
        return (FBaseTableHeaderRenderer) this.getHeaderRenderer();
    }

    protected FBaseTableCellRenderer getBodyRenderer() {

        if (super.getCellRenderer() instanceof FBaseTableRowCellRenderer) {
            FBaseTableRowCellRenderer renderer = (FBaseTableRowCellRenderer) super.getCellRenderer();
            return (FBaseTableCellRenderer) renderer.defaultRenderer;
        } else {
            return (FBaseTableCellRenderer) super.getCellRenderer();
        }
    }

    protected FBaseTableCellEditor getBodyEditor() {

        if (super.getCellEditor() instanceof FBaseTableRowCellEditor) {
            FBaseTableRowCellEditor editor = (FBaseTableRowCellEditor) super.getCellEditor();
            return (FBaseTableCellEditor) editor.defaultEditor;
        } else {
            return (FBaseTableCellEditor) super.getCellEditor();
        }

    }

    /**
     * �����Ƿ���������
     *
     * @param locked
     *            true���ǣ�false����
     *
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * ��ѯ�Ƿ���������
     *
     * @return true���ǣ�false����
     *
     */
    public boolean isLocked() {
        return this.locked;
    }

    /**
     * ����������ʾ��ʽ
     *
     * @param format
     *            local_currency,assist_currency,integer,decimal,percent,permill
     */
    public void setFormat(String format) {
        // �������ݸ�ʽ��
        // ϵͳԤ��������ݸ�ʽ����ٷֱ�/���ڸ�ʽ/���ֵķָ����/���ֵȣ��д���һ��������
        if (FormatManager.LOCAL_CURRENCY.equalsIgnoreCase(format)) {
            this.getBodyRenderer().setFormat(
                    FormatManager.getLocalCurrencyFormat());
        } else if (FormatManager.ASSIST_CURRENCY.equalsIgnoreCase(format)) {
            this.getBodyRenderer().setFormat(
                    FormatManager.getAssistCurrencyFormat());
        } else if (FormatManager.INTEGER_NUMBER.equalsIgnoreCase(format)) {
            this.getBodyRenderer().setFormat(
                    FormatManager.getIntegerNumberFormat());
            //this.getBodyEditor().setDataField(new FIntegerField());
        } else if (FormatManager.DECIMAL_NUMBER.equalsIgnoreCase(format)) {
            this.getBodyRenderer().setFormat(
                    FormatManager.getDecimalNumberFormat());
            //this.getBodyEditor().setDataField(new FDecimalField());
        } else if (FormatManager.PERCENT.equalsIgnoreCase(format)) {
            this.getBodyRenderer().setFormat(FormatManager.getPercentFormat());
        } else if (FormatManager.PERMILL.equalsIgnoreCase(format)) {
            this.getBodyRenderer().setFormat(FormatManager.getPermillFormat());
        }
    }

    /**
     * ���ݿؼ�id��ȡ�ؼ�
     *
     * @param controlID
     *            �ؼ�id
     * @return �ؼ�
     *
     */
    public Control getSubControlByID(String controlID) {
        if (this.getEditDataField().getId().equalsIgnoreCase(controlID)) {
            return this.getEditDataField();
        }
        return null;
    }

    /**
     * ����ӱ����
     *
     * @return �ӱ����
     *
     */
    public FBaseTableColumn[] getSubTableColumns() {
        if (this.subColumns.size() == 0) {
            return null;
        }
        FBaseTableColumn[] columns = new FBaseTableColumn[this.subColumns.size()];

        this.subColumns.copyInto(columns);
        return columns;
    }

    /**
     * ������е��ӱ����
     *
     * @return �ӱ����
     *
     */
    public FBaseTableColumn[] getAllSubTableColumns() {

        if (subColumns.size() == 0) {
            return new FBaseTableColumn[]{this};
        }

        Vector v = new Vector();
        for (int i = 0; i < subColumns.size(); i++) {
            FBaseTableColumn col = (FBaseTableColumn) subColumns.get(i);
            FBaseTableColumn[] subs = col.getAllSubTableColumns();
            for (int j = 0; j < subs.length; j++) {
                v.add(subs[j]);
            }
        }

        FBaseTableColumn[] cols = new FBaseTableColumn[v.size()];
        v.copyInto(cols);
        return cols;
    }

    /**
     * ��ȡ�еĲ��
     *
     * @return �еĲ��
     *
     */
    public int getColumnLayers() {
        FBaseTableColumn[] cols = this.getSubTableColumns();
        int max = 1;
        for (int i = 0; cols != null && i < cols.length; i++) {
            int layers = cols[i].getColumnLayers() + 1;
            if (layers > max) {
                max = layers;
            }
        }
        return max;
    }

    /**
     * ��ȡ���ؼ�
     *
     * @return ���ؼ�
     *
     */
    public Control getParentControl() {
        return parentControl;
    }

    /**
     * ���ø��ؼ�
     *
     * @param parentControl
     *            ���ؼ�
     */
    public void setParentControl(Control parentControl) {
        this.parentControl = parentControl;
    }

    /**
     * ����id��ȡ�����ӿؼ�
     *
     * @param id
     *            �ؼ�id
     *
     * @return �ؼ�
     *
     */
    public Control getSubControl(String id) {

        if (id == null) {
            return null;
        }

        for (int i = 0; i < this.subColumns.size(); i++) {
            FBaseTableColumn col = (FBaseTableColumn) this.subColumns.get(i);
            if (id.equalsIgnoreCase((String) col.getIdentifier())) {
                return col;
            }
        }
        return null;
    }

    /**
     * �����ӿؼ�,ͬʱ����Լ��
     *
     * @param control
     *            �ӿؼ�
     * @param contraint
     *            �ؼ���������
     */
    public void addControl(Control control, Object contraint) {
    }

    /**
     * ��ȡ��������ģ��
     *
     * @return ��������ģ��
     *
     */
    public FTableModel getTableModel() {
        return tableModel;
    }

    /**
     * ���ñ�������ģ��
     *
     * @param tableModel
     *            ��������ģ��
     *
     */
    public void setTableModel(FTableModel tableModel) {
        this.tableModel = tableModel;
    }

    /**
     * ��ȡ�����ܼƵ�������
     *
     * @return �����ܼƵ�������
     *
     */
    public Number getSumRowDataOld() {
        return sumRowDataOld;
    }

    /**
     * ���ü����ܼƵ�������
     *
     * @param sumRowDataOld
     *            �����ܼƵ�������
     *
     */
    public void setSumRowDataOld(Number sumRowDataOld) {
        this.sumRowDataOld = sumRowDataOld;
    }

    /**
     * ��ѯ�Ƿ���ʾ�ܼ���ֵ
     *
     * @return true����ʾ ��false������ʾ
     *
     */
    public boolean isAllTotal() {
        return isAllTotal;
    }

    /**
     * �����Ƿ���ʾ�ܼ���ֵ
     *
     * @param isAllTotal
     *            true����ʾ ��false������ʾ
     *
     */
    public void setAllTotal(boolean isAllTotal) {
        this.isAllTotal = isAllTotal;
    }

    /**
     * ȡ�ñ�ͷ��ɫ
     *
     * @return Color ��ͷ��ɫ
     * @author jerry
     */
    public Color getTitleColor() {
        return this.getHeadForeground();
    }

    /**
     * ���ñ�ͷ��ɫ
     *
     * @param color
     *            ��ͷ��ɫ
     *
     * @author jerry
     */
    public void setTitleColor(String color) {
        this.setHeadForeground(color);

    }

    /**
     * �ж��Ƿ����¼��
     * @return
     */
    public boolean is_must_input() {
        return this.is_must_input;
    }

    /**
     * �����Ƿ����¼��
     * @param is_must_input
     */
    public void setIs_must_input(boolean is_must_input) {
        this.is_must_input = is_must_input;
    }

    /**
     * �����е�Ԫ�������
     * @param row ����ģ�͵��к�
     * @param _renderer  �����
     * @author lindx 2008-06-10
     */
    protected void setRenderAt(int row, TableCellRenderer _renderer) {

        FBaseTableRowCellRenderer renderer = (FBaseTableRowCellRenderer) super.getCellRenderer();

        renderer.setRendererAt(row, _renderer);

    }

    /**
     * �����е�Ԫ��༭�ؼ�
     * @param row ����ģ�͵��к�
     * @param _editor  �༭�ؼ�
     * @author lindx 2008-06-10
     */
    protected void setEditorAt(int row, TableCellEditor _editor) {
        FBaseTableRowCellEditor editor = (FBaseTableRowCellEditor) super.getCellEditor();
        editor.setEditorAt(row, _editor);

    }

    /**
     * ��д�÷���
     */
    public TableCellRenderer getCellRenderer() {
        if (super.getCellRenderer() instanceof FBaseTableRowCellRenderer) {
            FBaseTableRowCellRenderer renderer = (FBaseTableRowCellRenderer) super.getCellRenderer();
            return renderer.defaultRenderer;
        } else {
            return super.getCellRenderer();
        }
    }

    public void setLargeText(boolean isLargeText) {
        this.isLargeText = isLargeText;
        
        if (this.tableModel != null) {
            if (this.tableModel.getColumnList().size() != 0 && this.getTableModel().getColumnList() != null) {
                for (int i = 0; i < this.tableModel.getColumnList().size(); i++) {
                    if (this.getId().equals(
                            ((FTableColumnItem) this.tableModel.getColumnList().get(i)).getId())) {                        
                        ((FTableColumnItem) this.tableModel.getColumnList().get(i)).setLargeText(isLargeText);
                    }
                }
            }
        }
    }
    
    public boolean isLargeText() {
        return isLargeText;
    }
    
}